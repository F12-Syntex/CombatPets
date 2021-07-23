package com.battlepets.shopdata;

import java.util.List;

import org.bukkit.entity.EntityType;

public class ShopItem {

	private EntityType entityType;
	private String displayName;
	private List<String> lore;
	private double price;
	private String permission;
	private boolean baby;
	
	public ShopItem(EntityType entityType, String displayName, List<String> lore, double price, String permission, boolean baby) {
		this.entityType = entityType;
		this.displayName = displayName;
		this.lore = lore;
		this.price = price;
		this.permission = permission;
		this.baby = baby;
	}

	public EntityType getEntityType() {
		return entityType;
	}

	public void setEntityType(EntityType entityType) {
		this.entityType = entityType;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public List<String> getLore() {
		return lore;
	}

	public void setLore(List<String> lore) {
		this.lore = lore;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public boolean isBaby() {
		return baby;
	}

	public void setBaby(boolean baby) {
		this.baby = baby;
	}
	
	
	
}
