package parsing;

import java.util.List;


import model.Event;

import org.jdom.Element;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class NFLXMLParser extends AbstractIntermediateXMLParser {

	private String START_TIME = "Col8";
	private String END_TIME = "Col9";
	
	public NFLXMLParser(){
		// Assigns names of tags for EVENT_NODE, TITLE, DESCRIPTION, LOCATION
		super("document", "row","Col1","Col3","Col15");
	}

	/**
	 * Parses a date and time in the format yyyy-mm-dd hh:mm:ss
	 * @return a DateTime containing that information
	 */
	private DateTime parseTime(Element event, String startEnd) {
		String startTime = event.getChildText(startEnd);
		DateTimeFormatter formatter = DateTimeFormat
		        .forPattern("yyyy-MM-dd HH:mm:ss");
		return formatter.parseDateTime(startTime);
	}
	
	@Override
	protected DateTime parseStartTime(Element event) {
		return parseTime(event, START_TIME);
	}

	@Override
	protected DateTime parseEndTime(Element event) {
		return parseTime(event, END_TIME);
	}

	
	public static void main(String[] args) {
		NFLXMLParser parser = new NFLXMLParser();
		parser.loadFile("http://www.cs.duke.edu/courses/cps108/current/assign/02_tivoo/data/NFL.xml");

		List<Event> listOfEvents = parser.processEvents();

		for (Event event : listOfEvents) {
			System.out.println(event.toString());
		}
	}

}
