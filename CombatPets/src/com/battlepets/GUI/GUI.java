package com.battlepets.GUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.battlepets.config.Messages;
import com.battlepets.config.gui.GUIConfig;
import com.battlepets.config.gui.GuiItem;
import com.battlepets.main.BattlePets;
import com.battlepets.utils.MessageUtils;

public abstract class GUI extends GenericConfigurableInterface implements Listener{

	public Player player;
	public Inventory inv;
	public Messages messages;
	
	protected List<GuiItem> inventoryItems = new ArrayList<GuiItem>();
	protected Map<String, Runnable> executables = new HashMap<String, Runnable>();
	protected Map<String, Object> placeholders = new HashMap<String, Object>();
	
	protected GUIConfig config;
	
	public GUI(Player player) {
		this.player = player;
		this.messages = BattlePets.getInstance().configManager.messages;
		this.config = BattlePets.getInstance().configManager.guis.getConfig(this);
		this.inv = Bukkit.createInventory(this.player, size(), MessageUtils.translateAlternateColorCodes(this.config.title));
	}
	
	public GUI() {}
	
	@EventHandler()
	public void onOpen(InventoryOpenEvent e) {
		if(e.getPlayer().getUniqueId().compareTo(this.player.getUniqueId()) != 0) return;
		if(!e.getView().getTitle().equals(this.name())) return;
		if(!e.getPlayer().hasPermission(permission())) {
			MessageUtils.sendRawMessage(player, messages.invalid_permission);
			e.setCancelled(true);
			return;
		}
		onOpenInventory(e);
	}
	
	@EventHandler()
	public void onClose(InventoryCloseEvent e) {
		if(e.getPlayer().getUniqueId().compareTo(this.player.getUniqueId()) != 0) return;
		if(!e.getView().getTitle().equals(this.name())) return;
		this.onCloseInventory(e);
		HandlerList.unregisterAll(this);
		
		player.getWorld().playSound(player.getLocation(), this.config.closeSound, (float) this.config.closeVolume, soundLevel());
		
	}
	
	@EventHandler()
	public void onClick(InventoryClickEvent e) {
		
		if(e.getClickedInventory() == null) return;
		if(e.getWhoClicked().getUniqueId().compareTo(this.player.getUniqueId()) != 0) return;
		if(e.getClickedInventory().getType() == InventoryType.PLAYER) {
			e.setCancelled(true);
			return;
		}
		
		String title = MessageUtils.translateAlternateColorCodes(e.getView().getTitle());
		String view = MessageUtils.translateAlternateColorCodes(this.config.title);
		
		if(!title.equals(view)) return;
		
		e.setCancelled(!canTakeItems());
		if(e.getCurrentItem() == null) {
			return;
		}
		
		for(GuiItem interior : this.config.interior) {
				
				GuiItem parent = interior;
				
				if(parent.getPosition().contains(e.getSlot())) {
					
					String id = parent.getRunnableID();
					
					if(!id.contains(" ")) {
						id = id + " ";
					}
					
					boolean clickPrefix = false;
					
						for(String control : id.split(" ")) {
							
							if(control.contains(":")) {
								
								if(control.split(":")[0].equalsIgnoreCase(e.getClick().name())) {
									
									Runnable runnable = this.executables().get(control.trim());
									Bukkit.getScheduler().runTask(BattlePets.getInstance(), runnable);
									clickPrefix = true;
									break;
								}
							}		
	
						}
	
					if(!clickPrefix) {
						System.out.println(parent.getRunnableID());
						Runnable runnable = this.executables().get(parent.getRunnableID());
						
						if(runnable == null) return;
						
						Bukkit.getScheduler().runTask(BattlePets.getInstance(), runnable);
					}
					
				}
		}
		
	}
	
	public void open() {
		this.player.closeInventory();
		BattlePets.instance.getServer().getPluginManager().registerEvents(this, BattlePets.instance);
		
		player.getWorld().playSound(player.getLocation(), this.config.openSound, (float) this.config.openVolume, soundLevel());
	    
		this.placeholders();
		
		this.Contents(inv);
	    
	    player.openInventory(inv);
	    
		this.config.interior.addAll(this.postConfiguration());
	}
	
	public void updatePlaceholders() {
		this.placeholders.clear();
		this.placeholders();
	}
	
	public void Contents(Inventory inv) {
		
		this.inv.clear();
		this.updatePlaceholders();
		
		List<GuiItem> items = this.config.interior;
		
		for(GuiItem i : items) {
		
			final ItemStack item = i.getItem().clone();
			
			if(!i.show()) {
				continue;
			}
			
			if(this.placeholders.isEmpty()) {
				for(int o : i.getPosition()) {
					this.inv.setItem(o, i.getItem());
				}
				continue;
			}
			
			ItemMeta meta = item.getItemMeta();
			
			for(String placeholder : this.placeholders.keySet()) {
			
				meta.setDisplayName(meta.getDisplayName().replace(placeholder, this.placeholders.get(placeholder).toString()));
				
				List<String> lore = meta.getLore();
				List<String> updatedLore = new ArrayList<String>();
				
				if(meta.hasLore()) {
					for(String o : lore) {
						updatedLore.add(o.replace(placeholder, this.placeholders.get(placeholder).toString()));
					}
					meta.setLore(updatedLore);
				}
				
			}
			
			
			item.setItemMeta(meta);
			
			for(int o : i.getPosition()) {
				this.inv.setItem(o, item);
			}
		}
		
	}
	
	public void addItem(int index, ItemStack item) {
		inv.setItem(index, item);
	}
	
	public void fillEmpty(ItemStack stack) {
		for(int i = 0; i < this.size(); i++) {
			boolean add = true;
			for(GuiItem o : this.inventoryItems) {
				if(o.getPosition().contains(i)) {
					add = false;
				}
			}
			if(add) {
				this.inventoryItems.add(new GuiItem(stack, i, config() + ":empty"));
			}
		}
	}

}
