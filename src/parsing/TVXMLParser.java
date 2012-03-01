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

public class TVXMLParser extends AbstractIntermediateXMLParser {

	/**
	 * Labels for specific nodes in the event tree
	 */
	private String START_TIME = "start";
	private String END_TIME = 	"stop"; 
	private String LOCATION = 	"channel";
	private String CREDITS 	=	"credits";
	private String RATING	=   "rating";
	
	public TVXMLParser(){
		// Assigns names of tags for EVENT_NODE, TITLE, DESCRIPTION, but LOCATION is overridden
		//TODO fix first arg
		super("BLABA", "programme", "title", "desc", null);
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
	
	@Override
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
	
	public static void main (String[] args){
		TVXMLParser parser = new TVXMLParser();
		parser.loadFile("http://dl.dropbox.com/u/5156866/tv.xml");
		System.err.println("done loading!");
		List<Event> listOfEvents = parser.processEvents();
		
		for(Event event : listOfEvents){
			System.out.println(event.toString());
		}
	}
}
