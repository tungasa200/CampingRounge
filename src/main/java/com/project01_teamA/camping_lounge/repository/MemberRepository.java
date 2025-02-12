package com.project01_teamA.camping_lounge.repository;

import com.project01_teamA.camping_lounge.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
}