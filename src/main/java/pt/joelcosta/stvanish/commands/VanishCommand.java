package pt.joelcosta.stvanish.commands;

import com.connorlinfoot.actionbarapi.ActionBarAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import pt.joelcosta.stvanish.Main;

import java.util.ArrayList;

import static pt.joelcosta.stvanish.Main.*;

public class VanishCommand implements CommandExecutor {

    Main main = JavaPlugin.getPlugin(Main.class);
    private final ArrayList<Player> vanishList = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!(commandSender instanceof Player)) commandSender.sendMessage("§cComando apenas pode ser executado em jogo.");

        assert commandSender instanceof Player;
        Player player = (Player) commandSender;

        if(command.getName().equalsIgnoreCase("vanish")){
            if (player.hasPermission(getInstance().getConfig().getString("Permission"))){
                if (vanishList.contains(player)){
                    for (Player p : Bukkit.getOnlinePlayers()){
                        p.showPlayer(player);
                    }
                    vanishList.remove(player);
                    player.sendMessage(getInstance().getConfig().getString("VanishMessages.Leave").replace("&", "§"));
                } else {
                    for (Player p : Bukkit.getOnlinePlayers()){
                        p.hidePlayer(player);
                        Bukkit.getServer().getScheduler().runTaskTimer(main, () -> {
                            if (vanishList.contains(player)){
                                if (vanishList.size() <= 1){
                                    ActionBarAPI.sendActionBar(player, "§aSomente tu em modo invisível.");
                                } else {
                                    int amount = vanishList.size() - 1;
                                    ActionBarAPI.sendActionBar(player, "§aTu e mais §f" + amount + " §aestão em modo invisível");
                                }
                            }
                        }, 0L, 20L);
                    }
                    vanishList.add(player);
                    player.sendMessage(getInstance().getConfig().getString("VanishMessages.Join").replace("&", "§"));
                }
            } else {
                player.sendMessage(getInstance().getConfig().getString("VanishMessages.NoPermission").replace("&", "§"));
            }
        }


        return false;
    }

}
