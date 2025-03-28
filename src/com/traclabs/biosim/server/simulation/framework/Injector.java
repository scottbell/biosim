package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.server.simulation.air.*;
import com.traclabs.biosim.server.simulation.environment.AirConsumer;
import com.traclabs.biosim.server.simulation.environment.AirProducer;
import com.traclabs.biosim.server.simulation.food.BiomassConsumer;
import com.traclabs.biosim.server.simulation.food.BiomassProducer;
import com.traclabs.biosim.server.simulation.food.FoodConsumer;
import com.traclabs.biosim.server.simulation.food.FoodProducer;
import com.traclabs.biosim.server.simulation.power.PowerConsumer;
import com.traclabs.biosim.server.simulation.power.PowerProducer;
import com.traclabs.biosim.server.simulation.waste.DryWasteConsumer;
import com.traclabs.biosim.server.simulation.waste.DryWasteProducer;
import com.traclabs.biosim.server.simulation.water.*;

/**
 * The basic Accumulator implementation. Can be configured to take any modules
 * as input, and any modules as output. It takes as much as it can (max taken
 * set by maxFlowRates) from one module and pushes it into another module.
 * Functionally equivalent to an Accumulator at this point.
 *
 * @author Scott Bell
 */

public class Injector extends ResourceMover implements PowerConsumer, PotableWaterConsumer, GreyWaterConsumer, DirtyWaterConsumer, WaterConsumer, O2Consumer, CO2Consumer, NitrogenConsumer, AirConsumer, BiomassConsumer, FoodConsumer, DryWasteConsumer, PowerProducer, PotableWaterProducer, GreyWaterProducer, DirtyWaterProducer, O2Producer, CO2Producer, NitrogenProducer, AirProducer, BiomassProducer, FoodProducer, DryWasteProducer {

    public Injector(int pID, String pName) {
        super(pID, pName);
    }


}