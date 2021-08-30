package org.bookmc.tools.tasks;

import org.gradle.api.provider.Property;
import org.gradle.workers.WorkParameters;

import java.awt.*;

public interface CommitWebhookParameters extends WorkParameters {
    Property<String> getWebhookURL();
    Property<String> getProjectName();
    Property<String> getProjectVersion();
    Property<String> getProjectGithub();
    Property<String> getUsername();
    Property<String> getAvatarURL();
    Property<String> getCommitMessage();
    Property<Color> getEmbedColor();
}
