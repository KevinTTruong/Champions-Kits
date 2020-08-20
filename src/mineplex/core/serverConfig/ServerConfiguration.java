package mineplex.core.serverConfig;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.plugin.java.JavaPlugin;

import mineplex.core.MiniPlugin;
import mineplex.core.account.CoreClientManager;
import net.minecraft.server.v1_7_R4.PlayerList;

public class ServerConfiguration extends MiniPlugin
{
	
	private Field _playerListMaxPlayers;

	public ServerConfiguration(JavaPlugin plugin, CoreClientManager clientManager)
	{
		super("Server Configuration", plugin);
		
		try
		{
			_playerListMaxPlayers = PlayerList.class.getDeclaredField("maxPlayers");
			_playerListMaxPlayers.setAccessible(true);
			_playerListMaxPlayers.setInt(((CraftServer)_plugin.getServer()).getHandle(), Bukkit.getOnlinePlayers().size());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		_plugin.getServer().setWhitelist(Bukkit.hasWhitelist());
	}
}
