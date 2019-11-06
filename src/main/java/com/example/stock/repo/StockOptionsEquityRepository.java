package com.example.stock.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.stock.bean.NiftyEquityDerivative;
import com.example.stock.bean.StockOptionsEquity;

@Repository
public interface StockOptionsEquityRepository extends CrudRepository<StockOptionsEquity, Long> {

	@Query(value = "from StockOptionsEquity t where date BETWEEN :startDate AND :endDate AND (symbol=:symbol or ''=:symbol) order by date")
	public List<StockOptionsEquity> getAllEquitiesBetweenDates(@Param("startDate")Date startDate,@Param("endDate")Date endDate,@Param("symbol") String symbol);
	
	@Query(value = "Select distinct date from StockOptionsEquity t where date BETWEEN :startDate AND :endDate order by date desc")
	public List<Date> getDistinctDateBetweenRange(@Param("startDate")Date startDate,@Param("endDate")Date endDate);
	
	@Query(value = "from StockOptionsEquity t where date BETWEEN :startDate AND :endDate AND type =:type AND (symbol=:symbol or ''=:symbol) order by date")
	public List<StockOptionsEquity> getAllEquitiesBetweenDatesAndByType(@Param("startDate")Date startDate,@Param("endDate")Date endDate,@Param("type") int type,@Param("symbol") String symbol);

	@Query(value = "from StockOptionsEquity t where strikePrice =:strikePrice AND type =:type order by date")
	public List<StockOptionsEquity> getEquitiesByStrikePrice(@Param("strikePrice")double strikePrice,@Param("type") int type);
	
	@Query(value = "from StockOptionsEquity t where strikePrice =:strikePrice AND date BETWEEN :startDate AND :endDate AND type =:type order by date")
	public List<StockOptionsEquity> getEquitiesByStrikePriceBetweenDatesAndByType(@Param("startDate")Date startDate,@Param("endDate")Date endDate,@Param("strikePrice")double strikePrice,@Param("type") int type);

	@Query(value = "Select DISTINCT new com.example.stock.bean.StockOptionsEquity(t.id,t.symbol,t.oi, t.chnginOI, t. volume,  t.iv,  t.ltp,t.netChng,  t.bidQty,  t.bidPrice,  t.askPrice,  t.askQty,  t.strikePrice," + 
			"			 t.date,  t.type,  t.rowNo,  t.expiryDate,  t.currentPrice" + 
			" , t1) "
			+ "from StockOptionsEquity t,StockOptionsEquity t1 WHERE t.date BETWEEN :startDate AND :endDate AND (t.symbol=:symbol or ''=:symbol) "
			+ " AND t1.type=:type AND t1.putId=t.id "
			+ "AND (t.expiryDate=:expiryDate OR ''=:expiryDate) AND t.type='1' order by t.expiryDate,t.date")
	public List<StockOptionsEquity> getAllEquitiesBetweenDates(@Param("startDate")Date startDate,@Param("endDate")Date endDate,@Param("type") int type,@Param("symbol") String symbol,@Param("expiryDate") String expiryDate);

	@Query(value = "Select distinct date from StockOptionsEquity t where date BETWEEN :startDate AND :endDate AND (t.expiryDate=:expiryDate OR ''=:expiryDate) order by date desc")
	public List<Date> getDistinctDateBetweenRangeAndExpiry(@Param("startDate")Date startDate,@Param("endDate")Date endDate,@Param("expiryDate") String expiryDate);
	
}
