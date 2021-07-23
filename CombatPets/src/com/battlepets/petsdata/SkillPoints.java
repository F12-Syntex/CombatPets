package com.battlepets.petsdata;

public class SkillPoints {

	private int points;
	private PointsAllocation allocation;
	private MaxAllocation maxAllocation;
	
	public SkillPoints(int points, PointsAllocation allocation, MaxAllocation maxAllocation) {
		this.points = points;
		this.allocation = allocation;
		this.maxAllocation = maxAllocation;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public PointsAllocation getAdditionPerStat() {
		return allocation;
	}

	public void setAllocation(PointsAllocation allocation) {
		this.allocation = allocation;
	}

	public MaxAllocation getMaxAllocation() {
		return maxAllocation;
	}

	public void setMaxAllocation(MaxAllocation maxAllocation) {
		this.maxAllocation = maxAllocation;
	}
	
}
