package com.example.stock.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ExchangeActivity {

	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
	@Column
	private String clientType;
	@Column
	private Double futureIdxLong;
	@Column
	private Double futureIdxShort;
	@Column
	private Double futureStockLong;
	@Column
	private Double futureStockShort;
	@Column
	private Double optionIdxCallLong;
	@Column
	private Double optionIdxPutLong;
	@Column
	private Double optionIdxCallShort;
	@Column
	private Double optionIdxPutShort;
	@Column
	private Double optionStockCallLong;
	@Column
	private Double optionStockPutLong;
	@Column
	private Double optionStockCallShort;
	@Column
	private Double optionStockPutShort;
	@Column
	private Double totalLongContracts;
	@Column
	private Double totalShortContracts;
	@Column
	private Date date;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getClientType() {
		return clientType;
	}
	public void setClientType(String clientType) {
		this.clientType = clientType;
	}
	public Double getFutureIdxLong() {
		return futureIdxLong;
	}
	public void setFutureIdxLong(Double futureIdxLong) {
		this.futureIdxLong = futureIdxLong;
	}
	public Double getFutureIdxShort() {
		return futureIdxShort;
	}
	public void setFutureIdxShort(Double futureIdxShort) {
		this.futureIdxShort = futureIdxShort;
	}
	public Double getFutureStockLong() {
		return futureStockLong;
	}
	public void setFutureStockLong(Double futureStockLong) {
		this.futureStockLong = futureStockLong;
	}
	public Double getFutureStockShort() {
		return futureStockShort;
	}
	public void setFutureStockShort(Double futureStockShort) {
		this.futureStockShort = futureStockShort;
	}
	public Double getOptionIdxCallLong() {
		return optionIdxCallLong;
	}
	public void setOptionIdxCallLong(Double optionIdxCallLong) {
		this.optionIdxCallLong = optionIdxCallLong;
	}
	public Double getOptionIdxPutLong() {
		return optionIdxPutLong;
	}
	public void setOptionIdxPutLong(Double optionIdxPutLong) {
		this.optionIdxPutLong = optionIdxPutLong;
	}
	public Double getOptionIdxCallShort() {
		return optionIdxCallShort;
	}
	public void setOptionIdxCallShort(Double optionIdxCallShort) {
		this.optionIdxCallShort = optionIdxCallShort;
	}
	public Double getOptionIdxPutShort() {
		return optionIdxPutShort;
	}
	public void setOptionIdxPutShort(Double optionIdxPutShort) {
		this.optionIdxPutShort = optionIdxPutShort;
	}
	public Double getOptionStockCallLong() {
		return optionStockCallLong;
	}
	public void setOptionStockCallLong(Double optionStockCallLong) {
		this.optionStockCallLong = optionStockCallLong;
	}
	public Double getOptionStockPutLong() {
		return optionStockPutLong;
	}
	public void setOptionStockPutLong(Double optionStockPutLong) {
		this.optionStockPutLong = optionStockPutLong;
	}
	public Double getOptionStockCallShort() {
		return optionStockCallShort;
	}
	public void setOptionStockCallShort(Double optionStockCallShort) {
		this.optionStockCallShort = optionStockCallShort;
	}
	public Double getOptionStockPutShort() {
		return optionStockPutShort;
	}
	public void setOptionStockPutShort(Double optionStockPutShort) {
		this.optionStockPutShort = optionStockPutShort;
	}
	public Double getTotalLongContracts() {
		return totalLongContracts;
	}
	public void setTotalLongContracts(Double totalLongContracts) {
		this.totalLongContracts = totalLongContracts;
	}
	public Double getTotalShortContracts() {
		return totalShortContracts;
	}
	public void setTotalShortContracts(Double totalShortContracts) {
		this.totalShortContracts = totalShortContracts;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}
