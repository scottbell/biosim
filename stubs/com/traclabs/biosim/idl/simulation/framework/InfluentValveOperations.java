package com.traclabs.biosim.idl.simulation.framework;


/**
 * Generated from IDL interface "InfluentValve".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface InfluentValveOperations
	extends com.traclabs.biosim.idl.simulation.framework.SimBioModuleOperations , com.traclabs.biosim.idl.simulation.power.PowerConsumerOperations , com.traclabs.biosim.idl.simulation.water.PotableWaterConsumerOperations , com.traclabs.biosim.idl.simulation.water.GreyWaterConsumerOperations , com.traclabs.biosim.idl.simulation.water.DirtyWaterConsumerOperations , com.traclabs.biosim.idl.simulation.water.WaterConsumerOperations , com.traclabs.biosim.idl.simulation.air.O2ConsumerOperations , com.traclabs.biosim.idl.simulation.air.CO2ConsumerOperations , com.traclabs.biosim.idl.simulation.air.NitrogenConsumerOperations , com.traclabs.biosim.idl.simulation.environment.AirConsumerOperations , com.traclabs.biosim.idl.simulation.food.BiomassConsumerOperations , com.traclabs.biosim.idl.simulation.food.FoodConsumerOperations , com.traclabs.biosim.idl.simulation.waste.DryWasteConsumerOperations , com.traclabs.biosim.idl.simulation.power.PowerProducerOperations , com.traclabs.biosim.idl.simulation.water.PotableWaterProducerOperations , com.traclabs.biosim.idl.simulation.water.GreyWaterProducerOperations , com.traclabs.biosim.idl.simulation.water.DirtyWaterProducerOperations , com.traclabs.biosim.idl.simulation.air.O2ProducerOperations , com.traclabs.biosim.idl.simulation.air.CO2ProducerOperations , com.traclabs.biosim.idl.simulation.air.NitrogenProducerOperations , com.traclabs.biosim.idl.simulation.environment.AirProducerOperations , com.traclabs.biosim.idl.simulation.food.BiomassProducerOperations , com.traclabs.biosim.idl.simulation.food.FoodProducerOperations , com.traclabs.biosim.idl.simulation.waste.DryWasteProducerOperations
{
	/* constants */
	/* operations  */
	void setIndexOfInfluentStore(int pIndexOfEffluentStore);
	int getIndexOfInfluentStore();
}
