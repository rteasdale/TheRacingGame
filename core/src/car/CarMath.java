package car;

import com.badlogic.gdx.math.Vector2;

public class CarMath {
	public static final float FLT_EPSILON = 1.192092896e-07F;

	public static float clamp(float a, float low, float height){
		return Math.max(low, Math.min(a, height));
	}
	
	public static Vector2 max(Vector2 a, Vector2 b){
		return new Vector2(Math.max(a.x, b.x), Math.max(a.y, b.y));
	}
	
	public static Vector2 min(Vector2 a, Vector2 b){
		return new Vector2(Math.min(a.x, b.x), Math.min(a.y, b.y));
	}
	
	public static Vector2 multiply(float s, Vector2 a) {
		return new Vector2(s * a.x, s * a.y);
	}

	public static Vector2 multiply(Vector2 a, float s) {
		return new Vector2(a.x * s, a.y * s);
	}

	public static Vector2 minus(Vector2 a) {
		a.x = (-a.x);
		a.y = (-a.y);
		return a;
	}

	public static float normalize(Vector2 vector) {
		float length = vector.len();

		if (length < FLT_EPSILON) {
			return 0.0f;
		}
		float invLength = 1.0f / length;
		vector.x = vector.x * invLength;
		vector.y = vector.y * invLength;

		return length;
	}
        
                    public static Vector2 getCenterPoint(Vector2 positionCar1, Vector2 positionCar2){
                        Vector2 center = null;
                        
                        center.x = (positionCar1.x + positionCar2.x)/2;
                        center.y = (positionCar1.y + positionCar2.y)/2;
                        
                        return center;
                    }
                    
                    public static Vector2 getCenterPoint(float Car1X, float Car1Y, float Car2X, float Car2Y){
                        Vector2 center = null;
                        
                        center.x = (Car1X + Car2X)/2;
                        center.y = (Car1Y + Car2Y)/2;
                        
                        return center;
                    }
                    
                    public static float Distance (Vector2 positionCar1, Vector2 positionCar2){
                        float distance = 0;
                        
                        float a = (float) Math.pow(positionCar2.x - positionCar1.x, 2);
                        float b = (float) Math.pow(positionCar2.y - positionCar1.y, 2);
                        
                        distance = (float) Math.sqrt(a+b);
                        
                        return distance;
                    }
                    
                    public static float Distance (float Car1X, float Car1Y, float Car2X, float Car2Y){
                        float distance = 0;
                        
                        float a = (float) Math.pow(Car2X - Car1X, 2);
                        float b = (float) Math.pow(Car2Y - Car1Y, 2);
                        
                        distance = (float) Math.sqrt(a+b);
                        
                        return distance;
                    }
                    
}
