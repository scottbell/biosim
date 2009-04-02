package com.traclabs.biosim.server.framework;

import com.traclabs.biosim.idl.framework.MalfunctionIntensity;
import com.traclabs.biosim.idl.framework.MalfunctionLength;
import com.traclabs.biosim.idl.framework.MalfunctionPOA;

/**
 * Malfunctions occur to BioModules and introduce interesting faults for a
 * controller to deal with. <br>
 * Once they're started, crew members can be assigned to fix them using the
 * repair() method. After <br>
 * repair is called a sufficient number of times (varying with the severity of
 * the malfunction), the malfunction <br>
 * is "fixed" and the BioModule resumes to normal behavior. <br>
 * <br>
 * Malfunctions are broken down into in accordance to temporal length (permanent
 * or temporary) and intensity (low, medium, severe). <br>
 * <br>
 * Each BioModule responds to malfunctions in different ways. For example, the
 * PotableWaterStore could have a temporary severe <br>
 * malfunction which means a "severe leak." The WaterRS could also have a
 * temporary severe malfunction which means the <br>
 * RO and the AES are temporarily offline. <br>
 * <br>
 * Modules may have multiple malfunctions at the same time. Each malfunction has
 * a unique ID associated with it. This ID <br>
 * is used to fix the module. <br>
 * 
 * @author Scott Bell
 */

public class MalfunctionImpl extends MalfunctionPOA {
    //The last ID of the malfunction created
    private static long lastID = 0;

    //My unique ID
    private long myID;

    //The intensity of this malfunction (low, medium, severe)
    private MalfunctionIntensity myIntensity;

    //The temporal length of this malfunction (permanent, temporary)
    private MalfunctionLength myLength;

    //The name of this malfunction. Essentially a description
    private String myName;

    //Whether this malfunction has been performed or activated. Useful for
    // malfunctions that only happen once (like crew death)
    private boolean performed;

    //How many time this malfunction has been repaired
    private int repairTime = 0;

    //How many times it takes to repair this module
    private float maxRepairTime = 1;

    //When this malfunction should occur
    private int tickToMalfunction = 0;

    /**
     * Creates a malfunction with a unique ID, sets up repair time, etc.
     * 
     * @param pName
     *            The name of the malfunction
     * @param pIntensity
     *            The intensity of the malfunction: <br>
     *            &nbsp;&nbsp;&nbsp;
     *            <code>MalfunctionIntensity.SEVERE_MALF</code> for severe
     *            intensity <br>
     *            &nbsp;&nbsp;&nbsp;
     *            <code>MalfunctionIntensity.MEDIUM_MALF</code> for medium
     *            intensity <br>
     *            &nbsp;&nbsp;&nbsp; <code>MalfunctionIntensity.LOW_MALF</code>
     *            for low intensity
     * @param pLength
     *            The temporal length of the malfunction: <br>
     *            &nbsp;&nbsp;&nbsp;
     *            <code>MalfunctionLength.TEMPORARY_MALF</code> for temporaray
     *            malfunctions <br>
     *            &nbsp;&nbsp;&nbsp;
     *            <code>MalfunctionIntensity.PERMANENT_MALF</code> for
     *            permanent malfunctions
     */
    public MalfunctionImpl(String pName, MalfunctionIntensity pIntensity,
            MalfunctionLength pLength, float tickInterval) {
        if (lastID > java.lang.Long.MAX_VALUE)
            lastID = 0;
        lastID++;
        myID = lastID;
        myName = pName;
        myIntensity = pIntensity;
        myLength = pLength;
        if (myLength == MalfunctionLength.TEMPORARY_MALF)
            if (myIntensity == MalfunctionIntensity.LOW_MALF)
                maxRepairTime = 1f * tickInterval;
            else if (myIntensity == MalfunctionIntensity.MEDIUM_MALF)
                maxRepairTime = 2f * tickInterval;
        if (myIntensity == MalfunctionIntensity.SEVERE_MALF)
            maxRepairTime = 4f * tickInterval;
    }

    /**
     * Tells the unique ID of this malfunction
     * 
     * @return the unique ID of this malfunction
     */
    public long getID() {
        return myID;
    }

    public void setTickToMalfunction(int pTick) {
        tickToMalfunction = pTick;
    }

    public int getTickToMalfunction() {
        return tickToMalfunction;
    }

    /**
     * Repairs this module once. Must be called several times depending on the
     * severity of the malfunction to fix the malfunction.
     */
    public void doSomeRepairWork() {
        if (myLength == MalfunctionLength.TEMPORARY_MALF)
            repairTime++;
    }

    /**
     * Tells whether <code>repair()</code> has been called enough to fix the
     * malfunction
     * 
     * @return whether this malfunction has been repaired
     */
    public boolean doneEnoughRepairWork() {
        return (repairTime >= maxRepairTime);
    }

    /**
     * Tells whether this malfunction has occured. Useful for malfunctions that
     * act once but have a constant effect (like crew sickness)
     * 
     * @return whether this malfunction has been performed
     */
    public boolean hasPerformed() {
        return performed;
    }

    /**
     * Sets whether this malfunction has occured. Useful for malfunctions that
     * act once but have a constant effect (like crew sickness)
     * 
     * @param pPerformed
     *            whether this malfunction has occured
     */
    public void setPerformed(boolean pPerformed) {
        performed = pPerformed;
    }

    /**
     * Gives the name of this malfunction (or description)
     * 
     * @return a name/description of this malfunction
     */
    public String getName() {
        return myName;
    }

    /**
     * Tells the intensity of the malfunction
     * 
     * @return The intensity of the malfunction: <br>
     *         &nbsp;&nbsp;&nbsp; <code>MalfunctionIntensity.SEVERE_MALF</code>
     *         for severe intensity <br>
     *         &nbsp;&nbsp;&nbsp; <code>MalfunctionIntensity.MEDIUM_MALF</code>
     *         for medium intensity <br>
     *         &nbsp;&nbsp;&nbsp; <code>MalfunctionIntensity.LOW_MALF</code>
     *         for low intensity <br>
     */
    public MalfunctionIntensity getIntensity() {
        return myIntensity;
    }

    /**
     * Tells the temporal length of the malfunction
     * 
     * @return pLength The temporal length of the malfunction: <br>
     *         &nbsp;&nbsp;&nbsp; <code>MalfunctionLength.TEMPORARY_MALF</code>
     *         for temporaray malfunctions <br>
     *         &nbsp;&nbsp;&nbsp;
     *         <code>MalfunctionIntensity.PERMANENT_MALF</code> for permanent
     *         malfunctions <br>
     */
    public MalfunctionLength getLength() {
        return myLength;
    }
    
    public void reset(){
    	performed = false;
    	repairTime = 0;
    }
}