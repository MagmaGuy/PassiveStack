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

import com.magmaguy.passivestack.ItemDropVelocity;
import com.magmaguy.passivestack.PassiveStack;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Pig;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.Random;

import static com.magmaguy.passivestack.PassiveStack.superPigList;
import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Material.MONSTER_EGG;
import static org.bukkit.Material.PORK;

public class PigHandler implements Listener{
    
    public void pigConstructor(List<Pig> pigList, int mobStackAmount, Plugin plugin){
        
        int pigCounter = mobStackAmount;
        
        for (Pig pig : pigList)
        {
            
            if (pigCounter > 1)
            {
                
                pig.remove();
                pigCounter--;
                
            } else {
                
                pig.setCustomName("Super Pig");
                pig.setCustomNameVisible(true);
                
                pig.setMaxHealth(pig.getMaxHealth() * mobStackAmount);
                pig.setHealth(pig.getMaxHealth());
                
                getLogger().info("SuperPig spawned.");
                
                pig.setMetadata("SuperPig", new FixedMetadataValue(plugin, true));
                
            }
            
        }
        
    }
    
    private PassiveStack plugin;
    
    public PigHandler(Plugin plugin){
        
        this.plugin = (PassiveStack) plugin;
        
    }
    
    @EventHandler
    public void superDrops (EntityDamageByEntityEvent event){
        
        if (event.getEntity().hasMetadata("SuperPig"))
        {
            
            Random random = new Random();
        
            Pig pig = (Pig) event.getEntity();

            double damage = event.getFinalDamage();
            //health is hardcoded here, maybe change it at some point?
            double dropChance = damage / 10;
            double dropRandomizer = random.nextDouble();
            //this rounds down
            int dropMinAmount = (int) dropChance;

            ItemStack porkchopStack = new ItemStack(PORK, random.nextInt(3) + 1);

            for (int i = 0; i < dropMinAmount; i++)
            {

                pig.getWorld().dropItem(pig.getLocation(), porkchopStack).setVelocity(ItemDropVelocity.ItemDropVelocity());

                ExperienceOrb xpDrop = pig.getWorld().spawn(pig.getLocation(), ExperienceOrb.class);
                xpDrop.setExperience(random.nextInt(3) + 1);

            }

            if (dropChance > dropRandomizer)
            {

                pig.getWorld().dropItem(pig.getLocation(), porkchopStack).setVelocity(ItemDropVelocity.ItemDropVelocity());

                ExperienceOrb xpDrop = pig.getWorld().spawn(pig.getLocation(), ExperienceOrb.class);
                xpDrop.setExperience(random.nextInt(3) + 1);

            }
            
        }

    }
    
    @EventHandler
    public void onDeath(EntityDeathEvent event){
        
        if (event.getEntity() instanceof Pig)
        {
            
            Pig pig = (Pig) event.getEntity();
            
            if (pig.hasMetadata("SuperPig"))
            {
                
                ItemStack pigMonsterEgg = new ItemStack (MONSTER_EGG, 2, (short) 90);
                pig.getWorld().dropItem(pig.getLocation(), pigMonsterEgg);
                
            }
            
        }
        
    }
    
}
