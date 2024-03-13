package com.nr.msscbeerservice.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nr.msscbeerservice.web.model.BeerDto;
import com.nr.msscbeerservice.web.model.BeerStyle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@WebMvcTest(BeerController.class)
@ComponentScan(basePackages = "com.nr.msscbeerservice.web.mappers")
@ExtendWith(RestDocumentationExtension.class)
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getBeer() throws Exception {
        mockMvc.perform(get("/api/v1/beer/{beerId}", UUID.randomUUID().toString())
                        .param("iscold", "yes")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("v1/beer",
                                pathParameters(parameterWithName("beerId").description("UUID of desired beer to get.")),
                                queryParameters(parameterWithName("iscold").description("Is Beer Cold Query param")),
                                responseFields(
                                        fieldWithPath("id").description("Id of Beer"),
                                        fieldWithPath("version").description("Version number"),
                                        fieldWithPath("createdDate").description("Date Created"),
                                        fieldWithPath("lastModifiedDate").description("Date Updated"),
                                        fieldWithPath("beerName").description("Beer Name"),
                                        fieldWithPath("beerStyle").description("Beer Style"),
                                        fieldWithPath("upc").description("UPC of Beer"),
                                        fieldWithPath("price").description("Price"),
                                        fieldWithPath("quantityOnHand").description("Quantity On hand")
                                )
                        )

                );
    }

    @Test
    void saveNewBeer() throws Exception {
        BeerDto beerDto = getValidBeerDto();
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);
        ConstrainedFields fields = new ConstrainedFields(BeerDto.class);
        mockMvc.perform(post("/api/v1/beer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(beerDtoJson)
                ).andExpect(status().isCreated())
                .andDo(document("v1/beer",
                                requestFields(
                                        fields.withPath("id").ignored(),
                                        fields.withPath("version").ignored(),
                                        fields.withPath("createdDate").ignored(),
                                        fields.withPath("lastModifiedDate").ignored(),
                                        fields.withPath("beerName").description("Name of the beer"),
                                        fields.withPath("beerStyle").description("Style of Beer"),
                                        fields.withPath("upc").description("Beer UPC").attributes(),
                                        fields.withPath("price").description("Beer Price"),
                                        fields.withPath("quantityOnHand").ignored()
                                )
                        )
                );
    }

    @Test
    void updateBeerId() throws Exception {
        BeerDto beerDto = getValidBeerDto();
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);
        ConstrainedFields fields = new ConstrainedFields(BeerDto.class);
        mockMvc.perform(put("/api/v1/beer/{beerId}", UUID.randomUUID().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(beerDtoJson)
                ).andExpect(status().isNoContent())
                .andDo(
                        document("v1/beer",
                                pathParameters(
                                        parameterWithName("beerId").description("Id of the editing beer.")
                                ), requestFields(
                                        fields.withPath("id").ignored(),
                                        fields.withPath("version").ignored(),
                                        fields.withPath("createdDate").ignored(),
                                        fields.withPath("lastModifiedDate").ignored(),
                                        fields.withPath("beerName").description("Name of the beer"),
                                        fields.withPath("beerStyle").description("Style of Beer"),
                                        fields.withPath("upc").description("Beer UPC").attributes(),
                                        fields.withPath("price").description("Beer Price"),
                                        fields.withPath("quantityOnHand").ignored()
                                )
                        )
                );
    }

    @Test
    void deleteHandle() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/{beerId}", UUID.randomUUID().toString())).andExpect(status().isNoContent())
                .andDo(document("v1/beer",
                        pathParameters(
                                parameterWithName("beerId").description("Id of deleting beer.")
                        )
                ));
    }

    private static class ConstrainedFields {

        private final ConstraintDescriptions constraintDescriptions;

        ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path) {
            return fieldWithPath(path).attributes(key("constraints").value(StringUtils
                    .collectionToDelimitedString(this.constraintDescriptions
                            .descriptionsForProperty(path), ". ")));
        }
    }

    BeerDto getValidBeerDto() {
        return BeerDto.builder().beerName("Galaxy Cat")
                .beerStyle(BeerStyle.PALE_ALE)
                .quantityOnHand(200)
                .upc(337010567001L)
                .price(new BigDecimal("11.95")).build();
    }
}