package output;
import java.util.*;

import model.Event;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

import com.hp.gagawa.java.Node;
import com.hp.gagawa.java.elements.*;

/**
 * Condensed calendar output for a single month
 * The month in question is the month of the earliest
 * element in the given list
 * @author herio
 *
 */
public class MonthDetailOutputter extends WeekDetailOutputter{
	
	@Override
	protected String appendFormatting(List<Event> events, DateTime dt, Body body){
        String filepath = "MonthDetail_of_" + dt.toString("MMMYYYY");
        
        Table table = new Table();
        table.setBorder("");
        body.appendChild(table);
        
        Tr row0 = new Tr();
        row0.appendChild(new Text(dt.toString("MMMM YYYY")));
        table.appendChild(row0);
        
        int thisMonth = dt.getMonthOfYear();  
        dt = dt.minusDays(dt.getDayOfWeek()-1);
        while(dt.getMonthOfYear()==thisMonth)
        	dt = dt.minusWeeks(1);
        
        Tr row1 = new Tr();
        DateTime dummy = new DateTime(dt);
        for(int i=0;i<DateTimeConstants.DAYS_PER_WEEK;i++){
        	Td DoW = new Td();
        	DoW.setWidth("1%");
        	B b = new B();
        	Div div = new Div();
        	div.setStyle("text-align:center");
        	b.appendChild(new Text(dummy.dayOfWeek().getAsText()));
        	div.appendChild(b);
        	DoW.appendChild(div);
        	row1.appendChild(DoW);
        	dummy = dummy.plusDays(1);
        }
        table.appendChild(row1);
        
        createCalendarCells(events, dt, table, filepath);
        return filepath;
    }

	@Override
	protected void createCalendarCells(List<Event> events, DateTime dt, Node table, String filepath) {
		int thisMonth = dt.plusMonths(1).getMonthOfYear();
		do{
        	Tr rown = new Tr();
        	rown.setValign("top");
        	super.createCalendarCells(events, dt, rown, filepath);
        	
        	((Table)table).appendChild(rown);
        	dt = dt.plusWeeks(1);
        }while(dt.getMonthOfYear()==thisMonth);
	}
}
