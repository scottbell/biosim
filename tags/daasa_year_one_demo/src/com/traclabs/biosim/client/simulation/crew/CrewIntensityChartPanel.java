package com.traclabs.biosim.client.simulation.crew;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;

import com.traclabs.biosim.client.framework.GraphPanel;
import com.traclabs.biosim.client.util.BioHolderInitializer;
import com.traclabs.biosim.idl.simulation.crew.CrewGroup;
import com.traclabs.biosim.idl.simulation.crew.CrewPerson;

/**
 * This is the JPanel that displays a chart about the crew and intensity levels
 * 
 * @author Scott Bell
 */
public class CrewIntensityChartPanel extends GraphPanel {
    private DefaultCategoryDataset myDataset;

	private Collection<CrewPerson> myCrewPeople;

    protected void buildGui() {
    	myCrewPeople = new ArrayList<CrewPerson>();
		for (CrewGroup crewGroup : BioHolderInitializer.getBioHolder().theCrewGroups)
			for (CrewPerson person : crewGroup.getCrewPeople())
				myCrewPeople.add(person);
        if (myCrewPeople.size() == 0) {
            setLayout(new BorderLayout());
            JPanel noCrewPanel = new JPanel();
            noCrewPanel.setLayout(new BorderLayout());
            JLabel noCrewLabel = new JLabel("No crew to display");
            noCrewPanel.add(noCrewLabel, BorderLayout.CENTER);
            add(noCrewPanel, BorderLayout.CENTER);
        } else {
            buildRealGui();
        }
    }

    private void buildRealGui() {
        removeAll();
        createGraph();
        super.buildGui();
        repaint();
        revalidate();
    }

    protected void createGraph() {
        // create the chart...
        myDataset = new DefaultCategoryDataset();
        for (CrewPerson person : myCrewPeople) {
            myDataset.addValue(person.getCurrentActivity().getActivityIntensity(), person.getName(), "");
		}
        JFreeChart myChart = ChartFactory.createBarChart3D(
                "Crew Activity Intensities", // chart title
                "", // domain axis label
                "Intensity", // range axis label
                myDataset, PlotOrientation.VERTICAL, // data
                true, // include legend
                true, false);
        // add the chart to a panel...
        CategoryPlot myPlot = myChart.getCategoryPlot();
        ValueAxis rangeAxis = myPlot.getRangeAxis();
        rangeAxis.setAutoRange(false);
        rangeAxis.setRange(0.0, 5.0);
        TextTitle myTextTitle = (myChart.getTitle());
        myTextTitle.setFont(myTextTitle.getFont().deriveFont(13.0f));
        myChartPanel = new ChartPanel(myChart);
        myChartPanel.setMinimumDrawHeight(250);
        myChartPanel.setMinimumDrawWidth(250);
        myChartPanel.setPreferredSize(new Dimension(200, 200));
    }

    public void refresh() {
        if (myChartPanel == null) {
            int numberOfCrew = myCrewPeople.size();
            if (numberOfCrew > 0) {
                buildRealGui();
            }
        } else {
            for (CrewPerson person : myCrewPeople) {
                myDataset.setValue(person.getCurrentActivity()
                        .getActivityIntensity(),person.getName(), "");
            }
        }
    }
}

