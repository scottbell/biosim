package com.traclabs.biosim.idl.simulation.food;

/**
 *	Generated from IDL interface "FoodProcessor"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
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
