/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015  Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2017 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.quasistellar.oldspeedrunpixeldungeon.plants;

import com.quasistellar.oldspeedrunpixeldungeon.actors.Actor;
import com.quasistellar.oldspeedrunpixeldungeon.actors.Char;
import com.quasistellar.oldspeedrunpixeldungeon.actors.buffs.Bleeding;
import com.quasistellar.oldspeedrunpixeldungeon.actors.buffs.Buff;
import com.quasistellar.oldspeedrunpixeldungeon.actors.buffs.Cripple;
import com.quasistellar.oldspeedrunpixeldungeon.actors.buffs.Drowsy;
import com.quasistellar.oldspeedrunpixeldungeon.actors.buffs.MagicalSleep;
import com.quasistellar.oldspeedrunpixeldungeon.actors.buffs.Poison;
import com.quasistellar.oldspeedrunpixeldungeon.actors.buffs.Slow;
import com.quasistellar.oldspeedrunpixeldungeon.actors.buffs.Vertigo;
import com.quasistellar.oldspeedrunpixeldungeon.actors.buffs.Weakness;
import com.quasistellar.oldspeedrunpixeldungeon.actors.hero.Hero;
import com.quasistellar.oldspeedrunpixeldungeon.actors.mobs.Mob;
import com.quasistellar.oldspeedrunpixeldungeon.items.potions.PotionOfPurity;
import com.quasistellar.oldspeedrunpixeldungeon.messages.Messages;
import com.quasistellar.oldspeedrunpixeldungeon.sprites.ItemSpriteSheet;
import com.quasistellar.oldspeedrunpixeldungeon.utils.GLog;

public class Dreamfoil extends Plant {

	{
		image = 10;
	}

	@Override
	public void activate() {
		Char ch = Actor.findChar(pos);

		if (ch != null) {
			if (ch instanceof Mob)
				Buff.affect(ch, MagicalSleep.class);
			else if (ch instanceof Hero){
				GLog.i( Messages.get(this, "refreshed") );
				Buff.detach( ch, Poison.class );
				Buff.detach( ch, Cripple.class );
				Buff.detach( ch, Weakness.class );
				Buff.detach( ch, Bleeding.class );
				Buff.detach( ch, Drowsy.class );
				Buff.detach( ch, Slow.class );
				Buff.detach( ch, Vertigo.class);
			}
		}
	}

	public static class Seed extends Plant.Seed {
		{
			image = ItemSpriteSheet.SEED_DREAMFOIL;

			plantClass = Dreamfoil.class;
			alchemyClass = PotionOfPurity.class;
		}
	}
}