package biosim.server.simulation.air;

/**
 * The abstract class all the air subsystems derive from (the VCCR, CRS, OGS, ...)
 *
 * @author    Scott Bell
 */

public abstract class AirRSSubSystem{
	//The power consumed (in watts) by this air subsystem at this tick (default)
	protected float currentPowerConsumed = 0;
	//During any given tick, this much power (in watts) is needed for a air subsystem (default)
	private float powerNeeded = 100f;
	protected AirRSImpl myAirRS;
	//Flag to determine whether the air subsystem has received enough power for this tick
	protected boolean hasEnoughPower = false;
	private boolean logInitialized = false;

	public AirRSSubSystem(AirRSImpl pAirRSImpl){
		myAirRS = pAirRSImpl;
	}
	
	public abstract void reset();
	
	public float getPowerConsumed(){
		return currentPowerConsumed;
	}

	/**
	* Returns whether the air subsytem has enough power at the current tick.
	* @return <code>true</code> if the air subsytem has enough power to function, <code>false</code> if not
	*/
	public boolean hasPower(){
		return hasEnoughPower;
	}

	/**
	* Adds power to the subsystem for this tick
	*/
	protected void gatherPower(){
		float gatheredPower = myAirRS.getFractionalResourceFromStore(myAirRS.getPowerInputs(), myAirRS.getPowerInputMaxFlowRates(), myAirRS.getPowerInputDesiredFlowRates(), myAirRS.getPowerInputActualFlowRates(), powerNeeded, 1f / myAirRS.getSubsystemsConsumingPower());
		if (gatheredPower < powerNeeded)
			hasEnoughPower = false;
		else
			hasEnoughPower = true;
	}

	public void tick(){
		gatherPower();
	}

	public void log(){
		/*
			LogNode powerNeededHead = myHead.addChild("Power Needed");
			myLogIndex.powerNeededIndex = powerNeededHead.addChild(""+powerNeeded);
			*/
	}

}
