package mineplex.core.teleport;

import java.io.Serializable;
import java.util.LinkedList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import mineplex.core.MiniPlugin;
import mineplex.core.account.event.ClientUnloadEvent;
import mineplex.core.common.util.F;
import mineplex.core.common.util.NautHashMap;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.common.util.UtilServer;
import mineplex.core.common.util.UtilWorld;
import mineplex.core.teleport.event.MineplexTeleportEvent;
import mineplex.core.updater.UpdateType;
import mineplex.core.updater.event.UpdateEvent;

public class Teleport extends MiniPlugin implements Serializable
{
	private LinkedList<Teleporter> teleportList = new LinkedList<Teleporter>();
	private NautHashMap<String, LinkedList<Location>> _tpHistory = new NautHashMap<String, LinkedList<Location>>();
	public Teleport(JavaPlugin plugin) 
	{
		super("Teleport", plugin);
		
		getPlugin().getConfig().getString("serverstatus.name");
	}

	/*
	@Override
	public void addCommands()
	{
		addCommand(new TeleportCommand(this));
	}
	*/
	

	@EventHandler
	public void UnloadHistory(ClientUnloadEvent event)
	{
		_tpHistory.remove(event.GetName());
	}
	
	@EventHandler
	public void update(UpdateEvent event)
	{
		if (event.getType() != UpdateType.TICK)
			return; 
		
		if (teleportList.isEmpty())
			return;

		teleportList.removeFirst().doTeleport();
	}

	public void playerToPlayer(Player caller, String from, String to)
	{
		LinkedList<Player> listA = new LinkedList<Player>();

		//ALL
		if (from.equals("%ALL%"))
			for (Player cur : UtilServer.getPlayers())
				listA.add(cur);
		//Normal
		else
			listA = UtilPlayer.matchOnline(caller, from, true);

		//To
		Player pB = UtilPlayer.searchOnline(caller, to, true);

		if (listA.isEmpty() || pB == null)
			return;

		if (listA.size() == 1)
		{
			Player pA = listA.getFirst();

			String mA = null;
			String mB = null;

			//Inform
			if (pA.equals(caller))
			{
				mA = F.main("Teleport", "You teleported to " + F.elem(pB.getName()) + ".");
			}
			else if (pB.equals(caller))
			{
				mA = F.main("Teleport", F.elem(caller.getName()) + " teleported you to themself.");
				mB = F.main("Teleport", "You teleported " + F.elem(pA.getName()) + " to yourself.");
			}	
			else
			{
				mA = F.main("Teleport", F.elem(caller.getName()) + " teleported you to " + F.elem(pB.getName()) + ".");
				mB = F.main("Teleport", "You teleported " + F.elem(pA.getName()) + " to " + F.elem(pB.getName()) + ".");
			}

			//Register
			Add(pA, pB.getLocation(), mA, true, caller, mB,
					pA.getName() + " teleported to " + pB.getName() + " via " + caller.getName());
			return;
		}

		boolean first = true;
		for (Player pA : listA)
		{
			String mA = null;
			String mB = null;

			//Inform
			if (pA.equals(caller))
			{
				mA = F.main("Teleport", "You teleported to " + F.elem(pB.getName()) + ".");
			}
			else if (pB.equals(caller))
			{
				mA = F.main("Teleport", F.elem(caller.getName()) + " teleported you to themself.");
				mB = F.main("Teleport", "You teleported " + F.elem(listA.size() + " Players") + " to yourself.");
			}	
			else
			{
				mA = F.main("Teleport", F.elem(caller.getName()) + " teleported you to " + F.elem(pB.getName()) + ".");
				mB = F.main("Teleport", "You teleported " + F.elem(listA.size() + " Players") + " to " + F.elem(pB.getName()) + ".");
			}

			//Register
			if (first)		
				Add(pA, pB.getLocation(), mA, true, caller, mB,	pA.getName() + " teleported to " + pB.getName() + " via " + caller.getName());
			
			else
				Add(pA, pB.getLocation(), mA, true, caller, null, pA.getName() + " teleported to " + pB.getName() + " via " + caller.getName());
			
			first = false;
		}
	}

	public void playerToLoc(Player caller, String target, String sX, String sY, String sZ) 
	{
		playerToLoc(caller, target, caller.getWorld().getName(), sX, sY, sZ);
	}
	
	public void playerToLoc(Player caller, String target, String world, String sX, String sY, String sZ) 
	{
		Player player = UtilPlayer.searchOnline(caller, target, true);

		if (player == null)
			return;

		try
		{
			int x = sX.matches(".*[0-9]") ? Integer.parseInt(sX.replace("~", "")) : 0;
			int y = sY.matches(".*[0-9]") ? Integer.parseInt(sY.replace("~", "")) : 0;
			int z = sZ.matches(".*[0-9]") ? Integer.parseInt(sZ.replace("~", "")) : 0;

			Location pLoc = player.getLocation();

			if (sX.startsWith("~"))
				x += pLoc.getBlockX();

			if (sY.startsWith("~"))
				y += pLoc.getBlockY();

			if (sZ.startsWith("~"))
				z += pLoc.getBlockZ();

			Location loc = new Location(Bukkit.getWorld(world), x, y, z);

			//Inform
			String mA = null;
			if (caller == player)	mA = F.main("Teleport", "You teleported to " + UtilWorld.locToStrClean(loc) + ".");
			else					mA = F.main("Teleport", F.elem(caller.getName()) + " teleported you to " + UtilWorld.locToStrClean(loc) + ".");

			//Register
			Add(player, loc, mA, true, caller, null, player.getName() + " teleported to " + UtilWorld.locToStrClean(loc) + " via " + caller.getName());
		}
		catch (Exception e)
		{
			UtilPlayer.message(caller, F.main("Teleport", "Invalid Location [" + sX + "," + sY + "," + sZ + "]."));
			return;
		}
	}

	public void Add(Player pA, Location loc, String mA, boolean record, Player pB, String mB, String log)
	{
		teleportList.addLast(new Teleporter(this, pA, pB, mA, mB, loc, record, log));
	}
	
	public void TP(Player player, Location getLocation)
	{
		TP(player, getLocation, true);
	}
	
	public void TP(Player player, Location loc, boolean dettach) 
	{
		//Event
		MineplexTeleportEvent event = new MineplexTeleportEvent(player, loc);
		UtilServer.getServer().getPluginManager().callEvent(event);
		
		if (event.isCancelled())
			return;
		
		if (dettach)
		{
			player.eject();
			player.leaveVehicle();
		}
		
		player.setFallDistance(0);
		player.setVelocity(new Vector(0,0,0));
		
		player.teleport(loc);
	}
	
	public LinkedList<Location> GetTPHistory(Player player)
	{
		return _tpHistory.get(player.getName());
	}
}
