package com.example.goodreadsclone.user;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * This model represents the books that the User has previously interacted with (Read, dropped, rated )
 * Helps with showing the recent books that the user interacted with
 */
@Table(value = "books_by_user")
public class BooksByUser {

    @PrimaryKeyColumn(name = "user_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String id;

    @PrimaryKeyColumn(name = "book_id", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    @CassandraType(type = Name.TEXT)
    private String bookId;

    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    @CassandraType(type = Name.TIMEUUID)
    private UUID timeUuid;

    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED, ordering = Ordering.ASCENDING)
    @CassandraType(type = Name.TEXT)
    private String readingStatus;

    @Column("book_name")
    @CassandraType(type = Name.TEXT)
    private String bookName;

    @Column("author_names")
    @CassandraType(type = Name.LIST, typeArguments = Name.TEXT)
    private List<String> authorName;

    @Column("cover_ids")
    @CassandraType(type = Name.LIST, typeArguments = Name.TEXT)
    private List<String> coverIds;

    @Column("rating")
    @CassandraType(type = Name.INT)
    private int rating;

    @Transient
    private String coverUrl;

    public BooksByUser(){
        this.timeUuid = Uuids.timeBased();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getReadingStatus() {
        return readingStatus;
    }

    public void setReadingStatus(String readingStatus) {
        this.readingStatus = readingStatus;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public List<String> getAuthorName() {
        return authorName;
    }

    public void setAuthorName(List<String> authorName) {
        this.authorName = authorName;
    }

    public List<String> getCoverIds() {
        return coverIds;
    }

    public void setCoverIds(List<String> coverIds) {
        this.coverIds = coverIds;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }
    public String getCoverUrl() {
        return coverUrl;
    }
// because we are using .distinct() in the stream in the HomeController so we need a way to compare the BooksByUser objects

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BooksByUser)) return false;
        BooksByUser that = (BooksByUser) o;
        return id.equals(that.id) && bookId.equals(that.bookId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookId);
    }
}
