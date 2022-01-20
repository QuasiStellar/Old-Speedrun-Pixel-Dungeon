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

package com.quasistellar.oldspeedrunpixeldungeon.items.weapon.enchantments;

import com.quasistellar.oldspeedrunpixeldungeon.actors.Char;
import com.quasistellar.oldspeedrunpixeldungeon.actors.buffs.Buff;
import com.quasistellar.oldspeedrunpixeldungeon.actors.buffs.Paralysis;
import com.quasistellar.oldspeedrunpixeldungeon.effects.Speck;
import com.quasistellar.oldspeedrunpixeldungeon.items.weapon.Weapon;
import com.quasistellar.oldspeedrunpixeldungeon.sprites.ItemSprite;
import com.quasistellar.oldspeedrunpixeldungeon.sprites.ItemSprite.Glowing;
import com.watabou.utils.Random;

public class Stunning extends Weapon.Enchantment {
	
	private static ItemSprite.Glowing YELLOW = new ItemSprite.Glowing( 0xCCAA44 );
	
	@Override
	public int proc( Weapon weapon, Char attacker, Char defender, int damage ) {
		// lvl 0 - 13%
		// lvl 1 - 22%
		// lvl 2 - 30%
		int level = Math.max( 0, weapon.level() );
		
		if (Random.Int( level + 8 ) >= 7) {
			
			Buff.prolong( defender, Paralysis.class, Random.Float( 1, 1.5f + level ) );
			defender.sprite.emitter().burst(Speck.factory(Speck.LIGHT), 12 );

		}

		return damage;
	}
	
	@Override
	public Glowing glowing() {
		return YELLOW;
	}
}
