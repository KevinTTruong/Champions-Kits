package mineplex.core.movement;

import java.io.Serializable;

import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

import mineplex.core.MiniClientPlugin;
import mineplex.core.common.util.UtilMath;
import mineplex.core.updater.UpdateType;
import mineplex.core.updater.event.UpdateEvent;

public class Movement extends MiniClientPlugin<ClientMovement> implements Serializable
{
	public Movement(JavaPlugin plugin)
	{
		super("Movement", plugin);
	}
	
	@EventHandler
	public void Update(UpdateEvent event)
	{
		if (event.getType() == UpdateType.TICK)
		{
			for (Player cur : getPlugin().getServer().getOnlinePlayers())
			{	
				ClientMovement player = Get(cur);
				
				if (player.LastLocation != null)
					if (UtilMath.offset(player.LastLocation, cur.getLocation()) > 0)
						player.LastMovement = System.currentTimeMillis();
				
				player.LastLocation = cur.getLocation();
				
				//Save Grounded
				if (((CraftPlayer)cur).getHandle().onGround)
					player.LastGrounded = System.currentTimeMillis();
			}
		}
	}

	@Override
	protected ClientMovement AddPlayer(String player)
	{
		return new ClientMovement();
	}
}
