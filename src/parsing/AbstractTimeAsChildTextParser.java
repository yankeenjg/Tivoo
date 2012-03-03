package parsing;
import org.jdom.*;

public class AbstractTimeAsChildTextParser extends AbstractSimpleTimeXMLParser {

	protected AbstractTimeAsChildTextParser(String rootNode, String eventNode,
            String title, String description, String location,
            String dateTimePattern, String start, String end) {
	    super(rootNode, eventNode, title, description, location, dateTimePattern,
	            start, end);
    }
	
	@Override
	protected String getTimestamp(Element time, String attrib) {
		return time.getChildText(attrib);
	}
	
	
	
}
