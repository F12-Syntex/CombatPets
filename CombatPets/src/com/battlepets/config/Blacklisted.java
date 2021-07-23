package com.battlepets.config;

import java.util.List;

import org.bukkit.World;

import com.battlepets.utils.ComponentBuilder;

public class Blacklisted extends Config{

	public List<String> worlds;
	
	public Blacklisted(String name, double version) {
		super(name, version);
		this.items.add(new ConfigItem("Blacklisted.worlds", ComponentBuilder.createLore("world1", "world2")));
	}

	@Override
	public Configuration configuration() {
		// TODO Auto-generated method stub
		return Configuration.BLACKLISTED;
	}
	
	@Override
	public void initialize() {
		this.worlds = this.getConfiguration().getStringList("Blacklisted.worlds");
	}

	public boolean isBlackListed(World world) {
		return this.worlds.contains(world.getName());
	}
	
	
}
