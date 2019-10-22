package com.winbaoxian.module.security.repository;

import com.winbaoxian.module.security.model.entity.WinSecurityRoleResourceEntity;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.data.jpa.repository.JpaRepository;

import java.nio.file.attribute.UserPrincipal;
import java.util.List;

public interface WinSecurityRoleResourceRepository extends JpaRepository<WinSecurityRoleResourceEntity, Long> {

    void deleteByRoleId(Long roleId);

    /**
     * @service WinSecurityRoleResourceRepository List<WinSecurityRoleResourceEntity> findByRoleId(Long roleId, User a, String m)
     * @serviceVersion 1.0.0
     * @serviceGroup WinSecurityRoleResourceRepository
     * @serviceName findByRoleId
     * @serviceParam (roleId) {Long} roleId
     * @serviceParamExample roleId
     * 7051
     * @serviceParam (a) {User} a
     * @serviceParam (a) {String} a.name
     * @serviceParam (a) {String} a.password
     * @serviceParam (a) {List<String>} a.role
     * @serviceParam (a) {boolean} a.defaultPassword
     * @serviceParamExample a
     * {"password":"QS4brin","role":["32SSsVEujk"],"name":"D","defaultPassword":true}
     * @serviceParam (m) {String} m
     * @serviceParamExample m
     * "90P"
     * @serviceResult (result) {List<WinSecurityRoleResourceEntity>} result
     * @serviceResult (result) {Long} result.id 主键id
     * @serviceResult (result) {Long} result.roleId 角色id
     * @serviceResult (result) {Long} result.resourceId 资源id
     * @serviceResultExample result
     * [{"resourceId":6585,"roleId":6779,"id":7807}]
     */
    List<WinSecurityRoleResourceEntity> findByRoleId(Long roleId, SecurityProperties.User a,String m);
}
