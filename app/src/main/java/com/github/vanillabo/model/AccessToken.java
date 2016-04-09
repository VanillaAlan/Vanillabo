package com.github.vanillabo.model;

/**
 * Created by alan on 16/4/8.
 */
public class AccessToken {

    public String access_token;

    public int remind_in;

    public int expires_in;

    public String uid;

    public AccessToken(String access_token, int remind_in, int expires_in, String uid) {
        this.access_token = access_token;
        this.remind_in = remind_in;
        this.expires_in = expires_in;
        this.uid = uid;
    }
}
