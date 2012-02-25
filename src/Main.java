
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.*;

import output.AbstractHtmlOutput;
import output.WeekListHtmlOutput;

import parsing.AbstractXMLParser;
import parsing.DukeXMLParser;
import processing.KeyWordFilter;

public class Main {
	public static void main(String[] args){
		AbstractXMLParser parser = new DukeXMLParser();
		parser.loadFile("http://www.cs.duke.edu/courses/spring12/cps108/assign/02_tivoo/data/dukecal.xml");
		List<Event> listOfEvents = parser.processEvents();
		KeyWordFilter filter = new KeyWordFilter();
		List<Event> newList = filter.filterByKeyword(listOfEvents, "Catholic");
		
    	AbstractHtmlOutput ho = new WeekListHtmlOutput();
    	ho.writeEventList(newList);
    	
    	for (Event event : newList) {
    		System.err.println(event.getTitle());
    	}
    	System.err.println("Done!");
	}
}

