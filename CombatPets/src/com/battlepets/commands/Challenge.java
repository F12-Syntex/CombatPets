
package com.battlepets.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.battlepets.main.BattlePets;
import com.battlepets.utils.MessageUtils;

public class Challenge extends SubCommand {

    @Override
    public void onCommand(Player player, String[] args) {
		
    	if(args.length >= 2) {
    		if(args[1].equalsIgnoreCase("accept")) {
    			return;
    		}		
    		if(args[1].equalsIgnoreCase("deny")) {
    			return;
    		}
    	}
    	
    	
    	
    	MessageUtils.sendMessage(player, "&c/" + BattlePets.getInstance().CommandManager.main[0] + " " + this.name() + " accept");
    	MessageUtils.sendMessage(player, "&c/" + BattlePets.getInstance().CommandManager.main[0] + " " + this.name() + " deny");
    	
    }

    @Override

    public String name() {
        return "challenge";
    }

    @Override
    public String info() {
        return "Accept/Deny a challeng.";
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
		tabCompleter.createEntry("accept");
		tabCompleter.createEntry("deny");
		return tabCompleter;
	}
	

}