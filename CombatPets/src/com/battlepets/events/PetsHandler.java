package com.battlepets.events;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockShearEntityEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.EntityPoseChangeEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.event.entity.EntityTransformEvent;
import org.bukkit.event.entity.PigZapEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.VillagerCareerChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import com.battlepets.GUI.GUI;
import com.battlepets.GUI.PetCombatGUI;
import com.battlepets.GUI.PetGui;
import com.battlepets.attributes.GenericPetAttribute;
import com.battlepets.battle.Request;
import com.battlepets.entities.ActiveEntityTracker;
import com.battlepets.entities.ActivePetEntity;
import com.battlepets.helpful.objects.PlayerLocation;
import com.battlepets.main.BattlePets;
import com.battlepets.utils.Communication;
import com.battlepets.utils.MessageUtils;

public class PetsHandler extends SubEvent{

	private List<PlayerLocation> players = new ArrayList<PlayerLocation>();
	public List<UUID> spawnerMobs = new ArrayList<UUID>();
	
	
	@Override
	public String name() {
		// TODO Auto-generated method stub
		return "pet handler";
	}
	
	@Override
	public String description() {
		// TODO Auto-generated method stub
		return "handles operations for all pets.";
	}
	
	@EventHandler
	public void onPetPlaced(CreatureSpawnEvent e) {
		
		if(e.getSpawnReason() == SpawnReason.SPAWNER) {
			this.spawnerMobs.add(e.getEntity().getUniqueId());
		}
		
		if(e.getSpawnReason() == SpawnReason.SPAWNER_EGG) {
		
			Location location = e.getLocation().getWorld().getBlockAt(e.getLocation()).getLocation();
			
			PlayerLocation playerLocation = null;
			
			for(PlayerLocation i : players) {
				try {
					if(i.getLocation().distance(location) <= 1) {
						playerLocation = i;
					}	
				}catch (Exception e2) {
					continue;
				}
			}
			
			if(playerLocation == null) return;
			
			players.remove(playerLocation);
			
			Player owner = playerLocation.getPlayer();
			
			if(playerLocation.getHand() == EquipmentSlot.HAND) {
				int amount = playerLocation.getPlayer().getInventory().getItemInMainHand().getAmount();
				playerLocation.getPlayer().getInventory().getItemInMainHand().setAmount((amount-1));					
			}else {
				int amount = playerLocation.getPlayer().getInventory().getItemInOffHand().getAmount();
				playerLocation.getPlayer().getInventory().getItemInOffHand().setAmount((amount-1));	
			}
			
			e.setCancelled(true);
			
			LivingEntity pet = (LivingEntity) location.getWorld().spawnEntity(location, e.getEntityType());
			
			pet.getCollidableExemptions().add(playerLocation.getPlayer().getUniqueId());
			playerLocation.getPlayer().getCollidableExemptions().add(pet.getUniqueId());
			
			if(pet instanceof Slime) {
				((Slime)pet).setSize(2);
			}

			try {
				pet.getEquipment().setHelmet(null);
				pet.getEquipment().setChestplate(null);
				pet.getEquipment().setLeggings(null);
				pet.getEquipment().setBoots(null);
				pet.getEquipment().setItem(EquipmentSlot.HAND, null);
				pet.getEquipment().setItem(EquipmentSlot.OFF_HAND, null);
			}catch (Exception e2) {}
			
			//EntityInsentient craftEntity = ((EntityInsentient) ((CraftEntity) pet).getHandle());
			
			try {
				
				/*
				 * 
				//PathfinderWater goalSelector = ((EntityInsentient) ((CraftEntity) pet).getHandle()).getGoalTarget();
				//PathfinderGoalSelector targetSelector = ((EntityInsentient) ((CraftEntity) pet).getHandle()).getGoalTarget();
				
				EntityLiving goalSelector = ((EntityInsentient) ((CraftEntity) pet).getHandle()).getGoalTarget();
				EntityLiving targetSelector = ((EntityInsentient) ((CraftEntity) pet).getHandle()).getGoalTarget();
				
				
			    Set<PathfinderGoalWrapped> goalD = (Set<PathfinderGoalWrapped>) NMSManager.getPrivateField("d", PathfinderGoalSelector.class, goalSelector);
			    goalD.clear();
		        Map<PathfinderGoal.Type, PathfinderGoalWrapped> goalC = (Map<PathfinderGoal.Type, PathfinderGoalWrapped>) NMSManager.getPrivateField("c", PathfinderGoalSelector.class, goalSelector);
		        goalC.clear();
		        Set<PathfinderGoalWrapped> goalD2 = (Set<PathfinderGoalWrapped>) NMSManager.getPrivateField("d", PathfinderGoalSelector.class, targetSelector); goalD2.clear();
		        Map<PathfinderGoal.Type, PathfinderGoalWrapped> goalC2 = (Map<PathfinderGoal.Type, PathfinderGoalWrapped>) NMSManager.getPrivateField("c", PathfinderGoalSelector.class, targetSelector);
		        goalC2.clear();
		       
		        craftEntity.setGoalTarget(goalSelector);
		        craftEntity.setGoalTarget(targetSelector);
		        */

			}catch (Exception e2) {
				e2.printStackTrace();
			}

			pet.setCanPickupItems(false);

			GenericPetAttribute data = playerLocation.getData();
			
			ActivePetEntity activePetEntity = new ActivePetEntity(data, pet, owner.getUniqueId());
			
			activePetEntity.updateAttributes(false);
			
			activePetEntity.follow(playerLocation.getPlayer());
			
			BattlePets.getInstance().tracker.addEntity(activePetEntity);
		}
	}
	
	@EventHandler
	public void onVillagerCareerChangeEvent(VillagerCareerChangeEvent e) {
		if(BattlePets.getInstance().tracker.isPet(e.getEntity())){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onTransform(PigZapEvent e) {
		if(BattlePets.getInstance().tracker.isPet(e.getEntity())){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onEntityInteract(EntityInteractEvent e) {
		if(BattlePets.getInstance().tracker.isPet(e.getEntity())){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		BattlePets.getInstance().tracker.getActivePets(e.getPlayer().getUniqueId()).forEach(i -> {
			
			i.getAttribute().setHealth(i.getEntity().getHealth());
			
			if(i.deul.inDuel) {
				
				ActivePetEntity target = i.deul.getTarget();
			
				//MessageUtils.sendMessage(Bukkit.getPlayer(target.getOwner()), "&c" + Bukkit.getPlayer(i.getOwner()).getDisplayName() + " has disconnected.");
				
				MessageUtils.sendRawMessage(Bukkit.getPlayer(target.getOwner()), BattlePets.getInstance().configManager.messages.getMessage("duel_disconnected").replace("%user%", Bukkit.getPlayer(i.getOwner()).getDisplayName()));
				
				target.stopFighting();
				i.stopFighting();
				
				target.follow(Bukkit.getPlayer(target.getOwner()));
			}
			
			i.returnToInventory(false);
		});		
		
	}
	
	@EventHandler
	public void onShear(BlockShearEntityEvent e) {
		if(BattlePets.getInstance().tracker.isPet(e.getEntity())) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onShear(EntityTeleportEvent e) {
		if(BattlePets.getInstance().tracker.isPet(e.getEntity())) {
			
			ActivePetEntity pet = BattlePets.getInstance().tracker.getPet(e.getEntity());
			
			if(Bukkit.getPlayer(pet.getOwner()).getLocation().distance(e.getTo()) < 2) {
				return;
			}
			
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onDeath(EntityDeathEvent e) {
		
		if(this.spawnerMobs.contains(e.getEntity().getUniqueId())) {
			this.spawnerMobs.remove(e.getEntity().getUniqueId());
		}
		
		
		if(BattlePets.getInstance().tracker.isPet(e.getEntity())){
			e.setDroppedExp(0);
			e.getDrops().clear();
			
			ActivePetEntity pet = BattlePets.getInstance().tracker.getPet(e.getEntity());
			
			if(pet.deul.inDuel) {
				ActivePetEntity target = pet.deul.target;
				
				target.stopFighting();
				target.follow(Bukkit.getPlayer(target.getOwner()));
				
				pet.stopFighting();
				
				GenericPetAttribute attribute = pet.getAttribute();
				
				attribute.setHealth(0);
				
				Bukkit.getPlayer(pet.getOwner()).closeInventory();
				
				pet.returnToInventory(false);
				//MessageUtils.sendMessage(Bukkit.getPlayer(pet.getOwner()), "&c" + attribute.getName() + "&c has died. You can revive the pet in /cp inventory.");
				
				MessageUtils.sendRawMessage(Bukkit.getPlayer(pet.getOwner()), BattlePets.getInstance().configManager.messages.getMessage("pet_death").replace("%pet%", attribute.getName()));
				
				
			}else {
				
				pet.stopFighting();
				
				GenericPetAttribute attribute = pet.getAttribute();
				
				attribute.setHealth(0);
				attribute.setName(attribute.getName());
				
				pet.setAttribute(attribute);
				
				Bukkit.getPlayer(pet.getOwner()).closeInventory();
				
				pet.returnToInventory(false);
				MessageUtils.sendRawMessage(Bukkit.getPlayer(pet.getOwner()), BattlePets.getInstance().configManager.messages.getMessage("pet_death").replace("%pet%", attribute.getName()));
				
			}
			
		}
		
		if(e.getEntity() instanceof Player) {
			BattlePets.getInstance().tracker.getActivePets(e.getEntity().getUniqueId()).forEach(i -> {
				i.returnToInventory(false);
				//MessageUtils.sendMessage((Player)e.getEntity(), "&c" + i.attribute.getName() + "&a has returned to your inventory.");  
				
				MessageUtils.sendRawMessage((Player)e.getEntity(), BattlePets.getInstance().configManager.messages.getMessage("pet_returned").replace("%pet%", i.attribute.getName()));
				
			});
			
			
			
			
		}
		
	}
	
	
	@EventHandler
	public void onInventoryChange(InventoryMoveItemEvent event) {
		if(BattlePets.getInstance().configManager.eggStats.eggFormatting.validate(event.getItem())) {
        	event.setCancelled(true);
        }
	}
	
	@EventHandler
	public void onInventoryChange(InventoryClickEvent event) {

		if(event.getInventory().getType() == InventoryType.CHEST || 
		   event.getInventory().getType() == InventoryType.ENDER_CHEST ||
		   event.getInventory().getType() == InventoryType.CREATIVE ||
		   event.getInventory().getType() == InventoryType.PLAYER ||
		   event.getInventory().getType() == InventoryType.SHULKER_BOX ||
		   event.getInventory().getType() == InventoryType.CRAFTING ||
		   event.getInventory().getType() == InventoryType.HOPPER) return;
			
			
		if(BattlePets.getInstance().configManager.eggStats.eggFormatting.validate(event.getCurrentItem())) {
        	event.setCancelled(true);
        	MessageUtils.sendRawMessage((Player)event.getWhoClicked(), BattlePets.getInstance().configManager.messages.getMessage("pet_illegal_move")); 
        }
        
    }
	
	@EventHandler
	public void petFishingRodEvent(PlayerFishEvent e) {
		if(BattlePets.getInstance().tracker.isPet(e.getCaught())) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void petSitInBoat(VehicleEnterEvent e) {
		if(BattlePets.getInstance().tracker.isPet(e.getEntered())) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void animalInteract(PlayerInteractEntityEvent e) {

		if(e.getHand().compareTo(EquipmentSlot.HAND) != 0) return;
		
		ActiveEntityTracker tracker = BattlePets.getInstance().tracker;
		
		if(!tracker.isPet(e.getRightClicked())) return;
		
		ActivePetEntity targetPet = tracker.getPet(e.getRightClicked());
		
		if(e.getPlayer().isSneaking() && (targetPet.getOwner().compareTo(e.getPlayer().getUniqueId()) == 0)) {
			targetPet.ToggleSitAndWait();
			e.setCancelled(true);
			
			if(targetPet.isWaiting) {	
				
				/*	
				BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
	            
	            int[] ID = new int[] {0};
	            
	            ID[0] = scheduler.scheduleSyncRepeatingTask(BattlePets.getInstance(), new Runnable() {
	                @Override
	                public void run() {
	                
	                	if(targetPet.isWaiting && targetPet.getEntity().isValid()) {
	                		
	                        Location location = targetPet.getEntity().getLocation();
	                        for (int degree = 0; degree < 360; degree++) {
	                            double radians = Math.toRadians(degree);
	                            double x = Math.cos(radians);
	                            double z = Math.sin(radians);
	                            location.add(x,0,z);
	                            location.subtract(x,0,z);
	                        }
	                        
                            location.getWorld().playEffect(location.add(0, 1, 0), Effect.CLICK2, 1);
	                		
	                		
	                	}else {
	                		Bukkit.getServer().getScheduler().cancelTask(ID[0]);
	                	}
	                	
	                }
	            }, 0L, 20L);				
	            */
			}
			
			
			return;
		}
		
		if(targetPet.getOwner().compareTo(e.getPlayer().getUniqueId()) != 0) {
			
			if(BattlePets.getInstance().battleRequests.contains(e.getPlayer().getUniqueId())) {
				
				Request request = BattlePets.getInstance().battleRequests.getRequest(e.getPlayer().getUniqueId());
				
				BattlePets.getInstance().battleRequests.removeRequest(request);
				
				Player target = Bukkit.getPlayer(targetPet.getOwner());
				
				/*
				MessageUtils.sendMessage(e.getPlayer(), "&aSent a battle request to " + target.getDisplayName());
				MessageUtils.sendMessage(target, "&a" + e.getPlayer().getDisplayName() + " wants to battle " + targetPet.getAttribute().getName());
				MessageUtils.sendMessage(target, "&cType &aConfirm &cTo start the fight!");
				*/
				
				MessageUtils.sendRawMessage(e.getPlayer(), BattlePets.getInstance().configManager.messages.getMessage("deul_request").replace("%target%", target.getDisplayName()).replace("%player%", e.getPlayer().getDisplayName()));
				MessageUtils.sendRawMessage(target, BattlePets.getInstance().configManager.messages.getMessage("deul_request_target_confirm").replace("%target%", target.getDisplayName()).replace("%player%", e.getPlayer().getDisplayName()));
				
				Communication.applyInput(target, (message) -> {
					
					if(message.trim().equalsIgnoreCase("confirm")) {
						
						Bukkit.getServer().getScheduler().runTask(BattlePets.getInstance(), () -> {
							e.getPlayer().closeInventory();
							target.closeInventory();
						});
						
						
						if(BattlePets.getInstance().tracker.getActivePets(target.getUniqueId()).size() <= 0) {
							//MessageUtils.sendMessage(target, "&cBattle canceled due to you not having an active pet.");
							//MessageUtils.sendMessage(e.getPlayer(), "&cBattle canceled due to " + target.getDisplayName() + "&c removing his pet.");
							
							MessageUtils.sendRawMessage(target, BattlePets.getInstance().configManager.messages.getMessage("deul_request_canceled_activepet").replace("%target%", target.getDisplayName()).replace("%player%", e.getPlayer().getDisplayName()));
							MessageUtils.sendRawMessage(e.getPlayer(), BattlePets.getInstance().configManager.messages.getMessage("deul_request_canceled_target_removed_pet").replace("%target%", target.getDisplayName()).replace("%player%", e.getPlayer().getDisplayName()));
							
							return;
						}
						
						if(BattlePets.getInstance().tracker.getActivePets(e.getPlayer().getUniqueId()).size() <= 0) {
							//MessageUtils.sendMessage(e.getPlayer(), "&cBattle canceled due to you not having an active pet.");
							//MessageUtils.sendMessage(target, "&cBattle canceled due to " + e.getPlayer().getDisplayName() + "&c removing his pet.");
							
							MessageUtils.sendRawMessage(e.getPlayer(), BattlePets.getInstance().configManager.messages.getMessage("deul_request_canceled_activepet").replace("%target%", target.getDisplayName()).replace("%player%", e.getPlayer().getDisplayName()));
							MessageUtils.sendRawMessage(target, BattlePets.getInstance().configManager.messages.getMessage("deul_request_canceled_target_removed_pet").replace("%target%", target.getDisplayName()).replace("%player%", e.getPlayer().getDisplayName()));
							
							
							return;
						}
						
						//deul_starting
						
						//MessageUtils.sendMessage(target, "&aBattle starting!");
						//MessageUtils.sendMessage(e.getPlayer(), "&aBattle starting!");
						
			        	MessageUtils.sendRawMessage(target, BattlePets.getInstance().configManager.messages.getMessage("deul_starting")); 
			        	MessageUtils.sendRawMessage(e.getPlayer(), BattlePets.getInstance().configManager.messages.getMessage("deul_starting")); 
						
						ActivePetEntity pet = request.getPet();
						
						pet.fight(targetPet.getEntity());
						targetPet.fight(pet.getEntity());
						
						
						
						
					}else {
						//MessageUtils.sendMessage(target, "&cRequest canceled!");
						//MessageUtils.sendMessage(e.getPlayer(), "&cRequest canceled!");
						
			        	MessageUtils.sendRawMessage(target, BattlePets.getInstance().configManager.messages.getMessage("deul_request_canceled")); 
			        	MessageUtils.sendRawMessage(e.getPlayer(), BattlePets.getInstance().configManager.messages.getMessage("deul_request_canceled")); 	
						
					}
					
					
				}, 60000, () -> {
					//MessageUtils.sendMessage(target, "&cRequest timed out!");
					//MessageUtils.sendMessage(e.getPlayer(), "&cRequest timed out!");
				 	MessageUtils.sendRawMessage(target, BattlePets.getInstance().configManager.messages.getMessage("request_timed_out")); 
		        	MessageUtils.sendRawMessage(e.getPlayer(), BattlePets.getInstance().configManager.messages.getMessage("request_timed_out")); 	
					
				});
			
				e.setCancelled(true);
				return;
			}
			
			e.setCancelled(true);
			//MessageUtils.sendMessage(e.getPlayer(), "&cThat's not your pet!");

        	MessageUtils.sendRawMessage(e.getPlayer(), BattlePets.getInstance().configManager.messages.getMessage("intraction_other_pet")); 	
			
			return;
		}

		if(targetPet.deul.inDuel) {
			GUI gui = new PetCombatGUI(e.getPlayer(), targetPet);
			gui.open();
		}else {
			GUI gui = new PetGui(e.getPlayer(), targetPet);
			gui.open();	
		}
		e.setCancelled(true);
	}

	@EventHandler
	public void onPetDamaged(EntityDamageByEntityEvent e) {

		if(BattlePets.getInstance().tracker.isPet(e.getEntity())) {			
			if(!BattlePets.getInstance().tracker.getPet(e.getEntity()).inAggro) {
				if(BattlePets.getInstance().tracker.isPet(e.getDamager())) {
					return;
				}
				e.getEntity().setFireTicks(0);
				
				if(e.getDamager() instanceof Player) {
					e.setCancelled(true);	
					return;
				}
				
				if(e.getDamager() instanceof LivingEntity) {
					BattlePets.getInstance().tracker.getPet(e.getEntity()).aggro((LivingEntity) e.getDamager());
					return;
				}
				
				e.setCancelled(true);	
				
			}else {
				e.getEntity().setFireTicks(0);
				
				if(e.getDamager().getType() == EntityType.PUFFERFISH) {
					e.setCancelled(true);
					return;
				}
				
				if(e.getDamager() instanceof LivingEntity) {
					BattlePets.getInstance().tracker.getPet(e.getEntity()).aggro((LivingEntity) e.getDamager());
				}
			}
		}	
		
		
		
		if(e.getEntity() instanceof Player) {
		
			if(!(e.getDamager() instanceof LivingEntity)) return;
			
			LivingEntity target = (LivingEntity)e.getDamager();
			
			if(target instanceof Player) {
				return;
			}
			
			if(BattlePets.getInstance().tracker.isPet(target)) {
				e.setCancelled(true);
				return;
			}
			
			Player player = (Player)e.getEntity();
			
			BattlePets.getInstance().tracker.getActivePets(player.getUniqueId()).forEach(i -> {
				i.aggro(target);
			});
			
		}
		
		if(e.getDamager() instanceof Player) {
			
			if(!(e.getEntity() instanceof LivingEntity)) return;
			
			LivingEntity target = (LivingEntity)e.getEntity();
			
			if(target instanceof Player) {
				return;
			}
			
			if(BattlePets.getInstance().tracker.isPet(target)) {
				e.setCancelled(true);
				return;
			}
			
			Player player = (Player)e.getDamager();
			
			BattlePets.getInstance().tracker.getActivePets(player.getUniqueId()).forEach(i -> {
				i.aggro(target);
			});
			
		}
		
	}
	
	@EventHandler
	public void onPetDamaged(EntityDamageEvent e) {
		if(BattlePets.getInstance().tracker.isPet(e.getEntity())) {
			if(e.getCause() == DamageCause.ENTITY_ATTACK) return;
			if(e.getCause() == DamageCause.ENTITY_EXPLOSION) return;
			if(e.getCause() == DamageCause.PROJECTILE) return;
				e.getEntity().setFireTicks(0);
				e.setCancelled(true);
			}
	}
	
	@EventHandler
	public void onPetDamaged(ProjectileHitEvent e) {
		if(e.getEntity().getShooter() instanceof Player) {
			if(!(e.getHitEntity() instanceof LivingEntity)) return;
			
			LivingEntity target = (LivingEntity)e.getHitEntity();
			
			if(target instanceof Player) {
				return;
			}
			
			if(e.getEntityType() == EntityType.SPLASH_POTION) {
				return;
			}
			
			if(BattlePets.getInstance().tracker.isPet(target)) {
				return;
			}
			
			Player player = (Player)e.getEntity().getShooter();
			
			BattlePets.getInstance().tracker.getActivePets(player.getUniqueId()).forEach(i -> {
				i.aggro(target);
			});
		}
		if(e.getEntity().getShooter() instanceof LivingEntity) {
			
			if(e.getEntity().getShooter() instanceof Player) return;
			
				if(BattlePets.getInstance().tracker.isPet(e.getHitEntity())) {
					BattlePets.getInstance().tracker.getPet(e.getHitEntity()).aggro((LivingEntity)e.getEntity().getShooter());
				}
		
		}
	}

	@EventHandler
    public void onEntityTarget(EntityTargetEvent e){
		
		//System.out.println(e.getEntity().getType().name() + " has targeted " + e.getTarget().getType().name() + " for " + e.getReason().toString());
		
		
		boolean isPet = BattlePets.getInstance().tracker.isPet(e.getEntity());
		boolean isTargetAPet = BattlePets.getInstance().tracker.isPet(e.getTarget());
		
		if(isPet) {
			e.setCancelled(true);
		}
		
		if(isTargetAPet) {
			if(!isPet && e.getReason() == TargetReason.CLOSEST_ENTITY) {
				e.setCancelled(true);
			}
		}
        
    }
	
	@EventHandler
    public void onEntityTarget(EntityTransformEvent e){
		
		boolean isPet = BattlePets.getInstance().tracker.isPet(e.getEntity());
		
		if(isPet) {
			e.setCancelled(true);
		}
        
    }
	
	@EventHandler
    public void onEntityPoseEvent(EntityPoseChangeEvent e){
        
    }

	/*
	@EventHandler
	public void onEntityEvent(EntityEvent e) {
		boolean isPet = BattlePets.getInstance().tracker.isPet(e.getEntity());
		if(isPet) {
			if(e instanceof EntityBreedEvent) ((EntityBreedEvent)e).setCancelled(true);
			if(e instanceof EntityEnterLoveModeEvent) ((EntityEnterLoveModeEvent)e).setCancelled(true);
			if(e instanceof EntityBreakDoorEvent) ((EntityBreakDoorEvent)e).setCancelled(true);
			if(e instanceof EntitySpellCastEvent) ((EntitySpellCastEvent)e).setCancelled(true);
			if(e instanceof EntityDamageByBlockEvent) ((EntityDamageByBlockEvent)e).setCancelled(true);
		}
	}
	*/
	
	@EventHandler
	public void interact(PlayerInteractEvent e) {
	
		if(!(e.getHand() == EquipmentSlot.HAND || e.getHand() == EquipmentSlot.OFF_HAND)) return;
		if(e.getClickedBlock() == null) return;
		
		ItemStack itemInHand = null;
		
		if(e.getHand() == EquipmentSlot.HAND) {
			itemInHand = e.getPlayer().getInventory().getItemInMainHand();
		}else {
			itemInHand = e.getPlayer().getInventory().getItemInOffHand();
		}
		
		if(!itemInHand.hasItemMeta()) return;
		
		boolean valid = BattlePets.getInstance().configManager.eggStats.eggFormatting.validate(itemInHand);
		
		final ItemStack item = itemInHand;
		
		if(valid) {			
			
			if(BattlePets.getInstance().configManager.blacklisted.isBlackListed(e.getPlayer().getWorld())) {
				//MessageUtils.sendMessage(e.getPlayer(), "&cYou can't have pets in this world!");

	        	MessageUtils.sendRawMessage(e.getPlayer(), BattlePets.getInstance().configManager.messages.getMessage("illegal_world_pet_use")); 
				
				e.setCancelled(true);
				return;
			}
			
			if(BattlePets.getInstance().tracker.getActivePets(e.getPlayer().getUniqueId()).size() >= 1) {
				//MessageUtils.sendMessage(e.getPlayer(), "&cYou can't have more then 1 active pet.");

	        	MessageUtils.sendRawMessage(e.getPlayer(), BattlePets.getInstance().configManager.messages.getMessage("pet_spawn_canceled_exists")); 
				
				e.setCancelled(true);
				return;
			}
			
			GenericPetAttribute data = BattlePets.getInstance().configManager.eggStats.eggFormatting.getAttributes(item);
			PlayerLocation playerLocation = new PlayerLocation(e.getPlayer(), e.getClickedBlock().getLocation(), data, e.getHand());
			players.add(playerLocation);
		}
		
		
	}
	
	
}
