package com.traclabs.biosim.server.framework;

import com.traclabs.biosim.server.simulation.crew.CrewGroup;
import com.traclabs.biosim.server.simulation.food.BiomassPS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/*
 *
 * @author Scott Bell
 */

public class BioDriver {
    // The ID of this instance of BioSim
    private final int myID;
    private final Logger myLogger;
    private final Map<String, IBioModule> myModuleMap = new HashMap<String, IBioModule>();
    // The thread to run the simulation
    private Thread myTickThread;
    // Flag to see whether the BioDriver is paused (started but not ticking)
    private boolean simulationIsPaused = false;
    // Flag to see whether the BioDriver is started at all
    private boolean simulationStarted = false;
    // If <runTillN == true, this is the number of ticks to run for.
    private int nTicks = -1;
    // The number of ticks gone by
    private int ticksGoneBy;
    // Tells whether simulation runs until crew death
    private boolean runTillCrewDeath = false;
    // Tells whether simulation runs until plant death
    private boolean runTillPlantDeath = false;
    // Tells whether simulation runs till a fixed number of ticks
    // If <runTillN == true, this is the number of ticks to run for.
    private boolean runTillN = false;
    // How long BioDriver should pause between ticks
    private int myDriverStutterLength;
    private boolean exitWhenFinished = false;
    // If we loop after end conditions of a simulation run have been met (crew
    // death or n-ticks)
    private boolean looping = false;
    private CrewGroup[] crewsToWatch;

    private BiomassPS[] plantsToWatch;

    private IBioModule[] modules;

    private IBioModule[] activeSimModules;

    private IBioModule[] passiveSimModules;

    private IBioModule[] prioritySimModules;

    private IBioModule[] sensors;

    private IBioModule[] actuators;

    private float myTickLength = 1f;

    /**
     * Constructs the BioDriver
     *
     * @param pID The ID of this instance of the BioSim (must be the same for
     *            all modules in the instance)
     */
    public BioDriver(int pID) {
        myID = pID;
        modules = new IBioModule[0];
        activeSimModules = new IBioModule[0];
        passiveSimModules = new IBioModule[0];
        prioritySimModules = new IBioModule[0];
        sensors = new IBioModule[0];
        actuators = new IBioModule[0];
        crewsToWatch = new CrewGroup[0];
        plantsToWatch = new BiomassPS[0];
        myLogger = LoggerFactory.getLogger(BioDriver.class);
    }

    /**
     * Starts the simulation
     */
    public void startSimulation() {
        reset();
        myTickThread = new Thread(new Ticker(), "Biosim Tick Thread");
        myTickThread.start();
    }

    /**
     * Returns the name of this instance of BioDriver
     *
     * @return The name of this instance (BioDriver + ID)
     */
    public String getName() {
        return "BioDriver";
    }

    /**
     * Checks to see if the simulation is paused.
     *
     * @return <code>true</code> if paused, <code>false</code> if not
     */
    public boolean isPaused() {
        return simulationIsPaused;
    }

    /**
     * Checks to see if the simulation has started.
     *
     * @return <code>true</code> if started, <code>false</code> if not
     */
    public boolean isStarted() {
        return simulationStarted;
    }

    /**
     * Tells The ID of this module. Should be the same as every other module in
     * this BioSim instance
     *
     * @return The ID of this module. Should be the same as every other module
     * in this BioSim instance
     */
    public int getID() {
        return myID;
    }

    /**
     * Simulation runs till all the crew dies if true.
     */
    public synchronized void setRunTillCrewDeath(boolean pRunTillDead) {
        runTillCrewDeath = pRunTillDead;
    }

    public synchronized void setExitWhenFinished(boolean exitWhenFinished) {
        this.exitWhenFinished = exitWhenFinished;
    }


    /**
     * Simulation runs till all the crew dies if true.
     */
    public synchronized void setRunTillPlantDeath(boolean pRunTillDead) {
        runTillPlantDeath = pRunTillDead;
    }

    public IBioModule[] getModules() {
        return modules;
    }

    public void setModules(IBioModule[] pModules) {
        modules = pModules;
        for (IBioModule module : pModules) {
            myModuleMap.put(module.getModuleName(), module);
        }
    }

    public IBioModule[] getSensors() {
        return sensors;
    }

    public void setSensors(IBioModule[] pSensors) {
        sensors = pSensors;
    }

    public IBioModule[] getActiveSimModules() {
        return activeSimModules;
    }

    public void setActiveSimModules(IBioModule[] pSimModules) {
        activeSimModules = pSimModules;
    }

    public IBioModule[] getPassiveSimModules() {
        return passiveSimModules;
    }

    public void setPassiveSimModules(IBioModule[] pSimModules) {
        passiveSimModules = pSimModules;
    }

    public IBioModule[] getPrioritySimModules() {
        return prioritySimModules;
    }

    public void setPrioritySimModules(IBioModule[] pSimModules) {
        prioritySimModules = pSimModules;
    }

    public IBioModule[] getActuators() {
        return actuators;
    }

    public void setActuators(IBioModule[] pActuators) {
        actuators = pActuators;
    }

    public IBioModule[] getSimModules() {
        IBioModule[] simModules = new IBioModule[activeSimModules.length
                + passiveSimModules.length + prioritySimModules.length];
        System.arraycopy(activeSimModules, 0, simModules, 0, activeSimModules.length);
        System.arraycopy(passiveSimModules, 0, simModules, activeSimModules.length, passiveSimModules.length);
        for (int i = 0; i < prioritySimModules.length; i++)
            simModules[i + activeSimModules.length + passiveSimModules.length] = prioritySimModules[i];
        return simModules;
    }

    public String[] getModuleNames() {
        String[] moduleNameArray = new String[modules.length];
        for (int i = 0; i < moduleNameArray.length; i++)
            moduleNameArray[i] = modules[i].getModuleName();
        return moduleNameArray;
    }

    public String[] getSensorNames() {
        String[] sensorNameArray = new String[sensors.length];
        for (int i = 0; i < sensorNameArray.length; i++)
            sensorNameArray[i] = sensors[i].getModuleName();
        return sensorNameArray;
    }

    public String[] getActuatorNames() {
        String[] actuatorNameArray = new String[actuators.length];
        for (int i = 0; i < actuatorNameArray.length; i++)
            actuatorNameArray[i] = actuators[i].getModuleName();
        return actuatorNameArray;
    }

    public String[] getActiveSimModuleNames() {
        String[] simModuleNameArray = new String[activeSimModules.length];
        for (int i = 0; i < simModuleNameArray.length; i++)
            simModuleNameArray[i] = activeSimModules[i].getModuleName();
        return simModuleNameArray;
    }

    public String[] getPassiveSimModuleNames() {
        String[] simModuleNameArray = new String[passiveSimModules.length];
        for (int i = 0; i < simModuleNameArray.length; i++)
            simModuleNameArray[i] = passiveSimModules[i].getModuleName();
        return simModuleNameArray;
    }

    public String[] getPrioritySimModuleNames() {
        String[] simModuleNameArray = new String[prioritySimModules.length];
        for (int i = 0; i < simModuleNameArray.length; i++)
            simModuleNameArray[i] = prioritySimModules[i].getModuleName();
        return simModuleNameArray;
    }

    public String[] getSimModuleNames() {
        IBioModule[] simModules = getSimModules();
        String[] simModuleNameArray = new String[simModules.length];
        for (int i = 0; i < simModules.length; i++)
            simModuleNameArray[i] = simModules[i].getModuleName();
        return simModuleNameArray;
    }

    public void setCrewsToWatch(CrewGroup[] pCrewGroups) {
        crewsToWatch = pCrewGroups;
    }

    public void setPlantsToWatch(BiomassPS[] pPlants) {
        plantsToWatch = pPlants;
    }

    /**
     * Simulation runs till n ticks.
     *
     * @param pTicks ticks to run simulation
     */
    public synchronized void setRunTillN(int pTicks) {
        nTicks = pTicks;
        if (nTicks > 0)
            runTillN = true;
    }

    /**
     * If n-ticks have been reached or the crew is dead, the simulation restarts
     */
    public synchronized void setLoopSimulation(boolean pLooping) {
        looping = pLooping;
    }

    public synchronized void setPauseSimulation(boolean pPaused) {
        simulationIsPaused = pPaused;
        if (!simulationIsPaused)
            notify();
    }

    /**
     * Tells how long the simulation pauses between full simulation ticks.
     *
     * @return How long the simulation pauses between full simulation ticks.
     */
    public int getDriverStutterLength() {
        return myDriverStutterLength;
    }

    /**
     * Sets how long BioDriver should pause between full simulation ticks (e.g.,
     * tick all modules, wait, tick all modules, wait, etc.)
     *
     * @param pDriverStutterLength the length (in milliseconds) for the driver to pause between
     *                             ticks
     */
    public synchronized void setDriverStutterLength(int pDriverStutterLength) {
        if (pDriverStutterLength > 0)
            myLogger.debug("BioDriver" + myID + ": driver pause of "
                    + pDriverStutterLength + " milliseconds");
        myDriverStutterLength = pDriverStutterLength;
    }

    /**
     * Tells whether BioDriver is looping the simulation after end conditions
     * have been met.
     *
     * @return Whether BioDriver is looping the simulation after end conditions
     * have been met.
     */
    public boolean isLooping() {
        return looping;
    }

    /**
     * Tells whether BioDriver should loop the simulation after end conditions
     * have been met.
     *
     * @param pLoop Whether BioDriver should loop the simulation after end
     *              conditions have been met.
     */
    public synchronized void setLooping(boolean pLoop) {
        looping = pLoop;
    }

    /**
     * Tells whether the simulation has met an end condition and has stopped.
     *
     * @return Whether the simulation has met an end condition and has stopped.
     */
    public boolean isDone() {
        if (runTillN) {
            if (ticksGoneBy >= nTicks) {
                myLogger.info("BioDriver" + myID
                        + ": Reached user defined tick limit of " + nTicks);
                return true;
            }
        }
        if (runTillCrewDeath) {
            for (int i = 0; i < crewsToWatch.length; i++) {
                if (crewsToWatch[i].anyDead()) {
                    myLogger.info("BioDriver" + myID
                            + ": simulation ended due to crew death at "
                            + nTicks);
                    return true;
                }
            }
        }
        if (runTillPlantDeath) {
            for (int i = 0; i < plantsToWatch.length; i++) {
                if (plantsToWatch[i].isAnyPlantDead()) {
                    myLogger.info("BioDriver" + myID
                            + ": simulation ended due to plant death at "
                            + nTicks);
                    return true;

                }
            }
        }
        return false;
    }

    /**
     * Tells the number of times BioDriver has ticked the simulation
     *
     * @return The number of times BioDriver has ticked the simulation
     */
    public int getTicks() {
        return ticksGoneBy;
    }

    /**
     * Ends the simulation entirely.
     */
    public synchronized void endSimulation() {
        myTickThread = null;
        notify();
        simulationStarted = false;
        myLogger.info("BioDriver" + myID + ": simulation ended on tick "
                + ticksGoneBy);
        if (exitWhenFinished)
            System.exit(0);
    }

    /**
     * Advances the simulation once tick. NOTICE: not pausing the simulation
     * before using this method can be very risky. Don't do it.
     */
    public synchronized void advanceOneTick() {
        if (!simulationIsPaused)
            setPauseSimulation(true);
        tick();
    }

    /**
     * Starts a malfunction on every module
     *
     * @param pIntensity The intensity of the malfunction <br>
     *                   Options are: <br>
     *                   &nbsp;&nbsp;&nbsp;
     *                   <code>MalfunctionIntensity.SEVERE_MALF</code><br>
     *                   &nbsp;&nbsp;&nbsp;
     *                   <code>MalfunctionIntensity.MEDIUM_MALF</code><br>
     *                   &nbsp;&nbsp;&nbsp; <code>MalfunctionIntensity.LOW_MALF</code>
     *                   <br>
     * @param pLength    The length (time-wise) of the malfunction <br>
     *                   Options are: <br>
     *                   &nbsp;&nbsp;&nbsp;
     *                   <code>MalfunctionLength.TEMPORARY_MALF</code><br>
     *                   &nbsp;&nbsp;&nbsp;
     *                   <code>MalfunctionLength.PERMANENT_MALF</code><br>
     */
    public void startMalfunction(MalfunctionIntensity pIntensity,
                                 MalfunctionLength pLength) {
        for (int i = 0; i < modules.length; i++) {
            IBioModule currentBioModule = (modules[i]);
            currentBioModule.startMalfunction(pIntensity, pLength);
        }
    }

	private String getTicksInHumanReadableFormat() {
		// ticks are 1 hour by default, but need to multiply by tick length
		// to get actual hours
		float hours = ticksGoneBy * getTickLength();
		int days = (int) (hours / 24);
		hours = hours % 24;
		return days + " days, " + hours + " hours";
	}

    /**
     * Resets the simulation by calling every known server's reset method.
     * Typically this means resetting the various gas levels, crew people, water
     * levels, etc.
     */
    public void reset() {
        myLogger.info("BioDriver" + myID + ": Resetting simulation");
        ticksGoneBy = 0;
        for (IBioModule currentBioModule : modules) {
            myLogger.debug("resetting " + currentBioModule.getModuleName());
            currentBioModule.reset();
            currentBioModule.setTickLength(getTickLength());
        }
    }

    /**
     * The ticking simulation loop. Uses a variety of semaphores to
     * pause/resume/end without causing deadlock. Essentially runs till told to
     * pause or die.
     */
    private void runSimulation() {
		Thread currentThread = Thread.currentThread();
		if (!simulationStarted) {
			reset();
		}
		while (myTickThread == currentThread) {
			// Wait immediately if the simulation is paused to avoid busy waiting
			synchronized (this) {
				while (simulationIsPaused && (myTickThread == currentThread)) {
					try {
						wait();
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
						return; // exit if the thread is interrupted
					}
				}
			}
			
			// Perform the simulation tick once resumed
			tick();
			
			// Check if an end condition has been met
			if (isDone()) {
				myLogger.info("🎬 Simulation ended after " + getTicksInHumanReadableFormat());
				endSimulation();
				if (looping) {
					startSimulation();
				} else {
					break;
				}
			}
			
			// Pause between ticks to throttle simulation speed
			try {
				Thread.sleep(myDriverStutterLength);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				break;
			}
		}
	}

    /**
     * Ticks every server. The SimEnvironment is ticked first as it keeps track
     * of time for the rest of the server. The other server are ticked in no
     * particular order by enumerating through the module hashtable. When every
     * server has been ticked, BioDriver notifies all it's listeners that
     * this has happened.
     */
    private void tick() {
        myLogger.debug("BioDrive: begin tick " + ticksGoneBy);
        // Iterate through the actuators and tick them
        for (IBioModule currentBioModule : actuators)
            currentBioModule.tick();
        // Iterate through the active sim modules and tick them
        for (IBioModule currentBioModule : activeSimModules) {
            currentBioModule.tick();
        }
        // Iterate through the passive sim modules and tick them
        for (IBioModule currentBioModule : passiveSimModules)
            currentBioModule.tick();
        // Iterate through the priority sim modules and tick them
        for (IBioModule currentBioModule : prioritySimModules)
            currentBioModule.tick();
        // Iterate through the sensors and tick them
        for (IBioModule currentBioModule : sensors)
            currentBioModule.tick();
        ticksGoneBy++;
    }

    /**
     * @return tick length, in seconds
     */
    public float getTickLength() {
        return myTickLength;
    }

    /**
     * @param pTickLength in hours
     */
    public void setTickLength(float pTickLength) {
        myTickLength = pTickLength;
    }

    private class Ticker implements Runnable {
        /**
         * Invoked by the myTickThread.start() method call and necessary to
         * implement Runnable. Sets flag that simulation is running, initializes
         * servers (if applicable), then begins ticking them.
         */
        public void run() {
            myLogger.info("BioDriver" + myID + ": Running simulation...");
            simulationStarted = true;
            runSimulation();
        }
    }
}
