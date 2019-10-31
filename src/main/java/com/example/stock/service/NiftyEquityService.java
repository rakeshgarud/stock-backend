package com.example.stock.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.stock.bean.NiftyEquityDerivative;
import com.example.stock.dto.SearchFilter;

@Service
public interface NiftyEquityService {

	public List<NiftyEquityDerivative> serachNiftyEquity(SearchFilter search);

	public List<NiftyEquityDerivative> getYesterdayMinusTodayK(SearchFilter search);

	public void saveNiftyEquityDerivatives();

}
