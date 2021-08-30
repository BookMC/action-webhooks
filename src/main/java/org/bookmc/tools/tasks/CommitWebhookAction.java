package org.bookmc.tools.tasks;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import org.gradle.workers.WorkAction;

import java.awt.*;
import java.time.Instant;

public abstract class CommitWebhookAction implements WorkAction<CommitWebhookParameters> {
    @Override
    public void execute() {
        String webhookURL = getParameters().getWebhookURL().get();
        String projectName = getParameters().getProjectName().get();
        String projectVersion = getParameters().getProjectVersion().get();
        String projectGithub = getParameters().getProjectGithub().get();
        String username = getParameters().getUsername().get();
        String avatarURL = getParameters().getAvatarURL().get();
        String commitMessage = getParameters().getCommitMessage().getOrNull();
        Color color = getParameters().getEmbedColor().get();

        WebhookClient client = WebhookClient.withUrl(webhookURL);

        String description = "A new version of `" + projectName + "` is now out! Check it out at " + projectGithub + ". The latest version of `" + projectName + "` is now `" + projectVersion;

        if (commitMessage != null) {
            description += "`\nLatest Commit: `" + commitMessage + "`";
        }

        WebhookEmbedBuilder builder = new WebhookEmbedBuilder()
            .setColor(color.getRGB())
            .setTitle(new WebhookEmbed.EmbedTitle("A new toolchain update has been released!", null))
            .setDescription(description)
            .setFooter(new WebhookEmbed.EmbedFooter("Powered by BuildTools", avatarURL))
            .setTimestamp(Instant.now());

        WebhookMessageBuilder messageBuilder = new WebhookMessageBuilder()
            .setUsername(username)
            .setAvatarUrl(avatarURL)
            .addEmbeds(builder.build());

        client.send(messageBuilder.build());
    }
}
