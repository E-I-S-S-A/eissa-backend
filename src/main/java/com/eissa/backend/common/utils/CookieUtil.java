package com.eissa.backend.common.utils;

import com.eissa.backend.accounts.models.enums.CookieEnum;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CookieUtil {

    public static final int COOKIE_EXPIRY = 7 * 24 * 60 * 60; // 1 week

    public static void setCookie(HttpServletResponse response, CookieEnum name, String value) {
        Cookie cookie = new Cookie(String.valueOf(name), value);
        cookie.setMaxAge(COOKIE_EXPIRY);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setDomain("eissa.in");
        response.addCookie(cookie);
    }

    public static String getCookie(HttpServletRequest request, CookieEnum name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(String.valueOf(name))) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
