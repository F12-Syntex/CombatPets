package com.battlepets.petsdata;

public class Riding {
	
	public boolean ridable;
	public int requiredLevel;
	
	public Riding(boolean ridable, int required) {
		this.ridable = ridable;
		this.requiredLevel = required;
	}

	public boolean isRidable() {
		return ridable;
	}

	public void setRidable(boolean ridable) {
		this.ridable = ridable;
	}

	public int getRequiredLevel() {
		return requiredLevel;
	}

	public void setRequiredLevel(int requiredLevel) {
		this.requiredLevel = requiredLevel;
	}

}
