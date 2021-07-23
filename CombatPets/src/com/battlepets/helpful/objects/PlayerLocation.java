package com.battlepets.helpful.objects;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;

import com.battlepets.attributes.GenericPetAttribute;

public class PlayerLocation {

	public Player player;
	public Location location;
	public GenericPetAttribute data;
	public EquipmentSlot hand;
	
	public PlayerLocation(Player player, Location location, GenericPetAttribute data, EquipmentSlot hand) {
		this.player = player;
		this.location = location;
		this.data = data;
		this.hand = hand;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public GenericPetAttribute getData() {
		return data;
	}

	public void setData(GenericPetAttribute data) {
		this.data = data;
	}

	public EquipmentSlot getHand() {
		return hand;
	}

	public void setHand(EquipmentSlot hand) {
		this.hand = hand;
	}
	
	
}
