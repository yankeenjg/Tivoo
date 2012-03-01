import model.*;
import output.*;
import parsing.*;
import filtering.*;

import java.util.*;


public class Main {
	public static void main(String[] args){
		//AbstractXMLParser parser = new DukeXMLParser();
		//parser.loadFile("http://www.cs.duke.edu/courses/spring12/cps108/assign/02_tivoo/data/dukecal.xml");
		
		List<AbstractXMLParser> parsers = new ArrayList<AbstractXMLParser>();
		
		parsers.add(new DukeBasketBallXMLParser());
		parsers.add(new DukeXMLParser());
		parsers.add(new TVXMLParser());
		parsers.add(new NFLXMLParser());
		parsers.add(new GoogleXMLParser());

		
		List<Event> listOfEvents = null;
		for(AbstractXMLParser parser : parsers){
			try{
				//parser.loadFile("http://duke.edu/~jjh38/tv.xml");
				parser.loadFile("http://www.cs.duke.edu/courses/spring12/cps108/assign/02_tivoo/data/dukecal.xml");
				listOfEvents = parser.processEvents();
			} catch (ParserException e){
				System.err.println(e.getMessage());
				continue;
			}
			if(listOfEvents != null && listOfEvents.size() != 0)
				break;
		}
		AbstractFilter filter = new KeywordFilter();
		List<Event> newList = filter.filter(listOfEvents, "");               
    	AbstractHtmlOutputter ho = new WeekDetailOutputter();
    	ho.writeEvents(newList);
    	
    	for (Event event : newList) {
    		System.err.println(event.getTitle());
    	}
    	System.err.println("Done!");
	}
}

