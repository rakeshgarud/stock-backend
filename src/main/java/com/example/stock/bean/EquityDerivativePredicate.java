package com.example.stock.bean;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class EquityDerivativePredicate {

	public static Predicate<EquityDerivative> isOIGreater(double oi) {
        return p -> p.getOi() > oi;
    }
	
	public static Predicate<EquityDerivative> isChangeInOILess(double changeInOI) {
        return p -> p.getChnginOI() < changeInOI;
    }
	
	public static boolean filterAndGet(EquityDerivative equity, Predicate<EquityDerivative> predicate) {
        return predicate.test(equity);
    }
	
	//Utility function
	public static <T> Predicate<T> distinctByKeys(Function<? super T, ?>... keyExtractors)
	{
	  final Map<List<?>, Boolean> seen = new ConcurrentHashMap<>();
	   
	  return t ->
	  {
	    final List<?> keys = Arrays.stream(keyExtractors)
	                .map(ke -> ke.apply(t))
	                .collect(Collectors.toList());
	     
	    return seen.putIfAbsent(keys, Boolean.TRUE) == null;
	  };
	}
}
