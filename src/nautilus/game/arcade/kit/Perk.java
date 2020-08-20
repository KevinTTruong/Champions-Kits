package nautilus.game.arcade.kit;

import java.io.Serializable;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import nautilus.game.arcade.ArcadeManager;

public abstract class Perk implements Listener, Serializable
{
	public ArcadeManager Manager;
	public Kit Kit;

	private String _perkName;
	private String[] _perkDesc;

	private boolean _display;
	
	public Perk(String name, String[] perkDesc)
	{
		_perkName = name;
		_perkDesc = perkDesc;
		_display = true;
	}
	
	public Perk(String name, String[] perkDesc, boolean display)
	{
		_perkName = name;
		_perkDesc = perkDesc;
		_display = display;
	}
	
	public void SetHost(Kit kit)
	{
		Manager = kit.Manager;
		Kit = kit;
	}

	public String GetName()
	{	
		return _perkName;
	}
	
	public String[] GetDesc()
	{
		return _perkDesc;
	}

	public boolean IsVisible()
	{
		return _display;
	}
	
	public void Apply(Player player) 
	{
		//Null Default
	}

	public void registeredEvents()
	{
		// When listener has been registered
	}
}
