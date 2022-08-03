package com.deskover.controller.rest.api.dashboard;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deskover.model.entity.database.FlashSale;
import com.deskover.service.FlashSaleService;

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

    @GetMapping("/checkActive")
	public void doCheckActiveFlashSale() {
		flashSaleService.isCheckActived();
	}
    
    @PostMapping("")
    public ResponseEntity<?> save(@Valid @RequestBody FlashSale flashSale, BindingResult result){
    	try {
    		flashSaleService.save(flashSale);
			return ResponseEntity.ok(flashSaleService.save(flashSale));
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
    }
}
