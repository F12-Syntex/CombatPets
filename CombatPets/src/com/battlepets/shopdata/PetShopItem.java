package com.battlepets.shopdata;

import java.util.List;

import org.bukkit.Material;

public class PetShopItem {

	private String name;
	private List<String> lore;
	private int cost;
	private Material material;
	private int health;
	
	public PetShopItem(Material material, String name, List<String> lore, int cost, int health) {
		this.name = name;
		this.lore = lore;
		this.cost = cost;
		this.setMaterial(material);
		this.setHealth(health);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getLore() {
		return lore;
	}

	public void setLore(List<String> lore) {
		this.lore = lore;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
}
