package com.battlepets.inventory;

import java.util.List;
import java.util.UUID;

import com.battlepets.attributes.GenericPetAttribute;

public class PetInventory {

	private List<GenericPetAttribute> pets;
	private UUID owner;
	
	public PetInventory(List<GenericPetAttribute> pets, UUID owner) {
		this.pets = pets;
		this.owner = owner;
	}

	public List<GenericPetAttribute> getPets() {
		return pets;
	}

	public void setPets(List<GenericPetAttribute> pets) {
		this.pets = pets;
	}

	public UUID getOwner() {
		return owner;
	}

	public void setOwner(UUID owner) {
		this.owner = owner;
	}
	
	public void addPet(GenericPetAttribute attribute) {
		this.pets.add(attribute);
	}
	public void removePet(GenericPetAttribute pet) {
		this.pets.remove(pet);
	}
	
}
