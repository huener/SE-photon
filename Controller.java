// handles keyboard navigation commands

//import java.awt.event.MouseListener;
//import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;


class Controller implements ActionListener, KeyListener
{
	View view;

	//ESCAPING
	boolean keyEsc;
	boolean keyQ;

	void setView(View v)
	{
		view = v;
	}

    public void actionPerformed(ActionEvent e)
	{
		
	}

	//KeyListener methods
	public void keyPressed(KeyEvent e)
	{
		
	}

	public void keyReleased(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			//escaping (but only on release cause its easier plus that's the last thing you'll do in this program)
			case KeyEvent.VK_ESCAPE: keyEsc = true; break;
			case KeyEvent.VK_Q: keyQ = true; break;
		}
	}

	public void keyTyped(KeyEvent e)
	{
	}

	//checks if key is pressed when update is pushed
	void update()
	{
		//escaping the program
		if(keyEsc) System.exit(0);
		if(keyQ) System.exit(0);
	}
}
