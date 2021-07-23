package com.battlepets.eggmeta;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import com.battlepets.main.BattlePets;

import io.netty.util.internal.ThreadLocalRandom;

public class EggHandler {
	
	public List<EggStorage> eggs;
	
	public EggHandler() {
		this.eggs = new ArrayList<EggStorage>();
	}
	
	public void addEgg(EggStorage egg) {
		this.eggs.add(egg);
	}
	
	public boolean contains(String uuid) {
		for(EggStorage egg : eggs) {
			if(egg.getUuid() == uuid) {
				return true;
			}
		}
		return false;
	}
	
	public EggStorage getEgg(String uuid) {
		
		if(!this.contains(uuid)) {
			EggStorage egg = new EggStorage(uuid, new ArrayList<ItemStack>());
			this.addEgg(egg);
			return egg;
		}
		
		for(EggStorage egg : eggs) {
			if(egg.getUuid() == uuid) {
				return egg;
			}
		}
		return null;
	}
	
	public EggStorage generateEgg() {
		
		String uuid = "Key" + ThreadLocalRandom.current().nextInt();
		
		List<ItemStack> items = new ArrayList<ItemStack>();
		
		EggStorage storage = new EggStorage(uuid, items);
		
		this.addEgg(storage);
	
		BattlePets.getInstance().configManager.eggData.update();
		
		return storage;
	}

	
   
}
