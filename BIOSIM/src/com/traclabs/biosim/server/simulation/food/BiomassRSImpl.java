package biosim.server.food;

import biosim.idl.food.*;
import biosim.idl.power.*;
import biosim.idl.water.*;
import biosim.idl.util.log.*;
import biosim.server.util.*;
import biosim.server.framework.*;
import biosim.idl.framework.*;
import java.util.*;
/**
 * The Biomass RS is essentially responsible for growing plants.  The plant matter (biomass) is fed into the food processor to create food
 * for the crew.  The plants can also (along with the AirRS) take CO2 out of the air and add O2.
 *
 * @author    Scott Bell
 */

public class BiomassRSImpl extends BioModuleImpl implements BiomassRSOperations, PotableWaterConsumerOperations, GreyWaterConsumerOperations, BiomassProducerOperations{
	private List myShelves;
	private int shelfCapacity = 100;
	private List shelfLogs;
	private float powerFlowRate = 0f;
	private float potableWaterFlowRate = 0f;
	private float greyWaterFlowRate = 0f;
	private float biomassFlowRate = 0f;
	private float[] powerFlowRates;
	private float[] biomassFlowRates;
	private float[] potableWaterFlowRates;
	private float[] greyWaterFlowRates;
	private GreyWaterStore[] myGreyWaterStores;
	private PotableWaterStore[] myPotableWaterStores;
	private PowerStore[] myPowerStores;
	private BiomassStore[] myBiomassStores;

	public BiomassRSImpl(int pID){
		super(pID);
		myGreyWaterStores = new GreyWaterStore[0];
		myPotableWaterStores = new PotableWaterStore[0];
		myPowerStores = new PowerStore[0];
		myBiomassStores = new BiomassStore[0];
		powerFlowRates = new float[0];
		biomassFlowRates = new float[0];
		potableWaterFlowRates = new float[0];
		greyWaterFlowRates = new float[0];
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
	
	public void setPowerInputFlowrate(float watts, int index){
		powerFlowRates[index] = watts;
	}
	
	public float getPowerInputFlowrate(int index){
		return powerFlowRates[index];
	}
	
	public void setPowerInputs(PowerStore[] sources, float[] flowRates){
		myPowerStores = sources;
		powerFlowRates = flowRates;
		System.out.println("Set power input of length: "+sources.length);
	}
	
	public PowerStore[] getPowerInputs(){
		return myPowerStores;
	}
	
	public void setGreyWaterInputFlowrate(float liters, int index){
		greyWaterFlowRates[index] = liters;
	}
	
	public float getGreyWaterInputFlowrate(int index){
		return greyWaterFlowRates[index];
	}
	
	public void setGreyWaterInputs(GreyWaterStore[] sources, float[] flowRates){
		myGreyWaterStores = sources;
		greyWaterFlowRates = flowRates;
	}
	
	public GreyWaterStore[] getGreyWaterInputs(){
		return myGreyWaterStores;
	}
	
	public void setPotableWaterInputFlowrate(float liters, int index){
		potableWaterFlowRates[index] = liters;
	}
	
	public float getPotableWaterInputFlowrate(int index){
		return potableWaterFlowRates[index];
	}
	
	public void setPotableWaterInputs(PotableWaterStore[] sources, float[] flowRates){
		myPotableWaterStores = sources;
		potableWaterFlowRates = flowRates;
	}
	
	public PotableWaterStore[] getPotableWaterInputs(){
		return myPotableWaterStores;
	}
	
	public void setBiomassOutputFlowrate(float kilograms, int index){
		biomassFlowRates[index] = kilograms;
	}
	
	public float getBiomassOutputFlowrate(int index){
		return biomassFlowRates[index];
	}
	
	public void setBiomassOutputs(BiomassStore[] destinations, float[] flowRates){
		myBiomassStores = destinations;
		biomassFlowRates = flowRates;
	}
	
	public BiomassStore[] getBiomassOutputs(){
		return myBiomassStores;
	}
}
