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
	
	protected float getBCF(){
		return 0.44f;
	}
	
	protected float getCarbonUseEfficiency24(){
		return 0.64f;
	}
	
	protected float getPhotoperiod(){
		return 20f;
	}
}
