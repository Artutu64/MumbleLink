package fr.artutu.mumblelink.mumble;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import fr.artutu.mumblelink.MumbleLink;
import fr.artutu.mumblelink.config.PluginData;
import fr.artutu.mumblelink.requests.WebRequest;
import fr.artutu.mumblelink.requests.WebRequest.Method;

public class MumbleUser {
	
	public static List<MumbleUser> USERLIST = new ArrayList<>();
	
	private String name = "§cUnknown";
	private String version = "§cUnknown";
	private boolean selfDeaf = false;
	private boolean selfMute = false;
	private int channel = -1;
	private boolean mute = false;
	private boolean deaf = false;
	private String context = "";
	private int id = 0;
	
	public MumbleUser(String json) {
		updateFromJson(json);
	}
	
	public MumbleUser(JsonObject jsonObject) {
		updateFromJson(jsonObject);
	}
	
	public void mute() {
		if(PluginData.mumbleServer != null) {
			Bukkit.getScheduler().runTaskLaterAsynchronously(MumbleLink.getInstance(), () -> {
				String url = "servers/" + PluginData.mumbleServer.getId() + "/user/" + this.name + "/mute";
				WebRequest.execute(url, Method.POST);
			}, 0);
		}
	}
	
	public static void UnmuteAll() {
		Bukkit.getOnlinePlayers().forEach((p) -> {
			BypassList.addMuteException(p);
		});
		Bukkit.broadcastMessage(" §b§lMUMBLE §7▪ §aUn opérateur a rendu la parole à tous les utilisateurs.");
	}
	
	public static void MuteAll() {
		Bukkit.getOnlinePlayers().forEach((p) -> {
			BypassList.removeMuteException(p);
		});
		Bukkit.broadcastMessage(" §b§lMUMBLE §7▪ §cUn opérateur suspendu la parole à tous les utilisateurs.");
	}
	
	public int getId() {
		return id;
	}
	
	public void unmute() {
		if(PluginData.mumbleServer != null) {
			Bukkit.getScheduler().runTaskLaterAsynchronously(MumbleLink.getInstance(), () -> {
				String url = "servers/" + PluginData.mumbleServer.getId() + "/user/" + this.name + "/unmute";
				WebRequest.execute(url, Method.POST);
			}, 0);
		}
	}
	
	public void updateFromJson(String json) {
		try {
			JsonObject jsonObject = (new JsonParser()).parse(json).getAsJsonObject();
			updateFromJson(jsonObject);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateFromJson(JsonObject jsonObject) {
		try {
			this.name = jsonObject.get("name").getAsString();
			this.version = jsonObject.get("release").getAsString();
			this.selfDeaf = jsonObject.get("selfDeaf").getAsBoolean();
			this.selfMute = jsonObject.get("selfMute").getAsBoolean();
			this.channel = jsonObject.get("channel").getAsInt();
			this.mute = jsonObject.get("mute").getAsBoolean();
			this.deaf = jsonObject.get("deaf").getAsBoolean();
			this.context = jsonObject.get("context").getAsString();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean isLinked() {
		return this.context.contains("Minecraft") || this.context.equals("TWluZWNyYWZ0AHsiZG9tYWluIjoiQW") || this.context.equals("TWluZWNyYWZ0IE11bWJsZSBMaW5rIE1vZAB");
	}
	
	public String getName() {
		return name;
	}
	
	public int getChannel() {
		return channel;
	}
	
	public String getContext() {
		return context;
	}
	
	public String getVersion() {
		return version;
	}
	
	public boolean isSelfDeaf() {
		return selfDeaf;
	}
	
	public boolean isSelfMute() {
		return selfMute;
	}
	
	public boolean isMute() {
		return mute;
	}
	public boolean isDeaf() {
		return deaf;
	}
	
	public static MumbleUser getUser(Player player) {
		for(MumbleUser user : USERLIST) {
			if(MumbleLink.PSEUDOS.containsKey(player.getUniqueId())) {
				if(user.getName().equalsIgnoreCase(MumbleLink.PSEUDOS.get(player.getUniqueId()))) {
					return user;
				}
			}
		}
		return null;
	}

}
