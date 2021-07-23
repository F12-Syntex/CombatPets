package com.battlepets.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.plugin.Plugin;

import com.battlepets.main.BattlePets;

public class EventHandler {

    public List<SubEvent> events = new ArrayList<SubEvent>();
	
    private Plugin plugin = BattlePets.instance;
    
    public PetsHandler petsHandler;
    public InputHandler inputHandler;
    
	public void setup() {
		
		this.petsHandler = new PetsHandler();
		this.inputHandler = new InputHandler();
		
		this.events.add(this.inputHandler);
		this.events.add(this.petsHandler);
		
		this.events.forEach(i -> plugin.getServer().getPluginManager().registerEvents(i, plugin));
	}
	
}
