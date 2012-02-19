import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.sun.org.apache.xalan.internal.xsltc.compiler.Pattern;

public class GoogleXMLParser extends AbstractXMLParser {

	private static final String split_one = "\\s+| |,|-|<";
	private static final String split_recur = "\\s+| |,|-|<|:";
	private static final String TITLE = "title";
	private static final String SUMMARY = "summary";
	private static final String CONTENT = "content";

	private Element parseGetEventsRoot() {
		eventsRoot = doc.getRootElement();
		return eventsRoot;
	}

	public DateTime parseStartTime(Element element) {
		
		String timeInfo = element.getChildText(CONTENT, null).toString();
		String[] timeInfoArray = timeInfo.split("\n");

		if (timeInfo.startsWith("Recurring")) {
			return parseRecurringEventStart(timeInfoArray[1].split(split_recur));
		}
		return parseOneTimeEvent(timeInfoArray[0].split(split_one));
	}

	public DateTime parseEndTime(Element element) {

	}

	public DateTime parseRecurringEventEnd(String[] timeInfoArray) {

	}

	public DateTime parseRecurringEventStart(String[] timeInfoArray) {
		int[] DateTimeArray = new int[6];

		for (int j = 0; j < 5; j++) {
			DateTimeArray[j] = Integer.parseInt(timeInfoArray[j + 2]);
		}
		DateTimeZone dateTimeZone = DateTimeZone.forID("UTC");

		return new DateTime(DateTimeArray[0], DateTimeArray[1],
		        DateTimeArray[2], DateTimeArray[3], DateTimeArray[4],
		        dateTimeZone);

	}

	public int parseOneTimeEventYear(String[] timeInfoArray) {
		return Integer.parseInt(timeInfoArray[5].substring(0, 4));
	}

	public int parseOneTimeEventDay(String[] timeInfoArray) {
		return Integer.parseInt(timeInfoArray[3]);
	}

	public int parseOneTimeEventMonth(String[] timeInfoArray) {
		DateTimeFormatter format = DateTimeFormat.forPattern("MMM");
		DateTime tempMonth = format.parseDateTime(timeInfoArray[2]);
		return tempMonth.getMonthOfYear();
	}

	public DateTime parseOneTimeEvent(String[] timeInfoArray) {

		int year = parseOneTimeEventYear(timeInfoArray);
		int day = parseOneTimeEventDay(timeInfoArray);
		int month = parseOneTimeEventMonth(timeInfoArray);

		DateTimeZone dateTimeZone = DateTimeZone.forID("UTC"); // /fix this
		String startTime = timeInfoArray[6];
		String[] splitTime = startTime.split(":");

		int hour = 0;
		int minute = 0;

		if (timeInfoArray.length < 9) {
			hour = 12;
			minute = 00;
			return new DateTime(year, month, day, hour, minute, dateTimeZone);
		}

		if (splitTime.length == 2) {
			hour = Integer.parseInt(splitTime[0]);
			minute = Integer.parseInt(splitTime[1].substring(0,
			        splitTime[1].length() - 2));
		}

		else {
			hour = Integer.parseInt(splitTime[0].substring(0,
			        splitTime[0].length() - 2));
			minute = 00;
		}

		return new DateTime(year, month, day, hour, minute, dateTimeZone);

	}

	public String parseTitle(Element event) {
		return event.getChildText(TITLE, null).toString();
	}

	public String[] parseContent(Element event) {
		String[] summary = event.getChildText(CONTENT, null).toString()
				.split("<br />");
	
		
}
	public String parseDescription(Element event) {

	//	String[] summary = event.getChildText(CONTENT, null).toString()
	//	        .split("<br />");
		String description = null;

		for (String line : summary) {
			if (line.contains("Event Description:"))
				description = line.substring(line.indexOf(": ") + 1);
		}
		return description;
	}

	public String parseLocation(Element event) {
		String[] summary = event.getChildText(SUMMARY, null).toString()
		        .split("\n");
		String location = null;

		for (String line : summary) {
			if (line.contains("Where: "))
				location = line.substring(line.indexOf(" ") + 1);
		}

		return location;
	}

	@Override
	public List<Event> processEvents() {
		parseGetEventsRoot();

		@SuppressWarnings("unchecked")
		List<Element> XMLChildren = (List<Element>) eventsRoot.getChildren(
		        "entry", null);

		List<Event> newXMLChildren = new ArrayList<Event>();

		for (Element event : XMLChildren) {

			String title = parseTitle(event);
			String description = parseDescription(event);
			String location = parseLocation(event);
			DateTime startTime = parseStartTime(event);
			DateTime endTime = parseStartTime(event);

			Event newEvent = new Event(title, startTime, endTime, description,
			        location);

			newXMLChildren.add(newEvent);
		}

		return newXMLChildren;
	}

	public static XMLParserFactory getFactory() {
		return new XMLParserFactory(new GoogleXMLParser());
	}

	public static void main(String[] args) {
		GoogleXMLParser parser = new GoogleXMLParser();
		parser.loadFile("http://www.cs.duke.edu/courses/cps108/current/assign/02_tivoo/data/googlecal.xml");
		List<Event> listOfEvents = parser.processEvents();

		for (Event event : listOfEvents) {
			System.out.println(event.toString());
		}
	}
}