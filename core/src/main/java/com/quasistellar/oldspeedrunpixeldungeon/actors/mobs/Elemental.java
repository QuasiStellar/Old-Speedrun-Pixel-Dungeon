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

package com.quasistellar.oldspeedrunpixeldungeon.actors.mobs;

import com.quasistellar.oldspeedrunpixeldungeon.Dungeon;
import com.quasistellar.oldspeedrunpixeldungeon.actors.Char;
import com.quasistellar.oldspeedrunpixeldungeon.actors.buffs.Buff;
import com.quasistellar.oldspeedrunpixeldungeon.actors.buffs.Burning;
import com.quasistellar.oldspeedrunpixeldungeon.actors.buffs.Chill;
import com.quasistellar.oldspeedrunpixeldungeon.actors.buffs.Frost;
import com.quasistellar.oldspeedrunpixeldungeon.effects.Speck;
import com.quasistellar.oldspeedrunpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.quasistellar.oldspeedrunpixeldungeon.items.wands.WandOfFireblast;
import com.quasistellar.oldspeedrunpixeldungeon.items.weapon.enchantments.Blazing;
import com.quasistellar.oldspeedrunpixeldungeon.sprites.ElementalSprite;
import com.watabou.utils.Random;

public class Elemental extends Mob {

	{
		spriteClass = ElementalSprite.class;
		
		HP = HT = 65;
		defenseSkill = 20;
		
		EXP = 10;
		maxLvl = 20;
		
		flying = true;
		
		loot = new PotionOfLiquidFlame();
		lootChance = 0.1f;

		properties.add(Property.DEMONIC);
	}
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange( 16, 26 );
	}
	
	@Override
	public int attackSkill( Char target ) {
		return 25;
	}
	
	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 5);
	}
	
	@Override
	public int attackProc( Char enemy, int damage ) {
		damage = super.attackProc( enemy, damage );
		if (Random.Int( 2 ) == 0) {
			Buff.affect( enemy, Burning.class ).reignite( enemy );
		}
		
		return damage;
	}
	
	@Override
	public void add( Buff buff ) {
		if (buff instanceof Burning) {
			if (HP < HT) {
				HP++;
				sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );
			}
		} else if (buff instanceof Frost || buff instanceof Chill) {
				if (Dungeon.level.water[this.pos])
					damage( Random.NormalIntRange( HT / 2, HT ), buff );
				else
					damage( Random.NormalIntRange( 1, HT * 2 / 3 ), buff );
		} else {
			super.add( buff );
		}
	}
	
	{
		immunities.add( Burning.class );
		immunities.add( Blazing.class );
		immunities.add( WandOfFireblast.class );
	}
}
