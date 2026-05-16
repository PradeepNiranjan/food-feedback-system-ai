package com.food.ai.feedback.AIService;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackAIService {

@Autowired
private ChatClient chatClient;

// private String systemPrompt = "You are a helpful assistant for analyzing customer feedback. " +
//         "You will receive feedback data in JSON format and provide insights based on the feedback. " +
//         "Focus on identifying common themes, sentiment, and actionable suggestions for improvement.";

private String systemprompt = """
    you are an assistant for a food feedback system.

    If the user asks to "Show all feedback", "what all feedback do we have?", "what feedback do we have?", 
    "what feedback do we have so far" you should call the tool "getAllFeedback" and return the result to the user.

    If the user asks to "submit feedback", "I want to submit feedback", "I want to give feedback", ask the user for the requierd 
     details as per the entity and call the tool saveFeedback.

    If the user asks about the rating and food details you should provide the details of the feedback as per the user query.

     If the user asks any other question related to feedback, you should provide the answer based on the feedback data.
     If you don't have enough information to answer the user's question, you should ask the user for more details or clarify their question.
     Always try to provide a helpful and informative response to the user's questions about feedback.
     """; 

     public String processFeedbackQuery(String userQuery) {
        // Create a chat request with the system prompt and user query
        // ChatRequest chatRequest = new ChatRequest(systemprompt, userQuery);

        // Send the chat request to the AI model and get the response
        try {
        String aiResponse = chatClient.prompt().system(systemprompt).user(userQuery).call().content();

        return aiResponse;
     }  catch (Exception e) {
        e.printStackTrace();
        return "Sorry, I encountered an error while processing your request.";
     }
   }

}
