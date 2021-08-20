package org.bookmc.tools;

import org.bookmc.tools.tasks.CommitWebhookTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

import javax.annotation.Nonnull;

public class BuildTools implements Plugin<Project> {
    @Override
    public void apply(@Nonnull Project project) {
        project.getTasks().register("commitWebhook", CommitWebhookTask.class, task -> {
            task.setWebhook(System.getenv("WEBHOOK_URL"));
            task.setProjectGithub("https://github.com/BookMC/" + project.getName());
            task.setProjectVersion(project.getVersion().toString());
            task.setProjectName(project.getName());

            task.setUsername("BookMC Toolchain");
            task.setAvatarUrl("https://avatars.githubusercontent.com/u/84095356?s=200&v=4");
        });
    }
}
