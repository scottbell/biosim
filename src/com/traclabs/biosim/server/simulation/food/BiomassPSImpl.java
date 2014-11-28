package com.traclabs.biosim.server.simulation.food;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.traclabs.biosim.idl.framework.Malfunction;
import com.traclabs.biosim.idl.framework.MalfunctionIntensity;
import com.traclabs.biosim.idl.framework.MalfunctionLength;
import com.traclabs.biosim.idl.simulation.environment.AirConsumerDefinition;
import com.traclabs.biosim.idl.simulation.environment.AirConsumerOperations;
import com.traclabs.biosim.idl.simulation.environment.AirProducerDefinition;
import com.traclabs.biosim.idl.simulation.environment.AirProducerOperations;
import com.traclabs.biosim.idl.simulation.food.BiomassPSOperations;
import com.traclabs.biosim.idl.simulation.food.BiomassProducerDefinition;
import com.traclabs.biosim.idl.simulation.food.BiomassProducerOperations;
import com.traclabs.biosim.idl.simulation.food.PlantType;
import com.traclabs.biosim.idl.simulation.food.Shelf;
import com.traclabs.biosim.idl.simulation.food.ShelfHelper;
import com.traclabs.biosim.idl.simulation.power.PowerConsumerDefinition;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterProducerDefinition;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterProducerOperations;
import com.traclabs.biosim.idl.simulation.water.GreyWaterConsumerDefinition;
import com.traclabs.biosim.idl.simulation.water.GreyWaterConsumerOperations;
import com.traclabs.biosim.idl.simulation.water.PotableWaterConsumerDefinition;
import com.traclabs.biosim.idl.simulation.water.PotableWaterConsumerOperations;
import com.traclabs.biosim.server.simulation.environment.AirConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.environment.AirProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;
import com.traclabs.biosim.server.simulation.power.PowerConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.water.DirtyWaterProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.water.GreyWaterConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.water.PotableWaterConsumerDefinitionImpl;
import com.traclabs.biosim.util.OrbUtils;

/**
 * The Biomass RS is essentially responsible for growing plants. The Biomass RS
 * consists of many ShelfImpls, and inside them, a Plant. The ShelfImpl gathers
 * water and light for the plant. The plant itself breathes from the atmosphere
 * and produces biomass. The plant matter (biomass) is fed into the food
 * processor to create food for the crew. The plants can also (along with the
 * AirRS) take CO2 out of the air and add O2.
 * 
 * @author Scott Bell
 */

public class BiomassPSImpl extends SimBioModuleImpl implements
        BiomassPSOperations, PotableWaterConsumerOperations,
        AirConsumerOperations, AirProducerOperations,
        GreyWaterConsumerOperations, BiomassProducerOperations,
        DirtyWaterProducerOperations {
    private List<ShelfImpl> myShelves;

    private boolean autoHarvestAndReplant = true;

    //Consumers, Producers
    private PowerConsumerDefinitionImpl myPowerConsumerDefinitionImpl;

    private AirConsumerDefinitionImpl myAirConsumerDefinitionImpl;

    private PotableWaterConsumerDefinitionImpl myPotableWaterConsumerDefinitionImpl;

    private GreyWaterConsumerDefinitionImpl myGreyWaterConsumerDefinitionImpl;

    private DirtyWaterProducerDefinitionImpl myDirtyWaterProducerDefinitionImpl;

    private AirProducerDefinitionImpl myAirProducerDefinitionImpl;

    private BiomassProducerDefinitionImpl myBiomassProducerDefinitionImpl;
    
    private boolean myDeathEnabled = true;
    
    public BiomassPSImpl(){
    	this(0, "Unnamed BiomassPSImpl");
    }

    public BiomassPSImpl(int pID, String pName) {
        super(pID, pName);
        myShelves = new Vector<ShelfImpl>();
        myPowerConsumerDefinitionImpl = new PowerConsumerDefinitionImpl(this);
        myAirConsumerDefinitionImpl = new AirConsumerDefinitionImpl(this);
        myPotableWaterConsumerDefinitionImpl = new PotableWaterConsumerDefinitionImpl(this);
        myGreyWaterConsumerDefinitionImpl = new GreyWaterConsumerDefinitionImpl(this);
        myDirtyWaterProducerDefinitionImpl = new DirtyWaterProducerDefinitionImpl(this);
        myAirProducerDefinitionImpl = new AirProducerDefinitionImpl(this);
        myBiomassProducerDefinitionImpl = new BiomassProducerDefinitionImpl(this);
    }

    public PowerConsumerDefinition getPowerConsumerDefinition() {
        return myPowerConsumerDefinitionImpl.getCorbaObject();
    }

    public AirConsumerDefinition getAirConsumerDefinition() {
        return myAirConsumerDefinitionImpl.getCorbaObject();
    }

    public PotableWaterConsumerDefinition getPotableWaterConsumerDefinition() {
        return myPotableWaterConsumerDefinitionImpl.getCorbaObject();
    }

    public GreyWaterConsumerDefinition getGreyWaterConsumerDefinition() {
        return myGreyWaterConsumerDefinitionImpl.getCorbaObject();
    }

    public DirtyWaterProducerDefinition getDirtyWaterProducerDefinition() {
        return myDirtyWaterProducerDefinitionImpl.getCorbaObject();
    }

    public AirProducerDefinition getAirProducerDefinition() {
        return myAirProducerDefinitionImpl.getCorbaObject();
    }

    public BiomassProducerDefinition getBiomassProducerDefinition() {
        return myBiomassProducerDefinitionImpl.getCorbaObject();
    }

    public Shelf[] getShelves() {
        Shelf[] theShelfArray = new Shelf[myShelves.size()];
        int i = 0;
        for (Iterator iter = myShelves.iterator(); iter.hasNext(); i++) {
            ShelfImpl currentShelf = (ShelfImpl) (iter.next());
            theShelfArray[i] = ShelfHelper.narrow(OrbUtils
                    .poaToCorbaObj(currentShelf));
        }
        return theShelfArray;
    }

    protected void performMalfunctions() {
        float productionRate = 1f;
        for (Iterator iter = myMalfunctions.values().iterator(); iter.hasNext();) {
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
        for (Iterator iter = myShelves.iterator(); iter.hasNext();) {
            ShelfImpl currentShelf = (ShelfImpl) (iter.next());
            currentShelf.getPlantImpl().setProductionRate(productionRate);
        }
    }

    public void clearShelves() {
        myShelves.clear();
    }

    public Shelf getShelf(int index) {
        ShelfImpl theShelf = (myShelves.get(index));
        return ShelfHelper.narrow(OrbUtils.poaToCorbaObj(theShelf));
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
        ShelfImpl newShelfImpl = new ShelfImpl(pType, pCropArea, this, startDay);
        myShelves.add(newShelfImpl);
        return ShelfHelper.narrow(OrbUtils.poaToCorbaObj(newShelfImpl));
    }

    /**
     * Resets production/consumption levels and death/affliction flags
     */
    public void reset() {
        super.reset();
        clearActualFlowRates();
        for (Iterator iter = myShelves.iterator(); iter.hasNext();) {
            ShelfImpl currentShelf = (ShelfImpl) (iter.next());
            currentShelf.reset();
        }
        myPowerConsumerDefinitionImpl.reset();
        myAirConsumerDefinitionImpl.reset();
        myPotableWaterConsumerDefinitionImpl.reset();
        myGreyWaterConsumerDefinitionImpl.reset();
        myDirtyWaterProducerDefinitionImpl.reset();
        myAirProducerDefinitionImpl.reset();
        myBiomassProducerDefinitionImpl.reset();
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
        for (Iterator iter = myShelves.iterator(); iter.hasNext();) {
            ShelfImpl currentShelf = (ShelfImpl) (iter.next());
            currentShelf.tick();
        }
        myLogger.debug(getModuleName() + " ticked");
    }

    public void log() {
    }
    
    public boolean isAnyPlantDead(){
    	for (Shelf currentShelf : getShelves())
			if (currentShelf.isDead())
				return true;
    	return false;
    }

    /**
     * @return Returns the myAirConsumerDefinitionImpl.
     */
    protected AirConsumerDefinitionImpl getAirConsumerDefinitionImpl() {
        return myAirConsumerDefinitionImpl;
    }

    /**
     * @return Returns the myAirProducerDefinitionImpl.
     */
    protected AirProducerDefinitionImpl getAirProducerDefinitionImpl() {
        return myAirProducerDefinitionImpl;
    }

    /**
     * @return Returns the myBiomassProducerDefinitionImpl.
     */
    protected BiomassProducerDefinitionImpl getBiomassProducerDefinitionImpl() {
        return myBiomassProducerDefinitionImpl;
    }

    /**
     * @return Returns the myDirtyWaterProducerDefinitionImpl.
     */
    protected DirtyWaterProducerDefinitionImpl getDirtyWaterProducerDefinitionImpl() {
        return myDirtyWaterProducerDefinitionImpl;
    }

    /**
     * @return Returns the myGreyWaterConsumerDefinitionImpl.
     */
    protected GreyWaterConsumerDefinitionImpl getGreyWaterConsumerDefinitionImpl() {
        return myGreyWaterConsumerDefinitionImpl;
    }

    /**
     * @return Returns the myPotableWaterConsumerDefinitionImpl.
     */
    protected PotableWaterConsumerDefinitionImpl getPotableWaterConsumerDefinitionImpl() {
        return myPotableWaterConsumerDefinitionImpl;
    }

    /**
     * @return Returns the myPowerConsumerDefinitionImpl.
     */
    protected PowerConsumerDefinitionImpl getPowerConsumerDefinitionImpl() {
        return myPowerConsumerDefinitionImpl;
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
