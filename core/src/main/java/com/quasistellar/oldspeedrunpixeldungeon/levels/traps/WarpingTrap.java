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

package com.quasistellar.oldspeedrunpixeldungeon.levels.traps;

import com.quasistellar.oldspeedrunpixeldungeon.Assets;
import com.quasistellar.oldspeedrunpixeldungeon.Dungeon;
import com.quasistellar.oldspeedrunpixeldungeon.actors.Actor;
import com.quasistellar.oldspeedrunpixeldungeon.actors.Char;
import com.quasistellar.oldspeedrunpixeldungeon.actors.hero.Hero;
import com.quasistellar.oldspeedrunpixeldungeon.actors.mobs.Mob;
import com.quasistellar.oldspeedrunpixeldungeon.effects.CellEmitter;
import com.quasistellar.oldspeedrunpixeldungeon.effects.Speck;
import com.quasistellar.oldspeedrunpixeldungeon.items.Heap;
import com.quasistellar.oldspeedrunpixeldungeon.items.Item;
import com.quasistellar.oldspeedrunpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.quasistellar.oldspeedrunpixeldungeon.messages.Messages;
import com.quasistellar.oldspeedrunpixeldungeon.scenes.GameScene;
import com.quasistellar.oldspeedrunpixeldungeon.utils.BArray;
import com.quasistellar.oldspeedrunpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;

public class WarpingTrap extends Trap {

	{
		color = TEAL;
		shape = STARS;
	}

	@Override
	public void activate() {
		CellEmitter.get(pos).start(Speck.factory(Speck.LIGHT), 0.2f, 3);
		Sample.INSTANCE.play( Assets.SND_TELEPORT );
		
		Char ch = Actor.findChar( pos);
		if (ch instanceof Hero){
			ScrollOfTeleportation.teleportHero( (Hero)ch);
			BArray.setFalse(Dungeon.level.visited);
			BArray.setFalse(Dungeon.level.mapped);
			GameScene.updateFog();
			Dungeon.observe();
			
		} else if (ch != null){
			int count = 10;
			int pos;
			do {
				pos = Dungeon.level.randomRespawnCell();
				if (count-- <= 0) {
					break;
				}
			} while (pos == -1);
			
			if (pos == -1 || Dungeon.bossLevel()) {
				
				GLog.w( Messages.get(ScrollOfTeleportation.class, "no_tele") );
				
			} else {
				
				ch.pos = pos;
				if (ch instanceof Mob && ((Mob) ch).state == ((Mob) ch).HUNTING){
					((Mob) ch).state = ((Mob) ch).WANDERING;
				}
				ch.sprite.place(ch.pos);
				ch.sprite.visible = Dungeon.level.heroFOV[pos];
				
			}
		}
		
		Heap heap = Dungeon.level.heaps.get(pos);
		
		if (heap != null){
			int cell = Dungeon.level.randomRespawnCell();
			
			Item item = heap.pickUp();
			
			if (cell != -1) {
				Dungeon.level.drop( item, cell );
			}
		}

	}
}
