package parsing;
import model.Event;

import java.io.File;
import java.util.*;
import org.jdom.*;
import org.jdom.input.*;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public abstract class AbstractXMLParser {
	
	protected Document doc;
	protected Element eventsRoot;
	
	public final void loadFile(File filename){
		try{
			SAXBuilder builder = new SAXBuilder();
			doc = builder.build(filename);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public static AbstractXMLParser chooseParser(File file) {
		return new DukeXMLParser();
	}
		
	public List<Event> processEvents(){
		List<Element> xmlEventsList = parseGetEventsList();
		List<Event> parsedEventsList = new ArrayList<Event>();
		for(Element event : xmlEventsList){
			String eventTitle 		= parseTitle(event);
			String eventDescription = parseDescription(event);
			String eventLocation 	= parseLocation(event);
			DateTime startTime 		= parseStartTime(event);
			DateTime endTime 		= parseEndTime(event);
			boolean allDay			= isAllDay(event);
			HashMap<String,ArrayList<String>> properties = getExtraProperties(event);
			
			Event newEvent = new Event(eventTitle, startTime, endTime, 
					eventDescription, eventLocation, allDay, properties);
			parsedEventsList.add(newEvent);
		}
		
		return parsedEventsList;
	}
	
	protected abstract List<Element> parseGetEventsList();
	
    protected abstract String parseTitle(Element event);
	protected abstract String parseDescription(Element event);
	protected abstract String parseLocation(Element event);

	protected abstract DateTime parseStartTime(Element event);
	protected abstract DateTime parseEndTime(Element event);
	
	protected abstract boolean isAllDay(Element event);
	protected abstract HashMap<String,ArrayList<String>> getExtraProperties(Element event);
	
	//implement this is subclasses
	//public abstract boolean isThisType(String URL);
	
}