package io.choerodon.kb.infra.task;

import io.choerodon.asgard.saga.dto.StartInstanceDTO;
import io.choerodon.asgard.saga.feign.SagaClient;
import io.choerodon.asgard.schedule.QuartzDefinition;
import io.choerodon.asgard.schedule.annotation.JobTask;
import io.choerodon.asgard.schedule.annotation.TimedTask;
import io.choerodon.core.oauth.DetailsHelper;
import io.choerodon.kb.app.service.WikiMigrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class KnowledgebaseTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(KnowledgebaseTask.class);

    @Autowired
    private WikiMigrationService wikiMigrationService;

    @Autowired
    private SagaClient sagaClient;

    @JobTask(maxRetryCount = 3, code = "syncWikiToKnowledgebase", description = "升级到0.18.0,同步wiki数据到新知识管理")
    @TimedTask(name = "syncWikiToKnowledgebase", description = "升级到0.18.0,同步wiki数据到新知识管理", oneExecution = true, repeatCount = 0, repeatInterval = 1, repeatIntervalUnit = QuartzDefinition.SimpleRepeatIntervalUnit.HOURS, params = {})
    public void syncWikiToNewKnowledgeBase(Map<String, Object> map) {
        DetailsHelper.setCustomUserDetails(1L,"zh_CN");
        LOGGER.info("==================================== Begin to upgrade 0.17.0 to 0.18.0, sync wiki to new knowledgebase! ====================================");
        wikiMigrationService.migration();
        LOGGER.info("==================================== Finished to sync wiki to new knowledgebase! =============================================");
        LOGGER.info("==================================== Begin to fix wiki relation by agile! =============================================");
        sagaClient.startSaga("agile-move-wiki-relation", new StartInstanceDTO());
    }

}