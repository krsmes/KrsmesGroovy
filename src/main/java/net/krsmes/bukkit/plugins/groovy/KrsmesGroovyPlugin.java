package net.krsmes.bukkit.plugins.groovy;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.logging.Logger;


public class KrsmesGroovyPlugin extends JavaPlugin {

    private static final Logger LOG = Logger.getLogger("Minecraft");

    private static final String META_BINDING = ".binding";

    private static final String VAR_PLAYER = "me";
    private static final String VAR_PLUGIN = "grv";
    private static final String VAR_SERVER = "srv";

    private Binding globalBinding;


//
// JavaPlugin
//

    @Override
    public void onDisable() {
        LOG.info("KrsmesGroovyPlugin::onDisable");
        globalBinding = null;
    }


    @Override
    public void onEnable() {
        LOG.info("KrsmesGroovyPlugin::onEnable");
        globalBinding = initializeBinding();
        getCommand("grv").setExecutor(new GroovyCommand(this));
    }


//
// public methods
//

    public Binding getBinding(CommandSender sender) {
        return (sender instanceof Player) ? getBinding((Player) sender) : globalBinding;
    }

    public GroovyShell getShell(CommandSender sender) {
        return new GroovyShell(getClassLoader(), getBinding(sender));
    }


//
// helper methods
//

    private Binding getBinding(Player player) {
        List<MetadataValue> values = player.getMetadata(getName() + META_BINDING);
        Binding playerBinding;
        if (values.isEmpty()) {
            playerBinding = initializeBinding();
            playerBinding.setVariable(VAR_PLAYER, player);
            player.setMetadata(getName() + META_BINDING, new FixedMetadataValue(this, playerBinding));
        }
        else {
            playerBinding = (Binding) values.get(0).value();
        }
        return playerBinding;
    }

    private Binding initializeBinding() {
        Binding binding = new Binding();
        binding.setVariable(VAR_PLUGIN, this);
        binding.setVariable(VAR_SERVER, this.getServer());
        return binding;
    }

}
