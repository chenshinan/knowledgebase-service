package io.choerodon.kb.api.dao;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Zenger on 2019/5/13.
 */
public class MoveWorkSpaceDTO {

    @NotNull
    @ApiModelProperty(value = "移动的工作空间id")
    private Long id;

    @NotNull
    @ApiModelProperty(value = "是否在前面")
    private Boolean isBefore;

    @NotNull
    @ApiModelProperty(value = "移动的参照工作空间id")
    private Long targetId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getBefore() {
        return isBefore;
    }

    public void setBefore(Boolean before) {
        isBefore = before;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }
}
