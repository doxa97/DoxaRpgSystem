package fir.sec.thi.doxarpgsystem;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;


import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;




public class Quest implements Listener {

    public Stat s = new Stat();

    @EventHandler
    public void QuestInteraction(PlayerInteractEntityEvent event){
        Player p = event.getPlayer();
        Entity e = event.getRightClicked();
        long[] Stat;
        Stat = s.getStat(p.getUniqueId().toString());
        if (e.getName().contains("이랑")){
            if (Stat[13] == 0) {
                if (Stat[14] == 0) {
                    p.sendMessage(ChatColor.GRAY + "[ 이랑 ] " + ChatColor.WHITE + "아? 일어났구나! " + p.getName() +
                            ", 오늘 표정이 좀 안좋아 보이는데?");
                    p.sendMessage(ChatColor.GRAY + "[ 이랑 ] " + ChatColor.WHITE + "어디 안좋은 건 아니지? 그보다 어제 그 소식 들었어?");
                    p.sendMessage(ChatColor.GRAY + "[ 이랑 ] " + ChatColor.WHITE + "요즘 이 주변 어수선하던데.. 설마, 진짜로 예언이 " +
                            "일어나는 건 아니겠지?");
                    p.sendMessage(ChatColor.GRAY + "[ 이랑 ] " + ChatColor.WHITE + "아! 맞다! 요즈음 옆집 아이 잘 안나오는 것 같던데, " +
                            "안부 대신 물어봐줄 수 있어?");
                    p.performCommand("tellraw @p [{\"text\":\"[ V ]\",\"color\":\"green\",\"bold\":true,\"clickEvent\":" +
                            "{\"action\":\"run_command\",\"value\":\"/sure\"}},{\"text\":\"   \"},{\"text\":\"[ X ]\",\"color\"" +
                            ":\"red\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/cancel\"}}]");
                    Stat[14] = Stat[14] + 1;
                }
            }
        }
        s.setStat(p.getUniqueId().toString(), Stat);
    }

}
