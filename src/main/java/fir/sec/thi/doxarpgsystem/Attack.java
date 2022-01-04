package fir.sec.thi.doxarpgsystem;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.w3c.dom.Entity;

public class Attack {

    public Stat s = new Stat();
    public DamageCalculator DC = new DamageCalculator();

    @EventHandler
    public void RangeAttack(EntityShootBowEvent event){
        if (event.getEntityType() == EntityType.PLAYER){
            Player player = (Player) event.getEntity();
            long[] stat = new long[13];
            stat = s.getStat(player.getUniqueId().toString());
            stat[12] = (int)(event.getForce()*100);
            s.setStat(player.getUniqueId().toString(), stat);
        }
    }

    public void Attack(EntityDamageByEntityEvent event){
        if (event.getDamager() != null && event.getDamager() instanceof Projectile){
            Projectile p = (Projectile) event.getDamager();
            if (p.getType() == EntityType.ARROW){
                if (p.getShooter() != null && p.getShooter() instanceof Player && event.getEntity() instanceof Player){
                    PlayerDamageByPlayer(event,(Player)p.getShooter(),(Player) event.getEntity(),(int)event.getDamage(),"활");
                }
                if (p.getShooter() != null && p.getShooter() instanceof Player && !(event.getEntity() instanceof Player)){
                    EntityDamageByPlayer(event,(Player) p.getShooter(),(int)event.getDamage(),"활");
                }
                if (p.getShooter() != null && !(p.getShooter() instanceof Player && !(event.getEntity() instanceof Player))){
                    PlayerDamageByEntity(event,(Player)event.getEntity());
                }
            }
        }
        else {
            if (event.getDamager() !=null && event.getDamager() instanceof Entity){
                if (event.getDamager() != null && event.getDamager() instanceof Player && event.getEntity() instanceof Player){
                    PlayerDamageByPlayer(event,(Player)event.getDamager(),(Player) event.getEntity(),(int)event.getDamage(),"근접");
                }
                if (event.getDamager() != null && event.getDamager() instanceof Player && event.getEntity() instanceof Player){
                    EntityDamageByPlayer(event,(Player)event.getDamager(),(int)event.getDamage(),"근접");
                }
                if (event.getDamager() != null && !(event.getDamager() instanceof Player) && event.getEntity() instanceof Player){
                    PlayerDamageByEntity(event, (Player) event.getEntity());
                }
            }
        }
    }

    public void PlayerDamageByPlayer(EntityDamageByEntityEvent event, Player attacker, Player defenser, int DefaultDamage, String AttackType) {
        long[] Astat = new long[13];
        Astat = s.getStat(attacker.getUniqueId().toString());
        long[] Dstat = new long[13];
        Dstat = s.getStat(defenser.getUniqueId().toString());
        if (AttackType == "활") {
            long Damage = DC.CombatRangeDamage(attacker, DefaultDamage, Astat[8]);
            Damage = DC.Critical(attacker, Astat[9], (int) Damage);
            Damage = (Damage*Astat[12]/100);
            Damage = Damage - Dstat[11];
            event.setDamage(Damage);
            if (Damage <= 0) {
                SendMessage(attacker);
            }

        } else if (AttackType == "근접") {
            long Damage = DC.CombatRangeDamage(attacker, DefaultDamage, Astat[8]);
            Damage = DC.Critical(attacker, Astat[9], (int) Damage);
            Damage = Damage - Dstat[11];
            event.setDamage(Damage);
            if (Damage <= 0) {
                SendMessage(attacker);
            }
        }
    }

    public void EntityDamageByPlayer(EntityDamageByEntityEvent event, Player attacker, int DefaultDamage, String AttackType) {
        long[] Astat = new long[13];
        Astat = s.getStat(attacker.getUniqueId().toString());
        if (AttackType == "활") {
            long Damage = DC.CombatRangeDamage(attacker, DefaultDamage, Astat[8]);
            Damage = DC.Critical(attacker, Astat[9], (int) Damage);
            event.setDamage(Damage);
            if (Damage <= 0) {
                SendMessage(attacker);
            }

        } else if (AttackType == "근접") {
            long Damage = DC.CombatRangeDamage(attacker, DefaultDamage, Astat[8]);
            Damage = DC.Critical(attacker, Astat[9], (int) Damage);
            event.setDamage(Damage);
            if (Damage <= 0) {
                SendMessage(attacker);
            }
        }
    }

    public void PlayerDamageByEntity(EntityDamageByEntityEvent event, Player defenser){
        long[] stat = new long[13];
        stat = s.getStat(defenser.getUniqueId().toString());
        if (event.getDamage() - stat[11] <= 0){
            event.setDamage(0.1);
        }
        else {
            event.setDamage(event.getDamage() - stat[11]);
        }
    }

    public void SendMessage(Player player){
        int a = DC.Random(1, 5);
        switch (a){
            case 1: player.sendMessage("[ Ercanel ]상대방의 방어력이 높습니다...!");
            case 2: player.sendMessage("[ Ercanel ]상대방의 방어 자세를 뚫을 수 없습니다...!");
            case 3: player.sendMessage("[ Ercanel ]상대방이 너무 견고합니다...!");
            case 4: player.sendMessage("[ Ercanel ]검이 상대의 갑옷에 팅겼습니다...!");
            case 5: player.sendMessage("[ Ercanel ]상대의 갑옷보다 무기가 무딘 것 같습니다...!");
        }
    }

}
