package biosim.server.simulation.food;

import biosim.idl.simulation.food.*;
import biosim.idl.simulation.air.*;
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
	protected float[] canopyClosureConstants;

	public PlantImpl(ShelfImpl pShelfImpl){
		myShelfImpl = pShelfImpl;
		canopyClosureConstants = new float[25];
	}

	public void reset(){
	}

	public void tick(){
	}
	
	public void harvest(){
	}
	
	public void shine(float pWatts){
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
	
	//Need to convert current CO2 levels to micromoles of CO2 / molecules of air
	private float calculateCO2(){
		return 0.1f;
	}
	
	private float calculateTimeTillCanopyClosure(float pPPF){
		float thePPF = pPPF;
		float theCO2 = calculateCO2();
		return 0.1f;
	}
	
	private float calculatePPFFractionAbsorbed(float pPPF){
		float PPFFractionAbsorbedMax = 0.93f;
		float timeTillCanopyClosure = calculateTimeTillCanopyClosure(pPPF);
		if (myAge < timeTillCanopyClosure){
			return PPFFractionAbsorbedMax * pow((myAge / timeTillCanopyClosure), getN());
		}
		else
			return PPFFractionAbsorbedMax;
	}
	
	private float calculateCQY(){
		return 0.1f;
	}
	
	private float calculatePPF(){
		return 0.1f;
	}
	
	protected float pow(float a, float b){
		return (new Double(Math.pow(a,b))).floatValue();
	}
	
	public abstract String getPlantType();

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
