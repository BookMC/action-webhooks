package org.bookmc.tools.tasks;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;
import org.gradle.workers.WorkerExecutor;

import javax.inject.Inject;
import java.awt.*;

public abstract class CommitWebhookTask extends DefaultTask {
    @Input
    private String webhook;

    @Input
    private String projectName;

    @Input
    private String projectVersion;

    @Input
    private String projectGithub;

    @Input
    private String username;

    @Input
    private String avatarURL;

    @Input
    private String commitMessage = null;

    @Input
    private Color color;

    @Inject
    abstract WorkerExecutor getWorkerExecutor();
    
    @TaskAction
    public void run() {
        getWorkerExecutor().noIsolation().submit(CommitWebhookAction.class, parameters -> {
            parameters.getWebhookURL().set(webhook);
            parameters.getProjectName().set(projectName);
            parameters.getProjectVersion().set(projectVersion);
            parameters.getProjectGithub().set(projectGithub);
            parameters.getUsername().set(username);
            parameters.getAvatarURL().set(avatarURL);
            if (commitMessage.isEmpty()) {
                parameters.getCommitMessage().set(commitMessage);
            }
            parameters.getEmbedColor().set(color);
        });
    }

    public String getWebhook() {
        return webhook;
    }

    public void setWebhook(String webhook) {
        this.webhook = webhook;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectGithub() {
        return projectGithub;
    }

    public void setProjectGithub(String projectGithub) {
        this.projectGithub = projectGithub;
    }

    public String getProjectVersion() {
        return projectVersion;
    }

    public void setProjectVersion(String projectVersion) {
        this.projectVersion = projectVersion;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    public String getCommitMessage() {
        return commitMessage;
    }

    public void setCommitMessage(String commitMessage) {
        this.commitMessage = commitMessage;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
