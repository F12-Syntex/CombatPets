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

import com.battlepets.attributes.GenericPetAttribute;
import com.battlepets.config.Messages;
import com.battlepets.config.gui.GuiItem;
import com.battlepets.config.gui.Page;
import com.battlepets.inventory.PetInventory;
import com.battlepets.main.BattlePets;
import com.battlepets.petsdata.Pet;
import com.battlepets.shopdata.ShopData;
import com.battlepets.shopdata.ShopItem;
import com.battlepets.tags.TagFactory;
import com.battlepets.utils.ComponentBuilder;
import com.battlepets.utils.MessageUtils;
import com.battlepets.utils.StringMinipulation;

import net.milkbowl.vault.economy.Economy;

public class ShopGUI extends PagedGeneratedGUI{

	public ShopGUI(Player player) {
		super(player);
		// TODO Auto-generated constructor stub
	}
	
	public ShopGUI() {}

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return MessageUtils.translateAlternateColorCodes("&4Shop");
	}

	@Override
	public String permission() {
		// TODO Auto-generated method stub
		return BattlePets.getInstance().configManager.permissions.basic;
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
		return "pet_shop";
	}

	public List<GuiItem> getShopItems() {
		
		List<GuiItem> items = new ArrayList<GuiItem>();
		
		int index = 0;
		
		ShopData data = BattlePets.getInstance().configManager.shop.shopData;
		
		for(ShopItem item : data.getAllItems()) {
			
			try {

				int current_page = Math.round(index/36) + 1;
				
				String materialType = item.getEntityType().name() + "_SPAWN_EGG";
				
				ItemStack spawnEgg = new ItemStack(Material.valueOf(materialType));
				
				ItemMeta meta = spawnEgg.getItemMeta();
				
				meta.setDisplayName(MessageUtils.translateAlternateColorCodes(item.getDisplayName()));
				
				TagFactory factory = TagFactory.instance(player);
				
				factory.setLore(item.getLore());
				factory.addMapping("%entity%", StringMinipulation.capitalize(item.getEntityType().name()));
				
				meta.setLore(ComponentBuilder.createLore(factory.listParse()));
				
				spawnEgg.setItemMeta(meta);
				
				int ID = index - ((current_page - 1) * 36);
				
				GuiItem pagedItem = new GuiItem(spawnEgg, ID+"", "pet:" + ID + ":" + current_page);
				pagedItem.setPage(new Page(current_page, false));
				pagedItem.data = item;
				
				items.add(pagedItem);
				
				index++;
				
				
			}catch (Exception e) {
				continue;
			}
			
		}
		
		return items;
		
	}
	
	@Override
	public List<GuiItem> configuration() {
		this.defaultPagedItem();
		return this.inventoryItems;
	}

	@Override
	public Map<String, Runnable> executables() {
		
		this.defaultExecution();
		
		for(GuiItem i : getShopItems()) {
			
			ShopItem item = (ShopItem)i.data;

			this.executables.put(i.getRunnableID(), () -> {
				
				Messages messages = BattlePets.getInstance().configManager.messages;
				
				Economy economy = BattlePets.getInstance().econ;
				
				double balance = economy.getBalance(this.player);
				
				if(!player.hasPermission(item.getPermission())) {
					MessageUtils.sendRawMessage(player, messages.invalid_permission);
					return;
				}
				
				
				if(balance < item.getPrice()) {
					MessageUtils.sendRawMessage(player, messages.getMessage("insufficient_balance"));
					return;
				}
				
				economy.withdrawPlayer(player, item.getPrice());
				
				//ItemStack egg = this.getSpawnEgg(item.getEntityType(), item.isBaby());
				
				Pet pet = BattlePets.getInstance().configManager.pets.petsData.getPet(item.getEntityType(), item.isBaby());
				
				GenericPetAttribute attribute = pet.getDefaultAttributes();
				
				attribute.setStorage(BattlePets.getInstance().configManager.eggData.handler.generateEgg());
				
				PetInventory inv = BattlePets.getInstance().petInventoryHandler.getInventory(player.getUniqueId());
				
				inv.addPet(attribute);
				
				//this.player.getInventory().addItem(egg);
				
				MessageUtils.sendRawMessage(player, messages.getMessage("purchase_successful"));
				
			});
		
		}
		
		this.executables.put("shop:inventory", ()->{
			
			player.closeInventory();
			InventoryGUI inventoryGUI = new InventoryGUI(player);
			inventoryGUI.open();
			
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
		
		List<GuiItem> o = this.getShopItems();
		for(GuiItem i : o) {
			item.add(i);
		}
		
		ItemStack inventory = GenerateItem.getItem("&cInventory", Material.BLUE_STAINED_GLASS_PANE, "&3Takes you to your inventory.");
		item.add(GuiItem.constructPagedItem(inventory, "52", "shop:inventory", new Page(0, true)));
		
		return item;
	}

}
