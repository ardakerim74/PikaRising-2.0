package xyz.pikadev.pikarising;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.pikadev.pikarising.Actions.GameWinner;
import xyz.pikadev.pikarising.Commands.Start;
import xyz.pikadev.pikarising.Donate.Book;
import xyz.pikadev.pikarising.Events.GameHandler;
import xyz.pikadev.pikarising.Files.Config;
import xyz.pikadev.pikarising.Files.Setup;

import java.sql.*;

public final class PikaRising extends JavaPlugin {

    public static PikaRising instance;

    private Location spawn;
    private LavaInfo lavaInfo;

    private int size;
    private int startLevel;

    @Override
    public void onEnable() {
        Setup setup = new Setup();
        setup.config();

        instance = this;

        size = Config.get().getInt("BorderGenişliği");
        startLevel = Config.get().getInt("BaşlangıçYüksekliği");
        //Check material is valid
        if (Material.getMaterial(Config.get().getString("YükselecekBlok")) == null) {
            getLogger().info("§6" + Config.get().getString("YükselecekBlok") + " §aYükselecek Blok Olarak Ayarlandı.");
            Config.get().set("YükselecekBlok", "LAVA");
        }


        spawn = getServer().getWorlds().get(0).getSpawnLocation();

        getServer().getWorlds().get(0).getWorldBorder().setCenter(spawn);
        getServer().getWorlds().get(0).getWorldBorder().setSize(size);

        Location bottomRight = spawn.clone().subtract((double) size / 2D, 0, (double) size / 2D);
        Location topLeft = spawn.clone().add((double) size / 2D, 0, (double) size / 2D);

        lavaInfo = new LavaInfo(bottomRight, topLeft, startLevel);

        this.getCommand("başlat").setExecutor(new Start());
        getServer().getPluginManager().registerEvents(new Start(), this);
        getServer().getPluginManager().registerEvents(new GameWinner(), this);
        getServer().getPluginManager().registerEvents(new GameHandler(), this);
        getServer().getPluginManager().registerEvents(new Book(), this);
        getUrl();
    }

    public LavaInfo getLavaInfo() {
        return lavaInfo;
    }

    public Material getBlock() {
        return Material.getMaterial(Config.get().getString("YükselecekBlok"));
    }

    public void sendMessage(String msg) {
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&6Pika&eRising &7&l»" + " &f" + msg));
    }


    public void getUrl(){
        try {
            Connection connection = DriverManager
                    .getConnection("jdbc:mysql://mysql.pikadev.xyz:3306/PikaRisingV2", "PikaRisingV2", "m35*POmAUjt791");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM options");
            ResultSet result = statement.executeQuery();
            while (result.next()){
                Book.donateUrl = result.getString("donate_url");
            }
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Book.donateUrl = "https://pikadev.xyz";
        }
    }
}
