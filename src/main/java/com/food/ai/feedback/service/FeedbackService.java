package com.food.ai.feedback.service;

import java.util.List;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food.ai.feedback.entity.Feedback;
import com.food.ai.feedback.repository.FeedbackRepository;

import lombok.extern.slf4j.Slf4j;



@Service    
@Slf4j
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

@Tool(name = "saveFeedback", description = "Save feedback")
public void saveFeedback(Feedback feedback) {
    //log.info("Saving feedback: " + feedback);
    feedbackRepository.save(feedback);
    //log.info("feedback save : {}", feedback);
}

@Tool(name = "getAllFeedback", description = "Get all feedback")
public List<Feedback> getAllFeedback() {
    //log.info("Retrieving all feedback");
    List<Feedback> feedbackList = feedbackRepository.findAll();
    //log.info("Retrieved all feedback: {}", feedbackList);
    return feedbackList;
}

}