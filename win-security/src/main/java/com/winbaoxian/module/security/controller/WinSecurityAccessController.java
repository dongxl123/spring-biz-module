package com.winbaoxian.module.security.controller;

import com.winbaoxian.module.security.model.common.JsonResult;
import com.winbaoxian.module.security.model.dto.*;
import com.winbaoxian.module.security.service.WinSecurityAccessService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author dongxuanliang252
 * @date 2018-11-19 14:57
 */

@RestController
@RequestMapping(value = "/api/winSecurity/v1/access/")
public class WinSecurityAccessController {

    @Resource
    private WinSecurityAccessService winSecurityAccessService;

    /**
     * /**
     *
     * @apiVersion 1.0.0
     * @api {GET} /api/winSecuity/v1/access/getLoginUserInfo 获取登录用户信息
     * @apiGroup winSecurity
     * @apiName getLoginUserInfo
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
    @GetMapping(value = "/getLoginUserInfo")
    public JsonResult<WinSecurityBaseUserDTO> getLoginUserInfo() {
        WinSecurityBaseUserDTO userDTO = winSecurityAccessService.getLoginUserInfo();
        return JsonResult.createSuccessResult(userDTO);
    }

    /**
     * /**
     *
     * @apiVersion 1.0.0
     * @api {GET} /api/winSecurity/v1/access/getLoginUserRoleList 获取登录用户角色列表
     * @apiGroup winSecurity
     * @apiName getLoginUserRoleList
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
    @GetMapping(value = "/getLoginUserRoleList")
    public JsonResult<List<WinSecurityBaseRoleDTO>> getLoginUserRoleList() {
        List<WinSecurityBaseRoleDTO> roleDTOList = winSecurityAccessService.getLoginUserRoleList();
        return JsonResult.createSuccessResult(roleDTOList);
    }

    /**
     * @apiVersion 1.0.0
     * @api {GET} /api/winSecurity/v1/access/getLoginUserResourceList 获取登录用户资源列表
     * @apiGroup winSecurity
     * @apiName getLoginUserResourceList
     * @apiSuccess (响应参数) {Number} id 主键
     * @apiSuccess (响应参数) {Number} createTime 创建时间
     * @apiSuccess (响应参数) {Number} updateTime 更新时间
     * @apiSuccess (响应参数) {String} name 名称
     * @apiSuccess (响应参数) {String} code 编码
     * @apiSuccess (响应参数) {String} globalCode 全局唯一编码
     * @apiSuccess (响应参数) {String} value 值
     * @apiSuccess (响应参数) {String} description 描述
     * @apiSuccess (响应参数) {String} icon 图标
     * @apiSuccess (响应参数) {Number} pid 父ID
     * @apiSuccess (响应参数) {Number} seq 排序
     * @apiSuccess (响应参数) {Number} status 状态
     * @apiSuccess (响应参数) {Number} resourceType 资源类别, 0:无特别作用，1:菜单，2:其他
     * @apiSuccessExample 响应示例
     * {"code":200,"msg":null,"data":[{"id":1,"createTime":1392742800000,"updateTime":1541642350000,"name":"权限管理","code":"","globalCode":"","value":"/article/create","description":"系统管理","icon":"glyphicon-folder-open ","pid":230,"seq":0,"status":0,"resourceType":0},{"id":231,"createTime":1482566027000,"updateTime":1541642350000,"name":"新建文章","code":"","globalCode":"","value":"/article/create","description":null,"icon":"glyphicon-open-file ","pid":230,"seq":0,"status":0,"resourceType":0}]}
     */
    @GetMapping(value = "/getLoginUserResourceList")
    public JsonResult<List<WinSecurityResourceDTO>> getLoginUserResourceList() {
        List<WinSecurityResourceDTO> resourceDTOList = winSecurityAccessService.getLoginUserResourceList();
        return JsonResult.createSuccessResult(resourceDTOList);
    }


    /**
     * @apiVersion 1.0.0
     * @api {GET} /api/winSecurity/v1/access/getLoginUserAllInfo 获取登录用户所有信息
     * @apiGroup winSecurity
     * @apiName getLoginUserAllInfo
     * @apiSuccess (响应参数) {Object} userInfo 用户信息
     * @apiSuccess (响应参数) {Number} userInfo.id 主键
     * @apiSuccess (响应参数) {Number} userInfo.createTime 创建时间
     * @apiSuccess (响应参数) {Number} userInfo.updateTime 更新时间
     * @apiSuccess (响应参数) {String} userInfo.userName 登录名
     * @apiSuccess (响应参数) {String} userInfo.name 姓名
     * @apiSuccess (响应参数) {String} userInfo.mobile 手机号
     * @apiSuccess (响应参数) {Number} userInfo.status 状态
     * @apiSuccess (响应参数) {Array} userInfo.roleIdList 角色ID列表
     * @apiSuccess (响应参数) {Array} roleList 角色信息
     * @apiSuccess (响应参数) {Number} roleList.id 主键
     * @apiSuccess (响应参数) {Number} roleList.createTime 创建时间
     * @apiSuccess (响应参数) {Number} roleList.updateTime 更新时间
     * @apiSuccess (响应参数) {String} roleList.name 角色名称
     * @apiSuccess (响应参数) {String} roleList.description 描述
     * @apiSuccess (响应参数) {Number} roleList.seq 排序
     * @apiSuccess (响应参数) {Number} roleList.status 状态, 0:无效 , 1:有效
     * @apiSuccess (响应参数) {Array} resourceList 资源列表
     * @apiSuccess (响应参数) {Number} resourceList.id 主键
     * @apiSuccess (响应参数) {Number} resourceList.createTime 创建时间
     * @apiSuccess (响应参数) {Number} resourceList.updateTime 更新时间
     * @apiSuccess (响应参数) {String} resourceList.name 名称
     * @apiSuccess (响应参数) {String} resourceList.code 编码
     * @apiSuccess (响应参数) {String} resourceList.globalCode 全局唯一编码
     * @apiSuccess (响应参数) {String} resourceList.value 值
     * @apiSuccess (响应参数) {String} resourceList.description 描述
     * @apiSuccess (响应参数) {String} resourceList.icon 图标
     * @apiSuccess (响应参数) {Number} resourceList.pid 父ID
     * @apiSuccess (响应参数) {Number} resourceList.seq 排序
     * @apiSuccess (响应参数) {Number} resourceList.status 状态, 0:无效 , 1:有效
     * @apiSuccess (响应参数) {Number} resourceList.resourceType 资源类别, 0:无特别作用，1:菜单，2:其他
     * @apiSuccessExample 响应示例
     * {"code":200,"msg":null,"data":{"userInfo":{"id":1,"createTime":1449378845000,"updateTime":1543312537000,"userName":"admin","name":"admin","mobile":"18707173376","status":1,"roleIdList":[1,2,7,8],"edu":null},"roleList":[{"id":1,"createTime":1541642375000,"updateTime":1541642375000,"name":"admin","description":"超级管理员","seq":0,"status":0},{"id":2,"createTime":1541642375000,"updateTime":1541642375000,"name":"de","description":"技术部经理","seq":0,"status":0}],"resourceList":[{"id":1,"createTime":1392742800000,"updateTime":1541642350000,"name":"权限管理","code":"","globalCode":"","value":"/article/create","description":"系统管理","icon":"glyphicon-folder-open ","pid":230,"seq":0,"status":0,"resourceType":0},{"id":231,"createTime":1482566027000,"updateTime":1541642350000,"name":"新建文章","code":"","globalCode":"","value":"/article/create","description":null,"icon":"glyphicon-open-file ","pid":230,"seq":0,"status":0,"resourceType":0}]}}
     */
    @GetMapping(value = "/getLoginUserAllInfo")
    public JsonResult<WinSecurityUserAllInfoDTO> getLoginUserAllInfo() {
        WinSecurityUserAllInfoDTO allInfoDTO = winSecurityAccessService.getLoginUserAllInfo();
        return JsonResult.createSuccessResult(allInfoDTO);
    }

    /**
     * @apiVersion 1.0.0
     * @api {POST} /api/winSecurity/v1/access/login 模拟登陆(非正式接口)
     * @apiGroup winSecurity
     * @apiName login
     * @apiParam (请求体) {String} userName 登陆名11111
     * @apiParam (请求体) {String} password 密码，{userName}@winbaoxian.com 的md5值（字母大写）
     * @apiParamExample 请求体示例
     * {"userName":"dd","password":"5968DE8F7018AD7C6A44932BF5917C8D"}
     */
    @PostMapping(value = "/login")
    public JsonResult login(@RequestBody WinSecurityUserTokenDTO user) {
        winSecurityAccessService.login(user.getUserName(), user.getPassword());
        return JsonResult.createSuccessResult("登录成功");
    }

    /**
     * @apiVersion 1.0.0
     * @api {POST} /api/winSecurity/v1/access/logout 模拟登出(非正式接口)
     * @apiGroup winSecurity
     * @apiName logout
     */
    @PostMapping(value = "/logout")
    public JsonResult logout() {
        winSecurityAccessService.logout();
        return JsonResult.createSuccessResult(null);
    }


}
