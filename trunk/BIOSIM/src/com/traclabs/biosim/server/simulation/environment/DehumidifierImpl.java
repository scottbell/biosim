package biosim.server.simulation.environment;

import biosim.idl.framework.AirConsumerOperations;
import biosim.idl.framework.DirtyWaterProducerOperations;
import biosim.idl.framework.MalfunctionIntensity;
import biosim.idl.framework.MalfunctionLength;
import biosim.idl.simulation.environment.DehumidifierOperations;
import biosim.idl.simulation.environment.SimEnvironment;
import biosim.idl.simulation.water.DirtyWaterStore;
import biosim.server.simulation.framework.SimBioModuleImpl;
/**
 * The basic Dehimidifier Implementation.
 * @author    Scott Bell
 */

public class DehumidifierImpl extends SimBioModuleImpl implements DehumidifierOperations, AirConsumerOperations, DirtyWaterProducerOperations{
	private DirtyWaterStore[] myDirtyWaterOutputs;
	private float[] dirtyWaterOutMaxFlowRates;
	private float[] dirtyWaterOutActualFlowRates;
	private float[] dirtyWaterOutDesiredFlowRates;
	
	private SimEnvironment[] myAirInputs;
	private float[] airInMaxFlowRates;
	private float[] airInActualFlowRates;
	private float[] airInDesiredFlowRates;
	
	private static final float OPTIMAL_MOISTURE_CONCENTRATION = 0.01f; //in kPA

	public DehumidifierImpl(int pID, String pName){
		super(pID, pName);

		myDirtyWaterOutputs = new DirtyWaterStore[0];
		dirtyWaterOutMaxFlowRates = new float[0];
		dirtyWaterOutActualFlowRates = new float[0];
		dirtyWaterOutDesiredFlowRates = new float[0];
		
		myAirInputs = new SimEnvironment[0];
		airInMaxFlowRates = new float[0];
		airInActualFlowRates = new float[0];
		airInDesiredFlowRates = new float[0];
	}


	public void tick(){
		super.tick();
		dehumidifyEnvironments();
		//System.out.println(getModuleName() + " ticked");
	}

	private void dehumidifyEnvironments(){
		float currentWaterMolesInEnvironment = myAirInputs[0].getWaterMoles();
		float totalMolesInEnvironment = myAirInputs[0].getTotalMoles();
		//myAirInputs[0].printCachedEnvironment();
		//System.out.println("Before: Water concentration "+currentWaterMolesInEnvironment / totalMolesInEnvironment);
		
		float molesOfWaterGathered = 0f;
		for (int i = 0; i < myAirInputs.length; i++){
			float molesNeededToRemove = calculateMolesNeededToRemove(myAirInputs[i]);
			if (molesNeededToRemove > 0){
				//cycle through and take molesNeededToRemove (or less if desired is less)
				float resourceToGatherFirst = Math.min(molesNeededToRemove, airInMaxFlowRates[i]);
				float resourceToGatherFinal = Math.min(resourceToGatherFirst, airInDesiredFlowRates[i]);
				airInActualFlowRates[i] = myAirInputs[i].takeWaterMoles(resourceToGatherFinal);
				//System.out.println("Going to remove "+resourceToGatherFinal+" moles of water");
				molesOfWaterGathered += airInActualFlowRates[i];
			}
		}
		float waterPushedToStore = pushResourceToStore(myDirtyWaterOutputs, dirtyWaterOutMaxFlowRates, dirtyWaterOutDesiredFlowRates, dirtyWaterOutActualFlowRates, waterMolesToLiters(molesOfWaterGathered));
		
		currentWaterMolesInEnvironment = myAirInputs[0].getWaterMoles();
		totalMolesInEnvironment = myAirInputs[0].getTotalMoles();
		//System.out.println("After: Pushed "+waterPushedToStore+" liters of water to the store (gathered "+molesOfWaterGathered+" moles), water concentration now "+currentWaterMolesInEnvironment / totalMolesInEnvironment);
		//myAirInputs[0].printEnvironment();
	}
	
	private static float calculateMolesNeededToRemove(SimEnvironment pEnvironment){
		float currentWaterMolesInEnvironment = pEnvironment.getWaterMoles();
		float totalMolesInEnvironment = pEnvironment.getTotalMoles();
		if ((currentWaterMolesInEnvironment / totalMolesInEnvironment) > OPTIMAL_MOISTURE_CONCENTRATION){
			float waterMolesAtOptimalConcentration =((totalMolesInEnvironment - currentWaterMolesInEnvironment) * OPTIMAL_MOISTURE_CONCENTRATION) / (1 - OPTIMAL_MOISTURE_CONCENTRATION);
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
	}

	//Dirty Water Outputs
	public void setDirtyWaterOutputMaxFlowRate(float amount, int index){
		dirtyWaterOutMaxFlowRates[index] = amount;
	}
	public float getDirtyWaterOutputMaxFlowRate(int index){
		return dirtyWaterOutMaxFlowRates[index];
	}
	public float[] getDirtyWaterOutputMaxFlowRates(){
		return dirtyWaterOutMaxFlowRates;
	}
	public void setDirtyWaterOutputDesiredFlowRate(float amount, int index){
		dirtyWaterOutDesiredFlowRates[index] = amount;
	}
	public float getDirtyWaterOutputDesiredFlowRate(int index){
		return dirtyWaterOutDesiredFlowRates[index];
	}
	public float[] getDirtyWaterOutputDesiredFlowRates(){
		return dirtyWaterOutDesiredFlowRates;
	}
	public float getDirtyWaterOutputActualFlowRate(int index){
		return dirtyWaterOutActualFlowRates[index];
	}
	public float[] getDirtyWaterOutputActualFlowRates(){
		return dirtyWaterOutActualFlowRates;
	}
	public void setDirtyWaterOutputs(DirtyWaterStore[] destinations, float[] maxFlowRates, float[] desiredFlowRates){
		myDirtyWaterOutputs = destinations;
		dirtyWaterOutMaxFlowRates = maxFlowRates;
		dirtyWaterOutDesiredFlowRates = desiredFlowRates;
		dirtyWaterOutActualFlowRates = new float[dirtyWaterOutDesiredFlowRates.length];
	}
	public DirtyWaterStore[] getDirtyWaterOutputs(){
		return myDirtyWaterOutputs;
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
