package biosim.server.simulation.food;

import biosim.server.framework.*;
import biosim.idl.simulation.food.*;
/**
 * The Food Processor Server.  Creates an instance of the Food Processor and registers it with the nameserver.
 *
 * @author    Scott Bell
 */

public class FoodProcessorServer extends GenericServer{
	
	/**
	* Instantiates the server and binds it to the name server.
	* @param args aren't used for anything
	*/
	public static void main(String args[]) {
		FoodProcessorServer myServer = new FoodProcessorServer();
		FoodProcessorImpl myFoodProcessor = new FoodProcessorImpl(myServer.getIDfromArgs(args), myServer.getNamefromArgs(args));
		myServer.registerServerAndRun(new FoodProcessorPOATie(myFoodProcessor), myFoodProcessor.getModuleName(), myFoodProcessor.getID());
	}
}


