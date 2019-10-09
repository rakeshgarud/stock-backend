package com.example.stock.bean;

import java.util.function.Predicate;

public class StockPredicate {

	public static Predicate<Stock> isClosePriceGreater(double closePrice) {
        return p -> p.getClosePrice() > closePrice;
    }
	
	public static Predicate<Stock> isClosePriceLess(double closePrice) {
        return p -> p.getClosePrice() < closePrice;
    }
	
	public static Predicate<Stock> isTtlTradeQtyGreater(double ttlTradeQty) {
        return p -> p.getTtlTradeQty() > ttlTradeQty;
    }
	
	public static Predicate<Stock> isTtlTradeQtyLess(double ttlTradeQty) {
        return p -> p.getTtlTradeQty() < ttlTradeQty;
    }
	
	public static Predicate<Stock> isDeliverQtyGreater(double delQty) {
        return p -> p.getDeliveryQty() > delQty;
    }
	
	public static Predicate<Stock> isDeliverQtyLess(double delQty) {
        return p -> p.getDeliveryQty() < delQty;
    }
	
	public static Predicate<Stock> isDeliverPerGreater(double delPer) {
        return p -> p.getDeliveryPer() > delPer;
    }
	
	public static Predicate<Stock> isDeliverPerLess(double ttlTradeQty) {
        return p -> p.getDeliveryPer() < ttlTradeQty;
    }
    
    public static boolean filterAndGet(Stock stock, Predicate<Stock> predicate) {
        return predicate.test(stock);
    }
}
