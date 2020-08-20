package mineplex.core.account;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import mineplex.core.MiniPlugin;
import mineplex.core.account.event.ClientUnloadEvent;
import mineplex.core.common.Rank;
import mineplex.core.common.util.NautHashMap;
import mineplex.core.updater.UpdateType;
import mineplex.core.updater.event.UpdateEvent;
import nautilus.game.arcade.ArcadeManager;

public class CoreClientManager extends MiniPlugin implements Serializable
{
	
	private JavaPlugin _plugin;
	public NautHashMap<String, CoreClient> _clientList;
	private HashSet<String> _duplicateLoginGlitchPreventionList;
	
	private NautHashMap<String, ILoginProcessor> _loginProcessors = new NautHashMap<String, ILoginProcessor>();
	
	public Object _clientLock = new Object();
	
	
	public CoreClientManager(JavaPlugin plugin, String webServer, ArcadeManager manager)
	{
		super("Client Manager", plugin);
		
		_plugin = plugin;
        _clientList = new NautHashMap<String, CoreClient>();
        _duplicateLoginGlitchPreventionList = new HashSet<String>();
	}

	public CoreClient Add(String name)
	{
		CoreClient newClient = null;
	    
		if (newClient == null)
		{
			newClient = new CoreClient(name);
		}
		
		CoreClient oldClient = null;
		
		synchronized(_clientLock)
		{
			oldClient = _clientList.put(name, newClient);
		}
	    
	    if (oldClient != null)
	    {
	    	oldClient.Delete();
	    }

		return newClient;
	}

	public void Del(String name)
	{
		synchronized(_clientLock)
		{
			_clientList.remove(name); 
		}
		
		_plugin.getServer().getPluginManager().callEvent(new ClientUnloadEvent(name));
	}

	public CoreClient Get(String name)
	{
		synchronized(_clientLock)
		{
			return _clientList.get(name);
		}
	}
	
	public CoreClient Get(Player player)
	{
		synchronized(_clientLock)
		{
			return _clientList.get(player.getName());
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void Login(PlayerLoginEvent event)
	{
		synchronized(_clientLock)
		{
			if (!_clientList.containsKey(event.getPlayer().getName()))
			{
				_clientList.put(event.getPlayer().getName(), new CoreClient(event.getPlayer().getName()));
			}
		}

        CoreClient client = Get(event.getPlayer().getName());
        
        if(event.getPlayer().isOp()){
        
         client.SetRank(Rank.OWNER);
         
        } else {
        	
        	client.SetRank(Rank.LEGEND);
        	
        }
        
        client.SetPlayer(event.getPlayer());
		
        // Reserved Slot Check
		if (Bukkit.getOnlinePlayers().size() >= Bukkit.getServer().getMaxPlayers())
		{
			if (client.GetRank().Has(event.getPlayer(), Rank.ULTRA, false))
			{
				event.allow();
				event.setResult(PlayerLoginEvent.Result.ALLOWED);
				return;
			}
			
			event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "This server is full and no longer accepts players.");
		}
	}
	
	@EventHandler
	public void Kick(PlayerKickEvent event)
	{
		if (event.getReason().contains("You logged in from another location"))
		{
			_duplicateLoginGlitchPreventionList.add(event.getPlayer().getName());
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void Quit(PlayerQuitEvent event)
	{
		// When an account is logged in to this server and the same account name logs in
		// Then it Fires events in this order  (original, new for accounts)
		// AsyncPreLogin -> new
		// PlayerLogin -> new
		// PlayerKick -> old
		// PlayerQuit -> old
		// Then it glitches because it added new, but then removed old afterwards since its based on name as key.
		
		if (!_duplicateLoginGlitchPreventionList.contains(event.getPlayer().getName()))
		{
			Del(event.getPlayer().getName());
			_duplicateLoginGlitchPreventionList.remove(event.getPlayer().getName());
		}
	}
	
	@EventHandler
	public void cleanGlitchedClients(UpdateEvent event)
	{
		if (event.getType() != UpdateType.SLOW)
			return;
		
		synchronized(_clientLock)
		{
			for (Iterator<Entry<String, CoreClient>> clientIterator = _clientList.entrySet().iterator(); clientIterator.hasNext();)
			{
				Player clientPlayer = clientIterator.next().getValue().GetPlayer();
				
				if (clientPlayer != null && !clientPlayer.isOnline())
				{
					clientIterator.remove();
					
					if (clientPlayer != null)
						_plugin.getServer().getPluginManager().callEvent(new ClientUnloadEvent(clientPlayer.getName()));
				}
			}
		}
	}
	
	@EventHandler
	public void debug(UpdateEvent event)
	{
		if (event.getType() != UpdateType.SLOWER)
			return;
		
//		System.out.println("=====");
//		System.out.println("Connecting  : " + _clientsConnecting.get());
//		System.out.println("Processing  : " + _clientsProcessing.get());
//		System.out.println("=====");
	}
	
	public void addStoredProcedureLoginProcessor(ILoginProcessor processor)
	{
		_loginProcessors.put(processor.getName(), processor);
	}
	
	public boolean hasRank(Player player, Rank rank)
	{
		CoreClient client = Get(player);
		if (client == null)
			return false;
		
		return client.GetRank().Has(rank);
	}
}