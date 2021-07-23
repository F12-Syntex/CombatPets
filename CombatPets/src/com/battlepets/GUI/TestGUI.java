package com.battlepets.GUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

import com.battlepets.config.gui.GuiItem;
import com.battlepets.config.gui.Page;
import com.battlepets.main.BattlePets;

public class TestGUI extends PagedGeneratedGUI{

	private int test;
	
	public TestGUI(Player player) {
		super(player);
	}
	
	public TestGUI() {}
	
	public PagedGeneratedGUI data() {
		return (PagedGeneratedGUI)this;
	}

	@Override
	public String name() {
		return "test";
	}

	@Override
	public String permission() {
		return BattlePets.getInstance().configManager.permissions.basic;
	}

	@Override
	public int size() {
		return 54;
	}

	@Override
	public float soundLevel() {
		return 0f;
	}

	@Override
	public boolean canTakeItems() {
		return false;
	}

	@Override
	public void onOpenInventory(InventoryOpenEvent e) {}
	
	@Override
	public void onCloseInventory(InventoryCloseEvent e) {}

	@Override
	public String config() {
		return "test";
	}

	@Override
	public List<GuiItem> configuration() {
		this.defaultPagedItem();
		
		
		for(int i = 0; i < 9; i++) {
			for(int x = 0; x < 9; x++) {
				
				GuiItem item = GuiItem.constructPagedItem(GenerateItem.getItem("&6PAGE: &7" + i, Material.PAPER), x+"", "", new Page(i, false));
				
				this.inventoryItems.add(item);
				
			}
		}
		
		return this.inventoryItems;
	}

	@Override
	public Map<String, Runnable> executables() {
		this.defaultExecution();
		return this.executables;
	}

	@Override
	public Map<String, Object> placeholders() {
		this.placeholders.put("%test%", test);
		return this.placeholders;
	}
	
	@Override
	public List<GuiItem> postConfiguration() {
		List<GuiItem> item = new ArrayList<GuiItem>();
		return item;
	}

}
