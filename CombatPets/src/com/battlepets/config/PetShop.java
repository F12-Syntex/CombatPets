package com.battlepets.config;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import com.battlepets.shopdata.PetShopItem;
import com.battlepets.utils.ComponentBuilder;

public class PetShop extends Config{

	public List<PetShopItem> healing;
	
	public PetShop(String name, double version) {
		super(name, version);
		
		for(int i = 1; i < 6; i++) {

			this.items.add(new ConfigItem("Shop.items.Healing.items." + i + ".Material", "APPLE"));
			this.items.add(new ConfigItem("Shop.items.Healing.items." + i + ".Healing", (i*5)));
			this.items.add(new ConfigItem("Shop.items.Healing.items." + i + ".Name", "&c" + (i*5) + " Hp"));
			this.items.add(new ConfigItem("Shop.items.Healing.items." + i + ".Lore", ComponentBuilder.createLore("&cGives your pet " + i*5 + " more HP", "&cCosts &7" + i*50 + "&a$")));
			this.items.add(new ConfigItem("Shop.items.Healing.items." + i + ".Cost", i*50));
		}
		
	}

	@Override
	public Configuration configuration() {
		// TODO Auto-generated method stub
		return Configuration.SHOP;
	}
	
	@Override
	public void initialize() {
	
		if(!this.getConfiguration().isConfigurationSection("Shop.items.Healing.items")) {
			return;
		}
		
		this.healing = new ArrayList<PetShopItem>();
	
		ConfigurationSection section = this.getConfiguration().getConfigurationSection("Shop.items.Healing.items");
		
		for(String i : section.getKeys(false)) {
			ConfigurationSection shopItem = section.getConfigurationSection(i);
			String name = shopItem.getString("Name");
			List<String> lore = shopItem.getStringList("Lore");
			int cost = shopItem.getInt("Cost");
			String material = shopItem.getString("Material");
			int healing = shopItem.getInt("Healing");
			PetShopItem item = new PetShopItem(Material.valueOf(material), name, lore, cost, healing);
			this.healing.add(item);
		}
	
	}


	
}
