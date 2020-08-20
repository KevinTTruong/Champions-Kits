package mineplex.minecraft.game.classcombat.shop.button;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import mineplex.core.shop.item.IButton;
import mineplex.minecraft.game.classcombat.item.Item;
import mineplex.minecraft.game.classcombat.shop.page.SkillPage;

public class PurchaseItemButton implements IButton
{
	private SkillPage _page;
	private Item _item;
	
	public PurchaseItemButton(SkillPage page, Item item)
	{
		_page = page;
		_item = item;
	}

	@Override
	public void onClick(Player player, ClickType clickType)
	{
		_page.PurchaseItem(player, _item);
	}
}
