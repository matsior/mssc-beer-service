package guru.springframework.msscbeerservice.services.inventory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@Builder
@AllArgsConstructor
public class BeerInventoryDto {

  private UUID beerId;
  private String upc;
  private Integer quantityOnHand;
}
