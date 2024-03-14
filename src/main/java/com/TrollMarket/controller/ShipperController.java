package com.TrollMarket.controller;

import com.TrollMarket.dto.shipper.ShipperUpsertDTO;
import com.TrollMarket.service.ShipperService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/shipper")
public class ShipperController {
    @Autowired
    private ShipperService shipperService;

    @GetMapping(path = "/index")
    public String index(@RequestParam(defaultValue = "1") Integer page,
                        Model model) {
        var shipperDataDTO = shipperService.getAllData(page);
        var totalPage = shipperDataDTO.getTotalPages();
        model.addAttribute("shipperDataDTO", shipperDataDTO);
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);
        return "/shipper/shipper-index";
    }

    @GetMapping(path = "/delete")
    public String delete(@RequestParam Integer shipperId) {
        shipperService.delete(shipperId);
        return "redirect:/shipper/index";
    }

    @GetMapping(path = "/stopService")
    public String stopService(@RequestParam Integer shipperId) {
        shipperService.stopService(shipperId);
        return "redirect:/shipper/index";
    }

    @GetMapping(path = "/{shipperId}")
    public ResponseEntity<Object> getOne(@PathVariable Integer shipperId) {
        try {
            var shipperUpsertDTO = shipperService.getOneData(shipperId);
            return ResponseEntity.status(200).body(shipperUpsertDTO);
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(exception.getMessage());
        }
    }

    @PostMapping(path = "/upsert")
    public ResponseEntity<Object> post(@Valid @RequestBody ShipperUpsertDTO shipperUpsertDTO,
                                       BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return ResponseEntity.status(400).body(bindingResult.getAllErrors());
            }

            shipperService.upsert(shipperUpsertDTO);
            return ResponseEntity.status(200).body(shipperUpsertDTO);
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(exception.getMessage());
        }
    }

    @PutMapping(path = "/upsert")
    public ResponseEntity<Object> put(@Valid @RequestBody ShipperUpsertDTO shipperUpsertDTO,
                                       BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return ResponseEntity.status(400).body(bindingResult.getAllErrors());
            }

            shipperService.upsert(shipperUpsertDTO);
            return ResponseEntity.status(200).body(shipperUpsertDTO);
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(exception.getMessage());
        }
    }
}
