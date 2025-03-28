package com.traclabs.biosim.server.simulation.food;

import com.traclabs.biosim.server.framework.Malfunction;
import com.traclabs.biosim.server.framework.MalfunctionIntensity;
import com.traclabs.biosim.server.framework.MalfunctionLength;
import com.traclabs.biosim.server.simulation.environment.AirConsumerDefinition;
import com.traclabs.biosim.server.simulation.environment.AirProducerDefinition;
import com.traclabs.biosim.server.simulation.framework.SimBioModule;
import com.traclabs.biosim.server.simulation.power.PowerConsumerDefinition;
import com.traclabs.biosim.server.simulation.water.DirtyWaterProducerDefinition;
import com.traclabs.biosim.server.simulation.water.GreyWaterConsumerDefinition;
import com.traclabs.biosim.server.simulation.water.PotableWaterConsumerDefinition;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * The Biomass RS is essentially responsible for growing plants. The Biomass RS
 * consists of many Shelfs, and inside them, a Plant. The Shelf gathers
 * water and light for the plant. The plant itself breathes from the atmosphere
 * and produces biomass. The plant matter (biomass) is fed into the food
 * processor to create food for the crew. The plants can also (along with the
 * AirRS) take CO2 out of the air and add O2.
 *
 * @author Scott Bell
 */

public class BiomassPS extends SimBioModule {
    private final List<Shelf> myShelves;

    private boolean autoHarvestAndReplant = true;

    //Consumers, Producers
    private final PowerConsumerDefinition myPowerConsumerDefinition;

    private final AirConsumerDefinition myAirConsumerDefinition;

    private final PotableWaterConsumerDefinition myPotableWaterConsumerDefinition;

    private final GreyWaterConsumerDefinition myGreyWaterConsumerDefinition;

    private final DirtyWaterProducerDefinition myDirtyWaterProducerDefinition;

    private final AirProducerDefinition myAirProducerDefinition;

    private final BiomassProducerDefinition myBiomassProducerDefinition;

    private boolean myDeathEnabled = true;

    public BiomassPS() {
        this(0, "Unnamed BiomassPS");
    }

    public BiomassPS(int pID, String pName) {
        super(pID, pName);
        myShelves = new Vector<Shelf>();
        myPowerConsumerDefinition = new PowerConsumerDefinition(this);
        myAirConsumerDefinition = new AirConsumerDefinition(this);
        myPotableWaterConsumerDefinition = new PotableWaterConsumerDefinition(this);
        myGreyWaterConsumerDefinition = new GreyWaterConsumerDefinition(this);
        myDirtyWaterProducerDefinition = new DirtyWaterProducerDefinition(this);
        myAirProducerDefinition = new AirProducerDefinition(this);
        myBiomassProducerDefinition = new BiomassProducerDefinition(this);
    }

    public Shelf[] getShelves() {
        Shelf[] theShelfArray = new Shelf[myShelves.size()];
        int i = 0;
        for (Iterator iter = myShelves.iterator(); iter.hasNext(); i++) {
            Shelf currentShelf = (Shelf) (iter.next());
            theShelfArray[i] = currentShelf;
        }
        return theShelfArray;
    }

    protected void performMalfunctions() {
        float productionRate = 1f;
        for (Iterator iter = myMalfunctions.values().iterator(); iter.hasNext(); ) {
            Malfunction currentMalfunction = (Malfunction) (iter.next());
            if (currentMalfunction.getLength() == MalfunctionLength.TEMPORARY_MALF) {
                if (currentMalfunction.getIntensity() == MalfunctionIntensity.SEVERE_MALF)
                    productionRate *= 0.50; // 50% reduction
                else if (currentMalfunction.getIntensity() == MalfunctionIntensity.MEDIUM_MALF)
                    productionRate *= 0.75; // 25% reduction
                else if (currentMalfunction.getIntensity() == MalfunctionIntensity.LOW_MALF)
                    productionRate *= 0.90; // 10% reduction
            } else if (currentMalfunction.getLength() == MalfunctionLength.PERMANENT_MALF) {
                if (currentMalfunction.getIntensity() == MalfunctionIntensity.SEVERE_MALF)
                    productionRate *= 0.50; // 50% reduction
                else if (currentMalfunction.getIntensity() == MalfunctionIntensity.MEDIUM_MALF)
                    productionRate *= 0.75; // 25% reduction
                else if (currentMalfunction.getIntensity() == MalfunctionIntensity.LOW_MALF)
                    productionRate *= 0.90; // 10% reduction
            }
        }
        for (Iterator iter = myShelves.iterator(); iter.hasNext(); ) {
            Shelf currentShelf = (Shelf) (iter.next());
            currentShelf.getPlant().setProductionRate(productionRate);
        }
    }

    public void clearShelves() {
        myShelves.clear();
    }

    public Shelf getShelf(int index) {
        Shelf theShelf = (myShelves.get(index));
        return theShelf;
    }

    public int getNumberOfShelves() {
        return myShelves.size();
    }

    public boolean autoHarvestAndReplantEnabled() {
        return autoHarvestAndReplant;
    }

    public void setAutoHarvestAndReplantEnabled(boolean pAutoHarvestAndReplant) {
        autoHarvestAndReplant = pAutoHarvestAndReplant;
    }

    public Shelf createNewShelf(PlantType pType, float pCropArea, int startDay) {
        Shelf newShelf = new Shelf(pType, pCropArea, this, startDay);
        myShelves.add(newShelf);
        return newShelf;
    }

    /**
     * Resets production/consumption levels and death/affliction flags
     */
    public void reset() {
        super.reset();
        clearActualFlowRates();
        for (Iterator iter = myShelves.iterator(); iter.hasNext(); ) {
            Shelf currentShelf = (Shelf) (iter.next());
            currentShelf.reset();
        }
        myPowerConsumerDefinition.reset();
        myAirConsumerDefinition.reset();
        myPotableWaterConsumerDefinition.reset();
        myGreyWaterConsumerDefinition.reset();
        myDirtyWaterProducerDefinition.reset();
        myAirProducerDefinition.reset();
        myBiomassProducerDefinition.reset();
    }

    private void clearActualFlowRates() {
        Arrays.fill(getPowerConsumerDefinition().getActualFlowRates(), 0f);
        Arrays.fill(getBiomassProducerDefinition().getActualFlowRates(), 0f);
        Arrays.fill(getPotableWaterConsumerDefinition().getActualFlowRates(),
                0f);
        Arrays.fill(getGreyWaterConsumerDefinition().getActualFlowRates(), 0f);
        Arrays.fill(getDirtyWaterProducerDefinition().getActualFlowRates(), 0f);
        Arrays.fill(getAirConsumerDefinition().getActualFlowRates(), 0f);
        Arrays.fill(getAirProducerDefinition().getActualFlowRates(), 0f);
    }

    public void addAirInputActualFlowRates(int index, float value) {
        getAirConsumerDefinition().getActualFlowRates()[index] += value;
    }

    public void addAirOutputActualFlowRates(int index, float value) {
        getAirProducerDefinition().getActualFlowRates()[index] += value;
    }

    public void tick() {
        super.tick();
        clearActualFlowRates();
        for (Iterator iter = myShelves.iterator(); iter.hasNext(); ) {
            Shelf currentShelf = (Shelf) (iter.next());
            currentShelf.tick();
        }
        myLogger.debug(getModuleName() + " ticked");
    }

    public void log() {
    }

    public boolean isAnyPlantDead() {
        for (Shelf currentShelf : getShelves())
            if (currentShelf.isDead())
                return true;
        return false;
    }

    /**
     * @return Returns the myAirConsumerDefinition.
     */
    protected AirConsumerDefinition getAirConsumerDefinition() {
        return myAirConsumerDefinition;
    }

    /**
     * @return Returns the myAirProducerDefinition.
     */
    protected AirProducerDefinition getAirProducerDefinition() {
        return myAirProducerDefinition;
    }

    /**
     * @return Returns the myBiomassProducerDefinition.
     */
    protected BiomassProducerDefinition getBiomassProducerDefinition() {
        return myBiomassProducerDefinition;
    }

    /**
     * @return Returns the myDirtyWaterProducerDefinition.
     */
    protected DirtyWaterProducerDefinition getDirtyWaterProducerDefinition() {
        return myDirtyWaterProducerDefinition;
    }

    /**
     * @return Returns the myGreyWaterConsumerDefinition.
     */
    protected GreyWaterConsumerDefinition getGreyWaterConsumerDefinition() {
        return myGreyWaterConsumerDefinition;
    }

    /**
     * @return Returns the myPotableWaterConsumerDefinition.
     */
    protected PotableWaterConsumerDefinition getPotableWaterConsumerDefinition() {
        return myPotableWaterConsumerDefinition;
    }

    /**
     * @return Returns the myPowerConsumerDefinition.
     */
    protected PowerConsumerDefinition getPowerConsumerDefinition() {
        return myPowerConsumerDefinition;
    }

    public void killPlants() {
        for (Shelf currentShelf : getShelves())
            currentShelf.kill();
    }

    public boolean getDeathEnabled() {
        return myDeathEnabled;
    }

    public void setDeathEnabled(boolean deathEnabled) {
        this.myDeathEnabled = deathEnabled;
    }
}
