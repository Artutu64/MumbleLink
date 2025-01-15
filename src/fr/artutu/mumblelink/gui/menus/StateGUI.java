package fr.artutu.mumblelink.gui.menus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.artutu.mumblelink.MumbleLink;
import fr.artutu.mumblelink.gui.GUI;
import fr.artutu.mumblelink.gui.GUIClick;
import fr.artutu.mumblelink.utils.GameState;
import fr.artutu.mumblelink.utils.ItemBuilder;

public class StateGUI extends GUI {
	
	
	public StateGUI() {
		super("§7(§a!§7) §bChange GameState");
		gui = this;
	}

	public static StateGUI gui;

	@Override
	public void open(Player player) {
		Inventory inv = Bukkit.createInventory(null, 27, getNom());
		
		ItemStack vitre = ItemBuilder.create(Material.STAINED_GLASS_PANE).setName("§f").toItem();
		
		inv.setItem(0, vitre);
		inv.setItem(1, vitre);
		inv.setItem(7, vitre);
		inv.setItem(8, vitre);
		
		inv.setItem(9, vitre);
		inv.setItem(17, vitre);
		
		inv.setItem(18, vitre);
		inv.setItem(19, vitre);
		inv.setItem(25, vitre);
		inv.setItem(26, vitre);
		
		int slot = 10;
		GameState[] states = new GameState[]{GameState.WAITING, GameState.STARTING, GameState.PLAYING, GameState.ENDING};
		for(GameState state : states) {
			inv.setItem(slot, ItemBuilder.create(Material.WATCH).setName("§6§l" + state.getState().toString().toUpperCase()).toItem());
			slot += 2;
		}
		
		inv.setItem(22, ItemBuilder.create(Material.ARROW).setName("§c§lRETOUR").toItem());
		
		player.openInventory(inv);
	}

	@Override
	public void handleClick(Player player, GUIClick click, ItemStack item, int rawSlot) {
		if(item.getType() == Material.ARROW) {
			ConfigPlayers.instance.open(player);
			return ;
		}
		
		if(item != null && item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null) {
			String nom = item.getItemMeta().getDisplayName().replace("§6§l", "");
			GameState[] states = new GameState[]{GameState.WAITING, GameState.STARTING, GameState.PLAYING, GameState.ENDING};
			GameState newState = null;
			for(GameState state : states) {
				if(state.getState().equalsIgnoreCase(nom)) {
					newState = state;
				}
			}
			if(newState != null && !newState.equals(MumbleLink.gameState)) {
				MumbleLink.gameState = new GameState(newState.getState());
				Bukkit.broadcastMessage(" §b§lMUMBLE §7▪ Le nouveau gamestate est §b§l" + newState.getState().toUpperCase());
			}
		}
		
	}

}
