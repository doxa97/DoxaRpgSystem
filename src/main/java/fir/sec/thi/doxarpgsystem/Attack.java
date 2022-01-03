package fir.sec.thi.doxarpgsystem;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.w3c.dom.Entity;

public class Attack {
    public void Attack(EntityDamageByEntityEvent event){
        if (event.getDamager() != null && event.getDamager() instanceof Projectile){
            Projectile p = (Projectile) event.getDamager();
            if (p.getType() == EntityType.ARROW){
                if (p.getShooter() != null && p.getShooter() instanceof Player && event.getEntity() instanceof Player){

                }
                if (p.getShooter() != null && p.getShooter() instanceof Player && !(event.getEntity() instanceof Player)){

                }
            }
        }
        else {
            if (event.getDamager() !=null && event.getDamager() instanceof Entity){
                if (event.getDamager() != null && event.getDamager() instanceof Player && event.getEntity() instanceof Player){

                }
                if (event.getDamager() != null && event.getDamager() instanceof Player && event.getEntity() instanceof Player){

                }
            }
        }
    }
}
