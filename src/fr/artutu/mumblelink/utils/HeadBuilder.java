package fr.artutu.mumblelink.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;

import fr.artutu.mumblelink.MumbleLink;

public class HeadBuilder {
	
	public static Map<UUID, String> headTextures = new HashMap<>();
	
	public static String defaultTextureStatic = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWZhOTc4NmE5YjE4ZjVhZWYwNmQ3ZGZlM2M1N2NmY2FmZTc5ZjVmMGRlMGFkZTExZGNkNDExZThkYjhhYjlmYyJ9fX0=";
	
	public static String getTexture(Player player) {
		return headTextures.getOrDefault(player.getUniqueId(), defaultTextureStatic);
	}
	
	public static String getFromName(Player player) {
		String name = player.getName();
        try {
            URL url_0 = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
            InputStreamReader reader_0 = new InputStreamReader(url_0.openStream());
            String uuid = new JsonParser().parse(reader_0).getAsJsonObject().get("id").getAsString();
     
            URL url_1 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
            InputStreamReader reader_1 = new InputStreamReader(url_1.openStream());
            JsonObject textureProperty = new JsonParser().parse(reader_1).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
            String texture = textureProperty.get("value").getAsString();
     
            return texture;
        } catch (IOException e) {
            e.printStackTrace();
            return defaultTextureStatic;
        }
    }
	
	public static void fetchSkin(Player player) {
		Bukkit.getScheduler().runTaskLaterAsynchronously(MumbleLink.getInstance(), () -> {
			String texture = getFromName(player);
			headTextures.put(player.getUniqueId(), texture);
			headTextures.replace(player.getUniqueId(), texture);
		}, 0);
	}
	
  private final ItemStack is;
  
  public HeadBuilder(Material m) {
    this(m, 1);
  }
  
  public HeadBuilder(ItemStack is) {
    this.is = is;
  }
  
  public HeadBuilder() {
	  this(Material.SKULL_ITEM, 1, (byte)SkullType.PLAYER.ordinal());
  }
  
  public HeadBuilder(Material m, int amount) {
    this.is = new ItemStack(m, amount);
  }
  
  public HeadBuilder(Material m, int amount, byte durability) {
    this.is = new ItemStack(m, amount, durability);
  }
  
  public static ItemStack fromJson(String json) {
    return (ItemStack)(new Gson()).fromJson(json, ItemStack.class);
  }
  
  @Override
  public HeadBuilder clone() {
    return new HeadBuilder(this.is);
  }
  
  public HeadBuilder setDurability(short dur) {
    this.is.setDurability(dur);
    return this;
  }
  
  public HeadBuilder setDurability(int dur) {
    setDurability((short)dur);
    return this;
  }
  
  public HeadBuilder setTexture(String hash) {
    GameProfile profile = new GameProfile(UUID.randomUUID(), null);
    PropertyMap propertyMap = profile.getProperties();
    propertyMap.put("textures", new Property("textures", hash));
    SkullMeta skullMeta = (SkullMeta)this.is.getItemMeta();
    Class<?> c_skullMeta = skullMeta.getClass();
    try {
      Field f_profile = c_skullMeta.getDeclaredField("profile");
      f_profile.setAccessible(true);
      f_profile.set(skullMeta, profile);
      f_profile.setAccessible(false);
      this.is.setItemMeta((ItemMeta)skullMeta);
      return this;
    } catch (IllegalAccessException|NoSuchFieldException e) {
      e.printStackTrace();
      return this;
    } 
  }
  
  public HeadBuilder setName(String name) {
    ItemMeta im = this.is.getItemMeta();
    im.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
    this.is.setItemMeta(im);
    return this;
  }
  
  public HeadBuilder hideItemFlags() {
    ItemMeta im = this.is.getItemMeta();
    byte b;
    int i;
    ItemFlag[] arrayOfItemFlag;
    for (i = (arrayOfItemFlag = ItemFlag.values()).length, b = 0; b < i; ) {
      ItemFlag value = arrayOfItemFlag[b];
      im.addItemFlags(new ItemFlag[] { value });
      b++;
    } 
    this.is.setItemMeta(im);
    return this;
  }
  
  public HeadBuilder setAmount(int amount) {
    this.is.setAmount(amount);
    return this;
  }
  
  public HeadBuilder addUnsafeEnchantment(Enchantment ench, int level) {
    this.is.addUnsafeEnchantment(ench, level);
    return this;
  }
  
  public HeadBuilder removeEnchantment(Enchantment ench) {
    this.is.removeEnchantment(ench);
    return this;
  }
  
  public HeadBuilder setSkullOwner(String owner) {
    try {
      SkullMeta im = (SkullMeta)this.is.getItemMeta();
      im.setOwner(owner);
      this.is.setItemMeta((ItemMeta)im);
    } catch (ClassCastException classCastException) {}
    return this;
  }
  
  public HeadBuilder addEnchant(Enchantment ench, int level) {
    ItemMeta im = this.is.getItemMeta();
    im.addEnchant(ench, level, true);
    this.is.setItemMeta(im);
    return this;
  }
  
  public HeadBuilder addEnchantments(Map<Enchantment, Integer> enchantments) {
    this.is.addEnchantments(enchantments);
    return this;
  }
  
  public HeadBuilder setInfinityDurability() {
    ItemMeta meta = this.is.getItemMeta();
    meta.spigot().setUnbreakable(true);
    this.is.setItemMeta(meta);
    return this;
  }
  
  public HeadBuilder setLore(String... lore) {
    ItemMeta im = this.is.getItemMeta();
    List<String> list = new ArrayList<>();
    byte b;
    int i;
    String[] arrayOfString;
    for (i = (arrayOfString = lore).length, b = 0; b < i; ) {
      String s = arrayOfString[b];
      list.add(ChatColor.translateAlternateColorCodes('&', s));
      b++;
    } 
    im.setLore(list);
    this.is.setItemMeta(im);
    return this;
  }
  
  public HeadBuilder setLore(List<String> lore) {
    ItemMeta im = this.is.getItemMeta();
    List<String> list = new ArrayList<>();
    for (String s : lore)
      list.add(ChatColor.translateAlternateColorCodes('&', s)); 
    im.setLore(list);
    this.is.setItemMeta(im);
    return this;
  }
  
  public HeadBuilder removeLoreLine(String line) {
    ItemMeta im = this.is.getItemMeta();
    List<String> lore = new ArrayList<>(im.getLore());
    if (!lore.contains(line))
      return this; 
    lore.remove(line);
    im.setLore(lore);
    this.is.setItemMeta(im);
    return this;
  }
  
  public HeadBuilder removeLoreLine(int index) {
    ItemMeta im = this.is.getItemMeta();
    List<String> lore = new ArrayList<>(im.getLore());
    if (index < 0 || index > lore.size())
      return this; 
    lore.remove(index);
    im.setLore(lore);
    this.is.setItemMeta(im);
    return this;
  }
  
  public HeadBuilder addLoreLine(String line) {
    ItemMeta im = this.is.getItemMeta();
    List<String> lore = new ArrayList<>();
    if (im.hasLore())
      lore = new ArrayList<>(im.getLore()); 
    lore.add(line);
    im.setLore(lore);
    this.is.setItemMeta(im);
    return this;
  }
  
  public HeadBuilder addLoreLine(String line, int pos) {
    ItemMeta im = this.is.getItemMeta();
    List<String> lore = new ArrayList<>(im.getLore());
    lore.set(pos, line);
    im.setLore(lore);
    this.is.setItemMeta(im);
    return this;
  }
  
  public HeadBuilder setDyeColor(DyeColor color) {
    this.is.setDurability(color.getData());
    return this;
  }
  
  @Deprecated
  public HeadBuilder setWoolColor(DyeColor color) {
    if (!this.is.getType().equals(Material.WOOL))
      return this; 
    this.is.setDurability(color.getData());
    return this;
  }
  
  public HeadBuilder setLeatherArmorColor(Color color) {
    try {
      LeatherArmorMeta im = (LeatherArmorMeta)this.is.getItemMeta();
      im.setColor(color);
      this.is.setItemMeta((ItemMeta)im);
    } catch (ClassCastException classCastException) {}
    return this;
  }
  
  public ItemStack toItemStack() {
    return this.is;
  }
  
  public String toJson() {
    return (new Gson()).toJson(this.is);
  }
}
