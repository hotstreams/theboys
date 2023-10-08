package com.theboys.services;

import com.theboys.data.entities.Medicine;
import com.theboys.data.repos.MedicineRepo;
import com.theboys.to.MedicineTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicineService {

    private final MedicineRepo medicineRepo;

    @Autowired
    public MedicineService(MedicineRepo medicineRepo) {
        this.medicineRepo = medicineRepo;
    }


    public List<MedicineTO> getMedicines() {
        return medicineRepo.findAll().stream()
                .map(this::createMedicineTO)
                .collect(Collectors.toList());
    }

    private MedicineTO createMedicineTO(Medicine medicine) {
        return new MedicineTO(medicine.getId(), medicine.getName());
    }
}
