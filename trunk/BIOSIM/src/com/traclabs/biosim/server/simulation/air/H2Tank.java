package biosim.server.simulation.air;

import biosim.idl.util.log.*;
import biosim.idl.simulation.environment.*;
import biosim.idl.simulation.power.*;
/**
 * H2Tank Subsystem
 *
 * @author    Scott Bell
 */

public class H2Tank extends AirRSSubSystem{
	private float H2Drawn = 0;

	public H2Tank(AirRSImpl pAirRSImpl){
		super(pAirRSImpl);
		hasEnoughPower = true;
	}
	
	public float takeH2(float molesToDraw){
		molesToDraw = myAirRS.randomFilter(molesToDraw);
		H2Drawn += molesToDraw;
		return molesToDraw;
	}
	
	public void reset(){
		currentPowerConsumed = 0;
		hasEnoughPower = true;
		H2Drawn = 0;
	}
	
	public void tick(){
	}

}
