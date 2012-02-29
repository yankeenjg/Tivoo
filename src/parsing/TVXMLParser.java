package parsing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.Event;

import org.jdom.Element;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

public class TVXMLParser extends AbstractXMLParser {

	/**
	 * Labels for specific nodes in the event tree
	 */
	private String EVENT_NODE = "programme";
	private String NAME = 		"title"; 
	private String DESCRIPTION ="desc";
	private String START_TIME = "start";
	private String END_TIME = 	"stop"; 
	private String LOCATION = 	"channel";
	private String CREDITS 	=	"credits";
	private String RATING	=   "rating";
	
	/**
	 * Gets the root node that contains all event nodes
	 */
	@Override
	protected List<Element> parseGetEventsList(){
		Element eventsRoot = doc.getRootElement();
		System.err.println("HAHA");//+eventsRoot.getName());
		return eventsRoot.getChildren(EVENT_NODE);
	}

	/**
	 * Gets the title of the event, given an event node
	 */
	@Override
	protected String parseTitle(Element event) {
		return event.getChildText(NAME);
	}

	/**
	 * Gets the description of the event, given an event node
	 */
	@Override
	protected String parseDescription(Element event) {
		return event.getChildText(DESCRIPTION);
	}

	/**
	 * Gets the location (channel) of the event, given an event node
	 */
	@Override
	protected String parseLocation(Element event) {
		return event.getAttributeValue(LOCATION);
	}

	/**
	 * Gets the start time node and calls parseTime()
	 * to parse it
	 */
	@Override
	protected DateTime parseStartTime(Element event) {
		return parseTime(event, START_TIME);
	}

	/**
	 * Gets the end time node and calls parseTime()
	 * to parse it
	 */
	@Override
	protected DateTime parseEndTime(Element event) {
		return parseTime(event, END_TIME);
	}
	
	protected DateTime parseTime(Element event, String attrib){
		DateTimeFormatter dtparser = DateTimeFormat.forPattern("yyyyMMddHHmmss Z");
		String utcdate = event.getAttributeValue(attrib);
		return dtparser.parseDateTime(utcdate);
	}
	
	protected HashMap<String,ArrayList<String>> getExtraProperties(Element event){
		HashMap<String,ArrayList<String>> map = new HashMap<String,ArrayList<String>>();
		
		addCreditsToMap(event,map);
		addRatingToMap(event,map);
		
		return map;
	}
	
	protected void addCreditsToMap(Element event, HashMap<String,ArrayList<String>> map){
		List<Element> creditsList;
		try{
		    creditsList = event.getChild(CREDITS).getChildren();
		} catch (NullPointerException e){
			return;
		}
		
		for(Element credit : creditsList){
			String creditName = credit.getName();
			String creditValue = credit.getText();
			if(!map.containsKey(creditName))
				map.put(creditName, new ArrayList<String>());
			map.get(creditName).add(creditValue);
		}
	}
	
	protected void addRatingToMap(Element event, HashMap<String,ArrayList<String>> map){
		if(!map.containsKey(RATING))
			map.put(RATING, new ArrayList<String>() );
		String ratingValue;
		try{
			ratingValue = event.getChild(RATING).getChildText("value");
			map.get(RATING).add(ratingValue);
		} catch (NullPointerException e){
			return;
		}
	}

	@Override
	protected boolean isAllDay(Element event) {
		// XMLTV does not support all day events
		return false;
	}
	
	/*public static void main (String[] args){
		TVXMLParser parser = new TVXMLParser();
		parser.loadFile("http://dl.dropbox.com/u/5156866/tv.xml");
		System.err.println("done loading!");
		List<Event> listOfEvents = parser.processEvents();
		
		for(Event event : listOfEvents){
			System.out.println(event.toString());
		}
	}*/
}
