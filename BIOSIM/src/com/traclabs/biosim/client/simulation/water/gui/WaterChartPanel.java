package biosim.client.water.gui;

import java.awt.*;
import javax.swing.*;
import biosim.client.framework.*;
import com.jrefinery.data.*;
import com.jrefinery.chart.*;

/**
 * This is the JPanel that displays a chart about the Water
 *
 * @author    Scott Bell
 */
public class WaterChartPanel extends JPanel {

	/** The data. */
	private CategoryDataset data;
	//Used for registereing this panel (for knowing when a tick occurs)
	private BioSimulator myBioSimulator;

	/**
	 * Default constructor.
	 */
	public WaterChartPanel(BioSimulator pBioSimulator) {
		myBioSimulator = pBioSimulator;
		// create a dataset...
		double[][] data = new double[][] {
		                          { 1.0, 4.0, 3.0, 5.0, 5.0, 7.0, 7.0, 8.0 },
		                          { 5.0, 7.0, 6.0, 8.0, 4.0, 4.0, 2.0, 1.0 },
		                          { 4.0, 3.0, 2.0, 3.0, 6.0, 3.0, 4.0, 3.0 }
		                  };

		DefaultCategoryDataset dataset = new DefaultCategoryDataset(data);

		// set the series names...
		String[] seriesNames = new String[] { "First", "Second", "Third" };
		dataset.setSeriesNames(seriesNames);

		// set the category names...
		String[] categories = new String[] { "Type 1", "Type 2", "Type 3", "Type 4",
		                                     "Type 5", "Type 6", "Type 7", "Type 8"  };
		dataset.setCategories(categories);

		// create the chart...
		JFreeChart chart = ChartFactory.createLineChart("Line Chart Demo 1",  // chart title
		                   "Category",           // domain axis label
		                   "Value",              // range axis label
		                   dataset,              // data
		                   true                  // include legend
		                                               );

		// NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...

		// set the background color for the chart...
		chart.setBackgroundPaint(Color.yellow);

		// get a reference to the plot for further customisation...
		CategoryPlot plot = chart.getCategoryPlot();

		// label data points with values...
		plot.setLabelsVisible(true);

		// set the color for each series...
		plot.setSeriesPaint(new Paint[] { Color.green, Color.orange, Color.red });

		// set the stroke for each series...
		Stroke[] seriesStrokeArray = new Stroke[3];
		seriesStrokeArray[0] = new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
		                                       1.0f, new float[] { 10.0f, 6.0f }, 0.0f);
		seriesStrokeArray[1] = new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
		                                       1.0f, new float[] { 6.0f, 6.0f }, 0.0f);
		seriesStrokeArray[2] = new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
		                                       1.0f, new float[] { 2.0f, 6.0f }, 0.0f);
		plot.setSeriesStroke(seriesStrokeArray);

		// change the auto tick unit selection to integer units only...
		NumberAxis rangeAxis = (NumberAxis)plot.getRangeAxis();
		rangeAxis.setAutoRangeIncludesZero(false);
		rangeAxis.setStandardTickUnits(TickUnits.createIntegerTickUnits());

		HorizontalCategoryAxis domainAxis = (HorizontalCategoryAxis)plot.getDomainAxis();
		domainAxis.setVerticalCategoryLabels(true);
		// OPTIONAL CUSTOMISATION COMPLETED.

		// add the chart to a panel...
		ChartPanel chartPanel = new ChartPanel(chart);
		add(chartPanel);

	}

}
