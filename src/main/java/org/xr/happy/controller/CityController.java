package org.xr.happy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.xr.happy.common.dto.City;
import org.xr.happy.mapper.UserMapper;
import org.xr.happy.model.User;
import org.xr.happy.service.CityService;

import java.util.List;

@RestController
public class CityController {

    private static final Logger logger = LoggerFactory.getLogger(CityController.class);

    @Autowired
    private CityService cityService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/cities")
    public ResponseEntity<?> getAllCities() {
       // List<City> cities = cityService.getAllCities();
        logger.info("citity is :{}",0);
        return ResponseEntity.ok(0);
    }


    @GetMapping("/cities/{id}")
    public ResponseEntity<?> getAllCities(@PathVariable(name = "id") int id) {
        System.out.println("city id is :"+id);
        return ResponseEntity.ok("kkkk");
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getALLUser(@PathVariable(name = "id") int id) {
        List<City> allCities = cityService.getAllCities();
        List<User> users = userMapper.selectAll();
        System.out.println(users);
        return ResponseEntity.ok("user");
    }
}
