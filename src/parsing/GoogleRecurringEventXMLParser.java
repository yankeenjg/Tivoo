package parsing;

import org.joda.time.DateTime;

import org.joda.time.DateTimeZone;

public class GoogleRecurringEventXMLParser {

	private static DateTimeZone TIMEZONE = DateTimeZone.forID("UTC");

	// gets the year, month, day, hour, minute of a recurring event
	public static int[] parseTime(String[] timeInfoArray) {
		int[] DateTimeArray = new int[6];

		for (int j = 0; j < 5; j++) {
			DateTimeArray[j] = Integer.parseInt(timeInfoArray[j + 3]);
		}
		return DateTimeArray;
	}

	/**
	 * Returns a DateTime of the start time, given an array of the start time
	 * information
	 */
	protected static DateTime parseEventStart(String[] timeInfoArray) {
		int[] DateTimeArray = parseTime(timeInfoArray);

		DateTimeZone dateTimeZone = TIMEZONE;

		return new DateTime(DateTimeArray[0], DateTimeArray[1],
		        DateTimeArray[2], DateTimeArray[3], DateTimeArray[4],
		        dateTimeZone);
	}

	/**
	 * Returns a DateTime of the end time, given an array of the start time
	 * information
	 */
	protected static DateTime parseEventEnd(String[] timeInfoArray,
	        String duration) {
		String[] durationArray = duration.split(" ");
		return parseEventStart(timeInfoArray).plusMinutes(
		        Integer.parseInt(durationArray[1]));
	}

}
