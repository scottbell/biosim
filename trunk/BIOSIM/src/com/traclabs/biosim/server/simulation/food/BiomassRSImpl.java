package biosim.server.simulation.food;

import biosim.idl.simulation.food.*;
import biosim.idl.simulation.power.*;
import biosim.idl.simulation.water.*;
import biosim.idl.simulation.environment.*;
import biosim.idl.util.log.*;
import biosim.server.util.*;
import biosim.server.simulation.framework.*;
import biosim.idl.simulation.framework.*;
import biosim.idl.framework.*;
import java.util.*;
/**
 * The Biomass RS is essentially responsible for growing plants.
 * The Biomass RS consists of many ShelfImpls, and inside them, a Plant.
 * The ShelfImpl gathers water and light for the plant.  The plant itself breathes from the atmosphere and produces biomass.
 * The plant matter (biomass) is fed into the food processor to create food
 * for the crew.  The plants can also (along with the AirRS) take CO2 out of the air and add O2.
 *
 * @author    Scott Bell
 */

public class BiomassRSImpl extends SimBioModuleImpl implements BiomassRSOperations, PotableWaterConsumerOperations, AirConsumerOperations, AirProducerOperations, GreyWaterConsumerOperations, BiomassProducerOperations, DirtyWaterProducerOperations{
	private List myShelves;
	private int shelfCapacity = 100;
	private List shelfLogs;
	private float[] powerMaxFlowRates;
	private float[] biomassMaxFlowRates;
	private float[] potableWaterMaxFlowRates;
	private float[] greyWaterMaxFlowRates;
	private float[] airInMaxFlowRates;
	private float[] airOutMaxFlowRates;
	private float[] powerActualFlowRates;
	private float[] biomassActualFlowRates;
	private float[] potableWaterActualFlowRates;
	private float[] dirtyWaterMaxFlowRates;
	private float[] greyWaterActualFlowRates;
	private float[] airInActualFlowRates;
	private float[] airOutActualFlowRates;
	private float[] dirtyWaterActualFlowRates;
	private float[] powerDesiredFlowRates;
	private float[] biomassDesiredFlowRates;
	private float[] potableWaterDesiredFlowRates;
	private float[] dirtyWaterDesiredFlowRates;
	private float[] greyWaterDesiredFlowRates;
	private float[] airInDesiredFlowRates;
	private float[] airOutDesiredFlowRates;
	private GreyWaterStore[] myGreyWaterStores;
	private DirtyWaterStore[] myDirtyWaterStores;
	private PotableWaterStore[] myPotableWaterStores;
	private PowerStore[] myPowerStores;
	private BiomassStore[] myBiomassStores;
	private SimEnvironment[] myAirInputs;                                               
	private SimEnvironment[] myAirOutputs;

	public BiomassRSImpl(int pID){
		super(pID);
		myShelves = new Vector();
		myGreyWaterStores = new GreyWaterStore[0];
		myPotableWaterStores = new PotableWaterStore[0];
		myDirtyWaterStores = new DirtyWaterStore[0];
		myPowerStores = new PowerStore[0];
		myBiomassStores = new BiomassStore[0];
		myAirInputs = new SimEnvironment[0];
		myAirOutputs = new SimEnvironment[0];
		powerMaxFlowRates = new float[0];
		biomassMaxFlowRates = new float[0];
		potableWaterMaxFlowRates = new float[0];
		greyWaterMaxFlowRates = new float[0];
		airInMaxFlowRates = new float[0];
		airOutMaxFlowRates = new float[0];
		powerActualFlowRates = new float[0];
		biomassActualFlowRates = new float[0];
		dirtyWaterActualFlowRates = new float[0];
		potableWaterActualFlowRates = new float[0];
		greyWaterActualFlowRates = new float[0];
		airInActualFlowRates = new float[0];
		airOutActualFlowRates = new float[0];
		powerDesiredFlowRates = new float[0];
		biomassDesiredFlowRates = new float[0];
		potableWaterDesiredFlowRates = new float[0];
		dirtyWaterDesiredFlowRates = new float[0];
		greyWaterDesiredFlowRates = new float[0];
		airInDesiredFlowRates = new float[0];
		airOutDesiredFlowRates = new float[0];
	}

	public Shelf[] getShelves(){
		Shelf[] theShelfArray = new Shelf[myShelves.size()];
		int i = 0;
		for (Iterator iter = myShelves.iterator(); iter.hasNext(); i++){
			ShelfImpl currentShelf = (ShelfImpl)(iter.next());
			theShelfArray[i] = ShelfHelper.narrow(OrbUtils.poaToCorbaObj(currentShelf));
		}
		return theShelfArray;
	}
	
	public int getNumberOfShelves(){
		return myShelves.size();
	}
	
	
	public Shelf createNewShelf(PlantType pType, float pCropArea){
		ShelfImpl newShelfImpl = new ShelfImpl(pType, pCropArea, this);
		myShelves.add(newShelfImpl);
		return ShelfHelper.narrow(OrbUtils.poaToCorbaObj(newShelfImpl));
	}

	/**
	* Resets production/consumption levels and death/affliction flags
	*/
	public void reset(){
		super.reset();
		clearActualFlowRates();
		for (Iterator iter = myShelves.iterator(); iter.hasNext();){
			ShelfImpl currentShelf = (ShelfImpl)(iter.next());
			currentShelf.reset();
		}
	}
	
	private void clearActualFlowRates(){
		Arrays.fill(powerActualFlowRates, 0f);
		Arrays.fill(biomassActualFlowRates, 0f);
		Arrays.fill(potableWaterActualFlowRates, 0f);                  
		Arrays.fill(greyWaterActualFlowRates, 0f);
		Arrays.fill(dirtyWaterActualFlowRates, 0f);
		Arrays.fill(airInActualFlowRates, 0f);
		Arrays.fill(airOutActualFlowRates, 0f);
	}
	
	private String arrayToString(float[] pArray){
		StringBuffer buffer = new StringBuffer();
		buffer.append("[");
		for (int i = 0; i < pArray.length; i++)
			buffer.append(pArray[i]+", ");
		buffer.append("]");
		return buffer.toString();
	}

	public void tick(){
		clearActualFlowRates();
		for (Iterator iter = myShelves.iterator(); iter.hasNext();){
			ShelfImpl currentShelf = (ShelfImpl)(iter.next());
			currentShelf.tick();
		}
		if (moduleLogging)
			log();
	}

	/**
	* Returns the name of this module (BiomassRS)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "BiomassRS"+getID();
	}

	protected void log(){
		//If not initialized, fill in the log
		if (!logInitialized){
			shelfLogs = new Vector();
			for (Iterator iter = myShelves.iterator(); iter.hasNext();){
				ShelfImpl currentShelf = (ShelfImpl)(iter.next());
				LogNode newShelfHead= myLog.addChild("shelf");
				shelfLogs.add(newShelfHead);
				currentShelf.log(newShelfHead);
			}
			logInitialized = true;
		}
		else{
			int i = 0;
			for (Iterator iter = myShelves.iterator(); iter.hasNext(); i++){
				ShelfImpl currentShelf = (ShelfImpl)(iter.next());
				LogNode shelfHead = (LogNode)(shelfLogs.get(i));
				currentShelf.log(shelfHead);
			}
		}
		sendLog(myLog);
	}

	/**
	* For fast reference to the log tree
	*/
	private class LogIndex{
		public LogNode nothingIndex;
	}
	
	//Power Inputs
	public void setPowerInputMaxFlowRate(float watts, int index){
		powerMaxFlowRates[index] = watts;
	}
	public float getPowerInputMaxFlowRate(int index){
		return powerMaxFlowRates[index];
	}
	public float[] getPowerInputMaxFlowRates(){
		return powerMaxFlowRates;
	}
	public void setPowerInputDesiredFlowRate(float watts, int index){
		powerDesiredFlowRates[index] = watts;
	}
	public float getPowerInputDesiredFlowRate(int index){
		return powerDesiredFlowRates[index];
	}
	public float[] getPowerInputDesiredFlowRates(){
		return powerDesiredFlowRates;
	}
	public float getPowerInputActualFlowRate(int index){
		return powerActualFlowRates[index];
	}
	public float[] getPowerInputActualFlowRates(){
		return powerActualFlowRates;
	}
	public void setPowerInputs(PowerStore[] sources, float[] maxFlowRates, float[] desiredFlowRates){
		myPowerStores = sources;
		powerMaxFlowRates = maxFlowRates;
		powerDesiredFlowRates = desiredFlowRates;
		powerActualFlowRates = new float[powerDesiredFlowRates.length]; 
	}
	public PowerStore[] getPowerInputs(){
		return myPowerStores;
	}
	
	//Grey Water Inputs
	public void setGreyWaterInputMaxFlowRate(float liters, int index){
		greyWaterMaxFlowRates[index] = liters;
	}
	public float getGreyWaterInputMaxFlowRate(int index){
		return greyWaterMaxFlowRates[index];
	}
	public float[] getGreyWaterInputMaxFlowRates(){
		return greyWaterMaxFlowRates;
	}
	public void setGreyWaterInputDesiredFlowRate(float liters, int index){
		greyWaterDesiredFlowRates[index] = liters;
	}
	public float getGreyWaterInputDesiredFlowRate(int index){
		return greyWaterDesiredFlowRates[index];
	}
	public float[] getGreyWaterInputDesiredFlowRates(){
		return greyWaterDesiredFlowRates;
	}
	public float getGreyWaterInputActualFlowRate(int index){
		return greyWaterActualFlowRates[index];
	}
	public float[] getGreyWaterInputActualFlowRates(){
		return greyWaterActualFlowRates;
	}
	public void setGreyWaterInputs(GreyWaterStore[] sources, float[] maxFlowRates, float[] desiredFlowRates){
		myGreyWaterStores = sources;
		greyWaterMaxFlowRates = maxFlowRates;
		greyWaterDesiredFlowRates = desiredFlowRates;
		greyWaterActualFlowRates = new float[greyWaterDesiredFlowRates.length]; 
	}
	public GreyWaterStore[] getGreyWaterInputs(){
		return myGreyWaterStores;
	}
	
	//Potable Water Inputs
	public void setPotableWaterInputMaxFlowRate(float liters, int index){
		potableWaterMaxFlowRates[index] = liters;
	}
	public float getPotableWaterInputMaxFlowRate(int index){
		return potableWaterMaxFlowRates[index];
	}
	public float[] getPotableWaterInputMaxFlowRates(){
		return potableWaterMaxFlowRates;
	}
	public void setPotableWaterInputDesiredFlowRate(float liters, int index){
		potableWaterDesiredFlowRates[index] = liters;
	}
	public float getPotableWaterInputDesiredFlowRate(int index){
		return potableWaterDesiredFlowRates[index];
	}
	public float[] getPotableWaterInputDesiredFlowRates(){
		return potableWaterDesiredFlowRates;
	}
	public float getPotableWaterInputActualFlowRate(int index){
		return potableWaterActualFlowRates[index];
	}
	public float[] getPotableWaterInputActualFlowRates(){
		return potableWaterActualFlowRates;
	}
	public void setPotableWaterInputs(PotableWaterStore[] sources, float[] maxFlowRates, float[] desiredFlowRates){
		myPotableWaterStores = sources;
		potableWaterMaxFlowRates = maxFlowRates;
		potableWaterDesiredFlowRates = desiredFlowRates;
		potableWaterActualFlowRates = new float[potableWaterDesiredFlowRates.length]; 
	}
	public PotableWaterStore[] getPotableWaterInputs(){
		return myPotableWaterStores;
	}
	
	//Biomass Outputs
	public void setBiomassOutputMaxFlowRate(float kilograms, int index){
		biomassMaxFlowRates[index] = kilograms;
	}
	public float getBiomassOutputMaxFlowRate(int index){
		return biomassMaxFlowRates[index];
	}
	public float[] getBiomassOutputMaxFlowRates(){
		return biomassMaxFlowRates;
	}
	public void setBiomassOutputDesiredFlowRate(float kilograms, int index){
		biomassDesiredFlowRates[index] = kilograms;
	}
	public float getBiomassOutputDesiredFlowRate(int index){
		return biomassDesiredFlowRates[index];
	}
	public float[] getBiomassOutputDesiredFlowRates(){
		return biomassDesiredFlowRates;
	}
	public float getBiomassOutputActualFlowRate(int index){
		return biomassActualFlowRates[index];
	}
	public float[] getBiomassOutputActualFlowRates(){
		return biomassActualFlowRates;
	}
	public void setBiomassOutputs(BiomassStore[] destinations, float[] maxFlowRates, float[] desiredFlowRates){
		myBiomassStores = destinations;
		biomassMaxFlowRates = maxFlowRates;
		biomassDesiredFlowRates = desiredFlowRates;
		biomassActualFlowRates = new float[biomassDesiredFlowRates.length]; 
	}
	public BiomassStore[] getBiomassOutputs(){
		return myBiomassStores;
	}
	public void addBiomassOutputActualFlowRates(int index, float value){
		biomassActualFlowRates[index] += value;
	}
	
	
	//DirtyWater Outputs
	public void setDirtyWaterOutputMaxFlowRate(float liters, int index){
		dirtyWaterMaxFlowRates[index] = liters;
	}
	public float getDirtyWaterOutputMaxFlowRate(int index){
		return dirtyWaterMaxFlowRates[index];
	}
	public float[] getDirtyWaterOutputMaxFlowRates(){
		return dirtyWaterMaxFlowRates;
	}
	public void setDirtyWaterOutputDesiredFlowRate(float liters, int index){
		dirtyWaterDesiredFlowRates[index] = liters;
	}
	public float getDirtyWaterOutputDesiredFlowRate(int index){
		return dirtyWaterDesiredFlowRates[index];
	}
	public float[] getDirtyWaterOutputDesiredFlowRates(){
		return dirtyWaterDesiredFlowRates;
	}
	public float getDirtyWaterOutputActualFlowRate(int index){
		return dirtyWaterActualFlowRates[index];
	}
	public float[] getDirtyWaterOutputActualFlowRates(){
		return dirtyWaterActualFlowRates;
	}
	public void setDirtyWaterOutputs(DirtyWaterStore[] destinations, float[] maxFlowRates, float[] desiredFlowRates){
		myDirtyWaterStores = destinations;
		dirtyWaterMaxFlowRates = maxFlowRates;
		dirtyWaterDesiredFlowRates = desiredFlowRates;
		dirtyWaterActualFlowRates = new float[dirtyWaterDesiredFlowRates.length]; 
	}
	public DirtyWaterStore[] getDirtyWaterOutputs(){
		return myDirtyWaterStores;
	}
	public void addDirtyWaterOutputActualFlowRates(int index, float value){
		dirtyWaterActualFlowRates[index] += value;
	}
	
	//Air Inputs
	public void setAirInputMaxFlowRate(float moles, int index){
		airInMaxFlowRates[index] = moles;
	}
	public float getAirInputMaxFlowRate(int index){
		return airInMaxFlowRates[index];
	}
	public float[] getAirInputMaxFlowRates(){
		return airInMaxFlowRates;
	}
	public void setAirInputDesiredFlowRate(float moles, int index){
		airInDesiredFlowRates[index] = moles;
	}
	public float getAirInputDesiredFlowRate(int index){
		return airInDesiredFlowRates[index];
	}
	public float[] getAirInputDesiredFlowRates(){
		return airInDesiredFlowRates;
	}
	public float getAirInputActualFlowRate(int index){
		return airInActualFlowRates[index];
	}
	public float[] getAirInputActualFlowRates(){
		return airInActualFlowRates;
	}
	public void setAirInputs(SimEnvironment[] sources, float[] maxFlowRates, float[] desiredFlowRates){
		myAirInputs = sources;
		airInMaxFlowRates = maxFlowRates;
		airInDesiredFlowRates = desiredFlowRates;
		airInActualFlowRates = new float[airInDesiredFlowRates.length]; 
	}
	public SimEnvironment[] getAirInputs(){
		return myAirInputs;
	}
	public void addAirInputActualFlowRates(int index, float value){
		airInActualFlowRates[index] += value;
	}
	
	//Air Outputs
	public void setAirOutputMaxFlowRate(float moles, int index){
		airOutMaxFlowRates[index] = moles;
	}
	public float getAirOutputMaxFlowRate(int index){
		return airOutMaxFlowRates[index];
	}
	public float[] getAirOutputMaxFlowRates(){
		return airOutMaxFlowRates;
	}
	public void setAirOutputDesiredFlowRate(float moles, int index){
		airOutDesiredFlowRates[index] = moles;
	}
	public float getAirOutputDesiredFlowRate(int index){
		return airOutDesiredFlowRates[index];
	}
	public float[] getAirOutputDesiredFlowRates(){
		return airOutDesiredFlowRates;
	}
	public float getAirOutputActualFlowRate(int index){
		return airOutActualFlowRates[index];
	}
	public float[] getAirOutputActualFlowRates(){
		return airOutActualFlowRates;
	}
	public void setAirOutputs(SimEnvironment[] sources, float[] maxFlowRates, float[] desiredFlowRates){
		myAirOutputs = sources;
		airOutMaxFlowRates = maxFlowRates;
		airOutDesiredFlowRates = desiredFlowRates;
		airOutActualFlowRates = new float[airOutDesiredFlowRates.length]; 
	}
	public SimEnvironment[] getAirOutputs(){
		return myAirOutputs;
	}
	
	public void addAirOutputActualFlowRates(int index, float value){
		airOutActualFlowRates[index] += value;
	}
}
