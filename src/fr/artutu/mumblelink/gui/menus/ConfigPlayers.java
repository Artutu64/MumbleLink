package fr.artutu.mumblelink.gui.menus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import fr.artutu.mumblelink.mumble.BypassList;
import fr.artutu.mumblelink.mumble.MumbleUser;
import fr.artutu.mumblelink.utils.HeadBuilder;
import fr.artutu.mumblelink.utils.ItemBuilder;

public class ConfigPlayers extends GUI {
	
	private Map<UUID, Integer> pages = new HashMap<>();
	public static ConfigPlayers instance;
	private static boolean isClosing = false;
	
	public static int PLAYER_PAGE_SIZE = 21;

	public ConfigPlayers() {
		super("§6Gestion des joueurs");
		instance = this;
	}

	@Override
	public void open(Player player) {
		pages.putIfAbsent(player.getUniqueId(), 1);
		open(player, 1);
	}
	
	public void open(Player player, int page) {
		if(page <= 0) {
			open(player, 1);
			return ;
		}
		pages.replace(player.getUniqueId(), page);
		List<List<Player>> playersPage = new ArrayList<>();
		List<Player> players = new ArrayList<>();
		List<Player> sortedPlayers = new ArrayList<>();
		for(Player p : Bukkit.getOnlinePlayers()) sortedPlayers.add(p);
		sortedPlayers.sort((p1, p2) -> { return p1.getName().compareTo(p2.getName()); });
		for(Player p : sortedPlayers) {
			players.add(p);
			if(players.size() >= PLAYER_PAGE_SIZE) {
				playersPage.add(players);
				players = new ArrayList<>();
			}
		}
		if(players.size() > 0) {
			playersPage.add(players);
		}
		if(page > playersPage.size()) {
			open(player, playersPage.size(), playersPage.get(playersPage.size()-1));
			return ;
		}
		open(player, page, playersPage.get(page-1));
	}
	
	public void open(Player player, int page, List<Player> players) {
		Inventory inv = Bukkit.createInventory(null, 45, getNom());	
		
		ItemStack vitre = ItemBuilder.create(Material.STAINED_GLASS_PANE).setName("§f").toItem();
		
		inv.setItem(0, vitre);
		inv.setItem(1, vitre);
		inv.setItem(7, vitre);
		inv.setItem(8, ItemBuilder.create(Material.WATCH).setName("§6§lChanger le gamestate").toItem());
		inv.setItem(9, vitre);
		inv.setItem(17, vitre);
		
		inv.setItem(27, vitre);
		inv.setItem(35, vitre);
		inv.setItem(36, vitre);
		inv.setItem(37, vitre);
		inv.setItem(43, vitre);
		inv.setItem(44, vitre);
		
		inv.setItem(4, ItemBuilder.create(Material.PAPER, page).setName("§fPage: §e" + page).setLore(new String[] {"", "§8Cliquez pour raffraichir", ""}).toItem());
		
		HeadBuilder previous = new HeadBuilder();
		previous.setTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjdhYWNhZDE5M2UyMjI2OTcxZWQ5NTMwMmRiYTQzMzQzOGJlNDY0NGZiYWI1ZWJmODE4MDU0MDYxNjY3ZmJlMiJ9fX0=");
		previous.setName("§bPage précédente");
		
		HeadBuilder next = new HeadBuilder();
		next.setTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDM0ZWYwNjM4NTM3MjIyYjIwZjQ4MDY5NGRhZGMwZjg1ZmJlMDc1OWQ1ODFhYTdmY2RmMmU0MzEzOTM3NzE1OCJ9fX0=");
		next.setName("§bPage suivante");
		
		inv.setItem(18, previous.toItemStack());
		inv.setItem(26, next.toItemStack());
		
		HeadBuilder dev = new HeadBuilder();
		dev.setTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWNiYTcyNzdmYzg5NWJmM2I2NzM2OTQxNTk4NjRiODMzNTFhNGQxNDcxN2U0NzZlYmRhMWMzYmYzOGZjZjM3In19fQ==");
		dev.setName("§bMumbleLink v" + MumbleLink.getInstance().getDescription().getVersion());
		dev.addLoreLine(" §7▪ Author: §eArtutu");
		dev.addLoreLine(" §7▪ Crédits: §ealfg");
		
		inv.setItem(40, dev.toItemStack());
		
		HeadBuilder muteAll = new HeadBuilder();
		muteAll.setTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjhkNDA5MzUyNzk3NzFhZGM2MzkzNmVkOWM4NDYzYWJkZjVjNWJhNzhkMmU4NmNiMWVjMTBiNGQxZDIyNWZiIn19fQ===");
		muteAll.setName("§6Mute-all");
		
		HeadBuilder demuteAll = new HeadBuilder();
		demuteAll.setTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWUzOGZhMzEzMWUyZGEwNGE4ZjhkNTA4NzJhODIzMmQ3ZDlkZWEzNDBkMDZjOTA5N2ZmYTNjYzQ4MjA4ZGYxZCJ9fX0=");
		demuteAll.setName("§6Demute-all");
		
		inv.setItem(39, muteAll.toItemStack());
		
		inv.setItem(41, demuteAll.toItemStack());
		
		inv.setItem(36, ItemBuilder.create(Material.BARRIER).setName("§c§lFERMER").toItem());
		inv.setItem(44, ItemBuilder.create(Material.REDSTONE).setName("§cStopper le serveur mumble").toItem());
		
		int slot = 10;
		for(Player p : players) {
			
			MumbleUser user = MumbleUser.getUser(p);
			
			int qte = 0;
			String[] lore = new String[] {"", " §7▪ §cNon connecté", ""};
			if(user != null) {
				qte = 1;
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
						"",
						" §7▪ Bypass parole: " + getValue(BypassList.canSpeakDueToBypass(p)),
						"",
						"§8Faites clique-gauche pour demute",
						"§8Faites clique-droit pour mute",
						""
					};
			}
			
			HeadBuilder head = new HeadBuilder();
			head.setTexture(HeadBuilder.getTexture(p));
			head.setName("§6" + p.getName());
			head.setAmount(qte);
			for(String line : lore) {
				head.addLoreLine(line);
			}
			
			inv.setItem(slot, head.toItemStack());
			slot++;
			if(slot % 9 == 8) slot++;
			if(slot % 9 == 0) slot++;
		}
		
		player.openInventory(inv);
	}
	
	
	
	@Override
	public void handleClick(Player player, GUIClick click, ItemStack item, int rawSlot) {
		
		if(item.getType() == Material.PAPER) {
			open(player, getPage(player));
			return ;
		}
		
		if(item.getType() == Material.ARROW) {
			ConfigMumble.instance.open(player);
			return ;
		}
		
		if(item.getType() == Material.WATCH) {
			StateGUI.gui.open(player);
			return ;
		}
		
		if(item.getType() == Material.SKULL_ITEM) {
			
			if(item != null && item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null) {
				if(item.getItemMeta().getDisplayName().equals("§bPage précédente")) {
					open(player, getPage(player)-1);
					return ;
				} else if(item.getItemMeta().getDisplayName().equals("§bPage suivante")) {
					open(player, getPage(player)+1);
					return ;
				}
			}
			
			if(item != null && item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null) {
				
				if(item.getItemMeta().getDisplayName().equals("§bMumbleLink v" + MumbleLink.getInstance().getDescription().getVersion())) return ;
				
				if(item.getItemMeta().getDisplayName().equals("§6Demute-all")) {
					MumbleUser.UnmuteAll();
					Bukkit.getScheduler().runTaskLater(MumbleLink.getInstance(), () -> {
						open(player, getPage(player));
					}, 7L);
					return ;
				}
				
				if(item.getItemMeta().getDisplayName().equals("§6Mute-all")) {
					MumbleUser.MuteAll();
					Bukkit.getScheduler().runTaskLater(MumbleLink.getInstance(), () -> {
						open(player, getPage(player));
					}, 7L);
					return ;
				}
				
				String name = item.getItemMeta().getDisplayName().replace("§6", "");
				Player target = Bukkit.getPlayer(name);
				if(target != null && target.isOnline() && MumbleUser.getUser(target) != null) {
					
					boolean isBypass = false;
					for(UUID uuid : BypassList.mutedBypassList) {
						if(uuid.equals(target.getUniqueId())) {
							isBypass = true;
						}
					}
					
					if(click == GUIClick.GAUCHE) {
						
						if(isBypass) {
							player.sendMessage(" §b§lMUMBLE §7▪ §cLe joueur a déjà la parole !");
							return ;
						}
						
						BypassList.addMuteException(target);
						player.sendMessage(" §b§lMUMBLE §7▪ Vous venez de rendre la parole à §b" + name + " §7!");
						if(!target.getName().equals(player.getName())) {
							target.sendMessage(" §b§lMUMBLE §7▪ §b" + player.getName() + "§7 vous a rendu la parole !");
						}
						Bukkit.getScheduler().runTaskLater(MumbleLink.getInstance(), () -> {
							open(player, getPage(player));
						}, 7L);
					} else if (click == GUIClick.DROIT) {
						
						if(!isBypass) {
							player.sendMessage(" §b§lMUMBLE §7▪ §cLe joueur est déjà mute !");
							return ;
						}
						
						BypassList.removeMuteException(target);
						player.sendMessage(" §b§lMUMBLE §7▪ Vous venez de supprimer le droit de parole de §b" + name + " §7!");
						if(!target.getName().equals(player.getName())) {
							target.sendMessage(" §b§lMUMBLE §7▪ §b" + player.getName() + "§7 vous a enlevé la parole !");
						}
						Bukkit.getScheduler().runTaskLater(MumbleLink.getInstance(), () -> {
							open(player, getPage(player));
						}, 7L);
					}
				} else {
					player.sendMessage(" §b§lMUMBLE §7▪ §cLe joueur §b" + name + " §cn'est pas connecté sur minecraft ou sur mumble !");
				}
			}
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
		
		if(item.getType() == Material.BARRIER) {
			player.closeInventory();
			return ;
		}
	}
	
	public String getValue(boolean bool) {
		return bool ? "§a§l✓" : "§c§l✘";
	}
	
	public int getPage(Player player) {
		return getPage(player.getUniqueId());
	}
	
	public int getPage(UUID uuid) {
		if(pages.containsKey(uuid)) {
			return pages.get(uuid);
		}
		return 1;
	}

}
