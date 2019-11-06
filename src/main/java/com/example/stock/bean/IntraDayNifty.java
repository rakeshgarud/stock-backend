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
@Table(name="NIFTY_INTRADAY_OPTIONS")
public class IntraDayNifty extends EquityDerivative{

	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
	
	@Transient
	private IntraDayNifty prevEquity;
	
	@Transient
	private IntraDayNifty put;
	
	public IntraDayNifty() {
		super();
	}
	public IntraDayNifty(long id,String symbol, double oi, double chnginOI, double volume, double iv, double ltp,
			double netChng, double bidQty, double bidPrice, double askPrice, double askQty, double strikePrice,
			Date date, int type, int rowNo, String expiryDate, double currentPrice,IntraDayNifty put) {
		
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
	public IntraDayNifty getPrevEquity() {
		return prevEquity;
	}
	public void setPrevEquity(IntraDayNifty prevEquity) {
		this.prevEquity = prevEquity;
	}
	
	public IntraDayNifty getPut() {
		return put;
	}
	public void setPut(IntraDayNifty put) {
		this.put = put;
	}
	@Override
	public String toString() {
		return "NiftyEquityDerivative [id=" + id + ", prevEquity=" + prevEquity + "]";
	}
	
}
