package com.battlepets.battle;

import java.util.UUID;

import com.battlepets.entities.ActivePetEntity;

public class Request {
	
	private UUID owner;
	private ActivePetEntity pet;
	
	public Request(UUID owner, ActivePetEntity pet) {
		this.owner = owner;
		this.pet = pet;
	}

	public UUID getOwner() {
		return owner;
	}

	public void setOwner(UUID owner) {
		this.owner = owner;
	}

	public ActivePetEntity getPet() {
		return pet;
	}

	public void setPet(ActivePetEntity pet) {
		this.pet = pet;
	}

}
