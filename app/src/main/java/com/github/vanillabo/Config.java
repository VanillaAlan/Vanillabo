package com.github.vanillabo;

/**
 * Created by alan on 16/4/8.
 */
public class Config {

    public static final String APP_KEY = "2847295106";

    public static final String APP_SECRET = "9357ae59c18e049f8ccbb852271559ef";

    public static final String REDIRECT_URI = "https://api.weibo.com/oauth2/default.html";

    public static final String WEIBO_API_HOST = "https://api.weibo.com/";

    public static final String AUTHORIZE_URL = "https://api.weibo.com/oauth2/authorize?client_id=" + Config.APP_KEY
            + "&response_type=code&redirect_uri=" + Config.REDIRECT_URI + "&forcelogin=true";

    public static String TWITTER_API_KEY = "g27zSTSAslxuIE4JXi4yR7wDT";

    public static String TWITTER_BUILD_SECRET = "KDFBtJA5JkSyRfwyLSInQdWkvBEi1wOa0xs9kEXhW9u30H79kn";

}
