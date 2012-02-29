import java.io.File;

import javax.swing.JFileChooser;
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
		MonthDetailOutputter outputter = new MonthDetailOutputter();
		return outputter.writeEvents(eventList);
		
	}
	
	public void keywordFilter() {
		KeywordFilter filter = new KeywordFilter();
		filter.filter(eventList, "");
	}
	
	public void locationFilter() {
		LocationFilter filter = new LocationFilter();
		filter.filter(eventList, "");
	}
	
	public void timeFilter() {
		TimeFilter filter = new TimeFilter();
		filter.filter(eventList, 500, 1000);
	}
	
	public void TVActorFilter() {
		TVActorFilter filter = new TVActorFilter();
		filter.filter(eventList, "");
	}
	
	public void endTimeSort() {
		EndTimeSorter sorter = new EndTimeSorter();
		sorter.sort(eventList);
	}
	
	public void startTimeSort() {
		StartTimeSorter sorter = new StartTimeSorter();
		sorter.sort(eventList);
	}
	
	public void titleSort() {
		TitleSorter sorter = new TitleSorter();
		sorter.sort(eventList);
	}
	
}