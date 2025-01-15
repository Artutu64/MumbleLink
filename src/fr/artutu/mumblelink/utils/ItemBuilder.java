package fr.artutu.mumblelink.utils;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemBuilder {
	
	ItemStack item;
	
	private ItemBuilder(Material mat) {
		this(mat, 1);
	}
	
	private ItemBuilder(Material mat, int count) {
		item = new ItemStack(mat, count);
	}
	
	public static ItemBuilder createSkull(String nameOfPlayer) {
		ItemBuilder it = new ItemBuilder(Material.SKULL_ITEM, 1);
		it.item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        
        SkullMeta playerheadmeta = (SkullMeta) it.item.getItemMeta();
        playerheadmeta.setOwner(nameOfPlayer);
        playerheadmeta.setDisplayName("§7" + nameOfPlayer);
        it.item.setItemMeta(playerheadmeta);
        return it;
	}
	
	public static ItemBuilder createSkull(String nameOfPlayer, int qte) {
		ItemBuilder it = new ItemBuilder(Material.SKULL_ITEM, qte);
		it.item = new ItemStack(Material.SKULL_ITEM, qte, (short) 3);
        
        SkullMeta playerheadmeta = (SkullMeta) it.item.getItemMeta();
        playerheadmeta.setOwner(nameOfPlayer);
        playerheadmeta.setDisplayName("§7" + nameOfPlayer);
        it.item.setItemMeta(playerheadmeta);
        return it;
	}
	
	public static ItemBuilder create(Material mat, int count) {
		return new ItemBuilder(mat, count);
	}
	
	public static ItemBuilder create(Material mat) {
		return new ItemBuilder(mat);
	}
	
	public ItemBuilder addEnchantment(Enchantment enchant, int level) {
		item.addEnchantment(enchant, level);
		return this;
	}
	
	public ItemBuilder setUnbreakable() {
		ItemMeta meta = item.getItemMeta();
		if(meta != null) {
			meta.spigot().setUnbreakable(true);
		}
		item.setItemMeta(meta);
		return this;
	}
	
	public ItemBuilder setName(String name) {
		ItemMeta meta = item.getItemMeta();
		if(meta != null) {
			meta.setDisplayName(name);
		}
		item.setItemMeta(meta);
		return this;
	}
	
	public ItemBuilder setLore(String[] lorem) {
		ItemMeta meta = item.getItemMeta();
		if(meta != null) {
			ArrayList<String> lore = new ArrayList<>();
			for(String line : lorem) {
				lore.add(line);
			}
			meta.setLore(lore);
		}
		item.setItemMeta(meta);
		return this;
	}
	
	public ItemStack toItem() {
		return item;
	}
	
	

}
