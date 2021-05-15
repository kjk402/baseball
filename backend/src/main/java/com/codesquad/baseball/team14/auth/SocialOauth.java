package com.codesquad.baseball.team14.auth;

public interface SocialOauth {
    String getOauthRedirectURL();


    String requestAccessToken(String code);

    default SocialLoginType type() {
        if (this instanceof GoogleOauth) {
            return SocialLoginType.GOOGLE;
        } else {
            return null;
        }
    }

}
