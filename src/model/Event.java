package model;
import org.joda.time.*;
import java.util.List;

public class Event {
	private String myTitle;
	private DateTime myStartTime;
	private DateTime myEndTime;
	private String myDescription;
	private String myLocation;
	private List<String> myActors;
	private boolean allDay;
	
	public Event(String title, DateTime startTime, DateTime endTime, String location, String description, List<String> actorList, boolean allday) {
		myTitle = title;
		myStartTime = startTime;
		myEndTime = endTime;
		myLocation = location;
		myDescription = description;
		myActors = actorList;
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
	
	public List<String> getActors() {
		return myActors;
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
				myActors + 
				allDay;
	}
}


