package fir.sec.thi.doxarpgsystem;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class Doxaplg01 extends JavaPlugin implements Listener, CommandExecutor {

    public Stat s = new Stat();
    public GUI SGui = new GUI();
    public Attack A = new Attack();
    public DamageCalculator DC = new DamageCalculator();

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Doxa On!");
        getServer().getPluginManager().registerEvents(A, this);
        getServer().getPluginManager().registerEvents(new Level(), this);
        getServer().getPluginManager().registerEvents(this, this);
        Objects.requireNonNull(getCommand("스탯")).setExecutor(this);
    }

    @Override
    public void onDisable() {
        getLogger().info("Doxa Off!");
        // Plugin shutdown logic
    }

    @SuppressWarnings("deprecation")
    public boolean consumeItem(Player player, int count, Material mat) {
        Map<Integer, ? extends ItemStack> ammo = player.getInventory().all(mat);
        int found = 0;
        for (ItemStack stack : ammo.values())
            found += stack.getAmount();
        if (count > found)
            return false;
        for (Integer index : ammo.keySet()) {
            ItemStack stack = ammo.get(index);
            int removed = Math.min(count, stack.getAmount());
            count -= removed;
            if (stack.getAmount() == removed)
                player.getInventory().setItem(index, null);
            else
                stack.setAmount(stack.getAmount() - removed);
            if (count <= 0)
                break;
        }
        player.updateInventory();
        return true;
    }

    @EventHandler
    public void loadPS(PlayerMoveEvent p) {
        Player player = p.getPlayer();
        long[] stat;
        stat = s.getStat(player.getUniqueId().toString());
        if (player.getEquipment().getItemInMainHand() == null || Objects.requireNonNull(player.getEquipment().getItemInMainHand()).getType() == Material.AIR ||
                ! player.getEquipment().getItemInMainHand().hasItemMeta() || ! Objects.requireNonNull(player.getEquipment().getItemInMainHand().getItemMeta()).hasLore()){
            stat[13] = 0;
            stat[14] = 0;
            stat[15] = 0;
            stat[31] = 0;
            s.setStat(player.getUniqueId().toString(), stat);
            if (player.getEquipment().getHelmet() == null || Objects.requireNonNull(player.getEquipment().getHelmet()).getType() == Material.AIR ||
                    ! player.getEquipment().getHelmet().hasItemMeta()){
                stat[19] = 0;
                stat[23] = 0;
                stat[27] = 0;
                s.setStat(player.getUniqueId().toString(), stat);
                if (player.getEquipment().getChestplate() == null || Objects.requireNonNull(player.getEquipment().getChestplate()).getType() == Material.AIR ||
                        ! player.getEquipment().getChestplate().hasItemMeta()){
                    stat[20] = 0;
                    stat[24] = 0;
                    stat[28] = 0;
                    s.setStat(player.getUniqueId().toString(), stat);
                    if (player.getEquipment().getLeggings() == null || Objects.requireNonNull(player.getEquipment().getLeggings()).getType() == Material.AIR ||
                            ! player.getEquipment().getLeggings().hasItemMeta()){
                        stat[21] = 0;
                        stat[25] = 0;
                        stat[29] = 0;
                        s.setStat(player.getUniqueId().toString(), stat);
                        if (player.getEquipment().getBoots() == null || Objects.requireNonNull(player.getEquipment().getBoots()).getType() == Material.AIR ||
                                ! player.getEquipment().getBoots().hasItemMeta()){
                            stat[22] = 0;
                            stat[26] = 0;
                            stat[30] = 0;
                            s.setStat(player.getUniqueId().toString(), stat);
                        } else {
                            ItemStack b = Objects.requireNonNull(player.getEquipment()).getBoots();
                            ArrayList lorelistb = (ArrayList) Objects.requireNonNull(b.getItemMeta()).getLore();
                            for (Object string : Objects.requireNonNull(lorelistb)){
                                String s1 = (String) lorelistb.get(3);
                                String s2 = (String) lorelistb.get(4);
                                String s3 = (String) lorelistb.get(5);
                                stat[26] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                                stat[22] = Long.parseLong(s2.replace("방어력 : ", ""));
                                stat[30] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                                s.setStat(player.getUniqueId().toString(), stat);
                            }
                        }
                    } else {
                        ItemStack l = Objects.requireNonNull(player.getEquipment()).getLeggings();
                        ArrayList lorelistl = (ArrayList) Objects.requireNonNull(l.getItemMeta()).getLore();
                        for (Object string : Objects.requireNonNull(lorelistl)){
                            String s1 = (String) lorelistl.get(3);
                            String s2 = (String) lorelistl.get(4);
                            String s3 = (String) lorelistl.get(5);
                            stat[25] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                            stat[21] = Long.parseLong(s2.replace("방어력 : ", ""));
                            stat[29] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                            s.setStat(player.getUniqueId().toString(), stat);
                        }
                        if (player.getEquipment().getBoots() == null || Objects.requireNonNull(player.getEquipment().getBoots()).getType() == Material.AIR ||
                                ! player.getEquipment().getBoots().hasItemMeta()){
                            stat[22] = 0;
                            stat[26] = 0;
                            stat[30] = 0;
                            s.setStat(player.getUniqueId().toString(), stat);
                        } else {
                            ItemStack b = Objects.requireNonNull(player.getEquipment()).getBoots();
                            ArrayList lorelistb = (ArrayList) Objects.requireNonNull(b.getItemMeta()).getLore();
                            for (Object string : Objects.requireNonNull(lorelistb)){
                                String s1 = (String) lorelistb.get(3);
                                String s2 = (String) lorelistb.get(4);
                                String s3 = (String) lorelistb.get(5);
                                stat[26] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                                stat[22] = Long.parseLong(s2.replace("방어력 : ", ""));
                                stat[30] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                                s.setStat(player.getUniqueId().toString(), stat);
                            }
                        }
                    }
                } else {
                    ItemStack c = Objects.requireNonNull(player.getEquipment()).getChestplate();
                    ArrayList lorelistc = (ArrayList) Objects.requireNonNull(c.getItemMeta()).getLore();
                    for (Object string : Objects.requireNonNull(lorelistc)){
                        String s1 = (String) lorelistc.get(3);
                        String s2 = (String) lorelistc.get(4);
                        String s3 = (String) lorelistc.get(5);
                        stat[24] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                        stat[20] = Long.parseLong(s2.replace("방어력 : ", ""));
                        stat[28] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                        s.setStat(player.getUniqueId().toString(), stat);
                    }
                    if (player.getEquipment().getLeggings() == null || Objects.requireNonNull(player.getEquipment().getLeggings()).getType() == Material.AIR ||
                            ! player.getEquipment().getLeggings().hasItemMeta()){
                        stat[21] = 0;
                        stat[25] = 0;
                        stat[29] = 0;
                        s.setStat(player.getUniqueId().toString(), stat);
                    } else {
                        ItemStack l = Objects.requireNonNull(player.getEquipment()).getLeggings();
                        ArrayList lorelistl = (ArrayList) Objects.requireNonNull(l.getItemMeta()).getLore();
                        for (Object string : Objects.requireNonNull(lorelistl)){
                            String s1 = (String) lorelistl.get(3);
                            String s2 = (String) lorelistl.get(4);
                            String s3 = (String) lorelistl.get(5);
                            stat[25] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                            stat[21] = Long.parseLong(s2.replace("방어력 : ", ""));
                            stat[29] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                            s.setStat(player.getUniqueId().toString(), stat);
                        }
                        if (player.getEquipment().getBoots() == null || Objects.requireNonNull(player.getEquipment().getBoots()).getType() == Material.AIR ||
                                ! player.getEquipment().getBoots().hasItemMeta()){
                            stat[22] = 0;
                            stat[26] = 0;
                            stat[30] = 0;
                            s.setStat(player.getUniqueId().toString(), stat);
                        } else {
                            ItemStack b = Objects.requireNonNull(player.getEquipment()).getBoots();
                            ArrayList lorelistb = (ArrayList) Objects.requireNonNull(b.getItemMeta()).getLore();
                            for (Object string : Objects.requireNonNull(lorelistb)){
                                String s1 = (String) lorelistb.get(3);
                                String s2 = (String) lorelistb.get(4);
                                String s3 = (String) lorelistb.get(5);
                                stat[26] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                                stat[22] = Long.parseLong(s2.replace("방어력 : ", ""));
                                stat[30] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                                s.setStat(player.getUniqueId().toString(), stat);
                            }
                        }
                    }
                }
            } else {
                ItemStack h = Objects.requireNonNull(player.getEquipment()).getHelmet();
                ArrayList lorelisth = (ArrayList) Objects.requireNonNull(h.getItemMeta()).getLore();
                for (Object string : Objects.requireNonNull(lorelisth)){
                    String s1 = (String) lorelisth.get(3);
                    String s2 = (String) lorelisth.get(4);
                    String s3 = (String) lorelisth.get(5);
                    stat[23] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                    stat[19] = Long.parseLong(s2.replace("방어력 : ", ""));
                    stat[27] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                    s.setStat(player.getUniqueId().toString(), stat);
                }
                if (player.getEquipment().getChestplate() == null || Objects.requireNonNull(player.getEquipment().getChestplate()).getType() == Material.AIR ||
                        ! player.getEquipment().getChestplate().hasItemMeta()){
                    stat[20] = 0;
                    stat[24] = 0;
                    stat[28] = 0;
                    s.setStat(player.getUniqueId().toString(), stat);
                    if (player.getEquipment().getLeggings() == null || Objects.requireNonNull(player.getEquipment().getLeggings()).getType() == Material.AIR ||
                            ! player.getEquipment().getLeggings().hasItemMeta()){
                        stat[21] = 0;
                        stat[25] = 0;
                        stat[29] = 0;
                        s.setStat(player.getUniqueId().toString(), stat);
                        if (player.getEquipment().getBoots() == null || Objects.requireNonNull(player.getEquipment().getBoots()).getType() == Material.AIR ||
                                ! player.getEquipment().getBoots().hasItemMeta()){
                            stat[22] = 0;
                            stat[26] = 0;
                            stat[30] = 0;
                            s.setStat(player.getUniqueId().toString(), stat);
                        } else {
                            ItemStack b = Objects.requireNonNull(player.getEquipment()).getBoots();
                            ArrayList lorelistb = (ArrayList) Objects.requireNonNull(b.getItemMeta()).getLore();
                            for (Object string : Objects.requireNonNull(lorelistb)){
                                String s1 = (String) lorelistb.get(3);
                                String s2 = (String) lorelistb.get(4);
                                String s3 = (String) lorelistb.get(5);
                                stat[26] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                                stat[22] = Long.parseLong(s2.replace("방어력 : ", ""));
                                stat[30] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                                s.setStat(player.getUniqueId().toString(), stat);
                            }
                        }
                    } else {
                        ItemStack l = Objects.requireNonNull(player.getEquipment()).getLeggings();
                        ArrayList lorelistl = (ArrayList) Objects.requireNonNull(l.getItemMeta()).getLore();
                        for (Object string : Objects.requireNonNull(lorelistl)){
                            String s1 = (String) lorelistl.get(3);
                            String s2 = (String) lorelistl.get(4);
                            String s3 = (String) lorelistl.get(5);
                            stat[25] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                            stat[21] = Long.parseLong(s2.replace("방어력 : ", ""));
                            stat[29] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                            s.setStat(player.getUniqueId().toString(), stat);
                        }
                        if (player.getEquipment().getBoots() == null || Objects.requireNonNull(player.getEquipment().getBoots()).getType() == Material.AIR ||
                                ! player.getEquipment().getBoots().hasItemMeta()){
                            stat[22] = 0;
                            stat[26] = 0;
                            stat[30] = 0;
                            s.setStat(player.getUniqueId().toString(), stat);
                        } else {
                            ItemStack b = Objects.requireNonNull(player.getEquipment()).getBoots();
                            ArrayList lorelistb = (ArrayList) Objects.requireNonNull(b.getItemMeta()).getLore();
                            for (Object string : Objects.requireNonNull(lorelistb)){
                                String s1 = (String) lorelistb.get(3);
                                String s2 = (String) lorelistb.get(4);
                                String s3 = (String) lorelistb.get(5);
                                stat[26] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                                stat[22] = Long.parseLong(s2.replace("방어력 : ", ""));
                                stat[30] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                                s.setStat(player.getUniqueId().toString(), stat);
                            }
                        }
                    }
                } else {
                    ItemStack c = Objects.requireNonNull(player.getEquipment()).getChestplate();
                    ArrayList lorelistc = (ArrayList) Objects.requireNonNull(c.getItemMeta()).getLore();
                    for (Object string : Objects.requireNonNull(lorelistc)){
                        String s1 = (String) lorelistc.get(3);
                        String s2 = (String) lorelistc.get(4);
                        String s3 = (String) lorelistc.get(5);
                        stat[24] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                        stat[20] = Long.parseLong(s2.replace("방어력 : ", ""));
                        stat[28] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                        s.setStat(player.getUniqueId().toString(), stat);
                    }
                    if (player.getEquipment().getLeggings() == null || Objects.requireNonNull(player.getEquipment().getLeggings()).getType() == Material.AIR ||
                            ! player.getEquipment().getLeggings().hasItemMeta()){
                        stat[21] = 0;
                        stat[25] = 0;
                        stat[29] = 0;
                        s.setStat(player.getUniqueId().toString(), stat);
                        if (player.getEquipment().getBoots() == null || Objects.requireNonNull(player.getEquipment().getBoots()).getType() == Material.AIR ||
                                ! player.getEquipment().getBoots().hasItemMeta()){
                            stat[22] = 0;
                            stat[26] = 0;
                            stat[30] = 0;
                            s.setStat(player.getUniqueId().toString(), stat);
                        } else {
                            ItemStack b = Objects.requireNonNull(player.getEquipment()).getBoots();
                            ArrayList lorelistb = (ArrayList) Objects.requireNonNull(b.getItemMeta()).getLore();
                            for (Object string : Objects.requireNonNull(lorelistb)){
                                String s1 = (String) lorelistb.get(3);
                                String s2 = (String) lorelistb.get(4);
                                String s3 = (String) lorelistb.get(5);
                                stat[26] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                                stat[22] = Long.parseLong(s2.replace("방어력 : ", ""));
                                stat[30] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                                s.setStat(player.getUniqueId().toString(), stat);
                            }
                        }
                    } else {
                        ItemStack l = Objects.requireNonNull(player.getEquipment()).getLeggings();
                        ArrayList lorelistl = (ArrayList) Objects.requireNonNull(l.getItemMeta()).getLore();
                        for (Object string : Objects.requireNonNull(lorelistl)){
                            String s1 = (String) lorelistl.get(3);
                            String s2 = (String) lorelistl.get(4);
                            String s3 = (String) lorelistl.get(5);
                            stat[25] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                            stat[21] = Long.parseLong(s2.replace("방어력 : ", ""));
                            stat[29] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                            s.setStat(player.getUniqueId().toString(), stat);
                        }
                        if (player.getEquipment().getBoots() == null || Objects.requireNonNull(player.getEquipment().getBoots()).getType() == Material.AIR ||
                                ! player.getEquipment().getBoots().hasItemMeta()){
                            stat[22] = 0;
                            stat[26] = 0;
                            stat[30] = 0;
                            s.setStat(player.getUniqueId().toString(), stat);
                        } else {
                            ItemStack b = Objects.requireNonNull(player.getEquipment()).getBoots();
                            ArrayList lorelistb = (ArrayList) Objects.requireNonNull(b.getItemMeta()).getLore();
                            for (Object string : Objects.requireNonNull(lorelistb)){
                                String s1 = (String) lorelistb.get(3);
                                String s2 = (String) lorelistb.get(4);
                                String s3 = (String) lorelistb.get(5);
                                stat[26] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                                stat[22] = Long.parseLong(s2.replace("방어력 : ", ""));
                                stat[30] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                                s.setStat(player.getUniqueId().toString(), stat);
                            }
                        }
                    }
                }
            }
        } else {
            ItemStack w = Objects.requireNonNull(player.getEquipment()).getItemInMainHand();
            //noinspection rawtypes
            ArrayList lorelistw = (ArrayList) Objects.requireNonNull(w.getItemMeta()).getLore();
            for (Object string : Objects.requireNonNull(lorelistw)){
                String s1 = (String) lorelistw.get(3);
                String s2 = (String) lorelistw.get(4);
                if (s1.contains("근접")) {
                    stat[13] = Long.parseLong(s1.replace("근접 공격력 : ", ""));
                    if (s2.contains("치명타")){
                        stat[31] = Long.parseLong(s2.replace("치명타 확률 : ", ""));
                    }
                }
                else if (s1.contains("원거리")) {
                    stat[14] = Long.parseLong(s1.replace("원거리 공격력 : ", ""));
                    if (s2.contains("치명타")){
                        stat[31] = Long.parseLong(s2.replace("치명타 확률 : ", ""));
                    }
                }
                else if (s1.contains("마법")) {
                    stat[15] = Long.parseLong(s1.replace("마법 공격력 : ", ""));
                    if (s2.contains("치명타")){
                        stat[31] = Long.parseLong(s2.replace("치명타 확률 : ", ""));
                    }
                }
                s.setStat(player.getUniqueId().toString(), stat);
            }
            if (player.getEquipment().getHelmet() == null || Objects.requireNonNull(player.getEquipment().getHelmet()).getType() == Material.AIR ||
                    ! player.getEquipment().getHelmet().hasItemMeta()){
                stat[19] = 0;
                stat[23] = 0;
                stat[27] = 0;
                s.setStat(player.getUniqueId().toString(), stat);
                if (player.getEquipment().getChestplate() == null || Objects.requireNonNull(player.getEquipment().getChestplate()).getType() == Material.AIR ||
                        ! player.getEquipment().getChestplate().hasItemMeta()){
                    stat[20] = 0;
                    stat[24] = 0;
                    stat[28] = 0;
                    s.setStat(player.getUniqueId().toString(), stat);
                    if (player.getEquipment().getLeggings() == null || Objects.requireNonNull(player.getEquipment().getLeggings()).getType() == Material.AIR ||
                            ! player.getEquipment().getLeggings().hasItemMeta()){
                        stat[21] = 0;
                        stat[25] = 0;
                        stat[29] = 0;
                        s.setStat(player.getUniqueId().toString(), stat);
                        if (player.getEquipment().getBoots() == null || Objects.requireNonNull(player.getEquipment().getBoots()).getType() == Material.AIR ||
                                ! player.getEquipment().getBoots().hasItemMeta()){
                            stat[22] = 0;
                            stat[26] = 0;
                            stat[30] = 0;
                            s.setStat(player.getUniqueId().toString(), stat);
                        } else {
                            ItemStack b = Objects.requireNonNull(player.getEquipment()).getBoots();
                            ArrayList lorelistb = (ArrayList) Objects.requireNonNull(b.getItemMeta()).getLore();
                            for (Object string : Objects.requireNonNull(lorelistb)){
                                String s1 = (String) lorelistb.get(3);
                                String s2 = (String) lorelistb.get(4);
                                String s3 = (String) lorelistb.get(5);
                                stat[26] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                                stat[22] = Long.parseLong(s2.replace("방어력 : ", ""));
                                stat[30] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                                s.setStat(player.getUniqueId().toString(), stat);
                            }
                        }
                    } else {
                        ItemStack l = Objects.requireNonNull(player.getEquipment()).getLeggings();
                        ArrayList lorelistl = (ArrayList) Objects.requireNonNull(l.getItemMeta()).getLore();
                        for (Object string : Objects.requireNonNull(lorelistl)){
                            String s1 = (String) lorelistl.get(3);
                            String s2 = (String) lorelistl.get(4);
                            String s3 = (String) lorelistl.get(5);
                            stat[25] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                            stat[21] = Long.parseLong(s2.replace("방어력 : ", ""));
                            stat[29] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                            s.setStat(player.getUniqueId().toString(), stat);
                        }
                        if (player.getEquipment().getBoots() == null || Objects.requireNonNull(player.getEquipment().getBoots()).getType() == Material.AIR ||
                                ! player.getEquipment().getBoots().hasItemMeta()){
                            stat[22] = 0;
                            stat[26] = 0;
                            stat[30] = 0;
                            s.setStat(player.getUniqueId().toString(), stat);
                        } else {
                            ItemStack b = Objects.requireNonNull(player.getEquipment()).getBoots();
                            ArrayList lorelistb = (ArrayList) Objects.requireNonNull(b.getItemMeta()).getLore();
                            for (Object string : Objects.requireNonNull(lorelistb)){
                                String s1 = (String) lorelistb.get(3);
                                String s2 = (String) lorelistb.get(4);
                                String s3 = (String) lorelistb.get(5);
                                stat[26] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                                stat[22] = Long.parseLong(s2.replace("방어력 : ", ""));
                                stat[30] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                                s.setStat(player.getUniqueId().toString(), stat);
                            }
                        }
                    }
                } else {
                    ItemStack c = Objects.requireNonNull(player.getEquipment()).getChestplate();
                    ArrayList lorelistc = (ArrayList) Objects.requireNonNull(c.getItemMeta()).getLore();
                    for (Object string : Objects.requireNonNull(lorelistc)){
                        String s1 = (String) lorelistc.get(3);
                        String s2 = (String) lorelistc.get(4);
                        String s3 = (String) lorelistc.get(5);
                        stat[24] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                        stat[20] = Long.parseLong(s2.replace("방어력 : ", ""));
                        stat[28] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                        s.setStat(player.getUniqueId().toString(), stat);
                    }
                    if (player.getEquipment().getLeggings() == null || Objects.requireNonNull(player.getEquipment().getLeggings()).getType() == Material.AIR ||
                            ! player.getEquipment().getLeggings().hasItemMeta()){
                        stat[21] = 0;
                        stat[25] = 0;
                        stat[29] = 0;
                        s.setStat(player.getUniqueId().toString(), stat);
                    } else {
                        ItemStack l = Objects.requireNonNull(player.getEquipment()).getLeggings();
                        ArrayList lorelistl = (ArrayList) Objects.requireNonNull(l.getItemMeta()).getLore();
                        for (Object string : Objects.requireNonNull(lorelistl)){
                            String s1 = (String) lorelistl.get(3);
                            String s2 = (String) lorelistl.get(4);
                            String s3 = (String) lorelistl.get(5);
                            stat[25] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                            stat[21] = Long.parseLong(s2.replace("방어력 : ", ""));
                            stat[29] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                            s.setStat(player.getUniqueId().toString(), stat);
                        }
                        if (player.getEquipment().getBoots() == null || Objects.requireNonNull(player.getEquipment().getBoots()).getType() == Material.AIR ||
                                ! player.getEquipment().getBoots().hasItemMeta()){
                            stat[22] = 0;
                            stat[26] = 0;
                            stat[30] = 0;
                            s.setStat(player.getUniqueId().toString(), stat);
                        } else {
                            ItemStack b = Objects.requireNonNull(player.getEquipment()).getBoots();
                            ArrayList lorelistb = (ArrayList) Objects.requireNonNull(b.getItemMeta()).getLore();
                            for (Object string : Objects.requireNonNull(lorelistb)){
                                String s1 = (String) lorelistb.get(3);
                                String s2 = (String) lorelistb.get(4);
                                String s3 = (String) lorelistb.get(5);
                                stat[26] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                                stat[22] = Long.parseLong(s2.replace("방어력 : ", ""));
                                stat[30] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                                s.setStat(player.getUniqueId().toString(), stat);
                            }
                        }
                    }
                }
            } else {
                ItemStack h = Objects.requireNonNull(player.getEquipment()).getHelmet();
                ArrayList lorelisth = (ArrayList) Objects.requireNonNull(h.getItemMeta()).getLore();
                for (Object string : Objects.requireNonNull(lorelisth)){
                    String s1 = (String) lorelisth.get(3);
                    String s2 = (String) lorelisth.get(4);
                    String s3 = (String) lorelisth.get(5);
                    stat[23] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                    stat[19] = Long.parseLong(s2.replace("방어력 : ", ""));
                    stat[27] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                    s.setStat(player.getUniqueId().toString(), stat);
                }
                if (player.getEquipment().getChestplate() == null || Objects.requireNonNull(player.getEquipment().getChestplate()).getType() == Material.AIR ||
                        ! player.getEquipment().getChestplate().hasItemMeta()){
                    stat[20] = 0;
                    stat[24] = 0;
                    stat[28] = 0;
                    s.setStat(player.getUniqueId().toString(), stat);
                    if (player.getEquipment().getLeggings() == null || Objects.requireNonNull(player.getEquipment().getLeggings()).getType() == Material.AIR ||
                            ! player.getEquipment().getLeggings().hasItemMeta()){
                        stat[21] = 0;
                        stat[25] = 0;
                        stat[29] = 0;
                        s.setStat(player.getUniqueId().toString(), stat);
                        if (player.getEquipment().getBoots() == null || Objects.requireNonNull(player.getEquipment().getBoots()).getType() == Material.AIR ||
                                ! player.getEquipment().getBoots().hasItemMeta()){
                            stat[22] = 0;
                            stat[26] = 0;
                            stat[30] = 0;
                            s.setStat(player.getUniqueId().toString(), stat);
                        } else {
                            ItemStack b = Objects.requireNonNull(player.getEquipment()).getBoots();
                            ArrayList lorelistb = (ArrayList) Objects.requireNonNull(b.getItemMeta()).getLore();
                            for (Object string : Objects.requireNonNull(lorelistb)){
                                String s1 = (String) lorelistb.get(3);
                                String s2 = (String) lorelistb.get(4);
                                String s3 = (String) lorelistb.get(5);
                                stat[26] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                                stat[22] = Long.parseLong(s2.replace("방어력 : ", ""));
                                stat[30] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                                s.setStat(player.getUniqueId().toString(), stat);
                            }
                        }
                    } else {
                        ItemStack l = Objects.requireNonNull(player.getEquipment()).getLeggings();
                        ArrayList lorelistl = (ArrayList) Objects.requireNonNull(l.getItemMeta()).getLore();
                        for (Object string : Objects.requireNonNull(lorelistl)){
                            String s1 = (String) lorelistl.get(3);
                            String s2 = (String) lorelistl.get(4);
                            String s3 = (String) lorelistl.get(5);
                            stat[25] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                            stat[21] = Long.parseLong(s2.replace("방어력 : ", ""));
                            stat[29] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                            s.setStat(player.getUniqueId().toString(), stat);
                        }
                        if (player.getEquipment().getBoots() == null || Objects.requireNonNull(player.getEquipment().getBoots()).getType() == Material.AIR ||
                                ! player.getEquipment().getBoots().hasItemMeta()){
                            stat[22] = 0;
                            stat[26] = 0;
                            stat[30] = 0;
                            s.setStat(player.getUniqueId().toString(), stat);
                        } else {
                            ItemStack b = Objects.requireNonNull(player.getEquipment()).getBoots();
                            ArrayList lorelistb = (ArrayList) Objects.requireNonNull(b.getItemMeta()).getLore();
                            for (Object string : Objects.requireNonNull(lorelistb)){
                                String s1 = (String) lorelistb.get(3);
                                String s2 = (String) lorelistb.get(4);
                                String s3 = (String) lorelistb.get(5);
                                stat[26] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                                stat[22] = Long.parseLong(s2.replace("방어력 : ", ""));
                                stat[30] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                                s.setStat(player.getUniqueId().toString(), stat);
                            }
                        }
                    }
                } else {
                    ItemStack c = Objects.requireNonNull(player.getEquipment()).getChestplate();
                    ArrayList lorelistc = (ArrayList) Objects.requireNonNull(c.getItemMeta()).getLore();
                    for (Object string : Objects.requireNonNull(lorelistc)){
                        String s1 = (String) lorelistc.get(3);
                        String s2 = (String) lorelistc.get(4);
                        String s3 = (String) lorelistc.get(5);
                        stat[24] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                        stat[20] = Long.parseLong(s2.replace("방어력 : ", ""));
                        stat[28] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                        s.setStat(player.getUniqueId().toString(), stat);
                    }
                    if (player.getEquipment().getLeggings() == null || Objects.requireNonNull(player.getEquipment().getLeggings()).getType() == Material.AIR ||
                            ! player.getEquipment().getLeggings().hasItemMeta()){
                        stat[21] = 0;
                        stat[25] = 0;
                        stat[29] = 0;
                        s.setStat(player.getUniqueId().toString(), stat);
                        if (player.getEquipment().getBoots() == null || Objects.requireNonNull(player.getEquipment().getBoots()).getType() == Material.AIR ||
                                ! player.getEquipment().getBoots().hasItemMeta()){
                            stat[22] = 0;
                            stat[26] = 0;
                            stat[30] = 0;
                            s.setStat(player.getUniqueId().toString(), stat);
                        } else {
                            ItemStack b = Objects.requireNonNull(player.getEquipment()).getBoots();
                            ArrayList lorelistb = (ArrayList) Objects.requireNonNull(b.getItemMeta()).getLore();
                            for (Object string : Objects.requireNonNull(lorelistb)){
                                String s1 = (String) lorelistb.get(3);
                                String s2 = (String) lorelistb.get(4);
                                String s3 = (String) lorelistb.get(5);
                                stat[26] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                                stat[22] = Long.parseLong(s2.replace("방어력 : ", ""));
                                stat[30] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                                s.setStat(player.getUniqueId().toString(), stat);
                            }
                        }
                    } else {
                        ItemStack l = Objects.requireNonNull(player.getEquipment()).getLeggings();
                        ArrayList lorelistl = (ArrayList) Objects.requireNonNull(l.getItemMeta()).getLore();
                        for (Object string : Objects.requireNonNull(lorelistl)){
                            String s1 = (String) lorelistl.get(3);
                            String s2 = (String) lorelistl.get(4);
                            String s3 = (String) lorelistl.get(5);
                            stat[25] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                            stat[21] = Long.parseLong(s2.replace("방어력 : ", ""));
                            stat[29] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                            s.setStat(player.getUniqueId().toString(), stat);
                        }
                        if (player.getEquipment().getBoots() == null || Objects.requireNonNull(player.getEquipment().getBoots()).getType() == Material.AIR ||
                                ! player.getEquipment().getBoots().hasItemMeta()){
                            stat[22] = 0;
                            stat[26] = 0;
                            stat[30] = 0;
                            s.setStat(player.getUniqueId().toString(), stat);
                        } else {
                            ItemStack b = Objects.requireNonNull(player.getEquipment()).getBoots();
                            ArrayList lorelistb = (ArrayList) Objects.requireNonNull(b.getItemMeta()).getLore();
                            for (Object string : Objects.requireNonNull(lorelistb)){
                                String s1 = (String) lorelistb.get(3);
                                String s2 = (String) lorelistb.get(4);
                                String s3 = (String) lorelistb.get(5);
                                stat[26] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                                stat[22] = Long.parseLong(s2.replace("방어력 : ", ""));
                                stat[30] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                                s.setStat(player.getUniqueId().toString(), stat);
                            }
                        }
                    }
                }
            }
        }
        stat[18] = 100 + stat[9] + stat[27] + stat[28] + stat[29] + stat[30];
        stat[17] = stat[5] + (stat[6] * 5) + stat[23] + stat[24] + stat[25] + stat [26];
        stat[11] = stat[19]+stat[20]+stat[21]+stat[22];
        double speed = stat[18]*0.001;
        Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(stat[17]);
        Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)).setBaseValue(speed);
        s.setStat(player.getUniqueId().toString(), stat);

    }

    //스탯 명령어 ctrl + o
    public boolean onCommand(CommandSender talker, Command command, String label, String[] args) {
        if (talker instanceof Player) {
            if (label.equals("스탯")) {
                SGui.StatusG((Player) talker);
                return true;
            }
            if (label.equals("수표")) {
                if (args.length == 0) {
                    talker.sendMessage(ChatColor.DARK_AQUA + "[ Ercanel ]" + ChatColor.WHITE + "/수표 [금액] [개수]");
                    return true;
                }
                if (args.length == 1) {
                    int money = 0;
                    Player p = (Player) talker;
                    long[] stat;
                    stat = s.getStat(p.getUniqueId().toString());
                    try {
                        money = Integer.parseInt(args[0]);
                    } catch (Exception e) {
                        p.sendMessage("Error");
                    }
                    if (money < 0) {
                        talker.sendMessage(ChatColor.DARK_AQUA + "[ Ercanel ]" + ChatColor.WHITE + "음수는 제공되지 않습니다 고객님 ^^7");
                    } else {
                        if (stat[4] > money) {
                            ItemStack paper = new ItemStack(Material.PAPER);
                            ItemMeta im = paper.getItemMeta();
                            assert im != null;
                            im.setDisplayName(ChatColor.DARK_AQUA + "[ Ercanel ] " + ChatColor.WHITE + args[0] + " 골드");
                            paper.setItemMeta(im);
                            p.getInventory().addItem(paper);
                            stat[4] = stat[4] - money;
                            s.setStat(p.getUniqueId().toString(), stat);
                        }
                        else {
                            talker.sendMessage(ChatColor.DARK_AQUA + "[ Ercanel ]" + ChatColor.WHITE + "돈이 부족합니다.");
                        }
                    }
                }
                if (args.length == 2) {
                    int money = 0;
                    int num = 0;
                    Player p = (Player) talker;
                    long[] stat;
                    stat = s.getStat(p.getUniqueId().toString());
                    try {
                        money = Integer.parseInt(args[0]);
                        num = Integer.parseInt(args[1]);
                    } catch (Exception e) {
                        p.sendMessage("Error");
                    }
                    if (money < 0) {
                        talker.sendMessage(ChatColor.DARK_AQUA + "[ Ercanel ]" + ChatColor.WHITE + "음수는 제공되지 않습니다 고객님 ^^7");
                    } else {
                        if (money < stat[4]){
                            ItemStack paper = new ItemStack(Material.PAPER);
                            ItemMeta im = paper.getItemMeta();
                            assert im != null;
                            im.setDisplayName(ChatColor.DARK_AQUA + "[ Ercanel ] " + ChatColor.WHITE + args[0] + " 골드");
                            paper.setItemMeta(im);
                            for (int i = 0; i < num; i++) {
                                p.getInventory().addItem(paper);
                            }
                            stat[4] = stat[4] - ((long) money * num);
                            s.setStat(p.getUniqueId().toString(), stat);
                        }else {
                            talker.sendMessage(ChatColor.DARK_AQUA + "[ Ercanel ]" + ChatColor.WHITE + "돈이 부족합니다.");
                        }
                    }
                }
            }
            if (label.equals("장비")){
                if (args.length < 3){
                    talker.sendMessage("/장비 이름 메테리얼 종류 등급 설명 장비능력치");
                                        //    0    1      2   3   4     5
                }
                else {
                    if (args[2].equals("보조")){
                        switch (args[3]){
                            case "일반":{
                                ItemStack lt = new ItemStack(Material.valueOf(args[1]));
                                ItemMeta tool = lt.getItemMeta();
                                Objects.requireNonNull(tool).setDisplayName(args[0]);
                                if (tool.getDisplayName().contains("_")){
                                    tool.setDisplayName(tool.getDisplayName().replace("_", " "));
                                }
                                tool.setLore(Arrays.asList("[ 보조 ]"+ "        "+ "[ 일반 ]", args[4],"레벨 제한 : " + args[5], "[ 미감정 마법서 ]"));
                                lt.setItemMeta(tool);
                                ((Player) talker).getInventory().addItem(lt);
                                return false;
                            }
                            case "레어":{
                                ItemStack lt = new ItemStack(Material.valueOf(args[1]));
                                ItemMeta tool = lt.getItemMeta();
                                Objects.requireNonNull(tool).setDisplayName(args[0]);
                                if (tool.getDisplayName().contains("_")){
                                    tool.setDisplayName(tool.getDisplayName().replace("_", " "));
                                }
                                tool.setLore(Arrays.asList("[ 보조 ]"+ "        "+ "[ 레어 ]", args[4],"레벨 제한 : " + args[5], "[ 미감정 마법서 ]"));
                                lt.setItemMeta(tool);
                                ((Player) talker).getInventory().addItem(lt);
                                return false;
                            }
                            case "에픽":{
                                ItemStack lt = new ItemStack(Material.valueOf(args[1]));
                                ItemMeta tool = lt.getItemMeta();
                                Objects.requireNonNull(tool).setDisplayName(args[0]);
                                if (tool.getDisplayName().contains("_")){
                                    tool.setDisplayName(tool.getDisplayName().replace("_", " "));
                                }
                                tool.setLore(Arrays.asList("[ 보조 ]"+ "        "+ "[ 에픽 ]", args[4],"레벨 제한 : " + args[5], "[ 미감정 마법서 ]"));
                                lt.setItemMeta(tool);
                                ((Player) talker).getInventory().addItem(lt);
                                return false;
                            }
                            case "전설":{
                                ItemStack lt = new ItemStack(Material.valueOf(args[1]));
                                ItemMeta tool = lt.getItemMeta();
                                Objects.requireNonNull(tool).setDisplayName(args[0]);
                                if (tool.getDisplayName().contains("_")){
                                    tool.setDisplayName(tool.getDisplayName().replace("_", " "));
                                }
                                tool.setLore(Arrays.asList("[ 보조 ]"+ "        "+ "[ 전설 ]", args[4],"레벨 제한 : " + args[5], "[ 미감정 마법서 ]"));
                                lt.setItemMeta(tool);
                                ((Player) talker).getInventory().addItem(lt);
                                return false;
                            }
                        }
                    }
                    if (args[2].equals("무기")){
                        switch (args[3]) {
                            case "일반": {
                                switch (args[6]) {
                                    case "근접": {
                                        ItemStack cw = new ItemStack(Material.valueOf(args[1]));
                                        ItemMeta weapon = cw.getItemMeta();
                                        Objects.requireNonNull(weapon).setDisplayName(args[0]);
                                        if (weapon.getDisplayName().contains("_")){
                                            weapon.setDisplayName(weapon.getDisplayName().replace("_", " "));
                                        }
                                        weapon.setLore(Arrays.asList("[ 무기 ]"+ "        "+ "[ 일반 ]", args[4],"레벨 제한 : " + args[5], "근접 공격력 : " + args[7], "치명타 확률 : " + args[8]));
                                        cw.setItemMeta(weapon);
                                        ((Player) talker).getInventory().addItem(cw);
                                        return false;
                                    }
                                    case "원거리": {
                                        ItemStack cw = new ItemStack(Material.valueOf(args[1]));
                                        ItemMeta weapon = cw.getItemMeta();
                                        Objects.requireNonNull(weapon).setDisplayName(args[0]);
                                        if (weapon.getDisplayName().contains("_")) {
                                            weapon.setDisplayName(weapon.getDisplayName().replace("_", " "));
                                        }
                                        weapon.setLore(Arrays.asList("[ 무기 ]"+ "        "+ "[ 일반 ]", args[4],"레벨 제한 : " + args[5], "원거리 공격력 : " + args[7], "치명타 확률 : " + args[8]));
                                        cw.setItemMeta(weapon);
                                        ((Player) talker).getInventory().addItem(cw);
                                        return false;
                                    }
                                    case "마법": {
                                        ItemStack cw = new ItemStack(Material.valueOf(args[1]));
                                        ItemMeta weapon = cw.getItemMeta();
                                        Objects.requireNonNull(weapon).setDisplayName(args[0]);
                                        if (weapon.getDisplayName().contains("_")) {
                                            weapon.setDisplayName(weapon.getDisplayName().replace("_", " "));
                                        }
                                        weapon.setLore(Arrays.asList("[ 무기 ]"+ "        "+ "[ 일반 ]", args[4],"레벨 제한 : " + args[5], "마법 공격력 : " + args[7], "치명타 확률 : " + args[8]));
                                        cw.setItemMeta(weapon);
                                        ((Player) talker).getInventory().addItem(cw);
                                        return false;
                                    }
                                }
                            }
                            case "레어": {
                                switch (args[6]) {
                                    case "근접": {
                                        ItemStack cw = new ItemStack(Material.valueOf(args[1]));
                                        ItemMeta weapon = cw.getItemMeta();
                                        Objects.requireNonNull(weapon).setDisplayName(args[0]);
                                        if (weapon.getDisplayName().contains("_")) {
                                            weapon.setDisplayName(weapon.getDisplayName().replace("_", " "));
                                        }
                                        weapon.setLore(Arrays.asList("[ 무기 ]"+ "        "+ "[ 레어 ]", args[4],"레벨 제한 : " + args[5], "근접 공격력 : " + args[7], "치명타 확률 : " + args[8]));
                                        cw.setItemMeta(weapon);
                                        ((Player) talker).getInventory().addItem(cw);
                                        return false;
                                    }
                                    case "원거리": {
                                        ItemStack cw = new ItemStack(Material.valueOf(args[1]));
                                        ItemMeta weapon = cw.getItemMeta();
                                        Objects.requireNonNull(weapon).setDisplayName(args[0]);
                                        if (weapon.getDisplayName().contains("_")) {
                                            weapon.setDisplayName(weapon.getDisplayName().replace("_", " "));
                                        }
                                        weapon.setLore(Arrays.asList("[ 무기 ]"+ "        "+ "[ 레어 ]", args[4],"레벨 제한 : " + args[5], "원거리 공격력 : " + args[7], "치명타 확률 : " + args[8]));
                                        cw.setItemMeta(weapon);
                                        ((Player) talker).getInventory().addItem(cw);
                                        return false;
                                    }
                                    case "마법": {
                                        ItemStack cw = new ItemStack(Material.valueOf(args[1]));
                                        ItemMeta weapon = cw.getItemMeta();
                                        Objects.requireNonNull(weapon).setDisplayName(args[0]);
                                        weapon.setLore(Arrays.asList("[ 무기 ]"+ "        "+ "[ 레어 ]", args[4],"레벨 제한 : " + args[5], "마법 공격력 : " + args[7], "치명타 확률 : " + args[8]));
                                        cw.setItemMeta(weapon);
                                        ((Player) talker).getInventory().addItem(cw);
                                        return false;
                                    }
                                }
                            }
                            case "에픽": {
                                switch (args[6]) {
                                    case "근접": {
                                        ItemStack cw = new ItemStack(Material.valueOf(args[1]));
                                        ItemMeta weapon = cw.getItemMeta();
                                        Objects.requireNonNull(weapon).setDisplayName(args[0]);
                                        if (weapon.getDisplayName().contains("_")) {
                                            weapon.setDisplayName(weapon.getDisplayName().replace("_", " "));
                                        }
                                        weapon.setLore(Arrays.asList("[ 무기 ]"+ "        "+ "[ 에픽 ]", args[4],"레벨 제한 : " + args[5], "근접 공격력 : " + args[7], "치명타 확률 : " + args[8]));
                                        cw.setItemMeta(weapon);
                                        ((Player) talker).getInventory().addItem(cw);
                                        return false;
                                    }
                                    case "원거리": {
                                        ItemStack cw = new ItemStack(Material.valueOf(args[1]));
                                        ItemMeta weapon = cw.getItemMeta();
                                        Objects.requireNonNull(weapon).setDisplayName(args[0]);
                                        if (weapon.getDisplayName().contains("_")) {
                                            weapon.setDisplayName(weapon.getDisplayName().replace("_", " "));
                                        }
                                        weapon.setLore(Arrays.asList("[ 무기 ]"+ "        "+ "[ 에픽 ]", args[4],"레벨 제한 : " + args[5], "원거리 공격력 : " + args[7], "치명타 확률 : " + args[8]));
                                        cw.setItemMeta(weapon);
                                        ((Player) talker).getInventory().addItem(cw);
                                        return false;
                                    }
                                    case "마법": {
                                        ItemStack cw = new ItemStack(Material.valueOf(args[1]));
                                        ItemMeta weapon = cw.getItemMeta();
                                        Objects.requireNonNull(weapon).setDisplayName(args[0]);
                                        if (weapon.getDisplayName().contains("_")) {
                                            weapon.setDisplayName(weapon.getDisplayName().replace("_", " "));
                                        }
                                        weapon.setLore(Arrays.asList("[ 무기 ]"+ "        "+ "[ 에픽 ]", args[4],"레벨 제한 : " + args[5], "마법 공격력 : " + args[7], "치명타 확률 : " + args[8]));
                                        cw.setItemMeta(weapon);
                                        ((Player) talker).getInventory().addItem(cw);
                                        return false;
                                    }
                                }
                            }
                            case "전설": {
                                switch (args[6]) {
                                    case "근접": {
                                        ItemStack cw = new ItemStack(Material.valueOf(args[1]));
                                        ItemMeta weapon = cw.getItemMeta();
                                        Objects.requireNonNull(weapon).setDisplayName(args[0]);
                                        if (weapon.getDisplayName().contains("_")) {
                                            weapon.setDisplayName(weapon.getDisplayName().replace("_", " "));
                                        }
                                        weapon.setLore(Arrays.asList("[ 무기 ]"+ "        "+ "[ 전설 ]", args[4],"레벨 제한 : " + args[5], "근접 공격력 : " + args[7], "치명타 확률 : " + args[8]));
                                        cw.setItemMeta(weapon);
                                        ((Player) talker).getInventory().addItem(cw);
                                        return false;
                                    }
                                    case "원거리": {
                                        ItemStack cw = new ItemStack(Material.valueOf(args[1]));
                                        ItemMeta weapon = cw.getItemMeta();
                                        Objects.requireNonNull(weapon).setDisplayName(args[0]);
                                        if (weapon.getDisplayName().contains("_")) {
                                            weapon.setDisplayName(weapon.getDisplayName().replace("_", " "));
                                        }
                                        weapon.setLore(Arrays.asList("[ 무기 ]"+ "        "+ "[ 전설 ]", args[4],"레벨 제한 : " + args[5], "원거리 공격력 : " + args[7], "치명타 확률 : " + args[8]));
                                        cw.setItemMeta(weapon);
                                        ((Player) talker).getInventory().addItem(cw);
                                        return false;
                                    }
                                    case "마법": {
                                        ItemStack cw = new ItemStack(Material.valueOf(args[1]));
                                        ItemMeta weapon = cw.getItemMeta();
                                        Objects.requireNonNull(weapon).setDisplayName(args[0]);
                                        if (weapon.getDisplayName().contains("_")) {
                                            weapon.setDisplayName(weapon.getDisplayName().replace("_", " "));
                                        }
                                        weapon.setLore(Arrays.asList("[ 무기 ]"+ "        "+ "[ 전설 ]", args[4],"레벨 제한 : " + args[5], "마법 공격력 : " + args[7], "치명타 확률 : " + args[8]));
                                        cw.setItemMeta(weapon);
                                        ((Player) talker).getInventory().addItem(cw);
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                    else if (args[2].equals("갑옷")){
                        switch (args[3]) {
                            case "일반": {
                                ItemStack ca = new ItemStack(Material.valueOf(args[1]));
                                ItemMeta armor = ca.getItemMeta();
                                Objects.requireNonNull(armor).setDisplayName(args[0]);
                                if (armor.getDisplayName().contains("_")) {
                                    armor.setDisplayName(armor.getDisplayName().replace("_", " "));
                                }
                                armor.setLore(Arrays.asList("[ 갑옷 ]"+ "        "+ "[ 일반 ]", args[4], "레벨 제한 : "+args[5], "체력 증가 : "+args[6], "방어력 : "+args[7], "이동 속도 : " + args[8]));
                                ca.setItemMeta(armor);
                                ((Player) talker).getInventory().addItem(ca);
                                return false;
                            }
                            case "레어": {
                                ItemStack ca = new ItemStack(Material.valueOf(args[1]));
                                ItemMeta armor = ca.getItemMeta();
                                Objects.requireNonNull(armor).setDisplayName(args[0]);
                                if (armor.getDisplayName().contains("_")) {
                                    armor.setDisplayName(armor.getDisplayName().replace("_", " "));
                                }
                                armor.setLore(Arrays.asList("[ 갑옷 ]"+ "        "+ "[ 레어 ]", args[4], "레벨 제한 : "+args[5], "체력 증가 : "+args[6], "방어력 : "+args[7], "이동 속도 : " + args[8]));
                                ca.setItemMeta(armor);
                                ((Player) talker).getInventory().addItem(ca);
                                return false;
                            }
                            case "에픽": {
                                ItemStack ca = new ItemStack(Material.valueOf(args[1]));
                                ItemMeta armor = ca.getItemMeta();
                                Objects.requireNonNull(armor).setDisplayName(args[0]);
                                if (armor.getDisplayName().contains("_")) {
                                    armor.setDisplayName(armor.getDisplayName().replace("_", " "));
                                }
                                armor.setLore(Arrays.asList("[ 갑옷 ]"+ "        "+ "[ 에픽 ]", args[4], "레벨 제한 : "+args[5], "체력 증가 : "+args[6], "방어력 : "+args[7], "이동 속도 : " + args[8]));
                                ca.setItemMeta(armor);
                                ((Player) talker).getInventory().addItem(ca);
                                return false;
                            }
                            case "전설": {
                                ItemStack ca = new ItemStack(Material.valueOf(args[1]));
                                ItemMeta armor = ca.getItemMeta();
                                Objects.requireNonNull(armor).setDisplayName(args[0]);
                                if (armor.getDisplayName().contains("_")) {
                                    armor.setDisplayName(armor.getDisplayName().replace("_", " "));
                                }
                                armor.setLore(Arrays.asList("[ 갑옷 ]"+ "        "+ "[ 전설 ]", args[4], "레벨 제한 : "+args[5], "체력 증가 : "+args[6], "방어력 : "+args[7], "이동 속도 : " + args[8]));
                                ca.setItemMeta(armor);
                                ((Player) talker).getInventory().addItem(ca);
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    @EventHandler
    public void RightClick(PlayerInteractEvent e){
        Player p = e.getPlayer();
        Action a = e.getAction();
        if (a == Action.LEFT_CLICK_AIR) {
            if (Objects.requireNonNull(p.getEquipment()).getItemInMainHand().getType() == Material.STICK) {
                if (p.getEquipment().getItemInOffHand().getType() != Material.BLAZE_ROD) {
                    p.sendMessage(ChatColor.DARK_AQUA + "[ Ercanel ]" + ChatColor.WHITE + "마법서를 왼 손에 들어주세요.");
                }
            }
        }else if(a == Action.RIGHT_CLICK_AIR){
            if (Objects.requireNonNull(p.getEquipment()).getItemInMainHand().getType() == Material.BLAZE_ROD){
                ArrayList<String> List = new ArrayList<>(Objects.requireNonNull(Objects.requireNonNull(p.getEquipment().getItemInMainHand().getItemMeta()).getLore()));
                if (List.contains("미감정")){
                    if (List.contains("일반")){
                        p.getEquipment().getItemInMainHand().getItemMeta().getLore().clear();
                        int r = DC.Random(1, 3);
                        switch (r) {
                            case 1: {
                                p.getEquipment().getItemInMainHand().getItemMeta().setLore(Collections.singletonList(ChatColor.WHITE+"[ 일반 마법 ]" +ChatColor.RED+" 불타는 총알"));
                            }
                            case 2: {
                                p.getEquipment().getItemInMainHand().getItemMeta().setLore(Collections.singletonList(ChatColor.WHITE+"[ 일반 마법 ]" +ChatColor.DARK_AQUA+" 정전기 방출"));
                            }
                            case 3: {
                                p.getEquipment().getItemInMainHand().getItemMeta().setLore(Collections.singletonList(ChatColor.WHITE+"[ 일반 마법 ]" +ChatColor.GREEN+" 날카로운 바람"));
                            }
                        }
                    }
                }
            }
            else if (p.getEquipment().getItemInMainHand().getType() == Material.PAPER){
                ItemStack total = p.getEquipment().getItemInMainHand();
                ItemMeta paper = total.getItemMeta();
                if (Objects.requireNonNull(paper).getDisplayName().contains("골드")){
                    paper.setDisplayName(ChatColor.stripColor(paper.getDisplayName().replace("[ Ercanel ] ", "")));
                    paper.setDisplayName(ChatColor.stripColor(paper.getDisplayName().replace(" 골드", "")));
                    int money = Integer.parseInt(paper.getDisplayName());
                    consumeItem(p, 1, Material.PAPER);
                    p.updateInventory();
                    long[] stat;
                    stat = s.getStat(p.getUniqueId().toString());
                    stat[4] = stat[4] + money;
                    s.setStat(p.getUniqueId().toString(), stat);
                }
            }
        }
    }

    @EventHandler
    public void onInvC(InventoryClickEvent e) {
        e.getWhoClicked();
        if (ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase("[ Status ]")) {
            SGui.StatusGui(e);
        }
    }

    @EventHandler
    public void FirstJoin(PlayerJoinEvent event){
        Player p = event.getPlayer();
        long[] stat;
        stat = s.getStat(p.getUniqueId().toString());
        if (stat[0] == Long.parseLong(null)) {
            s.CreateNewStat(event.getPlayer().getUniqueId().toString());
        }
    }

}

class RPGItem {
    private final ItemStack itemStack;

    public RPGItem(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    /*
                    String s1 = String.valueOf(Objects.requireNonNull(w.getItemMeta().getLore()).contains("공격력"));
                String s2 = String.valueOf(w.getItemMeta().getLore().contains("치명타 확률"));
                if (s1.contains("근접")) {
                    stat[13] = Long.parseLong(s1.replace("근접 공격력 : ", ""));
                }
                if (s1.contains("원거리")) {
                    stat[14] = Long.parseLong(s1.replace("원거리 공격력 : ", ""));
                }
                if (s1.contains("마법")) {
                    stat[15] = Long.parseLong(s1.replace("마법 공격력 : ", ""));
                }
                if (s2.contains("치명타")){
                    stat[31] = Long.parseLong(s2.replace("치명타 확률 : ", ""));
                }
     */
    public String get_attack_type() {
        List<String> lore = Objects.requireNonNull(itemStack.getItemMeta()).getLore();
        if (lore != null) {
            if (listcontains("근접", lore)) {
                return "근접";
            } else if (listcontains("원거리", lore)) {
                return "원거리";
            } else if (listcontains("마법", lore)) {
                return "마법";
            }
        }
        return "null";
    }

    public Double get_attack_value() {
        String attackType = get_attack_type();
        double doublevariable = 0.0;
        if (!Objects.equals(attackType, "null")) {
            List<String> lore = Objects.requireNonNull(itemStack.getItemMeta()).getLore();
            if (lore != null) {
                for (String i: lore) {
                    if (i.startsWith(attackType)) {
                        try {
                            doublevariable = Double.parseDouble(i.substring(attackType.length()));
                        } catch (NullPointerException | NumberFormatException exception) {
                            return 0.0;
                        }
                    }
                }
            }
        }
        return doublevariable;
    }

    private Boolean listcontains(String string, List<String> stringList) {
        for (String i: stringList) {
            if (Objects.equals(string, i)) {
                return true;
            }
        }
        return false;
    }

}
