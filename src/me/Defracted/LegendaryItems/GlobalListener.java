package me.Defracted.LegendaryItems;

import me.Defracted.LegendaryItems.Items.SevenLeagueBoots;
import me.Defracted.LegendaryItems.Items.TheMjollnir;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.FluidCollisionMode;
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

    private List<UUID> crouchingPlayersWithMjolnir = new ArrayList<UUID>();
    private Map<UUID, Long> lightningStrikeCooldowns = new HashMap<UUID, Long>();
    private Map<UUID, Long> dragonBallCooldowns = new HashMap<UUID, Long>();

    @EventHandler
    public void toggleSneakEvent(PlayerToggleSneakEvent event) {
        Player player = (Player) event.getPlayer();

        // Проверяем, если у игрока в руке топор Мьёльнир
        if (player.getInventory().getItemInMainHand().isSimilar(TheMjollnir.get())) {
            // Если сидит - вносим в список сидящих с Мьёлбниром
            // Если нет - проверяем нгаличие в списке сидящих и убираем оттуда
            if (event.isSneaking()) {
                crouchingPlayersWithMjolnir.add(player.getUniqueId());
            } else {
                if (crouchingPlayersWithMjolnir.contains(player.getUniqueId())) {
                    crouchingPlayersWithMjolnir.remove(player.getUniqueId());
                }
            }
        }
    }

    @EventHandler
    public void onJump(PlayerMoveEvent event) {
        Player player = (Player) event.getPlayer();

        // Подбрасывает в небо, если носит севен лиги
        if (player.getInventory().getBoots() != null)
            if (player.getInventory().getBoots().isSimilar(SevenLeagueBoots.get()))
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

            // Отмена ивента, если у челвоека ботинки == севенлигам
            if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                if (player.getInventory().getBoots() != null)
                    if (player.getInventory().getBoots().isSimilar(SevenLeagueBoots.get())) {
                        event.setCancelled(true);
                    }
            }
        }
    }

    @EventHandler
    public void onHit(EntityDamageEvent event) {
        if ((event.getEntity() instanceof Player)) {
            Player player = (Player) event.getEntity();

            // Отмена ивента, если в руках мьёлнир
            if ((event.getCause() == EntityDamageEvent.DamageCause.LIGHTNING) ||
                    (event.getCause() == EntityDamageEvent.DamageCause.FIRE) ||
                    (event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK))
                if (player.getInventory().getItemInMainHand().isSimilar(TheMjollnir.get())) {
                    event.setCancelled(true);
                }
        }
    }


    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        Player player = (Player) event.getPlayer();

        if (player.getInventory().getItemInMainHand().isSimilar(TheMjollnir.get())) {
            // ПКМ
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
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
                Block targetBlock = player.getTargetBlockExact(20, FluidCollisionMode.NEVER);

                if (targetBlock == null) {
                    return;
                }

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
            if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
                if (crouchingPlayersWithMjolnir.contains(player.getUniqueId())) {
                    if (dragonBallCooldowns.containsKey(player.getUniqueId())) {
                        if (dragonBallCooldowns.get(player.getUniqueId()) > System.currentTimeMillis()) {
                            long timeReset = (dragonBallCooldowns.get(player.getUniqueId()) - System.currentTimeMillis()) / 1000;
                            player.sendMessage(ChatColor.DARK_PURPLE + "Шар дракона Энда можно будет использовать через " + timeReset + " секунд(у).");
                            return;
                        }
                    }

                    player.launchProjectile(DragonFireball.class);
                    dragonBallCooldowns.put(player.getUniqueId(), System.currentTimeMillis() + (15 * 1000));
                }
            }
        }
    }
}
