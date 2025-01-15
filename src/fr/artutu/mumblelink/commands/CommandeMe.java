package fr.artutu.mumblelink.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.artutu.mumblelink.mumble.MumbleUser;

public class CommandeMe implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		
		if(!(sender instanceof Player)) {
			return false;
		}
		
		Player player = (Player) sender;
		MumbleUser user = MumbleUser.getUser(player);
		
		String[] lore = new String[] {"", " §7▪ §cNon connecté", ""};
		if(user != null) {
			lore = new String[] {
					"",
					" §7▪ Muet client: " + getValue(user.isSelfMute()), 
					" §7▪ Sourd client: " + getValue(user.isSelfDeaf()),
					"",
					" §7▪ §7MumbleLink: §6" + (user.isLinked() ? "§alink" : "§eDélink"),
					" §7▪ §7Version: §e" + user.getVersion(),
					"",
					" §7▪ Muet serveur: " + getValue(user.isMute()), 
					" §7▪ Sourd serveur: " + getValue(user.isDeaf()),
					""
				};
		}
		
		player.sendMessage("§8§m-----------------------");
		player.sendMessage("");
		player.sendMessage("§r           §b§lMumbleLink");
		for(String line : lore) player.sendMessage(line);
		player.sendMessage("§8§m-----------------------");
		
		return true;
	}
	
	public String getValue(boolean bool) {
		return bool ? "§a§l✓" : "§c§l✘";
	}

}
