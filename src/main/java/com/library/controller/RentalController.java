package com.library.controller;

import com.library.domain.BookDto;
import com.library.domain.LoggedUserDto;
import com.library.domain.RentalDto;
import com.library.domain.UserDto;
import com.library.exception.RentalNotFoundException;
import com.library.mapper.RentalMapper;
import com.library.mapper.UserMapper;
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

    @Autowired
    UserMapper userMapper;

    @GetMapping("getRentals")
    public List<RentalDto> getAllRentals() {
        return rentalMapper.mapToRentalDtoList(rentalService.getAllRentals());
    }

    @GetMapping("getRental")
    public RentalDto getRental(@RequestParam Long rentalId) throws RentalNotFoundException {
        return rentalMapper.mapToRentalDto(rentalService.getRental(rentalId).orElseThrow(RentalNotFoundException::new));
    }

    @DeleteMapping("deleteRental")
    public void deleteRental(@RequestParam Long rentalId) {
        rentalService.deleteRental(rentalId);
    }

    @PutMapping("updateRental")
    public RentalDto updateRental(@RequestBody RentalDto rentalDto) {
        return rentalMapper.mapToRentalDto(rentalService.saveRental(rentalMapper.mapToRental(rentalDto)));
    }

    @PostMapping("createRental")
    public void createRental(@Valid @RequestBody RentalDto rentalDto) {
        rentalService.saveRental(rentalMapper.mapToRental(rentalDto));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "checkoutBook")
    public List<RentalDto> checkoutBook(@RequestParam Long bookId, @RequestBody LoggedUserDto loggedUserDto) {

        return rentalMapper.mapToRentalDtoList(rentalService.checkoutBook(bookId, loggedUserDto));
    }

    @GetMapping("getRentalsForUser")
    public List<RentalDto> getAllRentals(@RequestParam LoggedUserDto loggedUserDto) {
        return rentalMapper.mapToRentalDtoList(rentalService.getRentals(loggedUserDto));
    }
}
