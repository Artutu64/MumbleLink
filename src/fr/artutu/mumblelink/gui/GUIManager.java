package fr.artutu.mumblelink.gui;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.artutu.mumblelink.MumbleLink;
import fr.artutu.mumblelink.gui.menus.ConfigMumble;
import fr.artutu.mumblelink.gui.menus.ConfigPlayers;
import fr.artutu.mumblelink.gui.menus.StateGUI;

public class GUIManager {
	
	public static Set<GUI> menus = new HashSet<>();
	
	public GUIManager() {
		Bukkit.getPluginManager().registerEvents(new GUIClickEvent(), MumbleLink.getInstance());
		registerMenu(new ConfigMumble());
		registerMenu(new ConfigPlayers());
		registerMenu(new StateGUI());
	}
	
	public static void registerMenu(GUI gui) {
		if(gui == null) { return; }
		if(!menus.contains(gui)) {
			menus.add(gui);
		}
	}
	
	public static GUI getMenu(String nom) {
		GUI gui = null;
		for(GUI menu : menus) {
			if(gui == null && menu.getNom().equalsIgnoreCase(nom)) {
				gui = menu;
			}
		}
		return gui;
	}
	
	public static void openMenu(Player player, String nom) {
		GUI gui = getMenu(nom);
		if(gui != null) {
			gui.open(player);
		}
	}

}
