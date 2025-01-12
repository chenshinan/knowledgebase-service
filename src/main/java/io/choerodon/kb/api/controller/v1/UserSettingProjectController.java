package io.choerodon.kb.api.controller.v1;

import io.choerodon.base.annotation.Permission;
import io.choerodon.base.enums.ResourceType;
import io.choerodon.core.iam.InitRoleCode;
import io.choerodon.kb.api.dao.UserSettingDTO;
import io.choerodon.kb.app.service.UserSettingService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by HuangFuqiang@choerodon.io on 2019/07/02.
 * Email: fuqianghuang01@gmail.com
 */
@RestController
@RequestMapping(value = "/v1/projects/{project_id}/user_setting")
public class UserSettingProjectController {

    @Autowired
    private UserSettingService userSettingService;

    @Permission(type = ResourceType.PROJECT, roles = {InitRoleCode.PROJECT_OWNER, InitRoleCode.PROJECT_MEMBER})
    @ApiOperation("项目层创建或更新个人设置")
    @PostMapping
    public ResponseEntity createOrUpdate(@ApiParam(value = "项目id", required = true)
                                         @PathVariable(name = "project_id") Long projectId,
                                         @ApiParam(value = "组织id", required = true)
                                         @RequestParam Long organizationId,
                                         @ApiParam(value = "user setting dto", required = true)
                                         @RequestBody UserSettingDTO userSettingDTO) {
        userSettingService.createOrUpdate(organizationId, projectId, userSettingDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
