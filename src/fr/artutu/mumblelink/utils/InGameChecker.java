package fr.artutu.mumblelink.utils;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public interface InGameChecker {
	
	public boolean isInGame(Player player);
	
	public static class DefaultInGameChecker implements InGameChecker {

		@Override
		public boolean isInGame(Player player) {
			return !player.getGameMode().equals(GameMode.SPECTATOR);
		}
		
	}

}
