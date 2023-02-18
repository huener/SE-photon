// Will run the program and initialize classes

import javax.swing.JFrame;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Insets;
import java.awt.Color;
import java.util.concurrent.TimeUnit;


public class Main extends JFrame
{
	//creates the model, controller and view and allows them to communicate with each other
	Data data = new Data();
	Controller controller = new Controller(data);
	View view = new View(controller, data);
	

	public Main()
	{
		
		// JFrame window customization
		this.setTitle("Photon Laser Tag Simulation (ﾉ◕ヮ◕)ﾉ*:･ﾟ✧");
		this.setSize(960, 720);
		this.setFocusable(true);
		this.setResizable(false);
		this.getContentPane().add(view);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		// passes listeners through the view and game in order to have controller do the controlling
		this.addKeyListener(controller);
        splashScreen();	// draw splash screen and sleep before text boxes are created

		view.setLayout(null);
		Insets insets = view.getInsets();
	
		view.createNavigationBar(insets);
        view.createBottomText(insets);   
		
		createPlayerEntryScreen(insets);
		//INITIALIZES DATA, IF YOU WANT TO INITIALIZE DATA SOMEWHERE ELSE THEN THIS IS THE LINE YOU NEED
		Data.initializeData("jdbc:postgresql://[db.fbfwczzgqtvrtlenozdg.supabase.co]:5432/postgres", "postgres", "A4Nx57ExIC3EesGw");
	}

    void splashScreen()
	{
        view.repaint();
        
        // let splash screen display for 3 seconds
        if(view.splash == false)
		{
            try
            {
                TimeUnit.SECONDS.sleep(3);
            } catch(Exception e) 
			{
				e.printStackTrace();
				System.exit(1);
			}
			view.splash = true; // toggle splash variable so splash screen stops showing
		}
	}

	//function call to create the PES
	void createPlayerEntryScreen(Insets insets)
	{
		for(int i = 0; i < data.teamRed.length; i++)
		{
			data.teamRed[i] = new Player("R" + i);

			view.add(data.teamRed[i].idField);
			view.add(data.teamRed[i].nameField);

			data.teamRed[i].idField.setBounds(150 + insets.left, 75 + insets.top + 25*i, data.teamRed[i].idSize.width, data.teamRed[i].idSize.height);
			data.teamRed[i].nameField.setBounds(225 + insets.left, 75 + insets.top + 25*i, data.teamRed[i].nameSize.width, data.teamRed[i].nameSize.height);
			data.teamRed[i].idField.addKeyListener(controller);
			data.teamRed[i].nameField.addKeyListener(controller);
			data.teamRed[i].idField.addFocusListener(controller);
			data.teamRed[i].nameField.addFocusListener(controller);

			//number labels
			data.teamRed[i].playerNumText.setText("" + i);
			data.teamRed[i].playerNumText.setBounds(130 + insets.left, 75 + insets.top + 25*i, 200, 20);
			data.teamRed[i].playerNumText.setFocusable(false);
			view.add(data.teamRed[i].playerNumText);
		}

		for(int i = 0; i < data.teamGreen.length; i++)
		{
			data.teamGreen[i] = new Player("G" + i);
			view.add(data.teamGreen[i].idField);
			view.add(data.teamGreen[i].nameField);
			data.teamGreen[i].idField.setBounds(550 + insets.left, 75 + insets.top + 25*i, data.teamGreen[i].idSize.width, data.teamGreen[i].idSize.height);
			data.teamGreen[i].nameField.setBounds(625 + insets.left, 75 + insets.top + 25*i, data.teamGreen[i].nameSize.width, data.teamGreen[i].nameSize.height);
			data.teamGreen[i].idField.addKeyListener(controller);
			data.teamGreen[i].nameField.addKeyListener(controller);
			data.teamGreen[i].idField.addFocusListener(controller);
			data.teamGreen[i].nameField.addFocusListener(controller);

			//number labels
			data.teamGreen[i].playerNumText.setText("" + i);
			data.teamGreen[i].playerNumText.setBounds(530 + insets.left, 75 + insets.top + 25*i, 200, 20);
			data.teamGreen[i].playerNumText.setFocusable(false);
			view.add(data.teamGreen[i].playerNumText);
		}

		JTextArea redText = new JTextArea("RED TEAM");
		redText.setForeground(Color.RED);
		redText.setBackground(Color.BLACK);
		redText.setBounds(insets.left + 220, insets.top + 40, 200, 20);
		redText.setEditable(false);
		redText.setFont(new java.awt.Font("Arial", Font.BOLD, 20));
		view.add(redText);

		JTextArea greenText = new JTextArea("GREEN TEAM");
		greenText.setForeground(Color.GREEN);
		greenText.setBackground(Color.BLACK);
		greenText.setBounds(insets.left + 610, insets.top + 40, 200, 20);
		greenText.setEditable(false);
		greenText.setFont(new java.awt.Font("Arial", Font.BOLD, 20));
		view.add(greenText);

		// this sets the very first focus tab always has somewhere to go
		data.teamRed[0].idField.requestFocusInWindow();
		data.teamRed[0].idField.setFocusCycleRoot(true);
	}

	//MAIN PROGRAM:: the actual code that is ran on start
	public static void main(String[] args)
	{
        	Main instance = new Main();
		instance.run();	//actually runs the game
	}

	// Framelimiter
	public void run()
	{
		while(true)
		{
			controller.update();
			view.revalidate();
			view.repaint(); // Indirectly calls View.paintComponent
			Toolkit.getDefaultToolkit().sync(); // Updates screen

			// Go to sleep for 40 milliseconds
			try
			{
				Thread.sleep(40); //changed to update every 40 ms, or 25 fps
			} catch(Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
}
