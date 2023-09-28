package org.teamproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.teamproject.entities.Member;
import org.teamproject.entities.QMember;

public interface MemberRepository extends JpaRepository<Member, Long>, QuerydslPredicateExecutor<Member> {
    Member findByEmail(String email);

    default boolean exists(String email) {

        return exists(QMember.member.email.eq(email));
    }


}
