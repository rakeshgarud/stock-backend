package com.example.stock.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Stock {
	
	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
	@Column
	private String name;
	@Column
	private Date date;
	@Column
	private double prevClose;
	@Column
	private double closePrice;
	@Column
	private double  ttlTradeQty;
	@Column
	private double  deliveryQty;
	@Column
	private double  deliveryPer;
	
	public Stock(String name, double value) {
		super();
		this.name = name;
	}
	
	public Stock() {
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getPrevClose() {
		return prevClose;
	}

	public void setPrevClose(double prevClose) {
		this.prevClose = prevClose;
	}

	public double getClosePrice() {
		return closePrice;
	}

	public void setClosePrice(double closePrice) {
		this.closePrice = closePrice;
	}

	public double getTtlTradeQty() {
		return ttlTradeQty;
	}

	public void setTtlTradeQty(double ttlTradeQty) {
		this.ttlTradeQty = ttlTradeQty;
	}

	public double getDeliveryQty() {
		return deliveryQty;
	}

	public void setDeliveryQty(double deliveryQty) {
		this.deliveryQty = deliveryQty;
	}

	public double getDeliveryPer() {
		return deliveryPer;
	}

	public void setDeliveryPer(double deliveryPer) {
		this.deliveryPer = deliveryPer;
	}

	@Override
	public String toString() {
		return "Stock [name=" + name + ", date=" + date + ", prevClose=" + prevClose
				+ ", closePrice=" + closePrice + ", ttlTradeQty=" + ttlTradeQty + ", deliveryQty=" + deliveryQty
				+ ", deliveryPer=" + deliveryPer + "]";
	}
}
