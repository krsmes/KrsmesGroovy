package net.krsmes.bukkit.plugins.groovy

import net.krsmes.bukkit.plugins.groovy.GroovyCommand
import net.krsmes.bukkit.plugins.groovy.KrsmesGroovyPlugin
import org.junit.Test


class GroovyCommandTest {

    KrsmesGroovyPlugin plugin = new KrsmesGroovyPlugin()
    GroovyCommand command = new GroovyCommand(plugin)

    @Test void commandShouldRunScript() {
        assert command
    }

}
