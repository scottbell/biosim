package biosim.client.simulation.air.gui;

import biosim.idl.simulation.air.*;
import biosim.client.simulation.framework.gui.*;
import biosim.client.simulation.framework.*;
import java.awt.*;
import com.jrefinery.chart.*;
import com.jrefinery.data.*;
import com.jrefinery.chart.axis.*;
import com.jrefinery.chart.plot.*;
import com.jrefinery.chart.renderer.*;

/**
 * This is the JPanel that displays a chart about the Air Stores
 *
 * @author    Scott Bell
 */
public class AirStorePanel extends GraphPanel
{
	private O2Store myO2Store;
	private CO2Store myCO2Store;
	private DefaultCategoryDataset myDataset;
	private ValueAxis rangeAxis;

	protected void createGraph(){
		// create the chart...
		myO2Store = (O2Store)(BioHolder.getBioModule(BioHolder.O2StoreName));
		myCO2Store = (CO2Store)(BioHolder.getBioModule(BioHolder.CO2StoreName));
		refresh();
		JFreeChart myChart = ChartFactory.createVerticalBarChart3D(
		                  "Air Store Levels",  // chart title
		                  "Stores",              // domain axis label
		                  "Air Level (L)",                 // range axis label
		                  myDataset,                 // data
		                  true,                     // include legend
				  true, //tooltips cool
				  false
		          );
		// add the chart to a panel...
		CategoryPlot myPlot = myChart.getCategoryPlot();
		rangeAxis = myPlot.getRangeAxis();
		rangeAxis.setAutoRange(false);
		rangeAxis.setRange(0.0, myO2Store.getCapacity());
		Renderer renderer = myPlot.getRenderer();
		renderer.setSeriesPaint(0, Color.BLUE);
		renderer.setSeriesPaint(1, Color.GREEN);
		TextTitle myTextTitle = (TextTitle)(myChart.getTitle());
		myTextTitle.setFont(myTextTitle.getFont().deriveFont(13.0f));
		myChartPanel = new ChartPanel(myChart);
		myChartPanel.setMinimumDrawHeight(250);
		myChartPanel.setMinimumDrawWidth(250);
		myChartPanel.setPreferredSize(new Dimension(200, 200));
	}

	public void refresh() {
		if (myDataset == null){
			myDataset = new DefaultCategoryDataset();
			String series1 = "O2";
			String series2 = "CO2";
			String category = "";
			myDataset.addValue(myO2Store.getLevel(),series1, category);
			myDataset.addValue(myCO2Store.getLevel(), series2, category);
		}
		else{
			float capacity = Math.max(myO2Store.getCapacity(), myCO2Store.getCapacity());
			if ((rangeAxis.getRange().getUpperBound() != capacity) && (capacity > 0)){
				rangeAxis.setRange(0.0, capacity);
				myChartPanel.repaint();
			}
			myDataset.setValue(new Float(myO2Store.getLevel()), "O2", "");
			myDataset.setValue(new Float(myCO2Store.getLevel()), "CO2", "");
		}
	}

}
