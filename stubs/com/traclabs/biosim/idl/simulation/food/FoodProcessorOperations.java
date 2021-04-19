package com.traclabs.biosim.idl.simulation.food;


/**
 * Generated from IDL interface "FoodProcessor".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface FoodProcessorOperations
	extends com.traclabs.biosim.idl.simulation.framework.SimBioModuleOperations , com.traclabs.biosim.idl.simulation.power.PowerConsumerOperations , com.traclabs.biosim.idl.simulation.food.BiomassConsumerOperations , com.traclabs.biosim.idl.simulation.food.FoodProducerOperations , com.traclabs.biosim.idl.simulation.waste.DryWasteProducerOperations , com.traclabs.biosim.idl.simulation.water.WaterProducerOperations
{
	/* constants */
	/* operations  */
	float getBiomassConsumed();
	float getPowerConsumed();
	float getFoodProduced();
	boolean hasPower();
	boolean hasBiomass();
}
