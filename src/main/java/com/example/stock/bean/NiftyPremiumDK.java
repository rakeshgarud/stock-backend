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
@Table(name="NIFTY_PREMIUMDK_OPTIONS")
public class NiftyPremiumDK extends EquityDerivative{

	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
	
	@Transient
	private NiftyPremiumDK prevEquity;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public NiftyPremiumDK getPrevEquity() {
		return prevEquity;
	}
	public void setPrevEquity(NiftyPremiumDK prevEquity) {
		this.prevEquity = prevEquity;
	}
	@Override
	public String toString() {
		return "NiftyEquityDerivative [id=" + id + ", prevEquity=" + prevEquity + "]";
	}
	
}
