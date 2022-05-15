package id.saputra.adi.customerdataservice.services;

import id.saputra.adi.customerdataservice.domain.dto.CustomerDto;

public interface CrudBase {

    Object create(CustomerDto customerDto);
    Object update(CustomerDto customerDto);
    Object delete(CustomerDto customerDto);
    Object showAll();
    Object showDetail(String username);

}
