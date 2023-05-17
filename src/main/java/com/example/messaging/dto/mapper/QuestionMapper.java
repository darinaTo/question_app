package com.example.messaging.dto.mapper;

import com.example.messaging.dto.QuestionRequestDto;
import com.example.messaging.dto.QuestionResponseDto;
import com.example.messaging.model.Question;
import org.springframework.stereotype.Component;

@Component
public class QuestionMapper {
    public QuestionResponseDto toResponseDto(Question question) {
        QuestionResponseDto questionResponseDto = new QuestionResponseDto();
        questionResponseDto.setId(question.getId());
        questionResponseDto.setName(question.getName());
        return questionResponseDto;
    }

    public Question toModel(QuestionRequestDto questionRequestDto) {
        Question question = new Question();
        question.setName(questionRequestDto.getName());
        return question;
    }
}
