package com.battlepets.GUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

import com.battlepets.attributes.GenericPetAttribute;
import com.battlepets.battle.Request;
import com.battlepets.config.gui.GuiItem;
import com.battlepets.entities.ActivePetEntity;
import com.battlepets.main.BattlePets;
import com.battlepets.utils.Communication;
import com.battlepets.utils.ComponentBuilder;
import com.battlepets.utils.Input;
import com.battlepets.utils.MessageUtils;
import com.battlepets.utils.StringMinipulation;

public class PetGui extends GUI{

	private ActivePetEntity entity;
	GenericPetAttribute attributes;
	private int ID = 0;
	
	public PetGui(Player player, ActivePetEntity entity) {
		super(player);
		// TODO Auto-generated constructor stub
		this.setEntity(entity);
	
		ID = BattlePets.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(BattlePets.getInstance(), () -> {
			this.Contents(this.inv);
		}, 20L, 20L);
		this.attributes = entity.getAttribute();
	
	}
	
	public PetGui() {}

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
		return 18;
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
		if(this.entity.entity.isDead()) {
			player.closeInventory();
		}
	}

	@Override
	public void onCloseInventory(InventoryCloseEvent e) {
		BattlePets.getInstance().getServer().getScheduler().cancelTask(this.ID);
	}

	public ActivePetEntity getEntity() {
		return entity;
	}

	public void setEntity(ActivePetEntity entity) {
		this.entity = entity;
	}
	
	@Override
	public List<GuiItem> configuration() {
		
		this.inventoryItems.add(new GuiItem(GenerateItem.getItem("&6%pet_name%", Material.DIAMOND, ComponentBuilder.createLore(
				"&cType: &6%pet_type%",
				"&cLevel: &6%pet_level%",
				"&cXP: &6%pet_xp%&7/&6%pet_maxxp%",
				"&aClick me to rename!"	
		)), 0, config() + ":empty"));
		
		this.inventoryItems.add(new GuiItem(GenerateItem.getItem("&6Stats", Material.GOLD_INGOT, ComponentBuilder.createLore(
				"&cHP: &7%pet_stats_health%&7/&7%pet_stats_maxhealth%",
				"&cStrength: &6%pet_basestats_damage%&7+&6%pet_stats_damage%",
				"&cDefense: &6%pet_basestats_defense%&7+&6%pet_stats_defense%",
				"&cDexterity: &6%pet_basestats_speed%&7+&6%pet_stats_speed%"
		)), 1, config() + ":empty"));
		
		this.inventoryItems.add(new GuiItem(GenerateItem.getItem("&6Points", Material.ANVIL, ComponentBuilder.createLore(
				"&cSkillpoints: &6%pet_points%",
				"&cVitality: &6%pet_points_hp%",
				"&cStrength: &6%pet_points_damage%",
				"&cDefense: &6%pet_points_defense%",
				"&cDexterity: &6%pet_points_speed%",
				"&aClick to manage points!"
		)), 2, config() + ":move:points"));
		
		this.inventoryItems.add(new GuiItem(GenerateItem.getItem("&5Boss bar", Material.DRAGON_HEAD, ComponentBuilder.createLore(
				"&aClick to toggle the bossbar"
		)), 3, config() + ":barToggle"));
		
		this.inventoryItems.add(new GuiItem(GenerateItem.getItem("&6Ride", Material.SADDLE, ComponentBuilder.createLore(
				"&cRide your pet!"
		)), 9, config() + ":ride") {
					
			@Override
			public boolean show() {
				
				if(entity.getAttribute().getGeneric().getRiding().ridable) {
					if(entity.getAttribute().getLevel() >= entity.getAttribute().getGeneric().getRiding().requiredLevel) {
						return true;
					}
				}
				
				return false;
			}
			
		});	
		
		
		this.inventoryItems.add(new GuiItem(GenerateItem.getItem("&6Duel", Material.DIAMOND_SWORD, ComponentBuilder.createLore(
				"&6Request a duel with someone!"
		)), 10, config() + ":request"));
	
		this.inventoryItems.add(new GuiItem(GenerateItem.getItem("&6" + "Healing", Material.GOLDEN_APPLE, ComponentBuilder.createLore(
				"&cHeal your pet."
		)), 8, config() + ":move:shop"));
		
		Material spawnEgg = Material.WOLF_SPAWN_EGG;
		
		try {
			spawnEgg = Material.valueOf(this.placeholders.get("%entity_type%") + "_SPAWN_EGG");
		}catch (Exception e) {
			
		}
		
		
		this.inventoryItems.add(new GuiItem(GenerateItem.getItem("&7Return", spawnEgg, ComponentBuilder.createLore(
				"&aReturn your pet to your inventory."
		)), 17, config() + ":return"));
		
		return this.inventoryItems;
	}

	@Override
	public Map<String, Runnable> executables() {
		
		this.executables.put(config() + ":rename", () -> {
			
			player.closeInventory();
			//MessageUtils.sendMessage(player, "&aType in the name of your pet. &7( &c\"cancel\"&a to cancel this action &7)");
			BattlePets.getInstance().configManager.messages.send(player, "pet_rename_message");
			
			
			Communication.applyInput(player, new Input() {
				
				@Override
				public void onRecieve(String i) {
					BattlePets.getInstance().getServer().getScheduler().runTask(BattlePets.getInstance(), () -> {
						
					    String defaultName = BattlePets.getInstance().configManager.pets.compulsoryName.replace("%level%", ""+entity.getAttribute().getLevel());
						
					    if(i.isEmpty()) {
							entity.getAttribute().setName(MessageUtils.translateAlternateColorCodes(defaultName.trim()));
							entity.entity.setCustomName(MessageUtils.translateAlternateColorCodes(defaultName.trim()));
							entity.updateAttributes();
							
							PetGui gui = new PetGui(player, entity);
							gui.open();	
							return;
					    }
					    
						if(i.toLowerCase().trim().equalsIgnoreCase("cancel")) {
							//MessageUtils.sendMessage(player, "&aNaming action has been canceled.");
							BattlePets.getInstance().configManager.messages.send(player, "pet_rename_cancenled");
							PetGui gui = new PetGui(player, entity);
							gui.open();	
							return;
						}
						
						if(i.length() > BattlePets.getInstance().configManager.settings.maxRenameLength) {
							//MessageUtils.sendMessage(player, "&cName length must not be over " + BattlePets.getInstance().configManager.settings.maxRenameLength + "!");
							BattlePets.getInstance().configManager.messages.send(player, "pet_rename_error_length");
							return;
						}
						
						entity.getAttribute().setName(MessageUtils.translateAlternateColorCodes(i.trim()));
						entity.entity.setCustomName(MessageUtils.translateAlternateColorCodes(i.trim()));
						entity.updateAttributes();
						
						PetGui gui = new PetGui(player, entity);
						gui.open();		
					});
				}
			}, 60000);
		});
		
		this.executables.put(config() + ":empty", () -> {
			player.closeInventory();
			PetGui gui = new PetGui(player, entity);
			gui.open();	
		});
		
		this.executables.put(config() + ":move:points", () -> {
			player.closeInventory();
			PointsGUI gui = new PointsGUI(player, entity);
			gui.open();				
		});
		
		this.executables.put(config() + ":barToggle", () -> {
			entity.bar.setVisible(!entity.bar.isVisible());
		});
		
		this.executables.put(config() + ":move:shop", () -> {
			this.player.closeInventory();
			PetShopItems gui = new PetShopItems(player, BattlePets.getInstance().configManager.petshop.healing, this.entity);
			gui.open();
		});
		
		this.executables.put(config() + ":request", () -> {
			BattlePets.getInstance().configManager.messages.send(player, "pet_challenge");
			Request request = new Request(this.player.getUniqueId(), entity);
			
			BattlePets.getInstance().battleRequests.addRequest(request);
			
			player.closeInventory();
			
		});
		
		this.executables.put(config() + ":return", () -> {
			this.entity.getAttribute().setHealth(this.entity.getEntity().getHealth());
			this.entity.returnToInventory(false);
			
			BattlePets.getInstance().configManager.messages.send(player, "pet_return_inventory");
			
			player.closeInventory();
			return;
		});
		
		this.executables.put(config() + ":ride", () -> {
			if(this.entity.getAttribute().getGeneric().getRiding().ridable) {
				if(this.entity.getAttribute().getLevel() >= this.entity.getAttribute().getGeneric().getRiding().requiredLevel) {
					this.entity.getEntity().addPassenger(player);
				}	
			}
		});

		return this.executables;
	}

	@Override
	public String config() {
		// TODO Auto-generated method stub
		return "pets";
	}

	@Override
	public Map<String, Object> placeholders() {
		
		this.placeholders.put("%pet_name%", attributes.getName());
		
		this.placeholders.put("%pet_type%", StringMinipulation.capitalize(attributes.getEntityType().name().toLowerCase()));
		this.placeholders.put("%pet_level%", attributes.getLevel());
		this.placeholders.put("%pet_xp%", attributes.getXp());
		this.placeholders.put("%pet_maxxp%", attributes.getMaxXP());
		this.placeholders.put("%pet_stats_health%", Math.round(entity.getEntity().getHealth()*10.0)/10.0);
		this.placeholders.put("%pet_stats_maxhealth%", entity.getEntity().getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
		this.placeholders.put("%pet_basestats_damage%", attributes.getPet().getBaseStats().getDamage());
		this.placeholders.put("%pet_stats_damage%", attributes.getPointsAllocation().getDamage() * attributes.getPet().getSkillPoints().getAdditionPerStat().getDamage());
		this.placeholders.put("%pet_basestats_defense%", attributes.getPet().getBaseStats().getDefense());
		this.placeholders.put("%pet_stats_defense%", attributes.getPointsAllocation().getDefense() * attributes.getPet().getSkillPoints().getAdditionPerStat().getDefense());
		this.placeholders.put("%pet_basestats_speed%", attributes.getPet().getBaseStats().getSpeed());
		this.placeholders.put("%pet_stats_speed%", attributes.getPointsAllocation().getSpeed() * attributes.getPet().getSkillPoints().getAdditionPerStat().getSpeed());
		
		this.placeholders.put("%pet_points%", attributes.getPoints());
		this.placeholders.put("%pet_points_hp%", (int)attributes.getPointsAllocation().getHp());
		this.placeholders.put("%pet_points_damage%",(int)attributes.getPointsAllocation().getDamage());
		this.placeholders.put("%pet_points_defense%", (int)attributes.getPointsAllocation().getDefense());
		this.placeholders.put("%pet_points_speed%", (int)attributes.getPointsAllocation().getSpeed());
		
		this.placeholders.put("%attribute%", this.entity);
		
		this.placeholders.put("%entity_type%", entity.getEntity().getType().name());
		
		
		return this.placeholders;
	}
	
	@Override
	public List<GuiItem> postConfiguration() {
		List<GuiItem> item = new ArrayList<GuiItem>();
		return item;
	}



}
