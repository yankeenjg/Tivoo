import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import output.AbstractHtmlOutputter;
import output.DetailOutputter;
import output.MonthDetailOutputter;
import output.WeekDetailOutputter;

import filtering.AbstractFilter;
import filtering.KeywordFilter;

import model.Event;

import parsing.AbstractXMLParser;
import parsing.DukeBasketBallXMLParser;
import parsing.DukeXMLParser;
import parsing.GoogleXMLParser;
import parsing.NFLXMLParser;
import parsing.ParserException;
import parsing.TVXMLParser;


public class MainModel {

	private List<AbstractXMLParser> myParsers;
	private List<Event> myInputListOfEvents;
	private List<Event> myProcessedListOfEvents;

	private AbstractFilter myFilter;
	private ArrayList<Object> myFilterParameters;
	private AbstractHtmlOutputter myOutputter;
	private String outputFilepath;
	
	public MainModel(){
		myParsers = new ArrayList<AbstractXMLParser>();
		myFilter = new KeywordFilter();
		myInputListOfEvents = new ArrayList<Event>();
		myFilterParameters = new ArrayList<Object>();
		myFilterParameters.add("Duke");
		
		myParsers.add(new DukeBasketBallXMLParser());
		myParsers.add(new DukeXMLParser());
		myParsers.add(new TVXMLParser());
		myParsers.add(new NFLXMLParser());
		myParsers.add(new GoogleXMLParser());
	}
	
	public String loadFile(File file){
		List<Event> listOfEvents = null;
		for(AbstractXMLParser parser : myParsers){
			try{
				listOfEvents = parser.processEvents(file.toString());
			} catch (ParserException e){
				System.err.println(e.getMessage());
				continue;
			}
			if(listOfEvents != null && listOfEvents.size() != 0){
				mergeNewListOfEvents(listOfEvents);
				outputFilepath = updateView();
				return outputFilepath;
			}
		}
		return outputFilepath;
	}
	
	public String chooseFilter(String filter) {
		try {
			myFilter = (AbstractFilter) Class.forName("filtering."+filter).getConstructor().newInstance();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return updateView();
	}
	
	public void setFilterParameters(Object... params){
		myFilterParameters = new ArrayList<Object>();
		for(Object param : params)
			myFilterParameters.add(param);
	}
	
	public void mergeNewListOfEvents(List<Event> newList){
		HashSet<Event> temp = new HashSet<Event>();
		temp.addAll(myInputListOfEvents);
		temp.addAll(newList);
		
		myInputListOfEvents = new ArrayList<Event>(temp);
	}
	
	public String updateView(){
		updateFilter();
		return updateOutput();
	}
	
	public void updateFilter(){
		Object[] params = new Object[myFilterParameters.size()];
		myFilterParameters.toArray(params);
		myProcessedListOfEvents = myFilter.filter(myInputListOfEvents, params);
	}
	
	public String updateOutput(){
		myOutputter = new MonthDetailOutputter();
		return myOutputter.writeEvents(myProcessedListOfEvents);
	}
	

}
