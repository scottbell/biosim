package biosim.client.water.gui;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import biosim.client.framework.*;
import biosim.client.framework.gui.*;
import biosim.idl.water.*;
import biosim.idl.environment.*;
import net.sourceforge.chart2d.*;

/**
 * This is the JPanel that displays a chart about the Water
 *
 * @author    Scott Bell
 */
public class WaterChartPanel extends BioTabPanel implements Runnable
{
	//Used for registereing this panel (for knowing when a tick occurs)
	private BioSimulator myBioSimulator;
	private WaterRS myWaterRS;
	private PotableWaterStore myPotableWaterStore;
	private GreyWaterStore myGreyWaterStore;
	private DirtyWaterStore myDirtyWaterStore;
	private SimEnvironment mySimEnvironment;
	private final static int TICKS_TO_DISPLAY = 20;
	private Dataset myDataset;
	private GraphChart2DProperties graphChart2DProps;
	private int ticksGoneBy = 0;
	private String[] labelsAxisLabels;
	private LBChart2D chart2D;
	//The thread to run the chart
	private Thread myChartUpdateThread;
	private boolean chartNeedsUpdating = false;;

	/**
	 * Default constructor.
	 */
	public WaterChartPanel(BioSimulator pBioSimulator) {
		myBioSimulator = pBioSimulator;
		myWaterRS = (WaterRS)(myBioSimulator.getBioModule(BioSimulator.waterRSName));
		myPotableWaterStore = (PotableWaterStore)(myBioSimulator.getBioModule(BioSimulator.potableWaterStoreName));
		myDirtyWaterStore = (DirtyWaterStore)(myBioSimulator.getBioModule(BioSimulator.dirtyWaterStoreName));
		myGreyWaterStore = (GreyWaterStore)(myBioSimulator.getBioModule(BioSimulator.greyWaterStoreName));
		mySimEnvironment = (SimEnvironment)(myBioSimulator.getBioModule(BioSimulator.simEnvironmentName));
		setLayout(new BorderLayout());
		createGraph();
		add(chart2D, BorderLayout.CENTER);
		myChartUpdateThread = new Thread(this);
		myChartUpdateThread.setPriority(Thread.MIN_PRIORITY);
		myChartUpdateThread.start();
	}

	private Chart2D createGraph() {

		//<-- Begin Chart2D configuration -->

		//Configure object properties
		Object2DProperties object2DProps = new Object2DProperties();
		object2DProps.setObjectTitleText ("Store Water Levels");
		object2DProps.setObjectMagnifyWhenResize (false);

		//Configure chart properties
		Chart2DProperties chart2DProps = new Chart2DProperties();

		//Configure legend properties
		LegendProperties legendProps = new LegendProperties();
		String[] legendLabels = {"Potable Water", "Grey Water", "Dirty Water"};
		legendProps.setLegendLabelsTexts (legendLabels);

		//Configure graph chart properties
		graphChart2DProps = new GraphChart2DProperties();
		labelsAxisLabels = new String[TICKS_TO_DISPLAY];
		int currentTick = mySimEnvironment.getTicks();
		for (int i = 0; i < TICKS_TO_DISPLAY; i ++){
			labelsAxisLabels[i] = Integer.toString(currentTick);
			currentTick++;
		}
		graphChart2DProps.setLabelsAxisLabelsTexts (labelsAxisLabels);
		graphChart2DProps.setLabelsAxisTitleText ("Ticks (hours)");
		graphChart2DProps.setNumbersAxisTitleText ("Water Level (liters)");
		graphChart2DProps.setLabelsAxisTicksAlignment (graphChart2DProps.CENTERED);

		/*
		graphChart2DProps.setChartDatasetCustomizeGreatestValue(true);
		float largestCapacity = myPotableWaterStore.getCapacity();
		if (myDirtyWaterStore.getCapacity() > largestCapacity)
			largestCapacity = myDirtyWaterStore.getCapacity();
		if (myGreyWaterStore.getCapacity() > largestCapacity)
			largestCapacity = myGreyWaterStore.getCapacity();
		graphChart2DProps.setChartDatasetCustomGreatestValue (largestCapacity);
		*/

		//Configure graph properties
		GraphProperties graphProps = new GraphProperties();
		graphProps.setGraphBarsExistence (false);
		graphProps.setGraphLinesExistence (true);
		graphProps.setGraphOutlineComponentsExistence (true);
		graphProps.setGraphAllowComponentAlignment (true);

		//Configure dataset
		myDataset = new Dataset(3, TICKS_TO_DISPLAY, 1);
		myDataset.set(0,  0, 0, myPotableWaterStore.getLevel());
		myDataset.set(1,  0, 0,  myGreyWaterStore.getLevel());
		myDataset.set(2,  0, 0,  myDirtyWaterStore.getLevel());

		//Configure graph component colors
		MultiColorsProperties multiColorsProps = new MultiColorsProperties();

		//Configure chart
		chart2D = new LBChart2D();
		chart2D.setObject2DProperties (object2DProps);
		chart2D.setChart2DProperties (chart2DProps);
		chart2D.setLegendProperties (legendProps);
		chart2D.setGraphChart2DProperties (graphChart2DProps);
		chart2D.addGraphProperties (graphProps);
		chart2D.addDataset (myDataset);
		chart2D.addMultiColorsProperties (multiColorsProps);

		//Optional validation:  Prints debug messages if invalid only.
		if (!chart2D.validate (false)) chart2D.validate (true);

		//<-- End Chart2D configuration -->

		return chart2D;
	}

	public void run(){
		System.out.println("Updater Thread started");
		while (true){
			System.out.println("Checking to see if we need to update...");
			Thread theCurrentThread = Thread.currentThread();
			while (myChartUpdateThread == theCurrentThread) {
				updateChart();
				System.out.println("Updated chart");
				chartNeedsUpdating = false;
				System.out.println("set chartNeedsUpdating to false");
				//Conditional below to speed up things
				if ((!chartNeedsUpdating) && (myChartUpdateThread==theCurrentThread)){
					try {
						synchronized(this) {
							while ((!chartNeedsUpdating) && (myChartUpdateThread==theCurrentThread)){
								wait();
							}
						}
					}
					catch (InterruptedException e){}
				}
			}
		}
	}

	private void updateChart(){
		ticksGoneBy++;
		//Start shifting to the left
		if (ticksGoneBy >= TICKS_TO_DISPLAY){
			//Shift tick labels to the left by one
			for (int i = 0; i < TICKS_TO_DISPLAY; i++){
				int currentLabelInt = Integer.parseInt(labelsAxisLabels[i]);
				currentLabelInt++;
				labelsAxisLabels[i] = Integer.toString(currentLabelInt);
			}
			graphChart2DProps.setLabelsAxisLabelsTexts (labelsAxisLabels);
			chart2D.setGraphChart2DProperties (graphChart2DProps);
			//Shift the dataset by 1
			float[] newData = {myPotableWaterStore.getLevel(), myGreyWaterStore.getLevel(), myDirtyWaterStore.getLevel()};
			myDataset.doShiftLower(newData);
			System.out.println("Displaying tick: "+labelsAxisLabels[0]);
		}
		else{
			myDataset.set(0,  ticksGoneBy, 0, myPotableWaterStore.getLevel());
			myDataset.set(1,  ticksGoneBy, 0,  myGreyWaterStore.getLevel());
			myDataset.set(2,  ticksGoneBy, 0,  myDirtyWaterStore.getLevel());
		}
		chart2D.repaint();
	}

	/**
	 * Updates every label on the panel with new data pulled from the servers.
	 */
	public synchronized void processTick(){
		chartNeedsUpdating = true;
		notify();
		System.out.println("set chartNeedsUpdating to true");
	}
}
