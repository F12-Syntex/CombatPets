package com.battlepets.config.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;

import com.battlepets.GUI.GenericConfigurableInterface;
import com.battlepets.config.Config;
import com.battlepets.config.ConfigItem;
import com.battlepets.main.BattlePets;

public abstract class GUIConfig extends Config{

	public GenericConfigurableInterface gui;
	
	public List<GuiItem> interior = new ArrayList<GuiItem>();
	
	public String title = "";
	public String permission = "";
	
	public Sound openSound = Sound.BLOCK_LEVER_CLICK;
	public double openVolume = 0f;
	
	public Sound closeSound = Sound.BLOCK_LEVER_CLICK;
	public double closeVolume = 0f;
	
	
	public GUIConfig(GenericConfigurableInterface gui, double version) {
		super(gui.config(), version);
		this.gui = gui;
		this.defaults();
	}

	public abstract void defaults();
	
	public File create() {
		File gui = new File(this.base.getDataFolder(), "gui");
		File backups = new File(gui, this.name);

		if(backups.mkdirs() || backups.exists()) {

			if(!this.getConfig().exists()) return null;
			
				int files = backups.listFiles().length + 1;
				
				File backup = new File(backups, this.name + "(" + files + ")" + ".yml");
				
				final File tempConfig = this.getConfig();
				
				boolean success = this.getConfig().renameTo(backup);
				
				if(success) {
					BattlePets.Log("&a" + this.name + ".yml has been created!");	
				}else {
					BattlePets.Log("&cCouldnt create!");
				}
			
				return tempConfig;
			
		}else if(!backups.exists()) {
			BattlePets.Log("&cCouldnt create folder!");
		}
		
		return null;
	}

	
	
	@Override
	public void setup() {
		
		File path = new File(this.base.getDataFolder().getAbsolutePath(), "gui");	
		this.config = new File(path, this.name + ".yml");
		
		this.configuration = YamlConfiguration.loadConfiguration(config);
		
		if(!config.exists()) {
		
			this.configuration.set("identity.version", this.verison);
			
			for(ConfigItem i : this.items) {
				this.configuration.set(i.getPath(), i.getData());
			}
		}
		
		
		this.save();
		
	}

	public List<GuiItem> items(){
		return this.gui.configuration();
	}
	
}
