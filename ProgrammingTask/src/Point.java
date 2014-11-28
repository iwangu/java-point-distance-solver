import java.text.MessageFormat;

public class Point implements Comparable<Point> {
	private final Short x;
	private final Short y;
	private Double distance;

	public Point(Short x, Short y, Point secondPoint) {
		this.x = x;
		this.y = y;
		this.setDistance(this.getEuclidianDistance(secondPoint));
	}

	public Double getEuclidianDistance(Point p) {
		if (p == null)
			return 0.0;

		Short x1 = p.getX();
		Short y1 = p.getY();

		double xDiff = x1 - this.getX();
		double xSqr = Math.pow(xDiff, 2);

		double yDiff = y1 - this.getY();
		double ySqr = Math.pow(yDiff, 2);

		return Math.sqrt(xSqr + ySqr);
	}

	public Short getX() {
		return x;
	}

	public Short getY() {
		return y;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	@Override
	public int compareTo(Point p) {
		if (this.getDistance() == p.getDistance())
			return 0;
		else if (this.getDistance() < p.getDistance())
			return -1;
		else
			return 1;
	}

	@Override
	public String toString() {
		return String.format(MessageFormat.format(
				"({0},{1}) having Euclidean distance {2}", this.getX(),
				this.getY(), this.distance));
	}

}
