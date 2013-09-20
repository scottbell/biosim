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
public class Thylakoid {
    private Lumen myLumen;
    private Membrane myMembrane;
    
    public Thylakoid(Chloroplast pChloroplast, Stroma pStroma){
        myLumen = new Lumen();
        myMembrane = new Membrane(pChloroplast, myLumen, pStroma);
    }

    /**
     * @return
     */
    public Lumen getLumen() {
        return myLumen;
    }

    /**
     * @return
     */
    public Membrane getMembrane() {
        return myMembrane;
    }

	public void reset() {
		myLumen.reset();
		myMembrane.reset();
	}
}
