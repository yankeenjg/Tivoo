package parsing;

import java.util.List;

import model.Event;

import org.jdom.Element;
import org.joda.time.DateTime;

public class DukeBasketBallXMLParser extends
        AbstractSimpleTimeWithSeparateDateAndTimeXMLParser {

	/**
	 * Labels for specific nodes in the event tree
	 */
	private String myStartDate = "StartDate";
	private String myStartTime = "StartTime";
	private String myEndDate = "EndDate";
	private String myEndTime = "EndTime";
	private String myPattern = "MM/dd/yyyyHH:mm:ss";

	public DukeBasketBallXMLParser() {
		// Assigns names of tags for myEventNode, myTitle, myDescription,
		// myLocation, myDateTimePattern, myStartDate, myStartTime, myEndDate,
		// myEndTime
		super("dataroot", "Calendar", "Subject", "Description", "Location",
		        "MM/dd/yyyyHH:mm:ss", "StartDate", "StartTime", "EndDate",
		        "EndTime");
	}

	/**
	 * Merges the date and time elements of an event,
	 * 
	 * @return a String of the combined information
	 */
	private String mergeDateAndTime(Element event, String dateTag,
	        String timeTag) {
		String date = event.getChildText(dateTag);
		String time = event.getChildText(timeTag);
		return date + time.split(" ")[0];
	}

	@Override
	protected DateTime parseStartTime(Element event) {
		String timestamp = mergeDateAndTime(event, myStartDate, myStartTime);
		return super.parseTime(event, myPattern, timestamp);
	}

	@Override
	protected DateTime parseEndTime(Element event) {
		String timestamp = mergeDateAndTime(event, myEndDate, myEndTime);
		return super.parseTime(event, myPattern, timestamp);

	}

	public static void main(String[] args) {
		DukeBasketBallXMLParser parser = new DukeBasketBallXMLParser();
		List<Event> listOfEvents = parser
		        .processEvents("http://www.cs.duke.edu/courses/cps108/current/assign/02_tivoo/data/DukeBasketBall.xml");

		for (Event event : listOfEvents) {
			System.out.println(event.toString());
		}
	}

}