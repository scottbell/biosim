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
public class Ferredoxin {
    FNR myFNR;
    /**
     * 
     */
    public void reduce() {
        myFNR.formFerredoxinFNRComplex();
    }

}