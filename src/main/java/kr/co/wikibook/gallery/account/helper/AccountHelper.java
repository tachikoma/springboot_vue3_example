package kr.co.wikibook.gallery.account.helper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.wikibook.gallery.account.dto.AccountJoinRequest;
import kr.co.wikibook.gallery.account.dto.AccountLoginRequest;

public interface AccountHelper {
    void join(AccountJoinRequest joinRequest);
    Integer getMemberId(HttpServletRequest req);
    String login(AccountLoginRequest loginRequest, HttpServletRequest req, HttpServletResponse res);
    boolean isLogin(HttpServletRequest req);
    void logout(HttpServletRequest req, HttpServletResponse res);
}
