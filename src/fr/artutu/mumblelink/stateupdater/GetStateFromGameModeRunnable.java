package fr.artutu.mumblelink.stateupdater;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import fr.artutu.mumblelink.MumbleLink;
import fr.artutu.mumblelink.utils.GameState;

public class GetStateFromGameModeRunnable extends BukkitRunnable {

	public static Set<StateFinder> stateFinders = new HashSet<>();
	
	@Override
	public void run() {
		GameState newState = null;
		
		for(StateFinder finder : stateFinders) {
			if(newState == null) {
				newState = finder.getState();
			}
		}
		
		if(newState != null && !MumbleLink.gameState.equals(newState)) {
			MumbleLink.gameState = new GameState(newState.getState());
			Bukkit.broadcastMessage(" §b§lMUMBLE §7▪ Le nouveau gamestate est §b§l" + newState.getState().toUpperCase());
		}
	}

}
