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

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;

import java.util.List;

import static com.magmaguy.passivestack.PassiveStack.*;

/**
 * Created by MagmaGuy on 02/10/2016.
 */
public class CommandHandler implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){

        if(sender instanceof Player)
        {

            Player player = (Player) sender;

            if (label.equals("passivestack"))
            {

                for (Object world : PassiveStack.worldList)
                {

                    List entityList = Bukkit.getWorld(world.toString()).getEntities();

                    for (Object object : entityList)
                    {

                        if (object instanceof Entity)
                        {

                            Entity entity = (Entity) object;

                            //Couldn't this be a switch statement?
                            if (entity.hasMetadata("SuperChicken"))
                            {

                                superChickenList.add((Chicken) entity);

                            } else if (entity.hasMetadata("SuperCow")) {

                                superCowList.add((Cow) entity);

                            } else if (entity.hasMetadata("SuperMushroomCow")) {

                                superMushroomCowList.add((MushroomCow) entity);

                            } else if (entity.hasMetadata("SuperPig")) {

                                superPigList.add((Pig) entity);

                            } else if (entity.hasMetadata("SuperSheep")) {

                                superSheepList.add((Sheep) entity);

                            } else if (entity.hasMetadata("SuperIronGolem")) {

                                superIronGolemList.add((IronGolem) entity);

                            }

                            superMobCount = superChickenList.size() + superCowList.size() + superMushroomCowList.size()
                                    + superPigList.size() + superSheepList.size() + superIronGolemList.size();

                        }

                    }

                }

                player.sendRawMessage("Total amount of Super Mobs: " + superMobCount);
                player.sendRawMessage("Total amount of Super Chickens: " + superChickenList.size());
                player.sendRawMessage("Total amount of Super Cows: " + superCowList.size());
                player.sendRawMessage("Total amount of Super Mooshrooms: " + superMushroomCowList.size());
                player.sendRawMessage("Total amount of Super Pigs: " + superPigList.size());
                player.sendRawMessage("Total amount of Super Sheep: " + superSheepList.size());
                player.sendRawMessage("Total amount of Super Iron Golems: " + superIronGolemList.size());

                superMobCount = 0;
                superChickenList.clear();
                superCowList.clear();
                superMushroomCowList.clear();
                superPigList.clear();
                superSheepList.clear();
                superIronGolemList.clear();

                return true;

            }

        }

        return false;

    }

}
