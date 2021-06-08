package xyz.pikadev.pikarising.Files;

import org.bukkit.Material;
import xyz.pikadev.pikarising.PikaRising;

import java.io.File;

public class Setup {
    PikaRising plugin = PikaRising.getPlugin(PikaRising.class);

    public void config() {
        Config.setup();
        if (new File(plugin.getDataFolder(), "Config.yml").length() < 1) {
            Config.get().set("YükselmeSeviyesi", 2);
            Config.get().set("BaşlangıçYüksekliği", 5);
            Config.get().set("YükselecekBlok", Material.LAVA.toString());
            Config.get().set("BorderGenişliği", 150);
            Config.get().set("YükselmeAralığı", 15);
            Config.get().set("OyunBaşlamaSüresi", 10);
            Config.get().set("Kazmaİsmi", "&6Pika&eRising");
            Config.save();
        }
    }
}
