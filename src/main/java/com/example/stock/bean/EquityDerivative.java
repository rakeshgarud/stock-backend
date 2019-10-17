package com.example.stock.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@MappedSuperclass
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class EquityDerivative {

	@Column
	private String symbol;
	@Column
	private double oi;
	@Column
	private double chnginOI;
	@Column
	private double volume;
	@Column
	private double iv;
	@Column
	private double ltp;
	@Column
	private double netChng;
	@Column
	private double bidQty;
	@Column
	private double bidPrice;
	@Column
	private double askPrice;
	@Column
	private double askQty;
	@Column
	private double strikePrice;
	@Column
	private Date date;
	@Column
	private int type;
	@Column
	private int rowNo;
	
	@Column
	private double postionsVol;
	
	@Column
	private double currentPrice;
	
	@Transient
	private double chnginDif;
	
	@Transient
	private double volumeDif;
	
	@Transient
	private EquityDerivative prevEquity;
	
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public double getOi() {
		return oi;
	}
	public void setOi(double oi) {
		this.oi = oi;
	}
	public double getChnginOI() {
		return chnginOI;
	}
	public void setChnginOI(double changeInOI) {
		this.chnginOI = changeInOI;
	}
	public double getVolume() {
		return volume;
	}
	public void setVolume(double volume) {
		this.volume = volume;
	}
	public double getIv() {
		return iv;
	}
	public void setIv(double iv) {
		this.iv = iv;
	}
	public double getLtp() {
		return ltp;
	}
	public void setLtp(double ltp) {
		this.ltp = ltp;
	}
	public double getNetChng() {
		return netChng;
	}
	public void setNetChng(double netChng) {
		this.netChng = netChng;
	}
	public double getBidQty() {
		return bidQty;
	}
	public void setBidQty(double bidQty) {
		this.bidQty = bidQty;
	}
	public double getBidPrice() {
		return bidPrice;
	}
	public void setBidPrice(double bidPrice) {
		this.bidPrice = bidPrice;
	}
	public double getAskPrice() {
		return askPrice;
	}
	public void setAskPrice(double askPrice) {
		this.askPrice = askPrice;
	}
	public double getAskQty() {
		return askQty;
	}
	public void setAskQty(double askQty) {
		this.askQty = askQty;
	}
	public double getStrikePrice() {
		return strikePrice;
	}
	public void setStrikePrice(double strikePrice) {
		this.strikePrice = strikePrice;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getRowNo() {
		return rowNo;
	}
	public void setRowNo(int row) {
		this.rowNo = row;
	}
	public EquityDerivative getPrevEquity() {
		return prevEquity;
	}
	public void setPrevEquity(EquityDerivative prevEquity) {
		this.prevEquity = prevEquity;
	}
	
	public double getPostionsVol() {
		return postionsVol;
	}
	public void setPostionsVol(double postionsVol) {
		this.postionsVol = postionsVol;
	}
	
	public double getChnginDif() {
		return chnginDif;
	}
	public void setChnginDif(double chnginDif) {
		this.chnginDif = chnginDif;
	}
	public double getVolumeDif() {
		return volumeDif;
	}
	public void setVolumeDif(double volumeDif) {
		this.volumeDif = volumeDif;
	}
	
	public double getCurrentPrice() {
		return currentPrice;
	}
	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}
	@Override
	public String toString() {
		return "EquityDerivative [oi=" + oi + ", changeInOI=" + chnginOI + ", volume=" + volume + ", iv=" + iv
				+ ", ltp=" + ltp + ", netChng=" + netChng + ", bidQty=" + bidQty + ", bidPrice=" + bidPrice
				+ ", askPrice=" + askPrice + ", askQty=" + askQty + ", strikePrice=" + strikePrice + ", date=" + date
				+ ", type=" + type + ", rowNo=" + rowNo +  ", postionsVol=" + postionsVol +"]";
	}
	
}
