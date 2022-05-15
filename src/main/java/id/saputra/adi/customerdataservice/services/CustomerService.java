package id.saputra.adi.customerdataservice.services;

import id.saputra.adi.customerdataservice.domain.dao.CustomerDao;
import id.saputra.adi.customerdataservice.domain.dto.CustomerDto;
import id.saputra.adi.customerdataservice.exception.ApplicationException;
import id.saputra.adi.customerdataservice.repository.CustomerRepository;
import id.saputra.adi.customerdataservice.util.TransformUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class CustomerService implements CrudBase {

    private static final String CACHE_NAME = "customers";
    private static final String MESSAGE_DATA_NOT_FOUND = "Data not found";
    private static final String MESSAGE_REQ_INCOMPLETE = "Request incomplete";
    

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    @Transactional
    @CachePut(value = CACHE_NAME, keyGenerator = "customerKeyGenerator")
    public CustomerDao create(CustomerDto customerDtoRq) {
        log.info("Starting to create data customer ...");
        if (Objects.isNull(customerDtoRq)){
            throw new ApplicationException(MESSAGE_REQ_INCOMPLETE, HttpStatus.BAD_REQUEST);
        }

        Optional<CustomerDao> optionalCustomerDao =
                customerRepository.findByUsernameAndIsDeleted(customerDtoRq.getUsername(), false);
        if (optionalCustomerDao.isPresent()){
            throw new ApplicationException("Username already exists", HttpStatus.FOUND);
        }

        CustomerDao customerDao = (CustomerDao) TransformUtil.transform(customerDtoRq, new CustomerDao());
        customerRepository.saveAndFlush(customerDao);
        log.info("Create data customer successfully ...");
        return customerDao;
    }

    @Override
    @Transactional
    @CachePut(value = CACHE_NAME, keyGenerator = "customerKeyGenerator")
    public CustomerDao update(CustomerDto customerDtoRq){
        log.info("Starting update data customer ....");
        if (Objects.isNull(customerDtoRq) || Objects.isNull(customerDtoRq.getUsername())){
            throw new ApplicationException(MESSAGE_REQ_INCOMPLETE, HttpStatus.BAD_REQUEST);
        }
        Optional<CustomerDao> optionalCustomerDao =
                customerRepository.findByUsernameAndIsDeleted(customerDtoRq.getUsername(), false);
        if (optionalCustomerDao.isPresent()){
            CustomerDao customerDao = (CustomerDao) TransformUtil.transform(customerDtoRq, optionalCustomerDao.get());
            customerRepository.saveAndFlush(customerDao);
            log.info("Update data customer successfully ...");
            return customerDao;
        }
        throw new ApplicationException(MESSAGE_DATA_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    @Override
    @Transactional
    @CachePut(value = CACHE_NAME, keyGenerator = "customerKeyGenerator")
    public CustomerDao delete(CustomerDto customerDtoRq){
        log.info("Starting soft delete data customer ...");
        if (Objects.isNull(customerDtoRq) || Objects.isNull(customerDtoRq.getUsername())){
            throw new ApplicationException(MESSAGE_REQ_INCOMPLETE, HttpStatus.BAD_REQUEST);
        }
        Optional<CustomerDao> optionalCustomerDao =
                customerRepository.findByUsernameAndIsDeleted(customerDtoRq.getUsername(), false);
        if (optionalCustomerDao.isPresent()){
            CustomerDao customerDao = optionalCustomerDao.get();
            customerDao.setDeleted(true);
            customerRepository.saveAndFlush(customerDao);
            log.info("Soft delete data customer successfully ...");
            return customerDao;
        }
        throw new ApplicationException(MESSAGE_DATA_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    @Override
    @Cacheable(value = CACHE_NAME, keyGenerator = "customerKeyGenerator")
    public Object showAll() {
        log.info("Starting get all data customer ...");
        List<CustomerDao> customerDaos = customerRepository.findAll();
        log.info("Starting get all data customer successfully ...");
        return TransformUtil.transform(customerDaos, new CustomerDto());
    }

    @Override
    @Cacheable(value = CACHE_NAME, keyGenerator = "customerKeyGenerator")
    public CustomerDao showDetail(String username) {
        log.info("Starting get detail data customer ...");
        if (Objects.isNull(username)){
            throw new ApplicationException(MESSAGE_REQ_INCOMPLETE, HttpStatus.BAD_REQUEST);
        }
        Optional<CustomerDao> optionalCustomerDao =
                customerRepository.findByUsernameAndIsDeleted(username, false);
        if (optionalCustomerDao.isPresent()){
            CustomerDao customerDao = optionalCustomerDao.get();
            log.info("get data detail customer successfully ...");
            return customerDao;
        }
        throw new ApplicationException(MESSAGE_DATA_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

}
