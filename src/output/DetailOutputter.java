package output;
import java.util.List;

import org.joda.time.DateTime;

import model.Event;
import com.hp.gagawa.java.elements.*;

public abstract class DetailOutputter extends AbstractHtmlOutputter{
	
	/*
     * creates the detail pages for the given event
     */
    protected String writeDetails(Event e, String filepath, int evNum){
        Html html = new Html();
        Body body = new Body();
        html.appendChild(body);
        
        P st = new P();
        B b = new B();
        b.appendChild(new Text(e.getTitle()));
        st.appendChild(b);
        if(e.isAllDay()){
        	st.appendChild(new Text("<br/> All day "+e.getStartTime().toString("MM/dd, YYYY")));
        }else{
            st.appendChild(new Text("<br/>  Start: "+e.getStartTime().toString("MM/dd HH:mm, YYYY")));
            st.appendChild(new Text("<br/>  End: "+e.getEndTime().toString("MM/dd HH:mm, YYYY")));
        }
            
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
    
    /*
     * Appends all relevant event info into a cell of the week table
     * Takes the p to write to, list of events to check, datetime of the
     * cell, and the filepath to write to
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
                if(e.isAllDay()){
                	p.appendChild(new Text("<br/>  All day "+e.getStartTime().toString("MM/dd")+"<br/><br/>"));
                }
                else{
                    p.appendChild(new Text("<br/>  Start: "+e.getStartTime().toString("MM/dd HH:mm")));
                    p.appendChild(new Text("<br/>  End: "+e.getEndTime().toString("MM/dd HH:mm")+"<br/><br/>"));
                }
            }
        }
    }

}
