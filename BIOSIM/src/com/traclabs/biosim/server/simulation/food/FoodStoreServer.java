package biosim.server.simulation.food;

import biosim.server.framework.*;
import biosim.idl.simulation.food.*;
/**
 * The Food Store Server.  Creates an instance of the Food Processor and registers it with the nameserver.
 *
 * @author    Scott Bell
 */

public class FoodStoreServer extends GenericServer{
	
	/**
	* Instantiates the server and binds it to the name server.
	* @param args aren't used for anything
	*/
	public static void main(String args[]) {
		FoodStoreServer myServer = new FoodStoreServer();
		FoodStoreImpl myFoodStore = new FoodStoreImpl(myServer.getIDfromArgs(args), myServer.getNamefromArgs(args));
		myServer.registerServerAndRun(new FoodStorePOATie(myFoodStore), myFoodStore.getModuleName(), myFoodStore.getID());
	}
}

