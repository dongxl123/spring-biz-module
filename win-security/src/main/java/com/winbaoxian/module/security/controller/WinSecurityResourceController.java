package com.winbaoxian.module.security.controller;

import com.winbaoxian.module.security.model.common.JsonResult;
import com.winbaoxian.module.security.model.dto.WinSecurityResourceDTO;
import com.winbaoxian.module.security.service.WinSecurityResourceService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/api/winSecurity/v1/")
public class WinSecurityResourceController {

    @Resource
    private WinSecurityResourceService winSecurityResourceService;

    /**
     * @apiVersion 1.0.0
     * @api {POST} /api/winSecurity/v1/addResource 新增资源
     * @apiGroup resource
     * @apiName addResource
     * @apiParam (请求体) {String} name 名称
     * @apiParam (请求体) {String} code 编码
     * @apiParam (请求体) {String} value 值
     * @apiParam (请求体) {String} description 描述
     * @apiParam (请求体) {String} icon 图标
     * @apiParam (请求体) {Number} pid 上级父ID
     * @apiParam (请求体) {Number} seq 排序
     * @apiParam (请求体) {Number} status 状态，0:无效 , 1:有效
     * @apiParam (请求体) {Number} resourceType 资源类别, 0:无特别作用，1:菜单，2:子页面，3:按钮, 4:页面自定义变量
     * @apiParamExample 请求体示例
     * {"name":"资源管理","code":"","globalCode":"","value":"/resource/manager","description":"资源管理","icon":"glyphicon-th ","pid":1,"seq":1,"status":0,"resourceType":0}
     * @apiSuccess (响应参数) {Number} id 主键
     * @apiSuccess (响应参数) {Number} createTime 创建时间
     * @apiSuccess (响应参数) {Number} updateTime 更新时间
     * @apiSuccess (响应参数) {String} name 名称
     * @apiSuccess (响应参数) {String} code 编码
     * @apiSuccess (响应参数) {String} globalCode 全局唯一编码
     * @apiSuccess (响应参数) {String} value 值
     * @apiSuccess (响应参数) {String} description 描述
     * @apiSuccess (响应参数) {String} icon 图标
     * @apiSuccess (响应参数) {Number} pid 上级父ID
     * @apiSuccess (响应参数) {Number} seq 排序
     * @apiSuccess (响应参数) {Number} status 状态，0:无效 , 1:有效
     * @apiSuccess (响应参数) {Number} resourceType 资源类别, 0:无特别作用，1:菜单，2:子页面，3:按钮, 4:页面自定义变量
     * @apiSuccessExample 响应示例
     * {"code":200,"msg":null,"data":{"id":11,"createTime":1392742800000,"updateTime":1541642350000,"name":"资源管理","code":"","globalCode":"","value":"/resource/manager","description":"资源管理","icon":"glyphicon-th ","pid":1,"seq":1,"status":0,"resourceType":0,"deleted":false}}
     */
    @PostMapping(value = "/addResource")
    public JsonResult<WinSecurityResourceDTO> addResource(@RequestBody WinSecurityResourceDTO dto) {
        WinSecurityResourceDTO resourceDTO = winSecurityResourceService.addResource(dto);
        return JsonResult.createSuccessResult(resourceDTO);
    }

    /**
     * @apiVersion 1.0.0
     * @api {POST} /api/winSecurity/v1/deleteResource 删除资源
     * @apiGroup resource
     * @apiName deleteResource
     * @apiParam (请求参数) {Number} id 主键
     * @apiParamExample 请求参数示例
     * ?id=11
     * @apiSuccessExample 响应示例
     * {"code":200,"msg":null,"data":null}
     */
    @PostMapping(value = "/deleteResource")
    public JsonResult deleteResource(Long id) {
        winSecurityResourceService.deleteResource(id);
        return JsonResult.createSuccessResult(null);
    }

    /**
     * @apiVersion 1.0.0
     * @api {POST} /api/winSecurity/v1/updateResource 更新资源
     * @apiGroup resource
     * @apiName updateResource
     * @apiParam (请求体) {String} name 名称
     * @apiParam (请求体) {String} code 编码
     * @apiParam (请求体) {String} value 值
     * @apiParam (请求体) {String} description 描述
     * @apiParam (请求体) {String} icon 图标
     * @apiParam (请求体) {Number} pid 上级父ID
     * @apiParam (请求体) {Number} seq 排序
     * @apiParam (请求体) {Number} status 状态，0:无效 , 1:有效
     * @apiParam (请求体) {Number} resourceType 资源类别, 0:无特别作用，1:菜单，2:子页面，3:按钮, 4:页面自定义变量
     * @apiParamExample 请求体示例
     * {"id":11,"name":"资源管理","code":"","globalCode":"","value":"/resource/manager","description":"资源管理","icon":"glyphicon-th ","pid":1,"seq":1,"status":0,"resourceType":0}
     * @apiSuccess (响应参数) {Number} id 主键
     * @apiSuccess (响应参数) {Number} createTime 创建时间
     * @apiSuccess (响应参数) {Number} updateTime 更新时间
     * @apiSuccess (响应参数) {String} name 名称
     * @apiSuccess (响应参数) {String} code 编码
     * @apiSuccess (响应参数) {String} globalCode 全局唯一编码
     * @apiSuccess (响应参数) {String} value 值
     * @apiSuccess (响应参数) {String} description 描述
     * @apiSuccess (响应参数) {String} icon 图标
     * @apiSuccess (响应参数) {Number} pid 上级父ID
     * @apiSuccess (响应参数) {Number} seq 排序
     * @apiSuccess (响应参数) {Number} status 状态，0:无效 , 1:有效
     * @apiSuccess (响应参数) {Number} resourceType 资源类别, 0:无特别作用，1:菜单，2:子页面，3:按钮, 4:页面自定义变量
     * @apiSuccessExample 响应示例
     * {"code":200,"msg":null,"data":{"id":11,"createTime":1392742800000,"updateTime":1541642350000,"name":"资源管理","code":"","globalCode":"","value":"/resource/manager","description":"资源管理","icon":"glyphicon-th ","pid":1,"seq":1,"status":0,"resourceType":0,"deleted":false}}
     */
    @PostMapping(value = "/updateResource")
    public JsonResult<WinSecurityResourceDTO> updateResource(@RequestBody WinSecurityResourceDTO dto) {
        WinSecurityResourceDTO resourceDTO = winSecurityResourceService.updateResource(dto);
        return JsonResult.createSuccessResult(resourceDTO);
    }

    /**
     * @apiVersion 1.0.0
     * @api {GET} /api/winSecurity/v1/getResource 获取资源
     * @apiGroup resource
     * @apiName getResource
     * @apiParam (请求参数) {Number} id 主键
     * @apiParamExample 请求参数示例
     * ?id=11
     * @apiSuccess (响应参数) {Number} id 主键
     * @apiSuccess (响应参数) {Number} createTime 创建时间
     * @apiSuccess (响应参数) {Number} updateTime 更新时间
     * @apiSuccess (响应参数) {String} name 名称
     * @apiSuccess (响应参数) {String} code 编码
     * @apiSuccess (响应参数) {String} globalCode 全局唯一编码
     * @apiSuccess (响应参数) {String} value 值
     * @apiSuccess (响应参数) {String} description 描述
     * @apiSuccess (响应参数) {String} icon 图标
     * @apiSuccess (响应参数) {Number} pid 上级父ID
     * @apiSuccess (响应参数) {Number} seq 排序
     * @apiSuccess (响应参数) {Number} status 状态，0:无效 , 1:有效
     * @apiSuccess (响应参数) {Number} resourceType 资源类别, 0:无特别作用，1:菜单，2:子页面，3:按钮, 4:页面自定义变量
     * @apiSuccessExample 响应示例
     * {"code":200,"msg":null,"data":{"id":11,"createTime":1392742800000,"updateTime":1541642350000,"name":"资源管理","code":"","globalCode":"","value":"/resource/manager","description":"资源管理","icon":"glyphicon-th ","pid":1,"seq":1,"status":0,"resourceType":0,"deleted":false}}
     */
    @GetMapping(value = "/getResource")
    public JsonResult<WinSecurityResourceDTO> getResource(Long id) {
        WinSecurityResourceDTO resourceDTO = winSecurityResourceService.getResource(id);
        return JsonResult.createSuccessResult(resourceDTO);
    }

    /**
     * @apiVersion 1.0.0
     * @api {GET} /api/winSecurity/v1/getResourceList 获取资源列表
     * @apiGroup resource
     * @apiName getResourceList
     * @apiSuccess (响应参数) {Number} id 主键
     * @apiSuccess (响应参数) {Number} createTime 创建时间
     * @apiSuccess (响应参数) {Number} updateTime 更新时间
     * @apiSuccess (响应参数) {String} name 名称
     * @apiSuccess (响应参数) {String} code 编码
     * @apiSuccess (响应参数) {String} globalCode 全局唯一编码
     * @apiSuccess (响应参数) {String} value 值
     * @apiSuccess (响应参数) {String} description 描述
     * @apiSuccess (响应参数) {String} icon 图标
     * @apiSuccess (响应参数) {Number} pid 上级父ID
     * @apiSuccess (响应参数) {Number} seq 排序
     * @apiSuccess (响应参数) {Number} status 状态，0:无效 , 1:有效
     * @apiSuccess (响应参数) {Number} resourceType 资源类别, 0:无特别作用，1:菜单，2:子页面，3:按钮, 4:页面自定义变量
     * @apiSuccessExample 响应示例
     * {"code":200,"msg":null,"data":[{"id":1,"createTime":1392742800000,"updateTime":1541642350000,"name":"权限管理","code":"","globalCode":"","value":"/resource/treeGrid","description":"系统管理","icon":"glyphicon-folder-open ","pid":11,"seq":1,"status":0,"resourceType":1,"deleted":false},{"id":111,"createTime":1392742800000,"updateTime":1541642350000,"name":"列表","code":"","globalCode":"","value":"/resource/treeGrid","description":"资源列表","icon":"glyphicon-list ","pid":11,"seq":0,"status":0,"resourceType":1,"deleted":false},{"id":112,"createTime":1392742800000,"updateTime":1541642350000,"name":"添加","code":"","globalCode":"","value":"/resource/add","description":"资源添加","icon":"glyphicon-plus icon-green","pid":11,"seq":0,"status":0,"resourceType":1,"deleted":false},{"id":11,"createTime":1392742800000,"updateTime":1541642350000,"name":"资源管理","code":"","globalCode":"","value":"/resource/manager","description":"资源管理","icon":"glyphicon-th ","pid":1,"seq":1,"status":0,"resourceType":0,"deleted":false}]}
     */
    @GetMapping(value = "/getResourceList")
    public JsonResult<List<WinSecurityResourceDTO>> getResourceList() {
        List<WinSecurityResourceDTO> resourceList = winSecurityResourceService.getResourceList();
        return JsonResult.createSuccessResult(resourceList);
    }

}
