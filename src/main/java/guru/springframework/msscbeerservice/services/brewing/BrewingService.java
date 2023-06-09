package guru.springframework.msscbeerservice.services.brewing;

import guru.springframework.msscbeerservice.confing.JmsConfig;
import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.events.BrewBeerEvent;
import guru.springframework.msscbeerservice.repositories.BeerRepository;
import guru.springframework.msscbeerservice.services.inventory.BeerInventoryService;
import guru.springframework.msscbeerservice.web.mappers.BeerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrewingService {

  private final BeerRepository beerRepository;
  private final BeerInventoryService beerInventoryService;
  private final JmsTemplate jmsTemplate;
  private final BeerMapper beerMapper;

  @Scheduled(fixedRate = 5000)
  public void checkForLowInventory() {
    final List<Beer> beers = beerRepository.findAll();

    beers.forEach(beer -> {
      Integer quantityOnHand = beerInventoryService.getOnHandInventory(beer.getId());

      log.debug("Minimum on hand is: {}", beer.getMinOnHand());
      log.debug("Inventory is: {}", quantityOnHand);

      if (beersOnHandIsTooLow(beer, quantityOnHand)) {
        sendBrewBeerEvent(beer);
      }
    });
  }

  private void sendBrewBeerEvent(final Beer beer) {
    jmsTemplate.convertAndSend(JmsConfig.BREWING_REQUEST_QUEUE, new BrewBeerEvent(beerMapper.beerToBeerDto(beer)));
  }

  private static boolean beersOnHandIsTooLow(final Beer beer, final Integer quantityOnHand) {
    return quantityOnHand < beer.getMinOnHand();
  }

}
