package com.traclabs.biosim.server.simulation.food;

import com.traclabs.biosim.idl.simulation.food.FoodStorePOATie;
import com.traclabs.biosim.server.framework.GenericServer;
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
		FoodStoreImpl myFoodStore = new FoodStoreImpl(GenericServer.getIDfromArgs(args), GenericServer.getNamefromArgs(args));
		myServer.registerServerAndRun(new FoodStorePOATie(myFoodStore), myFoodStore.getModuleName(), myFoodStore.getID());
	}
}


