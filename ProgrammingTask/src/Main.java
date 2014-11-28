import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main { 

	private List<Task> tasks;

	public Main(List<Task> tasks) {
		this.setTasks(tasks);
	}

	public static void main(String[] args) {
		
		Main run = new Main(setUpData());

		// for each task solve it and print the result
		for (Task task : run.tasks) { 
			BinaryIn blobStream = new BinaryIn("data/points"); 
			run.fillShortList(blobStream, task); 
			run.sortPointsList(task); 
			run.parseBlobAndUpdateArray(blobStream, task);
			run.getAndPrintResults(task); 
			System.out.println(); 
		}
	}

	// add tasks from PDF to list "tasks"
	private static List<Task> setUpData() {  
		List<Task> tasks = new ArrayList<Task>();
		
		Point point = new Point((short) -200, (short) 300, null);
		Task t1 = new Task(point, 10, true);
		tasks.add(t1);
		
		point = new Point((short) 1000, (short) 25, null);
		Task t2 = new Task(point, 20, false);  
		tasks.add(t2);
 
		return tasks;
	}
 
	
	// To have a short list that can be updated while going through the big data
	// set, we need to build it first
	void fillShortList(BinaryIn blobStream, Task task) {
		// fill array with noOfValues values
		for (int i = 0; i < task.noOfValues; i++) {
			Short x = blobStream.readShort(); 
			Short y = blobStream.readShort(); 
			Point point = new Point(x, y, task.targetPoint);
			task.pointList.add(point);
		}
	}

	
	// main job: going through the blob and updating the small array
	void parseBlobAndUpdateArray(BinaryIn blobStream, Task task) {
		boolean eof = false;
		while (!eof) {
			try {
				Short x = blobStream.readShort(); 
				Short y = blobStream.readShort();  

				Point point = new Point(x, y, task.targetPoint);
				// if current point is better than worst point in pointlist
				if (point.getDistance() < task.pointList.get(0).getDistance()) {
					task.pointList.set(0, point);
					// having element that is furthest / has biggest
					// distance at
					// the head
					sortPointsList(task);
				}
			} catch (Exception e) {
				eof = true;
			}
		} 
	}

	// We sort the short list, to have the "worst" element always at the top.
	// A sort is not needed here, I could use TreeSet for an efficient insert of an element into a sorted list.
	// But TreeSet does not work with duplicate values.
	void sortPointsList(Task task) { 
		if (task.reverse)
			Collections.sort(task.pointList, Collections.reverseOrder());
		else
			Collections.sort(task.pointList);
	}
	

	// print results of each task, which is in "pointList"
	List<Point> getAndPrintResults(Task task) {
		System.out.println(task);
		for (int i = 0; i < task.noOfValues; i++) {
			System.out.println((i + 1) + " " + task.pointList.get(i));
		}
		return task.pointList;
	}

	public List<Task> getTasks() { 
		return tasks;
	}

	
	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
}
