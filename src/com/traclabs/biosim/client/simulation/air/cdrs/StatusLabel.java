package com.traclabs.biosim.client.simulation.air.cdrs;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import com.traclabs.biosim.client.framework.Updateable;

public abstract class StatusLabel extends JLabel implements Updateable{
	public StatusLabel(){
		setBorder(BorderFactory.createEtchedBorder());
	}
}
