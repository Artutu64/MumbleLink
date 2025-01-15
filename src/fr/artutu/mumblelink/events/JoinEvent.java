package fr.artutu.mumblelink.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.artutu.mumblelink.MumbleLink;
import fr.artutu.mumblelink.config.PluginData;
import fr.artutu.mumblelink.requests.WebRequest;
import fr.artutu.mumblelink.requests.WebRequest.Method;
import fr.artutu.mumblelink.utils.HeadBuilder;
import fr.artutu.mumblelink.utils.PseudoGenerator;

public class JoinEvent implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		HeadBuilder.fetchSkin(e.getPlayer());

		
		if(!MumbleLink.PSEUDOS.containsKey(e.getPlayer().getUniqueId())) {
			String mumblePseudo = PseudoGenerator.generatePseudo(e.getPlayer());
			MumbleLink.PSEUDOS.put(e.getPlayer().getUniqueId(), mumblePseudo);
		}
		
		if(PluginData.mumbleServer != null) {
			Player player = e.getPlayer();
			String url = "servers/shortlink?uuid=" + player.getUniqueId().toString() +"&port=" + PluginData.mumbleServer.getPort() + "&host=" + PluginData.mumbleServer.getHost() +"&pseudo=" + MumbleLink.PSEUDOS.get(player.getUniqueId());
			Bukkit.getScheduler().runTaskLaterAsynchronously(MumbleLink.getInstance(), () -> {
				WebRequest.execute(url, Method.POST);
			}, 0);
		}
		
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		if(PluginData.mumbleServer != null) {
			Player player = e.getPlayer();
			String url = "servers/shortlink?uuid=" + player.getUniqueId().toString();
			Bukkit.getScheduler().runTaskLaterAsynchronously(MumbleLink.getInstance(), () -> {
				WebRequest.execute(url, Method.POST);
			}, 0);
		}
	}

}
