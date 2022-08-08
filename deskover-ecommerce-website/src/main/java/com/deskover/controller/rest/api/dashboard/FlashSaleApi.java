package com.deskover.controller.rest.api.dashboard;

import com.deskover.model.entity.database.FlashSale;
import com.deskover.model.entity.dto.security.payload.MessageResponse;
import com.deskover.other.util.MessageErrorUtil;
import com.deskover.service.FlashSaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("v1/api/admin/flash-sales")
public class FlashSaleApi {
	@Autowired
	private FlashSaleService flashSaleService;

	@PostMapping("/datatables")
	public ResponseEntity<?> getAllFlashSaleForDatatables(@Valid @RequestBody DataTablesInput input) {
		return ResponseEntity.ok(flashSaleService.getByActiveForDatatables(input));
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

	@DeleteMapping("/{flashSaleId}")
	public ResponseEntity<?> doDelete(@PathVariable Long flashSaleId) {
		try {
			flashSaleService.delete(flashSaleId);
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

	@PutMapping("/status-toggle/{flashSaleId}")
	public ResponseEntity<?> doChangeActive(@PathVariable("flashSaleId") Long flashSaleId) {
		try {
			flashSaleService.activeToggle(flashSaleId);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			MessageResponse error = MessageErrorUtil.message("Cập nhập không thành công", e);
			return ResponseEntity.badRequest().body(error);
		}
	}

}
