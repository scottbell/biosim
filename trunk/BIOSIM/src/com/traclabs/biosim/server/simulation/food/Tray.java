package biosim.server.food;

import biosim.idl.food.*;
import java.util.*;
/**
 * Tray contains Plants
 * @author    Scott Bell
 */

public class Tray {
	private Vector crops;
	private int cropCapacity = 10;
	
	public Tray(){
		crops = new Vector();
		for (int i = 0; i < cropCapacity; i++){
			Plant newPlant = new Wheat();
			crops.add(newPlant);
		}
	}
}
