package com.battlepets.eggformat;

public class FormatItem {
	
	private String placeholder = "";
	private String lore;
	private String value;
	private int position;
	
	public FormatItem(String lore, String value, int position) {
		this.lore = lore;
		this.value = value;
		this.position = position;
	}
	
	public FormatItem(String lore, String value, int position, String placeholder) {
		this.lore = lore;
		this.value = value;
		this.position = position;
		this.setPlaceholder(placeholder);
	}

	public String getLore() {
		return lore;
	}

	public void setLore(String lore) {
		this.lore = lore;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getPlaceholder() {
		return placeholder;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}

}
