package biosim.server.simulation.food;

/**
 * Wheat
 * @author    Scott Bell
 */

public class Wheat extends PlantImpl{
	public Wheat(BiomassRSImpl pBiomassImpl){
		super(pBiomassImpl);
	}

	public String getPlantType(){
		return "wheat";
	}

}
