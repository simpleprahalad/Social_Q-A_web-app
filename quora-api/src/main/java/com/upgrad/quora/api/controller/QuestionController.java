package com.upgrad.quora.api.controller;


import com.upgrad.quora.api.model.QuestionEditRequest;
import com.upgrad.quora.api.model.QuestionRequest;
import com.upgrad.quora.api.model.QuestionResponse;
import com.upgrad.quora.service.business.QuestionBusinessService;
import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.InvalidQuestionException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class QuestionController {

    @Autowired
    private QuestionBusinessService questionBusinessService;

    @RequestMapping(method = RequestMethod.POST,
            path = "/question/create",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<QuestionResponse> createQuestion(final QuestionRequest questionRequest,
                                                           @RequestHeader("authorization") final String authorization)
            throws AuthorizationFailedException, UserNotFoundException {
        QuestionEntity questionEntity = questionBusinessService.createQuestion(questionRequest.getContent(), authorization);

        final QuestionResponse questionResponse = new QuestionResponse().id(questionEntity.getUuid()).status("QUESTION CREATED");
        return new ResponseEntity<>(questionResponse, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT,
            path = "/question/edit/{questionId}",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<QuestionResponse> editQuestion(@PathVariable("questionId") final String questionUuid,
                                                         final QuestionEditRequest questionEditRequest,
                                                         @RequestHeader("authorization") final String authorization)
            throws AuthorizationFailedException, InvalidQuestionException {
        QuestionEntity questionEntity = questionBusinessService.editQuestion(questionUuid,questionEditRequest.getContent(), authorization);

        final QuestionResponse questionResponse = new QuestionResponse().id(questionEntity.getUuid()).status("QUESTION EDITED");
        return new ResponseEntity<>(questionResponse, HttpStatus.OK);
    }

}
