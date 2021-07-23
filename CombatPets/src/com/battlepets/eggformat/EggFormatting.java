package com.battlepets.eggformat;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.battlepets.attributes.GenericPetAttribute;
import com.battlepets.config.EggFormat;
import com.battlepets.eggmeta.EggStorage;
import com.battlepets.main.BattlePets;
import com.battlepets.petsdata.Pet;
import com.battlepets.petsdata.PointsAllocation;
import com.battlepets.utils.ComponentBuilder;
import com.battlepets.utils.MessageUtils;

public class EggFormatting {
	
	public String displayName;
	
	public Lore lore;
	public Position position;
	public Values values;
	
	public EggFormatting(String displayname, Lore lore, Position position, Values values) {
		this.displayName = displayname;
		this.lore = lore;
		this.position = position;
		this.values = values;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void setLore(Lore lore) {
		this.lore = lore;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Values getValues() {
		return values;
	}

	public void setValues(Values values) {
		this.values = values;
	}
	
	public boolean validate(ItemStack stack) {
	
		if(!stack.hasItemMeta()) return false;
		
		List<String> refrence = this.getLore(BattlePets.getInstance().configManager.pets.petsData.getAllPets().get(0).getDefaultAttributes());
		
		List<String> lore = stack.getItemMeta().getLore();
	
		
		String comparisonA = "";
		String comparisonB = "";
		
		try {
		
			for(String i : lore) {
				comparisonA += i.split(":")[0];
			}
			
			for(String i : refrence) {
				comparisonB += i.split(":")[0];
			}
			
			return comparisonA.equals(comparisonB);
			
		}catch (Exception e) {
			return false;
		}
		
	}
	
	public void getAttributes1(ItemStack stack) {
		
          List<FormatItem> keys = new ArrayList<FormatItem>();
		
		  FormatItem Entity = new FormatItem(this.lore.getEntity(), this.values.getEntity(), this.position.getEntity(), "%entity%");
	      FormatItem Level = new FormatItem(this.lore.getLevel(), this.values.getLevel(), this.position.getLevel(), "%level%"); 
	      FormatItem XP = new FormatItem(this.lore.getXp(), this.values.getXp(), this.position.getXp(), "%xp%"); 
	      FormatItem HP = new FormatItem(this.lore.getHp(), this.values.getHp(), this.position.getHp(), "%health%"); 
	      FormatItem Skillpoints = new FormatItem(this.lore.getSkillpoints(), this.values.getSkillpoints(), this.position.getSkillpoints(), "%skillpoints%"); 
	      FormatItem VitalityPoints = new FormatItem(this.lore.getVitality(), this.values.getVitality(), this.position.getVitality(), "%vitalitypoints%"); 
	      FormatItem DamagePoints = new FormatItem(this.lore.getDamage(), this.values.getDamage(), this.position.getDamage(), "%damagepoints%"); 
	      FormatItem DefensePoints = new FormatItem(this.lore.getDefense(), this.values.getDefense(), this.position.getDefense(), "%denfesepoints%"); 
	      FormatItem SpeedPoints = new FormatItem(this.lore.getSpeed(), this.values.getSpeed(), this.position.getSpeed(), "%speedpoints%");
	      
	      keys.add(Entity);
	      keys.add(Level);
	      keys.add(XP);
	      keys.add(HP);
	      keys.add(Skillpoints);
	      keys.add(VitalityPoints);
	      keys.add(DamagePoints);
	      keys.add(DefensePoints);
	      keys.add(SpeedPoints);
		
	      keys.sort((o1, o2) -> {
	    	  
	    	  if(o1.getPosition() > o2.getPosition()) {
	    		  return 1;
	    	  }
	    	  
	    	  if(o1.getPosition() < o2.getPosition()) {
	    		  return -1;
	    	  }
	    	  
	    	  return 0;
	    	  
	      });
	      
			List<String> lore = stack.getItemMeta().getLore();
			
			for(FormatItem i : keys) {
				for(int o = 0; o < lore.size(); o++) {
					if(lore.get(o).split(":")[0].equals(MessageUtils.translateAlternateColorCodes(i.getLore()))) {
						
						String tempValue = i.getValue();

						String value = lore.get(o).split(":")[1].trim();
						
						int subStringStart = tempValue.split(i.getPlaceholder())[0].length();
						int subStringEnd = value.length();

						System.out.println("Value: " + value + " length is " + value.length() + " trying to substring by " + subStringStart);
						
						value = value.substring(subStringStart);
						
						if(tempValue.split(i.getPlaceholder()).length > 1) {
							subStringEnd = tempValue.split(i.getPlaceholder())[1].length();	
							System.out.println("String with end: " + tempValue.split(i.getPlaceholder())[0] + " " + tempValue.split(i.getPlaceholder())[1]);
							System.out.println("Value: " + value + " length is " + value.length() + " trying to cut the end by " + subStringEnd);
							value = value.substring(0, value.length() - subStringEnd);
						}
						
						System.out.println(tempValue.split(i.getPlaceholder()).length + " ");
						System.out.println("" + subStringEnd + " : " + value.length());
						
						i.setValue(value);
						
						System.out.println("Set data [" + i.getLore() + "] to " + i.getValue());
					}
				}
			}

	}
	
	public GenericPetAttribute getAttributes(ItemStack stack) {
		
			List<String> lore = stack.getItemMeta().getLore();
			
			String entity = ChatColor.stripColor(lore.get(0).split(" ")[1]).trim();
			boolean baby = stack.getItemMeta().getDisplayName().toLowerCase().contains("baby");
			//int level = Integer.valueOf(ChatColor.stripColor(lore.get(1).split(" ")[1]).trim());
			double xp = Double.valueOf(ChatColor.stripColor(lore.get(2)).trim().split(" ")[1].split(" ")[0]);
			double health = Double.valueOf(ChatColor.stripColor(lore.get(3)).trim().split(" ")[1].split(" ")[0]);
			int skillpoints = Integer.valueOf(ChatColor.stripColor(lore.get(4).split(" ")[1]).trim());
			int vitality = Integer.valueOf(ChatColor.stripColor(lore.get(5).split(" ")[1]).trim());
			int damage = Integer.valueOf(ChatColor.stripColor(lore.get(6).split(" ")[1]).trim());
			int defense = Integer.valueOf(ChatColor.stripColor(lore.get(7).split(" ")[1]).trim());
			int speed = Integer.valueOf(ChatColor.stripColor(lore.get(8).split(" ")[1]).trim());
			
			String uuid = ChatColor.stripColor(lore.get(9).split(" ")[1]).trim();
			
			String displayName = stack.getItemMeta().getDisplayName();
			
			GenericPetAttribute pet = BattlePets.getInstance().configManager.pets.getPetsData().getPet(EntityType.valueOf(entity), baby).getDefaultAttributes();
			
			pet.setBaby(baby);
			pet.setXp(xp);
			pet.setHealth(health);
			pet.setPoints(skillpoints);
			pet.setPointsAllocation(new PointsAllocation(vitality, damage, defense, speed));
			pet.setName(displayName);
			
			pet.setStorage(BattlePets.getInstance().configManager.eggData.handler.getEgg(uuid));
			
			return pet;

	}
	
	public List<String> getLore(GenericPetAttribute pet){
		
		EggFormat format = BattlePets.getInstance().configManager.format;
	
		double xp = (double)pet.getXp();
		double maxxp = pet.getGeneric().getLevel().getRequiredXpForLevel(pet.getLevel());
		
		double health = (double)pet.getHealth();
		double maxhealth = pet.getGeneric().getBaseStats().getHp() + (pet.getGeneric().getSkillPoints().getAdditionPerStat().getHp() * pet.getPointsAllocation().getHp());
		
		if(pet.getStorage() == null) {
			pet.setStorage(new EggStorage("Key0", new ArrayList<ItemStack>()));
		}

		if(pet.isBaby()) {
			List<String> lore = ComponentBuilder.createLore(
					format.prefix + "Entity: " + format.activeValue + pet.getEntityType().name() + " (Baby)",
					format.prefix + "Level: " + format.activeValue + pet.getLevel(),
					format.prefix + "XP: " + format.value + xp + " &7- " + format.activeValue + maxxp,
					format.prefix + "HP: " +  format.value + health + " &7- " + format.activeValue + maxhealth,
					format.prefix + "Skillpoints: " + format.activeValue + pet.getPoints(),
					format.prefix + "Vitality: " + format.activeValue + (int)pet.getPointsAllocation().getHp(),
					format.prefix + "Strength: " + format.activeValue + (int)pet.getPointsAllocation().getDamage(),
					format.prefix + "Defense: " + format.activeValue + (int)pet.getPointsAllocation().getDefense(),
					format.prefix + "Dexterity: " + format.activeValue + (int)pet.getPointsAllocation().getSpeed(),
					format.prefix + "UUID: " + format.activeValue + pet.getStorage().getUuid() + ""
			);	

			return lore;
		}else {
			List<String> lore = ComponentBuilder.createLore(
					format.prefix + "Entity: " + format.activeValue + pet.getEntityType().name(),
					format.prefix + "Level: " + format.activeValue + pet.getLevel(),
					format.prefix + "XP: " + format.value + xp + " &7- " + format.activeValue + maxxp,
					format.prefix + "HP: " +  format.value + health + " &7- " + format.activeValue + maxhealth,
					format.prefix + "Skillpoints: " + format.activeValue + pet.getPoints(),
					format.prefix + "Vitality: " + format.activeValue + (int)pet.getPointsAllocation().getHp(),
					format.prefix + "Strength: " + format.activeValue + (int)pet.getPointsAllocation().getDamage(),
					format.prefix + "Defense: " + format.activeValue + (int)pet.getPointsAllocation().getDefense(),
					format.prefix + "Dexterity: " + format.activeValue + (int)pet.getPointsAllocation().getSpeed(),
					format.prefix + "UUID: " + format.activeValue + pet.getStorage().getUuid() + ""
			);	
			return lore;
		}
		
	}

	public ItemStack getSpawnEgg(EntityType entityType, boolean baby) {
		
		Pet pet = BattlePets.getInstance().configManager.pets.petsData.getPet(entityType, baby);
		
		String materialType = pet.getEntityType().name() + "_SPAWN_EGG";
		
		ItemStack spawnEgg = new ItemStack(Material.valueOf(materialType));
		
		ItemStack egg = new ItemStack(Material.valueOf(materialType));
		
		ItemMeta meta = egg.getItemMeta();
		
		List<String> lore = BattlePets.getInstance().configManager.eggStats.eggFormatting.getLore(pet.getDefaultAttributes());
		
		meta.setLore(lore);
		meta.setDisplayName(MessageUtils.translateAlternateColorCodes("&n" + pet.getDefaultName()));
		
		spawnEgg.setItemMeta(meta);
		
		return spawnEgg;
		
	}
	
	public ItemStack getSpawnEgg(GenericPetAttribute attributes) {
		
		Pet pet = BattlePets.getInstance().configManager.pets.petsData.getPet(attributes.getEntityType(), attributes.isBaby());
		
		pet.getDefaultAttributes().setStorage(attributes.getStorage());
		
		String materialType = pet.getEntityType().name() + "_SPAWN_EGG";
		
		ItemStack spawnEgg = new ItemStack(Material.valueOf(materialType));
		
		ItemStack egg = new ItemStack(Material.valueOf(materialType));
		
		ItemMeta meta = egg.getItemMeta();
		
		List<String> lore = BattlePets.getInstance().configManager.eggStats.eggFormatting.getLore(attributes);
		
		meta.setLore(lore);
		meta.setDisplayName(MessageUtils.translateAlternateColorCodes(attributes.getName()));
		
		spawnEgg.setItemMeta(meta);
		
		return spawnEgg;
		
	}
	
	public String getLoad(int len, double xp, double maxxp) {
		EggFormat format = BattlePets.getInstance().configManager.format;
		
		double amount = (len*xp)/maxxp;
		
		System.out.println(amount + " : " + len);
		
		String buffer = format.activeValue;
		
		String symbol = "=";
		
		int preLength = 0;
		
		for(int i = 0; i < amount; i++) {
			buffer+=symbol;
			preLength++;
			System.out.println("Added.");
		}
		
		buffer+=format.value;
		
		for(int i = preLength; i < len; i++) {
			System.out.println(i + "<" + len);
			buffer+=symbol;
		}
		
		return buffer;
		
	}
	
	public List<String> getLore1(GenericPetAttribute pet){
		
		List<String> lore = new ArrayList<String>();
		
		List<FormatItem> keys = new ArrayList<FormatItem>();
		
	    FormatItem Entity;
		
		if(pet.isBaby()) {
			Entity = new FormatItem(this.lore.getEntity(), this.values.getEntity() + " ( Baby )", this.position.getEntity()); 
		}else {
			Entity = new FormatItem(this.lore.getEntity(), this.values.getEntity(), this.position.getEntity()); 
		}
		
	      FormatItem Level = new FormatItem(this.lore.getLevel(), this.values.getLevel(), this.position.getLevel()); 
	      FormatItem XP = new FormatItem(this.lore.getXp(), this.values.getXp(), this.position.getXp()); 
	      FormatItem HP = new FormatItem(this.lore.getHp(), this.values.getHp(), this.position.getHp()); 
	      FormatItem Skillpoints = new FormatItem(this.lore.getSkillpoints(), this.values.getSkillpoints(), this.position.getSkillpoints()); 
	      FormatItem VitalityPoints = new FormatItem(this.lore.getVitality(), this.values.getVitality(), this.position.getVitality()); 
	      FormatItem DamagePoints = new FormatItem(this.lore.getDamage(), this.values.getDamage(), this.position.getDamage()); 
	      FormatItem DefensePoints = new FormatItem(this.lore.getDefense(), this.values.getDefense(), this.position.getDefense()); 
	      FormatItem SpeedPoints = new FormatItem(this.lore.getSpeed(), this.values.getSpeed(), this.position.getSpeed());
	      
	      keys.add(Entity);
	      keys.add(Level);
	      keys.add(XP);
	      keys.add(HP);
	      keys.add(Skillpoints);
	      keys.add(VitalityPoints);
	      keys.add(DamagePoints);
	      keys.add(DefensePoints);
	      keys.add(SpeedPoints);
		
	      keys.sort((o1, o2) -> {
	    	  
	    	  if(o1.getPosition() > o2.getPosition()) {
	    		  return 1;
	    	  }
	    	  
	    	  if(o1.getPosition() < o2.getPosition()) {
	    		  return -1;
	    	  }
	    	  
	    	  return 0;
	    	  
	      });
	      
	      keys.forEach(i -> {
	    	  
	    	  String value = i.getValue();
	    	  
	    	  value = value.replace("%entity%", pet.getEntityType().name());
	    	  value = value.replace("%level%", pet.getLevel() + "");
	    	  value = value.replace("%xp%", 0 + pet.getXp() + "");
	    	  value = value.replace("%maxxp%", pet.getGeneric().getLevel().getRequiredXpForLevel(pet.getLevel()) + "");
	    	  value = value.replace("%health%", pet.getHealth() + "");
	    	  value = value.replace("%maxhealth%", pet.getGeneric().getBaseStats().getHp() + (pet.getGeneric().getSkillPoints().getAdditionPerStat().getHp() * pet.getPointsAllocation().getHp()) + "");
	    	  value = value.replace("%skillpoints%", pet.getPoints() + "");
	    	  value = value.replace("%vitalitypoints%", (int)pet.getPointsAllocation().getHp() + "");
	    	  value = value.replace("%damagepoints%", (int)pet.getPointsAllocation().getDamage() + "");
	    	  value = value.replace("%denfesepoints%", (int)pet.getPointsAllocation().getDefense() + "");
	    	  value = value.replace("%speedpoints%", (int)pet.getPointsAllocation().getSpeed() + "");
	    	  
	    	  i.setValue(value);
	    	  
	      });
	      
	      keys.forEach(i -> {
	    	  String tempLore = i.getLore();
	    	  String tempValue = i.getValue();
	    	  lore.add(MessageUtils.translateAlternateColorCodes(tempLore + ": " + tempValue));  
	      });
	      
		return lore;
	}

}
