package com.ddwu.notalonemarket.dto;

public class ChatRoomCreateDTO {
    private Long postId;
    private Long hostId;

    // 생성자, Getter, Setter
    public ChatRoomCreateDTO() {}

    public ChatRoomCreateDTO(Long postId, Long hostId) {
        this.postId = postId;
        this.hostId = hostId;
    }

    public Long getPostId() { return postId; }
    public void setPostId(Long postId) { this.postId = postId; }

    public Long getHostId() { return hostId; }
    public void setHostId(Long hostId) { this.hostId = hostId; }
}
