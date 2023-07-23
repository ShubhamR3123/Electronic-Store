package com.electronic.store.services;

import com.electronic.store.dtos.CategoryDto;
import com.electronic.store.dtos.PageableResponse;
import com.electronic.store.dtos.ProductDto;
import com.electronic.store.dtos.UserDto;
import com.electronic.store.entites.Category;
import com.electronic.store.repositories.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class CategoryServiceTest {

    @Autowired
    private ModelMapper mapper;

    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    Category category;

    @BeforeEach
    public void init() {

        category = Category.builder().categoryId("101").title("Category!").description("This is first Category").coverImage("abc.png")

                .build();
    }

    @Test
    public void createCategory() {

        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(category);
        CategoryDto category1 = categoryService.createCategory(mapper.map(category, CategoryDto.class));
        System.out.println(category1);

        Assertions.assertNotNull(category1);
        Assertions.assertEquals("Category!", category1.getTitle());
    }

    @Test
    public void updateCategoryTest() {

        CategoryDto categoryDto = CategoryDto.builder().title("Category!").description("This is first Category").coverImage("abc.png")

                .build();
        String categoryId = "abcId";
        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(category);
        CategoryDto updateCategory = categoryService.updateCategory(categoryDto, categoryId);
        System.out.println(updateCategory.getTitle());

    }

    @Test
    public void getAllCategoryTest() {
        Category category1 = Category.builder().categoryId("101").title("Category!").description("This is first Category").coverImage("abc.png")

                .build();
        Category category2 = Category.builder().categoryId("101").title("Category!").description("This is first Category").coverImage("abc.png")

                .build();

        List<Category> list = Arrays.asList(category, category1, category2);

        Page<Category> page = new PageImpl<>(list);
        Mockito.when(categoryRepository.findAll((Pageable) Mockito.any())).thenReturn(page);
        PageableResponse<CategoryDto> allCategory = categoryService.getAllCategory(1, 2, "asc", "title");
        Assertions.assertEquals(3, allCategory.getContent().size());
    }

    @Test
    public void deleteCategoryTest() {

        String categoryId = "abc123";
        Mockito.when(categoryRepository.findById("abc123")).thenReturn(Optional.of(category));
        categoryService.deleteCategory(categoryId);
        Mockito.verify(categoryRepository, Mockito.times(1)).delete(category);
    }

    @Test
    public void getCategoryByIdTest() {

        String categoryId = "abc123";

        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        CategoryDto categoryDto = categoryService.getCategoryById(categoryId);
        Assertions.assertNotNull(categoryDto);
        Assertions.assertEquals(category.getTitle(), categoryDto.getTitle());
    }

    @Test
    public void searchCategoryTest() {
        Category category1 = Category.builder().categoryId("101").title("Category!").description("This is first Category").coverImage("abc.png")
                .build();
        Category category2 = Category.builder().categoryId("102").title("Categoryxyz").description("This is Second Category").coverImage("abc.png")
                .build();

        String title="Category!";
        List<Category> list = Arrays.asList(category, category1, category2);


        Mockito.when(categoryRepository.findByTitleContaining(title)).thenReturn(list);
        List<CategoryDto> response = categoryService.searchCategory(title);
        Assertions.assertEquals(3, response.size());
    }
}
