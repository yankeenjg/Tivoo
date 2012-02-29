import java.io.File;

import javax.swing.JFileChooser;

import org.joda.time.DateTime;

import model.Event;
import java.util.List;
import java.util.ArrayList;
import output.*;
import parsing.*;
import filtering.*;
import sorting.*;


public class GUIModel {
  
	private List<Event> eventList;

	public GUIModel() {
		eventList = new ArrayList<Event>();
	}

	public File loadFile() {
		JFileChooser fileChooser = new JFileChooser();
		int returnVal = fileChooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();	
			AbstractXMLParser parser = AbstractXMLParser.chooseParser(file);
			parser.loadFile(file);
			eventList.addAll(parser.processEvents());
		}
		return null;
	}

	public File previewWebpage() {
		SortedListOutputter outputter = new SortedListOutputter();
		return outputter.writeEvents(eventList);
	}

	public void keywordFilter(String keyword) {
		KeywordFilter filter = new KeywordFilter();
		eventList = filter.filter(eventList, keyword);
	}

	public void locationFilter(String location) {
		LocationFilter filter = new LocationFilter();
		eventList = filter.filter(eventList, location);
	}

	public void timeFilter(DateTime lowerLimit, DateTime upperLimit) {
		TimeFilter filter = new TimeFilter();
		eventList = filter.filter(eventList, lowerLimit, upperLimit);
	}

	public void TVActorFilter(String actor) {
		TVActorFilter filter = new TVActorFilter();
		eventList = filter.filter(eventList, actor);
	}

	public void endTimeSort() {
		EndTimeSorter sorter = new EndTimeSorter();
		eventList = sorter.sort(eventList);
	}

	public void startTimeSort() {
		StartTimeSorter sorter = new StartTimeSorter();
		eventList = sorter.sort(eventList);
	}

	public void titleSort() {
		TitleSorter sorter = new TitleSorter();
		eventList = sorter.sort(eventList);
	}

}