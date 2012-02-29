package filtering;
import model.Event;
import org.joda.time.*;
import org.joda.time.DateTimeComparator;

public class TimeFilter extends AbstractFilter {
	
	public boolean checkFilterCondition(Event event, Object ... args) {
		return isInRange(event, (DateTime) args[0], (DateTime) args[1]);
	}
	
	public boolean isInRange(Event event, DateTime lowerLimit, DateTime upperLimit) {
		DateTimeComparator comparator = DateTimeComparator.getInstance();
		return (comparator.compare(event.getStartTime(), lowerLimit) <=0) && (comparator.compare(event.getStartTime(), upperLimit) >= 0);
	}
}
