package biosim.server.simulation.framework;

import biosim.idl.simulation.framework.*;
import biosim.idl.framework.*;
import biosim.idl.simulation.food.*;
import biosim.idl.simulation.air.*;
import biosim.idl.simulation.water.*;
import biosim.idl.simulation.environment.*;
import biosim.idl.simulation.power.*;
import java.util.*;
import biosim.server.util.*;
import biosim.idl.util.log.*;
import biosim.server.simulation.framework.*;
/**
 * The basic Accumulator Implementation.  Can be configured to take any modules as input, and any modules as output.
 * It takes as much as it can (max taken set by flowRates) from one module and pushes it into another module.
 * Functionally equivalent to an Injector at this point. 
 * @author    Scott Bell
 */

public class AccumulatorImpl extends SimBioModuleImpl implements AccumulatorOperations, PowerConsumerOperations, PotableWaterConsumerOperations, GreyWaterConsumerOperations, DirtyWaterConsumerOperations, O2ConsumerOperations, CO2ConsumerOperations, AirConsumerOperations, BiomassConsumerOperations, FoodConsumerOperations, PowerProducerOperations, PotableWaterProducerOperations, GreyWaterProducerOperations, DirtyWaterProducerOperations, O2ProducerOperations, CO2ProducerOperations, AirProducerOperations, BiomassProducerOperations, FoodProducerOperations, O2AirConsumerOperations, CO2AirConsumerOperations, O2AirProducerOperations, CO2AirProducerOperations{
	private LogIndex myLogIndex;
	private PowerStore[] myPowerInputs;
	private PowerStore[] myPowerOutputs;
	private float[] powerOutFlowRates;
	private float[] powerInFlowRates;

	private GreyWaterStore[] myGreyWaterInputs;
	private GreyWaterStore[] myGreyWaterOutputs;
	private float[] greyWaterOutFlowRates;
	private float[] greyWaterInFlowRates;

	private DirtyWaterStore[] myDirtyWaterInputs;
	private DirtyWaterStore[] myDirtyWaterOutputs;
	private float[] dirtyWaterOutFlowRates;
	private float[] dirtyWaterInFlowRates;

	private PotableWaterStore[] myPotableWaterInputs;
	private PotableWaterStore[] myPotableWaterOutputs;
	private float[] potableWaterOutFlowRates;
	private float[] potableWaterInFlowRates;

	private BiomassStore[] myBiomassInputs;
	private BiomassStore[] myBiomassOutputs;
	private float[] biomassOutFlowRates;
	private float[] biomassInFlowRates;

	private FoodStore[] myFoodInputs;
	private FoodStore[] myFoodOutputs;
	private float[] foodOutFlowRates;
	private float[] foodInFlowRates;

	private SimEnvironment[] myAirInputs;
	private SimEnvironment[] myAirOutputs;
	private float[] airOutFlowRates;
	private float[] airInFlowRates;

	private O2Store[] myO2Inputs;
	private O2Store[] myO2Outputs;
	private float[] O2OutFlowRates;
	private float[] O2InFlowRates;

	private CO2Store[] myCO2Inputs;
	private CO2Store[] myCO2Outputs;
	private float[] CO2OutFlowRates;
	private float[] CO2InFlowRates;
	
	private CO2Store[] myCO2AirStoreInputs;
	private CO2Store[] myCO2AirStoreOutputs;
	private float[] CO2AirStoreOutFlowRates;
	private float[] CO2AirStoreInFlowRates;
	
	private O2Store[] myO2AirStoreInputs;
	private O2Store[] myO2AirStoreOutputs;
	private float[] O2AirStoreOutFlowRates;
	private float[] O2AirStoreInFlowRates;
	
	private SimEnvironment[] myCO2AirEnvironmentInputs;
	private SimEnvironment[] myCO2AirEnvironmentOutputs;
	private float[] CO2AirEnvironmentOutFlowRates;
	private float[] CO2AirEnvironmentInFlowRates;
	
	private SimEnvironment[] myO2AirEnvironmentInputs;
	private SimEnvironment[] myO2AirEnvironmentOutputs;
	private float[] O2AirEnvironmentOutFlowRates;
	private float[] O2AirEnvironmentInFlowRates;

	public AccumulatorImpl(int pID){
		super(pID);
		myPowerOutputs = new PowerStore[0];
		myPowerInputs = new PowerStore[0];
		powerOutFlowRates = new float[0];
		powerInFlowRates = new float[0];

		myGreyWaterOutputs = new GreyWaterStore[0];
		myGreyWaterInputs = new GreyWaterStore[0];
		greyWaterOutFlowRates = new float[0];
		greyWaterInFlowRates = new float[0];

		myDirtyWaterOutputs = new DirtyWaterStore[0];
		myDirtyWaterInputs = new DirtyWaterStore[0];
		dirtyWaterOutFlowRates = new float[0];
		dirtyWaterInFlowRates = new float[0];

		myPotableWaterOutputs = new PotableWaterStore[0];
		myPotableWaterInputs = new PotableWaterStore[0];
		potableWaterOutFlowRates = new float[0];
		potableWaterInFlowRates = new float[0];

		myBiomassOutputs = new BiomassStore[0];
		myBiomassInputs = new BiomassStore[0];
		biomassOutFlowRates = new float[0];
		biomassInFlowRates = new float[0];

		myFoodOutputs = new FoodStore[0];
		myFoodInputs = new FoodStore[0];
		foodOutFlowRates = new float[0];
		foodInFlowRates = new float[0];

		myAirOutputs = new SimEnvironment[0];
		myAirInputs = new SimEnvironment[0];
		airOutFlowRates = new float[0];
		airInFlowRates = new float[0];

		myO2Outputs = new O2Store[0];
		myO2Inputs = new O2Store[0];
		O2OutFlowRates = new float[0];
		O2InFlowRates = new float[0];

		myCO2Outputs = new CO2Store[0];
		myCO2Inputs = new CO2Store[0];
		CO2OutFlowRates = new float[0];
		CO2InFlowRates = new float[0];
		
		myCO2AirStoreOutputs = new CO2Store[0];
		myCO2AirStoreInputs = new CO2Store[0];
		CO2AirStoreOutFlowRates = new float[0];
		CO2AirStoreInFlowRates = new float[0];
		
		myO2AirStoreOutputs = new O2Store[0];
		myO2AirStoreInputs = new O2Store[0];
		O2AirStoreOutFlowRates = new float[0];
		O2AirStoreInFlowRates = new float[0];
		
		myCO2AirEnvironmentOutputs = new SimEnvironment[0];
		myCO2AirEnvironmentInputs = new SimEnvironment[0];
		CO2AirEnvironmentOutFlowRates = new float[0];
		CO2AirEnvironmentInFlowRates = new float[0];
		
		myO2AirEnvironmentOutputs = new SimEnvironment[0];
		myO2AirEnvironmentInputs = new SimEnvironment[0];
		O2AirEnvironmentOutFlowRates = new float[0];
		O2AirEnvironmentInFlowRates = new float[0];
	}


	public void tick(){
		getAndPushResources();
		if (isMalfunctioning())
			performMalfunctions();
		if (moduleLogging)
			log();
	}
	
	private void getAndPushResources(){
		float powerGathered = getMaxResourceFromStore(myPowerInputs, powerInFlowRates);
		float powerPushed = pushResourceToStore(myPowerOutputs, powerOutFlowRates, powerGathered);
		
		float greyWaterGathered = getMaxResourceFromStore(myGreyWaterInputs, greyWaterInFlowRates);
		float greyWaterPushed = pushResourceToStore(myGreyWaterOutputs, greyWaterOutFlowRates, greyWaterGathered);
		
		float potableWaterGathered = getMaxResourceFromStore(myPotableWaterInputs, potableWaterInFlowRates);
		float potableWaterPushed = pushResourceToStore(myPotableWaterOutputs, potableWaterOutFlowRates, potableWaterGathered);
		
		float dirtyWaterGathered = getMaxResourceFromStore(myDirtyWaterInputs, dirtyWaterInFlowRates);
		float dirtyWaterPushed = pushResourceToStore(myDirtyWaterOutputs, dirtyWaterOutFlowRates, dirtyWaterGathered);
		
		float biomassGathered = getMaxResourceFromStore(myBiomassInputs, biomassInFlowRates);
		float biomassPushed = pushResourceToStore(myBiomassOutputs, biomassOutFlowRates, biomassGathered);
		
		float foodGathered = getMaxResourceFromStore(myFoodInputs, foodInFlowRates);
		float foodPushed = pushResourceToStore(myFoodOutputs, foodOutFlowRates, foodGathered);
		
		float O2Gathered = getMaxResourceFromStore(myO2Inputs, O2InFlowRates);
		float O2Pushed = pushResourceToStore(myO2Outputs, O2OutFlowRates, O2Gathered);
		
		float CO2Gathered = getMaxResourceFromStore(myCO2Inputs, CO2InFlowRates);
		float CO2Pushed = pushResourceToStore(myCO2Outputs, CO2OutFlowRates, CO2Gathered);
		
		
		Breath gatheredAir = new Breath(0,0,0);
		for (int i = 0; (i < myAirInputs.length) && ((gatheredAir.O2 + gatheredAir.CO2 + gatheredAir.other) < airInFlowRates[i]); i++){
			Breath currentBreath = myAirInputs[i].takeVolume(airInFlowRates[i] - (gatheredAir.O2 + gatheredAir.CO2 + gatheredAir.other));
			gatheredAir.O2 += currentBreath.O2;
			gatheredAir.CO2 += currentBreath.CO2;
			gatheredAir.other += currentBreath.other;
		}
		
		Breath distributedAir = new Breath(gatheredAir.O2, gatheredAir.CO2, gatheredAir.other);
		for (int i = 0; (i < myAirOutputs.length) && ((distributedAir.O2 + distributedAir.CO2 + distributedAir.other) > 0); i++){
			distributedAir = myAirInputs[i].addBreath(distributedAir);
		}
		
		//Get CO2
		float gatheredCO2Air = getMaxResourceFromStore(myCO2AirStoreInputs, CO2AirStoreInFlowRates);
		for (int i = 0; i < myCO2AirEnvironmentInputs.length; i++){
			gatheredCO2Air += myCO2AirEnvironmentInputs[i].takeCO2(CO2AirEnvironmentInFlowRates[i]);
		}
		//Push CO2
		float CO2AirPushed = pushResourceToStore(myCO2AirStoreOutputs, CO2AirStoreOutFlowRates, gatheredCO2Air);
		float CO2AirLeft = gatheredCO2Air - CO2AirPushed;
		for (int i = 0; (i < myCO2AirEnvironmentOutputs.length) && (CO2AirLeft > 0); i++){
			CO2AirLeft -= myCO2AirEnvironmentOutputs[i].addCO2(CO2AirEnvironmentOutFlowRates[i]);
		}
		CO2AirPushed = gatheredCO2Air - CO2AirLeft;
		
		//Get O2
		float gatheredO2Air = getMaxResourceFromStore(myO2AirStoreInputs, O2AirStoreInFlowRates);
		for (int i = 0; i < myO2AirEnvironmentInputs.length; i++){
			gatheredO2Air += myO2AirEnvironmentInputs[i].takeO2(O2AirEnvironmentInFlowRates[i]);
		}
		//Push O2
		float O2AirPushed = pushResourceToStore(myO2AirStoreOutputs, O2AirStoreOutFlowRates, gatheredO2Air);
		float O2AirLeft = gatheredO2Air - O2AirPushed;
		for (int i = 0; (i < myO2AirEnvironmentOutputs.length) && (O2AirLeft > 0); i++){
			O2AirLeft -= myO2AirEnvironmentOutputs[i].addO2(O2AirEnvironmentOutFlowRates[i]);
		}
		O2AirPushed = gatheredO2Air - O2AirLeft;
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

	private void performMalfunctions(){
	}

	public void reset(){
		super.reset();
	}

	private void log(){
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

	public void setPowerInputFlowRate(float amount, int index){
		powerInFlowRates[index] = amount;
	}

	public float getPowerInputFlowRate(int index){
		return powerInFlowRates[index];
	}

	public void setPowerInputs(PowerStore[] sources, float[] flowRates){
		myPowerInputs = sources;
		powerInFlowRates = flowRates;
	}

	public PowerStore[] getPowerInputs(){
		return myPowerInputs;
	}
	
	public float[] getPowerInputFlowRates(){
		return powerInFlowRates;
	}

	public void setPowerOutputFlowRate(float amount, int index){
		powerOutFlowRates[index] = amount;
	}

	public float getPowerOutputFlowRate(int index){
		return powerOutFlowRates[index];
	}

	public void setPowerOutputs(PowerStore[] destinations, float[] flowRates){
		myPowerOutputs = destinations;
		powerOutFlowRates = flowRates;
	}

	public PowerStore[] getPowerOutputs(){
		return myPowerOutputs;
	}
	
	public float[] getPowerOutputFlowRates(){
		return powerOutFlowRates;
	}

	public void setGreyWaterInputFlowRate(float amount, int index){
		greyWaterInFlowRates[index] = amount;
	}

	public float getGreyWaterInputFlowRate(int index){
		return greyWaterInFlowRates[index];
	}

	public void setGreyWaterInputs(GreyWaterStore[] sources, float[] flowRates){
		myGreyWaterInputs = sources;
		greyWaterInFlowRates = flowRates;
	}

	public GreyWaterStore[] getGreyWaterInputs(){
		return myGreyWaterInputs;
	}
	
	public float[] getGreyWaterInputFlowRates(){
		return greyWaterInFlowRates;
	}

	public void setGreyWaterOutputFlowRate(float amount, int index){
		greyWaterOutFlowRates[index] = amount;
	}

	public float getGreyWaterOutputFlowRate(int index){
		return greyWaterOutFlowRates[index];
	}

	public void setGreyWaterOutputs(GreyWaterStore[] destinations, float[] flowRates){
		myGreyWaterOutputs = destinations;
		greyWaterOutFlowRates = flowRates;
	}

	public GreyWaterStore[] getGreyWaterOutputs(){
		return myGreyWaterOutputs;
	}
	
	public float[] getGreyWaterOutputFlowRates(){
		return greyWaterOutFlowRates;
	}

	public void setDirtyWaterInputFlowRate(float amount, int index){
		dirtyWaterInFlowRates[index] = amount;
	}

	public float getDirtyWaterInputFlowRate(int index){
		return dirtyWaterInFlowRates[index];
	}

	public void setDirtyWaterInputs(DirtyWaterStore[] sources, float[] flowRates){
		myDirtyWaterInputs = sources;
		dirtyWaterInFlowRates = flowRates;
	}

	public DirtyWaterStore[] getDirtyWaterInputs(){
		return myDirtyWaterInputs;
	}
	
	public float[] getDirtyWaterInputFlowRates(){
		return dirtyWaterInFlowRates;
	}

	public void setDirtyWaterOutputFlowRate(float amount, int index){
		dirtyWaterOutFlowRates[index] = amount;
	}

	public float getDirtyWaterOutputFlowRate(int index){
		return dirtyWaterOutFlowRates[index];
	}

	public void setDirtyWaterOutputs(DirtyWaterStore[] destinations, float[] flowRates){
		myDirtyWaterOutputs = destinations;
		dirtyWaterOutFlowRates = flowRates;
	}

	public DirtyWaterStore[] getDirtyWaterOutputs(){
		return myDirtyWaterOutputs;
	}
	
	public float[] getDirtyWaterOutputFlowRates(){
		return dirtyWaterOutFlowRates;
	}

	public void setPotableWaterInputFlowRate(float amount, int index){
		potableWaterInFlowRates[index] = amount;
	}

	public float getPotableWaterInputFlowRate(int index){
		return potableWaterInFlowRates[index];
	}

	public void setPotableWaterInputs(PotableWaterStore[] sources, float[] flowRates){
		myPotableWaterInputs = sources;
		potableWaterInFlowRates = flowRates;
	}

	public PotableWaterStore[] getPotableWaterInputs(){
		return myPotableWaterInputs;
	}
	
	public float[] getPotableWaterInputFlowRates(){
		return potableWaterInFlowRates;
	}

	public void setPotableWaterOutputFlowRate(float amount, int index){
		potableWaterOutFlowRates[index] = amount;
	}

	public float getPotableWaterOutputFlowRate(int index){
		return potableWaterOutFlowRates[index];
	}

	public void setPotableWaterOutputs(PotableWaterStore[] destinations, float[] flowRates){
		myPotableWaterOutputs = destinations;
		potableWaterOutFlowRates = flowRates;
	}

	public PotableWaterStore[] getPotableWaterOutputs(){
		return myPotableWaterOutputs;
	}
	
	public float[] getPotableWaterOutputFlowRates(){
		return potableWaterOutFlowRates;
	}

	public void setFoodInputFlowRate(float amount, int index){
		foodInFlowRates[index] = amount;
	}

	public float getFoodInputFlowRate(int index){
		return foodInFlowRates[index];
	}

	public void setFoodInputs(FoodStore[] sources, float[] flowRates){
		myFoodInputs = sources;
		foodInFlowRates = flowRates;
	}

	public FoodStore[] getFoodInputs(){
		return myFoodInputs;
	}
	
	public float[] getFoodInFlowRates(){
		return foodInFlowRates;
	}

	public void setFoodOutputFlowRate(float amount, int index){
		foodOutFlowRates[index] = amount;
	}

	public float getFoodOutputFlowRate(int index){
		return foodOutFlowRates[index];
	}

	public void setFoodOutputs(FoodStore[] destinations, float[] flowRates){
		myFoodOutputs = destinations;
		foodOutFlowRates = flowRates;
	}

	public FoodStore[] getFoodOutputs(){
		return myFoodOutputs;
	}
	
	public float[] getFoodOutputFlowRates(){
		return foodOutFlowRates;
	}
	
	public void setBiomassInputFlowRate(float amount, int index){
		biomassInFlowRates[index] = amount;
	}

	public float getBiomassInputFlowRate(int index){
		return biomassInFlowRates[index];
	}

	public void setBiomassInputs(BiomassStore[] sources, float[] flowRates){
		myBiomassInputs = sources;
		biomassInFlowRates = flowRates;
	}

	public BiomassStore[] getBiomassInputs(){
		return myBiomassInputs;
	}
	
	public float[] getBiomassInputFlowRates(){
		return biomassInFlowRates;
	}

	public void setBiomassOutputFlowRate(float amount, int index){
		biomassOutFlowRates[index] = amount;
	}

	public float getBiomassOutputFlowRate(int index){
		return biomassOutFlowRates[index];
	}

	public void setBiomassOutputs(BiomassStore[] destinations, float[] flowRates){
		myBiomassOutputs = destinations;
		biomassOutFlowRates = flowRates;
	}

	public BiomassStore[] getBiomassOutputs(){
		return myBiomassOutputs;
	}
	
	public float[] getBiomassOutputFlowRates(){
		return biomassOutFlowRates;
	}

	public void setAirInputFlowRate(float amount, int index){
		airInFlowRates[index] = amount;
	}

	public float getAirInputFlowRate(int index){
		return airInFlowRates[index];
	}

	public void setAirInputs(SimEnvironment[] sources, float[] flowRates){
		myAirInputs = sources;
		airInFlowRates = flowRates;
	}

	public SimEnvironment[] getAirInputs(){
		return myAirInputs;
	}
	
	public float[] getAirInputFlowRates(){
		return airInFlowRates;
	}

	public void setAirOutputFlowRate(float amount, int index){
		airOutFlowRates[index] = amount;
	}

	public float getAirOutputFlowRate(int index){
		return airOutFlowRates[index];
	}

	public void setAirOutputs(SimEnvironment[] destinations, float[] flowRates){
		myAirOutputs = destinations;
		airOutFlowRates = flowRates;
	}

	public SimEnvironment[] getAirOutputs(){
		return myAirOutputs;
	}
	
	public float[] getAirOutputFlowRates(){
		return airOutFlowRates;
	}

	public void setO2InputFlowRate(float amount, int index){
		O2InFlowRates[index] = amount;
	}

	public float getO2InputFlowRate(int index){
		return O2InFlowRates[index];
	}

	public void setO2Inputs(O2Store[] sources, float[] flowRates){
		myO2Inputs = sources;
		O2InFlowRates = flowRates;
	}

	public O2Store[] getO2Inputs(){
		return myO2Inputs;
	}
	
	public float[] getO2InputFlowRates(){
		return O2InFlowRates;
	}

	public void setO2OutputFlowRate(float amount, int index){
		O2OutFlowRates[index] = amount;
	}

	public float getO2OutputFlowRate(int index){
		return O2OutFlowRates[index];
	}

	public void setO2Outputs(O2Store[] destinations, float[] flowRates){
		myO2Outputs = destinations;
		O2OutFlowRates = flowRates;
	}

	public O2Store[] getO2Outputs(){
		return myO2Outputs;
	}
	
	public float[] getO2OutputFlowRates(){
		return O2OutFlowRates;
	}

	public void setCO2InputFlowRate(float amount, int index){
		CO2InFlowRates[index] = amount;
	}

	public float getCO2InputFlowRate(int index){
		return CO2InFlowRates[index];
	}

	public void setCO2Inputs(CO2Store[] sources, float[] flowRates){
		myCO2Inputs = sources;
		CO2InFlowRates = flowRates;
	}
	
	public float[] getCO2InputFlowRates(){
		return CO2InFlowRates;
	}

	public CO2Store[] getCO2Inputs(){
		return myCO2Inputs;
	}

	public void setCO2OutputFlowRate(float amount, int index){
		CO2OutFlowRates[index] = amount;
	}

	public float getCO2OutputFlowRate(int index){
		return CO2OutFlowRates[index];
	}

	public void setCO2Outputs(CO2Store[] destinations, float[] flowRates){
		myCO2Outputs = destinations;
		CO2OutFlowRates = flowRates;
	}

	public CO2Store[] getCO2Outputs(){
		return myCO2Outputs;
	}
	
	public float[] getCO2OutputFlowRates(){
		return CO2OutFlowRates;
	}
	
	public void setCO2AirStoreInputFlowRate(float amount, int index){
		CO2AirStoreInFlowRates[index] = amount;
	}
	
	public void setCO2AirEnvironmentInputFlowRate(float amount, int index){
		CO2AirEnvironmentInFlowRates[index] = amount;
	}

	public float getCO2AirStoreInputFlowRate(int index){
		return CO2AirStoreInFlowRates[index];
	}
	
	public float getCO2AirEnvironmentInputFlowRate(int index){
		return CO2AirEnvironmentInFlowRates[index];
	}

	public void setCO2AirStoreInputs(CO2Store[] sources, float[] flowRates){
		myCO2AirStoreInputs = sources;
		CO2AirStoreInFlowRates = flowRates;
	}
	
	public void setCO2AirEnvironmentInputs(SimEnvironment[] sources, float[] flowRates){
		myCO2AirEnvironmentInputs = sources;
		CO2AirEnvironmentInFlowRates = flowRates;
	}
	
	public float[] getCO2AirStoreInputFlowRates(){
		return CO2AirStoreInFlowRates;
	}
	
	public float[] getCO2AirEnvironmentInputFlowRates(){
		return CO2AirEnvironmentInFlowRates;
	}
	
	public CO2Store[] getCO2AirStoreInputs(){
		return myCO2AirStoreInputs;
	}
	
	public SimEnvironment[] getCO2AirEnvironmentInputs(){
		return myCO2AirEnvironmentInputs;
	}
	
	public void setCO2AirStoreOutputFlowRate(float amount, int index){
		CO2AirStoreOutFlowRates[index] = amount;
	}
	
	public void setCO2AirEnvironmentOutputFlowRate(float amount, int index){
		CO2AirEnvironmentOutFlowRates[index] = amount;
	}

	public float getCO2AirStoreOutputFlowRate(int index){
		return CO2AirStoreOutFlowRates[index];
	}
	
	public float getCO2AirEnvironmentOutputFlowRate(int index){
		return CO2AirEnvironmentOutFlowRates[index];
	}

	public void setCO2AirStoreOutputs(CO2Store[] destinations, float[] flowRates){
		myCO2AirStoreOutputs = destinations;
		CO2AirStoreOutFlowRates = flowRates;
	}
	
	public void setCO2AirEnvironmentOutputs(SimEnvironment[] destinations, float[] flowRates){
		myCO2AirEnvironmentOutputs = destinations;
		CO2AirEnvironmentOutFlowRates = flowRates;
	}
	
	public float[] getCO2AirStoreOutputFlowRates(){
		return CO2AirStoreOutFlowRates;
	}
	
	public float[] getCO2AirEnvironmentOutputFlowRates(){
		return CO2AirEnvironmentOutFlowRates;
	}
	
	public CO2Store[] getCO2AirStoreOutputs(){
		return myCO2AirStoreOutputs;
	}
	
	public SimEnvironment[] getCO2AirEnvironmentOutputs(){
		return myCO2AirEnvironmentOutputs;
	}
	
	public void setO2AirStoreInputFlowRate(float amount, int index){
		O2AirStoreInFlowRates[index] = amount;
	}
	
	public void setO2AirEnvironmentInputFlowRate(float amount, int index){
		O2AirEnvironmentInFlowRates[index] = amount;
	}

	public float getO2AirStoreInputFlowRate(int index){
		return O2AirStoreInFlowRates[index];
	}
	
	public float getO2AirEnvironmentInputFlowRate(int index){
		return O2AirEnvironmentInFlowRates[index];
	}

	public void setO2AirStoreInputs(O2Store[] sources, float[] flowRates){
		myO2AirStoreInputs = sources;
		O2AirStoreInFlowRates = flowRates;
	}
	
	public void setO2AirEnvironmentInputs(SimEnvironment[] sources, float[] flowRates){
		myO2AirEnvironmentInputs = sources;
		O2AirEnvironmentInFlowRates = flowRates;
	}
	
	public float[] getO2AirStoreInputFlowRates(){
		return O2AirStoreInFlowRates;
	}
	
	public float[] getO2AirEnvironmentInputFlowRates(){
		return O2AirEnvironmentInFlowRates;
	}
	
	public O2Store[] getO2AirStoreInputs(){
		return myO2AirStoreInputs;
	}
	
	public SimEnvironment[] getO2AirEnvironmentInputs(){
		return myO2AirEnvironmentInputs;
	}
	
	public void setO2AirStoreOutputFlowRate(float amount, int index){
		O2AirStoreOutFlowRates[index] = amount;
	}
	
	public void setO2AirEnvironmentOutputFlowRate(float amount, int index){
		O2AirEnvironmentOutFlowRates[index] = amount;
	}

	public float getO2AirStoreOutputFlowRate(int index){
		return O2AirStoreOutFlowRates[index];
	}
	
	public float getO2AirEnvironmentOutputFlowRate(int index){
		return O2AirEnvironmentOutFlowRates[index];
	}

	public void setO2AirStoreOutputs(O2Store[] destinations, float[] flowRates){
		myO2AirStoreOutputs = destinations;
		O2AirStoreOutFlowRates = flowRates;
	}
	
	public void setO2AirEnvironmentOutputs(SimEnvironment[] destinations, float[] flowRates){
		myO2AirEnvironmentOutputs = destinations;
		O2AirEnvironmentOutFlowRates = flowRates;
	}
	
	public float[] getO2AirStoreOutputFlowRates(){
		return O2AirStoreOutFlowRates;
	}
	
	public float[] getO2AirEnvironmentOutputFlowRates(){
		return O2AirEnvironmentOutFlowRates;
	}
	
	public O2Store[] getO2AirStoreOutputs(){
		return myO2AirStoreOutputs;
	}
	
	public SimEnvironment[] getO2AirEnvironmentOutputs(){
		return myO2AirEnvironmentOutputs;
	}
	
	/**
	* Returns the name of the module
	* @return the name of this module
	*/
	public String getModuleName(){
		return "Accumulator"+getID();
	}
}
