package xyz.pikadev.pikarising.Actions;

import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import xyz.pikadev.pikarising.Events.GameHandler;
import xyz.pikadev.pikarising.PikaRising;

public class GameWinner implements Listener {
    public static int onlinePlayers;
    public static int survivors;
    public static int dies;
    public static Player lastPlayer;

    @EventHandler
    public void handler(PlayerDeathEvent event) {
        onlinePlayers = 0;
        survivors = -1;
        dies = 0;
        for (Player player : Bukkit.getOnlinePlayers()) {
            onlinePlayers++;
            if (player.getGameMode() == GameMode.SURVIVAL) {
                survivors++;
                lastPlayer = player;
            }
            if (player.getGameMode() == GameMode.SPECTATOR) {
                dies++;
            }
        }
        switch (survivors) {
            case 2:
                PikaRising.instance.sendMessage("§bBüyük Final, Geriye Son §c2 §bKişi Kaldı.");
                break;
            case 1:
                PikaRising.instance.sendMessage("§aVeee Kazanaaan §6" + lastPlayer.getName());
                GameHandler.gameReady = false;
                GameHandler.gameStatus = false;
                Firework fw = (Firework) lastPlayer.getWorld().spawnEntity(lastPlayer.getLocation().add(0,3,0), EntityType.FIREWORK);
                FireworkMeta fwm = fw.getFireworkMeta();

                fwm.setPower(2);
                fwm.addEffect(FireworkEffect.builder().withColor(Color.LIME).flicker(true).build());

                fw.setFireworkMeta(fwm);
                fw.detonate();

                for(int i = 0;i<5; i++){
                    Firework fw2 = (Firework) lastPlayer.getWorld().spawnEntity(lastPlayer.getLocation().add(0,3,0), EntityType.FIREWORK);
                    fw2.setFireworkMeta(fwm);
                }
                break;
            default:
                PikaRising.instance.sendMessage("§eGeriye Son §c" + survivors + " §eKişi Kaldı.");
                break;
        }
    }
}
