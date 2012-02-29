package parsing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jdom.Element;
import org.joda.time.DateTime;

public abstract class AbstractIntermediateXMLParser extends AbstractXMLParser {

	private String ROOT_NODE;
	private String EVENT_NODE;
	private String TITLE; 
	private String DESCRIPTION;
	private String LOCATION;
	
	protected AbstractIntermediateXMLParser(String root_node, String event_node, String title,
											String description, String location){
		ROOT_NODE = root_node;
		EVENT_NODE = event_node;
		TITLE = title;
		DESCRIPTION = description;
		LOCATION = location;
	}

	/**
	 * Gets the root node that contains all event nodes
	 */
	@SuppressWarnings("unchecked")
    @Override
	protected List<Element> parseGetEventsList() {
		Element eventsRoot = doc.getRootElement();
		if ( !eventsRoot.getName().equals(ROOT_NODE) ){
			String errorMessage = "Expected root node: " + ROOT_NODE +
					"but found: " + eventsRoot.getName();
			throw new ParserException(errorMessage, ParserException.Type.WRONG_TYPE);
		}
		return eventsRoot.getChildren(EVENT_NODE);
	}
	
	/**
	 * Gets the title of the event, given an event node
	 */
	protected String parseTitle(Element event) {
		String title = event.getChildText(TITLE);
		if(title != null)
			return title;
		else throw new NullPointerException("Couldn't find node: " + TITLE);
	}
	
	/**
	 * Gets the description of the event, given an event node
	 */
	protected String parseDescription(Element event) {
		String desc = event.getChildText(DESCRIPTION);
		if(desc != null)
			return desc;
		else throw new NullPointerException("Couldn't find node: " + DESCRIPTION);
	}
	
	/**
	 * Gets the location of the event, given an event node
	 */
	protected String parseLocation(Element event) {
		String location = event.getChildText(LOCATION);
		if(location != null)
			return location;
		else throw new NullPointerException("Couldn't find node: " + LOCATION);
	}
	
}
