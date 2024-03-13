package com.nr.msscbeerservice.web.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BeerDto {
    @Null(message = "Id should not be added.")
    private UUID id;

    @Null(message = "Version should not be added.")
    private Integer version;
    
    @Null(message = "Create Date should not be added.")
    private OffsetDateTime createDate;

    @Null(message = "Last Modified Date should not be added.")
    private OffsetDateTime lastModifiedDate;

    @NotBlank(message = "Beer name is blank.")
    private String beerName;

    @NotNull(message = "Beer Style cannot be null.")
    private BeerStyle beerStyle;

    @Positive(message = "UPC should be positive.")
    @NotNull(message = "UPC cannot be null.")
    private Long upc;

    @Positive(message = "Price should be positive.")
    @NotNull(message = "Price cannot be null.")
    private BigDecimal price;

    private Integer quantityOnHand;
}
