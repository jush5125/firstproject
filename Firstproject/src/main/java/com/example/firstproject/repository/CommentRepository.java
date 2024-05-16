package com.example.firstproject.repository;

import com.example.firstproject.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    //1.특정 게시글의 모든 댓글 조회
    //value 속성에 실행하려는 쿼리 작성함.
    @Query(value ="SELECT * FROM " +
            "comment WHERE" +
            " article_id= " +
            ":articleId" , nativeQuery = true)
    List<Comment> findByArticleId(Long articleId);

    //2.특정 닉네임의 모든 댓글 조회
    List<Comment> findByNickname(String nickname);

}
