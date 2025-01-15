package fr.artutu.mumblelink.config;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import fr.artutu.mumblelink.MumbleLink;
import fr.artutu.mumblelink.requests.WebRequest;
import fr.artutu.mumblelink.requests.WebRequest.Method;
import fr.artutu.mumblelink.requests.WebRequest.WebResult;

public class MumbleServer {
	
	private int id;
	private String host;
	private int port;
	
	public MumbleServer(int id, String host, int port) {
		this.id = id;
		this.host = host;
		this.port = port;
	}
	
	public String getHost() {
		return host;
	}
	
	public int getId() {
		return id;
	}
	
	public int getPort() {
		return port;
	}
	
	public static void startServer() {
		if(PluginData.backendOnline && PluginData.mumbleServer == null) {
			WebResult response = WebRequest.execute("servers/", Method.POST);
			if(response.ok) {
				try {
					JsonObject jsonObject = (new JsonParser()).parse(response.getBody()).getAsJsonObject();
					int port = jsonObject.get("port").getAsInt();
					int id = jsonObject.get("id").getAsInt();
					PluginData.mumbleServer = new MumbleServer(id, PluginConfig.MURMUR_HOST, port);
					for(Player player : Bukkit.getOnlinePlayers()) {
						String url = "servers/shortlink?uuid=" + player.getUniqueId().toString() +"&port=" + PluginData.mumbleServer.getPort() + "&host=" + PluginData.mumbleServer.getHost() +"&pseudo=" + MumbleLink.PSEUDOS.get(player.getUniqueId());
						Bukkit.getScheduler().runTaskLaterAsynchronously(MumbleLink.getInstance(), () -> {
							WebRequest.execute(url, Method.POST);
						}, 0);
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static boolean stopServer() {
		if(PluginData.mumbleServer != null) {
			WebRequest.execute("servers/" + PluginData.mumbleServer.getId() + "/", Method.DELETE);
			for(Player player : Bukkit.getOnlinePlayers()) {
				String url = "servers/shortlink?uuid=" + player.getUniqueId().toString();
				Bukkit.getScheduler().runTaskLaterAsynchronously(MumbleLink.getInstance(), () -> {
					WebRequest.execute(url, Method.POST);
				}, 0);
			}
			PluginData.mumbleServer = null;
			return true;
		}
		return false;
	}

}
