package biosim.server.simulation.food;

/**
 * Wheat
 * @author    Scott Bell
 */

public class Wheat extends PlantImpl{
	public Wheat(ShelfImpl pShelfImpl){
		super(pShelfImpl);
	}

	public String getPlantType(){
		return "wheat";
	}
	
}
