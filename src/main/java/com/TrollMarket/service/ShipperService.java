package com.TrollMarket.service;

import com.TrollMarket.dto.shipper.ShipperDataDTO;
import com.TrollMarket.dto.shipper.ShipperUpsertDTO;
import com.TrollMarket.entity.Shipper;
import com.TrollMarket.repository.CartRepository;
import com.TrollMarket.repository.OrderRepository;
import com.TrollMarket.repository.ShipperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ShipperService {
    @Autowired
    private ShipperRepository shipperRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;

    public Page<ShipperDataDTO> getAllData(Integer page) {
        var pageable = PageRequest.of(page - 1, 10);
        var shipperDataDTO = shipperRepository.findAllData(pageable);

        for (ShipperDataDTO shipper : shipperDataDTO) {
            shipper.setCartDependencies(cartRepository.countDependenciesToShipper(shipper.getShipperId()));
            shipper.setOrderDependencies(orderRepository.countDependenciesToShipper(shipper.getShipperId()));
        }

        return shipperDataDTO;
    }

    public ShipperUpsertDTO getOneData(Integer shipperId) {
        var shipper = shipperRepository.findById(shipperId).get();
        var shipperUpsertDTO = new ShipperUpsertDTO();
        shipperUpsertDTO.setShipperId(shipper.getShipperId());
        shipperUpsertDTO.setName(shipper.getName());
        shipperUpsertDTO.setPrice(shipper.getPrice());
        shipperUpsertDTO.setService(shipper.getService());
        return shipperUpsertDTO;
    }

    public void upsert(ShipperUpsertDTO shipperUpsertDTO) {
        var shipper = new Shipper();
        shipper.setShipperId(shipperUpsertDTO.getShipperId());
        shipper.setName(shipperUpsertDTO.getName());
        shipper.setPrice(shipperUpsertDTO.getPrice());
        shipper.setService(shipperUpsertDTO.getService());
        shipperRepository.save(shipper);
    }

    public void delete(Integer shipperId) {
        shipperRepository.deleteById(shipperId);
    }

    public void stopService(Integer shipperId) {
        var shipper = shipperRepository.findById(shipperId).get();
        shipper.setService(false);
        shipperRepository.save(shipper);
    }
}
