import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.*;
import javax.swing.event.*;


public class GUIViewer extends JPanel{

	public static final Dimension SIZE = new Dimension(800, 600);
	public static final String BLANK = "";
	
	private GUIModel myModel;
	
	private JEditorPane myPage;
	private JLabel myStatus;
	
	private JButton myBackButton;
	private JButton myNextButton;
	private JButton myHomeButton;
	
	private JButton myKeywordFilterButton;
	private JButton myLoadFileButton;
	private JButton myLocationFilterButton;
	private JTextField myKeywordField;
	
	public GUIViewer(GUIModel model){
		myModel = model;
		myPage = new JEditorPane();
		setLayout(new BorderLayout());
		add(makePageDisplay(), BorderLayout.CENTER);
		add(makeInformationPanel(), BorderLayout.SOUTH);
		add(makeNavigationPanel(), BorderLayout.PAGE_START);
		enableButtons();
	}
	
	private JComponent makePageDisplay ()
	{
        myPage = new JEditorPane();
        myPage.setPreferredSize(SIZE);
        myPage.setEditable(false);
        myPage.addHyperlinkListener(new LinkFollower());
		return new JScrollPane(myPage);
	}
	
	private JComponent makeInformationPanel()
	{
		myStatus = new JLabel(BLANK);
		return myStatus;
	}
	
	private JComponent makeNavigationPanel()
	{
		JPanel panel = new JPanel();
		
		myLoadFileButton = new JButton("Load File");
		panel.add(myLoadFileButton);
		myLoadFileButton.addActionListener(new ChooseLoadFileAction());
		
		myBackButton = new JButton("Back");
		panel.add(myBackButton);
		myBackButton.addActionListener(new BackAction());
		
		myNextButton = new JButton("Next");
		panel.add(myNextButton);
		myNextButton.addActionListener(new NextAction());
		
		myHomeButton = new JButton("Calendar Top");
		panel.add(myHomeButton);
		myHomeButton.addActionListener(new HomeAction());
		
		myKeywordFilterButton = new JButton("Filter by Keyword");
		panel.add(myKeywordFilterButton);
		myKeywordFilterButton.addActionListener(new ChooseKeywordFilterAction());
		
		myLocationFilterButton = new JButton("Filter by Location");
		panel.add(myLocationFilterButton);
		myLocationFilterButton.addActionListener(new ChooseLocationFilterAction());
		
		myKeywordField = new JTextField(20);	
		panel.add(myKeywordField);
		
		return panel;
	}
	
	private void enableButtons(){
		myBackButton.setEnabled(myModel.hasPrevious());
		myNextButton.setEnabled(myModel.hasNext());
		myHomeButton.setEnabled(myModel.getHome() != null);
	}
	
	/**
     * Inner class to deal with link-clicks and mouse-overs
     */
    private class LinkFollower implements HyperlinkListener
    {
        public void hyperlinkUpdate (HyperlinkEvent evt)
        {
            // user clicked a link, load it and show it
            if (evt.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
            {
            	showPage(evt.getURL().toString());
            }
            // user moused-into a link, show what would load
            else if (evt.getEventType() == HyperlinkEvent.EventType.ENTERED)
            {
            	showStatus(evt.getURL().toString());
            }
            // user moused-out of a link, erase what was shown
            else if (evt.getEventType() == HyperlinkEvent.EventType.EXITED)
            {
                showStatus(BLANK);
            }
        }
    }
    
    // move to the next URL in the history
    private void goNext ()
    {
        String url = myModel.next();
        if (url != null)
        {
            update(url);
        }
    }

    // move to the previous URL in the history
    private void goBack ()
    {
        String url = myModel.back();
        if (url != null)
        {
            update(url);
        }
    }

    // change current URL to the home page, if set
    private void goHome ()
    {
        String url = myModel.getHome();
        if (url != null)
        {
            showPage(url);
        }
    }
    
    private void reset()
    {
    	myModel.reset();
    }
    
    // update just the view to display given URL
    private void update (String url)
    {
        try
        {
            myPage.setPage(url);
            enableButtons();
        }
        catch (IOException e)
        {
        	// should never happen since only checked URLs make it this far ...
            showError("Could not load " + url);
        }
    }
    
    private void chooseFile(){
    	JFileChooser fc = new JFileChooser();
        
        int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            myModel.setHome(myModel.loadFile(file));
        }
        return;
    }
    
    private void chooseKeywordFilter(String keyword){
    	myModel.setHome(myModel.chooseKeywordFilter(keyword));
    }
    
	public void chooseLocationFilter(String keyword) {
		myModel.setHome(myModel.chooseLocationFilter(keyword));
	}
    
    /**
     * Display given URL.
     */
    public void showPage (String url)
    {
            // must be a valid URL, now update model and display results
            myModel.go(url);
            update(url);
    }


    /**
     * Display given message as an error in the GUI.
     */
    public void showError (String message)
    {
        JOptionPane.showMessageDialog(this,
        		                      message, 
        		                      "Browser Error",
        		                      JOptionPane.ERROR_MESSAGE);
    }


    /**
     * Display given message as information in the GUI.
     */
    public void showStatus (String message)
    {
        myStatus.setText(message);
    }

    
    private class BackAction implements ActionListener
    {
		@Override
		public void actionPerformed(ActionEvent a)
		{
			goBack();
		}	
    }
    
    private class NextAction implements ActionListener
    {
    	@Override
    	public void actionPerformed(ActionEvent a){
    		goNext();
    	}
    }
    
    private class HomeAction implements ActionListener
    {
    	@Override
    	public void actionPerformed(ActionEvent e){
    		goHome();
    	}
    }
    
    private class ChooseLoadFileAction implements ActionListener
    {
    	@Override
    	public void actionPerformed(ActionEvent e){
    		chooseFile();
    		reset();
    		goHome();
    	}
    }
    
    private class ChooseKeywordFilterAction implements ActionListener
    {
    	@Override
    	public void actionPerformed(ActionEvent e){
    		chooseKeywordFilter(myKeywordField.getText());
    		reset();
    		goHome();
    	}
    }
    
    private class ChooseLocationFilterAction implements ActionListener
    {
    	@Override
    	public void actionPerformed(ActionEvent e){
    		chooseLocationFilter(myKeywordField.getText());
    		reset();
    		goHome();
    	}
    }

}
