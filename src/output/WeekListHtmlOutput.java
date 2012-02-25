package output;
import Event;

import java.util.*;
import org.joda.time.*;
import com.hp.gagawa.java.elements.*;

public class WeekListHtmlOutput extends AbstractHtmlOutput{
    
    /*
     * Takes a list of events
     * All events are assumed to be within the same 7 day period
     * Currently events on the same day of the week will appear
     * as occurring on the same day if they happen over multiple weeks
     * 
     * Creates an html file and a folder with html files for individual
     * event details
     */
    public void writeEventList(List<Event> events) {
        if(events.isEmpty()) return;
        
        DateTime dt = events.get(0).getStartTime();
        String filepath = "WeekList_of_" + dt.toString("MMMdd");
        
        Html html = new Html();
        Body body = new Body();
        html.appendChild(body);
        Table table = new Table();
        body.appendChild(table);
        
        Tr row1 = new Tr();
        Tr row2 = new Tr();
        row2.setValign("top");
        //table view
        for(int i=0; i<DateTimeConstants.DAYS_PER_WEEK; i++){
        	Td dayHeader = new Td();
        	B b = new B();
        	Div div = new Div();
        	div.setStyle("text-align:center");
        	div.appendChild(b);
        	dayHeader.appendChild(div);
        	row1.appendChild(dayHeader);
        	
        	b.appendChild(new Text(dt.toString("MMM dd")+" ("+dt.dayOfWeek().getAsText()+")"));

        	Td evs = new Td();
        	P p = new P();
        	evs.appendChild(p);
        	for(int j=0; j<events.size(); j++){
        		Event e = events.get(j);
        		if(e.getStartTime().getDayOfWeek()==dt.getDayOfWeek()){
        			String detailPath = writeDetails(e, filepath, j);
        			A detailLink = new A();
        			detailLink.setHref(detailPath);
                    detailLink.appendChild(new Text(e.getTitle()));
                    
                    p.appendChild(detailLink);
                    if(! e.isAllDay() ){
                    	p.appendChild(new Text("<br/>  Start: "+e.getStartTime().toString("MMM dd HH:mm")));
                    	p.appendChild(new Text("<br/>  End: "+e.getEndTime().toString("MMM dd HH:mm")+"<br/><br/>"));
                    }
                    else{ //if all day
                    	p.appendChild(new Text("<br/>  Start: "+e.getStartTime().toString("MMM dd")));
                    	p.appendChild(new Text("<br/>  End: "+e.getEndTime().toString("MMM dd")+"<br/><br/>"));
                    }
        		}
        	}
        	row2.appendChild(evs);
        	
        	dt = dt.plusDays(1);
        }
        table.appendChild(row1, row2);
        
        //list view
        /*for(int i=0;i<DateTimeConstants.DAYS_PER_WEEK; i++){
            P dayHeader = new P();
            B b = new B();
            
            b.appendChild(new Text(dt.toString("MMM dd")+" ("+dt.dayOfWeek().getAsText()+")"));
            dayHeader.appendChild(b);
            body.appendChild(dayHeader);
            for(int j=0; j<events.size(); j++){
                Event e = events.get(j);
                if(e.getStartTime().getDayOfWeek()==dt.getDayOfWeek()){
                    String detailPath = writeDetails(e, filepath, j);
                    A detailLink = new A();
                    detailLink.setHref(detailPath);
                    detailLink.appendChild(new Text(e.getTitle()));
                    
                    P st = new P();
                    st.appendChild(detailLink);
                    st.appendChild(new Text("<br/>  Start: "+e.getStartTime().toString("MMM dd HH:mm")));
                    st.appendChild(new Text("<br/>  End: "+e.getEndTime().toString("MMM dd HH:mm")));
                    
                    body.appendChild(st);
                    
                    
                }
            }
            dt = dt.plusDays(1);
        }*/
        
        writeHtmlFile(html, filepath + FILE_EXT);
    }
    
    /*
     * perhaps this could be a class of its own
     * but it doesn't want to extend abstracthtmloutput
     * and nothing else so far wants to use it
     */
    public String writeDetails(Event e, String filepath, int evNum){
        Html html = new Html();
        
        /*Head head = new Head();
        Style style = new Style("text/css");
        head.appendChild(style);
        style.appendChild(new Text("p.font{font-family:Helvetica, sans-serif;"));
        html.appendChild(head);*/
        
        Body body = new Body();
        html.appendChild(body);
        
        P st = new P();
        B b = new B();
        b.appendChild(new Text(e.getTitle()));
        st.appendChild(b);
        st.appendChild(new Text("<br/>  Start: "+e.getStartTime().toString("HH:mm")));
        st.appendChild(new Text("<br/>  End: "+e.getEndTime().toString("HH:mm")));
        st.appendChild(new Text("<br/>  Location: "+e.getLocation()));
        st.appendChild(new Text("<br/>  Description: "+e.getDescription()));
        
        st.setCSSClass("font");
        
        body.appendChild(st);
        
        String eventpath = filepath + "/event" + evNum + FILE_EXT;
        
        writeHtmlFile(html, eventpath);
        
        return eventpath;
        
    }
    
    
    
    
    /*public static void main (String[] args){
    	AbstractHtmlOutput ho = new WeekListHtmlOutput();
    	DateTime dt1 = new DateTime(2012, 2, 24, 11, 15);
    	DateTime dt2 = new DateTime(2012, 2, 24, 11, 30);
    	DateTime dt3 = new DateTime(2012, 2, 24, 11, 45);
    	DateTime dt4 = new DateTime(2012, 2, 24, 12, 00);
    	DateTime dt5 = new DateTime(2012, 2, 27, 12, 15);
    	DateTime dt6 = new DateTime(2012, 2, 27, 12, 30);
    	DateTime dt7 = new DateTime(2012, 2, 28, 12, 45);
    	DateTime dt8 = new DateTime(2012, 2, 28, 13, 00);
    	Event e1 = new Event("Title", dt1, dt2, "Description", "Location");
    	Event e2 = new Event("Title2", dt3, dt4, "Description2", "Location");
    	Event e3 = new Event("Title3", dt5, dt6, "Description3", "Location2.333");
    	Event e4 = new Event("Title4", dt7, dt8, "Description 4", "Location4");
    	List<Event> l = new ArrayList<Event>();
    	l.add(e1);
    	l.add(e2);
    	l.add(e3);
    	l.add(e4);
    	ho.writeEventList(l);
    }*/
    

}
