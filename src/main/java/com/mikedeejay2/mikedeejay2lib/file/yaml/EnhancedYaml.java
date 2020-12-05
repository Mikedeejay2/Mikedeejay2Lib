package com.mikedeejay2.mikedeejay2lib.file.yaml;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.*;

/**
 * This Enhanced Yaml class inherits YamlConfiguration and is meant to be
 * a middle layer that adds extra processing and features to the YamlConfiguration
 * class. <p>
 *
 * The main things that this class does is
 * <ul>
 *     <li>Adds the ability to add comments above keys in a yaml file</li>
 *     <li>Adds the ability to update the yaml file on disk new keys found in the
 *     yaml file in the plugin's jar.</li>
 * </ul>
 *
 * @author Mikedeejay2
 */
public class EnhancedYaml extends YamlConfiguration
{
    // Map that stores paths to comments and the comments themselves, used when saving comments to disk
    private Map<String, String> comments;
    private YamlFileIO yamlFileIO;

    public EnhancedYaml(YamlFileIO yamlFileIO)
    {
        comments = new HashMap<>();
        this.yamlFileIO = yamlFileIO;
    }

    /**
     * This is an override of the saveToString class to implement
     * comments anywhere in the file. Where comments are located,
     * the comment is added to the String that will be returned.
     *
     * @return The String with the added comments
     */
    @Override
    public String saveToString()
    {
        String contents = super.saveToString();

        List<String> lines = new ArrayList<>();
        Collections.addAll(lines, contents.split("\n"));

        StringBuilder newStr = new StringBuilder();

        String currentPath = "";
        int previousDeepness = 0;
        for(String line : lines)
        {
            String trimmed = line.trim();
            if(line.isEmpty() || trimmed.startsWith("#") || !trimmed.contains(":"))
            {
                newStr.append(line).append("\n");
                continue;
            }
            int deepness = getDeepness(line);
            String key = getKey(trimmed);
            currentPath = getPath(currentPath, key, previousDeepness, deepness);
            previousDeepness = deepness;

            if(!comments.containsKey(currentPath))
            {
                newStr.append(line).append("\n");
                continue;
            }

            String comment = "\n" + comments.get(currentPath);
            newStr.append(comment);
            newStr.append(line).append("\n");
        }

        return newStr.toString();
    }

    /**
     * This method overrides the loadFromString method so that
     * comment locations from the file will be read and stored in
     * this class so that when the file is saved later the comments will
     * stay in their correct place.
     *
     * @param contents Original contents of the yaml file without comments.
     * @throws InvalidConfigurationException If there is an error in the yaml file, an <tt>InvalidConfigurationException</tt> will be thrown
     */
    @Override
    public void loadFromString(String contents) throws InvalidConfigurationException
    {
        List<String> lines = new ArrayList<>(Arrays.asList(contents.split("\n")));
        boolean shouldRemoveHeader = false;
        int length = 0;
        if(!lines.isEmpty())
        {
            for(int i = 0; i < lines.size(); i++)
            {
                String line = lines.get(i).trim();
                length += line.length();
                if(!line.startsWith("#") && line.contains(":"))
                {
                    shouldRemoveHeader = true;
                    break;
                }
                if(line.isEmpty()) break;
            }
        }
        super.loadFromString(shouldRemoveHeader ? removeComments(contents) : contents.substring(0, length) + removeComments(contents));

        String currentPath = "";
        int previousDeepness = 0;
        int index = 0;
        for(String line : lines)
        {
            ++index;
            String trimmed = line.trim();
            if(line.isEmpty() || trimmed.startsWith("#") || !trimmed.contains(":")) continue;

            int deepness = getDeepness(line);
            String key = getKey(trimmed);
            currentPath = getPath(currentPath, key, previousDeepness, deepness);
            previousDeepness = deepness;

            int startingCommentIndex = getStartingCommentIndex(lines, index);
            if(startingCommentIndex == -1) continue;
            String comment = compileComments(lines, index, startingCommentIndex);
            comments.put(currentPath, comment);
        }
    }

    /**
     * Update this yaml file based on a yaml file in this plugin's jar file.
     * It is important that you know that the file in the plugin's jar exists
     * before attempting to update, otherwise nothing will happen and you will
     * have wasted a small amount of processing power on doing nothing.
     *
     * @param filePath The path to the file in the jar to update with.
     * @return Whether the update and loading of the file from the jar was successful.
     */
    public boolean updateFromJar(String filePath)
    {
        EnhancedYaml yaml = new EnhancedYaml(yamlFileIO);
        boolean loadSuccess = yamlFileIO.loadYamlConfigFromJar(yaml, filePath, false);
        if(!loadSuccess) return false;
        String contents = yaml.saveToString();
        List<String> lines = new ArrayList<>(Arrays.asList(contents.split("\n")));

        String currentPath = "";
        int previousDeepness = 0;
        int index = 0;
        for(String line : lines)
        {
            ++index;
            String trimmed = line.trim();
            if(line.isEmpty() || trimmed.startsWith("#") || !trimmed.contains(":")) continue;

            int deepness = getDeepness(line);
            String key = getKey(trimmed);
            currentPath = getPath(currentPath, key, previousDeepness, deepness);
            previousDeepness = deepness;

            if(!this.contains(currentPath))
            {
                this.set(currentPath, yaml.get(currentPath));
            }

            int startingCommentIndex = getStartingCommentIndex(lines, index);
            if(startingCommentIndex == -1) continue;
            String comment = compileComments(lines, index, startingCommentIndex);
            if(!comments.containsKey(currentPath) || !comments.get(currentPath).equals(comment))
            {
                comments.put(currentPath, comment);
            }
        }
        return true;
    }

    /**
     * Helper method to take contents of the yaml file and remove
     * all of the contents (aka that annoying header that was duplicating the top comment)
     *
     * @param contents Yaml file contents to be processed
     * @return The string of contents but without the comments
     */
    private String removeComments(String contents)
    {
        String[] lines = contents.split("\n");

        StringBuilder newHeader = new StringBuilder();
        for(int i = 0; i < lines.length; i++)
        {
            String line = lines[i];
            String trimmed = line.trim();
            if(trimmed.startsWith("#")) continue;
            newHeader.append(line).append("\n");
        }
        return newHeader.toString();
    }

    /**
     * Get the path of a new key using several different params
     *
     * @param currentPath The current path to the key that will be modified to reach the new path
     * @param key The key (name of the key) to get the current path for
     * @param previousDeepness The previous deepness (layer) that the iterator was at
     * @param deepness THe current deepness (layer) that the iterator is at
     * @return The String of the path (ex. "my.new.path")
     */
    private String getPath(String currentPath, String key, int previousDeepness, int deepness)
    {
        String newCurPath = currentPath;
        if(previousDeepness >= deepness)
        {
            newCurPath = regressPath(newCurPath, previousDeepness+1 - deepness);
        }
        if(!newCurPath.isEmpty()) newCurPath += ".";
        newCurPath += key;
        return newCurPath;
    }

    /**
     * Compiles a comment for a specific key in the yaml file
     *
     * @param lines All of the lines of the yaml file
     * @param index The index that this method will be getting the comments for
     * @param startingCommentIndex The starting comment index, the first line that the comments start on
     * @return The String of comments
     */
    private String compileComments(List<String> lines, int index, int startingCommentIndex)
    {
        StringBuilder commentsBuilder = new StringBuilder();
        for(int i = startingCommentIndex; i < index-1; i++)
        {
            String commentLine = lines.get(i);
            commentsBuilder.append(commentLine).append("\n");
        }
        return commentsBuilder.toString();
    }

    /**
     * Get the index that a comment starts on via backtracking
     *
     * @param lines All lines of the yaml file including comments
     * @param index The index that this method will be getting the starting comment index for
     * @return The starting comment index in int form
     */
    private int getStartingCommentIndex(List<String> lines, int index)
    {
        int startingCommentIndex = -1;
        for(int i = index-2; i >= 0; i--)
        {
            String curLine = lines.get(i).trim();
            if(!curLine.startsWith("#")) break;
            startingCommentIndex = i;
        }
        return startingCommentIndex;
    }

    /**
     * Go back a certain amount of paths to get the right path
     *
     * @param currentPath The String for the current path
     * @param amountToRegress The amount of directories to backtrack on
     * @return The new path
     */
    private String regressPath(String currentPath, int amountToRegress)
    {
        String[] splitStr = currentPath.split("\\.");
        StringBuilder newStr = new StringBuilder();
        for(int i = 0; i < splitStr.length-amountToRegress; i++)
        {
            if(i == 0) newStr.append(splitStr[i]);
            else newStr.append(".").append(splitStr[i]);
        }
        return newStr.toString();
    }

    /**
     * Simple helper method to get a key from a single line
     *
     * @param line Line to get key from, this line should be confirmed to have a key in it
     * @return The key in String format
     */
    private String getKey(String line)
    {
        return line.substring(0, line.indexOf(":"));
    }

    /**
     * Get the deepness of a line by counting spaces
     *
     * @param line The line to get the deepness for
     * @return The deepness in int form
     */
    private int getDeepness(String line)
    {
        float deepness = 0;
        for(int i = 0; i < line.length(); i++)
        {
            if(line.charAt(i) != ' ') break;
            deepness += 0.5f;
        }
        return (int)deepness;
    }
}
