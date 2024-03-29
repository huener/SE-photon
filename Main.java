// Will run the program and initialize classes
import javax.swing.JFrame;
import javax.swing.BoxLayout;
import java.awt.Toolkit;
import java.util.concurrent.TimeUnit;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;
import java.lang.Math;


public class Main extends JFrame
{
	//creates the model, controller and view and allows them to communicate with each other
	Data data = new Data();
	Controller controller = new Controller(data);
	View view = new View(controller, data);
	AudioPlayer audio;
	Runnable traffic;
	Thread trafficThread;

	public Main() throws IOException
	{
		
		//initialize sound
		int trackSelect = (int)(Math.random()*8 + 1);	//random track
		System.out.println("Playing track: " + trackSelect);
		try{
			audio = new AudioPlayer(trackSelect);
		}	catch(Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		
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

		view.setLayout(new BoxLayout(view, BoxLayout.Y_AXIS));
		view.addClip(audio.clip); // passes the audio in to view so it can play when a game starts

		view.startGUI(controller);
		view.createPlayerEntryScreen(controller);
		view.createPlayerActionScreen(controller);
		view.mainPanel.add(view.actionPanel);
		view.createTimerScreen();

		//INITIALIZES DATA, IF YOU WANT TO INITIALIZE DATA SOMEWHERE ELSE THEN THIS IS THE LINE YOU NEED
		Data.initializeData("jdbc:postgresql://[db.fbfwczzgqtvrtlenozdg.supabase.co]:5432/postgres", "postgres", "A4Nx57ExIC3EesGw");

		// INITIALIZE TRAFFIC THREAD
		Runnable traffic = new trafficServer(data, view, controller);
		Thread trafficThread = new Thread(traffic);
		trafficThread.start();
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

	//MAIN PROGRAM:: the actual code that is ran on start
	public static void main(String[] args) throws IOException
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

//Class for audio player
class AudioPlayer
{
    AudioInputStream audioInputStream;
    Clip clip;

    public AudioPlayer(int track)
        throws UnsupportedAudioFileException, IOException, LineUnavailableException 
    {
        // create AudioInputStream object
        audioInputStream = 
                AudioSystem.getAudioInputStream(new File("photon_tracks/Track0" + track + ".wav").getAbsoluteFile());
        
        // create clip reference
        clip = AudioSystem.getClip();
        
        // open audioInputStream to the clip
        clip.open(audioInputStream);
        
        clip.loop(Clip.LOOP_CONTINUOUSLY);
	clip.stop();
    }

    public void play() 
    {
        //start the clip
        clip.start();
    }
}
