package com.battlepets.config.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.battlepets.GUI.ConfirmationGUI;
import com.battlepets.GUI.GenericConfigurableInterface;
import com.battlepets.GUI.PagedGeneratedGUI;
import com.battlepets.GUI.PetCombatGUI;
import com.battlepets.GUI.PetGui;
import com.battlepets.GUI.PetShopGui;
import com.battlepets.GUI.PetShopItems;
import com.battlepets.GUI.PointsGUI;
import com.battlepets.GUI.ShopGUI;
import com.battlepets.GUI.TestGUI;
import com.battlepets.config.ConfigItem;
import com.battlepets.config.Configuration;
import com.battlepets.utils.ComponentBuilder;

public class GuiConfigHandler {
	
	public List<GUIConfig> interfaces = new ArrayList<GUIConfig>();
	
	public GuiConfigHandler() {
	
		List<GenericConfigurableInterface> guis = new ArrayList<GenericConfigurableInterface>();
		guis.add(new ConfirmationGUI());
		guis.add(new PetCombatGUI());
		guis.add(new PetGui());
		guis.add(new PetShopGui());
		guis.add(new PointsGUI());
		guis.add(new ShopGUI());
		guis.add(new PetShopItems());
		
		guis.add(new TestGUI().data());
		
		for(GenericConfigurableInterface i : guis) {
			interfaces.add(new GUIConfig(i, 1.2) {

				@Override
				public Configuration configuration() {
					return Configuration.GUI;
				}

				@SuppressWarnings("deprecation")
				@Override
				public void initialize() {
					
					ConfigurationSection settings = this.getConfiguration().getConfigurationSection("Settings");
					
					this.title = settings.getString("Name");
					this.permission = settings.getString("Permission");
					
					this.openSound = Sound.valueOf(settings.getString("Sound.Open.Sound"));
					this.openVolume = settings.getDouble("Sound.Open.Volume");
					
					this.closeSound = Sound.valueOf(settings.getString("Sound.Close.Sound"));
					this.closeVolume = settings.getDouble("Sound.Close.Volume");
					
					if(!this.getConfiguration().isConfigurationSection("Items")) {
						this.interior = new ArrayList<GuiItem>();
						return;
					}
					
					ConfigurationSection section = this.getConfiguration().getConfigurationSection("Items");
					
						
					for(String index : section.getKeys(false)) {
						
						if(!section.isConfigurationSection(index)) {
							continue;
						}
						
						ConfigurationSection item = section.getConfigurationSection(index);
						
						Material mat = Material.valueOf(item.getString("Item.Material"));
						String displayName = item.getString("Item.Display");
						List<String> lore = item.getStringList("Item.Lore");
						
						
						ItemStack material = new ItemStack(mat);
						
						ItemMeta meta = material.getItemMeta();
						
						meta.setDisplayName(displayName);;
						meta.setLore(lore);
	
						for(String i : item.getStringList("Item.ItemFlags")) {
							meta.addItemFlags(ItemFlag.valueOf(i));
						};
						
						
						material.setItemMeta(meta);
						
						/*
						if(section.isConfigurationSection("Item.Enchantments")) {
							ConfigurationSection enchantSection = section.getConfigurationSection("Item.Enchantments");
							for(String ench : enchantSection.getKeys(false)) {
								meta.addEnchant(Enchantment.getByName(ench), enchantSection.getInt(ench + ".Level"), false);
							}							
						}
						*/

						if(item.isConfigurationSection("Item.Enchantments")) {
							ConfigurationSection config = item.getConfigurationSection("Item.Enchantments");
							for(String key : config.getKeys(false)) {
								String enchantName = config.getString(key + ".Enchantment");
								int enchantLevel = config.getInt(key + ".Level");
								material.addUnsafeEnchantment(Enchantment.getByName(enchantName), enchantLevel);
							}
						}
						
						String position = item.getString("Position");
						
						String execution = item.getString("Execution");
						
						GuiItem gui = new GuiItem(material, position, execution);
						
						if(i instanceof PagedGeneratedGUI) {
							int pageCount = item.getInt("pages.page");
							boolean allPaged = item.getBoolean("pages.all");
							Page page = new Page(pageCount, allPaged);
							gui.setPage(page);
						}
						
						
						
						this.interior.add(gui);
						
					}
					
					
				}

				@SuppressWarnings("deprecation")
				@Override
				public void defaults() {
				
					int count = 0;
					
					this.items.add(new ConfigItem("Settings.Name", i.name()));
					this.items.add(new ConfigItem("Settings.Permission", i.permission()));
					
					this.items.add(new ConfigItem("Settings.Sound.Open.Sound", Sound.BLOCK_LEVER_CLICK.name()));
					this.items.add(new ConfigItem("Settings.Sound.Open.Volume", 0f));
					
					this.items.add(new ConfigItem("Settings.Sound.Close.Sound", Sound.BLOCK_LEVER_CLICK.name()));
					this.items.add(new ConfigItem("Settings.Sound.Close.Volume", 0f));
					
					for(GuiItem o : i.configuration()) {

						this.items.add(new ConfigItem("Items." + count + ".Item.Material", o.getItem().getType().name()));
						this.items.add(new ConfigItem("Items." + count + ".Item.Display", o.getItem().getItemMeta().getDisplayName()));
						
						if(!o.getItem().getItemMeta().hasLore()) {
							this.items.add(new ConfigItem("Items." + count + ".Item.Lore", new ArrayList<String>()));
						}else {
							this.items.add(new ConfigItem("Items." + count + ".Item.Lore", o.getItem().getItemMeta().getLore()));
						}
					
						Map<Enchantment, Integer> map = o.getItem().getEnchantments();
					
						/*
						for(Enchantment i : map.keySet()) {
							this.items.add(new ConfigItem("Items." + count + ".Item.Enchantments." + i + ".Level", map.get(i)));
						}
						*/
					
						int c = 0;
						
						if(!map.keySet().isEmpty()) {
							for(Enchantment i : map.keySet()) {
								//this.items.add(new ConfigItem("Items." + count + ".Item.Enchantments." + i + ".Level", map.get(i)));
								this.items.add(new ConfigItem("Items." + count + ".Item.Enchantments." + c + ".Enchantment", i.getName()));
								this.items.add(new ConfigItem("Items." + count + ".Item.Enchantments." + c + ".Level", map.get(i)));
							}
						}else {
							this.items.add(new ConfigItem("Items." + count + ".Item.Enchantments", "Empty"));
						}
						
						this.items.add(new ConfigItem("Items." + count + ".Item.ItemFlags", ComponentBuilder.createLore(ItemFlag.HIDE_ATTRIBUTES.name())));
						
						this.items.add(new ConfigItem("Items." + count + ".Position", o.posFormat));
						this.items.add(new ConfigItem("Items." + count + ".Execution", o.getRunnableID()));
						
						if(i instanceof PagedGeneratedGUI) {
							this.items.add(new ConfigItem("Items." + count + ".pages.page", o.getPage().getPage()));
							this.items.add(new ConfigItem("Items." + count + ".pages.all", o.getPage().isAllPaged()));
						}
						
						
						//meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
						
						
						count++;
					}

				}
			});
			
		}
	
	}
	
	public GUIConfig getConfig(GenericConfigurableInterface gui) {
		for(GUIConfig i : this.interfaces) {
			if(i.gui.config().equalsIgnoreCase(gui.config())) {
				return i;
			}
		}
		return null;
	}
	
	
}
