package com.stackroute.bookrecommendationservice.projections;

import java.util.List;

public interface UserBookProjection {
    public String getEmailId();
    public String getUserId();

    List<BookProjection> getFavoriteBooks();

    interface BookProjection {

        public String getAuthorName();
        public String getId();
        public String getBookTitle();
        public String getDescription();
        public String getBookUrl();
        public Long getTotalViews();
        public Integer getTotalPage();
        public String getPublisher();
        public Long getTotalDownloads();
        public String getImageUrl();
        public String getIsbnNumber();
        public String getGenre();

    }
}
