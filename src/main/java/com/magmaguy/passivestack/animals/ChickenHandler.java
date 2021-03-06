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

/**
 * Created by MagmaGuy on 02/10/2016.
 */

import com.magmaguy.passivestack.ItemDropVelocity;
import com.magmaguy.passivestack.PassiveStack;
import org.bukkit.Bukkit;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.magmaguy.passivestack.PassiveStack.superChickenList;
import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Material.*;

//Reminder: This is getting called every second (20 ticks)
public class ChickenHandler implements Listener{
    
    public void chickenConstructor(List<Chicken> chickenList, int mobStackAmount, Plugin plugin){
        
        int chickenCounter = mobStackAmount;
        
        for (Chicken chicken : chickenList)
        {

            if (chickenCounter > 1)
            {
                
                chicken.remove();
                chickenCounter--;
                
            } else {

                chicken.setCustomName("Super Chicken");
                chicken.setCustomNameVisible(true);

                chicken.setMaxHealth(chicken.getMaxHealth() * mobStackAmount);
                chicken.setHealth(chicken.getMaxHealth());
                
                getLogger().info("SuperChicken spawned.");
                
                chicken.setMetadata("SuperChicken", new FixedMetadataValue(plugin, true));

            }

        }
        
    }


    private PassiveStack plugin;
    
    public ChickenHandler(Plugin plugin){
        
        this.plugin = (PassiveStack) plugin;
        
    }


    @EventHandler
    public void superDrops (EntityDamageByEntityEvent event){

        if (event.getEntity().hasMetadata("SuperChicken"))
        {
            
            Random random = new Random();
            
            Chicken chicken = (Chicken) event.getEntity();
            
            double damage = event.getFinalDamage();
            //health is hardcoded here, maybe change it at some point?
            double dropChance = damage / 4;
            double dropRandomizer = random.nextDouble();
            //this rounds down
            int dropMinAmount = (int) dropChance;
            
            ItemStack featherStack = new ItemStack(FEATHER, (random.nextInt(2) + 1));
            ItemStack poultryStack = new ItemStack(RAW_CHICKEN, 1);

            for(int i = 0; i < dropMinAmount; i++)
            {
                
                chicken.getWorld().dropItem(chicken.getLocation(), featherStack).setVelocity(ItemDropVelocity.ItemDropVelocity());
                chicken.getWorld().dropItem(chicken.getLocation(), poultryStack).setVelocity(ItemDropVelocity.ItemDropVelocity());

                ExperienceOrb xpDrop = chicken.getWorld().spawn(chicken.getLocation(), ExperienceOrb.class);
                xpDrop.setExperience(random.nextInt(3) + 1);

            }
            
            if (dropChance > dropRandomizer)
            {
                
                chicken.getWorld().dropItem(chicken.getLocation(), featherStack).setVelocity(ItemDropVelocity.ItemDropVelocity());
                chicken.getWorld().dropItem(chicken.getLocation(), poultryStack).setVelocity(ItemDropVelocity.ItemDropVelocity());

                ExperienceOrb xpDrop = chicken.getWorld().spawn(chicken.getLocation(), ExperienceOrb.class);
                xpDrop.setExperience(random.nextInt(3) + 1);

            }
            
        }
        
    }


    @EventHandler
    public void onDeath(EntityDeathEvent event){
        
        if (event.getEntity().hasMetadata("SuperChicken"))
        {
            
            Chicken chicken = (Chicken) event.getEntity();
            
            if (chicken.hasMetadata("SuperChicken"))
            {
                
                ItemStack chickenMonsterEgg = new ItemStack(MONSTER_EGG, 2, (short) 93);
                chicken.getWorld().dropItem(chicken.getLocation(), chickenMonsterEgg);
                
            }
            
        }
        
    }
    
    
    public void superEggs (){

        List<Chicken> tempChickenList = new ArrayList<>();

        for (Object world : PassiveStack.worldList)
        {

            List entityList = Bukkit.getWorld(world.toString()).getEntities();

            for (Object object : entityList)
            {

                if(object instanceof Chicken)
                {

                    if(((Chicken) object).hasMetadata("SuperChicken"))
                    {

                        tempChickenList.add((Chicken) object);

                    }

                }

            }

        }

        if (tempChickenList.size() > 0)
        {
            
            Random random = new Random();
            int eggChance = random.nextInt(10);

            //there should be a 0.1 chance of eggs spawning every second
            if(eggChance == 1)
            {

                for (Chicken chicken : tempChickenList)
                {
                    
                    ItemStack eggStack = new ItemStack(EGG, 1);

                    chicken.getWorld().dropItem(chicken.getLocation(), eggStack).setVelocity(ItemDropVelocity.ItemDropVelocity());

                }

            }

        }
        
        
    }
    
}
