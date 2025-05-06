package kr.co.wikibook.gallery.member.service;

import kr.co.wikibook.gallery.common.util.HashUtils;
import kr.co.wikibook.gallery.member.entity.Member;
import kr.co.wikibook.gallery.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BaseMemberService implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public void save(String name, String loginId, String loginPw) {
        String loginPwSalt = HashUtils.generateSalt(16);
        String hashedLoginPw = HashUtils.generateHash(loginPw, loginPwSalt);
        memberRepository.save(new Member(name, loginId, hashedLoginPw, loginPwSalt));
    }

    @Override
    public Member find(String loginId, String loginPw) {
        // 로그인 아이디로 회원 조회
        Optional<Member> memberOptional  = memberRepository.findByLoginId(loginId);

        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            String hashedLoginPw = HashUtils.generateHash(loginPw, member.getLoginPwSalt());
            if (member.getLoginPw().equals(hashedLoginPw)) {
                return member;
            }
        }

        return null;
    }

    @Override
    public Member find(String loginId) {
        return memberRepository.findByLoginId(loginId).orElse(null);
    }
}
