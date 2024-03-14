package com.TrollMarket.repository;

import com.TrollMarket.dto.shipper.ShipperDataDTO;
import com.TrollMarket.dto.utility.DropdownDTO;
import com.TrollMarket.entity.Shipper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;

@Repository
public interface ShipperRepository extends JpaRepository<Shipper, Integer> {
    @Query("""
            SELECT new com.TrollMarket.dto.shipper.ShipperDataDTO(shi.shipperId,
                shi.name,
                shi.price,
                shi.service)
            FROM Shipper AS shi
            """)
    public Page<ShipperDataDTO> findAllData(Pageable pageable);

    @Query("""
            SELECT new com.TrollMarket.dto.utility.DropdownDTO(shi.shipperId,
                shi.name)
            FROM Shipper AS shi
            WHERE shi.service = true
            """)
    public LinkedList<DropdownDTO> shipperDropdown();
}
