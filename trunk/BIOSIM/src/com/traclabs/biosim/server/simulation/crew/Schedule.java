package com.traclabs.biosim.server.simulation.crew;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.traclabs.biosim.idl.simulation.crew.Activity;
import com.traclabs.biosim.idl.simulation.crew.ActivityHelper;
import com.traclabs.biosim.util.OrbUtils;

/**
 * The Schedule dictates what each crew member shall do at what time, for how
 * long, and at what intensity. You cannot have two activities at the same time
 * and each name must be unique.
 * 
 * @author Scott Bell
 */

public class Schedule {
    //Each activity hashed by their names
    private Map<String, Activity> allActivities;

    //Each activity ordered (scheduled if you will)
    private List<Activity> orderedSchedule;

    private Activity myBornActivity;

    private Activity myDeadActivity;

    private Activity mySickActivity;

    private Activity myAbsentActivity;
    
    private CrewGroupImpl myCrewGroupImpl;

    /**
     * Constructor that creates a new default schedule.
     */
    public Schedule(CrewGroupImpl pCrewGroupImpl) {
    	myCrewGroupImpl = pCrewGroupImpl;
        createDefaultActivites();
    }

    private void createDefaultActivites() {
        allActivities = new Hashtable<String, Activity>();
        orderedSchedule = new Vector<Activity>();
        ActivityImpl bornActivityImpl = new ActivityImpl("born", 0, 0);
        ActivityImpl deadActivityImpl = new ActivityImpl("dead", 0, 0);
        ActivityImpl absentActivityImpl = new ActivityImpl("absent", 0, 0);
        ActivityImpl sickActivityImpl = new ActivityImpl("sick", (int)(Math.ceil(12 / myCrewGroupImpl.getTickLength())), 1);
        myBornActivity = ActivityHelper.narrow(OrbUtils
                .poaToCorbaObj(bornActivityImpl));
        myDeadActivity = ActivityHelper.narrow(OrbUtils
                .poaToCorbaObj(deadActivityImpl));
        mySickActivity = ActivityHelper.narrow(OrbUtils
                .poaToCorbaObj(sickActivityImpl));
        myAbsentActivity = ActivityHelper.narrow(OrbUtils
                .poaToCorbaObj(absentActivityImpl));
        allActivities.put("born", myBornActivity);
        orderedSchedule.add(0, myBornActivity);
        allActivities.put("dead", myDeadActivity);
        allActivities.put("sick", mySickActivity);
        allActivities.put("absent", myAbsentActivity);
    }

    /**
     * Finds and returns an activity specified by a name
     * 
     * @param activityName
     *            the name of the activity wanted
     * @return the activity sought
     */
    public Activity getActivityByName(String activityName) {
        return allActivities.get(activityName);
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("-Ordered Schedule-\n");
        for (Iterator iter = orderedSchedule.iterator(); iter.hasNext();) {
            Activity currentActivity = (Activity) (iter.next());
            buffer.append(orderedSchedule.indexOf(currentActivity) + ":  (");
            buffer.append(currentActivity.getName() + ", "
                    + currentActivity.getTimeLength() + "h, "
                    + currentActivity.getActivityIntensity() + ")\n");
        }
        buffer.append("\n-All Activities-\n");
        for (Iterator iter = allActivities.values().iterator(); iter.hasNext();) {
            Activity currentActivity = (Activity) (iter.next());
            buffer.append("(" + currentActivity.getName() + ", "
                    + currentActivity.getTimeLength() + "h, "
                    + currentActivity.getActivityIntensity() + ")\n");
        }
        return buffer.toString();
    }

    public int getOrderOfScheduledActivity(String activityName) {
        Object foundActivity = allActivities.get(activityName);
        if (foundActivity != null)
            return orderedSchedule.indexOf(foundActivity);
		return -1;
    }

    /**
     * Finds and returns an activity specified by the order
     * 
     * @param order
     *            the order of the activity wanted
     * @return the activity sought
     */
    public Activity getScheduledActivityByOrder(int order) {
        return (orderedSchedule.get(order));
    }

    /**
     * Returns the number of activities in the schedule
     * 
     * @return the number of activities in the schedule
     */
    public int getNumberOfScheduledActivities() {
        return orderedSchedule.size();
    }

    public void insertActivityInSchedule(Activity pActivity) {
        if (!allActivities.containsKey(pActivity.getName()))
            allActivities.put(pActivity.getName(), pActivity);
        orderedSchedule.add(pActivity);
    }

    public void insertActivityInSchedule(Activity pActivity, int pOrder) {
        if (!allActivities.containsKey(pActivity.getName()))
            allActivities.put(pActivity.getName(), pActivity);
        orderedSchedule.add(pOrder, pActivity);
    }
    

}