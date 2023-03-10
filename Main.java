// Will run the program and initialize classes
import javax.swing.JFrame;
import javax.swing.BoxLayout;
import java.awt.Toolkit;
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

		view.setLayout(new BoxLayout(view, BoxLayout.Y_AXIS));
		
		view.startGUI(controller);
		view.createPlayerEntryScreen(controller);
		view.createPlayerActionScreen(controller);
		
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
