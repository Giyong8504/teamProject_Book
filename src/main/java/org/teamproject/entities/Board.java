package org.teamproject.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.teamproject.commons.constants.Role;

@Entity @Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class Board extends BaseMemberEntity{

    @Id
    @Column(length = 30)
    private String bId; // 게시판 Id

    @Column(length = 60, nullable = false)
    private String bName; // 게시판명

    @Column(name="isUse")
    private boolean use; // 사용 여부

    private int rowOfPage = 20; // 1페이지당 게시글 수

    private int showViewList; // 게시글 하단 목록 노출

    @Lob
    private String category; // 게시판 분류

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private Role listAccessRole = Role.ALL; // 목록 접근 권한

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private Role viewAccessRole = Role.ALL; // 글보기 접근 권한

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private Role writeAccessRole = Role.ALL; // 글쓰기 접근 권한

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private Role replyAccessRole = Role.ALL; // 답글 접근 권한

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private Role commentAccessRole = Role.ALL; // 댓글 접근 권한
    
    private boolean useEditor; // 에디터 사용 여부
    
    private boolean useAttachFile; // 파일 첨부 사용 여부

    private boolean useAttachImage; // 이미저 첨부 사용 여부

    @Column(length = 10, nullable = false)
    private String locationAfterWriting = "view"; // 글 작성 후 이동

    private boolean useReply; // 답글 사용 여부

    private boolean useComment; // 댓글 사용 여부

    @Column(length = 20, nullable = false)
    private String skin = "default"; // 게시판 스킨
}
