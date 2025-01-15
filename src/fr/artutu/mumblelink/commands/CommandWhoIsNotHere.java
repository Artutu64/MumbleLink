package fr.artutu.mumblelink.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import fr.artutu.mumblelink.mumble.UserUpdateCheck;

public class CommandWhoIsNotHere implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
		
		sender.sendMessage("§8§m--+------------------------+--");
		Bukkit.getOnlinePlayers().forEach((player) -> {
			String state = UserUpdateCheck.lastStates.getOrDefault(player.getUniqueId(), "§cDéconnecté");
			if(!state.contains("§aLink")) {
				sender.sendMessage("§r    §8▪ §7" + player.getName() + " §8- §r" + state);
			}
		});
		sender.sendMessage("§8§m--+------------------------+--");
		sender.sendMessage("§8§oSeulement vous pouvez voir ce message.");
		return true;
	}

}
