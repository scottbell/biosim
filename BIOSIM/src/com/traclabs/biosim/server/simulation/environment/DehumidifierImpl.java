package biosim.server.simulation.environment;

import biosim.idl.simulation.water.*;
import biosim.idl.simulation.environment.*;
import biosim.idl.framework.*;
import java.util.*;
import biosim.server.util.*;
import biosim.idl.util.log.*;
import biosim.server.simulation.framework.*;
/**
 * The basic Dehimidifier Implementation.
 * @author    Scott Bell
 */

public class DehumidifierImpl extends SimBioModuleImpl implements DehumidifierOperations, AirConsumerOperations, PotableWaterProducerOperations{
	private LogIndex myLogIndex;
	
	private PotableWaterStore[] myPotableWaterOutputs;
	private float[] potableWaterOutMaxFlowRates;
	private float[] potableWaterOutActualFlowRates;
	private float[] potableWaterOutDesiredFlowRates;
	
	private SimEnvironment[] myAirInputs;
	private float[] airInMaxFlowRates;
	private float[] airInActualFlowRates;
	private float[] airInDesiredFlowRates;
	
	private static final float OPTIMAL_MOISTURE_CONCENTRATION = 0.01f; //in kPA

	public DehumidifierImpl(int pID, String pName){
		super(pID, pName);

		myPotableWaterOutputs = new PotableWaterStore[0];
		potableWaterOutMaxFlowRates = new float[0];
		potableWaterOutActualFlowRates = new float[0];
		potableWaterOutDesiredFlowRates = new float[0];
		
		myAirInputs = new SimEnvironment[0];
		airInMaxFlowRates = new float[0];
		airInActualFlowRates = new float[0];
		airInDesiredFlowRates = new float[0];
	}


	public void tick(){
		super.tick();
		dehumidifyEnvironments();
	}

	private void dehumidifyEnvironments(){
		/*float currentWaterMolesInEnvironment = myAirInputs[0].getWaterMoles();
		float totalMolesInEnvironment = myAirInputs[0].getTotalMoles();
		System.out.println("Before: Water concentration now "+currentWaterMolesInEnvironment / totalMolesInEnvironment);
		*/
		float molesOfWaterGathered = 0f;
		for (int i = 0; i < myAirInputs.length; i++){
			float molesNeededToRemove = calculateMolesNeededToRemove(myAirInputs[i]);
			if (molesNeededToRemove > 0){
				//cycle through and take molesNeededToRemove (or less if desired is less)
				float resourceToGatherFirst = Math.min(molesNeededToRemove, airInMaxFlowRates[i]);
				float resourceToGatherFinal = Math.min(resourceToGatherFirst, airInDesiredFlowRates[i]);
				airInActualFlowRates[i] = myAirInputs[i].takeWaterMoles(resourceToGatherFinal);
				molesOfWaterGathered += airInActualFlowRates[i];
			}
		}
		float waterPushedToStore = pushResourceToStore(myPotableWaterOutputs, potableWaterOutMaxFlowRates, potableWaterOutDesiredFlowRates, potableWaterOutActualFlowRates, waterMolesToLiters(molesOfWaterGathered));
		
		/*currentWaterMolesInEnvironment = myAirInputs[0].getWaterMoles();
		totalMolesInEnvironment = myAirInputs[0].getTotalMoles();
		System.out.println("After: Pushed "+waterPushedToStore+" liters of water to the store, water concentration now "+currentWaterMolesInEnvironment / totalMolesInEnvironment);
		*/
	}
	
	private static float calculateMolesNeededToRemove(SimEnvironment pEnvironment){
		float currentWaterMolesInEnvironment = pEnvironment.getWaterMoles();
		float totalMolesInEnvironment = pEnvironment.getTotalMoles();
		if ((currentWaterMolesInEnvironment / totalMolesInEnvironment) > OPTIMAL_MOISTURE_CONCENTRATION){
			float waterMolesAtOptimalConcentration = totalMolesInEnvironment * OPTIMAL_MOISTURE_CONCENTRATION;
			return currentWaterMolesInEnvironment - waterMolesAtOptimalConcentration;
		}
		else
			return 0f;
	}                                            
	
	private static float waterLitersToMoles(float pLiters){
		return (pLiters * 1000f) / 18.01524f; // 1000g/liter, 18.01524g/mole
	}
	
	private static float waterMolesToLiters(float pMoles){
		return (pMoles * 18.01524f) / 1000f; // 1000g/liter, 18.01524g/mole
	}

	protected String getMalfunctionName(MalfunctionIntensity pIntensity, MalfunctionLength pLength){
		StringBuffer returnBuffer = new StringBuffer();
		if (pIntensity == MalfunctionIntensity.SEVERE_MALF)
			returnBuffer.append("Severe ");
		else if (pIntensity == MalfunctionIntensity.MEDIUM_MALF)
			returnBuffer.append("Medium ");
		else if (pIntensity == MalfunctionIntensity.LOW_MALF)
			returnBuffer.append("Low ");
		if (pLength == MalfunctionLength.TEMPORARY_MALF)
			returnBuffer.append("Temporary Production Reduction");
		else if (pLength == MalfunctionLength.PERMANENT_MALF)
			returnBuffer.append("Permanent Production Reduction");
		return returnBuffer.toString();
	}

	protected void performMalfunctions(){
	}

	public void reset(){
		super.reset();
	}

	public void log(){
		//If not initialized, fill in the log
		if (!logInitialized){
			myLogIndex = new LogIndex();
			LogNode intHead = myLog.addChild("in");
			//myLogIndex.levelIndex = levelHead.addChild((""+in));
			LogNode outHead = myLog.addChild("out");
			//myLogIndex.capacityIndex = capacityHead.addChild((""+out));
			logInitialized = true;
		}
		else{
			//myLogIndex.intHead.setValue(""+in);
			//myLogIndex.outHead.setValue(""+out);
		}
		sendLog(myLog);
	}

	/**
	* For fast reference to the log tree
	*/
	private class LogIndex{
		public LogNode inIndex;
		public LogNode outIndex;
	}

	//Potable Water Outputs
	public void setPotableWaterOutputMaxFlowRate(float amount, int index){
		potableWaterOutMaxFlowRates[index] = amount;
	}
	public float getPotableWaterOutputMaxFlowRate(int index){
		return potableWaterOutMaxFlowRates[index];
	}
	public float[] getPotableWaterOutputMaxFlowRates(){
		return potableWaterOutMaxFlowRates;
	}
	public void setPotableWaterOutputDesiredFlowRate(float amount, int index){
		potableWaterOutDesiredFlowRates[index] = amount;
	}
	public float getPotableWaterOutputDesiredFlowRate(int index){
		return potableWaterOutDesiredFlowRates[index];
	}
	public float[] getPotableWaterOutputDesiredFlowRates(){
		return potableWaterOutDesiredFlowRates;
	}
	public float getPotableWaterOutputActualFlowRate(int index){
		return potableWaterOutActualFlowRates[index];
	}
	public float[] getPotableWaterOutputActualFlowRates(){
		return potableWaterOutActualFlowRates;
	}
	public void setPotableWaterOutputs(PotableWaterStore[] destinations, float[] maxFlowRates, float[] desiredFlowRates){
		myPotableWaterOutputs = destinations;
		potableWaterOutMaxFlowRates = maxFlowRates;
		potableWaterOutDesiredFlowRates = desiredFlowRates;
		potableWaterOutActualFlowRates = new float[potableWaterOutDesiredFlowRates.length];
	}
	public PotableWaterStore[] getPotableWaterOutputs(){
		return myPotableWaterOutputs;
	}

	//Air Inputs
	public void setAirInputMaxFlowRate(float amount, int index){
		airInMaxFlowRates[index] = amount;
	}
	public float getAirInputMaxFlowRate(int index){
		return airInMaxFlowRates[index];
	}
	public float[] getAirInputMaxFlowRates(){
		return airInMaxFlowRates;
	}
	public void setAirInputDesiredFlowRate(float amount, int index){
		airInDesiredFlowRates[index] = amount;
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
}
