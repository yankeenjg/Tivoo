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
	
	public DateTime parseStartTime(Element event){
		int year = parseStartYear(event);	
		int month = parseStartMonth(event);	
		int day = parseStartDay(event);	
		
		int hour24 = parseStartHour24(event);	
		int minute = parseStartMinute(event);	
		
		DateTimeZone timeZone = parseStartTimeZone(event);
		
		return new DateTime(year, month, day, hour24, minute, timeZone);
	}
	
	public DateTime parseEndTime(Element event) {
		int year = parseEndYear(event);
		int month = parseEndMonth(event);
		int day = parseEndDay(event);
		
		int hour24 = parseEndHour24(event);
		int minute = parseEndMinute(event);
		
		DateTimeZone timeZone = parseEndTimeZone(event);
		
		return new DateTime(year, month, day, hour24, minute, timeZone);
	}
	
	protected abstract List<Element> parseGetEventsList();
	
    protected abstract String parseTitle(Element event);
	protected abstract String parseDescription(Element event);
	protected abstract String parseLocation(Element event);

	protected abstract int parseStartYear(Element event);
	protected abstract int parseStartMonth(Element event);
	protected abstract int parseStartDay(Element event);
	protected abstract int parseStartHour24(Element event);
	protected abstract int parseStartMinute(Element event);
	protected abstract DateTimeZone parseStartTimeZone(Element event);
	
	protected abstract int parseEndYear(Element event);
	protected abstract int parseEndMonth(Element event);
	protected abstract int parseEndDay(Element event);
	protected abstract int parseEndHour24(Element event);
	protected abstract int parseEndMinute(Element event);
	protected abstract DateTimeZone parseEndTimeZone(Element event);

	
	//implement this is subclasses
	//public abstract boolean isThisType(String URL);
	
}