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

package com.quasistellar.oldspeedrunpixeldungeon.items.wands;

import com.quasistellar.oldspeedrunpixeldungeon.Assets;
import com.quasistellar.oldspeedrunpixeldungeon.Dungeon;
import com.quasistellar.oldspeedrunpixeldungeon.actors.Actor;
import com.quasistellar.oldspeedrunpixeldungeon.actors.Char;
import com.quasistellar.oldspeedrunpixeldungeon.actors.blobs.Blob;
import com.quasistellar.oldspeedrunpixeldungeon.actors.blobs.VenomGas;
import com.quasistellar.oldspeedrunpixeldungeon.effects.MagicMissile;
import com.quasistellar.oldspeedrunpixeldungeon.items.weapon.enchantments.Venomous;
import com.quasistellar.oldspeedrunpixeldungeon.items.weapon.melee.MagesStaff;
import com.quasistellar.oldspeedrunpixeldungeon.mechanics.Ballistica;
import com.quasistellar.oldspeedrunpixeldungeon.scenes.GameScene;
import com.quasistellar.oldspeedrunpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.ColorMath;
import com.watabou.utils.PathFinder;

public class WandOfVenom extends Wand {

	{
		image = ItemSpriteSheet.WAND_VENOM;

		collisionProperties = Ballistica.STOP_TARGET | Ballistica.STOP_TERRAIN;
	}

	@Override
	protected void onZap(Ballistica bolt) {
		Blob venomGas = Blob.seed(bolt.collisionPos, 50 + 10 * level(), VenomGas.class);
		((VenomGas)venomGas).setStrength(level()+1);
		GameScene.add(venomGas);

		for (int i : PathFinder.NEIGHBOURS9) {
			Char ch = Actor.findChar(bolt.collisionPos + i);
			if (ch != null) {
				processSoulMark(ch, chargesPerCast());
			}
		}
		
		if (Actor.findChar(bolt.collisionPos) == null){
			Dungeon.level.press(bolt.collisionPos, null);
		}
	}

	@Override
	protected void fx(Ballistica bolt, Callback callback) {
		MagicMissile.boltFromChar(
				curUser.sprite.parent,
				MagicMissile.POISON,
				curUser.sprite,
				bolt.collisionPos,
				callback);
		Sample.INSTANCE.play(Assets.SND_ZAP);
	}

	@Override
	public void onHit(MagesStaff staff, Char attacker, Char defender, int damage) {
		//acts like venomous enchantment
		new Venomous().proc(staff, attacker, defender, damage);
	}

	@Override
	public void staffFx(MagesStaff.StaffParticle particle) {
		particle.color( ColorMath.random( 0x8844FF, 0x00FF00) );
		particle.am = 0.6f;
		particle.setLifespan( 1f );
		particle.acc.set(0, 20);
		particle.setSize( 0.5f, 2f);
		particle.shuffleXY( 1f );
	}

}
