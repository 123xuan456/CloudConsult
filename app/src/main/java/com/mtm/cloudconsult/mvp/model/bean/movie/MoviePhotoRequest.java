package com.mtm.cloudconsult.mvp.model.bean.movie;


import com.mtm.cloudconsult.app.api.AdapterConstant;
import com.mtm.cloudconsult.app.base.BaseEntityBean;

import java.util.List;

/**
 * Created by li.xiao on 2018-1-27.
 */

public class MoviePhotoRequest extends BaseEntityBean{


    private int count;
    private int total;
    private int start;
    private MovieBean subject;
    private List<PhotosBean> photos;
    private List<MovieComment> reviews;
    private List<MovieComment> comments;

    public List<MovieComment> getReviews() {
        return reviews;
    }

    public void setReviews(List<MovieComment> reviews) {
        this.reviews = reviews;
    }

    public List<MovieComment> getComments() {
        return comments;
    }

    public void setComments(List<MovieComment> comments) {
        this.comments = comments;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public MovieBean getSubject() {
        return subject;
    }

    public void setSubject(MovieBean subject) {
        this.subject = subject;
    }

    public List<PhotosBean> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotosBean> photos) {
        this.photos = photos;
    }

    public static class PhotosBean extends BaseEntityBean {
        /**
         * photos_count : 12
         * thumb : https://img3.doubanio.com/view/photo/thumb/public/p2406383762.jpg
         * icon : https://img3.doubanio.com/view/photo/icon/public/p2406383762.jpg
         * author : {"uid":"122971558","avatar":"https://img3.doubanio.com/icon/u122971558-2.jpg","signature":"","alt":"https://www.douban.com/people/122971558/","id":"122971558","name":"轮子"}
         * created_at : 2016-12-18 20:14:19
         * album_id : 1638319514
         * cover : https://img3.doubanio.com/view/photo/albumcover/public/p2406383762.jpg
         * id : 2406383762
         * prev_photo : 2408074715
         * album_url : https://movie.douban.com/subject/26865690/photos
         * comments_count : 3
         * image : https://img3.doubanio.com/view/photo/photo/public/p2406383762.jpg
         * recs_count : 1
         * position : 6
         * alt : https://movie.douban.com/photos/photo/2406383762/
         * album_title : 恐怖理发店(26865690)
         * next_photo : 2406383761
         * subject_id : 26865690
         * desc :
         */

        private int photos_count;
        private String thumb;
        private String icon;
        private MovieAuthor author;
        private String created_at;
        private String album_id;
        private String cover;
        private String id;
        private String prev_photo;
        private String album_url;
        private int comments_count;
        private String image;
        private int recs_count;
        private int position;
        private String alt;
        private String album_title;
        private String next_photo;
        private String subject_id;
        private String desc;

        @Override
        public int getItemType() {
            return AdapterConstant.ITME_MOVIE_PHOTO_DEFAULT;
        }

        public int getPhotos_count() {
            return photos_count;
        }

        public void setPhotos_count(int photos_count) {
            this.photos_count = photos_count;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public MovieAuthor getAuthor() {
            return author;
        }

        public void setAuthor(MovieAuthor author) {
            this.author = author;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getAlbum_id() {
            return album_id;
        }

        public void setAlbum_id(String album_id) {
            this.album_id = album_id;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPrev_photo() {
            return prev_photo;
        }

        public void setPrev_photo(String prev_photo) {
            this.prev_photo = prev_photo;
        }

        public String getAlbum_url() {
            return album_url;
        }

        public void setAlbum_url(String album_url) {
            this.album_url = album_url;
        }

        public int getComments_count() {
            return comments_count;
        }

        public void setComments_count(int comments_count) {
            this.comments_count = comments_count;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getRecs_count() {
            return recs_count;
        }

        public void setRecs_count(int recs_count) {
            this.recs_count = recs_count;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public String getAlbum_title() {
            return album_title;
        }

        public void setAlbum_title(String album_title) {
            this.album_title = album_title;
        }

        public String getNext_photo() {
            return next_photo;
        }

        public void setNext_photo(String next_photo) {
            this.next_photo = next_photo;
        }

        public String getSubject_id() {
            return subject_id;
        }

        public void setSubject_id(String subject_id) {
            this.subject_id = subject_id;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
