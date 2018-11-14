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

    /**
     * @apiVersion 1.0.0
     * @api {POST} /api/winSecurity/v1/role 新增角色
     * @apiGroup role
     * @apiName addRole
     * @apiParam (请求体) {String} name 角色名称
     * @apiParam (请求体) {String} description 描述
     * @apiParam (请求体) {Number} seq 排序
     * @apiParam (请求体) {Number} status 状态, 0:禁用 , 1:启用, 2:全部放行
     * @apiParam (请求体) {Array} resourceIdList 资源ID列表
     * @apiParamExample 请求体示例
     * {"name":"超级管理2员","description":"超级管理2员1111","seq":0,"status":0,"resourceIdList":[143,144,221,222,223,224,227,228]}
     * @apiSuccess (响应参数) {Number} createTime 创建时间
     * @apiSuccess (响应参数) {String} description 描述
     * @apiSuccess (响应参数) {Number} id 主键
     * @apiSuccess (响应参数) {String} name 角色名称
     * @apiSuccess (响应参数) {Array} resourceIdList 资源ID列表
     * @apiSuccess (响应参数) {Number} seq 排序
     * @apiSuccess (响应参数) {Number} status 状态, 0:禁用 , 1:启用, 2:全部放行
     * @apiSuccess (响应参数) {Number} updateTime 更新时间
     * @apiSuccessExample 响应示例
     * {"code":200,"data":{"createTime":1541753887713,"description":"超级管理2员1111","id":12,"name":"超级管理2员","resourceIdList":[143,144,221,222,223,224,227,228],"seq":0,"status":0,"updateTime":1541753887713}}
     */
    @PostMapping(value = "/role")
    public JsonResult<RoleDTO> addRole(@RequestBody RoleDTO dto) {
        RoleDTO RoleDTO = roleService.addRole(dto);
        return JsonResult.createSuccessResult(RoleDTO);
    }


    /**
     * @apiVersion 1.0.0
     * @api {DELETE} /api/winSecurity/v1/role/{id} 删除角色
     * @apiGroup role
     * @apiName deleteRole
     * @apiSuccessExample 响应示例
     * {"code":200,"msg":null,"data":null}
     */
    @DeleteMapping(value = "/role/{id}")
    public JsonResult deleteRole(@PathVariable("id") Long id) {
        roleService.deleteRole(id);
        return JsonResult.createSuccessResult(null);
    }


    /**
     * @apiVersion 1.0.0
     * @api {UPDATE} /api/winSecurity/v1/role/{id} 更新角色
     * @apiGroup role
     * @apiName updateRole
     * @apiParam (请求体) {String} name 角色名称
     * @apiParam (请求体) {String} description 描述
     * @apiParam (请求体) {Number} seq 排序
     * @apiParam (请求体) {Number} status 状态, 0:禁用 , 1:启用, 2:全部放行
     * @apiParam (请求体) {Array} resourceIdList 资源ID列表
     * @apiParamExample 请求体示例
     * {"name":"admin","description":"超级管理2员","seq":0,"status":0,"resourceIdList":[143,144,221,222,223,224,227,228]}
     * @apiSuccess (响应参数) {Number} id 主键
     * @apiSuccess (响应参数) {Number} createTime 创建时间
     * @apiSuccess (响应参数) {Number} updateTime 更新时间
     * @apiSuccess (响应参数) {String} name 角色名称
     * @apiSuccess (响应参数) {String} description 描述
     * @apiSuccess (响应参数) {Number} seq 排序
     * @apiSuccess (响应参数) {Number} status 状态, 0:禁用 , 1:启用, 2:全部放行
     * @apiSuccess (响应参数) {Array} resourceIdList 资源ID列表
     * @apiSuccessExample 响应示例
     * {"code":200,"msg":null,"data":{"id":1,"createTime":1541642375000,"updateTime":1541642375000,"name":"admin","description":"超级管理2员","seq":0,"status":0,"resourceIdList":[143,144,221,222,223,224,227,228]}}
     */
    @PutMapping(value = "/role/{id}")
    public JsonResult<RoleDTO> updateRole(@PathVariable("id") Long id, @RequestBody RoleDTO dto) {
        RoleDTO RoleDTO = roleService.updateRole(id, dto);
        return JsonResult.createSuccessResult(RoleDTO);
    }


    /**
     * @apiVersion 1.0.0
     * @api {GET} /api/winSecurity/v1/role/{id} 获取角色
     * @apiGroup role
     * @apiName getRole
     * @apiSuccess (响应参数) {Number} id 主键
     * @apiSuccess (响应参数) {Number} createTime 创建时间
     * @apiSuccess (响应参数) {Number} updateTime 更新时间
     * @apiSuccess (响应参数) {String} name 角色名称
     * @apiSuccess (响应参数) {String} description 描述
     * @apiSuccess (响应参数) {Number} seq 排序
     * @apiSuccess (响应参数) {Number} status 状态, 0:禁用 , 1:启用, 2:全部放行
     * @apiSuccess (响应参数) {Array} resourceIdList 资源ID列表
     * @apiSuccessExample 响应示例
     * {"code":200,"msg":null,"data":{"id":1,"createTime":1541642375000,"updateTime":1541642375000,"name":"admin","description":"超级管理员","seq":0,"status":0,"resourceIdList":[1,11,12,13,14]}}
     */
    @GetMapping(value = "/role/{id}")
    public JsonResult<RoleDTO> getRole(@PathVariable("id") Long id) {
        RoleDTO RoleDTO = roleService.getRole(id);
        return JsonResult.createSuccessResult(RoleDTO);
    }


    /**
     * @apiVersion 1.0.0
     * @api {GET} /api/winSecurity/v1/role 获取角色列表
     * @apiGroup role
     * @apiName getRoleList
     * @apiSuccess (响应参数) {Number} id 主键
     * @apiSuccess (响应参数) {Number} createTime 创建时间
     * @apiSuccess (响应参数) {Number} updateTime 更新时间
     * @apiSuccess (响应参数) {String} name 角色名称
     * @apiSuccess (响应参数) {String} description 描述
     * @apiSuccess (响应参数) {Number} seq 排序
     * @apiSuccess (响应参数) {Number} status 状态, 0:禁用 , 1:启用, 2:全部放行
     * @apiSuccessExample 响应示例
     * {"code":200,"msg":null,"data":[{"id":1,"createTime":1541642375000,"updateTime":1541642375000,"name":"admin","description":"超级管理员","seq":0,"status":0},{"id":2,"createTime":1541642375000,"updateTime":1541642375000,"name":"de","description":"技术部经理","seq":0,"status":0}]}
     */
    @GetMapping(value = "/role")
    public JsonResult<List<RoleDTO>> getRoleList() {
        List<RoleDTO> RoleList = roleService.getRoleList();
        return JsonResult.createSuccessResult(RoleList);
    }


    /**
     * @apiVersion 1.0.0
     * @api {GET} /api/winSecurity/v1/role/p 获取角色分页数据
     * @apiGroup role
     * @apiName getRoleP
     * @apiParam (请求参数) {Number} pageNum 第几页
     * @apiParam (请求参数) {Number} pageSize 每页数量
     * @apiParamExample 请求参数示例
     * ?pageNum=1&pageSize=2
     * @apiSuccess (响应参数) {Number} pageNum 第几页
     * @apiSuccess (响应参数) {Number} pageSize 每页数量
     * @apiSuccess (响应参数) {Number} totalRow 总数据量
     * @apiSuccess (响应参数) {Number} totalPage 总页数
     * @apiSuccess (响应参数) {Object} orderProperty 排序字段
     * @apiSuccess (响应参数) {Object} orderDirection 升序:ASC，降序:DESC
     * @apiSuccess (响应参数) {Array} list 列表
     * @apiSuccess (响应参数) {Number} list.id 主键
     * @apiSuccess (响应参数) {Number} list.createTime 创建时间
     * @apiSuccess (响应参数) {Number} list.updateTime 更新时间
     * @apiSuccess (响应参数) {String} list.name 角色名称
     * @apiSuccess (响应参数) {String} list.description 描述
     * @apiSuccess (响应参数) {Number} list.seq 排序
     * @apiSuccess (响应参数) {Number} list.status 状态, 0:禁用 , 1:启用, 2:全部放行
     * @apiSuccess (响应参数) {Number} startRow 本页起始行
     * @apiSuccessExample 响应示例
     * {"code":200,"msg":null,"data":{"pageNum":1,"pageSize":2,"totalRow":4,"totalPage":2,"orderProperty":null,"orderDirection":null,"list":[{"id":8,"createTime":1541642375000,"updateTime":1541642375000,"name":"test","description":"测试账户","seq":0,"status":0},{"id":2,"createTime":1541642375000,"updateTime":1541642375000,"name":"de","description":"技术部经理","seq":0,"status":0}],"startRow":0}}
     */
    @GetMapping(value = "/role/p")
    public JsonResult<PaginationDTO<RoleDTO>> getRolePage(Pagination pagination) {
        PaginationDTO<RoleDTO> paginationDTO = roleService.getRolePage(pagination);
        return JsonResult.createSuccessResult(paginationDTO);
    }
}
