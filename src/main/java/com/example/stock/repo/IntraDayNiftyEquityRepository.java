package com.example.stock.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.stock.bean.IntraDayEquity;

@Repository
public interface IntraDayNiftyEquityRepository extends CrudRepository<IntraDayEquity, Long> {

	@Query(value = "from IntraDayEquity t where date BETWEEN :startDate AND :endDate AND (symbol=:symbol or ''=:symbol) order by date")
	public List<IntraDayEquity> getAllEquitiesBetweenDates(@Param("startDate")Date startDate,@Param("endDate")Date endDate,@Param("symbol") String symbol);
	
	@Query(value = "Select distinct date from IntraDayEquity t where date BETWEEN :startDate AND :endDate order by date desc")
	public List<Date> getDistinctDateBetweenRange(@Param("startDate")Date startDate,@Param("endDate")Date endDate);
	
	@Query(value = "from IntraDayEquity t where date BETWEEN :startDate AND :endDate AND type =:type AND (symbol=:symbol or ''=:symbol) order by date")
	public List<IntraDayEquity> getAllEquitiesBetweenDatesAndByType(@Param("startDate")Date startDate,@Param("endDate")Date endDate,@Param("type") int type,@Param("symbol") String symbol);

	@Query(value = "from IntraDayEquity t where strikePrice =:strikePrice AND type =:type order by date")
	public List<IntraDayEquity> getEquitiesByStrikePrice(@Param("strikePrice")double strikePrice,@Param("type") int type);
	
	@Query(value = "from IntraDayEquity t where strikePrice =:strikePrice AND date BETWEEN :startDate AND :endDate AND type =:type order by date")
	public List<IntraDayEquity> getEquitiesByStrikePriceBetweenDatesAndByType(@Param("startDate")Date startDate,@Param("endDate")Date endDate,@Param("strikePrice")double strikePrice,@Param("type") int type);

}
