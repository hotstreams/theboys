package services;

import com.theboys.data.entities.Medicine;
import com.theboys.data.repos.MedicineRepo;
import com.theboys.security.PersistentUserManager;
import com.theboys.security.User;
import com.theboys.security.WebSecurityConfig;
import com.theboys.services.MedicineService;
import com.theboys.to.MedicineTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest(
        classes = {
                PersistentUserManager.class,
                MedicineService.class
        })
@EnableAutoConfiguration
@EnableJpaRepositories(basePackageClasses = MedicineRepo.class)
@EntityScan(basePackageClasses = {
        Medicine.class,
        User.class
})
@Import(WebSecurityConfig.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class MedicineServiceTest {

    @Autowired
    private MedicineRepo medicineRepo;

    @Autowired
    private MedicineService medicineService;

    @Test
    @Transactional
    public void testGetMedicines() {
        List<MedicineTO> medicines = medicineService.getMedicines();
        Assertions.assertEquals(0, medicines.size());
    }
}
