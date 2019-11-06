package com.example.stock.bean;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name="STOCKS_OPTIONS_EOD")
public class StockOptionsEquity extends EquityDerivative{

	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
	
	@Transient
	private StockOptionsEquity prevEquity;
	
	@Transient
	private StockOptionsEquity put;
	
	public StockOptionsEquity() {
		super();
	}
	public StockOptionsEquity(long id,String symbol, double oi, double chnginOI, double volume, double iv, double ltp,
			double netChng, double bidQty, double bidPrice, double askPrice, double askQty, double strikePrice,
			Date date, int type, int rowNo, String expiryDate, double currentPrice,StockOptionsEquity put) {
		
		super( symbol,  oi,  chnginOI,  volume,  iv,  ltp,
			 netChng,  bidQty,  bidPrice,  askPrice,  askQty,  strikePrice,
			 date,  type,  rowNo,  expiryDate,  currentPrice);
		this.id=id;
		this.put = put;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public StockOptionsEquity getPrevEquity() {
		return prevEquity;
	}
	public void setPrevEquity(StockOptionsEquity prevEquity) {
		this.prevEquity = prevEquity;
	}
	
	public StockOptionsEquity getPut() {
		return put;
	}
	public void setPut(StockOptionsEquity put) {
		this.put = put;
	}
	@Override
	public String toString() {
		return "NiftyEquityDerivative [id=" + id + ", prevEquity=" + prevEquity + "]";
	}
	
}
