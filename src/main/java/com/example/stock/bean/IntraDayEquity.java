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
@Table(name="NIFTY_INTRADAY_OPTIONS")
public class IntraDayEquity extends EquityDerivative{

	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
	
	@Transient
	private IntraDayEquity prevEquity;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public IntraDayEquity getPrevEquity() {
		return prevEquity;
	}
	public void setPrevEquity(IntraDayEquity prevEquity) {
		this.prevEquity = prevEquity;
	}
	@Override
	public String toString() {
		return "NiftyEquityDerivative [id=" + id + ", prevEquity=" + prevEquity + "]";
	}
	
}
