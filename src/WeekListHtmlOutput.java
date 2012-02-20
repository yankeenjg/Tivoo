import java.io.*;
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
        String filepath = "ListView_Week_of_" + dt.toString("MMMdd");
        
        Html html = new Html();
        Body body = new Body();
        html.appendChild(body);
        
        for(int i=0;i<7; i++){
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
        }
        
        BufferedWriter out;
        try {
            out = new BufferedWriter(new FileWriter(filepath+".html"));
        
        out.write(html.write());
        out.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        
    }
    
    /*
     * perhaps this could be a class of its own
     * but it doesn't want to extend abstracthtmloutput
     * and nothing else so far wants to use it
     */
    public String writeDetails(Event e, String filepath, int evNum){
        Html html = new Html();
        
        Head head = new Head();
        Style style = new Style("text/css");
        head.appendChild(style);
        style.appendChild(new Text("p.font{font-family:Helvetica, sans-serif;"));
        html.appendChild(head);
        
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
        
        String eventpath = filepath+"/event"+evNum+".html";
        
        writeHtmlFile(html, eventpath);
        
        return eventpath;
        
    }
    
    
    
    /*
     * tester hurp durp
     */
    public static void main (String[] args){
    	AbstractHtmlOutput ho = new WeekListHtmlOutput();
    	DateTime dt1 = new DateTime(2012, 2, 24, 11, 15);
    	DateTime dt2 = new DateTime(2012, 2, 24, 11, 30);
    	DateTime dt3 = new DateTime(2012, 2, 28, 11, 45);
    	DateTime dt4 = new DateTime(2012, 2, 28, 12, 00);
    	Event e1 = new Event("Title", dt1, dt2, "Description", "Location");
    	Event e2 = new Event("Title2", dt3, dt4, "Description2", "Location");
    	List<Event> l = new ArrayList<Event>();
    	l.add(e1);
    	l.add(e2);
    	ho.writeEventList(l);
    }
    

}