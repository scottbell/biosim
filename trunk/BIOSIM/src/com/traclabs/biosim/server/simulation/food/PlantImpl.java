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
	int myAge = 0;
	private LogIndex myLogIndex;
	private boolean logInitialized = false;
	private ShelfImpl myShelfImpl;
	private float waterNeeded = .01f;
	private float myPPF = 0f;
	protected float[] canopyClosureConstants;

	public PlantImpl(ShelfImpl pShelfImpl){
		myShelfImpl = pShelfImpl;
		canopyClosureConstants = new float[25];
		Arrays.fill(canopyClosureConstants, 0f);
	}

	public void reset(){
		myPPF = 0f;
	}

	public void tick(){
	}

	public void harvest(){
	}

	public void shine(float pPPF){
		myPPF = pPPF;
	}

	public float getWaterNeeded(){
		return waterNeeded;
	}
	

	private void calculateBiomass(){
		float molecularWeightOfCarbon = 12.011f; // g/mol
		float cropGrowthRate = molecularWeightOfCarbon * calculateDailyCarbonGain() / getBCF();
	}

	private float calculateDailyCarbonGain(){
		float thePPF = calculatePPF();
		return 0.0036f * getPhotoperiod() * getCarbonUseEfficiency24() * calculatePPFFractionAbsorbed(thePPF) * calculateCQY() * thePPF;
	}

	protected abstract float getBCF();
	protected abstract float getCarbonUseEfficiency24();
	protected abstract float getPhotoperiod();
	protected abstract float getN();
	protected abstract float getTimeTillCanopySenescence();
	protected abstract float getCQYMin();
	protected abstract float getTimeTillCropMaturity();
	public abstract float getPPFNeeded();
	public abstract PlantType getPlantType();

	//Need to convert current CO2 levels to micromoles of CO2 / molecules of air
	private float calculateCO2(){
		return 0.1f;
	}
	
	//returns the age in days
	private float getDaysOfGrowth(){
		return myAge / 24f;
	}

	private float calculateTimeTillCanopyClosure(float pPPF){
		float thePPF = pPPF;
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

	private float calculatePPFFractionAbsorbed(float pPPF){
		float PPFFractionAbsorbedMax = 0.93f;
		float timeTillCanopyClosure = calculateTimeTillCanopyClosure(pPPF);
		if (getDaysOfGrowth() < timeTillCanopyClosure){
			return PPFFractionAbsorbedMax * pow((getDaysOfGrowth() / timeTillCanopyClosure), getN());
		}
		else
			return PPFFractionAbsorbedMax;
	}
	
	private float calculateCQYMax(){
		return 0.1f;
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

	private float calculatePPF(){
		return myPPF;
	}

	protected float pow(float a, float b){
		return (new Double(Math.pow(a,b))).floatValue();
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
