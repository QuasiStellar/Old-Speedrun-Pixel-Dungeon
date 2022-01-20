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

package com.quasistellar.oldspeedrunpixeldungeon.items;

import com.quasistellar.oldspeedrunpixeldungeon.Dungeon;
import com.quasistellar.oldspeedrunpixeldungeon.OldSpeedrunPixelDungeon;
import com.quasistellar.oldspeedrunpixeldungeon.items.armor.Armor;
import com.quasistellar.oldspeedrunpixeldungeon.items.armor.ClothArmor;
import com.quasistellar.oldspeedrunpixeldungeon.items.armor.LeatherArmor;
import com.quasistellar.oldspeedrunpixeldungeon.items.armor.MailArmor;
import com.quasistellar.oldspeedrunpixeldungeon.items.armor.PlateArmor;
import com.quasistellar.oldspeedrunpixeldungeon.items.armor.ScaleArmor;
import com.quasistellar.oldspeedrunpixeldungeon.items.artifacts.AlchemistsToolkit;
import com.quasistellar.oldspeedrunpixeldungeon.items.artifacts.Artifact;
import com.quasistellar.oldspeedrunpixeldungeon.items.artifacts.CapeOfThorns;
import com.quasistellar.oldspeedrunpixeldungeon.items.artifacts.ChaliceOfBlood;
import com.quasistellar.oldspeedrunpixeldungeon.items.artifacts.CloakOfShadows;
import com.quasistellar.oldspeedrunpixeldungeon.items.artifacts.DriedRose;
import com.quasistellar.oldspeedrunpixeldungeon.items.artifacts.EtherealChains;
import com.quasistellar.oldspeedrunpixeldungeon.items.artifacts.HornOfPlenty;
import com.quasistellar.oldspeedrunpixeldungeon.items.artifacts.LloydsBeacon;
import com.quasistellar.oldspeedrunpixeldungeon.items.artifacts.MasterThievesArmband;
import com.quasistellar.oldspeedrunpixeldungeon.items.artifacts.SandalsOfNature;
import com.quasistellar.oldspeedrunpixeldungeon.items.artifacts.TalismanOfForesight;
import com.quasistellar.oldspeedrunpixeldungeon.items.artifacts.TimekeepersHourglass;
import com.quasistellar.oldspeedrunpixeldungeon.items.artifacts.UnstableSpellbook;
import com.quasistellar.oldspeedrunpixeldungeon.items.bags.Bag;
import com.quasistellar.oldspeedrunpixeldungeon.items.food.Food;
import com.quasistellar.oldspeedrunpixeldungeon.items.food.MysteryMeat;
import com.quasistellar.oldspeedrunpixeldungeon.items.food.Pasty;
import com.quasistellar.oldspeedrunpixeldungeon.items.potions.Potion;
import com.quasistellar.oldspeedrunpixeldungeon.items.potions.PotionOfExperience;
import com.quasistellar.oldspeedrunpixeldungeon.items.potions.PotionOfFrost;
import com.quasistellar.oldspeedrunpixeldungeon.items.potions.PotionOfHealing;
import com.quasistellar.oldspeedrunpixeldungeon.items.potions.PotionOfInvisibility;
import com.quasistellar.oldspeedrunpixeldungeon.items.potions.PotionOfLevitation;
import com.quasistellar.oldspeedrunpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.quasistellar.oldspeedrunpixeldungeon.items.potions.PotionOfMight;
import com.quasistellar.oldspeedrunpixeldungeon.items.potions.PotionOfMindVision;
import com.quasistellar.oldspeedrunpixeldungeon.items.potions.PotionOfParalyticGas;
import com.quasistellar.oldspeedrunpixeldungeon.items.potions.PotionOfPurity;
import com.quasistellar.oldspeedrunpixeldungeon.items.potions.PotionOfStrength;
import com.quasistellar.oldspeedrunpixeldungeon.items.potions.PotionOfToxicGas;
import com.quasistellar.oldspeedrunpixeldungeon.items.rings.Ring;
import com.quasistellar.oldspeedrunpixeldungeon.items.rings.RingOfAccuracy;
import com.quasistellar.oldspeedrunpixeldungeon.items.rings.RingOfElements;
import com.quasistellar.oldspeedrunpixeldungeon.items.rings.RingOfEnergy;
import com.quasistellar.oldspeedrunpixeldungeon.items.rings.RingOfEvasion;
import com.quasistellar.oldspeedrunpixeldungeon.items.rings.RingOfForce;
import com.quasistellar.oldspeedrunpixeldungeon.items.rings.RingOfFuror;
import com.quasistellar.oldspeedrunpixeldungeon.items.rings.RingOfHaste;
import com.quasistellar.oldspeedrunpixeldungeon.items.rings.RingOfMight;
import com.quasistellar.oldspeedrunpixeldungeon.items.rings.RingOfSharpshooting;
import com.quasistellar.oldspeedrunpixeldungeon.items.rings.RingOfTenacity;
import com.quasistellar.oldspeedrunpixeldungeon.items.rings.RingOfWealth;
import com.quasistellar.oldspeedrunpixeldungeon.items.scrolls.Scroll;
import com.quasistellar.oldspeedrunpixeldungeon.items.scrolls.ScrollOfIdentify;
import com.quasistellar.oldspeedrunpixeldungeon.items.scrolls.ScrollOfLullaby;
import com.quasistellar.oldspeedrunpixeldungeon.items.scrolls.ScrollOfMagicMapping;
import com.quasistellar.oldspeedrunpixeldungeon.items.scrolls.ScrollOfMagicalInfusion;
import com.quasistellar.oldspeedrunpixeldungeon.items.scrolls.ScrollOfMirrorImage;
import com.quasistellar.oldspeedrunpixeldungeon.items.scrolls.ScrollOfPsionicBlast;
import com.quasistellar.oldspeedrunpixeldungeon.items.scrolls.ScrollOfRage;
import com.quasistellar.oldspeedrunpixeldungeon.items.scrolls.ScrollOfRecharging;
import com.quasistellar.oldspeedrunpixeldungeon.items.scrolls.ScrollOfRemoveCurse;
import com.quasistellar.oldspeedrunpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.quasistellar.oldspeedrunpixeldungeon.items.scrolls.ScrollOfTerror;
import com.quasistellar.oldspeedrunpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.quasistellar.oldspeedrunpixeldungeon.items.wands.Wand;
import com.quasistellar.oldspeedrunpixeldungeon.items.wands.WandOfBlastWave;
import com.quasistellar.oldspeedrunpixeldungeon.items.wands.WandOfCorruption;
import com.quasistellar.oldspeedrunpixeldungeon.items.wands.WandOfDisintegration;
import com.quasistellar.oldspeedrunpixeldungeon.items.wands.WandOfFireblast;
import com.quasistellar.oldspeedrunpixeldungeon.items.wands.WandOfFrost;
import com.quasistellar.oldspeedrunpixeldungeon.items.wands.WandOfLightning;
import com.quasistellar.oldspeedrunpixeldungeon.items.wands.WandOfMagicMissile;
import com.quasistellar.oldspeedrunpixeldungeon.items.wands.WandOfPrismaticLight;
import com.quasistellar.oldspeedrunpixeldungeon.items.wands.WandOfRegrowth;
import com.quasistellar.oldspeedrunpixeldungeon.items.wands.WandOfTransfusion;
import com.quasistellar.oldspeedrunpixeldungeon.items.wands.WandOfVenom;
import com.quasistellar.oldspeedrunpixeldungeon.items.weapon.Weapon;
import com.quasistellar.oldspeedrunpixeldungeon.items.weapon.melee.AssassinsBlade;
import com.quasistellar.oldspeedrunpixeldungeon.items.weapon.melee.BattleAxe;
import com.quasistellar.oldspeedrunpixeldungeon.items.weapon.melee.Dagger;
import com.quasistellar.oldspeedrunpixeldungeon.items.weapon.melee.Dirk;
import com.quasistellar.oldspeedrunpixeldungeon.items.weapon.melee.Flail;
import com.quasistellar.oldspeedrunpixeldungeon.items.weapon.melee.Glaive;
import com.quasistellar.oldspeedrunpixeldungeon.items.weapon.melee.Greataxe;
import com.quasistellar.oldspeedrunpixeldungeon.items.weapon.melee.Greatshield;
import com.quasistellar.oldspeedrunpixeldungeon.items.weapon.melee.Greatsword;
import com.quasistellar.oldspeedrunpixeldungeon.items.weapon.melee.HandAxe;
import com.quasistellar.oldspeedrunpixeldungeon.items.weapon.melee.Knuckles;
import com.quasistellar.oldspeedrunpixeldungeon.items.weapon.melee.Longsword;
import com.quasistellar.oldspeedrunpixeldungeon.items.weapon.melee.Mace;
import com.quasistellar.oldspeedrunpixeldungeon.items.weapon.melee.MagesStaff;
import com.quasistellar.oldspeedrunpixeldungeon.items.weapon.melee.Quarterstaff;
import com.quasistellar.oldspeedrunpixeldungeon.items.weapon.melee.RoundShield;
import com.quasistellar.oldspeedrunpixeldungeon.items.weapon.melee.RunicBlade;
import com.quasistellar.oldspeedrunpixeldungeon.items.weapon.melee.Sai;
import com.quasistellar.oldspeedrunpixeldungeon.items.weapon.melee.Scimitar;
import com.quasistellar.oldspeedrunpixeldungeon.items.weapon.melee.Shortsword;
import com.quasistellar.oldspeedrunpixeldungeon.items.weapon.melee.Spear;
import com.quasistellar.oldspeedrunpixeldungeon.items.weapon.melee.Sword;
import com.quasistellar.oldspeedrunpixeldungeon.items.weapon.melee.WarHammer;
import com.quasistellar.oldspeedrunpixeldungeon.items.weapon.melee.Whip;
import com.quasistellar.oldspeedrunpixeldungeon.items.weapon.melee.WornShortsword;
import com.quasistellar.oldspeedrunpixeldungeon.items.weapon.missiles.Boomerang;
import com.quasistellar.oldspeedrunpixeldungeon.items.weapon.missiles.CurareDart;
import com.quasistellar.oldspeedrunpixeldungeon.items.weapon.missiles.Dart;
import com.quasistellar.oldspeedrunpixeldungeon.items.weapon.missiles.IncendiaryDart;
import com.quasistellar.oldspeedrunpixeldungeon.items.weapon.missiles.Javelin;
import com.quasistellar.oldspeedrunpixeldungeon.items.weapon.missiles.Shuriken;
import com.quasistellar.oldspeedrunpixeldungeon.items.weapon.missiles.Tamahawk;
import com.quasistellar.oldspeedrunpixeldungeon.plants.BlandfruitBush;
import com.quasistellar.oldspeedrunpixeldungeon.plants.Blindweed;
import com.quasistellar.oldspeedrunpixeldungeon.plants.Dreamfoil;
import com.quasistellar.oldspeedrunpixeldungeon.plants.Earthroot;
import com.quasistellar.oldspeedrunpixeldungeon.plants.Fadeleaf;
import com.quasistellar.oldspeedrunpixeldungeon.plants.Firebloom;
import com.quasistellar.oldspeedrunpixeldungeon.plants.Icecap;
import com.quasistellar.oldspeedrunpixeldungeon.plants.Plant;
import com.quasistellar.oldspeedrunpixeldungeon.plants.Rotberry;
import com.quasistellar.oldspeedrunpixeldungeon.plants.Sorrowmoss;
import com.quasistellar.oldspeedrunpixeldungeon.plants.Starflower;
import com.quasistellar.oldspeedrunpixeldungeon.plants.Stormvine;
import com.quasistellar.oldspeedrunpixeldungeon.plants.Sungrass;
import com.watabou.utils.Bundle;
import com.watabou.utils.GameMath;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Generator {

	public enum Category {
		WEAPON	( 6,    Weapon.class ),
		WEP_T1	( 0,    Weapon.class),
		WEP_T2	( 0,    Weapon.class),
		WEP_T3	( 0,    Weapon.class),
		WEP_T4	( 0,    Weapon.class),
		WEP_T5	( 0,    Weapon.class),
		ARMOR	( 4,    Armor.class ),
		POTION	( 20,   Potion.class ),
		SCROLL	( 20,   Scroll.class ),
		WAND	( 3,    Wand.class ),
		RING	( 1,    Ring.class ),
		ARTIFACT( 1,    Artifact.class),
		SEED	( 0,    Plant.Seed.class ),
		FOOD	( 0,    Food.class ),
		GOLD	( 25,   Gold.class );
		
		public Class<?>[] classes;
		public float[] probs;
		
		public float prob;
		public Class<? extends Item> superClass;
		
		private Category( float prob, Class<? extends Item> superClass ) {
			this.prob = prob;
			this.superClass = superClass;
		}
		
		public static int order( Item item ) {
			for (int i=0; i < values().length; i++) {
				if (values()[i].superClass.isInstance( item )) {
					return i;
				}
			}
			
			return item instanceof Bag ? Integer.MAX_VALUE : Integer.MAX_VALUE - 1;
		}
		
		private static final float[] INITIAL_ARTIFACT_PROBS = new float[]{ 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1};
		
		static {
			GOLD.classes = new Class<?>[]{
					Gold.class };
			GOLD.probs = new float[]{ 1 };
			
			SCROLL.classes = new Class<?>[]{
					ScrollOfIdentify.class,
					ScrollOfTeleportation.class,
					ScrollOfRemoveCurse.class,
					ScrollOfUpgrade.class,
					ScrollOfRecharging.class,
					ScrollOfMagicMapping.class,
					ScrollOfRage.class,
					ScrollOfTerror.class,
					ScrollOfLullaby.class,
					ScrollOfMagicalInfusion.class,
					ScrollOfPsionicBlast.class,
					ScrollOfMirrorImage.class };
			SCROLL.probs = new float[]{ 30, 10, 20, 0, 15, 15, 12, 8, 8, 0, 4, 10 };
			
			POTION.classes = new Class<?>[]{
					PotionOfHealing.class,
					PotionOfExperience.class,
					PotionOfToxicGas.class,
					PotionOfParalyticGas.class,
					PotionOfLiquidFlame.class,
					PotionOfLevitation.class,
					PotionOfStrength.class,
					PotionOfMindVision.class,
					PotionOfPurity.class,
					PotionOfInvisibility.class,
					PotionOfMight.class,
					PotionOfFrost.class };
			POTION.probs = new float[]{ 45, 4, 15, 10, 15, 10, 0, 20, 12, 10, 0, 10 };
			
			//TODO: add last ones when implemented
			WAND.classes = new Class<?>[]{
					WandOfMagicMissile.class,
					WandOfLightning.class,
					WandOfDisintegration.class,
					WandOfFireblast.class,
					WandOfVenom.class,
					WandOfBlastWave.class,
					//WandOfLivingEarth.class,
					WandOfFrost.class,
					WandOfPrismaticLight.class,
					//WandOfWarding.class,
					WandOfTransfusion.class,
					WandOfCorruption.class,
					WandOfRegrowth.class };
			WAND.probs = new float[]{ 5, 4, 4, 4, 4, 3, /*3,*/ 3, 3, /*3,*/ 3, 3, 3 };
			
			//see generator.randomWeapon
			WEAPON.classes = new Class<?>[]{};
			WEAPON.probs = new float[]{};
			
			WEP_T1.classes = new Class<?>[]{
					WornShortsword.class,
					Knuckles.class,
					Dagger.class,
					MagesStaff.class,
					Boomerang.class,
					Dart.class
			};
			WEP_T1.probs = new float[]{ 1, 1, 1, 0, 0, 1 };
			
			WEP_T2.classes = new Class<?>[]{
					Shortsword.class,
					HandAxe.class,
					Spear.class,
					Quarterstaff.class,
					Dirk.class,
					IncendiaryDart.class
			};
			WEP_T2.probs = new float[]{ 6, 5, 5, 4, 4, 6 };
			
			WEP_T3.classes = new Class<?>[]{
					Sword.class,
					Mace.class,
					Scimitar.class,
					RoundShield.class,
					Sai.class,
					Whip.class,
					Shuriken.class,
					CurareDart.class
			};
			WEP_T3.probs = new float[]{ 6, 5, 5, 4, 4, 4, 6, 6 };
			
			WEP_T4.classes = new Class<?>[]{
					Longsword.class,
					BattleAxe.class,
					Flail.class,
					RunicBlade.class,
					AssassinsBlade.class,
					Javelin.class
			};
			WEP_T4.probs = new float[]{ 6, 5, 5, 4, 4, 6 };
			
			WEP_T5.classes = new Class<?>[]{
					Greatsword.class,
					WarHammer.class,
					Glaive.class,
					Greataxe.class,
					Greatshield.class,
					Tamahawk.class
			};
			WEP_T5.probs = new float[]{ 6, 5, 5, 4, 4, 6 };
			
			//see Generator.randomArmor
			ARMOR.classes = new Class<?>[]{
					ClothArmor.class,
					LeatherArmor.class,
					MailArmor.class,
					ScaleArmor.class,
					PlateArmor.class };
			ARMOR.probs = new float[]{ 0, 0, 0, 0, 0 };
			
			FOOD.classes = new Class<?>[]{
					Food.class,
					Pasty.class,
					MysteryMeat.class };
			FOOD.probs = new float[]{ 4, 1, 0 };
			
			RING.classes = new Class<?>[]{
					RingOfAccuracy.class,
					RingOfEvasion.class,
					RingOfElements.class,
					RingOfForce.class,
					RingOfFuror.class,
					RingOfHaste.class,
					RingOfEnergy.class,
					RingOfMight.class,
					RingOfSharpshooting.class,
					RingOfTenacity.class,
					RingOfWealth.class};
			RING.probs = new float[]{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
			
			ARTIFACT.classes = new Class<?>[]{
					CapeOfThorns.class,
					ChaliceOfBlood.class,
					CloakOfShadows.class,
					HornOfPlenty.class,
					MasterThievesArmband.class,
					SandalsOfNature.class,
					TalismanOfForesight.class,
					TimekeepersHourglass.class,
					UnstableSpellbook.class,
					AlchemistsToolkit.class, //currently removed from drop tables, pending rework.
					DriedRose.class,
					LloydsBeacon.class,
					EtherealChains.class
			};
			ARTIFACT.probs = INITIAL_ARTIFACT_PROBS.clone();
			
			SEED.classes = new Class<?>[]{
					Firebloom.Seed.class,
					Icecap.Seed.class,
					Sorrowmoss.Seed.class,
					Blindweed.Seed.class,
					Sungrass.Seed.class,
					Earthroot.Seed.class,
					Fadeleaf.Seed.class,
					Rotberry.Seed.class,
					BlandfruitBush.Seed.class,
					Dreamfoil.Seed.class,
					Stormvine.Seed.class,
					Starflower.Seed.class};
			SEED.probs = new float[]{ 12, 12, 12, 12, 12, 12, 12, 0, 3, 12, 12, 1 };
		}
	}

	private static final float[][] floorSetTierProbs = new float[][] {
			{0, 70, 20,  8,  2},
			{0, 25, 50, 20,  5},
			{0, 10, 40, 40, 10},
			{0,  5, 20, 50, 25},
			{0,  2,  8, 20, 70}
	};
	
	private static HashMap<Category,Float> categoryProbs = new LinkedHashMap<>();
	
	public static void reset() {
		for (Category cat : Category.values()) {
			categoryProbs.put( cat, cat.prob );
		}
	}
	
	public static Item random() {
		Category cat = Random.chances( categoryProbs );
		if (cat == null){
			reset();
			cat = Random.chances( categoryProbs );
		}
		categoryProbs.put( cat, categoryProbs.get( cat ) - 1);
		return random( cat );
	}
	
	public static Item random( Category cat ) {
		try {
			
			switch (cat) {
			case ARMOR:
				return randomArmor();
			case WEAPON:
				return randomWeapon();
			case ARTIFACT:
				Item item = randomArtifact();
				//if we're out of artifacts, return a ring instead.
				return item != null ? item : random(Category.RING);
			default:
				return ((Item)cat.classes[Random.chances( cat.probs )].newInstance()).random();
			}
			
		} catch (Exception e) {

			OldSpeedrunPixelDungeon.reportException(e);
			return null;
			
		}
	}
	
	public static Item random( Class<? extends Item> cl ) {
		try {
			
			return ((Item)cl.newInstance()).random();
			
		} catch (Exception e) {

			OldSpeedrunPixelDungeon.reportException(e);
			return null;
			
		}
	}

	public static Armor randomArmor(){
		return randomArmor(Dungeon.depth / 5);
	}
	
	public static Armor randomArmor(int floorSet) {

		floorSet = (int)GameMath.gate(0, floorSet, floorSetTierProbs.length-1);

		try {
			Armor a = (Armor)Category.ARMOR.classes[Random.chances(floorSetTierProbs[floorSet])].newInstance();
			a.random();
			return a;
		} catch (Exception e) {
			OldSpeedrunPixelDungeon.reportException(e);
			return null;
		}
	}

	public static final Category[] wepTiers = new Category[]{
			Category.WEP_T1,
			Category.WEP_T2,
			Category.WEP_T3,
			Category.WEP_T4,
			Category.WEP_T5
	};

	public static Weapon randomWeapon(){
		return randomWeapon(Dungeon.depth / 5);
	}
	
	public static Weapon randomWeapon(int floorSet) {

		floorSet = (int)GameMath.gate(0, floorSet, floorSetTierProbs.length-1);

		try {
			Category c = wepTiers[Random.chances(floorSetTierProbs[floorSet])];
			Weapon w = (Weapon)c.classes[Random.chances(c.probs)].newInstance();
			w.random();
			return w;
		} catch (Exception e) {
			OldSpeedrunPixelDungeon.reportException(e);
			return null;
		}
	}

	//enforces uniqueness of artifacts throughout a run.
	public static Artifact randomArtifact() {

		try {
			Category cat = Category.ARTIFACT;
			int i = Random.chances( cat.probs );

			//if no artifacts are left, return null
			if (i == -1){
				return null;
			}
			
			Class<?extends Artifact> art = (Class<? extends Artifact>) cat.classes[i];

			if (removeArtifact(art)) {
				Artifact artifact = art.newInstance();
				
				artifact.random();
				
				return artifact;
			} else {
				return null;
			}

		} catch (Exception e) {
			OldSpeedrunPixelDungeon.reportException(e);
			return null;
		}
	}

	public static boolean removeArtifact(Class<?extends Artifact> artifact) {
		if (spawnedArtifacts.contains(artifact))
			return false;

		Category cat = Category.ARTIFACT;
		for (int i = 0; i < cat.classes.length; i++)
			if (cat.classes[i].equals(artifact)) {
				if (cat.probs[i] == 1){
					cat.probs[i] = 0;
					spawnedArtifacts.add(artifact);
					return true;
				} else
					return false;
			}

		return false;
	}

	//resets artifact probabilities, for new dungeons
	public static void initArtifacts() {
		Category.ARTIFACT.probs = Category.INITIAL_ARTIFACT_PROBS.clone();
		spawnedArtifacts = new ArrayList<>();
	}

	private static ArrayList<Class<?extends Artifact>> spawnedArtifacts = new ArrayList<>();
	
	private static final String GENERAL_PROBS = "general_probs";
	private static final String SPAWNED_ARTIFACTS = "spawned_artifacts";
	
	public static void storeInBundle(Bundle bundle) {
		Float[] genProbs = categoryProbs.values().toArray(new Float[0]);
		float[] storeProbs = new float[genProbs.length];
		for (int i = 0; i < storeProbs.length; i++){
			storeProbs[i] = genProbs[i];
		}
		bundle.put( GENERAL_PROBS, storeProbs);
		
		bundle.put( SPAWNED_ARTIFACTS, spawnedArtifacts.toArray(new Class[0]));
	}

	public static void restoreFromBundle(Bundle bundle) {
		if (bundle.contains(GENERAL_PROBS)){
			float[] probs = bundle.getFloatArray(GENERAL_PROBS);
			for (int i = 0; i < probs.length; i++){
				categoryProbs.put(Category.values()[i], probs[i]);
			}
		} else {
			reset();
		}
		
		initArtifacts();
		if (bundle.contains(SPAWNED_ARTIFACTS)){
			for ( Class<?extends Artifact> artifact : bundle.getClassArray(SPAWNED_ARTIFACTS) ){
				removeArtifact(artifact);
			}
		//pre-0.6.1 saves
		} else if (bundle.contains("artifacts")) {
			String[] names = bundle.getStringArray("artifacts");
			Category cat = Category.ARTIFACT;

			for (String artifact : names)
				for (int i = 0; i < cat.classes.length; i++)
					if (cat.classes[i].getSimpleName().equals(artifact))
						cat.probs[i] = 0;
		}
	}
}
