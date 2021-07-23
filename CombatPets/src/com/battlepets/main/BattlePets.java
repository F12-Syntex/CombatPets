package com.battlepets.main;
import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.battlepets.battle.BattleRequests;
import com.battlepets.config.ConfigManager;
import com.battlepets.cooldown.CooldownManager;
import com.battlepets.cooldown.CooldownTick;
import com.battlepets.entities.ActiveEntityTracker;
import com.battlepets.events.EventHandler;
import com.battlepets.inventory.PetInventoryHandler;
import com.battlepets.utils.MessageUtils;

import net.milkbowl.vault.economy.Economy;


public class BattlePets extends JavaPlugin implements Listener{


    public static BattlePets instance;
    public com.battlepets.commands.CommandManager CommandManager;
    public ConfigManager configManager;
    public EventHandler eventHandler;
    public CooldownManager cooldownManager;
    public CooldownTick cooldownTick;
	public File ParentFolder;
	public PetInventoryHandler petInventoryHandler;
	public ActiveEntityTracker tracker;
	public BattleRequests battleRequests;
	
	public Economy econ;
	
	@Override
	public void onEnable(){
		
		ParentFolder = getDataFolder();
	    instance = this;

	    this.petInventoryHandler = new PetInventoryHandler();
	    
	    configManager = new ConfigManager();
	    configManager.setup(this);
	    
	    eventHandler = new EventHandler();
	    eventHandler.setup();
	    
	    this.cooldownManager = new CooldownManager();

	    this.cooldownTick = new CooldownTick();
	    this.cooldownTick.schedule();
	    
	    this.CommandManager = new com.battlepets.commands.CommandManager();
	    this.CommandManager.setup(this);
	    
	    this.tracker = new ActiveEntityTracker();
	    
	    this.battleRequests = new BattleRequests();
	    
	    if (!this.setupEconomy() ) {
            MessageUtils.sendConsoleMessage("&cDisabled due to no Vault dependency found!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

	    
	}

	private boolean setupEconomy() {
		
       if (getServer().getPluginManager().getPlugin("Vault") == null) {
           return false;
       }
       
       RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
       
       if (rsp == null) {
           return false;
       }
       econ = rsp.getProvider();
       
       return econ != null;
    }
	
	@Override
	public void onDisable(){

		this.tracker.putAllPetsBackIntoUsersInventories();
		this.petInventoryHandler.saveInventories();
		this.eventHandler = null;
		BattlePets.getInstance().configManager.eggData.update();
		HandlerList.getRegisteredListeners(instance);
	}
	
	public static void Log(String msg){
		  Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&c" + BattlePets.getInstance().getName() + "&7] &c(&7LOG&c): " + msg));
	}
	

	public void reload() {
		this.petInventoryHandler.saveInventories();
		this.configManager = new ConfigManager();
		this.configManager.setup(this);
	}
		

	public static BattlePets getInstance() {
		return instance;
	}
		
	
}
