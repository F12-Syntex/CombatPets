package com.battlepets.utils;

import java.util.Optional;

public class StringMinipulation {
	
	public static String removeLastCharOptional(String s) {
	    return Optional.ofNullable(s)
	      .filter(str -> str.length() != 0)
	      .map(str -> str.substring(0, str.length() - 1))
	      .orElse(s);
	    }
	
	public static String capitalize(String word) {
		String str = word.toLowerCase();
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

}
