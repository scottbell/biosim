package biosim.client.environment.gui;

import biosim.client.framework.*;
import biosim.idl.environment.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.*;

public class EnvironmentPanel extends JPanel implements BioSimulatorListener
{
	private JPanel tickPanel;
	private JLabel tickLabel;
	private JLabel O2Label;
	private JLabel CO2Label;
	private JLabel otherLabel;
	private JPanel airPanel;
	private SimEnvironment mySimEnvironment;
	private BioSimulator myBioSimulator;
	
	public EnvironmentPanel(BioSimulator pBioSimulator){
		myBioSimulator = pBioSimulator;
		myBioSimulator.registerListener(this);
		mySimEnvironment = (SimEnvironment)(myBioSimulator.getBioModule(BioSimulator.simEnvironmentName));
		buildGui();
	}
	
	private void buildGui(){
		setLayout(new BorderLayout());
		tickPanel = new JPanel();
		tickLabel = new JLabel(""+mySimEnvironment.getTicks());
		tickPanel.add(tickLabel);
		tickPanel.setBorder(BorderFactory.createTitledBorder("Ticks"));
		airPanel = new JPanel();
		airPanel.setLayout(new BorderLayout());
		airPanel.setBorder(BorderFactory.createTitledBorder("Air"));
		O2Label = new JLabel("O2: "+mySimEnvironment.getO2Level() +" L");
		CO2Label = new JLabel("CO2: "+mySimEnvironment.getCO2Level() + " L");
		otherLabel = new JLabel("other: "+mySimEnvironment.getOtherLevel() + " L");
		airPanel.add(O2Label, BorderLayout.WEST);
		airPanel.add(otherLabel, BorderLayout.NORTH);
		airPanel.add(CO2Label, BorderLayout.EAST);
		add(airPanel, BorderLayout.SOUTH);
		add(tickPanel, BorderLayout.NORTH);
	}
	
	public void processTick(){
		tickLabel.setText(""+mySimEnvironment.getTicks());
		O2Label.setText("O2: "+mySimEnvironment.getO2Level() +" L");
		CO2Label.setText("CO2: "+mySimEnvironment.getCO2Level() + " L");
		otherLabel.setText("other: "+mySimEnvironment.getOtherLevel() + " L");
	}
}
