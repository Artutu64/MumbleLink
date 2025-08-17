package fr.artutu.mumblelink.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import fr.artutu.mumblelink.MumbleLink;
import fr.artutu.mumblelink.stateupdater.WerewolfStateFinder;
import fr.artutu.mumblelink.utils.AESUtils;

public class PluginConfig {
	
	public static String BACKEND_IP = "localhost";
	
	public static String PROTOCOL;
	public static String MURMUR_HOST;
	
	public static boolean NAME_VISIBLES;
	
	public static AESUtils AES_UTILS = null;
	
	public static void initConfig(JavaPlugin instance) {
	        FileConfiguration config = instance.getConfig();
	        
	        if (!config.contains("backend")) {
	            config.set("backend", "localhost");
	        }
	        
	        if (!config.contains("protocol")) {
	            config.set("protocol", "http");
	        }
	        
	        if (!config.contains("random-names")) {
	            config.set("random-names", false);
	        }
	        
	        if (!config.contains("iv")) {
	            config.set("iv", "abcdef9876543210");
	        }
	        
	        if (!config.contains("key")) {
	            config.set("key", "0123456789abcdef0123456789abcdef");
	        }
	        
	        if (!config.contains("state-finder")) {
	            config.set("state-finder", "none");
	        }

	        instance.saveConfig();
	}
	
	public static void getConfigData(JavaPlugin instance) {
		FileConfiguration config = instance.getConfig();
		BACKEND_IP = config.getString("backend");
		
		System.out.println(BACKEND_IP);
		
		PROTOCOL = config.getString("protocol");
		NAME_VISIBLES = !config.getBoolean("random-names");
		MURMUR_HOST = BACKEND_IP;
		
		try {
			String iv = config.getString("iv");
			String key = config.getString("key");
			byte[] ivb = iv.getBytes();
			byte[] keyb = key.getBytes();
			AES_UTILS = new AESUtils(keyb, ivb);
		} catch(Exception e) {
			e.printStackTrace();
			AES_UTILS = null;
		}
		
		/*
		 * Ajouter ici les futurs State finders !
		 */
		
		String stateFinder = config.getString("state-finder");
		if(stateFinder.equalsIgnoreCase("LG_Ph1Lou")){
			MumbleLink.getInstance()._addStateFinder_(new WerewolfStateFinder());
		}
		
		
	}
	
	public static String getAdresse() {
		String proto = "http";
		if(PROTOCOL.equals("http") || PROTOCOL.equals("https")) {
			proto = PROTOCOL;
		}
		return proto + "://" + BACKEND_IP + "/";
	}

}
