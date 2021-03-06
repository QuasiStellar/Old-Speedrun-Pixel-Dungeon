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
import com.quasistellar.oldspeedrunpixeldungeon.actors.mobs.npcs.Sheep;
import com.quasistellar.oldspeedrunpixeldungeon.effects.CellEmitter;
import com.quasistellar.oldspeedrunpixeldungeon.effects.Speck;
import com.quasistellar.oldspeedrunpixeldungeon.scenes.GameScene;
import com.quasistellar.oldspeedrunpixeldungeon.utils.BArray;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class FlockTrap extends Trap {

	{
		color = WHITE;
		shape = WAVES;
	}


	@Override
	public void activate() {
		//use an actor as we want to put this on a slight delay so all chars get a chance to act this turn first.
		Actor.add(new Actor() {

			{ actPriority = 3; }

			protected boolean act() {
				PathFinder.buildDistanceMap( pos, BArray.not( Dungeon.level.solid, null ), 2 );
				for (int i = 0; i < PathFinder.distance.length; i++) {
					if (PathFinder.distance[i] < Integer.MAX_VALUE) {
						if (Dungeon.level.insideMap(i)
								&& Actor.findChar(i) == null
								&& !(Dungeon.level.pit[i])) {
							Sheep sheep = new Sheep();
							sheep.lifespan = Random.NormalIntRange(3 + Dungeon.depth/4, 6 + Dungeon.depth/2 );
							sheep.pos = i;
							Dungeon.level.mobPress(sheep);
							GameScene.add(sheep);
							CellEmitter.get(i).burst(Speck.factory(Speck.WOOL), 4);
						}
					}
				}
				Sample.INSTANCE.play(Assets.SND_PUFF);
				Actor.remove(this);
				return true;
			}
		});

	}

}
