package com.traclabs.biosim.client.sensor.framework;

import java.awt.GridLayout;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;

import org.apache.log4j.Logger;

import com.traclabs.biosim.client.framework.TimedPanel;
import com.traclabs.biosim.client.framework.spreadsheet.SpreadSheet;
import com.traclabs.biosim.client.util.BioHolderInitializer;
import com.traclabs.biosim.idl.sensor.framework.GenericSensor;
import com.traclabs.biosim.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class SensorViewer extends TimedPanel
{
	private List<GenericSensor> mySensors;
	//For formatting floats 
	private DecimalFormat myNumFormat;
	private SpreadSheet mySpreadSheet;
	
    private Logger myLogger;
	
	public SensorViewer(){
		myLogger = Logger.getLogger(this.getClass());
        
		myNumFormat = new DecimalFormat("#,##0.00;(#)");
		mySensors = BioHolderInitializer.getBioHolder().theSensors;
		if (mySensors.size() <= 0){
			myLogger.warn("No sensors found!");
			add(new JLabel("No senors found!"));
			return;
		}
		String[] sensorNames = new String[mySensors.size()];
		for (int i = 0; i < mySensors.size(); i++)
			sensorNames[i] = mySensors.get(i).getModuleName();
		mySpreadSheet = new SpreadSheet(new String[]{"value"}, sensorNames);
		setLayout(new GridLayout(1,1));
		add(mySpreadSheet.getScrollPane());
	}
	
	public static void main(String args[]) {
		OrbUtils.initializeLog();
		JFrame myFrame = new JFrame("Sensor Viewer");
		SensorViewer myViewer = new SensorViewer();
		myFrame.getContentPane().add(myViewer);
		myFrame.pack();
		myFrame.setVisible(true);
		myViewer.visibilityChange(true);
	}
	
	public void refresh(){
		for (int i = 0; i < mySensors.size(); i++){
			//myLogger.info("value = " + mySensors.get(i).getValue());
    		mySpreadSheet.setValueAt(myNumFormat.format(mySensors.get(i).getValue()), i, 0);
    	}
	}
}

