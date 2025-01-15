package fr.artutu.mumblelink.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GUIClickEvent implements Listener {
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Player player = (Player) e.getWhoClicked();
		GUIClick typeClick = null;
		if(e.getAction().equals(InventoryAction.PICKUP_ALL)) {
			typeClick = GUIClick.GAUCHE;
		} else if(e.getAction().equals(InventoryAction.PICKUP_HALF)) {
			typeClick = GUIClick.DROIT;
		}
		if(typeClick == null) { return ; }
		ItemStack item = e.getCurrentItem();
		if(item == null || item.getType().equals(Material.AIR)) {
			return ;
		}
		GUI gui = GUIManager.getMenu(e.getInventory().getName());
		if(gui == null) { return ; }
		e.setCancelled(true);
		gui.handleClick(player, typeClick, item, e.getRawSlot());
	}

}
