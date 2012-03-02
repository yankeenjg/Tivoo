package parsing;

import java.util.List;


import org.jdom.Element;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public abstract class AbstractSimpleTimeXMLParser extends AbstractXMLParser {

	private String myTitle;
	private String myDescription;
	private String myLocation;
	private String myDateTimePattern;

	public AbstractSimpleTimeXMLParser(String rootNode, String eventNode,
			String title, String description, String location,
			String dateTimePattern){
		super(rootNode,eventNode);
		myTitle = title;
		myDescription = description;
		myLocation = location;
		myDateTimePattern = dateTimePattern;
	}

	@Override
	protected String parseTitle(Element event) {
		return parseInformation(event, myTitle);
	}

	@Override
	protected String parseDescription(Element event) {
		return parseInformation(event, myDescription);
	}

	@Override
	protected String parseLocation(Element event) {
		return parseInformation(event, myLocation);
	}
	@Override
	protected DateTime parseStartTime(Element event) {
		return parseTime(event, myDateTimePattern, myStart);
	}
	@Override
	protected DateTime parseEndTime(Element event) {
		return parseTime(event, myDateTimePattern, myEnd);
	}
	/**
	 * @param event
	 *            , the event being parsed
	 * @param keyword
	 *            , the part of the event you are parsing (ie title,
	 *            description, location)
	 * @return the String of information according to what the keyword is
	 */
	private String parseInformation(Element event, String keyword) {
		String information = event.getChildText(keyword);
		if (information != null)
			return information;
		else
			throw new NullPointerException("Couldn't find node: " + information);
	}

	/**
	 * converts the given structure of date and time into the format of a
	 * DateTime
	 * 
	 * @return a DateTime
	 */
	protected DateTime parseTime(Element time, String attrib) {
		DateTimeFormatter dtparser = DateTimeFormat.forPattern(myDateTimePattern);
		String timestamp = getTimestamp(time);time.getChildText(attrib);
		return dtparser.parseDateTime(utcdate);
	}


}
