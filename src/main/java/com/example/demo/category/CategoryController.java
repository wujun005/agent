package com.example.demo.category;

import java.util.List;

import com.example.demo.product1.Product1StatusResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;
//    private CategoryStatusResponse categoryStatusResponse;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public CategoryListResponse list(@RequestParam(required = false) String name) {
        List<Category> records = categoryService.findAll(name);

        return CategoryListResponse.success(new CategoryPage(
                records.size(),
                10,
                1,
                records
        ));
    }
    @PostMapping("/save")
    public CategoryStatusResponse save(@RequestBody Category category) {
        categoryService.save(category);
        return CategoryStatusResponse.sucess();
    }

    @PostMapping("/update")
    public CategoryStatusResponse update(@RequestBody Category category) {
        categoryService.update(category);
        return CategoryStatusResponse.sucess();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public CategoryStatusResponse delete(@PathVariable Integer id) { categoryService.delete(id);
    return CategoryStatusResponse.sucess();
    }
}
