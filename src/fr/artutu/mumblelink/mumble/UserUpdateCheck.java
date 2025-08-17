package fr.artutu.mumblelink.mumble;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import fr.artutu.mumblelink.config.PluginData;
import fr.artutu.mumblelink.requests.TCPRequest;
import fr.artutu.mumblelink.requests.TCPRequest.Method;
import fr.artutu.mumblelink.requests.TCPRequest.TCPResult;

public class UserUpdateCheck extends BukkitRunnable {
	
	public static HashMap<UUID, String> lastStates = new HashMap<>();

	@Override
	public void run() {
		if(PluginData.backendOnline && PluginData.mumbleServer != null) {
			TCPResult response = TCPRequest.execute("servers/" + PluginData.mumbleServer.getId() + "/user", Method.GET);
			
			MumbleUser.USERLIST.clear();
			
			if(response.ok) {
				JsonObject jsonObject = (new JsonParser()).parse(response.getBody()).getAsJsonObject();

		        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
		            JsonElement value = entry.getValue();
		            if (value.isJsonObject()) {
		                JsonObject object = value.getAsJsonObject();
		                if(object != null) {
							MumbleUser user = new MumbleUser(object);
							if(user != null) {
								MumbleUser.USERLIST.add(user);
								if(user.isMute() && StateHandler.needToUnmute(user)) {
									user.unmute();
								} else if (!user.isMute() && StateHandler.needToMute(user)){
									user.mute();
								}
							}
						}
		            }
		        }
				
			}
			
			for(Player player : Bukkit.getOnlinePlayers()) {
				String userState = "§cDéconnecté";
				MumbleUser user = MumbleUser.getUser(player);
				if(user != null) {
					if(user.isLinked()) {
						userState = "§aLink";
					} else {
						userState = "§6Délink";
					}
				}
				boolean stateChanged = !lastStates.containsKey(player.getUniqueId());
				if(!stateChanged) {
					if(!lastStates.get(player.getUniqueId()).equals(userState)) {
						stateChanged = true;
					}
				} else {
					lastStates.put(player.getUniqueId(), "§cDéconnecté");
				}
				
				if(stateChanged) {
					player.sendMessage(" §b§lMUMBLE §7▪ §7Vous êtes maintenant " + userState);
					lastStates.replace(player.getUniqueId(), userState);
				}
				
			}
			
		}
	}

}
