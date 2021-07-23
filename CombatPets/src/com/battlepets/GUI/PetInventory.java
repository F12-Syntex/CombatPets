package com.battlepets.GUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;

import com.battlepets.config.gui.GuiItem;
import com.battlepets.eggmeta.EggStorage;
import com.battlepets.entities.ActivePetEntity;
import com.battlepets.main.BattlePets;
import com.battlepets.utils.MessageUtils;

public class PetInventory extends PagedGeneratedGUI{

	private ActivePetEntity entity;
	
	public PetInventory(Player player, ActivePetEntity entity) {
		super(player);
		// TODO Auto-generated constructor stub
		this.entity = entity;
	}

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return MessageUtils.translateAlternateColorCodes("&5Inventory.");
	}

	@Override
	public String permission() {
		// TODO Auto-generated method stub
		return BattlePets.getInstance().configManager.permissions.basic;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 54;
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
	public String config() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onOpenInventory(InventoryOpenEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCloseInventory(InventoryCloseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<GuiItem> configuration() {
		return new ArrayList<GuiItem>();
	}

	@Override
	public List<GuiItem> postConfiguration() {
		
		this.executables.clear();
		EggStorage storage = this.entity.getAttribute().getStorage();
		
		int index = 0;
		
		this.executables = this.defaultExecution();
		
		for(ItemStack i : storage.getInventory()) {
			
			int current_page = Math.round(index/36) + 1;
			int ID = index - ((current_page - 1) * 36);
			
			this.post_items.add(new GuiItem(i, ID, "pet:" + ID + ":" + current_page));
			this.executables.put("pet:" + ID + ":" + current_page, () -> {
				player.sendMessage(i.getItemMeta().getDisplayName());
			});
			
			index++;
		}
		
		return post_items;
		
	}

	@Override
	public Map<String, Runnable> executables() {
		// TODO Auto-generated method stub
		return this.executables;
	}

	@Override
	public Map<String, Object> placeholders() {
		// TODO Auto-generated method stub
		return null;
	}


	/*
	@Override
	public List<PagedItem> Contents() {
		
		List<PagedItem> items = new ArrayList<PagedItem>();
		
		EggStorage storage = this.entity.getAttribute().getStorage();
		
		for(ItemStack i : storage.getInventory()) {
			
			PagedItem pagedItem = new PagedItem(i, () -> {
				
			});
			
			items.add(pagedItem);
			
		}
		
		return items;
	}

	@Override
	public List<SpecialItem> SpecialContents() {

		List<SpecialItem> contents = new ArrayList<SpecialItem>();
		
		ItemStack inventory = GenerateItem.getItem("&6Back", Material.BLUE_STAINED_GLASS_PANE, "&3Takes you to back to the pet menu.");
		
		SpecialItem items = new SpecialItem(inventory, () -> {
			player.closeInventory();
			GUI inv = new PetGui(player, entity);
			inv.open();
		}, 46);
		
		contents.add(items);
		
		return contents;
	}
*/
	
}
