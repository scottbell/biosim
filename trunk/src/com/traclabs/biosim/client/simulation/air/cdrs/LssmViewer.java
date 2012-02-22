package com.traclabs.biosim.client.simulation.air.cdrs;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.WindowConstants;

import org.apache.log4j.Logger;

import com.traclabs.biosim.client.framework.BioFrame;
import com.traclabs.biosim.client.framework.TimedPanel;
import com.traclabs.biosim.client.util.BioHolder;
import com.traclabs.biosim.client.util.BioHolderInitializer;
import com.traclabs.biosim.idl.simulation.air.cdrs.CDRSArmedStatus;
import com.traclabs.biosim.idl.simulation.air.cdrs.CDRSDayNightState;
import com.traclabs.biosim.idl.simulation.air.cdrs.CDRSModule;
import com.traclabs.biosim.idl.simulation.air.cdrs.CDRSPowerState;
import com.traclabs.biosim.idl.simulation.air.cdrs.CDRSState;
import com.traclabs.biosim.idl.simulation.air.cdrs.CDRSValveState;
import com.traclabs.biosim.util.OrbUtils;

public class LssmViewer extends TimedPanel {
	private Logger myLogger;
	private BioHolder myBioHolder;
	private LssmPanel myLSSMPanel = new LssmPanel();

	public LssmViewer() {
		myLogger = Logger.getLogger(this.getClass());
		myBioHolder = BioHolderInitializer.getBioHolder();
		setLayout(new BorderLayout());
		add(myLSSMPanel, BorderLayout.CENTER);
	}

	public static void main(String[] strings) {
		OrbUtils.initializeLog();
		LssmViewer newViewer = new LssmViewer();
		BioFrame myFrame = new BioFrame("LSSM", false);
		myFrame.getContentPane().add(newViewer);
		myFrame.pack();
		myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		ImageIcon airIcon = new ImageIcon(LssmViewer.class
				.getClassLoader().getResource("com/traclabs/biosim/client/air/air.png"));
		myFrame.setIconImage(airIcon.getImage());
		myFrame.setVisible(true);
		newViewer.visibilityChange(true);
	}

	@Override
	public void refresh() {
		myLSSMPanel.refresh();
	}
	
	public static CDRSModule getCDRSModule(){
		return BioHolderInitializer.getBioHolder().theCDRSModules.get(0);
	}
	
	public static String getArmedStatus(CDRSArmedStatus armedStatus) {
		if (armedStatus == CDRSArmedStatus.armed)
			return "armed";
		else if (armedStatus == CDRSArmedStatus.not_armed)
			return "not armed";
		else if (armedStatus == CDRSArmedStatus.in_progress)
			return "in progress";
		return "?";
	}
	
	public static  String getDayNight(CDRSDayNightState state) {
		if (state == CDRSDayNightState.day)
			return "day";
		else if (state == CDRSDayNightState.night)
			return "night";
		return "?";
	}

	public static  String getCDRSState(CDRSState state) {
		if (state == CDRSState.dual_bed)
			return "dual bed";
		else if (state == CDRSState.inactive)
			return "inactive";
		else if (state == CDRSState.single_bed)
			return "single bed";
		else if (state == CDRSState.init)
			return "init";
		else if (state == CDRSState.standby)
			return "standby";
		else if (state == CDRSState.transitioning)
			return "transitioning";
		return "?";
	}
	
	public static String getPowerState(CDRSPowerState powerState) {
		if (powerState == CDRSPowerState.off)
			return "off";
		else if (powerState == CDRSPowerState.on)
			return "on";
		return "?";
	}

	public static String getCDRSValveState(CDRSValveState valveState) {
		if (valveState == CDRSValveState.open)
			return "open";
		else if (valveState == CDRSValveState.closed)
			return "closed";
		return "?";
	}

	public static void sendCommand(String commandName) {
		getCDRSModule().notifyCommandSent(commandName);
			
	}

}
