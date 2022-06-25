package com.deskover.api.admin;

import com.deskover.configuration.security.payload.response.MessageResponse;
import com.deskover.entity.Brand;
import com.deskover.repository.BrandRepository;
import com.deskover.service.BrandService;
import com.deskover.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("v1/api/admin")
public class BrandApi {

    @Autowired
    BrandService service;

    @Autowired
    BrandRepository repo;

    @GetMapping("/brands")
    public ResponseEntity<?> doGetAll() {
        List<Brand> listBrand = service.getAll();
        return ResponseEntity.ok(listBrand);
    }

    @GetMapping("/brands/actived")
    public ResponseEntity<?> doGetAllBrandIsActived() {
        List<Brand> listBrand = service.getAllBrandIsActived();
        if (listBrand == null) {
            return ResponseEntity.ok(new MessageResponse("Không có brand nào đang hoạt động"));
        }
        return ResponseEntity.ok(listBrand);
    }

    @GetMapping("/brands/{id}")
    public ResponseEntity<?> doGetById(@PathVariable("id") Long id) {
        Brand brand = service.getById(id);
        if (brand == null) {
            return ResponseEntity.ok(new MessageResponse("Không tìm thấy brand có id: " + id));
        }
        return ResponseEntity.ok(brand);
    }

    @PostMapping("/brand")
    public ResponseEntity<?> doCreate(@Valid @RequestBody Brand brand, BindingResult result) {
        if(result.hasErrors()){
            MessageResponse errors = ValidationUtil.ConvertValidationErrors(result);
            return ResponseEntity.badRequest().body(errors);
        }
        try {
            if (repo.existsBySlug(brand.getSlug())) {
                return ResponseEntity.ok(new MessageResponse("Slug này đã tồn tại"));
            }
            service.create(brand);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/brand/{id}")
    public ResponseEntity<?> doUpdate(@PathVariable("id") Long id, @RequestBody Brand brand) {
        try {
            if (brand.getSlug() != null && service.getById(id).getSlug().equals(brand.getSlug())) {
                service.update(id, brand);
            } else if (brand.getSlug() != null && service.existsBySlug(brand.getSlug())) {
                return ResponseEntity.ok(new MessageResponse("Slug này đã tồn tại"));
            }
            service.update(id, brand);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/brand/datatables-by-active")
    public ResponseEntity<?> doGetForDatatablesByActive(@Valid @RequestBody DataTablesInput input, @RequestParam("isActive") Optional<Boolean> isActive) {
        return ResponseEntity.ok(service.getByActiveForDatatables(input, isActive.orElse(Boolean.TRUE)));
    }

    @DeleteMapping("/brands/{id}")
    public ResponseEntity<?> doChangeActive(@PathVariable("id") Long id) {
        try {
            service.changeActived(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
