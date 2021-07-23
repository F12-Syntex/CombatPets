package com.battlepets.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.entity.Player;

import com.battlepets.main.BattlePets;

public class Communication {
	
	public static Map<Player, Input> players = new HashMap<Player, Input>();
	
	public static void applyInput(Player player, Input execution, int delay) {
		players.put(player, execution);
		
		Timer timer = new Timer();
	
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if(players.containsKey(player)) {
					MessageUtils.sendRawMessage(player, BattlePets.getInstance().configManager.messages.prefix + " &cOperation timed out!");
					players.remove(player);	
				}
			}
		}, delay);
		
		
		
	}
	
	public static void applyInput(Player player, Input execution, int delay, Runnable timedOut) {
		players.put(player, execution);
		
		Timer timer = new Timer();
	
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if(players.containsKey(player)) {
					BattlePets.getInstance().getServer().getScheduler().runTask(BattlePets.getInstance(), timedOut);
					players.remove(player);	
				}
			}
		}, delay);
		
		
		
	}
	
	
	

}
