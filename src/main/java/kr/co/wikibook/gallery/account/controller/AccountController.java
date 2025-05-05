package kr.co.wikibook.gallery.account.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.wikibook.gallery.account.dto.AccountJoinRequest;
import kr.co.wikibook.gallery.account.dto.AccountLoginRequest;
import kr.co.wikibook.gallery.account.etc.AccountConstants;
import kr.co.wikibook.gallery.account.helper.AccountHelper;
import kr.co.wikibook.gallery.block.service.BlockService;
import kr.co.wikibook.gallery.common.util.HttpUtils;
import kr.co.wikibook.gallery.common.util.TokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class AccountController {
    private final AccountHelper accountHelper;
    private final BlockService blockService;

    @GetMapping("/api/account/token")
    public ResponseEntity<?> regenerate(HttpServletRequest req) {
        String accessToken = "";
        String refreshToken = HttpUtils.getCookieValue(req, AccountConstants.REFRESH_TOKEN_NAME);
        if (StringUtils.hasLength(refreshToken) && TokenUtils.isValid(refreshToken) && !blockService.has(refreshToken)) {
            Map<String, Object> tokenBody = TokenUtils.getBody(refreshToken);
            Integer memerId = (Integer) tokenBody.get(AccountConstants.MEMBER_ID_NAME);
            accessToken = TokenUtils.generate(AccountConstants.ACCESS_TOKEN_NAME, AccountConstants.MEMBER_ID_NAME, memerId, AccountConstants.ACCESS_TOKEN_EXP_MINUTES);

        }

        return ResponseEntity.ok(accessToken);
    }

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
