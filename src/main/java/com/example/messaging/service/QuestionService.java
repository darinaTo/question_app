package com.example.messaging.service;

import com.example.messaging.model.Question;
import java.util.List;

public interface QuestionService {
    Question save(Question question);

    List<Question> findTopQuestion(int number);

    int findCountQuestionByName(Question name, int count);
}
