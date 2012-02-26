package output;

import model.Event;
import java.util.*;
import org.joda.time.*;
import com.hp.gagawa.java.elements.*;

public class WeekListOutputter extends AbstractHtmlOutputter{
    
    /*
     * Takes a list of events
     * All events are assumed to be within the same 7 day period
     * Currently events on the same day of the week will appear
     * as occurring on the same day if they happen over multiple weeks
     * 
     * Creates an html file and a folder with html files for individual
     * event details
     */
    public void writeEvents(List<Event> events) {
        DateTime dt;
        if(events.isEmpty())
            dt = new DateTime();
        else
            dt = new DateTime(events.get(0).getStartTime());
        
        String filepath = "WeekList_of_" + dt.toString("MMMdd");
        
        Html html = new Html();
        Body body = new Body();
        html.appendChild(body);
        Table table = new Table();
        body.appendChild(table);
        
        //table view
        Tr row0 = new Tr();
        row0.appendChild(new Text("Week of "+dt.toString("MMM dd, YYYY")));
        Tr row1 = new Tr();
        Tr row2 = new Tr();
        row2.setValign("top");
        for(int i=0; i<DateTimeConstants.DAYS_PER_WEEK; i++){
        	Td dayHeader = new Td();
        	B b = new B();
        	Div div = new Div();
        	div.setStyle("text-align:center");
        	div.appendChild(b);
        	dayHeader.appendChild(div);
        	row1.appendChild(dayHeader);
        	
        	b.appendChild(new Text(dt.toString("MM/dd")+" ("+dt.dayOfWeek().getAsText()+")"));

        	Td evs = new Td();
        	P p = new P();
        	evs.appendChild(p);
        	writeEventP(p, events, dt, filepath);
        	
        	row2.appendChild(evs);
        	
        	dt = dt.plusDays(1);
        }
        table.appendChild(row0, row1, row2);
        
        writeHtmlFile(html, filepath + FILE_EXT);
    }
    
    /*
     * Appends all relevant event info into a cell of the week table
     * Takes the p to write to, list of events to check, datetime of the
     * cell, and the filepath to write to
     */
    private void writeEventP(P p, List<Event> events, DateTime dt, String filepath){
        for(int j=0; j<events.size(); j++){
            Event e = events.get(j);
            if(e.getStartTime().getDayOfWeek()==dt.getDayOfWeek()){
                String detailPath = writeDetails(e, filepath, j);
                A detailLink = new A();
                detailLink.setHref(detailPath);
                detailLink.appendChild(new Text(e.getTitle()));
                
                p.appendChild(detailLink);
                if(! e.isAllDay() ){
                    p.appendChild(new Text("<br/>  Start: "+e.getStartTime().toString("MM/dd HH:mm")));
                    p.appendChild(new Text("<br/>  End: "+e.getEndTime().toString("MM/dd HH:mm")+"<br/><br/>"));
                }
                else{ //if all day
                    p.appendChild(new Text("<br/>  All day "+e.getStartTime().toString("MM/dd")+"<br/><br/>"));
                }
            }
        }
    }
    
    /*
     * creates the detailed pages for the given event
     */
    private String writeDetails(Event e, String filepath, int evNum){
        Html html = new Html();
        Body body = new Body();
        html.appendChild(body);
        
        P st = new P();
        B b = new B();
        b.appendChild(new Text(e.getTitle()));
        st.appendChild(b);
        if(!e.isAllDay()){
            st.appendChild(new Text("<br/>  Start: "+e.getStartTime().toString("MM/dd HH:mm, YYYY")));
            st.appendChild(new Text("<br/>  End: "+e.getEndTime().toString("MM/dd HH:mm, YYYY")));
        }else{
            st.appendChild(new Text("<br/> All day "+e.getStartTime().toString("MM/dd, YYYY")));
        }
            
        st.appendChild(new Text("<br/>  Location: "+e.getLocation()));
        st.appendChild(new Text("<br/>  Description: "+e.getDescription()));
        
        body.appendChild(st);
        
        String eventpath = filepath + "/event" + evNum + FILE_EXT;
        
        writeHtmlFile(html, eventpath);
        return eventpath;
    }
    
    
    /*public static void main (String[] args){
    	AbstractHtmlOutputter ho = new WeekListOutputter();
    	DateTime dt1 = new DateTime(2012, 2, 24, 11, 15);
    	DateTime dt2 = new DateTime(2012, 2, 24, 11, 30);
    	DateTime dt3 = new DateTime(2012, 2, 24, 11, 45);
    	DateTime dt4 = new DateTime(2012, 2, 24, 12, 00);
    	DateTime dt5 = new DateTime(2012, 2, 27, 12, 15);
    	DateTime dt6 = new DateTime(2012, 2, 27, 12, 30);
    	DateTime dt7 = new DateTime(2012, 2, 28, 12, 45);
    	DateTime dt8 = new DateTime(2012, 2, 28, 13, 00);
    	Event e1 = new Event("Title", dt1, dt2, "Description", "Location", true);
    	Event e2 = new Event("Title2", dt3, dt4, "Description2", "Location", false);
    	Event e3 = new Event("Title3", dt5, dt6, "Description3", "Location2.333", false);
    	Event e4 = new Event("Title4", dt7, dt8, "Description 4", "Location4", false);
    	List<Event> l = new ArrayList<Event>();
    	l.add(e1);
    	l.add(e2);
    	l.add(e3);
    	l.add(e4);
    	ho.writeEvents(l);
    }*/
    
}
