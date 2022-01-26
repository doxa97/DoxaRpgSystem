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
                        if (event.getEntity().getName().contains("LV.1")) {
                            event.setDroppedExp(0);
                            long[] stat;
                            stat = s.getStat(player.getUniqueId().toString());
                            s.setStat(player.getUniqueId().toString(), stat);
                            s.LevelUP(stat, player);
                        }
                    }
                    if (event.getEntityType() == EntityType.SLIME){
                        if (event.getEntity().getName().contains("LV.1")) {
                            event.setDroppedExp(0);
                            long[] stat;
                            stat = s.getStat(player.getUniqueId().toString());
                            s.setStat(player.getUniqueId().toString(), stat);
                            s.LevelUP(stat, player);
                        }
                        if (event.getEntity().getName().contains("LV.2")) {
                            event.setDroppedExp(0);
                            long[] stat;
                            stat = s.getStat(player.getUniqueId().toString());
                            stat[2] = stat[2] + 1;
                            s.setStat(player.getUniqueId().toString(), stat);
                            s.LevelUP(stat, player);
                        }
                        if (event.getEntity().getName().contains("LV.3")) {
                            event.setDroppedExp(0);
                            long[] stat;
                            stat = s.getStat(player.getUniqueId().toString());
                            stat[2] = stat[2] + 2;
                            s.setStat(player.getUniqueId().toString(), stat);
                            s.LevelUP(stat, player);
                        }
                    }
                    if (event.getEntityType() == EntityType.ZOMBIE_VILLAGER) {
                        if (event.getEntity().getName().contains("LV.4")) {
                            event.setDroppedExp(0);
                            long[] stat;
                            stat = s.getStat(player.getUniqueId().toString());
                            stat[2] = stat[2] + 4;
                            s.setStat(player.getUniqueId().toString(), stat);
                            s.LevelUP(stat, player);
                        }
                    }
                    if (event.getEntityType() == EntityType.ZOMBIE) {
                        if (event.getEntity().getName().contains("LV.5")) {
                            if (event.getEntity().getName().contains("일반")) {
                                event.setDroppedExp(0);
                                long[] stat;
                                stat = s.getStat(player.getUniqueId().toString());
                                stat[2] = stat[2] + 7;
                                s.setStat(player.getUniqueId().toString(), stat);
                                s.LevelUP(stat, player);
                            }
                            if (event.getEntity().getName().contains("변종")) {
                                event.setDroppedExp(0);
                                long[] stat;
                                stat = s.getStat(player.getUniqueId().toString());
                                stat[2] = stat[2] + 8;
                                s.setStat(player.getUniqueId().toString(), stat);
                                s.LevelUP(stat, player);
                            }
                        }
                        if (event.getEntity().getName().contains("LV.6")) {
                            if (event.getEntity().getName().contains("숙주")) {
                                event.setDroppedExp(0);
                                long[] stat;
                                stat = s.getStat(player.getUniqueId().toString());
                                stat[2] = stat[2] + 11;
                                s.setStat(player.getUniqueId().toString(), stat);
                                s.LevelUP(stat, player);
                            }
                        }
                    }
                    if (event.getEntityType() == EntityType.SKELETON) {
                        if (event.getEntity().getName().contains("LV.7")) {
                            if (event.getEntity().getName().contains("해골")) {
                                event.setDroppedExp(0);
                                long[] stat;
                                stat = s.getStat(player.getUniqueId().toString());
                                stat[2] = stat[2] + 14;
                                s.setStat(player.getUniqueId().toString(), stat);
                                s.LevelUP(stat, player);
                            }
                        }
                        if (event.getEntity().getName().contains("LV.8")) {
                            if (event.getEntity().getName().contains("해골")) {
                                event.setDroppedExp(0);
                                long[] stat;
                                stat = s.getStat(player.getUniqueId().toString());
                                stat[2] = stat[2] + 18;
                                s.setStat(player.getUniqueId().toString(), stat);
                                s.LevelUP(stat, player);
                            }
                        }
                        if (event.getEntity().getName().contains("LV.9")) {
                            if (event.getEntity().getName().contains("해골")) {
                                event.setDroppedExp(0);
                                long[] stat;
                                stat = s.getStat(player.getUniqueId().toString());
                                stat[2] = stat[2] + 24;
                                s.setStat(player.getUniqueId().toString(), stat);
                                s.LevelUP(stat, player);
                            }
                        }
                    }
                }
            }
        }
    }
}
