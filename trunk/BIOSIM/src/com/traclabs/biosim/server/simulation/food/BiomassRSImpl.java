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

public class BiomassRSImpl extends SimBioModuleImpl implements BiomassRSOperations, PotableWaterConsumerOperations, AirConsumerOperations, AirProducerOperations, GreyWaterConsumerOperations, BiomassProducerOperations{
	private List myShelves;
	private int shelfCapacity = 100;
	private List shelfLogs;
	private float powerMaxFlowRate = 0f;
	private float potableWaterMaxFlowRate = 0f;
	private float greyWaterMaxFlowRate = 0f;
	private float biomassMaxFlowRate = 0f;
	private float[] powerMaxFlowRates;
	private float[] biomassMaxFlowRates;
	private float[] potableWaterMaxFlowRates;
	private float[] greyWaterMaxFlowRates;
	private float[] airInMaxFlowRates;
	private float[] airOutMaxFlowRates;
	private GreyWaterStore[] myGreyWaterStores;
	private PotableWaterStore[] myPotableWaterStores;
	private PowerStore[] myPowerStores;
	private BiomassStore[] myBiomassStores;
	private SimEnvironment[] myAirInputs;
	private SimEnvironment[] myAirOutputs;

	public BiomassRSImpl(int pID){
		super(pID);
		myGreyWaterStores = new GreyWaterStore[0];
		myPotableWaterStores = new PotableWaterStore[0];
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
		myShelves = new Vector(shelfCapacity);
		for (int i = 0; i < shelfCapacity; i++){
			myShelves.add(new ShelfImpl(pID, this));
		}
	}

	public BiomassRSImpl(int pID, int pShelfCapacity){
		super(pID);
		shelfCapacity = pShelfCapacity;
		myShelves = new Vector(shelfCapacity);
		for (int i = 0; i < shelfCapacity; i++){
			myShelves.add(new ShelfImpl(pID, this));
		}
	}

	public float getTotalPotableWaterConsumed(){
		float theWater = 0.0f;
		for (Iterator iter = myShelves.iterator(); iter.hasNext();){
			ShelfImpl currentShelf = (ShelfImpl)(iter.next());
			theWater += currentShelf.getPotableWaterConsumed();
		}
		return theWater;
	}

	public float getTotalBiomassProduced(){
		float theBiomass = 0.0f;
		for (Iterator iter = myShelves.iterator(); iter.hasNext();){
			ShelfImpl currentShelf = (ShelfImpl)(iter.next());
			theBiomass += currentShelf.getBiomassProduced();
		}
		return theBiomass;
	}

	public float getTotalCO2Consumed(){
		float theCO2Consumed = 0.0f;
		for (Iterator iter = myShelves.iterator(); iter.hasNext();){
			ShelfImpl currentShelf = (ShelfImpl)(iter.next());
			theCO2Consumed += currentShelf.getCO2Consumed();
		}
		return theCO2Consumed;
	}

	public float getTotalO2Produced(){
		float theO2Produced = 0.0f;
		for (Iterator iter = myShelves.iterator(); iter.hasNext();){
			ShelfImpl currentShelf = (ShelfImpl)(iter.next());
			theO2Produced += currentShelf.getO2Produced();
		}
		return theO2Produced;
	}

	public float getTotalGreyWaterConsumed(){
		float theWater = 0.0f;
		for (Iterator iter = myShelves.iterator(); iter.hasNext();){
			ShelfImpl currentShelf = (ShelfImpl)(iter.next());
			theWater += currentShelf.getGreyWaterConsumed();
		}
		return theWater;
	}

	public float getTotalPowerConsumed(){
		float thePower = 0.0f;
		for (Iterator iter = myShelves.iterator(); iter.hasNext();){
			ShelfImpl currentShelf = (ShelfImpl)(iter.next());
			thePower += currentShelf.getPowerConsumed();
		}
		return thePower;
	}

	public float getTotalPlantArea(){
		float thePlantArea = 0.0f;
		for (Iterator iter = myShelves.iterator(); iter.hasNext();){
			ShelfImpl currentShelf = (ShelfImpl)(iter.next());
			thePlantArea += currentShelf.getArea();
		}
		return thePlantArea;
	}

	public String[] getPlantTypes(){
		List typeList = new Vector();
		for (Iterator iter = myShelves.iterator(); iter.hasNext();){
			ShelfImpl currentShelf = (ShelfImpl)(iter.next());
			if (!typeList.contains(currentShelf.getPlantType()))
				typeList.add(currentShelf.getPlantType());
		}
		String[] plantTypeArray = new String[typeList.size()];
		return (String[])(typeList.toArray(plantTypeArray));
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

	private void setProductionRate(float pProductionRate){
		for (Iterator iter = myShelves.iterator(); iter.hasNext();){
			ShelfImpl currentShelf = (ShelfImpl)(iter.next());
			currentShelf.setProductionRate(pProductionRate);
		}
	}

	protected String getMalfunctionName(MalfunctionIntensity pIntensity, MalfunctionLength pLength){
		String returnName = new String();
		if (pIntensity == MalfunctionIntensity.SEVERE_MALF)
			returnName += "Severe ";
		else if (pIntensity == MalfunctionIntensity.MEDIUM_MALF)
			returnName += "Medium ";
		else if (pIntensity == MalfunctionIntensity.LOW_MALF)
			returnName += "Low ";
		if (pLength == MalfunctionLength.TEMPORARY_MALF)
			returnName += "Production Rate Decrease (temporary)";
		else if (pLength == MalfunctionLength.PERMANENT_MALF)
			returnName += "Production Rate Decrease (permanent)";
		return returnName;
	}

	private void performMalfunctions(){
		float productionRate = 1f;
		for (Iterator iter = myMalfunctions.values().iterator(); iter.hasNext(); ){
			Malfunction currentMalfunction = (Malfunction)(iter.next());
			if (currentMalfunction.getLength() == MalfunctionLength.TEMPORARY_MALF){
				if (currentMalfunction.getIntensity() == MalfunctionIntensity.SEVERE_MALF)
					productionRate *= 0.50;
				else if (currentMalfunction.getIntensity() == MalfunctionIntensity.MEDIUM_MALF)
					productionRate *= 0.25;
				else if (currentMalfunction.getIntensity() == MalfunctionIntensity.LOW_MALF)
					productionRate *= 0.10;
			}
			else if (currentMalfunction.getLength() == MalfunctionLength.PERMANENT_MALF){
				if (currentMalfunction.getIntensity() == MalfunctionIntensity.SEVERE_MALF)
					productionRate *= 0.50;
				else if (currentMalfunction.getIntensity() == MalfunctionIntensity.MEDIUM_MALF)
					productionRate *= 0.25;
				else if (currentMalfunction.getIntensity() == MalfunctionIntensity.LOW_MALF)
					productionRate *= 0.10;
			}
		}
		setProductionRate(productionRate);
	}

	/**
	* Resets production/consumption levels and death/affliction flags
	*/
	public void reset(){
		super.reset();
		for (Iterator iter = myShelves.iterator(); iter.hasNext();){
			ShelfImpl currentShelf = (ShelfImpl)(iter.next());
			currentShelf.reset();
		}
	}

	/**
	* When ticked, the Biomass RS does the following
	* on the condition that the plants aren't dead it.
	* 1) attempts to collect references to various server (if not already done).
	* 4) consumes
	*/
	public void tick(){
		if (isMalfunctioning())
			performMalfunctions();
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

	private void log(){
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
	
	public void setPowerInputMaxFlowRate(float watts, int index){
		powerMaxFlowRates[index] = watts;
	}
	
	public float getPowerInputMaxFlowRate(int index){
		return powerMaxFlowRates[index];
	}
	
	public void setPowerInputs(PowerStore[] sources, float[] maxFlowRates){
		myPowerStores = sources;
		powerMaxFlowRates = maxFlowRates;
	}
	
	public PowerStore[] getPowerInputs(){
		return myPowerStores;
	}
	
	public float[] getPowerInputMaxFlowRates(){
		return powerMaxFlowRates;
	}
	
	public void setGreyWaterInputMaxFlowRate(float liters, int index){
		greyWaterMaxFlowRates[index] = liters;
	}
	
	public float getGreyWaterInputMaxFlowRate(int index){
		return greyWaterMaxFlowRates[index];
	}
	
	public void setGreyWaterInputs(GreyWaterStore[] sources, float[] maxFlowRates){
		myGreyWaterStores = sources;
		greyWaterMaxFlowRates = maxFlowRates;
	}
	
	public GreyWaterStore[] getGreyWaterInputs(){
		return myGreyWaterStores;
	}
	
	public float[] getGreyWaterInputMaxFlowRates(){
		return greyWaterMaxFlowRates;
	}
	
	public void setPotableWaterInputMaxFlowRate(float liters, int index){
		potableWaterMaxFlowRates[index] = liters;
	}
	
	public float getPotableWaterInputMaxFlowRate(int index){
		return potableWaterMaxFlowRates[index];
	}
	
	public void setPotableWaterInputs(PotableWaterStore[] sources, float[] maxFlowRates){
		myPotableWaterStores = sources;
		potableWaterMaxFlowRates = maxFlowRates;
	}
	
	public PotableWaterStore[] getPotableWaterInputs(){
		return myPotableWaterStores;
	}
	
	public float[] getPotableWaterInputMaxFlowRates(){
		return potableWaterMaxFlowRates;
	}
	
	public void setBiomassOutputMaxFlowRate(float kilograms, int index){
		biomassMaxFlowRates[index] = kilograms;
	}
	
	public float getBiomassOutputMaxFlowRate(int index){
		return biomassMaxFlowRates[index];
	}
	
	public void setBiomassOutputs(BiomassStore[] destinations, float[] maxFlowRates){
		myBiomassStores = destinations;
		biomassMaxFlowRates = maxFlowRates;
	}
	
	public BiomassStore[] getBiomassOutputs(){
		return myBiomassStores;
	}
	
	public float[] getBiomassOutputMaxFlowRates(){
		return biomassMaxFlowRates;
	}
	
	public void setAirInputMaxFlowRate(float liters, int index){
		airInMaxFlowRates[index] = liters;
	}

	public float getAirInputMaxFlowRate(int index){
		return airInMaxFlowRates[index];
	}

	public void setAirInputs(SimEnvironment[] sources, float[] maxFlowRates){
		myAirInputs = sources;
		airInMaxFlowRates = maxFlowRates;
	}

	public SimEnvironment[] getAirInputs(){
		return myAirInputs;
	}
	
	public float[] getAirInputMaxFlowRates(){
		return airInMaxFlowRates;
	}

	public void setAirOutputMaxFlowRate(float liters, int index){
		airOutMaxFlowRates[index] = liters;
	}

	public float getAirOutputMaxFlowRate(int index){
		return airOutMaxFlowRates[index];
	}

	public void setAirOutputs(SimEnvironment[] sources, float[] maxFlowRates){
		myAirOutputs = sources;
	}

	public SimEnvironment[] getAirOutputs(){
		return myAirOutputs;
	}
	
	public float[] getAirOutputMaxFlowRates(){
		return airOutMaxFlowRates;
	}
}
