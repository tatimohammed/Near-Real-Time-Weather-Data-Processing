package com.tati.dao;

import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.tati.backend.model.Weather;

@Repository
public interface CassandraRepo extends CassandraRepository<Weather, UUID> {

    public Weather findFirstByNameOrderByLastUpdateDesc(String name);
    
}
