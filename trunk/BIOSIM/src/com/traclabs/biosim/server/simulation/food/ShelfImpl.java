package biosim.server.food;

import biosim.idl.food.*;
import java.util.*;
/**
 * Tray contains Plants
 * @author    Scott Bell
 */

public class ShelfImpl extends ShelfPOA {
	private Vector crops;
	private int cropCapacity = 10;
	
	public ShelfImpl(){
		crops = new Vector();
		for (int i = 0; i < cropCapacity; i++){
			Plant newPlant = new Wheat();
			crops.add(newPlant);
		}
	}
}
