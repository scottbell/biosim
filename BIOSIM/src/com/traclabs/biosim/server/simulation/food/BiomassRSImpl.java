package com.traclabs.biosim.server.simulation.food;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.traclabs.biosim.idl.framework.Malfunction;
import com.traclabs.biosim.idl.framework.MalfunctionIntensity;
import com.traclabs.biosim.idl.framework.MalfunctionLength;
import com.traclabs.biosim.idl.simulation.food.BiomassRSOperations;
import com.traclabs.biosim.idl.simulation.food.PlantType;
import com.traclabs.biosim.idl.simulation.food.Shelf;
import com.traclabs.biosim.idl.simulation.food.ShelfHelper;
import com.traclabs.biosim.idl.simulation.framework.AirConsumerDefinition;
import com.traclabs.biosim.idl.simulation.framework.AirConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.AirProducerDefinition;
import com.traclabs.biosim.idl.simulation.framework.AirProducerOperations;
import com.traclabs.biosim.idl.simulation.framework.BiomassProducerDefinition;
import com.traclabs.biosim.idl.simulation.framework.BiomassProducerOperations;
import com.traclabs.biosim.idl.simulation.framework.DirtyWaterProducerDefinition;
import com.traclabs.biosim.idl.simulation.framework.DirtyWaterProducerOperations;
import com.traclabs.biosim.idl.simulation.framework.GreyWaterConsumerDefinition;
import com.traclabs.biosim.idl.simulation.framework.GreyWaterConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.PotableWaterConsumerDefinition;
import com.traclabs.biosim.idl.simulation.framework.PotableWaterConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.PowerConsumerDefinition;
import com.traclabs.biosim.server.simulation.framework.AirConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.framework.AirProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.framework.BiomassProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.framework.DirtyWaterProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.framework.GreyWaterConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.framework.PotableWaterConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.framework.PowerConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;
import com.traclabs.biosim.server.util.OrbUtils;

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

public class BiomassRSImpl extends SimBioModuleImpl implements
        BiomassRSOperations, PotableWaterConsumerOperations,
        AirConsumerOperations, AirProducerOperations,
        GreyWaterConsumerOperations, BiomassProducerOperations,
        DirtyWaterProducerOperations {
    private List myShelves;

    private boolean autoHarvestAndReplant = true;
    
    //Consumers, Producers
    private PowerConsumerDefinitionImpl myPowerConsumerDefinitionImpl;
    private AirConsumerDefinitionImpl myAirConsumerDefinitionImpl;
    private PotableWaterConsumerDefinitionImpl myPotableWaterConsumerDefinitionImpl;
    private GreyWaterConsumerDefinitionImpl myGreyWaterConsumerDefinitionImpl;
    private DirtyWaterProducerDefinitionImpl myDirtyWaterProducerDefinitionImpl;
    private AirProducerDefinitionImpl myAirProducerDefinitionImpl;
    private BiomassProducerDefinitionImpl myBiomassProducerDefinitionImpl;
    

    public BiomassRSImpl(int pID, String pName) {
        super(pID, pName);
        myShelves = new Vector();

        myPowerConsumerDefinitionImpl = new PowerConsumerDefinitionImpl();
        myAirConsumerDefinitionImpl = new AirConsumerDefinitionImpl();
        myPotableWaterConsumerDefinitionImpl = new PotableWaterConsumerDefinitionImpl();
        myGreyWaterConsumerDefinitionImpl = new GreyWaterConsumerDefinitionImpl();
        myDirtyWaterProducerDefinitionImpl = new DirtyWaterProducerDefinitionImpl();
        myAirProducerDefinitionImpl = new AirProducerDefinitionImpl();
        myBiomassProducerDefinitionImpl = new BiomassProducerDefinitionImpl();
    }
    

    public PowerConsumerDefinition getPowerConsumerDefinition(){
        return myPowerConsumerDefinitionImpl.getCorbaObject();
    }
    
    public AirConsumerDefinition getAirConsumerDefinition(){
        return myAirConsumerDefinitionImpl.getCorbaObject();
    }
    
    public PotableWaterConsumerDefinition getPotableWaterConsumerDefinition(){
        return myPotableWaterConsumerDefinitionImpl.getCorbaObject();
    }
    
    public GreyWaterConsumerDefinition getGreyWaterConsumerDefinition(){
        return myGreyWaterConsumerDefinitionImpl.getCorbaObject();
    }
    
    public DirtyWaterProducerDefinition getDirtyWaterProducerDefinition(){
        return myDirtyWaterProducerDefinitionImpl.getCorbaObject();
    }
    
    public AirProducerDefinition getAirProducerDefinition(){
        return myAirProducerDefinitionImpl.getCorbaObject();
    }
    
    public BiomassProducerDefinition getBiomassProducerDefinition(){
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
                    productionRate *= 0.50;
                else if (currentMalfunction.getIntensity() == MalfunctionIntensity.MEDIUM_MALF)
                    productionRate *= 0.25;
                else if (currentMalfunction.getIntensity() == MalfunctionIntensity.LOW_MALF)
                    productionRate *= 0.10;
            } else if (currentMalfunction.getLength() == MalfunctionLength.PERMANENT_MALF) {
                if (currentMalfunction.getIntensity() == MalfunctionIntensity.SEVERE_MALF)
                    productionRate *= 0.50;
                else if (currentMalfunction.getIntensity() == MalfunctionIntensity.MEDIUM_MALF)
                    productionRate *= 0.25;
                else if (currentMalfunction.getIntensity() == MalfunctionIntensity.LOW_MALF)
                    productionRate *= 0.10;
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
        ShelfImpl theShelf = (ShelfImpl) (myShelves.get(index));
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
    }

    private void clearActualFlowRates() {
        Arrays.fill(getPowerConsumerDefinition().getActualFlowRates(), 0f);
        Arrays.fill(getBiomassProducerDefinition().getActualFlowRates(), 0f);
        Arrays.fill(getPotableWaterConsumerDefinition().getActualFlowRates(), 0f);
        Arrays.fill(getGreyWaterConsumerDefinition().getActualFlowRates(), 0f);
        Arrays.fill(getDirtyWaterProducerDefinition().getActualFlowRates(), 0f);
        Arrays.fill(getAirConsumerDefinition().getActualFlowRates(), 0f);
        Arrays.fill(getAirProducerDefinition().getActualFlowRates(), 0f);
    }

    private String arrayToString(float[] pArray) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        for (int i = 0; i < pArray.length; i++)
            buffer.append(pArray[i] + ", ");
        buffer.append("]");
        return buffer.toString();
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
}