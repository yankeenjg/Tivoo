import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.sun.org.apache.xalan.internal.xsltc.compiler.Pattern;

public class GoogleXMLParser extends AbstractXMLParser {

	private static final String split = "\\s+| |,|-|<";
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

		if (timeInfo.startsWith("When")) {
			return parseOneTimeEvent(timeInfoArray[0].split(split));
		}

		return parseRecurringEventStart(timeInfoArray[1].split(split));

	}

	public DateTime parseRecurringEventStart(String[] timeInfoArray) {

		int year = Integer.parseInt(timeInfoArray[3]);
		int month = Integer.parseInt(timeInfoArray[4]);
		int day = Integer.parseInt(timeInfoArray[5]);
		int hour = Integer.parseInt(timeInfoArray[6]);
		int minute = Integer.parseInt(timeInfoArray[7]);
		DateTimeZone dateTimeZone = DateTimeZone.forID("UTC");

		return new DateTime(year, month, day, hour, minute, dateTimeZone);

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
				System.out.println(year);

		int day = parseOneTimeEventDay(timeInfoArray);
		System.out.println(day);

		int month = parseOneTimeEventMonth(timeInfoArray);
		System.out.println(month);

		DateTimeZone dateTimeZone = DateTimeZone.forID("UTC"); // /fix this
		String startTime = timeInfoArray[6];
		System.out.println(startTime);
		String[] splitTime = startTime.split(":");
		
		int hour = 0;
		int minute = 0;
		
		if (splitTime.length == 2) {
			hour = Integer.parseInt(splitTime[0]);
			minute = Integer.parseInt(splitTime[1].substring(0,1));
		}
		
		else {
			hour = Integer.parseInt(splitTime[0].substring(0, splitTime[0].length()-3));
			minute = 00;
		}
		
		/**
		if (timeInfoArray.length < 10) {
			String[] time = timeInfoArray[4].split(":");
			hour = Integer.parseInt(time[0]);
			minute = 00;
			if (time.length == 2) {
				minute = Integer.parseInt(time[1]);
			}
			return new DateTime(year, month, day, hour, minute, dateTimeZone);
		}

		if (timeInfoArray[6].contains("am")) {
			hour = Integer.parseInt(timeInfoArray[7].substring(0,
					timeInfoArray[7].indexOf("a")));
			minute = 00;
		}
		if (timeInfoArray[6].contains("pm")) {
			hour = Integer.parseInt(timeInfoArray[7].substring(0,
					timeInfoArray[7].indexOf("pm")));
			if (hour < 12)
				hour += 12;
			minute = 00;
		} else if (!timeInfoArray[6].contains("am")
				&& !timeInfoArray[6].contains("pm")) {
			hour = Integer.parseInt(timeInfoArray[7]);
			minute = Integer.parseInt(timeInfoArray[8].substring(0, 2));
		}
		*/

		return new DateTime(year, month, day, hour, minute, dateTimeZone);

	}

	public String parseTitle(Element event) {
		return event.getChildText(TITLE, null).toString();
	}

	public String parseDescription(Element event) {

		String[] summary = event.getChildText(CONTENT, null).toString()
				.split("<br />");
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