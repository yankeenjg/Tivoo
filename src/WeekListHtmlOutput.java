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
                    st.appendChild(new Text("  Start: "+e.getStartTime().toString("HH:mm")));
                    st.appendChild(new Text("<br/>  End: "+e.getEndTime().toString("HH:mm")));
                    
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
    
    /*
     * perhaps this could be a class of its own
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
        st.appendChild(new Text("<b>"+e.getTitle()+"</b>"));
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
     * perhaps there is an easier way to do this already in joda
     * if not, could probably move to super
     */
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
