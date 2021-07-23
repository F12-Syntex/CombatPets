package com.battlepets.eggmeta;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import com.battlepets.main.BattlePets;

public class EggStorage {

	public String uuid;
	
	private List<ItemStack> inventory;
	
	public EggStorage(String uuid, List<ItemStack> inventory){
		this.uuid = uuid;
		this.inventory = inventory;		
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public List<ItemStack> getInventory() {
		
		try {
		
			ConfigurationSection key = BattlePets.getInstance().configManager.eggData.getConfiguration().getConfigurationSection(this.uuid + "");
			
			ConfigurationSection inventory = key.getConfigurationSection("Inventory");
			
			List<ItemStack> items = new ArrayList<ItemStack>();
			
			for(String i : inventory.getKeys(false)) {
			
				ItemStack item = inventory.getItemStack(i);
				
				items.add(item);
				
			}
			
			this.inventory = items;
			
			return this.inventory;
			
		}catch (Exception e) {
			return new ArrayList<ItemStack>();
		}
		
	}
	
	public void setInventory(List<ItemStack> inv) {
		
			ConfigurationSection key = BattlePets.getInstance().configManager.eggData.getConfiguration().getConfigurationSection(this.uuid + "");

			List<ItemStack> items = inv;
			
			int count = 0;
			
			for(ItemStack i : items) {
				
				System.out.println(i.getAmount());
				
				key.set("Inventory." +count+"", "asd");
				
				count++;
			}
			
			this.inventory = items;
		
			BattlePets.getInstance().configManager.eggData.update();
			
	}
	
}
