/*
 * Created on Jun 8, 2005
 *
 */
package com.traclabs.biosim.server.simulation.food.photosynthesis;


/**
 * @author scott
 *
 */
public class Stroma extends PassiveEnzyme{
    private Chemical myProtons = new Chemical(40);
    private Chemical myNADPs = new Chemical(28);
    private Chemical myADPs = new Chemical(22);
    private Chemical myPhosphates = new Chemical(24);
    private Chemical myATPs = new Chemical(0);
    private Chemical myNADPHs = new Chemical(0);
    
    public void tick(){
        myProtons.update();
        myNADPs.update();
        myADPs.update();
        myPhosphates.update();
        myATPs.update();
        myNADPHs.update();
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
