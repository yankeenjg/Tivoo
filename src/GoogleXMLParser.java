import java.util.ArrayList;
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
	
	
//	protected void parseTimezone() {
//
//		 //System.out.println(eventsRoot.getChildText("gCal:timezone"));
//		Element timeZone = eventsRoot.getChild("gCal:timezone", eventsRoot.getNamespace("http://schemas.google.com/gCal/2005"));
//		 System.out.println(timeZone);
//		if (timeZone.getChildText("islocal").equals("true"))
//			TIMEZONE = DateTimeZone.getDefault();
//		else
//			TIMEZONE = DateTimeZone.forID(timeZone.getChildText("id"));
//	}

	private Element parseGetEventsRoot() {
		eventsRoot = doc.getRootElement();
		for (Object e : eventsRoot.getChildren())
			System.out.println(e);
		return eventsRoot;
	}

	protected String[] parseTimeType(Element element) {
		String timeInfo = element.getChildText(CONTENT, null).toString();
		return timeInfo.split("\n");
	}
		
//	public DateTime parseTime(Element element) {
//		String[] timeInfoArray = parseTimeType(element);
//		if (timeInfoArray[0].startsWith("Recurring")) {
//			return parseTimeRecurring(element);
//		}
//		return super.parseTime(element);
//	}
	
//	protected DateTime parseStartTime(Element element) {
//		String[] timeInfoArray = parseTimeType(element);
//
//		if (timeInfoArray[0].startsWith("Recurring")) {
//			return parseRecurringEventStart(timeInfoArray[1].split(split_recur));
//		}
//		return parseOneTimeEventStart(timeInfoArray[0].split(split_one));
//	}

	
	public DateTime parseStartTime(Element time) {
		if (parseTimeType(time)[0].startsWith("Recurring"))
			return parseRecurringStartTime(time);
		return super.parseStartTime(time);
	}

	
	private DateTime parseOneTimeEventStart(String[] timeInfoArray) {

		int[] yearMonthDay = parseOneTimeEvent(timeInfoArray);

		DateTimeZone dateTimeZone = TIMEZONE; // /fix this
		String startTime = timeInfoArray[6];
		String[] splitTime = startTime.split(":");

		int[] hourMinute = parseHourMinute(splitTime, timeInfoArray);

		return new DateTime(yearMonthDay[0], yearMonthDay[1], yearMonthDay[2],
		        hourMinute[0], hourMinute[1], dateTimeZone);
	}
	
	@Override
	protected int parseStartYear(Element time) {
		return Integer.parseInt(parseTimeType(time)[0].split(split_one)[5]);
	}
	
	@Override 
	protected int parseStartMonth(Element time) {
		DateTimeFormatter format = DateTimeFormat.forPattern("MMM");
		DateTime tempMonth = format.parseDateTime(parseTimeType(time)[0].split(split_one)[2]);
		return tempMonth.getMonthOfYear();
	}
	
	@Override
	protected int parseStartDay(Element time) {
		return Integer.parseInt(parseTimeType(time)[0].split(split_one)[3]);
	}
	
	private int[] parseOneTimeEvent(Element time) {
		int[] yearMonthDay = new int[3];
		yearMonthDay[0] = parseStartYear(time);
		yearMonthDay[1] = parseStartMonth(time);
		yearMonthDay[2] = parseStartDay(time);
		return yearMonthDay;
	}
	
	
	private DateTime parseOneTimeEventEnd(String[] timeInfoArray) {
		DateTimeZone dateTimeZone = TIMEZONE; // /fix this
		if (timeInfoArray.length < 9){
			return parseOneTimeEventStart(timeInfoArray);
		}
		
		if (timeInfoArray.length >= 14) {
			int year = Integer.parseInt(timeInfoArray[12]);
			DateTimeFormatter format = DateTimeFormat.forPattern("MMM");
			DateTime tempMonth = format.parseDateTime(timeInfoArray[9]);
			int month = tempMonth.getMonthOfYear();
			int day = Integer.parseInt(timeInfoArray[10]);

			String endTime = timeInfoArray[13];
			String[] splitTime = endTime.split(":");

			int[] hourMinute = parseHourMinute(splitTime, timeInfoArray);

			return new DateTime(year, month, day, hourMinute[0], hourMinute[1],
			        dateTimeZone);
		}
		int[] yearMonthDay = parseOneTimeEvent(timeInfoArray);

		//System.out.println(timeInfoArray[8]);
		String endTime = timeInfoArray[8];
		String[] splitTime = endTime.split(":");
		int[] hourMinute = parseHourMinute(splitTime, timeInfoArray);

		return new DateTime(yearMonthDay[0], yearMonthDay[1], yearMonthDay[2],
		        hourMinute[0], hourMinute[1], dateTimeZone);

	}


	private int[] parseHourMinute(String[] splitTime, String[] timeInfoArray) {
		int[] hourMinute = new int[2];
		if (timeInfoArray.length < 9) {
			hourMinute[0] = 12;
			hourMinute[1] = 00;
			return hourMinute;
		}

		if (splitTime.length == 2) {
			hourMinute[0] = Integer.parseInt(splitTime[0]);
			hourMinute[1] = Integer.parseInt(splitTime[1].substring(0,
			       splitTime[1].indexOf('m')-1));
		}

		else {
			
			hourMinute[0] = Integer.parseInt(splitTime[0].trim().substring(0,
			        splitTime[0].indexOf('m')-1));
			hourMinute[1] = 00;
		}
		return hourMinute;
	}
	
	protected DateTime parseEndTime(Element element) {
		String[] timeInfoArray = parseTimeType(element);
		
		if (timeInfoArray[0].startsWith("Recurring")) {
			return parseRecurringEventEnd(timeInfoArray[1].split(split_recur),
			        timeInfoArray[3]);
		}
		return parseOneTimeEventEnd(timeInfoArray[0].split(split_one));
	}

	//gets the year, month, day, hour, minute of a recurring event
	private int[] parseRecurringEvent(String[] timeInfoArray) {

		int[] DateTimeArray = new int[6];

		for (int j = 0; j < 5; j++) {
			DateTimeArray[j] = Integer.parseInt(timeInfoArray[j + 3]);
		}
		return DateTimeArray;
	}
	
	private DateTime parseRecurringEventStart(String[] timeInfoArray) {
		int[] DateTimeArray = parseRecurringEvent(timeInfoArray);

		DateTimeZone dateTimeZone = TIMEZONE;

		return new DateTime(DateTimeArray[0], DateTimeArray[1],
		        DateTimeArray[2], DateTimeArray[3], DateTimeArray[4],
		        dateTimeZone);

	}
	private DateTime parseRecurringEventEnd(String[] timeInfoArray,
	        String duration) {
		String[] durationArray = duration.split(" ");
		return parseRecurringEventStart(timeInfoArray).plusMinutes(
		        Integer.parseInt(durationArray[1]));
	}
	
	
	


	protected String parseTitle(Element event) {
		return event.getChildText(TITLE, null).toString();
	}

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

	protected String parseDescription(Element event) {
		return parseContent(event, "Event Description:");
	}

	protected String parseLocation(Element event) {
		return parseContent(event, "Where:");
	}

	@Override
	public List<Event> processEvents() {
		parseGetEventsRoot();
		//parseTimezone();

		@SuppressWarnings("unchecked")
		List<Element> XMLChildren = (List<Element>) eventsRoot.getChildren(
		        "entry", null);

		List<Event> newXMLChildren = new ArrayList<Event>();

		for (Element event : XMLChildren) {

			String title = parseTitle(event);
			String description = parseDescription(event);
			String location = parseLocation(event);
			DateTime startTime = parseStartTime(event);
			DateTime endTime = parseEndTime(event);
			Event newEvent = new Event(title, startTime, endTime, description,
			        location);

			System.out.println(title);

			newXMLChildren.add(newEvent);
		}

		return newXMLChildren;
	}

//	public static XMLParserFactory getFactory() {
//		return new XMLParserFactory(new GoogleXMLParser());
//	}

	public static void main(String[] args) {
		GoogleXMLParser parser = new GoogleXMLParser();
		parser.loadFile("http://www.cs.duke.edu/courses/cps108/current/assign/02_tivoo/data/googlecal.xml");
		//parser.loadFile("https://www.google.com/calendar/feeds/kathleen.oshima%40gmail.com/private-cf4e2a2cf06315dece847f9aaf867f3e/basic");
		List<Event> listOfEvents = parser.processEvents();

		for (Event event : listOfEvents) {
			System.out.println(event.toString());
		}
	}




	@Override
    protected DateTimeZone parseTimeZone(Element time) {
	    // TODO Auto-generated method stub
	    return null;
    }

	@Override
    protected List<Element> parseGetEventsList() {
	    // TODO Auto-generated method stub
	    return null;
    }




	@Override
    protected int parseStartHour24(Element time) {
	    // TODO Auto-generated method stub
	    return 0;
    }

	@Override
    protected int parseStartMinute(Element time) {
	    // TODO Auto-generated method stub
	    return 0;
    }

}
