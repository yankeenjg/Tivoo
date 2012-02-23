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
						
			Event newEvent = new Event(eventTitle, startTime, endTime,
										eventDescription, eventLocation);
			parsedEventsList.add(newEvent);
		}
		
		return parsedEventsList;
	}
	
	public DateTime parseStartTime(Element time){
		int year = parseStartYear(time);	
		int month = parseStartMonth(time);	
		int day = parseStartDay(time);	
		
		int hour24 = parseStartHour24(time);	
		int minute = parseStartMinute(time);	
		
		DateTimeZone timeZone = parseTimeZone(time);
		
		return new DateTime(year, month, day, hour24, minute, timeZone);
	}
	
	public DateTime parseEndTime(Element time) {
		int year = parseEndYear(time);
		int month = parseEndMonth(time);
		int day = parseEndDay(time);
		
		int hour24 = parseEndHour24(time);
		int minute = parseEndMinute(time);
		
		DateTimeZone timeZone = parseTimeZone(time);
		
		return new DateTime(year, month, day, hour24, minute, timeZone);
	}
	
	protected abstract List<Element> parseGetEventsList();
	
    protected abstract String parseTitle(Element event);
	protected abstract String parseDescription(Element event);
	protected abstract String parseLocation(Element event);

	protected abstract int parseStartYear(Element time);
	protected abstract int parseStartMonth(Element time);
	protected abstract int parseStartDay(Element time);
	protected abstract int parseStartHour24(Element time);
	protected abstract int parseStartMinute(Element time);
	protected abstract DateTimeZone parseTimeZone(Element time);
	
	protected abstract int parseEndYear(Element time);
	protected abstract int parseEndMonth(Element time);
	protected abstract int parseEndDay(Element time);
	protected abstract int parseEndHour24(Element time);
	protected abstract int parseEndMinute(Element time);
	
	//implement this is subclasses
	//public abstract boolean isThisType(String URL);
	
}