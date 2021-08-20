const core = require("@actions/core");
const github = require("@actions/github");
const { buildWebhook } = require("./webhook");
const centra = require("centra");

try {
    const webhookUrl = process.env.WEBHOOK_URL
    const version = process.env.PROJECT_VERSION
    const repo = github.context.repo()
    const avatar = "https://avatars.githubusercontent.com/u/84095356?s=200&v=4"

    await centra(webhookUrl, "POST")
    .body(buildWebhook(version, repo.repo, `https://github.com/${repo.owner}/${repo.repo}`, avatar))
    .send()
    console.log("Successufly sent webhook!")
} catch (err) {
    core.setFailed(err.message)
}