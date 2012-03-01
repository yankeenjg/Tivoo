package filtering;
import model.Event;
import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;

public class TimeFilter extends AbstractFilter {

	public boolean checkFilterCondition(Event event, Object ... args) {
		return isInStartTimeRange(event, (DateTime) args[0], (DateTime) args[1]);
	}
	
	public boolean isInStartTimeRange(Event event, DateTime lowerLimit, DateTime upperLimit) {
		DateTimeComparator comparator = DateTimeComparator.getInstance();
		return comparator.compare(lowerLimit, event.getStartTime()) <= 0 && comparator.compare(upperLimit, event.getStartTime()) >= 0;
	}
}
