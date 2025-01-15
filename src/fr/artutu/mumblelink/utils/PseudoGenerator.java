package fr.artutu.mumblelink.utils;

import java.util.Random;

import org.bukkit.entity.Player;

import fr.artutu.mumblelink.MumbleLink;
import fr.artutu.mumblelink.config.PluginConfig;

public class PseudoGenerator {
	
	public static String generatePseudo(Player player) {
		
		if(player == null || !player.isOnline()) {
			return "";
		}
		
		if(PluginConfig.NAME_VISIBLES) {
			return player.getName();
		}
		
		if(MumbleLink.PSEUDOS.containsKey(player.getUniqueId())) {
			return MumbleLink.PSEUDOS.get(player.getUniqueId());
		}
		
		return generateRandomString(32);
		
	}
	
	public static String generateRandomString(int length) {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder randomString = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            randomString.append(characters.charAt(index));
        }

        return randomString.toString();
    }

}
