
import org.joda.time.*;

public class Event {
	String myTitle;
	DateTime myStartTime;
	DateTime myEndTime;
	String myDescription;
	String myLocation;
	
	public Event(String title, DateTime startTime, DateTime endTime, String description, String location) {
		myTitle = title;
		myStartTime = startTime;
		myEndTime = endTime;
		myDescription = description;
		myLocation = location;
	}
	
	public DateTime getStartTime() {
		return myStartTime;
	}
	
	public String getTitle() {
		return myTitle;
	}
	
	public String toString(){
		return myTitle + " " +
				myStartTime + " " +
				myEndTime + " " +
				myDescription + " " +
				myLocation;
	}
}


