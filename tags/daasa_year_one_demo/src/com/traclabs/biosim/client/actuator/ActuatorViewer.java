package com.traclabs.biosim.client.actuator;

import java.awt.GridLayout;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;

import org.apache.log4j.Logger;

import com.traclabs.biosim.client.framework.TimedPanel;
import com.traclabs.biosim.client.framework.spreadsheet.SpreadSheet;
import com.traclabs.biosim.client.util.BioHolderInitializer;
import com.traclabs.biosim.idl.actuator.framework.GenericActuator;
import com.traclabs.biosim.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class ActuatorViewer extends TimedPanel
{
	private List<GenericActuator> myActuators;
	//For formatting floats 
	private DecimalFormat myNumFormat;
	private SpreadSheet mySpreadSheet;
	
    private Logger myLogger;
	
	public ActuatorViewer(){
		myLogger = Logger.getLogger(this.getClass());
        
		myNumFormat = new DecimalFormat("#,##0.00;(#)");
		myActuators = BioHolderInitializer.getBioHolder().theActuators;
		if (myActuators.size() <= 0){
			myLogger.warn("No actuators found!");
			add(new JLabel("No actuators found!"));
			return;
		}
		String[] actuatorNames = new String[myActuators.size()];
		for (int i = 0; i < myActuators.size(); i++)
			actuatorNames[i] = myActuators.get(i).getModuleName();
		mySpreadSheet = new SpreadSheet(new String[]{"value"}, actuatorNames);
		mySpreadSheet.setEnabled(true);
		mySpreadSheet.setCellSelectionEnabled(true);
		mySpreadSheet.
		setLayout(new GridLayout(1,1));
		add(mySpreadSheet.getScrollPane());
	}
	
	public static void main(String args[]) {
		OrbUtils.initializeLog();
		JFrame myFrame = new JFrame("Actuator Viewer");
		ActuatorViewer myViewer = new ActuatorViewer();
		myFrame.getContentPane().add(myViewer);
		myFrame.pack();
		myFrame.setVisible(true);
		myViewer.visibilityChange(true);
	}
	
	public void refresh(){
		for (int i = 0; i < myActuators.size(); i++){
    		mySpreadSheet.setValueAt(myNumFormat.format(myActuators.get(i).getValue()), i, 0);
    	}
	}
}

