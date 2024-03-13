package com.nr.msscbeerservice.bootstrap;

import com.nr.msscbeerservice.domain.Beer;
import com.nr.msscbeerservice.repositories.BeerRepository;
import com.nr.msscbeerservice.web.model.BeerStyle;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BeerDataLoader implements CommandLineRunner {

    private final BeerRepository beerRepository;

    public BeerDataLoader(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadBeerObjects();
    }

    private void loadBeerObjects() {
        if ((beerRepository.count()) == 0) {
            beerRepository.save(Beer.builder()
                    .beerName("Mango Bobs")
                    .beerStyle(BeerStyle.IPA)
                    .minOnHand(12)
                    .quantityToBrew(200)
                    .upc(3370101000001L)
                    .price(new BigDecimal("12.95"))
                    .build());

            beerRepository.save(Beer.builder()
                    .beerName("Galaxy Cat")
                    .beerStyle(BeerStyle.PALE_ALE)
                    .quantityToBrew(200)
                    .minOnHand(12)
                    .upc(337010567001L)
                    .price(new BigDecimal("11.95"))
                    .build());

        }
        System.out.println("Loaded Beers :" + beerRepository.count());
    }
}
