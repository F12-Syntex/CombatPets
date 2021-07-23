package com.battlepets.GUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

import com.battlepets.attributes.GenericPetAttribute;
import com.battlepets.config.gui.GuiItem;
import com.battlepets.entities.ActivePetEntity;
import com.battlepets.main.BattlePets;
import com.battlepets.utils.ComponentBuilder;
import com.battlepets.utils.MessageUtils;
import com.battlepets.utils.StringMinipulation;

public class PetCombatGUI extends GUI{

	private ActivePetEntity pet;
	private int ID;
	
	GenericPetAttribute attribute;
	
	
	public PetCombatGUI(Player player, ActivePetEntity entity) {
		super(player);
		// TODO Auto-generated constructor stub
		this.setEntity(entity);
		
		ID = BattlePets.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(BattlePets.getInstance(), () -> {
			this.Contents(this.inv);
		}, 20L, 20L);
		
		this.attribute = this.getEntity().getAttribute();
		
	}
	
	public PetCombatGUI() {}

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return MessageUtils.translateAlternateColorCodes("&cPet stats!");
	}

	@Override
	public String permission() {
		// TODO Auto-generated method stub
		return BattlePets.getInstance().configManager.permissions.basic;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 9;
	}

	@Override
	public float soundLevel() {
		// TODO Auto-generated method stub
		return 0f;
	}

	@Override
	public boolean canTakeItems() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onOpenInventory(InventoryOpenEvent e) {
		if(this.pet.entity.isDead()) {
			player.closeInventory();
		}
	}

	@Override
	public void onCloseInventory(InventoryCloseEvent e) {
		BattlePets.getInstance().getServer().getScheduler().cancelTask(this.ID);
	}

	public ActivePetEntity getEntity() {
		return pet;
	}

	public void setEntity(ActivePetEntity entity) {
		this.pet = entity;
	}
	
	@Override
	public List<GuiItem> configuration() {
		
		this.inventoryItems.add(new GuiItem(GenerateItem.getItem("&6%pet_name%", Material.DIAMOND, ComponentBuilder.createLore(
				"&cType: &6%pet_type%",
				"&cLevel: &6%pet_level%",
				"&cXP: &6%pet_xp%&7/&6%pet_maxxp%"
		)), 0, config() + ":empty"));
		
		this.inventoryItems.add(new GuiItem(GenerateItem.getItem("&6Stats", Material.GOLD_INGOT, ComponentBuilder.createLore(
				"&cHP: &7%pets_stats_health%&7/&7%pet_stats_maxhealth%",
				"&cStrength: &6%pet_basestats_damage% &7+%pet_stats_damage%",
				"&cDefense: &6%pet_basestats_damage% &7+%pet_stats_damage%",
				"&cDexterity: &6%pet_basestats_damage% &7+%pet_stats_damage%"
		)), 1, config() + ":empty"));
		
		this.inventoryItems.add(new GuiItem(GenerateItem.getItem("&6Ride", Material.SADDLE, ComponentBuilder.createLore(
				"&cRide your pet!"
		)), 8, config() + ":ride") {
					
			@Override
			public boolean show() {
				
				if(pet.getAttribute().getGeneric().getRiding().ridable) {
					if(pet.getAttribute().getLevel() >= pet.getAttribute().getGeneric().getRiding().requiredLevel) {
						return true;
					}
				}
				
				return false;
			}
			
		});	
		
		this.inventoryItems.add(new GuiItem(GenerateItem.getItem("&4Surrender", Material.BARRIER, ComponentBuilder.createLore(
				"&cStops the current fight."
		)), 7, config() + ":stopFight"));
		
		return this.inventoryItems;
	}

	@Override
	public Map<String, Runnable> executables() {
		
		ActivePetEntity entity = this.pet;
		
		this.executables.put(this.config() + ":ride", () -> {
			if(entity.getAttribute().getGeneric().getRiding().ridable) {
				if(entity.getAttribute().getLevel() >= entity.getAttribute().getGeneric().getRiding().requiredLevel) {
					entity.getEntity().addPassenger(player);
				}	
			}
		});
		
		this.executables.put(this.config() + ":stopFight", () -> {
			ActivePetEntity target = entity.deul.getTarget();
			
			MessageUtils.sendMessage(Bukkit.getPlayer(target.getOwner()), "&c" + Bukkit.getPlayer(entity.getOwner()).getDisplayName() + " has surrendered.");
			MessageUtils.sendMessage(Bukkit.getPlayer(entity.getOwner()), "&cYou have surredered.");
			
			Player targetPlayer = Bukkit.getPlayer(target.getOwner());
			Player player = Bukkit.getPlayer(entity.getOwner());
			
			MessageUtils.sendRawMessage(targetPlayer, BattlePets.getInstance().configManager.messages.getMessage("duel_surrendered_target").replace("%target%", targetPlayer.getDisplayName()).replace("%player%", player.getDisplayName()));
			MessageUtils.sendRawMessage(player, BattlePets.getInstance().configManager.messages.getMessage("duel_surrendered_user").replace("%target%", targetPlayer.getDisplayName()).replace("%player%", player.getDisplayName()));
			
			
			target.stopFighting();
			entity.stopFighting();
			
			target.follow(Bukkit.getPlayer(target.getOwner()));
			entity.follow(Bukkit.getPlayer(entity.getOwner()));
		});
		
		return this.executables;
	}

	@Override
	public String config() {
		// TODO Auto-generated method stub
		return "combat";
	}

	@Override
	public Map<String, Object> placeholders() {
		
		
		this.placeholders.put("%attribute%", this.attribute);
		this.placeholders.put("%pet%", this.pet);
		
		this.placeholders.put("%pet_name%", attribute.getName());
		this.placeholders.put("%pet_type%", StringMinipulation.capitalize(attribute.getEntityType().name().toLowerCase()));
		this.placeholders.put("%pet_level%", StringMinipulation.capitalize(attribute.getEntityType().name().toLowerCase()));
		this.placeholders.put("%pet_xp%", attribute.getXp());
		this.placeholders.put("%pet_maxxp%", attribute.getMaxXP());
		
		this.placeholders.put("%pet_stats_health%", Math.round(pet.getEntity().getHealth()*10.0)/10.0);
		this.placeholders.put("%pet_stats_maxhealth%", pet.getEntity().getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
		
		this.placeholders.put("%pet_basestats_damage%", attribute.getPet().getBaseStats().getDamage());
		this.placeholders.put("%pet_stats_damage%", attribute.getPointsAllocation().getDamage() * attribute.getPet().getSkillPoints().getAdditionPerStat().getDamage());
		this.placeholders.put("%pet_basestats_defense%", attribute.getPet().getBaseStats().getDefense());
		this.placeholders.put("%pet_stats_defense%", attribute.getPointsAllocation().getDefense() * attribute.getPet().getSkillPoints().getAdditionPerStat().getDefense());
		this.placeholders.put("%pet_basestats_speed%", attribute.getPet().getBaseStats().getSpeed());
		this.placeholders.put("%pet_stats_speed%", attribute.getPointsAllocation().getSpeed() * attribute.getPet().getSkillPoints().getAdditionPerStat().getSpeed());
		
		return this.placeholders;
	}
	
	@Override
	public List<GuiItem> postConfiguration() {
		List<GuiItem> item = new ArrayList<GuiItem>();
		return item;
	}

}
