package pt.joelcosta.stvanish;

import org.bukkit.plugin.java.JavaPlugin;
import pt.joelcosta.stvanish.commands.VanishCommand;

public final class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        loadConfig();
        getCommand("vanish").setExecutor(new VanishCommand());
    }

    @Override
    public void onDisable() {
    }

    private void loadConfig(){
        getConfig().options().copyDefaults(false);
        saveDefaultConfig();
    }

    public static Main getInstance() {
        return instance;
    }
}
