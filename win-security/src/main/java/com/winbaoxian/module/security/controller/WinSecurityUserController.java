package com.winbaoxian.module.security.controller;

import com.alibaba.fastjson.JSON;
import com.winbaoxian.module.security.config.loader.WinSecurityClassLoaderConfiguration;
import com.winbaoxian.module.security.model.common.JsonResult;
import com.winbaoxian.module.security.model.common.Pagination;
import com.winbaoxian.module.security.model.common.PaginationDTO;
import com.winbaoxian.module.security.model.dto.IdParamDTO;
import com.winbaoxian.module.security.model.dto.WinSecurityBaseUserDTO;
import com.winbaoxian.module.security.model.entity.WinSecurityBaseUserEntity;
import com.winbaoxian.module.security.model.enums.WinSecurityErrorEnum;
import com.winbaoxian.module.security.model.exceptions.WinSecurityException;
import com.winbaoxian.module.security.service.WinSecurityUserService;
import com.winbaoxian.module.security.utils.MapFastUtils;
import com.winbaoxian.module.security.utils.TransformerUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/winSecurity/v1/user/")
public class WinSecurityUserController<D extends WinSecurityBaseUserDTO, E extends WinSecurityBaseUserEntity> {

    @Resource
    private WinSecurityUserService<D, E> winSecurityUserService;
    @Resource
    private WinSecurityClassLoaderConfiguration winSecurityClassLoaderConfiguration;


    /**
     * @apiVersion 1.0.0
     * @api {POST} /api/winSecurity/v1/user/addUser 新增用户
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
        D dto = JSON.parseObject(dtoStr, (Type) winSecurityClassLoaderConfiguration.getUserDTOClass());
        D UserDTO = winSecurityUserService.addUser(dto);
        return JsonResult.createSuccessResult(UserDTO);
    }


    /**
     * @api {POST} /api/winSecurity/v1/user/deleteUser 删除用户
     * @apiVersion 1.0.0
     * @apiGroup user
     * @apiName deleteUser
     * @apiParam (请求体) {Number} id 主键
     * @apiParamExample 请求体示例
     * {"id":4676}
     * @apiSuccess (响应结果) {Number} code
     * @apiSuccess (响应结果) {String} msg
     * @apiSuccess (响应结果) {Object} data
     * @apiSuccessExample 响应结果示例
     * {"code":200,"msg":null,"data":null}
     */
    @PostMapping(value = "/deleteUser")
    public JsonResult deleteUser(@RequestBody IdParamDTO paramDTO) {
        if (paramDTO.getId() == null) {
            throw new WinSecurityException(WinSecurityErrorEnum.COMMON_PARAM_ID_NOT_EXISTS);
        }
        winSecurityUserService.deleteUser(paramDTO.getId());
        return JsonResult.createSuccessResult(null);
    }


    /**
     * @apiVersion 1.0.0
     * @api {POST} /api/winSecurity/v1/user/updateUser 更新用户
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
        D dto = JSON.parseObject(dtoStr, (Type) winSecurityClassLoaderConfiguration.getUserDTOClass());
        D UserDTO = winSecurityUserService.updateUser(dto);
        return JsonResult.createSuccessResult(UserDTO);
    }


    /**
     * @apiVersion 1.0.0
     * @api {GET} /api/winSecurity/v1/user/getUser 获取用户
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
        D UserDTO = winSecurityUserService.getUser(id);
        return JsonResult.createSuccessResult(UserDTO);
    }


    /**
     * @apiVersion 1.0.0
     * @api {GET} /api/winSecurity/v1/user/getUserList 获取用户列表
     * @apiDescription 支持动态查询数据，参数放到URL中
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
    public JsonResult<List<D>> getUserList(@RequestParam Map<String, Object> paramsMap) {
        MapFastUtils.INSTANCE.valueEmptyToNull(paramsMap);
        D params = (D) TransformerUtils.INSTANCE.transformMap2Object(paramsMap, winSecurityClassLoaderConfiguration.getUserDTOClass());
        List<D> UserList = winSecurityUserService.getUserList(params);
        return JsonResult.createSuccessResult(UserList);
    }


    /**
     * @apiVersion 1.0.0
     * @api {GET} /api/winSecurity/v1/user/getUserPage 获取用户分页数据
     * @apiDescription 支持动态查询数据，参数放到URL中
     * @apiGroup user
     * @apiName getUserPage
     * @apiParam (请求参数) {String} userName 登录名,支持模糊搜索searchWord%
     * @apiParam (请求参数) {String} mobile 手机号,支持模糊搜索searchWord%
     * @apiParam (请求参数) {Number} status 用户状态，, 状态, 0:无效 , 1:有效
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
    public JsonResult<PaginationDTO<D>> getUserPage(@RequestParam Map<String, Object> paramsMap, Pagination pagination) {
        MapFastUtils.INSTANCE.valueEmptyToNull(paramsMap);
        D params = (D) TransformerUtils.INSTANCE.transformMap2Object(paramsMap, winSecurityClassLoaderConfiguration.getUserDTOClass());
        PaginationDTO<D> paginationDTO = winSecurityUserService.getUserPage(params, pagination);
        return JsonResult.createSuccessResult(paginationDTO);
    }
}
