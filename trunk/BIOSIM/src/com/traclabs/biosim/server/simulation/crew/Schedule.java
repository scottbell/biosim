package biosim.server.crew;

// The package containing our stubs.
import SIMULATION.*;
import java.io.*;
import java.net.*;

public class Schedule{
	
	public Schedule(){
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
		File myScheduleFile = pScheduleFile;
		parseSchedule(myScheduleFile);
	}
	
	private void parseSchedule(File scheduleFile){
		try{
			 BufferedReader inputReader = new BufferedReader(new FileReader(scheduleFile));
			 String currentLine = inputReader.readLine();
			 while ((currentLine != null) && (!currentLine.equals("#DayEnd"))){
				System.out.println("Read: "+inputReader.readLine());
				currentLine = inputReader.readLine();
			 }
		}
		catch (IOException e){
			System.out.println("Had problems parsing schedule file "+scheduleFile+" with exception: "+e);
		}
	}
}