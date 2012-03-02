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
	private static final String myTitle = "title";
	private static final String myContent = "content";
	private static final String RECUR = "Recurring";
	private static final int YEAR = 4;
	private static final int DAY = 2;
	private static final int TIME = 5;
	private static final int HOUR = 0;
	private static final int MINUTES = 1;

	private static DateTimeZone TIMEZONE = DateTimeZone.forID("UTC");

	private String[] startTimeArray;
	private String[] endTimeArray;

	public GoogleXMLParser(){
		super("feed", "entry");
	}

	@Override
	protected String parseTitle(Element event) {
		String title = event.getChildText(myTitle, null).toString();
		if (title != null)
			return title;
		else
			throw new NullPointerException("Couldn't find node: " + myTitle);
	}

	@Override
	protected String parseDescription(Element event) {
		return parseContent(event, "Event Description:");
	}

	@Override
	protected String parseLocation(Element event) {
		return parseContent(event, "Where:");
	}

	/**
	 * @param event
	 *            : the event being parsed finder: the information you are
	 *            looking for (ie. "Where" for location)
	 * @return the information of the "content" node pertaining to the String finder
	 *         (location, descriptions, etc)
	 */
	private String parseContent(Element event, String finder) {
		String[] summary = event.getChildText(myContent, null).toString()
		        .split("<br />");
		if (summary != null) {
			String information = null;
			for (String line : summary) {
				if (line.contains(finder))
					information = line.substring(line.indexOf(": ") + 1);
			}
			return information;
		} else
			throw new NullPointerException("Couldn't find node: " + myContent);
	}

	/**
	 * @return an array of the different elements of the "content" node, split
	 *         on whitespace
	 */
	protected String[] parseTimeSplitUp(Element element) {
		String timeInfo = element.getChildText(myContent, null).toString();
		return timeInfo.split("<br />");
	}

	/**
	 * Given an event, determines whether the event is recurring or not
	 */
	private Boolean isRecurring(Element time) {
		if (parseTimeSplitUp(time)[0].startsWith(RECUR))
			return true;
		return false;
	}

	/**
	 * Given an array of time, determines whether and event is all day
	 * 
	 * @return
	 */
	private Boolean isAllDayEvent(String[] time) {
		if (time.length == 5)
			return true;
		return false;
	}

	/**
	 * Determines whether an event spans more that one day
	 */
	private Boolean isMultipleDayEvent(String[] time) {
		if (time.length > 3)
			return true;
		return false;
	}

	/**
	 * Returns a DateTime object with the appropriate year, month, day, hour
	 * minute, timezone information, given an array with the time information
	 */
	protected DateTime parseTime(String[] time) {

		int year = parseYear(startTimeArray);
		int month = parseMonth(startTimeArray);
		int day = parseDay(startTimeArray);

		if (isMultipleDayEvent(time)) {
			year = parseYear(time);
			month = parseMonth(time);
			day = parseDay(time);
		}
		if (isAllDayEvent(time)) {
			int hour24 = 00;
			int minute = 00;
			return new DateTime(year, month, day, hour24, minute, TIMEZONE);
		}

		int hour24 = parseHour24(time);
		int minute = parseMinute(time);

		return new DateTime(year, month, day, hour24, minute, TIMEZONE);
	}


	@Override
	protected DateTime parseStartTime(Element time) {
		if (isRecurring(time)) {
			return GoogleRecurringEventXMLParser
			        .parseEventStart(parseTimeSplitUp(time)[1]
			                .substring(14, 33));
		}
		startTimeArray = parseTimeSplitUp(time)[0].split("to")[0].substring(6)
		        .split(split_one);
		return parseTime(startTimeArray);
	}

	@Override
	protected DateTime parseEndTime(Element time) {
		if (isRecurring(time)) {
			return GoogleRecurringEventXMLParser.parseEventEnd(
			        parseTimeSplitUp(time)[1].substring(14, 33),
			        parseTimeSplitUp(time)[2]);
		}

		if (isAllDayEvent(startTimeArray)) {
			return parseTime(startTimeArray);
		}
		endTimeArray = parseTimeSplitUp(time)[0].split("to")[1].substring(1)
		        .split(split_one);
		return parseTime(endTimeArray);
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
	 * Returns the time element, hour or minute, despending on the specified int
	 * element, and the given string array of the time info in the form hh:mm
	 */
	private int getTimeElement(String[] time, int element) {
		if (isMultipleDayEvent(time))
			return parseHourMinute(time[TIME].split(":"))[element];
		return parseHourMinute(time[0].split(":"))[element];
	}

	/**
	 * Returns the hour of the time element, given the array of start or end
	 * time information
	 */
	protected int parseHour24(String[] time) {
		return getTimeElement(time, HOUR);
	}

	/**
	 * Returns the minutes of the time element, given the array of start or end
	 * time information
	 */
	protected int parseMinute(String[] time) {
		return getTimeElement(time, MINUTES);
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

	public static void main(String[] args) {
		GoogleXMLParser parser = new GoogleXMLParser();
		List<Event> listOfEvents = parser.processEvents("http://www.cs.duke.edu/courses/cps108/current/assign/02_tivoo/data/googlecal.xml");

		for (Event event : listOfEvents) {
			System.out.println(event.toString());
		}
	}

}
