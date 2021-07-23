package com.battlepets.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.battlepets.config.Cooldown;
import com.battlepets.config.Messages;
import com.battlepets.config.Permissions;
import com.battlepets.cooldown.CooldownUser;
import com.battlepets.main.BattlePets;
import com.battlepets.tags.TagFactory;
import com.battlepets.utils.MessageUtils;
import com.battlepets.utils.StringMinipulation;

public class CommandManager implements CommandExecutor {

    private ArrayList<SubCommand> commands = new ArrayList<SubCommand>();
    private Messages messages = BattlePets.getInstance().configManager.messages;
    private Permissions permissions = BattlePets.getInstance().configManager.permissions;
    private Cooldown cooldowns = BattlePets.getInstance().configManager.cooldown;
    
    private BattlePets plugin;

    //Sub Commands
    public String[] main = new String[] {"cp"};
    
    public void setup(BattlePets plugin) {
    	this.setPlugin(plugin);
    	
        commands.add(new Help());
        commands.add(new Reload());
        commands.add(new Configure());
        commands.add(new Shop());
        commands.add(new Default());
        commands.add(new Inventory());
        commands.add(new Set());
        commands.add(new Challenge());
        commands.add(new Developer());
        
        TabCompleter commandTab = new TabCompleter() {
			@Override
			public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
				
				if(args.length == 1) {
				
					List<String> tabCommands = new ArrayList<String>();
					
					for(SubCommand i : commands) {
						tabCommands.add(i.name());
					}
					return tabCommands;
				}
	  
				SubCommand parent = get(args[0]);
				
				AutoComplete completer = null;
				
				try {
					completer = parent.autoComplete(sender);
				}catch (Exception e) {
					return new ArrayList<String>();
				}
				
				if(completer == null) {
					return new ArrayList<String>();
				}
				
				String filter = "";
				
				for(int i = 0; i < args.length - 1; i++) {
					filter += args[i] + ".";	
				}
				
				List<String> values = completer.filter(StringMinipulation.removeLastCharOptional(filter));
				
				return values;
			}
		};
		
    	for(String i : main) {
    		plugin.getCommand(i).setExecutor(this);
            plugin.getCommand(i).setTabCompleter(commandTab); 
    	}
        

    }


    public ArrayList<SubCommand> getCommands(){
    	return commands;
    }

    
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {


        if (!(sender instanceof Player)) {

            sender.sendMessage(messages.invalid_entitiy);

            return true;

        }

        Player player = (Player) sender;
        
    	try {

    		boolean isCommand = false;
    		
    		for(String i : main) {
    			if(command.getName().equalsIgnoreCase(i)) {
    				isCommand = true;
    				break;
    			}
    		}
    		
    		
        if (isCommand) {

            if (args.length == 0) {
            	
            	CooldownUser user = BattlePets.getInstance().cooldownManager.getUser(player.getUniqueId());
            	
            	SubCommand cmd = new Help();
            	
            	if(!player.hasPermission(cmd.permission())) {
		    		MessageUtils.sendRawMessage(player, messages.invalid_permission);
		    		return true;
		        }
            	
            	
            	String key = cmd.name();

            	int timer = user.getTime(key);
            	
            	if(timer <= 0 || player.hasPermission(permissions.bypass)) {
            	
            		cmd.onCommand(player, args);
            	  
                	user.reset(key);
                
            	}else {
                	
                	TagFactory tagHelper = TagFactory.instance(cooldowns.message);
                
                	tagHelper.setCooldown(timer);
                	
                	MessageUtils.sendRawMessage(player, tagHelper.parse());
                }
            	
                return true;

            }

            SubCommand target = this.get(args[0]);

            if (target == null) {

                player.sendMessage(messages.invalid_syntax);

                return true;

            }
            
		    if(!player.hasPermission(target.permission())) {
		    		MessageUtils.sendRawMessage(player, messages.invalid_permission);
		    		return true;
		    }

            ArrayList<String> arrayList = new ArrayList<String>();

            arrayList.addAll(Arrays.asList(args));

            arrayList.remove(0);
            
            try{
            	
            	CooldownUser user = BattlePets.getInstance().cooldownManager.getUser(player.getUniqueId());
            	
            	String key = args[0].trim();

            	int timer = user.getTime(key);
            	
            	if(timer <= 0 || player.hasPermission(permissions.bypass)) {
            		
            	    target.onCommand(player, args);
            	    
            	    user.reset(key);
                
            	}else {
                	
                	TagFactory tagHelper = TagFactory.instance(cooldowns.message);
                
                	tagHelper.setCooldown(timer);
                	
                	MessageUtils.sendRawMessage(player, tagHelper.parse());
                }
            	
            
            
            }catch (Exception e){
                player.sendMessage(messages.error);
                e.printStackTrace();
            }

        }


    }catch(Throwable e) {
        player.sendMessage(messages.error);
        e.printStackTrace();
    }

        return true;
    
    }



    public SubCommand get(String name) {

        Iterator<SubCommand> subcommands = commands.iterator();

        while (subcommands.hasNext()) {

            SubCommand sc = (SubCommand) subcommands.next();


            if (sc.name().equalsIgnoreCase(name)) {

                return sc;

            }


            String[] aliases;

            int length = (aliases = sc.aliases()).length;



            for (int var5 = 0; var5 < length; ++var5) {

                String alias = aliases[var5];

                if (name.equalsIgnoreCase(alias)) {

                    return sc;

                }

            }

        }

        return null;

    }


	public BattlePets getPlugin() {
		return plugin;
	}


	public void setPlugin(BattlePets plugin) {
		this.plugin = plugin;
	}

}