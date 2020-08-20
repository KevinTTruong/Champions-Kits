package nautilus.game.arcade.command;

import org.bukkit.entity.Player;

import mineplex.core.command.CommandBase;
import mineplex.core.common.Rank;
import nautilus.game.arcade.ArcadeManager;

public class KitUnlockCommand extends CommandBase<ArcadeManager>
{
	public KitUnlockCommand(ArcadeManager plugin)
	{
		super(plugin, Rank.OWNER, new Rank[] {Rank.YOUTUBE, Rank.TWITCH}, new String[] {"youtube", "twitch", "kits"});
	}

	@Override
	public void Execute(Player caller, String[] args)
	{
		Plugin.toggleUnlockKits(caller);
	}
}
