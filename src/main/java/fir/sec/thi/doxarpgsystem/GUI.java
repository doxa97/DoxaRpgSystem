package fir.sec.thi.doxarpgsystem;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class GUI {
    public Stat s = new Stat();

    public void Stack(String Display, Material ID, int DATA, int STACK, List<String> lore, int loc, Inventory status){
        ItemStack item = new MaterialData(ID, (byte)DATA).toItemStack(STACK);
        ItemMeta item_Meta = item.getItemMeta();
        Objects.requireNonNull(item_Meta).setDisplayName(Display);
        item_Meta.setLore(lore);
        item.setItemMeta(item_Meta);
        status.setItem(loc, item);
    }

    public void StatusGui(InventoryClickEvent e){
        e.setCancelled(true);
        Player player = (Player) e.getWhoClicked();
        if (ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase("[ Status ]")){
            if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR ||
                    ! e.getCurrentItem().hasItemMeta()){
                return;
            }
            else{
                long[] i;
                i = s.getStat(player.getUniqueId().toString());
                switch ((ChatColor.stripColor(Objects.requireNonNull(e.getCurrentItem().getItemMeta()).getDisplayName()))){
                    case "[ 체력 ]" : s.StatUp(i, player, 6); StatusG(player); break;
                    case "[ 힘 ]" : s.StatUp(i, player, 7);  StatusG(player); break;
                    case "[ 명중률 ]" : s.StatUp(i, player, 8);  StatusG(player); break;
                    case "[ 민첩 ]" : s.StatUp(i, player, 9);  StatusG(player); break;
                    case "[ 마력 ]" : s.StatUp(i, player, 10);  StatusG(player); break;
                }
            }
        }
    }

    public void StatusG(Player player){
        long[] i;
        i = s.getStat(player.getUniqueId().toString());
        Inventory status = Bukkit.createInventory(null, 9 ,"[ Status ]");
        Stack(ChatColor.RED+"[ 체력 ]", Material.APPLE,0,1, Arrays.asList(ChatColor.GRAY+"현재 체력 스탯 : " + i[6],
                ChatColor.GRAY+"현재 여유 스탯 : " + i[1],
                ChatColor.GRAY+"추가 체력 : " + i[6]*5, ChatColor.GRAY+"추가 재생력 : " + i[6]*2),0,status);
        Stack(ChatColor.DARK_RED+"[ 힘 ]", Material.IRON_SWORD,0,1, Arrays.asList(ChatColor.GRAY+"현재 힘 스탯 : " + i[7],
                ChatColor.GRAY+"현재 여유 스탯 : " + i[1],
                ChatColor.GRAY+"추가 근접 물리 공격력 : " + i[7]*1.5),2,status);
        Stack(ChatColor.AQUA+"[ 명중률 ]", Material.BOW,0,1, Arrays.asList(ChatColor.GRAY+"현재 명중률 스탯 : " + i[8],
                ChatColor.GRAY+"현재 여유 스탯 : " + i[1],
                ChatColor.GRAY+"추가 원거리 물리 공격력 : " + i[8]*2),4,status);
        Stack(ChatColor.GOLD+"[ 민첩 ]", Material.GOLDEN_BOOTS,0,1, Arrays.asList(ChatColor.GRAY+"현재 민첩 스탯 : " + i[9],
                ChatColor.GRAY+"현재 여유 스탯 : " + i[1],
                ChatColor.GRAY+"추가 이동속도 : " + i[9]*0.001, "추가 치명타 확률 : " + i[9]*0.1 + "%"),6,status);
        Stack(ChatColor.LIGHT_PURPLE+"[ 마력 ]", Material.BLAZE_POWDER,0,1, Arrays.asList(ChatColor.GRAY+"현재 마력 스탯 : " + i[10],
                ChatColor.GRAY+"현재 여유 스탯 : " + i[1],
                ChatColor.GRAY+"추가 마법 공격력 : " + i[10]*1.3),8,status);
        player.openInventory(status);

    }
}
