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
 * It takes as much as it can (max taken set by maxFlowRates) from one module and pushes it into another module.
 * Functionally equivalent to an Accumulator at this point. 
 * @author    Scott Bell
 */

public class AccumulatorImpl extends SimBioModuleImpl implements AccumulatorOperations, PowerConsumerOperations, PotableWaterConsumerOperations, GreyWaterConsumerOperations, DirtyWaterConsumerOperations, O2ConsumerOperations, CO2ConsumerOperations, AirConsumerOperations, BiomassConsumerOperations, FoodConsumerOperations, PowerProducerOperations, PotableWaterProducerOperations, GreyWaterProducerOperations, DirtyWaterProducerOperations, O2ProducerOperations, CO2ProducerOperations, AirProducerOperations, BiomassProducerOperations, FoodProducerOperations, O2AirConsumerOperations, CO2AirConsumerOperations, O2AirProducerOperations, CO2AirProducerOperations{
	private LogIndex myLogIndex;

	private PowerStore[] myPowerInputs;
	private PowerStore[] myPowerOutputs;
	private float[] powerOutMaxFlowRates;
	private float[] powerInMaxFlowRates;
	private float[] powerOutActualFlowRates;
	private float[] powerInActualFlowRates;
	private float[] powerOutDesiredFlowRates;
	private float[] powerInDesiredFlowRates;

	private GreyWaterStore[] myGreyWaterInputs;
	private GreyWaterStore[] myGreyWaterOutputs;
	private float[] greyWaterOutMaxFlowRates;
	private float[] greyWaterInMaxFlowRates;
	private float[] greyWaterOutActualFlowRates;
	private float[] greyWaterInActualFlowRates;
	private float[] greyWaterOutDesiredFlowRates;
	private float[] greyWaterInDesiredFlowRates;

	private DirtyWaterStore[] myDirtyWaterInputs;
	private DirtyWaterStore[] myDirtyWaterOutputs;
	private float[] dirtyWaterOutMaxFlowRates;
	private float[] dirtyWaterInMaxFlowRates;
	private float[] dirtyWaterOutActualFlowRates;
	private float[] dirtyWaterInActualFlowRates;
	private float[] dirtyWaterOutDesiredFlowRates;
	private float[] dirtyWaterInDesiredFlowRates;

	private PotableWaterStore[] myPotableWaterInputs;
	private PotableWaterStore[] myPotableWaterOutputs;
	private float[] potableWaterOutMaxFlowRates;
	private float[] potableWaterInMaxFlowRates;
	private float[] potableWaterOutActualFlowRates;
	private float[] potableWaterInActualFlowRates;
	private float[] potableWaterOutDesiredFlowRates;
	private float[] potableWaterInDesiredFlowRates;

	private BiomassStore[] myBiomassInputs;
	private BiomassStore[] myBiomassOutputs;
	private float[] biomassOutMaxFlowRates;
	private float[] biomassInMaxFlowRates;
	private float[] biomassOutActualFlowRates;
	private float[] biomassInActualFlowRates;
	private float[] biomassOutDesiredFlowRates;
	private float[] biomassInDesiredFlowRates;

	private FoodStore[] myFoodInputs;
	private FoodStore[] myFoodOutputs;
	private float[] foodOutMaxFlowRates;
	private float[] foodInMaxFlowRates;
	private float[] foodOutActualFlowRates;
	private float[] foodInActualFlowRates;
	private float[] foodOutDesiredFlowRates;
	private float[] foodInDesiredFlowRates;

	private SimEnvironment[] myAirInputs;
	private SimEnvironment[] myAirOutputs;
	private float[] airOutMaxFlowRates;
	private float[] airInMaxFlowRates;
	private float[] airOutActualFlowRates;
	private float[] airInActualFlowRates;
	private float[] airOutDesiredFlowRates;
	private float[] airInDesiredFlowRates;

	private O2Store[] myO2Inputs;
	private O2Store[] myO2Outputs;
	private float[] O2OutMaxFlowRates;
	private float[] O2InMaxFlowRates;
	private float[] O2OutActualFlowRates;
	private float[] O2InActualFlowRates;
	private float[] O2OutDesiredFlowRates;
	private float[] O2InDesiredFlowRates;
	
	private NitrogenStore[] myNitrogenInputs;
	private NitrogenStore[] myNitrogenOutputs;
	private float[] nitrogenOutMaxFlowRates;
	private float[] nitrogenInMaxFlowRates;
	private float[] nitrogenOutActualFlowRates;
	private float[] nitrogenInActualFlowRates;
	private float[] nitrogenOutDesiredFlowRates;
	private float[] nitrogenInDesiredFlowRates;

	private CO2Store[] myCO2Inputs;
	private CO2Store[] myCO2Outputs;
	private float[] CO2OutMaxFlowRates;
	private float[] CO2InMaxFlowRates;
	private float[] CO2OutActualFlowRates;
	private float[] CO2InActualFlowRates;
	private float[] CO2OutDesiredFlowRates;
	private float[] CO2InDesiredFlowRates;

	private CO2Store[] myCO2AirStoreInputs;
	private CO2Store[] myCO2AirStoreOutputs;
	private float[] CO2AirStoreOutMaxFlowRates;
	private float[] CO2AirStoreInMaxFlowRates;
	private float[] CO2AirStoreOutActualFlowRates;
	private float[] CO2AirStoreInActualFlowRates;
	private float[] CO2AirStoreOutDesiredFlowRates;
	private float[] CO2AirStoreInDesiredFlowRates;

	private O2Store[] myO2AirStoreInputs;
	private O2Store[] myO2AirStoreOutputs;
	private float[] O2AirStoreOutMaxFlowRates;
	private float[] O2AirStoreInMaxFlowRates;
	private float[] O2AirStoreOutActualFlowRates;
	private float[] O2AirStoreInActualFlowRates;
	private float[] O2AirStoreOutDesiredFlowRates;
	private float[] O2AirStoreInDesiredFlowRates;
	
	private NitrogenStore[] myNitrogenAirStoreInputs;
	private NitrogenStore[] myNitrogenAirStoreOutputs;
	private float[] nitrogenAirStoreOutMaxFlowRates;
	private float[] nitrogenAirStoreInMaxFlowRates;
	private float[] nitrogenAirStoreOutActualFlowRates;
	private float[] nitrogenAirStoreInActualFlowRates;
	private float[] nitrogenAirStoreOutDesiredFlowRates;
	private float[] nitrogenAirStoreInDesiredFlowRates;
	
	private PotableWaterStore[] myWaterAirStoreInputs;
	private PotableWaterStore[] myWaterAirStoreOutputs;
	private float[] waterAirStoreOutMaxFlowRates;
	private float[] waterAirStoreInMaxFlowRates;
	private float[] waterAirStoreOutActualFlowRates;
	private float[] waterAirStoreInActualFlowRates;
	private float[] waterAirStoreOutDesiredFlowRates;
	private float[] waterAirStoreInDesiredFlowRates;

	private SimEnvironment[] myCO2AirEnvironmentInputs;
	private SimEnvironment[] myCO2AirEnvironmentOutputs;
	private float[] CO2AirEnvironmentOutMaxFlowRates;
	private float[] CO2AirEnvironmentInMaxFlowRates;
	private float[] CO2AirEnvironmentOutActualFlowRates;
	private float[] CO2AirEnvironmentInActualFlowRates;
	private float[] CO2AirEnvironmentOutDesiredFlowRates;
	private float[] CO2AirEnvironmentInDesiredFlowRates;

	private SimEnvironment[] myO2AirEnvironmentInputs;
	private SimEnvironment[] myO2AirEnvironmentOutputs;
	private float[] O2AirEnvironmentOutMaxFlowRates;
	private float[] O2AirEnvironmentInMaxFlowRates;
	private float[] O2AirEnvironmentOutActualFlowRates;
	private float[] O2AirEnvironmentInActualFlowRates;
	private float[] O2AirEnvironmentOutDesiredFlowRates;
	private float[] O2AirEnvironmentInDesiredFlowRates;

	private SimEnvironment[] myNitrogenAirEnvironmentInputs;
	private SimEnvironment[] myNitrogenAirEnvironmentOutputs;
	private float[] nitrogenAirEnvironmentOutMaxFlowRates;
	private float[] nitrogenAirEnvironmentInMaxFlowRates;
	private float[] nitrogenAirEnvironmentOutActualFlowRates;
	private float[] nitrogenAirEnvironmentInActualFlowRates;
	private float[] nitrogenAirEnvironmentOutDesiredFlowRates;
	private float[] nitrogenAirEnvironmentInDesiredFlowRates;

	private SimEnvironment[] myWaterAirEnvironmentInputs;
	private SimEnvironment[] myWaterAirEnvironmentOutputs;
	private float[] waterAirEnvironmentOutMaxFlowRates;
	private float[] waterAirEnvironmentInMaxFlowRates;
	private float[] waterAirEnvironmentOutActualFlowRates;
	private float[] waterAirEnvironmentInActualFlowRates;
	private float[] waterAirEnvironmentOutDesiredFlowRates;
	private float[] waterAirEnvironmentInDesiredFlowRates;

	public AccumulatorImpl(int pID, String pName){
		super(pID, pName);
		myPowerOutputs = new PowerStore[0];
		myPowerInputs = new PowerStore[0];
		powerOutMaxFlowRates = new float[0];
		powerInMaxFlowRates = new float[0];
		powerOutActualFlowRates = new float[0];
		powerInActualFlowRates = new float[0];
		powerOutDesiredFlowRates = new float[0];
		powerInDesiredFlowRates = new float[0];

		myGreyWaterOutputs = new GreyWaterStore[0];
		myGreyWaterInputs = new GreyWaterStore[0];
		greyWaterOutMaxFlowRates = new float[0];
		greyWaterInMaxFlowRates = new float[0];
		greyWaterOutActualFlowRates = new float[0];
		greyWaterInActualFlowRates = new float[0];
		greyWaterOutDesiredFlowRates = new float[0];
		greyWaterInDesiredFlowRates = new float[0];

		myDirtyWaterOutputs = new DirtyWaterStore[0];
		myDirtyWaterInputs = new DirtyWaterStore[0];
		dirtyWaterOutMaxFlowRates = new float[0];
		dirtyWaterInMaxFlowRates = new float[0];
		dirtyWaterOutActualFlowRates = new float[0];
		dirtyWaterInActualFlowRates = new float[0];
		dirtyWaterOutDesiredFlowRates = new float[0];
		dirtyWaterInDesiredFlowRates = new float[0];

		myPotableWaterOutputs = new PotableWaterStore[0];
		myPotableWaterInputs = new PotableWaterStore[0];
		potableWaterOutMaxFlowRates = new float[0];
		potableWaterInMaxFlowRates = new float[0];
		potableWaterOutActualFlowRates = new float[0];
		potableWaterInActualFlowRates = new float[0];
		potableWaterOutDesiredFlowRates = new float[0];
		potableWaterInDesiredFlowRates = new float[0];

		myBiomassOutputs = new BiomassStore[0];
		myBiomassInputs = new BiomassStore[0];
		biomassOutMaxFlowRates = new float[0];
		biomassInMaxFlowRates = new float[0];
		biomassOutActualFlowRates = new float[0];
		biomassInActualFlowRates = new float[0];
		biomassOutDesiredFlowRates = new float[0];
		biomassInDesiredFlowRates = new float[0];

		myFoodOutputs = new FoodStore[0];
		myFoodInputs = new FoodStore[0];
		foodOutMaxFlowRates = new float[0];
		foodInMaxFlowRates = new float[0];
		foodOutActualFlowRates = new float[0];
		foodInActualFlowRates = new float[0];
		foodOutDesiredFlowRates = new float[0];
		foodInDesiredFlowRates = new float[0];

		myAirOutputs = new SimEnvironment[0];
		myAirInputs = new SimEnvironment[0];
		airOutMaxFlowRates = new float[0];
		airInMaxFlowRates = new float[0];
		airOutActualFlowRates = new float[0];
		airInActualFlowRates = new float[0];
		airOutDesiredFlowRates = new float[0];
		airInDesiredFlowRates = new float[0];

		myO2Outputs = new O2Store[0];
		myO2Inputs = new O2Store[0];
		O2OutMaxFlowRates = new float[0];
		O2InMaxFlowRates = new float[0];
		O2OutActualFlowRates = new float[0];
		O2InActualFlowRates = new float[0];
		O2OutDesiredFlowRates = new float[0];
		O2InDesiredFlowRates = new float[0];

		myCO2Outputs = new CO2Store[0];
		myCO2Inputs = new CO2Store[0];
		CO2OutMaxFlowRates = new float[0];
		CO2InMaxFlowRates = new float[0];
		CO2OutActualFlowRates = new float[0];
		CO2InActualFlowRates = new float[0];
		CO2OutDesiredFlowRates = new float[0];
		CO2InDesiredFlowRates = new float[0];
		
		myNitrogenOutputs = new NitrogenStore[0];
		myNitrogenInputs = new NitrogenStore[0];
		nitrogenOutMaxFlowRates = new float[0];
		nitrogenInMaxFlowRates = new float[0];
		nitrogenOutActualFlowRates = new float[0];
		nitrogenInActualFlowRates = new float[0];
		nitrogenOutDesiredFlowRates = new float[0];
		nitrogenInDesiredFlowRates = new float[0];

		myCO2AirStoreOutputs = new CO2Store[0];
		myCO2AirStoreInputs = new CO2Store[0];
		CO2AirStoreOutMaxFlowRates = new float[0];
		CO2AirStoreInMaxFlowRates = new float[0];
		CO2AirStoreOutActualFlowRates = new float[0];
		CO2AirStoreInActualFlowRates = new float[0];
		CO2AirStoreOutDesiredFlowRates = new float[0];
		CO2AirStoreInDesiredFlowRates = new float[0];

		myCO2AirEnvironmentOutputs = new SimEnvironment[0];
		myCO2AirEnvironmentInputs = new SimEnvironment[0];
		CO2AirEnvironmentOutMaxFlowRates = new float[0];
		CO2AirEnvironmentInMaxFlowRates = new float[0];
		CO2AirEnvironmentOutActualFlowRates = new float[0];
		CO2AirEnvironmentInActualFlowRates = new float[0];
		CO2AirEnvironmentOutDesiredFlowRates = new float[0];
		CO2AirEnvironmentInDesiredFlowRates = new float[0];

		myO2AirStoreOutputs = new O2Store[0];
		myO2AirStoreInputs = new O2Store[0];
		O2AirStoreOutMaxFlowRates = new float[0];
		O2AirStoreInMaxFlowRates = new float[0];
		O2AirStoreOutActualFlowRates = new float[0];
		O2AirStoreInActualFlowRates = new float[0];
		O2AirStoreOutDesiredFlowRates = new float[0];
		O2AirStoreInDesiredFlowRates = new float[0];

		myO2AirEnvironmentOutputs = new SimEnvironment[0];
		myO2AirEnvironmentInputs = new SimEnvironment[0];
		O2AirEnvironmentOutMaxFlowRates = new float[0];
		O2AirEnvironmentInMaxFlowRates = new float[0];
		O2AirEnvironmentOutActualFlowRates = new float[0];
		O2AirEnvironmentInActualFlowRates = new float[0];
		O2AirEnvironmentOutDesiredFlowRates = new float[0];
		O2AirEnvironmentInDesiredFlowRates = new float[0];
		
		myNitrogenAirStoreOutputs = new NitrogenStore[0];
		myNitrogenAirStoreInputs = new NitrogenStore[0];
		nitrogenAirStoreOutMaxFlowRates = new float[0];
		nitrogenAirStoreInMaxFlowRates = new float[0];
		nitrogenAirStoreOutActualFlowRates = new float[0];
		nitrogenAirStoreInActualFlowRates = new float[0];
		nitrogenAirStoreOutDesiredFlowRates = new float[0];
		nitrogenAirStoreInDesiredFlowRates = new float[0];

		myNitrogenAirEnvironmentOutputs = new SimEnvironment[0];
		myNitrogenAirEnvironmentInputs = new SimEnvironment[0];
		nitrogenAirEnvironmentOutMaxFlowRates = new float[0];
		nitrogenAirEnvironmentInMaxFlowRates = new float[0];
		nitrogenAirEnvironmentOutActualFlowRates = new float[0];
		nitrogenAirEnvironmentInActualFlowRates = new float[0];
		nitrogenAirEnvironmentOutDesiredFlowRates = new float[0];
		nitrogenAirEnvironmentInDesiredFlowRates = new float[0];
		
		myWaterAirStoreOutputs = new PotableWaterStore[0];
		myWaterAirStoreInputs = new PotableWaterStore[0];
		waterAirStoreOutMaxFlowRates = new float[0];
		waterAirStoreInMaxFlowRates = new float[0];
		waterAirStoreOutActualFlowRates = new float[0];
		waterAirStoreInActualFlowRates = new float[0];
		waterAirStoreOutDesiredFlowRates = new float[0];
		waterAirStoreInDesiredFlowRates = new float[0];

		myWaterAirEnvironmentOutputs = new SimEnvironment[0];
		myWaterAirEnvironmentInputs = new SimEnvironment[0];
		waterAirEnvironmentOutMaxFlowRates = new float[0];
		waterAirEnvironmentInMaxFlowRates = new float[0];
		waterAirEnvironmentOutActualFlowRates = new float[0];
		waterAirEnvironmentInActualFlowRates = new float[0];
		waterAirEnvironmentOutDesiredFlowRates = new float[0];
		waterAirEnvironmentInDesiredFlowRates = new float[0];
	}


	public void tick(){
		super.tick();
		getAndPushResources();
	}

	private void getAndPushResources(){
		float powerGathered = getMostResourceFromStore(myPowerInputs, powerInMaxFlowRates, powerInDesiredFlowRates, powerInActualFlowRates);
		float powerPushed = pushResourceToStore(myPowerOutputs, powerOutMaxFlowRates, powerOutDesiredFlowRates, powerOutActualFlowRates, powerGathered);

		float greyWaterGathered = getMostResourceFromStore(myGreyWaterInputs, greyWaterInMaxFlowRates, greyWaterInDesiredFlowRates, greyWaterInActualFlowRates);
		float greyWaterPushed = pushResourceToStore(myGreyWaterOutputs, greyWaterOutMaxFlowRates, greyWaterOutDesiredFlowRates, greyWaterOutActualFlowRates, greyWaterGathered);

		float potableWaterGathered = getMostResourceFromStore(myPotableWaterInputs, potableWaterInMaxFlowRates, potableWaterInDesiredFlowRates, potableWaterInActualFlowRates);
		float potableWaterPushed = pushResourceToStore(myPotableWaterOutputs, potableWaterOutMaxFlowRates, potableWaterOutDesiredFlowRates, potableWaterOutActualFlowRates, potableWaterGathered);

		float dirtyWaterGathered = getMostResourceFromStore(myDirtyWaterInputs, dirtyWaterInMaxFlowRates, dirtyWaterInDesiredFlowRates, dirtyWaterInActualFlowRates);
		float dirtyWaterPushed = pushResourceToStore(myDirtyWaterOutputs, dirtyWaterOutMaxFlowRates, dirtyWaterOutDesiredFlowRates, dirtyWaterOutActualFlowRates, dirtyWaterGathered);

		float biomassGathered = getMostResourceFromStore(myBiomassInputs, biomassInMaxFlowRates, biomassInDesiredFlowRates, biomassInActualFlowRates);
		float biomassPushed = pushResourceToStore(myBiomassOutputs, biomassOutMaxFlowRates, biomassOutDesiredFlowRates, biomassOutActualFlowRates, biomassGathered);

		float foodGathered = getMostResourceFromStore(myFoodInputs, foodInMaxFlowRates, foodInDesiredFlowRates, foodInActualFlowRates);
		float foodPushed = pushResourceToStore(myFoodOutputs, foodOutMaxFlowRates, foodOutDesiredFlowRates, foodOutActualFlowRates, foodGathered);

		float O2Gathered = getMostResourceFromStore(myO2Inputs, O2InMaxFlowRates, O2InDesiredFlowRates, O2InActualFlowRates);
		float O2Pushed = pushResourceToStore(myO2Outputs, O2OutMaxFlowRates, O2OutDesiredFlowRates, O2OutActualFlowRates, O2Gathered);

		float CO2Gathered = getMostResourceFromStore(myCO2Inputs, CO2InMaxFlowRates, CO2InDesiredFlowRates, CO2InActualFlowRates);
		float CO2Pushed = pushResourceToStore(myCO2Outputs, CO2OutMaxFlowRates, CO2OutDesiredFlowRates, CO2OutActualFlowRates, CO2Gathered);
		
		float nitrogenGathered = getMostResourceFromStore(myNitrogenInputs, nitrogenInMaxFlowRates, nitrogenInDesiredFlowRates, nitrogenInActualFlowRates);
		float nitrogenPushed = pushResourceToStore(myNitrogenOutputs, nitrogenOutMaxFlowRates, nitrogenOutDesiredFlowRates, nitrogenOutActualFlowRates, nitrogenGathered);

		Breath gatheredAir = new Breath(0f, 0f, 0f, 0f, 0f);
		for (int i = 0; (i < myAirInputs.length); i++){
			float amountToTake = Math.min(airInMaxFlowRates[i], airInDesiredFlowRates[i]);
			Breath currentBreath = myAirInputs[i].takeAirMoles(amountToTake);
			gatheredAir.O2 += currentBreath.O2;
			gatheredAir.CO2 += currentBreath.CO2;
			gatheredAir.other += currentBreath.other;
			airInActualFlowRates[i] = currentBreath.O2 + currentBreath.CO2 + currentBreath.other + currentBreath.nitrogen;
		}

		Breath distributedAir = new Breath(gatheredAir.O2, gatheredAir.CO2, gatheredAir.water, gatheredAir.other, gatheredAir.nitrogen);
		for (int i = 0; (i < myAirOutputs.length) && ((distributedAir.O2 + distributedAir.CO2 + distributedAir.other + distributedAir.nitrogen) > 0); i++){
			Breath breathAdded = myAirInputs[i].addBreath(distributedAir);
			distributedAir.O2 -= breathAdded.O2;
			distributedAir.CO2 -= breathAdded.CO2;
			distributedAir.other -= breathAdded.other;
			distributedAir.nitrogen -= breathAdded.nitrogen;
			airOutActualFlowRates[i] = breathAdded.O2 + breathAdded.CO2 + breathAdded.other + breathAdded.nitrogen;
		}

		//Get CO2 from stores/environment
		float gatheredCO2Air = getMostResourceFromStore(myCO2AirStoreInputs, CO2AirStoreInMaxFlowRates, CO2AirStoreInDesiredFlowRates, CO2AirStoreInActualFlowRates);
		for (int i = 0; i < myCO2AirEnvironmentInputs.length; i++){
			float amountToTake = Math.min(CO2AirEnvironmentInMaxFlowRates[i], CO2AirEnvironmentInDesiredFlowRates[i]);
			CO2AirEnvironmentInActualFlowRates[i] = myCO2AirEnvironmentInputs[i].takeCO2Moles(amountToTake);
			gatheredCO2Air += CO2AirEnvironmentInActualFlowRates[i];
		}
		//Push CO2 to stores/environment
		float CO2AirPushed = pushResourceToStore(myCO2AirStoreOutputs, CO2AirStoreOutMaxFlowRates, CO2AirStoreOutDesiredFlowRates, CO2AirStoreOutActualFlowRates, gatheredCO2Air);
		float CO2AirLeft = gatheredCO2Air - CO2AirPushed;
		for (int i = 0; (i < myCO2AirEnvironmentOutputs.length) && (CO2AirLeft > 0); i++){
			float amountToPush = Math.min(CO2AirEnvironmentOutMaxFlowRates[i], CO2AirEnvironmentOutDesiredFlowRates[i]);
			CO2AirEnvironmentOutActualFlowRates[i] = myCO2AirEnvironmentOutputs[i].addCO2Moles(amountToPush);
			CO2AirLeft -= CO2AirEnvironmentOutActualFlowRates[i];
		}
		CO2AirPushed = gatheredCO2Air - CO2AirLeft;

		//Get O2 from stores/environment
		float gatheredO2Air = getMostResourceFromStore(myO2AirStoreInputs, O2AirStoreInMaxFlowRates, O2AirStoreInDesiredFlowRates, O2AirStoreInActualFlowRates);
		for (int i = 0; i < myO2AirEnvironmentInputs.length; i++){
			float amountToTake = Math.min(O2AirEnvironmentInMaxFlowRates[i], O2AirEnvironmentInDesiredFlowRates[i]);
			O2AirEnvironmentInActualFlowRates[i] = myO2AirEnvironmentInputs[i].takeO2Moles(amountToTake);
			gatheredO2Air += O2AirEnvironmentInActualFlowRates[i];
		}
		//Push O2 to stores/environment
		float O2AirPushed = pushResourceToStore(myO2AirStoreOutputs, O2AirStoreOutMaxFlowRates, O2AirStoreOutDesiredFlowRates, O2AirStoreOutActualFlowRates, gatheredO2Air);
		float O2AirLeft = gatheredO2Air - O2AirPushed;
		for (int i = 0; (i < myO2AirEnvironmentOutputs.length) && (O2AirLeft > 0); i++){
			float amountToPush = Math.min(O2AirEnvironmentOutMaxFlowRates[i], O2AirEnvironmentOutDesiredFlowRates[i]);
			O2AirEnvironmentOutActualFlowRates[i] = myO2AirEnvironmentOutputs[i].addO2Moles(amountToPush);
			O2AirLeft -= O2AirEnvironmentOutActualFlowRates[i];
		}
		O2AirPushed = gatheredO2Air - O2AirLeft;
		
		//Get nitrogen from stores/environment
		float gatheredNitrogenAir = getMostResourceFromStore(myNitrogenAirStoreInputs, nitrogenAirStoreInMaxFlowRates, nitrogenAirStoreInDesiredFlowRates, nitrogenAirStoreInActualFlowRates);
		for (int i = 0; i < myNitrogenAirEnvironmentInputs.length; i++){
			float amountToTake = Math.min(nitrogenAirEnvironmentInMaxFlowRates[i], nitrogenAirEnvironmentInDesiredFlowRates[i]);
			nitrogenAirEnvironmentInActualFlowRates[i] = myNitrogenAirEnvironmentInputs[i].takeNitrogenMoles(amountToTake);
			gatheredNitrogenAir += nitrogenAirEnvironmentInActualFlowRates[i];
		}
		//Push nitrogen to stores/environment
		float nitrogenAirPushed = pushResourceToStore(myNitrogenAirStoreOutputs, nitrogenAirStoreOutMaxFlowRates, nitrogenAirStoreOutDesiredFlowRates, nitrogenAirStoreOutActualFlowRates, gatheredNitrogenAir);
		float nitrogenAirLeft = gatheredNitrogenAir - nitrogenAirPushed;
		for (int i = 0; (i < myNitrogenAirEnvironmentOutputs.length) && (nitrogenAirLeft > 0); i++){
			float amountToPush = Math.min(nitrogenAirEnvironmentOutMaxFlowRates[i], nitrogenAirEnvironmentOutDesiredFlowRates[i]);
			nitrogenAirEnvironmentOutActualFlowRates[i] = myNitrogenAirEnvironmentOutputs[i].addNitrogenMoles(amountToPush);
			nitrogenAirLeft -= nitrogenAirEnvironmentOutActualFlowRates[i];
		}
		nitrogenAirPushed = gatheredNitrogenAir - nitrogenAirLeft;
		
		//Get water from stores/environment
		float gatheredWaterAirMoles = getMostResourceFromStore(myWaterAirStoreInputs, waterAirStoreInMaxFlowRates, waterAirStoreInDesiredFlowRates, waterAirStoreInActualFlowRates);
		for (int i = 0; i < myWaterAirEnvironmentInputs.length; i++){
			float amountToTake = Math.min(waterAirEnvironmentInMaxFlowRates[i], waterAirEnvironmentInDesiredFlowRates[i]);
			waterAirEnvironmentInActualFlowRates[i] = myWaterAirEnvironmentInputs[i].takeWaterMoles(amountToTake);
			//System.out.println("Taking "+waterAirEnvironmentInActualFlowRates[i]+" moles of water from "+myWaterAirEnvironmentInputs[i].getModuleName()+" ("+(i+1)+" of "+myWaterAirEnvironmentInputs.length+")");
			gatheredWaterAirMoles += waterAirEnvironmentInActualFlowRates[i];
		}
		//Convert to liters (for water store)
		float gatheredWaterAirLiters = waterMolesToLiters(gatheredWaterAirMoles);
		//Push water to stores/environment
		float waterAirPushedLiters = pushResourceToStore(myWaterAirStoreOutputs, waterAirStoreOutMaxFlowRates, waterAirStoreOutDesiredFlowRates, waterAirStoreOutActualFlowRates, gatheredWaterAirMoles);
		
		float waterAirLeft = gatheredWaterAirMoles - waterLitersToMoles(waterAirPushedLiters);
		for (int i = 0; (i < myWaterAirEnvironmentOutputs.length) && (waterAirLeft > 0); i++){
			float amountToPush = Math.min(waterAirEnvironmentOutMaxFlowRates[i], waterAirEnvironmentOutDesiredFlowRates[i]);
			waterAirEnvironmentOutActualFlowRates[i] = myWaterAirEnvironmentOutputs[i].addWaterMoles(amountToPush);
			waterAirLeft -= waterAirEnvironmentOutActualFlowRates[i];
		}
		float waterAirPushedMoles = gatheredWaterAirMoles - waterAirLeft;
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
	public void setPowerInputDesiredFlowRate(float amount, int index){
		powerInDesiredFlowRates[index] = amount;
	}
	public float getPowerInputDesiredFlowRate(int index){
		return powerInDesiredFlowRates[index];
	}
	public float[] getPowerInputDesiredFlowRates(){
		return powerInDesiredFlowRates;
	}
	public float getPowerInputActualFlowRate(int index){
		return powerInActualFlowRates[index];
	}
	public float[] getPowerInputActualFlowRates(){
		return powerInActualFlowRates;
	}
	public void setPowerInputs(PowerStore[] sources, float[] maxFlowRates, float[] desiredFlowRates){
		myPowerInputs = sources;
		powerInMaxFlowRates = maxFlowRates;
		powerInDesiredFlowRates = desiredFlowRates;
		powerInActualFlowRates = new float[powerInDesiredFlowRates.length];
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
	public void setPowerOutputDesiredFlowRate(float amount, int index){
		powerOutDesiredFlowRates[index] = amount;
	}
	public float getPowerOutputDesiredFlowRate(int index){
		return powerOutDesiredFlowRates[index];
	}
	public float[] getPowerOutputDesiredFlowRates(){
		return powerOutDesiredFlowRates;
	}
	public float getPowerOutputActualFlowRate(int index){
		return powerOutActualFlowRates[index];
	}
	public float[] getPowerOutputActualFlowRates(){
		return powerOutActualFlowRates;
	}
	public void setPowerOutputs(PowerStore[] destinations, float[] maxFlowRates, float[] desiredFlowRates){
		myPowerOutputs = destinations;
		powerOutMaxFlowRates = maxFlowRates;
		powerOutDesiredFlowRates = desiredFlowRates;
		powerOutActualFlowRates = new float[powerOutDesiredFlowRates.length];
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
	public void setGreyWaterInputDesiredFlowRate(float amount, int index){
		greyWaterInDesiredFlowRates[index] = amount;
	}
	public float getGreyWaterInputDesiredFlowRate(int index){
		return greyWaterInDesiredFlowRates[index];
	}
	public float[] getGreyWaterInputDesiredFlowRates(){
		return greyWaterInDesiredFlowRates;
	}
	public float getGreyWaterInputActualFlowRate(int index){
		return greyWaterInActualFlowRates[index];
	}
	public float[] getGreyWaterInputActualFlowRates(){
		return greyWaterInActualFlowRates;
	}
	public void setGreyWaterInputs(GreyWaterStore[] sources, float[] maxFlowRates, float[] desiredFlowRates){
		myGreyWaterInputs = sources;
		greyWaterInMaxFlowRates = maxFlowRates;
		greyWaterInDesiredFlowRates = desiredFlowRates;
		greyWaterInActualFlowRates = new float[greyWaterInDesiredFlowRates.length];
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
	public void setGreyWaterOutputDesiredFlowRate(float amount, int index){
		greyWaterOutDesiredFlowRates[index] = amount;
	}
	public float getGreyWaterOutputDesiredFlowRate(int index){
		return greyWaterOutDesiredFlowRates[index];
	}
	public float[] getGreyWaterOutputDesiredFlowRates(){
		return greyWaterOutDesiredFlowRates;
	}
	public float getGreyWaterOutputActualFlowRate(int index){
		return greyWaterOutActualFlowRates[index];
	}
	public float[] getGreyWaterOutputActualFlowRates(){
		return greyWaterOutActualFlowRates;
	}
	public void setGreyWaterOutputs(GreyWaterStore[] destinations, float[] maxFlowRates, float[] desiredFlowRates){
		myGreyWaterOutputs = destinations;
		greyWaterOutMaxFlowRates = maxFlowRates;
		greyWaterOutDesiredFlowRates = desiredFlowRates;
		greyWaterOutActualFlowRates = new float[greyWaterOutDesiredFlowRates.length];
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
	public void setDirtyWaterInputDesiredFlowRate(float amount, int index){
		dirtyWaterInDesiredFlowRates[index] = amount;
	}
	public float getDirtyWaterInputDesiredFlowRate(int index){
		return dirtyWaterInDesiredFlowRates[index];
	}
	public float[] getDirtyWaterInputDesiredFlowRates(){
		return dirtyWaterInDesiredFlowRates;
	}
	public float getDirtyWaterInputActualFlowRate(int index){
		return dirtyWaterInActualFlowRates[index];
	}
	public float[] getDirtyWaterInputActualFlowRates(){
		return dirtyWaterInActualFlowRates;
	}
	public void setDirtyWaterInputs(DirtyWaterStore[] sources, float[] maxFlowRates, float[] desiredFlowRates){
		myDirtyWaterInputs = sources;
		dirtyWaterInMaxFlowRates = maxFlowRates;
		dirtyWaterInDesiredFlowRates = desiredFlowRates;
		dirtyWaterInActualFlowRates = new float[dirtyWaterInDesiredFlowRates.length];
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
	public void setPotableWaterInputDesiredFlowRate(float amount, int index){
		potableWaterInDesiredFlowRates[index] = amount;
	}
	public float getPotableWaterInputDesiredFlowRate(int index){
		return potableWaterInDesiredFlowRates[index];
	}
	public float[] getPotableWaterInputDesiredFlowRates(){
		return potableWaterInDesiredFlowRates;
	}
	public float getPotableWaterInputActualFlowRate(int index){
		return potableWaterInActualFlowRates[index];
	}
	public float[] getPotableWaterInputActualFlowRates(){
		return potableWaterInActualFlowRates;
	}
	public void setPotableWaterInputs(PotableWaterStore[] sources, float[] maxFlowRates, float[] desiredFlowRates){
		myPotableWaterInputs = sources;
		potableWaterInMaxFlowRates = maxFlowRates;
		potableWaterInDesiredFlowRates = desiredFlowRates;
		potableWaterInActualFlowRates = new float[potableWaterInDesiredFlowRates.length];
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
	public void setFoodInputDesiredFlowRate(float amount, int index){
		foodInDesiredFlowRates[index] = amount;
	}
	public float getFoodInputDesiredFlowRate(int index){
		return foodInDesiredFlowRates[index];
	}
	public float[] getFoodInDesiredFlowRates(){
		return foodInDesiredFlowRates;
	}
	public float getFoodInputActualFlowRate(int index){
		return foodInActualFlowRates[index];
	}
	public float[] getFoodInActualFlowRates(){
		return foodInActualFlowRates;
	}
	public void setFoodInputs(FoodStore[] sources, float[] maxFlowRates, float[] desiredFlowRates){
		myFoodInputs = sources;
		foodInMaxFlowRates = maxFlowRates;
		foodInDesiredFlowRates = desiredFlowRates;
		foodInActualFlowRates = new float[foodInDesiredFlowRates.length];
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
	public void setFoodOutputDesiredFlowRate(float amount, int index){
		foodOutDesiredFlowRates[index] = amount;
	}
	public float getFoodOutputDesiredFlowRate(int index){
		return foodOutDesiredFlowRates[index];
	}
	public float[] getFoodOutputDesiredFlowRates(){
		return foodOutDesiredFlowRates;
	}
	public float getFoodOutputActualFlowRate(int index){
		return foodOutActualFlowRates[index];
	}
	public float[] getFoodOutputActualFlowRates(){
		return foodOutActualFlowRates;
	}
	public void setFoodOutputs(FoodStore[] destinations, float[] maxFlowRates, float[] desiredFlowRates){
		myFoodOutputs = destinations;
		foodOutMaxFlowRates = maxFlowRates;
		foodOutDesiredFlowRates = desiredFlowRates;
		foodOutActualFlowRates = new float[foodOutDesiredFlowRates.length];
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
	public void setBiomassInputDesiredFlowRate(float amount, int index){
		biomassInDesiredFlowRates[index] = amount;
	}
	public float getBiomassInputDesiredFlowRate(int index){
		return biomassInDesiredFlowRates[index];
	}
	public float[] getBiomassInputDesiredFlowRates(){
		return biomassInDesiredFlowRates;
	}
	public float getBiomassInputActualFlowRate(int index){
		return biomassInActualFlowRates[index];
	}
	public float[] getBiomassInputActualFlowRates(){
		return biomassInActualFlowRates;
	}
	public void setBiomassInputs(BiomassStore[] sources, float[] maxFlowRates, float[] desiredFlowRates){
		myBiomassInputs = sources;
		biomassInMaxFlowRates = maxFlowRates;
		biomassInDesiredFlowRates = desiredFlowRates;
		biomassInActualFlowRates = new float[biomassInDesiredFlowRates.length];
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
	public void setBiomassOutputDesiredFlowRate(float amount, int index){
		biomassOutDesiredFlowRates[index] = amount;
	}
	public float getBiomassOutputDesiredFlowRate(int index){
		return biomassOutDesiredFlowRates[index];
	}
	public float[] getBiomassOutputDesiredFlowRates(){
		return biomassOutDesiredFlowRates;
	}
	public float getBiomassOutputActualFlowRate(int index){
		return biomassOutActualFlowRates[index];
	}
	public float[] getBiomassOutputActualFlowRates(){
		return biomassOutActualFlowRates;
	}
	public void setBiomassOutputs(BiomassStore[] destinations, float[] maxFlowRates, float[] desiredFlowRates){
		myBiomassOutputs = destinations;
		biomassOutMaxFlowRates = maxFlowRates;
		biomassOutDesiredFlowRates = desiredFlowRates;
		biomassOutActualFlowRates = new float[biomassOutDesiredFlowRates.length];
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
	public void setAirOutputDesiredFlowRate(float amount, int index){
		airOutDesiredFlowRates[index] = amount;
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
	public void setAirOutputs(SimEnvironment[] destinations, float[] maxFlowRates, float[] desiredFlowRates){
		myAirOutputs = destinations;
		airOutMaxFlowRates = maxFlowRates;
		airOutDesiredFlowRates = desiredFlowRates;
		airOutActualFlowRates = new float[airOutDesiredFlowRates.length];
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
	public void setO2InputDesiredFlowRate(float amount, int index){
		O2InDesiredFlowRates[index] = amount;
	}
	public float getO2InputDesiredFlowRate(int index){
		return O2InDesiredFlowRates[index];
	}
	public float[] getO2InputDesiredFlowRates(){
		return O2InDesiredFlowRates;
	}
	public float getO2InputActualFlowRate(int index){
		return O2InActualFlowRates[index];
	}
	public float[] getO2InputActualFlowRates(){
		return O2InActualFlowRates;
	}
	public void setO2Inputs(O2Store[] sources, float[] maxFlowRates, float[] desiredFlowRates){
		myO2Inputs = sources;
		O2InMaxFlowRates = maxFlowRates;
		O2InDesiredFlowRates = desiredFlowRates;
		O2InActualFlowRates = new float[O2InDesiredFlowRates.length];
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
	public void setO2OutputDesiredFlowRate(float amount, int index){
		O2OutDesiredFlowRates[index] = amount;
	}
	public float getO2OutputDesiredFlowRate(int index){
		return O2OutDesiredFlowRates[index];
	}
	public float[] getO2OutputDesiredFlowRates(){
		return O2OutDesiredFlowRates;
	}
	public float getO2OutputActualFlowRate(int index){
		return O2OutActualFlowRates[index];
	}
	public float[] getO2OutputActualFlowRates(){
		return O2OutActualFlowRates;
	}
	public void setO2Outputs(O2Store[] destinations, float[] maxFlowRates, float[] desiredFlowRates){
		myO2Outputs = destinations;
		O2OutMaxFlowRates = maxFlowRates;
		O2OutDesiredFlowRates = desiredFlowRates;
		O2OutActualFlowRates = new float[O2OutDesiredFlowRates.length];
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
	public void setCO2InputDesiredFlowRate(float amount, int index){
		CO2InDesiredFlowRates[index] = amount;
	}
	public float getCO2InputDesiredFlowRate(int index){
		return CO2InDesiredFlowRates[index];
	}
	public float[] getCO2InputDesiredFlowRates(){
		return CO2InDesiredFlowRates;
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
	public void setCO2Inputs(CO2Store[] sources, float[] maxFlowRates, float[] desiredFlowRates){
		myCO2Inputs = sources;
		CO2InMaxFlowRates = maxFlowRates;
		CO2InDesiredFlowRates = desiredFlowRates;
		CO2InActualFlowRates = new float[CO2InDesiredFlowRates.length];
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
	public void setCO2OutputDesiredFlowRate(float amount, int index){
		CO2OutDesiredFlowRates[index] = amount;
	}
	public float getCO2OutputDesiredFlowRate(int index){
		return CO2OutDesiredFlowRates[index];
	}
	public float[] getCO2OutputDesiredFlowRates(){
		return CO2OutDesiredFlowRates;
	}
	public float getCO2OutputActualFlowRate(int index){
		return CO2OutActualFlowRates[index];
	}
	public float[] getCO2OutputActualFlowRates(){
		return CO2OutActualFlowRates;
	}
	public void setCO2Outputs(CO2Store[] destinations, float[] maxFlowRates, float[] desiredFlowRates){
		myCO2Outputs = destinations;
		CO2OutMaxFlowRates = maxFlowRates;
		CO2OutDesiredFlowRates = desiredFlowRates;
		CO2OutActualFlowRates = new float[CO2OutDesiredFlowRates.length];
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
	public void setCO2AirStoreInputDesiredFlowRate(float amount, int index){
		CO2AirStoreInDesiredFlowRates[index] = amount;
	}
	public void setCO2AirEnvironmentInputDesiredFlowRate(float amount, int index){
		CO2AirEnvironmentInDesiredFlowRates[index] = amount;
	}
	public float getCO2AirStoreInputDesiredFlowRate(int index){
		return CO2AirStoreInDesiredFlowRates[index];
	}
	public float getCO2AirEnvironmentInputDesiredFlowRate(int index){
		return CO2AirEnvironmentInDesiredFlowRates[index];
	}
	public float[] getCO2AirStoreInputDesiredFlowRates(){
		return CO2AirStoreInDesiredFlowRates;
	}
	public float[] getCO2AirEnvironmentInputDesiredFlowRates(){
		return CO2AirEnvironmentInDesiredFlowRates;
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
	public void setCO2AirStoreInputs(CO2Store[] sources, float[] maxFlowRates, float[] desiredFlowRates){
		myCO2AirStoreInputs = sources;
		CO2AirStoreInMaxFlowRates = maxFlowRates;
		CO2AirStoreInDesiredFlowRates = desiredFlowRates;
		CO2AirStoreInActualFlowRates = new float[CO2AirStoreInDesiredFlowRates.length];
	}
	public void setCO2AirEnvironmentInputs(SimEnvironment[] sources, float[] maxFlowRates, float[] desiredFlowRates){
		myCO2AirEnvironmentInputs = sources;
		CO2AirEnvironmentInMaxFlowRates = maxFlowRates;
		CO2AirEnvironmentInDesiredFlowRates = desiredFlowRates;
		CO2AirEnvironmentInActualFlowRates = new float[CO2AirEnvironmentInDesiredFlowRates.length];
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
	public void setCO2AirStoreOutputDesiredFlowRate(float amount, int index){
		CO2AirStoreOutDesiredFlowRates[index] = amount;
	}
	public void setCO2AirEnvironmentOutputDesiredFlowRate(float amount, int index){
		CO2AirEnvironmentOutDesiredFlowRates[index] = amount;
	}
	public float getCO2AirStoreOutputDesiredFlowRate(int index){
		return CO2AirStoreOutDesiredFlowRates[index];
	}
	public float getCO2AirEnvironmentOutputDesiredFlowRate(int index){
		return CO2AirEnvironmentOutDesiredFlowRates[index];
	}
	public float[] getCO2AirStoreOutputDesiredFlowRates(){
		return CO2AirStoreOutDesiredFlowRates;
	}
	public float[] getCO2AirEnvironmentOutputDesiredFlowRates(){
		return CO2AirEnvironmentOutDesiredFlowRates;
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
	public void setCO2AirStoreOutputs(CO2Store[] destinations, float[] maxFlowRates, float[] desiredFlowRates){
		myCO2AirStoreOutputs = destinations;
		CO2AirStoreOutMaxFlowRates = maxFlowRates;
		CO2AirStoreOutDesiredFlowRates = desiredFlowRates;
		CO2AirStoreOutActualFlowRates = new float[CO2AirStoreOutDesiredFlowRates.length];
	}
	public void setCO2AirEnvironmentOutputs(SimEnvironment[] destinations, float[] maxFlowRates, float[] desiredFlowRates){
		myCO2AirEnvironmentOutputs = destinations;
		CO2AirEnvironmentOutMaxFlowRates = maxFlowRates;
		CO2AirEnvironmentOutDesiredFlowRates = desiredFlowRates;
		CO2AirEnvironmentOutActualFlowRates = new float[CO2AirEnvironmentOutDesiredFlowRates.length];
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
	public void setO2AirStoreInputDesiredFlowRate(float amount, int index){
		O2AirStoreInDesiredFlowRates[index] = amount;
	}
	public void setO2AirEnvironmentInputDesiredFlowRate(float amount, int index){
		O2AirEnvironmentInDesiredFlowRates[index] = amount;
	}
	public float getO2AirStoreInputDesiredFlowRate(int index){
		return O2AirStoreInDesiredFlowRates[index];
	}
	public float getO2AirEnvironmentInputDesiredFlowRate(int index){
		return O2AirEnvironmentInDesiredFlowRates[index];
	}
	public float[] getO2AirStoreInputDesiredFlowRates(){
		return O2AirStoreInDesiredFlowRates;
	}
	public float[] getO2AirEnvironmentInputDesiredFlowRates(){
		return O2AirEnvironmentInDesiredFlowRates;
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
	public void setO2AirStoreInputs(O2Store[] sources, float[] maxFlowRates, float[] desiredFlowRates){
		myO2AirStoreInputs = sources;
		O2AirStoreInMaxFlowRates = maxFlowRates;
		O2AirStoreInDesiredFlowRates = desiredFlowRates;
		O2AirStoreInActualFlowRates = new float[O2AirStoreInDesiredFlowRates.length];
	}
	public void setO2AirEnvironmentInputs(SimEnvironment[] sources, float[] maxFlowRates, float[] desiredFlowRates){
		myO2AirEnvironmentInputs = sources;
		O2AirEnvironmentInMaxFlowRates = maxFlowRates;
		O2AirEnvironmentInDesiredFlowRates = desiredFlowRates;
		O2AirEnvironmentInActualFlowRates = new float[O2AirEnvironmentInDesiredFlowRates.length];
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
	public void setO2AirStoreOutputDesiredFlowRate(float amount, int index){
		O2AirStoreOutDesiredFlowRates[index] = amount;
	}
	public void setO2AirEnvironmentOutputDesiredFlowRate(float amount, int index){
		O2AirEnvironmentOutDesiredFlowRates[index] = amount;
	}
	public float getO2AirStoreOutputDesiredFlowRate(int index){
		return O2AirStoreOutDesiredFlowRates[index];
	}
	public float getO2AirEnvironmentOutputDesiredFlowRate(int index){
		return O2AirEnvironmentOutDesiredFlowRates[index];
	}
	public float[] getO2AirStoreOutputDesiredFlowRates(){
		return O2AirStoreOutDesiredFlowRates;
	}
	public float[] getO2AirEnvironmentOutputDesiredFlowRates(){
		return O2AirEnvironmentOutDesiredFlowRates;
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
	public void setO2AirStoreOutputs(O2Store[] destinations, float[] maxFlowRates, float[] desiredFlowRates){
		myO2AirStoreOutputs = destinations;
		O2AirStoreOutMaxFlowRates = maxFlowRates;
		O2AirStoreOutDesiredFlowRates = desiredFlowRates;
		O2AirStoreOutActualFlowRates = new float[O2AirStoreOutDesiredFlowRates.length];
	}
	public void setO2AirEnvironmentOutputs(SimEnvironment[] destinations, float[] maxFlowRates, float[] desiredFlowRates){
		myO2AirEnvironmentOutputs = destinations;
		O2AirEnvironmentOutMaxFlowRates = maxFlowRates;
		O2AirEnvironmentOutDesiredFlowRates = desiredFlowRates;
		O2AirEnvironmentOutActualFlowRates = new float[O2AirEnvironmentOutDesiredFlowRates.length];
	}
	public O2Store[] getO2AirStoreOutputs(){
		return myO2AirStoreOutputs;
	}
	public SimEnvironment[] getO2AirEnvironmentOutputs(){
		return myO2AirEnvironmentOutputs;
	}
	
	//Nitrogen Inputs
	public void setNitrogenInputMaxFlowRate(float amount, int index){
		nitrogenInMaxFlowRates[index] = amount;
	}
	public float getNitrogenInputMaxFlowRate(int index){
		return nitrogenInMaxFlowRates[index];
	}
	public float[] getNitrogenInputMaxFlowRates(){
		return nitrogenInMaxFlowRates;
	}
	public void setNitrogenInputDesiredFlowRate(float amount, int index){
		nitrogenInDesiredFlowRates[index] = amount;
	}
	public float getNitrogenInputDesiredFlowRate(int index){
		return nitrogenInDesiredFlowRates[index];
	}
	public float[] getNitrogenInputDesiredFlowRates(){
		return nitrogenInDesiredFlowRates;
	}
	public float getNitrogenInputActualFlowRate(int index){
		return nitrogenInActualFlowRates[index];
	}
	public float[] getNitrogenInputActualFlowRates(){
		return nitrogenInActualFlowRates;
	}
	public NitrogenStore[] getNitrogenInputs(){
		return myNitrogenInputs;
	}
	public void setNitrogenInputs(NitrogenStore[] sources, float[] maxFlowRates, float[] desiredFlowRates){
		myNitrogenInputs = sources;
		nitrogenInMaxFlowRates = maxFlowRates;
		nitrogenInDesiredFlowRates = desiredFlowRates;
		nitrogenInActualFlowRates = new float[nitrogenInDesiredFlowRates.length];
	}

	//Nitrogen Outputs
	public void setNitrogenOutputMaxFlowRate(float amount, int index){
		nitrogenOutMaxFlowRates[index] = amount;
	}
	public float getNitrogenOutputMaxFlowRate(int index){
		return nitrogenOutMaxFlowRates[index];
	}
	public float[] getNitrogenOutputMaxFlowRates(){
		return nitrogenOutMaxFlowRates;
	}
	public void setNitrogenOutputDesiredFlowRate(float amount, int index){
		nitrogenOutDesiredFlowRates[index] = amount;
	}
	public float getNitrogenOutputDesiredFlowRate(int index){
		return nitrogenOutDesiredFlowRates[index];
	}
	public float[] getNitrogenOutputDesiredFlowRates(){
		return nitrogenOutDesiredFlowRates;
	}
	public float getNitrogenOutputActualFlowRate(int index){
		return nitrogenOutActualFlowRates[index];
	}
	public float[] getNitrogenOutputActualFlowRates(){
		return nitrogenOutActualFlowRates;
	}
	public void setNitrogenOutputs(NitrogenStore[] destinations, float[] maxFlowRates, float[] desiredFlowRates){
		myNitrogenOutputs = destinations;
		nitrogenOutMaxFlowRates = maxFlowRates;
		nitrogenOutDesiredFlowRates = desiredFlowRates;
		nitrogenOutActualFlowRates = new float[nitrogenOutDesiredFlowRates.length];
	}
	public NitrogenStore[] getNitrogenOutputs(){
		return myNitrogenOutputs;
	}

	//Nitrogen Air Inputs
	public void setNitrogenAirStoreInputMaxFlowRate(float amount, int index){
		nitrogenAirStoreInMaxFlowRates[index] = amount;
	}
	public void setNitrogenAirEnvironmentInputMaxFlowRate(float amount, int index){
		nitrogenAirEnvironmentInMaxFlowRates[index] = amount;
	}
	public float getNitrogenAirStoreInputMaxFlowRate(int index){
		return nitrogenAirStoreInMaxFlowRates[index];
	}
	public float getNitrogenAirEnvironmentInputMaxFlowRate(int index){
		return nitrogenAirEnvironmentInMaxFlowRates[index];
	}
	public float[] getNitrogenAirStoreInputMaxFlowRates(){
		return nitrogenAirStoreInMaxFlowRates;
	}
	public float[] getNitrogenAirEnvironmentInputMaxFlowRates(){
		return nitrogenAirEnvironmentInMaxFlowRates;
	}
	public void setNitrogenAirStoreInputDesiredFlowRate(float amount, int index){
		nitrogenAirStoreInDesiredFlowRates[index] = amount;
	}
	public void setNitrogenAirEnvironmentInputDesiredFlowRate(float amount, int index){
		nitrogenAirEnvironmentInDesiredFlowRates[index] = amount;
	}
	public float getNitrogenAirStoreInputDesiredFlowRate(int index){
		return nitrogenAirStoreInDesiredFlowRates[index];
	}
	public float getNitrogenAirEnvironmentInputDesiredFlowRate(int index){
		return nitrogenAirEnvironmentInDesiredFlowRates[index];
	}
	public float[] getNitrogenAirStoreInputDesiredFlowRates(){
		return nitrogenAirStoreInDesiredFlowRates;
	}
	public float[] getNitrogenAirEnvironmentInputDesiredFlowRates(){
		return nitrogenAirEnvironmentInDesiredFlowRates;
	}
	public float getNitrogenAirStoreInputActualFlowRate(int index){
		return nitrogenAirStoreInActualFlowRates[index];
	}
	public float getNitrogenAirEnvironmentInputActualFlowRate(int index){
		return nitrogenAirEnvironmentInActualFlowRates[index];
	}
	public float[] getNitrogenAirStoreInputActualFlowRates(){
		return nitrogenAirStoreInActualFlowRates;
	}
	public float[] getNitrogenAirEnvironmentInputActualFlowRates(){
		return nitrogenAirEnvironmentInActualFlowRates;
	}
	public void setNitrogenAirStoreInputs(NitrogenStore[] sources, float[] maxFlowRates, float[] desiredFlowRates){
		myNitrogenAirStoreInputs = sources;
		nitrogenAirStoreInMaxFlowRates = maxFlowRates;
		nitrogenAirStoreInDesiredFlowRates = desiredFlowRates;
		nitrogenAirStoreInActualFlowRates = new float[nitrogenAirStoreInDesiredFlowRates.length];
	}
	public void setNitrogenAirEnvironmentInputs(SimEnvironment[] sources, float[] maxFlowRates, float[] desiredFlowRates){
		myNitrogenAirEnvironmentInputs = sources;
		nitrogenAirEnvironmentInMaxFlowRates = maxFlowRates;
		nitrogenAirEnvironmentInDesiredFlowRates = desiredFlowRates;
		nitrogenAirEnvironmentInActualFlowRates = new float[nitrogenAirEnvironmentInDesiredFlowRates.length];
	}
	public NitrogenStore[] getNitrogenAirStoreInputs(){
		return myNitrogenAirStoreInputs;
	}
	public SimEnvironment[] getNitrogenAirEnvironmentInputs(){
		return myNitrogenAirEnvironmentInputs;
	}

	//Nitrogen Air Outputs
	public void setNitrogenAirStoreOutputMaxFlowRate(float amount, int index){
		nitrogenAirStoreOutMaxFlowRates[index] = amount;
	}
	public void setNitrogenAirEnvironmentOutputMaxFlowRate(float amount, int index){
		nitrogenAirEnvironmentOutMaxFlowRates[index] = amount;
	}
	public float getNitrogenAirStoreOutputMaxFlowRate(int index){
		return nitrogenAirStoreOutMaxFlowRates[index];
	}
	public float getNitrogenAirEnvironmentOutputMaxFlowRate(int index){
		return nitrogenAirEnvironmentOutMaxFlowRates[index];
	}
	public float[] getNitrogenAirStoreOutputMaxFlowRates(){
		return nitrogenAirStoreOutMaxFlowRates;
	}
	public float[] getNitrogenAirEnvironmentOutputMaxFlowRates(){
		return nitrogenAirEnvironmentOutMaxFlowRates;
	}
	public void setNitrogenAirStoreOutputDesiredFlowRate(float amount, int index){
		nitrogenAirStoreOutDesiredFlowRates[index] = amount;
	}
	public void setNitrogenAirEnvironmentOutputDesiredFlowRate(float amount, int index){
		nitrogenAirEnvironmentOutDesiredFlowRates[index] = amount;
	}
	public float getNitrogenAirStoreOutputDesiredFlowRate(int index){
		return nitrogenAirStoreOutDesiredFlowRates[index];
	}
	public float getNitrogenAirEnvironmentOutputDesiredFlowRate(int index){
		return nitrogenAirEnvironmentOutDesiredFlowRates[index];
	}
	public float[] getNitrogenAirStoreOutputDesiredFlowRates(){
		return nitrogenAirStoreOutDesiredFlowRates;
	}
	public float[] getNitrogenAirEnvironmentOutputDesiredFlowRates(){
		return nitrogenAirEnvironmentOutDesiredFlowRates;
	}
	public float getNitrogenAirStoreOutputActualFlowRate(int index){
		return nitrogenAirStoreOutActualFlowRates[index];
	}
	public float getNitrogenAirEnvironmentOutputActualFlowRate(int index){
		return nitrogenAirEnvironmentOutActualFlowRates[index];
	}
	public float[] getNitrogenAirStoreOutputActualFlowRates(){
		return nitrogenAirStoreOutActualFlowRates;
	}
	public float[] getNitrogenAirEnvironmentOutputActualFlowRates(){
		return nitrogenAirEnvironmentOutActualFlowRates;
	}
	public void setNitrogenAirStoreOutputs(NitrogenStore[] destinations, float[] maxFlowRates, float[] desiredFlowRates){
		myNitrogenAirStoreOutputs = destinations;
		nitrogenAirStoreOutMaxFlowRates = maxFlowRates;
		nitrogenAirStoreOutDesiredFlowRates = desiredFlowRates;
		nitrogenAirStoreOutActualFlowRates = new float[nitrogenAirStoreOutDesiredFlowRates.length];
	}
	public void setNitrogenAirEnvironmentOutputs(SimEnvironment[] destinations, float[] maxFlowRates, float[] desiredFlowRates){
		myNitrogenAirEnvironmentOutputs = destinations;
		nitrogenAirEnvironmentOutMaxFlowRates = maxFlowRates;
		nitrogenAirEnvironmentOutDesiredFlowRates = desiredFlowRates;
		nitrogenAirEnvironmentOutActualFlowRates = new float[nitrogenAirEnvironmentOutDesiredFlowRates.length];
	}
	public NitrogenStore[] getNitrogenAirStoreOutputs(){
		return myNitrogenAirStoreOutputs;
	}
	public SimEnvironment[] getNitrogenAirEnvironmentOutputs(){
		return myNitrogenAirEnvironmentOutputs;
	}
	
	//Water Air Inputs
	public void setWaterAirStoreInputMaxFlowRate(float amount, int index){
		waterAirStoreInMaxFlowRates[index] = amount;
	}
	public void setWaterAirEnvironmentInputMaxFlowRate(float amount, int index){
		waterAirEnvironmentInMaxFlowRates[index] = amount;
	}
	public float getWaterAirStoreInputMaxFlowRate(int index){
		return waterAirStoreInMaxFlowRates[index];
	}
	public float getWaterAirEnvironmentInputMaxFlowRate(int index){
		return waterAirEnvironmentInMaxFlowRates[index];
	}
	public float[] getWaterAirStoreInputMaxFlowRates(){
		return waterAirStoreInMaxFlowRates;
	}
	public float[] getWaterAirEnvironmentInputMaxFlowRates(){
		return waterAirEnvironmentInMaxFlowRates;
	}
	public void setWaterAirStoreInputDesiredFlowRate(float amount, int index){
		waterAirStoreInDesiredFlowRates[index] = amount;
	}
	public void setWaterAirEnvironmentInputDesiredFlowRate(float amount, int index){
		waterAirEnvironmentInDesiredFlowRates[index] = amount;
	}
	public float getWaterAirStoreInputDesiredFlowRate(int index){
		return waterAirStoreInDesiredFlowRates[index];
	}
	public float getWaterAirEnvironmentInputDesiredFlowRate(int index){
		return waterAirEnvironmentInDesiredFlowRates[index];
	}
	public float[] getWaterAirStoreInputDesiredFlowRates(){
		return waterAirStoreInDesiredFlowRates;
	}
	public float[] getWaterAirEnvironmentInputDesiredFlowRates(){
		return waterAirEnvironmentInDesiredFlowRates;
	}
	public float getWaterAirStoreInputActualFlowRate(int index){
		return waterAirStoreInActualFlowRates[index];
	}
	public float getWaterAirEnvironmentInputActualFlowRate(int index){
		return waterAirEnvironmentInActualFlowRates[index];
	}
	public float[] getWaterAirStoreInputActualFlowRates(){
		return waterAirStoreInActualFlowRates;
	}
	public float[] getWaterAirEnvironmentInputActualFlowRates(){
		return waterAirEnvironmentInActualFlowRates;
	}
	public void setWaterAirStoreInputs(PotableWaterStore[] sources, float[] maxFlowRates, float[] desiredFlowRates){
		myWaterAirStoreInputs = sources;
		waterAirStoreInMaxFlowRates = maxFlowRates;
		waterAirStoreInDesiredFlowRates = desiredFlowRates;
		waterAirStoreInActualFlowRates = new float[waterAirStoreInDesiredFlowRates.length];
	}
	public void setWaterAirEnvironmentInputs(SimEnvironment[] sources, float[] maxFlowRates, float[] desiredFlowRates){
		myWaterAirEnvironmentInputs = sources;
		waterAirEnvironmentInMaxFlowRates = maxFlowRates;
		waterAirEnvironmentInDesiredFlowRates = desiredFlowRates;
		waterAirEnvironmentInActualFlowRates = new float[waterAirEnvironmentInDesiredFlowRates.length];
	}
	public PotableWaterStore[] getWaterAirStoreInputs(){
		return myWaterAirStoreInputs;
	}
	public SimEnvironment[] getWaterAirEnvironmentInputs(){
		return myWaterAirEnvironmentInputs;
	}

	//Water Air Outputs
	public void setWaterAirStoreOutputMaxFlowRate(float amount, int index){
		waterAirStoreOutMaxFlowRates[index] = amount;
	}
	public void setWaterAirEnvironmentOutputMaxFlowRate(float amount, int index){
		waterAirEnvironmentOutMaxFlowRates[index] = amount;
	}
	public float getWaterAirStoreOutputMaxFlowRate(int index){
		return waterAirStoreOutMaxFlowRates[index];
	}
	public float getWaterAirEnvironmentOutputMaxFlowRate(int index){
		return waterAirEnvironmentOutMaxFlowRates[index];
	}
	public float[] getWaterAirStoreOutputMaxFlowRates(){
		return waterAirStoreOutMaxFlowRates;
	}
	public float[] getWaterAirEnvironmentOutputMaxFlowRates(){
		return waterAirEnvironmentOutMaxFlowRates;
	}
	public void setWaterAirStoreOutputDesiredFlowRate(float amount, int index){
		waterAirStoreOutDesiredFlowRates[index] = amount;
	}
	public void setWaterAirEnvironmentOutputDesiredFlowRate(float amount, int index){
		waterAirEnvironmentOutDesiredFlowRates[index] = amount;
	}
	public float getWaterAirStoreOutputDesiredFlowRate(int index){
		return waterAirStoreOutDesiredFlowRates[index];
	}
	public float getWaterAirEnvironmentOutputDesiredFlowRate(int index){
		return waterAirEnvironmentOutDesiredFlowRates[index];
	}
	public float[] getWaterAirStoreOutputDesiredFlowRates(){
		return waterAirStoreOutDesiredFlowRates;
	}
	public float[] getWaterAirEnvironmentOutputDesiredFlowRates(){
		return waterAirEnvironmentOutDesiredFlowRates;
	}
	public float getWaterAirStoreOutputActualFlowRate(int index){
		return waterAirStoreOutActualFlowRates[index];
	}
	public float getWaterAirEnvironmentOutputActualFlowRate(int index){
		return waterAirEnvironmentOutActualFlowRates[index];
	}
	public float[] getWaterAirStoreOutputActualFlowRates(){
		return waterAirStoreOutActualFlowRates;
	}
	public float[] getWaterAirEnvironmentOutputActualFlowRates(){
		return waterAirEnvironmentOutActualFlowRates;
	}
	public void setWaterAirStoreOutputs(PotableWaterStore[] destinations, float[] maxFlowRates, float[] desiredFlowRates){
		myWaterAirStoreOutputs = destinations;
		waterAirStoreOutMaxFlowRates = maxFlowRates;
		waterAirStoreOutDesiredFlowRates = desiredFlowRates;
		waterAirStoreOutActualFlowRates = new float[waterAirStoreOutDesiredFlowRates.length];
	}
	public void setWaterAirEnvironmentOutputs(SimEnvironment[] destinations, float[] maxFlowRates, float[] desiredFlowRates){
		myWaterAirEnvironmentOutputs = destinations;
		waterAirEnvironmentOutMaxFlowRates = maxFlowRates;
		waterAirEnvironmentOutDesiredFlowRates = desiredFlowRates;
		waterAirEnvironmentOutActualFlowRates = new float[waterAirEnvironmentOutDesiredFlowRates.length];
	}
	public PotableWaterStore[] getWaterAirStoreOutputs(){
		return myWaterAirStoreOutputs;
	}
	public SimEnvironment[] getWaterAirEnvironmentOutputs(){
		return myWaterAirEnvironmentOutputs;
	}
}
