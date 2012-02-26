package output;
import model.Event;
import java.util.*;
import org.joda.time.*;
import com.hp.gagawa.java.elements.*;

public class ConflictOutputter extends AbstractHtmlOutputter{

    /*
     * takes a list of events and loops through to check
     * which events have time overlaps with other events
     * and lists them in a table
     */
	public void writeEvents(List<Event> events) {
	    DateTime dt;
        if(events.isEmpty())
            dt = new DateTime();
        else
            dt = new DateTime(events.get(0).getStartTime());
        
        String filepath = "Conflicts_starting_ at_" + dt.toString("MMMdd");
        
        Html html = new Html();
        Body body = new Body();
        html.appendChild(body);
        Table table = new Table();
        body.appendChild(table);
        
        Tr row0 = new Tr();
        Td colTitle1 = new Td();
        Td colTitle2 = new Td();
        colTitle1.appendChild(new Text("Event"));
        colTitle2.appendChild(new Text("Conflicts with:"));
        row0.appendChild(colTitle1, colTitle2);
        table.appendChild(row0);
        
        for(Event e: events){
            Tr row1 = new Tr();
            row1.setValign("top");
            Td td1 = new Td();
            Td td2 = new Td();
            row1.appendChild(td1, td2);
            td1.appendChild(new Text(e.getTitle()+"<br/>"));
            if(e.isAllDay()){
                td1.appendChild(new Text("All day "+e.getStartTime().toString("MM/dd")));
            }else{
                td1.appendChild(new Text("Start: "+e.getStartTime().toString("HH:mm MM/dd")));
                td1.appendChild(new Text("End: "+e.getEndTime().toString("HH:mm MM/dd")));
            }
            findConflicts(e, events, td2);
            
            
        }
        
	}
	
	private void findConflicts(Event e1, List<Event> events, Td td){
	    for(Event e2: events){
	        if(e1!=e2){
	            if(e1.isAllDay() && (e2.getStartTime().isAfter(e1.getStartTime())))
	            if( e1.getStartTime().isAfter(e2.getEndTime())||e1.getEndTime().isBefore(e2.getStartTime())) )
	            //create a conflict filter checker thing and do some filtering 
	            //to make sure the lists are as expected before outputting
	            //YEAHYEAHYEAHGETHYPE
	            
	        }
	    }
	    
	}

}
