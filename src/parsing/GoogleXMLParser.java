package parsing;

import model.Event;

import java.util.List;

import org.jdom.Element;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * This class takes a google calendar in xml format and parses the different
 * events into separate event objects
 */
public class GoogleXMLParser extends AbstractXMLParser {

	private static final String split_one = "\\s+| |,|-|<";
	private static final String split_recur = "\\s+| |,|-|<|:";
	private static final String TITLE = "title";
	private static final String CONTENT = "content";
	private static DateTimeZone TIMEZONE = DateTimeZone.forID("UTC");
	private static final int YEAR = 4;
	private static final int DAY = 2;
	private static final int TIME = 5;
	private static final int HOUR = 0;
	private static final int MINUTES = 1;

	private String[] eventInfoArray;
	private String[] startTimeArray;

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * @return  the list of child nodes of the root node
	 */
	protected List<Element> parseGetEventsList() {	
		eventsRoot = doc.getRootElement();
		return (List<Element>) eventsRoot.getChildren("entry", null);
	}

	/**
	 * @return an array of the different elements of the "content" node, split
	 *         on whitespace
	 */
	protected String[] parseTimeSplitUp(Element element) {	
		String timeInfo = element.getChildText(CONTENT, null).toString();
		return timeInfo.split("\n");
	}

	/**
	 * determines whether the event is recurring or one-time parses time
	 * appropriately
	 */
	public DateTime parseTypeOfEvent(int startEnd, int sub, Element time) {
		if (parseTimeSplitUp(time)[0].startsWith("Recurring")) {
			eventInfoArray = parseTimeSplitUp(time)[1].split(split_recur);
			return GoogleRecurringEventXMLParser
			        .parseEventStart(eventInfoArray);
		}
		String[] timeArray = parseTimeSplitUp(time)[0].split("to")[startEnd]
		        .substring(sub).split(split_one);

		return parseTime(timeArray);
	}

	/**
	 * Returns a DateTime object with the appropriate year, month, day, hour
	 * minute, timezone information, given an array with the time information
	 */
	protected DateTime parseTime(String[] time) {

		int year = parseYear(startTimeArray);
		int month = parseMonth(startTimeArray);
		int day = parseDay(startTimeArray);
		if (time.length > 3) {
			year = parseYear(time);
			month = parseMonth(time);
			day = parseDay(time);
		}
		int hour24 = parseHour24(time);
		int minute = parseMinute(time);
		return new DateTime(year, month, day, hour24, minute, TIMEZONE);
	}

	/**
	 * Returns the DateTime form of the start time, using an array of
	 * information about the start time
	 */
	@Override
	protected DateTime parseStartTime(Element time) {
		startTimeArray = parseTimeSplitUp(time)[0].split("to")[0].substring(6)
		        .split(split_one);
		return parseTypeOfEvent(0, 6, time);
	}

	/**
	 * Returns the DateTime form of the end time, using an array of information
	 * about the end time
	 */
	@Override
	protected DateTime parseEndTime(Element time) {
		return parseTypeOfEvent(1, 1, time);
	}

	/**
	 * Returns the month, given an array of either start or end time information
	 */
	protected int parseYear(String[] time) {
		return Integer.parseInt(time[YEAR]);
	}

	/**
	 * Returns the month, given an array of either start or end time information
	 */
	protected int parseMonth(String[] time) {
		DateTimeFormatter format = DateTimeFormat.forPattern("MMM");
		DateTime tempMonth = format.parseDateTime(time[1]);
		return tempMonth.getMonthOfYear();
	}

	/**
	 * Returns the day in int form, given an array of either start or end time
	 */
	protected int parseDay(String[] time) {
		return Integer.parseInt(time[DAY]);
	}

	/**
	 * Returns the hour of the time element, given the array of start or end
	 * time information
	 */
	protected int parseHour24(String[] time) {
		if (time.length > 3)
			return parseHourMinute(time[TIME].split(":"))[HOUR];
		return parseHourMinute(time[0].split(":"))[HOUR];
	}

	/**
	 * Returns the minutes of the time element, given the array of start or end
	 * time information
	 */
	protected int parseMinute(String[] time) {
		if (time.length > 3)
			return parseHourMinute(time[TIME].split(":"))[MINUTES];
		return parseHourMinute(time[0].split(":"))[MINUTES];
	}

	/**
	 * Returns an array containing the hour and minutes element of the time
	 * information, given the information in the hh:mm format
	 */
	private int[] parseHourMinute(String[] splitTime) {
		int[] hourMinute = new int[2];

		if (splitTime.length == 2) {
			hourMinute[HOUR] = Integer.parseInt(splitTime[HOUR]);
			hourMinute[MINUTES] = Integer.parseInt(splitTime[MINUTES]
			        .substring(0, splitTime[1].indexOf('m') - 1));
		}

		else {
			hourMinute[HOUR] = Integer.parseInt(splitTime[HOUR].substring(0,
			        splitTime[HOUR].indexOf('m') - 1));
			hourMinute[MINUTES] = 00;
		}
		return hourMinute;
	}

	/**
	 * @return the title of the event
	 */
	protected String parseTitle(Element event) {	
		return event.getChildText(TITLE, null).toString();
	}

	/**
	 * @param event
	 *            : the event being parsed finder: the information you are
	 *            looking for (ie. "Where" for location)
	 * @return the information of the "content" node pertaining to the finder
	 *         (location, descriptions, etc)
	 */
	private String parseContent(Element event, String finder) {	
		String[] summary = event.getChildText(CONTENT, null).toString()
		        .split("<br />");
		String information = null;
		for (String line : summary) {
			if (line.contains(finder))
				information = line.substring(line.indexOf(": ") + 1);
		}
		return information;

	}

	/**
	 * @return the description of the event
	 */
	protected String parseDescription(Element event) {	
		return parseContent(event, "Event Description:");
	}

	/**
	 * @return the location of the event
	 */
	protected String parseLocation(Element event) {	
		return parseContent(event, "Where:");
	}

	protected Boolean parseAllDayEvent(Element event) {
		if (eventInfoArray.length < 9) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		GoogleXMLParser parser = new GoogleXMLParser();
		parser.loadFile("https://www.google.com/calendar/feeds/kathleen.oshima%40gmail.com/private-cf4e2a2cf06315dece847f9aaf867f3e/basic");
		//parser.loadFile("http://www.cs.duke.edu/courses/cps108/current/assign/02_tivoo/data/googlecal.xml");

		List<Event> listOfEvents = parser.processEvents();

		for (Event event : listOfEvents) {
			System.out.println(event.toString());
		}
	}
}
