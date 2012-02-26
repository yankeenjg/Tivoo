package model;
import org.joda.time.*;

public class Event {
	
	private String myTitle;
	private DateTime myStartTime;
	private DateTime myEndTime;
	private String myDescription;
	private String myLocation;
	
	public Event(String title, DateTime startTime, DateTime endTime, String description, String location) {
		myTitle = title;
		myStartTime = startTime;
		myEndTime = endTime;
		myDescription = description;
		myLocation = location;
	}
	
	public String getTitle() {
		return myTitle;
	}

	public DateTime getStartTime() {
		return myStartTime;
	}
	
	public DateTime getEndTime(){
		return myEndTime;
	}

	public String getDescription(){
		return myDescription;
	}
	
	public String getLocation(){
		return myLocation;
	}
	
	public String toString(){
		return myTitle + " " +
				myStartTime + " " +
				myEndTime + " " +
				myDescription;
	}
}


