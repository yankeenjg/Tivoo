package filtering;

import org.joda.time.DateTime;
import model.Event;

public class ConflictFilter extends AbstractFilter{

	public boolean checkFilterCondition(Event event, Object... args) {
		return isConflicting(event, (Event) args[0]);
	}
	
	public boolean isConflicting(Event e1, Event e2){
		if(e1!=e2){
        	//there is probably a more efficient way to check for conflicts,
        	//but I do not know it
			DateTime e1s = e1.getStartTime();
	    	DateTime e1e = e1.getEndTime();
			DateTime e2s = e2.getStartTime();
        	DateTime e2e = e2.getEndTime();
        	//e1 starts before e2 ends
        	if(e1s.isAfter(e2s) && e1s.isBefore(e2e))
        		return true;
        	//e1 ends after e2 starts
        	else if(e1e.isAfter(e2s) && e1e.isBefore(e2e))
        		return true;
        	//e1 starts before and ends after e2
        	else if(e1s.isBefore(e2s) && e1e.isAfter(e2e))
        		return true;
        	//e1 starts and ends at same time as e2
        	else if(e1s.isEqual(e2s) && e1e.isEqual(e2e))
        		return true;
        }
		return false;
	}

}
