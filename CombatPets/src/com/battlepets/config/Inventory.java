package com.battlepets.config;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import com.battlepets.attributes.GenericPetAttribute;
import com.battlepets.eggmeta.EggStorage;
import com.battlepets.inventory.PetInventory;
import com.battlepets.main.BattlePets;
import com.battlepets.petsdata.PointsAllocation;

public class Inventory extends Config{

	private final String babyPrefix = "BABY-";
	
	public Inventory(String name, double version) {
		super(name, version);
	}
	@Override
	public Configuration configuration() {
		// TODO Auto-generated method stub
		return Configuration.INVENTORIES;
	}
	
	@Override
	public void initialize() {
		
		if(!this.getConfiguration().isConfigurationSection("Inventories")) {
			return;
		}
		
		BattlePets.getInstance().petInventoryHandler.inventories.clear();
	
		ConfigurationSection players = this.getConfiguration().getConfigurationSection("Inventories");
		
		for(String uuid : players.getKeys(false)) {
		
			ConfigurationSection inventory = players.getConfigurationSection(uuid);
			
			List<GenericPetAttribute> attributes = new ArrayList<GenericPetAttribute>();
			
			for(String index : inventory.getKeys(false)) {
				
				ConfigurationSection egg = inventory.getConfigurationSection(index);
				
				String displayName = egg.getString("displayName");
				String entity = egg.getString("entity");
				boolean baby = egg.getBoolean("baby");
				//int level = egg.getInt("level");
				int xp = egg.getInt("XP");
				double health = egg.getDouble("HP");
				
				int skillpoints = egg.getInt("Skillpoints.points");
				int vitality = egg.getInt("Skillpoints.allocation.vitality");
				int damage = egg.getInt("Skillpoints.allocation.damage");
				int defense = egg.getInt("Skillpoints.allocation.defense");
				int speed = egg.getInt("Skillpoints.allocation.speed");
				
				String id = egg.getString("UUID");
				
				PointsAllocation allocation = new PointsAllocation(vitality, damage, defense, speed);
				
				GenericPetAttribute attribute = new GenericPetAttribute(EntityType.valueOf(entity), baby, skillpoints, xp, allocation, displayName, health);
				
				EggStorage storage = new EggStorage(id, new ArrayList<ItemStack>());
				
				attribute.setStorage(storage);
				
				attributes.add(attribute);
				
			}
			
			PetInventory petInventory = new PetInventory(attributes, UUID.fromString(uuid));
			
			BattlePets.getInstance().petInventoryHandler.registerInventory(petInventory);
			
		}
		
		
	}

	public String getBabyPrefix() {
		return babyPrefix;
	}


	
}
