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
		ActivityImpl bornActivity = new ActivityImpl("born", 0, 0);
		ActivityImpl deadActivity = new ActivityImpl("dead", 0, 0);
		scheduleNameHash.put("born", bornActivity);
		scheduleOrderHash.put(new Integer(0), bornActivity);
		scheduleNameHash.put("dead", deadActivity);
		scheduleOrderHash.put(new Integer(-1), deadActivity);
		try{
			BufferedReader inputReader = new BufferedReader(new FileReader(scheduleFile));
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
							Integer orderOfActivity = new Integer(itemsRead);
							itemsRead++;
							ActivityImpl newActivity = new ActivityImpl(activityName, lengthOfActivity, intensityOfActivity);
							scheduleNameHash.put(activityName, newActivity);
							scheduleOrderHash.put(orderOfActivity, newActivity);
						}
					}
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
