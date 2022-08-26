package com.mikedeejay2.mikedeejay2lib.util.update;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.text.PlaceholderFormatter;
import com.mikedeejay2.mikedeejay2lib.text.Text;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

/**
 * An update checker that checks for a plugin for updates based off of its GitHub repository.
 * <p>
 * This class accesses the website <code>api.github.com</code>, if the GitHub API is down this update checker
 * won't work.
 * <p>
 * The update checker must be initialized using the {@link UpdateChecker#init(String, String)} method.
 * <p>
 * The method {@link UpdateChecker#checkForUpdates(int)} is the recommended method for checking for updates.
 * <p>
 * If an update is found a message will be printed to console.
 *
 * @author Mikedeejay2
 */
public class UpdateChecker {
    /**
     * The {@link BukkitPlugin} instance
     */
    protected final BukkitPlugin plugin;

    /**
     * The username that owns the repository
     */
    protected String userName;

    /**
     * The name of the repository
     */
    protected String repoName;

    /**
     * The URL to the API link of the repository
     */
    protected URL url;

    /**
     * The latest version from the API
     */
    protected String version;

    /**
     * The name of the version from the API
     */
    protected String versionName;

    /**
     * The release time from the API
     */
    protected String releasedTime;

    /**
     * The direct download URL from the API
     */
    protected String downloadUrl;

    /**
     * The {@link JsonObject} of the API information of the repository
     */
    protected JsonObject json;

    /**
     * Construct a new <code>UpdateChecker</code>
     *
     * @param plugin The {@link BukkitPlugin} instance
     */
    public UpdateChecker(BukkitPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Initialize <code>UpdateChecker</code> and specify the username and repository name of the
     * repository to check the updates for.
     *
     * @param userName The username of the Github profile to get the repository from
     * @param repoName The name of the repository that should be gotten
     */
    public void init(String userName, String repoName) {
        this.userName = userName;
        this.repoName = repoName;
        if(plugin.getDescription().getVersion().contains("-SNAPSHOT")) {
            return;
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    url = new URL("https://api.github.com/repos/" + userName + "/" + repoName + "/releases/latest");
                } catch(Exception e) {
                    plugin.getLogger().severe(Text.translatable("update_checker.error.invalid_url").get());
                    return;
                }
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                InputStream stream = null;

                try {
                    stream = url.openStream();
                } catch(Exception e) {
                    plugin.getLogger().severe(Text.translatable("update_checker.error.cant_open").get());
                    return;
                }
                Reader reader = new InputStreamReader(stream);
                json = gson.fromJson(reader, JsonObject.class);
                try {
                    stream.close();
                } catch(IOException e) {
                    plugin.getLogger().severe(Text.translatable("update_checker.error.cant_close").get());
                    return;
                }

                version = json.get("tag_name").getAsString();
                versionName = json.get("name").getAsString();
                releasedTime = json.get("published_at").getAsString();

                JsonArray assets = json.getAsJsonArray("assets");
                JsonObject downloadObj = assets.get(0).getAsJsonObject();
                downloadUrl = downloadObj.get("browser_download_url").getAsString();
            }
        }.runTaskAsynchronously(plugin);
    }

    /**
     * Check for updates after a certain amount of time.
     * A delay is specified so that ample time is given for the Github API to load
     * in the required data.
     *
     * @param delay Delay before the updates are checked in seconds.
     */
    public void checkForUpdates(int delay) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if(version == null) return;
                PluginDescriptionFile description = plugin.getDescription();
                String currentVersion = description.getVersion();
                String pluginName = description.getName();
                if(currentVersion.equals(version)) return;
                plugin.sendMessage("&a" + Text
                    .translatable("update_checker.update_available")
                    .placeholder(PlaceholderFormatter.of("plugin", "&b" + pluginName + "&a"))
                    .get());
                plugin.sendMessage("&e" + Text
                    .translatable("update_checker.new_version")
                    .placeholder(PlaceholderFormatter.of("plugin", "&b" + pluginName + "&e")
                                     .and("version", "&c&l" + version + "&r&e"))
                    .get());
                plugin.sendMessage("&e" + Text
                    .translatable("update_checker.release_time")
                    .placeholder(PlaceholderFormatter.of("time", "&a" + releasedTime + "&e"))
                    .get());
                plugin.sendMessage("&e" + Text
                    .translatable("update_checker.download_link")
                    .placeholder(PlaceholderFormatter.of("link", "&f" + downloadUrl))
                    .get());
            }
        }.runTaskLaterAsynchronously(plugin, delay * 20L);
    }
}
