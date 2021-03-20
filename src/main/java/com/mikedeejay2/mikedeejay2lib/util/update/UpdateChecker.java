package com.mikedeejay2.mikedeejay2lib.util.update;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mikedeejay2.mikedeejay2lib.PluginBase;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

/**
 * An update checker that checks for a plugin for updates based off of its Github repository.
 * <p>
 * This class accesses the website <tt>api.github.com</tt>, if the Github API is down this update checker
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
public class UpdateChecker
{
    protected final PluginBase plugin;

    protected String userName;
    protected String repoName;
    protected URL url;
    protected String version;
    protected String versionName;
    protected String releasedTime;
    protected String downloadUrl;
    protected JsonObject json;

    public UpdateChecker(PluginBase plugin)
    {
        this.plugin = plugin;
    }

    /**
     * Initialize <tt>UpdateChecker</tt> and specify the username and repository name of the
     * repository to check the updates for.
     *
     * @param userName The username of the Github profile to get the repository from
     * @param repoName The name of the repository that should be gotten
     */
    public void init(String userName, String repoName)
    {
        this.userName = userName;
        this.repoName = repoName;
        if(plugin.getDescription().getVersion().endsWith("-SNAPSHOT"))
        {
            return;
        }
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                try
                {
                    url = new URL("https://api.github.com/repos/" + userName + "/" + repoName + "/releases/latest");
                }
                catch(Exception e)
                {
                    plugin.getLogger().severe(plugin.langManager().getTextLib("update_checker.error.invalid_url"));
                    return;
                }
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                InputStream stream = null;

                try
                {
                    stream = url.openStream();
                }
                catch(Exception e)
                {
                    plugin.getLogger().severe(plugin.langManager().getTextLib("update_checker.error.cant_open"));
                    return;
                }
                Reader reader = new InputStreamReader(stream);
                json = gson.fromJson(reader, JsonObject.class);
                try
                {
                    stream.close();
                }
                catch(IOException e)
                {
                    plugin.getLogger().severe(plugin.langManager().getTextLib("update_checker.error.cant_close"));
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
    public void checkForUpdates(int delay)
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                if(version == null) return;
                PluginDescriptionFile description = plugin.getDescription();
                String currentVersion = description.getVersion();
                String pluginName = description.getName();
                if(currentVersion.equals(version)) return;
                plugin.sendMessage("&a" + plugin.langManager().getTextLib("update_checker.update_available",
                        new String[]{"PLUGIN"},
                        new String[]{"&b" + pluginName + "&a"}));
                plugin.sendMessage("&e" + plugin.langManager().getTextLib("update_checker.new_version",
                        new String[]{"PLUGIN", "VERSION"},
                        new String[]{"&b" + pluginName + "&e", "&c&l" + version + "&r&e"}));
                plugin.sendMessage("&e" + plugin.langManager().getTextLib("update_checker.release_time",
                        new String[]{"TIME"},
                        new String[]{"&a" + releasedTime + "&e"}));
                plugin.sendMessage("&e" + plugin.langManager().getTextLib("update_checker.download_link",
                        new String[]{"LINK"},
                        new String[]{"&f" + downloadUrl}));
            }
        }.runTaskLaterAsynchronously(plugin, delay * 20L);
    }
}
