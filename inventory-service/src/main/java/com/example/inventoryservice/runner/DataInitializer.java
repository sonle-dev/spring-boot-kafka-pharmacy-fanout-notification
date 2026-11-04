package com.example.inventoryservice.runner;

import com.example.inventoryservice.entity.Medicine;
import com.example.inventoryservice.repository.MedicineRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final MedicineRepository medicineRepository;

    public DataInitializer(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }

    @Override
    public void run(String... args) {
        if (medicineRepository.count() == 0) {
            Medicine defaultMedicine = new Medicine("Panadol", "Thuốc giảm đau", 15000.0, 100);
            medicineRepository.save(defaultMedicine);
            System.out.println(">>> Đã tạo thuốc Panadol mẫu (ID = 1, Tồn kho = 100)");
        }
    }
}