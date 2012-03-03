package output;
import model.Event;
import java.util.*;

import org.joda.time.*;

import com.hp.gagawa.java.elements.*;

import filtering.ConflictFilter;

/**
 * Provides a view of all events in a list and all other
 * events in that list that conflict in time
 * @author herio
 *
 */
public class ConflictOutputter extends AbstractHtmlOutputter{

	protected String appendFormatting(List<Event> events, DateTime dt, Body body){
        String filepath = "Conflicts_starting_at_" + dt.toString("MMMdd");

        Table table = new Table();
        table.setBorder("");
        body.appendChild(table);
        
        Tr row0 = new Tr();
        Td colTitle1 = new Td();
        Td colTitle2 = new Td();
        B b1 = new B();
        b1.appendChild(new Text("Event"));
        colTitle1.appendChild(b1);
        B b2 = new B();
        b2.appendChild(new Text("Conflicts with:"));
        colTitle2.appendChild(b2);
        row0.appendChild(colTitle1, colTitle2);
        table.appendChild(row0);
        
        for(Event e: events){
            Tr row1 = new Tr();
            row1.setValign("top");
            Td td1 = new Td();
            Td td2 = new Td();
            row1.appendChild(td1, td2);
            table.appendChild(row1);
            
            P p1 = new P();
            td1.appendChild(p1);
            appendTitleTimes(e, p1);
            
            ConflictFilter cf = new ConflictFilter();
            for(Event e2: cf.filter(events, e)){
            	P p2 = new P();
            	td2.appendChild(p2);
            	appendTitleTimes(e2, p2);
            }
        }
        
        return filepath;
	}
}
