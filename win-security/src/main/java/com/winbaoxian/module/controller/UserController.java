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

    /**
     * @apiVersion 1.0.0
     * @api {POST} /api/winSecurity/v1/user 新增用户
     * @apiGroup user
     * @apiName addUser
     * @apiParam (请求体) {String} userName 登录名
     * @apiParam (请求体) {String} name 姓名
     * @apiParam (请求体) {String} mobile 手机号
     * @apiParam (请求体) {Number} status 用户状态，, 0:禁用 , 1:启用, 2:全部放行
     * @apiParam (请求体) {Array} roleIdList 角色ID列表
     * @apiParamExample 请求体示例
     * {"userName":"admin1","name":"admin","mobile":"18707173376","status":0,"roleIdList":[1,2]}
     * @apiSuccess (响应参数) {Number} id 主键
     * @apiSuccess (响应参数) {Object} createTime 创建时间
     * @apiSuccess (响应参数) {Object} updateTime 更新时间
     * @apiSuccess (响应参数) {String} userName 登录名
     * @apiSuccess (响应参数) {String} name 姓名
     * @apiSuccess (响应参数) {String} mobile 手机号
     * @apiSuccess (响应参数) {Number} status 用户状态，, 0:禁用 , 1:启用, 2:全部放行
     * @apiSuccess (响应参数) {Array} roleIdList 角色ID列表
     * @apiSuccessExample 响应示例
     * {"code":200,"msg":null,"data":{"id":16,"createTime":null,"updateTime":null,"userName":"admin1","name":"admin","mobile":"18707173376","status":0,"roleIdList":[1,2]}}
     */
    @PostMapping(value = "/user")
    public JsonResult<UserDTO> addUser(@RequestBody UserDTO dto) {
        UserDTO UserDTO = userService.addUser(dto);
        return JsonResult.createSuccessResult(UserDTO);
    }


    /**
     * @apiVersion 1.0.0
     * @api {DELETE} /api/winSecurity/v1/user/{id} 删除用户
     * @apiGroup user
     * @apiName deleteUser
     * @apiSuccessExample 响应示例
     * {"code":200,"msg":null,"data":null}
     */
    @DeleteMapping(value = "/user/{id}")
    public JsonResult deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return JsonResult.createSuccessResult(null);
    }


    /**
     * @apiVersion 1.0.0
     * @api {PUT} /api/winSecurity/v1/user/{id} 更新用户
     * @apiGroup user
     * @apiName updateUser
     * @apiParam (请求体) {String} userName 登录名
     * @apiParam (请求体) {String} name 姓名
     * @apiParam (请求体) {String} mobile 手机号
     * @apiParam (请求体) {Number} status 用户状态，, 0:禁用 , 1:启用, 2:全部放行
     * @apiParam (请求体) {Array} roleIdList 角色ID列表
     * @apiParamExample 请求体示例
     * {"userName":"admin1","name":"admin","mobile":"18707173372","status":0,"roleIdList":[2,3]}
     * @apiSuccess (响应参数) {Number} id 主键
     * @apiSuccess (响应参数) {Number} createTime 创建时间
     * @apiSuccess (响应参数) {Number} updateTime 更新时间
     * @apiSuccess (响应参数) {String} userName 登录名
     * @apiSuccess (响应参数) {String} name 姓名
     * @apiSuccess (响应参数) {String} mobile 手机号
     * @apiSuccess (响应参数) {Number} status 用户状态，, 0:禁用 , 1:启用, 2:全部放行
     * @apiSuccess (响应参数) {Array} roleIdList 角色ID列表
     * @apiSuccessExample 响应示例
     * {"code":200,"msg":null,"data":{"id":16,"createTime":1541743881000,"updateTime":1541744132000,"userName":"admin1","name":"admin","mobile":"18707173372","status":0,"roleIdList":[2,3]}}
     */
    @PutMapping(value = "/user/{id}")
    public JsonResult<UserDTO> updateUser(@PathVariable("id") Long id, @RequestBody UserDTO dto) {
        UserDTO UserDTO = userService.updateUser(id, dto);
        return JsonResult.createSuccessResult(UserDTO);
    }


    /**
     * @apiVersion 1.0.0
     * @api {GET} /api/winSecurity/v1/user/{id}} 获取用户
     * @apiGroup user
     * @apiName getUser
     * @apiSuccess (响应参数) {Number} id 主键
     * @apiSuccess (响应参数) {Number} createTime 创建时间
     * @apiSuccess (响应参数) {Number} updateTime 更新时间
     * @apiSuccess (响应参数) {String} userName 登录名
     * @apiSuccess (响应参数) {String} name 姓名
     * @apiSuccess (响应参数) {String} mobile 手机号
     * @apiSuccess (响应参数) {Number} status 用户状态，, 0:禁用 , 1:启用, 2:全部放行
     * @apiSuccess (响应参数) {Array} roleIdList 角色ID列表
     * @apiSuccessExample 响应示例
     * {"code":200,"msg":null,"data":{"id":16,"createTime":1541743881000,"updateTime":1541744132000,"userName":"admin1","name":"admin","mobile":"18707173372","status":0,"roleIdList":[2,3]}}
     */
    @GetMapping(value = "/user/{id}")
    public JsonResult<UserDTO> getUser(@PathVariable("id") Long id) {
        UserDTO UserDTO = userService.getUser(id);
        return JsonResult.createSuccessResult(UserDTO);
    }


    /**
     * @apiVersion 1.0.0
     * @api {GET} /api/winSecurity/v1/user 获取用户列表
     * @apiGroup user
     * @apiName getUserList
     * @apiSuccess (响应参数) {Number} id 主键
     * @apiSuccess (响应参数) {Number} createTime 创建时间
     * @apiSuccess (响应参数) {Number} updateTime 更新时间
     * @apiSuccess (响应参数) {String} userName 登录名
     * @apiSuccess (响应参数) {String} name 姓名
     * @apiSuccess (响应参数) {String} mobile 手机号
     * @apiSuccess (响应参数) {Number} status 用户状态，, 0:禁用 , 1:启用, 2:全部放行
     * @apiSuccessExample 响应示例
     * {"code":200,"msg":null,"data":[{"id":1,"createTime":1449378845000,"updateTime":1541642528000,"userName":"admin","name":"admin","mobile":"18707173376","status":0,"roleIdList":null},{"id":13,"createTime":1443676327000,"updateTime":1541642528000,"userName":"snoopy","name":"snoopy","mobile":"18707173376","status":0}]}
     */
    @GetMapping(value = "/user")
    public JsonResult<List<UserDTO>> getUserList() {
        List<UserDTO> UserList = userService.getUserList();
        return JsonResult.createSuccessResult(UserList);
    }


    /**
     * @apiVersion 1.0.0
     * @api {GET} /api/winSecurity/v1/user/p 获取用户分页数据
     * @apiGroup user
     * @apiName getUserP
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
     * @apiSuccess (响应参数) {String} list.userName 登录名
     * @apiSuccess (响应参数) {String} list.name 姓名
     * @apiSuccess (响应参数) {String} list.mobile 手机号
     * @apiSuccess (响应参数) {Number} list.status 用户状态，, 0:禁用 , 1:启用, 2:全部放行
     * @apiSuccess (响应参数) {Number} startRow 本页起始行
     * @apiSuccessExample 响应示例
     * {"code":200,"msg":null,"data":{"pageNum":1,"pageSize":2,"totalRow":4,"totalPage":2,"orderProperty":null,"orderDirection":null,"list":[{"id":1,"createTime":1449378845000,"updateTime":1541642528000,"userName":"admin","name":"admin","mobile":"18707173376","status":0,"roleIdList":null},{"id":13,"createTime":1443676327000,"updateTime":1541642528000,"userName":"snoopy","name":"snoopy","mobile":"18707173376","status":0}],"startRow":0}}
     */
    @GetMapping(value = "/user/p")
    public JsonResult<PaginationDTO<UserDTO>> getUserPage(Pagination pagination) {
        PaginationDTO<UserDTO> paginationDTO = userService.getUserPage(pagination);
        return JsonResult.createSuccessResult(paginationDTO);
    }
}
