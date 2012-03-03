package output;
import java.util.*;

import model.Event;
import org.joda.time.DateTime;
import com.hp.gagawa.java.*;
import com.hp.gagawa.java.elements.*;

/**
 * Condensed calendar output for a single day
 * The day in question is the day of the earliest event
 * in the given list
 * @author herio
 *
 */
public class DayDetailOutputter extends DetailOutputter{

	protected String appendFormatting(List<Event> events, DateTime dt, Body body){
		String filepath = "DayDetail_of_" + dt.toString("MMMdd");
		
        H4 h4 = new H4();
        h4.appendChild(new Text(dt.toString("MMM dd, YYYY")));
        body.appendChild(h4);
        
        P p = new P();
        body.appendChild(p);
        createCalendarCells(events, dt, p, filepath);
        
        return filepath;
    }
	
	@Override
	protected void createCalendarCells(List<Event> events, DateTime dt, Node n, String filepath){
		super.createCalendarCells(events, dt, n, filepath);
	}
}
