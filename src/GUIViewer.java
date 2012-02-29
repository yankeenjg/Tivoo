import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.JEditorPane;
import java.io.*;

@SuppressWarnings("serial")
public class GUIViewer extends JPanel {
  
	private GUIModel myModel;
	private JEditorPane pane;
	private JButton myLoadFileButton;
	private JButton myPreviewWebpageButton;
	private JButton myKeywordFilterButton;
	private JButton myLocationFilterButton;
	private JButton myTimeFilterButton;
	private JButton myTVActorFilterButton;
	private JButton myEndTimeSorterButton;
	private JButton myStartTimeSorterButton;
	private JButton myTitleSorterButton;
	
	public GUIViewer(GUIModel model) {
		myModel = model;
		setLayout(new BorderLayout());
		add(makeMiscPanel(), BorderLayout.WEST);
		add(makeFilterPanel(), BorderLayout.CENTER);
		add(makeSorterPanel(), BorderLayout.EAST);
		enableButtons();
	}
	
	private void enableButtons() {
		myLoadFileButton.setEnabled(true);
		myPreviewWebpageButton.setEnabled(true);
		myKeywordFilterButton.setEnabled(true);
		myLocationFilterButton.setEnabled(true);
		myTimeFilterButton.setEnabled(true);
		myTVActorFilterButton.setEnabled(true);
		myEndTimeSorterButton.setEnabled(true);
		myStartTimeSorterButton.setEnabled(true);
		myTitleSorterButton.setEnabled(true);
	}
	
	private JComponent makeMiscPanel() {
        JPanel panel = new JPanel();
        myLoadFileButton = new JButton("Load File");
        myLoadFileButton.addActionListener(new LoadFileAction());
        panel.add(myLoadFileButton);
        myPreviewWebpageButton = new JButton("Preview Webpage");
        myPreviewWebpageButton.addActionListener(new PreviewWebpageAction());
        panel.add(myPreviewWebpageButton);
        return panel;
    }

	private JComponent makeFilterPanel() {
		JPanel panel = new JPanel();
		myKeywordFilterButton = new JButton("Keyword Filter");
		myKeywordFilterButton.addActionListener(new KeywordFilterAction());
		panel.add(myKeywordFilterButton);
		myLocationFilterButton = new JButton("Location Filter");
		myLocationFilterButton.addActionListener(new LocationFilterAction());
		panel.add(myLocationFilterButton);
		myTimeFilterButton = new JButton("Time Filter");
		myTimeFilterButton.addActionListener(new TimeFilterAction());
		panel.add(myTimeFilterButton);
		myTVActorFilterButton = new JButton("TVActor Filter");
		myTVActorFilterButton.addActionListener(new TVActorFilterAction());
		panel.add(myTVActorFilterButton);
		return panel;
	}
	
	private JComponent makeSorterPanel() {
		JPanel panel = new JPanel();
		myEndTimeSorterButton = new JButton("End Time Sorter");
		myEndTimeSorterButton.addActionListener(new EndTimeSorterAction());
		panel.add(myEndTimeSorterButton);
		myStartTimeSorterButton = new JButton("Start Time Sorter");
		myStartTimeSorterButton.addActionListener(new StartTimeSorterAction());
		panel.add(myStartTimeSorterButton);
		myTitleSorterButton = new JButton("Title Sorter");
		myTitleSorterButton.addActionListener(new TitleSorterAction());
		panel.add(myTitleSorterButton);
		return panel;
	}
	
	private void loadFile() {
		myModel.loadFile();
    }
	
	private void previewWebpage() throws IOException {
		String url = myModel.previewWebpage().toURI().toString();
		pane = new JEditorPane();
		getRootPane().setLayout(new BorderLayout());
		getRootPane().add(pane, BorderLayout.CENTER);
		//getContentPane().add(pane, BorderLayout.CENTER);
		pane.setEditable(false);
		pane.setPreferredSize(new Dimension(800,600));
		JFrame frame = new JFrame();
		frame.pack();
		pane.setPage(url);
		setVisible(true);
	}


	
	private void keywordFilter() {
		myModel.keywordFilter();
	}
	
	private void locationFilter() {
		myModel.locationFilter();
	}
	
	private void timeFilter() {
		myModel.timeFilter();
	}
	
	private void TVActorFilter() {
		myModel.TVActorFilter();
	}
	
	private void endTimeSort() {
		myModel.endTimeSort();
	}
	
	private void startTimeSort() {
		myModel.startTimeSort();
	}
	
	private void titleSort() {
		myModel.titleSort();
	}
	
	private class LoadFileAction implements ActionListener {
    	public void actionPerformed (ActionEvent e) {
    		loadFile();
    	}
    }
	
	private class PreviewWebpageAction implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			try {
				previewWebpage();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	private class KeywordFilterAction implements ActionListener {
    	public void actionPerformed (ActionEvent e) {
    		keywordFilter();
    	}
    }
	
	private class LocationFilterAction implements ActionListener {
    	public void actionPerformed (ActionEvent e) {
    		locationFilter();
    	}
    }
	
	private class TimeFilterAction implements ActionListener {
    	public void actionPerformed (ActionEvent e) {
    		timeFilter();
    	}
    }
	
	private class TVActorFilterAction implements ActionListener {
    	public void actionPerformed (ActionEvent e) {
    		TVActorFilter();
    	}
    }
	
	private class StartTimeSorterAction implements ActionListener {
    	public void actionPerformed (ActionEvent e) {
    		startTimeSort();
    	}
    }
	
	private class EndTimeSorterAction implements ActionListener {
    	public void actionPerformed (ActionEvent e) {
    		endTimeSort();
    	}
    }
	
	private class TitleSorterAction implements ActionListener {
    	public void actionPerformed (ActionEvent e) {
    		titleSort();
    	}
    }
}
