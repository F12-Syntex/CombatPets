package com.battlepets.GUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

import com.battlepets.attributes.GenericPetAttribute;
import com.battlepets.config.gui.GuiItem;
import com.battlepets.entities.ActivePetEntity;
import com.battlepets.main.BattlePets;
import com.battlepets.utils.ComponentBuilder;
import com.battlepets.utils.MessageUtils;

public class PointsGUI extends GUI{

	private ActivePetEntity entity;
	
	public Map<Integer, Integer> upgrades = new HashMap<Integer, Integer>();
	public Map<Integer, Integer> used = new HashMap<Integer, Integer>();
	public int totalUsed = 0;
	
	public PointsGUI(Player player, ActivePetEntity entity) {
		super(player);
		// TODO Auto-generated constructor stub
		this.setEntity(entity);
		
		
		upgrades.put(11, 0);
		upgrades.put(12, 0);
		upgrades.put(13, 0);
		upgrades.put(14, 0);
		
		used.put(11, 0);
		used.put(12, 0);
		used.put(13, 0);
		used.put(14, 0);
		
	}

	public PointsGUI() {}
	
	@Override
	public String name() {
		// TODO Auto-generated method stub
		return MessageUtils.translateAlternateColorCodes("&cPet points allocation!");
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
		// TODO Auto-generated method stub
	}

	@Override
	public void onCloseInventory(InventoryCloseEvent e) {
		// TODO Auto-generated method stub
	}

	public ActivePetEntity getEntity() {
		return entity;
	}

	public void setEntity(ActivePetEntity entity) {
		this.entity = entity;
	}
	
	@Override
	public List<GuiItem> configuration() {

		this.inventoryItems.add(new GuiItem(GenerateItem.getItem("&6" + "Skillpoints", Material.GOLD_INGOT, ComponentBuilder.createLore(
				"&cAvailable points: &e%pet_points%")
		), 0, "points:empty"));

		this.inventoryItems.add(new GuiItem(GenerateItem.getItem("&c" + "Vitality", Material.REDSTONE, ComponentBuilder.createLore(
				"&cPoints: &e%pet_points_hp%",
				"&cMax Points: &e%pet_generic_pointallocation_hp%")
		), 2, "points:empty"));
		
		this.inventoryItems.add(new GuiItem(GenerateItem.getItem("&4" + "Strength", Material.DIAMOND_SWORD, ComponentBuilder.createLore(
				"&cPoints: &e%pet_points_damage%",
				"&cMax Points: &e%pet_generic_pointallocation_damage%")
		), 3, "points:empty"));
		
		this.inventoryItems.add(new GuiItem(GenerateItem.getItem("&6" + "Defense", Material.IRON_CHESTPLATE, ComponentBuilder.createLore(
				"&cPoints: &e%pet_points_defense%",
				"&cMax Points: &e%pet_generic_pointallocation_defense%")
		), 4, "points:empty"));
		
		this.inventoryItems.add(new GuiItem(GenerateItem.getItem("&a" + "Dexterity", Material.GOLDEN_BOOTS, ComponentBuilder.createLore(
				"&cPoints: &e%pet_points_speed%",
				"&cMax Points: &e%pet_generic_pointallocation_speed%")
		), 5, "points:empty"));
		
		this.inventoryItems.add(new GuiItem(GenerateItem.getItem("&c" + "Vitality+%vitality_points_added%", Material.EMERALD, ComponentBuilder.createLore(
				"&7Left click to add points to this skill.",
				"&7Right click to undo points from this skill."
		)), 11, "LEFT:VitalityAdd RIGHT:VitalityUndo"));
		
		this.inventoryItems.add(new GuiItem(GenerateItem.getItem("&4" + "Strength+%strength_points_added%", Material.EMERALD, ComponentBuilder.createLore(
				"&7Left click to add points to this skill.",
				"&7Right click to undo points from this skill."
		)), 12, "LEFT:StrengthAdd RIGHT:StrengthUndo"));
		
		this.inventoryItems.add(new GuiItem(GenerateItem.getItem("&6" + "Defense+%defense_points_added%", Material.EMERALD, ComponentBuilder.createLore(
				"&7Left click to add points to this skill.",
				"&7Right click to undo points from this skill."
		)), 13, "LEFT:DefenseAdd RIGHT:DefenseUndo"));
		
		this.inventoryItems.add(new GuiItem(GenerateItem.getItem("&a" + "Dexterity+%dexterity_points_added%", Material.EMERALD, ComponentBuilder.createLore(
				"&7Left click to add points to this skill.",
				"&7Right click to undo points from this skill."
		)), 14, "LEFT:DexterityAdd RIGHT:DexterityUndo"));
		
		this.inventoryItems.add(new GuiItem(GenerateItem.getItem("&7" + "Return", Material.EMERALD, ComponentBuilder.createLore(
				"&8Discard changes."
		)), 8, "points:move:petgui"));
		
		this.inventoryItems.add(new GuiItem(GenerateItem.getItem("&a" + "Confirm", Material.DIAMOND, ComponentBuilder.createLore(
				"&aSave the changes you've made",
				"&c[&4WARNING&c] You cannot undo this action."
		)), 17, "points:save"));
		
		
		return this.inventoryItems;
	}

	@Override
	public Map<String, Runnable> executables() {
		
		this.executables.put("LEFT:VitalityAdd", () -> {
			int current = this.upgrades.get(11);
			
			if(this.totalUsed >= this.entity.getAttribute().getPoints()) return;
			
			if((current + this.entity.getAttribute().getPointsAllocation().getHp()) >= this.entity.getAttribute().getGeneric().getSkillPoints().getMaxAllocation().getHp()) return;
			
			current++;
			totalUsed++;
			
			this.used.put(11, (this.used.get(11)+1));
			this.upgrades.put(11, current);
			
			this.Contents(inv);
		});
		
		this.executables.put("RIGHT:VitalityUndo", () -> {
			int current = this.upgrades.get(11);
			if(used.get(11) <= 0) return;
			current--;
			used.put(11, (used.get(11)-1));
			totalUsed--;
			this.upgrades.put(11, current);
			this.Contents(inv);
		});
		
		
		this.executables.put("LEFT:StrengthAdd", () -> {
			int current = this.upgrades.get(12);
			if(this.totalUsed >= this.entity.getAttribute().getPoints()) return;
			if((current + this.entity.getAttribute().getPointsAllocation().getDamage()) >= this.entity.getAttribute().getGeneric().getSkillPoints().getMaxAllocation().getDamage()) return;
			current++;
			totalUsed++;
			used.put(12, (used.get(12)+1));

			this.upgrades.put(12, current);
			this.Contents(inv);
		});
		
		this.executables.put("RIGHT:StrengthUndo", () -> {
			int current = this.upgrades.get(12);
			if(used.get(12) <= 0) return;
			current--;
			used.put(12, (used.get(12)-1));
			totalUsed--;

			this.upgrades.put(12, current);
			this.Contents(inv);
		});
		

		this.executables.put("LEFT:DefenseAdd", () -> {
			int current = this.upgrades.get(13);
			if(this.totalUsed >= this.entity.getAttribute().getPoints()) return;
			if((current + this.entity.getAttribute().getPointsAllocation().getDefense()) >= this.entity.getAttribute().getGeneric().getSkillPoints().getMaxAllocation().getDefense()) return;
			current++;	
			totalUsed++;
			used.put(13, (used.get(13)+1));

			this.upgrades.put(13, current);
			this.Contents(inv);
		});
		
		this.executables.put("RIGHT:DefenseUndo", () -> {
			int current = this.upgrades.get(13);
			if(used.get(13) <= 0) return;
			current--;
			used.put(13, (used.get(13)-1));
			totalUsed--;

			this.upgrades.put(13, current);
			this.Contents(inv);
		});
		
		this.executables.put("LEFT:DexterityAdd", () -> {
			int current = this.upgrades.get(14);
			if(this.totalUsed >= this.entity.getAttribute().getPoints()) return;
			if((current + this.entity.getAttribute().getPointsAllocation().getSpeed()) >= this.entity.getAttribute().getGeneric().getSkillPoints().getMaxAllocation().getSpeed()) return;
			current++;
			totalUsed++;
			used.put(14, (used.get(14)+1));
			
			this.upgrades.put(14, current);
			this.Contents(inv);
		});
		
		this.executables.put("RIGHT:DexterityUndo", () -> {
			int current = this.upgrades.get(14);
			if(this.totalUsed >= this.entity.getAttribute().getPoints()) return;
			if((current + this.entity.getAttribute().getPointsAllocation().getSpeed()) >= this.entity.getAttribute().getGeneric().getSkillPoints().getMaxAllocation().getSpeed()) return;
			current++;
			totalUsed++;
			used.put(14, (used.get(14)+1));
			
			this.upgrades.put(14, current);
			this.Contents(inv);
		});
		
		this.executables.put("points:move:petgui", () -> {
			player.closeInventory();
			GUI gui = new PetGui(player, entity);
			gui.open();
		});
		
		this.executables.put("points:save", () -> {
			this.getEntity().getAttribute().getPointsAllocation().hp += this.upgrades.get(11);
			this.getEntity().getAttribute().getPointsAllocation().damage += this.upgrades.get(12);
			this.getEntity().getAttribute().getPointsAllocation().defense += this.upgrades.get(13);
			this.getEntity().getAttribute().getPointsAllocation().speed += this.upgrades.get(14);
			this.getEntity().getAttribute().setPoints((this.getEntity().getAttribute().getPoints() - this.totalUsed));
			this.getEntity().updateAttributes();
			//MessageUtils.sendMessage(player, "&aSkills successfully saved.");
			BattlePets.getInstance().configManager.messages.send(player, "pet_skills_saved");	
			player.closeInventory();
		});
		
		this.executables.put("points:empty", () ->{});
		
		return this.executables;
	}

	@Override
	public String config() {
		// TODO Auto-generated method stub
		return "points";
	}
	
	@Override
	public Map<String, Object> placeholders() {
		
		GenericPetAttribute attributes = this.getEntity().getAttribute();
		
		this.placeholders.put("%pet_points%", attributes.getPoints());
		
		this.placeholders.put("%pet_points_hp%", (int)attributes.getPointsAllocation().getHp());
		this.placeholders.put("%pet_generic_pointallocation_hp%", (int)attributes.getGeneric().getSkillPoints().getMaxAllocation().getHp());
		
		this.placeholders.put("%pet_points_damage%", (int)attributes.getPointsAllocation().getDamage());
		this.placeholders.put("%pet_generic_pointallocation_damage%", (int)attributes.getGeneric().getSkillPoints().getMaxAllocation().getDamage());
		
		this.placeholders.put("%pet_points_defense%", (int)attributes.getPointsAllocation().getDefense());
		this.placeholders.put("%pet_generic_pointallocation_defense%", (int)attributes.getGeneric().getSkillPoints().getMaxAllocation().getDefense());

		this.placeholders.put("%pet_points_speed%", (int)attributes.getPointsAllocation().getSpeed());
		this.placeholders.put("%pet_generic_pointallocation_speed%", (int)attributes.getGeneric().getSkillPoints().getMaxAllocation().getSpeed());
		
		this.placeholders.put("%vitality_points_added%", this.upgrades.get(11));
		this.placeholders.put("%strength_points_added%", this.upgrades.get(12));
		this.placeholders.put("%defense_points_added%", this.upgrades.get(13));
		this.placeholders.put("%dexterity_points_added%", this.upgrades.get(14));
		return this.placeholders;
	}
	
	@Override
	public List<GuiItem> postConfiguration() {
		List<GuiItem> item = new ArrayList<GuiItem>();
		return item;
	}

}
