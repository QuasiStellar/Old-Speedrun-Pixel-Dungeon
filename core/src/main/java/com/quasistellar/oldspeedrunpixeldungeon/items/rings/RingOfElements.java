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

import com.quasistellar.oldspeedrunpixeldungeon.actors.Char;
import com.quasistellar.oldspeedrunpixeldungeon.actors.blobs.ToxicGas;
import com.quasistellar.oldspeedrunpixeldungeon.actors.buffs.Burning;
import com.quasistellar.oldspeedrunpixeldungeon.actors.buffs.Poison;
import com.quasistellar.oldspeedrunpixeldungeon.actors.buffs.Venom;
import com.quasistellar.oldspeedrunpixeldungeon.actors.mobs.Eye;
import com.quasistellar.oldspeedrunpixeldungeon.actors.mobs.Shaman;
import com.quasistellar.oldspeedrunpixeldungeon.actors.mobs.Warlock;
import com.quasistellar.oldspeedrunpixeldungeon.actors.mobs.Yog;
import com.watabou.utils.Random;

import java.util.HashSet;

public class RingOfElements extends Ring {
	
	@Override
	protected RingBuff buff( ) {
		return new Resistance();
	}

	private static final HashSet<Class> EMPTY = new HashSet<>();
	public static final HashSet<Class> FULL = new HashSet<>();
	static {
		FULL.add( Burning.class );
		FULL.add( ToxicGas.class );
		FULL.add( Poison.class );
		FULL.add( Venom.class );
		FULL.add( Shaman.class );
		FULL.add( Warlock.class );
		FULL.add( Eye.class );
		FULL.add( Yog.BurningFist.class );
	}
	
	public static HashSet<Class> resistances( Char target ){
		if (Random.Int( getBonus(target, Resistance.class) + 2 ) >= 2) {
			return FULL;
		} else {
			return EMPTY;
		}
	}
	
	public static float durationFactor( Char target ){
		int level = getBonus( target, Resistance.class);
		return level <= 0 ? 1 : (1 + 0.5f * level) / (1 + level);
	}
	
	public class Resistance extends RingBuff {
		
		public float durationFactor() {
			return level() < 0 ? 1 : (1 + 0.5f * level()) / (1 + level());
		}
	}
}
