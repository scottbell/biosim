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
 * It takes as much as it can (max taken set by maxFlowRates) from one module and injects it into another module.
 * Functionally equivalent to an Accumulator at this point. 
 * @author    Scott Bell
 */

public class InjectorImpl extends SimBioModuleImpl implements InjectorOperations, PowerConsumerOperations, PotableWaterConsumerOperations, GreyWaterConsumerOperations, DirtyWaterConsumerOperations, O2ConsumerOperations, CO2ConsumerOperations, AirConsumerOperations, BiomassConsumerOperations, FoodConsumerOperations, PowerProducerOperations, PotableWaterProducerOperations, GreyWaterProducerOperations, DirtyWaterProducerOperations, O2ProducerOperations, CO2ProducerOperations, AirProducerOperations, BiomassProducerOperations, FoodProducerOperations, O2AirConsumerOperations, CO2AirConsumerOperations, O2AirProducerOperations, CO2AirProducerOperations{
	private LogIndex myLogIndex;
	private PowerStore[] myPowerInputs;
	private PowerStore[] myPowerOutputs;
	private float[] powerOutMaxFlowRates;
	private float[] powerInMaxFlowRates;

	private GreyWaterStore[] myGreyWaterInputs;
	private GreyWaterStore[] myGreyWaterOutputs;
	private float[] greyWaterOutMaxFlowRates;
	private float[] greyWaterInMaxFlowRates;

	private DirtyWaterStore[] myDirtyWaterInputs;
	private DirtyWaterStore[] myDirtyWaterOutputs;
	private float[] dirtyWaterOutMaxFlowRates;
	private float[] dirtyWaterInMaxFlowRates;

	private PotableWaterStore[] myPotableWaterInputs;
	private PotableWaterStore[] myPotableWaterOutputs;
	private float[] potableWaterOutMaxFlowRates;
	private float[] potableWaterInMaxFlowRates;

	private BiomassStore[] myBiomassInputs;
	private BiomassStore[] myBiomassOutputs;
	private float[] biomassOutMaxFlowRates;
	private float[] biomassInMaxFlowRates;

	private FoodStore[] myFoodInputs;
	private FoodStore[] myFoodOutputs;
	private float[] foodOutMaxFlowRates;
	private float[] foodInMaxFlowRates;

	private SimEnvironment[] myAirInputs;
	private SimEnvironment[] myAirOutputs;
	private float[] airOutMaxFlowRates;
	private float[] airInMaxFlowRates;

	private O2Store[] myO2Inputs;
	private O2Store[] myO2Outputs;
	private float[] O2OutMaxFlowRates;
	private float[] O2InMaxFlowRates;

	private CO2Store[] myCO2Inputs;
	private CO2Store[] myCO2Outputs;
	private float[] CO2OutMaxFlowRates;
	private float[] CO2InMaxFlowRates;
	
	private CO2Store[] myCO2AirStoreInputs;
	private CO2Store[] myCO2AirStoreOutputs;
	private float[] CO2AirStoreOutMaxFlowRates;
	private float[] CO2AirStoreInMaxFlowRates;
	
	private O2Store[] myO2AirStoreInputs;
	private O2Store[] myO2AirStoreOutputs;
	private float[] O2AirStoreOutMaxFlowRates;
	private float[] O2AirStoreInMaxFlowRates;
	
	private SimEnvironment[] myCO2AirEnvironmentInputs;
	private SimEnvironment[] myCO2AirEnvironmentOutputs;
	private float[] CO2AirEnvironmentOutMaxFlowRates;
	private float[] CO2AirEnvironmentInMaxFlowRates;
	
	private SimEnvironment[] myO2AirEnvironmentInputs;
	private SimEnvironment[] myO2AirEnvironmentOutputs;
	private float[] O2AirEnvironmentOutMaxFlowRates;
	private float[] O2AirEnvironmentInMaxFlowRates;

	public InjectorImpl(int pID){
		super(pID);
		myPowerOutputs = new PowerStore[0];
		myPowerInputs = new PowerStore[0];
		powerOutMaxFlowRates = new float[0];
		powerInMaxFlowRates = new float[0];

		myGreyWaterOutputs = new GreyWaterStore[0];
		myGreyWaterInputs = new GreyWaterStore[0];
		greyWaterOutMaxFlowRates = new float[0];
		greyWaterInMaxFlowRates = new float[0];

		myDirtyWaterOutputs = new DirtyWaterStore[0];
		myDirtyWaterInputs = new DirtyWaterStore[0];
		dirtyWaterOutMaxFlowRates = new float[0];
		dirtyWaterInMaxFlowRates = new float[0];

		myPotableWaterOutputs = new PotableWaterStore[0];
		myPotableWaterInputs = new PotableWaterStore[0];
		potableWaterOutMaxFlowRates = new float[0];
		potableWaterInMaxFlowRates = new float[0];

		myBiomassOutputs = new BiomassStore[0];
		myBiomassInputs = new BiomassStore[0];
		biomassOutMaxFlowRates = new float[0];
		biomassInMaxFlowRates = new float[0];

		myFoodOutputs = new FoodStore[0];
		myFoodInputs = new FoodStore[0];
		foodOutMaxFlowRates = new float[0];
		foodInMaxFlowRates = new float[0];

		myAirOutputs = new SimEnvironment[0];
		myAirInputs = new SimEnvironment[0];
		airOutMaxFlowRates = new float[0];
		airInMaxFlowRates = new float[0];

		myO2Outputs = new O2Store[0];
		myO2Inputs = new O2Store[0];
		O2OutMaxFlowRates = new float[0];
		O2InMaxFlowRates = new float[0];

		myCO2Outputs = new CO2Store[0];
		myCO2Inputs = new CO2Store[0];
		CO2OutMaxFlowRates = new float[0];
		CO2InMaxFlowRates = new float[0];
		
		myCO2AirStoreOutputs = new CO2Store[0];
		myCO2AirStoreInputs = new CO2Store[0];
		CO2AirStoreOutMaxFlowRates = new float[0];
		CO2AirStoreInMaxFlowRates = new float[0];
		
		myO2AirStoreOutputs = new O2Store[0];
		myO2AirStoreInputs = new O2Store[0];
		O2AirStoreOutMaxFlowRates = new float[0];
		O2AirStoreInMaxFlowRates = new float[0];
		
		myCO2AirEnvironmentOutputs = new SimEnvironment[0];
		myCO2AirEnvironmentInputs = new SimEnvironment[0];
		CO2AirEnvironmentOutMaxFlowRates = new float[0];
		CO2AirEnvironmentInMaxFlowRates = new float[0];
		
		myO2AirEnvironmentOutputs = new SimEnvironment[0];
		myO2AirEnvironmentInputs = new SimEnvironment[0];
		O2AirEnvironmentOutMaxFlowRates = new float[0];
		O2AirEnvironmentInMaxFlowRates = new float[0];
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

	public void setPowerInputMaxFlowRate(float amount, int index){
		powerInMaxFlowRates[index] = amount;
	}

	public float getPowerInputMaxFlowRate(int index){
		return powerInMaxFlowRates[index];
	}

	public void setPowerInputs(PowerStore[] sources, float[] maxFlowRates){
		myPowerInputs = sources;
		powerInMaxFlowRates = maxFlowRates;
	}

	public PowerStore[] getPowerInputs(){
		return myPowerInputs;
	}
	
	public float[] getPowerInputMaxFlowRates(){
		return powerInMaxFlowRates;
	}

	public void setPowerOutputMaxFlowRate(float amount, int index){
		powerOutMaxFlowRates[index] = amount;
	}

	public float getPowerOutputMaxFlowRate(int index){
		return powerOutMaxFlowRates[index];
	}

	public void setPowerOutputs(PowerStore[] destinations, float[] maxFlowRates){
		myPowerOutputs = destinations;
		powerOutMaxFlowRates = maxFlowRates;
	}

	public PowerStore[] getPowerOutputs(){
		return myPowerOutputs;
	}
	
	public float[] getPowerOutputMaxFlowRates(){
		return powerOutMaxFlowRates;
	}

	public void setGreyWaterInputMaxFlowRate(float amount, int index){
		greyWaterInMaxFlowRates[index] = amount;
	}

	public float getGreyWaterInputMaxFlowRate(int index){
		return greyWaterInMaxFlowRates[index];
	}

	public void setGreyWaterInputs(GreyWaterStore[] sources, float[] maxFlowRates){
		myGreyWaterInputs = sources;
		greyWaterInMaxFlowRates = maxFlowRates;
	}

	public GreyWaterStore[] getGreyWaterInputs(){
		return myGreyWaterInputs;
	}
	
	public float[] getGreyWaterInputMaxFlowRates(){
		return greyWaterInMaxFlowRates;
	}

	public void setGreyWaterOutputMaxFlowRate(float amount, int index){
		greyWaterOutMaxFlowRates[index] = amount;
	}

	public float getGreyWaterOutputMaxFlowRate(int index){
		return greyWaterOutMaxFlowRates[index];
	}

	public void setGreyWaterOutputs(GreyWaterStore[] destinations, float[] maxFlowRates){
		myGreyWaterOutputs = destinations;
		greyWaterOutMaxFlowRates = maxFlowRates;
	}

	public GreyWaterStore[] getGreyWaterOutputs(){
		return myGreyWaterOutputs;
	}
	
	public float[] getGreyWaterOutputMaxFlowRates(){
		return greyWaterOutMaxFlowRates;
	}

	public void setDirtyWaterInputMaxFlowRate(float amount, int index){
		dirtyWaterInMaxFlowRates[index] = amount;
	}

	public float getDirtyWaterInputMaxFlowRate(int index){
		return dirtyWaterInMaxFlowRates[index];
	}

	public void setDirtyWaterInputs(DirtyWaterStore[] sources, float[] maxFlowRates){
		myDirtyWaterInputs = sources;
		dirtyWaterInMaxFlowRates = maxFlowRates;
	}

	public DirtyWaterStore[] getDirtyWaterInputs(){
		return myDirtyWaterInputs;
	}
	
	public float[] getDirtyWaterInputMaxFlowRates(){
		return dirtyWaterInMaxFlowRates;
	}

	public void setDirtyWaterOutputMaxFlowRate(float amount, int index){
		dirtyWaterOutMaxFlowRates[index] = amount;
	}

	public float getDirtyWaterOutputMaxFlowRate(int index){
		return dirtyWaterOutMaxFlowRates[index];
	}

	public void setDirtyWaterOutputs(DirtyWaterStore[] destinations, float[] maxFlowRates){
		myDirtyWaterOutputs = destinations;
		dirtyWaterOutMaxFlowRates = maxFlowRates;
	}

	public DirtyWaterStore[] getDirtyWaterOutputs(){
		return myDirtyWaterOutputs;
	}
	
	public float[] getDirtyWaterOutputMaxFlowRates(){
		return dirtyWaterOutMaxFlowRates;
	}

	public void setPotableWaterInputMaxFlowRate(float amount, int index){
		potableWaterInMaxFlowRates[index] = amount;
	}

	public float getPotableWaterInputMaxFlowRate(int index){
		return potableWaterInMaxFlowRates[index];
	}

	public void setPotableWaterInputs(PotableWaterStore[] sources, float[] maxFlowRates){
		myPotableWaterInputs = sources;
		potableWaterInMaxFlowRates = maxFlowRates;
	}

	public PotableWaterStore[] getPotableWaterInputs(){
		return myPotableWaterInputs;
	}
	
	public float[] getPotableWaterInputMaxFlowRates(){
		return potableWaterInMaxFlowRates;
	}

	public void setPotableWaterOutputMaxFlowRate(float amount, int index){
		potableWaterOutMaxFlowRates[index] = amount;
	}

	public float getPotableWaterOutputMaxFlowRate(int index){
		return potableWaterOutMaxFlowRates[index];
	}

	public void setPotableWaterOutputs(PotableWaterStore[] destinations, float[] maxFlowRates){
		myPotableWaterOutputs = destinations;
		potableWaterOutMaxFlowRates = maxFlowRates;
	}

	public PotableWaterStore[] getPotableWaterOutputs(){
		return myPotableWaterOutputs;
	}
	
	public float[] getPotableWaterOutputMaxFlowRates(){
		return potableWaterOutMaxFlowRates;
	}

	public void setFoodInputMaxFlowRate(float amount, int index){
		foodInMaxFlowRates[index] = amount;
	}

	public float getFoodInputMaxFlowRate(int index){
		return foodInMaxFlowRates[index];
	}

	public void setFoodInputs(FoodStore[] sources, float[] maxFlowRates){
		myFoodInputs = sources;
		foodInMaxFlowRates = maxFlowRates;
	}

	public FoodStore[] getFoodInputs(){
		return myFoodInputs;
	}
	
	public float[] getFoodInMaxFlowRates(){
		return foodInMaxFlowRates;
	}

	public void setFoodOutputMaxFlowRate(float amount, int index){
		foodOutMaxFlowRates[index] = amount;
	}

	public float getFoodOutputMaxFlowRate(int index){
		return foodOutMaxFlowRates[index];
	}

	public void setFoodOutputs(FoodStore[] destinations, float[] maxFlowRates){
		myFoodOutputs = destinations;
		foodOutMaxFlowRates = maxFlowRates;
	}

	public FoodStore[] getFoodOutputs(){
		return myFoodOutputs;
	}
	
	public float[] getFoodOutputMaxFlowRates(){
		return foodOutMaxFlowRates;
	}
	
	public void setBiomassInputMaxFlowRate(float amount, int index){
		biomassInMaxFlowRates[index] = amount;
	}

	public float getBiomassInputMaxFlowRate(int index){
		return biomassInMaxFlowRates[index];
	}

	public void setBiomassInputs(BiomassStore[] sources, float[] maxFlowRates){
		myBiomassInputs = sources;
		biomassInMaxFlowRates = maxFlowRates;
	}

	public BiomassStore[] getBiomassInputs(){
		return myBiomassInputs;
	}
	
	public float[] getBiomassInputMaxFlowRates(){
		return biomassInMaxFlowRates;
	}

	public void setBiomassOutputMaxFlowRate(float amount, int index){
		biomassOutMaxFlowRates[index] = amount;
	}

	public float getBiomassOutputMaxFlowRate(int index){
		return biomassOutMaxFlowRates[index];
	}

	public void setBiomassOutputs(BiomassStore[] destinations, float[] maxFlowRates){
		myBiomassOutputs = destinations;
		biomassOutMaxFlowRates = maxFlowRates;
	}

	public BiomassStore[] getBiomassOutputs(){
		return myBiomassOutputs;
	}
	
	public float[] getBiomassOutputMaxFlowRates(){
		return biomassOutMaxFlowRates;
	}

	public void setAirInputMaxFlowRate(float amount, int index){
		airInMaxFlowRates[index] = amount;
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

	public void setAirOutputMaxFlowRate(float amount, int index){
		airOutMaxFlowRates[index] = amount;
	}

	public float getAirOutputMaxFlowRate(int index){
		return airOutMaxFlowRates[index];
	}

	public void setAirOutputs(SimEnvironment[] destinations, float[] maxFlowRates){
		myAirOutputs = destinations;
		airOutMaxFlowRates = maxFlowRates;
	}

	public SimEnvironment[] getAirOutputs(){
		return myAirOutputs;
	}
	
	public float[] getAirOutputMaxFlowRates(){
		return airOutMaxFlowRates;
	}

	public void setO2InputMaxFlowRate(float amount, int index){
		O2InMaxFlowRates[index] = amount;
	}

	public float getO2InputMaxFlowRate(int index){
		return O2InMaxFlowRates[index];
	}

	public void setO2Inputs(O2Store[] sources, float[] maxFlowRates){
		myO2Inputs = sources;
		O2InMaxFlowRates = maxFlowRates;
	}

	public O2Store[] getO2Inputs(){
		return myO2Inputs;
	}
	
	public float[] getO2InputMaxFlowRates(){
		return O2InMaxFlowRates;
	}

	public void setO2OutputMaxFlowRate(float amount, int index){
		O2OutMaxFlowRates[index] = amount;
	}

	public float getO2OutputMaxFlowRate(int index){
		return O2OutMaxFlowRates[index];
	}

	public void setO2Outputs(O2Store[] destinations, float[] maxFlowRates){
		myO2Outputs = destinations;
		O2OutMaxFlowRates = maxFlowRates;
	}

	public O2Store[] getO2Outputs(){
		return myO2Outputs;
	}
	
	public float[] getO2OutputMaxFlowRates(){
		return O2OutMaxFlowRates;
	}

	public void setCO2InputMaxFlowRate(float amount, int index){
		CO2InMaxFlowRates[index] = amount;
	}

	public float getCO2InputMaxFlowRate(int index){
		return CO2InMaxFlowRates[index];
	}

	public void setCO2Inputs(CO2Store[] sources, float[] maxFlowRates){
		myCO2Inputs = sources;
		CO2InMaxFlowRates = maxFlowRates;
	}
	
	public float[] getCO2InputMaxFlowRates(){
		return CO2InMaxFlowRates;
	}

	public CO2Store[] getCO2Inputs(){
		return myCO2Inputs;
	}

	public void setCO2OutputMaxFlowRate(float amount, int index){
		CO2OutMaxFlowRates[index] = amount;
	}

	public float getCO2OutputMaxFlowRate(int index){
		return CO2OutMaxFlowRates[index];
	}

	public void setCO2Outputs(CO2Store[] destinations, float[] maxFlowRates){
		myCO2Outputs = destinations;
		CO2OutMaxFlowRates = maxFlowRates;
	}

	public CO2Store[] getCO2Outputs(){
		return myCO2Outputs;
	}
	
	public float[] getCO2OutputMaxFlowRates(){
		return CO2OutMaxFlowRates;
	}
	
	//

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

	public void setCO2AirStoreInputs(CO2Store[] sources, float[] maxFlowRates){
		myCO2AirStoreInputs = sources;
		CO2AirStoreInMaxFlowRates = maxFlowRates;
	}
	
	public void setCO2AirEnvironmentInputs(SimEnvironment[] sources, float[] maxFlowRates){
		myCO2AirEnvironmentInputs = sources;
		CO2AirEnvironmentInMaxFlowRates = maxFlowRates;
	}
	
	public float[] getCO2AirStoreInputMaxFlowRates(){
		return CO2AirStoreInMaxFlowRates;
	}
	
	public float[] getCO2AirEnvironmentInputMaxFlowRates(){
		return CO2AirEnvironmentInMaxFlowRates;
	}
	
	public CO2Store[] getCO2AirStoreInputs(){
		return myCO2AirStoreInputs;
	}
	
	public SimEnvironment[] getCO2AirEnvironmentInputs(){
		return myCO2AirEnvironmentInputs;
	}
	
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

	public void setCO2AirStoreOutputs(CO2Store[] destinations, float[] maxFlowRates){
		myCO2AirStoreOutputs = destinations;
		CO2AirStoreOutMaxFlowRates = maxFlowRates;
	}
	
	public void setCO2AirEnvironmentOutputs(SimEnvironment[] destinations, float[] maxFlowRates){
		myCO2AirEnvironmentOutputs = destinations;
		CO2AirEnvironmentOutMaxFlowRates = maxFlowRates;
	}
	
	public float[] getCO2AirStoreOutputMaxFlowRates(){
		return CO2AirStoreOutMaxFlowRates;
	}
	
	public float[] getCO2AirEnvironmentOutputMaxFlowRates(){
		return CO2AirEnvironmentOutMaxFlowRates;
	}
	
	public CO2Store[] getCO2AirStoreOutputs(){
		return myCO2AirStoreOutputs;
	}
	
	public SimEnvironment[] getCO2AirEnvironmentOutputs(){
		return myCO2AirEnvironmentOutputs;
	}
	
	//
	
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

	public void setO2AirStoreInputs(O2Store[] sources, float[] maxFlowRates){
		myO2AirStoreInputs = sources;
		O2AirStoreInMaxFlowRates = maxFlowRates;
	}
	
	public void setO2AirEnvironmentInputs(SimEnvironment[] sources, float[] maxFlowRates){
		myO2AirEnvironmentInputs = sources;
		O2AirEnvironmentInMaxFlowRates = maxFlowRates;
	}
	
	public float[] getO2AirStoreInputMaxFlowRates(){
		return O2AirStoreInMaxFlowRates;
	}
	
	public float[] getO2AirEnvironmentInputMaxFlowRates(){
		return O2AirEnvironmentInMaxFlowRates;
	}
	
	public O2Store[] getO2AirStoreInputs(){
		return myO2AirStoreInputs;
	}
	
	public SimEnvironment[] getO2AirEnvironmentInputs(){
		return myO2AirEnvironmentInputs;
	}
	
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

	public void setO2AirStoreOutputs(O2Store[] destinations, float[] maxFlowRates){
		myO2AirStoreOutputs = destinations;
		O2AirStoreOutMaxFlowRates = maxFlowRates;
	}
	
	public void setO2AirEnvironmentOutputs(SimEnvironment[] destinations, float[] maxFlowRates){
		myO2AirEnvironmentOutputs = destinations;
		O2AirEnvironmentOutMaxFlowRates = maxFlowRates;
	}
	
	public float[] getO2AirStoreOutputMaxFlowRates(){
		return O2AirStoreOutMaxFlowRates;
	}
	
	public float[] getO2AirEnvironmentOutputMaxFlowRates(){
		return O2AirEnvironmentOutMaxFlowRates;
	}
	
	public O2Store[] getO2AirStoreOutputs(){
		return myO2AirStoreOutputs;
	}
	
	public SimEnvironment[] getO2AirEnvironmentOutputs(){
		return myO2AirEnvironmentOutputs;
	}
	
	/**
	* Returns the name of the module, "Unamed" if not overriden
	* @return the name of this module
	*/
	public String getModuleName(){
		return "Injector"+getID();
	}
}
