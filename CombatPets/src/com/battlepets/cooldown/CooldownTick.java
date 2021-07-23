package com.battlepets.cooldown;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.attribute.Attribute;
import org.bukkit.scheduler.BukkitScheduler;

import com.battlepets.entities.ActivePetEntity;
import com.battlepets.main.BattlePets;

public class CooldownTick {
	
	private BukkitScheduler scheduler;

	public CooldownTick() {
		this.scheduler = BattlePets.getInstance().getServer().getScheduler();
	}

	@SuppressWarnings("deprecation")
	public void schedule() {

		new Thread(() -> {

	        scheduler.scheduleSyncRepeatingTask(BattlePets.getInstance(), new Runnable() {
	            @Override
	            public void run() {
	            	List<CooldownRunnable> remove = new ArrayList<CooldownRunnable>();
	            	
	            	
	            	for(CooldownRunnable i : BattlePets.getInstance().cooldownManager.getRunnables()) {
	            		
	            		i.setTimer((i.getTimer()-1));
	            		
	            		if(i.getTimer() == 0) {
	            			i.onComplete();
	            			remove.add(i);
	            		}else {
	            			i.onTick();
	            		}
	            		
	            	}
	            	
	            	for(CooldownRunnable i : remove) {
	            		BattlePets.getInstance().cooldownManager.getRunnables().remove(i);
	            	}
	            	
	            	for(ActivePetEntity e : BattlePets.getInstance().tracker.getEntities()) {
	            		
	        			double healthPerc = e.getEntity().getHealth() / e.getEntity().getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
	        			BattlePets.getInstance().tracker.getPet(e.getEntity()).bar.setProgress(healthPerc);
	            		
	            		if(e.deul.inDuel) continue;
	            		if(e.inAggro) continue;
	            		
	            		double rate = e.getAttribute().getGeneric().getRegeneration().getRate();
	            		double addedHealth = rate * e.getEntity().getHealth();
	            		if(e.getEntity().getHealth() == e.getEntity().getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) continue;
	            		
	            		double newHealth = addedHealth + e.getEntity().getHealth();
	            		if(newHealth > e.getEntity().getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) {
	            			e.getEntity().setHealth(e.getEntity().getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
	            		}else {
	            			e.getEntity().setHealth(newHealth);
	            		}
	            		
	            	}
	            	
	            }  	
	            	
	        }, 0L, 20L);
	        
	        scheduler.scheduleAsyncRepeatingTask(BattlePets.getInstance(), new Runnable() {
	            @Override
	            public void run() {
	            	for(CooldownUser i : BattlePets.getInstance().cooldownManager.getUsers()) {
	            		i.tick();
	            	}
	            }  	
	            	
	        }, 0L, 20L);
			
		}, "Schedular").start();

	}
	
	public void stop() {
		this.scheduler.cancelTasks(BattlePets.getInstance());
	}
	
}
