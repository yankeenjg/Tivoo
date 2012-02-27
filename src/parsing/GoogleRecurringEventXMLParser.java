package parsing;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class GoogleRecurringEventXMLParser {
	private static DateTimeZone TIMEZONE = DateTimeZone.forID("UTC");

	
	// gets the year, month, day, hour, minute of a recurring event
	private static int[] parseRecurringEvent(String[] timeInfoArray) {

		int[] DateTimeArray = new int[6];

		for (int j = 0; j < 5; j++) {
			DateTimeArray[j] = Integer.parseInt(timeInfoArray[j + 3]);
		}
		return DateTimeArray;
	}
	public static DateTime parseEventStart(String[] timeInfoArray) {
		int[] DateTimeArray = parseRecurringEvent(timeInfoArray);

		DateTimeZone dateTimeZone = TIMEZONE;

		return new DateTime(DateTimeArray[0], DateTimeArray[1],
		        DateTimeArray[2], DateTimeArray[3], DateTimeArray[4],
		        dateTimeZone);

	}
	public DateTime parseEventEnd(String[] timeInfoArray,
	        String duration) {
		String[] durationArray = duration.split(" ");
		return parseEventStart(timeInfoArray).plusMinutes(
		        Integer.parseInt(durationArray[1]));
	}
}
