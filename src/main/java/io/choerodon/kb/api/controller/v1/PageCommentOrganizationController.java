package io.choerodon.kb.api.controller.v1;

import io.choerodon.base.annotation.Permission;
import io.choerodon.base.enums.ResourceType;
import io.choerodon.core.iam.InitRoleCode;
import io.choerodon.kb.api.dao.PageCommentDTO;
import io.choerodon.kb.api.dao.PageCreateCommentDTO;
import io.choerodon.kb.api.dao.PageUpdateCommentDTO;
import io.choerodon.kb.app.service.PageCommentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Zenger on 2019/4/30.
 */
@RestController
@RequestMapping(value = "/v1/organizations/{organization_id}/page_comment")
public class PageCommentOrganizationController {

    private PageCommentService pageCommentService;

    public PageCommentOrganizationController(PageCommentService pageCommentService) {
        this.pageCommentService = pageCommentService;
    }

    /**
     * 创建page评论
     *
     * @param organizationId       组织id
     * @param pageCreateCommentDTO 评论信息
     * @return List<PageCommentDTO>
     */
    @Permission(type = ResourceType.ORGANIZATION,
            roles = {InitRoleCode.ORGANIZATION_ADMINISTRATOR,
                    InitRoleCode.ORGANIZATION_MEMBER})
    @ApiOperation("创建page评论")
    @PostMapping
    public ResponseEntity<PageCommentDTO> create(@ApiParam(value = "组织ID", required = true)
                                                 @PathVariable(value = "organization_id") Long organizationId,
                                                 @ApiParam(value = "评论信息", required = true)
                                                 @RequestBody @Valid PageCreateCommentDTO pageCreateCommentDTO) {
        return new ResponseEntity<>(pageCommentService.create(pageCreateCommentDTO), HttpStatus.CREATED);
    }


    @Permission(type = ResourceType.ORGANIZATION,
            roles = {InitRoleCode.ORGANIZATION_ADMINISTRATOR,
                    InitRoleCode.ORGANIZATION_MEMBER})
    @ApiOperation(value = " 查询页面评论")
    @GetMapping(value = "/list")
    public ResponseEntity<List<PageCommentDTO>> queryByList(
            @ApiParam(value = "组织id", required = true)
            @PathVariable(value = "organization_id") Long organizationId,
            @ApiParam(value = "页面id", required = true)
            @RequestParam Long pageId) {
        return new ResponseEntity<>(pageCommentService.queryByList(pageId), HttpStatus.OK);
    }

    /**
     * 更新page评论
     *
     * @param organizationId       组织id
     * @param id                   评论id
     * @param pageUpdateCommentDTO 评论信息
     * @return
     */
    @Permission(type = ResourceType.ORGANIZATION,
            roles = {InitRoleCode.ORGANIZATION_ADMINISTRATOR,
                    InitRoleCode.ORGANIZATION_MEMBER})
    @ApiOperation("更新page评论")
    @PutMapping(value = "/{id}")
    public ResponseEntity<PageCommentDTO> update(@ApiParam(value = "组织ID", required = true)
                                                 @PathVariable(value = "organization_id") Long organizationId,
                                                 @ApiParam(value = "评论id", required = true)
                                                 @PathVariable Long id,
                                                 @ApiParam(value = "评论信息", required = true)
                                                 @RequestBody @Valid PageUpdateCommentDTO pageUpdateCommentDTO) {
        return new ResponseEntity<>(pageCommentService.update(id,
                pageUpdateCommentDTO),
                HttpStatus.CREATED);
    }

    /**
     * 通过id删除评论（管理员权限）
     *
     * @param organizationId 组织ID
     * @param id             评论id
     * @return ResponseEntity
     */
    @Permission(type = ResourceType.ORGANIZATION,
            roles = {InitRoleCode.ORGANIZATION_ADMINISTRATOR,
                    InitRoleCode.ORGANIZATION_MEMBER})
    @ApiOperation("通过id删除评论（管理员权限）")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteComment(@ApiParam(value = "组织ID", required = true)
                                        @PathVariable(value = "organization_id") Long organizationId,
                                        @ApiParam(value = "评论id", required = true)
                                        @PathVariable Long id) {
        pageCommentService.delete(id, true);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * 通过id删除评论（删除自己的评论）
     *
     * @param organizationId 组织ID
     * @param id             评论id
     * @return ResponseEntity
     */
    @Permission(type = ResourceType.ORGANIZATION,
            roles = {InitRoleCode.ORGANIZATION_ADMINISTRATOR,
                    InitRoleCode.ORGANIZATION_MEMBER})
    @ApiOperation("通过id删除评论（删除自己的评论）")
    @DeleteMapping(value = "/delete_my/{id}")
    public ResponseEntity deleteMyComment(@ApiParam(value = "组织ID", required = true)
                                          @PathVariable(value = "organization_id") Long organizationId,
                                          @ApiParam(value = "评论id", required = true)
                                          @PathVariable Long id) {
        pageCommentService.delete(id, false);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
