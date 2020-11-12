package org.jboss.perf.regression;

import org.jboss.perf.regression.dto.Change;
import org.jboss.perf.regression.dto.GhApiResult;
import org.jboss.perf.regression.utils.RunState;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/github/api")
public class GitHubIssueResource {

    @Inject
    GithubApi githubApi;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String unsupported(){
        return "GET requests are not supported";
    }

    @POST
    @Path("/fail")
    @Produces(MediaType.APPLICATION_JSON)
    public GhApiResult submitChange(Change change){

        githubApi.updateIssue(RunState.FAILED, Integer.toString(change.runId), change);

        return null;
    }

    @POST
    @Path("/success")
    @Produces(MediaType.APPLICATION_JSON)
    public GhApiResult closeIssue(){

        githubApi.updateIssue(RunState.SUCCESS, "",null);

        return null;
    }


    }