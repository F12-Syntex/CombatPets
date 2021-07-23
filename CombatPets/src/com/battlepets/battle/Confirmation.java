package com.battlepets.battle;

import java.util.UUID;

public class Confirmation {
	
	private UUID owner;
	private Request request;
	
	public Confirmation(UUID owner, Request request) {
		this.owner = owner;
		this.setRequest(request);
	}

	public UUID getOwner() {
		return owner;
	}

	public void setOwner(UUID owner) {
		this.owner = owner;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

}
