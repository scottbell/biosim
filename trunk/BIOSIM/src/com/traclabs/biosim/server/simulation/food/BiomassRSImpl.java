package biosim.server.food;

import biosim.idl.food.*;
import biosim.idl.util.*;
import biosim.server.framework.*;
import java.util.*;
/**
 * The Biomass RS is essentially responsible for growing plants.  The plant matter (biomass) is fed into the food processor to create food
 * for the crew.  The plants can also (along with the AirRS) take CO2 out of the air and add O2.
 *
 * @author    Scott Bell
 */

public class BiomassRSImpl extends BioModuleImpl implements BiomassRSOperations {
	private Vector myShelves;
	private int shelfCapacity = 10;
	private Vector shelfLogs;
	
	public BiomassRSImpl(){
		myShelves = new Vector(shelfCapacity);
		for (int i = 0; i < shelfCapacity; i++){
			myShelves.add(new ShelfImpl());
		}
	}
	
	public BiomassRSImpl(int pShelfCapacity){
		shelfCapacity = pShelfCapacity;
		myShelves = new Vector(shelfCapacity);
		for (int i = 0; i < shelfCapacity; i++){
			myShelves.add(new ShelfImpl());
		}
	}

	/**
	* Resets production/consumption levels and death/affliction flags
	*/
	public void reset(){
		for (Enumeration e = myShelves.elements(); e.hasMoreElements();){
			ShelfImpl currentShelf = (ShelfImpl)(e.nextElement());
			currentShelf.reset();
		}
	}

	/**
	* When ticked, the Biomass RS does the following
	* on the condition that the plants aren't dead it.
	* 1) attempts to collect references to various server (if not already done).
	* 4) consumes
	*/
	public void tick(){
		for (Enumeration e = myShelves.elements(); e.hasMoreElements();){
			ShelfImpl currentShelf = (ShelfImpl)(e.nextElement());
			currentShelf.tick();
		}
		if (moduleLogging)
			log();
	}

	/**
	* Returns the name of this module (BiomassRS)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "BiomassRS";
	}
	
	private void log(){
		//If not initialized, fill in the log
		if (!logInitialized){
			shelfLogs = new Vector();
			for (int i = 0; i < myShelves.size(); i++){
				ShelfImpl currentShelf = (ShelfImpl)(myShelves.get(i));
				LogNode newShelfHead= myLog.getHead().addChild("Shelf "+i);
				shelfLogs.add(newShelfHead);
				currentShelf.log(newShelfHead);
			}
			logInitialized = true;
		}
		else{
			for (int i = 0; i < myShelves.size(); i++){
				ShelfImpl currentShelf = (ShelfImpl)(myShelves.get(i));
				LogNode shelfHead = (LogNode)(shelfLogs.get(i));
				currentShelf.log(shelfHead);
			}
		}
		sendLog(myLog);
	}

	/**
	* For fast reference to the log tree
	*/
	private class LogIndex{
		public LogNode nothingIndex;
	}
}
