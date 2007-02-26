package com.traclabs.biosim.client.control;

import java.util.Random;

import com.traclabs.biosim.idl.simulation.food.PlantType;
import com.traclabs.biosim.util.MersenneTwister;

/**
 * A controller for analytical approach.
 * 
 * @author Haibei Configuration 3, fixed Total Crop Area and varying Crop Mix
 */
public class AnalyticalController3 extends EnvironmentController implements BiosimController {
	public static final String DEFAULT_CONFIGURATION_FILE = "kirsten/MurderControllerInit_CropMix.xml";

	private static final String DEFAULT_LOG_FILE = "AnalyticalController3.log";

	private Random myRandomGenerator = new MersenneTwister();

	private float myCropAreaTotal = 14;

	private float myCropAreaOne = (float) (myCropAreaTotal * myRandomGenerator
			.nextDouble());

	private float myCropAreaTwo = (float) ((myCropAreaTotal - myCropAreaOne) * myRandomGenerator
			.nextDouble());

	private float myCropAreaThree = (float) ((myCropAreaTotal - myCropAreaOne - myCropAreaTwo) * myRandomGenerator
			.nextDouble());

	private float myCropAreaFour = (float) ((myCropAreaTotal - myCropAreaOne
			- myCropAreaTwo - myCropAreaThree) * myRandomGenerator.nextDouble());

	private float myCropAreaFive = (float) ((myCropAreaTotal - myCropAreaOne
			- myCropAreaTwo - myCropAreaThree - myCropAreaFour) * myRandomGenerator
			.nextDouble());

	private float myCropAreaSix = (float) ((myCropAreaTotal - myCropAreaOne
			- myCropAreaTwo - myCropAreaThree - myCropAreaFour - myCropAreaFive) * myRandomGenerator
			.nextDouble());

	private float myCropAreaSeven = (float) ((myCropAreaTotal - myCropAreaOne
			- myCropAreaTwo - myCropAreaThree - myCropAreaFour - myCropAreaFive - myCropAreaSix) * myRandomGenerator
			.nextDouble());

	private float myCropAreaEight = (float) ((myCropAreaTotal - myCropAreaOne
			- myCropAreaTwo - myCropAreaThree - myCropAreaFour - myCropAreaFive
			- myCropAreaSix - myCropAreaSeven) * myRandomGenerator.nextDouble());

	private float myCropAreaNine = (float) (myCropAreaTotal - myCropAreaOne
			- myCropAreaTwo - myCropAreaThree - myCropAreaFour - myCropAreaFive
			- myCropAreaSix - myCropAreaSeven - myCropAreaEight);

	private int numberFromMonteCarlo1 = (int) (Math.random() * 8);

	private PlantType plantType1 = PlantType.from_int(numberFromMonteCarlo1);

	private int numberFromMonteCarlo2 = (int) (Math.random() * 8);

	private PlantType plantType2 = PlantType.from_int(numberFromMonteCarlo2);

	private int numberFromMonteCarlo3 = (int) (Math.random() * 8);

	private PlantType plantType3 = PlantType.from_int(numberFromMonteCarlo3);

	private int numberFromMonteCarlo4 = (int) (Math.random() * 8);

	private PlantType plantType4 = PlantType.from_int(numberFromMonteCarlo4);

	private int numberFromMonteCarlo5 = (int) (Math.random() * 8);

	private PlantType plantType5 = PlantType.from_int(numberFromMonteCarlo5);

	private int numberFromMonteCarlo6 = (int) (Math.random() * 8);

	private PlantType plantType6 = PlantType.from_int(numberFromMonteCarlo6);

	private int numberFromMonteCarlo7 = (int) (Math.random() * 8);

	private PlantType plantType7 = PlantType.from_int(numberFromMonteCarlo7);

	private int numberFromMonteCarlo8 = (int) (Math.random() * 8);

	private PlantType plantType8 = PlantType.from_int(numberFromMonteCarlo8);

	private int numberFromMonteCarlo9 = (int) (Math.random() * 8);

	private PlantType plantType9 = PlantType.from_int(numberFromMonteCarlo9);
	
	public AnalyticalController3() {
		super(DEFAULT_CONFIGURATION_FILE, DEFAULT_LOG_FILE);
	}
	
	public AnalyticalController3(String configurationFileName, String logFileName) {
		super(configurationFileName, logFileName);
	}

	public static void main(String[] args) {
		int max = 5000;
		for (int i = 0; i < max; i++) {
			EnvironmentController myController = new EnvironmentController(DEFAULT_CONFIGURATION_FILE, DEFAULT_LOG_FILE);
			myController.collectReferences();
			myController.runSim();
		}
	}

	/**
	 * Collects references to BioModules we'll need to run/observer/poke the
	 * sim. The BioHolder is a utility for clients to easily access different
	 * parts of BioSim.
	 * 
	 */
	public void collectReferences() {
		super.collectReferences();
		// this implements the crop mix, before the first tick. In is
		// configuration, total crop area is fixed
		myBioHolder.theBiomassPSModules.get(0).getShelf(0).replant(plantType1,
				myCropAreaOne);
		myBioHolder.theBiomassPSModules.get(0).getShelf(1).replant(plantType2,
				myCropAreaTwo);
		myBioHolder.theBiomassPSModules.get(0).getShelf(2).replant(plantType3,
				myCropAreaThree);
		myBioHolder.theBiomassPSModules.get(0).getShelf(3).replant(plantType4,
				myCropAreaFour);
		myBioHolder.theBiomassPSModules.get(0).getShelf(4).replant(plantType5,
				myCropAreaFive);
		myBioHolder.theBiomassPSModules.get(0).getShelf(5).replant(plantType6,
				myCropAreaSix);
		myBioHolder.theBiomassPSModules.get(0).getShelf(6).replant(plantType7,
				myCropAreaSeven);
		myBioHolder.theBiomassPSModules.get(0).getShelf(7).replant(plantType8,
				myCropAreaEight);
		myBioHolder.theBiomassPSModules.get(0).getShelf(8).replant(plantType9,
				myCropAreaNine);
	}

	public void printConfigurations() {
		myOutput.println();
		myOutput.print(" Crop area 1= "
				+ myBioHolder.theBiomassPSModules.get(0).getShelf(0)
						.getCropAreaUsed() + " " + "Crop Type 1 is"
				+ numberFromMonteCarlo1);
		myOutput.print(" Crop area 2= "
				+ myBioHolder.theBiomassPSModules.get(0).getShelf(1)
						.getCropAreaUsed() + " " + "Crop Type 2 is"
				+ numberFromMonteCarlo2);
		myOutput.print(" Crop area 3= "
				+ myBioHolder.theBiomassPSModules.get(0).getShelf(2)
						.getCropAreaUsed() + " " + "Crop Type 3 is"
				+ numberFromMonteCarlo3);
		myOutput.print("Crop area 4= "
				+ myBioHolder.theBiomassPSModules.get(0).getShelf(3)
						.getCropAreaUsed() + " " + "Crop Type 4 is"
				+ numberFromMonteCarlo4);
		myOutput.println("Crop area 5= "
				+ myBioHolder.theBiomassPSModules.get(0).getShelf(4)
						.getCropAreaUsed() + " " + "Crop Type 5 is"
				+ numberFromMonteCarlo5);
		myOutput.println("Crop area 6= "
				+ myBioHolder.theBiomassPSModules.get(0).getShelf(5)
						.getCropAreaUsed() + " " + "Crop Type 6 is"
				+ numberFromMonteCarlo6);
		myOutput.println("Crop area 7= "
				+ myBioHolder.theBiomassPSModules.get(0).getShelf(6)
						.getCropAreaUsed() + " " + "Crop Type 7 is"
				+ numberFromMonteCarlo7);
		myOutput.println("Crop area 8= "
				+ myBioHolder.theBiomassPSModules.get(0).getShelf(7)
						.getCropAreaUsed() + " " + "Crop Type 8 is"
				+ numberFromMonteCarlo8);
		myOutput.println("Crop area 9= "
				+ myBioHolder.theBiomassPSModules.get(0).getShelf(8)
						.getCropAreaUsed() + " " + "Crop Type 9 is"
				+ numberFromMonteCarlo9);
		myOutput.println("Controller ended on tick " + myBioHolder.theBioDriver.getTicks());
		myOutput.println();
		myOutput.flush();
	}
}
