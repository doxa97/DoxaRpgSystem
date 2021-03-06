package fir.sec.thi.doxarpgsystem;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Stat {
    public void CreateNewStat(String player) {
        File filename = new File("plugins/RPGStat/Stat/" + player + ".yml");
        File folder_Location1 = new File("plugins/RPGStat/");
        File folder_Location2 = new File("plugins/RPGStat/Stat");
        try {
            if (!filename.exists()) {
                folder_Location1.mkdir();
                folder_Location2.mkdir();
                filename.createNewFile();
            }
            BufferedWriter w = new BufferedWriter(new FileWriter(filename));
            w.append("레벨:1" + "\r\n" + "잔여 스탯:0" + "\r\n" + "경험치:0" +
                    "\r\n" + "최대 경험치:10" + "\r\n" + "돈:0" + "\r\n" + "기본 체력:5" +
                    "\r\n" + "체력 스탯:0" + "\r\n" + "힘 스탯:0" + "\r\n" + "명중 스탯:0" +
                    "\r\n" + "민첩 스탯:0" + "\r\n" + "지력 스탯:0" + "\r\n" + "방어도:0" +
                    "\r\n" + "활시위:0" + "\r\n" + "퀘스트 진행도:0"+ "\r\n" + "NPC:0"+ "\r\n" + "Dummy:0"+ "\r\n" + "총 치명타 확률:0"
                    + "\r\n" + "총 체력 증가:0"+ "\r\n" + "총 이동 속도:0"+ "\r\n" + "Dummy:0"+ "\r\n" + "Dummy:0"+ "\r\n" + "Dummy:0"+ "\r\n" + "Dummy:0"
                    + "\r\n" + "Dummy:0"+ "\r\n" + "Dummy:0"+ "\r\n" + "Dummy:0"+ "\r\n" + "Dummy:0"
                    + "\r\n" + "Dummy:0"+ "\r\n" + "Dummy:0"+ "\r\n" + "Dummy:0"+ "\r\n" + "Dummy:0"+"\r\n"+"퀘스트 진행도:0");
            w.flush();
            w.close();
        } catch (IOException localIoException) {
        }
    }

    public long[] getStat(String player) {
        File filename = new File("plugins/RPGStat/Stat/" + player + ".yml");
        File folder_Location1 = new File("plugins/RPGStat/");
        File folder_Location2 = new File("plugins/RPGStat/Stat");
        long[] stat = new long[32];
        try {
            if (!filename.exists()) {
                folder_Location1.mkdir();
                folder_Location2.mkdir();
                filename.createNewFile();
            }
            BufferedReader R = new BufferedReader(new FileReader(filename));
            List list = new ArrayList();
            String s;
            while ((s = R.readLine()) != null) {
                list.add(Cutter(s));
            }
            R.close();
            for (int count = 0; count < 31; count++) {
                stat[count] = (Long) list.get(count);
            }
            return stat;
        } catch (IOException localIoException) {
        }
        return stat;
    }

    public long Cutter(String line) {
        String[] cut = line.split(":");
        return Long.parseLong(cut[1]);
    }

    public void setStat(String player, long[] stat) {
        File filename = new File("plugins/RPGStat/Stat/" + player + ".yml");
        File folder_Location1 = new File("plugins/RPGStat/");
        File folder_Location2 = new File("plugins/RPGStat/Stat");
        try {
            if (!filename.exists()) {
                folder_Location1.mkdir();
                folder_Location2.mkdir();
                filename.createNewFile();
            }
            BufferedWriter W = new BufferedWriter(new FileWriter(filename));
            W.append("레벨:" + stat[0] + "\r\n" + "잔여 스탯:" + stat[1] + "\r\n" +
                    "경험치:" + stat[2] + "\r\n" + "최대 경험치:" + stat[3] + "\r\n" + "돈:" + stat[4] + "\r\n" +
                    "기본 체력:" + stat[5] + "\r\n" + "체력 스탯:" + stat[6] + "\r\n" + "힘 스탯:" + stat[7] + "\r\n" +
                    "명중 스탯:" + stat[8] + "\r\n" + "민첩 스탯:" + stat[9] + "\r\n" + "지력 스탯:" + stat[10] + "\r\n" +
                    "방어도:" + stat[11] + "\r\n" + "활시위:" + stat[12] +"\r\n"+ "퀘스트 진행도:"+ stat[13] +"\r\n" + "NPC:"
                    + stat[14] +"\r\n" + "Dummy:" +stat[15] + "\r\n" + "총 치명타 확률:" +stat[16] + "\r\n" + "총 체력 증가:"+stat[17]
                    + "\r\n" + "총 이동 속도:"+stat[18] + "\r\n" + "Dummy:"+stat[19] + "\r\n" + "Dummy:"+stat[20] + "\r\n"
                    + "Dummy:"+stat[21] + "\r\n" + "Dummy:"+stat[22]
                    + "\r\n" + "Dummy:"+stat[23] + "\r\n" + "Dummy:"+stat[24] +"\r\n" + "Dummy:"+stat[25] +"\r\n" + "Dummy:"+stat[26] +
                    "\r\n" + "Dummy:"+stat[27] + "\r\n" + "Dummy:"+stat[28] +"\r\n" + "Dummy:"+stat[29] +"\r\n" + "Dummy:"+stat[30]
                    +"\r\n"+"퀘스트 진행도:"+stat[31]);
            W.flush();
            W.close();
        } catch (IOException localIoException) {
        }
    }

    public void StatUp(long[] stat, Player player, int num) {
        if (stat[1] > 0) {
            stat[num] = stat[num] + 1;
            stat[1] = stat[1] - 1;
            setStat(player.getUniqueId().toString(), stat);
        } else {
            player.sendMessage(ChatColor.DARK_AQUA+"[Ercanel]"+ChatColor.WHITE+"잔여 스탯이 부족합니다.");
        }
    }

    public long[] LevelUP(long[] stat, Player player) {
        for (; ; ) {
            if (stat[2] < stat[3]) {
                break;
            } else {
                stat[2] = stat[2] - stat[3];
                stat[0] = stat[0] + 1;
                stat[3] = (stat[0] * 2) + stat[3];
                stat[1] = (long) ((stat[1] * 1.5) + 3);
                player.sendMessage(ChatColor.DARK_AQUA+"[ Ercanel ]"+ChatColor.WHITE+"Level Up!");
                setStat(player.getUniqueId().toString(), stat);
            }
        }
        return stat;
    }
}
