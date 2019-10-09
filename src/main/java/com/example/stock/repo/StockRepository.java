package com.example.stock.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.stock.bean.Stock;

@Repository
public interface StockRepository extends CrudRepository<Stock, Long> {

	@Query(value = "from Stock t where date BETWEEN :startDate AND :endDate order by date")
	public List<Stock> getAllStocksBetweenDates(@Param("startDate")Date startDate,@Param("endDate")Date endDate);
	
	@Query(value = "Select distinct date from Stock t where date BETWEEN :startDate AND :endDate order by date")
	public List<Date> getDistinctDateBetweenRange(@Param("startDate")Date startDate,@Param("endDate")Date endDate);
	
	@Query(value = "Select distinct name from Stock t order by name")
	public List<String> getDistinctSymbolName();
}
