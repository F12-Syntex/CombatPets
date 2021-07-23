package com.battlepets.petsdata;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.EntityType;

public class PetsData {
	
	private List<Pet> petData;
	
	public PetsData(List<Pet> petData) {
		this.petData = petData;
	}
	public PetsData() {
		this.petData = new ArrayList<Pet>();
	}

	public void registerPet(Pet pet) {
		this.petData.add(pet);
	}
	
	public List<Pet> getAllPets(){
		return this.petData;
	}
	
	public Pet getPet(EntityType entityType, boolean baby) {
		for(Pet i : this.petData) {
			if(i.getEntityType().compareTo(entityType) == 0) {
				if(i.isBaby() == baby) {
					return i;
				}
			}
		}
		return null;
	}
	
}
