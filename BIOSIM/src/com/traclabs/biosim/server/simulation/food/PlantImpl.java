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
	private float myAveragePPF = 0f;
	private float myTotalPPF = 0f;
	private int myNumberOfPPFReadings = 0;
	private float myAverageCO2Concentration = 0f;
	private float myTotalCO2Concentration = 0f;
	private int myNumberOfCO2ConcentrationReadings = 0;
	//in dry weight
	private float myCurrentTotalWetBiomass = 0f;
	private float myCurrentEdibleWetBiomass = 0f;
	private float myCurrentDryBiomass = 0f;
	private float myLastTotalWetBiomass = 0f;
	private float myLastEdibleWetBiomass = 0f;
	private float myWaterNeeded = 0f;
	private float myWaterLevel = 0f;
	private float CQY = 0f;
	private float carbonUseEfficiency24 = 0f;
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
		myAveragePPF = 0f;
		myAverageCO2Concentration = 0f;
		myTotalCO2Concentration = 0f;
		myTotalPPF = 0f;
		myNumberOfCO2ConcentrationReadings = 0;
		myNumberOfPPFReadings = 0;
		myCurrentTotalWetBiomass = 0f;
		myLastTotalWetBiomass = 0f;
		carbonUseEfficiency24 = 0f;
		myCurrentDryBiomass = 0f;
		myAge = 0;
		CQY = 0f;
	}

	public void tick(){
		System.out.println("PlantImpl: **************Begin plant tick***********");
		myAge++;
		calculateAverageCO2Concentration();
		growBiomass();
		System.out.println("PlantImpl: **************End plant tick***********");
	}

	public float harvest(){
		float biomassToReturn = myCurrentTotalWetBiomass;
		myCurrentTotalWetBiomass = 0f;
		myLastTotalWetBiomass = 0f;
		myCurrentEdibleWetBiomass = 0f;
		myLastEdibleWetBiomass = 0f;
		myNumberOfCO2ConcentrationReadings = 0;
		myTotalCO2Concentration = 0f;
		myTotalPPF = 0f;
		myNumberOfPPFReadings = 0;
		myWaterNeeded = 0f;
		myWaterLevel = 0f;
		myCurrentDryBiomass = 0f;
		myAge = 0;
		return biomassToReturn;
	}

	public void shine(float pPPF){
		myTotalPPF += pPPF;
		myNumberOfPPFReadings++;
		myAveragePPF = myTotalPPF / myNumberOfPPFReadings;
	}
	
	private float calculateDailyCanopyTranspirationRate(){
		//assumes water is at 20 C and 101kPA of total pressure
		float airPressure = myShelfImpl.getBiomassRSImpl().getAirOutputs()[0].getTotalPressure();
		float canopySurfaceConductance = calculateCanopySurfaceConductance();
		float vaporPressureDeficit = calculateVaporPressureDeficit();
		System.out.println("PlantImpl: airPressure: "+airPressure);
		System.out.println("PlantImpl: canopySurfaceConductance: "+canopySurfaceConductance);
		System.out.println("PlantImpl: vaporPressureDeficit: "+vaporPressureDeficit);
		return 3600f * getPhotoperiod() * (18.015f / 998.23f) * canopySurfaceConductance * (vaporPressureDeficit / airPressure);
	}
	
	protected float calculateVaporPressureDeficit(){
		float saturatedMoistureVaporPressure = calculateSaturatedMoistureVaporPressure();
		float actualMoistureVaporPressure = calculateActualMoistureVaporPressure();
		System.out.println("PlantImpl: saturatedMoistureVaporPressure: "+saturatedMoistureVaporPressure);
		System.out.println("PlantImpl: actualMoistureVaporPressure: "+actualMoistureVaporPressure);
		float vaporPressureDeficit = saturatedMoistureVaporPressure - calculateActualMoistureVaporPressure();
		if (vaporPressureDeficit < 0)
			return 0f;
		else
			return vaporPressureDeficit;
	}
	
	private float calculateSaturatedMoistureVaporPressure(){
		float temperatureLight = myShelfImpl.getBiomassRSImpl().getAirOutputs()[0].getTemperature();
		float exponent = (17.4f * temperatureLight) / (temperatureLight + 239f);
		System.out.println("PlantImpl: temperatureLight: "+temperatureLight);
		System.out.println("PlantImpl: exponent: "+exponent);
		return 0.611f * exp(exponent);
	}
	
	private float calculateActualMoistureVaporPressure(){
		return myShelfImpl.getBiomassRSImpl().getAirInputs()[0].getWaterPressure();
	}
	
	protected float calculateNetCanopyPhotosynthesis(){
		float plantGrowthDiurnalCycle = 24f;
		float grossCanopyPhotosynthesis = calculateGrossCanopyPhotosynthesis();
		float photoperiod = getPhotoperiod();
		System.out.println("PlantImpl: plantGrowthDiurnalCycle: "+plantGrowthDiurnalCycle);
		System.out.println("PlantImpl: carbonUseEfficiency24: "+carbonUseEfficiency24);
		System.out.println("PlantImpl: grossCanopyPhotosynthesis: "+grossCanopyPhotosynthesis);
		System.out.println("PlantImpl: photoperiod: "+photoperiod);
		return (((plantGrowthDiurnalCycle - photoperiod) / plantGrowthDiurnalCycle) + ((photoperiod * carbonUseEfficiency24) / plantGrowthDiurnalCycle)) * grossCanopyPhotosynthesis;
	}
	
	private float calculateGrossCanopyPhotosynthesis(){
		float PPF = getAveragePPF();
		float PPFFractionAbsorbed = calculatePPFFractionAbsorbed();
		System.out.println("PlantImpl: CQY: "+CQY);
		System.out.println("PlantImpl: PPF: "+PPF);
		System.out.println("PlantImpl: PPFFractionAbsorbed: "+PPFFractionAbsorbed);
		return PPFFractionAbsorbed * CQY * PPF;
	}
	
	private float calculateCanopySurfaceConductance(){
		float canopyStomatalConductance = calculateCanopyStomatalConductance();
		float atmosphericAeroDynamicConductance = calculateAtmosphericAeroDynamicConductance();
		System.out.println("PlantImpl: canopyStomatalConductance: "+canopyStomatalConductance);
		System.out.println("PlantImpl: atmosphericAeroDynamicConductance: "+atmosphericAeroDynamicConductance);
		return (atmosphericAeroDynamicConductance * canopyStomatalConductance) / (canopyStomatalConductance + atmosphericAeroDynamicConductance);
	}
	
	private void growBiomass(){
		//Biomass Grown
		float molecularWeightOfCarbon = 12.011f; // g/mol
		CQY = calculateCQY();
		carbonUseEfficiency24 = getCarbonUseEfficiency24();
		float dailyCarbonGain = calculateDailyCarbonGain();
		System.out.println("PlantImpl: dailyCarbonGain: "+dailyCarbonGain);
		float cropGrowthRate = molecularWeightOfCarbon * (dailyCarbonGain / getBCF());
		System.out.println("PlantImpl: cropGrowthRate: "+cropGrowthRate);
		myCurrentDryBiomass += (cropGrowthRate / 1000 / 24f); //in kilograms
		System.out.println("PlantImpl: myCurrentDryBiomass: "+myCurrentDryBiomass);
		myLastTotalWetBiomass = myCurrentTotalWetBiomass;
		myLastEdibleWetBiomass = myCurrentEdibleWetBiomass;
		myCurrentTotalWetBiomass = (myCurrentDryBiomass * getFreshFactor());
		myCurrentEdibleWetBiomass = getFractionOfEdibleBiomass() * myCurrentTotalWetBiomass;
		System.out.println("PlantImpl: getFreshFactor(): "+getFreshFactor());
		System.out.println("PlantImpl: myCurrentTotalWetBiomass: "+myCurrentTotalWetBiomass);
		System.out.println("PlantImpl: myCurrentEdibleWetBiomass: "+myCurrentEdibleWetBiomass);
		
		//Water In
		myWaterNeeded = calculateWaterUptake();
		myWaterLevel = myShelfImpl.takeWater(myWaterNeeded);
		System.out.println("PlantImpl: myWaterNeeded: "+myWaterNeeded);
		System.out.println("PlantImpl: myWaterLevel: "+myWaterLevel);
		
		//Breathe Air
		float molesOfCO2ToInhale = dailyCarbonGain / 24f;
		float molesOfCO2Inhaled = myShelfImpl.getBiomassRSImpl().getAirInputs()[0].takeCO2Moles(molesOfCO2ToInhale);
		myShelfImpl.getBiomassRSImpl().addAirInputActualFlowRates(0,molesOfCO2Inhaled);
		System.out.println("PlantImpl: molesOfCO2ToInhale: "+molesOfCO2ToInhale);
		System.out.println("PlantImpl: molesOfCO2Inhaled: "+molesOfCO2Inhaled);
		
		//Exhale Air
		float O2Produced = getOPF() * dailyCarbonGain * myShelfImpl.getCropArea() / 24f; //in mol of oxygen per hour
		float O2Exhaled = myShelfImpl.getBiomassRSImpl().getAirOutputs()[0].addO2Moles(O2Produced);
		myShelfImpl.getBiomassRSImpl().addAirOutputActualFlowRates(0,O2Exhaled);
		System.out.println("PlantImpl: O2Produced: "+O2Produced);
		System.out.println("PlantImpl: O2Exhaled: "+O2Exhaled);
		
		//Water Vapor Produced
		float litersOfWaterProduced = calculateDailyCanopyTranspirationRate() / 24f;
		float molesOfWaterProduced = (litersOfWaterProduced * 1000 * 18); //1000 liters per milliter, 1 gram per millilters, 18 moles per gram
		float molesOfWaterAdded = myShelfImpl.getBiomassRSImpl().getAirOutputs()[0].addWaterMoles(molesOfWaterProduced);
		myShelfImpl.getBiomassRSImpl().addAirOutputActualFlowRates(0,molesOfWaterAdded);
		System.out.println("PlantImpl: litersOfWaterProduced: "+litersOfWaterProduced);
		System.out.println("PlantImpl: molesOfWaterProduced: "+molesOfWaterProduced);
		System.out.println("PlantImpl: molesOfWaterAdded: "+molesOfWaterAdded);
	}
	
	//in g/meters^2*day
	private float calculateDailyCarbonGain(){
		float photoperiod = getPhotoperiod();
		float PPFFractionAbsorbed = calculatePPFFractionAbsorbed();
		float PPF = getAveragePPF();
		System.out.println("PlantImpl: photoperiod: "+photoperiod);
		System.out.println("PlantImpl: carbonUseEfficiency24: "+carbonUseEfficiency24);
		System.out.println("PlantImpl: PPFFractionAbsorbed: "+PPFFractionAbsorbed);
		System.out.println("PlantImpl: CQY: "+CQY);
		System.out.println("PlantImpl: PPF: "+PPF);
		return (0.0036f * photoperiod * carbonUseEfficiency24 * myShelfImpl.getCropArea() * PPFFractionAbsorbed * CQY * PPF);
	}
	
	private float calculateWaterUptake(){
		float dailyCanopyTranspirationRate = calculateDailyCanopyTranspirationRate();
		float wetIncoporatedWaterUptake = calculateWetIncoporatedWaterUptake();
		float dryIncoporatedWaterUptake = calculateDryIncoporatedWaterUptake(dailyCanopyTranspirationRate, wetIncoporatedWaterUptake);
		System.out.println("PlantImpl: dailyCanopyTranspirationRate: "+dailyCanopyTranspirationRate);
		System.out.println("PlantImpl: wetIncoporatedWaterUptake: "+wetIncoporatedWaterUptake);
		System.out.println("PlantImpl: dryIncoporatedWaterUptake: "+dryIncoporatedWaterUptake);
		return ((dailyCanopyTranspirationRate + wetIncoporatedWaterUptake + dryIncoporatedWaterUptake) * myShelfImpl.getCropArea()) / 24f;
	}
	
	private float calculateWetIncoporatedWaterUptake(){
		float myCurrentInedibleWetBiomass = myCurrentTotalWetBiomass - myCurrentEdibleWetBiomass;
		float myLastInedibleWetBiomass = myLastTotalWetBiomass - myLastEdibleWetBiomass;
		if (myCurrentInedibleWetBiomass < 0)
			myCurrentInedibleWetBiomass = 0f;
		if (myLastInedibleWetBiomass < 0)
			myLastInedibleWetBiomass = 0f;
		float densityOfWater = myShelfImpl.getBiomassRSImpl().getAirInputs()[0].getWaterDensity();
		System.out.println("PlantImpl: myCurrentTotalWetBiomass: "+myCurrentTotalWetBiomass);
		System.out.println("PlantImpl: myCurrentEdibleWetBiomass: "+myCurrentEdibleWetBiomass);
		System.out.println("PlantImpl: myLastTotalWetBiomass: "+myLastTotalWetBiomass);
		System.out.println("PlantImpl: myLastEdibleWetBiomass: "+myLastEdibleWetBiomass);
		System.out.println("PlantImpl: myCurrentInedibleWetBiomass: "+myCurrentInedibleWetBiomass);
		System.out.println("PlantImpl: myLastInedibleWetBiomass: "+myLastInedibleWetBiomass);
		System.out.println("PlantImpl: densityOfWater: "+densityOfWater);
		return (((myCurrentEdibleWetBiomass - myLastEdibleWetBiomass) * getEdibleFreshBasisWaterContent()) + ((myCurrentInedibleWetBiomass + myLastInedibleWetBiomass) * getInedibleFreshBasisWaterContent())) / densityOfWater;
	}
	
	private float calculateDryIncoporatedWaterUptake(float pDailyCanopyTranspirationRate, float pWetIncoporatedWaterUptake){
		return (pDailyCanopyTranspirationRate + pWetIncoporatedWaterUptake) / 500;
	}
	
	public float getWaterNeeded(){
		return myWaterNeeded;
	}
	
	protected float getAverageCO2Concentration(){
		return myAverageCO2Concentration;
	}

	//Convert current CO2 levels to micromoles of CO2 / moles of air
	private void calculateAverageCO2Concentration(){
		SimEnvironment myEnvironment = myShelfImpl.getBiomassRSImpl().getAirOutputs()[0];
		float CO2MicroMoles = myEnvironment.getCO2Moles() * pow (10,6); //in micro moles
		float airMoles = myEnvironment.getTotalMoles(); //in moles
		if (CO2MicroMoles <=0)
			CO2MicroMoles = pow(1f, -30f);
		else if (airMoles <= 0)
			airMoles = pow(1f, -30f);
		System.out.println("PlantImpl: CO2MicroMoles: "+CO2MicroMoles);
		System.out.println("PlantImpl: airMoles: "+airMoles);
		myTotalCO2Concentration += (CO2MicroMoles / airMoles);
		myNumberOfCO2ConcentrationReadings ++;
		myAverageCO2Concentration = myTotalCO2Concentration / myNumberOfCO2ConcentrationReadings;
	}
	
	//returns the age in days
	private float getDaysOfGrowth(){
		Float myAgeInFloat = new Float(myAge);
		float daysOfGrowth = myAgeInFloat.floatValue() / 24f;
		System.out.println("PlantImpl: myAgeInFloat: "+myAgeInFloat.floatValue());
		System.out.println("PlantImpl: daysOfGrowth: "+daysOfGrowth);
		return daysOfGrowth;
	}

	private float calculateTimeTillCanopyClosure(){
		float thePPF = getAveragePPF();
		float oneOverPPf = 1f / thePPF;
		float thePPFsquared = pow(thePPF, 2f);
		float thePPFcubed = pow(thePPF, 3f);


		float theCO2 = getAverageCO2Concentration();
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
		if (tA < 0){
			tA = 0;
			System.out.println("PlantImpl: Time till canopy closure is negative!");
		}
		return tA;
	}
	
	private float calculatePPFFractionAbsorbed(){
		float PPFFractionAbsorbedMax = 0.93f;
		float timeTillCanopyClosure = calculateTimeTillCanopyClosure();
		System.out.println("PlantImpl: PPFFractionAbsorbedMax: "+PPFFractionAbsorbedMax);
		System.out.println("PlantImpl: timeTillCanopyClosure: "+timeTillCanopyClosure);
		System.out.println("PlantImpl: getDaysOfGrowth(): "+getDaysOfGrowth());
		System.out.println("PlantImpl: getN(): "+getN());
		if (getDaysOfGrowth() < timeTillCanopyClosure){
			return PPFFractionAbsorbedMax * pow((getDaysOfGrowth() / timeTillCanopyClosure), getN());
		}
		else
			return PPFFractionAbsorbedMax;
	}
	
	private float calculateCQYMax(){
		float thePPF = getAveragePPF();
		float oneOverPPf = 1f / thePPF;
		float thePPFsquared = pow(thePPF, 2f);
		float thePPFcubed = pow(thePPF, 3f);		
		System.out.println("PlantImpl: thePPF: "+thePPF);
		System.out.println("PlantImpl: oneOverPPf: "+oneOverPPf);
		System.out.println("PlantImpl: thePPFsquared: "+thePPFsquared);
		System.out.println("PlantImpl: thePPFcubed: "+thePPFcubed);

		float theCO2 = getAverageCO2Concentration();
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
		if (theCQYMax < 0){
			theCQYMax = 0;
			System.out.println("PlantImpl: CQYMax is negative!");
		}
		return theCQYMax;
	}

	private float calculateCQY(){
		float CQYMax = calculateCQYMax();
		float timeTillCanopySenescence = getTimeTillCanopySenescence();
		System.out.println("PlantImpl: CQYMax: "+CQYMax);
		System.out.println("PlantImpl: timeTillCanopySenescence: "+timeTillCanopySenescence);
		if (getDaysOfGrowth() < getTimeTillCanopySenescence()){
			return CQYMax; 
		}
		else{
			float CQYMin = getCQYMin();
			float daysOfGrowth = getDaysOfGrowth();
			float timeTillCropMaturity = getTimeTillCropMaturity();
			float calculatedCQY = CQYMax - ((CQYMax - CQYMin) * ((daysOfGrowth - timeTillCanopySenescence)) / (timeTillCropMaturity - timeTillCanopySenescence));
			System.out.println("PlantImpl: CQYMin: "+CQYMin);
			System.out.println("PlantImpl: daysOfGrowth: "+daysOfGrowth);
			System.out.println("PlantImpl: timeTillCropMaturity: "+timeTillCropMaturity);
			if (calculatedCQY < 0f)
				return 0f;
			else
				return calculatedCQY;
		}
	}

	private float getAveragePPF(){
		return myAveragePPF;
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
