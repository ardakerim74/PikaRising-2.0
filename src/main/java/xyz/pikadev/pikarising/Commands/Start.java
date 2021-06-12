package xyz.pikadev.pikarising.Commands;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import xyz.pikadev.pikarising.Actions.ClearPlayers;
import xyz.pikadev.pikarising.Actions.GivePickaxe;
import xyz.pikadev.pikarising.Actions.RevivePlayer;
import xyz.pikadev.pikarising.Actions.Timer;
import xyz.pikadev.pikarising.Donate.Book;
import xyz.pikadev.pikarising.Events.GameHandler;
import xyz.pikadev.pikarising.Files.Config;
import xyz.pikadev.pikarising.LavaInfo;
import xyz.pikadev.pikarising.PikaRising;

public class Start implements CommandExecutor, Listener {
    int lavRisingDelay = Config.get().getInt("YükselmeAralığı");
    int gameStartingTime = Config.get().getInt("OyunBaşlamaSüresi") * 60;

    BossBar bar = Bukkit.createBossBar(null, BarColor.YELLOW, BarStyle.SEGMENTED_10);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.hasPermission("pikarising.start")) {
                return false;
            } else if (GameHandler.gameStatus) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Pika&eRising &7&l»" + " &6 " + "Zaten Bir Oyun Oynanıyor."));
                return false;
            }
        }
        new Timer().allPlayers();
        new ClearPlayers().allPlayers();
        new GivePickaxe().allPlayers();
        new RevivePlayer().allPlayers();
        GameHandler.gameReady = true;
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setGameMode(GameMode.SURVIVAL);
        }
        PikaRising.instance.getServer().getScheduler().scheduleSyncDelayedTask(PikaRising.instance, new Runnable() {
            public void run() {
                doLava();
                new Book().removeBook();
            }
        }, 20L * ((long) gameStartingTime));
        return true;
    }

    private void lavaTimer() {
        PikaRising.instance.getServer().getScheduler().scheduleSyncDelayedTask(PikaRising.instance, new Runnable() {
            public void run() {
                doLava();
            }
        }, 20L * ((long) lavRisingDelay) - (20L * 10L));
    }

    private void doLava() {
        bar.setTitle("§e10 Saniye Sonra Lav Yükselecek!");
        bar.setColor(BarColor.YELLOW);
        bar.setProgress(1.0);
        bar.setVisible(true);
        for (Player player : Bukkit.getOnlinePlayers()) {
            bar.addPlayer(player);
        }

        PikaRising.instance.getServer().getScheduler().scheduleSyncDelayedTask(PikaRising.instance, new Runnable() {
            public void run() {
                bar.setTitle("§e5 Saniye Sonra Lav Yükselecek!");
                bar.setColor(BarColor.YELLOW);
                bar.setProgress(1.0);
                bar.setVisible(true);
                for (Player player : Bukkit.getOnlinePlayers()) {
                    bar.addPlayer(player);
                }
            }
        }, 20L * 5L);

        PikaRising.instance.getServer().getScheduler().scheduleSyncDelayedTask(PikaRising.instance, new Runnable() {
            public void run() {
                bar.setTitle("§e4 Saniye Sonra Lav Yükselecek!");
                bar.setColor(BarColor.YELLOW);
                bar.setProgress(0.80);
            }
        }, 20L * 6L);

        PikaRising.instance.getServer().getScheduler().scheduleSyncDelayedTask(PikaRising.instance, new Runnable() {
            public void run() {
                bar.setTitle("§e3 Saniye Sonra Lav Yükselecek!");
                bar.setColor(BarColor.YELLOW);
                bar.setProgress(0.60);
            }
        }, 20L * 7L);

        PikaRising.instance.getServer().getScheduler().scheduleSyncDelayedTask(PikaRising.instance, new Runnable() {
            public void run() {
                bar.setTitle("§e2 Saniye Sonra Lav Yükselecek!");
                bar.setColor(BarColor.YELLOW);
                bar.setProgress(0.40);
            }
        }, 20L * 8L);

        PikaRising.instance.getServer().getScheduler().scheduleSyncDelayedTask(PikaRising.instance, new Runnable() {
            public void run() {
                bar.setTitle("§e1 Saniye Sonra Lav Yükselecek!");
                bar.setColor(BarColor.YELLOW);
                bar.setProgress(0.20);
            }
        }, 20L * 9L);

        PikaRising.instance.getServer().getScheduler().scheduleSyncDelayedTask(PikaRising.instance, new Runnable() {
            public void run() {
                bar.setTitle("§cLav Bir Kademe Daha Yükseldi!");
                bar.setColor(BarColor.RED);
                bar.setProgress(1.0);
                LavaInfo lavaInfo = PikaRising.instance.getLavaInfo();

                World world = lavaInfo.bottomRight.getWorld();
                Location edgeMin = lavaInfo.bottomRight;
                Location edgeMax = lavaInfo.topLeft;

                for (int x = edgeMin.getBlockX(); x <= edgeMax.getBlockX(); x++) {
                    for (int y = edgeMin.getBlockY(); y <= edgeMax.getBlockY(); y++) {
                        for (int z = edgeMin.getBlockZ(); z <= edgeMax.getBlockZ(); z++) {
                            Block block = new Location(world, x, y, z).getBlock();

                            if (block.getType() == Material.AIR) {
                                block.setType(PikaRising.instance.getBlock());
                            }
                        }
                    }
                }
                if (edgeMax.getBlockY() >= 64) {
                    if (edgeMax.getBlockY() < 255) {
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            if (player.getGameMode().equals(GameMode.SURVIVAL)) {
                                if (player.getLocation().getBlockY() <= edgeMax.getBlockY() - 1) {
                                    player.playSound(player.getLocation(), Sound.ENTITY_GHAST_HURT, 1, 1);
                                    player.damage(player.getHealth());
                                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&6Pika&eRising &7&l» "
                                            + ChatColor.YELLOW + player.getName()
                                            + " §cAdlı Oyuncu Bug Kullanmaya Çalışırken Yakalandı. :)"));
                                }
                            }
                        }
                    }
                }

                lavaInfo.IncreaseCurrentLevel();
                bar.removeAll();

                if (GameHandler.gameStatus) {
                    lavaTimer();
                }
            }
        }, 20L * 10L);
    }
}
