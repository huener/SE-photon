// Java GUI window setup consists of a navigation panel and a gameplay panel. The gameplay panel shifts between player entry, gameplay, and game over screens as necessary.

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.BoxLayout;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.Insets;
import java.awt.Font;
import javax.swing.border.Border;
import javax.swing.text.FlowView;
import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import java.awt.Dimension;
import javax.swing.GroupLayout;

class View extends JPanel
{
	Data data;

	JPanel topActionPanel;
	JPanel botActionPanel;
	JPanel timeActionPanel;

	//Splash Screen
	BufferedImage splash_image;
   	int splashFrames = 0;
    boolean splash = false;

	//Panels Framework
	JPanel titlePanel;
	JPanel mainPanel;
	JPanel navPanel;
	JPanel bottomPanel;

	//Switchable panles
	JPanel entryPanel;
	JPanel actionPanel;

	JTextField bottomText;

    View(Controller c, Data d)
	{
		c.setView(this); 	//sets the controller's view to this view instance in order for the two to be able to communicate
        data = d;
		bottomText = new JTextField(50);
		//initiating and sizing panels
		titlePanel = new JPanel(); titlePanel.setMaximumSize(new Dimension(960, 50)); titlePanel.setBackground(Color.black);
		mainPanel = new JPanel(); mainPanel.setMaximumSize(new Dimension(960, 580)); mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS)); mainPanel.setBackground(Color.black);
		navPanel = new JPanel(); navPanel.setMaximumSize(new Dimension(960, 60)); navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.X_AXIS)); navPanel.setBackground(Color.black);
		bottomPanel = new JPanel(); bottomPanel.setMaximumSize(new Dimension(960, 30));

		entryPanel = new JPanel(); entryPanel.setSize(new Dimension(960, 580)); entryPanel.setBackground(Color.black);
		actionPanel = new JPanel(); actionPanel.setBackground(Color.white); actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.Y_AXIS));

        // loads the picture for the splash screen.
        splash_image = loadImage("logo1.jpg");    // logo1.jpg is 960x623. Black background at the bottom
        // of the window matches best.
	}
    // window size is set to 960 x 720 in main

	public void paintComponent(Graphics g)
	{
		//background color
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
        
        	if(splash == false) {
            	g.drawImage(splash_image, 0, 0, null);
            	//splash = true;
        	}
		
	}

	void startGUI()
	{
		this.add(titlePanel);
        this.add(mainPanel);
        this.add(navPanel);
        this.add(bottomPanel);
		createNavigationBar();
		createBottomText();
	}

	void createNavigationBar() {
		// create navigation bar / instructions
		Border border = BorderFactory.createLineBorder(Color.WHITE);
		// create array of JTextArea
		JTextArea navBar[] = new JTextArea[12];
		for(int i = 0; i < navBar.length; i++)
		{
			navBar[i] = new JTextArea();
			navBar[i].setEditable(false);
			navBar[i].setForeground(Color.GREEN);
			navBar[i].setBackground(Color.BLACK);

			// space them out evenly horizontally; same height on the screen
			navBar[i].setMaximumSize(new Dimension(78, 60));

			// only add border to the JTextAreas for the controls. No borders for empty areas.
			if(i != 3 && i != 5 && i != 8 && i != 10) {
				navBar[i].setBorder(BorderFactory.createCompoundBorder(border,
					BorderFactory.createEmptyBorder(1, 1, 1, 1)));
			}
            
			// add to view (so they show up)
			this.navPanel.add(navBar[i]);
		}
		// set unique text for the necessary text areas.
		// JTextArea apparently doesn't have a good way to center text,
		// so strings are formatted to be centered in the textbox
		navBar[0].setText("         F1 \n        Edit \n      Game");
		navBar[1].setText("         F2 \n      Game \n  Parameters");
		navBar[2].setText("         F3 \n       Start \n      Game");
		navBar[4].setText("         F5 \n  PreEntered \n     Games");
		navBar[6].setText("         F7");
		navBar[7].setText("         F8 \n       View \n      Game");
		navBar[9].setText("        F10 \n       Flick \n       Sync");
		navBar[11].setText("        F12 \n       Clear \n      Game");
	}

	void createBottomText() {
		// create bottom textbox
		// editmode specific text?
		JTextField bottomText = new JTextField(100);
		bottomText.setEditable(false);
		bottomText.setHorizontalAlignment(JTextField.CENTER);
		bottomText.setText("<Del> to Delete Player, <Ins> to Manually Insert, or edit codename");
		bottomText.setFont(new java.awt.Font("Arial", Font.BOLD, 12));
		this.bottomPanel.add(bottomText);
	}

	//function call to create the PES
	void createPlayerEntryScreen(Controller controller)
	{
		for(int i = 0; i < data.teamRed.length; i++)
		{
			data.teamRed[i] = new Player("R" + i);

			entryPanel.add(data.teamRed[i].idField);
			entryPanel.add(data.teamRed[i].nameField);

			data.teamRed[i].idField.setBounds(150 + entryPanel.getInsets().left, 75 + entryPanel.getInsets().top + 25*i, data.teamRed[i].idSize.width, data.teamRed[i].idSize.height);
			data.teamRed[i].nameField.setBounds(225 + entryPanel.getInsets().left, 75 + entryPanel.getInsets().top + 25*i, data.teamRed[i].nameSize.width, data.teamRed[i].nameSize.height);
			data.teamRed[i].idField.addKeyListener(controller);
			data.teamRed[i].nameField.addKeyListener(controller);
			data.teamRed[i].idField.addFocusListener(controller);
			data.teamRed[i].nameField.addFocusListener(controller);

			//number labels
			data.teamRed[i].playerNumText.setText("" + i);
			data.teamRed[i].playerNumText.setBounds(130 + entryPanel.getInsets().left, 75 + entryPanel.getInsets().top + 25*i, 200, 20);
			data.teamRed[i].playerNumText.setFocusable(false);
			entryPanel.add(data.teamRed[i].playerNumText);
		}

		for(int i = 0; i < data.teamGreen.length; i++)
		{
			data.teamGreen[i] = new Player("G" + i);
			entryPanel.add(data.teamGreen[i].idField);
			entryPanel.add(data.teamGreen[i].nameField);
			data.teamGreen[i].idField.setBounds(550 + entryPanel.getInsets().left, 75 + entryPanel.getInsets().top + 25*i, data.teamGreen[i].idSize.width, data.teamGreen[i].idSize.height);
			data.teamGreen[i].nameField.setBounds(625 + entryPanel.getInsets().left, 75 + entryPanel.getInsets().top + 25*i, data.teamGreen[i].nameSize.width, data.teamGreen[i].nameSize.height);
			data.teamGreen[i].idField.addKeyListener(controller);
			data.teamGreen[i].nameField.addKeyListener(controller);
			data.teamGreen[i].idField.addFocusListener(controller);
			data.teamGreen[i].nameField.addFocusListener(controller);

			//number labels
			data.teamGreen[i].playerNumText.setText("" + i);
			data.teamGreen[i].playerNumText.setBounds(530 + entryPanel.getInsets().left, 75 + entryPanel.getInsets().top + 25*i, 200, 20);
			data.teamGreen[i].playerNumText.setFocusable(false);
			entryPanel.add(data.teamGreen[i].playerNumText);
		}

		JTextArea redText = new JTextArea("RED TEAM");
		redText.setForeground(Color.RED);
		redText.setBackground(Color.BLACK);
		redText.setBounds(entryPanel.getInsets().left + 220, entryPanel.getInsets().top + 40, 200, 20);
		redText.setEditable(false);
		redText.setFont(new java.awt.Font("Arial", Font.BOLD, 20));
		entryPanel.add(redText);

		JTextArea greenText = new JTextArea("GREEN TEAM");
		greenText.setForeground(Color.GREEN);
		greenText.setBackground(Color.BLACK);
		greenText.setBounds(entryPanel.getInsets().left + 610, entryPanel.getInsets().top + 40, 200, 20);
		greenText.setEditable(false);
		greenText.setFont(new java.awt.Font("Arial", Font.BOLD, 20));
		entryPanel.add(greenText);

		// this sets the very first focus tab always has somewhere to go
		data.teamRed[0].idField.requestFocusInWindow();
		data.teamRed[0].idField.setFocusCycleRoot(true);
	}


	//function to create action screen, will only create it once but pull it up each time we switch
	void createPlayerActionScreen(Controller controller)
	{
		topActionPanel = new JPanel(); topActionPanel.setMaximumSize(new Dimension(960, 240));//list of players per team
		botActionPanel = new JPanel(); botActionPanel.setMaximumSize(new Dimension(960, 300));//game action feed
		timeActionPanel = new JPanel(); timeActionPanel.setMaximumSize(new Dimension(960, 40)); //game timer
		this.actionPanel.add(topActionPanel); this.actionPanel.add(botActionPanel); this.actionPanel.add(timeActionPanel);
		topActionPanel.setBackground(Color.BLACK);
		botActionPanel.setBackground(Color.BLACK);
		timeActionPanel.setBackground(Color.red);
		createTeamFeeds();

	}

	//Displays the player action feed which tells when a player has been hit.
	void createTeamFeeds(){
		//Create Red Team side
		JPanel redTeamPanel = new JPanel();
		redTeamPanel.setBackground(Color.RED);
		redTeamPanel.setMaximumSize(new Dimension(17,33));
		//redTeamPanel.setLocation(900, 260);
		
		JTextArea redTeamText = new JTextArea(17,33);
		redTeamText.setAlignmentX(LEFT_ALIGNMENT);
		redTeamText.setBackground(Color.BLACK);
		redTeamText.setForeground(Color.WHITE);
		redTeamText.setText("Bob1 hit by Steve3");

		JScrollPane redTeamScrollPane = new JScrollPane(redTeamText);
		redTeamScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		redTeamScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		redTeamPanel.add(redTeamScrollPane);
	
		//this.botActionPanel.add(redTeamPanel);

		//Create Green Team side
		JPanel greenTeamPanel = new JPanel();
		greenTeamPanel.setBackground(Color.GREEN);
		greenTeamPanel.setMaximumSize(new Dimension(17,33));
	
		JTextArea greenTeamText = new JTextArea(17,33);
		greenTeamText.setAlignmentX(LEFT_ALIGNMENT);
		greenTeamText.setBackground(Color.BLACK);
		greenTeamText.setForeground(Color.WHITE);
		greenTeamText.setText("Bob4 hit by Steve0");

		JScrollPane greenTeamScrollPane = new JScrollPane(greenTeamText);
		greenTeamScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		greenTeamScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		greenTeamPanel.add(greenTeamScrollPane);

		//this.botActionPanel.add(greenTeamScrollPane);

		JSplitPane s1 = new JSplitPane(SwingConstants.VERTICAL, redTeamPanel, greenTeamPanel);
		s1.setOrientation(SwingConstants.VERTICAL);
		this.botActionPanel.add(s1);

		//redTeamFeed.setEditable(false);
		//redTeamFeed.setForeground(Color.GREEN);
		//redTeamFeed.setBackground(Color.BLACK);
		//feedBoxes[0].setMaximumSize(new Dimension(950,290));
	}


	//static method to load an image with a string input of its name
	public static BufferedImage loadImage(String imageName)
	{
		BufferedImage temp = null;
		try
		{
			temp = ImageIO.read(new File(imageName));
		}
		catch(Exception e) 
		{
			e.printStackTrace(System.err);
			System.exit(1);
		}
		return temp;
	}

}
