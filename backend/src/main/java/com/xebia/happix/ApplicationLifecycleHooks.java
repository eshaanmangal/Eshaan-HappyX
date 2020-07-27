package com.xebia.happix;

import com.google.common.collect.Sets;
import com.xebia.happix.application.service.MasterDataService;
import com.xebia.happix.model.EmailTemplate;
import com.xebia.happix.repository.EmailTemplateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Slf4j
@Component
public class ApplicationLifecycleHooks implements ApplicationRunner {

    @Autowired
    private MasterDataService masterData;

    @Autowired
    private EmailTemplateRepository templateRepository;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        templateMoodDataSetUp();
        startUp();
    }

    public void startUp() {
        log.debug(">>>>>>>>>>>>>>>>>> Application startup hook starts >>>>>>>>>>>>>>>>>>");

        initializeMasterData();

        log.debug(">>>>>>>>>>>>>>>>>> Application startup hook ends... >>>>>>>>>>>>>>>>>>");
    }

    @Async
    public void initializeMasterData() {

        while (!this.masterData.isBeanInitialized()) {
            try {
                log.debug("Trying to initialize master data......");
                this.masterData.initialize();
            } catch (final Exception e) {
                log.error("Error while master data initialization, So will be re-tried in 2 mins", e);
            }
            try {
                Thread.sleep(2 * 60 * 1000);
            } catch (InterruptedException e) {
                log.error("InterruptedException while waiting to retry master data initialization", e);
            }
        }
    }

    @PreDestroy
    public void shutDown() {
        log.debug("<<<<<<<<<<<<<<<<<< Application shutdown hook starts <<<<<<<<<<<<<<<<<");

        log.debug("<<<<<<<<<<<<<<<<<< Application shutdown hook ends... <<<<<<<<<<<<<<<<<");
    }

    @Scheduled(cron = "0 0/30 * * * *")
    public void executeSystemGarbageCollector() {
        // log.debug(">>>>>>>>>>>>>>>>>> Performing Application Garbage Collector starts >>>>>>>>>>>>>>>>>>");
        System.gc();
        // log.debug(">>>>>>>>>>>>>>>>>> Performing Application Garbage Collector ends >>>>>>>>>>>>>>>>>>");
    }

    @Scheduled(cron = "0 1 1 * * ?")
    public void initialiseStaffData() {
        log.debug(">>>>>>>>>>>>>>>>>> Initialising Master Data In Morning starts >>>>>>>>>>>>>>>>>>");
        this.masterData.setBeanInitializedtoFalse();
        initializeMasterData();
        log.debug(">>>>>>>>>>>>>>>>>> Initialising Master Data In Morning  ends >>>>>>>>>>>>>>>>>>");
    }

    public void templateMoodDataSetUp() {

        EmailTemplate mailTemplate = new EmailTemplate();
        mailTemplate.setTemplateName("MailTemplate");
        mailTemplate.setMoods(Sets.newHashSet("HAPPY", "SAD", "CONFUSED", "ANGRY", "NO_RESPONSE"));

        EmailTemplate mailTemplate1 = new EmailTemplate();
        mailTemplate1.setTemplateName("1");
        mailTemplate1.setMoods(Sets.newHashSet("HAPPY", "SAD", "CONFUSED", "ANGRY", "NO_RESPONSE"));

        EmailTemplate mailTemplate2 = new EmailTemplate();
        mailTemplate2.setTemplateName("2");
        mailTemplate2.setMoods(Sets.newHashSet("HIGHLYMOTIVATED", "MOTIVATED", "NOCHANGE", "CLUELESS", "NO_RESPONSE"));

        EmailTemplate mailTemplate3 = new EmailTemplate();
        mailTemplate3.setTemplateName("3");
        mailTemplate3.setMoods(Sets.newHashSet("ACCOMPLISHED", "EXCITED", "STRESSED", "EXHAUSTED", "NO_RESPONSE"));

        if (!templateRepository.findByTemplateName("MailTemplate").isPresent())
            templateRepository.save(mailTemplate);
        if (!templateRepository.findByTemplateName("1").isPresent())
            templateRepository.save(mailTemplate1);
        if (!templateRepository.findByTemplateName("2").isPresent())
            templateRepository.save(mailTemplate2);
        if (!templateRepository.findByTemplateName("3").isPresent())
            templateRepository.save(mailTemplate3);

    }

}
