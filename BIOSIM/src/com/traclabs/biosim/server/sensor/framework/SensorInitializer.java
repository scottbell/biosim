package com.traclabs.biosim.server.sensor.framework;

import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;

import com.traclabs.biosim.idl.sensor.air.CO2InFlowRateSensor;
import com.traclabs.biosim.idl.sensor.air.CO2InFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.air.CO2InFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.air.CO2OutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.air.CO2OutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.air.CO2OutFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.air.CO2StoreLevelSensor;
import com.traclabs.biosim.idl.sensor.air.CO2StoreLevelSensorHelper;
import com.traclabs.biosim.idl.sensor.air.CO2StoreLevelSensorPOATie;
import com.traclabs.biosim.idl.sensor.air.H2InFlowRateSensor;
import com.traclabs.biosim.idl.sensor.air.H2InFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.air.H2InFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.air.H2OutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.air.H2OutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.air.H2OutFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.air.H2StoreLevelSensor;
import com.traclabs.biosim.idl.sensor.air.H2StoreLevelSensorHelper;
import com.traclabs.biosim.idl.sensor.air.H2StoreLevelSensorPOATie;
import com.traclabs.biosim.idl.sensor.air.NitrogenInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.air.NitrogenInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.air.NitrogenInFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.air.NitrogenOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.air.NitrogenOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.air.NitrogenOutFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.air.NitrogenStoreLevelSensor;
import com.traclabs.biosim.idl.sensor.air.NitrogenStoreLevelSensorHelper;
import com.traclabs.biosim.idl.sensor.air.NitrogenStoreLevelSensorPOATie;
import com.traclabs.biosim.idl.sensor.air.O2InFlowRateSensor;
import com.traclabs.biosim.idl.sensor.air.O2InFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.air.O2InFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.air.O2OutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.air.O2OutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.air.O2OutFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.air.O2StoreLevelSensor;
import com.traclabs.biosim.idl.sensor.air.O2StoreLevelSensorHelper;
import com.traclabs.biosim.idl.sensor.air.O2StoreLevelSensorPOATie;
import com.traclabs.biosim.idl.sensor.crew.CrewGroupAnyDeadSensor;
import com.traclabs.biosim.idl.sensor.crew.CrewGroupAnyDeadSensorHelper;
import com.traclabs.biosim.idl.sensor.crew.CrewGroupAnyDeadSensorPOATie;
import com.traclabs.biosim.idl.sensor.crew.CrewGroupDeathSensor;
import com.traclabs.biosim.idl.sensor.crew.CrewGroupDeathSensorHelper;
import com.traclabs.biosim.idl.sensor.crew.CrewGroupDeathSensorPOATie;
import com.traclabs.biosim.idl.sensor.crew.CrewGroupProductivitySensor;
import com.traclabs.biosim.idl.sensor.crew.CrewGroupProductivitySensorHelper;
import com.traclabs.biosim.idl.sensor.crew.CrewGroupProductivitySensorPOATie;
import com.traclabs.biosim.idl.sensor.environment.AirInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.environment.AirInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.AirInFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.environment.AirOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.environment.AirOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.AirOutFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.environment.CO2AirConcentrationSensor;
import com.traclabs.biosim.idl.sensor.environment.CO2AirConcentrationSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.CO2AirConcentrationSensorPOATie;
import com.traclabs.biosim.idl.sensor.environment.CO2AirEnvironmentInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.environment.CO2AirEnvironmentInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.CO2AirEnvironmentInFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.environment.CO2AirEnvironmentOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.environment.CO2AirEnvironmentOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.CO2AirEnvironmentOutFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.environment.CO2AirPressureSensor;
import com.traclabs.biosim.idl.sensor.environment.CO2AirPressureSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.CO2AirPressureSensorPOATie;
import com.traclabs.biosim.idl.sensor.environment.CO2AirStoreInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.environment.CO2AirStoreInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.CO2AirStoreInFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.environment.CO2AirStoreOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.environment.CO2AirStoreOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.CO2AirStoreOutFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.environment.NitrogenAirConcentrationSensor;
import com.traclabs.biosim.idl.sensor.environment.NitrogenAirConcentrationSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.NitrogenAirConcentrationSensorPOATie;
import com.traclabs.biosim.idl.sensor.environment.NitrogenAirEnvironmentInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.environment.NitrogenAirEnvironmentInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.NitrogenAirEnvironmentInFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.environment.NitrogenAirEnvironmentOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.environment.NitrogenAirEnvironmentOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.NitrogenAirEnvironmentOutFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.environment.NitrogenAirPressureSensor;
import com.traclabs.biosim.idl.sensor.environment.NitrogenAirPressureSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.NitrogenAirPressureSensorPOATie;
import com.traclabs.biosim.idl.sensor.environment.NitrogenAirStoreInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.environment.NitrogenAirStoreInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.NitrogenAirStoreInFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.environment.NitrogenAirStoreOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.environment.NitrogenAirStoreOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.NitrogenAirStoreOutFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.environment.O2AirConcentrationSensor;
import com.traclabs.biosim.idl.sensor.environment.O2AirConcentrationSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.O2AirConcentrationSensorPOATie;
import com.traclabs.biosim.idl.sensor.environment.O2AirEnvironmentInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.environment.O2AirEnvironmentInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.O2AirEnvironmentInFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.environment.O2AirEnvironmentOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.environment.O2AirEnvironmentOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.O2AirEnvironmentOutFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.environment.O2AirPressureSensor;
import com.traclabs.biosim.idl.sensor.environment.O2AirPressureSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.O2AirPressureSensorPOATie;
import com.traclabs.biosim.idl.sensor.environment.O2AirStoreInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.environment.O2AirStoreInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.O2AirStoreInFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.environment.O2AirStoreOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.environment.O2AirStoreOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.O2AirStoreOutFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.environment.OtherAirConcentrationSensor;
import com.traclabs.biosim.idl.sensor.environment.OtherAirConcentrationSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.OtherAirConcentrationSensorPOATie;
import com.traclabs.biosim.idl.sensor.environment.OtherAirPressureSensor;
import com.traclabs.biosim.idl.sensor.environment.OtherAirPressureSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.OtherAirPressureSensorPOATie;
import com.traclabs.biosim.idl.sensor.environment.WaterAirConcentrationSensor;
import com.traclabs.biosim.idl.sensor.environment.WaterAirConcentrationSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.WaterAirConcentrationSensorPOATie;
import com.traclabs.biosim.idl.sensor.environment.WaterAirEnvironmentInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.environment.WaterAirEnvironmentInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.WaterAirEnvironmentInFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.environment.WaterAirEnvironmentOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.environment.WaterAirEnvironmentOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.WaterAirEnvironmentOutFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.environment.WaterAirPressureSensor;
import com.traclabs.biosim.idl.sensor.environment.WaterAirPressureSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.WaterAirPressureSensorPOATie;
import com.traclabs.biosim.idl.sensor.environment.WaterAirStoreInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.environment.WaterAirStoreInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.WaterAirStoreInFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.environment.WaterAirStoreOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.environment.WaterAirStoreOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.WaterAirStoreOutFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.food.BiomassInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.food.BiomassInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.food.BiomassInFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.food.BiomassOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.food.BiomassOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.food.BiomassOutFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.food.BiomassStoreLevelSensor;
import com.traclabs.biosim.idl.sensor.food.BiomassStoreLevelSensorHelper;
import com.traclabs.biosim.idl.sensor.food.BiomassStoreLevelSensorPOATie;
import com.traclabs.biosim.idl.sensor.food.BiomassStoreWaterContentSensor;
import com.traclabs.biosim.idl.sensor.food.BiomassStoreWaterContentSensorHelper;
import com.traclabs.biosim.idl.sensor.food.BiomassStoreWaterContentSensorPOATie;
import com.traclabs.biosim.idl.sensor.food.FoodInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.food.FoodInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.food.FoodInFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.food.FoodOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.food.FoodOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.food.FoodOutFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.food.FoodStoreLevelSensor;
import com.traclabs.biosim.idl.sensor.food.FoodStoreLevelSensorHelper;
import com.traclabs.biosim.idl.sensor.food.FoodStoreLevelSensorPOATie;
import com.traclabs.biosim.idl.sensor.food.HarvestSensor;
import com.traclabs.biosim.idl.sensor.food.HarvestSensorHelper;
import com.traclabs.biosim.idl.sensor.food.HarvestSensorPOATie;
import com.traclabs.biosim.idl.sensor.food.PlantDeathSensor;
import com.traclabs.biosim.idl.sensor.food.PlantDeathSensorHelper;
import com.traclabs.biosim.idl.sensor.food.PlantDeathSensorPOATie;
import com.traclabs.biosim.idl.sensor.framework.StoreLevelSensor;
import com.traclabs.biosim.idl.sensor.framework.StoreLevelSensorHelper;
import com.traclabs.biosim.idl.sensor.framework.StoreLevelSensorPOATie;
import com.traclabs.biosim.idl.sensor.framework.StoreOverflowSensor;
import com.traclabs.biosim.idl.sensor.framework.StoreOverflowSensorHelper;
import com.traclabs.biosim.idl.sensor.framework.StoreOverflowSensorPOATie;
import com.traclabs.biosim.idl.sensor.power.PowerInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.power.PowerInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.power.PowerInFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.power.PowerOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.power.PowerOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.power.PowerOutFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.power.PowerStoreLevelSensor;
import com.traclabs.biosim.idl.sensor.power.PowerStoreLevelSensorHelper;
import com.traclabs.biosim.idl.sensor.power.PowerStoreLevelSensorPOATie;
import com.traclabs.biosim.idl.sensor.waste.DryWasteInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.waste.DryWasteInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.waste.DryWasteInFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.waste.DryWasteOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.waste.DryWasteOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.waste.DryWasteOutFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.waste.DryWasteStoreLevelSensor;
import com.traclabs.biosim.idl.sensor.waste.DryWasteStoreLevelSensorHelper;
import com.traclabs.biosim.idl.sensor.waste.DryWasteStoreLevelSensorPOATie;
import com.traclabs.biosim.idl.sensor.water.DirtyWaterInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.water.DirtyWaterInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.water.DirtyWaterInFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.water.DirtyWaterOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.water.DirtyWaterOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.water.DirtyWaterOutFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.water.DirtyWaterStoreLevelSensor;
import com.traclabs.biosim.idl.sensor.water.DirtyWaterStoreLevelSensorHelper;
import com.traclabs.biosim.idl.sensor.water.DirtyWaterStoreLevelSensorPOATie;
import com.traclabs.biosim.idl.sensor.water.GreyWaterInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.water.GreyWaterInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.water.GreyWaterInFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.water.GreyWaterOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.water.GreyWaterOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.water.GreyWaterOutFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.water.GreyWaterStoreLevelSensor;
import com.traclabs.biosim.idl.sensor.water.GreyWaterStoreLevelSensorHelper;
import com.traclabs.biosim.idl.sensor.water.GreyWaterStoreLevelSensorPOATie;
import com.traclabs.biosim.idl.sensor.water.PotableWaterInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.water.PotableWaterInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.water.PotableWaterInFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.water.PotableWaterOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.water.PotableWaterOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.water.PotableWaterOutFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.water.PotableWaterStoreLevelSensor;
import com.traclabs.biosim.idl.sensor.water.PotableWaterStoreLevelSensorHelper;
import com.traclabs.biosim.idl.sensor.water.PotableWaterStoreLevelSensorPOATie;
import com.traclabs.biosim.idl.sensor.water.WaterInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.water.WaterInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.water.WaterInFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.water.WaterOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.water.WaterOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.water.WaterOutFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.water.WaterStoreLevelSensor;
import com.traclabs.biosim.idl.sensor.water.WaterStoreLevelSensorHelper;
import com.traclabs.biosim.idl.sensor.water.WaterStoreLevelSensorPOATie;
import com.traclabs.biosim.idl.simulation.air.CO2StoreHelper;
import com.traclabs.biosim.idl.simulation.air.H2StoreHelper;
import com.traclabs.biosim.idl.simulation.air.NitrogenStoreHelper;
import com.traclabs.biosim.idl.simulation.air.O2StoreHelper;
import com.traclabs.biosim.idl.simulation.crew.CrewGroupHelper;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironmentHelper;
import com.traclabs.biosim.idl.simulation.food.BiomassRSHelper;
import com.traclabs.biosim.idl.simulation.food.BiomassStoreHelper;
import com.traclabs.biosim.idl.simulation.food.FoodStoreHelper;
import com.traclabs.biosim.idl.simulation.framework.AirConsumerHelper;
import com.traclabs.biosim.idl.simulation.framework.AirProducerHelper;
import com.traclabs.biosim.idl.simulation.framework.BiomassConsumerHelper;
import com.traclabs.biosim.idl.simulation.framework.BiomassProducerHelper;
import com.traclabs.biosim.idl.simulation.framework.CO2AirConsumerHelper;
import com.traclabs.biosim.idl.simulation.framework.CO2AirProducerHelper;
import com.traclabs.biosim.idl.simulation.framework.CO2ConsumerHelper;
import com.traclabs.biosim.idl.simulation.framework.CO2ProducerHelper;
import com.traclabs.biosim.idl.simulation.framework.DirtyWaterConsumerHelper;
import com.traclabs.biosim.idl.simulation.framework.DirtyWaterProducerHelper;
import com.traclabs.biosim.idl.simulation.framework.DryWasteConsumerHelper;
import com.traclabs.biosim.idl.simulation.framework.DryWasteProducerHelper;
import com.traclabs.biosim.idl.simulation.framework.FoodConsumerHelper;
import com.traclabs.biosim.idl.simulation.framework.FoodProducerHelper;
import com.traclabs.biosim.idl.simulation.framework.GreyWaterConsumerHelper;
import com.traclabs.biosim.idl.simulation.framework.GreyWaterProducerHelper;
import com.traclabs.biosim.idl.simulation.framework.H2ConsumerHelper;
import com.traclabs.biosim.idl.simulation.framework.H2ProducerHelper;
import com.traclabs.biosim.idl.simulation.framework.NitrogenAirConsumerHelper;
import com.traclabs.biosim.idl.simulation.framework.NitrogenAirProducerHelper;
import com.traclabs.biosim.idl.simulation.framework.NitrogenConsumerHelper;
import com.traclabs.biosim.idl.simulation.framework.NitrogenProducerHelper;
import com.traclabs.biosim.idl.simulation.framework.O2AirConsumerHelper;
import com.traclabs.biosim.idl.simulation.framework.O2AirProducerHelper;
import com.traclabs.biosim.idl.simulation.framework.O2ConsumerHelper;
import com.traclabs.biosim.idl.simulation.framework.O2ProducerHelper;
import com.traclabs.biosim.idl.simulation.framework.PotableWaterConsumerHelper;
import com.traclabs.biosim.idl.simulation.framework.PotableWaterProducerHelper;
import com.traclabs.biosim.idl.simulation.framework.PowerConsumerHelper;
import com.traclabs.biosim.idl.simulation.framework.PowerProducerHelper;
import com.traclabs.biosim.idl.simulation.framework.StoreHelper;
import com.traclabs.biosim.idl.simulation.framework.WaterAirConsumerHelper;
import com.traclabs.biosim.idl.simulation.framework.WaterAirProducerHelper;
import com.traclabs.biosim.idl.simulation.framework.WaterConsumerHelper;
import com.traclabs.biosim.idl.simulation.framework.WaterProducerHelper;
import com.traclabs.biosim.idl.simulation.power.PowerStoreHelper;
import com.traclabs.biosim.idl.simulation.waste.DryWasteStoreHelper;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterStoreHelper;
import com.traclabs.biosim.idl.simulation.water.GreyWaterStoreHelper;
import com.traclabs.biosim.idl.simulation.water.PotableWaterStoreHelper;
import com.traclabs.biosim.idl.simulation.water.WaterStoreHelper;
import com.traclabs.biosim.server.framework.BioInitializer;
import com.traclabs.biosim.server.framework.BiosimServer;
import com.traclabs.biosim.server.sensor.air.CO2InFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.air.CO2OutFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.air.CO2StoreLevelSensorImpl;
import com.traclabs.biosim.server.sensor.air.H2InFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.air.H2OutFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.air.H2StoreLevelSensorImpl;
import com.traclabs.biosim.server.sensor.air.NitrogenInFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.air.NitrogenOutFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.air.NitrogenStoreLevelSensorImpl;
import com.traclabs.biosim.server.sensor.air.O2InFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.air.O2OutFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.air.O2StoreLevelSensorImpl;
import com.traclabs.biosim.server.sensor.crew.CrewGroupAnyDeadSensorImpl;
import com.traclabs.biosim.server.sensor.crew.CrewGroupDeathSensorImpl;
import com.traclabs.biosim.server.sensor.crew.CrewGroupProductivitySensorImpl;
import com.traclabs.biosim.server.sensor.environment.AirInFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.environment.AirOutFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.environment.CO2AirConcentrationSensorImpl;
import com.traclabs.biosim.server.sensor.environment.CO2AirEnvironmentInFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.environment.CO2AirEnvironmentOutFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.environment.CO2AirPressureSensorImpl;
import com.traclabs.biosim.server.sensor.environment.CO2AirStoreInFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.environment.CO2AirStoreOutFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.environment.NitrogenAirConcentrationSensorImpl;
import com.traclabs.biosim.server.sensor.environment.NitrogenAirEnvironmentInFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.environment.NitrogenAirEnvironmentOutFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.environment.NitrogenAirPressureSensorImpl;
import com.traclabs.biosim.server.sensor.environment.NitrogenAirStoreInFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.environment.NitrogenAirStoreOutFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.environment.O2AirConcentrationSensorImpl;
import com.traclabs.biosim.server.sensor.environment.O2AirEnvironmentInFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.environment.O2AirEnvironmentOutFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.environment.O2AirPressureSensorImpl;
import com.traclabs.biosim.server.sensor.environment.O2AirStoreInFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.environment.O2AirStoreOutFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.environment.OtherAirConcentrationSensorImpl;
import com.traclabs.biosim.server.sensor.environment.OtherAirPressureSensorImpl;
import com.traclabs.biosim.server.sensor.environment.WaterAirConcentrationSensorImpl;
import com.traclabs.biosim.server.sensor.environment.WaterAirEnvironmentInFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.environment.WaterAirEnvironmentOutFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.environment.WaterAirPressureSensorImpl;
import com.traclabs.biosim.server.sensor.environment.WaterAirStoreInFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.environment.WaterAirStoreOutFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.food.BiomassInFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.food.BiomassOutFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.food.BiomassStoreLevelSensorImpl;
import com.traclabs.biosim.server.sensor.food.BiomassStoreWaterContentSensorImpl;
import com.traclabs.biosim.server.sensor.food.FoodInFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.food.FoodOutFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.food.FoodStoreLevelSensorImpl;
import com.traclabs.biosim.server.sensor.food.HarvestSensorImpl;
import com.traclabs.biosim.server.sensor.food.PlantDeathSensorImpl;
import com.traclabs.biosim.server.sensor.power.PowerInFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.power.PowerOutFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.power.PowerStoreLevelSensorImpl;
import com.traclabs.biosim.server.sensor.waste.DryWasteInFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.waste.DryWasteOutFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.waste.DryWasteStoreLevelSensorImpl;
import com.traclabs.biosim.server.sensor.water.DirtyWaterInFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.water.DirtyWaterOutFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.water.DirtyWaterStoreLevelSensorImpl;
import com.traclabs.biosim.server.sensor.water.GreyWaterInFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.water.GreyWaterOutFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.water.GreyWaterStoreLevelSensorImpl;
import com.traclabs.biosim.server.sensor.water.PotableWaterInFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.water.PotableWaterOutFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.water.PotableWaterStoreLevelSensorImpl;
import com.traclabs.biosim.server.sensor.water.WaterInFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.water.WaterOutFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.water.WaterStoreLevelSensorImpl;

/**
 * Reads BioSim configuration from XML file.
 * 
 * @author Scott Bell
 */
public class SensorInitializer {
    private int myID = 0;

    private List mySensors;

    private Logger myLogger;

    /** Default constructor. */
    public SensorInitializer(int pID) {
        myID = pID;
        mySensors = new Vector();
        myLogger = Logger.getLogger(this.getClass());
    }

    //Sensors
    public void crawlSensors(Node node, boolean firstPass) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("air")) {
                crawlAirSensors(child, firstPass);

            } else if (childName.equals("crew")) {
                crawlCrewSensors(child, firstPass);

            } else if (childName.equals("environment")) {
                crawlEnvironmentSensors(child, firstPass);

            } else if (childName.equals("food")) {
                crawlFoodSensors(child, firstPass);

            } else if (childName.equals("framework")) {
                crawlFrameworkSensors(child, firstPass);

            } else if (childName.equals("power")) {
                crawlPowerSensors(child, firstPass);

            } else if (childName.equals("water")) {
                crawlWaterSensors(child, firstPass);

            } else if (childName.equals("waste")) {
                crawlWasteSensors(child, firstPass);

            }
            child = child.getNextSibling();
        }
    }

    private static String getInputName(Node pNode) {
        return pNode.getAttributes().getNamedItem("input").getNodeValue();
    }

    private static int getFlowRateIndex(Node pNode) {
        return Integer.parseInt(pNode.getAttributes().getNamedItem("index")
                .getNodeValue());
    }

    private static int getShelfIndex(Node pNode) {
        return Integer.parseInt(pNode.getAttributes()
                .getNamedItem("shelfIndex").getNodeValue());
    }

    //Air
    private void createCO2InFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating CO2InFlowRateSensor with moduleName: "
                    + moduleName);
            CO2InFlowRateSensorImpl myCO2InFlowRateSensorImpl = new CO2InFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myCO2InFlowRateSensorImpl, node);
            BiosimServer.registerServer(new CO2InFlowRateSensorPOATie(
                    myCO2InFlowRateSensorImpl), myCO2InFlowRateSensorImpl
                    .getModuleName(), myCO2InFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureCO2InFlowRateSensor(Node node) {
        CO2InFlowRateSensor myCO2InFlowRateSensor = CO2InFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myCO2InFlowRateSensor.setInput(CO2ConsumerHelper.narrow(BioInitializer
                .grabModule(myID, getInputName(node))), getFlowRateIndex(node));
        mySensors.add(myCO2InFlowRateSensor);
    }

    private void createCO2OutFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating CO2OutFlowRateSensor with moduleName: "
                    + moduleName);
            CO2OutFlowRateSensorImpl myCO2OutFlowRateSensorImpl = new CO2OutFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myCO2OutFlowRateSensorImpl, node);
            BiosimServer.registerServer(new CO2OutFlowRateSensorPOATie(
                    myCO2OutFlowRateSensorImpl), myCO2OutFlowRateSensorImpl
                    .getModuleName(), myCO2OutFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureCO2OutFlowRateSensor(Node node) {
        CO2OutFlowRateSensor myCO2OutFlowRateSensor = CO2OutFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myCO2OutFlowRateSensor.setInput(CO2ProducerHelper.narrow(BioInitializer
                .grabModule(myID, getInputName(node))), getFlowRateIndex(node));
        mySensors.add(myCO2OutFlowRateSensor);
    }

    private void createCO2StoreLevelSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating CO2StoreLevelSensor with moduleName: "
                    + moduleName);
            CO2StoreLevelSensorImpl myCO2StoreLevelSensorImpl = new CO2StoreLevelSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myCO2StoreLevelSensorImpl, node);
            BiosimServer.registerServer(new CO2StoreLevelSensorPOATie(
                    myCO2StoreLevelSensorImpl), myCO2StoreLevelSensorImpl
                    .getModuleName(), myCO2StoreLevelSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureCO2StoreLevelSensor(Node node) {
        CO2StoreLevelSensor myCO2StoreLevelSensor = CO2StoreLevelSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myCO2StoreLevelSensor.setInput(CO2StoreHelper.narrow(BioInitializer
                .grabModule(myID, getInputName(node))));
        mySensors.add(myCO2StoreLevelSensor);
    }

    private void createO2InFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating O2InFlowRateSensor with moduleName: "
                    + moduleName);
            O2InFlowRateSensorImpl myO2InFlowRateSensorImpl = new O2InFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myO2InFlowRateSensorImpl, node);
            BiosimServer.registerServer(new O2InFlowRateSensorPOATie(
                    myO2InFlowRateSensorImpl), myO2InFlowRateSensorImpl
                    .getModuleName(), myO2InFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureO2InFlowRateSensor(Node node) {
        O2InFlowRateSensor myO2InFlowRateSensor = O2InFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myO2InFlowRateSensor.setInput(O2ConsumerHelper.narrow(BioInitializer
                .grabModule(myID, getInputName(node))), getFlowRateIndex(node));
        mySensors.add(myO2InFlowRateSensor);
    }

    private void createO2OutFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating O2OutFlowRateSensor with moduleName: "
                    + moduleName);
            O2OutFlowRateSensorImpl myO2OutFlowRateSensorImpl = new O2OutFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myO2OutFlowRateSensorImpl, node);
            BiosimServer.registerServer(new O2OutFlowRateSensorPOATie(
                    myO2OutFlowRateSensorImpl), myO2OutFlowRateSensorImpl
                    .getModuleName(), myO2OutFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureO2OutFlowRateSensor(Node node) {
        O2OutFlowRateSensor myO2OutFlowRateSensor = O2OutFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myO2OutFlowRateSensor.setInput(O2ProducerHelper.narrow(BioInitializer
                .grabModule(myID, getInputName(node))), getFlowRateIndex(node));
        mySensors.add(myO2OutFlowRateSensor);
    }

    private void createO2StoreLevelSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating O2StoreLevelSensor with moduleName: "
                    + moduleName);
            O2StoreLevelSensorImpl myO2StoreLevelSensorImpl = new O2StoreLevelSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myO2StoreLevelSensorImpl, node);
            BiosimServer.registerServer(new O2StoreLevelSensorPOATie(
                    myO2StoreLevelSensorImpl), myO2StoreLevelSensorImpl
                    .getModuleName(), myO2StoreLevelSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureO2StoreLevelSensor(Node node) {
        O2StoreLevelSensor myO2StoreLevelSensor = O2StoreLevelSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myO2StoreLevelSensor.setInput(O2StoreHelper.narrow(BioInitializer
                .grabModule(myID, getInputName(node))));
        mySensors.add(myO2StoreLevelSensor);
    }

    private void createH2InFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating H2InFlowRateSensor with moduleName: "
                    + moduleName);
            H2InFlowRateSensorImpl myH2InFlowRateSensorImpl = new H2InFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myH2InFlowRateSensorImpl, node);
            BiosimServer.registerServer(new H2InFlowRateSensorPOATie(
                    myH2InFlowRateSensorImpl), myH2InFlowRateSensorImpl
                    .getModuleName(), myH2InFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureH2InFlowRateSensor(Node node) {
        H2InFlowRateSensor myH2InFlowRateSensor = H2InFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myH2InFlowRateSensor.setInput(H2ConsumerHelper.narrow(BioInitializer
                .grabModule(myID, getInputName(node))), getFlowRateIndex(node));
        mySensors.add(myH2InFlowRateSensor);
    }

    private void createH2OutFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating H2OutFlowRateSensor with moduleName: "
                    + moduleName);
            H2OutFlowRateSensorImpl myH2OutFlowRateSensorImpl = new H2OutFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myH2OutFlowRateSensorImpl, node);
            BiosimServer.registerServer(new H2OutFlowRateSensorPOATie(
                    myH2OutFlowRateSensorImpl), myH2OutFlowRateSensorImpl
                    .getModuleName(), myH2OutFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureH2OutFlowRateSensor(Node node) {
        H2OutFlowRateSensor myH2OutFlowRateSensor = H2OutFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myH2OutFlowRateSensor.setInput(H2ProducerHelper.narrow(BioInitializer
                .grabModule(myID, getInputName(node))), getFlowRateIndex(node));
        mySensors.add(myH2OutFlowRateSensor);
    }

    private void createH2StoreLevelSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating H2StoreLevelSensor with moduleName: "
                    + moduleName);
            H2StoreLevelSensorImpl myH2StoreLevelSensorImpl = new H2StoreLevelSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myH2StoreLevelSensorImpl, node);
            BiosimServer.registerServer(new H2StoreLevelSensorPOATie(
                    myH2StoreLevelSensorImpl), myH2StoreLevelSensorImpl
                    .getModuleName(), myH2StoreLevelSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureH2StoreLevelSensor(Node node) {
        H2StoreLevelSensor myH2StoreLevelSensor = H2StoreLevelSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myH2StoreLevelSensor.setInput(H2StoreHelper.narrow(BioInitializer
                .grabModule(myID, getInputName(node))));
        mySensors.add(myH2StoreLevelSensor);
    }

    private void createNitrogenInFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating NitrogenInFlowRateSensor with moduleName: "
                            + moduleName);
            NitrogenInFlowRateSensorImpl myNitrogenInFlowRateSensorImpl = new NitrogenInFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myNitrogenInFlowRateSensorImpl, node);
            BiosimServer.registerServer(new NitrogenInFlowRateSensorPOATie(
                    myNitrogenInFlowRateSensorImpl),
                    myNitrogenInFlowRateSensorImpl.getModuleName(),
                    myNitrogenInFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureNitrogenInFlowRateSensor(Node node) {
        NitrogenInFlowRateSensor myNitrogenInFlowRateSensor = NitrogenInFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myNitrogenInFlowRateSensor.setInput(NitrogenConsumerHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))),
                getFlowRateIndex(node));
        mySensors.add(myNitrogenInFlowRateSensor);
    }

    private void createNitrogenOutFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating NitrogenOutFlowRateSensor with moduleName: "
                            + moduleName);
            NitrogenOutFlowRateSensorImpl myNitrogenOutFlowRateSensorImpl = new NitrogenOutFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer
                    .setupBioModule(myNitrogenOutFlowRateSensorImpl, node);
            BiosimServer.registerServer(new NitrogenOutFlowRateSensorPOATie(
                    myNitrogenOutFlowRateSensorImpl),
                    myNitrogenOutFlowRateSensorImpl.getModuleName(),
                    myNitrogenOutFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureNitrogenOutFlowRateSensor(Node node) {
        NitrogenOutFlowRateSensor myNitrogenOutFlowRateSensor = NitrogenOutFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myNitrogenOutFlowRateSensor.setInput(NitrogenProducerHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))),
                getFlowRateIndex(node));
        mySensors.add(myNitrogenOutFlowRateSensor);
    }

    private void createNitrogenStoreLevelSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating NitrogenStoreLevelSensor with moduleName: "
                            + moduleName);
            NitrogenStoreLevelSensorImpl myNitrogenStoreLevelSensorImpl = new NitrogenStoreLevelSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myNitrogenStoreLevelSensorImpl, node);
            BiosimServer.registerServer(new NitrogenStoreLevelSensorPOATie(
                    myNitrogenStoreLevelSensorImpl),
                    myNitrogenStoreLevelSensorImpl.getModuleName(),
                    myNitrogenStoreLevelSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureNitrogenStoreLevelSensor(Node node) {
        NitrogenStoreLevelSensor myNitrogenStoreLevelSensor = NitrogenStoreLevelSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myNitrogenStoreLevelSensor.setInput(NitrogenStoreHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))));
        mySensors.add(myNitrogenStoreLevelSensor);
    }

    private void crawlAirSensors(Node node, boolean firstPass) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("CO2InFlowRateSensor")) {
                if (firstPass)
                    createCO2InFlowRateSensor(child);
                else
                    configureCO2InFlowRateSensor(child);
            } else if (childName.equals("CO2OutFlowRateSensor")) {
                if (firstPass)
                    createCO2OutFlowRateSensor(child);
                else
                    configureCO2OutFlowRateSensor(child);
            } else if (childName.equals("CO2StoreLevelSensor")) {
                if (firstPass)
                    createCO2StoreLevelSensor(child);
                else
                    configureCO2StoreLevelSensor(child);
            } else if (childName.equals("O2InFlowRateSensor")) {
                if (firstPass)
                    createO2InFlowRateSensor(child);
                else
                    configureO2InFlowRateSensor(child);
            } else if (childName.equals("O2OutFlowRateSensor")) {
                if (firstPass)
                    createO2OutFlowRateSensor(child);
                else
                    configureO2OutFlowRateSensor(child);
            } else if (childName.equals("O2StoreLevelSensor")) {
                if (firstPass)
                    createO2StoreLevelSensor(child);
                else
                    configureO2StoreLevelSensor(child);
            } else if (childName.equals("H2InFlowRateSensor")) {
                if (firstPass)
                    createH2InFlowRateSensor(child);
                else
                    configureH2InFlowRateSensor(child);
            } else if (childName.equals("H2OutFlowRateSensor")) {
                if (firstPass)
                    createH2OutFlowRateSensor(child);
                else
                    configureH2OutFlowRateSensor(child);
            } else if (childName.equals("H2StoreLevelSensor")) {
                if (firstPass)
                    createH2StoreLevelSensor(child);
                else
                    configureH2StoreLevelSensor(child);
            } else if (childName.equals("NitrogenInFlowRateSensor")) {
                if (firstPass)
                    createNitrogenInFlowRateSensor(child);
                else
                    configureNitrogenInFlowRateSensor(child);
            } else if (childName.equals("NitrogenOutFlowRateSensor")) {
                if (firstPass)
                    createNitrogenOutFlowRateSensor(child);
                else
                    configureNitrogenOutFlowRateSensor(child);
            } else if (childName.equals("NitrogenStoreLevelSensor")) {
                if (firstPass)
                    createNitrogenStoreLevelSensor(child);
                else
                    configureNitrogenStoreLevelSensor(child);
            }
            child = child.getNextSibling();
        }
    }

    //Crew
    private void createCrewGroupDeathSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating CrewGroupDeathSensor with moduleName: "
                    + moduleName);
            CrewGroupDeathSensorImpl myCrewGroupDeathSensorImpl = new CrewGroupDeathSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myCrewGroupDeathSensorImpl, node);
            BiosimServer.registerServer(new CrewGroupDeathSensorPOATie(
                    myCrewGroupDeathSensorImpl), myCrewGroupDeathSensorImpl
                    .getModuleName(), myCrewGroupDeathSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureCrewGroupDeathSensor(Node node) {
        CrewGroupDeathSensor myCrewGroupDeathSensor = CrewGroupDeathSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myCrewGroupDeathSensor.setInput(CrewGroupHelper.narrow(BioInitializer
                .grabModule(myID, getInputName(node))));
        mySensors.add(myCrewGroupDeathSensor);
    }

    private void createCrewGroupAnyDeadSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating CrewGroupAnyDeadSensor with moduleName: "
                    + moduleName);
            CrewGroupAnyDeadSensorImpl myCrewGroupAnyDeadSensorImpl = new CrewGroupAnyDeadSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myCrewGroupAnyDeadSensorImpl, node);
            BiosimServer.registerServer(new CrewGroupAnyDeadSensorPOATie(
                    myCrewGroupAnyDeadSensorImpl), myCrewGroupAnyDeadSensorImpl
                    .getModuleName(), myCrewGroupAnyDeadSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureCrewGroupAnyDeadSensor(Node node) {
        CrewGroupAnyDeadSensor myCrewGroupAnyDeadSensor = CrewGroupAnyDeadSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myCrewGroupAnyDeadSensor.setInput(CrewGroupHelper.narrow(BioInitializer
                .grabModule(myID, getInputName(node))));
        mySensors.add(myCrewGroupAnyDeadSensor);
    }

    private void createCrewGroupProductivitySensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating CrewGroupProductivitySensor with moduleName: "
                            + moduleName);
            CrewGroupProductivitySensorImpl myCrewGroupProductivitySensorImpl = new CrewGroupProductivitySensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myCrewGroupProductivitySensorImpl,
                    node);
            BiosimServer.registerServer(new CrewGroupProductivitySensorPOATie(
                    myCrewGroupProductivitySensorImpl),
                    myCrewGroupProductivitySensorImpl.getModuleName(),
                    myCrewGroupProductivitySensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureCrewGroupProductivitySensor(Node node) {
        CrewGroupProductivitySensor myCrewGroupProductivitySensor = CrewGroupProductivitySensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myCrewGroupProductivitySensor.setInput(CrewGroupHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))));
        mySensors.add(myCrewGroupProductivitySensor);
    }

    private void crawlCrewSensors(Node node, boolean firstPass) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("CrewGroupDeathSensor")) {
                if (firstPass)
                    createCrewGroupDeathSensor(child);
                else
                    configureCrewGroupDeathSensor(child);
            } else if (childName.equals("CrewGroupAnyDeadSensor")) {
                if (firstPass)
                    createCrewGroupAnyDeadSensor(child);
                else
                    configureCrewGroupAnyDeadSensor(child);
            } else if (childName.equals("CrewGroupProductivitySensor")) {
                if (firstPass)
                    createCrewGroupProductivitySensor(child);
                else
                    configureCrewGroupProductivitySensor(child);
            }
            child = child.getNextSibling();
        }
    }

    //Environment
    private void createAirInFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating AirInFlowRateSensor with moduleName: "
                    + moduleName);
            AirInFlowRateSensorImpl myAirInFlowRateSensorImpl = new AirInFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myAirInFlowRateSensorImpl, node);
            BiosimServer.registerServer(new AirInFlowRateSensorPOATie(
                    myAirInFlowRateSensorImpl), myAirInFlowRateSensorImpl
                    .getModuleName(), myAirInFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureAirInFlowRateSensor(Node node) {
        AirInFlowRateSensor myAirInFlowRateSensor = AirInFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myAirInFlowRateSensor.setInput(AirConsumerHelper.narrow(BioInitializer
                .grabModule(myID, getInputName(node))), getFlowRateIndex(node));
        mySensors.add(myAirInFlowRateSensor);
    }

    private void createAirOutFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating AirOutFlowRateSensor with moduleName: "
                    + moduleName);
            AirOutFlowRateSensorImpl myAirOutFlowRateSensorImpl = new AirOutFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myAirOutFlowRateSensorImpl, node);
            BiosimServer.registerServer(new AirOutFlowRateSensorPOATie(
                    myAirOutFlowRateSensorImpl), myAirOutFlowRateSensorImpl
                    .getModuleName(), myAirOutFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureAirOutFlowRateSensor(Node node) {
        AirOutFlowRateSensor myAirOutFlowRateSensor = AirOutFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myAirOutFlowRateSensor.setInput(AirProducerHelper.narrow(BioInitializer
                .grabModule(myID, getInputName(node))), getFlowRateIndex(node));
        mySensors.add(myAirOutFlowRateSensor);
    }

    private void createCO2AirConcentrationSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating CO2AirConcentrationSensor with moduleName: "
                            + moduleName);
            CO2AirConcentrationSensorImpl myCO2AirConcentrationSensorImpl = new CO2AirConcentrationSensorImpl(
                    myID, moduleName);
            BioInitializer
                    .setupBioModule(myCO2AirConcentrationSensorImpl, node);
            BiosimServer.registerServer(new CO2AirConcentrationSensorPOATie(
                    myCO2AirConcentrationSensorImpl),
                    myCO2AirConcentrationSensorImpl.getModuleName(),
                    myCO2AirConcentrationSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureCO2AirConcentrationSensor(Node node) {
        CO2AirConcentrationSensor myCO2AirConcentrationSensor = CO2AirConcentrationSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myCO2AirConcentrationSensor.setInput(SimEnvironmentHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))));
        mySensors.add(myCO2AirConcentrationSensor);
    }

    private void createCO2AirPressureSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating CO2AirPressureSensor with moduleName: "
                    + moduleName);
            CO2AirPressureSensorImpl myCO2AirPressureSensorImpl = new CO2AirPressureSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myCO2AirPressureSensorImpl, node);
            BiosimServer.registerServer(new CO2AirPressureSensorPOATie(
                    myCO2AirPressureSensorImpl), myCO2AirPressureSensorImpl
                    .getModuleName(), myCO2AirPressureSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureCO2AirPressureSensor(Node node) {
        CO2AirPressureSensor myCO2AirPressureSensor = CO2AirPressureSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myCO2AirPressureSensor.setInput(SimEnvironmentHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))));
        mySensors.add(myCO2AirPressureSensor);
    }

    private void createCO2AirEnvironmentInFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating CO2AirEnvironmentInFlowRateSensor with moduleName: "
                            + moduleName);
            CO2AirEnvironmentInFlowRateSensorImpl myCO2AirEnvironmentInFlowRateSensorImpl = new CO2AirEnvironmentInFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(
                    myCO2AirEnvironmentInFlowRateSensorImpl, node);
            BiosimServer.registerServer(
                    new CO2AirEnvironmentInFlowRateSensorPOATie(
                            myCO2AirEnvironmentInFlowRateSensorImpl),
                    myCO2AirEnvironmentInFlowRateSensorImpl.getModuleName(),
                    myCO2AirEnvironmentInFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureCO2AirEnvironmentInFlowRateSensor(Node node) {
        CO2AirEnvironmentInFlowRateSensor myCO2AirEnvironmentInFlowRateSensor = CO2AirEnvironmentInFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myCO2AirEnvironmentInFlowRateSensor.setInput(CO2AirConsumerHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))),
                getFlowRateIndex(node));
        mySensors.add(myCO2AirEnvironmentInFlowRateSensor);
    }

    private void createCO2AirEnvironmentOutFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating CO2AirEnvironmentOutFlowRateSensor with moduleName: "
                            + moduleName);
            CO2AirEnvironmentOutFlowRateSensorImpl myCO2AirEnvironmentOutFlowRateSensorImpl = new CO2AirEnvironmentOutFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(
                    myCO2AirEnvironmentOutFlowRateSensorImpl, node);
            BiosimServer.registerServer(
                    new CO2AirEnvironmentOutFlowRateSensorPOATie(
                            myCO2AirEnvironmentOutFlowRateSensorImpl),
                    myCO2AirEnvironmentOutFlowRateSensorImpl.getModuleName(),
                    myCO2AirEnvironmentOutFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureCO2AirEnvironmentOutFlowRateSensor(Node node) {
        CO2AirEnvironmentOutFlowRateSensor myCO2AirEnvironmentOutFlowRateSensor = CO2AirEnvironmentOutFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myCO2AirEnvironmentOutFlowRateSensor.setInput(CO2AirProducerHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))),
                getFlowRateIndex(node));
        mySensors.add(myCO2AirEnvironmentOutFlowRateSensor);
    }

    private void createCO2AirStoreInFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating CO2AirStoreInFlowRateSensor with moduleName: "
                            + moduleName);
            CO2AirStoreInFlowRateSensorImpl myCO2AirStoreInFlowRateSensorImpl = new CO2AirStoreInFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myCO2AirStoreInFlowRateSensorImpl,
                    node);
            BiosimServer.registerServer(new CO2AirStoreInFlowRateSensorPOATie(
                    myCO2AirStoreInFlowRateSensorImpl),
                    myCO2AirStoreInFlowRateSensorImpl.getModuleName(),
                    myCO2AirStoreInFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureCO2AirStoreInFlowRateSensor(Node node) {
        CO2AirStoreInFlowRateSensor myCO2AirStoreInFlowRateSensor = CO2AirStoreInFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myCO2AirStoreInFlowRateSensor.setInput(CO2AirConsumerHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))),
                getFlowRateIndex(node));
        mySensors.add(myCO2AirStoreInFlowRateSensor);
    }

    private void createCO2AirStoreOutFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating CO2AirStoreOutFlowRateSensor with moduleName: "
                            + moduleName);
            CO2AirStoreOutFlowRateSensorImpl myCO2AirStoreOutFlowRateSensorImpl = new CO2AirStoreOutFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myCO2AirStoreOutFlowRateSensorImpl,
                    node);
            BiosimServer.registerServer(new CO2AirStoreOutFlowRateSensorPOATie(
                    myCO2AirStoreOutFlowRateSensorImpl),
                    myCO2AirStoreOutFlowRateSensorImpl.getModuleName(),
                    myCO2AirStoreOutFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureCO2AirStoreOutFlowRateSensor(Node node) {
        CO2AirStoreOutFlowRateSensor myCO2AirStoreOutFlowRateSensor = CO2AirStoreOutFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myCO2AirStoreOutFlowRateSensor.setInput(CO2AirProducerHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))),
                getFlowRateIndex(node));
        mySensors.add(myCO2AirStoreOutFlowRateSensor);
    }

    private void createO2AirConcentrationSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating O2AirConcentrationSensor with moduleName: "
                            + moduleName);
            O2AirConcentrationSensorImpl myO2AirConcentrationSensorImpl = new O2AirConcentrationSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myO2AirConcentrationSensorImpl, node);
            BiosimServer.registerServer(new O2AirConcentrationSensorPOATie(
                    myO2AirConcentrationSensorImpl),
                    myO2AirConcentrationSensorImpl.getModuleName(),
                    myO2AirConcentrationSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureO2AirConcentrationSensor(Node node) {
        O2AirConcentrationSensor myO2AirConcentrationSensor = O2AirConcentrationSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myO2AirConcentrationSensor.setInput(SimEnvironmentHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))));
        mySensors.add(myO2AirConcentrationSensor);
    }

    private void createO2AirPressureSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating O2AirPressureSensor with moduleName: "
                    + moduleName);
            O2AirPressureSensorImpl myO2AirPressureSensorImpl = new O2AirPressureSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myO2AirPressureSensorImpl, node);
            BiosimServer.registerServer(new O2AirPressureSensorPOATie(
                    myO2AirPressureSensorImpl), myO2AirPressureSensorImpl
                    .getModuleName(), myO2AirPressureSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureO2AirPressureSensor(Node node) {
        O2AirPressureSensor myO2AirPressureSensor = O2AirPressureSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myO2AirPressureSensor.setInput(SimEnvironmentHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))));
        mySensors.add(myO2AirPressureSensor);
    }

    private void createO2AirEnvironmentInFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating O2AirEnvironmentInFlowRateSensor with moduleName: "
                            + moduleName);
            O2AirEnvironmentInFlowRateSensorImpl myO2AirEnvironmentInFlowRateSensorImpl = new O2AirEnvironmentInFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(
                    myO2AirEnvironmentInFlowRateSensorImpl, node);
            BiosimServer.registerServer(
                    new O2AirEnvironmentInFlowRateSensorPOATie(
                            myO2AirEnvironmentInFlowRateSensorImpl),
                    myO2AirEnvironmentInFlowRateSensorImpl.getModuleName(),
                    myO2AirEnvironmentInFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureO2AirEnvironmentInFlowRateSensor(Node node) {
        O2AirEnvironmentInFlowRateSensor myO2AirEnvironmentInFlowRateSensor = O2AirEnvironmentInFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myO2AirEnvironmentInFlowRateSensor.setInput(O2AirConsumerHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))),
                getFlowRateIndex(node));
        mySensors.add(myO2AirEnvironmentInFlowRateSensor);
    }

    private void createO2AirEnvironmentOutFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating O2AirEnvironmentOutFlowRateSensor with moduleName: "
                            + moduleName);
            O2AirEnvironmentOutFlowRateSensorImpl myO2AirEnvironmentOutFlowRateSensorImpl = new O2AirEnvironmentOutFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(
                    myO2AirEnvironmentOutFlowRateSensorImpl, node);
            BiosimServer.registerServer(
                    new O2AirEnvironmentOutFlowRateSensorPOATie(
                            myO2AirEnvironmentOutFlowRateSensorImpl),
                    myO2AirEnvironmentOutFlowRateSensorImpl.getModuleName(),
                    myO2AirEnvironmentOutFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureO2AirEnvironmentOutFlowRateSensor(Node node) {
        O2AirEnvironmentOutFlowRateSensor myO2AirEnvironmentOutFlowRateSensor = O2AirEnvironmentOutFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myO2AirEnvironmentOutFlowRateSensor.setInput(O2AirProducerHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))),
                getFlowRateIndex(node));
        mySensors.add(myO2AirEnvironmentOutFlowRateSensor);
    }

    private void createO2AirStoreInFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating O2AirStoreInFlowRateSensor with moduleName: "
                            + moduleName);
            O2AirStoreInFlowRateSensorImpl myO2AirStoreInFlowRateSensorImpl = new O2AirStoreInFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myO2AirStoreInFlowRateSensorImpl,
                    node);
            BiosimServer.registerServer(new O2AirStoreInFlowRateSensorPOATie(
                    myO2AirStoreInFlowRateSensorImpl),
                    myO2AirStoreInFlowRateSensorImpl.getModuleName(),
                    myO2AirStoreInFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureO2AirStoreInFlowRateSensor(Node node) {
        O2AirStoreInFlowRateSensor myO2AirStoreInFlowRateSensor = O2AirStoreInFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myO2AirStoreInFlowRateSensor.setInput(O2AirConsumerHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))),
                getFlowRateIndex(node));
        mySensors.add(myO2AirStoreInFlowRateSensor);
    }

    private void createO2AirStoreOutFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating O2AirStoreOutFlowRateSensor with moduleName: "
                            + moduleName);
            O2AirStoreOutFlowRateSensorImpl myO2AirStoreOutFlowRateSensorImpl = new O2AirStoreOutFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myO2AirStoreOutFlowRateSensorImpl,
                    node);
            BiosimServer.registerServer(new O2AirStoreOutFlowRateSensorPOATie(
                    myO2AirStoreOutFlowRateSensorImpl),
                    myO2AirStoreOutFlowRateSensorImpl.getModuleName(),
                    myO2AirStoreOutFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureO2AirStoreOutFlowRateSensor(Node node) {
        O2AirStoreOutFlowRateSensor myO2AirStoreOutFlowRateSensor = O2AirStoreOutFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myO2AirStoreOutFlowRateSensor.setInput(O2AirProducerHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))),
                getFlowRateIndex(node));
        mySensors.add(myO2AirStoreOutFlowRateSensor);
    }

    private void createOtherAirConcentrationSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating OtherAirConcentrationSensor with moduleName: "
                            + moduleName);
            OtherAirConcentrationSensorImpl myOtherAirConcentrationSensorImpl = new OtherAirConcentrationSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myOtherAirConcentrationSensorImpl,
                    node);
            BiosimServer.registerServer(new OtherAirConcentrationSensorPOATie(
                    myOtherAirConcentrationSensorImpl),
                    myOtherAirConcentrationSensorImpl.getModuleName(),
                    myOtherAirConcentrationSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureOtherAirConcentrationSensor(Node node) {
        OtherAirConcentrationSensor myOtherAirConcentrationSensor = OtherAirConcentrationSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myOtherAirConcentrationSensor.setInput(SimEnvironmentHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))));
        mySensors.add(myOtherAirConcentrationSensor);
    }

    private void createOtherAirPressureSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating OtherAirPressureSensor with moduleName: "
                    + moduleName);
            OtherAirPressureSensorImpl myOtherAirPressureSensorImpl = new OtherAirPressureSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myOtherAirPressureSensorImpl, node);
            BiosimServer.registerServer(new OtherAirPressureSensorPOATie(
                    myOtherAirPressureSensorImpl), myOtherAirPressureSensorImpl
                    .getModuleName(), myOtherAirPressureSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureOtherAirPressureSensor(Node node) {
        OtherAirPressureSensor myOtherAirPressureSensor = OtherAirPressureSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myOtherAirPressureSensor.setInput(SimEnvironmentHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))));
        mySensors.add(myOtherAirPressureSensor);
    }

    private void createWaterAirConcentrationSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating WaterAirConcentrationSensor with moduleName: "
                            + moduleName);
            WaterAirConcentrationSensorImpl myWaterAirConcentrationSensorImpl = new WaterAirConcentrationSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myWaterAirConcentrationSensorImpl,
                    node);
            BiosimServer.registerServer(new WaterAirConcentrationSensorPOATie(
                    myWaterAirConcentrationSensorImpl),
                    myWaterAirConcentrationSensorImpl.getModuleName(),
                    myWaterAirConcentrationSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureWaterAirConcentrationSensor(Node node) {
        WaterAirConcentrationSensor myWaterAirConcentrationSensor = WaterAirConcentrationSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myWaterAirConcentrationSensor.setInput(SimEnvironmentHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))));
        mySensors.add(myWaterAirConcentrationSensor);
    }

    private void createWaterAirPressureSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating WaterAirPressureSensor with moduleName: "
                    + moduleName);
            WaterAirPressureSensorImpl myWaterAirPressureSensorImpl = new WaterAirPressureSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myWaterAirPressureSensorImpl, node);
            BiosimServer.registerServer(new WaterAirPressureSensorPOATie(
                    myWaterAirPressureSensorImpl), myWaterAirPressureSensorImpl
                    .getModuleName(), myWaterAirPressureSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureWaterAirPressureSensor(Node node) {
        WaterAirPressureSensor myWaterAirPressureSensor = WaterAirPressureSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myWaterAirPressureSensor.setInput(SimEnvironmentHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))));
        mySensors.add(myWaterAirPressureSensor);
    }

    private void createWaterAirEnvironmentInFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating WaterAirEnvironmentInFlowRateSensor with moduleName: "
                            + moduleName);
            WaterAirEnvironmentInFlowRateSensorImpl myWaterAirEnvironmentInFlowRateSensorImpl = new WaterAirEnvironmentInFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(
                    myWaterAirEnvironmentInFlowRateSensorImpl, node);
            BiosimServer.registerServer(
                    new WaterAirEnvironmentInFlowRateSensorPOATie(
                            myWaterAirEnvironmentInFlowRateSensorImpl),
                    myWaterAirEnvironmentInFlowRateSensorImpl.getModuleName(),
                    myWaterAirEnvironmentInFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureWaterAirEnvironmentInFlowRateSensor(Node node) {
        WaterAirEnvironmentInFlowRateSensor myWaterAirEnvironmentInFlowRateSensor = WaterAirEnvironmentInFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myWaterAirEnvironmentInFlowRateSensor.setInput(WaterAirConsumerHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))),
                getFlowRateIndex(node));
        mySensors.add(myWaterAirEnvironmentInFlowRateSensor);
    }

    private void createWaterAirEnvironmentOutFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating WaterAirEnvironmentOutFlowRateSensor with moduleName: "
                            + moduleName);
            WaterAirEnvironmentOutFlowRateSensorImpl myWaterAirEnvironmentOutFlowRateSensorImpl = new WaterAirEnvironmentOutFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(
                    myWaterAirEnvironmentOutFlowRateSensorImpl, node);
            BiosimServer.registerServer(
                    new WaterAirEnvironmentOutFlowRateSensorPOATie(
                            myWaterAirEnvironmentOutFlowRateSensorImpl),
                    myWaterAirEnvironmentOutFlowRateSensorImpl.getModuleName(),
                    myWaterAirEnvironmentOutFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureWaterAirEnvironmentOutFlowRateSensor(Node node) {
        WaterAirEnvironmentOutFlowRateSensor myWaterAirEnvironmentOutFlowRateSensor = WaterAirEnvironmentOutFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myWaterAirEnvironmentOutFlowRateSensor.setInput(WaterAirProducerHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))),
                getFlowRateIndex(node));
        mySensors.add(myWaterAirEnvironmentOutFlowRateSensor);
    }

    private void createWaterAirStoreInFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating WaterAirStoreInFlowRateSensor with moduleName: "
                            + moduleName);
            WaterAirStoreInFlowRateSensorImpl myWaterAirStoreInFlowRateSensorImpl = new WaterAirStoreInFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myWaterAirStoreInFlowRateSensorImpl,
                    node);
            BiosimServer.registerServer(
                    new WaterAirStoreInFlowRateSensorPOATie(
                            myWaterAirStoreInFlowRateSensorImpl),
                    myWaterAirStoreInFlowRateSensorImpl.getModuleName(),
                    myWaterAirStoreInFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureWaterAirStoreInFlowRateSensor(Node node) {
        WaterAirStoreInFlowRateSensor myWaterAirStoreInFlowRateSensor = WaterAirStoreInFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myWaterAirStoreInFlowRateSensor.setInput(WaterAirConsumerHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))),
                getFlowRateIndex(node));
        mySensors.add(myWaterAirStoreInFlowRateSensor);
    }

    private void createWaterAirStoreOutFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating WaterAirStoreOutFlowRateSensor with moduleName: "
                            + moduleName);
            WaterAirStoreOutFlowRateSensorImpl myWaterAirStoreOutFlowRateSensorImpl = new WaterAirStoreOutFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myWaterAirStoreOutFlowRateSensorImpl,
                    node);
            BiosimServer.registerServer(
                    new WaterAirStoreOutFlowRateSensorPOATie(
                            myWaterAirStoreOutFlowRateSensorImpl),
                    myWaterAirStoreOutFlowRateSensorImpl.getModuleName(),
                    myWaterAirStoreOutFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureWaterAirStoreOutFlowRateSensor(Node node) {
        WaterAirStoreOutFlowRateSensor myWaterAirStoreOutFlowRateSensor = WaterAirStoreOutFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myWaterAirStoreOutFlowRateSensor.setInput(WaterAirProducerHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))),
                getFlowRateIndex(node));
        mySensors.add(myWaterAirStoreOutFlowRateSensor);
    }

    private void createNitrogenAirConcentrationSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating NitrogenAirConcentrationSensor with moduleName: "
                            + moduleName);
            NitrogenAirConcentrationSensorImpl myNitrogenAirConcentrationSensorImpl = new NitrogenAirConcentrationSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myNitrogenAirConcentrationSensorImpl,
                    node);
            BiosimServer.registerServer(
                    new NitrogenAirConcentrationSensorPOATie(
                            myNitrogenAirConcentrationSensorImpl),
                    myNitrogenAirConcentrationSensorImpl.getModuleName(),
                    myNitrogenAirConcentrationSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureNitrogenAirConcentrationSensor(Node node) {
        NitrogenAirConcentrationSensor myNitrogenAirConcentrationSensor = NitrogenAirConcentrationSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myNitrogenAirConcentrationSensor.setInput(SimEnvironmentHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))));
        mySensors.add(myNitrogenAirConcentrationSensor);
    }

    private void createNitrogenAirPressureSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating NitrogenAirPressureSensor with moduleName: "
                            + moduleName);
            NitrogenAirPressureSensorImpl myNitrogenAirPressureSensorImpl = new NitrogenAirPressureSensorImpl(
                    myID, moduleName);
            BioInitializer
                    .setupBioModule(myNitrogenAirPressureSensorImpl, node);
            BiosimServer.registerServer(new NitrogenAirPressureSensorPOATie(
                    myNitrogenAirPressureSensorImpl),
                    myNitrogenAirPressureSensorImpl.getModuleName(),
                    myNitrogenAirPressureSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureNitrogenAirPressureSensor(Node node) {
        NitrogenAirPressureSensor myNitrogenAirPressureSensor = NitrogenAirPressureSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myNitrogenAirPressureSensor.setInput(SimEnvironmentHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))));
        mySensors.add(myNitrogenAirPressureSensor);
    }

    private void createNitrogenAirEnvironmentInFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating NitrogenAirEnvironmentInFlowRateSensor with moduleName: "
                            + moduleName);
            NitrogenAirEnvironmentInFlowRateSensorImpl myNitrogenAirEnvironmentInFlowRateSensorImpl = new NitrogenAirEnvironmentInFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(
                    myNitrogenAirEnvironmentInFlowRateSensorImpl, node);
            BiosimServer.registerServer(
                    new NitrogenAirEnvironmentInFlowRateSensorPOATie(
                            myNitrogenAirEnvironmentInFlowRateSensorImpl),
                    myNitrogenAirEnvironmentInFlowRateSensorImpl
                            .getModuleName(),
                    myNitrogenAirEnvironmentInFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureNitrogenAirEnvironmentInFlowRateSensor(Node node) {
        NitrogenAirEnvironmentInFlowRateSensor myNitrogenAirEnvironmentInFlowRateSensor = NitrogenAirEnvironmentInFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myNitrogenAirEnvironmentInFlowRateSensor.setInput(
                NitrogenAirConsumerHelper.narrow(BioInitializer.grabModule(
                        myID, getInputName(node))), getFlowRateIndex(node));
        mySensors.add(myNitrogenAirEnvironmentInFlowRateSensor);
    }

    private void createNitrogenAirEnvironmentOutFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating NitrogenAirEnvironmentOutFlowRateSensor with moduleName: "
                            + moduleName);
            NitrogenAirEnvironmentOutFlowRateSensorImpl myNitrogenAirEnvironmentOutFlowRateSensorImpl = new NitrogenAirEnvironmentOutFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(
                    myNitrogenAirEnvironmentOutFlowRateSensorImpl, node);
            BiosimServer.registerServer(
                    new NitrogenAirEnvironmentOutFlowRateSensorPOATie(
                            myNitrogenAirEnvironmentOutFlowRateSensorImpl),
                    myNitrogenAirEnvironmentOutFlowRateSensorImpl
                            .getModuleName(),
                    myNitrogenAirEnvironmentOutFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureNitrogenAirEnvironmentOutFlowRateSensor(Node node) {
        NitrogenAirEnvironmentOutFlowRateSensor myNitrogenAirEnvironmentOutFlowRateSensor = NitrogenAirEnvironmentOutFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myNitrogenAirEnvironmentOutFlowRateSensor.setInput(
                NitrogenAirProducerHelper.narrow(BioInitializer.grabModule(
                        myID, getInputName(node))), getFlowRateIndex(node));
        mySensors.add(myNitrogenAirEnvironmentOutFlowRateSensor);
    }

    private void createNitrogenAirStoreInFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating NitrogenAirStoreInFlowRateSensor with moduleName: "
                            + moduleName);
            NitrogenAirStoreInFlowRateSensorImpl myNitrogenAirStoreInFlowRateSensorImpl = new NitrogenAirStoreInFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(
                    myNitrogenAirStoreInFlowRateSensorImpl, node);
            BiosimServer.registerServer(
                    new NitrogenAirStoreInFlowRateSensorPOATie(
                            myNitrogenAirStoreInFlowRateSensorImpl),
                    myNitrogenAirStoreInFlowRateSensorImpl.getModuleName(),
                    myNitrogenAirStoreInFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureNitrogenAirStoreInFlowRateSensor(Node node) {
        NitrogenAirStoreInFlowRateSensor myNitrogenAirStoreInFlowRateSensor = NitrogenAirStoreInFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myNitrogenAirStoreInFlowRateSensor.setInput(NitrogenAirConsumerHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))),
                getFlowRateIndex(node));
        mySensors.add(myNitrogenAirStoreInFlowRateSensor);
    }

    private void createNitrogenAirStoreOutFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating NitrogenAirStoreOutFlowRateSensor with moduleName: "
                            + moduleName);
            NitrogenAirStoreOutFlowRateSensorImpl myNitrogenAirStoreOutFlowRateSensorImpl = new NitrogenAirStoreOutFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(
                    myNitrogenAirStoreOutFlowRateSensorImpl, node);
            BiosimServer.registerServer(
                    new NitrogenAirStoreOutFlowRateSensorPOATie(
                            myNitrogenAirStoreOutFlowRateSensorImpl),
                    myNitrogenAirStoreOutFlowRateSensorImpl.getModuleName(),
                    myNitrogenAirStoreOutFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureNitrogenAirStoreOutFlowRateSensor(Node node) {
        NitrogenAirStoreOutFlowRateSensor myNitrogenAirStoreOutFlowRateSensor = NitrogenAirStoreOutFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myNitrogenAirStoreOutFlowRateSensor.setInput(NitrogenAirProducerHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))),
                getFlowRateIndex(node));
        mySensors.add(myNitrogenAirStoreOutFlowRateSensor);
    }

    private void crawlEnvironmentSensors(Node node, boolean firstPass) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("AirInFlowRateSensor")) {
                if (firstPass)
                    createAirInFlowRateSensor(child);
                else
                    configureAirInFlowRateSensor(child);
            } else if (childName.equals("AirOutFlowRateSensor")) {
                if (firstPass)
                    createAirOutFlowRateSensor(child);
                else
                    configureAirOutFlowRateSensor(child);
            } else if (childName.equals("CO2AirConcentrationSensor")) {
                if (firstPass)
                    createCO2AirConcentrationSensor(child);
                else
                    configureCO2AirConcentrationSensor(child);
            } else if (childName.equals("CO2AirEnvironmentInFlowRateSensor")) {
                if (firstPass)
                    createCO2AirEnvironmentInFlowRateSensor(child);
                else
                    configureCO2AirEnvironmentInFlowRateSensor(child);
            } else if (childName.equals("CO2AirEnvironmentOutFlowRateSensor")) {
                if (firstPass)
                    createCO2AirEnvironmentOutFlowRateSensor(child);
                else
                    configureCO2AirEnvironmentOutFlowRateSensor(child);
            } else if (childName.equals("CO2AirPressureSensor")) {
                if (firstPass)
                    createCO2AirPressureSensor(child);
                else
                    configureCO2AirPressureSensor(child);
            } else if (childName.equals("CO2AirStoreInFlowRateSensor")) {
                if (firstPass)
                    createCO2AirStoreInFlowRateSensor(child);
                else
                    configureCO2AirStoreInFlowRateSensor(child);
            } else if (childName.equals("CO2AirStoreOutFlowRateSensor")) {
                if (firstPass)
                    createCO2AirStoreOutFlowRateSensor(child);
                else
                    configureCO2AirStoreOutFlowRateSensor(child);
            } else if (childName.equals("O2AirConcentrationSensor")) {
                if (firstPass)
                    createO2AirConcentrationSensor(child);
                else
                    configureO2AirConcentrationSensor(child);
            } else if (childName.equals("O2AirEnvironmentInFlowRateSensor")) {
                if (firstPass)
                    createO2AirEnvironmentInFlowRateSensor(child);
                else
                    configureO2AirEnvironmentInFlowRateSensor(child);
            } else if (childName.equals("O2AirEnvironmentOutFlowRateSensor")) {
                if (firstPass)
                    createO2AirEnvironmentOutFlowRateSensor(child);
                else
                    configureO2AirEnvironmentOutFlowRateSensor(child);
            } else if (childName.equals("O2AirPressureSensor")) {
                if (firstPass)
                    createO2AirPressureSensor(child);
                else
                    configureO2AirPressureSensor(child);
            } else if (childName.equals("O2AirStoreInFlowRateSensor")) {
                if (firstPass)
                    createO2AirStoreInFlowRateSensor(child);
                else
                    configureO2AirStoreInFlowRateSensor(child);
            } else if (childName.equals("O2AirStoreOutFlowRateSensor")) {
                if (firstPass)
                    createO2AirStoreOutFlowRateSensor(child);
                else
                    configureO2AirStoreOutFlowRateSensor(child);
            } else if (childName.equals("OtherAirConcentrationSensor")) {
                if (firstPass)
                    createOtherAirConcentrationSensor(child);
                else
                    configureOtherAirConcentrationSensor(child);
            } else if (childName.equals("OtherAirPressureSensor")) {
                if (firstPass)
                    createOtherAirPressureSensor(child);
                else
                    configureOtherAirPressureSensor(child);
            } else if (childName.equals("WaterAirConcentrationSensor")) {
                if (firstPass)
                    createWaterAirConcentrationSensor(child);
                else
                    configureWaterAirConcentrationSensor(child);
            } else if (childName.equals("WaterAirPressureSensor")) {
                if (firstPass)
                    createWaterAirPressureSensor(child);
                else
                    configureWaterAirPressureSensor(child);
            } else if (childName.equals("WaterAirStoreInFlowRateSensor")) {
                if (firstPass)
                    createWaterAirStoreInFlowRateSensor(child);
                else
                    configureWaterAirStoreInFlowRateSensor(child);
            } else if (childName.equals("WaterAirStoreOutFlowRateSensor")) {
                if (firstPass)
                    createWaterAirStoreOutFlowRateSensor(child);
                else
                    configureWaterAirStoreOutFlowRateSensor(child);
            } else if (childName.equals("WaterAirEnvironmentInFlowRateSensor")) {
                if (firstPass)
                    createWaterAirEnvironmentInFlowRateSensor(child);
                else
                    configureWaterAirEnvironmentInFlowRateSensor(child);
            } else if (childName.equals("WaterAirEnvironmentOutFlowRateSensor")) {
                if (firstPass)
                    createWaterAirEnvironmentOutFlowRateSensor(child);
                else
                    configureWaterAirEnvironmentOutFlowRateSensor(child);
            } else if (childName.equals("NitrogenAirConcentrationSensor")) {
                if (firstPass)
                    createNitrogenAirConcentrationSensor(child);
                else
                    configureNitrogenAirConcentrationSensor(child);
            } else if (childName
                    .equals("NitrogenAirEnvironmentInFlowRateSensor")) {
                if (firstPass)
                    createNitrogenAirEnvironmentInFlowRateSensor(child);
                else
                    configureNitrogenAirEnvironmentInFlowRateSensor(child);
            } else if (childName
                    .equals("NitrogenAirEnvironmentOutFlowRateSensor")) {
                if (firstPass)
                    createNitrogenAirEnvironmentOutFlowRateSensor(child);
                else
                    configureNitrogenAirEnvironmentOutFlowRateSensor(child);
            } else if (childName.equals("NitrogenAirPressureSensor")) {
                if (firstPass)
                    createNitrogenAirPressureSensor(child);
                else
                    configureNitrogenAirPressureSensor(child);
            } else if (childName.equals("NitrogenAirStoreInFlowRateSensor")) {
                if (firstPass)
                    createNitrogenAirStoreInFlowRateSensor(child);
                else
                    configureNitrogenAirStoreInFlowRateSensor(child);
            } else if (childName.equals("NitrogenAirStoreOutFlowRateSensor")) {
                if (firstPass)
                    createNitrogenAirStoreOutFlowRateSensor(child);
                else
                    configureNitrogenAirStoreOutFlowRateSensor(child);
            }
            child = child.getNextSibling();
        }
    }

    //Food
    private void createBiomassInFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating BiomassInFlowRateSensor with moduleName: "
                    + moduleName);
            BiomassInFlowRateSensorImpl myBiomassInFlowRateSensorImpl = new BiomassInFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myBiomassInFlowRateSensorImpl, node);
            BiosimServer.registerServer(new BiomassInFlowRateSensorPOATie(
                    myBiomassInFlowRateSensorImpl),
                    myBiomassInFlowRateSensorImpl.getModuleName(),
                    myBiomassInFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureBiomassInFlowRateSensor(Node node) {
        BiomassInFlowRateSensor myBiomassInFlowRateSensor = BiomassInFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myBiomassInFlowRateSensor.setInput(BiomassConsumerHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))),
                getFlowRateIndex(node));
        mySensors.add(myBiomassInFlowRateSensor);
    }

    private void createBiomassOutFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating BiomassOutFlowRateSensor with moduleName: "
                            + moduleName);
            BiomassOutFlowRateSensorImpl myBiomassOutFlowRateSensorImpl = new BiomassOutFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myBiomassOutFlowRateSensorImpl, node);
            BiosimServer.registerServer(new BiomassOutFlowRateSensorPOATie(
                    myBiomassOutFlowRateSensorImpl),
                    myBiomassOutFlowRateSensorImpl.getModuleName(),
                    myBiomassOutFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureBiomassOutFlowRateSensor(Node node) {
        BiomassOutFlowRateSensor myBiomassOutFlowRateSensor = BiomassOutFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myBiomassOutFlowRateSensor.setInput(BiomassProducerHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))),
                getFlowRateIndex(node));
        mySensors.add(myBiomassOutFlowRateSensor);
    }

    private void createBiomassStoreLevelSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating BiomassStoreLevelSensor with moduleName: "
                    + moduleName);
            BiomassStoreLevelSensorImpl myBiomassStoreLevelSensorImpl = new BiomassStoreLevelSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myBiomassStoreLevelSensorImpl, node);
            BiosimServer.registerServer(new BiomassStoreLevelSensorPOATie(
                    myBiomassStoreLevelSensorImpl),
                    myBiomassStoreLevelSensorImpl.getModuleName(),
                    myBiomassStoreLevelSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureBiomassStoreLevelSensor(Node node) {
        BiomassStoreLevelSensor myBiomassStoreLevelSensor = BiomassStoreLevelSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myBiomassStoreLevelSensor.setInput(BiomassStoreHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))));
        mySensors.add(myBiomassStoreLevelSensor);
    }

    private void createFoodInFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating FoodInFlowRateSensor with moduleName: "
                    + moduleName);
            FoodInFlowRateSensorImpl myFoodInFlowRateSensorImpl = new FoodInFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myFoodInFlowRateSensorImpl, node);
            BiosimServer.registerServer(new FoodInFlowRateSensorPOATie(
                    myFoodInFlowRateSensorImpl), myFoodInFlowRateSensorImpl
                    .getModuleName(), myFoodInFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureFoodInFlowRateSensor(Node node) {
        FoodInFlowRateSensor myFoodInFlowRateSensor = FoodInFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myFoodInFlowRateSensor.setInput(FoodConsumerHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))),
                getFlowRateIndex(node));
        mySensors.add(myFoodInFlowRateSensor);
    }

    private void createFoodOutFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating FoodOutFlowRateSensor with moduleName: "
                    + moduleName);
            FoodOutFlowRateSensorImpl myFoodOutFlowRateSensorImpl = new FoodOutFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myFoodOutFlowRateSensorImpl, node);
            BiosimServer.registerServer(new FoodOutFlowRateSensorPOATie(
                    myFoodOutFlowRateSensorImpl), myFoodOutFlowRateSensorImpl
                    .getModuleName(), myFoodOutFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureFoodOutFlowRateSensor(Node node) {
        FoodOutFlowRateSensor myFoodOutFlowRateSensor = FoodOutFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myFoodOutFlowRateSensor.setInput(FoodProducerHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))),
                getFlowRateIndex(node));
        mySensors.add(myFoodOutFlowRateSensor);
    }

    private void createFoodStoreLevelSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating FoodStoreLevelSensor with moduleName: "
                    + moduleName);
            FoodStoreLevelSensorImpl myFoodStoreLevelSensorImpl = new FoodStoreLevelSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myFoodStoreLevelSensorImpl, node);
            BiosimServer.registerServer(new FoodStoreLevelSensorPOATie(
                    myFoodStoreLevelSensorImpl), myFoodStoreLevelSensorImpl
                    .getModuleName(), myFoodStoreLevelSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureBiomassStoreWaterContentSensor(Node node) {
        BiomassStoreWaterContentSensor myBiomassStoreWaterContentSensor = BiomassStoreWaterContentSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myBiomassStoreWaterContentSensor.setInput(BiomassStoreHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))));
        mySensors.add(myBiomassStoreWaterContentSensor);
    }

    private void createBiomassStoreWaterContentSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating BiomassStoreWaterContentSensor with moduleName: "
                            + moduleName);
            BiomassStoreWaterContentSensorImpl myBiomassStoreWaterContentSensorImpl = new BiomassStoreWaterContentSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myBiomassStoreWaterContentSensorImpl,
                    node);
            BiosimServer.registerServer(
                    new BiomassStoreWaterContentSensorPOATie(
                            myBiomassStoreWaterContentSensorImpl),
                    myBiomassStoreWaterContentSensorImpl.getModuleName(),
                    myBiomassStoreWaterContentSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureFoodStoreLevelSensor(Node node) {
        FoodStoreLevelSensor myFoodStoreLevelSensor = FoodStoreLevelSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myFoodStoreLevelSensor.setInput(FoodStoreHelper.narrow(BioInitializer
                .grabModule(myID, getInputName(node))));
        mySensors.add(myFoodStoreLevelSensor);
    }

    private void createHarvestSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating HarvestSensor with moduleName: "
                    + moduleName);
            HarvestSensorImpl myHarvestSensorImpl = new HarvestSensorImpl(myID,
                    moduleName);
            BioInitializer.setupBioModule(myHarvestSensorImpl, node);
            BiosimServer.registerServer(new HarvestSensorPOATie(
                    myHarvestSensorImpl), myHarvestSensorImpl.getModuleName(),
                    myHarvestSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureHarvestSensor(Node node) {
        try {
            int index = Integer.parseInt(node.getAttributes().getNamedItem(
                    "shelfIndex").getNodeValue());
            HarvestSensor myHarvestSensor = HarvestSensorHelper
                    .narrow(BioInitializer.grabModule(myID, BioInitializer
                            .getModuleName(node)));
            myHarvestSensor.setInput(BiomassRSHelper.narrow(BioInitializer
                    .grabModule(myID, getInputName(node))), index);
            mySensors.add(myHarvestSensor);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void createPlantDeathSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating PlantDeathSensor with moduleName: "
                    + moduleName);
            PlantDeathSensorImpl myPlantDeathSensorImpl = new PlantDeathSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myPlantDeathSensorImpl, node);
            BiosimServer.registerServer(new PlantDeathSensorPOATie(
                    myPlantDeathSensorImpl), myPlantDeathSensorImpl
                    .getModuleName(), myPlantDeathSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configurePlantDeathSensor(Node node) {
        try {
            int index = Integer.parseInt(node.getAttributes().getNamedItem(
                    "shelfIndex").getNodeValue());
            PlantDeathSensor myPlantDeathSensor = PlantDeathSensorHelper
                    .narrow(BioInitializer.grabModule(myID, BioInitializer
                            .getModuleName(node)));
            myPlantDeathSensor.setInput(BiomassRSHelper.narrow(BioInitializer
                    .grabModule(myID, getInputName(node))), index);
            mySensors.add(myPlantDeathSensor);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void crawlFoodSensors(Node node, boolean firstPass) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("BiomassInFlowRateSensor")) {
                if (firstPass)
                    createBiomassInFlowRateSensor(child);
                else
                    configureBiomassInFlowRateSensor(child);
            } else if (childName.equals("BiomassOutFlowRateSensor")) {
                if (firstPass)
                    createBiomassOutFlowRateSensor(child);
                else
                    configureBiomassOutFlowRateSensor(child);
            } else if (childName.equals("BiomassStoreLevelSensor")) {
                if (firstPass)
                    createBiomassStoreLevelSensor(child);
                else
                    configureBiomassStoreLevelSensor(child);
            } else if (childName.equals("BiomassStoreWaterContentSensor")) {
                if (firstPass)
                    createBiomassStoreWaterContentSensor(child);
                else
                    configureBiomassStoreWaterContentSensor(child);
            } else if (childName.equals("FoodInFlowRateSensor")) {
                if (firstPass)
                    createFoodInFlowRateSensor(child);
                else
                    configureFoodInFlowRateSensor(child);
            } else if (childName.equals("FoodOutFlowRateSensor")) {
                if (firstPass)
                    createFoodOutFlowRateSensor(child);
                else
                    configureFoodOutFlowRateSensor(child);
            } else if (childName.equals("FoodStoreLevelSensor")) {
                if (firstPass)
                    createFoodStoreLevelSensor(child);
                else
                    configureFoodStoreLevelSensor(child);
            } else if (childName.equals("HarvestSensor")) {
                if (firstPass)
                    createHarvestSensor(child);
                else
                    configureHarvestSensor(child);
            } else if (childName.equals("PlantDeathSensor")) {
                if (firstPass)
                    createPlantDeathSensor(child);
                else
                    configurePlantDeathSensor(child);
            }
            child = child.getNextSibling();
        }
    }

    //Framework
    private void createStoreLevelSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating StoreLevelSensor with moduleName: "
                    + moduleName);
            StoreLevelSensorImpl myStoreLevelSensorImpl = new StoreLevelSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myStoreLevelSensorImpl, node);
            BiosimServer.registerServer(new StoreLevelSensorPOATie(
                    myStoreLevelSensorImpl), myStoreLevelSensorImpl
                    .getModuleName(), myStoreLevelSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureStoreLevelSensor(Node node) {
        StoreLevelSensor myStoreLevelSensor = StoreLevelSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myStoreLevelSensor.setInput(StoreHelper.narrow(BioInitializer
                .grabModule(myID, getInputName(node))));
        mySensors.add(myStoreLevelSensor);
    }

    private void createStoreOverflowSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating StoreOverflowSensor with moduleName: "
                    + moduleName);
            StoreOverflowSensorImpl myStoreOverflowSensorImpl = new StoreOverflowSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myStoreOverflowSensorImpl, node);
            BiosimServer.registerServer(new StoreOverflowSensorPOATie(
                    myStoreOverflowSensorImpl), myStoreOverflowSensorImpl
                    .getModuleName(), myStoreOverflowSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureStoreOverflowSensor(Node node) {
        StoreOverflowSensor myStoreOverflowSensor = StoreOverflowSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myStoreOverflowSensor.setInput(StoreHelper.narrow(BioInitializer
                .grabModule(myID, getInputName(node))));
        mySensors.add(myStoreOverflowSensor);
    }

    private void crawlFrameworkSensors(Node node, boolean firstPass) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("StoreLevelSensor")) {
                if (firstPass)
                    createStoreLevelSensor(child);
                else
                    configureStoreLevelSensor(child);
            } else if (childName.equals("StoreOverflowSensor")) {
                if (firstPass)
                    createStoreOverflowSensor(child);
                else
                    configureStoreOverflowSensor(child);
            }
            child = child.getNextSibling();
        }
    }

    //Power
    private void createPowerInFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating PowerInFlowRateSensor with moduleName: "
                    + moduleName);
            PowerInFlowRateSensorImpl myPowerInFlowRateSensorImpl = new PowerInFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myPowerInFlowRateSensorImpl, node);
            BiosimServer.registerServer(new PowerInFlowRateSensorPOATie(
                    myPowerInFlowRateSensorImpl), myPowerInFlowRateSensorImpl
                    .getModuleName(), myPowerInFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configurePowerInFlowRateSensor(Node node) {
        PowerInFlowRateSensor myPowerInFlowRateSensor = PowerInFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myPowerInFlowRateSensor.setInput(PowerConsumerHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))),
                getFlowRateIndex(node));
        mySensors.add(myPowerInFlowRateSensor);
    }

    private void createPowerOutFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating PowerOutFlowRateSensor with moduleName: "
                    + moduleName);
            PowerOutFlowRateSensorImpl myPowerOutFlowRateSensorImpl = new PowerOutFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myPowerOutFlowRateSensorImpl, node);
            BiosimServer.registerServer(new PowerOutFlowRateSensorPOATie(
                    myPowerOutFlowRateSensorImpl), myPowerOutFlowRateSensorImpl
                    .getModuleName(), myPowerOutFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configurePowerOutFlowRateSensor(Node node) {
        PowerOutFlowRateSensor myPowerOutFlowRateSensor = PowerOutFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myPowerOutFlowRateSensor.setInput(PowerProducerHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))),
                getFlowRateIndex(node));
        mySensors.add(myPowerOutFlowRateSensor);
    }

    private void createPowerStoreLevelSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating PowerStoreLevelSensor with moduleName: "
                    + moduleName);
            PowerStoreLevelSensorImpl myPowerStoreLevelSensorImpl = new PowerStoreLevelSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myPowerStoreLevelSensorImpl, node);
            BiosimServer.registerServer(new PowerStoreLevelSensorPOATie(
                    myPowerStoreLevelSensorImpl), myPowerStoreLevelSensorImpl
                    .getModuleName(), myPowerStoreLevelSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configurePowerStoreLevelSensor(Node node) {
        PowerStoreLevelSensor myPowerStoreLevelSensor = PowerStoreLevelSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myPowerStoreLevelSensor.setInput(PowerStoreHelper.narrow(BioInitializer
                .grabModule(myID, getInputName(node))));
        mySensors.add(myPowerStoreLevelSensor);
    }

    private void crawlPowerSensors(Node node, boolean firstPass) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("PowerInFlowRateSensor")) {
                if (firstPass)
                    createPowerInFlowRateSensor(child);
                else
                    configurePowerInFlowRateSensor(child);
            } else if (childName.equals("PowerOutFlowRateSensor")) {
                if (firstPass)
                    createPowerOutFlowRateSensor(child);
                else
                    configurePowerOutFlowRateSensor(child);
            } else if (childName.equals("PowerStoreLevelSensor")) {
                if (firstPass)
                    createPowerStoreLevelSensor(child);
                else
                    configurePowerStoreLevelSensor(child);
            }
            child = child.getNextSibling();
        }
    }

    //Water
    private void createPotableWaterInFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating PotableWaterInFlowRateSensor with moduleName: "
                            + moduleName);
            PotableWaterInFlowRateSensorImpl myPotableWaterInFlowRateSensorImpl = new PotableWaterInFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myPotableWaterInFlowRateSensorImpl,
                    node);
            BiosimServer.registerServer(new PotableWaterInFlowRateSensorPOATie(
                    myPotableWaterInFlowRateSensorImpl),
                    myPotableWaterInFlowRateSensorImpl.getModuleName(),
                    myPotableWaterInFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configurePotableWaterInFlowRateSensor(Node node) {
        PotableWaterInFlowRateSensor myPotableWaterInFlowRateSensor = PotableWaterInFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myPotableWaterInFlowRateSensor.setInput(PotableWaterConsumerHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))),
                getFlowRateIndex(node));
        mySensors.add(myPotableWaterInFlowRateSensor);
    }

    private void createPotableWaterOutFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating PotableWaterOutFlowRateSensor with moduleName: "
                            + moduleName);
            PotableWaterOutFlowRateSensorImpl myPotableWaterOutFlowRateSensorImpl = new PotableWaterOutFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myPotableWaterOutFlowRateSensorImpl,
                    node);
            BiosimServer.registerServer(
                    new PotableWaterOutFlowRateSensorPOATie(
                            myPotableWaterOutFlowRateSensorImpl),
                    myPotableWaterOutFlowRateSensorImpl.getModuleName(),
                    myPotableWaterOutFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configurePotableWaterOutFlowRateSensor(Node node) {
        PotableWaterOutFlowRateSensor myPotableWaterOutFlowRateSensor = PotableWaterOutFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myPotableWaterOutFlowRateSensor.setInput(PotableWaterProducerHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))),
                getFlowRateIndex(node));
        mySensors.add(myPotableWaterOutFlowRateSensor);
    }

    private void createPotableWaterStoreLevelSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating PotableWaterStoreLevelSensor with moduleName: "
                            + moduleName);
            PotableWaterStoreLevelSensorImpl myPotableWaterStoreLevelSensorImpl = new PotableWaterStoreLevelSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myPotableWaterStoreLevelSensorImpl,
                    node);
            BiosimServer.registerServer(new PotableWaterStoreLevelSensorPOATie(
                    myPotableWaterStoreLevelSensorImpl),
                    myPotableWaterStoreLevelSensorImpl.getModuleName(),
                    myPotableWaterStoreLevelSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configurePotableWaterStoreLevelSensor(Node node) {
        PotableWaterStoreLevelSensor myPotableWaterStoreLevelSensor = PotableWaterStoreLevelSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myPotableWaterStoreLevelSensor.setInput(PotableWaterStoreHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))));
        mySensors.add(myPotableWaterStoreLevelSensor);
    }

    private void createGreyWaterInFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating GreyWaterInFlowRateSensor with moduleName: "
                            + moduleName);
            GreyWaterInFlowRateSensorImpl myGreyWaterInFlowRateSensorImpl = new GreyWaterInFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer
                    .setupBioModule(myGreyWaterInFlowRateSensorImpl, node);
            BiosimServer.registerServer(new GreyWaterInFlowRateSensorPOATie(
                    myGreyWaterInFlowRateSensorImpl),
                    myGreyWaterInFlowRateSensorImpl.getModuleName(),
                    myGreyWaterInFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureGreyWaterInFlowRateSensor(Node node) {
        GreyWaterInFlowRateSensor myGreyWaterInFlowRateSensor = GreyWaterInFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myGreyWaterInFlowRateSensor.setInput(GreyWaterConsumerHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))),
                getFlowRateIndex(node));
        mySensors.add(myGreyWaterInFlowRateSensor);
    }

    private void createGreyWaterOutFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating GreyWaterOutFlowRateSensor with moduleName: "
                            + moduleName);
            GreyWaterOutFlowRateSensorImpl myGreyWaterOutFlowRateSensorImpl = new GreyWaterOutFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myGreyWaterOutFlowRateSensorImpl,
                    node);
            BiosimServer.registerServer(new GreyWaterOutFlowRateSensorPOATie(
                    myGreyWaterOutFlowRateSensorImpl),
                    myGreyWaterOutFlowRateSensorImpl.getModuleName(),
                    myGreyWaterOutFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureGreyWaterOutFlowRateSensor(Node node) {
        GreyWaterOutFlowRateSensor myGreyWaterOutFlowRateSensor = GreyWaterOutFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myGreyWaterOutFlowRateSensor.setInput(GreyWaterProducerHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))),
                getFlowRateIndex(node));
        mySensors.add(myGreyWaterOutFlowRateSensor);
    }

    private void createGreyWaterStoreLevelSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating GreyWaterStoreLevelSensor with moduleName: "
                            + moduleName);
            GreyWaterStoreLevelSensorImpl myGreyWaterStoreLevelSensorImpl = new GreyWaterStoreLevelSensorImpl(
                    myID, moduleName);
            BioInitializer
                    .setupBioModule(myGreyWaterStoreLevelSensorImpl, node);
            BiosimServer.registerServer(new GreyWaterStoreLevelSensorPOATie(
                    myGreyWaterStoreLevelSensorImpl),
                    myGreyWaterStoreLevelSensorImpl.getModuleName(),
                    myGreyWaterStoreLevelSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureGreyWaterStoreLevelSensor(Node node) {
        GreyWaterStoreLevelSensor myGreyWaterStoreLevelSensor = GreyWaterStoreLevelSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myGreyWaterStoreLevelSensor.setInput(GreyWaterStoreHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))));
        mySensors.add(myGreyWaterStoreLevelSensor);
    }

    private void createDirtyWaterInFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating DirtyWaterInFlowRateSensor with moduleName: "
                            + moduleName);
            DirtyWaterInFlowRateSensorImpl myDirtyWaterInFlowRateSensorImpl = new DirtyWaterInFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myDirtyWaterInFlowRateSensorImpl,
                    node);
            BiosimServer.registerServer(new DirtyWaterInFlowRateSensorPOATie(
                    myDirtyWaterInFlowRateSensorImpl),
                    myDirtyWaterInFlowRateSensorImpl.getModuleName(),
                    myDirtyWaterInFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureDirtyWaterInFlowRateSensor(Node node) {
        DirtyWaterInFlowRateSensor myDirtyWaterInFlowRateSensor = DirtyWaterInFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myDirtyWaterInFlowRateSensor.setInput(DirtyWaterConsumerHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))),
                getFlowRateIndex(node));
        mySensors.add(myDirtyWaterInFlowRateSensor);
    }

    private void createDirtyWaterOutFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating DirtyWaterOutFlowRateSensor with moduleName: "
                            + moduleName);
            DirtyWaterOutFlowRateSensorImpl myDirtyWaterOutFlowRateSensorImpl = new DirtyWaterOutFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myDirtyWaterOutFlowRateSensorImpl,
                    node);
            BiosimServer.registerServer(new DirtyWaterOutFlowRateSensorPOATie(
                    myDirtyWaterOutFlowRateSensorImpl),
                    myDirtyWaterOutFlowRateSensorImpl.getModuleName(),
                    myDirtyWaterOutFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureDirtyWaterOutFlowRateSensor(Node node) {
        DirtyWaterOutFlowRateSensor myDirtyWaterOutFlowRateSensor = DirtyWaterOutFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myDirtyWaterOutFlowRateSensor.setInput(DirtyWaterProducerHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))),
                getFlowRateIndex(node));
        mySensors.add(myDirtyWaterOutFlowRateSensor);
    }

    private void createDirtyWaterStoreLevelSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating DirtyWaterStoreLevelSensor with moduleName: "
                            + moduleName);
            DirtyWaterStoreLevelSensorImpl myDirtyWaterStoreLevelSensorImpl = new DirtyWaterStoreLevelSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myDirtyWaterStoreLevelSensorImpl,
                    node);
            BiosimServer.registerServer(new DirtyWaterStoreLevelSensorPOATie(
                    myDirtyWaterStoreLevelSensorImpl),
                    myDirtyWaterStoreLevelSensorImpl.getModuleName(),
                    myDirtyWaterStoreLevelSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureDirtyWaterStoreLevelSensor(Node node) {
        DirtyWaterStoreLevelSensor myDirtyWaterStoreLevelSensor = DirtyWaterStoreLevelSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myDirtyWaterStoreLevelSensor.setInput(DirtyWaterStoreHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))));
        mySensors.add(myDirtyWaterStoreLevelSensor);
    }

    private void createWaterInFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating WaterInFlowRateSensor with moduleName: "
                    + moduleName);
            WaterInFlowRateSensorImpl myWaterInFlowRateSensorImpl = new WaterInFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myWaterInFlowRateSensorImpl, node);
            BiosimServer.registerServer(new WaterInFlowRateSensorPOATie(
                    myWaterInFlowRateSensorImpl), myWaterInFlowRateSensorImpl
                    .getModuleName(), myWaterInFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureWaterInFlowRateSensor(Node node) {
        WaterInFlowRateSensor myWaterInFlowRateSensor = WaterInFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myWaterInFlowRateSensor.setInput(WaterConsumerHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))),
                getFlowRateIndex(node));
        mySensors.add(myWaterInFlowRateSensor);
    }

    private void createWaterOutFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating WaterOutFlowRateSensor with moduleName: "
                    + moduleName);
            WaterOutFlowRateSensorImpl myWaterOutFlowRateSensorImpl = new WaterOutFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myWaterOutFlowRateSensorImpl, node);
            BiosimServer.registerServer(new WaterOutFlowRateSensorPOATie(
                    myWaterOutFlowRateSensorImpl), myWaterOutFlowRateSensorImpl
                    .getModuleName(), myWaterOutFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureWaterOutFlowRateSensor(Node node) {
        WaterOutFlowRateSensor myWaterOutFlowRateSensor = WaterOutFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myWaterOutFlowRateSensor.setInput(WaterProducerHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))),
                getFlowRateIndex(node));
        mySensors.add(myWaterOutFlowRateSensor);
    }

    private void createWaterStoreLevelSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating WaterStoreLevelSensor with moduleName: "
                    + moduleName);
            WaterStoreLevelSensorImpl myWaterStoreLevelSensorImpl = new WaterStoreLevelSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myWaterStoreLevelSensorImpl, node);
            BiosimServer.registerServer(new WaterStoreLevelSensorPOATie(
                    myWaterStoreLevelSensorImpl), myWaterStoreLevelSensorImpl
                    .getModuleName(), myWaterStoreLevelSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureWaterStoreLevelSensor(Node node) {
        WaterStoreLevelSensor myWaterStoreLevelSensor = WaterStoreLevelSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myWaterStoreLevelSensor.setInput(WaterStoreHelper.narrow(BioInitializer
                .grabModule(myID, getInputName(node))));
        mySensors.add(myWaterStoreLevelSensor);
    }

    private void crawlWaterSensors(Node node, boolean firstPass) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("PotableWaterInFlowRateSensor")) {
                if (firstPass)
                    createPotableWaterInFlowRateSensor(child);
                else
                    configurePotableWaterInFlowRateSensor(child);
            } else if (childName.equals("PotableWaterOutFlowRateSensor")) {
                if (firstPass)
                    createPotableWaterOutFlowRateSensor(child);
                else
                    configurePotableWaterOutFlowRateSensor(child);
            } else if (childName.equals("PotableWaterStoreLevelSensor")) {
                if (firstPass)
                    createPotableWaterStoreLevelSensor(child);
                else
                    configurePotableWaterStoreLevelSensor(child);
            } else if (childName.equals("GreyWaterInFlowRateSensor")) {
                if (firstPass)
                    createGreyWaterInFlowRateSensor(child);
                else
                    configureGreyWaterInFlowRateSensor(child);
            } else if (childName.equals("GreyWaterOutFlowRateSensor")) {
                if (firstPass)
                    createGreyWaterOutFlowRateSensor(child);
                else
                    configureGreyWaterOutFlowRateSensor(child);
            } else if (childName.equals("GreyWaterStoreLevelSensor")) {
                if (firstPass)
                    createGreyWaterStoreLevelSensor(child);
                else
                    configureGreyWaterStoreLevelSensor(child);
            } else if (childName.equals("DirtyWaterInFlowRateSensor")) {
                if (firstPass)
                    createDirtyWaterInFlowRateSensor(child);
                else
                    configureDirtyWaterInFlowRateSensor(child);
            } else if (childName.equals("DirtyWaterOutFlowRateSensor")) {
                if (firstPass)
                    createDirtyWaterOutFlowRateSensor(child);
                else
                    configureDirtyWaterOutFlowRateSensor(child);
            } else if (childName.equals("DirtyWaterStoreLevelSensor")) {
                if (firstPass)
                    createDirtyWaterStoreLevelSensor(child);
                else
                    configureDirtyWaterStoreLevelSensor(child);
            } else if (childName.equals("WaterInFlowRateSensor")) {
                if (firstPass)
                    createWaterInFlowRateSensor(child);
                else
                    configureWaterInFlowRateSensor(child);
            } else if (childName.equals("WaterOutFlowRateSensor")) {
                if (firstPass)
                    createWaterOutFlowRateSensor(child);
                else
                    configureWaterOutFlowRateSensor(child);
            } else if (childName.equals("WaterStoreLevelSensor")) {
                if (firstPass)
                    createWaterStoreLevelSensor(child);
                else
                    configureWaterStoreLevelSensor(child);
            }
            child = child.getNextSibling();
        }
    }

    //Waste
    private void createDryWasteInFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating DryWasteInFlowRateSensor with moduleName: "
                            + moduleName);
            DryWasteInFlowRateSensorImpl myDryWasteInFlowRateSensorImpl = new DryWasteInFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myDryWasteInFlowRateSensorImpl, node);
            BiosimServer.registerServer(new DryWasteInFlowRateSensorPOATie(
                    myDryWasteInFlowRateSensorImpl),
                    myDryWasteInFlowRateSensorImpl.getModuleName(),
                    myDryWasteInFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureDryWasteInFlowRateSensor(Node node) {
        DryWasteInFlowRateSensor myDryWasteInFlowRateSensor = DryWasteInFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myDryWasteInFlowRateSensor.setInput(DryWasteConsumerHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))),
                getFlowRateIndex(node));
        mySensors.add(myDryWasteInFlowRateSensor);
    }

    private void createDryWasteOutFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating DryWasteOutFlowRateSensor with moduleName: "
                            + moduleName);
            DryWasteOutFlowRateSensorImpl myDryWasteOutFlowRateSensorImpl = new DryWasteOutFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer
                    .setupBioModule(myDryWasteOutFlowRateSensorImpl, node);
            BiosimServer.registerServer(new DryWasteOutFlowRateSensorPOATie(
                    myDryWasteOutFlowRateSensorImpl),
                    myDryWasteOutFlowRateSensorImpl.getModuleName(),
                    myDryWasteOutFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureDryWasteOutFlowRateSensor(Node node) {
        DryWasteOutFlowRateSensor myDryWasteOutFlowRateSensor = DryWasteOutFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myDryWasteOutFlowRateSensor.setInput(DryWasteProducerHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))),
                getFlowRateIndex(node));
        mySensors.add(myDryWasteOutFlowRateSensor);
    }

    private void createDryWasteStoreLevelSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating DryWasteStoreLevelSensor with moduleName: "
                            + moduleName);
            DryWasteStoreLevelSensorImpl myDryWasteStoreLevelSensorImpl = new DryWasteStoreLevelSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myDryWasteStoreLevelSensorImpl, node);
            BiosimServer.registerServer(new DryWasteStoreLevelSensorPOATie(
                    myDryWasteStoreLevelSensorImpl),
                    myDryWasteStoreLevelSensorImpl.getModuleName(),
                    myDryWasteStoreLevelSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureDryWasteStoreLevelSensor(Node node) {
        DryWasteStoreLevelSensor myDryWasteStoreLevelSensor = DryWasteStoreLevelSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myDryWasteStoreLevelSensor.setInput(DryWasteStoreHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))));
        mySensors.add(myDryWasteStoreLevelSensor);
    }

    private void crawlWasteSensors(Node node, boolean firstPass) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("DryWasteInFlowRateSensor")) {
                if (firstPass)
                    createDryWasteInFlowRateSensor(child);
                else
                    configureDryWasteInFlowRateSensor(child);
            } else if (childName.equals("DryWasteOutFlowRateSensor")) {
                if (firstPass)
                    createDryWasteOutFlowRateSensor(child);
                else
                    configureDryWasteOutFlowRateSensor(child);
            } else if (childName.equals("DryWasteStoreLevelSensor")) {
                if (firstPass)
                    createDryWasteStoreLevelSensor(child);
                else
                    configureDryWasteStoreLevelSensor(child);
            }
            child = child.getNextSibling();
        }
    }

    /**
     * @return Returns the mySensors.
     */
    public List getSensors() {
        return mySensors;
    }
}