package biosim.client.sensor.framework.gui;

/**
 * @author    Scott Bell
 */	
import javax.swing.*;
import biosim.client.framework.gui.*;

public class SensorViewer extends TimedPanel
{
	private SensorSheet mySensorSheet;
	
	public SensorViewer(){
		mySensorSheet = new SensorSheet();
		add(mySensorSheet.getScrollPane());
	}

	public static void main(String args[])
	{
		BioFrame myFrame = new BioFrame("Sensor Viewer", false);
		SensorViewer myViewer = new SensorViewer();
		myFrame.getContentPane().add(myViewer);
		myFrame.pack();
		myFrame.setVisible(true);
		myViewer.visibilityChange(true);
	}
	
	public void refresh(){
		mySensorSheet.refresh();
	}
}

