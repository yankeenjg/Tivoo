package parsing;

import model.*;
import java.util.*;
import org.jdom.*;
import org.jdom.input.*;
import org.joda.time.DateTime;

public abstract class AbstractXMLParser {

	protected Document doc;
	protected Element eventsRoot;

	public final void loadFile(String filename) {
		try {
			SAXBuilder builder = new SAXBuilder();
			doc = builder.build(filename);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates a array of all the child nodes of the document
	 * 
	 * @return a new array of parsed Events containing information such as
	 *         title, description, location, start time, and end time
	 */
	public List<Event> processEvents() {

		List<Element> xmlEventsList = parseGetEventsList();
		List<Event> parsedEventsList = new ArrayList<Event>();
		try {
			for (Element event : xmlEventsList) {
				String eventTitle = parseTitle(event);
				String eventDescription = parseDescription(event);
				String eventLocation = parseLocation(event);
				DateTime startTime = parseStartTime(event);
				DateTime endTime = parseEndTime(event);
				HashMap<String, ArrayList<String>> properties = getExtraProperties(event);

				Event newEvent = new Event(eventTitle, startTime, endTime,
				        eventDescription, eventLocation, properties);
				parsedEventsList.add(newEvent);
			}
		} catch (NullPointerException e) {
			String errorMessage = "Wrong parser: " + this.getClass().getName()
			        + "for file: " + doc.getBaseURI();
			throw new ParserException(e.getMessage(),
			        ParserException.Type.WRONG_TYPE);
		}

		return parsedEventsList;
	}

	/**
	 * @return a list of the child elements of the root node
	 */
	protected abstract List<Element> parseGetEventsList();

	/**
	 * @param an
	 *            event element
	 * @return a String title of the event
	 */
	protected abstract String parseTitle(Element event);

	/**
	 * @param an
	 *            event element
	 * @return a String description of the event
	 */
	protected abstract String parseDescription(Element event);

	/**
	 * @param an
	 *            event element
	 * @return a String location of the event
	 */
	protected abstract String parseLocation(Element event);

	/**
	 * @param an
	 *            event element
	 * @return a DateTime start time of the event
	 */
	protected abstract DateTime parseStartTime(Element event);

	/**
	 * @param an
	 *            event element
	 * @return a DateTime end time of the event
	 */
	protected abstract DateTime parseEndTime(Element event);

	/**
	 * Parses extra properties relevant to the specific type
	 * of events only and puts them in a HashMap
	 * @return map
	 */
	protected HashMap<String, ArrayList<String>> getExtraProperties(
	        Element event) {
		return null;
	}
}