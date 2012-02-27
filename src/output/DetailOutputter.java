package output;
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
        if(!e.isAllDay()){
            st.appendChild(new Text("<br/>  Start: "+e.getStartTime().toString("MM/dd HH:mm, YYYY")));
            st.appendChild(new Text("<br/>  End: "+e.getEndTime().toString("MM/dd HH:mm, YYYY")));
        }else{
            st.appendChild(new Text("<br/> All day "+e.getStartTime().toString("MM/dd, YYYY")));
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

}
