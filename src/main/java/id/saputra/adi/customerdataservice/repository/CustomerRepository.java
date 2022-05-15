package id.saputra.adi.customerdataservice.repository;

import id.saputra.adi.customerdataservice.domain.dao.CustomerDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerDao, Integer> {
    Optional<CustomerDao> findByUsernameAndIsDeleted(String userName, boolean isDeleted);
}
