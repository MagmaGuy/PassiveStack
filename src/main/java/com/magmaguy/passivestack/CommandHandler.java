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

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
                player.sendRawMessage("Total amount of Super Mobs: " + superMobList.size());
                player.sendRawMessage("Total amount of Super Chickens: " + superChickenList.size());
                player.sendRawMessage("Total amount of Super Cows: " + superCowList.size());
                player.sendRawMessage("Total amount of Super Mooshrooms: " + superMushroomCowList.size());
                player.sendRawMessage("Total amount of Super Pigs: " + superPigList.size());
                player.sendRawMessage("Total amount of Super Sheep: " + superSheepList.size());
                player.sendRawMessage("Total amount of Super Iron Golems: " + superIronGolemList.size());
                return true;

            }

        }

        return false;

    }

}
