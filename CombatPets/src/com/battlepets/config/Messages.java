package com.battlepets.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

import com.battlepets.utils.ComponentBuilder;
import com.battlepets.utils.MessageUtils;

public class Messages extends Config{

	public String prefix = "&c[&6CombatPets&c]";
	public String error = "%prefix% sorry an error has accured!";;
	public String invalid_syntax = "%prefix% &cInvalid syntax";
	public String invalid_permission = "%prefix% &cYou cant do that!";
	public String invalid_entitiy = "%prefix% &cplayers only!";
	public String invalid_help_command = "%prefix% &c%command% is not a command!";
	public String invalid_configure_command = "%prefix% &c%config% is not a valid config!";
	public List<String> help_format = ComponentBuilder.createLore("%prefix% &b%command%&7: &c%description%", "%prefix% &bpermissions&7: &c%permission%");

	
	
	
	//public String insufficient_balance = "%prefix% &6Sorry you dont have enough money!";
	//public String purchase_successful = "%prefix% &6Your pet has been added to your battle inventory!";
	
	public List<HashMap<String, String>> messages = new ArrayList<HashMap<String, String>>();
	
	public Map<Integer, String> key = new HashMap<Integer, String>();
	public List<String> data = new ArrayList<String>();

	public Messages(String name, double version) {
		super(name, version);
		
		this.items.add(new ConfigItem("Messages.prefix", prefix));
		this.items.add(new ConfigItem("Messages.error", error));
		this.items.add(new ConfigItem("Messages.invalid_syntax", invalid_syntax));
		this.items.add(new ConfigItem("Messages.invalid_permission", invalid_permission));
		this.items.add(new ConfigItem("Messages.invalid_entitiy", invalid_entitiy));
		this.items.add(new ConfigItem("Messages.help.invalid_command", invalid_help_command));
		this.items.add(new ConfigItem("Messages.configure.invalid_command", invalid_configure_command));
		this.items.add(new ConfigItem("Messages.help.command_help_format", help_format));
		
		
		//plugin_reload
		
		this.updateMessages("plugin_reload", "%prefix% &7CombatPets &chas reloaded.");
		this.updateMessages("duel_disconnected", "%prefix% &c%user% has disconnected.");
		this.updateMessages("pet_death", "%prefix% &c%pet% has died. You can revive the pet in /cp inventory.");
		this.updateMessages("pet_returned", "%prefix% &a has returned to your inventory.");
		this.updateMessages("pet_illegal_move", "%prefix% &cSorry you can't move this item.");
		this.updateMessages("deul_request", "%prefix% &aSent a battle request to %target%");
		this.updateMessages("deul_request_target", "%prefix% &a%player% wants to battle %target%");
		this.updateMessages("deul_request_target_confirm", "%prefix% &cType &aConfirm &cTo start the fight!");
		this.updateMessages("deul_request_canceled_activepet", "%prefix% &cBattle canceled due to you not having an active pet.");
		this.updateMessages("deul_request_canceled_target_removed_pet", "%prefix% &cBattle canceled due to %target%&c removing his pet.");
		this.updateMessages("deul_starting", "%prefix% &aBattle starting!");
		this.updateMessages("deul_request_canceled", "%prefix% &cRequest canceled!");
		this.updateMessages("request_timed_out", "%prefix% &cRequest timed out!");
		this.updateMessages("illegal_world_pet_use", "%prefix% &cYou can't have pets in this world!");
		this.updateMessages("pet_spawn_canceled_exists", "%prefix% &cYou can't have more then 1 active pet.");
		this.updateMessages("intraction_other_pet", "%prefix% &cThat's not your pet!");

		//this.items.add(new ConfigItem("Messages.shop.insufficient_balance", insufficient_balance));
		//this.items.add(new ConfigItem("Messages.shop.purchase_successful", purchase_successful));
		
		this.updateMessages("insufficient_balance", "%prefix% &6Sorry you dont have enough money!");
		this.updateMessages("purchase_successful", "%prefix% &6Your pet has been added to your battle inventory!");

		this.updateMessages("set_failed_hand", "%prefix% &cPlease hold a pet in your hand.");
		this.updateMessages("set_failed_level_args", "%prefix% &c/cp set level <number>");
		this.updateMessages("set_failed_points_args", "%prefix% &c/cp set points <number>");
		this.updateMessages("set_failed_number", "%prefix% &cThat's not a valid number.");
		this.updateMessages("set_modification_success", "%prefix% &aPet has successfully been modified.");
		this.updateMessages("set_points_gained", "%prefix% &aPet has recieved the points.");
		this.updateMessages("set_default_help", "%prefix% &c/cp set level/points {amount}");
		
		this.updateMessages("pet_revivied", "%prefix% &cPet has been successfully revived.");
		this.updateMessages("petInventory_full", "%prefix% &cYour inventory is full.");
		this.updateMessages("duel_surrendered_user", "%prefix% &cYou have surrendered.");
		this.updateMessages("duel_surrendered_target", "%prefix% &c%player% has surrendered.");
		this.updateMessages("pet_rename_message", "%prefix% &aType in the name of your pet. &7( &c'cancel'&a to cancel this action &7)");
		this.updateMessages("pet_rename_cancenled", "%prefix% &aNaming action has been canceled.");
		this.updateMessages("pet_rename_error_length", "%prefix% &cName length must not be over 30!");
		this.updateMessages("pet_challenge", "%prefix% &cRight click a pet you wish to battle.");
		this.updateMessages("pet_return_inventory", "%prefix% &aAdded pet to your inventory!");
		this.updateMessages("pet_heal_error_maxhealth", "%prefix% &cYour pet already has max health.");
		this.updateMessages("pet_heal", "%prefix% &cYour pet now has &7%health% &chealth");
		this.updateMessages("pet_skills_saved", "%prefix% &aSkills successfully saved.");
		
		
		this.items.add(new ConfigItem("Messages.contents", messages));
		
	}
	
	private void updateMessages(String key, String data) {
		HashMap<String, String> message = new HashMap<String, String>();
		message.put(key, data);
		this.messages.add(message);
	}
	

	@Override
	public Configuration configuration() {
		// TODO Auto-generated method stub
		return Configuration.MESSAGES;
	}
	
	@Override
	public void initialize() {
		this.prefix = MessageUtils.translateAlternateColorCodes(this.getConfiguration().getString("Messages.prefix"));
		this.error = MessageUtils.translateAlternateColorCodes(this.getConfiguration().getString("Messages.error").replace("%prefix%", prefix));
		this.invalid_syntax = MessageUtils.translateAlternateColorCodes(this.getConfiguration().getString("Messages.invalid_syntax").replace("%prefix%", prefix));
		this.invalid_permission = MessageUtils.translateAlternateColorCodes(this.getConfiguration().getString("Messages.invalid_permission").replace("%prefix%", prefix));
		this.invalid_entitiy = MessageUtils.translateAlternateColorCodes(this.getConfiguration().getString("Messages.invalid_entitiy").replace("%prefix%", prefix));
		this.invalid_help_command = MessageUtils.translateAlternateColorCodes(this.getConfiguration().getString("Messages.help.invalid_command").replace("%prefix%", prefix));
		this.help_format = ComponentBuilder.createLore(this.getConfiguration().getStringList("Messages.help.command_help_format"));
		this.invalid_configure_command = MessageUtils.translateAlternateColorCodes(this.getConfiguration().getString("Messages.configure.invalid_command").replace("%prefix%", prefix));
		
		//this.insufficient_balance = MessageUtils.translateAlternateColorCodes(this.getConfiguration().getString("Messages.shop.insufficient_balance").replace("%prefix%", prefix));
		//this.purchase_successful = MessageUtils.translateAlternateColorCodes(this.getConfiguration().getString("Messages.shop.purchase_successful").replace("%prefix%", prefix));
	
		List<Map<?, ?>> mapping = this.getConfiguration().getMapList("Messages.contents");
		
		for(Map<?, ?> i : mapping) {
			String key = String.valueOf(i.keySet().stream().findFirst().get());
			String data = i.get(key).toString();
			this.data.add(MessageUtils.translateAlternateColorCodes(data).replace("%prefix%", prefix));
			int index = (this.data.size() - 1);
			this.key.put(index, key);
		}
		
	
	}
	
	
	public void send(Player player, String key) {
		MessageUtils.sendRawMessage(player, this.getMessage(key));
	}
	
	public String getMessage(String key) {
		
		for(Integer i : this.key.keySet()) {
			if(this.key.get(i).equalsIgnoreCase(key)) {
				return this.data.get(i);
			}
		}

		String data = this.prefix + " &cPlease update your config for the setting &7[&c" + key + "&7]";
		
		HashMap<String, String> message = new HashMap<String, String>();
		message.put(key, data);
		List<Map<?, ?>> mapping = this.getConfiguration().getMapList("Messages.contents");
		mapping.add(message);
		this.getConfiguration().set("Messages.contents", mapping);
		this.save();
		
		this.data.add(data);
		this.key.put(this.key.size(), key);
		
		return MessageUtils.translateAlternateColorCodes(data);
	}

	
}
