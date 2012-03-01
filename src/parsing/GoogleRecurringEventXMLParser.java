package parsing;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class GoogleRecurringEventXMLParser {

	public static DateTime parseEventStart(String timeInfo) {
		DateTimeFormatter formatter = DateTimeFormat
		        .forPattern("yyyy-MM-dd HH:mm:ss");
		return formatter.parseDateTime(timeInfo);	
		
	}
	public static DateTime parseEventEnd(String timeInfo,
	        String duration) {
		String[] durationArray = duration.split("\\s");
		return parseEventStart(timeInfo).plusMinutes(
		        Integer.parseInt(durationArray[2]));
	}
}
