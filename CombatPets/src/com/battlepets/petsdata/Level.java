package com.battlepets.petsdata;

public class Level {

	private int maxLevel;
	private double starting;
	private double multiplier;
	private int startingLevel;
	
	public Level(int max, double starting, double multiplier, int startingLevel) {
		this.maxLevel = max;
		this.starting = starting;
		this.multiplier = multiplier;
		this.startingLevel = startingLevel;
	}

	public double getRequiredXpForLevel(double level) {
		
		if(level <= 1) return starting;
		
		double xp = getRequiredXpForLevel((level - 1)) + (getRequiredXpForLevel((level - 1)) * multiplier);

		return xp;
		
		//return starting * (multiplier * ((level - 1)) + this.startingLevel);
	}
	
		public int getLevelByXp(double xp) {
			
		for(int i = 1; i < this.maxLevel; i++) {
			if(this.getRequiredXpForLevel(i) > xp) {
				return i;
			}
		}
		return this.startingLevel;
	}

	public int getMax() {
		return maxLevel;
	}

	public void setMax(int max) {
		this.maxLevel = max;
	}

	public double getStarting() {
		return starting;
	}

	public void setStarting(double starting) {
		this.starting = starting;
	}

	public double getMultiplier() {
		return multiplier;
	}

	public void setMultiplier(double multiplier) {
		this.multiplier = multiplier;
	}

	public int getStartingLevel() {
		return startingLevel;
	}

	public void setStartingLevel(int startingLevel) {
		this.startingLevel = startingLevel;
	}
	
	
}
