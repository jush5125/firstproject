package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j //로그찍기
@Service //서비스 객체 생성
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    //index()메서드
    public List<Article> index() {
        return articleRepository.findAll();
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    //게시그 ㄹ수정 요청 개선하기
    //update 메서드
    public Article update(Long id, ArticleForm dto) {
        // 1. DTO -> 엔티티 변환하기
        Article article = dto.toEntity();
        log.info("id: {}, article: {}", id, article.toString());

        // 2. 타겟 조회하기
        Article target = articleRepository.findById(id).orElse(null);

        // 3. 잘못된 요청 처리하기
        if (target == null || id != article.getId()) {
            // 400번, 잘못된 요청 응답!
            log.info("잘못된 요청! id: {}, article: {}", id, article.toString());
            return null; //응답은 컨트롤러가 하므로, 여기서는 NULL 값 반환
        }

        // 4. 업데이트하기
        target.patch(article);
        Article updated = articleRepository.save(target);
        return updated; //응답은 컨트롤러가 하므로 여기서는 수정 데이터만 반환
    }

    //create
    public Article create(ArticleForm dto) {
        //DTO -> entity 변환 후, article에 저장
        Article article = dto.toEntity();

        //ID는 데이터를 생성할 때 굳이 넣을 필요 없음 -> DB가 알아서 생성하기 때문
        //따라서, article 객체가 id가 존재한다면, null값으로 반환하는 코드 추가함
        if (article.getId() != null) {
            return null;
        }

        //article을 DB에 저장함.
        return articleRepository.save(article);
    }


    public Article delete(Long id) {
        //1.대상 찾기(엔티티 찾음)
        Article target = articleRepository.findById(id).orElse(null);

        //2.잘못된 요청 처리하기-(수정필요)
        if (target == null) {
            return null; //응답은, 컨트롤러가 하므로 여기서는 null만 반환!
        }

        //3.대상 삭제하기
        articleRepository.delete(target);
        return target; //DB에서 삭제한 대상을 컨트롤러에 반환
    }

    //하나의 트랜잭션으로 묶어, 이전 상태로 돌아가는 롤백
    //어노테이션 추가
    @Transactional
    public List<Article> createArticles(List<ArticleForm> dtos) {
        //1.스트림화 하기
        List<Article> articleList = dtos.stream()
                .map(dto -> dto.toEntity())
                .collect(Collectors.toList());
        //2.엔티티 묶음(리스트)을 DB로 저장하기
        articleList.stream() // articleList을 스트림화 한다.
                .forEach(article -> articleRepository.save(article));

        //3.강제로 에러를 발생 시키기
        articleRepository.findById(-1L) //id가 -1인 음수 데이터 찾기
                .orElseThrow(()-> new IllegalArgumentException("결제 실패!"));//찾는 데이터 없으면 예외발생!

        //4.결과 값 반환하기
        return articleList;
    }
}

