
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.*;

public class Main {
	public static void main(String[] args){
		AbstractXMLParser parser = new DukeXMLParser();
		parser.loadFile("http://www.cs.duke.edu/courses/spring12/cps108/assign/02_tivoo/data/dukecal.xml");
		List<Event> listOfEvents = parser.processEvents();
		
		KeyWordFilter filter = new KeyWordFilter(listOfEvents);
		List<Event> newList = filter.filterByKeyword("");
		
    	AbstractHtmlOutput ho = new WeekListHtmlOutput();
    	ho.writeEventList(newList);
    	
    	System.err.println(newList.get(0).toString());
    	System.err.println("Done!");
	}
}

