package nautilus.game.arcade;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import nautilus.game.arcade.game.games.champions.kits.KitAssassin;
import nautilus.game.arcade.game.games.champions.kits.KitBrute;
import nautilus.game.arcade.game.games.champions.kits.KitKnight;
import nautilus.game.arcade.game.games.champions.kits.KitMage;
import nautilus.game.arcade.game.games.champions.kits.KitRanger;
import nautilus.game.arcade.kit.Kit;

public class BookShelfListener implements Listener{
	ArcadeManager manager;
	Map<String, Kit> classes = new HashMap<String,Kit>();
	
	public BookShelfListener(ArcadeManager manager) {
		this.manager=manager;
		classes.put("Brute", new KitBrute(manager));
		classes.put("Ranger", new KitRanger(manager));
		classes.put("Knight", new KitKnight(manager));
		classes.put("Mage", new KitMage(manager));
		classes.put("Assassin", new KitAssassin(manager));
	}
	
	@EventHandler
	public void playerInteract(PlayerInteractEvent e){
	  if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
		Player player=e.getPlayer();
	    Material m = e.getClickedBlock().getType();
	    if(m.equals(Material.BOOKSHELF)){
	    	String classKit=getClass(player);
	    	if(!classKit.equals("Invalid")) {
	    		classes.get(classKit).Selected(player);
	    		manager.openClassShop(player);
	    	}
	    }
	  }
	}
	
	private String getClass(Player player) {
		if(player.getInventory().getHelmet()==null
		||player.getInventory().getChestplate()==null
		||player.getInventory().getLeggings()==null
    	||player.getInventory().getBoots()==null) {
    		return "Invalid";
    	}
		if(player.getInventory().getHelmet().getType().equals(Material.IRON_HELMET)
		&&player.getInventory().getChestplate().getType().equals(Material.IRON_CHESTPLATE)
		&&player.getInventory().getLeggings().getType().equals(Material.IRON_LEGGINGS)
    	&&player.getInventory().getBoots().getType().equals(Material.IRON_BOOTS)) {
    		return "Knight";
    	}
		else if(player.getInventory().getHelmet().getType().equals(Material.GOLD_HELMET)
			&&player.getInventory().getChestplate().getType().equals(Material.GOLD_CHESTPLATE)
			&&player.getInventory().getLeggings().getType().equals(Material.GOLD_LEGGINGS)
	    	&&player.getInventory().getBoots().getType().equals(Material.GOLD_BOOTS)) {
	    		return "Mage";
	    }
		else if(player.getInventory().getHelmet().getType().equals(Material.DIAMOND_HELMET)
			&&player.getInventory().getChestplate().getType().equals(Material.DIAMOND_CHESTPLATE)
			&&player.getInventory().getLeggings().getType().equals(Material.DIAMOND_LEGGINGS)
	    	&&player.getInventory().getBoots().getType().equals(Material.DIAMOND_BOOTS)) {
	    		return "Brute";
	    }
		else if(player.getInventory().getHelmet().getType().equals(Material.CHAINMAIL_HELMET)
			&&player.getInventory().getChestplate().getType().equals(Material.CHAINMAIL_CHESTPLATE)
			&&player.getInventory().getLeggings().getType().equals(Material.CHAINMAIL_LEGGINGS)
	    	&&player.getInventory().getBoots().getType().equals(Material.CHAINMAIL_BOOTS)) {
	    		return "Ranger";
	    }
		else if(player.getInventory().getHelmet().getType().equals(Material.LEATHER_HELMET)
			&&player.getInventory().getChestplate().getType().equals(Material.LEATHER_CHESTPLATE)
			&&player.getInventory().getLeggings().getType().equals(Material.LEATHER_LEGGINGS)
	    	&&player.getInventory().getBoots().getType().equals(Material.LEATHER_BOOTS)) {
	    		return "Assassin";
	    }
		else {
			return "Invalid";
		}
	}
}
