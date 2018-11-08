package com.winbaoxian.module.controller;

import com.winbaoxian.module.model.common.JsonResult;
import com.winbaoxian.module.model.common.Pagination;
import com.winbaoxian.module.model.common.PaginationDTO;
import com.winbaoxian.module.model.dto.UserDTO;
import com.winbaoxian.module.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/api/winSecurity/v1/")
public class UserController {

    @Resource
    private UserService userService;
 
    @PostMapping(value = "/user")
    public JsonResult<UserDTO> addUser(@RequestBody UserDTO dto) {
        UserDTO UserDTO = userService.addUser(dto);
        return JsonResult.createSuccessResult(UserDTO);
    }

 
    @DeleteMapping(value = "/user/{id}")
    public JsonResult deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return JsonResult.createSuccessResult(null);
    }

   
    @PutMapping(value = "/user/{id}")
    public JsonResult<UserDTO> updateUser(@PathVariable("id") Long id, @RequestBody UserDTO dto) {
        UserDTO UserDTO = userService.updateUser(id, dto);
        return JsonResult.createSuccessResult(UserDTO);
    }


    @GetMapping(value = "/user/{id}")
    public JsonResult<UserDTO> getUser(@PathVariable("id") Long id) {
        UserDTO UserDTO = userService.getUser(id);
        return JsonResult.createSuccessResult(UserDTO);
    }


    @GetMapping(value = "/user")
    public JsonResult<List<UserDTO>> getUserList() {
        List<UserDTO> UserList = userService.getUserList();
        return JsonResult.createSuccessResult(UserList);
    }


    @GetMapping(value = "/user/p")
    public JsonResult<PaginationDTO<UserDTO>> getUserPage(Pagination pagination) {
        PaginationDTO<UserDTO> paginationDTO = userService.getUserPage(pagination);
        return JsonResult.createSuccessResult(paginationDTO);
    }
}
