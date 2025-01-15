package fr.artutu.mumblelink.mumble;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.artutu.mumblelink.MumbleLink;

public class StateHandler {
	
	public static boolean needToMuteWithoutBypass(MumbleUser user) {
		return MumbleLink.defaultMuteChecker.needToMuteWithoutBypass(user);
	}
	
	
	public static boolean needToMute(MumbleUser user) {
		boolean result = needToMuteWithoutBypass(user);
		
		try {
			Player player = Bukkit.getPlayer(user.getName());
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
			Player player = Bukkit.getPlayer(user.getName());
			if(player != null && player.isOnline()) {
				if(BypassList.canSpeakDueToBypass(player)) {
					return true;
				}
			}
		} catch(Exception e) {}
		
		return !needToMuteWithoutBypass(user);
	}

}
