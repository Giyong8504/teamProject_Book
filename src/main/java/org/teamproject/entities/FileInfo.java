package org.teamproject.entities;

import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data @Builder
@AllArgsConstructor @NoArgsConstructor
public class FileInfo extends BaseMemberEntity{ // 로그인 아이디 비교를 위한 상속

    @Id @GeneratedValue
    private Long id;

    @Column(length = 50, nullable = false)// 파일을 묶기 위한 그룹
    private String gid = UUID.randomUUID().toString(); // 랜덤, 중복되지 않는 unique 아이디 생성

    @Column(length = 50)
    private String location; // 게시물 이미지 각각의 위치

    @Column(length = 100, nullable = false)
    private String fileName; //원래 파일명

    @Column(length = 50)
    private String extension; // 확장자

    @Column(length = 70)
    private String fileType;

    private boolean done; // 작성 완료시 성공여부 (성공 파일만 남겨두기)
}