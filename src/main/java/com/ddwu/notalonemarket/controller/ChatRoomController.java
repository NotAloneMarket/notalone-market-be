package com.ddwu.notalonemarket.controller;

import com.ddwu.notalonemarket.domain.User;
import com.ddwu.notalonemarket.dto.ChatRoomCreateDTO;
import com.ddwu.notalonemarket.dto.ChatRoomDTO;
import com.ddwu.notalonemarket.dto.MessageResponseDTO;
import com.ddwu.notalonemarket.service.ChatRoomService;
import com.ddwu.notalonemarket.service.UserService;
import com.ddwu.notalonemarket.util.JwtUtil;
import com.ddwu.notalonemarket.service.ChatMessageService;
import com.ddwu.notalonemarket.service.ChatParticipantService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/chatrooms")
public class ChatRoomController {

	@Autowired
	private ChatRoomService chatRoomService;

	@Autowired
	private ChatMessageService chatMessageService;

	@Autowired
	private ChatParticipantService participantService;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserService userService;

	@PostMapping("")
	public ResponseEntity<?> createRoom(@RequestBody ChatRoomCreateDTO dto,
	                                    @RequestHeader("Authorization") String authHeader) {
	    System.out.println("✅ createRoom() 호출됨");
	    System.out.println("👉 받은 postId: " + dto.getPostId());
	    System.out.println("👉 받은 hostId: " + dto.getHostId());
	    System.out.println("👉 Authorization: " + authHeader);

	    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("토큰 누락 또는 잘못됨");
	    }

	    // Optional: 토큰에서 loginId 추출해 검증하기
	    try {
	        String token = authHeader.substring(7);
	        String loginId = jwtUtil.extractLoginId(token);
	        User user = userService.findByLoginId(loginId);
	        if (user == null) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("사용자 없음");
	        }

	        // 권한 있는 사용자만 채팅방 생성 가능하게 하려면 여기서 추가 검증 가능

	        Long roomId = chatRoomService.createRoom(dto);
	        participantService.join(roomId, dto.getHostId(), true);

	        return ResponseEntity.ok(Map.of("roomId", roomId));
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 에러: " + e.getMessage());
	    }
	}


	

	// 유저가 참여한 채팅방 목록 조회
	@GetMapping("")
	public ResponseEntity<?> getRooms(@RequestHeader("Authorization") String authHeader) {
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
		}

		String token = authHeader.substring(7);
		String loginId = jwtUtil.extractLoginId(token);

		User user = userService.findByLoginId(loginId);
		if (user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}

		List<ChatRoomDTO> rooms = participantService.getRoomsByUserId(user.getUserId());
		return ResponseEntity.ok(rooms);
	}

	@PostMapping("/{roomId}/join")
	public ResponseEntity<?> joinRoom(@PathVariable Long roomId, @RequestHeader("Authorization") String authHeader) {
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
		}

		String token = authHeader.substring(7);
		String loginId = jwtUtil.extractLoginId(token);

		User user = userService.findByLoginId(loginId);
		if (user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}

		Long userId = user.getUserId();

		if (!participantService.isUserInRoom(userId, roomId)) {
			participantService.join(roomId, userId, false);
		}

		return ResponseEntity.ok(Map.of("message", "joined"));
	}

	// 특정 채팅방의 메시지 반환
	@GetMapping("/{roomId}/messages")
	public List<MessageResponseDTO> getMessages(@PathVariable Long roomId) {
		return chatMessageService.getMessageDTOsByRoomId(roomId);
	}

	// 채팅방 거래 완료 처리
	@PutMapping("/{roomId}/complete")
	public String completeRoom(@PathVariable Long roomId) {
		chatRoomService.completeRoom(roomId);
		return "completed";
	}

	// 채팅방 참여자 수 반환 API
	@GetMapping("/post/{postId}/room")
	public ResponseEntity<?> getChatRoomIdByPostId(@PathVariable Long postId) {
	    try {
	        Long chatRoomId = chatRoomService.findRoomIdByPostId(postId);
	        return ResponseEntity.ok(Map.of("chatRoomId", chatRoomId));
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "채팅방을 찾을 수 없습니다."));
	    }
	}
	
	// 디테일 페이지 동그라미 
	@GetMapping("/{roomId}/count")
	public ResponseEntity<?> countParticipants(@PathVariable Long roomId, @RequestHeader("Authorization") String authHeader) {
	    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
	    }

	    try {
	        long count = participantService.countParticipantsByRoomId(roomId);
	        return ResponseEntity.ok(Map.of("participantCount", count));
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
	    }
	}

}
