package com.nr.msscbeerservice.web.mappers;

import com.nr.msscbeerservice.domain.Beer;
import com.nr.msscbeerservice.web.model.BeerDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface BeerMapper {
    BeerDto beerToBeerDto(Beer beer);

    Beer beerDtoToBeer(BeerDto beerDto);
}
