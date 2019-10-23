package com.example.stock.util;

import java.util.List;

public class CommonUtil {
	
	// function that returns correlation coefficient. 
    public static float getCorrelationCoefficient(List<Double> IVList, 
    		List<Double> stikePriceList) 
    { 
    	double[] X = IVList.stream().mapToDouble(i -> i).toArray();
		double[] Y = stikePriceList.stream().mapToDouble(i -> i).toArray();
    	int n = X.length;
    	double sum_X = 0, sum_Y = 0, sum_XY = 0; 
    	double squareSum_X = 0, squareSum_Y = 0; 
       
        for (int i = 0; i < n; i++) 
        { 
            // sum of elements of array X. 
            sum_X = sum_X + X[i]; 
       
            // sum of elements of array Y. 
            sum_Y = sum_Y + Y[i]; 
       
            // sum of X[i] * Y[i]. 
            sum_XY = sum_XY + X[i] * Y[i]; 
       
            // sum of square of array elements. 
            squareSum_X = squareSum_X + X[i] * X[i]; 
            squareSum_Y = squareSum_Y + Y[i] * Y[i]; 
        } 
       
        // use formula for calculating correlation  
        // coefficient. 
        float corr = (float)(n * sum_XY - sum_X * sum_Y)/ 
                     (float)(Math.sqrt((n * squareSum_X - 
                     sum_X * sum_X) * (n * squareSum_Y -  
                     sum_Y * sum_Y))); 
       
        return corr; 
    }

}
