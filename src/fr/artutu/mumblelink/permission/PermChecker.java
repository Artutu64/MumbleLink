package fr.artutu.mumblelink.permission;

import org.bukkit.command.CommandSender;

public interface PermChecker {

	public boolean canEditMumble(CommandSender sender);
	
}
