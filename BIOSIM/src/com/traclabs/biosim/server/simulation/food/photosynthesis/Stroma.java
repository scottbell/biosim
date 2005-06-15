/*
 * Created on Jun 8, 2005
 *
 * TODO
 */
package com.traclabs.biosim.server.simulation.food.photosynthesis;

/**
 * @author scott
 *
 * TODO
 */
public class Stroma extends PassiveEnzyme{
    private Chemical myProtons;
    private Chemical myNADPs;
    private Chemical myADPs;
    private Chemical myPhosphates;
    private Chemical myATPs;
    private Chemical myNADPHs;
    
    public void tick(){
        myProtons.update();
        myNADPs.update();
        myADPs.update();
        myPhosphates.update();
        myATPs.update();
    }

    /**
     * @return Returns the myADPs.
     */
    public Chemical getADPs() {
        return myADPs;
    }
    /**
     * @return Returns the myATPs.
     */
    public Chemical getATPs() {
        return myATPs;
    }
    /**
     * @return Returns the myNADPs.
     */
    public Chemical getNADPs() {
        return myNADPs;
    }
    /**
     * @return Returns the myPhosphates.
     */
    public Chemical getPhosphates() {
        return myPhosphates;
    }
    /**
     * @return Returns the myProtons.
     */
    public Chemical getProtons() {
        return myProtons;
    }
    /**
     * @return Returns the myNADPHs.
     */
    public Chemical getNADPHs() {
        return myNADPHs;
    }
}
