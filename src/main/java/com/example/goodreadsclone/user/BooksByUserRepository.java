package com.example.goodreadsclone.user;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public interface BooksByUserRepository extends CassandraRepository<BooksByUser, String> {
    Slice<BooksByUser> findAllById(String id, Pageable pageable);
}
