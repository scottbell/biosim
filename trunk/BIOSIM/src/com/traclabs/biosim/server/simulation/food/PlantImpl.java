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
	private int myAge = 0;
	private LogIndex myLogIndex;
	private boolean logInitialized = false;
	protected ShelfImpl myShelfImpl;
	private float myPPF = 0f;
	private float myCurrentBiomass = 0f;
	private Breath myCurrentBreath;
	private float myWaterLevel;
	protected float[] canopyClosureConstants;
	protected float[] canopyQYConstants;

	public PlantImpl(ShelfImpl pShelfImpl){
		myShelfImpl = pShelfImpl;
		canopyClosureConstants = new float[25];
		canopyQYConstants = new float[25];
		Arrays.fill(canopyClosureConstants, 0f);
		Arrays.fill(canopyQYConstants, 0f);
		myCurrentBreath = new Breath(0f, 0f, 0f, 0f);
	}

	public void reset(){
		myPPF = 0f;
		myCurrentBiomass = 0f;
		myAge = 0;
		myCurrentBreath = new Breath(0f, 0f, 0f, 0f);
	}

	public void tick(){
		growBiomass();
	}

	public float harvest(){
		float biomassToReturn = myCurrentBiomass;
		myCurrentBiomass = 0f;
		myAge = 0;
		return biomassToReturn;
	}

	public void shine(float pPPF){
		myPPF = pPPF;
	}
	
	private float calculateDailyCanopyTranspirationRate(){
		//assumes water is at 20 C and 101kPA of total pressure
		float airPressure = myShelfImpl.getBiomassRSImpl().getAirInputs()[0].getTotalPressure();
		return 3600f * getPhotoperiod() * (18.015f / 998.23f) * calculateCanopySurfaceConductance() * (calculateVaporPressureDeficit() / airPressure);
	}
	
	private float calculateVaporPressureDeficit(){
		float saturatedMoistureVaporPressure = calculateSaturatedMoistureVaporPressure();
		return saturatedMoistureVaporPressure - calculateActualMoistureVaporPressure(saturatedMoistureVaporPressure);
	}
	
	private float calculateSaturatedMoistureVaporPressure(){
		float temperatureLight = myShelfImpl.getBiomassRSImpl().getAirInputs()[0].getTemperature();
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
		myWaterLevel = myShelfImpl.takeWater(getWaterNeeded());
		float molecularWeightOfCarbon = 12.011f; // g/mol
		float dailyCarbonGain = calculateDailyCarbonGain();
		float cropGrowthRate = molecularWeightOfCarbon * dailyCarbonGain / getBCF();
		myCurrentBiomass += cropGrowthRate * 1000 * myShelfImpl.getCropArea(); //in kilograms
		float O2Produced = getOPF() * dailyCarbonGain * myShelfImpl.getCropArea() / 24; //in mol of oxygen per hour
		myShelfImpl.getBiomassRSImpl().getAirOutputs()[0].addO2Moles(O2Produced);
		myShelfImpl.getBiomassRSImpl().addAirOutputActualFlowRates(0,O2Produced);
	}
	
	//in g/meters^2*hour
	private float calculateDailyCarbonGain(){
		return 0.0036f * getPhotoperiod() * getCarbonUseEfficiency24() * calculatePPFFractionAbsorbed() * calculateCQY() * getPPF() * 24;
	}

	protected abstract float getBCF();
	protected abstract float getCarbonUseEfficiency24();
	protected abstract float getPhotoperiod();
	protected abstract float getN();
	protected abstract float getTimeTillCanopySenescence();
	protected abstract float getCQYMin();
	protected abstract float getTimeTillCropMaturity();
	protected abstract float getWaterNeeded();
	protected abstract float getOPF();
	protected abstract float calculateCanopyStomatalConductance();
	protected abstract float calculateAtmosphericAeroDynamicConductance();
	public abstract float getPPFNeeded();
	public abstract PlantType getPlantType();

	//Convert current CO2 levels to micromoles of CO2 / molecules of air
	protected float calculateCO2(){
		return (myCurrentBreath.CO2 * pow (10,-6)) / (myCurrentBreath.O2 + myCurrentBreath.CO2 + myCurrentBreath.other);
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


		float theCO2 = calculateCO2();
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


		float theCO2 = calculateCO2();
		float oneOverCO2 = 1f / theCO2;
		float theCO2squared = pow(theCO2, 2f);
		float theCO2cubed = pow(theCO2, 3f);

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
