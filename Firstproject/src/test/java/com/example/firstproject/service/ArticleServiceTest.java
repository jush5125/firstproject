package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//1.해당 클래스를 스프링부트와 연동해 테스트함.
@SpringBootTest
class ArticleServiceTest {
    //2.객체 주입
    @Autowired
    ArticleService articleService;

    @Test
    void index() {
        //1.예상 데이터
        Article a = new Article(1L, "가가가가", "1111");
        Article b = new Article(2L, "나나나나", "2222");
        Article c = new Article(3L, "다다다다", "3333");
        //A,B,C 합치기-> 정적리스트를 일반리스트로 만듬(새 ArrayList로 만듬)
        List<Article> expected = new ArrayList<Article>(Arrays.asList(a,b,c));

        //2.실제 데이터
        List<Article> articles =articleService.index();

        //3.비교 및 검증
        assertEquals(expected.toString(),articles.toString());
        //x:예상데이터를 문자열로 변환한,expected.toString()
        //y:실제데이터를 문자열로 변환한,articles.toString()
    }

    @Test
    void show_성공_존재하는_id입력() {
        //1.예상데이터
        Long id = 1L;
        //3.예상 데이터 저장
        Article expected = new Article(id, "가가가가", "1111");
        //4.실제 데이터 저장
        Article article = articleService.show(id);
        //5. 비교 및 검증
        assertEquals(expected.toString(), article.toString());
    }



    @Test
    void show_실패_존재하지_않는_ID_입력() {
        //1.예상 데이터
        Long id = -1L;
        //2.예상 데이터
        Article expected = null;
        //3.실제 데이터
        Article article = articleService.show(id);
        //4.비교 및 검증
        assertEquals(expected,article);
    }

    //게시글 생성 상황
    //title / content만 있는 dto를 입력한 경우 테스트 함
    @Test
    void create_성공_title_content만_있는_dto_입력() {
        //1.예상 데이터
        // title,content 값을 임의로 배정
        String title ="라라라라";
        String content ="4444";

        //dto 생성
        ArticleForm dto = new ArticleForm(null,title,content);

        //예상 데이터 저장
        Article expected = new Article(4L, title, content);

        //2.실제 데이터
        Article article = articleService.create(dto);

        //3.비교 및 검증
        assertEquals(expected.toString(),article.toString());
    }

    @Test
    void create_실패_id가_포함된_dto_입력() {
        // 1. 예상 데이터
        Long id = 4L;
        String title = "라라라라";
        String content = "4444";
        ArticleForm dto = new ArticleForm(id, title, content);
        Article expected = null;

        // 2. 실제 데이터
        Article article = articleService.create(dto);

        // 3. 비교 및 검증
        assertEquals(expected, article);
    }
}