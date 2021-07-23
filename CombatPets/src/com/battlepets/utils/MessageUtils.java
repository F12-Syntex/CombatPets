package com.battlepets.utils;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import com.battlepets.main.BattlePets;

public class MessageUtils {

	public static String translateAlternateColorCodes(String s) {
		return ChatColor.translateAlternateColorCodes('&', MessageUtils.convertColors(s));
	}
	
	public static String convertColors(String s) {
        Pattern pattern = Pattern.compile(Pattern.quote("{#") + "(.*?)" +  Pattern.quote("}"));
        Matcher match = pattern.matcher(s);
        String ns = s;
        while (match.find()) {
            String colorcode = match.group(1);
            ns = ns.replaceAll("\\{#" + colorcode + "\\}", MessageUtils.getColor("#"+colorcode).toString());
        }
 
        return ns;
}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static ChatColor getColor(String s) {
        try {    	
		Class c = ChatColor.class;
		Method m = c.getMethod("of", String.class);
        Object o = m.invoke(null, s);
        return (ChatColor)o;
 
        }catch (Exception e) {
            return ChatColor.WHITE;
        }
    }
	
	public static void sendRawMessage(Player player, String s) {
		player.sendMessage(MessageUtils.translateAlternateColorCodes(s));
	}
	
	public static void inform(Player player, String s) {
		player.sendMessage(BattlePets.getInstance().configManager.messages.prefix + MessageUtils.translateAlternateColorCodes(s));
	}
	
	public static void sendMessage(Player player, String s) {
		player.sendMessage(BattlePets.getInstance().configManager.messages.prefix + " " + MessageUtils.translateAlternateColorCodes(s));
	}

	public static void sendMessage(HumanEntity player, String s) {
		player.sendMessage(BattlePets.getInstance().configManager.messages.prefix + " " + MessageUtils.translateAlternateColorCodes(s));
	}
	
	public static void sendConsoleMessage(String msg){
		  Bukkit.getConsoleSender().sendMessage(MessageUtils.translateAlternateColorCodes(msg));
	}
	public static void sendConsoleMessage(String[] msg){
		for(int i = 0; i < msg.length; i++)
		System.out.println(MessageUtils.translateAlternateColorCodes(msg[i]));
	}
	public static void sendHelp(Player player) {
		 BattlePets.getInstance().CommandManager.getCommands().forEach(i -> {
			 MessageUtils.sendRawMessage(player,BattlePets.getInstance().configManager.messages.prefix + " " + "&6• /" + BattlePets.getInstance().CommandManager.main[0] + " " + i.name() + "&7 : &e" + i.info());
		 });
	}
}
