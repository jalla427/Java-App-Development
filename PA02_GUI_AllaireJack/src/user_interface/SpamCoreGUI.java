/**********************************************
* 
* @name SpamCoreGUI.java
* @author Jack Allaire
* @purpose computational object designed to read an email and scan
* 		   it for common phrases found in spam emails.
* @sources - https://stackoverflow.com/questions/5451010/nested-class-vs-implements-actionlistener
* 		   - https://www.mkyong.com/swing/java-swing-jfilechooser-example/
* 		   - https://stackoverflow.com/questions/9119481/how-to-present-a-simple-alert-message-in-java
* 		   - https://stackoverflow.com/questions/13245490/check-how-many-times-string-contains-character-g-in-eligible-string
* 
**********************************************/

package user_interface;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

import computational_object.InvalidSpamException;
import computational_object.SpamPointCalculator;
import computational_object.SpamPointCalculatorInterface;
import gui_components.*;

public class SpamCoreGUI
{
	//Assign calculator to a variable
	SpamPointCalculatorInterface calculator = new SpamPointCalculator();
	
	//Text Area is referenced by multiple sections, and must be a class variable
	//Used in method createEmailInputPanel
	JTextArea spamScoreTextArea = null;
	
	public SpamCoreGUI(String title)
	{
		//Local Variables
		JFrame jfrSpamGUI = null;
		Container mainContainer = null;
		
		//Instantiate Outer Container
		jfrSpamGUI = new JFrame(title);
		
		//Get Address of the Object for the JFrame
		mainContainer = jfrSpamGUI.getContentPane();
		
		//Build GUI
		buildGUI(mainContainer);
		
		//Set GUI attributes
		setJFrameAttributes(jfrSpamGUI);
	}
	
	public void buildGUI(Container mainContainer)
	{
		//Local Variables
		JPanel spamPhraseEditorPanel = null;
		JPanel emailInputPanel = null;
		JPanel spamCalcOutputPanel = null;
		
		//Set Container Layout
		mainContainer.setLayout(new BorderLayout());
		
		//Create Inner Containers
		spamPhraseEditorPanel = createSpamPhraseEditorPanel();
		spamCalcOutputPanel = createSpamCalcOutputPanel();
		emailInputPanel = createEmailInputPanel();
		
		//Add Inner Containers to Outer Container
		mainContainer.add("North", spamPhraseEditorPanel);
		mainContainer.add("Center", emailInputPanel);
		mainContainer.add("South", spamCalcOutputPanel);
	}
	
	private void setJFrameAttributes(JFrame guiMain)
	{
		//Sets attributes for main container
		guiMain.setSize(900, 670);
		guiMain.setLocation(200, 200);
		//guiMain.pack();
		
		//Set frame to be visible
		guiMain.setVisible(true);
	}

	private JPanel createSpamPhraseEditorPanel()
	{
		//Local Variables - Panels
		JPanel mainPanel = null;
		JPanel leftMainPanel = null;
		JPanel rightMainPanel = null;
		JPanel upperLeftPanel = null;
		JPanel lowerLeftPanel = null;
		
		//Local Variables - Components
		JLabel enterPhraseLabel = null;
		JLabel listPhrasesLabel = null;
		JTextField enterPhraseField = null;
		JTextArea listPhrasesArea = null;
		JScrollPane listPhrasesAreaScroll = null;
		JButton addBtn = null;
		JButton removeBtn = null;
		
		//Instantiate Panels
		mainPanel = new SpamPanel(Color.GRAY, Color.BLUE);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		mainPanel.setMaximumSize(new Dimension(900, 320));
		
		leftMainPanel = new SpamPanel(Color.GRAY, Color.BLUE);
		leftMainPanel.setLayout(new BoxLayout(leftMainPanel, BoxLayout.Y_AXIS));
		leftMainPanel.setMaximumSize(new Dimension(450, 320));
		
		rightMainPanel = new SpamPanel(Color.GRAY, Color.BLUE);
		rightMainPanel.setLayout(new BoxLayout(rightMainPanel, BoxLayout.Y_AXIS));
		rightMainPanel.setMaximumSize(new Dimension(450, 320));
		
		upperLeftPanel = new SpamPanel(Color.GRAY, Color.GRAY);
		upperLeftPanel.setLayout(new BoxLayout(upperLeftPanel, BoxLayout.Y_AXIS));
		
		lowerLeftPanel = new SpamPanel(Color.GRAY, Color.GRAY);
		lowerLeftPanel.setLayout(new BoxLayout(lowerLeftPanel, BoxLayout.X_AXIS));

		
		//Instantiate Panel Components
		enterPhraseLabel = new SpamLabel("Spam Phrase or Keyword:");
		enterPhraseField = new SpamTextField(1000, 20, false);
		listPhrasesLabel = new SpamLabel("List of Spam Phrases or Keywords");
		listPhrasesArea = new JTextArea(10, 20);
		listPhrasesArea.setText(calculator.getSpamList());
		listPhrasesAreaScroll = new JScrollPane(listPhrasesArea);
		addBtn = new SpamButton("Add", new AddSpamListener(enterPhraseField,listPhrasesArea));
		removeBtn = new SpamButton("Remove", new RemoveSpamListener(enterPhraseField, listPhrasesArea));
		
		//Add Panel Components
		upperLeftPanel.add(enterPhraseLabel);
		upperLeftPanel.add(enterPhraseField);
		
		lowerLeftPanel.add(addBtn);
		lowerLeftPanel.add(removeBtn);
		
		rightMainPanel.add(listPhrasesLabel);
		rightMainPanel.add(listPhrasesAreaScroll);
		
		//Place Panels in Main Panel
		leftMainPanel.add(upperLeftPanel);
		leftMainPanel.add(lowerLeftPanel);
		
		mainPanel.add(leftMainPanel);
		mainPanel.add(rightMainPanel);
		
		//Return Main Panel
		return mainPanel;
	}
	
	private JPanel createEmailInputPanel()
	{
		//Local Variables - Panels
		JPanel mainPanel = null;
		JPanel upperPanel = null;
		JPanel rightUpperPanel = null;
		JPanel leftUpperPanel = null;
		JPanel lowerPanel = null;

		
		//Local Variables - Components
		JLabel emailTextLabel = null;
		JLabel fileNameLabel = null;
		JTextArea emailTextArea = null;
		JTextField fileNameTextField = null;
		JButton browseBtn = null;
		JButton inputBtn = null;
		JButton clearBtn = null;
		JButton calculateBtn = null;
		JButton exitBtn = null;
		
		//Instantiate Panels
		mainPanel = new SpamPanel(Color.LIGHT_GRAY, Color.BLUE);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setMaximumSize(new Dimension(900, 320));
		
		upperPanel = new SpamPanel(Color.LIGHT_GRAY, Color.LIGHT_GRAY);
		upperPanel.setLayout(new BoxLayout(upperPanel, BoxLayout.X_AXIS));
		upperPanel.setMaximumSize(new Dimension(450, 300));
		
		lowerPanel = new SpamPanel(Color.LIGHT_GRAY, Color.LIGHT_GRAY);
		lowerPanel.setLayout(new BoxLayout(lowerPanel, BoxLayout.X_AXIS));
		lowerPanel.setMaximumSize(new Dimension(450, 20));
		
		rightUpperPanel = new SpamPanel(Color.LIGHT_GRAY, Color.LIGHT_GRAY);
		rightUpperPanel.setLayout(new BoxLayout(rightUpperPanel, BoxLayout.Y_AXIS));

		leftUpperPanel = new SpamPanel(Color.LIGHT_GRAY, Color.LIGHT_GRAY);
		leftUpperPanel.setLayout(new BoxLayout(leftUpperPanel, BoxLayout.Y_AXIS));
		
		//Instantiate Panel Components
		emailTextLabel = new SpamLabel("Email text:");
		fileNameLabel = new SpamLabel("Filename:");
		emailTextArea = new JTextArea(20, 20);
		emailTextArea.setLineWrap(true);
		fileNameTextField = new SpamTextField(500, 20, false);
		inputBtn = new SpamButton("Input", new EmailInputListener(fileNameTextField, emailTextArea));
		browseBtn = new SpamButton("Browse", new emailBrowseListener(fileNameTextField));
		clearBtn = new SpamButton("Clear", new ClearEmailListener(emailTextArea));
		calculateBtn = new SpamButton("Calculate", new CalculateListener(emailTextArea, spamScoreTextArea));
		exitBtn = new SpamButton("Exit", new exitListener());
		
		//Add Panel Components
		rightUpperPanel.add(fileNameLabel);
		rightUpperPanel.add(fileNameTextField);
		rightUpperPanel.add(inputBtn);
		rightUpperPanel.add(browseBtn);
		
		leftUpperPanel.add(emailTextLabel);
		leftUpperPanel.add(emailTextArea);

		lowerPanel.add(clearBtn);
		lowerPanel.add(calculateBtn);
		lowerPanel.add(exitBtn);
		
		//Place Panels in Main Panel
		upperPanel.add(leftUpperPanel);
		upperPanel.add(rightUpperPanel);

		mainPanel.add(upperPanel);
		mainPanel.add(lowerPanel);
		
		//Return Panel
		return mainPanel;
	}
	
	private JPanel createSpamCalcOutputPanel()
	{
		//Local Variables - Panels
		JPanel mainPanel = null;
		JPanel rightPanel = null;
		JPanel leftPanel = null;
		JPanel upperRightPanel = null;
		JPanel lowerRightPanel = null;
		
		//Local Variables - Components
		JLabel fileNameLabel = null;
		JTextField fileNameTextField = null;
		JScrollPane spamScoreTextAreaScroll = null;
		JButton clearBtn = null;
		JButton saveBtn = null;
		JButton browseBtn = null;
		
		//Instantiate Container
		mainPanel = new SpamPanel(Color.GRAY, Color.BLUE);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		mainPanel.setMaximumSize(new Dimension(900, 320));
		
		rightPanel = new SpamPanel(Color.GRAY, Color.GRAY);
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		rightPanel.setMaximumSize(new Dimension(450, 320));
		
		leftPanel = new SpamPanel(Color.GRAY, Color.GRAY);
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		leftPanel.setMaximumSize(new Dimension(450, 320));
		
		upperRightPanel = new SpamPanel(Color.GRAY, Color.GRAY);
		upperRightPanel.setLayout(new BoxLayout(upperRightPanel, BoxLayout.X_AXIS));
		
		lowerRightPanel = new SpamPanel(Color.GRAY, Color.GRAY);
		lowerRightPanel.setLayout(new BoxLayout(lowerRightPanel, BoxLayout.X_AXIS));
		
		//Instantiate Panel Components
		fileNameLabel = new SpamLabel("Filename:");
		fileNameTextField = new SpamTextField(500, 20, false);
		spamScoreTextArea = new JTextArea(10, 20);
		spamScoreTextAreaScroll = new JScrollPane(spamScoreTextArea); 
		clearBtn = new SpamButton("Clear", new ClearSpamListener(spamScoreTextArea));
		saveBtn = new SpamButton("Save", new saveSpamListener(fileNameTextField));
		browseBtn = new SpamButton("Browse", new emailBrowseListener(fileNameTextField));
		
		//Add Panel Components
		leftPanel.add(spamScoreTextAreaScroll);
		
		upperRightPanel.add(clearBtn);
		upperRightPanel.add(saveBtn);
		upperRightPanel.add(browseBtn);
		
		lowerRightPanel.add(fileNameLabel);
		lowerRightPanel.add(fileNameTextField);
		
		//Add Panels to Main Panel
		rightPanel.add(upperRightPanel);
		rightPanel.add(lowerRightPanel);
		
		mainPanel.add(leftPanel);
		mainPanel.add(rightPanel);
		
		//Return Panel
		return mainPanel;
	}
	
	public static void main(String[] args) throws InvalidSpamException 
	{
		new SpamCoreGUI("Spam GUI");
	}
	
	
	//*** Action Listeners ***//
	
	//ActionListener for add button
	private class AddSpamListener implements ActionListener
	{
		JTextField spamPhraseTextBox = null;
		JTextArea spamPhrasesTextArea = null;

		public AddSpamListener(JTextField spamPhraseTextBox, JTextArea spamPhrasesTextArea) 
		{
			super();
			this.spamPhraseTextBox = spamPhraseTextBox;
			this.spamPhrasesTextArea = spamPhrasesTextArea;
		}


		public void actionPerformed(ActionEvent event) 
		{
			boolean exists =  false;
			
			try 
			{
				if(spamPhraseTextBox.getText().length() > 3)
				{
					try 
					{
						//Scanner to read the list of spam phrases
						Scanner spamListScanner = new Scanner(new File("emailText1.txt"));
						
						//Iterate through spam list
						while(spamListScanner.hasNextLine())
						{
							if(spamListScanner.nextLine().equals(spamPhraseTextBox.getText()))
							{
								exists = true;
							}
						}
						spamListScanner.close();
					} 
					catch (FileNotFoundException e) 
					{
						e.printStackTrace();
					}
					
					if(exists)
					{
						throw new InvalidSpamException("This phrase is already on the list.");
					}
					else
					{
						calculator.add(spamPhraseTextBox.getText());
						spamPhrasesTextArea.setText(calculator.getSpamList());
					}	
				}
				else
				{
					throw new InvalidSpamException("This phrase is not long enough, it must be four characters long.");
				}
			} 
			catch(InvalidSpamException e) 
			{
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		}
		
	}
	
	//ActionListener for remove button
	private class RemoveSpamListener implements ActionListener
	{
		JTextField spamPhraseTextBox = null;
		JTextArea spamPhrasesTextArea = null;

		public RemoveSpamListener(JTextField spamPhraseTextBox, JTextArea spamPhrasesTextArea) 
		{
			super();
			this.spamPhraseTextBox = spamPhraseTextBox;
			this.spamPhrasesTextArea = spamPhrasesTextArea;
		}


		public void actionPerformed(ActionEvent event) 
		{
			boolean exists = false;
			
			try 
			{
				try 
				{
					//Scanner to read the list of spam phrases
					Scanner spamListScanner = new Scanner(new File("emailText1.txt"));
					
					//Iterate through spam list
					while(spamListScanner.hasNextLine())
					{
						if(spamListScanner.nextLine().equals(spamPhraseTextBox.getText()))
						{
							exists = true;
						}
					}
					spamListScanner.close();
				} 
				catch (FileNotFoundException e) 
				{
					e.printStackTrace();
				}
				
				if(!exists)
				{
					throw new InvalidSpamException("This phrase is not on the list.");
				}
				else
				{
					calculator.remove(spamPhraseTextBox.getText());
					spamPhrasesTextArea.setText(calculator.getSpamList());
				}		
			} 
			catch (InvalidSpamException e) 
			{
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		}
	}
	
	//ActionListener for clear email button
	private class ClearEmailListener implements ActionListener
	{
		JTextArea theTextBox = null;

		public ClearEmailListener(JTextArea textBox) 
		{
			super();
			this.theTextBox = textBox;
		}

		public void actionPerformed(ActionEvent event) 
		{
			theTextBox.setText("");
		}
	}
	
	//ActionListener for clear spam score results button
	private class ClearSpamListener implements ActionListener
	{
		JTextArea theTextBox = null;

		public ClearSpamListener(JTextArea textBox) 
		{
			super();
			this.theTextBox = textBox;
		}

		public void actionPerformed(ActionEvent event) 
		{
			theTextBox.setText("");
		}
	}
	
	//ActionListener for calculate spam score results button
	private class CalculateListener implements ActionListener
	{
		JTextArea emailText = null;
		JTextArea resultsText = null;

		public CalculateListener(JTextArea emailTextInput, JTextArea resultsTextOuput) 
		{
			super();
			this.emailText = emailTextInput;
			this.resultsText = resultsTextOuput;
		}

		public void actionPerformed(ActionEvent event) 
		{
			resultsText.setText(calculator.displayCalculatedSpamPoints(emailText.getText()));
		}
	}
	
	//ActionListener for browse for input email button
	private class EmailInputListener implements ActionListener
	{
		JTextField fileNameText = null;
		JTextArea emailText = null;
		String inputString = "";

		public EmailInputListener(JTextField fileName, JTextArea emailTextArea) 
		{
			super();
			this.fileNameText = fileName;
			this.emailText = emailTextArea;
		}

		public void actionPerformed(ActionEvent event) 
		{
			//Read in the contents of the file
			try 
			{
				Scanner emailScanner = new Scanner(new File(fileNameText.getText()));
				
				//While there is still text to read, continue reading
				while(emailScanner.hasNext())
				{
					inputString += emailScanner.nextLine();
				}
				emailScanner.close();
				
				//Set text box to show email contents
				emailText.setText(inputString);
				fileNameText.setText("");
			} 
			catch (FileNotFoundException e) 
			{
				JOptionPane.showMessageDialog(null, "This file does not exist");
			}
		}
	}
	
	//ActionListener for browse for email file button
	private class emailBrowseListener implements ActionListener
	{
		JTextField emailText = null;

		public emailBrowseListener(JTextField emailTextInput) 
		{
			super();
			this.emailText = emailTextInput;
		}

		public void actionPerformed(ActionEvent event) 
		{
			JFileChooser browse = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			File selectedFile = null;
			
			int returnValue = browse.showOpenDialog(null);

			if (returnValue == JFileChooser.APPROVE_OPTION) 
			{
				selectedFile = browse.getSelectedFile();
				emailText.setText(selectedFile.getAbsolutePath());
			}
		}
	}
	
	//ActionListener for save spam list button
	private class saveSpamListener implements ActionListener
	{
		JTextField theTextBox = null;

		public saveSpamListener(JTextField textBox) 
		{
			super();
			this.theTextBox = textBox;
		}

		public void actionPerformed(ActionEvent event) 
		{
			//Obtain size variable
			int size = calculator.size();
			
			//String array to hold copied values
			String[] spamList = new String[size];
			
			//Get spam list
			spamList = calculator.spamListToArray();
			
			//File writer instantiation
			//Will be used to alter the spam list accordingly
			FileWriter fileWriter = null;
			try 
			{
				fileWriter = new FileWriter(theTextBox.getText(), false);
				
				//Write new array to file
				for(int counter = 0; counter < size; counter++)
				{
					fileWriter.write(spamList[counter] + "\n");
				}
				fileWriter.close();
			} 
			catch(IOException e) 
			{
				JOptionPane.showMessageDialog(null, "This file does not exist");
			}
		}
	}
	
	//ActionListener for clear email button
	private class exitListener implements ActionListener
	{
		public exitListener() 
		{
			super();
		}

		public void actionPerformed(ActionEvent event) 
		{
			System.exit(0);
		}
	}
}
