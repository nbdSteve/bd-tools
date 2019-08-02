package dev.nuer.bd.tools.managers;

import dev.nuer.bd.tools.BdTools;
import dev.nuer.bd.tools.tools.Tool;
import dev.nuer.bd.tools.tools.ToolType;

import java.util.HashMap;

public class ToolManager {
    private static HashMap<Integer, Tool> tools;

    public ToolManager() {
        tools = new HashMap<>();
        for (int i = 1; i <= FileManager.get("config").getInt("number-of-tools"); i++) {
            Tool tool = new Tool(i,
                    ToolType.valueOf(FileManager.get("config").getString("tools." + i + ".type")),
                    FileManager.get("config").getInt("tools." + i + ".reuse-delay"));
            tools.put(i, tool);
        }
        BdTools.log.info("Successfully loaded all tools into internal configuration.");
    }

    public static HashMap<Integer, Tool> getTools() {
        return tools;
    }

    public static Tool getToolByID(int configID) {
        return tools.get(configID);
    }
}
