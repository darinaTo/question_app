package com.example.messaging.repository;

import com.example.messaging.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query(value = " FROM Question q order by length(q.name)  desc LIMIT ?1")
    List<Question> findTopQuestion(int numberOfTop);

    List<Question> findQuestionByNameContains(String name);
}
