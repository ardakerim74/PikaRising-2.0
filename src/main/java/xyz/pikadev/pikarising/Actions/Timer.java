package xyz.pikadev.pikarising.Actions;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import xyz.pikadev.pikarising.Events.GameHandler;
import xyz.pikadev.pikarising.Files.Config;

public class Timer implements ThoseAffected, Runnable {
    BossBar bar = Bukkit.createBossBar(null, BarColor.YELLOW, BarStyle.SEGMENTED_10);
    int time = Config.get().getInt("OyunBaşlamaSüresi") * 60;
    double timeReaming = 1.0 / time;

    @Override
    public void allPlayers() {
        Timer timer = new Timer();
        Thread thread = new Thread(timer);
        thread.start();
    }

    @Override
    public void run() {
        while (time != 0) {
            try {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    switch (time) {
                        case 60:
                            player.sendMessage("§6Pika§eRising §7§l» " + "§eLavın Yükselmesine Son §c60 Saniye §eKaldı.");
                            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                            break;
                        case 30:
                            player.sendMessage("§6Pika§eRising §7§l» " + "§eLavın Yükselmesine Son §c30 Saniye §eKaldı.");
                            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                            break;
                        case 10:
                            player.sendMessage("§6Pika§eRising §7§l» " + "§eLavın Yükselmesine Son §c10 Saniye §eKaldı.");
                            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                            break;
                        case 5:
                            player.sendMessage("§6Pika§eRising §7§l» " + "§eLavın Yükselmesine Son §c5 Saniye §eKaldı.");
                            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                            break;
                        case 1:
                            player.sendMessage("§6Pika§eRising §7§l» " + "§6Lav Yükseliyor!!!");
                            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                            break;
                    }
                }

                time -= 1;
                bossBar();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        GameHandler.gameStatus = true;

        bar.setTitle("§aPVP Açıldı");
        bar.setColor(BarColor.GREEN);
        bar.setProgress(1.0);
        bar.setVisible(true);
        for (Player player : Bukkit.getOnlinePlayers()) {
            bar.addPlayer(player);
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();

        }
        bar.removeAll();
        bar.setTitle("§eLav Yükseliyor!!!");
        bar.setColor(BarColor.YELLOW);
        bar.setProgress(1.0);
        bar.setVisible(true);
        for (Player player : Bukkit.getOnlinePlayers()) {
            bar.addPlayer(player);
        }
        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        bar.removeAll();
    }

    public void bossBar() {
        bar.setTitle("§eKalan Süre §6" + time / 60 + " Dakika " + time % 60 + " Saniye");
        bar.setProgress(bar.getProgress() - timeReaming);
        bar.setVisible(true);
        for (Player player : Bukkit.getOnlinePlayers()) {
            bar.addPlayer(player);
        }
        if (time <= 1) {
            bar.removeAll();
        }

    }
}
