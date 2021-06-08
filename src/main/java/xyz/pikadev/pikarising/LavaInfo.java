package xyz.pikadev.pikarising;

import org.bukkit.Location;
import xyz.pikadev.pikarising.Files.Config;

public class LavaInfo {
    public Location bottomRight;
    public Location topLeft;

    public int currentLevel;

    public LavaInfo(Location bottomRight, Location topLeft, int currentLevel) {
        super();
        this.bottomRight = bottomRight;
        this.topLeft = topLeft;
        this.currentLevel = currentLevel;

        int increaseAmount = Config.get().getInt("YükselmeSeviyesi");

        this.bottomRight.setY(this.currentLevel);
        this.topLeft.setY(this.currentLevel + increaseAmount - 1);
    }

    public void IncreaseCurrentLevel() {
        int increaseAmount = Config.get().getInt("YükselmeSeviyesi");

        this.currentLevel += increaseAmount;

        this.bottomRight.setY(this.currentLevel);
        this.topLeft.setY(this.currentLevel + increaseAmount - 1);
    }
}
