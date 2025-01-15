package fr.artutu.mumblelink.mumble;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class BypassList implements Listener {
	
	public static Set<UUID> mutedBypassList = new HashSet<>();
	
	public static void addMuteException(Player player) {
		if(!mutedBypassList.contains(player.getUniqueId())) {
			mutedBypassList.add(player.getUniqueId());
		}
	}
	
	public static void removeMuteException(Player player) {
		while(mutedBypassList.contains(player.getUniqueId())) {
			mutedBypassList.remove(player.getUniqueId());
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		removeMuteException(e.getPlayer());
	}
	
	public static boolean canSpeakDueToBypass(Player player) {
		return mutedBypassList.contains(player.getUniqueId());
	}

}
