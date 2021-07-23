package com.battlepets.eggformat;

public class Position {

	public int entity;
	public int level;
	public int xp;
	public int hp;
	public int skillpoints;
	public int vitality;
	public int damage;
	public int defense;
	public int speed;
	
	public Position(int entity, int level, int xp, int hp, int skillpoints, int vitality, int damage, int defense, int speed) {
		this.entity = entity;
		this.level = level;
		this.xp = xp;
		this.hp = hp;
		this.skillpoints = skillpoints;
		this.vitality = vitality;
		this.damage = damage;
		this.defense = defense;
		this.speed = speed;
	}

	public int getEntity() {
		return entity;
	}

	public void setEntity(int entity) {
		this.entity = entity;
	}
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getXp() {
		return xp;
	}

	public void setXp(int xp) {
		this.xp = xp;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getSkillpoints() {
		return skillpoints;
	}

	public void setSkillpoints(int skillpoints) {
		this.skillpoints = skillpoints;
	}

	public int getVitality() {
		return vitality;
	}

	public void setVitality(int vitality) {
		this.vitality = vitality;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
}
