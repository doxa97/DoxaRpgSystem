package fir.sec.thi.doxarpgsystem;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class Level implements Listener {

    public Stat s = new Stat();

    @EventHandler
    public void MonsterKill(EntityDeathEvent event) {
        Player player = event.getEntity().getKiller();
        long[] Stat;
        Stat = s.getStat(Objects.requireNonNull(player).getUniqueId().toString());
        if (event.getEntity().getName().contains("LV.1 강벌레")){
            ItemStack item = new ItemStack(Material.STRING);
            ItemMeta meta = item.getItemMeta();
            assert meta != null;
            meta.setDisplayName(ChatColor.DARK_GRAY+"너저분한 실");
            meta.setLore(Arrays.asList(ChatColor.GRAY+"[기타]        [일반]",ChatColor.GRAY+"강에 뒹굴던 강벌레의 털이다.",ChatColor.GRAY+"사용하기에는 힘들어보인다."));
            item.setItemMeta(meta);
            Random r = new Random();
            if (r.nextInt(100) <= 30){
                Objects.requireNonNull(player).getInventory().addItem(item);
            }
            Objects.requireNonNull(player).updateInventory();
        }
        if (event.getEntity().getName().contains("LV.2 큰 하수도 슬라임")){
            ItemStack item = new ItemStack(Material.SLIME_BALL);
            ItemMeta meta = item.getItemMeta();
            assert meta != null;
            meta.setDisplayName(ChatColor.DARK_GRAY+"끈적한 점액");
            meta.setLore(Arrays.asList(ChatColor.GRAY+"[기타]        [일반]",ChatColor.GRAY+"하수도의 냄새가 지독한 점액이다.",ChatColor.GRAY+"악취에 비해 약간의 회복 능력이 있다."));
            item.setItemMeta(meta);
            Random r = new Random();
            if (r.nextInt(100) <= 30){
                Objects.requireNonNull(player).getInventory().addItem(item);
            }
            Objects.requireNonNull(player).updateInventory();
            Stat[2] = Stat[2] + 1;
        }
        s.LevelUP(Stat, player);
        s.setStat(player.getUniqueId().toString(), Stat);
    }
}
