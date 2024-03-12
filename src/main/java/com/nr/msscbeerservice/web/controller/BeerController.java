package com.nr.msscbeerservice.web.controller;

import com.nr.msscbeerservice.web.model.BeerDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/beer")
public class BeerController {
//    private final BeerService beerService;
//
//
//    public BeerController(BeerService beerService) {
//        this.beerService = beerService;
//    }

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDto> getBeer(@PathVariable("beerId") UUID beerId) {
        return new ResponseEntity<>(BeerDto.builder().build()/*beerService.getBeerById(beerId)*/, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpHeaders> saveNewBeer(@RequestBody BeerDto beerDto) {
        BeerDto savedDto = BeerDto.builder().id(UUID.randomUUID()).build(); /*beerService.saveBeer(beerDto)*/

        HttpHeaders headers = new HttpHeaders();
        //ToDo add hostname to url
        headers.add("Location", "/api/v1/beer/" + savedDto.getId().toString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping("/{beerId}")
    public ResponseEntity<HttpStatus> updateBeerId(@RequestBody BeerDto beerDto, @PathVariable("beerId") UUID beerId) {
//        beerService.updateBeer(beerId, beerDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{beerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteHandle(@PathVariable("beerId") UUID beerId) {
//        beerService.deleteById(beerId);
    }
}
