package com.deskover.controller.rest.api.dashboard;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.deskover.model.entity.database.Discount;
import com.deskover.model.entity.database.FlashSale;
import com.deskover.model.entity.dto.security.payload.MessageResponse;
import com.deskover.other.util.MessageErrorUtil;
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
	public ResponseEntity<?> doSave(@Valid @RequestBody FlashSale flashSale, BindingResult result) {
		try {
			return ResponseEntity.ok(flashSaleService.create(flashSale));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}
	}

	@DeleteMapping("")
	public ResponseEntity<?> doDelete(@RequestParam("id") Long id) {
		try {
			flashSaleService.delete(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}
	}

	@GetMapping("")
	public ResponseEntity<?> doGetById(@RequestParam("id") Long id) {
		try {
			return ResponseEntity.ok(flashSaleService.getById(id));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}
	}

	@PutMapping()
	public ResponseEntity<?> doUpdateProductsToFlashSale(@RequestBody FlashSale flashSale,
			@RequestParam("productIdToAdd") Optional<Long> productIdToAdd,
			@RequestParam("productIdToRemove") Optional<Long> productIdToRemove) {
		try {
			flashSaleService.updateProductToFlashSale(flashSale, productIdToAdd.orElse(null),
					productIdToRemove.orElse(null));
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			MessageResponse error = MessageErrorUtil.message("Cập nhập không thành công", e);
			return ResponseEntity.badRequest().body(error);
		}
	}
}
