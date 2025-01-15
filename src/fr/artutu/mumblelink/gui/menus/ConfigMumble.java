package fr.artutu.mumblelink.gui.menus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.artutu.mumblelink.MumbleLink;
import fr.artutu.mumblelink.config.MumbleServer;
import fr.artutu.mumblelink.config.PluginData;
import fr.artutu.mumblelink.gui.GUI;
import fr.artutu.mumblelink.gui.GUIClick;
import fr.artutu.mumblelink.utils.ItemBuilder;

public class ConfigMumble extends GUI {
	
	public static GUI instance;
	private static long lastUse = 0;
	private static boolean isClosing = false;

	public ConfigMumble() {
		super("§aConfiguration de mumble");
		instance = this;
	}

	@Override
	public void open(Player player) {
		
		if(PluginData.mumbleServer != null) {
			ConfigPlayers.instance.open(player);
			return ;
		}
		
		Inventory inv = Bukkit.createInventory(null, 27, getNom());
		
		ItemStack vitre = ItemBuilder.create(Material.STAINED_GLASS_PANE).setName("§f").toItem();
		
		inv.setItem(0, vitre);
		inv.setItem(1, vitre);
		inv.setItem(7, vitre);
		inv.setItem(8, vitre);
		
		inv.setItem(9, vitre);
		inv.setItem(17, vitre);
		
		inv.setItem(18, vitre);
		inv.setItem(19, vitre);
		inv.setItem(25, vitre);
		inv.setItem(26, vitre);
		inv.setItem(13, ItemBuilder.create(Material.SLIME_BALL).setName("§7┃ §aDémarrer le serveur").setLore(new String[] {"", "§7┃ §fOuvre un serveur mumble sur lequel", "§7┃ §fles joueurs peuvent se connecter.", ""}).toItem());
		
		player.openInventory(inv);
	}

	@Override
	public void handleClick(Player player, GUIClick click, ItemStack item, int rawSlot) {
		
		if(PluginData.mumbleServer == null && item.getType() == Material.SLIME_BALL) {
			if(!canStart()) {
				player.sendMessage(" §b§lMUMBLE §7▪  §cMerci d'attendre avant de créer un nouveau serveur ! (" + (10-getDiff()) + "s)");
				return ;
			}
			lastUse = System.currentTimeMillis();
			player.closeInventory();
			player.sendMessage(" §b§lMUMBLE §7▪ Démarrage du serveur mumble...");
			
			Bukkit.getScheduler().runTaskLaterAsynchronously(MumbleLink.getInstance(), () -> {
				MumbleServer.startServer();
				lastUse = 0;
				if(PluginData.mumbleServer == null) {
					player.sendMessage(" §b§lMUMBLE §7▪ §cIl y a eu une erreur lors du démarrage du serveur mumble.");
				} else {
					player.sendMessage(" §b§lMUMBLE §7▪ §aLe serveur mumble vient d'être démarré !");
					ConfigMumble.instance.open(player);
				}
			}, 0);
			
			return ;
		}
		
		if(PluginData.mumbleServer != null) {
			if(item.getType() == Material.REDSTONE) {
				if(isClosing) {
					player.sendMessage(" §b§lMUMBLE §7▪ §cLe serveur est déjà en cours d'arrêt !");
					return ;
				}
				player.sendMessage(" §b§lMUMBLE §7▪ Arrêt du serveur mumble en cours...");
				isClosing = true;
				player.closeInventory();
				Bukkit.getScheduler().runTaskLaterAsynchronously(MumbleLink.getInstance(), () -> {
					boolean result = MumbleServer.stopServer();
					if(result) {
						isClosing = false;
						player.sendMessage(" §b§lMUMBLE §7▪ §aLe serveur vient d'être stoppé !");
					} else {
						player.sendMessage(" §b§lMUMBLE §7▪ §cIl y a eu une erreur lors de l'arrêt du serveur mumble.");
					}
				}, 0);
			}
			
			if(item.getType() == Material.SKULL_ITEM) {
				ConfigPlayers.instance.open(player);
				return ;
			}
		}
	}
	
	
	public static boolean canStart() {
		return getDiff() >= 10;
	}
	
	public static int getDiff() {
		return (int) ((System.currentTimeMillis() - lastUse)/1000L);
	}

}
