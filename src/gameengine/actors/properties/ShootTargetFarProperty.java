
// This entire file is part of my masterpiece.
// HARI RAJAN
//
// This class allows an actor to be shoot the target furthest from to it within its hit
// radius. I think the use of generics here is especially noteworthy. I spent a 
// lot of time explaining this in my ANALYSIS.md, but the use of generics here 
// allows this property class to gain access to exactly the methods it needs. 
// Because the ShootTargetNearProperty needs to spawn projectiles,it needs access
// to the methods from the ReadAndSpawnGrid. This class also showcases how duplicate
// code can easily be eliminated using abstract super classes.

package gameengine.actors.properties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

import gamedata.composition.ShootTargetFarData;
import gameengine.grid.interfaces.ActorGrid.ReadAndSpawnGrid;
import gameengine.grid.interfaces.Identifiers.Grid2D;
import util.PathUtil;

/**
 * Class extension of the ShootTargetLineProperty that fires an actor toward an
 * enemy unit within a certain range that is the furthest away from the actor
 * holding this property
 * 
 * @author Moses Wayne
 *
 * @param <G> generic grid extending the ReadAndSpawnGrid
 */
public class ShootTargetFarProperty<G extends ReadAndSpawnGrid> extends ShootTargetLineProperty<G> {

	public ShootTargetFarProperty(ShootTargetFarData myData) {
		super(myData);
	}

	@Override
	protected Collection<Double> getEnemyToShoot(Collection<Grid2D> points, Grid2D myPos) {
		ArrayList<Double> retCollection = new ArrayList<>();
		points.stream().max(Comparator.comparingDouble(point -> PathUtil.getDistance(myPos, point)))
				.ifPresent((point) -> retCollection.add(PathUtil.getAngle(myPos, point)));
		return retCollection;
	}

}
