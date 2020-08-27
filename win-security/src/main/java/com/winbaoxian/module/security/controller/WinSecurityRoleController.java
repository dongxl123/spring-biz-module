package com.winbaoxian.module.security.controller;

import com.winbaoxian.module.security.model.common.JsonResult;
import com.winbaoxian.module.security.model.common.Pagination;
import com.winbaoxian.module.security.model.common.PaginationDTO;
import com.winbaoxian.module.security.model.dto.IdParamDTO;
import com.winbaoxian.module.security.model.dto.WinSecurityRoleDTO;
import com.winbaoxian.module.security.model.enums.WinSecurityErrorEnum;
import com.winbaoxian.module.security.model.exceptions.WinSecurityException;
import com.winbaoxian.module.security.service.WinSecurityRoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/api/winSecurity/v1/role/")
public class WinSecurityRoleController {

    @Resource
    private WinSecurityRoleService winSecurityRoleService;

    /**
     * @apiVersion 1.0.0
     * @api {POST} /api/winSecurity/v1/role/addRole 新增角色
     * @apiGroup role
     * @apiName addRole
     * @apiParam (请求体) {String} name 角色名称
     * @apiParam (请求体) {String} description 描述
     * @apiParam (请求体) {Number} seq 排序
     * @apiParam (请求体) {Number} status 状态, 0:无效 , 1:有效
     * @apiParam (请求体) {Array} resourceIdList 资源ID列表
     * @apiParamExample 请求体示例
     * {"name":"超级管理2员","description":"超级管理2员1111","seq":0,"status":0,"resourceIdList":[143,144,221,222,223,224,227,228]}
     * @apiSuccess (响应参数) {Number} createTime 创建时间
     * @apiSuccess (响应参数) {String} description 描述
     * @apiSuccess (响应参数) {Number} id 主键
     * @apiSuccess (响应参数) {String} name 角色名称
     * @apiSuccess (响应参数) {Array} resourceIdList 资源ID列表
     * @apiSuccess (响应参数) {Number} seq 排序
     * @apiSuccess (响应参数) {Number} status 状态, 0:无效 , 1:有效
     * @apiSuccess (响应参数) {Number} updateTime 更新时间
     * @apiSuccessExample 响应示例
     * {"code":200,"data":{"createTime":1541753887713,"description":"超级管理2员1111","id":12,"name":"超级管理2员","resourceIdList":[143,144,221,222,223,224,227,228],"seq":0,"status":0,"updateTime":1541753887713}}
     */
    @PostMapping(value = "/addRole")
    public JsonResult<WinSecurityRoleDTO> addRole(@RequestBody WinSecurityRoleDTO dto) {
        WinSecurityRoleDTO roleDTO = winSecurityRoleService.addRole(dto);
        return JsonResult.createSuccessResult(roleDTO);
    }


    /**
     * @api {POST} /api/winSecurity/v1/role/deleteRole 删除角色
     * @apiVersion 1.0.0
     * @apiGroup role
     * @apiName deleteRole
     * @apiParam (请求体) {Number} id 主键
     * @apiParamExample 请求体示例
     * {"id":233}
     * @apiSuccess (响应结果) {Number} code
     * @apiSuccess (响应结果) {String} msg
     * @apiSuccess (响应结果) {Object} data
     * @apiSuccessExample 响应结果示例
     * {"code":200,"msg":null,"data":null}
     */
    @PostMapping(value = "/deleteRole")
    public JsonResult deleteRole(@RequestBody IdParamDTO paramDTO) {
        if (paramDTO.getId() == null) {
            throw new WinSecurityException(WinSecurityErrorEnum.COMMON_PARAM_ID_NOT_EXISTS);
        }
        winSecurityRoleService.deleteRole(paramDTO.getId());
        return JsonResult.createSuccessResult(null);
    }


    /**
     * @apiVersion 1.0.0
     * @api {POST} /api/winSecurity/v1/role/updateRole 更新角色
     * @apiGroup role
     * @apiName updateRole
     * @apiParam (请求体) {String} name 角色名称
     * @apiParam (请求体) {String} description 描述
     * @apiParam (请求体) {Number} seq 排序
     * @apiParam (请求体) {Number} status 状态, 0:无效 , 1:有效
     * @apiParam (请求体) {Array} resourceIdList 资源ID列表
     * @apiParamExample 请求体示例
     * {"id":1,"name":"admin","description":"超级管理2员","seq":0,"status":0,"resourceIdList":[143,144,221,222,223,224,227,228]}
     * @apiSuccess (响应参数) {Number} id 主键
     * @apiSuccess (响应参数) {Number} createTime 创建时间
     * @apiSuccess (响应参数) {Number} updateTime 更新时间
     * @apiSuccess (响应参数) {String} name 角色名称
     * @apiSuccess (响应参数) {String} description 描述
     * @apiSuccess (响应参数) {Number} seq 排序
     * @apiSuccess (响应参数) {Number} status 状态, 0:无效 , 1:有效
     * @apiSuccess (响应参数) {Array} resourceIdList 资源ID列表
     * @apiSuccessExample 响应示例
     * {"code":200,"msg":null,"data":{"id":1,"createTime":1541642375000,"updateTime":1541642375000,"name":"admin","description":"超级管理2员","seq":0,"status":0,"resourceIdList":[143,144,221,222,223,224,227,228]}}
     */
    @PostMapping(value = "/updateRole")
    public JsonResult<WinSecurityRoleDTO> updateRole(@RequestBody WinSecurityRoleDTO dto) {
        WinSecurityRoleDTO roleDTO = winSecurityRoleService.updateRole(dto);
        return JsonResult.createSuccessResult(roleDTO);
    }


    /**
     * @apiVersion 1.0.0
     * @api {GET} /api/winSecurity/v1/role/getRole 获取角色
     * @apiGroup role
     * @apiName getRole
     * @apiParam (请求参数) {Number} id 主键
     * @apiParamExample 请求参数示例
     * ?id=1
     * @apiSuccess (响应参数) {Number} id 主键
     * @apiSuccess (响应参数) {Number} createTime 创建时间
     * @apiSuccess (响应参数) {Number} updateTime 更新时间
     * @apiSuccess (响应参数) {String} name 角色名称
     * @apiSuccess (响应参数) {String} description 描述
     * @apiSuccess (响应参数) {Number} seq 排序
     * @apiSuccess (响应参数) {Number} status 状态, 0:无效 , 1:有效
     * @apiSuccess (响应参数) {Array} resourceIdList 资源ID列表
     * @apiSuccessExample 响应示例
     * {"code":200,"msg":null,"data":{"id":1,"createTime":1541642375000,"updateTime":1541642375000,"name":"admin","description":"超级管理员","seq":0,"status":0,"resourceIdList":[1,11,12,13,14]}}
     */
    @GetMapping(value = "/getRole")
    public JsonResult<WinSecurityRoleDTO> getRole(Long id) {
        WinSecurityRoleDTO roleDTO = winSecurityRoleService.getRole(id);
        return JsonResult.createSuccessResult(roleDTO);
    }


    /**
     * @apiVersion 1.0.0
     * @api {GET} /api/winSecurity/v1/role/getRoleList 获取角色列表
     * @apiDescription 支持动态查询数据，参数放到URL中
     * @apiGroup role
     * @apiName getRoleList
     * @apiSuccess (响应参数) {Number} id 主键
     * @apiSuccess (响应参数) {Number} createTime 创建时间
     * @apiSuccess (响应参数) {Number} updateTime 更新时间
     * @apiSuccess (响应参数) {String} name 角色名称
     * @apiSuccess (响应参数) {String} description 描述
     * @apiSuccess (响应参数) {Number} seq 排序
     * @apiSuccess (响应参数) {Number} status 状态, 0:无效 , 1:有效
     * @apiSuccessExample 响应示例
     * {"code":200,"msg":null,"data":[{"id":1,"createTime":1541642375000,"updateTime":1541642375000,"name":"admin","description":"超级管理员","seq":0,"status":0},{"id":2,"createTime":1541642375000,"updateTime":1541642375000,"name":"de","description":"技术部经理","seq":0,"status":0}]}
     */
    @GetMapping(value = "/getRoleList")
    public JsonResult<List<WinSecurityRoleDTO>> getRoleList(WinSecurityRoleDTO params) {
        List<WinSecurityRoleDTO> roleList = winSecurityRoleService.getRoleList(params);
        return JsonResult.createSuccessResult(roleList);
    }


    /**
     * @apiVersion 1.0.0
     * @api {GET} /api/winSecurity/v1/role/getRolePage 获取角色分页数据
     * @apiDescription 支持动态查询数据，参数放到URL中
     * @apiGroup role
     * @apiName getRolePage
     * @apiParam (请求参数) {String} name 角色名称,支持模糊搜索searchWord%
     * @apiParam (请求参数) {Number} status 状态, 0:无效 , 1:有效
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
     * @apiSuccess (响应参数) {Number} list.status 状态, 0:无效 , 1:有效
     * @apiSuccess (响应参数) {Number} startRow 本页起始行
     * @apiSuccessExample 响应示例
     * {"code":200,"msg":null,"data":{"pageNum":1,"pageSize":2,"totalRow":4,"totalPage":2,"orderProperty":null,"orderDirection":null,"list":[{"id":8,"createTime":1541642375000,"updateTime":1541642375000,"name":"test","description":"测试账户","seq":0,"status":0},{"id":2,"createTime":1541642375000,"updateTime":1541642375000,"name":"de","description":"技术部经理","seq":0,"status":0}],"startRow":0}}
     */
    @GetMapping(value = "/getRolePage")
    public JsonResult<PaginationDTO<WinSecurityRoleDTO>> getRolePage(WinSecurityRoleDTO params, Pagination pagination) {
        PaginationDTO<WinSecurityRoleDTO> paginationDTO = winSecurityRoleService.getRolePage(params, pagination);
        return JsonResult.createSuccessResult(paginationDTO);
    }
}
