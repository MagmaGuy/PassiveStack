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

import com.magmaguy.passivestack.animals.SheepHandler;
import com.magmaguy.passivestack.animals.ChickenHandler;
import com.magmaguy.passivestack.animals.CowHandler;
import com.magmaguy.passivestack.animals.IronGolemHandler;
import com.magmaguy.passivestack.animals.MushroomCowHandler;
import com.magmaguy.passivestack.animals.PigHandler;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class PassiveStack extends JavaPlugin implements Listener{
    
    public static ArrayList<String> worldList = new ArrayList<>();
    public static ArrayList<Entity> superMobList = new ArrayList<>();
    public static List<Chicken> superChickenList = new ArrayList<>();
    public static List<Cow> superCowList = new ArrayList<>();
    public static List<MushroomCow> superMushroomCowList = new ArrayList();
    public static List<Pig> superPigList = new ArrayList<>();
    public static List<Sheep> superSheepList = new ArrayList();
    public static List<IronGolem> superIronGolemList = new ArrayList();

    
    @Override
    public void onEnable(){
        
        getLogger().info("Passive Stack - Enabled Test!");
        
        this.getServer().getPluginManager().registerEvents(this, this);
        this.getServer().getPluginManager().registerEvents(new ChickenHandler(this), this);
        this.getServer().getPluginManager().registerEvents(new CowHandler(this), this);
        this.getServer().getPluginManager().registerEvents(new MushroomCowHandler(this), this);
        this.getServer().getPluginManager().registerEvents(new PigHandler(this), this);
        this.getServer().getPluginManager().registerEvents(new SheepHandler(this), this);
        this.getServer().getPluginManager().registerEvents(new IronGolemHandler(this), this);

        this.getCommand("passivestack").setExecutor(new CommandHandler());

        for (Object object : Bukkit.getWorlds())
        {
            
            if (object instanceof World)
            {
                
                worldList.add(((World) object).getName());
                
            }
            
        }
        
        ChickenHandler chickenHandler = new ChickenHandler(this);
        AnimalChecker chickenCheck = new AnimalChecker(this);
        
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            
            public void run(){
                
                chickenHandler.superEggs();
                chickenCheck.checkAnimals();
                passiveMobDetector();
                
            }
            
            
        }, 1L, 20L);
        
        
    }
    
    @Override
    public void onDisable(){
        
        getLogger().info("Passive Stack - Disabled!");
        
    }


    public void passiveMobDetector(){

        for (Chicken chicken : superChickenList)
        {

            if(!superMobList.contains(chicken))
            {

                superMobList.add(chicken);

            }

        }

        for (Cow cow : superCowList)
        {

            if(!superMobList.contains(cow))
            {

                superMobList.add(cow);

            }

        }

        for (MushroomCow mushroomCow : superMushroomCowList)
        {

            if(!superMobList.contains(mushroomCow))
            {

                superMobList.add(mushroomCow);

            }

        }

        for (Pig pig : superPigList)
        {
            if(!superMobList.contains(pig))
            {

                superMobList.add(pig);

            }

        }

        for (Sheep sheep : superSheepList)
        {

            if(!superMobList.contains(sheep))
            {

                superMobList.add(sheep);

            }

        }

        for (IronGolem ironGolem : superIronGolemList)
        {

            if(!superMobList.contains(ironGolem))
            {

                superMobList.add(ironGolem);

            }

        }

        for (Entity entity : superMobList)
        {

            if(entity instanceof Chicken && !superChickenList.contains(entity))
            {

                superMobList.remove(entity);

            } else if (entity instanceof Cow && !superCowList.contains(entity)) {

                superMobList.remove(entity);

            } else if (entity instanceof MushroomCow && !superMushroomCowList.contains(entity)) {

                superMobList.remove(entity);

            } else if (entity instanceof Pig && !superPigList.contains(entity)) {

                superMobList.remove(entity);

            } else if (entity instanceof Sheep && !superSheepList.contains(entity)) {

                superMobList.remove(entity);

            } else if (entity instanceof IronGolem && !superIronGolemList.contains(entity)) {

                superMobList.remove(entity);

            }

        }

    }
    
}
