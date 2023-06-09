package guru.springframework.msscbeerservice.web.mappers;

import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.services.inventory.BeerInventoryService;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import org.springframework.beans.factory.annotation.Autowired;

public class BeerMapperDecorator implements BeerMapper {

  private BeerInventoryService beerInventoryService;
  private BeerMapper mapper;

  @Autowired
  public void setBeerInventoryService(BeerInventoryService beerInventoryService) {
    this.beerInventoryService = beerInventoryService;
  }

  @Autowired
  public void setMapper(BeerMapper mapper) {
    this.mapper = mapper;
  }

  @Override
  public BeerDto beerToBeerDto(final Beer beer) {
    return mapper.beerToBeerDto(beer);
  }

  @Override
  public BeerDto beerToBeerDtoWithInventory(final Beer beer) {
    final BeerDto dto = mapper.beerToBeerDto(beer);
    dto.setQuantityOnHand(beerInventoryService.getOnHandInventory(beer.getId()));
    return dto;
  }

  @Override
  public Beer beerDtoToBeer(final BeerDto dto) {
    return mapper.beerDtoToBeer(dto);
  }
}
