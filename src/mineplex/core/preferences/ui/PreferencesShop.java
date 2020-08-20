package mineplex.core.preferences.ui;

import mineplex.core.account.CoreClientManager;
import mineplex.core.preferences.PreferencesManager;
import mineplex.core.shop.ShopBase;
import mineplex.core.shop.page.ShopPageBase;

import java.io.Serializable;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PreferencesShop extends ShopBase<PreferencesManager> implements Serializable
{
	public PreferencesShop(PreferencesManager plugin, CoreClientManager clientManager)
	{
		super(plugin, clientManager, "User Preferences");
	}

	@Override
	protected ShopPageBase<PreferencesManager, ? extends ShopBase<PreferencesManager>> buildPagesFor(Player player)
	{
		return new PreferencesPage(getPlugin(), this, getClientManager(), "          " + ChatColor.UNDERLINE + "User Preferences", player);
	}
}