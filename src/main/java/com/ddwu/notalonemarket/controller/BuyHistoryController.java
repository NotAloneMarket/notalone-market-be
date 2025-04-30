// --- BuyHistoryController.java ---
package com.ddwu.notalonemarket.controller;

import com.ddwu.notalonemarket.dto.BuyHistoryDTO;
import com.ddwu.notalonemarket.service.BuyHistoryService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buy-history")
public class BuyHistoryController {

    @Autowired
    private BuyHistoryService buyHistoryService;

    @GetMapping
    public ResponseEntity<List<BuyHistoryDTO>> getBuyHistory(HttpSession session) {
        Long userId = (Long) session.getAttribute("loginUserId");

        if (userId == null) {
            return ResponseEntity.badRequest().build();
        }

        List<BuyHistoryDTO> historyList = buyHistoryService.getBuyHistory(userId);
        return ResponseEntity.ok(historyList);
    }
}
