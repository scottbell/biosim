package biosim.server.crew;

import java.io.*;
import java.net.*;
import java.util.*;

public class Schedule{
	
	private Hashtable scheduleHash;
	
	public Schedule(){
		scheduleHash = new Hashtable();
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
		scheduleHash = new Hashtable();
		File myScheduleFile = pScheduleFile;
		parseSchedule(myScheduleFile);
	}
	
	private void parseSchedule(File scheduleFile){
		try{
			 BufferedReader inputReader = new BufferedReader(new FileReader(scheduleFile));
			 String currentLine = inputReader.readLine().trim();
			 int itemsRead = 0;
			 while ((currentLine != null) && (!currentLine.equals("#DayEnd"))){
			 	try{
					StringTokenizer tokenizer = new StringTokenizer(currentLine);
					String activity = tokenizer.nextToken();
					int lengthOfActivity = Integer.parseInt(tokenizer.nextToken());
					Integer orderOfActivity = new Integer(itemsRead);
					itemsRead++;
					scheduleHash.put(orderOfActivity, new Activity(activity, lengthOfActivity));
					currentLine = inputReader.readLine().trim();
				}
				catch(java.util.NoSuchElementException e){
					System.out.println("Problem parsing line "+itemsRead+" in "+scheduleFile+": "+currentLine);
				}
			 }
		}
		catch (IOException e){
			System.out.println("Had problems parsing schedule file "+scheduleFile+" with exception: "+e);
		}
	}
}
