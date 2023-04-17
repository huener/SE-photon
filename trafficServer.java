// Java program to illustrate Server side
// Implementation using DatagramSocket
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
// import java.net.InetAddress;
// import java.net.SocketException;

public class trafficServer implements Runnable
{
	Boolean stop;
	Data d;
	View v;
	Controller c;
	DatagramSocket ds;
	byte[] receive;
	DatagramPacket DpReceive;


	trafficServer(Data data, View view, Controller controller) throws IOException
	{		
		d = data;
		v = view;
		c = controller;
		stop = false;

		// Step 1 : Create a socket to listen at port 7501
		ds = new DatagramSocket(7501);
		receive = new byte[65535];
		DpReceive = null;
	}

	public void run()
	{
		while (!stop)
		{
			// Step 2 : create a DatgramPacket to receive the data.
			DpReceive = new DatagramPacket(receive, receive.length);

			// Step 3 : revieve the data in byte buffer.
			try {
				ds.receive(DpReceive);
			} catch (IOException e) {
				e.printStackTrace();
			}

			// Step 4: seperate the string into base parts and update data ONLY IF we have started the game
			System.out.println(data(receive));

			if (c.mainPanelSelect == 1)
			{
				// Split data into appropriate parts
				String message = data(receive).toString();
				int slice = message.indexOf(':');
				int winner = Integer.parseInt(message.substring(0,slice));
				int loser = Integer.parseInt(message.substring(slice+1));
				
				int winnerIndex = -5;
				int loserIndex = -5;
				char team = 'r';

				// Send scoring data to appropriate player
				for (int i = 0; i < d.teamRed.length; i++)
				{	
					if(d.teamRed[i].playerID == winner)
					{
						d.teamRed[i].score += 10;
						System.out.println("Red " + d.teamRed[i].codename + " +10");
						winnerIndex = i;
						team = 'r';
					}
					if(d.teamRed[i].playerID == loser)
					{
						if (d.teamRed[i].score > 0)
						{
							d.teamRed[i].score -= 10;
							System.out.println("Red " + d.teamRed[i].codename + " -10");
						}
						loserIndex = i;
					}
				}
				
				for (int i = 0; i < d.teamGreen.length; i++)
				{	
					if(d.teamGreen[i].playerID == winner)
					{
						d.teamGreen[i].score += 10;
						System.out.println("Green " + d.teamGreen[i].codename + " +10");
						winnerIndex = i;
						team = 'g';
					}
					if(d.teamGreen[i].playerID == loser)
					{

						if (d.teamGreen[i].score > 0)
						{
							d.teamGreen[i].score -= 10;
							System.out.println("Green " + d.teamGreen[i].codename + " -10");
						}
						loserIndex = i;
					}
				}
				v.ActionFeedUpdate(winnerIndex, loserIndex, team);
			}

			// Exit the server if the client sends "bye"
			if (data(receive).toString().equals("bye"))
			{
				System.out.println("Client sent bye.....EXITING");
				break;
			}

			// Clear the buffer after every message.
			receive = new byte[65535];
		}
	}

	// A utility method to convert the byte array
	// data into a string representation.
	public static StringBuilder data(byte[] a)
	{
		if (a == null)
			return null;
		StringBuilder ret = new StringBuilder();
		int i = 0;
		while (a[i] != 0)
		{
			ret.append((char) a[i]);
			i++;
		}
		return ret;
	}
}
