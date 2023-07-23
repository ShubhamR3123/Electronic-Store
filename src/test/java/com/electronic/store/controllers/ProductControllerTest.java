package com.electronic.store.controllers;

import com.electronic.store.dtos.CategoryDto;
import com.electronic.store.dtos.PageableResponse;
import com.electronic.store.dtos.ProductDto;
import com.electronic.store.dtos.UserDto;
import com.electronic.store.entites.Product;
import com.electronic.store.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ComponentScan("com.electronic.store")
public class ProductControllerTest {

    @MockBean
    private ProductService productService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MockMvc mockMvc;


    private Product product;

    @BeforeEach
    public void init() {
        product = Product.builder().productId("12345").title("Samsung A12").description("Launched in 2022").price(50000).discounted_price(5000).quantity(12).live(true).stock(false).imageName("abc.png").build();
    }

    @Test
    public void createProductTest() throws Exception {


        ProductDto dto = modelMapper.map(product, ProductDto.class);
        Mockito.when(productService.createProduct(Mockito.any())).thenReturn(dto);

            //actual request for url

            this.mockMvc.perform(
                            MockMvcRequestBuilders.post("/products/")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(ConvertObjectToJsonString(product))
                                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.title").exists());
        }
    @Test
    public void updateProductTest() throws Exception {

        String productId="1234";

        ProductDto dto = this.modelMapper.map(product, ProductDto.class);
        Mockito.when(productService.updateProduct(Mockito.any(),Mockito.anyString())).thenReturn(dto);
        this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/products/" +productId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(ConvertObjectToJsonString(product))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists());
    }

    @Test
    public void getAllProductTest() throws Exception {

        Product  product1 = Product.builder().title("Samsung A12").description("Launched in 2022").price(50000).discounted_price(5000).quantity(12).live(true).stock(false).imageName("abc.png").build();
        Product product2= Product.builder().title("Samsung A12").description("Launched in 2022").price(50000).discounted_price(5000).quantity(12).live(true).stock(false).imageName("abc.png").build();


        PageableResponse<ProductDto> pageableResponse=new PageableResponse<>();
        pageableResponse.setContent(Arrays.asList());
        pageableResponse.setLastPage(false);
        pageableResponse.setPageSize(10);
        pageableResponse.setPageNumber(100);
        pageableResponse.setTotalElements(1000);
        Mockito.when(productService.getAllProduct(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(pageableResponse);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/products/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void deleteProductTest() throws Exception {

        String productId = "12345";

        ProductDto dto = this.modelMapper.map(product, ProductDto.class);
        Mockito.doNothing().when(productService).deleteProduct(productId);
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/products/" + productId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void getUserById() throws Exception {

        String productId = "1234";

        ProductDto dto = this.modelMapper.map(product, ProductDto.class);
        Mockito.when(productService.getSingleProduct(Mockito.any())).thenReturn(dto);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/products/" + productId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());

    }
    @Test
    public void getAllLiveTest() throws Exception {

        ProductDto  product1 = ProductDto.builder().title("Samsung A12").description("Launched in 2022").price(50000).discounted_price(5000).quantity(12).live(true).stock(false).imageName("abc.png").build();
        ProductDto product2= ProductDto.builder().title("Samsung A12").description("Launched in 2022").price(50000).discounted_price(5000).quantity(12).live(true).stock(false).imageName("abc.png").build();


        PageableResponse<ProductDto> pageableResponse=new PageableResponse<>();
        pageableResponse.setContent(Arrays.asList());
        pageableResponse.setLastPage(false);
        pageableResponse.setPageSize(10);
        pageableResponse.setPageNumber(100);
        pageableResponse.setTotalElements(1000);
        Mockito.when(productService.getAllLive(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(pageableResponse);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/products/live")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    public void searchProductTest() throws Exception {
String title="Samsung A12";
        ProductDto  product1 = ProductDto.builder().title("Samsung A12").description("Launched in 2022").price(50000).discounted_price(5000).quantity(12).live(true).stock(false).imageName("abc.png").build();
        ProductDto product2= ProductDto.builder().title("Samsung A12").description("Launched in 2022").price(50000).discounted_price(5000).quantity(12).live(true).stock(false).imageName("abc.png").build();
        PageableResponse<ProductDto> pageableResponse = new PageableResponse<>();
        pageableResponse.setLastPage(false);
        pageableResponse.setTotalElements(2000);
        pageableResponse.setPageNumber(50);
        pageableResponse.setContent(Arrays.asList(product1,product2));
        pageableResponse.setTotalPages(200);
        pageableResponse.setPageSize(20);

        Mockito.when(productService.searchByTitle(Mockito.anyString(),Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(pageableResponse);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/products/search/"+title)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk());
    }
    @Test
    public void createProductWithCategoryIdTest() throws Exception {

        String categoryId="ab1234";
        ProductDto dto = modelMapper.map(product, ProductDto.class);
        Mockito.when(productService.createWithCategory(Mockito.any(),Mockito.anyString())).thenReturn(dto);

        //actual request for url

        this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/products/category/product/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(ConvertObjectToJsonString(product))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").exists());
    }
    private String ConvertObjectToJsonString(Product product) {


            try {

                return new ObjectMapper().writeValueAsString(product);

            }catch (Exception ex){
                ex.printStackTrace();
                return null;
            }
        }
    }


