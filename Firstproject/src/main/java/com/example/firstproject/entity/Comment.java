package com.example.firstproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

//■----------------------댓글 엔티티
@Entity //해당 클래 엔티티 선언, 클래스 필드 바탕으로 DB에 테이블 생성
@Getter //각 필드값 조회(자동생성)
@ToString //모든 필드 출력(자동생성)
@AllArgsConstructor //모든 필드 매개변수 갖는 생성자(자동생성)
@NoArgsConstructor //매개변수가 아예 없는 기본 생성자(자동생성)


public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //DB 자동으로 1씩 증가
    private Long id;//(id)대표키

    @ManyToOne //다대일 관계로 설정(Comment와 Article 관계)
    @JoinColumn(name="article_id")//외래키 생성, Article 엔티티의 기본키(id)와 매핑
    private Article article;  //(article)해당 댓글의 부모 게시글


    @Column //해당 필드를 테이블의 속성으로 매핑
    private String nickname; //(nickname)댓글은 단 사람

    @Column //해당 필드를 테이블의 속성으로 매핑
    private String body;//(body)댓글 본문
}
