package biosim.server.simulation.food;

import biosim.idl.simulation.food.FoodProcessorPOATie;
import biosim.server.framework.GenericServer;
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
		FoodProcessorImpl myFoodProcessor = new FoodProcessorImpl(GenericServer.getIDfromArgs(args), GenericServer.getNamefromArgs(args));
		myServer.registerServerAndRun(new FoodProcessorPOATie(myFoodProcessor), myFoodProcessor.getModuleName(), myFoodProcessor.getID());
	}
}


