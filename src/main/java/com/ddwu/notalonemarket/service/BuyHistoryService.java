package com.ddwu.notalonemarket.service;

import com.ddwu.notalonemarket.dto.BuyHistoryResponseDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BuyHistoryService {

    public List<BuyHistoryResponseDTO> getBuyHistory(Long userId) {
        List<BuyHistoryResponseDTO> list = new ArrayList<>();

        // 더미 데이터 예시
        if (userId == 1001L) {
            list.add(new BuyHistoryResponseDTO(1L, "쉐이크 공동구매", 2000, 2, LocalDateTime.of(2025, 5, 20, 15, 0), "/img/shake.png"));
        }
        return list;
    }
}
