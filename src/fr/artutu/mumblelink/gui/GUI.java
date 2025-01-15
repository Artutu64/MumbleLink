package fr.artutu.mumblelink.gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class GUI {
	
	private String nom;
	
	public GUI(String nom) {
		this.nom = nom;
	}
	
	public abstract void open(Player player);
	
	public abstract void handleClick(Player player, GUIClick click, ItemStack item, int rawSlot);

	public String getNom() {
		return nom;
	}
	
	@Override
	public String toString() {
		return "{MumbleLink_GUI#" + this.nom + "}";
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null) return false;
		if(o instanceof GUI) {
			GUI gui = (GUI) o;
			return gui.toString().equals(this.toString());
		}
		return false;
	}

}
