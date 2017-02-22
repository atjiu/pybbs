package cn.tomoya.common;

import cn.tomoya.module.section.entity.Section;
import cn.tomoya.module.section.service.SectionService;
import cn.tomoya.module.security.entity.Permission;
import cn.tomoya.module.security.entity.Role;
import cn.tomoya.module.security.service.PermissionService;
import cn.tomoya.module.security.service.RoleService;
import cn.tomoya.module.setting.entity.Setting;
import cn.tomoya.module.setting.service.SettingService;
import cn.tomoya.module.user.entity.User;
import cn.tomoya.module.user.service.UserService;
import cn.tomoya.util.LocaleMessageSourceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@Component
@Transactional
public class InitDB {

    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private SettingService settingService;
    @Autowired
    private SectionService sectionService;
    @Autowired
    private LocaleMessageSourceUtil localeMessageSourceUtil;

    public void init() {
        Setting setting = settingService.findByName("init");
        if(setting == null || setting.getValue().equals("0")) {
            insert();
        }
    }

    public void insert() {
        Permission dashboardPermission = new Permission();
        dashboardPermission.setDescription(localeMessageSourceUtil.getMessage("db.permission.dashboard"));
        dashboardPermission.setPid(0);
        dashboardPermission.setName("index");

        Permission sectionPermission = new Permission();
        sectionPermission.setDescription(localeMessageSourceUtil.getMessage("db.permission.section"));
        sectionPermission.setPid(0);
        sectionPermission.setName("section");

        Permission topicPermission = new Permission();
        topicPermission.setDescription(localeMessageSourceUtil.getMessage("db.permission.topic"));
        topicPermission.setPid(0);
        topicPermission.setName("topic");

        Permission commentPermission = new Permission();
        commentPermission.setDescription(localeMessageSourceUtil.getMessage("db.permission.comment"));
        commentPermission.setPid(0);
        commentPermission.setName("reply");

        Permission userPermission = new Permission();
        userPermission.setDescription(localeMessageSourceUtil.getMessage("db.permission.user"));
        userPermission.setPid(0);
        userPermission.setName("user");

        Permission rolePermission = new Permission();
        rolePermission.setDescription(localeMessageSourceUtil.getMessage("db.permission.role"));
        rolePermission.setPid(0);
        rolePermission.setName("role");

        Permission permissionPermission = new Permission();
        permissionPermission.setDescription(localeMessageSourceUtil.getMessage("db.permission.permission"));
        permissionPermission.setPid(0);
        permissionPermission.setName("permission");

        Permission indexedPermission = new Permission();
        indexedPermission.setDescription(localeMessageSourceUtil.getMessage("db.permission.indexed"));
        indexedPermission.setPid(0);
        indexedPermission.setName("indexed");

        Permission settingPermission = new Permission();
        settingPermission.setDescription(localeMessageSourceUtil.getMessage("db.permission.setting"));
        settingPermission.setPid(0);
        settingPermission.setName("setting");

        permissionService.save(dashboardPermission);
        permissionService.save(sectionPermission);
        permissionService.save(topicPermission);
        permissionService.save(commentPermission);
        permissionService.save(userPermission);
        permissionService.save(rolePermission);
        permissionService.save(permissionPermission);
        permissionService.save(indexedPermission);
        permissionService.save(settingPermission);

        Permission adminIndexPermission = new Permission();
        adminIndexPermission.setDescription(localeMessageSourceUtil.getMessage("db.permission.adminIndex"));
        adminIndexPermission.setPid(dashboardPermission.getId());
        adminIndexPermission.setName("admin:index");
        adminIndexPermission.setUrl("/admin/index");

        Permission sectionsPermission = new Permission();
        sectionsPermission.setDescription(localeMessageSourceUtil.getMessage("db.permission.sections"));
        sectionsPermission.setPid(dashboardPermission.getId());
        sectionsPermission.setName("section:list");
        sectionsPermission.setUrl("/admin/section/list");

        Permission addSectionPermission = new Permission();
        addSectionPermission.setDescription(localeMessageSourceUtil.getMessage("db.permission.addSection"));
        addSectionPermission.setPid(dashboardPermission.getId());
        addSectionPermission.setName("section:add");
        addSectionPermission.setUrl("/admin/section/add");

        Permission editSectionPermission = new Permission();
        editSectionPermission.setDescription(localeMessageSourceUtil.getMessage("db.permission.editSection"));
        editSectionPermission.setPid(dashboardPermission.getId());
        editSectionPermission.setName("section:edit");
        editSectionPermission.setUrl("/admin/section/*/edit");

        Permission deleteSectionPermission = new Permission();
        deleteSectionPermission.setDescription(localeMessageSourceUtil.getMessage("db.permission.deleteSection"));
        deleteSectionPermission.setPid(dashboardPermission.getId());
        deleteSectionPermission.setName("section:delete");
        deleteSectionPermission.setUrl("/admin/section/*/delete");

        Permission editTopicPermission = new Permission();
        editTopicPermission.setDescription(localeMessageSourceUtil.getMessage("db.permission.editTopic"));
        editTopicPermission.setPid(topicPermission.getId());
        editTopicPermission.setName("topic:edit");
        editTopicPermission.setUrl("/topic/*/edit");

        Permission deleteTopicPermission = new Permission();
        deleteTopicPermission.setDescription(localeMessageSourceUtil.getMessage("db.permission.deleteTopic"));
        deleteTopicPermission.setPid(topicPermission.getId());
        deleteTopicPermission.setName("topic:delete");
        deleteTopicPermission.setUrl("/topic/*/delete");

        Permission setTopicTopPermission = new Permission();
        setTopicTopPermission.setDescription(localeMessageSourceUtil.getMessage("db.permission.setTopicTop"));
        setTopicTopPermission.setPid(topicPermission.getId());
        setTopicTopPermission.setName("topic:top");
        setTopicTopPermission.setUrl("/topic/*/top");

        Permission setTopicGoodPermission = new Permission();
        setTopicGoodPermission.setDescription(localeMessageSourceUtil.getMessage("db.permission.setTopicGood"));
        setTopicGoodPermission.setPid(topicPermission.getId());
        setTopicGoodPermission.setName("topic:good");
        setTopicGoodPermission.setUrl("/topic/*/good");

        Permission editCommentPermission = new Permission();
        editCommentPermission.setDescription(localeMessageSourceUtil.getMessage("db.permission.editComment"));
        editCommentPermission.setPid(commentPermission.getId());
        editCommentPermission.setName("reply:edit");
        editCommentPermission.setUrl("/reply/*/edit");

        Permission deleteCommentPermission = new Permission();
        deleteCommentPermission.setDescription(localeMessageSourceUtil.getMessage("db.permission.deleteComment"));
        deleteCommentPermission.setPid(commentPermission.getId());
        deleteCommentPermission.setName("reply:delete");
        deleteCommentPermission.setUrl("/reply/*/delete");

        Permission commentsPermission = new Permission();
        commentsPermission.setDescription(localeMessageSourceUtil.getMessage("db.permission.comments"));
        commentsPermission.setPid(commentPermission.getId());
        commentsPermission.setName("reply:list");
        commentsPermission.setUrl("/admin/reply/list");

        Permission usersPermission = new Permission();
        usersPermission.setDescription(localeMessageSourceUtil.getMessage("db.permission.users"));
        usersPermission.setPid(userPermission.getId());
        usersPermission.setName("user:list");
        usersPermission.setUrl("/admin/user/list");

        Permission userRolesPermission = new Permission();
        userRolesPermission.setDescription(localeMessageSourceUtil.getMessage("db.permission.userRoles"));
        userRolesPermission.setPid(userPermission.getId());
        userRolesPermission.setName("user:role");
        userRolesPermission.setUrl("/admin/user/*/role");

        Permission deleteUserPermission = new Permission();
        deleteUserPermission.setDescription(localeMessageSourceUtil.getMessage("db.permission.deleteUser"));
        deleteUserPermission.setPid(userPermission.getId());
        deleteUserPermission.setName("user:delete");
        deleteUserPermission.setUrl("/admin/user/*/delete");

        Permission blockUserPermission = new Permission();
        blockUserPermission.setDescription(localeMessageSourceUtil.getMessage("db.permission.blockUser"));
        blockUserPermission.setPid(userPermission.getId());
        blockUserPermission.setName("user:block");
        blockUserPermission.setUrl("/admin/user/*/block");

        Permission unblockUserPermission = new Permission();
        unblockUserPermission.setDescription(localeMessageSourceUtil.getMessage("db.permission.unblockUser"));
        unblockUserPermission.setPid(userPermission.getId());
        unblockUserPermission.setName("user:unblock");
        unblockUserPermission.setUrl("/admin/user/*/unblock");

        Permission permissionsPermission = new Permission();
        permissionsPermission.setDescription(localeMessageSourceUtil.getMessage("db.permission.permissions"));
        permissionsPermission.setPid(permissionPermission.getId());
        permissionsPermission.setName("permission:list");
        permissionsPermission.setUrl("/admin/permission/list");

        Permission addPermissionPermission = new Permission();
        addPermissionPermission.setDescription(localeMessageSourceUtil.getMessage("db.permission.addPermission"));
        addPermissionPermission.setPid(permissionPermission.getId());
        addPermissionPermission.setName("permission:add");
        addPermissionPermission.setUrl("/admin/permission/add");

        Permission editPermissionPermission = new Permission();
        editPermissionPermission.setDescription(localeMessageSourceUtil.getMessage("db.permission.editPermission"));
        editPermissionPermission.setPid(permissionPermission.getId());
        editPermissionPermission.setName("permission:edit");
        editPermissionPermission.setUrl("/admin/permission/*/edit");

        Permission deletePermissionPermission = new Permission();
        deletePermissionPermission.setDescription(localeMessageSourceUtil.getMessage("db.permission.deletePermission"));
        deletePermissionPermission.setPid(permissionPermission.getId());
        deletePermissionPermission.setName("permission:delete");
        deletePermissionPermission.setUrl("/admin/permission/*/delete");

        Permission rolesPermission = new Permission();
        rolesPermission.setDescription(localeMessageSourceUtil.getMessage("db.permission.roles"));
        rolesPermission.setPid(rolePermission.getId());
        rolesPermission.setName("role:list");
        rolesPermission.setUrl("/admin/role/list");

        Permission addRolePermission = new Permission();
        addRolePermission.setDescription(localeMessageSourceUtil.getMessage("db.permission.addRole"));
        addRolePermission.setPid(rolePermission.getId());
        addRolePermission.setName("role:add");
        addRolePermission.setUrl("/admin/role/add");

        Permission editRolePermission = new Permission();
        editRolePermission.setDescription(localeMessageSourceUtil.getMessage("db.permission.editRole"));
        editRolePermission.setPid(rolePermission.getId());
        editRolePermission.setName("role:edit");
        editRolePermission.setUrl("/admin/role/*/edit");

        Permission deleteRolePermission = new Permission();
        deleteRolePermission.setDescription(localeMessageSourceUtil.getMessage("db.permission.deleteRole"));
        deleteRolePermission.setPid(rolePermission.getId());
        deleteRolePermission.setName("role:delete");
        deleteRolePermission.setUrl("/admin/role/*/delete");

        Permission indexedAllTopicsPermission = new Permission();
        indexedAllTopicsPermission.setDescription(localeMessageSourceUtil.getMessage("db.permission.indexedAllTopics"));
        indexedAllTopicsPermission.setPid(indexedPermission.getId());
        indexedAllTopicsPermission.setName("index:add");
        indexedAllTopicsPermission.setUrl("/admin/indexed/indexAll");

        Permission deleteAllIndexedPermission = new Permission();
        deleteAllIndexedPermission.setDescription(localeMessageSourceUtil.getMessage("db.permission.deleteAllIndexed"));
        deleteAllIndexedPermission.setPid(indexedPermission.getId());
        deleteAllIndexedPermission.setName("index:deleteAll");
        deleteAllIndexedPermission.setUrl("/admin/indexed/deleteAll");

        Permission settingDetailPermission = new Permission();
        settingDetailPermission.setDescription(localeMessageSourceUtil.getMessage("db.permission.settingDetail"));
        settingDetailPermission.setPid(settingPermission.getId());
        settingDetailPermission.setName("setting:detail");
        settingDetailPermission.setUrl("/admin/setting/detail");

        Permission editSettingPermission = new Permission();
        editSettingPermission.setDescription(localeMessageSourceUtil.getMessage("db.permission.updateSetting"));
        editSettingPermission.setPid(settingPermission.getId());
        editSettingPermission.setName("setting:update");
        editSettingPermission.setUrl("/admin/setting/update");

        permissionService.save(adminIndexPermission);
        permissionService.save(sectionsPermission);
        permissionService.save(addSectionPermission);
        permissionService.save(editSectionPermission);
        permissionService.save(deleteSectionPermission);
        permissionService.save(editTopicPermission);
        permissionService.save(deleteTopicPermission);
        permissionService.save(setTopicTopPermission);
        permissionService.save(setTopicGoodPermission);
        permissionService.save(editCommentPermission);
        permissionService.save(deleteCommentPermission);
        permissionService.save(commentsPermission);
        permissionService.save(usersPermission);
        permissionService.save(userRolesPermission);
        permissionService.save(deleteUserPermission);
        permissionService.save(blockUserPermission);
        permissionService.save(unblockUserPermission);
        permissionService.save(permissionsPermission);
        permissionService.save(addPermissionPermission);
        permissionService.save(editPermissionPermission);
        permissionService.save(deletePermissionPermission);
        permissionService.save(rolesPermission);
        permissionService.save(addRolePermission);
        permissionService.save(editRolePermission);
        permissionService.save(deleteRolePermission);
        permissionService.save(indexedAllTopicsPermission);
        permissionService.save(deleteAllIndexedPermission);
        permissionService.save(settingDetailPermission);
        permissionService.save(editSettingPermission);

        Role adminRole = new Role();
        adminRole.setName("admin");
        adminRole.setDescription(localeMessageSourceUtil.getMessage("db.role.administrator"));

        Role moderatorRole = new Role();
        moderatorRole.setName("moderator");
        moderatorRole.setDescription(localeMessageSourceUtil.getMessage("db.role.moderator"));

        Role normalUserRole = new Role();
        normalUserRole.setName("user");
        normalUserRole.setDescription(localeMessageSourceUtil.getMessage("db.role.normalUser"));

        // role add permission
        Set<Permission> adminRolePermissions = new HashSet<>();
        adminRolePermissions.add(adminIndexPermission);
        adminRolePermissions.add(sectionsPermission);
        adminRolePermissions.add(addSectionPermission);
        adminRolePermissions.add(editSectionPermission);
        adminRolePermissions.add(deleteSectionPermission);
        adminRolePermissions.add(editTopicPermission);
        adminRolePermissions.add(deleteTopicPermission);
        adminRolePermissions.add(editCommentPermission);
        adminRolePermissions.add(deleteCommentPermission);
        adminRolePermissions.add(commentsPermission);
        adminRolePermissions.add(usersPermission);
        adminRolePermissions.add(userRolesPermission);
        adminRolePermissions.add(deleteUserPermission);
        adminRolePermissions.add(blockUserPermission);
        adminRolePermissions.add(unblockUserPermission);
        adminRolePermissions.add(permissionsPermission);
        adminRolePermissions.add(addPermissionPermission);
        adminRolePermissions.add(editPermissionPermission);
        adminRolePermissions.add(deletePermissionPermission);
        adminRolePermissions.add(rolesPermission);
        adminRolePermissions.add(addRolePermission);
        adminRolePermissions.add(editRolePermission);
        adminRolePermissions.add(deleteRolePermission);
        adminRolePermissions.add(indexedAllTopicsPermission);
        adminRolePermissions.add(deleteAllIndexedPermission);
        adminRolePermissions.add(setTopicTopPermission);
        adminRolePermissions.add(setTopicGoodPermission);
        adminRolePermissions.add(settingDetailPermission);
        adminRolePermissions.add(editSettingPermission);
        adminRole.setPermissions(adminRolePermissions);

        Set<Permission> moderatorRolePermissions = new HashSet<>();
        moderatorRolePermissions.add(adminIndexPermission);
        moderatorRolePermissions.add(editTopicPermission);
        moderatorRolePermissions.add(deleteTopicPermission);
        moderatorRolePermissions.add(editCommentPermission);
        moderatorRolePermissions.add(deleteCommentPermission);
        moderatorRolePermissions.add(commentsPermission);
        moderatorRole.setPermissions(moderatorRolePermissions);

        roleService.save(adminRole);
        roleService.save(moderatorRole);
        roleService.save(normalUserRole);

        User defaultUser = new User();
        defaultUser.setEmail("default_user@example.com");
        defaultUser.setSignature("hello world");
        defaultUser.setUsername("tomoya");
        defaultUser.setPassword("$2a$10$KkUG107R3ASTHfAHei.bweXWXgCa4cE1KhK.F0odzfE0r97aeeTXC");
        defaultUser.setInTime(new Date());
        defaultUser.setBlock(false);
        defaultUser.setAvatar("http://localhost:8080/static/images/avatar/default.png");

        Set<Role> defaultUserRole = new HashSet<>();
        defaultUserRole.add(adminRole);
        defaultUser.setRoles(defaultUserRole);

        userService.save(defaultUser);

        // init setting
        Setting initSetting = new Setting();
        initSetting.setName("init");
        initSetting.setDescription(localeMessageSourceUtil.getMessage("db.setting.init"));
        initSetting.setValue("true");

        Setting themeSetting = new Setting();
        themeSetting.setName("theme");
        themeSetting.setDescription(localeMessageSourceUtil.getMessage("db.setting.theme"));
        themeSetting.setValue("bootstrap");

        Setting pageSizeSetting = new Setting();
        pageSizeSetting.setName("page_size");
        pageSizeSetting.setDescription(localeMessageSourceUtil.getMessage("db.setting.pageSize"));
        pageSizeSetting.setValue("20");

        Setting baseUrlSetting = new Setting();
        baseUrlSetting.setName("base_url");
        baseUrlSetting.setDescription(localeMessageSourceUtil.getMessage("db.setting.baseUrl"));
        baseUrlSetting.setValue("http://localhost:8080/");

        Setting editorSetting = new Setting();
        editorSetting.setName("editor");
        editorSetting.setDescription(localeMessageSourceUtil.getMessage("db.setting.editor"));
        editorSetting.setValue("markdown");

        Setting uploadPathSetting = new Setting();
        uploadPathSetting.setName("upload_path");
        uploadPathSetting.setDescription(localeMessageSourceUtil.getMessage("db.setting.uploadPath"));
        uploadPathSetting.setValue("");

        Setting avatarPathSetting = new Setting();
        avatarPathSetting.setName("avatar_path");
        avatarPathSetting.setDescription(localeMessageSourceUtil.getMessage("db.setting.avatarPath"));
        avatarPathSetting.setValue("");

        Setting searchSetting = new Setting();
        searchSetting.setName("search");
        searchSetting.setDescription(localeMessageSourceUtil.getMessage("db.setting.search"));
        searchSetting.setValue("false");

        settingService.save(initSetting);
        settingService.save(themeSetting);
        settingService.save(pageSizeSetting);
        settingService.save(baseUrlSetting);
        settingService.save(editorSetting);
        settingService.save(uploadPathSetting);
        settingService.save(avatarPathSetting);
        settingService.save(searchSetting);

        // init sections
        Section askSection = new Section();
        askSection.setName(localeMessageSourceUtil.getMessage("db.section.ask"));

        Section shareSection = new Section();
        shareSection.setName(localeMessageSourceUtil.getMessage("db.section.share"));

        Section blogSection = new Section();
        blogSection.setName(localeMessageSourceUtil.getMessage("db.section.blog"));

        Section newsSection = new Section();
        newsSection.setName(localeMessageSourceUtil.getMessage("db.section.news"));

        Section jobSection = new Section();
        jobSection.setName(localeMessageSourceUtil.getMessage("db.section.job"));

        sectionService.save(askSection);
        sectionService.save(shareSection);
        sectionService.save(blogSection);
        sectionService.save(newsSection);
        sectionService.save(jobSection);

    }
}
