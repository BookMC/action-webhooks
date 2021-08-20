export const buildWebhook = (version, project, github, avatar) = {
    content: null,
    emebeds: [
        {
            title: `A toolchain update has been released! (${version})`,
            description: `A new version of ${project} has been released.\nCheck more out at ${github}`,
            color: 5549140
        }
    ],
    username: "BookMC Toolchain",
    avatar: avatar
};