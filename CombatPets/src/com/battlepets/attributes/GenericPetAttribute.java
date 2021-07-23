package com.battlepets.attributes;

import org.bukkit.entity.EntityType;

import com.battlepets.eggmeta.EggStorage;
import com.battlepets.main.BattlePets;
import com.battlepets.petsdata.Pet;
import com.battlepets.petsdata.PointsAllocation;

public class GenericPetAttribute {
	
	private String name;
	
	private int points;
	private double xp;
	
	private double health;
	
	private EntityType entityType;
	private boolean baby;
	
	private Pet generic;

	private PointsAllocation pointsAllocation;
	
	private EggStorage storage;

	public GenericPetAttribute(EntityType entityType, boolean baby, int points, int xp, PointsAllocation pointsAllocation, String name, double health) {
		this.points = points;
		this.xp = xp;

		this.entityType = entityType;
		this.baby = baby;
	
		this.generic = BattlePets.getInstance().configManager.pets.petsData.getPet(entityType, baby);
		
		this.pointsAllocation = pointsAllocation;
		this.name = name;
		this.health = health;	
	}
	
	public double getMaxHealth() {
		return this.getGeneric().getBaseStats().getHp() + (this.getGeneric().getSkillPoints().getAdditionPerStat().getHp() * this.getPointsAllocation().getHp());
	}
	
	public void addXp(int xp) {
		this.xp += xp;
	}
	
	public double getMaxXP() {
		return this.getGeneric().getLevel().getRequiredXpForLevel(this.getLevel());
	}

	public int getLevel() {
		
		int level = this.getGeneric().getLevel().getLevelByXp(this.xp);
		
		if(level >= BattlePets.getInstance().configManager.settings.maxLevel) {
			level = BattlePets.getInstance().configManager.settings.maxLevel;
		}
		return level;
	}

	public int getPoints() {
		return points;
	}
	
	public void addPoints(int points) {
		this.points += points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public double getXp() {
		return xp;
	}

	public void setXp(double xp2) {
		this.xp = xp2;
	}

	public PointsAllocation getPointsAllocation() {
		return pointsAllocation;
	}

	public void setPointsAllocation(PointsAllocation pointsAllocation) {
		this.pointsAllocation = pointsAllocation;
	}

	public EntityType getEntityType() {
		return entityType;
	}

	public void setEntityType(EntityType entityType) {
		this.entityType = entityType;
	}

	public boolean isBaby() {
		return baby;
	}

	public void setBaby(boolean baby) {
		this.baby = baby;
	}

	public Pet getPet() {
		return generic;
	}

	public void setPet(Pet pet) {
		this.generic = pet;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Pet getGeneric() {
		return generic;
	}

	public void setGeneric(Pet generic) {
		this.generic = generic;
	}

	public double getHealth() {
		return health;
	}

	public void setHealth(double health) {
		this.health = health;
	}

	public EggStorage getStorage() {
		return storage;
	}

	public void setStorage(EggStorage storage) {
		this.storage = storage;
	}
	

}
