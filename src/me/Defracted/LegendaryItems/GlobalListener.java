package me.Defracted.LegendaryItems;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.DragonFireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.*;

public class GlobalListener implements Listener {
    static Main plugin;

    public GlobalListener(Main instance) {
        plugin = instance;
    }

    private List<String> crouchingPlayersWithMjolnir = new ArrayList<>();
    private Map<UUID, Long> lightningStrikeCooldowns = new HashMap<UUID, Long>();
    private Map<UUID, Long> drgonBallCooldowns = new HashMap<UUID, Long>();

    @EventHandler
    public void onJump(PlayerMoveEvent event) {
        Player player = (Player) event.getPlayer();

        // Seven Leagyes
        if (player.getInventory().getBoots() != null)
            if (player.getInventory().getBoots().getItemMeta().getDisplayName().contains("Seven League Boots"))
                if (player.getInventory().getBoots().getItemMeta().hasLore())
                    if (event.getFrom().getY() < event.getTo().getY() &&
                            player.getLocation().subtract(0, 1, 0).getBlock().getType() != Material.AIR)
                        if (event.getFrom().getY() < event.getTo().getY() &&
                                player.getLocation().subtract(0, 1, 0).getBlock().getType() != Material.WATER) {
                            player.setVelocity(player.getLocation().getDirection().multiply(1).setY(1));
                        }
    }

    @EventHandler
    public void onFall(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            // Seven Leagyes
            if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                if (player.getInventory().getBoots() != null)
                    if (player.getInventory().getBoots().getItemMeta().getDisplayName().contains("Seven League Boots"))
                        if (player.getInventory().getBoots().getItemMeta().hasLore()) {
                            event.setCancelled(true);
                        }
            }
        }
    }

    @EventHandler
    public void toggleSneakEvent(PlayerToggleSneakEvent event) {
        Player player = (Player) event.getPlayer();

        // Проверяем, если у игрока в руке топор Мьёльнир
        if (player.getInventory().getItemInMainHand().getType() == Material.DIAMOND_AXE)
            if (player.getInventory().getItemInMainHand().getItemMeta().hasLore())
                if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("The Mjöllnir")) {
                    // Если сидит - вносим в список сидящих с Мьёлбниром
                    // Если нет - проверяем нгаличие в списке сидящих и убираем оттуда
                    if (event.isSneaking()) {
                        crouchingPlayersWithMjolnir.add(player.getName());
                    } else {
                        if (crouchingPlayersWithMjolnir.contains(player.getName())) {
                            crouchingPlayersWithMjolnir.remove(player.getName());
                        }
                    }
                }
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        Player player = (Player) event.getPlayer();

        if (player.getInventory().getItemInMainHand().getType() == Material.DIAMOND_AXE)
            if (player.getInventory().getItemInMainHand().getItemMeta().hasLore())
                if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("The Mjöllnir")) {
                    // ПКМ
                    if (event.getAction() == Action.RIGHT_CLICK_AIR) {
                        // Проверяем, есть ли у игрока какие-либо кулдауны
                        if (lightningStrikeCooldowns.containsKey(player.getUniqueId())) {
                            // Сверяем момент получения послежнего кулдауна, с текущим
                            if (lightningStrikeCooldowns.get(player.getUniqueId()) > System.currentTimeMillis()) {
                                long timeReset = (lightningStrikeCooldowns.get(player.getUniqueId()) - System.currentTimeMillis()) / 1000;
                                player.sendMessage(ChatColor.RED + "Удар молний можно будет использовать через " + timeReset + " секунд(у).");
                                return;
                            }
                        }

                        World world = player.getWorld();
                        Block targetBlock = player.getTargetBlock((Set<Material>) null, 20);

                        // Если блок-цель = воздуху, ничего не делаем.
                        if (targetBlock.getType() == Material.AIR) {
                            return;
                        }

                        Location strikingLocationMain = targetBlock.getLocation();

                        for (int i = 0; i <= 5; i++) {
                            world.strikeLightning(strikingLocationMain);
                        }

                        // Обновляем данные в хэшкарте кулдаунов
                        lightningStrikeCooldowns.put(player.getUniqueId(), System.currentTimeMillis() + (25 * 1000));
                        return;
                    }

                    // ЛКМ
                    if (event.getAction() == Action.LEFT_CLICK_AIR) {
                        if (crouchingPlayersWithMjolnir.contains(player.getName())) {
                            if (drgonBallCooldowns.containsKey(player.getUniqueId())) {
                                if (drgonBallCooldowns.get(player.getUniqueId()) > System.currentTimeMillis()) {
                                    long timeReset = (drgonBallCooldowns.get(player.getUniqueId()) - System.currentTimeMillis()) / 1000;
                                    player.sendMessage(ChatColor.DARK_PURPLE + "Шар дракона Энда можно будет использовать через " + timeReset + " секунд(у).");
                                    return;
                                }
                            }

                            player.launchProjectile(DragonFireball.class);
                            drgonBallCooldowns.put(player.getUniqueId(), System.currentTimeMillis() + (15 * 1000));
                        }
                    }
                }
    }
}
