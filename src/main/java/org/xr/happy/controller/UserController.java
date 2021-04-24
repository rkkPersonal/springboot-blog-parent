package org.xr.happy.controller;

import org.springframework.web.bind.annotation.*;
import org.xr.happy.common.dto.Result;

/**
 * user controller to manager user info
 *
 * @author Steven
 */
@RestController
@RequestMapping("/user")
public class UserController {


    @PostMapping(path = "/{userId}")
    public Result addUser(@PathVariable(name = "userId") Long userId) {

        return Result.ok();
    }

    @DeleteMapping(path = "/{userId}")
    public Result deleteUser(@PathVariable(name = "userId") Long userId) {

        return Result.ok();
    }

    @PutMapping(path = "/{userId}")
    public Result updateUser(@PathVariable(name = "userId") Long userId) {

        return Result.ok();
    }

    @GetMapping(path = "/{userId}")
    public Result queryUser(@PathVariable(name = "userId") Long userId) {

        return Result.ok();
    }
}
