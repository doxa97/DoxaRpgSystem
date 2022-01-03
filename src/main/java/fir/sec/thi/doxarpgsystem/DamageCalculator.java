package fir.sec.thi.doxarpgsystem;

import org.bukkit.entity.Player;

import java.util.Random;

public class DamageCalculator {

    public  Random r = new Random();

    public int CombatDamage(Player player, int DefaultDamage, long STR){
        return (int) (STR*1.5) + DefaultDamage;
    }
    public int CombatRangeDamage(Player player, int DefaultDamage, long DEX){
        return (int) (DEX*2) + DefaultDamage;
    }
    public int CombatMagicDamage(Player player, int DefaultDamage, long INT){
        return (int) (INT*1.8) + DefaultDamage;
    }

    public int Critical(Long LUK, int damage){
        int critical = (int)(LUK/100);
        if (critical > 100) critical = 90;
        if (critical < 0) critical = 0;
        int chance = Random(0, 100);
        if (chance <= critical){
            return damage*2;
        }
        else {
            return damage;
        }
    }

    public int Random(int min, int max){
        return r.nextInt(max-min+1)+min;
    }

}
