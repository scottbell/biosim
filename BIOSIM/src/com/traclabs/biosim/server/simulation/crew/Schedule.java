package biosim.server.crew;

import java.io.*;
import java.net.*;
import java.util.*;

public class Schedule{
	
	private Hashtable scheduleNameHash;
	private Hashtable scheduleOrderHash;
	
	int numberOfActivities = 0;

	public Schedule(){
		scheduleNameHash = new Hashtable();
		scheduleOrderHash = new Hashtable();
		//use default schedule
		System.out.println("Using default schedule...");
		URL defaultURL = ClassLoader.getSystemClassLoader().getResource("biosim/server/crew/defaultSchedule.txt");
		if (defaultURL == null){
			System.out.println("Had problems finding default Schedule!");
			return;
		}
		try{
			File myScheduleFile = new File(new URI(defaultURL.toString()));
			parseSchedule(myScheduleFile);
		}
		catch(java.net.URISyntaxException e){
			System.out.println("Problem finding default Schedule: "+e);
		}
	}
	
	public Schedule(File pScheduleFile){
		scheduleNameHash = new Hashtable();
		scheduleOrderHash = new Hashtable();
		File myScheduleFile = pScheduleFile;
		parseSchedule(myScheduleFile);
	}

	public ActivityImpl getActivityByName(String activityName){
		Object foundActivity = scheduleNameHash.get(activityName);
		if (foundActivity != null)
			return (ActivityImpl)(foundActivity);
		else
			return null;
	}


	public ActivityImpl getActivityByOrder(int order){
		Object foundActivity = scheduleOrderHash.get(new Integer(order));
		if (foundActivity != null)
			return (ActivityImpl)(foundActivity);
		else
			return null;
	}
	
	public int getNumberOfActivities(){
		return numberOfActivities;
	}


	private void parseSchedule(File scheduleFile){
		//Add 2 defaults..
		scheduleNameHash.put("Born", new ActivityImpl("Born", 0));
		scheduleOrderHash.put(new Integer(0), new ActivityImpl("Born", 0));
		scheduleNameHash.put("Dead", new ActivityImpl("Dead", -1));
		scheduleOrderHash.put(new Integer(-1), new ActivityImpl("Dead", -1));
		try{
			 BufferedReader inputReader = new BufferedReader(new FileReader(scheduleFile));
			 String currentLine = inputReader.readLine().trim();
			 int itemsRead = 1;
			 while ((currentLine != null) && (!currentLine.equals("#DayEnd"))){
			 	try{
					StringTokenizer tokenizer = new StringTokenizer(currentLine);
					String activityName = tokenizer.nextToken();
					int lengthOfActivity = Integer.parseInt(tokenizer.nextToken());
					Integer orderOfActivity = new Integer(itemsRead);
					itemsRead++;
					scheduleNameHash.put(activityName, new ActivityImpl(activityName, lengthOfActivity));
					scheduleOrderHash.put(orderOfActivity, new ActivityImpl(activityName, lengthOfActivity));
					currentLine = inputReader.readLine().trim();
				}
				catch(java.util.NoSuchElementException e){
					System.out.println("Problem parsing line "+itemsRead+" in "+scheduleFile+": "+currentLine);
				}
			 }
			 numberOfActivities = itemsRead;
		}
		catch (IOException e){
			System.out.println("Had problems parsing schedule file "+scheduleFile+" with exception: "+e);
		}
	}
}
