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

package com.quasistellar.oldspeedrunpixeldungeon.actors.blobs;

import com.quasistellar.oldspeedrunpixeldungeon.Dungeon;
import com.quasistellar.oldspeedrunpixeldungeon.actors.Actor;
import com.quasistellar.oldspeedrunpixeldungeon.actors.Char;
import com.quasistellar.oldspeedrunpixeldungeon.actors.buffs.Buff;
import com.quasistellar.oldspeedrunpixeldungeon.actors.buffs.Roots;
import com.quasistellar.oldspeedrunpixeldungeon.effects.BlobEmitter;
import com.quasistellar.oldspeedrunpixeldungeon.effects.particles.WebParticle;
import com.quasistellar.oldspeedrunpixeldungeon.messages.Messages;

public class Web extends Blob {
	
	@Override
	protected void evolve() {

		int cell;

		for (int i = area.left; i < area.right; i++){
			for (int j = area.top; j < area.bottom; j++){
				cell = i + j*Dungeon.level.width();
				off[cell] = cur[cell] > 0 ? cur[cell] - 1 : 0;

				if (off[cell] > 0) {

					volume += off[cell];

					Char ch = Actor.findChar( cell );
					if (ch != null && !ch.immunities().contains(this.getClass())) {
						Buff.prolong( ch, Roots.class, TICK );
					}
				}
			}
		}
	}
	
	@Override
	public void use( BlobEmitter emitter ) {
		super.use( emitter );
		
		emitter.pour( WebParticle.FACTORY, 0.4f );
	}
	
	@Override
	public String tileDesc() {
		return Messages.get(this, "desc");
	}
}
