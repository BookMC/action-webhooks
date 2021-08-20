package org.bookmc.tools.tasks;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

import java.time.Instant;

public class CommitWebhookTask extends DefaultTask {
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
    private String avatarUrl;

    @TaskAction
    public void run() {
        WebhookClient client = WebhookClient.withUrl(getWebhook());

        WebhookEmbedBuilder builder = new WebhookEmbedBuilder()
            .setColor(5549140)
            .setTitle(new WebhookEmbed.EmbedTitle("A new toolchain update has been released!", null))
            .setDescription("A new version of `" + getProjectName() + "` is now out! Check it out at " + getProjectGithub() + ". The latest version of `" + getProjectName() + "` is now `" + getProjectVersion() + "`")
            .setFooter(new WebhookEmbed.EmbedFooter("Powered by BuildTools", getAvatarUrl()))
            .setTimestamp(Instant.now());
        WebhookMessageBuilder messageBuilder = new WebhookMessageBuilder()
            .setUsername(getUsername())
            .setAvatarUrl(getAvatarUrl())
            .addEmbeds(builder.build());

        client.send(messageBuilder.build());
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

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
