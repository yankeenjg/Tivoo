package parsing;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;

import model.*;

import org.jdom.Element;

public class TVXMLParser extends
        AbstractConnectedTimeAsAttributeValueParser {

	/**
	 * Labels for specific nodes in the event tree
	 */
	private String myLocation = "channel";
	private String myCredits = "credits";
	private String myRating = "rating";

	public TVXMLParser() {
		// Assigns names of tags for myEventNode, myTitle, myDescription,
		// myLocation, myDateTimePatter, myStart, myEnd
		// myLocation is overridden
		super("tv", "programme", "title", "desc", null, "yyyyMMddHHmmss Z",
		        "start", "stop");
	}

	@Override
	protected String parseLocation(Element event) {
		return event.getAttributeValue(myLocation);
	}

	@Override
	protected HashMap<String, ArrayList<String>> getExtraProperties(
	        Element event) {
		HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();

		addCreditsToMap(event, map);
		addRatingToMap(event, map);

		return map;
	}

	/**
	 * Parses credit information (actors, directors...) and adds to parameter
	 * map
	 * 
	 * @param event
	 * @param map
	 */
	@SuppressWarnings("unchecked")
	protected void addCreditsToMap(Element event,
	        HashMap<String, ArrayList<String>> map) {
		List<Element> creditsList;
		try {
			creditsList = event.getChild(myCredits).getChildren();
		} catch (NullPointerException e) {
			return;
		}

		for (Element credit : creditsList) {
			String creditName = credit.getName();
			String creditValue = credit.getText();
			if (!map.containsKey(creditName))
				map.put(creditName, new ArrayList<String>());
			map.get(creditName).add(creditValue);
		}
	}

	/**
	 * Parses rating information and adds to parameter map
	 * 
	 * @param event
	 * @param map
	 */
	protected void addRatingToMap(Element event,
	        HashMap<String, ArrayList<String>> map) {
		if (!map.containsKey(myRating))
			map.put(myRating, new ArrayList<String>());
		String ratingValue;
		try {
			ratingValue = event.getChild(myRating).getChildText("value");
			map.get(myRating).add(ratingValue);
		} catch (NullPointerException e) {
			return;
		}
	}

	public static void main(String[] args) {
		TVXMLParser parser = new TVXMLParser();
		List<Event> listOfEvents = parser
		        .processEvents("http://dl.dropbox.com/u/5156866/tv.xml");
		System.err.println("done loading!");

		for (Event event : listOfEvents) {
			System.out.println(event.toString());
		}
	}
}
