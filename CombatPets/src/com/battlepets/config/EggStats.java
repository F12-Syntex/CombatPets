package com.battlepets.config;

import com.battlepets.eggformat.EggFormatting;
import com.battlepets.eggformat.Lore;
import com.battlepets.eggformat.Position;
import com.battlepets.eggformat.Values;

public class EggStats extends Config{
	
	public EggFormatting eggFormatting;

	public EggStats(String name, double version) {
		super(name, version);
		
		this.items.add(new ConfigItem("Eggstats.layout.displayName", "%name%"));
		this.items.add(new ConfigItem("Eggstats.layout.Lore.Entity", "&6Entity"));
		this.items.add(new ConfigItem("Eggstats.layout.Lore.Level", "&6Level"));
		this.items.add(new ConfigItem("Eggstats.layout.Lore.XP", "&6XP"));
		this.items.add(new ConfigItem("Eggstats.layout.Lore.HP", "&6HP"));
		this.items.add(new ConfigItem("Eggstats.layout.Lore.Skillpoints", "&6Skillpoints"));
		this.items.add(new ConfigItem("Eggstats.layout.Lore.VitalityPoints", "&6Vitality"));
		this.items.add(new ConfigItem("Eggstats.layout.Lore.DamagePoints", "&6Damage"));
		this.items.add(new ConfigItem("Eggstats.layout.Lore.DefensePoints", "&6Defense"));
		this.items.add(new ConfigItem("Eggstats.layout.Lore.SpeedPoints", "&6Speed"));
		
		this.items.add(new ConfigItem("Eggstats.layout.Values.Entity", "&e%entity%"));
		this.items.add(new ConfigItem("Eggstats.layout.Values.Level", "&e%level%"));
		this.items.add(new ConfigItem("Eggstats.layout.Values.XP", "&e%xp% - %maxxp%"));
		this.items.add(new ConfigItem("Eggstats.layout.Values.HP", "&e%health% - %maxhealth%"));
		this.items.add(new ConfigItem("Eggstats.layout.Values.Skillpoints", "&e%skillpoints%"));
		this.items.add(new ConfigItem("Eggstats.layout.Values.VitalityPoints", "&e%vitalitypoints%"));
		this.items.add(new ConfigItem("Eggstats.layout.Values.DamagePoints", "&e%damagepoints%"));
		this.items.add(new ConfigItem("Eggstats.layout.Values.DefensePoints", "&e%denfesepoints%"));
		this.items.add(new ConfigItem("Eggstats.layout.Values.SpeedPoints", "&e%speedpoints%"));
		
		this.items.add(new ConfigItem("Eggstats.layout.Position.Entity", 1));
		this.items.add(new ConfigItem("Eggstats.layout.Position.Level", 2));
		this.items.add(new ConfigItem("Eggstats.layout.Position.XP", 3));
		this.items.add(new ConfigItem("Eggstats.layout.Position.HP", 4));
		this.items.add(new ConfigItem("Eggstats.layout.Position.Skillpoints", 5));
		this.items.add(new ConfigItem("Eggstats.layout.Position.VitalityPoints", 6));
		this.items.add(new ConfigItem("Eggstats.layout.Position.DamagePoints", 7));
		this.items.add(new ConfigItem("Eggstats.layout.Position.DefensePoints", 8));
		this.items.add(new ConfigItem("Eggstats.layout.Position.SpeedPoints", 9));

	}

	@Override
	public Configuration configuration() {
		// TODO Auto-generated method stub
		return Configuration.PETS;
	}
	
	@Override
	public void initialize() {
		
		String displayName = this.getConfiguration().getString("Eggstats.layout.displayName");
		
	    String EntityLore = this.getConfiguration().getString("Eggstats.layout.Lore.Entity");
	    String LevelLore = this.getConfiguration().getString("Eggstats.layout.Lore.Level");
	    String XPLore = this.getConfiguration().getString("Eggstats.layout.Lore.XP");
	    String HPLore = this.getConfiguration().getString("Eggstats.layout.Lore.HP");
	    String SkillpointsLore = this.getConfiguration().getString("Eggstats.layout.Lore.Skillpoints");
	    String VitalityPointsLore = this.getConfiguration().getString("Eggstats.layout.Lore.VitalityPoints");
	    String DamagePointsLore = this.getConfiguration().getString("Eggstats.layout.Lore.DamagePoints");
	    String DefensePointsLore = this.getConfiguration().getString("Eggstats.layout.Lore.DefensePoints");
	    String SpeedPointsLore = this.getConfiguration().getString("Eggstats.layout.Lore.SpeedPoints");
	    
	    String EntityValues = this.getConfiguration().getString("Eggstats.layout.Values.Entity");
	    String LevelValues = this.getConfiguration().getString("Eggstats.layout.Values.Level");
	    String XPValues = this.getConfiguration().getString("Eggstats.layout.Values.XP");
	    String HPValues = this.getConfiguration().getString("Eggstats.layout.Values.HP");
	    String SkillpointsValues = this.getConfiguration().getString("Eggstats.layout.Values.Skillpoints");
	    String VitalityPointsValues = this.getConfiguration().getString("Eggstats.layout.Values.VitalityPoints");
	    String DamagePointsValues = this.getConfiguration().getString("Eggstats.layout.Values.DamagePoints");
	    String DefensePointsValues = this.getConfiguration().getString("Eggstats.layout.Values.DefensePoints");
	    String SpeedPointsValues = this.getConfiguration().getString("Eggstats.layout.Values.SpeedPoints");
	    
	    int EntityPosition = this.getConfiguration().getInt("Eggstats.layout.Position.Entity");
	    int LevelPosition = this.getConfiguration().getInt("Eggstats.layout.Position.Level");
	    int XPPosition = this.getConfiguration().getInt("Eggstats.layout.Position.XP");
	    int HPPosition = this.getConfiguration().getInt("Eggstats.layout.Position.HP");
	    int SkillpointsPosition = this.getConfiguration().getInt("Eggstats.layout.Position.Skillpoints");
	    int VitalityPointsPosition = this.getConfiguration().getInt("Eggstats.layout.Position.VitalityPoints");
	    int DamagePointsPosition = this.getConfiguration().getInt("Eggstats.layout.Position.DamagePoints");
	    int DefensePointsPosition = this.getConfiguration().getInt("Eggstats.layout.Position.DefensePoints");
	    int SpeedPointsPosition = this.getConfiguration().getInt("Eggstats.layout.Position.SpeedPoints");
	    
	    Lore lore = new Lore(EntityLore, LevelLore, XPLore, HPLore, SkillpointsLore, VitalityPointsLore, DamagePointsLore, DefensePointsLore, SpeedPointsLore);
	    Values values = new Values(EntityValues, LevelValues, XPValues, HPValues, SkillpointsValues, VitalityPointsValues, DamagePointsValues, DefensePointsValues, SpeedPointsValues);
	    Position position = new Position(EntityPosition, LevelPosition, XPPosition, HPPosition, SkillpointsPosition, VitalityPointsPosition, DamagePointsPosition, DefensePointsPosition, SpeedPointsPosition);
		  
	    EggFormatting formatting = new EggFormatting(displayName, lore, position, values);
	    
		this.eggFormatting = formatting;
	}
	
}
