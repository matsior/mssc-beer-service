package guru.springframework.msscbeerservice.services.inventory;

import guru.springframework.msscbeerservice.bootstrap.BeerLoader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BeerInventoryServiceRestTemplateImplTest {

  @Autowired
  BeerInventoryService beerInventoryService;

  @Test
  void getOnHandInventory() {
    final Integer qoh = beerInventoryService.getOnHandInventory(UUID.fromString(BeerLoader.BEER_2_UUID));
    System.out.println(qoh);
  }
}