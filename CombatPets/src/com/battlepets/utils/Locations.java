package com.battlepets.utils;

import org.bukkit.Location;

public class Locations {

	public static boolean inWater(Location location) {
		return location.getBlock().isLiquid();
	}
	
}
