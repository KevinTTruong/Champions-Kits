package nautilus.game.arcade.shop;

import java.io.Serializable;

import org.bukkit.entity.Player;

import mineplex.core.account.CoreClientManager;
import mineplex.core.common.CurrencyType;
import mineplex.core.shop.ShopBase;
import mineplex.core.shop.page.ShopPageBase;
import nautilus.game.arcade.ArcadeManager;

public class ArcadeShop extends ShopBase<ArcadeManager> implements Serializable
{
	public ArcadeShop(ArcadeManager plugin, CoreClientManager clientManager)
	{
		super(plugin, clientManager, "Shop", CurrencyType.Gems);
	}

	@Override
	protected ShopPageBase<ArcadeManager, ? extends ShopBase<ArcadeManager>> buildPagesFor(Player player)
	{
		return null;
	}
}
