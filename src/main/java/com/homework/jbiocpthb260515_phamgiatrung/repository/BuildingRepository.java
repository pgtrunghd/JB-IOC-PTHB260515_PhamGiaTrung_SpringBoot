package com.homework.jbiocpthb260515_phamgiatrung.repository;

import com.homework.jbiocpthb260515_phamgiatrung.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BuildingRepository extends JpaRepository<Building, Long>, JpaSpecificationExecutor<Building> {
    boolean existsByBuildingNameIgnoreCase(String buildingName);

    boolean existsByBuildingNameIgnoreCaseAndIdNot(String buildingName, Long id);
}
