package biosim.server.crew;

public class Activity{
	private String activityName;
	private int activityTime;

	public Activity(String pActivityName, int pActivityTime){
		activityName = pActivityName;
		activityTime = pActivityTime;
	}
	
	public String getActivityName(){
		return activityName;
	}
	
	public int getActivityTime(){
		return activityTime;
	}

	public String toString(){
		return activityName;
	}
}
