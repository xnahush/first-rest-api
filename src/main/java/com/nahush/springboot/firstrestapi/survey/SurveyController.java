package com.nahush.springboot.firstrestapi.survey;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class SurveyController {
    private final SurveyService surveyService;

    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @RequestMapping(value = "surveys", method = RequestMethod.GET)
    public List<Survey> retrieveAllSurveys(){
        return surveyService.getAllSurveys();
    }

    @RequestMapping(value = "surveys/{id}", method = RequestMethod.GET)
    public Survey retrieveSurveyById(@PathVariable String id){
        Survey survey = surveyService.getSurveyById(id);
        if (survey == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return survey;
    }

    @RequestMapping(value = "surveys/{id}/questions", method = RequestMethod.GET)
    public List<Question> retrieveQuestionsBySurveyId(@PathVariable String id){
        List<Question> questions = surveyService.getAllSurveyQuestions(id);
        if(questions == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return questions;
    }

    @RequestMapping(value = "surveys/{id}/questions/{questionId}", method = RequestMethod.GET)
    public Question retrieveQuestionsById(@PathVariable String id, @PathVariable String questionId){
        Question question = surveyService.getQuestionById(id, questionId);
        if(question == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return question;
    }

    @RequestMapping(value = "surveys/{id}/questions", method = RequestMethod.POST)
    public ResponseEntity<Object> addNewSurveyQuestion(@PathVariable String id, @RequestBody Question question){
        String randomQuestionId = surveyService.addNewSurveyQuestion(id, question);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{questionId}").buildAndExpand(randomQuestionId).toUri();
        return ResponseEntity.created(location).build();
    }

    @RequestMapping(value = "surveys/{id}/questions/{questionId}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteQuestionsById(@PathVariable String id, @PathVariable String questionId){
        String s = surveyService.deleteQuestion(id, questionId);
        if (s == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "surveys/{id}/questions/{questionId}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateQuestionsById(@PathVariable String id, @PathVariable String questionId, @RequestBody Question question){
        surveyService.updateQuestionById(id, questionId, question);
        return ResponseEntity.noContent().build();
    }
}
