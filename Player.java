import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;

// Container class for player, an array of these Player objects will be utilized for gameplay (array in Main.java??)
class Player
{
	String codename;
	int playerID;
	JTextField idField, nameField;
	Dimension idSize, nameSize;
	JTextArea playerNumText;

	public Player(String codename, int playerID)
	{
		this.codename = codename;
		this.playerID = playerID;
		setTextFields();
	}

	public Player(String codename)
	{
		this.codename = codename;
		this.playerID = -1;
		setTextFields();
	}

	public Player(int playerID)
	{
		this.codename = "";
		this.playerID = playerID;
		setTextFields();
	}

	public Player()
	{
		this.codename = "";
		this.playerID = -1;
		setTextFields();
	}

	void setTextFields()
	{
		//set up text boxes associated with each player slot
		idField = new JTextField(6);
		nameField = new JTextField(15);
		playerNumText = new JTextArea();
		playerNumText.setForeground(Color.WHITE);
		playerNumText.setBackground(Color.BLACK);
		playerNumText.setEditable(false);
		playerNumText.setFont(new java.awt.Font("Arial", Font.PLAIN, 15));

		idSize = idField.getPreferredSize();
		nameSize = nameField.getPreferredSize();
	}
}