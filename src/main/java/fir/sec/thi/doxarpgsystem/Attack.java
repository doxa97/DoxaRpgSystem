package fir.sec.thi.doxarpgsystem;

import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;

import java.util.Objects;

public class Attack implements Listener {

    public Stat s = new Stat();
    public DamageCalculator DC = new DamageCalculator();

    @EventHandler
    public void AttackEvent(EntityDamageByEntityEvent event) {
        Player player = (Player) event.getDamager();
        double ac = player.getAttackCooldown();
        if (ac < 1.0) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.DARK_AQUA+"[ Ercanel ]"+ChatColor.WHITE+"성급!");
        } else {
            if (event.getDamager() instanceof Projectile) {
                Projectile p = (Projectile) event.getDamager();
                if (p.getType() == EntityType.ARROW) {
                    if (p.getShooter() != null && p.getShooter() instanceof Player && event.getEntity() instanceof Player) {
                        PlayerDamageByPlayer(event, (Player) p.getShooter(), (Player) event.getEntity(), (int) event.getDamage(), "B");
                    } else if (p.getShooter() != null && p.getShooter() instanceof Player && !(event.getEntity() instanceof Player)) {
                        EntityDamageByPlayer(event, (Player) p.getShooter(), (int) event.getDamage(), "B");
                    } else if (p.getShooter() != null && !(p.getShooter() instanceof Player) && event.getEntity() instanceof Player) {
                        PlayerDamageByEntity(event, (Player) event.getEntity());
                    }
                }
            } else {
                if (event.getDamager() instanceof LivingEntity) {
                    if (Objects.requireNonNull(event.getEntity().getLastDamageCause()).getCause().equals(EntityDamageEvent.DamageCause.MAGIC)) {
                        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
                            PlayerDamageByPlayer(event, (Player) event.getDamager(), (Player) event.getEntity(), (int) event.getDamage(), "M");
                        } else if (event.getDamager() instanceof Player && !(event.getEntity() instanceof Player)) {
                            EntityDamageByPlayer(event, (Player) event.getDamager(), (int) event.getDamage(), "M");
                        } else if (!(event.getDamager() instanceof Player) && event.getEntity() instanceof Player) {
                            PlayerDamageByEntity(event, (Player) event.getEntity());
                        }
                    } else if (event.getEntity().getLastDamageCause().getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)){
                        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
                            PlayerDamageByPlayer(event, (Player) event.getDamager(), (Player) event.getEntity(), (int) event.getDamage(), "A");
                        } else if (event.getDamager() instanceof Player && !(event.getEntity() instanceof Player)) {
                            EntityDamageByPlayer(event, (Player) event.getDamager(), (int) event.getDamage(), "A");
                        } else if (!(event.getDamager() instanceof Player) && event.getEntity() instanceof Player) {
                            PlayerDamageByEntity(event, (Player) event.getEntity());
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void PlayerDamageByPlayer(EntityDamageByEntityEvent event, Player attacker, Player defender, int DefaultDamage, String AttackType) {
        long[] Astat;
        Astat = s.getStat(attacker.getUniqueId().toString());
        long[] Dstat;
        Dstat = s.getStat(defender.getUniqueId().toString());
        if (Objects.equals(AttackType, "B")) {
            long Damage = DC.CombatRangeDamage(DefaultDamage, Astat[8]);
            Damage = DC.Critical(attacker, Astat[9], (int) Damage);
            Damage = Damage + Astat[14];
            double bow = Astat[12]*0.01;
            Damage = (long) (Damage*bow);
            Damage = Damage - Dstat[11];
            event.setDamage(Damage);
            if (Damage <= 0) {
                SendMessage(attacker);
            }
        }
        else {
            if (Objects.equals(AttackType, "A")) {
                long Damage = DC.CombatDamage(DefaultDamage, Astat[7]);
                Damage = DC.Critical(attacker, Astat[9], (int) Damage);
                Damage = Damage + Astat[13];
                Damage = Damage - Dstat[11];
                event.setDamage(Damage);
                if (Damage <= 0) {
                    SendMessage(attacker);
                }
            }
            else {
                if (Objects.equals(AttackType, "M")) {
                    long Damage = DC.CombatMagicDamage(DefaultDamage, Astat[10]);
                    Damage = DC.Critical(attacker, Astat[9], (int) Damage);
                    Damage = Damage + Astat[13];
                    event.setDamage(Damage);
                    if (Damage <= 0) {
                        SendMessage(attacker);
                    }
                }
            }
        }
    }

    @EventHandler
    public void RangeAttack(EntityShootBowEvent event) {
        if (event.getEntityType() == EntityType.PLAYER) {
            Player player = (Player) event.getEntity();
            long[] stat;
            stat = s.getStat(player.getUniqueId().toString());
            stat[12] = (int) (event.getForce()*100);
            s.setStat(player.getUniqueId().toString(), stat);
        }
    }

    @EventHandler
    public void EntityDamageByPlayer(EntityDamageByEntityEvent event, Player attacker, int DefaultDamage, String AttackType) {
        long[] Astat;
        Astat = s.getStat(attacker.getUniqueId().toString());
        if (Objects.equals(AttackType, "B")) {
            long Damage = DC.CombatRangeDamage(DefaultDamage, Astat[8]);
            Damage = DC.Critical(attacker, Astat[9], (int) Damage);
            Damage = Damage + Astat[14];
            double bow = Astat[12]*0.01;
            Damage = (long) (Damage*bow);
            event.setDamage(Damage);
            if (Damage <= 0) {
                SendMessage(attacker);
            }
            else {
                attacker.sendMessage(Damage + "");
            }

        } else if (Objects.equals(AttackType, "A")) {
            long Damage = DC.CombatDamage(DefaultDamage, Astat[7]);
            Damage = DC.Critical(attacker, Astat[9], (int) Damage);
            Damage = Damage + Astat[13];
            event.setDamage(Damage);
            if (Damage <= 0) {
                SendMessage(attacker);
            }
        }
        else {
            if (Objects.equals(AttackType, "M")) {
                long Damage = DC.CombatMagicDamage(DefaultDamage, Astat[10]);
                Damage = DC.Critical(attacker, Astat[9], (int) Damage);
                Damage = Damage + Astat[13];
                event.setDamage(Damage);
                if (Damage <= 0) {
                    SendMessage(attacker);
                }
            }
        }
    }

    @EventHandler
    public void PlayerDamageByEntity(EntityDamageByEntityEvent event, Player defender){
        long[] stat;
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
            case 5: player.sendMessage(ChatColor.DARK_AQUA +"[ Ercanel ]"+ChatColor.WHITE+"상대방의 방어력이 높습니다...!"); break;
            case 4: player.sendMessage(ChatColor.DARK_AQUA +"[ Ercanel ]"+ChatColor.WHITE+"상대방의 방어 자세를 뚫을 수 없습니다...!"); break;
            case 3: player.sendMessage(ChatColor.DARK_AQUA +"[ Ercanel ]"+ChatColor.WHITE+"상대방이 너무 견고합니다...!"); break;
            case 2: player.sendMessage(ChatColor.DARK_AQUA +"[ Ercanel ]"+ChatColor.WHITE+"무기가 상대의 갑옷에 팅겼습니다...!"); break;
            case 1: player.sendMessage(ChatColor.DARK_AQUA +"[ Ercanel ]"+ChatColor.WHITE+"상대의 갑옷보다 무기가 무딘 것 같습니다...!"); break;
        }
    }

}
