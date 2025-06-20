package com.ddwu.notalonemarket.domain;

import java.util.HashSet;
import java.util.Set;

public class ChatRoom {
    private String roomId;
    private String name;
    private Set<String> participants = new HashSet<>();

    public ChatRoom(String roomId, String name) {
        this.roomId = roomId;
        this.name = name;
    }

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<String> getParticipants() {
		return participants;
	}

	public void setParticipants(Set<String> participants) {
		this.participants = participants;
	}
}
