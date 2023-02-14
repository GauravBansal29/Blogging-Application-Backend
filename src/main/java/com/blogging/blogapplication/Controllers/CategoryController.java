package com.blogging.blogapplication.Controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.blogging.blogapplication.Payloads.CategoryDto;
import com.blogging.blogapplication.Services.CategoryService;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    CategoryService catService;

    // create a new category
    @PostMapping("/")
    public ResponseEntity<CategoryDto> addCategory(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto newcategory = catService.createcategory(categoryDto);
        return new ResponseEntity<>(newcategory, null, 200);
    }

    // get category by id
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) {
        CategoryDto categoryDto = catService.getCategoryById(id);
        return new ResponseEntity<>(categoryDto, null, HttpStatus.FOUND);
    }

    // get all categories
    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> list = catService.getAllCategories();
        return new ResponseEntity<>(list, null, HttpStatus.FOUND);
    }

    // update given id
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,
            @PathVariable Long id) {
        CategoryDto updatedCategory = catService.updateCategory(categoryDto, id);
        return new ResponseEntity<>(updatedCategory, null, HttpStatus.ACCEPTED);

    }

    // delete element by id
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        catService.deleteCategory(id);
        return new ResponseEntity<>("Deleted successfully", null, HttpStatus.ACCEPTED);
    }

}
