package com.battlepets.inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.configuration.ConfigurationSection;

import com.battlepets.attributes.GenericPetAttribute;
import com.battlepets.main.BattlePets;

public class PetInventoryHandler {

	public List<PetInventory> inventories = new ArrayList<PetInventory>();
	
	public PetInventoryHandler() {
		
	}
	
	public void registerInventory(PetInventory inventory) {
		this.inventories.add(inventory);
	}
	
	public PetInventory getInventory(UUID uuid) {
		for(PetInventory i : this.inventories) {
			if(i.getOwner().compareTo(uuid) == 0) return i;
		}
		
		List<GenericPetAttribute> inv = new ArrayList<GenericPetAttribute>();
		PetInventory inventory = new PetInventory(inv, uuid);
		this.registerInventory(inventory);
		this.saveInventories();
		return inventory;
	}
	
	public void saveInventories() {
		
		if(!BattlePets.getInstance().configManager.inventory.getConfiguration().contains("Inventories")) {
			BattlePets.getInstance().configManager.inventory.getConfiguration().createSection("Inventories");
		}
		
		ConfigurationSection inventory = BattlePets.getInstance().configManager.inventory.getConfiguration().getConfigurationSection("Inventories");
		
		for(PetInventory inv : inventories) {

			List<GenericPetAttribute> attributes = inv.getPets();
			
			String uuid = inv.getOwner().toString();
	
			BattlePets.getInstance().configManager.inventory.getConfiguration().set("Inventories." + uuid , null);
			
			for(int i = 0; i < attributes.size(); i++) {
				
				GenericPetAttribute attribute = attributes.get(i);

				inventory.set(uuid + "." + i + ".displayName", attribute.getName());
				inventory.set(uuid + "." + i + ".entity", attribute.getEntityType().name());
				inventory.set(uuid + "." + i + ".baby", attribute.isBaby());
				inventory.set(uuid + "." + i + ".level", attribute.getLevel());
				inventory.set(uuid + "." + i + ".XP", attribute.getXp());
				inventory.set(uuid + "." + i + ".HP", attribute.getHealth());
				inventory.set(uuid + "." + i + ".Skillpoints.points", attribute.getPoints());
				inventory.set(uuid + "." + i + ".Skillpoints.allocation.vitality", attribute.getPointsAllocation().getHp());
				inventory.set(uuid + "." + i + ".Skillpoints.allocation.damage", attribute.getPointsAllocation().getDamage());
				inventory.set(uuid + "." + i + ".Skillpoints.allocation.defense", attribute.getPointsAllocation().getDefense());
				inventory.set(uuid + "." + i + ".Skillpoints.allocation.speed", attribute.getPointsAllocation().getSpeed());
				inventory.set(uuid + "." + i + ".UUID", attribute.getStorage().getUuid() + "");
				
			}
			
		}
		
		BattlePets.getInstance().configManager.inventory.save();
		
	}
	
}
