package output;
import model.Event;
import java.util.*;

import org.joda.time.*;

import com.hp.gagawa.java.elements.*;

/**
 * Very condensed output that simply displays the title and
 * start time of a list of events sorted by start time
 * @author herio
 *
 */
public class SortedListOutputter extends AbstractHtmlOutputter{

	protected String appendFormatting(List<Event> events, DateTime dt, Body body){ 
        String filepath = "Event_List_from_" + dt.toString("MMMdd");
        
        P p = new P();
        B titleB = new B();
        body.appendChild(titleB, p);
        
        titleB.appendChild(new Text("Event list from "+dt.toString("MM/dd")+" by starting time"));
        
        B b = new B();
        b.appendChild(new Text(dt.toString("MM/dd")+"<br/>"));
        p.appendChild(b);
        
        for(Event e: events){
        	checkNewDay(p, dt, e.getStartTime());
        	U u = new U();
        	u.appendChild(new Text(e.getTitle()));
        	p.appendChild(u);
        	p.appendChild(new Text(" ("+e.getStartTime().toString("HH:mm")+" start)<br/>"));
        }
        
        return filepath;
	}
	
	/**
	 * Writes day headers for each day from the date of the first
	 * event to the second (so one header will be written for events
	 * one day apart, two headers for two days apart, etc.)
	 * @param p P element to which to append
	 * @param first First date
	 * @param second Second date
	 */
	private void checkNewDay(P p, DateTime first, DateTime second){
		if(second.getYear()>=first.getYear() && second.getDayOfYear()>first.getDayOfYear()){
			while(!isSameDate(first, second)){
				first = first.plusDays(1);
				B b = new B();
				b.appendChild(new Text(first.toString("MM/dd")+"<br/>"));
				p.appendChild(b);
			}
		}
	}
}
