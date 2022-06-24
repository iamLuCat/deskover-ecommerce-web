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

@RestController
@CrossOrigin("*")
@RequestMapping("v1/api/admin")
public class BrandApi {

    @Autowired
    BrandService service;

    @Autowired
    BrandRepository repo;

    /**
     * Get All brand
     *
     * @return List<Brand>
     */
    @GetMapping("/brand/get-all")
    public ResponseEntity<?> doGetAll() {
        List<Brand> listBrand = service.getAll();
        return ResponseEntity.ok(listBrand);
    }

    /**
     * Get All brand Actived
     *
     * @return List<Brand>
     */
    @GetMapping("/brand/get-all-brand-actived")
    public ResponseEntity<?> doGetAllBrandIsActived() {
        List<Brand> listBrand = service.getAllBrandIsActived();
        if (listBrand == null) {
            return ResponseEntity.ok(new MessageResponse("Không có brand nào đang hoạt động"));
        }
        return ResponseEntity.ok(listBrand);
    }

    /**
     * Get brand By Id
     *
     * @param Long id
     * @return Brand
     */
    @GetMapping("/brand/{id}")
    public ResponseEntity<?> doGetById(@PathVariable("id") Long id) {
        Brand brand = service.getById(id);
        if (brand == null) {
            return ResponseEntity.ok(new MessageResponse("Không tìm thấy brand có id: " + id));
        }
        return ResponseEntity.ok(brand);
    }

    /**
     * Get brand By Slug
     * @param String slug
     * @return Brand
     */
//	@GetMapping("/brand")
//	public ResponseEntity<?> doGetBySlug(@RequestParam(name = "slug") String slug) {
//		Brand brand = service.getBySlug(slug);
//		if (brand == null) {
//			return ResponseEntity.ok(new MessageResponse("Không tìm thấy brand có slug: " + slug));
//		}
//		return ResponseEntity.ok(brand);
//	}

    /**
     * Create brand
     *
     * @param Brand brand
     * @return Brand
     */
    @PostMapping("/brand/create")
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

    /**
     * Create brand
     *
     * @param Long id
     * @return brand
     */
    @PutMapping("/brand/delete/{id}")
    public ResponseEntity<?> doDelete(@PathVariable("id") Long id) {
        try {
            service.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/brand/update/{id}")
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

    @PostMapping("/brand/datatables")
    public ResponseEntity<?> doGetForDatatables(@Valid @RequestBody DataTablesInput input) {
        return ResponseEntity.ok(service.getAllForDatatables(input));
    }

    @DeleteMapping("/brand/{id}")
    public ResponseEntity<?> doChangeActive(@PathVariable("id") Long id) {
        try {
            service.changeActived(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
