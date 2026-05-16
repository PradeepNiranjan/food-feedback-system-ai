package com.food.ai.feedback;

import java.util.Scanner;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.food.ai.feedback.AIService.FeedbackAIService;
import com.food.ai.feedback.service.FeedbackService;

@SpringBootApplication
public class FeedbackApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(FeedbackApplication.class, args);

		do {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter your query here:");
		String userQuery = scanner.nextLine();

		// Process the user query using the FeedbackAIService
		FeedbackAIService feedbackAIService = context.getBean(FeedbackAIService.class);
		String aiResponse = feedbackAIService.processFeedbackQuery(userQuery);
		System.out.println(aiResponse);
	}while(true);
	}


	@Bean
SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/h2-console/**").permitAll()
            .anyRequest().authenticated())
        .csrf(csrf -> csrf.disable())
        .headers(headers -> headers.frameOptions(frame -> frame.disable()));

    return http.build();
}

	@Bean
	public ChatClient chatClient(OpenAiChatModel chatModel, FeedbackService feedbackService) {
		return ChatClient.builder(chatModel).defaultTools(feedbackService).build();
	}

// 	@Bean
// public ChatClient chatClient(OpenAiChatModel chatModel, FeedbackService feedbackService) {
//     return ChatClient.builder(chatModel)
//             .tool(Tool.of("getAllFeedback", "Get all feedback", 
//                 Map.of("type", "object", "properties", Map.of())))
//             .tool(Tool.of("saveFeedback", "Save feedback", 
//                 Map.of(
//                     "type", "object",
//                     "properties", Map.of(
//                         "rating", Map.of("type", "integer"),
//                         "comments", Map.of("type", "string"),
//                         "foodItem", Map.of("type", "string")
//                     ),
//                     "required", List.of("rating", "comments", "foodItem")
//                 )))
//             .toolHandler((tool, args) -> {
//                 if ("getAllFeedback".equals(tool.getName())) {
//                     return feedbackService.getAllFeedback().toString();
//                 }
//                 if ("saveFeedback".equals(tool.getName())) {
//                     Feedback feedback = parseFeedback(args);
//                     feedbackService.saveFeedback(feedback);
//                     return "Feedback saved";
//                 }
//                 throw new IllegalArgumentException("Unknown tool: " + tool.getName());
//             })
//             .build();
// }

}
