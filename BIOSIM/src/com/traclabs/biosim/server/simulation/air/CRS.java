package biosim.server.air;

import biosim.idl.util.*;
import biosim.idl.environment.*;
import biosim.idl.power.*;
import biosim.server.util.*;
/**
 * CRS Subsystem
 *
 * @author    Scott Bell
 */

public class CRS extends AirRSSubSystem{
	private float H2Needed = 576.0f;
	private float CO2Needed = 576.0f;
	private boolean enoughCO2 = false;
	private boolean enoughH2 = false;
	private float currentCO2Consumed = 0;
	private float currentH2Consumed = 0;
	private float currentH2OProduced = 0;
	private float currentCH4Produced = 0;
	private CO2Tank myCO2Tank;
	private H2Tank myH2Tank;
	private CH4Tank myCH4Tank;
	private OGS myOGS;

	public CRS(AirRSImpl pAirRSImpl){
		super(pAirRSImpl);
	}
	
	public boolean hasEnoughCO2(){
		return enoughCO2;
	}
	
	public boolean hasEnoughH2(){
		return enoughH2;
	}
	
	/**
	* Collects references to subsystems needed for putting/getting resources
	*/
	private void collectReferences(){
		if (!hasCollectedReferences){
			try{
				myCO2Tank = myAirRS.getCO2Tank();
				myH2Tank = myAirRS.getH2Tank();
				myCH4Tank = myAirRS.getCH4Tank();
				myOGS = myAirRS.getOGS();
				myPowerStore = PowerStoreHelper.narrow(OrbUtils.getNCRef().resolve_str("PowerStore"));
				hasCollectedReferences = true;
			}
			catch (org.omg.CORBA.UserException e){
				e.printStackTrace(System.out);
			}
		}
	}
	
	private void gatherGasses(){
		currentCO2Consumed = myCO2Tank.takeCO2(CO2Needed);
		currentH2Consumed = myOGS.takeH2(H2Needed);
		if (CO2Needed < currentCO2Consumed)
			enoughCO2 = false;
		else
			enoughCO2 = true;
		if (currentH2Consumed < H2Needed){
			//Get remainder from H2 tank
			currentH2Consumed += myH2Tank.takeH2(H2Needed - currentH2Consumed);
			//check again
			if (currentH2Consumed < H2Needed)
				enoughH2 = false;
			else
				enoughH2 = true;
		}
		else
			enoughH2 = true;
	}
	
	private void pushGasses(){
		currentH2OProduced = new Double((currentH2Consumed + currentCO2Consumed) * .80).floatValue();
		myOGS.addH2O(currentH2OProduced);
		currentCH4Produced = new Double((currentH2Consumed + currentCO2Consumed) * .20).floatValue();
		myCH4Tank.addCH4(currentCH4Produced);
	}
	
	public void reset(){
		currentPowerConsumed = 0;
		hasEnoughPower = false;
		enoughCO2 = false;
		enoughH2 = false;
		currentCO2Consumed = 0;
		currentH2Consumed = 0;
		currentH2OProduced = 0;
		currentCH4Produced = 0;
	}
	
	public void tick(){
		collectReferences();
		gatherPower();
		if (hasEnoughPower){
			gatherGasses();
			pushGasses();
		}
	}

}
