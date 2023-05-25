package io.mkolodziejczyk92.data.service;

import io.mkolodziejczyk92.data.entity.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long>, JpaSpecificationExecutor<Manufacturer> {
}
