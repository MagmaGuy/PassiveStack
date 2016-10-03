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

package com.magmaguy.passivestack;

import com.magmaguy.passivestack.animals.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

import static com.magmaguy.passivestack.PassiveStack.*;
import static org.bukkit.Bukkit.getLogger;

public class AnimalChecker {
    
    private PassiveStack plugin;
    
    public AnimalChecker (Plugin plugin){
        
        this.plugin = (PassiveStack) plugin;
        
    }
    
    public static final int mobStackAmount = 50;
    private final int range = 15;
    
    private final int chickenHealth = 4;
    private final int cowHealth = 10;
    private final int mushroomCowHealth = 10;
    private final int pigHealth = 10;
    private final int sheepHealth = 8;
    private final int ironGolemHealth = 100;
    
    //for now scanning is instant and creating mobs is limited to 1 per second
    //however, they should be decoupled at some point because if a mob is created
    //it will stop scanning at that point (easiest solution right now would be to
    //move the scanners to after ALL the checkers)
    public void checkAnimals(){
        
        for(Object world : PassiveStack.worldList)
        {
            
            List entityList = Bukkit.getWorld(world.toString()).getEntities();
            
            for (Object object : entityList){
                
                if (object instanceof Chicken)
                {
                    
                    Chicken chicken = (Chicken) object;
                    
                    //Check for existing SuperChicken using HP because it's just easier
                    if (chicken.getMaxHealth() == chickenHealth * mobStackAmount && chicken.getHealth() != 0
                            && !chicken.hasMetadata("SuperChicken"))
                    {

                        chicken.setMetadata("SuperChicken", new FixedMetadataValue(plugin, true));
                        getLogger().info("Found a Super Chicken.");

                    }
                    
                    
                    if (!chicken.hasMetadata("SuperChicken"))
                    {
                        
                        if (scanNearbyChicken(chicken))
                        {
                            
                            //forces the creation of only 1 supermob per second, for faster
                            //operations and making sure it doesn't mess up the count
                            return;
                            
                        }
                        
                    }
                    
                }
                
                else if (object instanceof Cow && !(object instanceof MushroomCow))
                {
                    
                    Cow cow = (Cow) object;
                    
                    if (cow.getMaxHealth() == cowHealth * mobStackAmount && cow.getHealth() != 0 && !cow.hasMetadata("SuperCow"))
                    {

                        cow.setMetadata("SuperCow", new FixedMetadataValue(plugin, true));
                        getLogger().info("Found a Super Cow.");
                        
                    }
                    
                    if (!cow.hasMetadata("SuperCow"))
                    {
                        
                        if (scanNearbyCow(cow))
                        {
                            
                            return;
                            
                        }
                        
                    }
                    
                }
                
                else if (object instanceof MushroomCow)
                {
                    
                    MushroomCow mushroomCow = (MushroomCow) object;
                    
                    if (mushroomCow.getMaxHealth() == mushroomCowHealth * mobStackAmount && mushroomCow.getHealth() != 0
                            && !mushroomCow.hasMetadata("SuperMushroomCow"))
                    {

                        mushroomCow.setMetadata("SuperMushroomCow", new FixedMetadataValue(plugin, true));

                        getLogger().info("Found a Super Mushroom Cow.");
                        
                    }
                    
                    if (!mushroomCow.hasMetadata("SuperMushroomCow"))
                    {
                        
                        if (scanNearbyMushroomCow(mushroomCow))
                        {
                            
                            return;
                            
                        }
                                
                    }
                    
                }
                
                else if (object instanceof Pig)
                {
                    
                    Pig pig = (Pig) object;
                    
                    if (pig.getMaxHealth() == pigHealth * mobStackAmount && pig.getHealth() != 0 && !pig.hasMetadata("SuperPig"))
                    {

                        pig.setMetadata("SuperPig", new FixedMetadataValue(plugin, true));

                        getLogger().info("Found a Super Pig.");
                        
                    }
                    
                    if (!pig.hasMetadata("SuperPig"))
                    {
                        
                        if (scanNearbyPig(pig))
                        {
                            
                            return;
                            
                        }
                        
                    }
                    
                }
                
                else if (object instanceof Sheep)
                {
                    
                    Sheep sheep = (Sheep) object;
                    
                    if (sheep.getMaxHealth() == sheepHealth * mobStackAmount && sheep.getHealth() != 0 && !sheep.hasMetadata("SuperSheep"))
                    {

                        sheep.setMetadata("SuperSheep", new FixedMetadataValue(plugin, true));

                        getLogger().info("Found a Super Sheep");
                        
                    }
                    
                    if (!sheep.hasMetadata("SuperSheep"))
                    {
                        
                        if (scanNearbySheep(sheep))
                        {
                            
                            return;
                            
                        }
                        
                    }
                    
                } else if (object instanceof IronGolem) {
                    
                    IronGolem ironGolem = (IronGolem) object;
                    
                    if (ironGolem.getMaxHealth() == ironGolemHealth * mobStackAmount && ironGolem.getHealth() != 0
                            && !ironGolem.hasMetadata("SuperIronGolem"))
                    {

                        ironGolem.setMetadata("SuperIronGolem", new FixedMetadataValue(plugin, true));

                        getLogger().info("Found an Iron Golem.");
                        
                    }
                    
                    if (!ironGolem.hasMetadata("SuperIronGolem"))
                    {
                        
                        if (scanNearbyIronGolem(ironGolem))
                        {
                            
                            return;
                            
                        }
                        
                    }
                    
                }
                
            }
            
        }
        
    }
    
    
    public boolean scanNearbyChicken(Chicken chicken){
        
        List chickenList = new ArrayList<Chicken>();
        chickenList.add(chicken);
        
        for (Entity entityNearChicken : chicken.getNearbyEntities(range, range, range))
        {
            
            if (entityNearChicken instanceof Chicken && !entityNearChicken.hasMetadata("SuperChicken"))
            {
                
                if (chickenList.size() < mobStackAmount)
                {
                    
                    chickenList.add(entityNearChicken);
                    
                }
                
                if (chickenList.size() >= mobStackAmount)
                {

                    ChickenHandler chickenHandler = new ChickenHandler(plugin);
                    chickenHandler.chickenConstructor(chickenList, mobStackAmount, plugin);

                    return true;
                    
                }

            }

        }
        
        return false;
        
    }
    
    
    public boolean scanNearbyCow(Cow cow){
        
        List cowList = new ArrayList<Cow>();
        cowList.add(cow);
        
        for (Entity entityNearCow : cow.getNearbyEntities(range, range, range))
        {
            
            if (entityNearCow instanceof Cow && !entityNearCow.hasMetadata("SuperCow"))
            {
                
                if (cowList.size() < mobStackAmount)
                {
                    
                    cowList.add(entityNearCow);
                    
                }
                
                if (cowList.size() >= mobStackAmount)
                {
                    
                    CowHandler cowHandler = new CowHandler(plugin);
                    cowHandler.cowConstructor(cowList, mobStackAmount, plugin);
                    
                    return true;
                    
                }
                
            }
            
        }
        
        return false;
        
    }
    
    
    public boolean scanNearbyMushroomCow (MushroomCow mushroomCow){
        
        List mushroomCowList = new ArrayList<MushroomCow>();
        mushroomCowList.add(mushroomCow);
        
        for (Entity entityNearMushroomCow : mushroomCow.getNearbyEntities(range, range, range))
        {
            
            if (entityNearMushroomCow instanceof MushroomCow && !entityNearMushroomCow.hasMetadata("SuperMushroomCow"))
            {
                
                if (mushroomCowList.size() < mobStackAmount)
                {

                    mushroomCowList.add(entityNearMushroomCow);

                }

                if (mushroomCowList.size() >= mobStackAmount)
                {

                    MushroomCowHandler mushroomCowHandler = new MushroomCowHandler(plugin);
                    mushroomCowHandler.mushroomCowConstructor(mushroomCowList, mobStackAmount, plugin);

                    return true;

                }
                
            }
            
        }
        
        return false;
        
    }
    
    
    public boolean scanNearbyPig(Pig pig){
        
        List pigList = new ArrayList<Pig>();
        pigList.add(pig);
        
        for (Entity entityNearPig : pig.getNearbyEntities(range, range, range))
        {
            
            if (entityNearPig instanceof  Pig && !entityNearPig.hasMetadata("SuperPig"))
            {
               
               if (pigList.size() < mobStackAmount)
               {
                   
                   pigList.add(entityNearPig);
                   
               }
               
               if (pigList.size() >= mobStackAmount)
               {
                   
                   PigHandler pigHandler = new PigHandler(plugin);
                   pigHandler.pigConstructor(pigList, mobStackAmount, plugin);
                   
                   return true;
                   
               }
                
            }
            
        }
        
        return false;
        
    }
    
    
    public boolean scanNearbySheep(Sheep sheep){
        
        List sheepList = new ArrayList<Sheep>();
        sheepList.add(sheep);
        
        for (Entity entityNearSheep : sheep.getNearbyEntities(range, range, range))
        {
            
            if (entityNearSheep instanceof Sheep && !entityNearSheep.hasMetadata("SuperSheep"))
            {
                
                Sheep entityNearSheepIsSheep = (Sheep) entityNearSheep;
                
                if (sheep.getColor() == entityNearSheepIsSheep.getColor())
                {
                    
                    if (sheepList.size() < mobStackAmount)
                    {
                        
                        sheepList.add(entityNearSheep);
                        
                    }
                    
                    if (sheepList.size() >= mobStackAmount)
                    {
                        
                        SheepHandler sheepHandler = new SheepHandler(plugin);
                        sheepHandler.sheepConstructor(sheepList, mobStackAmount, plugin);
                        
                        return true;
                        
                    }
                    
                }
                
            }
            
        }
        
        return false;
        
    }
    
    
    public boolean scanNearbyIronGolem(IronGolem ironGolem){
        
        List ironGolemList = new ArrayList<IronGolem>();
        ironGolemList.add(ironGolem);
        
        for (Entity entityNearIronGolem : ironGolem.getNearbyEntities(range, range, range))
        {
            
            if (entityNearIronGolem instanceof IronGolem && !entityNearIronGolem.hasMetadata("SuperIronGolem"))
            {
                
                if (ironGolemList.size() < mobStackAmount)
                {
                    
                    ironGolemList.add(entityNearIronGolem);
                    
                }
                
                if (ironGolemList.size() >= mobStackAmount)
                {
                    
                    IronGolemHandler ironGolemHandler = new IronGolemHandler(plugin);
                    ironGolemHandler.ironGolemConstructor(ironGolemList, mobStackAmount, plugin);
                    
                    return true;
                    
                }
                
            }
            
        }
        
        return false;
        
    }
    
}
