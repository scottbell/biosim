/**
 * This interface can be implemented by a class and registered with the BioSimulator so that on 
 * each tick of the BioSimulator, it's processTick method is invoked.
 *
 * @author    Scott Bell
 */	
package biosim.client.framework;

public interface BioSimulatorListener{
	/**
	* The method invoked by the BioSimulator after a tick has finished.
	*/
	public void processTick();
}
