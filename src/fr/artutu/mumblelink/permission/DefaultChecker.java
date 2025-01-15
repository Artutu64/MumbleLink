package fr.artutu.mumblelink.permission;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DefaultChecker implements PermChecker {

	@Override
	public boolean canEditMumble(CommandSender sender) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			return player.isOp();
		}
		return true;
	}

}
