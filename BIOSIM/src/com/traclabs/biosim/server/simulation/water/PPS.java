/**
 * The PPS is the last stage of water purification.  It takes water from the AES, filters it, and
 * waits for the WaterRS to send the now clean water to the potable water store
 *
 * @author    Scott Bell
 */

package biosim.server.water;

public class PPS extends WaterRSSubSystem{
	/**
	* Constructor that creates the PPS, the power required is 168 watts
	* @param pWaterRSImpl The Water RS system the AES is contained in
	*/
	public PPS(WaterRSImpl pWaterRSImpl){
		super(pWaterRSImpl);
		powerNeeded =168; 
	}
	
	/**
	* Flushes the water from this subsystem (via the WaterRS) to the Potable Water Store
	*/
	public float takeWater(){
		float potableWaterProduced = waterLevel;
		waterLevel = 0;
		return potableWaterProduced;
	}
}
