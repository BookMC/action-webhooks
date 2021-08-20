package org.bookmc.tools.tasks;

import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

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
    public void run() throws IOException {
        JsonObject object = new JsonObject();
        object.addProperty("username", getUsername());
        object.addProperty("avatar_url", getAvatarUrl());
        object.add("content", JsonNull.INSTANCE);

        JsonArray embeds = new JsonArray();
        JsonObject embed = new JsonObject();
        embed.addProperty("color", 5549140);
        embed.addProperty("title", "A new toolchain update has been released!");
        embed.addProperty("description", "A new version of " + getProjectName() + " is now out! Check it out at " + getProjectGithub() + ". The latest version of " + getProjectName() + " is now " + getProjectVersion());
        embeds.add(embed);

        object.add("embeds", embeds);

        byte[] postData = object.toString().getBytes(StandardCharsets.UTF_8);
        int postDataLength = postData.length;

        URL url = new URL(getWebhook());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setInstanceFollowRedirects(false);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("charset", "utf-8");
        conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
        conn.setUseCaches(false);

        try (DataOutputStream daos = new DataOutputStream(conn.getOutputStream())) {
            daos.write(postData);
        }
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
