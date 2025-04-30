package com.ddwu.notalonemarket.service;

import com.ddwu.notalonemarket.dto.BuyHistoryDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BuyHistoryService {

    private static final List<BuyHistoryDTO> dummyData = new ArrayList<>();

    static {
        dummyData.add(new BuyHistoryDTO(1L, 1001L, 2001L, 2, LocalDateTime.now().minusDays(2)));
        dummyData.add(new BuyHistoryDTO(2L, 1002L, 2002L, 1, LocalDateTime.now().minusDays(1)));
        dummyData.add(new BuyHistoryDTO(3L, 1001L, 2003L, 3, LocalDateTime.now()));
    }

    public List<BuyHistoryDTO> getBuyHistory(Long userId) {
        List<BuyHistoryDTO> result = new ArrayList<>();
        for (BuyHistoryDTO dto : dummyData) {
            if (dto.getUserId().equals(userId)) {
                result.add(dto);
            }
        }
        return result;
    }
}
