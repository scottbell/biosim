package biosim.server.simulation.crew;
import java.util.*;
import biosim.idl.simulation.crew.*;
import biosim.server.util.*;
/**
 * The Schedule dictates what each crew member shall do at what time, for how long, and at what intensity.
 * You cannot have two activities at the same time and each name must be unique.
 * @author    Scott Bell
 */

public class Schedule{
	//Each activity hashed by their names
	private Map allActivities;
	//Each activity ordered (scheduled if you will)
	private List orderedSchedule;
	private static Activity myBornActivity;
	private static Activity myDeadActivity;
	private static Activity mySickActivity;
	
	/**
	* Constructor that creates a new default schedule.
	*/
	public Schedule(){
		createDefaultActivites();
		reset();
	}
	
	private void createDefaultActivites(){
		allActivities = new Hashtable();
		orderedSchedule = new Vector();
		if ((myBornActivity == null) || (myDeadActivity == null) || (mySickActivity == null)){
			ActivityImpl bornActivityImpl = new ActivityImpl("born", 0, 0);
			ActivityImpl deadActivityImpl = new ActivityImpl("dead", 0, 0);
			ActivityImpl sickActivityImpl = new ActivityImpl("sick", 24, 1);
			myBornActivity = ActivityHelper.narrow(OrbUtils.poaToCorbaObj(bornActivityImpl));
			myDeadActivity = ActivityHelper.narrow(OrbUtils.poaToCorbaObj(deadActivityImpl));
			mySickActivity = ActivityHelper.narrow(OrbUtils.poaToCorbaObj(sickActivityImpl));
		}
	}
	
	/**
	* Finds and returns an activity specified by a name
	* @param activityName the name of the activity wanted
	* @return the activity sought
	*/
	public Activity getActivityByName(String activityName){
		return (Activity)allActivities.get(activityName);
	}
	
	public int getOrderOfScheduledActivity(String activityName){
		Object foundActivity = allActivities.get(activityName);
		if (foundActivity != null)
			return orderedSchedule.indexOf(foundActivity);
		else
			return -1;
	}

	/**
	* Finds and returns an activity specified by the order
	* @param order the order of the activity wanted
	* @return the activity sought
	*/
	public Activity getScheduledActivityByOrder(int order){
		return (Activity)(orderedSchedule.get(order));
	}
	
	/**
	* Returns the number of activities in the schedule
	* @return the number of activities in the schedule
	*/
	public int getNumberOfScheduledActivities(){
		return orderedSchedule.size();
	}
	
	public void insertActivityInSchedule(Activity pActivity, int pOrder){
		if (!allActivities.containsKey(pActivity.getName()))
			allActivities.put(pActivity.getName(), pActivity);
		orderedSchedule.add(pOrder, pActivity);
	}

	/**
	* Reloads the schedule from the file and parses it again.
	*/
	public void reset(){
	}

}
