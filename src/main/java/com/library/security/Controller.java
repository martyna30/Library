package com.library.security;


import io.jsonwebtoken.SignatureAlgorithm;


import io.jsonwebtoken.Jwts;

import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.security.Principal;
import java.util.*;
@RestController
public class Controller {

    //@Autowired
   // UserService userService;
    //@Autowired
    //jwtService jwtService;

    //@Autowired
    //AuthenticationManager authenticationManager;
    private List<Buk> buks;

    public Controller() {
        this.buks = new ArrayList<>();
        buks.add(new Buk("Dom"));
        buks.add(new Buk("Dom zły"));

    }

    @PostMapping("/auth")
    public String generateToken(@RequestBody User user) throws Exception {
        return null;
    }



    @GetMapping("/test1")
    public List<Buk> getBuk(Principal principal) {
        System.out.println(principal.getName());
        return buks;
    }


    @PostMapping("/test2")
    public boolean addBuk(@RequestBody Buk buk) {
        return buks.add(buk);
    }

    @DeleteMapping("/test3")
    public void deleteBuk(@RequestParam Long id) {

    }
}
