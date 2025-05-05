package kr.co.wikibook.gallery.account.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.wikibook.gallery.account.dto.AccountJoinRequest;
import kr.co.wikibook.gallery.account.dto.AccountLoginRequest;
import kr.co.wikibook.gallery.account.helper.AccountHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class AccountController {
    private final AccountHelper accountHelper;

    @PostMapping("/api/account/join")
    public ResponseEntity<?> join(@RequestBody AccountJoinRequest joinRequest) {
        if (!StringUtils.hasLength(joinRequest.getName())
                || !StringUtils.hasLength(joinRequest.getLoginId())
                || !StringUtils.hasLength(joinRequest.getLoginPw())) {
            return ResponseEntity.badRequest().build();
        }
        accountHelper.join(joinRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/account/login")
    public ResponseEntity<?> login(HttpServletRequest req, HttpServletResponse res, @RequestBody AccountLoginRequest loginRequest) {
        if (!StringUtils.hasLength(loginRequest.getLoginId())
                || !StringUtils.hasLength(loginRequest.getLoginPw())) {
            return ResponseEntity.badRequest().build();
        }
        String loginId = accountHelper.login(loginRequest, req, res);
        if (loginId == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(loginId);
    }

    @GetMapping("/api/account/check")
    public ResponseEntity<?> check(HttpServletRequest req) {
        return ResponseEntity.ok(accountHelper.isLogin(req));
    }

    @PostMapping("/api/account/logout")
    public ResponseEntity<?> logout(HttpServletRequest req, HttpServletResponse res) {
        accountHelper.logout(req, res);
        return ResponseEntity.ok().build();
    }
}
