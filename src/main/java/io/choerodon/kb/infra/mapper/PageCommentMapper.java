package io.choerodon.kb.infra.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import io.choerodon.kb.infra.dataobject.PageCommentDO;
import io.choerodon.mybatis.common.Mapper;

/**
 * Created by Zenger on 2019/4/30.
 */
public interface PageCommentMapper extends Mapper<PageCommentDO> {

    List<PageCommentDO> selectByPageId(@Param("pageId") Long pageId);
}
