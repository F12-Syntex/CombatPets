package com.battlepets.GUI;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.battlepets.attributes.GenericPetAttribute;
import com.battlepets.config.Messages;
import com.battlepets.inventory.PetInventory;
import com.battlepets.main.BattlePets;
import com.battlepets.utils.ComponentBuilder;
import com.battlepets.utils.MessageUtils;

import net.milkbowl.vault.economy.Economy;

public class InventoryGUI extends PagedGUI{

	public InventoryGUI(Player player) {
		super(player);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return MessageUtils.translateAlternateColorCodes("&4Inventory");
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
	public Sound sound() {
		// TODO Auto-generated method stub
		return Sound.BLOCK_LEVER_CLICK;
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
	public void onClickInventory(InventoryClickEvent e) {
		// TODO Auto-generated method stub
		
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
	public List<PagedItem> Contents() {
		// TODO Auto-generated method stub
		
		List<PagedItem> items = new ArrayList<PagedItem>();
		
		PetInventory inventory = BattlePets.getInstance().petInventoryHandler.getInventory(player.getUniqueId());
		
		for(GenericPetAttribute item : inventory.getPets()) {
			
			try {
			
				ItemStack egg = BattlePets.getInstance().configManager.eggStats.eggFormatting.getSpawnEgg(item);
				
				if(item.getHealth() == 0) {
					
					int cost = BattlePets.getInstance().configManager.settings.revive;
					
					ItemMeta meta = egg.getItemMeta();
					
					List<String> lore = meta.getLore();
					
					lore.add("&4[&cRevive for &a" + cost + "&7$&4]");
					
					meta.setLore(ComponentBuilder.createLore(lore));
					
					egg.setItemMeta(meta);
					
					final Messages messages = BattlePets.getInstance().configManager.messages;
					
					final Economy economy = BattlePets.getInstance().econ;
					
					
					PagedItem pagedItem = new PagedItem(egg, () -> {

						GUI confirmation = new ConfirmationGUI(player, () -> {
							double balance = economy.getBalance(this.player);
							
							if(balance < cost) {
								MessageUtils.sendRawMessage(player, messages.getMessage("insufficient_balance"));
								return;
							}
							
							economy.withdrawPlayer(player, cost);
							
							//MessageUtils.sendMessage(player, "&cPet has been successfully revived.");
							BattlePets.getInstance().configManager.messages.send(player, "pet_revivied");
							
							item.setHealth(item.getMaxHealth());
							
							player.closeInventory();
					    	PagedGUI gui = new InventoryGUI(player);
					    	gui.open();							
						});
						
						player.closeInventory();
						confirmation.open();
						
					});	
	
					
					items.add(pagedItem);
				}else {
					PagedItem pagedItem = new PagedItem(egg, () -> {
						
						if(player.getInventory().firstEmpty() == -1) {
							//MessageUtils.sendMessage(player, "&cYour inventory is full.");
							BattlePets.getInstance().configManager.messages.send(player, "petInventory_full");
							return;
						}
						
						
						player.getInventory().addItem(egg);
						inventory.removePet(item);
						player.closeInventory();
						BattlePets.getInstance().petInventoryHandler.saveInventories();
				    	PagedGUI gui = new InventoryGUI(player);
				    	gui.open();
					});	
					items.add(pagedItem);
				}
				
				
			}catch (Exception e) {
				continue;
			}
			
		}
		
		return items;
	}

	@Override
	public List<SpecialItem> SpecialContents() {

		List<SpecialItem> contents = new ArrayList<SpecialItem>();
		
		ItemStack inventory = GenerateItem.getItem("&cShop", Material.BLUE_STAINED_GLASS_PANE, "&3Takes you to the shop.");
		
		SpecialItem items = new SpecialItem(inventory, () -> {
			player.closeInventory();
			ShopGUI inv = new ShopGUI(player);
			inv.open(1);
		}, 46);
		
		contents.add(items);
		
		return contents;
	}

}
