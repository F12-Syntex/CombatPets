package com.battlepets.config;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.battlepets.eggmeta.EggHandler;
import com.battlepets.eggmeta.EggStorage;
import com.battlepets.utils.ComponentBuilder;

public class EggData extends Config{
	
	public EggHandler handler;
	
	public EggData(String name, double version) {
		super(name, version);

		ItemStack exmaple = new ItemStack(Material.DIAMOND_AXE);
		
		ItemMeta meta = exmaple.getItemMeta();
		
		meta.setDisplayName("example");
		meta.setLore(ComponentBuilder.createLore("test", "test2"));
		
		exmaple.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 2);
		
		exmaple.setItemMeta(meta);
		
		this.items.add(new ConfigItem("Data.Key0.Inventory.0", exmaple.serialize()));

		this.handler = new EggHandler();
		
	}
	
	@Override
	public Configuration configuration() {
		// TODO Auto-generated method stub
		return Configuration.EGGDATA;
	}
	
	@Override
	public void initialize() {
		
		ConfigurationSection data = this.getConfiguration().getConfigurationSection("Data");
		
		for(String key : data.getKeys(false)) {
			
			String uuid = key;

			List<ItemStack> items = new ArrayList<ItemStack>();
			
			if(!data.isConfigurationSection(key + ".Inventory")) {
				
				EggStorage eggStorage = new EggStorage(uuid, items);
				
				this.handler.addEgg(eggStorage);
				
				continue;
			}
			
			
			ConfigurationSection inventory = data.getConfigurationSection(key + ".Inventory");
		
			for(String slot : inventory.getKeys(false)) {
				
				ItemStack item = inventory.getItemStack(slot);
				
				items.add(item);
				
			}
			
			EggStorage eggStorage = new EggStorage(uuid, items);
			
			this.handler.addEgg(eggStorage);
			
		}
		
		
	}

	public void update() {
		
		for(EggStorage storage : this.handler.eggs) {
			
			this.getConfiguration().set("Data." + storage.getUuid() + ".Inventory", new ArrayList<ItemStack>());
			
			ConfigurationSection section = this.getConfiguration().getConfigurationSection("Data.Key" + storage.getUuid() + ".Inventory");
			
			for(int i = 0; i < storage.getInventory().size(); i++) {
				
				ItemStack item = storage.getInventory().get(i);
				
				section.set(i + "", item.serialize());
				
			}
			
		}
		
		this.save();
		
	}

	
}
