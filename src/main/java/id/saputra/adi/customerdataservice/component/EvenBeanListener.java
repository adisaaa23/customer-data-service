package id.saputra.adi.customerdataservice.component;

import id.saputra.adi.customerdataservice.domain.dao.CustomerDao;
import id.saputra.adi.customerdataservice.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class EvenBeanListener {

    @Autowired
    private CustomerRepository customerRepository;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        /* Initial Data RiskProfile */
        log.info("Prepare insert initiate customer data");
        List<CustomerDao> customerDaos = new ArrayList<>();
        for (int i = 1; i <=3; i++){
            CustomerDao customerDao = CustomerDao.builder()
                    .username("user" + i)
                    .riskProfileCode(String.valueOf(i))
                    .fullName("User S" + i)
                    .build();
            customerDaos.add(customerDao);
        }
        customerRepository.saveAll(customerDaos);
        log.info("Successfully insert initiate customer data");
    }
}
