package guru.springframework.msscbeerservice.services.inventory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Component
@ConfigurationProperties(prefix = "sfg.brewery", ignoreUnknownFields = false)
public class BeerInventoryServiceRestTemplateImpl implements BeerInventoryService {

  private final String INVENTORY_PATH = "/api/v1/beer/{beerId}/inventory";
  private final RestTemplate restTemplate;

  public BeerInventoryServiceRestTemplateImpl(RestTemplateBuilder restTemplateBuilder) {
    this.restTemplate = restTemplateBuilder.build();
  }

  private String beerInventoryServiceHost;

  @Override
  public Integer getOnHandInventory(final UUID beerId) {
    log.debug("Calling Inventory Service");

    ResponseEntity<List<BeerInventoryDto>> responseEntity = restTemplate
        .exchange(beerInventoryServiceHost + INVENTORY_PATH, HttpMethod.GET, null,
            new ParameterizedTypeReference<List<BeerInventoryDto>>() {}, (Object) beerId);

    return Objects.requireNonNull(responseEntity.getBody())
        .stream()
        .mapToInt(BeerInventoryDto::getQuantityOnHand)
        .sum();
  }

  public void setBeerInventoryServiceHost(final String beerInventoryServiceHost) {
    this.beerInventoryServiceHost = beerInventoryServiceHost;
  }
}
