package org.teamproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.teamproject.entities.Member;
import org.teamproject.entities.QMember;

public interface MemberRepository extends JpaRepository<Member, Long>, QuerydslPredicateExecutor<Member> {
    Member findByEmail(String email);

    default boolean existsByEmail(String email) {
        QMember qMember = QMember.member;
        return exists(qMember.email.eq(email));
    }

    Member findByUserNmAndEmail(String userNm, String email);

    // 비밀번호 찾기
    Member findByUserNoAndEmail(Long userNo, String email);

    // 비밀번호 변경
    @Modifying
    @Query("update Member m set m.userPw = :newPassword where m.userNm = :userNm and m.email = :email")
    void updatePassword(@Param("userNm") String userId, @Param("email") String email, @Param("newPassword") String newPassword);
}
