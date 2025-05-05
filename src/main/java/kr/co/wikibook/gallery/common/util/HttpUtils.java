package kr.co.wikibook.gallery.common.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class HttpUtils {

    public static void setSession(HttpServletRequest req, String key, Object value) {
        req.getSession().setAttribute(key, value);
    }

    public static Object getSessionValue(HttpServletRequest req, String key) {
        return req.getSession().getAttribute(key);
    }

    public static void removeSession(HttpServletRequest req, String key) {
        req.getSession().removeAttribute(key);
    }

    public static void setCookie(HttpServletResponse res, String name, String value, int expireSeconds) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true); // Prevent JavaScript access to the cookie
        cookie.setPath("/"); // Set the path to the root of the application

        if (expireSeconds > 0) {
            cookie.setMaxAge(expireSeconds);
        }

        res.addCookie(cookie);
    }

    public static String getCookieValue(HttpServletRequest req, String name) {
        Cookie[] cookies = req.getCookies();

        if (cookies != null) {
            for (Cookie cookie: cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }

    public static void removeCookie(HttpServletResponse res, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0); // Set the max age to 0 to delete the cookie
        cookie.setPath("/"); // Set the path to the root of the application
        res.addCookie(cookie);
    }

    public static String getBearToken(HttpServletRequest req) {
        String authHeader = req.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}
