
package com.battlepets.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.battlepets.GUI.ShopGUI;
import com.battlepets.main.BattlePets;

public class Shop extends SubCommand {

    @Override
    public void onCommand(Player player, String[] args) {
    	
    	ShopGUI gui = new ShopGUI(player);
    	
    	gui.open(1);
    	
    }

    @Override

    public String name() {
        return "shop";
    }

    @Override
    public String info() {
        return "opens up the shop.";
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }

	@Override
	public String permission() {
		return  BattlePets.getInstance().configManager.permissions.shop;
	}
	
	@Override
	public AutoComplete autoComplete(CommandSender sender) {
		AutoComplete tabCompleter = new AutoComplete();		
		return tabCompleter;
	}
	

}