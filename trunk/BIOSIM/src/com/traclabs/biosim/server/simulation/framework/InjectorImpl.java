package biosim.server.framework;

import biosim.idl.framework.*;
import biosim.idl.food.*;
import biosim.idl.air.*;
import biosim.idl.water.*;
import biosim.idl.environment.*;
import biosim.idl.power.*;
import java.util.*;
import biosim.server.util.*;
import biosim.idl.util.log.*;
/**
 * The basic Injector Implementation.
 * @author    Scott Bell
 */

public class InjectorImpl extends BioModuleImpl implements InjectorOperations, PowerConsumerOperations, PotableWaterConsumerOperations, GreyWaterConsumerOperations, DirtyWaterConsumerOperations, O2ConsumerOperations, CO2ConsumerOperations, AirConsumerOperations, BiomassConsumerOperations, FoodConsumerOperations, PowerProducerOperations, PotableWaterProducerOperations, GreyWaterProducerOperations, DirtyWaterProducerOperations, O2ProducerOperations, CO2ProducerOperations, AirProducerOperations, BiomassProducerOperations, FoodProducerOperations, O2AirConsumer, CO2AirConsumer, OtherAirConsumer, O2AirProducer, CO2AirProducer, OtherAirProducer{
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
	
	private SimEnvironment[] myCO2AirInputs;
	private SimEnvironment[] myCO2AirOutputs;
	private float[] CO2AirOutFlowRates;
	private float[] CO2AirInFlowRates;
	
	private SimEnvironment[] myO2AirInputs;
	private SimEnvironment[] myO2AirOutputs;
	private float[] O2AirOutFlowRates;
	private float[] O2AirInFlowRates;
	
	private SimEnvironment[] myOtherAirInputs;
	private SimEnvironment[] myOtherAirOutputs;
	private float[] otherAirOutFlowRates;
	private float[] otherAirInFlowRates;

	public InjectorImpl(int pID){
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
		
		myCO2AirOutputs = new SimEnvironment[0];
		myCO2AirInputs = new SimEnvironment[0];
		CO2AirOutFlowRates = new float[0];
		CO2AirInFlowRates = new float[0];
		
		myO2AirOutputs = new SimEnvironment[0];
		myO2AirInputs = new SimEnvironment[0];
		O2AirOutFlowRates = new float[0];
		O2AirInFlowRates = new float[0];
		
		myOtherAirOutputs = new SimEnvironment[0];
		myOtherAirInputs = new SimEnvironment[0];
		otherAirOutFlowRates = new float[0];
		otherAirInFlowRates = new float[0];
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


	/**
	* Resets the level to 0
	*/
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

	public void setPowerInputFlowrate(float amount, int index){
		powerInFlowRates[index] = amount;
	}

	public float getPowerInputFlowrate(int index){
		return powerInFlowRates[index];
	}

	public void setPowerInputs(PowerStore[] sources, float[] flowRates){
		myPowerInputs = sources;
		powerInFlowRates = flowRates;
	}

	public PowerStore[] getPowerInputs(){
		return myPowerInputs;
	}
	
	public float[] getPowerInputFlowrates(){
		return powerInFlowRates;
	}

	public void setPowerOutputFlowrate(float amount, int index){
		powerOutFlowRates[index] = amount;
	}

	public float getPowerOutputFlowrate(int index){
		return powerOutFlowRates[index];
	}

	public void setPowerOutputs(PowerStore[] destinations, float[] flowRates){
		myPowerOutputs = destinations;
		powerOutFlowRates = flowRates;
	}

	public PowerStore[] getPowerOutputs(){
		return myPowerOutputs;
	}
	
	public float[] getPowerOutputFlowrates(){
		return powerOutFlowRates;
	}

	public void setGreyWaterInputFlowrate(float amount, int index){
		greyWaterInFlowRates[index] = amount;
	}

	public float getGreyWaterInputFlowrate(int index){
		return greyWaterInFlowRates[index];
	}

	public void setGreyWaterInputs(GreyWaterStore[] sources, float[] flowRates){
		myGreyWaterInputs = sources;
		greyWaterInFlowRates = flowRates;
	}

	public GreyWaterStore[] getGreyWaterInputs(){
		return myGreyWaterInputs;
	}
	
	public float[] getGreyWaterInputFlowrates(){
		return greyWaterInFlowRates;
	}

	public void setGreyWaterOutputFlowrate(float amount, int index){
		greyWaterOutFlowRates[index] = amount;
	}

	public float getGreyWaterOutputFlowrate(int index){
		return greyWaterOutFlowRates[index];
	}

	public void setGreyWaterOutputs(GreyWaterStore[] destinations, float[] flowRates){
		myGreyWaterOutputs = destinations;
		greyWaterOutFlowRates = flowRates;
	}

	public GreyWaterStore[] getGreyWaterOutputs(){
		return myGreyWaterOutputs;
	}
	
	public float[] getGreyWaterOutputFlowrates(){
		return greyWaterOutFlowRates;
	}

	public void setDirtyWaterInputFlowrate(float amount, int index){
		dirtyWaterInFlowRates[index] = amount;
	}

	public float getDirtyWaterInputFlowrate(int index){
		return dirtyWaterInFlowRates[index];
	}

	public void setDirtyWaterInputs(DirtyWaterStore[] sources, float[] flowRates){
		myDirtyWaterInputs = sources;
		dirtyWaterInFlowRates = flowRates;
	}

	public DirtyWaterStore[] getDirtyWaterInputs(){
		return myDirtyWaterInputs;
	}
	
	public float[] getDirtyWaterInputFlowrates(){
		return dirtyWaterInFlowRates;
	}

	public void setDirtyWaterOutputFlowrate(float amount, int index){
		dirtyWaterOutFlowRates[index] = amount;
	}

	public float getDirtyWaterOutputFlowrate(int index){
		return dirtyWaterOutFlowRates[index];
	}

	public void setDirtyWaterOutputs(DirtyWaterStore[] destinations, float[] flowRates){
		myDirtyWaterOutputs = destinations;
		dirtyWaterOutFlowRates = flowRates;
	}

	public DirtyWaterStore[] getDirtyWaterOutputs(){
		return myDirtyWaterOutputs;
	}
	
	public float[] getDirtyWaterOutputFlowrates(){
		return dirtyWaterOutFlowRates;
	}

	public void setPotableWaterInputFlowrate(float amount, int index){
		potableWaterInFlowRates[index] = amount;
	}

	public float getPotableWaterInputFlowrate(int index){
		return potableWaterInFlowRates[index];
	}

	public void setPotableWaterInputs(PotableWaterStore[] sources, float[] flowRates){
		myPotableWaterInputs = sources;
		potableWaterInFlowRates = flowRates;
	}

	public PotableWaterStore[] getPotableWaterInputs(){
		return myPotableWaterInputs;
	}
	
	public float[] getPotableWaterInputFlowrates(){
		return potableWaterInFlowRates;
	}

	public void setPotableWaterOutputFlowrate(float amount, int index){
		potableWaterOutFlowRates[index] = amount;
	}

	public float getPotableWaterOutputFlowrate(int index){
		return potableWaterOutFlowRates[index];
	}

	public void setPotableWaterOutputs(PotableWaterStore[] destinations, float[] flowRates){
		myPotableWaterOutputs = destinations;
		potableWaterOutFlowRates = flowRates;
	}

	public PotableWaterStore[] getPotableWaterOutputs(){
		return myPotableWaterOutputs;
	}
	
	public float[] getPotableWaterOutputFlowrates(){
		return potableWaterOutFlowRates;
	}

	public void setFoodInputFlowrate(float amount, int index){
		foodInFlowRates[index] = amount;
	}

	public float getFoodInputFlowrate(int index){
		return foodInFlowRates[index];
	}

	public void setFoodInputs(FoodStore[] sources, float[] flowRates){
		myFoodInputs = sources;
		foodInFlowRates = flowRates;
	}

	public FoodStore[] getFoodInputs(){
		return myFoodInputs;
	}
	
	public float[] getFoodInFlowrates(){
		return foodInFlowRates;
	}

	public void setFoodOutputFlowrate(float amount, int index){
		foodOutFlowRates[index] = amount;
	}

	public float getFoodOutputFlowrate(int index){
		return foodOutFlowRates[index];
	}

	public void setFoodOutputs(FoodStore[] destinations, float[] flowRates){
		myFoodOutputs = destinations;
		foodOutFlowRates = flowRates;
	}

	public FoodStore[] getFoodOutputs(){
		return myFoodOutputs;
	}
	
	public float[] getFoodOutputFlowrates(){
		return foodOutFlowRates;
	}
	
	public void setBiomassInputFlowrate(float amount, int index){
		biomassInFlowRates[index] = amount;
	}

	public float getBiomassInputFlowrate(int index){
		return biomassInFlowRates[index];
	}

	public void setBiomassInputs(BiomassStore[] sources, float[] flowRates){
		myBiomassInputs = sources;
		biomassInFlowRates = flowRates;
	}

	public BiomassStore[] getBiomassInputs(){
		return myBiomassInputs;
	}
	
	public float[] getBiomassInputFlowrates(){
		return biomassInFlowRates;
	}

	public void setBiomassOutputFlowrate(float amount, int index){
		biomassOutFlowRates[index] = amount;
	}

	public float getBiomassOutputFlowrate(int index){
		return biomassOutFlowRates[index];
	}

	public void setBiomassOutputs(BiomassStore[] destinations, float[] flowRates){
		myBiomassOutputs = destinations;
		biomassOutFlowRates = flowRates;
	}

	public BiomassStore[] getBiomassOutputs(){
		return myBiomassOutputs;
	}
	
	public float[] getBiomassOutputFlowrates(){
		return biomassOutFlowRates;
	}

	public void setAirInputFlowrate(float amount, int index){
		airInFlowRates[index] = amount;
	}

	public float getAirInputFlowrate(int index){
		return airInFlowRates[index];
	}

	public void setAirInputs(SimEnvironment[] sources, float[] flowRates){
		myAirInputs = sources;
		airInFlowRates = flowRates;
	}

	public SimEnvironment[] getAirInputs(){
		return myAirInputs;
	}
	
	public float[] getAirInputFlowrates(){
		return airInFlowRates;
	}

	public void setAirOutputFlowrate(float amount, int index){
		airOutFlowRates[index] = amount;
	}

	public float getAirOutputFlowrate(int index){
		return airOutFlowRates[index];
	}

	public void setAirOutputs(SimEnvironment[] destinations, float[] flowRates){
		myAirOutputs = destinations;
		airOutFlowRates = flowRates;
	}

	public SimEnvironment[] getAirOutputs(){
		return myAirOutputs;
	}
	
	public float[] getAirOutputFlowrates(){
		return airOutFlowRates;
	}

	public void setO2InputFlowrate(float amount, int index){
		O2InFlowRates[index] = amount;
	}

	public float getO2InputFlowrate(int index){
		return O2InFlowRates[index];
	}

	public void setO2Inputs(O2Store[] sources, float[] flowRates){
		myO2Inputs = sources;
		O2InFlowRates = flowRates;
	}

	public O2Store[] getO2Inputs(){
		return myO2Inputs;
	}
	
	public float[] getO2InputFlowrates(){
		return O2InFlowRates;
	}

	public void setO2OutputFlowrate(float amount, int index){
		O2OutFlowRates[index] = amount;
	}

	public float getO2OutputFlowrate(int index){
		return O2OutFlowRates[index];
	}

	public void setO2Outputs(O2Store[] destinations, float[] flowRates){
		myO2Outputs = destinations;
		O2OutFlowRates = flowRates;
	}

	public O2Store[] getO2Outputs(){
		return myO2Outputs;
	}
	
	public float[] getO2OutputFlowrates(){
		return O2OutFlowRates;
	}

	public void setCO2InputFlowrate(float amount, int index){
		CO2InFlowRates[index] = amount;
	}

	public float getCO2InputFlowrate(int index){
		return CO2InFlowRates[index];
	}

	public void setCO2Inputs(CO2Store[] sources, float[] flowRates){
		myCO2Inputs = sources;
		CO2InFlowRates = flowRates;
	}
	
	public float[] getCO2InputFlowrates(){
		return CO2InFlowRates;
	}

	public CO2Store[] getCO2Inputs(){
		return myCO2Inputs;
	}

	public void setCO2OutputFlowrate(float amount, int index){
		CO2OutFlowRates[index] = amount;
	}

	public float getCO2OutputFlowrate(int index){
		return CO2OutFlowRates[index];
	}

	public void setCO2Outputs(CO2Store[] destinations, float[] flowRates){
		myCO2Outputs = destinations;
		CO2OutFlowRates = flowRates;
	}

	public CO2Store[] getCO2Outputs(){
		return myCO2Outputs;
	}
	
	public float[] getCO2OutputFlowrates(){
		return CO2OutFlowRates;
	}

	public void setCO2AirInputFlowrate(float amount, int index){
		CO2AirInFlowRates[index] = amount;
	}

	public float getCO2AirInputFlowrate(int index){
		return CO2AirInFlowRates[index];
	}

	public void setCO2AirInputs(SimEnvironment[] sources, float[] flowRates){
		myCO2AirInputs = sources;
		CO2AirInFlowRates = flowRates;
	}
	
	public float[] getCO2AirInputFlowrates(){
		return CO2AirInFlowRates;
	}

	public SimEnvironment[] getCO2AirInputs(){
		return myCO2AirInputs;
	}

	public void setCO2AirOutputFlowrate(float amount, int index){
		CO2AirOutFlowRates[index] = amount;
	}

	public float getCO2AirOutputFlowrate(int index){
		return CO2AirOutFlowRates[index];
	}

	public void setCO2AirOutputs(SimEnvironment[] destinations, float[] flowRates){
		myCO2AirOutputs = destinations;
		CO2AirOutFlowRates = flowRates;
	}

	public SimEnvironment[] getCO2AirOutputs(){
		return myCO2AirOutputs;
	}
	
	public float[] getCO2AirOutputFlowrates(){
		return CO2AirOutFlowRates;
	}
	
	public void setO2AirInputFlowrate(float amount, int index){
		O2AirInFlowRates[index] = amount;
	}

	public float getO2AirInputFlowrate(int index){
		return O2AirInFlowRates[index];
	}

	public void setO2AirInputs(SimEnvironment[] sources, float[] flowRates){
		myO2AirInputs = sources;
		O2AirInFlowRates = flowRates;
	}
	
	public float[] getO2AirInputFlowrates(){
		return O2AirInFlowRates;
	}

	public SimEnvironment[] getO2AirInputs(){
		return myO2AirInputs;
	}

	public void setO2AirOutputFlowrate(float amount, int index){
		O2AirOutFlowRates[index] = amount;
	}

	public float getO2AirOutputFlowrate(int index){
		return O2AirOutFlowRates[index];
	}

	public void setO2AirOutputs(SimEnvironment[] destinations, float[] flowRates){
		myO2AirOutputs = destinations;
		O2AirOutFlowRates = flowRates;
	}

	public SimEnvironment[] getO2AirOutputs(){
		return myO2AirOutputs;
	}
	
	public float[] getO2AirOutputFlowrates(){
		return O2AirOutFlowRates;
	}
	
	public void setOtherAirInputFlowrate(float amount, int index){
		otherAirInFlowRates[index] = amount;
	}

	public float getOtherAirInputFlowrate(int index){
		return OtherAirInFlowRates[index];
	}

	public void setOtherAirInputs(SimEnvironment[] sources, float[] flowRates){
		myOtherAirInputs = sources;
		otherAirInFlowRates = flowRates;
	}
	
	public float[] getOtherAirInputFlowrates(){
		return OtherAirInFlowRates;
	}

	public SimEnvironment[] getOtherAirInputs(){
		return myOtherAirInputs;
	}

	public void setOtherAirOutputFlowrate(float amount, int index){
		otherAirOutFlowRates[index] = amount;
	}

	public float getOtherAirOutputFlowrate(int index){
		return OtherAirOutFlowRates[index];
	}

	public void setOtherAirOutputs(SimEnvironment[] destinations, float[] flowRates){
		myOtherAirOutputs = destinations;
		otherAirOutFlowRates = flowRates;
	}

	public SimEnvironment[] getOtherAirOutputs(){
		return myOtherAirOutputs;
	}
	
	public float[] getOtherAirOutputFlowrates(){
		return otherAirOutFlowRates;
	}
	
	/**
	* Returns the name of the module, "Unamed" if not overriden
	* @return the name of this module
	*/
	public String getModuleName(){
		return "Injector"+getID();
	}
}
