package car;

public class GroundAreaType extends FixtureUserData {

public float frictionModifier;
boolean outOfCourse;
public int sound;
	
    public GroundAreaType(float frictionModifier, boolean outOfCourse, int sound) {
         super(FixtureUserDataType.FUD_GROUND_AREA);
		
        this.frictionModifier = frictionModifier;
        this.outOfCourse = outOfCourse;
        this.sound = sound;
	}

}
