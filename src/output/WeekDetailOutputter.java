package output;

import model.Event;
import java.util.*;

import org.joda.time.*;

import com.hp.gagawa.java.Node;
import com.hp.gagawa.java.elements.*;

/**
 * Condensed calendar output for a single week
 * The week in question is the 7 day period starting from
 * the earliest event in the given list
 * @author herio
 *
 */
public class WeekDetailOutputter extends DayDetailOutputter{
    
	@Override
	protected String appendFormatting(List<Event> events, DateTime dt, Body body){
        String filepath = "WeekDetail_of_" + dt.toString("MMMdd");
        
        Table table = new Table();
        table.setBorder("");
        body.appendChild(table);
        
        Tr row0 = new Tr();
        row0.appendChild(new Text("Week of "+dt.toString("MMM dd, YYYY")));
        Tr row1 = new Tr();
        Tr row2 = new Tr();
        row2.setValign("top");
        
        //header row
        for(int i=0; i<DateTimeConstants.DAYS_PER_WEEK; i++){
        	Td dayHeader = new Td();
        	dayHeader.setWidth("1%");
        	B b = new B();
        	Div div = new Div();
        	div.setStyle("text-align:center");
        	div.appendChild(b);
        	dayHeader.appendChild(div);
        	row1.appendChild(dayHeader);
        	b.appendChild(new Text(dt.dayOfWeek().getAsText()));
        	
        	dt = dt.plusDays(1);
        }
        dt = dt.minusDays(DateTimeConstants.DAYS_PER_WEEK);
        
        createCalendarCells(events, dt, row2, filepath);

        table.appendChild(row0, row1, row2);
        return filepath;
    }

	@Override
	protected void createCalendarCells(List<Event> events, DateTime dt, Node row2, String filepath) {
		for(int i=0; i<DateTimeConstants.DAYS_PER_WEEK; i++){
        	Td evs = new Td();
        	P p = new P();
        	evs.appendChild(p);
        	super.createCalendarCells(events, dt, p, filepath);
        	
        	((Tr) row2).appendChild(evs);
        	
        	dt = dt.plusDays(1);
        }
	}
}
