package com.battlepets.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.entity.Entity;

import com.battlepets.main.BattlePets;

public class ActiveEntityTracker {
	
	private List<ActivePetEntity> entities;
	
	public ActiveEntityTracker() {
		this.setEntities(new ArrayList<ActivePetEntity>());
	}
	
	public void putAllPetsBackIntoUsersInventories() {
		if(!this.entities.isEmpty()) {
			this.getEntities().forEach(i -> {
				BattlePets.getInstance().petInventoryHandler.getInventory(i.getOwner()).addPet(i.getAttribute());
				i.entity.remove();
			});
		}
	}
	
	public void validate() {
		final List<ActivePetEntity> clone = entities.stream().collect(Collectors.toList());
		for(ActivePetEntity i : clone) {
			if(!i.getEntity().isValid()) {
				this.entities.remove(i);
			}
		}
	}
	
	public void addEntity(ActivePetEntity pet) {
		this.entities.add(pet);
	}

	public List<ActivePetEntity> getEntities() {
		return entities;
	}

	public void setEntities(List<ActivePetEntity> entities) {
		this.entities = entities;
	}
	
	public List<ActivePetEntity> getActivePets(UUID uuid) {
		List<ActivePetEntity> e = new ArrayList<ActivePetEntity>();
		for(ActivePetEntity i : this.entities) {
			if(i.getOwner().compareTo(uuid) == 0) {
				e.add(i);
			}
		}
		return e;
	}

	public ActivePetEntity getPet(UUID uuid, Entity entity) {
		for(ActivePetEntity i : this.entities) {
			if(i.getOwner().compareTo(uuid) == 0) {
				if(i.getEntity().getUniqueId().compareTo(entity.getUniqueId()) == 0) {
					return i;
				}
			}
		}
		return null;
	}
	
	public ActivePetEntity getPet(Entity entity) {
		for(ActivePetEntity i : this.entities) {
			if(i.getEntity().getUniqueId().compareTo(entity.getUniqueId()) == 0) {
				return i;
			}
		}
		return null;
	}
	
	public boolean isPet(Entity e) {
		
		for(ActivePetEntity i : this.entities) {
			try {
				if(i.getEntity().getUniqueId().compareTo(e.getUniqueId()) == 0) {
					return true;
				}	
			}catch (Exception e2) {
				continue;
			}
		}
		return false;
	}
	
	public void removePet(Entity entity) {
		this.entities.remove(this.getPet(entity));
	}

	
	
}
