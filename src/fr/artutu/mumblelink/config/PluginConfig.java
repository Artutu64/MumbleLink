package fr.artutu.mumblelink.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginConfig {
	
	public static int PORT;
	public static String HOST;
	public static String PROTOCOL;
	public static String MURMUR_HOST;
	public static boolean NAME_VISIBLES;
	
	public static void initConfig(JavaPlugin instance) {
	        FileConfiguration config = instance.getConfig();
	        
	        if (!config.contains("host")) {
	            config.set("host", "localhost");
	        }
	        if (!config.contains("port")) {
	            config.set("port", 8080);
	        }
	        if (!config.contains("protocol")) {
	            config.set("protocol", "http");
	        }
	        if (!config.contains("random-names")) {
	            config.set("random-names", false);
	        }

	        instance.saveConfig();
	}
	
	public static void getConfigData(JavaPlugin instance) {
		FileConfiguration config = instance.getConfig();
		PORT = config.getInt("port");
		HOST = config.getString("host");
		PROTOCOL = config.getString("protocol");
		NAME_VISIBLES = !config.getBoolean("random-names");
		MURMUR_HOST = HOST;
	}
	
	public static String getAdresse() {
		String proto = "http";
		if(PROTOCOL.equals("http") || PROTOCOL.equals("https")) {
			proto = PROTOCOL;
		}
		return proto + "://" + HOST + ":" + PORT + "/";
	}

}
