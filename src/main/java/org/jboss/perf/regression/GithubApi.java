package org.jboss.perf.regression;

import io.quarkus.runtime.StartupEvent;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;
import org.jboss.perf.regression.dto.Change;
import org.jboss.perf.regression.utils.MessageFormatter;
import org.jboss.perf.regression.utils.RunState;
import org.kohsuke.github.*;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.io.IOException;
import java.io.UncheckedIOException;

@ApplicationScoped
public class GithubApi {

    private static final Logger LOGGER = Logger.getLogger(GithubApi.class);

    @ConfigProperty(name = "github.token")
    String token;

    @ConfigProperty(name = "github.issueRepo")
    String issueRepo;

    @ConfigProperty(name = "github.issueNumber")
    Integer issueNumber;

    @ConfigProperty(name = "github.thisRepo")
    String thisRepo;


    @Inject
    MessageFormatter messageFormatter;

    GitHub github;
    GHRepository repository;

    public void init(@Observes StartupEvent startupEvent) {
        try {

            github = new GitHubBuilder().withOAuthToken(token).build();

            repository = github.getRepository(issueRepo);


        } catch (IOException e) {
            LOGGER.error("Error occurred: ", e);
            throw new UncheckedIOException(e);
        }

    }


    public void updateIssue(RunState status, String runId, Change change) {

        try {
            GHIssue issue =  repository.getIssue(issueNumber);
            final boolean succeed = RunState.SUCCESS.equals(status);
            if (RunState.CANCELLED.equals(status)) {
                LOGGER.error("Job status is `cancelled` - exiting");
                System.exit(0);
            }

            LOGGER.info(String.format("The CI build had status %s.", status));

            if (issue == null) {
                LOGGER.error(String.format("Unable to find the issue %s in project %s", issueNumber, issueRepo));
                System.exit(-1);
            } else {
                LOGGER.info(String.format("Report issue found: %s - %s", issue.getTitle(), issue.getHtmlUrl().toString()));
                LOGGER.info(String.format("The issue is currently %s", issue.getState().toString()));
            }

            if (succeed) {
                if (issue != null && isOpen(issue)) {
                    // close issue with a comment
                    final GHIssueComment comment = issue.comment(messageFormatter.getSuccessMsg(thisRepo, runId));
                    issue.close();
                    LOGGER.info(String.format("Comment added on issue %s - %s, the issue has also been closed", issue.getHtmlUrl().toString(), comment.getHtmlUrl().toString()));
                } else {
                    LOGGER.info("Nothing to do - the build passed and the issue is already closed");
                }
            } else {
                if (isOpen(issue)) {
                    final GHIssueComment comment = issue.comment(messageFormatter.getStillFailingMsg(thisRepo, runId, change.description));
                    LOGGER.info(String.format("Comment added on issue %s - %s", issue.getHtmlUrl().toString(), comment.getHtmlUrl().toString()));
                } else {
                    issue.reopen();
                    final GHIssueComment comment = issue.comment(messageFormatter.getFailedMsg(thisRepo, runId, change.description));
                    LOGGER.info(String.format("Comment added on issue %s - %s, the issue has been re-opened", issue.getHtmlUrl().toString(), comment.getHtmlUrl().toString()));
                }
            }
        } catch (IOException e) {
            LOGGER.error("Error occurred: ", e);
            throw new UncheckedIOException(e);
        }
    }

    private static boolean isOpen(GHIssue issue) {
        return (issue.getState() == GHIssueState.OPEN);
    }

}
