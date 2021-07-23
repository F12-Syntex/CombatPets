package com.battlepets.petsdata;

import java.time.temporal.ChronoUnit;

public class Regeneration {

	private final ChronoUnit measure = ChronoUnit.SECONDS;
	private double rate;
	
	public Regeneration(double rate) {
		this.rate = rate;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public ChronoUnit getMeasure() {
		return measure;
	}
	
}
