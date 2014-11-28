import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class Task {
	
	public Task(Point targetPoint, int noOfValues, boolean reverse){
		this.targetPoint = targetPoint;
		this.noOfValues = noOfValues;
		this.reverse = reverse;
		this.pointList = new ArrayList<Point>();
	}

	// List that is updated while scanning through the data-blob
	List<Point> pointList;

	// point we are comparing all points with
	Point targetPoint;

	// no of nearest/farthest neighbours
	int noOfValues;

	// Farhest neighbours, then reverse = false
	// Nearest neighbours, then reverse = true
	boolean reverse;

	@Override
	public String toString() {
		String distString = reverse ? "Nearest" : "Farthest";
		return String.format(MessageFormat.format(
				"{0} {1} values to point ({2},{3})", distString, noOfValues,
				targetPoint.getX(), targetPoint.getY()));
	}
}