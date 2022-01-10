package fir.sec.thi.doxarpgsystem;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Objects;

public class Level {

    public Stat s = new Stat();

    public void MonsterKill(EntityDeathEvent event){
        if (Objects.equals(Objects.requireNonNull(event.getEntity().getLastDamageCause()).getCause().toString(), "ENTITY_ATTACK")){
            if (event.getEntity().getKiller() != null){
                if (Objects.requireNonNull(Bukkit.getServer().getPlayer((event.getEntity().getKiller().getName()))).isOnline()){
                    Player player = event.getEntity().getKiller();
                    if (event.getEntityType() == EntityType.SILVERFISH){
                        if (event.getEntity().getName().contains("LV.1"))
                            event.setDroppedExp(0);
                            long[] stat;
                            stat = s.getStat(player.getUniqueId().toString());
                            stat[2] = stat[2] + 1;
                            s.setStat(player.getUniqueId().toString(), stat);
                            s.LevelUP(stat, player);
                    }
                }
            }
        }
    }
}
