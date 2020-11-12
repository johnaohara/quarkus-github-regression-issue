package org.jboss.perf.regression.utils;

import io.quarkus.qute.Template;
import io.quarkus.qute.api.ResourcePath;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MessageFormatter {
    @ResourcePath("fixedTemplate")
    Template fixedTemplate;

    @ResourcePath("failedTemplate")
    Template failedTemplate;

    @ResourcePath("stillFailingTemplate")
    Template stillFailingTemplate;


    public String getSuccessMsg(String repo, String runId){
        return fixedTemplate
                .data("repo", repo)
                .data("runId", runId)
                .render();
    }

    public String getFailedMsg(String repo, String runId){
        return failedTemplate
                .data("repo", repo)
                .data("runId", runId)
                .render();
    }

    public String getStillFailingMsg(String repo, String runId){
        return failedTemplate
                .data("repo", repo)
                .data("runId", runId)
                .render();
    }

}
