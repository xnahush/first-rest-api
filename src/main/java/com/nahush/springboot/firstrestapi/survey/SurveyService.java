package com.nahush.springboot.firstrestapi.survey;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class SurveyService {
    private static List<Survey> surveys = new ArrayList<>();

    static {
        Question question1 = new Question("Question1",
                "Most Popular Cloud Platform Today", Arrays.asList(
                "AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");
        Question question2 = new Question("Question2",
                "Fastest Growing Cloud Platform", Arrays.asList(
                "AWS", "Azure", "Google Cloud", "Oracle Cloud"), "Google Cloud");
        Question question3 = new Question("Question3",
                "Most Popular DevOps Tool", Arrays.asList(
                "Kubernetes", "Docker", "Terraform", "Azure DevOps"), "Kubernetes");

        List<Question> questions1 = new ArrayList<>(Arrays.asList(question1,
                question2, question3));
        List<Question> questions2 = new ArrayList<>(Arrays.asList(question1,
                question2));

        Survey survey1 = new Survey("Survey1", "My Favorite Survey",
                "Description of the Survey", questions1);
        Survey survey2 = new Survey("Survey2", "Popular survey",
                "Description of the Survey", questions2);

        surveys.add(survey1);
        surveys.add(survey2);

    }

    public List<Survey> getAllSurveys() {
        return surveys;
    }

    public Survey getSurveyById(String id){
        Predicate<? super Survey> predicate = survey -> survey.getId().equalsIgnoreCase(id);
        Optional<Survey> optionalSurvey = surveys.stream().filter(predicate).findFirst();
        return optionalSurvey.orElse(null);
    }

    public List<Question> getAllSurveyQuestions(String surveyId){
        Survey survey = getSurveyById(surveyId);
        if(survey == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return survey.getQuestions();
    }

    public Question getQuestionById(String surveyId, String questionId) {
        List<Question> questions = getAllSurveyQuestions(surveyId);
        Predicate<? super Question> predicate = question -> question.getId().equalsIgnoreCase(questionId);
        Optional<Question> optionalQuestion = questions.stream().filter(predicate).findFirst();
        return optionalQuestion.orElse(null);
    }

    public String addNewSurveyQuestion(String surveyId, Question question){
        question.setId(generateRandomId());
        getAllSurveyQuestions(surveyId).add(question);
        return question.getId();
    }

    private String generateRandomId() {
        SecureRandom secureRandom = new SecureRandom();
        return new BigInteger(32, secureRandom).toString();
    }

    public String deleteQuestion(String surveyId, String questionId){
        List<Question> questions = getAllSurveyQuestions(surveyId);
        if (questions == null) return null;
        Predicate<? super Question> predicate = question -> question.getId().equalsIgnoreCase(questionId);
        boolean removed = questions.removeIf(predicate);
        if (!removed) return null;
        return questionId;
    }

    public void updateQuestionById(String surveyId, String questionId, Question question){
        deleteQuestion(surveyId, questionId);
        addNewSurveyQuestion(surveyId, question);
    }
}
