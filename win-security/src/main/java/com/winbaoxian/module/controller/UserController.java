package com.winbaoxian.module.controller;

import com.alibaba.fastjson.JSON;
import com.winbaoxian.module.config.UserConfiguration;
import com.winbaoxian.module.model.common.JsonResult;
import com.winbaoxian.module.model.common.Pagination;
import com.winbaoxian.module.model.common.PaginationDTO;
import com.winbaoxian.module.model.dto.BaseUserDTO;
import com.winbaoxian.module.model.entity.BaseUserEntity;
import com.winbaoxian.module.service.UserService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping(value = "/api/winSecurity/v1/")
public class UserController<D extends BaseUserDTO, E extends BaseUserEntity> {

    @Resource
    private UserService<D, E> userService;
    @Resource
    private UserConfiguration userConfiguration;


    /**
     * @apiVersion 1.0.0
     * @api {POST} /api/winSecurity/v1/addUser 新增用户
     * @apiGroup user
     * @apiName addUser
     * @apiParam (请求体) {String} userName 登录名
     * @apiParam (请求体) {String} name 姓名
     * @apiParam (请求体) {String} mobile 手机号
     * @apiParam (请求体) {Number} status 用户状态，, 状态, 0:无效 , 1:有效
     * @apiParam (请求体) {Array} roleIdList 角色ID列表
     * @apiParamExample 请求体示例
     * {"userName":"admin1","name":"admin","mobile":"18707173376","status":0,"roleIdList":[1,2]}
     * @apiSuccess (响应参数) {Number} id 主键
     * @apiSuccess (响应参数) {Object} createTime 创建时间
     * @apiSuccess (响应参数) {Object} updateTime 更新时间
     * @apiSuccess (响应参数) {String} userName 登录名
     * @apiSuccess (响应参数) {String} name 姓名
     * @apiSuccess (响应参数) {String} mobile 手机号
     * @apiSuccess (响应参数) {Number} status 用户状态，, 状态, 0:无效 , 1:有效
     * @apiSuccess (响应参数) {Array} roleIdList 角色ID列表
     * @apiSuccessExample 响应示例
     * {"code":200,"msg":null,"data":{"id":16,"createTime":null,"updateTime":null,"userName":"admin1","name":"admin","mobile":"18707173376","status":0,"roleIdList":[1,2]}}
     */
    @PostMapping(value = "/addUser")
    public JsonResult<D> addUser(@RequestBody String dtoStr) {
        D dto = JSON.parseObject(dtoStr, (Type) userConfiguration.getUserDTOClass());
        D UserDTO = userService.addUser(dto);
        return JsonResult.createSuccessResult(UserDTO);
    }


    /**
     * @apiVersion 1.0.0
     * @api {POST} /api/winSecurity/v1/deleteUser 删除用户
     * @apiGroup user
     * @apiName deleteUser
     * @apiParam (请求参数) {Number} id 主键
     * @apiParamExample 请求参数示例
     * ?id=1
     * @apiSuccessExample 响应示例
     * {"code":200,"msg":null,"data":null}
     */
    @PostMapping(value = "/deleteUser")
    public JsonResult deleteUser(Long id) {
        userService.deleteUser(id);
        return JsonResult.createSuccessResult(null);
    }


    /**
     * @apiVersion 1.0.0
     * @api {POST} /api/winSecurity/v1/updateUser 更新用户
     * @apiGroup user
     * @apiName updateUser
     * @apiParam (请求体) {String} userName 登录名
     * @apiParam (请求体) {String} name 姓名
     * @apiParam (请求体) {String} mobile 手机号
     * @apiParam (请求体) {Number} status 用户状态，, 状态, 0:无效 , 1:有效
     * @apiParam (请求体) {Array} roleIdList 角色ID列表
     * @apiParamExample 请求体示例
     * {"id":16,"userName":"admin1","name":"admin","mobile":"18707173372","status":0,"roleIdList":[2,3]}
     * @apiSuccess (响应参数) {Number} id 主键
     * @apiSuccess (响应参数) {Number} createTime 创建时间
     * @apiSuccess (响应参数) {Number} updateTime 更新时间
     * @apiSuccess (响应参数) {String} userName 登录名
     * @apiSuccess (响应参数) {String} name 姓名
     * @apiSuccess (响应参数) {String} mobile 手机号
     * @apiSuccess (响应参数) {Number} status 用户状态，, 状态, 0:无效 , 1:有效
     * @apiSuccess (响应参数) {Array} roleIdList 角色ID列表
     * @apiSuccessExample 响应示例
     * {"code":200,"msg":null,"data":{"id":16,"createTime":1541743881000,"updateTime":1541744132000,"userName":"admin1","name":"admin","mobile":"18707173372","status":0,"roleIdList":[2,3]}}
     */
    @PostMapping(value = "/updateUser")
    public JsonResult<D> updateUser(@RequestBody String dtoStr) {
        D dto = JSON.parseObject(dtoStr, (Type) userConfiguration.getUserDTOClass());
        D UserDTO = userService.updateUser(dto);
        return JsonResult.createSuccessResult(UserDTO);
    }


    /**
     * @apiVersion 1.0.0
     * @api {GET} /api/winSecurity/v1/getUser 获取用户
     * @apiGroup user
     * @apiName getUser
     * @apiParam (请求参数) {Number} id 主键
     * @apiParamExample 请求参数示例
     * ?id=1
     * @apiSuccess (响应参数) {Number} id 主键
     * @apiSuccess (响应参数) {Number} createTime 创建时间
     * @apiSuccess (响应参数) {Number} updateTime 更新时间
     * @apiSuccess (响应参数) {String} userName 登录名
     * @apiSuccess (响应参数) {String} name 姓名
     * @apiSuccess (响应参数) {String} mobile 手机号
     * @apiSuccess (响应参数) {Number} status 用户状态，, 状态, 0:无效 , 1:有效
     * @apiSuccess (响应参数) {Array} roleIdList 角色ID列表
     * @apiSuccessExample 响应示例
     * {"code":200,"msg":null,"data":{"id":16,"createTime":1541743881000,"updateTime":1541744132000,"userName":"admin1","name":"admin","mobile":"18707173372","status":0,"roleIdList":[2,3]}}
     */
    @GetMapping(value = "/getUser")
    public JsonResult<D> getUser(Long id) {
        D UserDTO = userService.getUser(id);
        return JsonResult.createSuccessResult(UserDTO);
    }


    /**
     * @apiVersion 1.0.0
     * @api {GET} /api/winSecurity/v1/getUserList 获取用户列表
     * @apiGroup user
     * @apiName getUserList
     * @apiSuccess (响应参数) {Number} id 主键
     * @apiSuccess (响应参数) {Number} createTime 创建时间
     * @apiSuccess (响应参数) {Number} updateTime 更新时间
     * @apiSuccess (响应参数) {String} userName 登录名
     * @apiSuccess (响应参数) {String} name 姓名
     * @apiSuccess (响应参数) {String} mobile 手机号
     * @apiSuccess (响应参数) {Number} status 用户状态，, 状态, 0:无效 , 1:有效
     * @apiSuccessExample 响应示例
     * {"code":200,"msg":null,"data":[{"id":1,"createTime":1449378845000,"updateTime":1541642528000,"userName":"admin","name":"admin","mobile":"18707173376","status":0,"roleIdList":null},{"id":13,"createTime":1443676327000,"updateTime":1541642528000,"userName":"snoopy","name":"snoopy","mobile":"18707173376","status":0}]}
     */
    @GetMapping(value = "/getUserList")
    public JsonResult<List<D>> getUserList() {
        List<D> UserList = userService.getUserList();
        return JsonResult.createSuccessResult(UserList);
    }


    /**
     * @apiVersion 1.0.0
     * @api {GET} /api/winSecurity/v1/getUserPage 获取用户分页数据
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
     * @apiSuccess (响应参数) {Number} list.status 用户状态，, 状态, 0:无效 , 1:有效
     * @apiSuccess (响应参数) {Number} startRow 本页起始行
     * @apiSuccessExample 响应示例
     * {"code":200,"msg":null,"data":{"pageNum":1,"pageSize":2,"totalRow":4,"totalPage":2,"orderProperty":null,"orderDirection":null,"list":[{"id":1,"createTime":1449378845000,"updateTime":1541642528000,"userName":"admin","name":"admin","mobile":"18707173376","status":0,"roleIdList":null},{"id":13,"createTime":1443676327000,"updateTime":1541642528000,"userName":"snoopy","name":"snoopy","mobile":"18707173376","status":0}],"startRow":0}}
     */
    @GetMapping(value = "/getUserPage")
    public JsonResult<PaginationDTO<D>> getUserPage(Pagination pagination) {
        PaginationDTO<D> paginationDTO = userService.getUserPage(pagination);
        return JsonResult.createSuccessResult(paginationDTO);
    }
}
