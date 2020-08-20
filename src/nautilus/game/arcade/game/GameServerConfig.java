package nautilus.game.arcade.game;

import java.io.Serializable;
import java.util.ArrayList;

import nautilus.game.arcade.GameType;

public class GameServerConfig  implements Serializable
{
	public String ServerType = "Minigames";
	public int MinPlayers = 1;
	public int MaxPlayers = 100;
	public ArrayList<GameType> GameList = new ArrayList<GameType>();
	
	//Flags
	public String HostName = null;
	
	public boolean Tournament = false;
	
	public boolean TournamentPoints = false;
	
	public boolean TeamRejoin = false;
	public boolean TeamAutoJoin = true;
	public boolean TeamForceBalance = true;
	
	public boolean GameAutoStart = true;
	public boolean GameTimeout = true;

	public boolean RewardGems = true;
	public boolean RewardItems = true;	
	public boolean RewardStats = true;
	public boolean RewardAchievements = true;
	
	public boolean HotbarInventory = true;
	public boolean HotbarHubClock = true;
	
	public boolean PlayerKickIdle = true;
	
	public boolean PublicServer = false;

	public boolean PlayerServerWhitelist = false;
	
	public boolean IsValid()
	{
		//addGames();
		return ServerType != null && MinPlayers != -1 && MaxPlayers != -1;
	}
	/*
	public void addGames(){
		GameList.add(GameType.SurvivalGames);
	}
	*/
	
}
