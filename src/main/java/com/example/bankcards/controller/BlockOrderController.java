package com.example.bankcards.controller;

import com.example.bankcards.dto.blockorder.BlockOrderRequest;
import com.example.bankcards.dto.mapper.BlockOrderMapper;
import com.example.bankcards.dto.success.SuccessResponse;
import com.example.bankcards.entity.BlockOrder;
import com.example.bankcards.service.BlockOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/block-requests")
@RequiredArgsConstructor
@Tag(name = " BlockRequest Controller", description = " BlockRequest API")
public class BlockOrderController {

    private final BlockOrderService blockOrderService;
    private final BlockOrderMapper blockOrderMapper;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all pending card block requests")
    public ResponseEntity<List<BlockOrderRequest>> getPendingRequests() {
        List<BlockOrder> requests = blockOrderService.getPendingRequests();
        return ResponseEntity.ok(blockOrderMapper.toDto(requests));
    }

    @PostMapping("/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Approve a card block request")
    public ResponseEntity<SuccessResponse> approve(@PathVariable Long id) {
        blockOrderService.approveRequest(id);
        return ResponseEntity.ok(new SuccessResponse("Request approved and card blocked"));
    }

    @PostMapping("/{id}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Reject a card block request")
    public ResponseEntity<SuccessResponse> reject(@PathVariable Long id) {
        blockOrderService.rejectRequest(id);
        return ResponseEntity.ok(new SuccessResponse("Request rejected"));
    }
}