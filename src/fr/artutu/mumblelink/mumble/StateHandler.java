package fr.artutu.mumblelink.mumble;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.artutu.mumblelink.MumbleLink;
import fr.artutu.mumblelink.utils.PseudoGenerator;

public class StateHandler {
	
	public static boolean needToMuteWithoutBypass(MumbleUser user) {
		String pseudo = PseudoGenerator.getNameFromGeneratedPseudo(user.getName());
		boolean value = MumbleLink.defaultMuteChecker.needToMuteWithoutBypass(user, pseudo);
		return value;
	}
	
	
	public static boolean needToMute(MumbleUser user) {
		boolean result = needToMuteWithoutBypass(user);
		
		try {
			String pseudo = PseudoGenerator.getNameFromGeneratedPseudo(user.getName());
			Player player = Bukkit.getPlayer(pseudo);
			if(player != null && player.isOnline()) {
				if(BypassList.canSpeakDueToBypass(player)) {
					return false;
				}
			}
		} catch(Exception e) {}
		
		return result;
		
	}
	
	public static boolean needToUnmute(MumbleUser user) {
		
		try {
			String pseudo = PseudoGenerator.getNameFromGeneratedPseudo(user.getName());
			Player player = Bukkit.getPlayer(pseudo);
			if(player != null && player.isOnline()) {
				if(BypassList.canSpeakDueToBypass(player)) {
					return true;
				}
			}
		} catch(Exception e) {}
		
		return !needToMuteWithoutBypass(user);
	}

}
