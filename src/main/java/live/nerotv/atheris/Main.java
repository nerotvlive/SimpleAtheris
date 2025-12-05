package live.nerotv.atheris;

import live.nerotv.atheris.commands.*;
import live.nerotv.atheris.listeners.AsyncChatListener;
import live.nerotv.atheris.utilities.Strings;
import live.nerotv.atheris.utilities.storage.Storage;
import live.nerotv.atheris.utilities.storage.types.Config;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Main extends JavaPlugin {

    private static Config pluginConfig = null;
    private static Config languageConfig = null;
    private static Storage saver = null;

    @Override
    public void onLoad() {
        initConfig();
    }

    @Override
    public void onEnable() {
        initCommands();
        initListeners();

        System.gc();
    }

    @Override
    public void onDisable() {

    }

    private void initConfig() {
        pluginConfig = new Config("plugins/atheris/config.yml");

        //CHAT CONFIG GENERATION
        pluginConfig.checkEntry("config.general.chat.enable",true);
        pluginConfig.checkEntry("config.general.chat.normal.format","§7%character%§8: §f%message%");
        pluginConfig.checkEntry("config.general.chat.normal.range",30.0D);
        pluginConfig.checkEntry("config.general.chat.global.format","§7[g] %player%§8: §f%message%");
        pluginConfig.checkEntry("config.general.chat.global.perWorld",false);
        pluginConfig.checkEntry("config.general.chat.shout.format","§7[!] %character%§8: §f%message%");
        pluginConfig.checkEntry("config.general.chat.shout.range",100.0D);
        pluginConfig.checkEntry("config.general.chat.whisper.format","§7[w] %character%§8: §f%message%");
        pluginConfig.checkEntry("config.general.chat.whisper.range",10.0D);

        //GENERAL SYSTEM CONFIG GENERATION
        pluginConfig.checkEntry("config.system.general.language","plugins/atheris/lang/de.yml");
        pluginConfig.checkEntry("config.system.general.maintenance.enable",true);
        pluginConfig.checkEntry("config.system.general.stop.countdown",25);
        pluginConfig.checkEntry("config.system.general.restart.countdown",25);
        pluginConfig.checkEntry("config.system.general.restart.automatic","04:30");

        //COMMAND FEEDBACK CONFIG GENERATION
        pluginConfig.checkEntry("config.system.general.commandFeedback.enable",true);
        pluginConfig.checkEntry("config.system.general.commandFeedback.normal.sound", "ENTITY_CHICKEN_EGG");
        pluginConfig.checkEntry("config.system.general.commandFeedback.normal.volume", 100);
        pluginConfig.checkEntry("config.system.general.commandFeedback.normal.pitch", 100);
        pluginConfig.checkEntry("config.system.general.commandFeedback.error.sound", "BLOCK_ANVIL_BREAK");
        pluginConfig.checkEntry("config.system.general.commandFeedback.error.volume", 100);
        pluginConfig.checkEntry("config.system.general.commandFeedback.error.pitch", 100);

        //SAVER CONFIG GENERATION
        pluginConfig.checkEntry("config.system.saver.useYamlStorage",false);
        pluginConfig.checkEntry("config.system.saver.mariadb.enable",false);
        pluginConfig.checkEntry("config.system.saver.mariadb.host","127.0.0.1");
        pluginConfig.checkEntry("config.system.saver.mariadb.port",3306);
        pluginConfig.checkEntry("config.system.saver.mariadb.username","mariadb-user");
        pluginConfig.checkEntry("config.system.saver.mariadb.password","mariadb-user-password");
        pluginConfig.checkEntry("config.system.saver.mariadb.database","mariadb-database");

        initLanguage();
        if(!initSaver()) {
            //TODO error message -> no persistent saved data
        }
        System.gc();
    }

    private void initLanguage() {
        languageConfig = new Config((String)pluginConfig.get("config.system.general.language"));

        languageConfig.checkEntry("strings.general.prefix.normal", Strings.prefix);
        languageConfig.checkEntry("strings.general.prefix.error", Strings.errPrefix);
        languageConfig.checkEntry("strings.general.errors.maintenanceKick", "§cDer Server befindet sich aktuell in §4Wartungsarbeiten§8, §cbitte versuche es später erneut§8!");
        languageConfig.checkEntry("strings.general.errors.noPermission", "§cDas darfst du nicht tun§8!");
        languageConfig.checkEntry("strings.general.errors.playerNeeded", "§cDas kannst du nur als Spieler tun§8!");
        languageConfig.checkEntry("strings.general.errors.playerNotFound", "§cDer Spieler %player% konnte nicht gefunden werden§8!");
        languageConfig.checkEntry("strings.general.errors.syntax", "§4Syntaxfehler§8: §c%syntax%");
    }

    private boolean initSaver() {
        try {
            String saverPath = "plugins/atheris/saver.";
            if ((boolean) pluginConfig.get("config.system.saver.useYamlStorage")) {
                saver = new Storage(saverPath + ".yml");
            } else {
                if ((boolean) pluginConfig.get("config.system.saver.mariadb.enable")) {
                    String mariadbHost = (String) pluginConfig.get("config.system.saver.mariadb.host");
                    String mariadbPort = (String) pluginConfig.get("config.system.saver.mariadb.port");
                    String mariadbDatabase = (String) pluginConfig.get("config.system.saver.mariadb.database");
                    String mariadbUsername = (String) pluginConfig.get("config.system.saver.mariadb.username");
                    String mariadbPassword = (String) pluginConfig.get("config.system.saver.mariadb.password");
                    saver = new Storage(mariadbHost, mariadbPort, mariadbDatabase, mariadbUsername, mariadbPassword);
                } else {
                    saver = new Storage(saverPath + ".db");
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void initCommands() {
        Objects.requireNonNull(getCommand("atheris")).setExecutor(new AtherisCommand());
        Objects.requireNonNull(getCommand("book")).setExecutor(new BookCommand());
        Objects.requireNonNull(getCommand("broadcast")).setExecutor(new BroadcastCommand());
        Objects.requireNonNull(getCommand("global")).setExecutor(new GlobalCommand());
        Objects.requireNonNull(getCommand("name")).setExecutor(new NameCommand());
        Objects.requireNonNull(getCommand("reveal")).setExecutor(new RevealCommand());
        Objects.requireNonNull(getCommand("shout")).setExecutor(new ShoutCommand());
        Objects.requireNonNull(getCommand("whisper")).setExecutor(new WhisperCommand());
    }

    private void initListeners() {
        Bukkit.getPluginManager().registerEvents(new AsyncChatListener(), this);
    }

    public static Config getPluginConfig() {
        return pluginConfig;
    }

    public static Config getLanguageConfig() {
        return languageConfig;
    }

    public static Storage getSaver() {
        return saver;
    }
}