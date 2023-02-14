// Container class for player, an array of these Player objects will be utilized for gameplay (array in Main.java??)
class Player
{
	String codename;
	int playerID;

	public Player(String codename, int playerID)
	{
		this.codename = codename;
		this.playerID = playerID;
	}

	public Player(String codename)
	{
		this.codename = codename;
		this.playerID = -1;
	}

	public Player(int playerID)
	{
		this.codename = "";
		this.playerID = playerID;
	}

	public Player()
	{
		this.codename = "";
		this.playerID = -1;
	}
}