package biosim.client.simulation.environment.gui;

import java.awt.*;
import biosim.idl.simulation.environment.*;
import biosim.client.framework.gui.*;
import biosim.client.simulation.framework.*;
import com.jrefinery.chart.*;
import com.jrefinery.data.*;
import com.jrefinery.chart.axis.*;
import com.jrefinery.chart.plot.*;
import com.jrefinery.chart.renderer.*;

/**
 * This is the JPanel that displays a chart about the Environment
 *
 * @author    Scott Bell
 */
public class EnvironmentPieChartPanel extends GraphPanel
{
	private SimEnvironment mySimEnvironment;
	private Pie3DPlot myPlot;
	private JFreeChart myChart;
	private DefaultPieDataset myDataset;
	private String O2Category = "O2";
	private String CO2Category = "CO2";
	private String otherCategory = "Other";
	private String vacuumCategory = "Vacuum";
	private boolean isVacuum = false;

	public EnvironmentPieChartPanel(String pEnvironmentName){
		super(pEnvironmentName);
	}

	protected void createGraph(){
		// create the chart...
		refresh();
		String titleText = "Environment";
		if (mySimEnvironment.getModuleName().startsWith(BioHolder.crewEnvironmentName))
			titleText = "Crew Environment";
		else if (mySimEnvironment.getModuleName().startsWith(BioHolder.plantEnvironmentName))
			titleText = "Plant Environment";
		myChart = ChartFactory.createPie3DChart(titleText, myDataset, true, true, false);
		myPlot = (Pie3DPlot)(myChart.getPlot());
		myPlot.setDepthFactor(0.1d);
		initDataset();
		TextTitle myTextTitle = (TextTitle)(myChart.getTitle());
		myTextTitle.setFont(myTextTitle.getFont().deriveFont(13.0f));
		myChartPanel = new ChartPanel(myChart);
		myChartPanel.setMinimumDrawHeight(250);
		myChartPanel.setMinimumDrawWidth(250);
		myChartPanel.setPreferredSize(new Dimension(250, 250));
	}
	
	protected void initializeDataSources(String dataSourceName){
		mySimEnvironment = (SimEnvironment)(BioHolder.getBioModule(dataSourceName));
	}

	public void refresh() {
		if (myDataset == null){
			myDataset = new DefaultPieDataset();
		}
		else if ((mySimEnvironment.getO2Level() <= 0) && (mySimEnvironment.getCO2Level() <= 0) && (mySimEnvironment.getOtherLevel() <= 0)){
			//It isn't in a vacuum, set it up
			if (!isVacuum){
				myDataset = new DefaultPieDataset();
				myPlot.setDataset(myDataset);
				myDataset.setValue(vacuumCategory, new Float(1f));
				myPlot.setPaint(0, Color.DARK_GRAY);
				isVacuum = true;
			}
		}
		else{
			//It in a vacuum, set it up for normal use
			if (isVacuum){
				myDataset = new DefaultPieDataset();
				myPlot.setDataset(myDataset);
				myPlot.setPaint(0, Color.BLUE);
				myPlot.setPaint(1, Color.GREEN);
				myPlot.setPaint(2, Color.RED);
				isVacuum = false;
			}
			myDataset.setValue(O2Category, new Float(mySimEnvironment.getO2Level()));
			myDataset.setValue(CO2Category, new Float(mySimEnvironment.getCO2Level()));
			myDataset.setValue(otherCategory, new Float(mySimEnvironment.getOtherLevel()));
		}
	}

	private void initDataset(){
		if (mySimEnvironment == null)
			System.err.println("EnvironmentPieChartPanel: mySimEnvironment is null!");
		if ((mySimEnvironment.getO2Level() <= 0) && (mySimEnvironment.getCO2Level() <= 0) && (mySimEnvironment.getOtherLevel() <= 0)){
			myDataset.setValue(vacuumCategory, new Float(1f));
			myPlot.setPaint(0, Color.DARK_GRAY);
			isVacuum = true;
		}
		else{
			myPlot.setPaint(0, Color.BLUE);
			myPlot.setPaint(1, Color.GREEN);
			myPlot.setPaint(2, Color.RED);
			isVacuum = false;
		}
	}
}
