
package com.battlepets.commands;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.battlepets.GUI.InventoryGUI;
import com.battlepets.GUI.PagedGUI;
import com.battlepets.main.BattlePets;

public class Inventory extends SubCommand {

    @Override
    public void onCommand(Player player, String[] args) {
    	
    	PagedGUI gui = new InventoryGUI(player);
    	gui.open();

    }

    @Override

    public String name() {
        return "inventory";
    }

    @Override
    public String info() {
        return "Opens up your inventory.";
    }

    @Override
    public String[] aliases() {
        return new String[] {"inv"};
    }

	@Override
	public String permission() {
		return BattlePets.getInstance().configManager.permissions.inventory;
	}
	
	@Override
	public AutoComplete autoComplete(CommandSender sender) {
		AutoComplete tabCompleter = new AutoComplete();
		
		List<SubCommand> commands = BattlePets.getInstance().CommandManager.getCommands();
		
		for(SubCommand i : commands) {
			tabCompleter.createEntry(i.name());
		}
		
		return tabCompleter;
	}
	

}