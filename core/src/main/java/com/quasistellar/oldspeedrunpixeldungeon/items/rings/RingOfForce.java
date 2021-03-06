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

package com.quasistellar.oldspeedrunpixeldungeon.items.rings;

import com.quasistellar.oldspeedrunpixeldungeon.Dungeon;
import com.quasistellar.oldspeedrunpixeldungeon.actors.Char;
import com.quasistellar.oldspeedrunpixeldungeon.actors.hero.Hero;
import com.quasistellar.oldspeedrunpixeldungeon.messages.Messages;
import com.watabou.utils.Random;

public class RingOfForce extends Ring {

	@Override
	protected RingBuff buff( ) {
		return new Force();
	}
	
	public static int armedDamageBonus( Char ch ){
		return getBonus( ch, Force.class);
	}
	
	
	// *** Weapon-like properties ***

	private static float tier(int str){
		float tier = Math.max(1, (str - 8)/2f);
		//each str point after 18 is half as effective
		if (tier > 5){
			tier = 5 + (tier - 5) / 2f;
		}
		return tier;
	}

	public static int damageRoll( Hero hero ){
		if (hero.buff(Force.class) != null) {
			int level = getBonus(hero, Force.class);
			float tier = tier(hero.STR());
			return Random.NormalIntRange(min(level, tier), max(level, tier));
		} else {
			//attack without any ring of force influence
			return Random.NormalIntRange(1, Math.max(hero.STR()-8, 1));
		}
	}

	//same as equivalent tier weapon
	private static int min(int lvl, float tier){
		return Math.round(
				tier +  //base
				lvl     //level scaling
		);
	}

	//same as equivalent tier weapon
	private static int max(int lvl, float tier){
		return Math.round(
				5*(tier+1) +    //base
				lvl*(tier+1)    //level scaling
		);
	}

	@Override
	public String desc() {
		String desc = super.desc();
		float tier = tier(Dungeon.hero.STR());
		if (levelKnown) {
			desc += "\n\n" + Messages.get(this, "avg_dmg", min(level(), tier), max(level(), tier));
		} else {
			desc += "\n\n" + Messages.get(this, "typical_avg_dmg", min(1, tier), max(1, tier));
		}

		return desc;
	}

	public class Force extends RingBuff {
	}
}

