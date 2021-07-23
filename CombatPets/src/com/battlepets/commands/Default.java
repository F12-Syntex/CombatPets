
package com.battlepets.commands;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.WordUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import com.battlepets.config.Shop;
import com.battlepets.main.BattlePets;
import com.battlepets.petsdata.Pet;
import com.battlepets.utils.ComponentBuilder;
import com.battlepets.utils.MessageUtils;
import com.battlepets.utils.Numbers;

public class Default extends SubCommand {

    @Override
    public void onCommand(Player player, String[] args) {

    	
    	if(args.length >= 2) {
    		if(args[1].equalsIgnoreCase("shop")) {
    	    	com.battlepets.config.Shop shop = BattlePets.getInstance().configManager.shop;
    			
    	    	for(Pet i : BattlePets.getInstance().configManager.pets.petsData.getAllPets()) {
    	    		String path = i.getEntityType().name();
    	    		
    	    		if(i.isBaby()) {
    	    			path = Shop.babyPrefix + path;
    	    		}
    	    		
    	    		String basePath = "Shop.items." + path;
    	    		
    	    		if(!shop.getConfiguration().isConfigurationSection(basePath)) {
    	    			
    	    			shop.getConfiguration().set(basePath + ".displayName", WordUtils.capitalize(i.getDefaultName()));	
    	    			shop.getConfiguration().set(basePath + ".lore", ComponentBuilder.createLore("&cA cute pet, buy now OWO", "&cCosts &a100$"));
    	    			shop.getConfiguration().set(basePath + ".price", 100);
    	    			shop.getConfiguration().set(basePath + ".permission", "battlepets.shop." + path.toLowerCase().replace("-", ""));
    	    			
    	    		}
    	    		
    	    		shop.save();
    	    		
    	    	}
    	    	
    	    	MessageUtils.sendMessage(player, "&aCreated default shop!");
    	    	BattlePets.getInstance().reload();
    	    	return;
    		}
    		
    		if(args[1].equalsIgnoreCase("lore")) {
    			if(args.length <= 2) {
    		    	MessageUtils.sendMessage(player, "&c/" + BattlePets.getInstance().CommandManager.main[0] + " " + this.name() + " lore <text>.");
    				return;
    			}
    			StringBuilder lore = new StringBuilder();
    			for(int i = 2; i < args.length; i++) {
    				lore.append(args[i] + " ");
    			}
    			
    			String newLore = lore.toString().trim();
    			
    			com.battlepets.config.Shop shop = BattlePets.getInstance().configManager.shop;
    			
    			ConfigurationSection section = shop.getConfiguration().getConfigurationSection("Shop.items");
    			
    			MessageUtils.sendMessage(player, "&aLore for all items has been set to. ");
    			
				List<String> Lore = new ArrayList<String>();
				
				for(String o : newLore.split("/n")) {
					Lore.add(o.trim());
					MessageUtils.sendMessage(player, o.trim());
				}
    			
    			section.getKeys(false).forEach(key -> {
    				ConfigurationSection item = section.getConfigurationSection(key);
    				item.set("lore", ComponentBuilder.createLore(Lore));
    			});
    			
    			
    			shop.save();
    			shop.initialize();
    			
    			
    		}
    		
    		if(args[1].equalsIgnoreCase("basestats")) {
    			if(args.length != 4) {
    		    	MessageUtils.sendMessage(player, "&c/" + BattlePets.getInstance().CommandManager.main[0] + " " + this.name() + " basestats <stat> <number>.");
    				return;
    			}
    			
    			if(!Numbers.isNumber(args[3].trim())) {
    		    	MessageUtils.sendMessage(player, "&c" + args[3] + " is not a number!");
    				return;	
    			}
    			
    			String[] key = new String[] {args[2].trim()};
    			int amount = Integer.parseInt(args[3].trim());
    			
    			if(key[0].equalsIgnoreCase("hp")) {
    				key[0] = "HP";
    			}else if(key[0].equalsIgnoreCase("Damage")) {
    				key[0] = "Damage";
    			}else if(key[0].equalsIgnoreCase("Defense")) {
    				key[0] = "Defense";
    			}else if(key[0].equalsIgnoreCase("Speed")) {
    				key[0] = "Speed";
    			}else {
    		    	MessageUtils.sendMessage(player, "&c" + args[2] + " is not a a valid key, valid keys are, HP, Damage, Defense, Speed.");
    				return;	
    			}
    			
    			BattlePets.getInstance().configManager.pets.getConfiguration().getConfigurationSection("Pets").getKeys(false).forEach(name -> {
    				BattlePets.getInstance().configManager.pets.getConfiguration().getConfigurationSection("Pets." + name + ".BaseStats").set(key[0], amount);;	
    			});

    			MessageUtils.sendMessage(player, "&cset componant " + key[0] + " to base value of " + amount);
    			BattlePets.getInstance().configManager.pets.save();
    			BattlePets.getInstance().configManager.pets.initialize();
    		}
    		
    		if(args[1].equalsIgnoreCase("addperstat")) {
    			if(args.length != 4) {
    		    	MessageUtils.sendMessage(player, "&c/" + BattlePets.getInstance().CommandManager.main[0] + " " + this.name() + " addperstat <stat> <number>.");
    				return;
    			}
    			
    			if(!Numbers.isNumber(args[3].trim())) {
    		    	MessageUtils.sendMessage(player, "&c" + args[3] + " is not a number!");
    				return;	
    			}
    			
    			String[] key = new String[] {args[2].trim()};
    			int amount = Integer.parseInt(args[3].trim());
    			
    			if(key[0].equalsIgnoreCase("hp")) {
    				key[0] = "HP";
    			}else if(key[0].equalsIgnoreCase("Damage")) {
    				key[0] = "Damage";
    			}else if(key[0].equalsIgnoreCase("Defense")) {
    				key[0] = "Defense";
    			}else if(key[0].equalsIgnoreCase("Speed")) {
    				key[0] = "Speed";
    			}else {
    		    	MessageUtils.sendMessage(player, "&c" + args[2] + " is not a a valid key, valid keys are, HP, Damage, Defense, Speed.");
    				return;	
    			}
    			
    			BattlePets.getInstance().configManager.pets.getConfiguration().getConfigurationSection("Pets").getKeys(false).forEach(name -> {
    				BattlePets.getInstance().configManager.pets.getConfiguration().getConfigurationSection("Pets." + name + ".skillpoints.AddPerStat").set(key[0], amount);;	
    			});

    			MessageUtils.sendMessage(player, "&cset componant " + key[0] + " to base value of " + amount);
    			BattlePets.getInstance().configManager.pets.save();
    			BattlePets.getInstance().configManager.pets.initialize();
    		}
    		
    		if(args[1].equalsIgnoreCase("name")) {
    			if(args.length <= 2) {
    		    	MessageUtils.sendMessage(player, "&c/" + BattlePets.getInstance().CommandManager.main[0] + " " + this.name() + " lore <text>.");
    				return;
    			}
    			StringBuilder lore = new StringBuilder();
    			for(int i = 2; i < args.length; i++) {
    				lore.append(args[i] + " ");
    			}
    			
    			String name = lore.toString().trim();
    			
    			com.battlepets.config.Shop shop = BattlePets.getInstance().configManager.shop;
    			
    			ConfigurationSection section = shop.getConfiguration().getConfigurationSection("Shop.items");
    			
    			MessageUtils.sendMessage(player, "&aName for all items has been set to, " + name);
    			
    			section.getKeys(false).forEach(key -> {
    				ConfigurationSection item = section.getConfigurationSection(key);
    				item.set("displayName", ComponentBuilder.createLore(name));
    			});
    			
    			
    			shop.save();
    			shop.initialize();
    			
    			
    		}
    		
    		
    		if(args[1].equalsIgnoreCase("price")) {
    			if(args.length != 3) {
    		    	MessageUtils.sendMessage(player, "&c/" + BattlePets.getInstance().CommandManager.main[0] + " " + this.name() + " price <number>.");
    				return;
    			}
    			
    			if(!Numbers.isNumber(args[2].trim())) {
    		    	MessageUtils.sendMessage(player, "&c" + args[2] + " is not a number!");
    				return;	
    			}
    			
    			int price = Integer.parseInt(args[2].trim());
    			
    			com.battlepets.config.Shop shop = BattlePets.getInstance().configManager.shop;
    			
    			ConfigurationSection section = shop.getConfiguration().getConfigurationSection("Shop.items");
    			
    			MessageUtils.sendMessage(player, "&cPrice for all items has been set to, &a" + price + "&7$");
    			
    			
    			section.getKeys(false).forEach(key -> {
    				ConfigurationSection item = section.getConfigurationSection(key);
    				item.set("price", price);
    			});
    			
    			
    			shop.save();
    			shop.initialize();
    		}
    		
    		return;
    	}
    	
    	MessageUtils.sendMessage(player, "&c/" + BattlePets.getInstance().CommandManager.main[0] + " " + this.name() + " shop");
    	MessageUtils.sendMessage(player, "&c/" + BattlePets.getInstance().CommandManager.main[0] + " " + this.name() + " lore");
    	MessageUtils.sendMessage(player, "&c/" + BattlePets.getInstance().CommandManager.main[0] + " " + this.name() + " price");
    	MessageUtils.sendMessage(player, "&c/" + BattlePets.getInstance().CommandManager.main[0] + " " + this.name() + " name");
    	MessageUtils.sendMessage(player, "&c/" + BattlePets.getInstance().CommandManager.main[0] + " " + this.name() + " basestats");
    	MessageUtils.sendMessage(player, "&c/" + BattlePets.getInstance().CommandManager.main[0] + " " + this.name() + " addperstat");
    	
    }

    @Override

    public String name() {
        return "default";
    }

    @Override
    public String info() {
        return "commit default changes.";
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }

	@Override
	public String permission() {
		return  BattlePets.getInstance().configManager.permissions.defaultShop;
	}
	
	@Override
	public AutoComplete autoComplete(CommandSender sender) {
		AutoComplete tabCompleter = new AutoComplete();
		tabCompleter.createEntry("shop");
		tabCompleter.createEntry("lore");
		tabCompleter.createEntry("price");
		tabCompleter.createEntry("name");
		tabCompleter.createEntry("basestats");
		tabCompleter.createEntry("addperstat");
		return tabCompleter;
	}
	

}