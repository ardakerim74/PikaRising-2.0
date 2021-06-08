package xyz.pikadev.pikarising.Actions;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class RevivePlayer implements ThoseAffected{

    @Override
    public void allPlayers() {
        for(Player player : Bukkit.getOnlinePlayers()){
            player.setHealth(player.getMaxHealth());
            player.setFoodLevel(20);
        }
    }
}
