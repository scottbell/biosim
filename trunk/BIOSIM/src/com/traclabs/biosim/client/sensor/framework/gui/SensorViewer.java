package biosim.client.sensor.framework.gui;

/**
 * @author    Scott Bell
 */	
import javax.swing.*;
import biosim.client.framework.gui.*;
 
public class SensorViewer extends BioFrame
{
	public SensorViewer(){
		super("Sensor Viewer", false);
	}
	
	public static void main(String args[])
	{
		SensorViewer myViewer = new SensorViewer();
		myViewer.setSize(320, 130);
		myViewer.setVisible(true);
	}

}

