package com.battlepets.utils;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class Direction {
	
	public static Location closest(Entity source, Entity target, double distance) {
		
		double closestX = source.getLocation().getX();
		double closestZ = source.getLocation().getZ();
		
		if(source.getLocation().getBlockX() < target.getLocation().getBlockX()) {
			for(int x = target.getLocation().getBlockX(); x > source.getLocation().getBlockX(); x--) {
				if((x - source.getLocation().getBlockX()) <= distance) {
					closestX = x;
				}
			}	
		}else {
			for(int x = target.getLocation().getBlockX(); x < source.getLocation().getBlockX(); x++) {
				if((source.getLocation().getBlockX() - x) <= distance) {
					closestX = x;
				}
			}
		}
		
		
		if(source.getLocation().getBlockZ() < target.getLocation().getBlockZ()) {
			for(int z = target.getLocation().getBlockZ(); z > source.getLocation().getBlockZ(); z--) {
				if((z - source.getLocation().getBlockZ()) <= distance) {
					closestZ = z;
				}
			}	
		}else {
			for(int z = target.getLocation().getBlockZ(); z < source.getLocation().getBlockZ(); z++) {
				if((source.getLocation().getBlockZ() - z) <= distance) {
					closestZ = z;
				}
			}
		}
		
		Location location = source.getLocation();
		
		location.setX(closestX);
		location.setZ(closestZ);
		
		return location;
	}

}
