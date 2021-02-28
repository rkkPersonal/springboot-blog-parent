package org.xr.happy.controller;

import org.springframework.web.bind.annotation.*;
import org.xr.happy.common.dto.Result;
import org.xr.happy.common.dto.Room;

import java.util.Random;

@RestController
@RequestMapping("/api")
public class ApiController {


    @PostMapping(value = "/checkSumRoom")
    public Result checkRoomSum(@RequestBody Room room) {
        int i = new Random().nextInt(1000);
        Room build = Room.builder().sum(i).build();
        return Result.success(build);
    }
}
