package com.theboys.services;

import com.theboys.data.entities.Medicine;
import com.theboys.data.repos.MedicineRepo;
import com.theboys.security.PersistentUserManager;
import com.theboys.security.User;
import com.theboys.security.WebSecurityConfig;
import com.theboys.to.MedicineTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

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
public class MedicineServiceTest {

    @Autowired
    private MedicineRepo medicineRepo;

    @Autowired
    private MedicineService medicineService;

    @Test
    public void testGetMedicines() {
        Medicine medicine = new Medicine();
        medicine.setName("Drug");
        medicineRepo.save(medicine);

        List<MedicineTO> medicines = medicineService.getMedicines();
        Assertions.assertEquals(1, medicines.size());

        MedicineTO medicineTO = medicines.get(0);
        Assertions.assertEquals(medicine.getName(), medicineTO.getName());
    }
}
