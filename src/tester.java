import java.util.ArrayList;
import org.joda.time.*;

import output.AbstractHtmlOutput;
import output.WeekListHtmlOutput;

public class tester {
    public static void main (String[] args){
        ArrayList<Event> elist = new ArrayList<Event>();
        elist.add(new Event("Event1", new DateTime(2012, 2, 14, 06, 00), 
                new DateTime(2012, 2, 14, 16, 15), "desc1", "loc1"));
        elist.add(new Event("Event2", new DateTime(2012, 2, 16, 06, 00), 
                new DateTime(2012, 2, 16, 8, 15), "desc2", "loc2"));
        
        AbstractHtmlOutput ws = new WeekListHtmlOutput();
        ws.writeEventList(elist);
        
    }

}
