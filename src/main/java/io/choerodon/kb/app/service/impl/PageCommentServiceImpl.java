package io.choerodon.kb.app.service.impl;

import io.choerodon.core.exception.CommonException;
import io.choerodon.core.oauth.DetailsHelper;
import io.choerodon.kb.api.dao.PageCommentDTO;
import io.choerodon.kb.api.dao.PageCreateCommentDTO;
import io.choerodon.kb.api.dao.PageUpdateCommentDTO;
import io.choerodon.kb.app.service.PageCommentService;
import io.choerodon.kb.domain.kb.repository.IamRepository;
import io.choerodon.kb.domain.kb.repository.PageCommentRepository;
import io.choerodon.kb.domain.kb.repository.PageRepository;
import io.choerodon.kb.infra.dataobject.PageCommentDO;
import io.choerodon.kb.infra.dataobject.PageDO;
import io.choerodon.kb.infra.dataobject.iam.UserDO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Zenger on 2019/4/30.
 */
@Service
public class PageCommentServiceImpl implements PageCommentService {

    private IamRepository iamRepository;
    private PageRepository pageRepository;
    private PageCommentRepository pageCommentRepository;
    private static final String ILLEGAL_ERROR = "error.delete.illegal";

    public PageCommentServiceImpl(IamRepository iamRepository,
                                  PageRepository pageRepository,
                                  PageCommentRepository pageCommentRepository) {
        this.iamRepository = iamRepository;
        this.pageRepository = pageRepository;
        this.pageCommentRepository = pageCommentRepository;
    }

    @Override
    public PageCommentDTO create(PageCreateCommentDTO pageCreateCommentDTO) {
        PageDO pageDO = pageRepository.selectById(pageCreateCommentDTO.getPageId());
        PageCommentDO pageCommentDO = new PageCommentDO();
        pageCommentDO.setPageId(pageDO.getId());
        pageCommentDO.setComment(pageCreateCommentDTO.getComment());
        pageCommentDO = pageCommentRepository.insert(pageCommentDO);
        return getCommentInfo(pageCommentDO);
    }

    @Override
    public PageCommentDTO update(Long id, PageUpdateCommentDTO pageUpdateCommentDTO) {
        PageCommentDO pageCommentDO = pageCommentRepository.selectById(id);
        if (!pageCommentDO.getPageId().equals(pageUpdateCommentDTO.getPageId())) {
            throw new CommonException("error.pageId.not.equal");
        }
        pageCommentDO.setObjectVersionNumber(pageUpdateCommentDTO.getObjectVersionNumber());
        pageCommentDO.setComment(pageUpdateCommentDTO.getComment());
        pageCommentDO = pageCommentRepository.update(pageCommentDO);
        return getCommentInfo(pageCommentDO);
    }

    @Override
    public List<PageCommentDTO> queryByList(Long pageId) {
        List<PageCommentDTO> pageCommentDTOList = new ArrayList<>();
        List<PageCommentDO> pageComments = pageCommentRepository.selectByPageId(pageId);
        if (pageComments != null && !pageComments.isEmpty()) {
            List<Long> userIds = pageComments.stream().map(PageCommentDO::getCreatedBy).distinct()
                    .collect(Collectors.toList());
            Long[] ids = new Long[userIds.size()];
            userIds.toArray(ids);
            List<UserDO> userDOList = iamRepository.userDOList(ids);
            Map<Long, UserDO> userMap = new HashMap<>();
            userDOList.forEach(userDO -> userMap.put(userDO.getId(), userDO));
            pageComments.forEach(p -> {
                PageCommentDTO pageCommentDTO = new PageCommentDTO();
                pageCommentDTO.setId(p.getId());
                pageCommentDTO.setPageId(p.getPageId());
                pageCommentDTO.setComment(p.getComment());
                pageCommentDTO.setObjectVersionNumber(p.getObjectVersionNumber());
                pageCommentDTO.setUserId(p.getCreatedBy());
                pageCommentDTO.setLastUpdateDate(p.getLastUpdateDate());
                UserDO userDO = userMap.getOrDefault(p.getCreatedBy(), new UserDO());
                pageCommentDTO.setLoginName(userDO.getLoginName());
                pageCommentDTO.setRealName(userDO.getRealName());
                pageCommentDTO.setUserImageUrl(userDO.getImageUrl());
                pageCommentDTOList.add(pageCommentDTO);
            });
        }
        return pageCommentDTOList;
    }

    @Override
    public void delete(Long id, Boolean isAdmin) {
        PageCommentDO comment = pageCommentRepository.selectById(id);
        if (!isAdmin) {
            Long currentUserId = DetailsHelper.getUserDetails().getUserId();
            if (!comment.getCreatedBy().equals(currentUserId)) {
                throw new CommonException(ILLEGAL_ERROR);
            }
        }
        pageCommentRepository.delete(id);
    }

    private PageCommentDTO getCommentInfo(PageCommentDO pageCommentDO) {
        PageCommentDTO pageCommentDTO = new PageCommentDTO();
        pageCommentDTO.setId(pageCommentDO.getId());
        pageCommentDTO.setPageId(pageCommentDO.getPageId());
        pageCommentDTO.setComment(pageCommentDO.getComment());
        pageCommentDTO.setObjectVersionNumber(pageCommentDO.getObjectVersionNumber());
        pageCommentDTO.setUserId(pageCommentDO.getCreatedBy());
        pageCommentDTO.setLastUpdateDate(pageCommentDO.getLastUpdateDate());
        Long[] ids = new Long[1];
        ids[0] = pageCommentDO.getCreatedBy();
        List<UserDO> userDOList = iamRepository.userDOList(ids);
        pageCommentDTO.setLoginName(userDOList.get(0).getLoginName());
        pageCommentDTO.setRealName(userDOList.get(0).getRealName());
        pageCommentDTO.setUserImageUrl(userDOList.get(0).getImageUrl());
        return pageCommentDTO;
    }
}
