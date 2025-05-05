package kr.co.wikibook.gallery.account.helper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.wikibook.gallery.account.dto.AccountJoinRequest;
import kr.co.wikibook.gallery.account.dto.AccountLoginRequest;
import kr.co.wikibook.gallery.account.etc.AccountConstants;
import kr.co.wikibook.gallery.common.util.HttpUtils;
import kr.co.wikibook.gallery.member.entity.Member;
import kr.co.wikibook.gallery.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SessionAccountHelper implements AccountHelper {

    private final MemberService memberService;

    @Override
    public void join(AccountJoinRequest joinRequest) {
        memberService.save(joinRequest.getName(), joinRequest.getLoginId(), joinRequest.getLoginPw());
    }

    @Override
    public Integer getMemberId(HttpServletRequest req) {
        Object memberId = HttpUtils.getSessionValue(req, AccountConstants.MEMBER_ID_NAME);
        if (memberId != null) {
            return (Integer) memberId;
        }

        return null;
    }

    @Override
    public String login(AccountLoginRequest loginRequest, HttpServletRequest req, HttpServletResponse res) {
        Member member = memberService.find(loginRequest.getLoginId(), loginRequest.getLoginPw());
        if (member == null) {
            return null;
        }

        HttpUtils.setSession(req, AccountConstants.MEMBER_ID_NAME, member.getId());
        return member.getLoginId();
    }

    @Override
    public boolean isLogin(HttpServletRequest req) {
        return getMemberId(req) != null;
    }

    @Override
    public void logout(HttpServletRequest req, HttpServletResponse res) {
        HttpUtils.removeSession(req, AccountConstants.MEMBER_ID_NAME);
    }
}
