package model;
import org.joda.time.*;

public class Event {
	private String myTitle;
	private DateTime myStartTime;
	private DateTime myEndTime;
	private String myDescription;
	private String myLocation;
	private boolean allDay;
	
	public Event(String title, DateTime startTime, DateTime endTime, String location, String description, boolean allday) {
		myTitle = title;
		myStartTime = startTime;
		myEndTime = endTime;
		myLocation = location;
		myDescription = description;
		allDay = allday;
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
	
	public String getLocation(){
		return myLocation;
	}

	public String getDescription(){
		return myDescription;
	}
	
	public boolean isAllDay(){
		return allDay;
	}
	
	public String toString(){
		return myTitle + " " +
				myStartTime + " " +
				myEndTime + " " +
				myLocation + " " +
				myDescription + " " +
				allDay;
	}
}


