package com.example.firstproject.dto;


import com.example.firstproject.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;


//DTO(폼데이터 받아올 그릇)
@AllArgsConstructor //ArticleForm 생성자 리팩토링
@ToString //toString()생성자 리팩토링
@Getter

public class ArticleForm {
    //필드
    private Long id; //id 필드 추가
    private String title;   //제목을 받을 필드
    private String content; //내용을 받을 필드

    //toEntity()
    public Article toEntity() {
        return new Article(id, title, content);
    }
}