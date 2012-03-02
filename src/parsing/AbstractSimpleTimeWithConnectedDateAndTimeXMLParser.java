package parsing;

import org.jdom.Element;
import org.joda.time.DateTime;

public class AbstractSimpleTimeWithConnectedDateAndTimeXMLParser extends
        AbstractSimpleTimeXMLParser {

	private String myStart;
	private String myEnd;
	/**
	 * constructs and instance of the AbstractSimpleTimeXMLParser with the
	 * parameters ROOT_NODE, EVENT_NODE, TITLE, DESCRIPTION, and LOCATION
	 */
	protected AbstractSimpleTimeWithConnectedDateAndTimeXMLParser(
	        String rootNode, String eventNode, String title,
	        String description, String location, String dateTimePattern,
	        String start, String end) {
		super(rootNode, eventNode, title, description, location,
				dateTimePattern);
		myStart = start;
		myEnd = end;
	}

	@Override
	protected DateTime parseStartTime(Element event) {
		return parseTime(event, myDateTimePattern, myStart);
	}
	@Override
	protected DateTime parseEndTime(Element event) {
		return parseTime(event, myDateTimePattern, myEnd);
	}
}
