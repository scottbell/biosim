/**
 * The Food Store Implementation.  Food placed here by the food processor is eaten by the crew memebers.
 *
 * @author    Scott Bell
 */

package biosim.server.food;

import biosim.idl.food.*;
import biosim.server.framework.*;

public class FoodStoreImpl extends StoreImpl implements FoodStoreOperations {
	/**
	* Returns the name of this module (FoodStore)
	* @return the name of this module
	*/
	public String getModuleName(){
		return "FoodStore";
	}
}
