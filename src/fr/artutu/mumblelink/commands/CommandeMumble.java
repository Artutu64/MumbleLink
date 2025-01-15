package fr.artutu.mumblelink.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.artutu.mumblelink.MumbleLink;
import fr.artutu.mumblelink.config.PluginData;
import fr.artutu.mumblelink.gui.menus.ConfigMumble;
import fr.artutu.mumblelink.requests.CheckBackend;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class CommandeMumble implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		
		if(args.length > 0 && args[0].equalsIgnoreCase("config") && MumbleLink.permChecker.canEditMumble(sender)) {
			
			if(!PluginData.backendOnline) {
				sender.sendMessage(" §7(§4!§7)§r §cLe backend n'est pas en ligne, §b§lMumble§c ne peut pas être configuré !");
				return true;
			}
			
			if(!(sender instanceof Player)) {
				sender.sendMessage(" §7(§4!§7)§r §cSeuls les joueurs peuvent éxecuter cette commande !");
				return false;
			}
			
			Player player = (Player) sender;
			
			ConfigMumble.instance.open(player);
			
			return true;
		}
		
		if(!(sender instanceof Player)) return false;
		
		Player player = (Player) sender;
		
		if(PluginData.mumbleServer != null) {
			
			player.sendMessage("");
			player.sendMessage("§r              §f▪§r §a§lMUMBLELINK§r §f▪");
			sender.sendMessage("");
			TextComponent msg = new TextComponent(" §8» §7Cliquez-ici pour vous connecter §8«");
			msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§8» §aCliquez pour ouvrir mumble").create()));
			msg.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, CheckBackend.SHORTLINK + "?uuid=" + player.getUniqueId().toString()));
			player.spigot().sendMessage(msg);

			player.sendMessage(" ");
			player.sendMessage("§8§oPour plus d'informations sur votre §b§oMumblelink§8§o, faites §d§o/status§8§o.");

			player.sendMessage(" ");
			return true;
		}
		
		player.sendMessage(" §b§lMUMBLE §7▪ §b§lMumble §cn'est pas actif pour le moment !");
		
		return true;
	}

}
