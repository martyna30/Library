package com.library.controller;

import com.library.domain.*;
import com.library.domain.rental.RentalBookDto;
import com.library.exception.MyException2;
import com.library.exception.RentalNotFoundException;
import com.library.mapper.BookMapper;
import com.library.mapper.RentalMapper;
import com.library.mapper.UserMapper;
import com.library.repository.UserRepository;
import com.library.service.BookService;
import com.library.service.RentalService;
import com.library.service.UserService;
import com.library.validationGroup.OrderChecks;
import com.library.validationGroup.OrderChecks3;
import com.mysql.cj.x.protobuf.MysqlxExpr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.InvalidClassException;
import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/library/rental")
public class RentalController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RentalController.class);

    @Autowired
    BookService bookService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookMapper bookMapper;
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
    public ResponseEntity<Object> deleteRental(@RequestParam Long rentalId) {
        rentalService.deleteRental(rentalId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("updateRental")
    public RentalDto updateRental(@RequestBody RentalDto rentalDto) {
        return rentalMapper.mapToRentalDto(rentalService.saveRental(rentalMapper.mapToRental(rentalDto)));
    }

    @PostMapping("createRental")
    public RentalDto createRental(@Valid @RequestBody RentalDto rentalDto) {
       return rentalMapper.mapToRentalDto(rentalService.saveRental(rentalMapper.mapToRental(rentalDto)));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "checkoutBook")
    public ResponseEntity<Object> checkoutBook (
            @Validated(value = {OrderChecks3.class}) @Valid @RequestBody BookDto bookDto,
            Errors errors, @RequestParam String username) throws Exception {

            if (errors.hasErrors()) {
                Map<String, ArrayList<Object>> errorsMap = new HashMap<>();

                errors.getFieldErrors().stream().forEach(fieldError -> {
                    String key = fieldError.getField();
                    if (!errorsMap.containsKey(key)) {
                        errorsMap.put(key, new ArrayList<>());
                    }
                    errorsMap.get(key).add(fieldError.getDefaultMessage());
                });
                errorsMap.values().stream().findFirst();
                return new ResponseEntity<>(errorsMap, HttpStatus.UNPROCESSABLE_ENTITY);
            }
            rentalService.processRequest(bookDto, username);
            // rentalMapper.mapToRentalDtoList(rentalService.checkoutBook(bookDto, username));
            return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @GetMapping("getRentalsForUser")
    public List<RentalDto> getRentalsByUsername(@RequestParam String username) {
        return rentalMapper.mapToRentalDtoList(rentalService.getRentals(username));
    }

}
