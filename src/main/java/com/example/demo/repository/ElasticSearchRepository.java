package com.example.demo.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.example.demo.entities.Hello;

public interface ElasticSearchRepository extends ElasticsearchRepository<Hello, Long> {

	List<Hello> findById(String firstName);


}
