package com.deskover.apis.dashboard;

import com.deskover.dto.MessageResponse;
import com.deskover.entity.Brand;
import com.deskover.reponsitory.BrandRepository;
import com.deskover.service.BrandService;
import com.deskover.utils.ValidationUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'SELLER')")
@RequestMapping("v1/api/admin/brands")
public class BrandApi {

    @Autowired
    BrandService service;

    @Autowired
    BrandRepository repo;

    @GetMapping()
    public ResponseEntity<?> doGetAll() {
        List<Brand> listBrand = service.getAll();
        return ResponseEntity.ok(listBrand);
    }

    @GetMapping("/actived")
    public ResponseEntity<?> doGetAllBrandIsActived() {
        List<Brand> listBrand = service.getAllBrandIsActived();
        if (listBrand == null) {
            return ResponseEntity.ok(new MessageResponse("Không có brand nào đang hoạt động"));
        }
        return ResponseEntity.ok(listBrand);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> doGetById(@PathVariable("id") Long id) {
        Brand brand = service.getById(id);
        if (brand == null) {
            return ResponseEntity.ok(new MessageResponse("Không tìm thấy brand có id: " + id));
        }
        return ResponseEntity.ok(brand);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PostMapping()
    public ResponseEntity<?> doCreate(@Valid @RequestBody Brand brand, BindingResult result) {
        if(result.hasErrors()){
            MessageResponse errors = ValidationUtil.ConvertValidationErrors(result);
            return ResponseEntity.badRequest().body(errors);
        }
        try {
            Brand brandCreated = service.create(brand);
            return ResponseEntity.ok().body(brandCreated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PutMapping()
    public ResponseEntity<?> doUpdate(@RequestBody Brand brand) {
        try {
            Brand brandUpdated = service.update(brand);
            return ResponseEntity.ok(brandUpdated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PostMapping("/datatables")
    public ResponseEntity<?> doGetForDatatablesByActive(@Valid @RequestBody DataTablesInput input, @RequestParam("isActive") Optional<Boolean> isActive) {
        return ResponseEntity.ok(service.getByActiveForDatatables(input, isActive.orElse(Boolean.TRUE)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> doChangeActive(@PathVariable("id") Long id) {
        try {
            service.changeActived(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
