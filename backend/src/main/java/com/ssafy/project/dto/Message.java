package com.ssafy.project.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Message {
	private MessageType type;
	private String content;
	private String sender;
}
