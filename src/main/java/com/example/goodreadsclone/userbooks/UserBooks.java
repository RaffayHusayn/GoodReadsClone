package com.example.goodreadsclone.userbooks;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.*;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;

import java.time.LocalDate;

@Table(value = "books_by_userId_and_bookId")
public class UserBooks {
    /*
     * Attributes:
     * 1. Primary Keys (more than one so defined in a separate class because @Repository can have 1 primary key only
     * 2. rating
     * 3. reading status
     * 4. date started
     * 5. date completed
     */

    @PrimaryKey
    private UserBooksPrimaryKey key;

    @Column("rating")
    @CassandraType(type = Name.INT)
    private int rating;

    @Column("reading_status")
    @CassandraType(type = Name.TEXT)
    private String readingStatus;

    @Column("date_started")
    @CassandraType(type = Name.DATE)
    private LocalDate dateStarted;

    @Column("date_completed")
    @CassandraType(type = Name.DATE)
    private LocalDate dateCompleted;

    public UserBooksPrimaryKey getKey() {
        return key;
    }

    public void setKey(UserBooksPrimaryKey key) {
        this.key = key;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReadingStatus() {
        return readingStatus;
    }

    public void setReadingStatus(String readingStatus) {
        this.readingStatus = readingStatus;
    }

    public LocalDate getDateStarted() {
        return dateStarted;
    }

    public void setDateStarted(LocalDate dateStarted) {
        this.dateStarted = dateStarted;
    }

    public LocalDate getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(LocalDate dateCompleted) {
        this.dateCompleted = dateCompleted;
    }
}
