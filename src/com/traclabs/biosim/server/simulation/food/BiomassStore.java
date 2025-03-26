package com.traclabs.biosim.server.simulation.food;

import com.traclabs.biosim.server.simulation.framework.Store;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * The Biomass Store implementation. Takes raw plant matter from the Biomass RS
 * to be used by the Food Processor.
 * 
 * @author Scott Bell
 */

public class BiomassStore extends Store {
    List<BioMatter> currentBiomassItems;

    BioMatter myOriginalMatter;
    
    public BiomassStore(){
    	this(0, "Unnamed BiomassStore");
    }

    public BiomassStore(int pID, String pName) {
        super(pID, pName);
        currentBiomassItems = new Vector<BioMatter>();
        myOriginalMatter = new BioMatter(0, 0, 0, 0, PlantType.UNKNOWN_PLANT);
    }

    public float add(float pMass) {
        BioMatter newBioMatter = new BioMatter(pMass, 0, 0, 0,
                PlantType.UNKNOWN_PLANT);
        return addBioMatter(newBioMatter);
    }

    public void setInitialLevel(float metricAmount) {
        super.setInitialLevel(metricAmount);
        setCurrentLevel(metricAmount);
    }

    public void setCurrentLevel(float metricAmount) {
        super.setCurrentLevel(metricAmount);
        currentBiomassItems.clear();
        if (metricAmount > 0) {
            BioMatter newBioMatter = new BioMatter(metricAmount, 0, 0, 0,
                    PlantType.UNKNOWN_PLANT);
            currentBiomassItems.add(newBioMatter);
            myOriginalMatter = newBioMatter;
        }
    }

    public void setInitialBioMatterLevel(BioMatter pMatter) {
        super.setInitialLevel(pMatter.mass);
        currentBiomassItems.clear();
        if (pMatter.mass > 0) {
            currentBiomassItems.add(pMatter);
            myOriginalMatter = pMatter;
        }
    }

    public float take(float pMass) {
        BioMatter[] massArray = takeBioMatterMass(pMass);
        float matterToReturn = 0f;
        for (int i = 0; i < massArray.length; i++)
            matterToReturn += massArray[i].mass;
        return matterToReturn;
    }

    private static float calculateWaterContent(BioMatter inMatter) {
        float totalWaterWithinBioMatter = 0f;
        if (inMatter.type == PlantType.DRY_BEAN)
            totalWaterWithinBioMatter = (inMatter.mass
                    * (1 - DryBean.getFractionOfEdibleBiomass()) * DryBean
                    .getInedibleFreshBasisWaterContent())
                    + (inMatter.mass * DryBean.getFractionOfEdibleBiomass() * DryBean
                            .getEdibleFreshBasisWaterContent());
        else if (inMatter.type == PlantType.LETTUCE)
            totalWaterWithinBioMatter = (inMatter.mass
                    * (1 - Lettuce.getFractionOfEdibleBiomass()) * Lettuce
                    .getInedibleFreshBasisWaterContent())
                    + (inMatter.mass * Lettuce.getFractionOfEdibleBiomass() * Lettuce
                            .getEdibleFreshBasisWaterContent());
        else if (inMatter.type == PlantType.PEANUT)
            totalWaterWithinBioMatter = (inMatter.mass
                    * (1 - Peanut.getFractionOfEdibleBiomass()) * Peanut
                    .getInedibleFreshBasisWaterContent())
                    + (inMatter.mass * Peanut.getFractionOfEdibleBiomass() * Peanut
                            .getEdibleFreshBasisWaterContent());
        else if (inMatter.type == PlantType.RICE)
            totalWaterWithinBioMatter = (inMatter.mass
                    * (1 - Rice.getFractionOfEdibleBiomass()) * Rice
                    .getInedibleFreshBasisWaterContent())
                    + (inMatter.mass * Rice.getFractionOfEdibleBiomass() * Rice
                            .getEdibleFreshBasisWaterContent());
        else if (inMatter.type == PlantType.SOYBEAN)
            totalWaterWithinBioMatter = (inMatter.mass
                    * (1 - Soybean.getFractionOfEdibleBiomass()) * Soybean
                    .getInedibleFreshBasisWaterContent())
                    + (inMatter.mass * Soybean.getFractionOfEdibleBiomass() * Soybean
                            .getEdibleFreshBasisWaterContent());
        else if (inMatter.type == PlantType.SWEET_POTATO)
            totalWaterWithinBioMatter = (inMatter.mass
                    * (1 - SweetPotato.getFractionOfEdibleBiomass()) * SweetPotato
                    .getInedibleFreshBasisWaterContent())
                    + (inMatter.mass * SweetPotato.getFractionOfEdibleBiomass() * SweetPotato
                            .getEdibleFreshBasisWaterContent());
        else if (inMatter.type == PlantType.TOMATO)
            totalWaterWithinBioMatter = (inMatter.mass
                    * (1 - Tomato.getFractionOfEdibleBiomass()) * Tomato
                    .getInedibleFreshBasisWaterContent())
                    + (inMatter.mass * Tomato.getFractionOfEdibleBiomass() * Tomato
                            .getEdibleFreshBasisWaterContent());
        else if (inMatter.type == PlantType.WHEAT)
            totalWaterWithinBioMatter = (inMatter.mass
                    * (1 - Wheat.getFractionOfEdibleBiomass()) * Wheat
                    .getInedibleFreshBasisWaterContent())
                    + (inMatter.mass * Wheat.getFractionOfEdibleBiomass() * Wheat
                            .getEdibleFreshBasisWaterContent());
        else if (inMatter.type == PlantType.WHITE_POTATO)
            totalWaterWithinBioMatter = (inMatter.mass
                    * (1 - WhitePotato.getFractionOfEdibleBiomass()) * WhitePotato
                    .getInedibleFreshBasisWaterContent())
                    + (inMatter.mass * WhitePotato.getFractionOfEdibleBiomass() * WhitePotato
                            .getEdibleFreshBasisWaterContent());
        return totalWaterWithinBioMatter;
    }

    public float calculateWaterContentInStore() {
        float totalWater = 0f;
        for (Iterator iter = currentBiomassItems.iterator(); iter.hasNext();) {
            BioMatter currentBioMatter = (BioMatter) iter.next();
            totalWater += calculateWaterContent(currentBioMatter);
        }
        return totalWater;
    }

    public float addBioMatter(BioMatter pMatter) {
        if (pMatter.mass <= 0)
            return 0f;
        myLogger.debug("pMatter.mass trying to be added=" + pMatter.mass);
        float acutallyAdded = 0f;
        if ((pMatter.mass + currentLevel) > currentCapacity) {
            //adding more than currentCapacity
            acutallyAdded = currentCapacity - currentLevel;
            currentLevel += acutallyAdded;
            overflow += (pMatter.mass - acutallyAdded);

            float fractionOfOriginal = acutallyAdded / pMatter.mass;
            BioMatter newBioMatter = new BioMatter(acutallyAdded,
                    pMatter.inedibleFraction, pMatter.edibleWaterContent
                            * fractionOfOriginal, pMatter.inedibleWaterContent
                            * fractionOfOriginal, pMatter.type);
            currentBiomassItems.add(newBioMatter);
            myLogger.debug("added = " + newBioMatter.mass
                    + " with currentLevel @ " + currentLevel);
            return acutallyAdded;
        }
		acutallyAdded = pMatter.mass;
		currentLevel += acutallyAdded;
		currentBiomassItems.add(pMatter);
		myLogger.debug("added = " + pMatter.mass + "with currentLevel @ "
		        + currentLevel);
		return acutallyAdded;
    }

    public BioMatter[] takeBioMatterMass(float pMass) {
        if (pMass <= 0)
            return new BioMatter[0];
        List<BioMatter> itemsToReturn = new Vector<BioMatter>();
        List<BioMatter> itemsToRemove = new Vector<BioMatter>();
        float collectedMass = 0f;
        for (Iterator iter = currentBiomassItems.iterator(); iter.hasNext()
                && (collectedMass <= pMass);) {
            BioMatter currentBioMatter = (BioMatter) (iter.next());
            float massStillNeeded = pMass - collectedMass;
            //we need to get more bio matter
            if (currentBioMatter.mass < massStillNeeded) {
                itemsToReturn.add(currentBioMatter);
                itemsToRemove.add(currentBioMatter);
                collectedMass += currentBioMatter.mass;
            }
            //we have enough, let's cut up the biomass (if too much)
            else if (currentBioMatter.mass >= massStillNeeded) {
                float fractionOfOriginal = massStillNeeded
                        / currentBioMatter.mass;
                BioMatter partialReturnedBioMatter = new BioMatter(
                        massStillNeeded, currentBioMatter.inedibleFraction,
                        currentBioMatter.edibleWaterContent
                                * fractionOfOriginal,
                        currentBioMatter.inedibleWaterContent
                                * fractionOfOriginal, currentBioMatter.type);
                currentBioMatter.mass -= partialReturnedBioMatter.mass;
                itemsToReturn.add(partialReturnedBioMatter);
                if (currentBioMatter.mass <= 0)
                    itemsToRemove.add(currentBioMatter);
                collectedMass += partialReturnedBioMatter.mass;
            }
        }
        //Remove items from List
        for (Iterator iter = itemsToRemove.iterator(); iter.hasNext();) {
            currentBiomassItems.remove(iter.next());
        }
        currentLevel -= collectedMass;
        //return the array
        BioMatter[] emptyArray = new BioMatter[0];
        BioMatter[] returnArray = (itemsToReturn
                .toArray(emptyArray));
        if (returnArray == null)
            return emptyArray;
		return returnArray;
    }

    public void reset() {
        super.reset();
        currentBiomassItems.clear();
        if (currentLevel > 0)
            currentBiomassItems.add(myOriginalMatter);
    }
    
    @Override
    public void log() {
    	super.log();
    	if (myLogger.isDebugEnabled()){
    		myLogger.debug("water content of store=" + calculateWaterContentInStore());
    	}
    }

}