package com.deskover.api.admin;

import java.util.List;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deskover.configuration.security.payload.response.MessageResponse;
import com.deskover.entity.Brand;
import com.deskover.repository.BrandRepository;
import com.deskover.service.BrandService;
import com.deskover.util.ValidationUtil;

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

    @PostMapping("/brands")
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

    @PutMapping("/brands")
    public ResponseEntity<?> doUpdate(@RequestBody Brand brand) {
        try {
            Brand brandUpdated = service.update(brand);
            return ResponseEntity.ok(brandUpdated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @PostMapping("/brands/datatables")
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
