package fr.artutu.mumblelink.stateupdater;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import fr.artutu.mumblelink.utils.GameState;
import fr.ph1lou.werewolfapi.game.WereWolfAPI;
import fr.ph1lou.werewolfplugin.Main;

public class WerewolfStateFinder implements StateFinder {

	@Override
	public GameState getState() {
		try {
			Plugin pl = Bukkit.getPluginManager().getPlugin("WereWolfPlugin");
			if(pl != null && (pl instanceof Main)) {
				Main werewolf = (Main) pl;
				if(werewolf.getWereWolfAPI() != null) {
					WereWolfAPI api = werewolf.getWereWolfAPI();
					switch(api.getState()) {
						case END:
							return GameState.ENDING;
						case GAME:
							return GameState.PLAYING;
						case LOBBY:
							return GameState.WAITING;
						case START:
							return GameState.PLAYING;
						case TRANSPORTATION:
							return GameState.STARTING;
						default:
							break;
						
						}
				}
				return null;
			} else {
				return null;
			}
		} catch(Exception e) {
			return null;
		}
	}
	
	

}
