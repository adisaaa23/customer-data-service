package id.saputra.adi.customerdataservice.services;

import id.saputra.adi.customerdataservice.domain.dao.CustomerDao;
import id.saputra.adi.customerdataservice.domain.dto.CustomerDto;
import id.saputra.adi.customerdataservice.exception.ApplicationException;
import id.saputra.adi.customerdataservice.repository.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @Before
    public void setup(){
        List<CustomerDao> customerDaos = List.of(
                CustomerDao.builder()
                .fullName("Adi Saputra")
                .username("adisapu")
                .riskProfileCode("3")
                .build(),
                CustomerDao.builder()
                        .fullName("Adi Saputra")
                        .username("adisapuExist")
                        .riskProfileCode("3")
                        .build()
        );
        customerRepository.saveAll(customerDaos);
    }

    @Test
    public void createExpectSuccess(){
        CustomerDao customerDao = customerService.create(
                CustomerDto.builder()
                        .fullName("Adi Sp2")
                        .username("ads")
                        .riskProfileCode("2")
                        .build()
        );
        assertEquals("ads", customerDao.getUsername());
    }

    @Test(expected = ApplicationException.class)
    public void createExpectRqIncomplete(){
        customerService.create(
                null
        );
    }

    @Test(expected = ApplicationException.class)
    public void createExpectUserExists(){
        customerRepository.save( CustomerDao.builder()
                .fullName("Adi Saputra")
                .username("adisapuExist2")
                .riskProfileCode("3")
                .build());
         customerService.create(
                CustomerDto.builder()
                        .fullName("Adi Sp2")
                        .username("adisapuExist2")
                        .riskProfileCode("2")
                        .build()
        );
    }

    @Test
    public void deleteExpectSuccess(){
        CustomerDao customerDao = customerService.delete(
                CustomerDto.builder()
                        .username("adisapu")
                        .isDeleted(true)
                        .build()
        );
        assertTrue(customerDao.isDeleted());
    }

    @Test(expected = ApplicationException.class)
    public void deleteExpectUserNotFound(){
         customerService.delete(
                CustomerDto.builder()
                        .fullName("Adi Sp2")
                        .username("adisapuxxxx")
                        .riskProfileCode("2")
                        .build()
        );
    }

    @Test(expected = ApplicationException.class)
    public void deleteExpectRqIncomplete(){
        customerService.delete(
                CustomerDto.builder().build()
        );
    }

    @Test
    public void updateExpectSuccess(){
        CustomerDao customerDao = customerService.update(
                CustomerDto.builder()
                        .fullName("Adi Sp2")
                        .username("adisapu")
                        .riskProfileCode("2")
                        .build()
        );
        assertEquals("Adi Sp2", customerDao.getFullName());
    }

    @Test(expected = ApplicationException.class)
    public void updateExpectUserNotFound(){
        customerService.update(
                CustomerDto.builder()
                        .fullName("Adi Sp2")
                        .username("adisapuxxxx")
                        .riskProfileCode("2")
                        .build()
        );
    }

    @Test(expected = ApplicationException.class)
    public void updateExpectRqIncomplete(){
        customerService.update(
                CustomerDto.builder().build()
        );
    }

    @Test
    public void showAllExpectSuccess(){
        List<CustomerDao> customerDaos = (List<CustomerDao>) customerService.showAll();
        assertNotNull(customerDaos);
    }

    @Test
    public void showDetailExpectSuccess(){
        customerRepository.save( CustomerDao.builder()
                .fullName("Adi Saputra")
                .username("adisapuDetail")
                .riskProfileCode("3")
                .build());
        CustomerDao customerDao = customerService.showDetail("adisapuDetail");
        assertEquals("adisapuDetail", customerDao.getUsername());
    }

    @Test(expected = ApplicationException.class)
    public void showDetailExpectRqIncomplete(){
        customerService.showDetail(
                null
        );
    }

    @Test(expected = ApplicationException.class)
    public void showDetailExpectUserNotFound(){
        customerService.showDetail(
                "xxxxx"
        );
    }
}
