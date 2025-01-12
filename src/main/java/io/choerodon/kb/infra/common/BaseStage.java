package io.choerodon.kb.infra.common;

import io.choerodon.kb.infra.mapper.WorkSpaceMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Zenger on 2018/7/18.
 */
public abstract class BaseStage {

    @Autowired
    private WorkSpaceMapper workSpaceMapper;

    private BaseStage() {

    }

    public static final String O = "O-";
    public static final String P = "P-";
    public static final String APPOINT = "appoint";
    public static final String USERNAME = "admin";
    public static final String REFERENCE_PAGE = "referencePage";
    public static final String REFERENCE_URL = "referenceUrl";
    public static final String SELF = "self";
    public static final String BACKETNAME = "knowledgebase-service";
    public static final String INSERT = "insert";
    public static final String UPDATE = "update";

    //data log
    public static final String PAGE_CREATE = "pageCreate";
    public static final String PAGE_UPDATE = "pageUpdate";
    public static final String COMMENT_CREATE = "commentCreate";
    public static final String COMMENT_UPDATE = "commentUpdate";
    public static final String COMMENT_DELETE = "commentDelete";
    public static final String ATTACHMENT_CREATE = "attachmentCreate";
    public static final String ATTACHMENT_DELETE = "attachmentDelete";
    public static final String CREATE_OPERATION = "Create";
    public static final String UPDATE_OPERATION = "Update";
    public static final String DELETE_OPERATION = "Delete";
    public static final String PAGE = "Page";
    public static final String COMMENT = "Comment";
    public static final String ATTACHMENT = "Attachment";
    public static final String SHARE = "Share";

    //share type
    public static final String SHARE_DISABLE = "disabled";
    public static final String SHARE_CURRENT = "current_page";
    public static final String SHARE_INCLUDE = "include_page";

    //es page index
    public static final String ES_PAGE_INDEX = "knowledge_page";
    public static final String ES_PAGE_FIELD_PAGE_ID = "id";
    public static final String ES_PAGE_FIELD_PROJECT_ID = "project_id";
    public static final String ES_PAGE_FIELD_ORGANIZATION_ID = "organization_id";
    public static final String ES_PAGE_FIELD_TITLE = "title";
    public static final String ES_PAGE_FIELD_CONTENT = "content";
}
