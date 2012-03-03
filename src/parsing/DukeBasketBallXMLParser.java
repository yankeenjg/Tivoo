package parsing;

import java.util.List;

import model.Event;

import org.jdom.Element;
import org.joda.time.DateTime;

public class DukeBasketBallXMLParser extends
        AbstractSimpleTimeWithSeparateDateAndTimeXMLParser {

	public DukeBasketBallXMLParser() {
		// Assigns names of tags for myEventNode, myTitle, myDescription,
		// myLocation, myDateTimePattern, myStartDate, myStartTime, myEndDate,
		// myEndTime
		super("dataroot", "Calendar", "Subject", "Description", "Location",
		        "MM/dd/yyyyHH:mm:ss", "StartDate", "StartTime", "EndDate",
		        "EndTime");
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