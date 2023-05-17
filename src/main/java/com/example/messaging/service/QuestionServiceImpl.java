package com.example.messaging.service;

import com.example.messaging.model.Question;
import com.example.messaging.repository.QuestionRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }


    @Override
    public Question save(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public List<Question> findTopQuestion(int number) {
        return questionRepository.findTopQuestion(number);
    }

    @Override
    public int findCountQuestionByName(Question question, int count) {
        List<Question> questionByName = questionRepository.findQuestionByNameContains(question.getName().split(" ")[0]);
        String[] strings = question.getName().split(" ");
        ExecutorService executor = Executors.newCachedThreadPool();

        AtomicInteger max = new AtomicInteger(0);
        AtomicInteger countString = new AtomicInteger(0);
        AtomicInteger countSimilarWords = new AtomicInteger(0);

        for (Question aloneQuestion : questionByName) {
            String[] qName = aloneQuestion.getName().split(" ");
            executor.execute(() -> {
                while (countString.get() < count) {
                    for (String s : qName) {
                        if (s.length() > 3 && Objects.equals(s, strings[countString.get()])) {
                            countSimilarWords.incrementAndGet();
                        }
                        if (max.get() < countSimilarWords.get()) {
                            max.set(countSimilarWords.get());
                        }
                    }
                    countString.incrementAndGet();
                }
            });
        }
        ifPresent(max.get(), question);
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return max.get();
    }

    private void ifPresent(int countQuestion, Question question) {
        if (countQuestion == 0) {
            questionRepository.save(question);
        }
    }
}
