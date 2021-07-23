package com.battlepets.petsdata;

import org.bukkit.entity.EntityType;

import com.battlepets.attributes.GenericPetAttribute;

public class Pet {
	
	private String defaultName;
	private Riding Riding;
	private Level Level;
	private Regeneration Regeneration;
	private BaseStats BaseStats;
	private SkillPoints SkillPoints;
	
	private EntityType entityType;
	private boolean baby;
	
	public Pet(EntityType entityType, boolean baby, String defaultName, Riding Riding, Level level, Regeneration regeneration, BaseStats baseStats, SkillPoints skillPoints) {
		this.defaultName = defaultName;
		this.Riding = Riding;
		this.Level = level;
		this.Regeneration = regeneration;
		this.BaseStats = baseStats;
		this.SkillPoints = skillPoints;
		this.entityType = entityType;
		this.baby = baby;
	}
	
	public GenericPetAttribute getDefaultAttributes() {
		GenericPetAttribute genericPetAttribute = new GenericPetAttribute(entityType, baby, SkillPoints.getPoints(), 0, new PointsAllocation(0, 0, 0, 0), this.defaultName, this.BaseStats.getHp());
		return genericPetAttribute;
	}

	public String getDefaultName() {
		return defaultName;
	}

	public void setDefaultName(String defaultName) {
		this.defaultName = defaultName;
	}

	public Riding getRiding() {
		return Riding;
	}

	public void setRiding(Riding riding) {
		Riding = riding;
	}

	public Level getLevel() {
		return Level;
	}

	public void setLevel(Level level) {
		Level = level;
	}

	public Regeneration getRegeneration() {
		return Regeneration;
	}

	public void setRegeneration(Regeneration regeneration) {
		Regeneration = regeneration;
	}

	public BaseStats getBaseStats() {
		return BaseStats;
	}

	public void setBaseStats(BaseStats baseStats) {
		BaseStats = baseStats;
	}

	public SkillPoints getSkillPoints() {
		return SkillPoints;
	}

	public void setSkillPoints(SkillPoints skillPoints) {
		SkillPoints = skillPoints;
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
	
	
}
