package biosim.client.simulation.crew.gui;

import java.awt.*;
import biosim.idl.simulation.crew.*;
import biosim.client.simulation.framework.gui.*;
import biosim.client.simulation.framework.*;
import com.jrefinery.chart.*;
import com.jrefinery.data.*;
import com.jrefinery.chart.axis.*;
import com.jrefinery.chart.plot.*;
import com.jrefinery.chart.renderer.*;

/**
 * This is the JPanel that displays a chart about the Crew and air
 *
 * @author    Scott Bell
 */
public class CrewAirChartPanel extends GraphPanel
{
	private CrewGroup myCrewGroup;
	private DefaultCategoryDataset myDataset;

	protected void createGraph(){
		// create the chart...
		myCrewGroup = (CrewGroup)(BioHolder.getBioModule(BioHolder.crewName));
		refresh();
		JFreeChart myChart = ChartFactory.createVerticalBarChart3D(
		                  "O2/CO2 Consumed/Produced",  // chart title
		                  "",              // domain axis label
		                  "Liters",                 // range axis label
		                  myDataset,                 // data
		                  true,                     // include legend
				  true,
				  false
		          );
		// add the chart to a panel...
		CategoryPlot myPlot = myChart.getCategoryPlot();
		ValueAxis rangeAxis = myPlot.getRangeAxis();
		rangeAxis.setAutoRange(false);
		rangeAxis.setRange(0.0, 500.00);
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
			String series1 = "O2 Consumed";
			String series2 = "CO2 Produced";
			String category = "";
			myDataset.addValue(myCrewGroup.getO2Consumed(),series1, category);
			myDataset.addValue(myCrewGroup.getCO2Produced(), series2, category);
		}
		else{
			myDataset.addValue(myCrewGroup.getO2Consumed(),"O2 Consumed", "");
			myDataset.addValue(myCrewGroup.getCO2Produced(), "CO2 Produced", "");
		}
	}
}
