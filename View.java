// Java GUI window setup consists of a navigation panel and a gameplay panel. The gameplay panel shifts between player entry, gameplay, and game over screens as necessary.

import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.BoxLayout;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.border.Border;
// import javax.swing.text.FlowView;
import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import java.awt.Dimension;
import java.awt.ComponentOrientation;
// import java.util.concurrent.TimeUnit;
import javax.swing.Timer;
import javax.sound.sampled.*;

class View extends JPanel
{
	Data data;

	//audio player to play when needed
	Clip clip;

	//Splash Screen
	BufferedImage splash_image;
   	int splashFrames = 0;
    	boolean splash = false;

	//Timer Variables
	int countDown = 3; //number from which we count down
	Timer  timer;



	//Panels Framework
	JPanel titlePanel;
	JPanel mainPanel;
	CardLayout mainPanelCards;
	JPanel navPanel;
	JPanel bottomPanel;

	JPanel topActionPanel;
   	JPanel botActionPanel;
    JPanel timeActionPanel;

	JPanel playerInfoPanel;
    	JPanel teamNamePanel;
    	JPanel actionPlayerColumns[] = new JPanel[4];

	//Switchable panles
	JPanel entryPanel;
	JPanel actionPanel;
	JPanel timerPanel;


	JTextField bottomText;

	JTextArea redPlayerNames[] = new JTextArea[6];
    	JTextArea redPlayerScores[] = new JTextArea[6];
    	JTextArea greenPlayerNames[] = new JTextArea[6];
    	JTextArea greenPlayerScores[] = new JTextArea[6];

	// total team scores
    	// temp??
    	int redTotal = 0;
    	int greenTotal = 0;

    	View(Controller c, Data d)
	{
		mainPanelCards = new CardLayout();
		c.setView(this); 	//sets the controller's view to this view instance in order for the two to be able to communicate
		data = d;
		bottomText = new JTextField(50);
		//initiating and sizing panels
		titlePanel = new JPanel();
		titlePanel.setMaximumSize(new Dimension(960, 50));
		titlePanel.setBackground(Color.black);

		mainPanel = new JPanel();
		mainPanel.setLayout(mainPanelCards);
		mainPanel.setMaximumSize(new Dimension(960, 580));
		mainPanel.setBackground(Color.black);

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

	void startGUI(Controller c)
	{
		this.add(titlePanel);
        	this.add(mainPanel);
        	this.add(navPanel);
        	this.add(bottomPanel);

		createNavigationBar(c);
		createBottomText();
	}

	void createNavigationBar(Controller c) {
		// create navigation bar / instructions
		Border border = BorderFactory.createLineBorder(Color.WHITE);
		// create array of JTextArea
		JTextArea navBar[] = new JTextArea[12];
		for(int i = 0; i < navBar.length; i++)
		{
			navBar[i] = new JTextArea();
			navBar[i].setEditable(false);
			navBar[i].setFocusable(true);
			navBar[i].setFocusTraversalKeysEnabled(true);
			navBar[i].addKeyListener(c);
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
		navBar[2].setText("         F3 \n        View \n      Game");
		navBar[4].setText("         F5 \n       Start\n      Game");
		navBar[6].setText("         F7");
		navBar[7].setText("         F8");
		navBar[9].setText("        F10");
		navBar[11].setText("        F12 \n       Clear \n      Game");
	}

	void createBottomText() {
		// create bottom textbox
		// editmode specific text?
		JTextField bottomText = new JTextField(100);
		bottomText.setEditable(false);
		bottomText.setHorizontalAlignment(JTextField.CENTER);
		bottomText.setText(" Press F1 to toggle Edit Mode. Click the text boxes to enter a new player, move to another to save player. ");
		bottomText.setFont(new java.awt.Font("Arial", Font.BOLD, 12));
		this.bottomPanel.add(bottomText);
	}

	//function call to create the PES
	void createPlayerEntryScreen(Controller controller)
	{
		entryPanel.setLayout(null);
		for(int i = 0; i < data.teamRed.length; i++)
		{
			data.teamRed[i] = new Player("R" + i);

			entryPanel.add(data.teamRed[i].idField);
			entryPanel.add(data.teamRed[i].nameField);

			data.teamRed[i].idField.setBounds(150 + entryPanel.getInsets().left, 50 + entryPanel.getInsets().top + 25*i, data.teamRed[i].idSize.width, data.teamRed[i].idSize.height);
			data.teamRed[i].nameField.setBounds(225 + entryPanel.getInsets().left, 50 + entryPanel.getInsets().top + 25*i, data.teamRed[i].nameSize.width, data.teamRed[i].nameSize.height);
			data.teamRed[i].idField.addKeyListener(controller);
			data.teamRed[i].nameField.addKeyListener(controller);
			data.teamRed[i].idField.addFocusListener(controller);
			data.teamRed[i].nameField.addFocusListener(controller);

			//number labels
			data.teamRed[i].playerNumText.setText("" + i);
			data.teamRed[i].playerNumText.setBounds(130 + entryPanel.getInsets().left, 50 + entryPanel.getInsets().top + 25*i, 200, 20);
			data.teamRed[i].playerNumText.setFocusable(false);
			entryPanel.add(data.teamRed[i].playerNumText);
		}

		for(int i = 0; i < data.teamGreen.length; i++)
		{
			data.teamGreen[i] = new Player("G" + i);
			entryPanel.add(data.teamGreen[i].idField);
			entryPanel.add(data.teamGreen[i].nameField);
			data.teamGreen[i].idField.setBounds(550 + entryPanel.getInsets().left, 50 + entryPanel.getInsets().top + 25*i, data.teamGreen[i].idSize.width, data.teamGreen[i].idSize.height);
			data.teamGreen[i].nameField.setBounds(625 + entryPanel.getInsets().left, 50 + entryPanel.getInsets().top + 25*i, data.teamGreen[i].nameSize.width, data.teamGreen[i].nameSize.height);
			data.teamGreen[i].idField.addKeyListener(controller);
			data.teamGreen[i].nameField.addKeyListener(controller);
			data.teamGreen[i].idField.addFocusListener(controller);
			data.teamGreen[i].nameField.addFocusListener(controller);

			//number labels
			data.teamGreen[i].playerNumText.setText("" + i);
			data.teamGreen[i].playerNumText.setBounds(530 + entryPanel.getInsets().left, 50 + entryPanel.getInsets().top + 25*i, 200, 20);
			data.teamGreen[i].playerNumText.setFocusable(false);
			entryPanel.add(data.teamGreen[i].playerNumText);
		}

		JTextArea redText = new JTextArea("RED TEAM");
		redText.setForeground(Color.RED);
		redText.setBackground(Color.BLACK);
		redText.setBounds(entryPanel.getInsets().left + 220, entryPanel.getInsets().top + 15, 200, 20);
		redText.setEditable(false);
		redText.setFont(new java.awt.Font("Arial", Font.BOLD, 20));
		entryPanel.add(redText);

		JTextArea greenText = new JTextArea("GREEN TEAM");
		greenText.setForeground(Color.GREEN);
		greenText.setBackground(Color.BLACK);
		greenText.setBounds(entryPanel.getInsets().left + 610, entryPanel.getInsets().top + 15, 200, 20);
		greenText.setEditable(false);
		greenText.setFont(new java.awt.Font("Arial", Font.BOLD, 20));
		entryPanel.add(greenText);

		mainPanelCards.addLayoutComponent(entryPanel, "entryPanel");
		mainPanel.add(entryPanel);

		// this sets the very last focus so tab always has somewhere to go
		data.teamGreen[14].nameField.requestFocusInWindow();
		data.teamGreen[14].nameField.setFocusCycleRoot(true);
	}

	//function to create action screen, will only create it once but pull it up each time we switch
	void createPlayerActionScreen(Controller controller)
	{
		topActionPanel = new JPanel(); topActionPanel.setMaximumSize(new Dimension(960, 240));//list of players per team
		botActionPanel = new JPanel(); botActionPanel.setMaximumSize(new Dimension(960, 300));//game action feed
		timeActionPanel = new JPanel(); timeActionPanel.setMaximumSize(new Dimension(960, 40)); //game timer
		this.actionPanel.add(topActionPanel); this.actionPanel.add(botActionPanel); this.actionPanel.add(timeActionPanel);

		topActionPanel.setBackground(Color.black);
		topActionPanel.addKeyListener(controller);
		topActionPanel.setFocusable(true);
		topActionPanel.setFocusTraversalKeysEnabled(false);

		// contains teamNamePanel and playerInfoPanel, y-axis layout so teamName is above playerInfo
		topActionPanel.setLayout(new BoxLayout(topActionPanel, BoxLayout.Y_AXIS)); // changed from red to black to match example video
		createTopActionPanel();

		botActionPanel.setBackground(Color.black);
		botActionPanel.addKeyListener(controller);
		botActionPanel.setFocusable(true);
		botActionPanel.requestFocus();
		botActionPanel.setFocusTraversalKeysEnabled(false);
		createTeamFeeds();

		timeActionPanel.setBackground(Color.red);
		timeActionPanel.addKeyListener(controller);
		timeActionPanel.setFocusable(true);
		timeActionPanel.setFocusTraversalKeysEnabled(false);

		mainPanelCards.addLayoutComponent(actionPanel, "actionPanel");
		mainPanel.add(actionPanel);
	}

	void createTimerScreen(){
		timerPanel = new JPanel();
		timerPanel = drawTimerImage(countDown);
		timerPanel.setPreferredSize(new Dimension(500, 500));
		timerPanel.setBackground(Color.BLUE);
		timerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));


		mainPanelCards.addLayoutComponent(timerPanel, "timerPanel");
		mainPanel.add(timerPanel);

	}


	// --------------------------------------------------------------------------
    	// sets up some panels for the topActionPanel
    	// --------------------------------------------------------------------------
    	void createTopActionPanel() {

        	teamNamePanel = new JPanel();
        	teamNamePanel.setBackground(Color.black);
        	teamNamePanel.setMaximumSize(new Dimension(900, 40));

        	// holds panels (columns) for player info, x-axis so columns are next to each other
        	playerInfoPanel = new JPanel();
        	playerInfoPanel.setBackground(Color.black);
        	playerInfoPanel.setMaximumSize(new Dimension(900, 200));
        	playerInfoPanel.setLayout(new BoxLayout(playerInfoPanel, BoxLayout.X_AXIS));

        	// one panel for each column
        	// 0 is redPlayerNames, 1 is redPlayerScores, 2 is greenPlayerNames, 3 is greenPlayerScores
        	for(int i = 0; i < actionPlayerColumns.length; i++) {
            		actionPlayerColumns[i] = new JPanel();
            		actionPlayerColumns[i].setMaximumSize(new Dimension(225, 200));
            		actionPlayerColumns[i].setLayout(new BoxLayout(actionPlayerColumns[i], BoxLayout.Y_AXIS));
            		actionPlayerColumns[i].setBackground(Color.black);
        	}


        	// spacing textboxes make the high score columns look more centered
        	// and creates some separation between the red team and green team information
        	JTextArea spacing1 = new JTextArea();
        	spacing1.setMaximumSize(new Dimension(50, 200));
        	spacing1.setBackground(Color.black);
        	spacing1.setText("                          ");
        	playerInfoPanel.add(spacing1);

        	this.playerInfoPanel.add(actionPlayerColumns[0]);
        	this.playerInfoPanel.add(actionPlayerColumns[1]);

        	JTextArea spacing2 = new JTextArea();
        	spacing2.setMaximumSize(new Dimension(70, 200));
        	spacing2.setBackground(Color.black);
        	playerInfoPanel.add(spacing2);

        	this.playerInfoPanel.add(actionPlayerColumns[2]);
        	this.playerInfoPanel.add(actionPlayerColumns[3]);

        	JTextArea spacing3 = new JTextArea();
        	spacing3.setMaximumSize(new Dimension(30, 200));
        	spacing3.setBackground(Color.black);
        	playerInfoPanel.add(spacing3);

        	this.topActionPanel.add(teamNamePanel);
        	this.topActionPanel.add(playerInfoPanel);

        	// create rest of upper action screen
        	createTeamNamePanel();
        	createActionRedTeam();
        	createActionGreenTeam();
    	}

    	// set up the panel that contains the team names
    	void createTeamNamePanel() {
        	// create textboxes for team names
        	JTextArea teamnames[] = new JTextArea[3];
        	for(int i = 0; i < 3; i++) {
            		teamnames[i] = new JTextArea();
            		teamnames[i].setSize(new Dimension(300, 40));
		    	teamnames[i].setBackground(Color.BLACK);
		    	teamnames[i].setEditable(false);
		    	teamnames[i].setFont(new java.awt.Font("Arial", Font.BOLD, 20));
        	}
        	// set text color
        	teamnames[0].setForeground(Color.RED);
        	teamnames[2].setForeground(Color.GREEN);
        	// set text
        	teamnames[0].setText("RED TEAM");
        	// textbox for spacing
        	teamnames[1].setText("                                                     ");
        	teamnames[2].setText("GREEN TEAM");
        	// add to panel
        	this.teamNamePanel.add(teamnames[0]);
        	this.teamNamePanel.add(teamnames[1]);
        	this.teamNamePanel.add(teamnames[2]);
    	}

    	// make text areas for red team top player info on the action screen
    	// initialized to the first 5 player names and scores since starting scores should all be 0
    	void createActionRedTeam() {
		//JTextArea redPlayerNames[] = new JTextArea[6];
        	//JTextArea redPlayerScores[] = new JTextArea[6];

		for(int i = 0; i < redPlayerNames.length; i++)
		{
            		redPlayerNames[i] = new JTextArea();
            		redPlayerScores[i] = new JTextArea();

            		// text boxes for scores have right alignment instead of left
            		redPlayerScores[i].setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

			redPlayerNames[i].setEditable(false);
			redPlayerNames[i].setForeground(Color.RED);
			redPlayerNames[i].setBackground(Color.BLACK);

            		redPlayerScores[i].setEditable(false);
			redPlayerScores[i].setForeground(Color.RED);
			redPlayerScores[i].setBackground(Color.BLACK);

			redPlayerNames[i].setMaximumSize(new Dimension(300, 35));
            		redPlayerScores[i].setMaximumSize(new Dimension(125, 35));

            		redPlayerNames[i].setFont(new java.awt.Font("Arial", Font.BOLD, 12));
            		redPlayerScores[i].setFont(new java.awt.Font("Arial", Font.BOLD, 12));

            		// testing text
            		//redPlayerNames[i].setText("samplePlayer" + i);
            		//redPlayerScores[i].setText("sampleScore" + i);

            		// initializes textboxes to first 5 players of red team
            		// updates to score will likely need to update which players are displayed
            		redPlayerNames[i].setText(data.teamRed[i].codename);
            		if(data.teamRed[i].codename != "") {
                		redPlayerScores[i].setText("" + data.teamRed[i].score);
            		}

			// add to panel
			this.actionPlayerColumns[0].add(redPlayerNames[i]);
           		this.actionPlayerColumns[1].add(redPlayerScores[i]);
		}
        	// set up text for the row with the total team score
        	redPlayerNames[5].setText("");
        	redPlayerScores[5].setText("" + redTotal);
    	}

    	// make text areas for green team top player info on the action screen
    	// initialized to the first 5 player names and scores since starting scores should all be 0
    	void createActionGreenTeam() {
        	//JTextArea greenPlayerNames[] = new JTextArea[6];
        	//JTextArea greenPlayerScores[] = new JTextArea[6];

        	for(int i = 0; i < greenPlayerNames.length; i++)
		{
            		greenPlayerNames[i] = new JTextArea();
            		greenPlayerScores[i] = new JTextArea();

            		// text boxes for scores have right alignment instead of left
            		greenPlayerScores[i].setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

            		greenPlayerNames[i].setEditable(false);
			greenPlayerNames[i].setForeground(Color.GREEN);
			greenPlayerNames[i].setBackground(Color.BLACK);

            		greenPlayerScores[i].setEditable(false);
			greenPlayerScores[i].setForeground(Color.GREEN);
			greenPlayerScores[i].setBackground(Color.BLACK);

			// space them out evenly horizontally; same height on the screen
            		greenPlayerNames[i].setMaximumSize(new Dimension(300, 35));
            		greenPlayerScores[i].setMaximumSize(new Dimension(125, 35));

            		greenPlayerNames[i].setFont(new java.awt.Font("Arial", Font.BOLD, 12));
            		greenPlayerScores[i].setFont(new java.awt.Font("Arial", Font.BOLD, 12));

           		// testing text
            		//greenPlayerNames[i].setText("samplePlayer" + i);
            		//greenPlayerScores[i].setText("sampleScore" + i);

            		// initializes textboxes to first 5 players of green team
            		// updates to score will likely need to update which players are displayed
            		greenPlayerNames[i].setText(data.teamGreen[i].codename);
            		if(data.teamGreen[i].codename != "") {
                		greenPlayerScores[i].setText("" + data.teamGreen[i].score);
            		}

			// add to panel
            		this.actionPlayerColumns[2].add(greenPlayerNames[i]);
            		this.actionPlayerColumns[3].add(greenPlayerScores[i]);
		}
        	// set up text for the row with the total team score
        	greenPlayerNames[5].setText("");
        	greenPlayerScores[5].setText("" + greenTotal);
	}

    	// sets topActionScreen textboxes to info for first 5 players of each team
    	// probably shouldn't be used during a game - no score check set up yet
    	void beforeGameActionUpdate() {
        	for(int i = 0; i < redPlayerNames.length; i++) {
            		redPlayerNames[i].setText(data.teamRed[i].codename);
            		if(data.teamRed[i].codename != "") {
                		redPlayerScores[i].setText("" + data.teamRed[i].score);
            		}
        	}
        	for(int i = 0; i < greenPlayerNames.length; i++) {
            		greenPlayerNames[i].setText(data.teamGreen[i].codename);
            		if(data.teamGreen[i].codename != "") {
                		greenPlayerScores[i].setText("" + data.teamGreen[i].score);
            		}
        	}
    	}

    	void testTopActionScreen1() {
        	// initialize some player names so they display
        	for(int i = 0; i < 5; i++) {
            		data.teamRed[i].codename = "Bob" + i;
        	}
        	for(int i = 0; i < 5; i++) {
            		data.teamGreen[i].codename = "Steve" + i;
        	}
    	}
   	void testTopActionScreen2() {
        	// initialize some player names so they display
        	for(int i = 0; i < 5; i++) {
            		data.teamRed[i].codename = "John" + i;
        	}
        	for(int i = 0; i < 5; i++) {
            		data.teamGreen[i].codename = "Bill" + i;
        	}
    	}

			void createTeamFeeds(){
				//Create Red Team side
				JPanel redTeamPanel = new JPanel();
				redTeamPanel.setBackground(Color.RED);
				redTeamPanel.setMaximumSize(new Dimension(17,33));

				JTextArea redTeamText = new JTextArea(17,33);
				redTeamText.setBackground(Color.BLACK);
				redTeamText.setForeground(Color.WHITE);
				redTeamText.setText("Bob1 hit by Steve3");
				redTeamText.setEditable(false);

				JScrollPane redTeamScrollPane = new JScrollPane(redTeamText);
				redTeamScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				redTeamScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
				redTeamPanel.add(redTeamScrollPane);


				//Create Green Team side
				JPanel greenTeamPanel = new JPanel();
				greenTeamPanel.setBackground(Color.GREEN);
				greenTeamPanel.setMaximumSize(new Dimension(17,33));

				JTextArea greenTeamText = new JTextArea(17,33);
				greenTeamText.setBackground(Color.BLACK);
				greenTeamText.setForeground(Color.WHITE);
				greenTeamText.setText("Steve0 hit by Bob4");
				greenTeamText.setEditable(false);

				JScrollPane greenTeamScrollPane = new JScrollPane(greenTeamText);
				greenTeamScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				greenTeamScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
				greenTeamPanel.add(greenTeamScrollPane);


				//Creates a split pane between both scrollboxes
				JSplitPane s1 = new JSplitPane(SwingConstants.VERTICAL, redTeamPanel, greenTeamPanel);
				s1.setOrientation(SwingConstants.VERTICAL);
				this.botActionPanel.add(s1);
			}

			void addClip(Clip a)
			{
				this.clip = a;
			}
   	// --------------------------------------------------------------------------


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
	//-----------------------------------------
	//
	//Countdown
	//
	//-----------------------------------------

		void startCountDown(){

			timer = new Timer(1000, (e) -> {

					System.out.println("count " + countDown);
					timerPanel = drawTimerImage(countDown);
					mainPanelCards.addLayoutComponent(timerPanel, "timerPanel"+countDown);
					mainPanel.add(timerPanel);
					mainPanelCards.show(mainPanel, "timerPanel"+countDown);

					 countDown--;
					 repaint();
					if (countDown == -1) { // countdown finished
						timer.stop(); // stop the timer
						//THIS LINE STARTS THE GAME SCREEN
						mainPanelCards.show(mainPanel, "actionPanel");
						beforeGameActionUpdate();
					}
                });
                timer.setInitialDelay(0);
				clip.start();
                timer.start();

			countDown=3;

		}
	public static JPanel drawTimerImage(int num) {
        JPanel panel = new JPanel() {
		BufferedImage countImage;

            {
                try {
					countImage = ImageIO.read(new File("count_"+(num)+".png"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLUE);
                g.fillRect(0, 0, getWidth(), getHeight());
                g.drawImage(countImage, 0, 0, getWidth(), getHeight(), null);
            }
        };
        panel.setPreferredSize(new Dimension(500, 500));
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        return panel;
    }



}
