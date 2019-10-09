package com.example.stock.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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

}
