import static org.junit.Assert.*; 
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Test;

public class Tests { 
	private double testEuclid(Point point, Point myPoint) {
		Short x = point.getX();
		Short y = point.getY();

		double xDiff = x - myPoint.getX();
		double xSqr = Math.pow(xDiff, 2);

		double yDiff = y - myPoint.getY();
		double ySqr = Math.pow(yDiff, 2);

		return Math.sqrt(xSqr + ySqr);

	}

	@Test
	public void testIfNoPointinDataBlobIsBigger() {
		List<Task> tasks = getTestData();
		Main run = new Main(tasks);

		// for each task solve it
		for (Task task : tasks) {

			BinaryIn blobStream = new BinaryIn("data/points");

			run.fillShortList(blobStream, task); 
			run.sortPointsList(task); 
			run.parseBlobAndUpdateArray(blobStream, task);
			List<Point> res = new ArrayList<Point>(); 
			res = run.getAndPrintResults(task);

			Set<Double> distances = new HashSet<Double>();
			for (Point point : res)
				distances.add(point.getDistance());

			run = new Main(tasks);
			blobStream = new BinaryIn("data/points");
			
			boolean eof = false;
			while (!eof) {
				try {
					Short x = blobStream.readShort();
					Short y = blobStream.readShort();

					Point point = new Point(x, y, task.targetPoint);
					// make sure there is no point "better" than the worst which
					// is not in the set
					if (task.reverse)
						assertFalse(Double.compare(point.getDistance(), res
								.get(0).getDistance()) < 0
								&& (!distances.contains(point.getDistance())));
					else
						assertFalse(Double.compare(point.getDistance(), res
								.get(0).getDistance()) < 0
								&& (!distances.contains(point.getDistance())));
				} catch (Exception e) {
					eof = true;
				}
			} 
		}
	}

	@Test
	public void testIfResultHasCorrectEuclidianDistances() { 
		List<Task> tasks = getTestData();
		Main run = new Main(tasks);

		// for each task solve it
		for (Task task : tasks) {
			BinaryIn blobStream = new BinaryIn("data/points");

			run.fillShortList(blobStream, task);
			run.sortPointsList(task);
			run.parseBlobAndUpdateArray(blobStream, task);
			List<Point> res = new ArrayList<Point>(); 
			res = run.getAndPrintResults(task);

			for (Point point : res) {
				assertTrue(point.getDistance() == testEuclid(point,
						task.targetPoint));
			}
		}

	}

	private List<Task> getTestData() { 
		List<Task> tasks = new ArrayList<Task>();

		Task t1 = new Task(new Point((short) -200, (short) 300, null), 10, true); 		
		tasks.add(t1);

		Task t2 = new Task(new Point((short) 1000, (short) 25, null), 20, false);
		tasks.add(t2); 
		
		return tasks;
	} 
} 