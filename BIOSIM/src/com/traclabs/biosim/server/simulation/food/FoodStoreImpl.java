package biosim.server.simulation.food;

import biosim.idl.simulation.food.*;
import biosim.server.simulation.framework.*;
/**
 * The Food Store Implementation.  Food placed here by the food processor is eaten by the crew memebers.
 *
 * @author    Scott Bell
 */

public class FoodStoreImpl extends StoreImpl implements FoodStoreOperations{
	public FoodStoreImpl(int pID){
		super(pID);
	}
	/**
	* Returns the name of this module (FoodStore)
	* @return the name of this module
	*/
	public String getModuleName(){
		return "FoodStore"+getID();
	}
}
