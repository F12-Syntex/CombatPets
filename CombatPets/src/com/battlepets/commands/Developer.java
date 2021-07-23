
package com.battlepets.commands;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.battlepets.GUI.PagedGeneratedGUI;
import com.battlepets.GUI.TestGUI;
import com.battlepets.main.BattlePets;

public class Developer extends SubCommand {

    @Override
    public void onCommand(Player player, String[] args) {
    	
    	PagedGeneratedGUI gui = new TestGUI(player);
    	
    	gui.open(1);
    	
    	
    }

    @Override

    public String name() {
        return "testing";
    }

    @Override
    public String info() {
        return "A test command for the developers ( syntex )";
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }

	@Override
	public String permission() {
		return  BattlePets.getInstance().configManager.permissions.admin;
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