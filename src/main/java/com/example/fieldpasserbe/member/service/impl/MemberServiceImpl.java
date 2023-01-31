package com.example.fieldpasserbe.member.service.impl;

import com.example.fieldpasserbe.member.entity.Member;
import com.example.fieldpasserbe.member.repository.MemberRepositoryJPA;
import com.example.fieldpasserbe.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final int contentsSize = 10;
    private final MemberRepositoryJPA memberRepository;

    @Override
    public Optional<Member> findMemberById(int id) {
        return memberRepository.findMemberById(id);
    }

    @Override
    public Optional<Member> findMemberByEmail(String email) throws NullPointerException{
        if (!memberRepository.findMemberByEmail(email).isPresent()) {
            throw new NullPointerException("존재하지 않는 회원입니다.");
        } else {
            return memberRepository.findMemberByEmail(email);
        }
    }

    @Override
    public Optional<Member> findAdminByEmail(String email) throws NullPointerException{
        if (!memberRepository.findAdminByEmail(email).isPresent()) {
            throw new NullPointerException("존재하지 않는 회원입니다.");
        } else {
            return memberRepository.findAdminByEmail(email);
        }
    }

    @Override
    public boolean updateVisitCount(int id) {
        try {
            if (memberRepository.updateVisitCount(id) != 1) {
                throw new Exception("방문 카운트 증가에 실패했습니다.");
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Page<Member> findAllMembers(int page) throws NullPointerException{
        PageRequest pageRequest = PageRequest.of(page - 1, contentsSize, Sort.by(Sort.Direction.DESC, "id"));
        if (memberRepository.findAllMembers(pageRequest).getContent().isEmpty()) {
            throw new NullPointerException("조회할 수 있는 회원이 없습니다.");
        } else {
            return memberRepository.findAllMembers(pageRequest);
        }
    }
}