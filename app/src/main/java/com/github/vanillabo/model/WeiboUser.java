package com.github.vanillabo.model;

/**
 * Created by alan on 16/4/12.
 */
public class WeiboUser {

    public final String uid;

    public final String name;

    public final String screen_name;

    public final String location;

    public final String description;

    public final String profile_image_url;

    public final String avatar_large;

    public final int followers_count;

    public final int friends_count;

    public final int statuses_count;

    public final String gender;

    public WeiboUser(String uid,
                     String name,
                     String screen_name,
                     String location,
                     String description,
                     String profile_image_url,
                     String avatar_large,
                     int followers_count,
                     int friends_count,
                     int statuses_count,
                     String gender) {
        this.uid = uid;
        this.name = name;
        this.screen_name = screen_name;
        this.location = location;
        this.description = description;
        this.profile_image_url = profile_image_url;
        this.avatar_large = avatar_large;
        this.followers_count = followers_count;
        this.friends_count = friends_count;
        this.statuses_count = statuses_count;
        this.gender = gender;
    }
}
