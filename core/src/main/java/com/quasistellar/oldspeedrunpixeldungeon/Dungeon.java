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

package com.quasistellar.oldspeedrunpixeldungeon;

import com.quasistellar.oldspeedrunpixeldungeon.actors.Actor;
import com.quasistellar.oldspeedrunpixeldungeon.actors.Char;
import com.quasistellar.oldspeedrunpixeldungeon.actors.buffs.Amok;
import com.quasistellar.oldspeedrunpixeldungeon.actors.buffs.Awareness;
import com.quasistellar.oldspeedrunpixeldungeon.actors.buffs.Light;
import com.quasistellar.oldspeedrunpixeldungeon.actors.buffs.MindVision;
import com.quasistellar.oldspeedrunpixeldungeon.actors.hero.Hero;
import com.quasistellar.oldspeedrunpixeldungeon.actors.hero.HeroClass;
import com.quasistellar.oldspeedrunpixeldungeon.actors.mobs.npcs.Blacksmith;
import com.quasistellar.oldspeedrunpixeldungeon.actors.mobs.npcs.Ghost;
import com.quasistellar.oldspeedrunpixeldungeon.actors.mobs.npcs.Imp;
import com.quasistellar.oldspeedrunpixeldungeon.actors.mobs.npcs.Wandmaker;
import com.quasistellar.oldspeedrunpixeldungeon.items.Ankh;
import com.quasistellar.oldspeedrunpixeldungeon.items.Generator;
import com.quasistellar.oldspeedrunpixeldungeon.items.Item;
import com.quasistellar.oldspeedrunpixeldungeon.items.artifacts.DriedRose;
import com.quasistellar.oldspeedrunpixeldungeon.items.potions.Potion;
import com.quasistellar.oldspeedrunpixeldungeon.items.rings.Ring;
import com.quasistellar.oldspeedrunpixeldungeon.items.scrolls.Scroll;
import com.quasistellar.oldspeedrunpixeldungeon.journal.Notes;
import com.quasistellar.oldspeedrunpixeldungeon.levels.CavesBossLevel;
import com.quasistellar.oldspeedrunpixeldungeon.levels.CavesLevel;
import com.quasistellar.oldspeedrunpixeldungeon.levels.CityBossLevel;
import com.quasistellar.oldspeedrunpixeldungeon.levels.CityLevel;
import com.quasistellar.oldspeedrunpixeldungeon.levels.DeadEndLevel;
import com.quasistellar.oldspeedrunpixeldungeon.levels.HallsBossLevel;
import com.quasistellar.oldspeedrunpixeldungeon.levels.HallsLevel;
import com.quasistellar.oldspeedrunpixeldungeon.levels.LastLevel;
import com.quasistellar.oldspeedrunpixeldungeon.levels.LastShopLevel;
import com.quasistellar.oldspeedrunpixeldungeon.levels.Level;
import com.quasistellar.oldspeedrunpixeldungeon.levels.PrisonBossLevel;
import com.quasistellar.oldspeedrunpixeldungeon.levels.PrisonLevel;
import com.quasistellar.oldspeedrunpixeldungeon.levels.SewerBossLevel;
import com.quasistellar.oldspeedrunpixeldungeon.levels.SewerLevel;
import com.quasistellar.oldspeedrunpixeldungeon.levels.rooms.secret.SecretRoom;
import com.quasistellar.oldspeedrunpixeldungeon.levels.rooms.special.SpecialRoom;
import com.quasistellar.oldspeedrunpixeldungeon.messages.Messages;
import com.quasistellar.oldspeedrunpixeldungeon.scenes.GameScene;
import com.quasistellar.oldspeedrunpixeldungeon.scenes.StartScene;
import com.quasistellar.oldspeedrunpixeldungeon.ui.QuickSlotButton;
import com.quasistellar.oldspeedrunpixeldungeon.utils.BArray;
import com.quasistellar.oldspeedrunpixeldungeon.utils.DungeonSeed;
import com.quasistellar.oldspeedrunpixeldungeon.windows.WndResurrect;
import com.watabou.noosa.Game;
import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;
import com.watabou.utils.SparseArray;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;

public class Dungeon {

	//enum of items which have limited spawns, records how many have spawned
	//could all be their own separate numbers, but this allows iterating, much nicer for bundling/initializing.
	public static enum LimitedDrops {
		//limited world drops
		STRENGTH_POTIONS,
		UPGRADE_SCROLLS,
		ARCANE_STYLI,

		//Health potion sources
		//enemies
		SWARM_HP,
		GUARD_HP,
		BAT_HP,
		WARLOCK_HP,
		SCORPIO_HP,
		//alchemy
		COOKING_HP,
		BLANDFRUIT_SEED,

		//doesn't use Generator, so we have to enforce one armband drop here
		THIEVES_ARMBAND,

		//containers
		DEW_VIAL,
		SEED_POUCH,
		SCROLL_HOLDER,
		POTION_BANDOLIER,
		WAND_HOLSTER;

		public int count = 0;

		//for items which can only be dropped once, should directly access count otherwise.
		public boolean dropped(){
			return count != 0;
		}
		public void drop(){
			count = 1;
		}

		public static void reset(){
			for (LimitedDrops lim : values()){
				lim.count = 0;
			}
		}

		public static void store( Bundle bundle ){
			for (LimitedDrops lim : values()){
				bundle.put(lim.name(), lim.count);
			}
		}

		public static void restore( Bundle bundle ){
			for (LimitedDrops lim : values()){
				if (bundle.contains(lim.name())){
					lim.count = bundle.getInt(lim.name());
				} else {
					lim.count = 0;
				}
			}
		}

		//for saves prior to 0.6.1
		public static void legacyRestore( int[] counts ){
			STRENGTH_POTIONS.count =    counts[0];
			UPGRADE_SCROLLS.count =     counts[1];
			ARCANE_STYLI.count =        counts[2];
			SWARM_HP.count =            counts[3];
			BAT_HP.count =              counts[4];
			WARLOCK_HP.count =          counts[5];
			SCORPIO_HP.count =          counts[6];
			COOKING_HP.count =          counts[7];
			BLANDFRUIT_SEED.count =     counts[8];
			THIEVES_ARMBAND.count =     counts[9];
			DEW_VIAL.count =            counts[10];
			SEED_POUCH.count =          counts[11];
			SCROLL_HOLDER.count =       counts[12];
			POTION_BANDOLIER.count =    counts[13];
			WAND_HOLSTER.count =        counts[14];
			GUARD_HP.count =            counts[15];
		}

	}

	public static int challenges;

	public static Hero hero;
	public static Level level;

	public static QuickSlot quickslot = new QuickSlot();
	
	public static int depth;
	public static int gold;
	
	public static HashSet<Integer> chapters;

	public static SparseArray<ArrayList<Item>> droppedItems;

	public static int version;

	public static long seed;
	
	public static void init() {

		version = Game.versionCode;
		challenges = OldSpeedrunPixelDungeon.challenges();

		seed = DungeonSeed.randomSeed();

		Actor.clear();
		Actor.resetNextID();
		
		Random.seed( seed );

			Scroll.initLabels();
			Potion.initColors();
			Ring.initGems();

			SpecialRoom.initForRun();
			SecretRoom.initForRun();

		Random.seed();
		
		Statistics.reset();
		Notes.reset();

		quickslot.reset();
		QuickSlotButton.reset();
		
		depth = 0;
		gold = 0;

		droppedItems = new SparseArray<ArrayList<Item>>();

		for (LimitedDrops a : LimitedDrops.values())
			a.count = 0;
		
		chapters = new HashSet<Integer>();
		
		Ghost.Quest.reset();
		Wandmaker.Quest.reset();
		Blacksmith.Quest.reset();
		Imp.Quest.reset();

		Generator.reset();
		Generator.initArtifacts();
		hero = new Hero();
		hero.live();
		
		Badges.reset();
		
		StartScene.curClass.initHero( hero );
	}

	public static boolean isChallenged( int mask ) {
		return (challenges & mask) != 0;
	}
	
	public static Level newLevel() {
		
		Dungeon.level = null;
		Actor.clear();
		
		depth++;
		if (depth > Statistics.deepestFloor) {
			Statistics.deepestFloor = depth;
			
			if (Statistics.qualifiedForNoKilling) {
				Statistics.completedWithNoKilling = true;
			} else {
				Statistics.completedWithNoKilling = false;
			}
		}
		
		Level level;
		switch (depth) {
		case 1:
		case 2:
		case 3:
		case 4:
			level = new SewerLevel();
			break;
		case 5:
			level = new SewerBossLevel();
			break;
		case 6:
		case 7:
		case 8:
		case 9:
			level = new PrisonLevel();
			break;
		case 10:
			level = new PrisonBossLevel();
			break;
		case 11:
		case 12:
		case 13:
		case 14:
			level = new CavesLevel();
			break;
		case 15:
			level = new CavesBossLevel();
			break;
		case 16:
		case 17:
		case 18:
		case 19:
			level = new CityLevel();
			break;
		case 20:
			level = new CityBossLevel();
			break;
		case 21:
			level = new LastShopLevel();
			break;
		case 22:
		case 23:
		case 24:
			level = new HallsLevel();
			break;
		case 25:
			level = new HallsBossLevel();
			break;
		case 26:
			level = new LastLevel();
			break;
		default:
			level = new DeadEndLevel();
			Statistics.deepestFloor--;
		}
		
		level.create();
		
		Statistics.qualifiedForNoKilling = !bossLevel();
		
		return level;
	}
	
	public static void resetLevel() {
		
		Actor.clear();
		
		level.reset();
		switchLevel( level, level.entrance );
	}

	public static long seedCurDepth(){
		return seedForDepth(depth);
	}

	public static long seedForDepth(int depth){
		Random.seed( seed );
		for (int i = 0; i < depth; i ++)
			Random.Long(); //we don't care about these values, just need to go through them
		long result = Random.Long();
		Random.seed();
		return result;
	}
	
	public static boolean shopOnLevel() {
		return depth == 6 || depth == 11 || depth == 16;
	}
	
	public static boolean bossLevel() {
		return bossLevel( depth );
	}
	
	public static boolean bossLevel( int depth ) {
		return depth == 5 || depth == 10 || depth == 15 || depth == 20 || depth == 25;
	}
	
	@SuppressWarnings("deprecation")
	public static void switchLevel( final Level level, int pos ) {
		
		if (pos < 0 || pos >= level.length()){
			pos = level.exit;
		}
		
		PathFinder.setMapSize(level.width(), level.height());
		
		Dungeon.level = level;
		DriedRose.restoreGhostHero( level, pos );
		Actor.init();
		
		Actor respawner = level.respawner();
		if (respawner != null) {
			Actor.add( level.respawner() );
		}

		hero.pos = pos;
		
		Light light = hero.buff( Light.class );
		hero.viewDistance = light == null ? level.viewDistance : Math.max( Light.DISTANCE, level.viewDistance );
		
		hero.curAction = hero.lastAction = null;
		
		observe();
		try {
			saveAll();
		} catch (IOException e) {
			OldSpeedrunPixelDungeon.reportException(e);
			/*This only catches IO errors. Yes, this means things can go wrong, and they can go wrong catastrophically.
			But when they do the user will get a nice 'report this issue' dialogue, and I can fix the bug.*/
		}
	}

	public static void dropToChasm( Item item ) {
		int depth = Dungeon.depth + 1;
		ArrayList<Item> dropped = (ArrayList<Item>)Dungeon.droppedItems.get( depth );
		if (dropped == null) {
			Dungeon.droppedItems.put( depth, dropped = new ArrayList<Item>() );
		}
		dropped.add( item );
	}

	public static boolean posNeeded() {
		//2 POS each floor set
		int posLeftThisSet = 2 - (LimitedDrops.STRENGTH_POTIONS.count - (depth / 5) * 2);
		if (posLeftThisSet <= 0) return false;

		int floorThisSet = (depth % 5);

		//pos drops every two floors, (numbers 1-2, and 3-4) with a 50% chance for the earlier one each time.
		int targetPOSLeft = 2 - floorThisSet/2;
		if (floorThisSet % 2 == 1 && Random.Int(2) == 0) targetPOSLeft --;

		if (targetPOSLeft < posLeftThisSet) return true;
		else return false;

	}
	
	public static boolean souNeeded() {
		//3 SOU each floor set
		int souLeftThisSet = 3 - (LimitedDrops.UPGRADE_SCROLLS.count - (depth / 5) * 3);
		if (souLeftThisSet <= 0) return false;

		int floorThisSet = (depth % 5);
		//chance is floors left / scrolls left
		return Random.Int(5 - floorThisSet) < souLeftThisSet;
	}
	
	public static boolean asNeeded() {
		//1 AS each floor set
		int asLeftThisSet = 1 - (LimitedDrops.ARCANE_STYLI.count - (depth / 5));
		if (asLeftThisSet <= 0) return false;

		int floorThisSet = (depth % 5);
		//chance is floors left / scrolls left
		return Random.Int(5 - floorThisSet) < asLeftThisSet;
	}
	
	private static final String RG_GAME_FILE	= "game.dat";
	private static final String RG_DEPTH_FILE	= "depth%d.dat";
	
	private static final String WR_GAME_FILE	= "warrior.dat";
	private static final String WR_DEPTH_FILE	= "warrior%d.dat";
	
	private static final String MG_GAME_FILE	= "mage.dat";
	private static final String MG_DEPTH_FILE	= "mage%d.dat";
	
	private static final String RN_GAME_FILE	= "ranger.dat";
	private static final String RN_DEPTH_FILE	= "ranger%d.dat";
	
	private static final String VERSION		= "version";
	private static final String SEED		= "seed";
	private static final String CHALLENGES	= "challenges";
	private static final String HERO		= "hero";
	private static final String GOLD		= "gold";
	private static final String DEPTH		= "depth";
	private static final String DROPPED     = "dropped%d";
	private static final String LEVEL		= "level";
	private static final String LIMDROPS    = "limited_drops";
	private static final String CHAPTERS	= "chapters";
	private static final String QUESTS		= "quests";
	private static final String BADGES		= "badges";
	
	public static String gameFile( HeroClass cl ) {
		switch (cl) {
		case WARRIOR:
			return WR_GAME_FILE;
		case MAGE:
			return MG_GAME_FILE;
		case HUNTRESS:
			return RN_GAME_FILE;
		default:
			return RG_GAME_FILE;
		}
	}
	
	private static String depthFile( HeroClass cl ) {
		switch (cl) {
		case WARRIOR:
			return WR_DEPTH_FILE;
		case MAGE:
			return MG_DEPTH_FILE;
		case HUNTRESS:
			return RN_DEPTH_FILE;
		default:
			return RG_DEPTH_FILE;
		}
	}
	
	public static void saveGame( String fileName ) throws IOException {
		try {
			Bundle bundle = new Bundle();

			version = Game.versionCode;
			bundle.put( VERSION, version );
			bundle.put( SEED, seed );
			bundle.put( CHALLENGES, challenges );
			bundle.put( HERO, hero );
			bundle.put( GOLD, gold );
			bundle.put( DEPTH, depth );

			for (int d : droppedItems.keyArray()) {
				bundle.put(Messages.format(DROPPED, d), droppedItems.get(d));
			}

			quickslot.storePlaceholders( bundle );

			Bundle limDrops = new Bundle();
			LimitedDrops.store( limDrops );
			bundle.put ( LIMDROPS, limDrops );
			
			int count = 0;
			int ids[] = new int[chapters.size()];
			for (Integer id : chapters) {
				ids[count++] = id;
			}
			bundle.put( CHAPTERS, ids );
			
			Bundle quests = new Bundle();
			Ghost		.Quest.storeInBundle( quests );
			Wandmaker	.Quest.storeInBundle( quests );
			Blacksmith	.Quest.storeInBundle( quests );
			Imp			.Quest.storeInBundle( quests );
			bundle.put( QUESTS, quests );
			
			SpecialRoom.storeRoomsInBundle( bundle );
			SecretRoom.storeRoomsInBundle( bundle );
			
			Statistics.storeInBundle( bundle );
			Notes.storeInBundle( bundle );
			Generator.storeInBundle( bundle );
			
			Scroll.save( bundle );
			Potion.save( bundle );
			Ring.save( bundle );

			Actor.storeNextID( bundle );
			
			Bundle badges = new Bundle();
			Badges.saveLocal( badges );
			bundle.put( BADGES, badges );
			
			OutputStream output = Game.instance.openFileOutput( fileName, Game.MODE_PRIVATE );
			Bundle.write( bundle, output );
			output.close();
			
		} catch (IOException e) {
			GamesInProgress.setUnknown( hero.heroClass );
			OldSpeedrunPixelDungeon.reportException(e);
		}
	}
	
	public static void saveLevel() throws IOException {
		Bundle bundle = new Bundle();
		bundle.put( LEVEL, level );
		
		OutputStream output = Game.instance.openFileOutput(
			Messages.format( depthFile( hero.heroClass ), depth ), Game.MODE_PRIVATE );
		Bundle.write( bundle, output );
		output.close();
	}
	
	public static void saveAll() throws IOException {
		if (hero != null && hero.isAlive()) {
			
			Actor.fixTime();
			saveGame( gameFile( hero.heroClass ) );
			saveLevel();

			GamesInProgress.set( hero.heroClass, depth, hero.lvl, challenges != 0 );

		} else if (WndResurrect.instance != null) {
			
			WndResurrect.instance.hide();
			Hero.reallyDie( WndResurrect.causeOfDeath );
			
		}
	}
	
	public static void loadGame( HeroClass cl ) throws IOException {
		loadGame( gameFile( cl ), true );
	}

	public static void loadGame( String fileName ) throws IOException {
		loadGame( fileName, false );
	}
	
	public static void loadGame( String fileName, boolean fullLoad ) throws IOException {
		
		Bundle bundle = gameBundle( fileName );

		version = bundle.getInt( VERSION );

		seed = bundle.contains( SEED ) ? bundle.getLong( SEED ) : DungeonSeed.randomSeed();

		Actor.restoreNextID( bundle );

		quickslot.reset();
		QuickSlotButton.reset();

		Dungeon.challenges = bundle.getInt( CHALLENGES );
		
		Dungeon.level = null;
		Dungeon.depth = -1;
		
		Scroll.restore( bundle );
		Potion.restore( bundle );
		Ring.restore( bundle );

		quickslot.restorePlaceholders( bundle );
		
		if (fullLoad) {

			//pre-0.6.1
			if( bundle.contains("limiteddrops") ){
				LimitedDrops.legacyRestore( bundle.getIntArray("limiteddrops") );
			} else {
				LimitedDrops.restore( bundle.getBundle(LIMDROPS) );
			}

			chapters = new HashSet<Integer>();
			int ids[] = bundle.getIntArray( CHAPTERS );
			if (ids != null) {
				for (int id : ids) {
					chapters.add( id );
				}
			}
			
			Bundle quests = bundle.getBundle( QUESTS );
			if (!quests.isNull()) {
				Ghost.Quest.restoreFromBundle( quests );
				Wandmaker.Quest.restoreFromBundle( quests );
				Blacksmith.Quest.restoreFromBundle( quests );
				Imp.Quest.restoreFromBundle( quests );
			} else {
				Ghost.Quest.reset();
				Wandmaker.Quest.reset();
				Blacksmith.Quest.reset();
				Imp.Quest.reset();
			}
			
			SpecialRoom.restoreRoomsFromBundle(bundle);
			SecretRoom.restoreRoomsFromBundle(bundle);
		}
		
		Bundle badges = bundle.getBundle(BADGES);
		if (!badges.isNull()) {
			Badges.loadLocal( badges );
		} else {
			Badges.reset();
		}
		
		Notes.restoreFromBundle( bundle );
		
		hero = null;
		hero = (Hero)bundle.get( HERO );
		
		gold = bundle.getInt( GOLD );
		depth = bundle.getInt( DEPTH );
		
		Statistics.restoreFromBundle( bundle );
		Generator.restoreFromBundle( bundle );

		droppedItems = new SparseArray<>();
		for (int i=2; i <= Statistics.deepestFloor + 1; i++) {
			ArrayList<Item> dropped = new ArrayList<Item>();
			if (bundle.contains(Messages.format( DROPPED, i )))
				for (Bundlable b : bundle.getCollection( Messages.format( DROPPED, i ) ) ) {
					dropped.add( (Item)b );
				}
			if (!dropped.isEmpty()) {
				droppedItems.put( i, dropped );
			}
		}
	}
	
	public static Level loadLevel( HeroClass cl ) throws IOException {
		
		Dungeon.level = null;
		Actor.clear();
		
		InputStream input = Game.instance.openFileInput( Messages.format( depthFile( cl ), depth ) ) ;
		Bundle bundle = Bundle.read( input );
		input.close();
		
		Level level = (Level)bundle.get( LEVEL );
		
		if (level == null){
			throw new IOException();
		} else {
			return level;
		}
	}
	
	public static void deleteGame( HeroClass cl, boolean deleteLevels ) {
		
		Game.instance.deleteFile( gameFile( cl ) );
		
		if (deleteLevels) {
			int depth = 1;
			while (Game.instance.deleteFile( Messages.format( depthFile( cl ), depth ) )) {
				depth++;
			}
		}
		
		GamesInProgress.delete( cl );
	}
	
	public static Bundle gameBundle( String fileName ) throws IOException {
		
		InputStream input = Game.instance.openFileInput( fileName );
		Bundle bundle = Bundle.read( input );
		input.close();
		
		return bundle;
	}
	
	public static void preview( GamesInProgress.Info info, Bundle bundle ) {
		info.depth = bundle.getInt( DEPTH );
		info.version = bundle.getInt( VERSION );
		info.challenges = (bundle.getInt( CHALLENGES ) != 0);
		Hero.preview( info, bundle.getBundle( HERO ) );
	}
	
	public static void fail( Class cause ) {
		if (hero.belongings.getItem( Ankh.class ) == null) {
			Rankings.INSTANCE.submit( false, cause );
		}
	}
	
	public static void win( Class cause ) {

		hero.belongings.identify();

		if (challenges != 0) {
			Badges.validateChampion();
		}

		Rankings.INSTANCE.submit( true, cause );
	}

	public static void observe(){
		observe( hero.viewDistance+1 );
	}
	
	public static void observe( int dist ) {

		if (level == null) {
			return;
		}
		
		level.updateFieldOfView(hero, level.heroFOV);

		if (hero.buff(MindVision.class) != null || hero.buff(Awareness.class) != null) {
			BArray.or( level.visited, level.heroFOV, 0, level.heroFOV.length, level.visited );

			GameScene.updateFog();
		} else {

			int cx = hero.pos % level.width();
			int cy = hero.pos / level.width();

			int ax = Math.max( 0, cx - dist );
			int bx = Math.min( cx + dist, level.width() - 1 );
			int ay = Math.max( 0, cy - dist );
			int by = Math.min( cy + dist, level.height() - 1 );

			int len = bx - ax + 1;
			int pos = ax + ay * level.width();

			for (int y = ay; y <= by; y++, pos+=level.width()) {
				BArray.or( level.visited, level.heroFOV, pos, len, level.visited );
			}

			GameScene.updateFog(ax, ay, len, by-ay);
		}


		GameScene.afterObserve();
	}

	//we store this to avoid having to re-allocate the array with each pathfind
	private static boolean[] passable;

	private static void setupPassable(){
		if (passable == null || passable.length != Dungeon.level.length())
			passable = new boolean[Dungeon.level.length()];
		else
			BArray.setFalse(passable);
	}

	public static PathFinder.Path findPath(Char ch, int from, int to, boolean pass[], boolean[] visible ) {

		setupPassable();
		if (ch.flying || ch.buff( Amok.class ) != null) {
			BArray.or( pass, Dungeon.level.avoid, passable );
		} else {
			System.arraycopy( pass, 0, passable, 0, Dungeon.level.length() );
		}

		for (Char c : Actor.chars()) {
			if (visible[c.pos]) {
				passable[c.pos] = false;
			}
		}

		return PathFinder.find( from, to, passable );

	}
	
	public static int findStep(Char ch, int from, int to, boolean pass[], boolean[] visible ) {

		if (Dungeon.level.adjacent( from, to )) {
			return Actor.findChar( to ) == null && (pass[to] || Dungeon.level.avoid[to]) ? to : -1;
		}

		setupPassable();
		if (ch.flying || ch.buff( Amok.class ) != null) {
			BArray.or( pass, Dungeon.level.avoid, passable );
		} else {
			System.arraycopy( pass, 0, passable, 0, Dungeon.level.length() );
		}
		
		for (Char c : Actor.chars()) {
			if (visible[c.pos]) {
				passable[c.pos] = false;
			}
		}
		
		return PathFinder.getStep( from, to, passable );

	}
	
	public static int flee( Char ch, int cur, int from, boolean pass[], boolean[] visible ) {

		setupPassable();
		if (ch.flying) {
			BArray.or( pass, Dungeon.level.avoid, passable );
		} else {
			System.arraycopy( pass, 0, passable, 0, Dungeon.level.length() );
		}
		
		for (Char c : Actor.chars()) {
			if (visible[c.pos]) {
				passable[c.pos] = false;
			}
		}
		passable[cur] = true;
		
		return PathFinder.getStepBack( cur, from, passable );
		
	}

}
