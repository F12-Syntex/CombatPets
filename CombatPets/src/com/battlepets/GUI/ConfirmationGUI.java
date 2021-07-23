package com.battlepets.GUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.battlepets.config.gui.GuiItem;
import com.battlepets.main.BattlePets;
import com.battlepets.utils.ComponentBuilder;
import com.battlepets.utils.MessageUtils;

public class ConfirmationGUI extends GUI{

	private Runnable execution;
	
	public ConfirmationGUI(Player player, Runnable execution) {
		super(player);
		this.execution = execution;
	}
	
	public ConfirmationGUI() {};

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return MessageUtils.translateAlternateColorCodes("&aConfirmation.");
	}

	@Override
	public String permission() {
		// TODO Auto-generated method stub
		return BattlePets.getInstance().configManager.permissions.inventory;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 27;
	}

	@Override
	public float soundLevel() {
		// TODO Auto-generated method stub
		return 0;
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

	public Runnable getExecution() {
		return execution;
	}

	public void setExecution(Runnable execution) {
		this.execution = execution;
	}

	@Override
	public String config() {
		// TODO Auto-generated method stub
		return "confirmation";
	}

	@Override
	public List<GuiItem> configuration() {
		
		ItemStack Confirmation = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
		ItemMeta ConfirmationMeta = Confirmation.getItemMeta();
		ConfirmationMeta.setDisplayName(MessageUtils.translateAlternateColorCodes("&aConfirm."));
		ConfirmationMeta.setLore(ComponentBuilder.createLore("&aClick here to confirm."));
		Confirmation.setItemMeta(ConfirmationMeta);
		
		this.inventoryItems.add(new GuiItem(Confirmation, 13, "confirmation:execute"));
		
		ItemStack stack = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(MessageUtils.translateAlternateColorCodes("&cX"));
		stack.setItemMeta(meta);
		
		this.fillEmpty(stack);
		
		return this.inventoryItems;
	}
	
	@Override
	public Map<String, Runnable> executables() {
		
		this.executables.put("confirmation:execute", () -> {
			player.closeInventory();
			this.execution.run();
		});
		
		return this.executables;
	}

	@Override
	public Map<String, Object> placeholders() {
		return this.placeholders;
	}

	@Override
	public List<GuiItem> postConfiguration() {
		List<GuiItem> item = new ArrayList<GuiItem>();
		return item;
	}

}
