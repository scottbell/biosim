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
 * The basic Injector Implementation.  Can be configured to take any modules as input, and any modules as output.
 * It takes as much as it can (max taken set by maxFlowRates) from one module and pushes it into another module.
 * Functionally equivalent to an Injector at this point. 
 * @author    Scott Bell
 */

public class InjectorImpl extends SimBioModuleImpl implements InjectorOperations, PowerConsumerOperations, PotableWaterConsumerOperations, GreyWaterConsumerOperations, DirtyWaterConsumerOperations, O2ConsumerOperations, CO2ConsumerOperations, AirConsumerOperations, BiomassConsumerOperations, FoodConsumerOperations, PowerProducerOperations, PotableWaterProducerOperations, GreyWaterProducerOperations, DirtyWaterProducerOperations, O2ProducerOperations, CO2ProducerOperations, AirProducerOperations, BiomassProducerOperations, FoodProducerOperations, O2AirConsumerOperations, CO2AirConsumerOperations, O2AirProducerOperations, CO2AirProducerOperations{
	private LogIndex myLogIndex;
	
	private PowerStore[] myPowerInputs;
	private PowerStore[] myPowerOutputs;
	private float[] powerOutMaxFlowRates;
	private float[] powerInMaxFlowRates;
	private float[] powerOutActualFlowRates;
	private float[] powerInActualFlowRates;

	private GreyWaterStore[] myGreyWaterInputs;
	private GreyWaterStore[] myGreyWaterOutputs;
	private float[] greyWaterOutMaxFlowRates;
	private float[] greyWaterInMaxFlowRates;
	private float[] greyWaterOutActualFlowRates;
	private float[] greyWaterInActualFlowRates;

	private DirtyWaterStore[] myDirtyWaterInputs;
	private DirtyWaterStore[] myDirtyWaterOutputs;
	private float[] dirtyWaterOutMaxFlowRates;
	private float[] dirtyWaterInMaxFlowRates;
	private float[] dirtyWaterOutActualFlowRates;
	private float[] dirtyWaterInActualFlowRates;

	private PotableWaterStore[] myPotableWaterInputs;
	private PotableWaterStore[] myPotableWaterOutputs;
	private float[] potableWaterOutMaxFlowRates;
	private float[] potableWaterInMaxFlowRates;
	private float[] potableWaterOutActualFlowRates;
	private float[] potableWaterInActualFlowRates;

	private BiomassStore[] myBiomassInputs;
	private BiomassStore[] myBiomassOutputs;
	private float[] biomassOutMaxFlowRates;
	private float[] biomassInMaxFlowRates;
	private float[] biomassOutActualFlowRates;
	private float[] biomassInActualFlowRates;

	private FoodStore[] myFoodInputs;
	private FoodStore[] myFoodOutputs;
	private float[] foodOutMaxFlowRates;
	private float[] foodInMaxFlowRates;
	private float[] foodOutActualFlowRates;
	private float[] foodInActualFlowRates;

	private SimEnvironment[] myAirInputs;
	private SimEnvironment[] myAirOutputs;
	private float[] airOutMaxFlowRates;
	private float[] airInMaxFlowRates;
	private float[] airOutActualFlowRates;
	private float[] airInActualFlowRates;

	private O2Store[] myO2Inputs;
	private O2Store[] myO2Outputs;
	private float[] O2OutMaxFlowRates;
	private float[] O2InMaxFlowRates;
	private float[] O2OutActualFlowRates;
	private float[] O2InActualFlowRates;

	private CO2Store[] myCO2Inputs;
	private CO2Store[] myCO2Outputs;
	private float[] CO2OutMaxFlowRates;
	private float[] CO2InMaxFlowRates;
	private float[] CO2OutActualFlowRates;
	private float[] CO2InActualFlowRates;
	
	private CO2Store[] myCO2AirStoreInputs;
	private CO2Store[] myCO2AirStoreOutputs;
	private float[] CO2AirStoreOutMaxFlowRates;
	private float[] CO2AirStoreInMaxFlowRates;
	private float[] CO2AirStoreOutActualFlowRates;
	private float[] CO2AirStoreInActualFlowRates;
	
	private O2Store[] myO2AirStoreInputs;
	private O2Store[] myO2AirStoreOutputs;
	private float[] O2AirStoreOutMaxFlowRates;
	private float[] O2AirStoreInMaxFlowRates;
	private float[] O2AirStoreOutActualFlowRates;
	private float[] O2AirStoreInActualFlowRates;
	
	private SimEnvironment[] myCO2AirEnvironmentInputs;
	private SimEnvironment[] myCO2AirEnvironmentOutputs;
	private float[] CO2AirEnvironmentOutMaxFlowRates;
	private float[] CO2AirEnvironmentInMaxFlowRates;
	private float[] CO2AirEnvironmentOutActualFlowRates;
	private float[] CO2AirEnvironmentInActualFlowRates;
	
	private SimEnvironment[] myO2AirEnvironmentInputs;
	private SimEnvironment[] myO2AirEnvironmentOutputs;
	private float[] O2AirEnvironmentOutMaxFlowRates;
	private float[] O2AirEnvironmentInMaxFlowRates;
	private float[] O2AirEnvironmentOutActualFlowRates;
	private float[] O2AirEnvironmentInActualFlowRates;

	public InjectorImpl(int pID){
		super(pID);
		myPowerOutputs = new PowerStore[0];
		myPowerInputs = new PowerStore[0];
		powerOutMaxFlowRates = new float[0];
		powerInMaxFlowRates = new float[0];
		powerOutActualFlowRates = new float[0];
		powerInActualFlowRates = new float[0];

		myGreyWaterOutputs = new GreyWaterStore[0];
		myGreyWaterInputs = new GreyWaterStore[0];
		greyWaterOutMaxFlowRates = new float[0];
		greyWaterInMaxFlowRates = new float[0];
		greyWaterOutActualFlowRates = new float[0];
		greyWaterInActualFlowRates = new float[0];

		myDirtyWaterOutputs = new DirtyWaterStore[0];
		myDirtyWaterInputs = new DirtyWaterStore[0];
		dirtyWaterOutMaxFlowRates = new float[0];
		dirtyWaterInMaxFlowRates = new float[0];
		dirtyWaterOutActualFlowRates = new float[0];
		dirtyWaterInActualFlowRates = new float[0];

		myPotableWaterOutputs = new PotableWaterStore[0];
		myPotableWaterInputs = new PotableWaterStore[0];
		potableWaterOutMaxFlowRates = new float[0];
		potableWaterInMaxFlowRates = new float[0];
		potableWaterOutActualFlowRates = new float[0];
		potableWaterInActualFlowRates = new float[0];

		myBiomassOutputs = new BiomassStore[0];
		myBiomassInputs = new BiomassStore[0];
		biomassOutMaxFlowRates = new float[0];
		biomassInMaxFlowRates = new float[0];
		biomassOutActualFlowRates = new float[0];
		biomassInActualFlowRates = new float[0];

		myFoodOutputs = new FoodStore[0];
		myFoodInputs = new FoodStore[0];
		foodOutMaxFlowRates = new float[0];
		foodInMaxFlowRates = new float[0];
		foodOutActualFlowRates = new float[0];
		foodInActualFlowRates = new float[0];

		myAirOutputs = new SimEnvironment[0];
		myAirInputs = new SimEnvironment[0];
		airOutMaxFlowRates = new float[0];
		airInMaxFlowRates = new float[0];
		airOutActualFlowRates = new float[0];
		airInActualFlowRates = new float[0];

		myO2Outputs = new O2Store[0];
		myO2Inputs = new O2Store[0];
		O2OutMaxFlowRates = new float[0];
		O2InMaxFlowRates = new float[0];
		O2OutActualFlowRates = new float[0];
		O2InActualFlowRates = new float[0];

		myCO2Outputs = new CO2Store[0];
		myCO2Inputs = new CO2Store[0];
		CO2OutMaxFlowRates = new float[0];
		CO2InMaxFlowRates = new float[0];
		CO2OutActualFlowRates = new float[0];
		CO2InActualFlowRates = new float[0];
		
		myCO2AirStoreOutputs = new CO2Store[0];
		myCO2AirStoreInputs = new CO2Store[0];
		CO2AirStoreOutMaxFlowRates = new float[0];
		CO2AirStoreInMaxFlowRates = new float[0];
		CO2AirStoreOutActualFlowRates = new float[0];
		CO2AirStoreInActualFlowRates = new float[0];
		
		myO2AirStoreOutputs = new O2Store[0];
		myO2AirStoreInputs = new O2Store[0];
		O2AirStoreOutMaxFlowRates = new float[0];
		O2AirStoreInMaxFlowRates = new float[0];
		O2AirStoreOutActualFlowRates = new float[0];
		O2AirStoreInActualFlowRates = new float[0];
		
		myCO2AirEnvironmentOutputs = new SimEnvironment[0];
		myCO2AirEnvironmentInputs = new SimEnvironment[0];
		CO2AirEnvironmentOutMaxFlowRates = new float[0];
		CO2AirEnvironmentInMaxFlowRates = new float[0];
		CO2AirEnvironmentOutActualFlowRates = new float[0];
		CO2AirEnvironmentInActualFlowRates = new float[0];
		
		myO2AirEnvironmentOutputs = new SimEnvironment[0];
		myO2AirEnvironmentInputs = new SimEnvironment[0];
		O2AirEnvironmentOutMaxFlowRates = new float[0];
		O2AirEnvironmentInMaxFlowRates = new float[0];
		O2AirEnvironmentOutActualFlowRates = new float[0];
		O2AirEnvironmentInActualFlowRates = new float[0];
	}


	public void tick(){
		getAndPushResources();
		if (isMalfunctioning())
			performMalfunctions();
		if (moduleLogging)
			log();
	}
	
	private void getAndPushResources(){
		float powerGathered = getMaxResourceFromStore(myPowerInputs, powerInMaxFlowRates);
		float powerPushed = pushResourceToStore(myPowerOutputs, powerOutMaxFlowRates, powerGathered);
		
		float greyWaterGathered = getMaxResourceFromStore(myGreyWaterInputs, greyWaterInMaxFlowRates);
		float greyWaterPushed = pushResourceToStore(myGreyWaterOutputs, greyWaterOutMaxFlowRates, greyWaterGathered);
		
		float potableWaterGathered = getMaxResourceFromStore(myPotableWaterInputs, potableWaterInMaxFlowRates);
		float potableWaterPushed = pushResourceToStore(myPotableWaterOutputs, potableWaterOutMaxFlowRates, potableWaterGathered);
		
		float dirtyWaterGathered = getMaxResourceFromStore(myDirtyWaterInputs, dirtyWaterInMaxFlowRates);
		float dirtyWaterPushed = pushResourceToStore(myDirtyWaterOutputs, dirtyWaterOutMaxFlowRates, dirtyWaterGathered);
		
		float biomassGathered = getMaxResourceFromStore(myBiomassInputs, biomassInMaxFlowRates);
		float biomassPushed = pushResourceToStore(myBiomassOutputs, biomassOutMaxFlowRates, biomassGathered);
		
		float foodGathered = getMaxResourceFromStore(myFoodInputs, foodInMaxFlowRates);
		float foodPushed = pushResourceToStore(myFoodOutputs, foodOutMaxFlowRates, foodGathered);
		
		float O2Gathered = getMaxResourceFromStore(myO2Inputs, O2InMaxFlowRates);
		float O2Pushed = pushResourceToStore(myO2Outputs, O2OutMaxFlowRates, O2Gathered);
		
		float CO2Gathered = getMaxResourceFromStore(myCO2Inputs, CO2InMaxFlowRates);
		float CO2Pushed = pushResourceToStore(myCO2Outputs, CO2OutMaxFlowRates, CO2Gathered);
		
		
		Breath gatheredAir = new Breath(0,0,0);
		for (int i = 0; (i < myAirInputs.length) && ((gatheredAir.O2 + gatheredAir.CO2 + gatheredAir.other) < airInMaxFlowRates[i]); i++){
			Breath currentBreath = myAirInputs[i].takeVolume(airInMaxFlowRates[i] - (gatheredAir.O2 + gatheredAir.CO2 + gatheredAir.other));
			gatheredAir.O2 += currentBreath.O2;
			gatheredAir.CO2 += currentBreath.CO2;
			gatheredAir.other += currentBreath.other;
		}
		
		Breath distributedAir = new Breath(gatheredAir.O2, gatheredAir.CO2, gatheredAir.other);
		for (int i = 0; (i < myAirOutputs.length) && ((distributedAir.O2 + distributedAir.CO2 + distributedAir.other) > 0); i++){
			distributedAir = myAirInputs[i].addBreath(distributedAir);
		}
		
		//Get CO2
		float gatheredCO2Air = getMaxResourceFromStore(myCO2AirStoreInputs, CO2AirStoreInMaxFlowRates);
		for (int i = 0; i < myCO2AirEnvironmentInputs.length; i++){
			gatheredCO2Air += myCO2AirEnvironmentInputs[i].takeCO2(CO2AirEnvironmentInMaxFlowRates[i]);
		}
		//Push CO2
		float CO2AirPushed = pushResourceToStore(myCO2AirStoreOutputs, CO2AirStoreOutMaxFlowRates, gatheredCO2Air);
		float CO2AirLeft = gatheredCO2Air - CO2AirPushed;
		for (int i = 0; (i < myCO2AirEnvironmentOutputs.length) && (CO2AirLeft > 0); i++){
			CO2AirLeft -= myCO2AirEnvironmentOutputs[i].addCO2(CO2AirEnvironmentOutMaxFlowRates[i]);
		}
		CO2AirPushed = gatheredCO2Air - CO2AirLeft;
		
		//Get O2
		float gatheredO2Air = getMaxResourceFromStore(myO2AirStoreInputs, O2AirStoreInMaxFlowRates);
		for (int i = 0; i < myO2AirEnvironmentInputs.length; i++){
			gatheredO2Air += myO2AirEnvironmentInputs[i].takeO2(O2AirEnvironmentInMaxFlowRates[i]);
		}
		//Push O2
		float O2AirPushed = pushResourceToStore(myO2AirStoreOutputs, O2AirStoreOutMaxFlowRates, gatheredO2Air);
		float O2AirLeft = gatheredO2Air - O2AirPushed;
		for (int i = 0; (i < myO2AirEnvironmentOutputs.length) && (O2AirLeft > 0); i++){
			O2AirLeft -= myO2AirEnvironmentOutputs[i].addO2(O2AirEnvironmentOutMaxFlowRates[i]);
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
	
	//Power Inputs
	public void setPowerInputMaxFlowRate(float amount, int index){
		powerInMaxFlowRates[index] = amount;
	}
	public float getPowerInputMaxFlowRate(int index){
		return powerInMaxFlowRates[index];
	}
	public float[] getPowerInputMaxFlowRates(){
		return powerInMaxFlowRates;
	}
	public void setPowerInputActualFlowRate(float amount, int index){
		powerInActualFlowRates[index] = amount;
	}
	public float getPowerInputActualFlowRate(int index){
		return powerInActualFlowRates[index];
	}
	public float[] getPowerInputActualFlowRates(){
		return powerInActualFlowRates;
	}
	public void setPowerInputs(PowerStore[] sources, float[] maxFlowRates, float[] actualFlowRates){
		myPowerInputs = sources;
		powerInMaxFlowRates = maxFlowRates;
		powerInActualFlowRates = actualFlowRates;
	}
	public PowerStore[] getPowerInputs(){
		return myPowerInputs;
	}
	
	//Power Outputs
	public void setPowerOutputMaxFlowRate(float amount, int index){
		powerOutMaxFlowRates[index] = amount;
	}
	public float getPowerOutputMaxFlowRate(int index){
		return powerOutMaxFlowRates[index];
	}
	public float[] getPowerOutputMaxFlowRates(){
		return powerOutMaxFlowRates;
	}
	public void setPowerOutputActualFlowRate(float amount, int index){
		powerOutActualFlowRates[index] = amount;
	}
	public float getPowerOutputActualFlowRate(int index){
		return powerOutActualFlowRates[index];
	}
	public float[] getPowerOutputActualFlowRates(){
		return powerOutActualFlowRates;
	}
	public void setPowerOutputs(PowerStore[] destinations, float[] maxFlowRates, float[] actualFlowRates){
		myPowerOutputs = destinations;
		powerOutMaxFlowRates = maxFlowRates;
		powerOutActualFlowRates = actualFlowRates;
	}
	public PowerStore[] getPowerOutputs(){
		return myPowerOutputs;
	}
	
	//Grey Water Inputs
	public void setGreyWaterInputMaxFlowRate(float amount, int index){
		greyWaterInMaxFlowRates[index] = amount;
	}
	public float getGreyWaterInputMaxFlowRate(int index){
		return greyWaterInMaxFlowRates[index];
	}
	public float[] getGreyWaterInputMaxFlowRates(){
		return greyWaterInMaxFlowRates;
	}
	public void setGreyWaterInputActualFlowRate(float amount, int index){
		greyWaterInActualFlowRates[index] = amount;
	}
	public float getGreyWaterInputActualFlowRate(int index){
		return greyWaterInActualFlowRates[index];
	}
	public float[] getGreyWaterInputActualFlowRates(){
		return greyWaterInActualFlowRates;
	}
	public void setGreyWaterInputs(GreyWaterStore[] sources, float[] maxFlowRates, float[] actualFlowRates){
		myGreyWaterInputs = sources;
		greyWaterInMaxFlowRates = maxFlowRates;
		greyWaterInActualFlowRates = actualFlowRates;
	}
	public GreyWaterStore[] getGreyWaterInputs(){
		return myGreyWaterInputs;
	}
	
	//Grey Water Outputs
	public void setGreyWaterOutputMaxFlowRate(float amount, int index){
		greyWaterOutMaxFlowRates[index] = amount;
	}
	public float getGreyWaterOutputMaxFlowRate(int index){
		return greyWaterOutMaxFlowRates[index];
	}
	public float[] getGreyWaterOutputMaxFlowRates(){
		return greyWaterOutMaxFlowRates;
	}
	public void setGreyWaterOutputActualFlowRate(float amount, int index){
		greyWaterOutActualFlowRates[index] = amount;
	}
	public float getGreyWaterOutputActualFlowRate(int index){
		return greyWaterOutActualFlowRates[index];
	}
	public float[] getGreyWaterOutputActualFlowRates(){
		return greyWaterOutActualFlowRates;
	}
	public void setGreyWaterOutputs(GreyWaterStore[] destinations, float[] maxFlowRates, float[] actualFlowRates){
		myGreyWaterOutputs = destinations;
		greyWaterOutMaxFlowRates = maxFlowRates;
		greyWaterOutActualFlowRates = actualFlowRates;
	}
	public GreyWaterStore[] getGreyWaterOutputs(){
		return myGreyWaterOutputs;
	}
	
	//Dirty Water Inputs
	public void setDirtyWaterInputMaxFlowRate(float amount, int index){
		dirtyWaterInMaxFlowRates[index] = amount;
	}
	public float getDirtyWaterInputMaxFlowRate(int index){
		return dirtyWaterInMaxFlowRates[index];
	}
	public float[] getDirtyWaterInputMaxFlowRates(){
		return dirtyWaterInMaxFlowRates;
	}
	public void setDirtyWaterInputActualFlowRate(float amount, int index){
		dirtyWaterInActualFlowRates[index] = amount;
	}
	public float getDirtyWaterInputActualFlowRate(int index){
		return dirtyWaterInActualFlowRates[index];
	}
	public float[] getDirtyWaterInputActualFlowRates(){
		return dirtyWaterInActualFlowRates;
	}
	public void setDirtyWaterInputs(DirtyWaterStore[] sources, float[] maxFlowRates, float[] actualFlowRates){
		myDirtyWaterInputs = sources;
		dirtyWaterInMaxFlowRates = maxFlowRates;
		dirtyWaterInActualFlowRates = actualFlowRates;
	}
	public DirtyWaterStore[] getDirtyWaterInputs(){
		return myDirtyWaterInputs;
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
	public void setDirtyWaterOutputActualFlowRate(float amount, int index){
		dirtyWaterOutActualFlowRates[index] = amount;
	}
	public float getDirtyWaterOutputActualFlowRate(int index){
		return dirtyWaterOutActualFlowRates[index];
	}
	public float[] getDirtyWaterOutputActualFlowRates(){
		return dirtyWaterOutActualFlowRates;
	}
	public void setDirtyWaterOutputs(DirtyWaterStore[] destinations, float[] maxFlowRates, float[] actualFlowRates){
		myDirtyWaterOutputs = destinations;
		dirtyWaterOutMaxFlowRates = maxFlowRates;
		dirtyWaterOutActualFlowRates = actualFlowRates;
	}
	public DirtyWaterStore[] getDirtyWaterOutputs(){
		return myDirtyWaterOutputs;
	}
	
	//Potable Water Inputs
	public void setPotableWaterInputMaxFlowRate(float amount, int index){
		potableWaterInMaxFlowRates[index] = amount;
	}
	public float getPotableWaterInputMaxFlowRate(int index){
		return potableWaterInMaxFlowRates[index];
	}
	public float[] getPotableWaterInputMaxFlowRates(){
		return potableWaterInMaxFlowRates;
	}
	public void setPotableWaterInputActualFlowRate(float amount, int index){
		potableWaterInActualFlowRates[index] = amount;
	}
	public float getPotableWaterInputActualFlowRate(int index){
		return potableWaterInActualFlowRates[index];
	}
	public float[] getPotableWaterInputActualFlowRates(){
		return potableWaterInActualFlowRates;
	}
	public void setPotableWaterInputs(PotableWaterStore[] sources, float[] maxFlowRates, float[] actualFlowRates){
		myPotableWaterInputs = sources;
		potableWaterInMaxFlowRates = maxFlowRates;
		potableWaterInActualFlowRates = actualFlowRates;
	}
	public PotableWaterStore[] getPotableWaterInputs(){
		return myPotableWaterInputs;
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
	public void setPotableWaterOutputActualFlowRate(float amount, int index){
		potableWaterOutActualFlowRates[index] = amount;
	}
	public float getPotableWaterOutputActualFlowRate(int index){
		return potableWaterOutActualFlowRates[index];
	}
	public float[] getPotableWaterOutputActualFlowRates(){
		return potableWaterOutActualFlowRates;
	}
	public void setPotableWaterOutputs(PotableWaterStore[] destinations, float[] maxFlowRates, float[] actualFlowRates){
		myPotableWaterOutputs = destinations;
		potableWaterOutMaxFlowRates = maxFlowRates;
		potableWaterOutActualFlowRates = actualFlowRates;
	}
	public PotableWaterStore[] getPotableWaterOutputs(){
		return myPotableWaterOutputs;
	}
	
	//Food Inputs
	public void setFoodInputMaxFlowRate(float amount, int index){
		foodInMaxFlowRates[index] = amount;
	}
	public float getFoodInputMaxFlowRate(int index){
		return foodInMaxFlowRates[index];
	}
	public float[] getFoodInMaxFlowRates(){
		return foodInMaxFlowRates;
	}
	public void setFoodInputActualFlowRate(float amount, int index){
		foodInActualFlowRates[index] = amount;
	}
	public float getFoodInputActualFlowRate(int index){
		return foodInActualFlowRates[index];
	}
	public float[] getFoodInActualFlowRates(){
		return foodInActualFlowRates;
	}
	public void setFoodInputs(FoodStore[] sources, float[] maxFlowRates, float[] actualFlowRates){
		myFoodInputs = sources;
		foodInMaxFlowRates = maxFlowRates;
		foodInActualFlowRates = actualFlowRates;
	}
	public FoodStore[] getFoodInputs(){
		return myFoodInputs;
	}
	
	//Food Outputs
	public void setFoodOutputMaxFlowRate(float amount, int index){
		foodOutMaxFlowRates[index] = amount;
	}
	public float getFoodOutputMaxFlowRate(int index){
		return foodOutMaxFlowRates[index];
	}
	public float[] getFoodOutputMaxFlowRates(){
		return foodOutMaxFlowRates;
	}
	public void setFoodOutputActualFlowRate(float amount, int index){
		foodOutActualFlowRates[index] = amount;
	}
	public float getFoodOutputActualFlowRate(int index){
		return foodOutActualFlowRates[index];
	}
	public float[] getFoodOutputActualFlowRates(){
		return foodOutActualFlowRates;
	}
	public void setFoodOutputs(FoodStore[] destinations, float[] maxFlowRates, float[] actualFlowRates){
		myFoodOutputs = destinations;
		foodOutMaxFlowRates = maxFlowRates;
		foodOutActualFlowRates = actualFlowRates;
	}
	public FoodStore[] getFoodOutputs(){
		return myFoodOutputs;
	}
	
	//Biomass Inputs
	public void setBiomassInputMaxFlowRate(float amount, int index){
		biomassInMaxFlowRates[index] = amount;
	}
	public float getBiomassInputMaxFlowRate(int index){
		return biomassInMaxFlowRates[index];
	}
	public float[] getBiomassInputMaxFlowRates(){
		return biomassInMaxFlowRates;
	}
	public void setBiomassInputActualFlowRate(float amount, int index){
		biomassInActualFlowRates[index] = amount;
	}
	public float getBiomassInputActualFlowRate(int index){
		return biomassInActualFlowRates[index];
	}
	public float[] getBiomassInputActualFlowRates(){
		return biomassInActualFlowRates;
	}
	public void setBiomassInputs(BiomassStore[] sources, float[] maxFlowRates, float[] actualFlowRates){
		myBiomassInputs = sources;
		biomassInMaxFlowRates = maxFlowRates;
		biomassInActualFlowRates = actualFlowRates;
	}
	public BiomassStore[] getBiomassInputs(){
		return myBiomassInputs;
	}
	
	//Biomass Outputs
	public void setBiomassOutputMaxFlowRate(float amount, int index){
		biomassOutMaxFlowRates[index] = amount;
	}
	public float getBiomassOutputMaxFlowRate(int index){
		return biomassOutMaxFlowRates[index];
	}
	public float[] getBiomassOutputMaxFlowRates(){
		return biomassOutMaxFlowRates;
	}
	public void setBiomassOutputActualFlowRate(float amount, int index){
		biomassOutActualFlowRates[index] = amount;
	}
	public float getBiomassOutputActualFlowRate(int index){
		return biomassOutActualFlowRates[index];
	}
	public float[] getBiomassOutputActualFlowRates(){
		return biomassOutActualFlowRates;
	}
	public void setBiomassOutputs(BiomassStore[] destinations, float[] maxFlowRates, float[] actualFlowRates){
		myBiomassOutputs = destinations;
		biomassOutMaxFlowRates = maxFlowRates;
		biomassOutActualFlowRates = actualFlowRates;
	}
	public BiomassStore[] getBiomassOutputs(){
		return myBiomassOutputs;
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
	public void setAirInputActualFlowRate(float amount, int index){
		airInActualFlowRates[index] = amount;
	}
	public float getAirInputActualFlowRate(int index){
		return airInActualFlowRates[index];
	}
	public float[] getAirInputActualFlowRates(){
		return airInActualFlowRates;
	}
	public void setAirInputs(SimEnvironment[] sources, float[] maxFlowRates, float[] actualFlowRates){
		myAirInputs = sources;
		airInMaxFlowRates = maxFlowRates;
		airInActualFlowRates = actualFlowRates;
	}
	public SimEnvironment[] getAirInputs(){
		return myAirInputs;
	}
	
	//Air Outputs
	public void setAirOutputMaxFlowRate(float amount, int index){
		airOutMaxFlowRates[index] = amount;
	}
	public float getAirOutputMaxFlowRate(int index){
		return airOutMaxFlowRates[index];
	}
	public float[] getAirOutputMaxFlowRates(){
		return airOutMaxFlowRates;
	}
	public void setAirOutputActualFlowRate(float amount, int index){
		airOutActualFlowRates[index] = amount;
	}
	public float getAirOutputActualFlowRate(int index){
		return airOutActualFlowRates[index];
	}
	public float[] getAirOutputActualFlowRates(){
		return airOutActualFlowRates;
	}
	public void setAirOutputs(SimEnvironment[] destinations, float[] maxFlowRates, float[] actualFlowRates){
		myAirOutputs = destinations;
		airOutMaxFlowRates = maxFlowRates;
		airOutActualFlowRates = actualFlowRates;
	}
	public SimEnvironment[] getAirOutputs(){
		return myAirOutputs;
	}
	
	//O2 Inputs
	public void setO2InputMaxFlowRate(float amount, int index){
		O2InMaxFlowRates[index] = amount;
	}
	public float getO2InputMaxFlowRate(int index){
		return O2InMaxFlowRates[index];
	}
	public float[] getO2InputMaxFlowRates(){
		return O2InMaxFlowRates;
	}
	public void setO2InputActualFlowRate(float amount, int index){
		O2InActualFlowRates[index] = amount;
	}
	public float getO2InputActualFlowRate(int index){
		return O2InActualFlowRates[index];
	}
	public float[] getO2InputActualFlowRates(){
		return O2InActualFlowRates;
	}
	public void setO2Inputs(O2Store[] sources, float[] maxFlowRates, float[] actualFlowRates){
		myO2Inputs = sources;
		O2InMaxFlowRates = maxFlowRates;
		O2InActualFlowRates = actualFlowRates;
	}
	public O2Store[] getO2Inputs(){
		return myO2Inputs;
	}
	
	//O2 Outputs
	public void setO2OutputMaxFlowRate(float amount, int index){
		O2OutMaxFlowRates[index] = amount;
	}
	public float getO2OutputMaxFlowRate(int index){
		return O2OutMaxFlowRates[index];
	}
	public float[] getO2OutputMaxFlowRates(){
		return O2OutMaxFlowRates;
	}
	public void setO2OutputActualFlowRate(float amount, int index){
		O2OutActualFlowRates[index] = amount;
	}
	public float getO2OutputActualFlowRate(int index){
		return O2OutActualFlowRates[index];
	}
	public float[] getO2OutputActualFlowRates(){
		return O2OutActualFlowRates;
	}
	public void setO2Outputs(O2Store[] destinations, float[] maxFlowRates, float[] actualFlowRates){
		myO2Outputs = destinations;
		O2OutMaxFlowRates = maxFlowRates;
		O2OutActualFlowRates = actualFlowRates;
	}
	public O2Store[] getO2Outputs(){
		return myO2Outputs;
	}
	
	//CO2 Inputs
	public void setCO2InputMaxFlowRate(float amount, int index){
		CO2InMaxFlowRates[index] = amount;
	}
	public float getCO2InputMaxFlowRate(int index){
		return CO2InMaxFlowRates[index];
	}
	public float[] getCO2InputMaxFlowRates(){
		return CO2InMaxFlowRates;
	}
	public void setCO2InputActualFlowRate(float amount, int index){
		CO2InActualFlowRates[index] = amount;
	}
	public float getCO2InputActualFlowRate(int index){
		return CO2InActualFlowRates[index];
	}
	public float[] getCO2InputActualFlowRates(){
		return CO2InActualFlowRates;
	}
	public CO2Store[] getCO2Inputs(){
		return myCO2Inputs;
	}
	public void setCO2Inputs(CO2Store[] sources, float[] maxFlowRates, float[] actualFlowRates){
		myCO2Inputs = sources;
		CO2InMaxFlowRates = maxFlowRates;
		CO2InActualFlowRates = actualFlowRates;
	}
	
	//CO2 Outputs
	public void setCO2OutputMaxFlowRate(float amount, int index){
		CO2OutMaxFlowRates[index] = amount;
	}
	public float getCO2OutputMaxFlowRate(int index){
		return CO2OutMaxFlowRates[index];
	}
	public float[] getCO2OutputMaxFlowRates(){
		return CO2OutMaxFlowRates;
	}
	public void setCO2OutputActualFlowRate(float amount, int index){
		CO2OutActualFlowRates[index] = amount;
	}
	public float getCO2OutputActualFlowRate(int index){
		return CO2OutActualFlowRates[index];
	}
	public float[] getCO2OutputActualFlowRates(){
		return CO2OutActualFlowRates;
	}
	public void setCO2Outputs(CO2Store[] destinations, float[] maxFlowRates, float[] actualFlowRates){
		myCO2Outputs = destinations;
		CO2OutMaxFlowRates = maxFlowRates;
		CO2OutActualFlowRates = actualFlowRates;
	}
	public CO2Store[] getCO2Outputs(){
		return myCO2Outputs;
	}
	
	//CO2 Air Inputs
	public void setCO2AirStoreInputMaxFlowRate(float amount, int index){
		CO2AirStoreInMaxFlowRates[index] = amount;
	}
	public void setCO2AirEnvironmentInputMaxFlowRate(float amount, int index){
		CO2AirEnvironmentInMaxFlowRates[index] = amount;
	}
	public float getCO2AirStoreInputMaxFlowRate(int index){
		return CO2AirStoreInMaxFlowRates[index];
	}
	public float getCO2AirEnvironmentInputMaxFlowRate(int index){
		return CO2AirEnvironmentInMaxFlowRates[index];
	}
	public float[] getCO2AirStoreInputMaxFlowRates(){
		return CO2AirStoreInMaxFlowRates;
	}
	public float[] getCO2AirEnvironmentInputMaxFlowRates(){
		return CO2AirEnvironmentInMaxFlowRates;
	}
	public void setCO2AirStoreInputActualFlowRate(float amount, int index){
		CO2AirStoreInActualFlowRates[index] = amount;
	}
	public void setCO2AirEnvironmentInputActualFlowRate(float amount, int index){
		CO2AirEnvironmentInActualFlowRates[index] = amount;
	}
	public float getCO2AirStoreInputActualFlowRate(int index){
		return CO2AirStoreInActualFlowRates[index];
	}
	public float getCO2AirEnvironmentInputActualFlowRate(int index){
		return CO2AirEnvironmentInActualFlowRates[index];
	}
	public float[] getCO2AirStoreInputActualFlowRates(){
		return CO2AirStoreInActualFlowRates;
	}
	public float[] getCO2AirEnvironmentInputActualFlowRates(){
		return CO2AirEnvironmentInActualFlowRates;
	}
	public void setCO2AirStoreInputs(CO2Store[] sources, float[] maxFlowRates, float[] actualFlowRates){
		myCO2AirStoreInputs = sources;
		CO2AirStoreInMaxFlowRates = maxFlowRates;
		CO2AirStoreInActualFlowRates = actualFlowRates;
	}
	public void setCO2AirEnvironmentInputs(SimEnvironment[] sources, float[] maxFlowRates, float[] actualFlowRates){
		myCO2AirEnvironmentInputs = sources;
		CO2AirEnvironmentInMaxFlowRates = maxFlowRates;
		CO2AirEnvironmentInActualFlowRates = actualFlowRates;
	}
	public CO2Store[] getCO2AirStoreInputs(){
		return myCO2AirStoreInputs;
	}
	public SimEnvironment[] getCO2AirEnvironmentInputs(){
		return myCO2AirEnvironmentInputs;
	}
	
	//CO2 Air Outputs
	public void setCO2AirStoreOutputMaxFlowRate(float amount, int index){
		CO2AirStoreOutMaxFlowRates[index] = amount;
	}
	public void setCO2AirEnvironmentOutputMaxFlowRate(float amount, int index){
		CO2AirEnvironmentOutMaxFlowRates[index] = amount;
	}
	public float getCO2AirStoreOutputMaxFlowRate(int index){
		return CO2AirStoreOutMaxFlowRates[index];
	}
	public float getCO2AirEnvironmentOutputMaxFlowRate(int index){
		return CO2AirEnvironmentOutMaxFlowRates[index];
	}
	public float[] getCO2AirStoreOutputMaxFlowRates(){
		return CO2AirStoreOutMaxFlowRates;
	}
	public float[] getCO2AirEnvironmentOutputMaxFlowRates(){
		return CO2AirEnvironmentOutMaxFlowRates;
	}
	public void setCO2AirStoreOutputActualFlowRate(float amount, int index){
		CO2AirStoreOutActualFlowRates[index] = amount;
	}
	public void setCO2AirEnvironmentOutputActualFlowRate(float amount, int index){
		CO2AirEnvironmentOutActualFlowRates[index] = amount;
	}
	public float getCO2AirStoreOutputActualFlowRate(int index){
		return CO2AirStoreOutActualFlowRates[index];
	}
	public float getCO2AirEnvironmentOutputActualFlowRate(int index){
		return CO2AirEnvironmentOutActualFlowRates[index];
	}
	public float[] getCO2AirStoreOutputActualFlowRates(){
		return CO2AirStoreOutActualFlowRates;
	}
	public float[] getCO2AirEnvironmentOutputActualFlowRates(){
		return CO2AirEnvironmentOutActualFlowRates;
	}
	public void setCO2AirStoreOutputs(CO2Store[] destinations, float[] maxFlowRates, float[] actualFlowRates){
		myCO2AirStoreOutputs = destinations;
		CO2AirStoreOutMaxFlowRates = maxFlowRates;
		CO2AirStoreOutActualFlowRates = actualFlowRates;
	}
	public void setCO2AirEnvironmentOutputs(SimEnvironment[] destinations, float[] maxFlowRates, float[] actualFlowRates){
		myCO2AirEnvironmentOutputs = destinations;
		CO2AirEnvironmentOutMaxFlowRates = maxFlowRates;
		CO2AirEnvironmentOutActualFlowRates = actualFlowRates;
	}
	public CO2Store[] getCO2AirStoreOutputs(){
		return myCO2AirStoreOutputs;
	}
	public SimEnvironment[] getCO2AirEnvironmentOutputs(){
		return myCO2AirEnvironmentOutputs;
	}
	
	//O2 Air Input
	public void setO2AirStoreInputMaxFlowRate(float amount, int index){
		O2AirStoreInMaxFlowRates[index] = amount;
	}
	public void setO2AirEnvironmentInputMaxFlowRate(float amount, int index){
		O2AirEnvironmentInMaxFlowRates[index] = amount;
	}
	public float getO2AirStoreInputMaxFlowRate(int index){
		return O2AirStoreInMaxFlowRates[index];
	}
	public float getO2AirEnvironmentInputMaxFlowRate(int index){
		return O2AirEnvironmentInMaxFlowRates[index];
	}
	public float[] getO2AirStoreInputMaxFlowRates(){
		return O2AirStoreInMaxFlowRates;
	}
	public float[] getO2AirEnvironmentInputMaxFlowRates(){
		return O2AirEnvironmentInMaxFlowRates;
	}
	public void setO2AirStoreInputActualFlowRate(float amount, int index){
		O2AirStoreInActualFlowRates[index] = amount;
	}
	public void setO2AirEnvironmentInputActualFlowRate(float amount, int index){
		O2AirEnvironmentInActualFlowRates[index] = amount;
	}
	public float getO2AirStoreInputActualFlowRate(int index){
		return O2AirStoreInActualFlowRates[index];
	}
	public float getO2AirEnvironmentInputActualFlowRate(int index){
		return O2AirEnvironmentInActualFlowRates[index];
	}
	public float[] getO2AirStoreInputActualFlowRates(){
		return O2AirStoreInActualFlowRates;
	}
	public float[] getO2AirEnvironmentInputActualFlowRates(){
		return O2AirEnvironmentInActualFlowRates;
	}
	public void setO2AirStoreInputs(O2Store[] sources, float[] maxFlowRates, float[] actualFlowRates){
		myO2AirStoreInputs = sources;
		O2AirStoreInMaxFlowRates = maxFlowRates;
		O2AirStoreInActualFlowRates = actualFlowRates;
	}
	public void setO2AirEnvironmentInputs(SimEnvironment[] sources, float[] maxFlowRates, float[] actualFlowRates){
		myO2AirEnvironmentInputs = sources;
		O2AirEnvironmentInMaxFlowRates = maxFlowRates;
		O2AirEnvironmentInActualFlowRates = actualFlowRates;
	}
	public O2Store[] getO2AirStoreInputs(){
		return myO2AirStoreInputs;
	}
	public SimEnvironment[] getO2AirEnvironmentInputs(){
		return myO2AirEnvironmentInputs;
	}
	
	//O2 Air Output
	public void setO2AirStoreOutputMaxFlowRate(float amount, int index){
		O2AirStoreOutMaxFlowRates[index] = amount;
	}
	public void setO2AirEnvironmentOutputMaxFlowRate(float amount, int index){
		O2AirEnvironmentOutMaxFlowRates[index] = amount;
	}
	public float getO2AirStoreOutputMaxFlowRate(int index){
		return O2AirStoreOutMaxFlowRates[index];
	}
	public float getO2AirEnvironmentOutputMaxFlowRate(int index){
		return O2AirEnvironmentOutMaxFlowRates[index];
	}
	public float[] getO2AirStoreOutputMaxFlowRates(){
		return O2AirStoreOutMaxFlowRates;
	}
	public float[] getO2AirEnvironmentOutputMaxFlowRates(){
		return O2AirEnvironmentOutMaxFlowRates;
	}
	public void setO2AirStoreOutputActualFlowRate(float amount, int index){
		O2AirStoreOutActualFlowRates[index] = amount;
	}
	public void setO2AirEnvironmentOutputActualFlowRate(float amount, int index){
		O2AirEnvironmentOutActualFlowRates[index] = amount;
	}
	public float getO2AirStoreOutputActualFlowRate(int index){
		return O2AirStoreOutActualFlowRates[index];
	}
	public float getO2AirEnvironmentOutputActualFlowRate(int index){
		return O2AirEnvironmentOutActualFlowRates[index];
	}
	public float[] getO2AirStoreOutputActualFlowRates(){
		return O2AirStoreOutActualFlowRates;
	}
	public float[] getO2AirEnvironmentOutputActualFlowRates(){
		return O2AirEnvironmentOutActualFlowRates;
	}
	public void setO2AirStoreOutputs(O2Store[] destinations, float[] maxFlowRates, float[] actualFlowRates){
		myO2AirStoreOutputs = destinations;
		O2AirStoreOutMaxFlowRates = maxFlowRates;
		O2AirStoreOutActualFlowRates = actualFlowRates;
	}
	public void setO2AirEnvironmentOutputs(SimEnvironment[] destinations, float[] maxFlowRates, float[] actualFlowRates){
		myO2AirEnvironmentOutputs = destinations;
		O2AirEnvironmentOutMaxFlowRates = maxFlowRates;
		O2AirEnvironmentOutActualFlowRates = actualFlowRates;
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
		return "Injector"+getID();
	}
}
