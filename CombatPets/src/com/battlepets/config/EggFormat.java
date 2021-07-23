package com.battlepets.config;

public class EggFormat extends Config{
	
	public String prefix;
	public String value;
	public String activeValue;

	public EggFormat(String name, double version) {
		super(name, version);
		
		this.items.add(new ConfigItem("Format.colour.prefix", "&6"));
		this.items.add(new ConfigItem("Format.colour.value", "&4"));
		this.items.add(new ConfigItem("Format.colour.active", "&c"));
	
	}

	@Override
	public Configuration configuration() {
		// TODO Auto-generated method stub
		return Configuration.EGGSTATS;
	}
	
	@Override
	public void initialize() {
	    this.prefix = this.getConfiguration().getString("Format.colour.prefix");
	    this.value = this.getConfiguration().getString("Format.colour.value");
	    this.activeValue = this.getConfiguration().getString("Format.colour.active");
	}
	
}
