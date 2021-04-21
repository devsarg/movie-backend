package com.devsarg.repository;

import com.devsarg.entity.Movie;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends MongoRepository<Movie, String> {
}
