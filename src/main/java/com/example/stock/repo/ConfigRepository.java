package com.example.stock.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.stock.bean.ConfigData;

@Repository
public interface ConfigRepository  extends CrudRepository<ConfigData,Long>{

}
