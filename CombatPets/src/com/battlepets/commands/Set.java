
package com.battlepets.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.battlepets.attributes.GenericPetAttribute;
import com.battlepets.main.BattlePets;
import com.battlepets.utils.Numbers;

public class Set extends SubCommand {

    @Override
    public void onCommand(Player player, String[] args) {

    	ItemStack itemInHand = player.getInventory().getItemInMainHand();
		
    	if(!itemInHand.hasItemMeta()) {
    		BattlePets.getInstance().configManager.messages.send(player, "set_failed_hand");	
    		return;
    	}
		
    	
		boolean valid = BattlePets.getInstance().configManager.eggStats.eggFormatting.validate(itemInHand);
		
		if(!valid) {
			BattlePets.getInstance().configManager.messages.send(player, "set_failed_hand");	
    		return;
		}
		
		final ItemStack item = itemInHand;
		
		GenericPetAttribute data = BattlePets.getInstance().configManager.eggStats.eggFormatting.getAttributes(item);
		
    	if(args.length >= 2) {
    		
    		if(args[1].equalsIgnoreCase("level")) {
    			if(args.length != 3) {
    		    	//MessageUtils.sendMessage(player, "&c/" + BattlePets.getInstance().CommandManager.main[0] + " " + this.name() + " level <number>.");
    				BattlePets.getInstance().configManager.messages.send(player, "set_failed_level_args");
    				return;
    			}
    			
    			if(!Numbers.isNumber(args[2].trim())) {
    		    	//MessageUtils.sendMessage(player, "&c" + args[2] + " is not a number!");
    		    	BattlePets.getInstance().configManager.messages.send(player, "set_failed_number");
    				return;	
    			}
    			
    			int level = Integer.parseInt(args[2].trim());

    			data.setXp(data.getGeneric().getLevel().getRequiredXpForLevel(level-1));
    			
    			ItemStack egg = BattlePets.getInstance().configManager.eggStats.eggFormatting.getSpawnEgg(data);
    			
    			player.getInventory().getItemInMainHand().setItemMeta(egg.getItemMeta());
    			
    			//MessageUtils.sendMessage(player, "&aPet has successfully been modified.");
    			BattlePets.getInstance().configManager.messages.send(player, "set_modification_success");	
    			
    		}
    		
    		if(args[1].equalsIgnoreCase("points")) {
    			if(args.length != 3) {
    		    	//MessageUtils.sendMessage(player, "&c/" + BattlePets.getInstance().CommandManager.main[0] + " " + this.name() + " points <number>.");
    				BattlePets.getInstance().configManager.messages.send(player, "set_failed_points_args");
    		    	return;
    			}
    			
    			if(!Numbers.isNumber(args[2].trim())) {
    		    	//MessageUtils.sendMessage(player, "&c" + args[2] + " is not a number!");
    		    	BattlePets.getInstance().configManager.messages.send(player, "set_failed_number");
    				return;	
    			}
    			
    			int points = Integer.parseInt(args[2].trim());

    			data.setPoints(points);

    			ItemStack egg = BattlePets.getInstance().configManager.eggStats.eggFormatting.getSpawnEgg(data);
    			
    			player.getInventory().getItemInMainHand().setItemMeta(egg.getItemMeta());
    			
    			//MessageUtils.sendMessage(player, "&aPet has successfully gained " + points + " points.");
    			BattlePets.getInstance().configManager.messages.send(player, "set_points_gained");
    			
    		}
    		
    		
    		
    		return;
    	}
    	
    	//MessageUtils.sendMessage(player, "&c/" + BattlePets.getInstance().CommandManager.main[0] + " " + this.name() + " level <number>");
    	//MessageUtils.sendMessage(player, "&c/" + BattlePets.getInstance().CommandManager.main[0] + " " + this.name() + " points <number>");
    
    	BattlePets.getInstance().configManager.messages.send(player, "set_default_help");
    }

    @Override

    public String name() {
        return "set";
    }

    @Override
    public String info() {
        return "set/change pet data.";
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }

	@Override
	public String permission() {
		return BattlePets.getInstance().configManager.permissions.set;
	}
	
	@Override
	public AutoComplete autoComplete(CommandSender sender) {
		AutoComplete tabCompleter = new AutoComplete();
		tabCompleter.createEntry("level");
		tabCompleter.createEntry("points");
		return tabCompleter;
	}
	

}