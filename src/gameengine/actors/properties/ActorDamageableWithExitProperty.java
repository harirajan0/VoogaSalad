
// This entire file is part of my masterpiece.
// HARI RAJAN
//
// This class allows an actor to be damageable. I believe this class showcases many
// of the concepts we have learned in class including implementing interfaces, 
// generics, streams, consumers, and more. I think the use of generics here is 
// especially noteworthy. I spent a lot of time explaining this in my ANALYSIS.md,
// but the use of generics here allows this property class to gain access to exactly
// the methods it needs. Because the ActorDamageableProperty needs to damage actors,
// it needs access to the ReadAndDamageGrid.  This class also showcases how duplicate
// code can easily be eliminated using abstract super classes.

package gameengine.actors.properties;

import java.util.function.Consumer;

import gamedata.composition.ActorDamageableWithExitData;
import gameengine.grid.interfaces.ActorGrid.ReadAndDamageGrid;

/**
 * Class extending the ActorDamageableProperty that allows for the alteration of
 * the game status' money and experience.
 * 
 * @author Moses Wayne
 *
 * @param <G>
 *            generic grid that extends ReadAndDamageGrid
 */
public class ActorDamageableWithExitProperty<G extends ReadAndDamageGrid> extends ActorDamageableProperty<G> {

	public ActorDamageableWithExitProperty(ActorDamageableWithExitData myData) {
		super(myData);
	}

	/**
	 * overrides the method determining how the ActorDamageable determines
	 * action by adding on calls to alter the game status
	 */
	@Override
	protected void damageActor(G grid, Integer actorID, Consumer<Double> damage, Double health) {
		super.damageActor(grid, actorID, damage, health);
		grid.getWriteableGameStatus().addExperience(health);
		grid.getWriteableGameStatus().addMoney(health.intValue());
	}

}
