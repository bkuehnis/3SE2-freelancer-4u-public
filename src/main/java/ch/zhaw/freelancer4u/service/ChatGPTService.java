package ch.zhaw.freelancer4u.service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.web.reactive.function.client.WebClient;

import ch.zhaw.freelancer4u.model.chatGPT.Message;
import ch.zhaw.freelancer4u.model.chatGPT.MessageResponse;
import ch.zhaw.freelancer4u.model.chatGPT.Messages;
import reactor.core.publisher.Mono;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@Service
public class ChatGPTService {
    private static final String CHAT_GPT_API_KEY = "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";

    private static final String CHAT_GPT_BASE_URL = "https://api.openai.com";

    //Timeout was set to 10 seconds, because chat-gpt tends to take a few seconds to respond.
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(15);
    private static final String USER_AGENT = "Spring 5 WebClient";
    private static final Logger logger = LoggerFactory.getLogger(ChatGPTService.class);

    private final WebClient webClient;

    @Autowired
    public ChatGPTService() {
        this.webClient = WebClient.builder()
                .baseUrl(CHAT_GPT_BASE_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.USER_AGENT, USER_AGENT)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + CHAT_GPT_API_KEY)
                .filter(ServiceUtils.logRequest(logger))
                .build();
    }

    public MessageResponse chatWithChatGpt(String messageContent) {
        
        Messages messages = new Messages();
        Message message = new Message();
        message.setRole("user");
        message.setContent(messageContent);
        messages.setMessages(new ArrayList<Message>(List.of(message)));

        var messageResposen =  webClient
                //http-method
                .post() 

                // uri to api
                .uri("/v1/chat/completions") 

                //converts the class to json and uses it as request body (serialization).
                .body(Mono.just(messages), Messages.class) 
                .retrieve()

                //conversts the response to a java object (deserialization).
                .bodyToMono(MessageResponse.class)

                .block(REQUEST_TIMEOUT); 
        return messageResposen;
    }
}
