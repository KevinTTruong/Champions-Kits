package mineplex.minecraft.game.classcombat.Class;

import java.io.Serializable;
import java.util.HashSet;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import mineplex.minecraft.game.classcombat.Class.repository.token.CustomBuildToken;
import mineplex.minecraft.game.classcombat.Skill.ISkill;

public interface IPvpClass
{
	public enum ClassType implements Serializable
	{
		Global,
		Knight,
		Ranger,
		Assassin,
		Mage,
		Brute,
		Shifter
	}
	
	int GetSalesPackageId();
    String GetName();
    ClassType GetType();
    Material GetHead();
    Material GetChestplate();
    Material GetLeggings();
    Material GetBoots();
    HashSet<ISkill> GetSkills();
    
    void checkEquip();
	Integer GetCost();
	boolean IsFree();
	String[] GetDesc();
	void ApplyArmor(Player caller);
	CustomBuildToken getDefaultBuild();
}
