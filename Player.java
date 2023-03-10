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
	String tableIndex;
	JTextField idField, nameField;
	Dimension idSize, nameSize;
	JTextArea playerNumText;
	int score;

	public Player(String codename, int playerID, String tableIndex)
	{
		this.codename = codename;
		this.playerID = playerID;
		this.tableIndex = tableIndex;
		setTextFields();
		score = 0;
	}

	public Player(String codename, String tableIndex)
	{
		this.codename = codename;
		this.playerID = -1;
		this.tableIndex = tableIndex;
		setTextFields();
		score = 0;
	}

	public Player(int playerID, String tableIndex)
	{
		this.codename = "";
		this.playerID = playerID;
		this.tableIndex = tableIndex;
		setTextFields();
		score = 0;
	}

	public Player(String tableIndex)
	{
		this.codename = "";
		this.playerID = -1;
		this.tableIndex = tableIndex;
		setTextFields();
		score = 0;
	}

	void setTextFields()
	{
		//set up text boxes associated with each player slot
		idField = new JTextField(6);
		nameField = new JTextField(15);
		idField.setName('I' + tableIndex);
		nameField.setName('N' + tableIndex);

		playerNumText = new JTextArea();
		playerNumText.setForeground(Color.WHITE);
		playerNumText.setBackground(Color.BLACK);
		playerNumText.setEditable(false);
		playerNumText.setFont(new java.awt.Font("Arial", Font.PLAIN, 15));

		idSize = idField.getPreferredSize();
		nameSize = nameField.getPreferredSize();
	}


}
