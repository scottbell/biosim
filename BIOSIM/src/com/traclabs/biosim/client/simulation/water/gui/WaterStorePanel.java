package biosim.client.water.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import biosim.client.framework.*;
import biosim.client.framework.gui.*;
import biosim.idl.water.*;
import biosim.idl.environment.*;
import com.jrefinery.chart.*;
import com.jrefinery.data.*;
import com.jrefinery.ui.*;

/**
 * This is the JPanel that displays a chart about the WaterStores
 *
 * @author    Scott Bell
 */
public class WaterStorePanel extends JPanel
{
	private WaterRS myWaterRS;
	private PotableWaterStore myPotableWaterStore;
	private GreyWaterStore myGreyWaterStore;
	private DirtyWaterStore myDirtyWaterStore;
	private SimEnvironment mySimEnvironment;
	private DefaultCategoryDataset myDataset;
	private JButton refreshButton;
	private ChartPanel myChartPanel;

	/**
	 * Default constructor.
	 */
	public WaterStorePanel() {
		myWaterRS = (WaterRS)(BioHolder.getBioModule(BioHolder.waterRSName));
		myPotableWaterStore = (PotableWaterStore)(BioHolder.getBioModule(BioHolder.potableWaterStoreName));
		myDirtyWaterStore = (DirtyWaterStore)(BioHolder.getBioModule(BioHolder.dirtyWaterStoreName));
		myGreyWaterStore = (GreyWaterStore)(BioHolder.getBioModule(BioHolder.greyWaterStoreName));
		mySimEnvironment = (SimEnvironment)(BioHolder.getBioModule(BioHolder.simEnvironmentName));
		createGraph();
		refreshButton = new JButton(new RefreshAction("Refresh"));
		add(myChartPanel, BorderLayout.CENTER);
		add(refreshButton, BorderLayout.NORTH);
	}

	private void createGraph(){
		// create the chart...
		refresh();
		JFreeChart chart = ChartFactory.createVerticalBarChart3D(
		                           "Water Store Levels",  // chart title
		                           "Stores",              // domain axis label
		                           "Water Level",                 // range axis label
		                           myDataset,                 // data
		                           true                     // include legend
		                   );
		// add the chart to a panel...
		CategoryPlot plot = chart.getCategoryPlot();
		plot.setSeriesPaint(new Paint[] { Color.BLUE, Color.GRAY, Color.YELLOW });
		myChartPanel = new ChartPanel(chart);
		myChartPanel.zoomOutBoth(50,50);
	}

	public void refresh() {
		if (myDataset == null){
			double[][] data = { {myPotableWaterStore.getLevel()}, {myGreyWaterStore.getLevel()}, {myDirtyWaterStore.getLevel()}};
			myDataset = new DefaultCategoryDataset(data);
			String[] theSeries = {"Potable Water", "Grey Water", "Dirty Water"};
			String[] theCategory = {""};
			myDataset.setSeriesNames(theSeries);
			myDataset.setCategories(theCategory);
		}
		else{
			myDataset.setValue(0, "", new Float(myPotableWaterStore.getLevel()));
			myDataset.setValue(1, "", new Float(myGreyWaterStore.getLevel()));
			myDataset.setValue(2, "", new Float(myDirtyWaterStore.getLevel()));
		}
	}
	
	/**
	* Action that displays the power panel in an internal frame on the desktop.
	*/
	private class RefreshAction extends AbstractAction{
		public RefreshAction(String name){
			super(name);
		}
		public void actionPerformed(ActionEvent ae){
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			refresh();
			setCursor(Cursor.getDefaultCursor());
		}
	}
}
