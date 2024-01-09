package com.brandon.chatsystem.chat;

import lombok.*;

import java.awt.*;
import java.net.Proxy;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ChatMessage {

    private String content; //this is the actual message
    private String sender; //this is the actual person who sent the message

    private MessageType type;


}
