package com.example.demo.subCategory;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/subcategory")
public class SubCategoryController {

    private final SubcategoryService subcategoryService;

    public SubCategoryController(SubcategoryService subcategoryService) {
        this.subcategoryService = subcategoryService;
    }

    @GetMapping
    public SubcategoryListResponse list(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        return SubcategoryListResponse.success(subcategoryService.findPage(name, pageNum, pageSize));
    }

    @PostMapping("/save")
    public int save(@RequestBody SubCategory subCategory) {
        return subcategoryService.save(subCategory);
    }

    @PostMapping("/update")
    public int update(@RequestBody SubCategory subCategory) {
        return subcategoryService.update(subCategory);
    }

    @DeleteMapping("/{id}")
    public int delete(@PathVariable Long id) {
        return subcategoryService.delete(id);
    }
}
