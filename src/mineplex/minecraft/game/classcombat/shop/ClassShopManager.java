package mineplex.minecraft.game.classcombat.shop;

import java.io.Serializable;

import org.bukkit.plugin.java.JavaPlugin;

import mineplex.core.MiniPlugin;
import mineplex.core.account.CoreClientManager;
import mineplex.minecraft.game.classcombat.Class.ClassManager;
import mineplex.minecraft.game.classcombat.Skill.SkillFactory;
import mineplex.minecraft.game.classcombat.item.ItemFactory;

public class ClassShopManager extends MiniPlugin implements Serializable
{
	private ClassManager _classManager;
	private SkillFactory _skillFactory;
	private ItemFactory _itemFactory;
	public ClassShopManager(JavaPlugin plugin, ClassManager classManager, SkillFactory skillFactory, ItemFactory itemFactory, CoreClientManager clientManager)
	{
		super("Class Shop Manager", plugin);
		
		_classManager = classManager;
		_skillFactory = skillFactory;
		_itemFactory = itemFactory;
	}
	
	public ClassManager GetClassManager()
	{
		return _classManager;
	}

	public SkillFactory GetSkillFactory()
	{
		return _skillFactory;
	}

	public ItemFactory GetItemFactory()
	{
		return _itemFactory;
	}
}
