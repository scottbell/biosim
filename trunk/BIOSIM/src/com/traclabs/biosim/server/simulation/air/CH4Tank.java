package biosim.server.air;

import biosim.idl.util.*;
import biosim.idl.environment.*;
import biosim.idl.power.*;
/**
 * CH4Tank Subsystem
 *
 * @author    Scott Bell
 */

public class CH4Tank extends AirRSSubSystem{
	private float CH4Level = 0;

	public CH4Tank(AirRSImpl pAirRSImpl){
		super(pAirRSImpl);
		hasEnoughPower = true;
	}
	
	public void addCH4(float litersToAdd){
		CH4Level += litersToAdd;
	}
	
	public void reset(){
		currentPowerConsumed = 0;
		hasEnoughPower = true;
		CH4Level = 0;
	}
	
	public void tick(){
	}

}
