package biosim.server.air;

import biosim.idl.air.*;
import biosim.idl.util.*;
import biosim.server.util.*;
import biosim.server.framework.*;

/**
 * The Air Revitalization System Implementation.  Takes in Air (O2, CO2, other) from the environment and
 * power from the Power Production System and produces air with less CO2 and more O2.
 *
 * @author    Scott Bell
 */

public class AirRSImpl extends BioModuleImpl implements AirRSOperations {
	private LogIndex myLogIndex;
	private VCCR myVCCR;
	private CO2Tank myCO2Tank;
	private CRS myCRS;
	private H2Tank myH2Tank;
	private CH4Tank myCH4Tank;
	private OGS myOGS;
	
	public AirRSImpl(){
		myVCCR = new VCCR(this);
		myCO2Tank = new CO2Tank(this);
		myCRS = new CRS(this);
		myH2Tank = new H2Tank(this);
		myCH4Tank = new CH4Tank(this);
		myOGS = new OGS(this);
	}
	
	public VCCR getVCCR(){
		return myVCCR;
	}
	
	public CO2Tank getCO2Tank(){
		return myCO2Tank;
	}
	
	public CRS getCRS(){
		return myCRS;
	}
	
	public H2Tank getH2Tank(){
		return myH2Tank;
	}
	
	public CH4Tank getCH4Tank(){
		return myCH4Tank;
	}
	
	public OGS getOGS(){
		return myOGS;
	}
	
	/**
	* Returns the power consumption (in watts) of the AirRS at the current tick.
	* @return the power consumed (in watts) at the current tick
	*/
	public float getPowerConsumed(){
		return 0;
	}
	
	/**
	* Returns the CO2 consumption (in liters) of the AirRS at the current tick.
	* @return the CO2 consumed at the current tick
	*/
	public float getCO2Consumed(){
		return 0;
	}
	
	/**
	* Returns the O2 produced (in liters) of the AirRS at the current tick.
	* @return the O2 produced (in liters) at the current tick
	*/
	public float getO2Produced(){
		return 0;
	}
	
	/**
	* Returns the CO2 produced (in liters) of the AirRS at the current tick.
	* @return the CO2 produced (in liters) at the current tick
	*/
	public float getCO2Produced(){
		return 0;
	}
	
	/**
	* Processes a tick by collecting referernces (if needed), resources, and pushing the new air out.
	*/
	public void tick(){
		myVCCR.tick();
		myCO2Tank.tick();
		myCRS.tick();
		myH2Tank.tick();
		myCH4Tank.tick();
		myOGS.tick();
		if (moduleLogging)
			log();
	}
	
	/**
	* Resets production/consumption levels.
	*/
	public void reset(){
		myVCCR.reset();
		myCO2Tank.reset();
		myCRS.reset();
		myH2Tank.reset();
		myCH4Tank.reset();
		myOGS.reset();
	}
	
	/**
	* Returns the name of this module (AirRS)
	* @return the name of this module
	*/
	public String getModuleName(){
		return "AirRS";
	}
	
	private void log(){
		//If not initialized, fill in the log
		if (!logInitialized){
			myLogIndex = new LogIndex();
			LogNode CO2NeededHead = myLog.getHead().addChild("CO2 Needed");
			LogNode currentO2ProducedHead = myLog.getHead().addChild("O2 Produced");
			LogNode currentCO2ProducedHead = myLog.getHead().addChild("CO2 Produced");
			LogNode currentCO2ConsumedHead = myLog.getHead().addChild("CO2 Consumed");
			LogNode currentPowerConsumedHead = myLog.getHead().addChild("Power Consumed");
			logInitialized = true;
		}
		else{
		}
		sendLog(myLog);
	}
	
	/**
	* For fast reference to the log tree
	*/
	private class LogIndex{
		public LogNode CO2NeededIndex;
		public LogNode currentO2ProducedIndex;
		public LogNode currentCO2ConsumedIndex;
		public LogNode currentCO2ProducedIndex;
		public LogNode currentPowerConsumedIndex;
	}
}
