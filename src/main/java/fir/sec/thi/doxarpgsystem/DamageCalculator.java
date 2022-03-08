package fir.sec.thi.doxarpgsystem;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Random;

public class DamageCalculator {

    public Stat s = new Stat();

    public int CombatDamage(int DefaultDamage, long STR){
        return (int) (STR*1.5) + DefaultDamage + Doxaplg01.attack.get("근접 공격력");
    }
    public int CombatRangeDamage(int DefaultDamage, long DEX){
        return (int) (DEX*2) + DefaultDamage + Doxaplg01.attack.get("원거리 공격력");
    }
    public int CombatMagicDamage(int DefaultDamage, long INT){
        return (int) (INT*1.3) + DefaultDamage + Doxaplg01.attack.get("마법 공격력");
    }

    public int Critical(Player player, Long LUK, int damage){//stat[16]
        long[] stat;
        stat = s.getStat(player.getUniqueId().toString());
        int critical = (int)(LUK/100) + Doxaplg01.critical.get("치명타 확률");
        if (critical > 100) critical = 100;
        if (critical < 0) critical = 0;
        stat[16] = critical;
        s.setStat(player.getUniqueId().toString(), stat);
        int chance = Random(0, 100);
        if (chance <= stat[16]){
            player.sendMessage(ChatColor.DARK_AQUA +"[ Ercanel ]"+ChatColor.WHITE+"Critical!");
            return damage*2;
        }
        else {
            return damage;
        }
    }

    public int Random(int min, int max) {
        Random r = new Random();
        return r.nextInt(max-min+1)+min;
    }

}
