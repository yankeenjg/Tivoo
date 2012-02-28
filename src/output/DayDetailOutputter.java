package output;
import java.util.*;
import model.Event;
import org.joda.time.DateTime;

import sorting.StartTimeSorter;


import com.hp.gagawa.java.elements.*;

public class DayDetailOutputter extends DetailOutputter{
	
	/*
	 * Take a list of events and sort it by start date
	 * Writes all events on the same day as the first event
	 * after sorting; all other events are ignored
	 */
	public void writeEvents(List<Event> events) {
        DateTime dt;
        if(events.isEmpty())
            dt = new DateTime();
        else{
        	StartTimeSorter sts = new StartTimeSorter();
        	events = sts.sort(events);
            dt = new DateTime(events.get(0).getStartTime());
        }
            
        String filepath = "DayDetail_of_" + dt.toString("MMMdd");
        
        Html html = new Html();
        Body body = new Body();
        html.appendChild(body);
        H4 h4 = new H4();
        h4.appendChild(new Text(dt.toString("MMM dd, YYYY")));
        body.appendChild(h4);
        
        P p = new P();
        body.appendChild(p);
        for(int i=0; i<events.size();i++){
        	Event e = events.get(i);
        	if(!isSameDate(dt, e.getStartTime()))
        		break;
        	writeEventP(p, e, filepath, i);
        }
        
        writeHtmlFile(html, filepath + FILE_EXT);
    }
	
	/*
     * Appends all relevant event info into the P object
     * Takes the p to write to, the event to check, and 
     * the filepath to write to
     */
    private void writeEventP(P p, Event e, String filepath, int evNum){
    	String detailPath = writeDetails(e, filepath, evNum);
    	A detailLink = new A();
    	detailLink.setHref(detailPath);
    	detailLink.appendChild(new Text(e.getTitle()));
    	p.appendChild(detailLink);
    	
    	if(e.isAllDay()){
        	p.appendChild(new Text("<br/>  All day "+e.getStartTime().toString("MM/dd")+"<br/><br/>"));
        }
        else{
            p.appendChild(new Text("<br/>  Start: "+e.getStartTime().toString("MM/dd HH:mm")));
            p.appendChild(new Text("<br/>  End: "+e.getEndTime().toString("MM/dd HH:mm")+"<br/><br/>"));
        }
    }
    
    /*public static void main (String[] args){
    	AbstractHtmlOutputter ho = new DayDetailOutputter();
    	DateTime dt1 = new DateTime(2012, 2, 24, 11, 15);
    	DateTime dt2 = new DateTime(2012, 2, 24, 11, 30);
    	DateTime dt3 = new DateTime(2012, 2, 24, 11, 45);
    	DateTime dt4 = new DateTime(2012, 2, 24, 12, 00);
    	DateTime dt5 = new DateTime(2012, 2, 27, 12, 15);
    	DateTime dt6 = new DateTime(2012, 2, 27, 12, 30);
    	DateTime dt7 = new DateTime(2012, 2, 28, 12, 45);
    	DateTime dt8 = new DateTime(2012, 2, 28, 13, 00);
    	List<String> actor = new ArrayList<String>();
    	actor.add("actor");
    	Event e1 = new Event("Title", dt1, dt2, "Description", "Location", true, null);
    	Event e2 = new Event("Title2", dt3, dt4, "Description2", "Location", false, null);
    	Event e3 = new Event("Title3", dt5, dt6, "Description3", "Location2.333", false, null);
    	Event e4 = new Event("Title4", dt7, dt8, "Description 4", "Location4", false, null);
    	List<Event> l = new ArrayList<Event>();
    	l.add(e2);
    	l.add(e1);
    	l.add(e3);
    	l.add(e4);
    	ho.writeEvents(l);
    }*/

}
