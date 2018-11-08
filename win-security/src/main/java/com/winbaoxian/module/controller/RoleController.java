package com.winbaoxian.module.controller;

import com.winbaoxian.module.model.common.JsonResult;
import com.winbaoxian.module.model.common.Pagination;
import com.winbaoxian.module.model.common.PaginationDTO;
import com.winbaoxian.module.model.dto.RoleDTO;
import com.winbaoxian.module.service.RoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/api/winSecurity/v1/")
public class RoleController {

    @Resource
    private RoleService roleService;
 
    @PostMapping(value = "/role")
    public JsonResult<RoleDTO> addRole(@RequestBody RoleDTO dto) {
        RoleDTO RoleDTO = roleService.addRole(dto);
        return JsonResult.createSuccessResult(RoleDTO);
    }

 
    @DeleteMapping(value = "/role/{id}")
    public JsonResult deleteRole(@PathVariable("id") Long id) {
        roleService.deleteRole(id);
        return JsonResult.createSuccessResult(null);
    }

   
    @PutMapping(value = "/role/{id}")
    public JsonResult<RoleDTO> updateRole(@PathVariable("id") Long id, @RequestBody RoleDTO dto) {
        RoleDTO RoleDTO = roleService.updateRole(id, dto);
        return JsonResult.createSuccessResult(RoleDTO);
    }


    @GetMapping(value = "/role/{id}")
    public JsonResult<RoleDTO> getRole(@PathVariable("id") Long id) {
        RoleDTO RoleDTO = roleService.getRole(id);
        return JsonResult.createSuccessResult(RoleDTO);
    }


    @GetMapping(value = "/role")
    public JsonResult<List<RoleDTO>> getRoleList() {
        List<RoleDTO> RoleList = roleService.getRoleList();
        return JsonResult.createSuccessResult(RoleList);
    }


    @GetMapping(value = "/role/p")
    public JsonResult<PaginationDTO<RoleDTO>> getRolePage(Pagination pagination) {
        PaginationDTO<RoleDTO> paginationDTO = roleService.getRolePage(pagination);
        return JsonResult.createSuccessResult(paginationDTO);
    }
}
