package parsing;
import model.Event;
import java.util.*;
import org.jdom.*;
import org.jdom.input.*;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public abstract class AbstractXMLParser {
	
	protected Document doc;
	protected Element eventsRoot;
	
	public final void loadFile(String filename){
		try{
			SAXBuilder builder = new SAXBuilder();
			doc = builder.build(filename);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
/*	public chooseParser() {
		//TODO: Implement this method
	}*/
		
	public List<Event> processEvents(){
		
		List<Element> xmlEventsList = parseGetEventsList();
		List<Event> parsedEventsList = new ArrayList<Event>();
		for(Element event : xmlEventsList){
			String eventTitle 		= parseTitle(event);
			String eventDescription = parseDescription(event);
			String eventLocation 	= parseLocation(event);
			DateTime startTime 		= parseStartTime(event);
			DateTime endTime 		= parseEndTime(event);
						
			Event newEvent = new Event(eventTitle, startTime, endTime, eventDescription, eventLocation, false);
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
	
	
	//implement this is subclasses
	//public abstract boolean isThisType(String URL);
	
}