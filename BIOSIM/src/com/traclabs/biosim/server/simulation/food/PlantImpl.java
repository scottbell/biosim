package biosim.server.simulation.food;

import biosim.idl.simulation.food.*;
import biosim.idl.simulation.air.*;
import java.util.*;
import biosim.idl.util.log.*;
import biosim.idl.simulation.environment.*;
import biosim.server.util.*;
/**
 * Plant
 * @author    Scott Bell
 */

public abstract class PlantImpl extends PlantPOA{
	protected int myAge = 0;
	private LogIndex myLogIndex;
	private boolean logInitialized = false;
	protected ShelfImpl myShelfImpl;
	private float myPPF = 0f;
	//in dry weight
	private float myCurrentTotalWetBiomass = 0f;
	private float myCurrentEdibleWetBiomass = 0f;
	private float myLastTotalWetBiomass = 0f;
	private float myLastEdibleWetBiomass = 0f;
	private float myWaterNeeded = 0f;
	private float myWaterLevel = 0f;
	protected float[] canopyClosureConstants;
	protected float[] canopyQYConstants;

	public PlantImpl(ShelfImpl pShelfImpl){
		myShelfImpl = pShelfImpl;
		canopyClosureConstants = new float[25];
		canopyQYConstants = new float[25];
		Arrays.fill(canopyClosureConstants, 0f);
		Arrays.fill(canopyQYConstants, 0f);
	}

	protected abstract float getBCF();
	protected abstract float getCarbonUseEfficiency24();
	protected abstract float getPhotoperiod();
	protected abstract float getN();
	protected abstract float getTimeTillCanopySenescence();
	protected abstract float getCQYMin();
	protected abstract float getTimeTillCropMaturity();
	protected abstract float getOPF();
	protected abstract float getFreshFactor();
	protected abstract float calculateCanopyStomatalConductance();
	protected abstract float calculateAtmosphericAeroDynamicConductance();
	protected abstract float getFractionOfEdibleBiomass();
	protected abstract float getEdibleFreshBasisWaterContent();
	protected abstract float getInedibleFreshBasisWaterContent();
	public abstract float getPPFNeeded();
	public abstract PlantType getPlantType();
	

	public void reset(){
		myPPF = 0f;
		myCurrentTotalWetBiomass = 0f;
		myLastTotalWetBiomass = 0f;
		myAge = 0;
	}

	public void tick(){
		System.out.println("PlantImpl: **************Begin plant tick***********");
		growBiomass();
		System.out.println("PlantImpl: **************End plant tick***********");
	}

	public float harvest(){
		float biomassToReturn = myCurrentTotalWetBiomass;
		myCurrentTotalWetBiomass = 0f;
		myLastTotalWetBiomass = 0f;
		myCurrentEdibleWetBiomass = 0f;
		myLastEdibleWetBiomass = 0f;
		myWaterNeeded = 0f;
		myWaterLevel = 0f;
		myAge = 0;
		return biomassToReturn;
	}

	public void shine(float pPPF){
		myPPF = pPPF;
	}
	
	private float calculateDailyCanopyTranspirationRate(){
		//assumes water is at 20 C and 101kPA of total pressure
		float airPressure = myShelfImpl.getBiomassRSImpl().getAirOutputs()[0].getTotalPressure();
		return 3600f * getPhotoperiod() * (18.015f / 998.23f) * calculateCanopySurfaceConductance() * (calculateVaporPressureDeficit() / airPressure);
	}
	
	private float calculateVaporPressureDeficit(){
		float saturatedMoistureVaporPressure = calculateSaturatedMoistureVaporPressure();
		return saturatedMoistureVaporPressure - calculateActualMoistureVaporPressure(saturatedMoistureVaporPressure);
	}
	
	private float calculateSaturatedMoistureVaporPressure(){
		float temperatureLight = myShelfImpl.getBiomassRSImpl().getAirOutputs()[0].getTemperature();
		float exponent = (17.4f * temperatureLight) / (temperatureLight + 239f);
		return 0.611f * exp(exponent);
	}
	
	private float calculateActualMoistureVaporPressure(float pSaturatedMoistureVaporPressure){
		return pSaturatedMoistureVaporPressure - myShelfImpl.getBiomassRSImpl().getAirInputs()[0].getWaterMoles();
	}
	
	protected float calculateNetCanopyPhotosynthesis(){
		float plantGrowthDiurnalCycle = 24f;
		return (((plantGrowthDiurnalCycle - getPhotoperiod()) / plantGrowthDiurnalCycle) + ((getPhotoperiod() * getCarbonUseEfficiency24()) / plantGrowthDiurnalCycle)) * calculateGrossCanopyPhotosynthesis();
	}
	
	private float calculateGrossCanopyPhotosynthesis(){
		return calculateCQY() * calculateCQY() * getPPF();
	}
	
	private float calculateCanopySurfaceConductance(){
		float canopyStomatalConductance = calculateCanopyStomatalConductance();
		float atmosphericAeroDynamicConductance = calculateAtmosphericAeroDynamicConductance();
		return (atmosphericAeroDynamicConductance * canopyStomatalConductance) / (canopyStomatalConductance + atmosphericAeroDynamicConductance);
	}
	
	
	private void growBiomass(){
		//Biomass Grown
		float molecularWeightOfCarbon = 12.011f; // g/mol
		float dailyCarbonGain = calculateDailyCarbonGain();
		System.out.println("PlantImpl: dailyCarbonGain: "+dailyCarbonGain);
		float cropGrowthRate = molecularWeightOfCarbon * (dailyCarbonGain / getBCF());
		System.out.println("PlantImpl: cropGrowthRate: "+cropGrowthRate);
		float myCurrentDryBiomass = cropGrowthRate * 1000 * myShelfImpl.getCropArea(); //in kilograms
		System.out.println("PlantImpl: myCurrentDryBiomass: "+myCurrentDryBiomass);
		myLastTotalWetBiomass = myCurrentTotalWetBiomass;
		myLastEdibleWetBiomass = myCurrentEdibleWetBiomass;
		myCurrentEdibleWetBiomass += getFractionOfEdibleBiomass() * myCurrentTotalWetBiomass;
		myCurrentTotalWetBiomass += (myCurrentDryBiomass * getFreshFactor());
		System.out.println("PlantImpl: myCurrentTotalWetBiomass: "+myCurrentTotalWetBiomass);
		System.out.println("PlantImpl: myCurrentEdibleWetBiomass: "+myCurrentEdibleWetBiomass);
		
		//Water In
		myWaterNeeded = calculateWaterUptake();
		myWaterLevel = myShelfImpl.takeWater(myWaterNeeded);
		System.out.println("PlantImpl: myWaterNeeded: "+myWaterNeeded);
		System.out.println("PlantImpl: myWaterLevel: "+myWaterLevel);
		
		//Breathe Air
		float molesOfCO2ToInhale = dailyCarbonGain;
		float molesOfCO2Inhaled = myShelfImpl.getBiomassRSImpl().getAirInputs()[0].takeCO2Moles(molesOfCO2ToInhale);
		myShelfImpl.getBiomassRSImpl().addAirInputActualFlowRates(0,molesOfCO2Inhaled);
		System.out.println("PlantImpl: molesOfCO2ToInhale: "+molesOfCO2ToInhale);
		System.out.println("PlantImpl: molesOfCO2Inhaled: "+molesOfCO2Inhaled);
		
		//Exhale Air
		float O2Produced = getOPF() * dailyCarbonGain * myShelfImpl.getCropArea() / 24; //in mol of oxygen per hour
		float O2Exhaled = myShelfImpl.getBiomassRSImpl().getAirOutputs()[0].addO2Moles(O2Produced);
		myShelfImpl.getBiomassRSImpl().addAirOutputActualFlowRates(0,O2Exhaled);
		System.out.println("PlantImpl: O2Produced: "+O2Produced);
		System.out.println("PlantImpl: O2Exhaled: "+O2Exhaled);
		
		//Water Vapor Produced
		float litersOfWaterProduced = calculateDailyCanopyTranspirationRate();
		float molesOfWaterProduced = (litersOfWaterProduced * 1000 * 18); //1000 liters per milliter, 1 gram per millilters, 18 moles per gram
		float molesOfWaterAdded = myShelfImpl.getBiomassRSImpl().getAirOutputs()[0].addWaterMoles(molesOfWaterProduced);
		myShelfImpl.getBiomassRSImpl().addAirOutputActualFlowRates(0,molesOfWaterAdded);
		System.out.println("PlantImpl: litersOfWaterProduced: "+litersOfWaterProduced);
		System.out.println("PlantImpl: molesOfWaterProduced: "+molesOfWaterProduced);
		System.out.println("PlantImpl: molesOfWaterAdded: "+molesOfWaterAdded);
	}
	
	//in g/meters^2*hour
	private float calculateDailyCarbonGain(){
		float photoperiod = getPhotoperiod();
		float carbonUseEfficiency24 = getCarbonUseEfficiency24();
		float PPFFractionAbsorbed = calculatePPFFractionAbsorbed();
		float CQY = calculateCQY();
		float PPF = getPPF();
		System.out.println("PlantImpl: photoperiod: "+photoperiod);
		System.out.println("PlantImpl: carbonUseEfficiency24: "+carbonUseEfficiency24);
		System.out.println("PlantImpl: PPFFractionAbsorbed: "+PPFFractionAbsorbed);
		System.out.println("PlantImpl: CQY: "+CQY);
		System.out.println("PlantImpl: PPF: "+PPF);
		return 0.0036f * photoperiod * carbonUseEfficiency24 * PPFFractionAbsorbed * CQY * PPF * 24;
	}
	
	private float calculateWaterUptake(){
		float dailyCanopyTranspirationRate = calculateDailyCanopyTranspirationRate();
		float wetIncoporatedWaterUptake = calculateWetIncoporatedWaterUptake();
		return dailyCanopyTranspirationRate + wetIncoporatedWaterUptake + calculateDryIncoporatedWaterUptake(dailyCanopyTranspirationRate, wetIncoporatedWaterUptake);
	}
	
	private float calculateWetIncoporatedWaterUptake(){
		float myCurrentInedibleWetBiomass = myCurrentTotalWetBiomass - myCurrentEdibleWetBiomass;
		float myLastInedibleWetBiomass = myLastTotalWetBiomass - myLastEdibleWetBiomass;
		float densityOfWater = myShelfImpl.getBiomassRSImpl().getAirInputs()[0].getWaterDensity();
		return (((myCurrentEdibleWetBiomass - myLastEdibleWetBiomass) * getEdibleFreshBasisWaterContent()) + ((myCurrentInedibleWetBiomass + myLastInedibleWetBiomass) * getInedibleFreshBasisWaterContent())) / densityOfWater;
	}
	
	private float calculateDryIncoporatedWaterUptake(float pDailyCanopyTranspirationRate, float pWetIncoporatedWaterUptake){
		return (pDailyCanopyTranspirationRate + pWetIncoporatedWaterUptake) / 500;
	}
	
	public float getWaterNeeded(){
		return myWaterNeeded;
	}

	//Convert current CO2 levels to micromoles of CO2 / moles of air
	protected float calculateCO2Concentration(){
		SimEnvironment myEnvironment = myShelfImpl.getBiomassRSImpl().getAirOutputs()[0];
		float CO2Moles = myEnvironment.getCO2Moles() * pow (10,-6); //in micro moles
		float airMoles = myEnvironment.getTotalMoles(); //in moles
		System.out.println("PlantImpl: CO2Moles: "+CO2Moles);
		System.out.println("PlantImpl: airMoles: "+airMoles);
		return airMoles / CO2Moles;
	}
	
	//returns the age in days
	private float getDaysOfGrowth(){
		return myAge / 24f;
	}

	private float calculateTimeTillCanopyClosure(){
		float thePPF = getPPF();
		float oneOverPPf = 1f / thePPF;
		float thePPFsquared = pow(thePPF, 2f);
		float thePPFcubed = pow(thePPF, 3f);


		float theCO2 = calculateCO2Concentration();
		float oneOverCO2 = 1f / theCO2;
		float theCO2squared = pow(theCO2, 2f);
		float theCO2cubed = pow(theCO2, 3f);

		float tA = canopyClosureConstants[0] * oneOverPPf * oneOverCO2 +
		           canopyClosureConstants[1] * oneOverPPf +
			   canopyClosureConstants[2] * oneOverPPf * theCO2 +
			   canopyClosureConstants[3] * oneOverPPf * theCO2squared +
			   canopyClosureConstants[4] * oneOverPPf * theCO2cubed +
			   canopyClosureConstants[5] * oneOverCO2 +
			   canopyClosureConstants[6] +
			   canopyClosureConstants[7] * theCO2 +
			   canopyClosureConstants[8] * theCO2squared +
			   canopyClosureConstants[9] * theCO2cubed +
			   canopyClosureConstants[10] * thePPF * oneOverCO2 +
			   canopyClosureConstants[11] * thePPF +
			   canopyClosureConstants[12] * thePPF * theCO2 +
			   canopyClosureConstants[13] * thePPF * theCO2squared +
			   canopyClosureConstants[14] * thePPF * theCO2cubed +
			   canopyClosureConstants[15] * thePPFsquared * oneOverCO2 +
			   canopyClosureConstants[16] * thePPFsquared +
			   canopyClosureConstants[17] * thePPFsquared * theCO2 +
			   canopyClosureConstants[18] * thePPFsquared * theCO2squared +
			   canopyClosureConstants[19] * thePPFsquared * theCO2cubed +
			   canopyClosureConstants[20] * thePPFcubed * oneOverCO2 +
			   canopyClosureConstants[21] * thePPFcubed +
			   canopyClosureConstants[22] * thePPFcubed  * theCO2 +
			   canopyClosureConstants[23] * thePPFcubed  * theCO2squared +
			   canopyClosureConstants[24] * thePPFcubed  * theCO2cubed;
		return tA;
	}
	
	public void goGet(){
	}

	private float calculatePPFFractionAbsorbed(){
		float PPFFractionAbsorbedMax = 0.93f;
		float timeTillCanopyClosure = calculateTimeTillCanopyClosure();
		if (getDaysOfGrowth() < timeTillCanopyClosure){
			return PPFFractionAbsorbedMax * pow((getDaysOfGrowth() / timeTillCanopyClosure), getN());
		}
		else
			return PPFFractionAbsorbedMax;
	}
	
	private float calculateCQYMax(){
		float thePPF = getPPF();
		float oneOverPPf = 1f / thePPF;
		float thePPFsquared = pow(thePPF, 2f);
		float thePPFcubed = pow(thePPF, 3f);
		System.out.println("PlantImpl: thePPF: "+thePPF);
		System.out.println("PlantImpl: oneOverPPf: "+oneOverPPf);
		System.out.println("PlantImpl: thePPFsquared: "+thePPFsquared);
		System.out.println("PlantImpl: thePPFcubed: "+thePPFcubed);


		float theCO2 = calculateCO2Concentration();
		float oneOverCO2 = 1f / theCO2;
		float theCO2squared = pow(theCO2, 2f);
		float theCO2cubed = pow(theCO2, 3f);
		System.out.println("PlantImpl: theCO2: "+theCO2);
		System.out.println("PlantImpl: oneOverCO2: "+oneOverCO2);
		System.out.println("PlantImpl: theCO2squared: "+theCO2squared);
		System.out.println("PlantImpl: theCO2cubed: "+theCO2cubed);

		float theCQYMax = canopyQYConstants[0] * oneOverPPf * oneOverCO2 +
		           canopyQYConstants[1] * oneOverPPf +
			   canopyQYConstants[2] * oneOverPPf * theCO2 +
			   canopyQYConstants[3] * oneOverPPf * theCO2squared +
			   canopyQYConstants[4] * oneOverPPf * theCO2cubed +
			   canopyQYConstants[5] * oneOverCO2 +
			   canopyQYConstants[6] +
			   canopyQYConstants[7] * theCO2 +
			   canopyQYConstants[8] * theCO2squared +
			   canopyQYConstants[9] * theCO2cubed +
			   canopyQYConstants[10] * thePPF * oneOverCO2 +
			   canopyQYConstants[11] * thePPF +
			   canopyQYConstants[12] * thePPF * theCO2 +
			   canopyQYConstants[13] * thePPF * theCO2squared +
			   canopyQYConstants[14] * thePPF * theCO2cubed +
			   canopyQYConstants[15] * thePPFsquared * oneOverCO2 +
			   canopyQYConstants[16] * thePPFsquared +
			   canopyQYConstants[17] * thePPFsquared * theCO2 +
			   canopyQYConstants[18] * thePPFsquared * theCO2squared +
			   canopyQYConstants[19] * thePPFsquared * theCO2cubed +
			   canopyQYConstants[20] * thePPFcubed * oneOverCO2 +
			   canopyQYConstants[21] * thePPFcubed +
			   canopyQYConstants[22] * thePPFcubed  * theCO2 +
			   canopyQYConstants[23] * thePPFcubed  * theCO2squared +
			   canopyQYConstants[24] * thePPFcubed  * theCO2cubed;
		return theCQYMax;
	}

	private float calculateCQY(){
		float CQYMax = calculateCQYMax();
		System.out.println("PlantImpl: CQYMax: "+CQYMax);
		if (getDaysOfGrowth() < getTimeTillCanopySenescence()){
			return CQYMax; 
		}
		else{
			return CQYMax - (CQYMax - getCQYMin()) * ((getDaysOfGrowth() - getTimeTillCanopySenescence()) / (getTimeTillCropMaturity() - getTimeTillCanopySenescence()));
		}
	}

	private float getPPF(){
		return myPPF;
	}

	protected float pow(float a, float b){
		return (new Double(Math.pow(a,b))).floatValue();
	}
	
	protected float exp(float a){
		return (new Double(Math.exp(a))).floatValue();
	}

	public void log(LogNode myLogHead){
		//If not initialized, fill in the log
		if (!logInitialized){
			myLogIndex = new LogIndex();
			LogNode typeHead = myLogHead.addChild("plant_type");
			myLogIndex.typeIndex = typeHead.addChild(""+getPlantType());
			logInitialized = true;
		}
		else{
			myLogIndex.typeIndex.setValue(""+getPlantType());
		}
	}

	/**
	* For fast reference to the log tree
	*/
	private class LogIndex{
		public LogNode typeIndex;
	}

}
