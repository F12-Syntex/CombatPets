package com.battlepets.config;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.WaterMob;

import com.battlepets.petsdata.BaseStats;
import com.battlepets.petsdata.Level;
import com.battlepets.petsdata.MaxAllocation;
import com.battlepets.petsdata.Pet;
import com.battlepets.petsdata.PetsData;
import com.battlepets.petsdata.PointsAllocation;
import com.battlepets.petsdata.Regeneration;
import com.battlepets.petsdata.Riding;
import com.battlepets.petsdata.SkillPoints;
import com.battlepets.utils.StringMinipulation;

public class Pets extends Config{

	public PetsData petsData;
	
	private final String babyPrefix = "BABY-";
	
	public String compulsoryName = " &c[&4Level: &c%level%]";
	
	public Pets(String name, double version) {
		super(name, version);
		
		this.items.add(new ConfigItem("Name.compulsoryNameEnd", compulsoryName));
		
		for(EntityType i : EntityType.values()) {
			
			
			/*
			if(i == EntityType.TURTLE) continue;
			if(i == EntityType.DOLPHIN) continue;
			if(i == EntityType.GHAST) continue;
			if(i == EntityType.PHANTOM) continue;
			if(i == EntityType.VEX) continue;
			if(i == EntityType.BAT) continue;
			if(i == EntityType.PUFFERFISH) continue;
			if(i == EntityType.SALMON) continue;
			if(i == EntityType.COD) continue;
			if(i == EntityType.TROPICAL_FISH) continue;
			if(i == EntityType.SHULKER) continue;
			if(i == EntityType.SLIME) continue;
			if(i == EntityType.MAGMA_CUBE) continue;
			if(i == EntityType.SQUID) continue;
			if(i == EntityType.ELDER_GUARDIAN) continue;
			if(i == EntityType.GUARDIAN) continue;
			if(i == EntityType.PARROT) continue;
			if(i == EntityType.BEE) continue;
			if(i == EntityType.CAT) continue;
			if(i == EntityType.OCELOT) continue;
			*/
			
			String creature = StringMinipulation.capitalize(i.name().toLowerCase().replace("_", " "));
			
			World world = Bukkit.getWorlds().get(0);
		
			try {

				Entity entity = world.spawnEntity(new Location(world, 0, 0, 0), i);
				
				if(entity instanceof WaterMob) continue;
				
				if(!(entity instanceof LivingEntity)) continue;
				
				LivingEntity livingEntity = (LivingEntity)entity;
				
				this.addEntity(i, creature, livingEntity, false);

				if(entity instanceof Ageable) {
					if(i == EntityType.WANDERING_TRADER) continue;
					if(i == EntityType.PARROT) continue;
					if(i == EntityType.PIGLIN_BRUTE) continue;
					if(i == EntityType.PIGLIN) continue;
				     this.addEntity(i, creature, livingEntity, true);
				}
				
				livingEntity.setHealth(0);
				
				
			}catch (Exception e) {
				continue;
			}
			
		}

	}

	public void addEntity(EntityType i, String creature, LivingEntity livingEntity, boolean baby) {
		
		//System.out.println("Average data for (" + livingEntity.getName() + ") : " + livingEntity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue());
		
		String prefixOfEntity = "";
		
		if(baby) {
			prefixOfEntity = this.babyPrefix;
		}
		
		if(baby) {
			this.items.add(new ConfigItem("Pets." + prefixOfEntity+i.name() + ".display.defaultName", "&6" + creature));
		}else {
			this.items.add(new ConfigItem("Pets." + prefixOfEntity+i.name() + ".display.defaultName",  "&6" + creature + " &7(&eBaby&7)"));
		}
		
		this.items.add(new ConfigItem("Pets." + prefixOfEntity+i.name() + ".riding.ridable", true));
		this.items.add(new ConfigItem("Pets." + prefixOfEntity+i.name() + ".riding.requiredLevel", 0));
		//this.items.add(new ConfigItem("Pets." + prefixOfEntity+i.name() + ".level.maxLevel", 30));
		this.items.add(new ConfigItem("Pets." + prefixOfEntity+i.name() + ".level.xp.starting", 150));
		this.items.add(new ConfigItem("Pets." + prefixOfEntity+i.name() + ".level.xp.multiplier", 1.1));
		//this.items.add(new ConfigItem("Pets." + prefixOfEntity+i.name() + ".level.skillPointsEarned", 5));
		this.items.add(new ConfigItem("Pets." + prefixOfEntity+i.name() + ".regeneration.amountPerSecond", 0.01));
		
		this.items.add(new ConfigItem("Pets." + prefixOfEntity+i.name() + ".BaseStats.HP", 20.0));
		this.items.add(new ConfigItem("Pets." + prefixOfEntity+i.name() + ".BaseStats.Damage", 2));
		this.items.add(new ConfigItem("Pets." + prefixOfEntity+i.name() + ".BaseStats.Defense", 0.5));
		this.items.add(new ConfigItem("Pets." + prefixOfEntity+i.name() + ".BaseStats.Speed", 0.2));
		
		this.items.add(new ConfigItem("Pets." + prefixOfEntity+i.name() + ".skillpoints.starting", 5));
		
		this.items.add(new ConfigItem("Pets." + prefixOfEntity+i.name() + ".skillpoints.AddPerStat.HP", 1));
		this.items.add(new ConfigItem("Pets." + prefixOfEntity+i.name() + ".skillpoints.AddPerStat.Damage", 0.25));
		this.items.add(new ConfigItem("Pets." + prefixOfEntity+i.name() + ".skillpoints.AddPerStat.Defense", 0.25));
		this.items.add(new ConfigItem("Pets." + prefixOfEntity+i.name() + ".skillpoints.AddPerStat.Speed", 0.02));
		
		this.items.add(new ConfigItem("Pets." + prefixOfEntity+i.name() + ".skillpoints.maxAllocation.HP", 40));
		this.items.add(new ConfigItem("Pets." + prefixOfEntity+i.name() + ".skillpoints.maxAllocation.Damage", 10));
		this.items.add(new ConfigItem("Pets." + prefixOfEntity+i.name() + ".skillpoints.maxAllocation.Defense", 40));
		this.items.add(new ConfigItem("Pets." + prefixOfEntity+i.name() + ".skillpoints.maxAllocation.Speed", 20));
	}
	
	@Override
	public Configuration configuration() {
		// TODO Auto-generated method stub
		return Configuration.PETS;
	}
	
	@Override
	public void initialize() {
		
		if(!this.getConfiguration().isConfigurationSection("Pets")) {
			return;
		}
	
		this.petsData = new PetsData();
		
		ConfigurationSection pets = this.getConfiguration().getConfigurationSection("Pets");
		
		this.compulsoryName = this.getConfiguration().getString("Name.compulsoryNameEnd");
		
		for(String pet : pets.getKeys(false)) {
			
			ConfigurationSection petSection = pets.getConfigurationSection(pet);
			
			boolean isBaby = pet.contains(this.babyPrefix);
			
			if(isBaby) {
				pet = pet.substring(this.babyPrefix.length());
			}
			
			EntityType entity = EntityType.valueOf(pet);
			
			String defaultName = petSection.getString("display.defaultName");
			
			boolean ridable = petSection.getBoolean("riding.ridable");
			int requiredLevel = petSection.getInt("riding.requiredLevel");
			
			Riding Riding = new Riding(ridable, requiredLevel);
			
			//int maxLevel = petSection.getInt("level.maxLevel");
			double xpStarting = petSection.getDouble("level.xp.starting");
			double xpMultiplier = petSection.getDouble("level.xp.multiplier");
			//int skillPointsEarned = petSection.getInt("level.skillPointsEarned");
			Level level = new Level(30, xpStarting, xpMultiplier, 1);

			double rate = petSection.getDouble("regeneration.amountPerSecond");
			
			Regeneration regeneration = new Regeneration(rate);
			
			double baseHealth = petSection.getDouble("BaseStats.HP");
			double baseDamage = petSection.getDouble("BaseStats.Damage");
			double baseDefense = petSection.getDouble("BaseStats.Defense");
			double baseSpeed = petSection.getDouble("BaseStats.Speed");
			
			BaseStats baseStats = new BaseStats(baseHealth, baseDamage, baseDefense, baseSpeed);
			
			int skillStarting = petSection.getInt("skillpoints.starting");
			
			double AddPerStatHealth = petSection.getDouble("skillpoints.AddPerStat.HP");
			double AddPerStatDamage = petSection.getDouble("skillpoints.AddPerStat.Damage");
			double AddPerStatDefense = petSection.getDouble("skillpoints.AddPerStat.Defense");
			double AddPerStatSpeed = petSection.getDouble("skillpoints.AddPerStat.Speed");

			double maxAllocationHealth = petSection.getDouble("skillpoints.maxAllocation.HP");
			double maxAllocationDamage = petSection.getDouble("skillpoints.maxAllocation.Damage");
			double maxAllocationDefense = petSection.getDouble("skillpoints.maxAllocation.Defense");
			double maxAllocationSpeed = petSection.getDouble("skillpoints.maxAllocation.Speed");
			
			PointsAllocation allocation = new PointsAllocation(AddPerStatHealth, AddPerStatDamage, AddPerStatDefense, AddPerStatSpeed);
			MaxAllocation maxAllocation = new MaxAllocation(maxAllocationHealth, maxAllocationDamage, maxAllocationDefense, maxAllocationSpeed);
			
			SkillPoints skillPoints = new SkillPoints(skillStarting, allocation, maxAllocation);
			
			Pet currentPet = new Pet(entity, isBaby, defaultName, Riding, level, regeneration, baseStats, skillPoints);
			
			this.petsData.registerPet(currentPet);
		}
	
	}

	public PetsData getPetsData() {
		return petsData;
	}

	public void setPetsData(PetsData petsData) {
		this.petsData = petsData;
	}

	public String getBabyPrefix() {
		return babyPrefix;
	}


	
}
