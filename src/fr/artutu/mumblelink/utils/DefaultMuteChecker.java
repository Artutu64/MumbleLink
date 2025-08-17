package fr.artutu.mumblelink.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.artutu.mumblelink.MumbleLink;
import fr.artutu.mumblelink.mumble.MumbleUser;

public class DefaultMuteChecker implements MuteChecker {

	@Override
	public boolean needToMuteWithoutBypass(MumbleUser user, String pseudo) {
		if(!user.isLinked()) return true;
		
		if(MumbleLink.gameState.equals(GameState.ENDING)) {
			return false;
		} else if(MumbleLink.gameState.equals(GameState.PLAYING)) {
			try {
				Player player = Bukkit.getPlayer(pseudo);
				if(player != null && player.isOnline()) {
					if(MumbleLink.inGameChecker.isInGame(player)) {
						return false;
					} else {
						return true;
					}
				} else {
					return true;
				}
			} catch(Exception e) {}
			return true;
		} else if(MumbleLink.gameState.equals(GameState.WAITING)) {
			return true;
		} else if(MumbleLink.gameState.equals(GameState.STARTING)) {
			return false;
		} else {
			return true;
		}
	}

}
