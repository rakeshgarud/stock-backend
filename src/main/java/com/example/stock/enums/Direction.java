package com.example.stock.enums;

public enum Direction {

	UP(1), DOWN(2);
	
    private int direction;
    
    private Direction(int direction) {
    	this.direction = direction;
    }
    
    public int getErrorCode() {
    return direction;
    }
}