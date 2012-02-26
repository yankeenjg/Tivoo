package parsing;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.sun.java.util.jar.pack.Attribute.Layout.Element;

public class GoogleRecurringEventXMLParser {

	private static String[] startTime;
	private static String[] endTime;
	private static final String split_recur = "\\s+| |,|-|<|:";

	private static DateTimeZone TIMEZONE = DateTimeZone.forID("UTC");

	protected static DateTime parseTime(Element time) {
		String[] startEnd = parseTimeSplitUp(time)[1].split("to");
		startTime = startEnd[0].split(split_recur);
		endTime = startEnd[1].split(split_recur);
		return parseEventStart(time);
	}
	
	// gets the year, month, day, hour, minute of a recurring event
	protected static int[] parseRecurringEvent(String[] timeInfoArray) {

		int[] DateTimeArray = new int[6];

		for (int j = 0; j < 5; j++) {
			DateTimeArray[j] = Integer.parseInt(timeInfoArray[j + 3]);
		}
		return DateTimeArray;
	}
	
	protected static DateTime parseEventStart(String[] timeInfoArray) {
		int[] DateTimeArray = parseRecurringEvent(timeInfoArray);

		DateTimeZone dateTimeZone = TIMEZONE;

		return new DateTime(DateTimeArray[0], DateTimeArray[1],
		        DateTimeArray[2], DateTimeArray[3], DateTimeArray[4],
		        dateTimeZone);

	}
	protected static DateTime parseEventEnd(String[] timeInfoArray,
	        String duration) {
		String[] durationArray = duration.split(" ");
		return parseEventStart(timeInfoArray).plusMinutes(
		        Integer.parseInt(durationArray[1]));
	}
	
}
