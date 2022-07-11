package com.library.controller;

import com.library.domain.RentalDto;
import com.library.mapper.RentalMapper;
import com.library.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/library")
public class RentalController {
    @Autowired
    RentalService rentalService;

    @Autowired
    RentalMapper rentalMapper;

    @GetMapping("getRentals")
    public List<RentalDto> getAllRentals() {
        return rentalMapper.mapToRentalDtoList(rentalService.getAllRentals());
    }

    @GetMapping("getRental")
    public RentalDto getRental(@RequestParam Long rentalId) throws RentalNotFoundException  {
        return rentalMapper.mapToRentalDto(rentalService.getRental(rentalId).orElseThrow(RentalNotFoundException::new));
    }

    @DeleteMapping( "deleteRental")
    public void deleteRental(@RequestParam Long rentalId) {
        rentalService.deleteRental(rentalId);
    }

    @PutMapping("updateRental")
    public RentalDto updateRental(@RequestBody RentalDto rentalDto) {
        return rentalMapper.mapToRentalDto(rentalService.saveRental(rentalMapper.mapToRental(rentalDto)));
    }

    @PostMapping( "createRental")
    public void createRental(@Valid @RequestBody RentalDto rentalDto) {
        rentalService.saveRental(rentalMapper.mapToRental(rentalDto));
    }
}
