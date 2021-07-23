package com.battlepets.entities;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftCreature;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftEntity;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sittable;

import com.battlepets.attributes.GenericPetAttribute;
import com.battlepets.main.BattlePets;
import com.battlepets.utils.Direction;
import com.battlepets.utils.MessageUtils;

import net.minecraft.world.entity.EntityInsentient;

public class ActivePetEntity {
	
	public UUID owner;
	public GenericPetAttribute attribute;
	public LivingEntity entity;
	public Duel deul;;
	public int followingTaskId = -1;
	public boolean inAggro;
	public boolean isWaiting;
	
	public BossBar bar;
	
	public ActivePetEntity(GenericPetAttribute attribute, LivingEntity entity, UUID owner) {
		this.attribute = attribute;
		this.entity = entity;
		this.owner = owner;
		this.deul = new Duel(false, null);
		this.isWaiting = false;
		
        this.bar = Bukkit.createBossBar("", BarColor.PURPLE, BarStyle.SOLID);
		
		this.showBossBar();
	}

	public GenericPetAttribute getPet() {
		return attribute;
	}

	public void setPet(GenericPetAttribute pet) {
		this.attribute = pet;
	}

	public LivingEntity getEntity() {
		return entity;
	}

	public void setEntity(LivingEntity entity) {
		this.entity = entity;
	}

	public UUID getOwner() {
		return owner;
	}

	public void setOwner(UUID owner) {
		this.owner = owner;
	}

	public GenericPetAttribute getAttribute() {
		return attribute;
	}
	
	public void updateAttributes() {
		this.updateAttributes(true);
	}
	
	@SuppressWarnings("deprecation")
	public void updateAttributes(boolean update) {

		LivingEntity pet = this.entity;
		GenericPetAttribute data = this.attribute;
		
		if(update) {
			data.setHealth(pet.getHealth());
		}
		
		pet.setCustomName(MessageUtils.translateAlternateColorCodes(data.getName() + BattlePets.getInstance().configManager.pets.compulsoryName.replace("%level%", ""+attribute.getLevel())));
		pet.setCustomNameVisible(true);

		try {
			pet.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(data.getMaxHealth());
			pet.setHealth(data.getHealth());
		}catch (Exception e) {}
		
		try {
			pet.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(data.getPet().getBaseStats().getDefense() + (data.getPet().getSkillPoints().getAdditionPerStat().getDefense() * data.getPointsAllocation().getDefense()));
		}catch (Exception e) {}
		
		try {
			pet.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(data.getPet().getBaseStats().getSpeed() + (data.getPet().getSkillPoints().getAdditionPerStat().getSpeed() * data.getPointsAllocation().getSpeed()));
		}catch (Exception e) {}
		
		try {
			pet.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(data.getPet().getBaseStats().getDamage() + (data.getPet().getSkillPoints().getAdditionPerStat().getDamage() * data.getPointsAllocation().getDamage()));
		}catch (Exception e) {}
		
		
		if(data.isBaby()) {
			((Ageable)pet).setBaby();
			((Ageable)pet).setAgeLock(true);
		}else {
			if(pet instanceof Ageable) {
				((Ageable)pet).setAdult();
				((Ageable)pet).setAgeLock(true);
			}
		}

		pet.setRemoveWhenFarAway(false);
		
		this.showBossBar();

	}

	public void returnToInventory(boolean toInventory) {
		
		this.hideBossBar();
		
		Player player = Bukkit.getPlayer(this.getOwner());
		
		if(player.getInventory().firstEmpty() != -1 && this.getAttribute().getHealth() > 0 && !player.isDead() && toInventory) {
			player.getInventory().addItem(BattlePets.getInstance().configManager.eggStats.eggFormatting.getSpawnEgg(this.getAttribute()));
		}else {
			BattlePets.getInstance().petInventoryHandler.getInventory(this.getOwner()).addPet(this.getAttribute());
		}
		
		this.entity.remove();
		BattlePets.getInstance().getServer().getScheduler().cancelTask(this.followingTaskId);
		BattlePets.getInstance().tracker.validate();
	}
	
	/*
	public void returnToInventory() {
		
		Player player = Bukkit.getPlayer(this.getOwner());
		
		if(player.getInventory().firstEmpty() != -1 && this.getAttribute().getHealth() > 0 && !player.isDead()) {
			player.getInventory().addItem(BattlePets.getInstance().configManager.eggStats.eggFormatting.getSpawnEgg(this.getAttribute()));
		}else {
			BattlePets.getInstance().petInventoryHandler.getInventory(this.getOwner()).addPet(this.getAttribute());
		}
		
		this.entity.remove();
		BattlePets.getInstance().getServer().getScheduler().cancelTask(this.followingTaskId);
		BattlePets.getInstance().tracker.validate();
	}
	*/


    public void follow(Entity player) {
    	
    	this.inAggro = false;
    	
        final LivingEntity e = entity;
        final Entity p = player;
        final float f = Float.valueOf(entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue() + "f");
        this.followingTaskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(BattlePets.getInstance(), new Runnable() {
            @Override
            public void run() {
            	try {
            		
                	if(e.getLocation().distance(p.getLocation()) > BattlePets.getInstance().configManager.settings.distance) {
                		e.teleport(p);
                	}else {
                		
                		double distance = Math.abs(e.getBoundingBox().getMaxX() - e.getBoundingBox().getMinX()) + 1;
                		
                		Location location = Direction.closest(p, e, distance);
                		
                	    ((EntityInsentient) ((CraftEntity) e).getHandle()).getNavigation().a(location.getX(), p.getLocation().getY(), location.getZ(), (1f + f));
                	}	
            	}catch (Throwable e) {
            		
            		Bukkit.getServer().getScheduler().cancelTask(followingTaskId);
        			returnToInventory(true);
        			MessageUtils.sendMessage(Bukkit.getPlayer(getOwner()), "&c" + attribute.getName() + "&a has returned to your inventory.");
        			
        			
            		
            	}
            	
            }
        }, 0, 5L);
    }
    
    public void showBossBar() {
    	bar.removeAll();
    	bar.setTitle(MessageUtils.translateAlternateColorCodes("&c" + this.attribute.getName()));
        bar.addPlayer(Bukkit.getPlayer(this.owner));
        bar.setVisible(true);
        
        
        
        
    }
    
    public void hideBossBar() {
    	bar.removeAll();
    	bar.setVisible(false);
    }
    
    public void aggro(LivingEntity target) {
    	
    	this.inAggro = true;
    	
    	if(this.followingTaskId == -1) {
    		return;
    	}
    	
    	Bukkit.getScheduler().cancelTask(this.followingTaskId);
    	
        final LivingEntity e = entity;
        final LivingEntity t = target;
        final float f = Float.valueOf(entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue() + "f");
        this.followingTaskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(BattlePets.getInstance(), new Runnable() {
            @Override
            public void run() {
            	
            	if(t.isDead()) {
            		Bukkit.getScheduler().cancelTask(followingTaskId);
                	follow(Bukkit.getPlayer(getOwner()));
            	}
            	
            	if(e.getLocation().distance(t.getLocation()) > BattlePets.getInstance().configManager.settings.aggroRemoveDistance) {
            		Bukkit.getScheduler().cancelTask(followingTaskId);
            		follow(Bukkit.getPlayer(getOwner()));
            	}else {
            	    ((EntityInsentient) ((CraftEntity) e).getHandle()).getNavigation().a(t.getLocation().getX(), t.getLocation().getY(), t.getLocation().getZ(), (1f + f));
            	}
            	
            	if(e.getLocation().distance(t.getLocation()) < 2) {
            		double damage = attribute.getPet().getBaseStats().getDamage() + (attribute.getPet().getSkillPoints().getAdditionPerStat().getDamage() * attribute.getPointsAllocation().getDamage());
            		t.damage(damage, e);
            		
            		
            		if(t instanceof Monster) {
            			((Monster) t).setTarget(e);
            		}else {
            			try {
            				entity.damage(t.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue());
            			}catch (Exception e) {}
            		}
            		
            		int levelBefore = attribute.getLevel();
            		
            		
            		if(BattlePets.getInstance().eventHandler.petsHandler.spawnerMobs.contains(t.getUniqueId()) && !BattlePets.getInstance().configManager.settings.allowFromSpawnerMobs) {
            			return;
            		}
            		
            		
            		attribute.addXp((int)Math.round((BattlePets.getInstance().configManager.settings.xpPerDamage * damage)));
            		
            		int levelAfter = attribute.getLevel();
            		
            		int Difference = levelAfter - levelBefore;
            		
            		int pointsEarned = Difference * BattlePets.getInstance().configManager.settings.pointsPerLevelUp;
            		
            		attribute.addPoints(pointsEarned);
            		
            		entity.setCustomName(MessageUtils.translateAlternateColorCodes(attribute.getName() + BattlePets.getInstance().configManager.pets.compulsoryName.replace("%level%", ""+attribute.getLevel())));
            		
            	}
            	
            }
        }, 0, 20L);
    }
    
    public void ToggleSitAndWait() {
    	
    	this.inAggro = false;
    	
    	if(this.followingTaskId == -1) {
    		this.follow(Bukkit.getEntity(this.getOwner()));

			MessageUtils.sendMessage(Bukkit.getPlayer(this.getOwner()), "&cYour pet is no longer waiting..");
			this.isWaiting = false;

    		
    		if(this.entity instanceof Sittable) {
    			((Sittable)entity).setSitting(false);
    		}
    		
    	}else {
    		Bukkit.getScheduler().cancelTask(this.followingTaskId);
    		
			MessageUtils.sendMessage(Bukkit.getPlayer(this.getOwner()), "&cYou let your pet wait in position.");
			this.isWaiting = true;
			
    		if(this.entity instanceof Sittable) {
    			((Sittable)entity).setSitting(true);
    		}
    		
    		this.followingTaskId = -1;
    	}
    }
    
    public void stopFighting() {
    	
    	this.inAggro = false;
    	
    	this.deul.setInDuel(false);
    	
    	if(this.followingTaskId == -1) {
    		return;
    	}
    	
    	Bukkit.getScheduler().cancelTask(this.followingTaskId);
    }
    
    public void fight(LivingEntity target) {
    	
    	
    	this.inAggro = false;
    	this.deul.setInDuel(true);
    	
    	final boolean isTargetAPet = BattlePets.getInstance().tracker.isPet(target);
    	
    	if(isTargetAPet) {
    		ActivePetEntity t = BattlePets.getInstance().tracker.getPet(target);
    		this.deul.setTarget(t);
    	}
    	
    	if(this.followingTaskId == -1) {
    		return;
    	}
    	
    	Bukkit.getScheduler().cancelTask(this.followingTaskId);
    	
        final LivingEntity e = entity;
        final LivingEntity t = target;
        
        
        final float f = Float.valueOf(entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue() + "f");
        this.followingTaskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(BattlePets.getInstance(), new Runnable() {
            @Override
            public void run() {
            	
            	if(e.getLocation().distance(t.getLocation()) > BattlePets.getInstance().configManager.settings.distance) {
            		e.teleport(t);
            	}else {
            	    ((EntityInsentient) ((CraftEntity) e).getHandle()).getNavigation().a(t.getLocation().getX(), t.getLocation().getY(), t.getLocation().getZ(), (1f + f));
            	}
            	
            	if(e.getLocation().distance(t.getLocation()) < 2) {
            		double damage = attribute.getPet().getBaseStats().getDamage() + (attribute.getPet().getSkillPoints().getAdditionPerStat().getDamage() * attribute.getPointsAllocation().getDamage());
            		t.damage(damage, e);
            		
            		if(!isTargetAPet) {
	            		
	            		int levelBefore = attribute.getLevel();
	            		
	            		attribute.addXp((int)Math.round((BattlePets.getInstance().configManager.settings.xpPerDamage * damage)));
	            		
	            		int levelAfter = attribute.getLevel();
	            		
	            		int Difference = levelAfter - levelBefore;
	            		
	            		int pointsEarned = Difference * BattlePets.getInstance().configManager.settings.pointsPerLevelUp;
	            		
	            		attribute.addPoints(pointsEarned);
	            		
	            		entity.setCustomName(MessageUtils.translateAlternateColorCodes(attribute.getName() + " &c[&4Level: &c" + attribute.getLevel()+"]"));
            		
            		}
            		
            	}
            	
            }
        }, 0, 5L);
    }
    
    public void walkToLocation(Location location) {
    	this.inAggro = false;
        ((CraftCreature) entity)
                .getHandle()
                .getNavigation()
                .a(location.getX(), location.getY(), location.getZ(), entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue());
    }
	public void setAttribute(GenericPetAttribute attribute) {
		this.attribute = attribute;
	}

}
