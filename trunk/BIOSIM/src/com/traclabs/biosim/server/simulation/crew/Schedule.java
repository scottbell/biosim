package biosim.server.crew;
import java.io.*;
import java.net.*;
import java.util.*;
/**
 * The Schedule dictates what each crew member shall do at what time, for how long, and at what intensity.
 * You cannot have two activities at the same time and each name must be unique.
 * Schedules must be in a text file and are specified like so:<br>
 * [activity name]	[time length]	[intensity]<br>
 * [activity name]	[time length]	[intensity]<br>
 * [activity name]	[time length]	[intensity]<br>
 * 			...						<br>
 * [activity name]	[time length]	[intensity]<br>
 * #DayEnd<br>
 * They must be in the above format or it won't parse and the CrewGroup will fail to start (it won't have a schedule).
 * By default, the Schedule loads a text file in BIOSIM_HOME/resources/biosim/server/crew/defaultSchedule.txt.
 * You may override this using the constructor that takes a File.
 * The Schedule puts 2 default activities into every Schedule, born and dead.
 * They both have a timelength and an intensity of 0..
 * Born has the order of 0.
 * Death has the order of -1
 * @author    Scott Bell
 */

public class Schedule{
	//Each activity hashed by their names
	private Map allActivities;
	//Each activity ordered (scheduled if you will)
	private List orderedSchedule;
	//The file where the schedule resides (known if using default)
	URL myScheduleURL;
	//Whether the schedule is using the default schedule (no schedule was specified by user)
	private boolean defaultSchedule = false;
	
	/**
	* Constructor that creates a new default schedule.
	*/
	public Schedule(){
		defaultSchedule = true;
		reset();
	}
	
	/**
	* Constructor that creates a new schedule with a specified file.
	* @param pScheduleFile the file that contains the schedule
	*/
	public Schedule(URL pScheduleURL){
		myScheduleURL = pScheduleURL;
		reset();
	}
	
	/**
	* Finds and returns an activity specified by a name
	* @param activityName the name of the activity wanted
	* @return the activity sought
	*/
	public ActivityImpl getActivityByName(String activityName){
		Object foundActivity = allActivities.get(activityName);
		if (foundActivity != null)
			return (ActivityImpl)(foundActivity);
		else
			return null;
	}
	
	public int getOrderOfActivity(String activityName){
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
	public ActivityImpl getActivityByOrder(int order){
		Object foundActivity = orderedSchedule.get(order);
		if (foundActivity != null)
			return (ActivityImpl)(foundActivity);
		else
			return null;
	}
	
	/**
	* Returns the number of activities in the schedule
	* @return the number of activities in the schedule
	*/
	public int getNumberOfScheduledActivities(){
		return orderedSchedule.size();
	}
	
	/**
	* Reloads the schedule from the file and parses it again.
	*/
	public void reset(){
		allActivities = new Hashtable();
		orderedSchedule = new Vector();
		if (defaultSchedule){
			//use default schedule
			defaultSchedule = true;
			URL defaultURL = ClassLoader.getSystemClassLoader().getResource("biosim/server/crew/defaultSchedule.txt");
			if (defaultURL == null){
				System.out.println("Had problems finding default Schedule!");
				return;
			}
			myScheduleURL = defaultURL;
			parseSchedule(myScheduleURL);
		}
		else{
			parseSchedule(myScheduleURL);
		}
	}

	/**
	* Parses the schedule file (user specified or default) and places the activities in the hashtables
	*/
	private void parseSchedule(URL scheduleURL){
		//Add 3 defaults..
		ActivityImpl bornActivity = new ActivityImpl("born", 0, 0);
		ActivityImpl deadActivity = new ActivityImpl("dead", 0, 0, true);
		ActivityImpl sickActivity = new ActivityImpl("sick", 1, 1, true);
		allActivities.put("born", bornActivity);
		orderedSchedule.add(0,bornActivity);
		allActivities.put("dead", deadActivity);
		allActivities.put("sick", sickActivity);
		try{
			BufferedReader inputReader = new BufferedReader(new InputStreamReader(scheduleURL.openStream()));
			String currentLine = inputReader.readLine().trim();
			int itemsRead = 1;
			while ((currentLine != null) && (!(currentLine.equals("#DayEnd")))){
				try{
					if (currentLine.length() > 1){
						if (currentLine.charAt(0) != '#'){
							StringTokenizer tokenizer = new StringTokenizer(currentLine);
							String activityName = tokenizer.nextToken();
							int lengthOfActivity = Integer.parseInt(tokenizer.nextToken());
							int intensityOfActivity = Integer.parseInt(tokenizer.nextToken());
							int orderOfActivity = itemsRead;
							itemsRead++;
							ActivityImpl newActivity = new ActivityImpl(activityName, lengthOfActivity, intensityOfActivity);
							allActivities.put(activityName, newActivity);
							orderedSchedule.add(orderOfActivity, newActivity);
						}
					}
					currentLine = inputReader.readLine().trim();
				}
				catch(java.util.NoSuchElementException e){
					System.out.println("Problem parsing line "+itemsRead+" in "+scheduleURL+": "+currentLine);
				}
			}
		}
		catch (IOException e){
			System.out.println("Had problems parsing schedule file "+scheduleURL+" with exception: "+e);
		}
	}
}
