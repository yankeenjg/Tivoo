import java.io.*;
import java.util.*;
import org.joda.time.*;
import com.hp.gagawa.java.Node;
import com.hp.gagawa.java.elements.*;

//TODO: generally improve design; currently functional

public class WeekSummary implements HtmlOutput{
    
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
        if(events.isEmpty()) return;
        
        DateTime dt = events.get(0).getStartTime();
        String filepath = "Week of " + dt.toString("MMM dd");
        int startOfWeek = dt.getDayOfWeek();
        System.out.println(startOfWeek);
        
        Html head = new Html();
        Body body = new Body();
        head.appendChild(body);
        
        for(int i=0;i<7; i++){
            H2 DoW = new H2();
            DoW.appendChild(new Text(getDayString(i+startOfWeek)));
            body.appendChild(DoW);
            for(int j=0; j<events.size(); j++){
                Event e = events.get(j);
                if(e.getStartTime().getDayOfWeek()==i+startOfWeek){
                    H3 title = new H3();
                    String detailPath = writeDetails(e, filepath, j);
                    A detailLink = new A();
                    detailLink.setHref(detailPath);
                    detailLink.appendChild(new Text(e.getTitle()));
                    
                    title.appendChild(detailLink);
                    DoW.appendChild(title);
                    
                    P st = new P();
                    st.appendChild(new Text("  Start: "+getFormattedTime(e.getStartTime())));
                    st.appendChild(new Text("<br/>  End: "+getFormattedTime(e.getEndTime())));
                    
                    body.appendChild(st);
                    
                    
                }
            }
        }
        
        BufferedWriter out;
        try {
            out = new BufferedWriter(new FileWriter(filepath+".html"));
        
        out.write(head.write());
        out.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        
        
    }
    
    public String writeDetails(Event e, String filepath, int evNum){
        Html head = new Html();
        Body body = new Body();
        head.appendChild(body);
        
        H3 title = new H3();
        title.appendChild(new Text(e.getTitle()));
        body.appendChild(title);
        
        P st = new P();
        st.appendChild(new Text("  Start: "+getFormattedTime(e.getStartTime())));
        st.appendChild(new Text("<br/>  End: "+getFormattedTime(e.getEndTime())));
        st.appendChild(new Text("<br/>  Location: "+e.getLocation()));
        st.appendChild(new Text("<br/>  Description: "+e.getDescription()));
        
        body.appendChild(st);
        
        String eventpath = filepath+"/event"+evNum+".html";
        File f = new File(eventpath);
        if(!f.getParentFile().exists())
            f.getParentFile().mkdir();
        
        createFiles(head, eventpath);
        
        return eventpath;
        
    }
    
    public void createFiles(Node head, String dir){
        BufferedWriter out;
        try {
            out = new BufferedWriter(new FileWriter(dir));
        
        out.write(head.write());
        out.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
    
    public String getFormattedTime(DateTime dt){
        return dt.toString("HH:mm");
    }
    
    public String getDayString(int i){
        if(i>7)
            i-=7;
        switch (i) {
        case 1: return "Monday";
        case 2: return "Tuesday";
        case 3: return "Wednesday";
        case 4: return "Thursday";
        case 5: return "Friday";
        case 6: return "Saturday";
        case 7: return "Sunday";
        default: return "";
        }
    }

}
