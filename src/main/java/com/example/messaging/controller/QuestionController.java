package com.example.messaging.controller;

import com.example.messaging.dto.QuestionRequestDto;
import com.example.messaging.dto.QuestionResponseDto;
import com.example.messaging.dto.mapper.QuestionMapper;
import com.example.messaging.model.Question;
import com.example.messaging.service.QuestionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/questions")
public class QuestionController {
    private final QuestionService questionService;
    private final QuestionMapper questionMapper;

    public QuestionController(QuestionService questionService, QuestionMapper questionMapper) {
        this.questionService = questionService;
        this.questionMapper = questionMapper;
    }

    @GetMapping("/top")
    public List<QuestionResponseDto> topQuestion(@RequestParam int number) {
        return questionService.findTopQuestion(number)
                .stream()
                .map(questionMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @PostMapping()
    public int similarQuestions(@RequestBody QuestionRequestDto request, @RequestParam int count) {
        Question question = questionMapper.toModel(request);
        return questionService.findCountQuestionByName(question, count);
    }
}
