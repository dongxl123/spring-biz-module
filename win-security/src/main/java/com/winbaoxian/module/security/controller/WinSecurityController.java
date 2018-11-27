package com.winbaoxian.module.security.controller;

import com.winbaoxian.module.security.model.common.JsonResult;
import com.winbaoxian.module.security.model.dto.WinSecurityResourceDTO;
import com.winbaoxian.module.security.model.dto.WinSecurityUserTokenDTO;
import com.winbaoxian.module.security.model.exceptions.WinSecurityException;
import com.winbaoxian.module.security.service.WinSecurityService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author dongxuanliang252
 * @date 2018-11-19 14:57
 */

@RestController
@RequestMapping(value = "/api/winSecurity/v1/")
public class WinSecurityController {

    @Resource
    private WinSecurityService winSecurityService;

    /**
     * @apiVersion 1.0.0
     * @api {GET} /api/winSecurity/v1/getUserResourceList 获取登录用户资源
     * @apiGroup winSecurity
     * @apiName getUserResourceList
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
     * @apiSuccess (响应参数) {Number} resourceType 资源类别, 0:无特别作用，1:菜单，2:子页面，3:按钮, 4:页面自定义变量
     * @apiSuccessExample 响应示例
     * {"code":200,"msg":null,"data":[{"id":1,"createTime":1392742800000,"updateTime":1541642350000,"name":"权限管理","code":"","globalCode":"","value":"/article/create","description":"系统管理","icon":"glyphicon-folder-open ","pid":230,"seq":0,"status":0,"resourceType":0},{"id":231,"createTime":1482566027000,"updateTime":1541642350000,"name":"新建文章","code":"","globalCode":"","value":"/article/create","description":null,"icon":"glyphicon-open-file ","pid":230,"seq":0,"status":0,"resourceType":0}]}
     */
    @GetMapping(value = "/getUserResourceList")
    public JsonResult<List<WinSecurityResourceDTO>> getUserResourceList() {
        String userName = winSecurityService.getLoginUserName();
        List<WinSecurityResourceDTO> resourceDTOList = winSecurityService.getUserResourceList(userName);
        return JsonResult.createSuccessResult(resourceDTOList);
    }

    /**
     * @apiVersion 1.0.0
     * @api {POST} /api/winSecurity/v1/login 模拟登陆
     * @apiGroup winSecurity
     * @apiName login
     * @apiParam (请求体) {String} userName 登陆名
     * @apiParam (请求体) {String} password 密码，{userName}@winbaoxian.com 的md5值（字母大写）
     * @apiParamExample 请求体示例
     * {"userName":"dd","password":"5968DE8F7018AD7C6A44932BF5917C8D"}
     */
    @PostMapping(value = "/login")
    public JsonResult login(@RequestBody WinSecurityUserTokenDTO user) {
        winSecurityService.login(user.getUserName(), user.getPassword());
        return JsonResult.createSuccessResult("登录成功");
    }

    /**
     * @apiVersion 1.0.0
     * @api {POST} /api/winSecurity/v1/logout 模拟登出
     * @apiGroup winSecurity
     * @apiName logout
     */
    @PostMapping(value = "/logout")
    public JsonResult logout() {
        winSecurityService.logout();
        return JsonResult.createSuccessResult(null);
    }


}
