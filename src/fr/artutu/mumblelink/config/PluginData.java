package fr.artutu.mumblelink.config;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import fr.artutu.mumblelink.MumbleLink;
import fr.artutu.mumblelink.requests.CheckBackend;

public class PluginData extends BukkitRunnable {

	public static boolean backendOnline = false;
	public static MumbleServer mumbleServer = null;
	
	public PluginData() {
		CheckBackend.execute();
		MumbleLink.getInstance().getLogger().info("Statut du backend: " + ((backendOnline) ? "Online" : "Offline"));
		Bukkit.getScheduler().runTaskTimerAsynchronously(MumbleLink.getInstance(), this, 0, 60L);
	}
	
	@Override
	public void run() {
		CheckBackend.execute();
	}
	

}
