import model.Event;
import java.util.List;
import output.*;
import processing.*;
import parsing.*;

public class Main {
	public static void main(String[] args){
		AbstractXMLParser parser = new DukeXMLParser();
		parser.loadFile("http://www.cs.duke.edu/courses/spring12/cps108/assign/02_tivoo/data/dukecal.xml");
		List<Event> listOfEvents = parser.processEvents();
		ContainsKeywordsFilter filter = new ContainsKeywordsFilter();
		List<Event> newList = filter.filterByKeywords(listOfEvents, "Catholic");               
    	AbstractHtmlOutput ho = new WeekListHtmlOutput();document
    	ho.writeEventList(newList);
    	
    	for (Event event : newList) {
    		System.err.println(event.getTitle());
    	}
    	System.err.println("Done!");
	}
}

