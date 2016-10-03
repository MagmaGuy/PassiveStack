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

import com.magmaguy.passivestack.PassiveStack;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.IronGolem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.Random;

import static com.magmaguy.passivestack.PassiveStack.superIronGolemList;
import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Material.IRON_INGOT;
import static org.bukkit.Material.RED_ROSE;

public class IronGolemHandler implements Listener{
    
    public void ironGolemConstructor(List<IronGolem> ironGolemList, int mobStackAmount, Plugin plugin){
        
        int ironGolemCounter = mobStackAmount;
        
        for (IronGolem ironGolem : ironGolemList)
        {
            
            if (ironGolemCounter > 1)
            {
                
                ironGolem.remove();
                ironGolemCounter--;
                
            } else {
                
                ironGolem.setCustomName("Super Iron Golem");
                ironGolem.setCustomNameVisible(true);
                
                ironGolem.setMaxHealth(ironGolem.getMaxHealth() * mobStackAmount);
                ironGolem.setHealth(ironGolem.getMaxHealth());
                
                getLogger().info("Super Iron Golem spawned.");
                
                ironGolem.setMetadata("SuperIronGolem", new FixedMetadataValue(plugin, true));
                
            }
            
        }
        
    }
    
    private PassiveStack plugin;
    
    public IronGolemHandler(Plugin plugin){
        
        this.plugin = (PassiveStack) plugin;
        
    }
    
    @EventHandler
    public void superDrops (EntityDamageByEntityEvent event){
        
        if (event.getEntity().hasMetadata("SuperIronGolem"))
        {
            
            Random random = new Random();
            
            IronGolem ironGolem = (IronGolem) event.getEntity();
            
            double damage = event.getFinalDamage();
            //health is hardcoded here, maybe change it at some point?
            double dropChance = damage / 100;
            double dropRandomizer = random.nextDouble();
            //this rounds down
            int dropMinAmount = (int) dropChance;
            
            ItemStack ironStack = new ItemStack(IRON_INGOT, random.nextInt(3) + 4);
            ItemStack poppyStack = new ItemStack(RED_ROSE, random.nextInt(3));
            
            for (int i = 0; i < dropMinAmount; i++)
            {
                
                ironGolem.getWorld().dropItem(ironGolem.getLocation(), ironStack);
                ironGolem.getWorld().dropItem(ironGolem.getLocation(), poppyStack);

                ExperienceOrb xpDrop = ironGolem.getWorld().spawn(ironGolem.getLocation(), ExperienceOrb.class);
                xpDrop.setExperience(random.nextInt(3) + 1);
                
            }
            
            if (dropChance > dropRandomizer)
            {
                
                ironGolem.getWorld().dropItem(ironGolem.getLocation(), ironStack);
                ironGolem.getWorld().dropItem(ironGolem.getLocation(), poppyStack);

                ExperienceOrb xpDrop = ironGolem.getWorld().spawn(ironGolem.getLocation(), ExperienceOrb.class);
                xpDrop.setExperience(random.nextInt(3) + 1);
                
            }
            
        }
        
    }
    
}
