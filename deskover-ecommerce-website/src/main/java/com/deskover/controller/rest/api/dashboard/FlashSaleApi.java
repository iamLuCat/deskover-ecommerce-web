package com.deskover.controller.rest.api.dashboard;

import com.deskover.service.FlashSaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("v1/api/admin/flash-sales")
public class FlashSaleApi {
    @Autowired
    private FlashSaleService flashSaleService;

    /* Truy xuất tất cả Flash Sale dạng Datatables */
    @PostMapping("/datatables")
    public ResponseEntity<?> getAllFlashSaleForDatatables(
            @Valid @RequestBody DataTablesInput input,
            @RequestParam("isActive") Optional<Boolean> isActive) {
        return ResponseEntity.ok(flashSaleService.getByActiveForDatatables(input, isActive.orElse(Boolean.TRUE)));
    }


}
