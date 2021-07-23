package com.battlepets.config;

import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;

import com.battlepets.shopdata.ShopData;
import com.battlepets.shopdata.ShopItem;
import com.battlepets.utils.ComponentBuilder;

public class Shop extends Config{

	public static final String babyPrefix = "BABY-";
	
	public ShopData shopData;
	
	public Shop(String name, double version) {
		super(name, version);
		
		this.items.add(new ConfigItem("Shop.items.COW.displayName", "&6Cow"));
    	this.items.add(new ConfigItem("Shop.items.COW.lore", ComponentBuilder.createLore("&cA cow pet, buy now OWO", "&cCosts &a100$")));
    	this.items.add(new ConfigItem("Shop.items.COW.price", 100));
    	this.items.add(new ConfigItem("Shop.items.COW.permission", "battlepets.shop.cow"));
    	
		this.items.add(new ConfigItem("Shop.items." + babyPrefix + "COW.displayName", "&6Cow &7(&eBaby&7)"));
    	this.items.add(new ConfigItem("Shop.items." + babyPrefix + "COW.lore", ComponentBuilder.createLore("&cA cow pet, buy now OWO", "&cCosts &a100$")));
    	this.items.add(new ConfigItem("Shop.items." + babyPrefix + "COW.price", 100));
    	this.items.add(new ConfigItem("Shop.items." + babyPrefix + "COW.permission", "battlepets.shop.babycow"));
	}

	@Override
	public Configuration configuration() {
		// TODO Auto-generated method stub
		return Configuration.SHOP;
	}
	
	@Override
	public void initialize() {
	
		if(!this.getConfiguration().isConfigurationSection("Shop.items")) {
			return;
		}
		
		this.shopData = new ShopData();
		
		ConfigurationSection shop = this.getConfiguration().getConfigurationSection("Shop.items");
		
		for(String key : this.getConfiguration().getConfigurationSection("Shop.items").getKeys(false)) {
			
			boolean isBaby = key.contains(babyPrefix);
			
			ConfigurationSection item = shop.getConfigurationSection(key);
			
			if(isBaby) {
				key = key.substring(babyPrefix.length());
			}
			
			EntityType entityType = EntityType.valueOf(key);
			
			String displayName = item.getString("displayName");
			List<String> lore = ComponentBuilder.createLore(item.getStringList("lore"));
			double price = item.getDouble("price");
			String permission = item.getString("permission");
			
			ShopItem shopItem = new ShopItem(entityType, displayName, lore, price, permission, isBaby);
			
			this.shopData.registerItem(shopItem);
			
		}
		
	
	}


	
}
