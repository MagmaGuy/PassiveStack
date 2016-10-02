/*
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.magmaguy.passivestack.animals;

import static com.magmaguy.passivestack.AnimalChecker.mobStackAmount;
import com.magmaguy.passivestack.ItemDropVelocity;
import com.magmaguy.passivestack.PassiveStack;
import static com.magmaguy.passivestack.PassiveStack.superSheepList;
import static org.bukkit.DyeColor.LIGHT_BLUE;
import static org.bukkit.DyeColor.MAGENTA;
import static org.bukkit.DyeColor.ORANGE;
import static org.bukkit.DyeColor.WHITE;
import static org.bukkit.DyeColor.YELLOW;
import static org.bukkit.DyeColor.LIME;
import static org.bukkit.DyeColor.PINK;
import static org.bukkit.DyeColor.GRAY;
import static org.bukkit.DyeColor.SILVER;
import static org.bukkit.DyeColor.CYAN;
import static org.bukkit.DyeColor.PURPLE;
import static org.bukkit.DyeColor.BLUE;
import static org.bukkit.DyeColor.BROWN;
import static org.bukkit.DyeColor.GREEN;
import static org.bukkit.DyeColor.RED;
import java.util.List;
import java.util.Random;
import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Material.MONSTER_EGG;
import static org.bukkit.Material.MUTTON;
import static org.bukkit.Material.WOOL;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class SheepHandler implements Listener{
    
    public void sheepConstructor(List<Sheep> sheepList, int mobStackAmount, Plugin plugin){
        
        int sheepCounter = mobStackAmount;
        
        for (Sheep sheep : sheepList)
        {
            
            if (sheepCounter > 1)
            {
                
                sheep.remove();
                sheepCounter--;
                
            } else {
                
                sheep.setCustomName("Super Sheep");
                sheep.setCustomNameVisible(true);
                
                sheep.setMaxHealth(sheep.getMaxHealth() * mobStackAmount);
                sheep.setHealth(sheep.getMaxHealth());
                getLogger().info("SuperSheep spawned.");
                
                if (!superSheepList.contains(sheep))
                {
                    
                    superSheepList.add(sheep);
                    
                }
                
                
            }
            
        }
        
    }
    
    private PassiveStack plugin;
    
    public SheepHandler(Plugin plguin){
        
        this.plugin = plugin;
        
    }
    
    @EventHandler
    public void superDrops (EntityDamageByEntityEvent event)
    {
        
        if (superSheepList.contains(event.getEntity()))
        {
            
            Random random = new Random();
            
            Sheep sheep = (Sheep) event.getEntity();
            
            double damage = event.getFinalDamage();
            //health is hardcoded here, maybe change it at some point?
            double dropChance = damage / 8;
            double dropRandomizer = random.nextDouble();
            //this rounds down
            int dropMinAmount = (int) dropChance;
            
            ItemStack muttonStack = new ItemStack(MUTTON, random.nextInt(2) + 1);
            ItemStack woolStack = new ItemStack(WOOL, 1);
            
            //for different wool colors
            switch (sheep.getColor()) {
                case WHITE:
                    woolStack = new ItemStack(WOOL, 1);
                    break;
                case ORANGE:
                    woolStack = new ItemStack(WOOL, 1, (short) 1);
                    break;
                case MAGENTA:
                    woolStack = new ItemStack(WOOL, 1, (short) 2);
                    break;
                case LIGHT_BLUE:
                    woolStack = new ItemStack(WOOL, 1, (short) 3);
                    break;
                case YELLOW:
                    woolStack = new ItemStack(WOOL, 1, (short) 4);
                    break;
                case LIME:
                    woolStack = new ItemStack(WOOL, 1, (short) 5);
                    break;
                case PINK:
                    woolStack = new ItemStack(WOOL, 1, (short) 6);
                    break;
                case GRAY:
                    woolStack = new ItemStack(WOOL, 1, (short) 7);
                    break;
                case SILVER:
                    woolStack = new ItemStack(WOOL, 1, (short) 8);
                    break;
                case CYAN:
                    woolStack = new ItemStack(WOOL, 1, (short) 9);
                    break;
                case PURPLE:
                    woolStack = new ItemStack(WOOL, 1, (short) 10);
                    break;
                case BLUE:
                    woolStack = new ItemStack(WOOL, 1, (short) 11);
                    break;
                case BROWN:
                    woolStack = new ItemStack(WOOL, 1, (short) 12);
                    break;
                case GREEN:
                    woolStack = new ItemStack(WOOL, 1, (short) 13);
                    break;
                case RED:
                    woolStack = new ItemStack(WOOL, 1, (short) 14);
                    break;
                case BLACK:
                    woolStack = new ItemStack(WOOL, 1, (short) 15);
                    break;
                default:
                    getLogger().info("Something went wrong with the sheep colors, one is missing");
                    break;
            }
            
            for (int i = 0; i < dropMinAmount; i++)
            {
                
                sheep.getWorld().dropItem(sheep.getLocation(), muttonStack).setVelocity(ItemDropVelocity.ItemDropVelocity());
                sheep.getWorld().dropItem(sheep.getLocation(), woolStack).setVelocity(ItemDropVelocity.ItemDropVelocity());
                
            }
            
            if (dropChance > dropRandomizer)
            {
                
                sheep.getWorld().dropItem(sheep.getLocation(), muttonStack).setVelocity(ItemDropVelocity.ItemDropVelocity());
                sheep.getWorld().dropItem(sheep.getLocation(), woolStack).setVelocity(ItemDropVelocity.ItemDropVelocity());
                
            }
            
        }
        
    }
    
    @EventHandler
    public void onDeath(EntityDeathEvent event){
        
        if (event.getEntity() instanceof Sheep)
            
        {
            
            Sheep sheep = (Sheep) event.getEntity();
            
            if(superSheepList.contains(sheep))
            {
                
                ItemStack sheepMonsterEgg = new ItemStack (MONSTER_EGG, 2, (short) 91);
                sheep.getWorld().dropItem(sheep.getLocation(), sheepMonsterEgg);
                
                int index = superSheepList.indexOf(sheep);
                superSheepList.remove(index);
                
            }
            
        }
        
    }
    
    @EventHandler
    public void onShear(PlayerShearEntityEvent event){
        
        if (superSheepList.contains(event.getEntity()))
        {
            
            Sheep sheep = (Sheep) event.getEntity();
            
            for (int i = 0; i < mobStackAmount; i++)
            {
                
                sheep.getWorld().dropItem(sheep.getLocation(), woolStackRandomizer(sheep)).setVelocity(ItemDropVelocity.ItemDropVelocity());
                
            }
            
        }
        
    }
    
    public ItemStack woolStackRandomizer (Sheep sheep){
        
        
        Random random = new Random();
        
        ItemStack woolStack = new ItemStack(WOOL, ((random.nextInt(3) + 1) * mobStackAmount));
        
        switch (sheep.getColor()) {
            case WHITE:
                woolStack = new ItemStack(WOOL, (random.nextInt(3) + 1));
                break;
            case ORANGE:
                woolStack = new ItemStack(WOOL, ((random.nextInt(3) + 1)), (short) 1);
                break;
            case MAGENTA:
                woolStack = new ItemStack(WOOL, ((random.nextInt(3) + 1)), (short) 2);
                break;
            case LIGHT_BLUE:
                woolStack = new ItemStack(WOOL, ((random.nextInt(3) + 1)), (short) 3);
                break;
            case YELLOW:
                woolStack = new ItemStack(WOOL, ((random.nextInt(3) + 1)), (short) 4);
                break;
            case LIME:
                woolStack = new ItemStack(WOOL, ((random.nextInt(3) + 1)), (short) 5);
                break;
            case PINK:
                woolStack = new ItemStack(WOOL, ((random.nextInt(3) + 1)), (short) 6);
                break;
            case GRAY:
                woolStack = new ItemStack(WOOL, ((random.nextInt(3) + 1)), (short) 7);
                break;
            case SILVER:
                woolStack = new ItemStack(WOOL, ((random.nextInt(3) + 1)), (short) 8);
                break;
            case CYAN:
                woolStack = new ItemStack(WOOL, ((random.nextInt(3) + 1)), (short) 9);
                break;
            case PURPLE:
                woolStack = new ItemStack(WOOL, ((random.nextInt(3) + 1)), (short) 10);
                break;
            case BLUE:
                woolStack = new ItemStack(WOOL, ((random.nextInt(3) + 1)), (short) 11);
                break;
            case BROWN:
                woolStack = new ItemStack(WOOL, ((random.nextInt(3) + 1)), (short) 12);
                break;
            case GREEN:
                woolStack = new ItemStack(WOOL, ((random.nextInt(3) + 1)), (short) 13);
                break;
            case RED:
                woolStack = new ItemStack(WOOL, ((random.nextInt(3) + 1)), (short) 14);
                break;
            case BLACK:
                woolStack = new ItemStack(WOOL, ((random.nextInt(3) + 1)), (short) 15);
                break;
            default:
                getLogger().info("Something went wrong with the sheep colors, one is missing");
                break;
        }
        
        return woolStack;
        
    } 
    
}
