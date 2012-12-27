package net.krsmes.bukkit.plugins.groovy;

import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public class GroovyCommand implements CommandExecutor {

    private final KrsmesGroovyPlugin groovyPlugin;

    public GroovyCommand(KrsmesGroovyPlugin groovyPlugin) {
        this.groovyPlugin = groovyPlugin;
    }


//
// CommandExecutor
//

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String text = join(" ", args);

        Object result = parseScriptText(sender, text).run();

        sender.sendMessage((result == null) ? "[" + command.getName() + " done] " + text : result.toString());
        return true;
    }


//
// helper methods
//

    private Script parseScriptText(CommandSender sender, String text) {
        GroovyShell shell = groovyPlugin.getShell(sender);
        return shell.parse(text);
    }


    private static String join(String glue, String... s) {
        StringBuilder out = new StringBuilder();
        int k = s.length;
        if (k > 0) {
            out.append(s[0]);
            for (int x = 1; x < k; ++x) {
                out.append(glue).append(s[x]);
            }
        }
        return out.toString();
    }

}
