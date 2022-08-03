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
import org.springframework.web.server.ResponseStatusException;

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
	public ResponseEntity<?> getAllFlashSaleForDatatables(@Valid @RequestBody DataTablesInput input,
			@RequestParam("isActive") Optional<Boolean> isActive) {
		return ResponseEntity.ok(flashSaleService.getByActiveForDatatables(input, isActive.orElse(Boolean.TRUE)));
	}

	@GetMapping("/checkActive")
	public void doCheckActiveFlashSale() {
		flashSaleService.isCheckActived();
	}

	@PostMapping("")
	public ResponseEntity<?> save(@Valid @RequestBody FlashSale flashSale, BindingResult result) {
		try {
			flashSaleService.create(flashSale);
			return ResponseEntity.ok(flashSaleService.create(flashSale));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}
	}

	@GetMapping("")
	public ResponseEntity<?> getById(@RequestParam("id") Long id) {
		try {
			return ResponseEntity.ok(flashSaleService.getById(id));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}
	}
}
