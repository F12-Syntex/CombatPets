package com.battlepets.eggformat;

public class Lore {

	public String entity;
	public boolean baby;
	public String level;
	public String xp;
	public String hp;
	public String skillpoints;
	public String vitality;
	public String damage;
	public String defense;
	public String speed;
	
	public Lore(String entity, String level, String xp, String hp, String skillpoints, String vitality, String damage, String defense, String speed) {
		this.entity = entity;
		this.baby = entity.startsWith("baby");
		this.level = level;
		this.xp = xp;
		this.hp = hp;
		this.skillpoints = skillpoints;
		this.vitality = vitality;
		this.damage = damage;
		this.defense = defense;
		this.speed = speed;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getXp() {
		return xp;
	}

	public void setXp(String xp) {
		this.xp = xp;
	}

	public String getHp() {
		return hp;
	}

	public void setHp(String hp) {
		this.hp = hp;
	}

	public String getSkillpoints() {
		return skillpoints;
	}

	public void setSkillpoints(String skillpoints) {
		this.skillpoints = skillpoints;
	}

	public String getVitality() {
		return vitality;
	}

	public void setVitality(String vitality) {
		this.vitality = vitality;
	}

	public String getDamage() {
		return damage;
	}

	public void setDamage(String damage) {
		this.damage = damage;
	}

	public String getDefense() {
		return defense;
	}

	public void setDefense(String defense) {
		this.defense = defense;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public boolean isBaby() {
		return baby;
	}

	public void setBaby(boolean baby) {
		this.baby = baby;
	}
	
}
