package com.battlepets.shopdata;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.EntityType;

public class ShopData {
	
	private List<ShopItem> shopItem;
	
	public ShopData(List<ShopItem> shopItem) {
		this.shopItem = shopItem;
	}
	public ShopData() {
		this.shopItem = new ArrayList<ShopItem>();
	}

	public void registerItem(ShopItem item) {
		this.shopItem.add(item);
	}
	
	public List<ShopItem> getAllItems(){
		return this.shopItem;
	}
	
	public ShopItem getPet(EntityType entityType) {
		for(ShopItem i : this.shopItem) {
			if(i.getEntityType().compareTo(entityType) == 0) {
				return i;
			}
		}
		return null;
	}
	
}
