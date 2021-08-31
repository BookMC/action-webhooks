package org.bookmc.tools;

import org.bookmc.tools.data.CommitData;
import org.bookmc.tools.tasks.CommitWebhookTask;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

import javax.annotation.Nonnull;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class BuildTools implements Plugin<Project> {
    @Override
    public void apply(@Nonnull Project project) {
        Map<String, String> env = System.getenv();

        project.getTasks().register("commitWebhook", CommitWebhookTask.class, task -> {
            task.setWebhook(env.get("WEBHOOK_URL"));
            task.setProjectGithub(env.getOrDefault("ORGANIZATION_URL", "https://github.com/BookMC") + "/" + project.getName());
            task.setProjectVersion(project.getVersion().toString());
            task.setProjectName(project.getName());

            task.setUsername(env.getOrDefault("WEBHOOK_NAME", "BookMC Toolchain"));
            task.setAvatarURL(env.getOrDefault("WEBHOOK_PROFILE_PICTURE", "https://avatars.githubusercontent.com/u/84095356?s=200&v=4"));

            task.setColor(new Color(5549140));

            try {
                CommitData data = getCommitMessage(Git.open(project.getProjectDir()));
                task.setCommitMessage("`" + data.hash() + "` " + data.message() + " by " + data.author());
            } catch (IOException | GitAPIException e) {
                e.printStackTrace();
            }
        });
    }

    private static CommitData getCommitMessage(Git git) throws IOException, GitAPIException {
        Ref branch = null;

        String rawBranch = git.getRepository().getFullBranch();
        for (Ref ref : git.branchList().setListMode(ListBranchCommand.ListMode.ALL).call()) {
            if (ref.getName().equals(rawBranch)) {
                branch = ref;
                break;
            }
        }

        if (branch == null) {
            throw new IllegalStateException("Failed to find branch! (" + rawBranch + ")");
        }

        RevWalk walk = new RevWalk(git.getRepository());
        RevCommit commit = walk.parseCommit(branch.getObjectId());

        return new CommitData(commit.getName().substring(0, 7), commit.getShortMessage(), commit.getAuthorIdent().getName());
    }
}
