package com.battlepets.petsdata;

public class MaxAllocation {

	private double hp;
	private double damage;
	private double defense;
	private double speed;	

	public MaxAllocation(double hp, double damage, double defense, double speed) {
		this.hp = hp;
		this.damage = damage;
		this.defense = defense;
		this.speed = speed;
	}

	public double getHp() {
		return hp;
	}

	public void setHp(double hp) {
		this.hp = hp;
	}

	public double getDamage() {
		return damage;
	}

	public void setDamage(double damage) {
		this.damage = damage;
	}

	public double getDefense() {
		return defense;
	}

	public void setDefense(double defense) {
		this.defense = defense;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
}
