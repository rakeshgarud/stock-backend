package com.example.stock.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.stock.bean.NiftyEquityDerivative;

@Repository
public interface NiftyEquityDerivativeRepository extends CrudRepository<NiftyEquityDerivative, Long> {

	@Query(value = "from NiftyEquityDerivative t where date BETWEEN :startDate AND :endDate AND (symbol=:symbol or ''=:symbol) order by date")
	public List<NiftyEquityDerivative> getAllEquitiesBetweenDates(@Param("startDate")Date startDate,@Param("endDate")Date endDate,@Param("symbol") String symbol);
	
	@Query(value = "Select distinct date from NiftyEquityDerivative t where date BETWEEN :startDate AND :endDate AND (expiryDate=:expiryDate OR ''=:expiryDate) order by date desc")
	public List<Date> getDistinctDateBetweenRange(@Param("startDate")Date startDate,@Param("endDate")Date endDate,@Param("expiryDate") String expiryDate);
	
	@Query(value = "from NiftyEquityDerivative t where date BETWEEN :startDate AND :endDate AND type =:type AND (symbol=:symbol or ''=:symbol) AND (expiryDate=:expiryDate OR ''=:expiryDate) order by expiryDate,date")
	public List<NiftyEquityDerivative> getAllEquitiesBetweenDatesAndByType(@Param("startDate")Date startDate,@Param("endDate")Date endDate,@Param("type") int type,@Param("symbol") String symbol,@Param("expiryDate") String expiryDate);

	@Query(value = "from NiftyEquityDerivative t where strikePrice =:strikePrice AND type =:type order by date")
	public List<NiftyEquityDerivative> getEquitiesByStrikePrice(@Param("strikePrice")double strikePrice,@Param("type") int type);
	
	@Query(value = "from NiftyEquityDerivative t where strikePrice =:strikePrice AND date BETWEEN :startDate AND :endDate AND type =:type order by date")
	public List<NiftyEquityDerivative> getEquitiesByStrikePriceBetweenDatesAndByType(@Param("startDate")Date startDate,@Param("endDate")Date endDate,@Param("strikePrice")double strikePrice,@Param("type") int type);

	@Query(value = "Select DISTINCT new com.example.stock.bean.NiftyEquityDerivative(t.id,t.symbol,t.oi, t.chnginOI, t. volume,  t.iv,  t.ltp,t.netChng,  t.bidQty,  t.bidPrice,  t.askPrice,  t.askQty,  t.strikePrice," + 
			"			 t.date,  t.type,  t.rowNo,  t.expiryDate,  t.currentPrice" + 
			" , (Select DISTINCT t1 FROM NiftyEquityDerivative t1 where t.strikePrice=t1.strikePrice and t.rowNo=t1.rowNo AND t1.type=:type AND t1.putId=t.id)) "
			+ "from NiftyEquityDerivative t WHERE t.date BETWEEN :startDate AND :endDate AND (t.symbol=:symbol or ''=:symbol) AND (t.expiryDate=:expiryDate OR ''=:expiryDate) AND t.type='1' order by t.expiryDate,t.date")
	public List<NiftyEquityDerivative> getAllEquitiesBetweenDates(@Param("startDate")Date startDate,@Param("endDate")Date endDate,@Param("type") int type,@Param("symbol") String symbol,@Param("expiryDate") String expiryDate);

}
