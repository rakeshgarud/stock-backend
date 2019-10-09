package com.example.stock.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name="STOCKS_OPTIONS")
public class StockOptionsEquity extends EquityDerivative{

	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
	
	@Transient
	private StockOptionsEquity prevEquity;
	
	
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
	@Override
	public String toString() {
		return "NiftyEquityDerivative [id=" + id + ", prevEquity=" + prevEquity + "]";
	}
	
}
