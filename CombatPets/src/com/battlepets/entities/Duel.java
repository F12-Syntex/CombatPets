package com.battlepets.entities;

public class Duel {

	public boolean inDuel;
	public ActivePetEntity target;
	
	public Duel(boolean inDuel, ActivePetEntity target) {
		this.inDuel = inDuel;
		this.target = target;
	}

	public boolean isInDuel() {
		return inDuel;
	}

	public void setInDuel(boolean inDuel) {
		this.inDuel = inDuel;
	}

	public ActivePetEntity getTarget() {
		return target;
	}

	public void setTarget(ActivePetEntity target) {
		this.target = target;
	}
}
