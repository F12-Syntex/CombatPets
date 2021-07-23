package com.battlepets.GUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

import com.battlepets.config.gui.GuiItem;
import com.battlepets.entities.ActivePetEntity;
import com.battlepets.main.BattlePets;
import com.battlepets.utils.ComponentBuilder;
import com.battlepets.utils.MessageUtils;

public class PetShopGui extends GUI{

	private ActivePetEntity entity;
	
	public PetShopGui(Player player, ActivePetEntity entity) {
		super(player);
		// TODO Auto-generated constructor stub
		this.setEntity(entity);
	}
	
	public PetShopGui() {}

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return MessageUtils.translateAlternateColorCodes("&cPet shop!");
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

		this.inventoryItems.add(new GuiItem(GenerateItem.getItem("&6" + "Healing", Material.GOLDEN_APPLE, ComponentBuilder.createLore(
				"&cHeal your pet."
		)), 0, this.config() + ":heal"));

		
		this.inventoryItems.add(new GuiItem(GenerateItem.getItem("&7" + "Return", Material.EMERALD, ComponentBuilder.createLore(
				"&8Go back to the main menu."
		)), 13, this.config() + ":back"));
		
		
		return this.inventoryItems;
	}

	@Override
	public Map<String, Runnable> executables() {
		
		this.executables.put(this.config() + ":heal", () -> {
			this.player.closeInventory();
			PetShopItems gui = new PetShopItems(player, BattlePets.getInstance().configManager.petshop.healing, this.entity);
			gui.open();
		});
		
		this.executables.put(this.config() + ":back", () -> {
			player.closeInventory();
			GUI gui = new PetGui(player, entity);
			gui.open();
		});
		
		return this.executables;
	}

	@Override
	public String config() {
		// TODO Auto-generated method stub
		return "shop";
	}

	@Override
	public Map<String, Object> placeholders() {
		// TODO Auto-generated method stub
		return this.placeholders;
	}
	
	@Override
	public List<GuiItem> postConfiguration() {
		List<GuiItem> item = new ArrayList<GuiItem>();
		return item;
	}

}
