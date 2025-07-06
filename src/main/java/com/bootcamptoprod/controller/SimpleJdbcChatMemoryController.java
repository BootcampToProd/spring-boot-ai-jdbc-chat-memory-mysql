package com.bootcamptoprod.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class SimpleJdbcChatMemoryController {

    private final ChatClient chatClient;

    private final ChatMemory chatMemory;

    public SimpleJdbcChatMemoryController(ChatClient chatClient, ChatMemory chatMemory) {
        this.chatClient = chatClient;
        this.chatMemory = chatMemory;
    }

    // Endpoint to send messages and get responses
    @PostMapping
    public String chat(@RequestBody String request) {
        return chatClient.prompt()
                .user(request)
                .call()
                .content();
    }

    // Endpoint to view conversation history
    @GetMapping("/history")
    public List<Message> getHistory() {
        return chatMemory.get("default");
    }

    // Endpoint to clear conversation history
    @DeleteMapping("/history")
    public String clearHistory() {
        chatMemory.clear("default");
        return "Conversation history cleared";
    }
}
