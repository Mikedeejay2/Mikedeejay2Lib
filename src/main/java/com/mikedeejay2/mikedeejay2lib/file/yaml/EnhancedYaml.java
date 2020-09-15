package com.mikedeejay2.mikedeejay2lib.file.yaml;

import com.mikedeejay2.mikedeejay2lib.util.chat.Chat;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.*;

public class EnhancedYaml extends YamlConfiguration
{
    // Path to Comment
    private Map<String, String> comments;

    public EnhancedYaml()
    {
        comments = new HashMap<>();
    }

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

    @Override
    public void loadFromString(String contents) throws InvalidConfigurationException
    {
        super.loadFromString(contents);
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

            int startingCommentIndex = getStartingCommentIndex(lines, index);
            if(startingCommentIndex == -1) continue;
            String comment = compileComments(lines, index, startingCommentIndex);
            comments.put(currentPath, comment);
        }
    }

    private String getPath(String currentPath, String key, int previousDeepness, int deepness)
    {
        String newCurPath = currentPath;
        if(previousDeepness > deepness)
        {
            newCurPath = regressPath(newCurPath, previousDeepness - deepness);
        }
        else
        {
            if(!newCurPath.isEmpty()) newCurPath += ".";
            newCurPath += key;
        }
        return newCurPath;
    }

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

    private String getKey(String str)
    {
        return str.substring(0, str.indexOf(":"));
    }

    private int getDeepness(String str)
    {
        float deepness = 0;
        for(int i = 0; i < str.length(); i++)
        {
            if(str.charAt(i) != ' ') break;
            deepness += 0.5f;
        }
        return (int)deepness;
    }
}
