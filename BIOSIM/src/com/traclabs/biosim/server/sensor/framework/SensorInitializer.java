package com.traclabs.biosim.server.sensor.framework;

import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;

import com.traclabs.biosim.idl.framework.MalfunctionIntensity;
import com.traclabs.biosim.idl.framework.MalfunctionLength;
import com.traclabs.biosim.idl.framework.StochasticIntensity;
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
import com.traclabs.biosim.server.framework.BioModuleImpl;
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
import com.traclabs.biosim.server.simulation.framework.SimulationInitializer;
import com.traclabs.biosim.server.util.OrbUtils;

/**
 * Reads BioSim configuration from XML file.
 * 
 * @author Scott Bell
 */
public class SensorInitializer {
    private int myID = 0;

    private List mySensors;

    private Logger myLogger;
    
    private SimulationInitializer mySimulationInitializer;

    /** Default constructor. */
    public SensorInitializer(int pID) {
        myID = pID;
        mySensors = new Vector();
        myLogger = Logger.getLogger(this.getClass());
    }

    public static boolean isCreatedLocally(Node node) {
        return node.getAttributes().getNamedItem("createLocally")
                .getNodeValue().equals("true");
    }

    public static String getModuleName(Node node) {
        return node.getAttributes().getNamedItem("name").getNodeValue();
    }

    public void printRemoteWarningMessage(String pName) {
        myLogger.warn("\nInstance of the module named " + pName
                + " should be created remotely (if not already done)");
    }

    private org.omg.CORBA.Object grabModule(String moduleName) {
        org.omg.CORBA.Object moduleToReturn = null;
        while (moduleToReturn == null) {
            try {
                moduleToReturn = OrbUtils.getNamingContext(myID).resolve_str(
                        moduleName);
            } catch (org.omg.CORBA.UserException e) {
                myLogger.error("BioHolder: Couldn't find module " + moduleName
                        + ", polling again...");
                OrbUtils.sleepAwhile();
            } catch (Exception e) {
                myLogger
                        .error("BioHolder: Had problems contacting nameserver with module "
                                + moduleName + ", polling again...");
                OrbUtils.resetInit();
                OrbUtils.sleepAwhile();
            }
        }
        return moduleToReturn;
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
    
    private static boolean getEnableBreakDown(Node pNode) {
        return pNode.getAttributes().getNamedItem("isLoggingEnabled")
                .getNodeValue().equals("true");
    }
    
    private static StochasticIntensity getStochasticIntensity(Node pNode) {
        String intensityString = pNode.getAttributes().getNamedItem(
                "setStochasticIntensity").getNodeValue();
        if (intensityString.equals("HIGH_STOCH"))
            return StochasticIntensity.HIGH_STOCH;
        else if (intensityString.equals("MEDIUM_STOCH"))
            return StochasticIntensity.MEDIUM_STOCH;
        else if (intensityString.equals("LOW_STOCH"))
            return StochasticIntensity.LOW_STOCH;
        else
            return StochasticIntensity.NONE_STOCH;
    }
    
    private static int getFlowRateIndex(Node pNode) {
        return Integer.parseInt(pNode.getAttributes().getNamedItem("index")
                .getNodeValue());
    }
    
    private static int getShelfIndex(Node pNode) {
        return Integer.parseInt(pNode.getAttributes()
                .getNamedItem("shelfIndex").getNodeValue());
    }
    
    private static MalfunctionLength getMalfunctionLength(Node pNode) {
        String lengthString = pNode.getAttributes().getNamedItem("length")
                .getNodeValue();
        if (lengthString.equals("TEMPORARY_MALF"))
            return MalfunctionLength.TEMPORARY_MALF;
        else
            return MalfunctionLength.PERMANENT_MALF;
    }

    private static int getMalfunctionTick(Node pNode) {
        int occursAtTick = 0;
        try {
            occursAtTick = Integer.parseInt(pNode.getAttributes().getNamedItem(
                    "occursAtTick").getNodeValue());
        } catch (NumberFormatException e) {

            e.printStackTrace();
        }
        return occursAtTick;
    }

    private static MalfunctionIntensity getMalfunctionIntensity(Node pNode) {
        String intensityString = pNode.getAttributes()
                .getNamedItem("intensity").getNodeValue();
        if (intensityString.equals("SEVERE_MALF"))
            return MalfunctionIntensity.SEVERE_MALF;
        else if (intensityString.equals("MEDIUM_MALF"))
            return MalfunctionIntensity.MEDIUM_MALF;
        else
            return MalfunctionIntensity.LOW_MALF;
    }
    
    private static void setupBioModule(BioModuleImpl pModule, Node node) {
        pModule.setEnableBreakdown(getEnableBreakDown(node));
        pModule.setStochasticIntensity(getStochasticIntensity(node));
        Node child = node.getFirstChild();
        while (child != null) {
            if (child.getNodeName().equals("malfunction")) {
                pModule.scheduleMalfunction(getMalfunctionIntensity(child),
                        getMalfunctionLength(child), getMalfunctionTick(child));
            }
            child = child.getNextSibling();
        }
    }

    //Air
    private void createCO2InFlowRateSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger.debug("Creating CO2InFlowRateSensor with moduleName: "
                    + moduleName);
            CO2InFlowRateSensorImpl myCO2InFlowRateSensorImpl = new CO2InFlowRateSensorImpl(
                    myID, moduleName);
            setupBioModule(myCO2InFlowRateSensorImpl, node);
            BiosimServer.registerServer(new CO2InFlowRateSensorPOATie(
                    myCO2InFlowRateSensorImpl), myCO2InFlowRateSensorImpl
                    .getModuleName(), myCO2InFlowRateSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureCO2InFlowRateSensor(Node node) {
        CO2InFlowRateSensor myCO2InFlowRateSensor = CO2InFlowRateSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myCO2InFlowRateSensor
                .setInput(CO2ConsumerHelper
                        .narrow(grabModule(getInputName(node))),
                        getFlowRateIndex(node));
        mySensors.add(myCO2InFlowRateSensor);
    }

    private void createCO2OutFlowRateSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger.debug("Creating CO2OutFlowRateSensor with moduleName: "
                    + moduleName);
            CO2OutFlowRateSensorImpl myCO2OutFlowRateSensorImpl = new CO2OutFlowRateSensorImpl(
                    myID, moduleName);
            setupBioModule(myCO2OutFlowRateSensorImpl, node);
            BiosimServer.registerServer(new CO2OutFlowRateSensorPOATie(
                    myCO2OutFlowRateSensorImpl), myCO2OutFlowRateSensorImpl
                    .getModuleName(), myCO2OutFlowRateSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureCO2OutFlowRateSensor(Node node) {
        CO2OutFlowRateSensor myCO2OutFlowRateSensor = CO2OutFlowRateSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myCO2OutFlowRateSensor
                .setInput(CO2ProducerHelper
                        .narrow(grabModule(getInputName(node))),
                        getFlowRateIndex(node));
        mySensors.add(myCO2OutFlowRateSensor);
    }

    private void createCO2StoreLevelSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger.debug("Creating CO2StoreLevelSensor with moduleName: "
                    + moduleName);
            CO2StoreLevelSensorImpl myCO2StoreLevelSensorImpl = new CO2StoreLevelSensorImpl(
                    myID, moduleName);
            setupBioModule(myCO2StoreLevelSensorImpl, node);
            BiosimServer.registerServer(new CO2StoreLevelSensorPOATie(
                    myCO2StoreLevelSensorImpl), myCO2StoreLevelSensorImpl
                    .getModuleName(), myCO2StoreLevelSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureCO2StoreLevelSensor(Node node) {
        CO2StoreLevelSensor myCO2StoreLevelSensor = CO2StoreLevelSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myCO2StoreLevelSensor.setInput(CO2StoreHelper
                .narrow(grabModule(getInputName(node))));
        mySensors.add(myCO2StoreLevelSensor);
    }

    private void createO2InFlowRateSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger.debug("Creating O2InFlowRateSensor with moduleName: "
                    + moduleName);
            O2InFlowRateSensorImpl myO2InFlowRateSensorImpl = new O2InFlowRateSensorImpl(
                    myID, moduleName);
            setupBioModule(myO2InFlowRateSensorImpl, node);
            BiosimServer.registerServer(new O2InFlowRateSensorPOATie(
                    myO2InFlowRateSensorImpl), myO2InFlowRateSensorImpl
                    .getModuleName(), myO2InFlowRateSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureO2InFlowRateSensor(Node node) {
        O2InFlowRateSensor myO2InFlowRateSensor = O2InFlowRateSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myO2InFlowRateSensor
                .setInput(O2ConsumerHelper
                        .narrow(grabModule(getInputName(node))),
                        getFlowRateIndex(node));
        mySensors.add(myO2InFlowRateSensor);
    }

    private void createO2OutFlowRateSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger.debug("Creating O2OutFlowRateSensor with moduleName: "
                    + moduleName);
            O2OutFlowRateSensorImpl myO2OutFlowRateSensorImpl = new O2OutFlowRateSensorImpl(
                    myID, moduleName);
            setupBioModule(myO2OutFlowRateSensorImpl, node);
            BiosimServer.registerServer(new O2OutFlowRateSensorPOATie(
                    myO2OutFlowRateSensorImpl), myO2OutFlowRateSensorImpl
                    .getModuleName(), myO2OutFlowRateSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureO2OutFlowRateSensor(Node node) {
        O2OutFlowRateSensor myO2OutFlowRateSensor = O2OutFlowRateSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myO2OutFlowRateSensor
                .setInput(O2ProducerHelper
                        .narrow(grabModule(getInputName(node))),
                        getFlowRateIndex(node));
        mySensors.add(myO2OutFlowRateSensor);
    }

    private void createO2StoreLevelSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger.debug("Creating O2StoreLevelSensor with moduleName: "
                    + moduleName);
            O2StoreLevelSensorImpl myO2StoreLevelSensorImpl = new O2StoreLevelSensorImpl(
                    myID, moduleName);
            setupBioModule(myO2StoreLevelSensorImpl, node);
            BiosimServer.registerServer(new O2StoreLevelSensorPOATie(
                    myO2StoreLevelSensorImpl), myO2StoreLevelSensorImpl
                    .getModuleName(), myO2StoreLevelSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureO2StoreLevelSensor(Node node) {
        O2StoreLevelSensor myO2StoreLevelSensor = O2StoreLevelSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myO2StoreLevelSensor.setInput(O2StoreHelper
                .narrow(grabModule(getInputName(node))));
        mySensors.add(myO2StoreLevelSensor);
    }

    private void createH2InFlowRateSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger.debug("Creating H2InFlowRateSensor with moduleName: "
                    + moduleName);
            H2InFlowRateSensorImpl myH2InFlowRateSensorImpl = new H2InFlowRateSensorImpl(
                    myID, moduleName);
            setupBioModule(myH2InFlowRateSensorImpl, node);
            BiosimServer.registerServer(new H2InFlowRateSensorPOATie(
                    myH2InFlowRateSensorImpl), myH2InFlowRateSensorImpl
                    .getModuleName(), myH2InFlowRateSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureH2InFlowRateSensor(Node node) {
        H2InFlowRateSensor myH2InFlowRateSensor = H2InFlowRateSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myH2InFlowRateSensor
                .setInput(H2ConsumerHelper
                        .narrow(grabModule(getInputName(node))),
                        getFlowRateIndex(node));
        mySensors.add(myH2InFlowRateSensor);
    }

    private void createH2OutFlowRateSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger.debug("Creating H2OutFlowRateSensor with moduleName: "
                    + moduleName);
            H2OutFlowRateSensorImpl myH2OutFlowRateSensorImpl = new H2OutFlowRateSensorImpl(
                    myID, moduleName);
            setupBioModule(myH2OutFlowRateSensorImpl, node);
            BiosimServer.registerServer(new H2OutFlowRateSensorPOATie(
                    myH2OutFlowRateSensorImpl), myH2OutFlowRateSensorImpl
                    .getModuleName(), myH2OutFlowRateSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureH2OutFlowRateSensor(Node node) {
        H2OutFlowRateSensor myH2OutFlowRateSensor = H2OutFlowRateSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myH2OutFlowRateSensor
                .setInput(H2ProducerHelper
                        .narrow(grabModule(getInputName(node))),
                        getFlowRateIndex(node));
        mySensors.add(myH2OutFlowRateSensor);
    }

    private void createH2StoreLevelSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger.debug("Creating H2StoreLevelSensor with moduleName: "
                    + moduleName);
            H2StoreLevelSensorImpl myH2StoreLevelSensorImpl = new H2StoreLevelSensorImpl(
                    myID, moduleName);
            setupBioModule(myH2StoreLevelSensorImpl, node);
            BiosimServer.registerServer(new H2StoreLevelSensorPOATie(
                    myH2StoreLevelSensorImpl), myH2StoreLevelSensorImpl
                    .getModuleName(), myH2StoreLevelSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureH2StoreLevelSensor(Node node) {
        H2StoreLevelSensor myH2StoreLevelSensor = H2StoreLevelSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myH2StoreLevelSensor.setInput(H2StoreHelper
                .narrow(grabModule(getInputName(node))));
        mySensors.add(myH2StoreLevelSensor);
    }

    private void createNitrogenInFlowRateSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger
                    .debug("Creating NitrogenInFlowRateSensor with moduleName: "
                            + moduleName);
            NitrogenInFlowRateSensorImpl myNitrogenInFlowRateSensorImpl = new NitrogenInFlowRateSensorImpl(
                    myID, moduleName);
            setupBioModule(myNitrogenInFlowRateSensorImpl, node);
            BiosimServer.registerServer(new NitrogenInFlowRateSensorPOATie(
                    myNitrogenInFlowRateSensorImpl),
                    myNitrogenInFlowRateSensorImpl.getModuleName(),
                    myNitrogenInFlowRateSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureNitrogenInFlowRateSensor(Node node) {
        NitrogenInFlowRateSensor myNitrogenInFlowRateSensor = NitrogenInFlowRateSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myNitrogenInFlowRateSensor
                .setInput(NitrogenConsumerHelper
                        .narrow(grabModule(getInputName(node))),
                        getFlowRateIndex(node));
        mySensors.add(myNitrogenInFlowRateSensor);
    }

    private void createNitrogenOutFlowRateSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger
                    .debug("Creating NitrogenOutFlowRateSensor with moduleName: "
                            + moduleName);
            NitrogenOutFlowRateSensorImpl myNitrogenOutFlowRateSensorImpl = new NitrogenOutFlowRateSensorImpl(
                    myID, moduleName);
            setupBioModule(myNitrogenOutFlowRateSensorImpl, node);
            BiosimServer.registerServer(new NitrogenOutFlowRateSensorPOATie(
                    myNitrogenOutFlowRateSensorImpl),
                    myNitrogenOutFlowRateSensorImpl.getModuleName(),
                    myNitrogenOutFlowRateSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureNitrogenOutFlowRateSensor(Node node) {
        NitrogenOutFlowRateSensor myNitrogenOutFlowRateSensor = NitrogenOutFlowRateSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myNitrogenOutFlowRateSensor
                .setInput(NitrogenProducerHelper
                        .narrow(grabModule(getInputName(node))),
                        getFlowRateIndex(node));
        mySensors.add(myNitrogenOutFlowRateSensor);
    }

    private void createNitrogenStoreLevelSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger
                    .debug("Creating NitrogenStoreLevelSensor with moduleName: "
                            + moduleName);
            NitrogenStoreLevelSensorImpl myNitrogenStoreLevelSensorImpl = new NitrogenStoreLevelSensorImpl(
                    myID, moduleName);
            setupBioModule(myNitrogenStoreLevelSensorImpl, node);
            BiosimServer.registerServer(new NitrogenStoreLevelSensorPOATie(
                    myNitrogenStoreLevelSensorImpl),
                    myNitrogenStoreLevelSensorImpl.getModuleName(),
                    myNitrogenStoreLevelSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureNitrogenStoreLevelSensor(Node node) {
        NitrogenStoreLevelSensor myNitrogenStoreLevelSensor = NitrogenStoreLevelSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myNitrogenStoreLevelSensor.setInput(NitrogenStoreHelper
                .narrow(grabModule(getInputName(node))));
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
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger.debug("Creating CrewGroupDeathSensor with moduleName: "
                    + moduleName);
            CrewGroupDeathSensorImpl myCrewGroupDeathSensorImpl = new CrewGroupDeathSensorImpl(
                    myID, moduleName);
            setupBioModule(myCrewGroupDeathSensorImpl, node);
            BiosimServer.registerServer(new CrewGroupDeathSensorPOATie(
                    myCrewGroupDeathSensorImpl), myCrewGroupDeathSensorImpl
                    .getModuleName(), myCrewGroupDeathSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureCrewGroupDeathSensor(Node node) {
        CrewGroupDeathSensor myCrewGroupDeathSensor = CrewGroupDeathSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myCrewGroupDeathSensor.setInput(CrewGroupHelper
                .narrow(grabModule(getInputName(node))));
        mySensors.add(myCrewGroupDeathSensor);
    }

    private void createCrewGroupAnyDeadSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger.debug("Creating CrewGroupAnyDeadSensor with moduleName: "
                    + moduleName);
            CrewGroupAnyDeadSensorImpl myCrewGroupAnyDeadSensorImpl = new CrewGroupAnyDeadSensorImpl(
                    myID, moduleName);
            setupBioModule(myCrewGroupAnyDeadSensorImpl, node);
            BiosimServer.registerServer(new CrewGroupAnyDeadSensorPOATie(
                    myCrewGroupAnyDeadSensorImpl), myCrewGroupAnyDeadSensorImpl
                    .getModuleName(), myCrewGroupAnyDeadSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureCrewGroupAnyDeadSensor(Node node) {
        CrewGroupAnyDeadSensor myCrewGroupAnyDeadSensor = CrewGroupAnyDeadSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myCrewGroupAnyDeadSensor.setInput(CrewGroupHelper
                .narrow(grabModule(getInputName(node))));
        mySensors.add(myCrewGroupAnyDeadSensor);
    }

    private void createCrewGroupProductivitySensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger
                    .debug("Creating CrewGroupProductivitySensor with moduleName: "
                            + moduleName);
            CrewGroupProductivitySensorImpl myCrewGroupProductivitySensorImpl = new CrewGroupProductivitySensorImpl(
                    myID, moduleName);
            setupBioModule(myCrewGroupProductivitySensorImpl, node);
            BiosimServer.registerServer(new CrewGroupProductivitySensorPOATie(
                    myCrewGroupProductivitySensorImpl),
                    myCrewGroupProductivitySensorImpl.getModuleName(),
                    myCrewGroupProductivitySensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureCrewGroupProductivitySensor(Node node) {
        CrewGroupProductivitySensor myCrewGroupProductivitySensor = CrewGroupProductivitySensorHelper
                .narrow(grabModule(getModuleName(node)));
        myCrewGroupProductivitySensor.setInput(CrewGroupHelper
                .narrow(grabModule(getInputName(node))));
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
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger.debug("Creating AirInFlowRateSensor with moduleName: "
                    + moduleName);
            AirInFlowRateSensorImpl myAirInFlowRateSensorImpl = new AirInFlowRateSensorImpl(
                    myID, moduleName);
            setupBioModule(myAirInFlowRateSensorImpl, node);
            BiosimServer.registerServer(new AirInFlowRateSensorPOATie(
                    myAirInFlowRateSensorImpl), myAirInFlowRateSensorImpl
                    .getModuleName(), myAirInFlowRateSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureAirInFlowRateSensor(Node node) {
        AirInFlowRateSensor myAirInFlowRateSensor = AirInFlowRateSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myAirInFlowRateSensor
                .setInput(AirConsumerHelper
                        .narrow(grabModule(getInputName(node))),
                        getFlowRateIndex(node));
        mySensors.add(myAirInFlowRateSensor);
    }

    private void createAirOutFlowRateSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger.debug("Creating AirOutFlowRateSensor with moduleName: "
                    + moduleName);
            AirOutFlowRateSensorImpl myAirOutFlowRateSensorImpl = new AirOutFlowRateSensorImpl(
                    myID, moduleName);
            setupBioModule(myAirOutFlowRateSensorImpl, node);
            BiosimServer.registerServer(new AirOutFlowRateSensorPOATie(
                    myAirOutFlowRateSensorImpl), myAirOutFlowRateSensorImpl
                    .getModuleName(), myAirOutFlowRateSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureAirOutFlowRateSensor(Node node) {
        AirOutFlowRateSensor myAirOutFlowRateSensor = AirOutFlowRateSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myAirOutFlowRateSensor
                .setInput(AirProducerHelper
                        .narrow(grabModule(getInputName(node))),
                        getFlowRateIndex(node));
        mySensors.add(myAirOutFlowRateSensor);
    }

    private void createCO2AirConcentrationSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger
                    .debug("Creating CO2AirConcentrationSensor with moduleName: "
                            + moduleName);
            CO2AirConcentrationSensorImpl myCO2AirConcentrationSensorImpl = new CO2AirConcentrationSensorImpl(
                    myID, moduleName);
            setupBioModule(myCO2AirConcentrationSensorImpl, node);
            BiosimServer.registerServer(new CO2AirConcentrationSensorPOATie(
                    myCO2AirConcentrationSensorImpl),
                    myCO2AirConcentrationSensorImpl.getModuleName(),
                    myCO2AirConcentrationSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureCO2AirConcentrationSensor(Node node) {
        CO2AirConcentrationSensor myCO2AirConcentrationSensor = CO2AirConcentrationSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myCO2AirConcentrationSensor.setInput(SimEnvironmentHelper
                .narrow(grabModule(getInputName(node))));
        mySensors.add(myCO2AirConcentrationSensor);
    }

    private void createCO2AirPressureSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger.debug("Creating CO2AirPressureSensor with moduleName: "
                    + moduleName);
            CO2AirPressureSensorImpl myCO2AirPressureSensorImpl = new CO2AirPressureSensorImpl(
                    myID, moduleName);
            setupBioModule(myCO2AirPressureSensorImpl, node);
            BiosimServer.registerServer(new CO2AirPressureSensorPOATie(
                    myCO2AirPressureSensorImpl), myCO2AirPressureSensorImpl
                    .getModuleName(), myCO2AirPressureSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureCO2AirPressureSensor(Node node) {
        CO2AirPressureSensor myCO2AirPressureSensor = CO2AirPressureSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myCO2AirPressureSensor.setInput(SimEnvironmentHelper
                .narrow(grabModule(getInputName(node))));
        mySensors.add(myCO2AirPressureSensor);
    }

    private void createCO2AirEnvironmentInFlowRateSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger
                    .debug("Creating CO2AirEnvironmentInFlowRateSensor with moduleName: "
                            + moduleName);
            CO2AirEnvironmentInFlowRateSensorImpl myCO2AirEnvironmentInFlowRateSensorImpl = new CO2AirEnvironmentInFlowRateSensorImpl(
                    myID, moduleName);
            setupBioModule(myCO2AirEnvironmentInFlowRateSensorImpl, node);
            BiosimServer.registerServer(
                    new CO2AirEnvironmentInFlowRateSensorPOATie(
                            myCO2AirEnvironmentInFlowRateSensorImpl),
                    myCO2AirEnvironmentInFlowRateSensorImpl.getModuleName(),
                    myCO2AirEnvironmentInFlowRateSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureCO2AirEnvironmentInFlowRateSensor(Node node) {
        CO2AirEnvironmentInFlowRateSensor myCO2AirEnvironmentInFlowRateSensor = CO2AirEnvironmentInFlowRateSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myCO2AirEnvironmentInFlowRateSensor
                .setInput(CO2AirConsumerHelper
                        .narrow(grabModule(getInputName(node))),
                        getFlowRateIndex(node));
        mySensors.add(myCO2AirEnvironmentInFlowRateSensor);
    }

    private void createCO2AirEnvironmentOutFlowRateSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger
                    .debug("Creating CO2AirEnvironmentOutFlowRateSensor with moduleName: "
                            + moduleName);
            CO2AirEnvironmentOutFlowRateSensorImpl myCO2AirEnvironmentOutFlowRateSensorImpl = new CO2AirEnvironmentOutFlowRateSensorImpl(
                    myID, moduleName);
            setupBioModule(myCO2AirEnvironmentOutFlowRateSensorImpl, node);
            BiosimServer.registerServer(
                    new CO2AirEnvironmentOutFlowRateSensorPOATie(
                            myCO2AirEnvironmentOutFlowRateSensorImpl),
                    myCO2AirEnvironmentOutFlowRateSensorImpl.getModuleName(),
                    myCO2AirEnvironmentOutFlowRateSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureCO2AirEnvironmentOutFlowRateSensor(Node node) {
        CO2AirEnvironmentOutFlowRateSensor myCO2AirEnvironmentOutFlowRateSensor = CO2AirEnvironmentOutFlowRateSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myCO2AirEnvironmentOutFlowRateSensor
                .setInput(CO2AirProducerHelper
                        .narrow(grabModule(getInputName(node))),
                        getFlowRateIndex(node));
        mySensors.add(myCO2AirEnvironmentOutFlowRateSensor);
    }

    private void createCO2AirStoreInFlowRateSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger
                    .debug("Creating CO2AirStoreInFlowRateSensor with moduleName: "
                            + moduleName);
            CO2AirStoreInFlowRateSensorImpl myCO2AirStoreInFlowRateSensorImpl = new CO2AirStoreInFlowRateSensorImpl(
                    myID, moduleName);
            setupBioModule(myCO2AirStoreInFlowRateSensorImpl, node);
            BiosimServer.registerServer(new CO2AirStoreInFlowRateSensorPOATie(
                    myCO2AirStoreInFlowRateSensorImpl),
                    myCO2AirStoreInFlowRateSensorImpl.getModuleName(),
                    myCO2AirStoreInFlowRateSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureCO2AirStoreInFlowRateSensor(Node node) {
        CO2AirStoreInFlowRateSensor myCO2AirStoreInFlowRateSensor = CO2AirStoreInFlowRateSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myCO2AirStoreInFlowRateSensor
                .setInput(CO2AirConsumerHelper
                        .narrow(grabModule(getInputName(node))),
                        getFlowRateIndex(node));
        mySensors.add(myCO2AirStoreInFlowRateSensor);
    }

    private void createCO2AirStoreOutFlowRateSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger
                    .debug("Creating CO2AirStoreOutFlowRateSensor with moduleName: "
                            + moduleName);
            CO2AirStoreOutFlowRateSensorImpl myCO2AirStoreOutFlowRateSensorImpl = new CO2AirStoreOutFlowRateSensorImpl(
                    myID, moduleName);
            setupBioModule(myCO2AirStoreOutFlowRateSensorImpl, node);
            BiosimServer.registerServer(new CO2AirStoreOutFlowRateSensorPOATie(
                    myCO2AirStoreOutFlowRateSensorImpl),
                    myCO2AirStoreOutFlowRateSensorImpl.getModuleName(),
                    myCO2AirStoreOutFlowRateSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureCO2AirStoreOutFlowRateSensor(Node node) {
        CO2AirStoreOutFlowRateSensor myCO2AirStoreOutFlowRateSensor = CO2AirStoreOutFlowRateSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myCO2AirStoreOutFlowRateSensor
                .setInput(CO2AirProducerHelper
                        .narrow(grabModule(getInputName(node))),
                        getFlowRateIndex(node));
        mySensors.add(myCO2AirStoreOutFlowRateSensor);
    }

    private void createO2AirConcentrationSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger
                    .debug("Creating O2AirConcentrationSensor with moduleName: "
                            + moduleName);
            O2AirConcentrationSensorImpl myO2AirConcentrationSensorImpl = new O2AirConcentrationSensorImpl(
                    myID, moduleName);
            setupBioModule(myO2AirConcentrationSensorImpl, node);
            BiosimServer.registerServer(new O2AirConcentrationSensorPOATie(
                    myO2AirConcentrationSensorImpl),
                    myO2AirConcentrationSensorImpl.getModuleName(),
                    myO2AirConcentrationSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureO2AirConcentrationSensor(Node node) {
        O2AirConcentrationSensor myO2AirConcentrationSensor = O2AirConcentrationSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myO2AirConcentrationSensor.setInput(SimEnvironmentHelper
                .narrow(grabModule(getInputName(node))));
        mySensors.add(myO2AirConcentrationSensor);
    }

    private void createO2AirPressureSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger.debug("Creating O2AirPressureSensor with moduleName: "
                    + moduleName);
            O2AirPressureSensorImpl myO2AirPressureSensorImpl = new O2AirPressureSensorImpl(
                    myID, moduleName);
            setupBioModule(myO2AirPressureSensorImpl, node);
            BiosimServer.registerServer(new O2AirPressureSensorPOATie(
                    myO2AirPressureSensorImpl), myO2AirPressureSensorImpl
                    .getModuleName(), myO2AirPressureSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureO2AirPressureSensor(Node node) {
        O2AirPressureSensor myO2AirPressureSensor = O2AirPressureSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myO2AirPressureSensor.setInput(SimEnvironmentHelper
                .narrow(grabModule(getInputName(node))));
        mySensors.add(myO2AirPressureSensor);
    }

    private void createO2AirEnvironmentInFlowRateSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger
                    .debug("Creating O2AirEnvironmentInFlowRateSensor with moduleName: "
                            + moduleName);
            O2AirEnvironmentInFlowRateSensorImpl myO2AirEnvironmentInFlowRateSensorImpl = new O2AirEnvironmentInFlowRateSensorImpl(
                    myID, moduleName);
            setupBioModule(myO2AirEnvironmentInFlowRateSensorImpl, node);
            BiosimServer.registerServer(
                    new O2AirEnvironmentInFlowRateSensorPOATie(
                            myO2AirEnvironmentInFlowRateSensorImpl),
                    myO2AirEnvironmentInFlowRateSensorImpl.getModuleName(),
                    myO2AirEnvironmentInFlowRateSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureO2AirEnvironmentInFlowRateSensor(Node node) {
        O2AirEnvironmentInFlowRateSensor myO2AirEnvironmentInFlowRateSensor = O2AirEnvironmentInFlowRateSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myO2AirEnvironmentInFlowRateSensor
                .setInput(O2AirConsumerHelper
                        .narrow(grabModule(getInputName(node))),
                        getFlowRateIndex(node));
        mySensors.add(myO2AirEnvironmentInFlowRateSensor);
    }

    private void createO2AirEnvironmentOutFlowRateSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger
                    .debug("Creating O2AirEnvironmentOutFlowRateSensor with moduleName: "
                            + moduleName);
            O2AirEnvironmentOutFlowRateSensorImpl myO2AirEnvironmentOutFlowRateSensorImpl = new O2AirEnvironmentOutFlowRateSensorImpl(
                    myID, moduleName);
            setupBioModule(myO2AirEnvironmentOutFlowRateSensorImpl, node);
            BiosimServer.registerServer(
                    new O2AirEnvironmentOutFlowRateSensorPOATie(
                            myO2AirEnvironmentOutFlowRateSensorImpl),
                    myO2AirEnvironmentOutFlowRateSensorImpl.getModuleName(),
                    myO2AirEnvironmentOutFlowRateSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureO2AirEnvironmentOutFlowRateSensor(Node node) {
        O2AirEnvironmentOutFlowRateSensor myO2AirEnvironmentOutFlowRateSensor = O2AirEnvironmentOutFlowRateSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myO2AirEnvironmentOutFlowRateSensor
                .setInput(O2AirProducerHelper
                        .narrow(grabModule(getInputName(node))),
                        getFlowRateIndex(node));
        mySensors.add(myO2AirEnvironmentOutFlowRateSensor);
    }

    private void createO2AirStoreInFlowRateSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger
                    .debug("Creating O2AirStoreInFlowRateSensor with moduleName: "
                            + moduleName);
            O2AirStoreInFlowRateSensorImpl myO2AirStoreInFlowRateSensorImpl = new O2AirStoreInFlowRateSensorImpl(
                    myID, moduleName);
            setupBioModule(myO2AirStoreInFlowRateSensorImpl, node);
            BiosimServer.registerServer(new O2AirStoreInFlowRateSensorPOATie(
                    myO2AirStoreInFlowRateSensorImpl),
                    myO2AirStoreInFlowRateSensorImpl.getModuleName(),
                    myO2AirStoreInFlowRateSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureO2AirStoreInFlowRateSensor(Node node) {
        O2AirStoreInFlowRateSensor myO2AirStoreInFlowRateSensor = O2AirStoreInFlowRateSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myO2AirStoreInFlowRateSensor
                .setInput(O2AirConsumerHelper
                        .narrow(grabModule(getInputName(node))),
                        getFlowRateIndex(node));
        mySensors.add(myO2AirStoreInFlowRateSensor);
    }

    private void createO2AirStoreOutFlowRateSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger
                    .debug("Creating O2AirStoreOutFlowRateSensor with moduleName: "
                            + moduleName);
            O2AirStoreOutFlowRateSensorImpl myO2AirStoreOutFlowRateSensorImpl = new O2AirStoreOutFlowRateSensorImpl(
                    myID, moduleName);
            setupBioModule(myO2AirStoreOutFlowRateSensorImpl, node);
            BiosimServer.registerServer(new O2AirStoreOutFlowRateSensorPOATie(
                    myO2AirStoreOutFlowRateSensorImpl),
                    myO2AirStoreOutFlowRateSensorImpl.getModuleName(),
                    myO2AirStoreOutFlowRateSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureO2AirStoreOutFlowRateSensor(Node node) {
        O2AirStoreOutFlowRateSensor myO2AirStoreOutFlowRateSensor = O2AirStoreOutFlowRateSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myO2AirStoreOutFlowRateSensor
                .setInput(O2AirProducerHelper
                        .narrow(grabModule(getInputName(node))),
                        getFlowRateIndex(node));
        mySensors.add(myO2AirStoreOutFlowRateSensor);
    }

    private void createOtherAirConcentrationSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger
                    .debug("Creating OtherAirConcentrationSensor with moduleName: "
                            + moduleName);
            OtherAirConcentrationSensorImpl myOtherAirConcentrationSensorImpl = new OtherAirConcentrationSensorImpl(
                    myID, moduleName);
            setupBioModule(myOtherAirConcentrationSensorImpl, node);
            BiosimServer.registerServer(new OtherAirConcentrationSensorPOATie(
                    myOtherAirConcentrationSensorImpl),
                    myOtherAirConcentrationSensorImpl.getModuleName(),
                    myOtherAirConcentrationSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureOtherAirConcentrationSensor(Node node) {
        OtherAirConcentrationSensor myOtherAirConcentrationSensor = OtherAirConcentrationSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myOtherAirConcentrationSensor.setInput(SimEnvironmentHelper
                .narrow(grabModule(getInputName(node))));
        mySensors.add(myOtherAirConcentrationSensor);
    }

    private void createOtherAirPressureSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger.debug("Creating OtherAirPressureSensor with moduleName: "
                    + moduleName);
            OtherAirPressureSensorImpl myOtherAirPressureSensorImpl = new OtherAirPressureSensorImpl(
                    myID, moduleName);
            setupBioModule(myOtherAirPressureSensorImpl, node);
            BiosimServer.registerServer(new OtherAirPressureSensorPOATie(
                    myOtherAirPressureSensorImpl), myOtherAirPressureSensorImpl
                    .getModuleName(), myOtherAirPressureSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureOtherAirPressureSensor(Node node) {
        OtherAirPressureSensor myOtherAirPressureSensor = OtherAirPressureSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myOtherAirPressureSensor.setInput(SimEnvironmentHelper
                .narrow(grabModule(getInputName(node))));
        mySensors.add(myOtherAirPressureSensor);
    }

    private void createWaterAirConcentrationSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger
                    .debug("Creating WaterAirConcentrationSensor with moduleName: "
                            + moduleName);
            WaterAirConcentrationSensorImpl myWaterAirConcentrationSensorImpl = new WaterAirConcentrationSensorImpl(
                    myID, moduleName);
            setupBioModule(myWaterAirConcentrationSensorImpl, node);
            BiosimServer.registerServer(new WaterAirConcentrationSensorPOATie(
                    myWaterAirConcentrationSensorImpl),
                    myWaterAirConcentrationSensorImpl.getModuleName(),
                    myWaterAirConcentrationSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureWaterAirConcentrationSensor(Node node) {
        WaterAirConcentrationSensor myWaterAirConcentrationSensor = WaterAirConcentrationSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myWaterAirConcentrationSensor.setInput(SimEnvironmentHelper
                .narrow(grabModule(getInputName(node))));
        mySensors.add(myWaterAirConcentrationSensor);
    }

    private void createWaterAirPressureSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger.debug("Creating WaterAirPressureSensor with moduleName: "
                    + moduleName);
            WaterAirPressureSensorImpl myWaterAirPressureSensorImpl = new WaterAirPressureSensorImpl(
                    myID, moduleName);
            setupBioModule(myWaterAirPressureSensorImpl, node);
            BiosimServer.registerServer(new WaterAirPressureSensorPOATie(
                    myWaterAirPressureSensorImpl), myWaterAirPressureSensorImpl
                    .getModuleName(), myWaterAirPressureSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureWaterAirPressureSensor(Node node) {
        WaterAirPressureSensor myWaterAirPressureSensor = WaterAirPressureSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myWaterAirPressureSensor.setInput(SimEnvironmentHelper
                .narrow(grabModule(getInputName(node))));
        mySensors.add(myWaterAirPressureSensor);
    }

    private void createWaterAirEnvironmentInFlowRateSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger
                    .debug("Creating WaterAirEnvironmentInFlowRateSensor with moduleName: "
                            + moduleName);
            WaterAirEnvironmentInFlowRateSensorImpl myWaterAirEnvironmentInFlowRateSensorImpl = new WaterAirEnvironmentInFlowRateSensorImpl(
                    myID, moduleName);
            setupBioModule(myWaterAirEnvironmentInFlowRateSensorImpl, node);
            BiosimServer.registerServer(
                    new WaterAirEnvironmentInFlowRateSensorPOATie(
                            myWaterAirEnvironmentInFlowRateSensorImpl),
                    myWaterAirEnvironmentInFlowRateSensorImpl.getModuleName(),
                    myWaterAirEnvironmentInFlowRateSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureWaterAirEnvironmentInFlowRateSensor(Node node) {
        WaterAirEnvironmentInFlowRateSensor myWaterAirEnvironmentInFlowRateSensor = WaterAirEnvironmentInFlowRateSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myWaterAirEnvironmentInFlowRateSensor
                .setInput(WaterAirConsumerHelper
                        .narrow(grabModule(getInputName(node))),
                        getFlowRateIndex(node));
        mySensors.add(myWaterAirEnvironmentInFlowRateSensor);
    }

    private void createWaterAirEnvironmentOutFlowRateSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger
                    .debug("Creating WaterAirEnvironmentOutFlowRateSensor with moduleName: "
                            + moduleName);
            WaterAirEnvironmentOutFlowRateSensorImpl myWaterAirEnvironmentOutFlowRateSensorImpl = new WaterAirEnvironmentOutFlowRateSensorImpl(
                    myID, moduleName);
            setupBioModule(myWaterAirEnvironmentOutFlowRateSensorImpl, node);
            BiosimServer.registerServer(
                    new WaterAirEnvironmentOutFlowRateSensorPOATie(
                            myWaterAirEnvironmentOutFlowRateSensorImpl),
                    myWaterAirEnvironmentOutFlowRateSensorImpl.getModuleName(),
                    myWaterAirEnvironmentOutFlowRateSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureWaterAirEnvironmentOutFlowRateSensor(Node node) {
        WaterAirEnvironmentOutFlowRateSensor myWaterAirEnvironmentOutFlowRateSensor = WaterAirEnvironmentOutFlowRateSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myWaterAirEnvironmentOutFlowRateSensor
                .setInput(WaterAirProducerHelper
                        .narrow(grabModule(getInputName(node))),
                        getFlowRateIndex(node));
        mySensors.add(myWaterAirEnvironmentOutFlowRateSensor);
    }

    private void createWaterAirStoreInFlowRateSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger
                    .debug("Creating WaterAirStoreInFlowRateSensor with moduleName: "
                            + moduleName);
            WaterAirStoreInFlowRateSensorImpl myWaterAirStoreInFlowRateSensorImpl = new WaterAirStoreInFlowRateSensorImpl(
                    myID, moduleName);
            setupBioModule(myWaterAirStoreInFlowRateSensorImpl, node);
            BiosimServer.registerServer(
                    new WaterAirStoreInFlowRateSensorPOATie(
                            myWaterAirStoreInFlowRateSensorImpl),
                    myWaterAirStoreInFlowRateSensorImpl.getModuleName(),
                    myWaterAirStoreInFlowRateSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureWaterAirStoreInFlowRateSensor(Node node) {
        WaterAirStoreInFlowRateSensor myWaterAirStoreInFlowRateSensor = WaterAirStoreInFlowRateSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myWaterAirStoreInFlowRateSensor
                .setInput(WaterAirConsumerHelper
                        .narrow(grabModule(getInputName(node))),
                        getFlowRateIndex(node));
        mySensors.add(myWaterAirStoreInFlowRateSensor);
    }

    private void createWaterAirStoreOutFlowRateSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger
                    .debug("Creating WaterAirStoreOutFlowRateSensor with moduleName: "
                            + moduleName);
            WaterAirStoreOutFlowRateSensorImpl myWaterAirStoreOutFlowRateSensorImpl = new WaterAirStoreOutFlowRateSensorImpl(
                    myID, moduleName);
            setupBioModule(myWaterAirStoreOutFlowRateSensorImpl, node);
            BiosimServer.registerServer(
                    new WaterAirStoreOutFlowRateSensorPOATie(
                            myWaterAirStoreOutFlowRateSensorImpl),
                    myWaterAirStoreOutFlowRateSensorImpl.getModuleName(),
                    myWaterAirStoreOutFlowRateSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureWaterAirStoreOutFlowRateSensor(Node node) {
        WaterAirStoreOutFlowRateSensor myWaterAirStoreOutFlowRateSensor = WaterAirStoreOutFlowRateSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myWaterAirStoreOutFlowRateSensor
                .setInput(WaterAirProducerHelper
                        .narrow(grabModule(getInputName(node))),
                        getFlowRateIndex(node));
        mySensors.add(myWaterAirStoreOutFlowRateSensor);
    }

    private void createNitrogenAirConcentrationSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger
                    .debug("Creating NitrogenAirConcentrationSensor with moduleName: "
                            + moduleName);
            NitrogenAirConcentrationSensorImpl myNitrogenAirConcentrationSensorImpl = new NitrogenAirConcentrationSensorImpl(
                    myID, moduleName);
            setupBioModule(myNitrogenAirConcentrationSensorImpl, node);
            BiosimServer.registerServer(
                    new NitrogenAirConcentrationSensorPOATie(
                            myNitrogenAirConcentrationSensorImpl),
                    myNitrogenAirConcentrationSensorImpl.getModuleName(),
                    myNitrogenAirConcentrationSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureNitrogenAirConcentrationSensor(Node node) {
        NitrogenAirConcentrationSensor myNitrogenAirConcentrationSensor = NitrogenAirConcentrationSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myNitrogenAirConcentrationSensor.setInput(SimEnvironmentHelper
                .narrow(grabModule(getInputName(node))));
        mySensors.add(myNitrogenAirConcentrationSensor);
    }

    private void createNitrogenAirPressureSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger
                    .debug("Creating NitrogenAirPressureSensor with moduleName: "
                            + moduleName);
            NitrogenAirPressureSensorImpl myNitrogenAirPressureSensorImpl = new NitrogenAirPressureSensorImpl(
                    myID, moduleName);
            setupBioModule(myNitrogenAirPressureSensorImpl, node);
            BiosimServer.registerServer(new NitrogenAirPressureSensorPOATie(
                    myNitrogenAirPressureSensorImpl),
                    myNitrogenAirPressureSensorImpl.getModuleName(),
                    myNitrogenAirPressureSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureNitrogenAirPressureSensor(Node node) {
        NitrogenAirPressureSensor myNitrogenAirPressureSensor = NitrogenAirPressureSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myNitrogenAirPressureSensor.setInput(SimEnvironmentHelper
                .narrow(grabModule(getInputName(node))));
        mySensors.add(myNitrogenAirPressureSensor);
    }

    private void createNitrogenAirEnvironmentInFlowRateSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger
                    .debug("Creating NitrogenAirEnvironmentInFlowRateSensor with moduleName: "
                            + moduleName);
            NitrogenAirEnvironmentInFlowRateSensorImpl myNitrogenAirEnvironmentInFlowRateSensorImpl = new NitrogenAirEnvironmentInFlowRateSensorImpl(
                    myID, moduleName);
            setupBioModule(myNitrogenAirEnvironmentInFlowRateSensorImpl, node);
            BiosimServer.registerServer(
                    new NitrogenAirEnvironmentInFlowRateSensorPOATie(
                            myNitrogenAirEnvironmentInFlowRateSensorImpl),
                    myNitrogenAirEnvironmentInFlowRateSensorImpl
                            .getModuleName(),
                    myNitrogenAirEnvironmentInFlowRateSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureNitrogenAirEnvironmentInFlowRateSensor(Node node) {
        NitrogenAirEnvironmentInFlowRateSensor myNitrogenAirEnvironmentInFlowRateSensor = NitrogenAirEnvironmentInFlowRateSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myNitrogenAirEnvironmentInFlowRateSensor.setInput(
                NitrogenAirConsumerHelper
                        .narrow(grabModule(getInputName(node))),
                getFlowRateIndex(node));
        mySensors.add(myNitrogenAirEnvironmentInFlowRateSensor);
    }

    private void createNitrogenAirEnvironmentOutFlowRateSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger
                    .debug("Creating NitrogenAirEnvironmentOutFlowRateSensor with moduleName: "
                            + moduleName);
            NitrogenAirEnvironmentOutFlowRateSensorImpl myNitrogenAirEnvironmentOutFlowRateSensorImpl = new NitrogenAirEnvironmentOutFlowRateSensorImpl(
                    myID, moduleName);
            setupBioModule(myNitrogenAirEnvironmentOutFlowRateSensorImpl, node);
            BiosimServer.registerServer(
                    new NitrogenAirEnvironmentOutFlowRateSensorPOATie(
                            myNitrogenAirEnvironmentOutFlowRateSensorImpl),
                    myNitrogenAirEnvironmentOutFlowRateSensorImpl
                            .getModuleName(),
                    myNitrogenAirEnvironmentOutFlowRateSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureNitrogenAirEnvironmentOutFlowRateSensor(Node node) {
        NitrogenAirEnvironmentOutFlowRateSensor myNitrogenAirEnvironmentOutFlowRateSensor = NitrogenAirEnvironmentOutFlowRateSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myNitrogenAirEnvironmentOutFlowRateSensor.setInput(
                NitrogenAirProducerHelper
                        .narrow(grabModule(getInputName(node))),
                getFlowRateIndex(node));
        mySensors.add(myNitrogenAirEnvironmentOutFlowRateSensor);
    }

    private void createNitrogenAirStoreInFlowRateSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger
                    .debug("Creating NitrogenAirStoreInFlowRateSensor with moduleName: "
                            + moduleName);
            NitrogenAirStoreInFlowRateSensorImpl myNitrogenAirStoreInFlowRateSensorImpl = new NitrogenAirStoreInFlowRateSensorImpl(
                    myID, moduleName);
            setupBioModule(myNitrogenAirStoreInFlowRateSensorImpl, node);
            BiosimServer.registerServer(
                    new NitrogenAirStoreInFlowRateSensorPOATie(
                            myNitrogenAirStoreInFlowRateSensorImpl),
                    myNitrogenAirStoreInFlowRateSensorImpl.getModuleName(),
                    myNitrogenAirStoreInFlowRateSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureNitrogenAirStoreInFlowRateSensor(Node node) {
        NitrogenAirStoreInFlowRateSensor myNitrogenAirStoreInFlowRateSensor = NitrogenAirStoreInFlowRateSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myNitrogenAirStoreInFlowRateSensor
                .setInput(NitrogenAirConsumerHelper
                        .narrow(grabModule(getInputName(node))),
                        getFlowRateIndex(node));
        mySensors.add(myNitrogenAirStoreInFlowRateSensor);
    }

    private void createNitrogenAirStoreOutFlowRateSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger
                    .debug("Creating NitrogenAirStoreOutFlowRateSensor with moduleName: "
                            + moduleName);
            NitrogenAirStoreOutFlowRateSensorImpl myNitrogenAirStoreOutFlowRateSensorImpl = new NitrogenAirStoreOutFlowRateSensorImpl(
                    myID, moduleName);
            setupBioModule(myNitrogenAirStoreOutFlowRateSensorImpl, node);
            BiosimServer.registerServer(
                    new NitrogenAirStoreOutFlowRateSensorPOATie(
                            myNitrogenAirStoreOutFlowRateSensorImpl),
                    myNitrogenAirStoreOutFlowRateSensorImpl.getModuleName(),
                    myNitrogenAirStoreOutFlowRateSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureNitrogenAirStoreOutFlowRateSensor(Node node) {
        NitrogenAirStoreOutFlowRateSensor myNitrogenAirStoreOutFlowRateSensor = NitrogenAirStoreOutFlowRateSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myNitrogenAirStoreOutFlowRateSensor
                .setInput(NitrogenAirProducerHelper
                        .narrow(grabModule(getInputName(node))),
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
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger.debug("Creating BiomassInFlowRateSensor with moduleName: "
                    + moduleName);
            BiomassInFlowRateSensorImpl myBiomassInFlowRateSensorImpl = new BiomassInFlowRateSensorImpl(
                    myID, moduleName);
            setupBioModule(myBiomassInFlowRateSensorImpl, node);
            BiosimServer.registerServer(new BiomassInFlowRateSensorPOATie(
                    myBiomassInFlowRateSensorImpl),
                    myBiomassInFlowRateSensorImpl.getModuleName(),
                    myBiomassInFlowRateSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureBiomassInFlowRateSensor(Node node) {
        BiomassInFlowRateSensor myBiomassInFlowRateSensor = BiomassInFlowRateSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myBiomassInFlowRateSensor
                .setInput(BiomassConsumerHelper
                        .narrow(grabModule(getInputName(node))),
                        getFlowRateIndex(node));
        mySensors.add(myBiomassInFlowRateSensor);
    }

    private void createBiomassOutFlowRateSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger
                    .debug("Creating BiomassOutFlowRateSensor with moduleName: "
                            + moduleName);
            BiomassOutFlowRateSensorImpl myBiomassOutFlowRateSensorImpl = new BiomassOutFlowRateSensorImpl(
                    myID, moduleName);
            setupBioModule(myBiomassOutFlowRateSensorImpl, node);
            BiosimServer.registerServer(new BiomassOutFlowRateSensorPOATie(
                    myBiomassOutFlowRateSensorImpl),
                    myBiomassOutFlowRateSensorImpl.getModuleName(),
                    myBiomassOutFlowRateSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureBiomassOutFlowRateSensor(Node node) {
        BiomassOutFlowRateSensor myBiomassOutFlowRateSensor = BiomassOutFlowRateSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myBiomassOutFlowRateSensor
                .setInput(BiomassProducerHelper
                        .narrow(grabModule(getInputName(node))),
                        getFlowRateIndex(node));
        mySensors.add(myBiomassOutFlowRateSensor);
    }

    private void createBiomassStoreLevelSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger.debug("Creating BiomassStoreLevelSensor with moduleName: "
                    + moduleName);
            BiomassStoreLevelSensorImpl myBiomassStoreLevelSensorImpl = new BiomassStoreLevelSensorImpl(
                    myID, moduleName);
            setupBioModule(myBiomassStoreLevelSensorImpl, node);
            BiosimServer.registerServer(new BiomassStoreLevelSensorPOATie(
                    myBiomassStoreLevelSensorImpl),
                    myBiomassStoreLevelSensorImpl.getModuleName(),
                    myBiomassStoreLevelSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureBiomassStoreLevelSensor(Node node) {
        BiomassStoreLevelSensor myBiomassStoreLevelSensor = BiomassStoreLevelSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myBiomassStoreLevelSensor.setInput(BiomassStoreHelper
                .narrow(grabModule(getInputName(node))));
        mySensors.add(myBiomassStoreLevelSensor);
    }

    private void createFoodInFlowRateSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger.debug("Creating FoodInFlowRateSensor with moduleName: "
                    + moduleName);
            FoodInFlowRateSensorImpl myFoodInFlowRateSensorImpl = new FoodInFlowRateSensorImpl(
                    myID, moduleName);
            setupBioModule(myFoodInFlowRateSensorImpl, node);
            BiosimServer.registerServer(new FoodInFlowRateSensorPOATie(
                    myFoodInFlowRateSensorImpl), myFoodInFlowRateSensorImpl
                    .getModuleName(), myFoodInFlowRateSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureFoodInFlowRateSensor(Node node) {
        FoodInFlowRateSensor myFoodInFlowRateSensor = FoodInFlowRateSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myFoodInFlowRateSensor
                .setInput(FoodConsumerHelper
                        .narrow(grabModule(getInputName(node))),
                        getFlowRateIndex(node));
        mySensors.add(myFoodInFlowRateSensor);
    }

    private void createFoodOutFlowRateSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger.debug("Creating FoodOutFlowRateSensor with moduleName: "
                    + moduleName);
            FoodOutFlowRateSensorImpl myFoodOutFlowRateSensorImpl = new FoodOutFlowRateSensorImpl(
                    myID, moduleName);
            setupBioModule(myFoodOutFlowRateSensorImpl, node);
            BiosimServer.registerServer(new FoodOutFlowRateSensorPOATie(
                    myFoodOutFlowRateSensorImpl), myFoodOutFlowRateSensorImpl
                    .getModuleName(), myFoodOutFlowRateSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureFoodOutFlowRateSensor(Node node) {
        FoodOutFlowRateSensor myFoodOutFlowRateSensor = FoodOutFlowRateSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myFoodOutFlowRateSensor
                .setInput(FoodProducerHelper
                        .narrow(grabModule(getInputName(node))),
                        getFlowRateIndex(node));
        mySensors.add(myFoodOutFlowRateSensor);
    }

    private void createFoodStoreLevelSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger.debug("Creating FoodStoreLevelSensor with moduleName: "
                    + moduleName);
            FoodStoreLevelSensorImpl myFoodStoreLevelSensorImpl = new FoodStoreLevelSensorImpl(
                    myID, moduleName);
            setupBioModule(myFoodStoreLevelSensorImpl, node);
            BiosimServer.registerServer(new FoodStoreLevelSensorPOATie(
                    myFoodStoreLevelSensorImpl), myFoodStoreLevelSensorImpl
                    .getModuleName(), myFoodStoreLevelSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureBiomassStoreWaterContentSensor(Node node) {
        BiomassStoreWaterContentSensor myBiomassStoreWaterContentSensor = BiomassStoreWaterContentSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myBiomassStoreWaterContentSensor.setInput(BiomassStoreHelper
                .narrow(grabModule(getInputName(node))));
        mySensors.add(myBiomassStoreWaterContentSensor);
    }

    private void createBiomassStoreWaterContentSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger
                    .debug("Creating BiomassStoreWaterContentSensor with moduleName: "
                            + moduleName);
            BiomassStoreWaterContentSensorImpl myBiomassStoreWaterContentSensorImpl = new BiomassStoreWaterContentSensorImpl(
                    myID, moduleName);
            setupBioModule(myBiomassStoreWaterContentSensorImpl, node);
            BiosimServer.registerServer(
                    new BiomassStoreWaterContentSensorPOATie(
                            myBiomassStoreWaterContentSensorImpl),
                    myBiomassStoreWaterContentSensorImpl.getModuleName(),
                    myBiomassStoreWaterContentSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureFoodStoreLevelSensor(Node node) {
        FoodStoreLevelSensor myFoodStoreLevelSensor = FoodStoreLevelSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myFoodStoreLevelSensor.setInput(FoodStoreHelper
                .narrow(grabModule(getInputName(node))));
        mySensors.add(myFoodStoreLevelSensor);
    }

    private void createHarvestSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger.debug("Creating HarvestSensor with moduleName: "
                    + moduleName);
            HarvestSensorImpl myHarvestSensorImpl = new HarvestSensorImpl(myID,
                    moduleName);
            setupBioModule(myHarvestSensorImpl, node);
            BiosimServer.registerServer(new HarvestSensorPOATie(
                    myHarvestSensorImpl), myHarvestSensorImpl.getModuleName(),
                    myHarvestSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureHarvestSensor(Node node) {
        try {
            int index = Integer.parseInt(node.getAttributes().getNamedItem(
                    "shelfIndex").getNodeValue());
            HarvestSensor myHarvestSensor = HarvestSensorHelper
                    .narrow(grabModule(getModuleName(node)));
            myHarvestSensor.setInput(BiomassRSHelper
                    .narrow(grabModule(getInputName(node))), index);
            mySensors.add(myHarvestSensor);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void createPlantDeathSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger.debug("Creating PlantDeathSensor with moduleName: "
                    + moduleName);
            PlantDeathSensorImpl myPlantDeathSensorImpl = new PlantDeathSensorImpl(
                    myID, moduleName);
            setupBioModule(myPlantDeathSensorImpl, node);
            BiosimServer.registerServer(new PlantDeathSensorPOATie(
                    myPlantDeathSensorImpl), myPlantDeathSensorImpl
                    .getModuleName(), myPlantDeathSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configurePlantDeathSensor(Node node) {
        try {
            int index = Integer.parseInt(node.getAttributes().getNamedItem(
                    "shelfIndex").getNodeValue());
            PlantDeathSensor myPlantDeathSensor = PlantDeathSensorHelper
                    .narrow(grabModule(getModuleName(node)));
            myPlantDeathSensor.setInput(BiomassRSHelper
                    .narrow(grabModule(getInputName(node))), index);
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
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger.debug("Creating StoreLevelSensor with moduleName: "
                    + moduleName);
            StoreLevelSensorImpl myStoreLevelSensorImpl = new StoreLevelSensorImpl(
                    myID, moduleName);
            setupBioModule(myStoreLevelSensorImpl, node);
            BiosimServer.registerServer(new StoreLevelSensorPOATie(
                    myStoreLevelSensorImpl), myStoreLevelSensorImpl
                    .getModuleName(), myStoreLevelSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureStoreLevelSensor(Node node) {
        StoreLevelSensor myStoreLevelSensor = StoreLevelSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myStoreLevelSensor.setInput(StoreHelper
                .narrow(grabModule(getInputName(node))));
        mySensors.add(myStoreLevelSensor);
    }

    private void createStoreOverflowSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger.debug("Creating StoreOverflowSensor with moduleName: "
                    + moduleName);
            StoreOverflowSensorImpl myStoreOverflowSensorImpl = new StoreOverflowSensorImpl(
                    myID, moduleName);
            setupBioModule(myStoreOverflowSensorImpl, node);
            BiosimServer.registerServer(new StoreOverflowSensorPOATie(
                    myStoreOverflowSensorImpl), myStoreOverflowSensorImpl
                    .getModuleName(), myStoreOverflowSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureStoreOverflowSensor(Node node) {
        StoreOverflowSensor myStoreOverflowSensor = StoreOverflowSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myStoreOverflowSensor.setInput(StoreHelper
                .narrow(grabModule(getInputName(node))));
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
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger.debug("Creating PowerInFlowRateSensor with moduleName: "
                    + moduleName);
            PowerInFlowRateSensorImpl myPowerInFlowRateSensorImpl = new PowerInFlowRateSensorImpl(
                    myID, moduleName);
            setupBioModule(myPowerInFlowRateSensorImpl, node);
            BiosimServer.registerServer(new PowerInFlowRateSensorPOATie(
                    myPowerInFlowRateSensorImpl), myPowerInFlowRateSensorImpl
                    .getModuleName(), myPowerInFlowRateSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configurePowerInFlowRateSensor(Node node) {
        PowerInFlowRateSensor myPowerInFlowRateSensor = PowerInFlowRateSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myPowerInFlowRateSensor
                .setInput(PowerConsumerHelper
                        .narrow(grabModule(getInputName(node))),
                        getFlowRateIndex(node));
        mySensors.add(myPowerInFlowRateSensor);
    }

    private void createPowerOutFlowRateSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger.debug("Creating PowerOutFlowRateSensor with moduleName: "
                    + moduleName);
            PowerOutFlowRateSensorImpl myPowerOutFlowRateSensorImpl = new PowerOutFlowRateSensorImpl(
                    myID, moduleName);
            setupBioModule(myPowerOutFlowRateSensorImpl, node);
            BiosimServer.registerServer(new PowerOutFlowRateSensorPOATie(
                    myPowerOutFlowRateSensorImpl), myPowerOutFlowRateSensorImpl
                    .getModuleName(), myPowerOutFlowRateSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configurePowerOutFlowRateSensor(Node node) {
        PowerOutFlowRateSensor myPowerOutFlowRateSensor = PowerOutFlowRateSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myPowerOutFlowRateSensor
                .setInput(PowerProducerHelper
                        .narrow(grabModule(getInputName(node))),
                        getFlowRateIndex(node));
        mySensors.add(myPowerOutFlowRateSensor);
    }

    private void createPowerStoreLevelSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger.debug("Creating PowerStoreLevelSensor with moduleName: "
                    + moduleName);
            PowerStoreLevelSensorImpl myPowerStoreLevelSensorImpl = new PowerStoreLevelSensorImpl(
                    myID, moduleName);
            setupBioModule(myPowerStoreLevelSensorImpl, node);
            BiosimServer.registerServer(new PowerStoreLevelSensorPOATie(
                    myPowerStoreLevelSensorImpl), myPowerStoreLevelSensorImpl
                    .getModuleName(), myPowerStoreLevelSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configurePowerStoreLevelSensor(Node node) {
        PowerStoreLevelSensor myPowerStoreLevelSensor = PowerStoreLevelSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myPowerStoreLevelSensor.setInput(PowerStoreHelper
                .narrow(grabModule(getInputName(node))));
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
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger
                    .debug("Creating PotableWaterInFlowRateSensor with moduleName: "
                            + moduleName);
            PotableWaterInFlowRateSensorImpl myPotableWaterInFlowRateSensorImpl = new PotableWaterInFlowRateSensorImpl(
                    myID, moduleName);
            setupBioModule(myPotableWaterInFlowRateSensorImpl, node);
            BiosimServer.registerServer(new PotableWaterInFlowRateSensorPOATie(
                    myPotableWaterInFlowRateSensorImpl),
                    myPotableWaterInFlowRateSensorImpl.getModuleName(),
                    myPotableWaterInFlowRateSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configurePotableWaterInFlowRateSensor(Node node) {
        PotableWaterInFlowRateSensor myPotableWaterInFlowRateSensor = PotableWaterInFlowRateSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myPotableWaterInFlowRateSensor
                .setInput(PotableWaterConsumerHelper
                        .narrow(grabModule(getInputName(node))),
                        getFlowRateIndex(node));
        mySensors.add(myPotableWaterInFlowRateSensor);
    }

    private void createPotableWaterOutFlowRateSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger
                    .debug("Creating PotableWaterOutFlowRateSensor with moduleName: "
                            + moduleName);
            PotableWaterOutFlowRateSensorImpl myPotableWaterOutFlowRateSensorImpl = new PotableWaterOutFlowRateSensorImpl(
                    myID, moduleName);
            setupBioModule(myPotableWaterOutFlowRateSensorImpl, node);
            BiosimServer.registerServer(
                    new PotableWaterOutFlowRateSensorPOATie(
                            myPotableWaterOutFlowRateSensorImpl),
                    myPotableWaterOutFlowRateSensorImpl.getModuleName(),
                    myPotableWaterOutFlowRateSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configurePotableWaterOutFlowRateSensor(Node node) {
        PotableWaterOutFlowRateSensor myPotableWaterOutFlowRateSensor = PotableWaterOutFlowRateSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myPotableWaterOutFlowRateSensor
                .setInput(PotableWaterProducerHelper
                        .narrow(grabModule(getInputName(node))),
                        getFlowRateIndex(node));
        mySensors.add(myPotableWaterOutFlowRateSensor);
    }

    private void createPotableWaterStoreLevelSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger
                    .debug("Creating PotableWaterStoreLevelSensor with moduleName: "
                            + moduleName);
            PotableWaterStoreLevelSensorImpl myPotableWaterStoreLevelSensorImpl = new PotableWaterStoreLevelSensorImpl(
                    myID, moduleName);
            setupBioModule(myPotableWaterStoreLevelSensorImpl, node);
            BiosimServer.registerServer(new PotableWaterStoreLevelSensorPOATie(
                    myPotableWaterStoreLevelSensorImpl),
                    myPotableWaterStoreLevelSensorImpl.getModuleName(),
                    myPotableWaterStoreLevelSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configurePotableWaterStoreLevelSensor(Node node) {
        PotableWaterStoreLevelSensor myPotableWaterStoreLevelSensor = PotableWaterStoreLevelSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myPotableWaterStoreLevelSensor.setInput(PotableWaterStoreHelper
                .narrow(grabModule(getInputName(node))));
        mySensors.add(myPotableWaterStoreLevelSensor);
    }

    private void createGreyWaterInFlowRateSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger
                    .debug("Creating GreyWaterInFlowRateSensor with moduleName: "
                            + moduleName);
            GreyWaterInFlowRateSensorImpl myGreyWaterInFlowRateSensorImpl = new GreyWaterInFlowRateSensorImpl(
                    myID, moduleName);
            setupBioModule(myGreyWaterInFlowRateSensorImpl, node);
            BiosimServer.registerServer(new GreyWaterInFlowRateSensorPOATie(
                    myGreyWaterInFlowRateSensorImpl),
                    myGreyWaterInFlowRateSensorImpl.getModuleName(),
                    myGreyWaterInFlowRateSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureGreyWaterInFlowRateSensor(Node node) {
        GreyWaterInFlowRateSensor myGreyWaterInFlowRateSensor = GreyWaterInFlowRateSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myGreyWaterInFlowRateSensor
                .setInput(GreyWaterConsumerHelper
                        .narrow(grabModule(getInputName(node))),
                        getFlowRateIndex(node));
        mySensors.add(myGreyWaterInFlowRateSensor);
    }

    private void createGreyWaterOutFlowRateSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger
                    .debug("Creating GreyWaterOutFlowRateSensor with moduleName: "
                            + moduleName);
            GreyWaterOutFlowRateSensorImpl myGreyWaterOutFlowRateSensorImpl = new GreyWaterOutFlowRateSensorImpl(
                    myID, moduleName);
            setupBioModule(myGreyWaterOutFlowRateSensorImpl, node);
            BiosimServer.registerServer(new GreyWaterOutFlowRateSensorPOATie(
                    myGreyWaterOutFlowRateSensorImpl),
                    myGreyWaterOutFlowRateSensorImpl.getModuleName(),
                    myGreyWaterOutFlowRateSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureGreyWaterOutFlowRateSensor(Node node) {
        GreyWaterOutFlowRateSensor myGreyWaterOutFlowRateSensor = GreyWaterOutFlowRateSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myGreyWaterOutFlowRateSensor
                .setInput(GreyWaterProducerHelper
                        .narrow(grabModule(getInputName(node))),
                        getFlowRateIndex(node));
        mySensors.add(myGreyWaterOutFlowRateSensor);
    }

    private void createGreyWaterStoreLevelSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger
                    .debug("Creating GreyWaterStoreLevelSensor with moduleName: "
                            + moduleName);
            GreyWaterStoreLevelSensorImpl myGreyWaterStoreLevelSensorImpl = new GreyWaterStoreLevelSensorImpl(
                    myID, moduleName);
            setupBioModule(myGreyWaterStoreLevelSensorImpl, node);
            BiosimServer.registerServer(new GreyWaterStoreLevelSensorPOATie(
                    myGreyWaterStoreLevelSensorImpl),
                    myGreyWaterStoreLevelSensorImpl.getModuleName(),
                    myGreyWaterStoreLevelSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureGreyWaterStoreLevelSensor(Node node) {
        GreyWaterStoreLevelSensor myGreyWaterStoreLevelSensor = GreyWaterStoreLevelSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myGreyWaterStoreLevelSensor.setInput(GreyWaterStoreHelper
                .narrow(grabModule(getInputName(node))));
        mySensors.add(myGreyWaterStoreLevelSensor);
    }

    private void createDirtyWaterInFlowRateSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger
                    .debug("Creating DirtyWaterInFlowRateSensor with moduleName: "
                            + moduleName);
            DirtyWaterInFlowRateSensorImpl myDirtyWaterInFlowRateSensorImpl = new DirtyWaterInFlowRateSensorImpl(
                    myID, moduleName);
            setupBioModule(myDirtyWaterInFlowRateSensorImpl, node);
            BiosimServer.registerServer(new DirtyWaterInFlowRateSensorPOATie(
                    myDirtyWaterInFlowRateSensorImpl),
                    myDirtyWaterInFlowRateSensorImpl.getModuleName(),
                    myDirtyWaterInFlowRateSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureDirtyWaterInFlowRateSensor(Node node) {
        DirtyWaterInFlowRateSensor myDirtyWaterInFlowRateSensor = DirtyWaterInFlowRateSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myDirtyWaterInFlowRateSensor
                .setInput(DirtyWaterConsumerHelper
                        .narrow(grabModule(getInputName(node))),
                        getFlowRateIndex(node));
        mySensors.add(myDirtyWaterInFlowRateSensor);
    }

    private void createDirtyWaterOutFlowRateSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger
                    .debug("Creating DirtyWaterOutFlowRateSensor with moduleName: "
                            + moduleName);
            DirtyWaterOutFlowRateSensorImpl myDirtyWaterOutFlowRateSensorImpl = new DirtyWaterOutFlowRateSensorImpl(
                    myID, moduleName);
            setupBioModule(myDirtyWaterOutFlowRateSensorImpl, node);
            BiosimServer.registerServer(new DirtyWaterOutFlowRateSensorPOATie(
                    myDirtyWaterOutFlowRateSensorImpl),
                    myDirtyWaterOutFlowRateSensorImpl.getModuleName(),
                    myDirtyWaterOutFlowRateSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureDirtyWaterOutFlowRateSensor(Node node) {
        DirtyWaterOutFlowRateSensor myDirtyWaterOutFlowRateSensor = DirtyWaterOutFlowRateSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myDirtyWaterOutFlowRateSensor
                .setInput(DirtyWaterProducerHelper
                        .narrow(grabModule(getInputName(node))),
                        getFlowRateIndex(node));
        mySensors.add(myDirtyWaterOutFlowRateSensor);
    }

    private void createDirtyWaterStoreLevelSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger
                    .debug("Creating DirtyWaterStoreLevelSensor with moduleName: "
                            + moduleName);
            DirtyWaterStoreLevelSensorImpl myDirtyWaterStoreLevelSensorImpl = new DirtyWaterStoreLevelSensorImpl(
                    myID, moduleName);
            setupBioModule(myDirtyWaterStoreLevelSensorImpl, node);
            BiosimServer.registerServer(new DirtyWaterStoreLevelSensorPOATie(
                    myDirtyWaterStoreLevelSensorImpl),
                    myDirtyWaterStoreLevelSensorImpl.getModuleName(),
                    myDirtyWaterStoreLevelSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureDirtyWaterStoreLevelSensor(Node node) {
        DirtyWaterStoreLevelSensor myDirtyWaterStoreLevelSensor = DirtyWaterStoreLevelSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myDirtyWaterStoreLevelSensor.setInput(DirtyWaterStoreHelper
                .narrow(grabModule(getInputName(node))));
        mySensors.add(myDirtyWaterStoreLevelSensor);
    }

    private void createWaterInFlowRateSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger.debug("Creating WaterInFlowRateSensor with moduleName: "
                    + moduleName);
            WaterInFlowRateSensorImpl myWaterInFlowRateSensorImpl = new WaterInFlowRateSensorImpl(
                    myID, moduleName);
            setupBioModule(myWaterInFlowRateSensorImpl, node);
            BiosimServer.registerServer(new WaterInFlowRateSensorPOATie(
                    myWaterInFlowRateSensorImpl), myWaterInFlowRateSensorImpl
                    .getModuleName(), myWaterInFlowRateSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureWaterInFlowRateSensor(Node node) {
        WaterInFlowRateSensor myWaterInFlowRateSensor = WaterInFlowRateSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myWaterInFlowRateSensor
                .setInput(WaterConsumerHelper
                        .narrow(grabModule(getInputName(node))),
                        getFlowRateIndex(node));
        mySensors.add(myWaterInFlowRateSensor);
    }

    private void createWaterOutFlowRateSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger.debug("Creating WaterOutFlowRateSensor with moduleName: "
                    + moduleName);
            WaterOutFlowRateSensorImpl myWaterOutFlowRateSensorImpl = new WaterOutFlowRateSensorImpl(
                    myID, moduleName);
            setupBioModule(myWaterOutFlowRateSensorImpl, node);
            BiosimServer.registerServer(new WaterOutFlowRateSensorPOATie(
                    myWaterOutFlowRateSensorImpl), myWaterOutFlowRateSensorImpl
                    .getModuleName(), myWaterOutFlowRateSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureWaterOutFlowRateSensor(Node node) {
        WaterOutFlowRateSensor myWaterOutFlowRateSensor = WaterOutFlowRateSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myWaterOutFlowRateSensor
                .setInput(WaterProducerHelper
                        .narrow(grabModule(getInputName(node))),
                        getFlowRateIndex(node));
        mySensors.add(myWaterOutFlowRateSensor);
    }

    private void createWaterStoreLevelSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger.debug("Creating WaterStoreLevelSensor with moduleName: "
                    + moduleName);
            WaterStoreLevelSensorImpl myWaterStoreLevelSensorImpl = new WaterStoreLevelSensorImpl(
                    myID, moduleName);
            setupBioModule(myWaterStoreLevelSensorImpl, node);
            BiosimServer.registerServer(new WaterStoreLevelSensorPOATie(
                    myWaterStoreLevelSensorImpl), myWaterStoreLevelSensorImpl
                    .getModuleName(), myWaterStoreLevelSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureWaterStoreLevelSensor(Node node) {
        WaterStoreLevelSensor myWaterStoreLevelSensor = WaterStoreLevelSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myWaterStoreLevelSensor.setInput(WaterStoreHelper
                .narrow(grabModule(getInputName(node))));
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
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger
                    .debug("Creating DryWasteInFlowRateSensor with moduleName: "
                            + moduleName);
            DryWasteInFlowRateSensorImpl myDryWasteInFlowRateSensorImpl = new DryWasteInFlowRateSensorImpl(
                    myID, moduleName);
            setupBioModule(myDryWasteInFlowRateSensorImpl, node);
            BiosimServer.registerServer(new DryWasteInFlowRateSensorPOATie(
                    myDryWasteInFlowRateSensorImpl),
                    myDryWasteInFlowRateSensorImpl.getModuleName(),
                    myDryWasteInFlowRateSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureDryWasteInFlowRateSensor(Node node) {
        DryWasteInFlowRateSensor myDryWasteInFlowRateSensor = DryWasteInFlowRateSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myDryWasteInFlowRateSensor
                .setInput(DryWasteConsumerHelper
                        .narrow(grabModule(getInputName(node))),
                        getFlowRateIndex(node));
        mySensors.add(myDryWasteInFlowRateSensor);
    }

    private void createDryWasteOutFlowRateSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger
                    .debug("Creating DryWasteOutFlowRateSensor with moduleName: "
                            + moduleName);
            DryWasteOutFlowRateSensorImpl myDryWasteOutFlowRateSensorImpl = new DryWasteOutFlowRateSensorImpl(
                    myID, moduleName);
            setupBioModule(myDryWasteOutFlowRateSensorImpl, node);
            BiosimServer.registerServer(new DryWasteOutFlowRateSensorPOATie(
                    myDryWasteOutFlowRateSensorImpl),
                    myDryWasteOutFlowRateSensorImpl.getModuleName(),
                    myDryWasteOutFlowRateSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureDryWasteOutFlowRateSensor(Node node) {
        DryWasteOutFlowRateSensor myDryWasteOutFlowRateSensor = DryWasteOutFlowRateSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myDryWasteOutFlowRateSensor
                .setInput(DryWasteProducerHelper
                        .narrow(grabModule(getInputName(node))),
                        getFlowRateIndex(node));
        mySensors.add(myDryWasteOutFlowRateSensor);
    }

    private void createDryWasteStoreLevelSensor(Node node) {
        String moduleName = getModuleName(node);
        if (isCreatedLocally(node)) {
            myLogger
                    .debug("Creating DryWasteStoreLevelSensor with moduleName: "
                            + moduleName);
            DryWasteStoreLevelSensorImpl myDryWasteStoreLevelSensorImpl = new DryWasteStoreLevelSensorImpl(
                    myID, moduleName);
            setupBioModule(myDryWasteStoreLevelSensorImpl, node);
            BiosimServer.registerServer(new DryWasteStoreLevelSensorPOATie(
                    myDryWasteStoreLevelSensorImpl),
                    myDryWasteStoreLevelSensorImpl.getModuleName(),
                    myDryWasteStoreLevelSensorImpl.getID());
        } else
            printRemoteWarningMessage(moduleName);
    }

    private void configureDryWasteStoreLevelSensor(Node node) {
        DryWasteStoreLevelSensor myDryWasteStoreLevelSensor = DryWasteStoreLevelSensorHelper
                .narrow(grabModule(getModuleName(node)));
        myDryWasteStoreLevelSensor.setInput(DryWasteStoreHelper
                .narrow(grabModule(getInputName(node))));
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