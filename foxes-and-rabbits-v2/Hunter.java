import java.util.Random;
import java.util.Iterator;
import java.util.List;
/**
 * Write a description of class Hunter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Hunter extends Actor
{
    // Random number generator
    private static final Random rand = Randomizer.getRandom();
    
    private int activePeriod;
    
    private int age;
    
    private boolean isActive;
    
    public Hunter(Field field, Location location){
        super(field, location);
        activePeriod = rand.nextInt();
        isActive = true;
    } 
    
    /**
     * This is what the fox does most of the time: it hunts for
     * prey.
     * @param field The field currently occupied.
     * @param newFoxes A list to add newly born foxes to.
     */
    public void act(List<Actor> newActor){
        incrementAge();
        if(isActive){
            // Move towards a source of food if found.
            Location location = getLocation();
            Location newLocation = findFood(location);
            if(newLocation == null) { 
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(location);
            }
            // See if it was possible to move.
            if(newLocation != null) {
                setLocation(newLocation);
            }
        }
    }
    
    /**
     * Increase the age. This could result in the wolf's death.
     */
    private void incrementAge()
    {
        age++;
        if(age > activePeriod) {
            isActive = false;
        }
    }
    
    /**
     * Tell the fox to look for rabbits adjacent to its current location.
     * Only the first live rabbit is eaten.
     * @param location Where in the field it is located.
     * @return Where food was found, or null if it wasn't.
     */
    private Location findFood(Location location)
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) animal;
                if(rabbit.isAlive()) { 
                    rabbit.setDead();
                    return where;
                }
            }
            if(animal instanceof Fox) {
                Fox fox = (Fox) animal;
                if(fox.isAlive()) { 
                    fox.setDead();
                    return where;
                }
            }
            if(animal instanceof Wolf) {
                Wolf wolf = (Wolf) animal;
                if(wolf.isAlive()) { 
                    wolf.setDead();
                    return where;
                }
            }
        }
        return null;
    }
    
    public boolean isAlive(){
        return true;
    }
}
