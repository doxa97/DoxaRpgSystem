package fir.sec.thi.doxarpgsystem;

import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Attack {

    public Stat s = new Stat();
    public DamageCalculator DC = new DamageCalculator();

    public void Attack(EntityDamageByEntityEvent event){
        if (event.getDamager() != null && event.getDamager() instanceof Projectile){
            Projectile p = (Projectile) event.getDamager();
            if (p.getType() == EntityType.ARROW){
                if (p.getShooter() != null && p.getShooter() instanceof Player && event.getEntity() instanceof Player){
                    PlayerDamageByPlayer(event,(Player)p.getShooter(),(Player) event.getEntity(),(int)event.getDamage(),"B");
                }
                if (p.getShooter() != null && p.getShooter() instanceof Player && !(event.getEntity() instanceof Player)){
                    EntityDamageByPlayer(event,(Player) p.getShooter(),(int)event.getDamage(),"B");
                }
                if (p.getShooter() != null && !(p.getShooter() instanceof Player) && event.getEntity() instanceof Player){
                    PlayerDamageByEntity(event,(Player)event.getEntity());
                }
            }
        }
        else {
            if (event.getDamager() !=null && event.getDamager() instanceof LivingEntity){
                if (event.getDamager() != null && event.getDamager() instanceof Player && event.getEntity() instanceof Player){
                    PlayerDamageByPlayer(event,(Player)event.getDamager(),(Player) event.getEntity(),(int)event.getDamage(),"A");
                }
                if (event.getDamager() != null && event.getDamager() instanceof Player && !(event.getEntity() instanceof Player)){
                    EntityDamageByPlayer(event,(Player)event.getDamager(),(int)event.getDamage(),"A");
                }
                if (event.getDamager() != null && !(event.getDamager() instanceof Player) && event.getEntity() instanceof Player){
                    PlayerDamageByEntity(event, (Player) event.getEntity());
                }
            }
        }
    }

    public void PlayerDamageByPlayer(EntityDamageByEntityEvent event, Player attacker, Player defender, int DefaultDamage, String AttackType) {
        long[] Astat = new long[13];
        Astat = s.getStat(attacker.getUniqueId().toString());
        long[] Dstat = new long[13];
        Dstat = s.getStat(defender.getUniqueId().toString());
        if (AttackType == "B") {
            long Damage = DC.CombatRangeDamage(attacker, DefaultDamage, Astat[8]);
            Damage = DC.Critical(attacker, Astat[9], (int) Damage);
            Damage = Damage - Dstat[11];
            event.setDamage(Damage);
            if (Damage <= 0) {
                SendMessage(attacker);
            }
        }
        else {
               if (AttackType == "A") {
                long Damage = DC.CombatDamage(attacker, DefaultDamage, Astat[7]);
                Damage = DC.Critical(attacker, Astat[9], (int) Damage);
                Damage = Damage - Dstat[11];
                event.setDamage(Damage);
                if (Damage <= 0) {
                    SendMessage(attacker);
                }
            }
        }
    }

    public void EntityDamageByPlayer(EntityDamageByEntityEvent event, Player attacker, int DefaultDamage, String AttackType) {
        long[] Astat = new long[13];
        Astat = s.getStat(attacker.getUniqueId().toString());
        if (AttackType == "B") {
            long Damage = DC.CombatRangeDamage(attacker, DefaultDamage, Astat[8]);
            Damage = DC.Critical(attacker, Astat[9], (int) Damage);
            Damage = (Damage*Astat[12])*100;
            event.setDamage(Damage);
            if (Damage <= 0) {
                SendMessage(attacker);
            }
            else {
                attacker.sendMessage(Damage + "");
            }

        } else if (AttackType == "A") {
            long Damage = DC.CombatDamage(attacker, DefaultDamage, Astat[7]);
            Damage = DC.Critical(attacker, Astat[9], (int) Damage);
            event.setDamage(Damage);
            if (Damage <= 0) {
                SendMessage(attacker);
            }
        }
    }

    public void PlayerDamageByEntity(EntityDamageByEntityEvent event, Player defender){
        long[] stat = new long[13];
        stat = s.getStat(defender.getUniqueId().toString());
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
            case 5: player.sendMessage(ChatColor.DARK_AQUA +"[ Ercanel ]"+ChatColor.WHITE+"상대방의 방어력이 높습니다...!");
            case 4: player.sendMessage(ChatColor.DARK_AQUA +"[ Ercanel ]"+ChatColor.WHITE+"상대방의 방어 자세를 뚫을 수 없습니다...!");
            case 3: player.sendMessage(ChatColor.DARK_AQUA +"[ Ercanel ]"+ChatColor.WHITE+"상대방이 너무 견고합니다...!");
            case 2: player.sendMessage(ChatColor.DARK_AQUA +"[ Ercanel ]"+ChatColor.WHITE+"검이 상대의 갑옷에 팅겼습니다...!");
            case 1: player.sendMessage(ChatColor.DARK_AQUA +"[ Ercanel ]"+ChatColor.WHITE+"상대의 갑옷보다 무기가 무딘 것 같습니다...!");
        }
    }

}
