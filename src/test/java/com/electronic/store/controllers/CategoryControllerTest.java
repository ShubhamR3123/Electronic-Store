package com.electronic.store.controllers;

import com.electronic.store.dtos.CategoryDto;
import com.electronic.store.dtos.PageableResponse;
import com.electronic.store.dtos.UserDto;
import com.electronic.store.entites.Category;
import com.electronic.store.services.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private MockMvc mockMvc;

    private Category category;

    private CategoryDto categoryDto;

    @BeforeEach
    public void init() {

        category = Category.builder().categoryId("101").title("Category!").description("This is first Category").coverImage("abc.png")

                .build();
    }

    @Test
    public void createCategoryTest() throws Exception {
        //users+Post+user data as a json
        //data as json+created

        CategoryDto dto = modelMapper.map(category, CategoryDto.class);
        Mockito.when(categoryService.createCategory(Mockito.any())).thenReturn(dto);

        //actual request for url

        this.mockMvc.perform(MockMvcRequestBuilders.post("/categories/").contentType(MediaType.APPLICATION_JSON).content(ConvertObjectToJsonString(category)).accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$.title").exists());
    }

    @Test
    public void updateCategoryTest() throws Exception {

        String categoryId = "1234";

        CategoryDto dto = this.modelMapper.map(category, CategoryDto.class);
        Mockito.when(categoryService.updateCategory(Mockito.any(), Mockito.anyString())).thenReturn(dto);
        this.mockMvc.perform(MockMvcRequestBuilders.put("/categories/" + categoryId).contentType(MediaType.APPLICATION_JSON).content(ConvertObjectToJsonString(category)).accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.title").exists());
    }


    @Test
    public void getAllCategoryTest() throws Exception {

        CategoryDto.CategoryDtoBuilder categoryDto = CategoryDto.builder().categoryId("101").title("Category").description("All Category Test").coverImage("abc.png");


        PageableResponse<CategoryDto> pageableResponse = new PageableResponse<>();
        pageableResponse.setContent(Arrays.asList());
        pageableResponse.setLastPage(false);
        pageableResponse.setPageSize(10);
        pageableResponse.setPageNumber(100);
        pageableResponse.setTotalElements(1000);
        Mockito.when(categoryService.getAllCategory(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(pageableResponse);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/categories/category").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());

    }

    @Test
    public void getCategoryById() throws Exception {

        String categoryId = "1234";

        CategoryDto dto = this.modelMapper.map(category, CategoryDto.class);
        Mockito.when(categoryService.getCategoryById(Mockito.any())).thenReturn(dto);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/categories/" + categoryId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());

    }

    @Test
    public void deleteCategoryTest() throws Exception {

        String categoryId = "12345";

        CategoryDto dto = this.modelMapper.map(category, CategoryDto.class);
        Mockito.doNothing().when(categoryService).deleteCategory(categoryId);
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/categories/" + categoryId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void searchCategoryTest() throws Exception {
        CategoryDto dto = CategoryDto.builder().categoryId("101").title("Category").description("All Category Test").coverImage("abc.png").build();
        CategoryDto dto1 = CategoryDto.builder().categoryId("101").title("Category123").description("All Category Test").coverImage("abc.png").build();

        String title = "Category";
        PageableResponse<CategoryDto> pageableResponse = new PageableResponse<>();
        pageableResponse.setContent(Arrays.asList(dto, dto1));
        pageableResponse.setLastPage(false);
        pageableResponse.setPageSize(10);
        pageableResponse.setPageNumber(100);
        pageableResponse.setTotalElements(1000);


        Mockito.when(categoryService.searchCategory(Mockito.anyString())).thenReturn(Arrays.asList(dto, dto1));


        this.mockMvc.perform(MockMvcRequestBuilders.get("/categories/search/" + title).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
    }


    private String ConvertObjectToJsonString(Object category) {
        try {

            return new ObjectMapper().writeValueAsString(category);

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
