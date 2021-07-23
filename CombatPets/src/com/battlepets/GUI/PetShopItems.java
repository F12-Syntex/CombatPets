package com.battlepets.GUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.battlepets.config.Messages;
import com.battlepets.config.gui.GuiItem;
import com.battlepets.entities.ActivePetEntity;
import com.battlepets.main.BattlePets;
import com.battlepets.shopdata.PetShopItem;
import com.battlepets.utils.ComponentBuilder;
import com.battlepets.utils.MessageUtils;

import net.milkbowl.vault.economy.Economy;

public class PetShopItems extends GUI{

	private List<PetShopItem> items;
	private ActivePetEntity entity;
	
	public PetShopItems(Player player, List<PetShopItem> healing, ActivePetEntity entity) {
		super(player);
		// TODO Auto-generated constructor stub
		this.setItems(healing);
		this.entity = entity;
	}
	
	public PetShopItems() {}

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return MessageUtils.translateAlternateColorCodes("&cShop");
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

	public List<PetShopItem> getItems() {
		return items;
	}

	public void setItems(List<PetShopItem> items) {
		this.items = items;
	}

	@Override
	public String config() {
		// TODO Auto-generated method stub
		return "healing_shop";
	}

	@Override
	public List<GuiItem> configuration() {
		
		int index = 0;
		
		for(PetShopItem shopItem : BattlePets.getInstance().configManager.petshop.healing) {
			
			ItemStack stack = new ItemStack(shopItem.getMaterial());
			
			ItemMeta meta = stack.getItemMeta();
			
			meta.setDisplayName(MessageUtils.translateAlternateColorCodes(shopItem.getName()));
			meta.setLore(ComponentBuilder.createLore(shopItem.getLore()));
			
			stack.setItemMeta(meta);
			
			GuiItem pagedItem = new GuiItem(stack, index, "healing:" + index);
			
			index++;
			this.inventoryItems.add(pagedItem);
			
		}
		
		return this.inventoryItems;
	}

	@Override
	public List<GuiItem> postConfiguration() {
		// TODO Auto-generated method stub
		return new ArrayList<GuiItem>();
	}

	@Override
	public Map<String, Runnable> executables() {
		// TODO Auto-generated method stub
		int index = 0;
		
		for(PetShopItem shopItem : BattlePets.getInstance().configManager.petshop.healing) {
			
			ItemStack stack = new ItemStack(shopItem.getMaterial());
			
			ItemMeta meta = stack.getItemMeta();
			
			meta.setDisplayName(MessageUtils.translateAlternateColorCodes(shopItem.getName()));
			meta.setLore(ComponentBuilder.createLore(shopItem.getLore()));
			
			stack.setItemMeta(meta);
			
			Messages messages = BattlePets.getInstance().configManager.messages;
			
			Economy economy = BattlePets.getInstance().econ;
			
			index++;
			
			this.executables.put("healing:" + index, () -> {
				
				double balance = economy.getBalance(this.player);
				
				double health = entity.getEntity().getHealth();
				double maxHealth = entity.getEntity().getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
				
				if(health == maxHealth) {
					BattlePets.getInstance().configManager.messages.send(player, "pet_heal_error_maxhealth");
					return;
				}
				
				
				if(balance < shopItem.getCost()) {
					MessageUtils.sendRawMessage(player, messages.getMessage("insufficient_balance"));
					return;
				}
				
				double newHealth = health + shopItem.getHealth();
				
				economy.withdrawPlayer(player, shopItem.getCost());
				
				if(newHealth > maxHealth) {
					entity.getEntity().setHealth(entity.getEntity().getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
				}else {
					entity.getEntity().setHealth(newHealth);
				}
				
				double healths = Math.round(entity.getEntity().getHealth()*10)/10;
				MessageUtils.sendRawMessage(player, BattlePets.getInstance().configManager.messages.getMessage("pet_heal").replace("%health%", healths+""));
				
			});
			
		}
		return this.executables;
	}

	@Override
	public Map<String, Object> placeholders() {
		// TODO Auto-generated method stub
		return null;
	}

}
