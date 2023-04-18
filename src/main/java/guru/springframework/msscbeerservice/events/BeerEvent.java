package guru.springframework.msscbeerservice.events;

import guru.springframework.msscbeerservice.web.model.BeerDto;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class BeerEvent implements Serializable {

  static final long serialVersionUID = 3617029281394426938L;
  private final BeerDto beerDto;
}
