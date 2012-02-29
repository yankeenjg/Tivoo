package output;
import java.util.List;

import org.joda.time.DateTime;

import model.Event;
import com.hp.gagawa.java.elements.*;

/**
 * Abstract class that provides a few common methods to calendar
 * outputs that want to link the condensed information in the
 * visual calendar to more detailed pages for each individual event
 * @author herio
 *
 */
public abstract class DetailOutputter extends AbstractHtmlOutputter{
	
    protected String writeDetails(Event e, String filepath, int evNum){
        Html html = new Html();
        Body body = new Body();
        html.appendChild(body);
        
        P st = new P();
        appendTitleTimes(e, st);
            
        st.appendChild(new Text("<br/>  Location: "+e.getLocation()));
        st.appendChild(new Text("<br/>  Description: "+e.getDescription()));
        
        if(e.getPropertyNames()!=null){
        	for(String prop:e.getPropertyNames()){
        		st.appendChild(new Text("<br/>"+prop+": "));
        		for(int i=0;i<e.getProperty(prop).size(); i++){
        			if(i!=0)
        			st.appendChild(new Text(", "));
        			st.appendChild(new Text(e.getProperty(prop).get(i)));
        		}
        	}
        }
        
        body.appendChild(st);
        
        String eventpath = filepath + "/event" + evNum + FILE_EXT;
        
        writeHtmlFile(html, eventpath);
        return eventpath;
    }
    
    /**
     * Appends all elements in a list that match the given DateTime to
     * a table cell in the calendar
     * @param p P element to which to append
     * @param events List of events that can be appended
     * @param dt DateTime to match
     * @param filepath Location of the files
     */
    protected void writeEventP(P p, List<Event> events, DateTime dt, String filepath){
    	for(int j=0; j<events.size(); j++){
            Event e = events.get(j);
            if(isSameDate(e.getStartTime(), dt)){
                String detailPath = writeDetails(e, filepath, j);
                A detailLink = new A();
                detailLink.setHref(detailPath);
                detailLink.appendChild(new Text(e.getTitle()));
                
                p.appendChild(detailLink);
                appendTimes(e, p);
                p.appendChild(new Text("<br/>"));
            }
        }
    }

}
