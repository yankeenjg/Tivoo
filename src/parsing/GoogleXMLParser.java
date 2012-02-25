package parsing;
import model.Event;


import java.util.List;

import org.jdom.Element;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


public class GoogleXMLParser extends AbstractXMLParser {

	private static final String split_one = "\\s+| |,|-|<";
	private static final String split_recur = "\\s+| |,|-|<|:";
	private static final String TITLE = "title";
	private static final String CONTENT = "content";
	private static DateTimeZone TIMEZONE = DateTimeZone.forID("UTC");

	private static final int YEAR = 5;
	private static final int END_YEAR = 12;
	private static final int DAY = 3;
	private static final int END_DAY = 10;
	private static final int TIME = 6;
	private static final int END_TIME = 8;
	private static final int END_TIME_DIFF_DAY = 13;
	private static final int HOUR = 0;
	private static final int MINUTES = 1;

	private String[] eventInfoArray;

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * @return
	 * 		the list of child nodes of the root node
	 */
	protected List<Element> parseGetEventsList() {
		eventsRoot = doc.getRootElement();
		return (List<Element>) eventsRoot.getChildren("entry", null);
	}

	/**
	 * 
	 * @return
	 * 		an array of the different elements of the "content" node,
	 * 		split on whitespace
	 */
	protected String[] parseTimeSplitUp(Element element) {
		String timeInfo = element.getChildText(CONTENT, null).toString();
		return timeInfo.split("\n");
	}

	/**
	 * determines whether the event is recurring or one-time
	 * parses appropriately
	 * 	
	 */
	public DateTime parseStartTime(Element time) {
		if (parseTimeSplitUp(time)[0].startsWith("Recurring")) {
			eventInfoArray = parseTimeSplitUp(time)[1].split(split_recur);
			return GoogleRecurringEventXMLParser.parseEventStart(eventInfoArray);
		}
		eventInfoArray = parseTimeSplitUp(time)[0].split(split_one);
		return super.parseStartTime(time);
	}

	/**
	 * 
	 */
	@Override
	protected int parseStartYear(Element time) {
		return parseTimeOfEvent(YEAR, YEAR);
	}

	@Override
	protected int parseStartMonth(Element time) {
		DateTimeFormatter format = DateTimeFormat.forPattern("MMM");
		DateTime tempMonth = format.parseDateTime(eventInfoArray[2]);
		return tempMonth.getMonthOfYear();
	}

	@Override
	protected int parseStartDay(Element time) {
		return parseTimeOfEvent(DAY, DAY);
	}

	private int parseStartHourMinute(int hourMinute) {
		return parseHourMinute(eventInfoArray[6].split(":"), eventInfoArray)[hourMinute];
	}

	protected int parseStartHour24(Element time) {
		return parseStartHourMinute(HOUR);
	}

	protected int parseStartMinute(Element time) {
		return parseStartHourMinute(MINUTES);
	}

	private int[] parseHourMinute(String[] splitTime, String[] timeInfoArray) {
		int[] hourMinute = new int[2];
		if (timeInfoArray.length < 9) {
			hourMinute[HOUR] = 12;
			hourMinute[MINUTES] = 00;
			return hourMinute;
		}

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

	public DateTime parseEndTime(Element time) {
		if (parseTimeSplitUp(time)[0].startsWith("Recurring"))
			return GoogleRecurringEventXMLParser.parseEventEnd(eventInfoArray,
			        parseTimeSplitUp(time)[3]);
		return super.parseEndTime(time);
	}
	@Override
	protected int parseEndYear(Element time) {
		return parseTimeOfEvent(YEAR, END_YEAR);
	}
	@Override
	protected int parseEndMonth(Element time) {
		return parseStartMonth(time);
	}

	@Override
	protected int parseEndDay(Element time) {
		return parseTimeOfEvent(DAY, END_DAY);
	}

	private int parseEndHourMinute(int hourMinute) {
		if (eventInfoArray.length < 9)
			return parseHourMinute(eventInfoArray[TIME].split(":"),
			        eventInfoArray)[hourMinute];
		if (eventInfoArray.length >= 14)
			return parseHourMinute(
			        eventInfoArray[END_TIME_DIFF_DAY].split(":"),
			        eventInfoArray)[hourMinute];
		return parseHourMinute(eventInfoArray[END_TIME].split(":"),
		        eventInfoArray)[hourMinute];
	}
	@Override
	protected int parseEndHour24(Element time) {
		return parseEndHourMinute(HOUR);
	}

	@Override
	protected int parseEndMinute(Element time) {
		return parseEndHourMinute(MINUTES);
	}

	private int parseTimeOfEvent(int start, int end) {
		if (eventInfoArray.length >= 14) {
			return Integer.parseInt(eventInfoArray[end]);
		}
		return Integer.parseInt(eventInfoArray[start]);
	}

//	// gets the year, month, day, hour, minute of a recurring event
//	private int[] parseRecurringEvent(String[] timeInfoArray) {
//
//		int[] DateTimeArray = new int[6];
//
//		for (int j = 0; j < 5; j++) {
//			DateTimeArray[j] = Integer.parseInt(timeInfoArray[j + 3]);
//		}
//		return DateTimeArray;
//	}
//	private DateTime parseRecurringEventStart(String[] timeInfoArray) {
//		int[] DateTimeArray = parseRecurringEvent(timeInfoArray);
//
//		DateTimeZone dateTimeZone = TIMEZONE;
//
//		return new DateTime(DateTimeArray[0], DateTimeArray[1],
//		        DateTimeArray[2], DateTimeArray[3], DateTimeArray[4],
//		        dateTimeZone);
//
//	}
//	private DateTime parseRecurringEventEnd(String[] timeInfoArray,
//	        String duration) {
//		String[] durationArray = duration.split(" ");
//		return parseRecurringEventStart(timeInfoArray).plusMinutes(
//		        Integer.parseInt(durationArray[1]));
//	}
	/**
	 * @return  the title of the event
	 */
	protected String parseTitle(Element event) {
		return event.getChildText(TITLE, null).toString();
	}

	/**
	 * @param
	 * 		event:  the event being parsed
	 * 		finder:  the information you are looking for (ie. "Where" for location)
	 * @return
	 * 		the information of the "content" node pertaining to the 
	 * 		finder (location, descriptions, etc)
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
	 * @return  the description of the event
	 */
	protected String parseDescription(Element event) {
		return parseContent(event, "Event Description:");
	}

	/**
	 * @return  the location of the event
	 */
	protected String parseLocation(Element event) {
		return parseContent(event, "Where:");
	}

	public static void main(String[] args) {
		GoogleXMLParser parser = new GoogleXMLParser();
		parser.loadFile("http://www.cs.duke.edu/courses/cps108/current/assign/02_tivoo/data/googlecal.xml");

		List<Event> listOfEvents = parser.processEvents();

		for (Event event : listOfEvents) {
			System.out.println(event.toString());
		}
	}

	@Override
	protected DateTimeZone parseStartTimeZone(Element event) {
		return DateTimeZone.getDefault();
	}

	@Override
	protected DateTimeZone parseEndTimeZone(Element event) {
		return DateTimeZone.getDefault();
	}

}
