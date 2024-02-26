package services;

import com.theboys.data.entities.Customer;
import com.theboys.data.repos.CustomerRepo;
import com.theboys.security.PersistentUserManager;
import com.theboys.security.User;
import com.theboys.security.WebSecurityConfig;
import com.theboys.services.CustomerService;
import com.theboys.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

@SpringBootTest(
        classes = {
                CustomerService.class,
                PersistentUserManager.class,
                UserService.class
        })
@EnableAutoConfiguration
@EnableJpaRepositories(basePackageClasses = CustomerRepo.class)
@EntityScan(basePackageClasses = {
        Customer.class,
        User.class
})
@Import(WebSecurityConfig.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class CustomerServiceTest {
    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private CustomerService customerService;

    @Test
    public void testGetCustomer() {
        Optional<Customer> optionalCustomer = customerService.getCustomer(28);
        Assertions.assertTrue(optionalCustomer.isEmpty());
    }

}
