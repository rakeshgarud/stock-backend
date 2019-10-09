package com.example.stock.enums;

public enum Column {

	CALL(1), PUT(2);
	
    private int column;
    
    private Column(int column) {
    	this.column = column;
    }
    
    public int getColumn() {
    return column;
    }
}
