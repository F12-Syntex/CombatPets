package com.battlepets.battle;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class BattleRequests {

	private List<Request> requests;
	private List<Confirmation> confirmations;
	
	public BattleRequests() {
		this.requests = new ArrayList<Request>();
		this.confirmations = new ArrayList<Confirmation>();
	}
	
	public void addRequest(Request uuid) {
		if(this.contains(uuid.getOwner())) {
			this.requests = this.requests.stream().filter(i -> i.getOwner().compareTo(uuid.getOwner()) != 0).collect(Collectors.toList());
		}
		this.requests.add(uuid);
	}
	
	public void removeRequest(Request uuid) {
		this.requests.remove(uuid);
	}
	
	public void removeConfirmation(Confirmation uuid) {
		this.confirmations.remove(uuid);
	}
	
	public boolean contains(UUID uuid) {
		for(Request i : requests) {
			if(i.getOwner().compareTo(uuid) == 0) return true;
		}
		return false;
	}
	
	public boolean hasConfirmation(UUID uuid) {
		for(Request i : requests) {
			if(i.getOwner().compareTo(uuid) == 0) return true;
		}
		return false;
	}
	
	public Confirmation getConfirmation(UUID uuid) {
		for(Confirmation i : confirmations) {
			if(i.getOwner().compareTo(uuid) == 0) return i;
		}
		return null;
	}
	
	public Request getRequest(UUID uuid) {
		return this.requests.stream().filter(i -> i.getOwner().compareTo(uuid) == 0).findFirst().get();
	}
	
	public void sendConfirmation(Confirmation uuid) {
		if(this.confirmations.stream().filter(i -> i.getOwner().compareTo(uuid.getOwner()) == 0).count() > 0) {
			this.confirmations = this.confirmations.stream().filter(i -> i.getOwner().compareTo(uuid.getOwner()) != 0).collect(Collectors.toList());
		}
		this.confirmations.add(uuid);
	}
	
}
