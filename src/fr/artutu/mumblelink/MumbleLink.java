package fr.artutu.mumblelink;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import fr.artutu.mumblelink.commands.CommandWhoIsNotHere;
import fr.artutu.mumblelink.commands.CommandeMe;
import fr.artutu.mumblelink.commands.CommandeMumble;
import fr.artutu.mumblelink.config.MumbleServer;
import fr.artutu.mumblelink.config.PluginConfig;
import fr.artutu.mumblelink.config.PluginData;
import fr.artutu.mumblelink.events.JoinEvent;
import fr.artutu.mumblelink.gui.GUIManager;
import fr.artutu.mumblelink.mumble.BypassList;
import fr.artutu.mumblelink.mumble.MumbleUser;
import fr.artutu.mumblelink.mumble.UserUpdateCheck;
import fr.artutu.mumblelink.permission.DefaultChecker;
import fr.artutu.mumblelink.permission.PermChecker;
import fr.artutu.mumblelink.stateupdater.GetStateFromGameModeRunnable;
import fr.artutu.mumblelink.stateupdater.StateFinder;
import fr.artutu.mumblelink.stateupdater.WerewolfStateFinder;
import fr.artutu.mumblelink.utils.DefaultMuteChecker;
import fr.artutu.mumblelink.utils.GameState;
import fr.artutu.mumblelink.utils.HeadBuilder;
import fr.artutu.mumblelink.utils.InGameChecker;
import fr.artutu.mumblelink.utils.InGameChecker.DefaultInGameChecker;
import fr.artutu.mumblelink.utils.MuteChecker;
import fr.artutu.mumblelink.utils.PseudoGenerator;

public class MumbleLink extends JavaPlugin {

	private static MumbleLink instance;
	public static PermChecker permChecker = new DefaultChecker();
	
	public static GameState gameState = GameState.WAITING;
	public static InGameChecker inGameChecker = new DefaultInGameChecker();
	public static MuteChecker defaultMuteChecker = new DefaultMuteChecker();
	
	public static Map<UUID, String> PSEUDOS = new HashMap<>();
	
	@Override
	public void onEnable() {
		super.onEnable();
		
		instance = this;
	    
	    PluginConfig.initConfig(this);
	    PluginConfig.getConfigData(this);
	    
	    getLogger().info("Configuration chargee avec succes !");
	    
	    new PluginData();
	    new GUIManager();
	    
	    getCommand("mumble").setExecutor(new CommandeMumble());
	    getCommand("whoisnothere").setExecutor(new CommandWhoIsNotHere());
	    getCommand("me").setExecutor(new CommandeMe());
	    
	    Bukkit.getScheduler().runTaskTimerAsynchronously(this, new UserUpdateCheck(), 0, 7L);
	    Bukkit.getScheduler().runTaskTimerAsynchronously(this, new GetStateFromGameModeRunnable(), 0, 5L);
	    
	    Bukkit.getPluginManager().registerEvents(new BypassList(), instance);
	    Bukkit.getPluginManager().registerEvents(new JoinEvent(), instance);
	    
	    getLogger().info("Plugin MumbleLink demarre avec succes !");
	    
	    for(Player player : Bukkit.getOnlinePlayers()) {
	    	HeadBuilder.fetchSkin(player);
	    	if(!MumbleLink.PSEUDOS.containsKey(player.getUniqueId())) {
				String mumblePseudo = PseudoGenerator.generatePseudo(player);
				MumbleLink.PSEUDOS.put(player.getUniqueId(), mumblePseudo);
			}
	    }
	    
	    _addStateFinder_(new WerewolfStateFinder());
	    
	}
	
	@Override
	public void onDisable() {
		MumbleServer.stopServer();
	}
	
	public void _addStateFinder_(StateFinder stateFinder) {
		GetStateFromGameModeRunnable.stateFinders.add(stateFinder);
	}
	
	public static void addStateFinder(StateFinder stateFinder) {
		MumbleLink api = getAPI();
		if(api != null) {
			api._addStateFinder_(stateFinder);
		}
	}
	
	private static MumbleLink getAPI() {
		Plugin pl = Bukkit.getPluginManager().getPlugin("MumbleLink");
		MumbleLink instance = null;
		if(pl != null && (pl instanceof MumbleLink)) {
			instance = (MumbleLink) pl;
		}
		return instance;
	}
	
	public static MumbleLink getInstance() {
		return instance;
	}
	
	public void _setPermChecker_(PermChecker permCheck) {
		MumbleLink.permChecker = permCheck;
	}
	
	public static void setPermChecker(PermChecker permCheck) {
		MumbleLink api = getAPI();
		if(api != null) {
			api._setPermChecker_(permCheck);
		}
	}
	
	public void _setGameState_(GameState gameState) {
		MumbleLink.gameState = gameState;
	}
	
	public static void setGameState(GameState gameState) {
		MumbleLink api = getAPI();
		if(api != null) {
			api._setGameState_(gameState);
		}
	}
	
	public void _setInGameChecker_(InGameChecker inGameChecker) {
		MumbleLink.inGameChecker = inGameChecker;
	}
	
	public static void setInGameChecker(InGameChecker inGameChecker) {
		MumbleLink api = getAPI();
		if(api != null) {
			api._setInGameChecker_(inGameChecker);
		}
	}
	
	public boolean _mute_(Player player) {
		MumbleUser user = MumbleUser.getUser(player);
		if(user != null) {
			user.mute();
			return true;
		}
		return false;
	}
	
	public static boolean mute(Player player) {
		MumbleLink api = getAPI();
		if(api != null) {
			return api._mute_(player);
		}
		return false;
	}
	
	public boolean _unmute_(Player player) {
		MumbleUser user = MumbleUser.getUser(player);
		if(user != null) {
			user.unmute();
			return true;
		}
		return false;
	}
	
	public static boolean unmute(Player player) {
		MumbleLink api = getAPI();
		if(api != null) {
			return api._unmute_(player);
		}
		return false;
	}
	
	public void _setMuteChecker_(MuteChecker checker) {
		MumbleLink.defaultMuteChecker = checker;
	}
	
	public static void setMuteChecker(MuteChecker checker) {
		MumbleLink api = getAPI();
		if(api != null) {
			api._setMuteChecker_(checker);
		}
	}
	
	
}
