package com.project01_teamA.camping_lounge.repository;

import com.project01_teamA.camping_lounge.entity.Member;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    @Transactional
    Optional<Member> findByEmail(String email);
}