package com.winbaoxian.module.controller;

import com.winbaoxian.module.model.common.JsonResult;
import com.winbaoxian.module.model.common.Pagination;
import com.winbaoxian.module.model.common.PaginationDTO;
import com.winbaoxian.module.model.dto.ResourceDTO;
import com.winbaoxian.module.service.ResourceService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/api/winSecurity/v1/")
public class ResourceController {

    @Resource
    private ResourceService resourceService;

    /**
     * @apiVersion 1.0.0
     * @api {POST} /api/winSecurity/v1/resource 新增资源
     * @apiGroup resource
     * @apiName addResource
     * @apiParam (请求体) {String} name 名称
     * @apiParam (请求体) {String} code 编码
     * @apiParam (请求体) {String} value 值
     * @apiParam (请求体) {String} description 描述
     * @apiParam (请求体) {String} icon 图标
     * @apiParam (请求体) {Number} pid 上级父ID
     * @apiParam (请求体) {Number} seq 排序
     * @apiParam (请求体) {Number} status 状态，0：失效  1：有效
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
     * @apiSuccess (响应参数) {Number} status 状态，0：失效  1：有效
     * @apiSuccess (响应参数) {Number} resourceType 资源类别, 0:无特别作用，1:菜单，2:子页面，3:按钮, 4:页面自定义变量
     * @apiSuccessExample 响应示例
     * {"code":200,"msg":null,"data":{"id":11,"createTime":1392742800000,"updateTime":1541642350000,"name":"资源管理","code":"","globalCode":"","value":"/resource/manager","description":"资源管理","icon":"glyphicon-th ","pid":1,"seq":1,"status":0,"resourceType":0,"deleted":false}}
     */
    @PostMapping(value = "/resource")
    public JsonResult<ResourceDTO> addResource(@RequestBody ResourceDTO dto) {
        ResourceDTO resourceDTO = resourceService.addResource(dto);
        return JsonResult.createSuccessResult(resourceDTO);
    }

    /**
     * @apiVersion 1.0.0
     * @api {DELETE} /api/winSecurity/v1/resource/{id} 删除资源
     * @apiGroup resource
     * @apiName deleteResource
     * @apiSuccessExample 响应示例
     * {"code":200,"msg":null,"data":null}
     */
    @DeleteMapping(value = "/resource/{id}")
    public JsonResult deleteResource(@PathVariable("id") Long id) {
        resourceService.deleteResource(id);
        return JsonResult.createSuccessResult(null);
    }

    /**
     * @apiVersion 1.0.0
     * @api {PUT} /api/winSecurity/v1/resource/{id} 更新资源
     * @apiGroup resource
     * @apiName updateResource
     * @apiParam (请求体) {String} name 名称
     * @apiParam (请求体) {String} code 编码
     * @apiParam (请求体) {String} value 值
     * @apiParam (请求体) {String} description 描述
     * @apiParam (请求体) {String} icon 图标
     * @apiParam (请求体) {Number} pid 上级父ID
     * @apiParam (请求体) {Number} seq 排序
     * @apiParam (请求体) {Number} status 状态，0：失效  1：有效
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
     * @apiSuccess (响应参数) {Number} status 状态，0：失效  1：有效
     * @apiSuccess (响应参数) {Number} resourceType 资源类别, 0:无特别作用，1:菜单，2:子页面，3:按钮, 4:页面自定义变量
     * @apiSuccessExample 响应示例
     * {"code":200,"msg":null,"data":{"id":11,"createTime":1392742800000,"updateTime":1541642350000,"name":"资源管理","code":"","globalCode":"","value":"/resource/manager","description":"资源管理","icon":"glyphicon-th ","pid":1,"seq":1,"status":0,"resourceType":0,"deleted":false}}
     */
    @PutMapping(value = "/resource/{id}")
    public JsonResult<ResourceDTO> updateResource(@PathVariable("id") Long id, @RequestBody ResourceDTO dto) {
        ResourceDTO resourceDTO = resourceService.updateResource(id, dto);
        return JsonResult.createSuccessResult(resourceDTO);
    }

    /**
     * @apiVersion 1.0.0
     * @api {GET} /api/winSecurity/v1/resource/{id} 获取资源
     * @apiGroup resource
     * @apiName getResource
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
     * @apiSuccess (响应参数) {Number} status 状态，0：失效  1：有效
     * @apiSuccess (响应参数) {Number} resourceType 资源类别, 0:无特别作用，1:菜单，2:子页面，3:按钮, 4:页面自定义变量
     * @apiSuccessExample 响应示例
     * {"code":200,"msg":null,"data":{"id":11,"createTime":1392742800000,"updateTime":1541642350000,"name":"资源管理","code":"","globalCode":"","value":"/resource/manager","description":"资源管理","icon":"glyphicon-th ","pid":1,"seq":1,"status":0,"resourceType":0,"deleted":false}}
     */
    @GetMapping(value = "/resource/{id}")
    public JsonResult<ResourceDTO> getResource(@PathVariable("id") Long id) {
        ResourceDTO resourceDTO = resourceService.getResource(id);
        return JsonResult.createSuccessResult(resourceDTO);
    }

    /**
     * @apiVersion 1.0.0
     * @api {GET} /api/winSecurity/v1/resource 获取资源列表
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
     * @apiSuccess (响应参数) {Number} status 状态，0：失效  1：有效
     * @apiSuccess (响应参数) {Number} resourceType 资源类别, 0:无特别作用，1:菜单，2:子页面，3:按钮, 4:页面自定义变量
     * @apiSuccessExample 响应示例
     * {"code":200,"msg":null,"data":[{"id":1,"createTime":1392742800000,"updateTime":1541642350000,"name":"权限管理","code":"","globalCode":"","value":"/resource/treeGrid","description":"系统管理","icon":"glyphicon-folder-open ","pid":11,"seq":1,"status":0,"resourceType":1,"deleted":false},{"id":111,"createTime":1392742800000,"updateTime":1541642350000,"name":"列表","code":"","globalCode":"","value":"/resource/treeGrid","description":"资源列表","icon":"glyphicon-list ","pid":11,"seq":0,"status":0,"resourceType":1,"deleted":false},{"id":112,"createTime":1392742800000,"updateTime":1541642350000,"name":"添加","code":"","globalCode":"","value":"/resource/add","description":"资源添加","icon":"glyphicon-plus icon-green","pid":11,"seq":0,"status":0,"resourceType":1,"deleted":false},{"id":11,"createTime":1392742800000,"updateTime":1541642350000,"name":"资源管理","code":"","globalCode":"","value":"/resource/manager","description":"资源管理","icon":"glyphicon-th ","pid":1,"seq":1,"status":0,"resourceType":0,"deleted":false}]}
     */
    @GetMapping(value = "/resource")
    public JsonResult<List<ResourceDTO>> getResourceList() {
        List<ResourceDTO> resourceList = resourceService.getResourceList();
        return JsonResult.createSuccessResult(resourceList);
    }

    /**
     * @apiVersion 1.0.0
     * @api {GET} /api/winSecurity/v1/resource/p 获取资源分页数据
     * @apiGroup resource
     * @apiName getResourceP
     * @apiParam (请求参数) {Number} pageNum 第几页
     * @apiParam (请求参数) {Number} pageSize 每页数量
     * @apiParamExample 请求参数示例
     * ?pageNum=1&pageSize=2
     * @apiSuccess (响应参数) {Number} pageNum 第几页
     * @apiSuccess (响应参数) {Number} pageSize 每页数量
     * @apiSuccess (响应参数) {Number} totalRow 总数据量
     * @apiSuccess (响应参数) {Number} totalPage 总页数
     * @apiSuccess (响应参数) {Array} list 列表
     * @apiSuccess (响应参数) {Number} list.id 主键
     * @apiSuccess (响应参数) {Number} list.createTime 创建时间
     * @apiSuccess (响应参数) {Number} list.updateTime 更新时间
     * @apiSuccess (响应参数) {String} list.name 名称
     * @apiSuccess (响应参数) {String} list.code 编码
     * @apiSuccess (响应参数) {String} list.globalCode 全局唯一编码
     * @apiSuccess (响应参数) {String} list.value 值
     * @apiSuccess (响应参数) {String} list.description 描述
     * @apiSuccess (响应参数) {String} list.icon 图标
     * @apiSuccess (响应参数) {Number} list.pid 上级父ID
     * @apiSuccess (响应参数) {Number} list.seq 排序
     * @apiSuccess (响应参数) {Number} list.status 状态，0：失效  1：有效
     * @apiSuccess (响应参数) {Number} list.resourceType 资源类别, 0:无特别作用，1:菜单，2:子页面，3:按钮, 4:页面自定义变量
     * @apiSuccess (响应参数) {Number} startRow 本页起始行
     * @apiSuccessExample 响应示例
     * {"code":200,"msg":null,"data":{"pageNum":1,"pageSize":2,"totalRow":32,"totalPage":16,"orderProperty":null,"orderDirection":null,"list":[{"id":1,"createTime":1392742800000,"updateTime":1541642350000,"name":"权限管理","code":"","globalCode":"","value":"/resource/treeGrid","description":"系统管理","icon":"glyphicon-folder-open ","pid":11,"seq":0,"status":0,"resourceType":1,"deleted":false},{"id":111,"createTime":1392742800000,"updateTime":1541642350000,"name":"列表","code":"","globalCode":"","value":"/resource/treeGrid","description":"资源列表","icon":"glyphicon-list ","pid":11,"seq":0,"status":0,"resourceType":1,"deleted":false}],"startRow":0}}
     */
    @GetMapping(value = "/resource/p")
    public JsonResult<PaginationDTO<ResourceDTO>> getResourcePage(Pagination pagination) {
        PaginationDTO<ResourceDTO> paginationDTO = resourceService.getResourcePage(pagination);
        return JsonResult.createSuccessResult(paginationDTO);
    }

}
