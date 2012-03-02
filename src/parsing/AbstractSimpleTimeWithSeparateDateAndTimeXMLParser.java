package parsing;

import org.jdom.Element;
import org.joda.time.DateTime;

public class AbstractSimpleTimeWithSeparateDateAndTimeXMLParser extends
        AbstractSimpleTimeXMLParser {

	private String myStartDate;
	private String myStartTime;
	private String myEndDate;
	private String myEndTime;

	protected AbstractSimpleTimeWithSeparateDateAndTimeXMLParser(
	        String rootNode, String eventNode, String title,
	        String description, String location, String dateTimePattern,
	        String startDate, String startTime, String endDate, String endTime) {
		super(rootNode, eventNode, title, description, location, dateTimePattern);
		myStartDate = startDate;
		myStartTime = startTime;
		myEndDate = endDate;
		myEndTime = endTime;
	}

	/**
	 * Merges the date and time elements of an event,
	 * 
	 * @return a String of the combined information
	 */
	private String mergeDateAndTime(Element event, String dateTag,
	        String timeTag) {
		String date = event.getChildText(dateTag);
		String time = event.getChildText(timeTag);
		return date + time.split(" ")[0];
	}

	@Override
	protected DateTime parseStartTime(Element event) {
		String timestamp = mergeDateAndTime(event, myStartDate, myStartTime);
		return super.parseTime(event, myDateTimePattern, timestamp);
	}

	@Override
	protected DateTime parseEndTime(Element event) {
		String timestamp = mergeDateAndTime(event, myEndDate, myEndTime);
		return super.parseTime(event, myDateTimePattern, timestamp);

	}
}
