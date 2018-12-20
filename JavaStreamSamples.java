import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class JavaStreamSamples {


	/*
	 * 
	 * Q1 : Integer Stream
	 * Q2 : Integer Stream with skip
	 * Q3 : Integer Stream with sum
	 * Q4 : Stream.of, sorted and findFirst "Ava", "Aneri", "Alberto"
	 * Q5 : Stream from Array, sort, filter and print {"Al", "Ankit", "Kushal", "Brent", "Sarika", "amanda", "Hans", "Shivika", "Sarah"};
	 * Q6 : average of squares of an int array new int[] {2, 4, 6, 8, 10}
	 * Q7 : Stream from List, filter and print Arrays.asList("Al", "Ankit", "Brent", "Sarika", "amanda", "Hans", "Shivika", "Sarah")
	 * Q8 : Stream rows from text file, sort, filter, and print -> use bands.txt
	 * Q9 : Stream rows from text file and save to List
	 * Q10 : Stream rows from CSV file and count -> Use data.txt
	 * Q11 : Stream rows from CSV file, parse data from rows
	 * Q12 : Stream rows from CSV file, store fields in HashMap
	 * Q13 : Reduction - sum (7.3, 1.5, 4.8)
	 * Q14 : Reduction - summary statistics (7, 2, 19, 88, 73, 4, 10)
	 *  
	 */
	public static void main(String[] args) {

		try {
			Class<?> tClass = Class.forName("JavaStreamSamples");

			Method[] declaredMethods = tClass.getDeclaredMethods();

			//Print all available methods
			Arrays.stream(declaredMethods).forEach(e-> System.out.println(e.getName()));

			//Stream to automatically call all static methods. 
			IntStream.range(1, declaredMethods.length-1).forEach(i->{
				Method m;
				try {
					m = tClass.getDeclaredMethod("Q"+i);
					if(Modifier.isStatic(m.getModifiers())){
						System.out.println("Q"+i+" : ");
						m.invoke(null, null);
						System.out.println("------------------");
					}
				} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			});

		} catch (ClassNotFoundException | SecurityException | IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//Q1 : Integer Stream
	public static void Q1() {
		IntStream.range(1, 11).forEach(System.out::println);
	}

	//Q2 : Integer Stream with skip
	public static void Q2() {
		IntStream.range(1, 11).skip(5).forEach(System.out::println);
	}

	//Q3 : Integer Stream with sum
	public static void Q3() {
		int sum = IntStream.range(1, 11).sum();
		System.out.println(sum);
	}

	//Q4 : Stream.of, sorted and findFirst
	public static void Q4() {
		Stream.of( "Ava", "Aneri", "Alberto").sorted().findFirst().ifPresent(System.out::println);
	}

	//Q5 : Stream from Array, sort, filter and print 
	public static void Q5() {
		String s[] = {"Al", "Ankit", "Kushal", "Brent", "Sarika", "amanda", "Hans", "Shivika", "Sarah"};

		Arrays.stream(s).sorted().filter(x-> x.startsWith("a") || x.startsWith("A")).forEach(System.out::println);
	}

	//Q6 : average of squares of an int array 
	public static void Q6() {
		int n[] = new int[] {2, 4, 6, 8, 10};
		IntStream.of(n).map(x->x*2).average().ifPresent(System.out::println);
	}

	//Q7 : Stream from List, filter and print 
	public static void Q7() {
		List<String> s = Arrays.asList("Al", "Ankit", "Brent", "Sarika", "amanda", "Hans", "Shivika", "Sarah");
		s.stream().filter(x->x.length()<=4).forEach(System.out::println);
	}

	//Q8 : Stream rows from text file, sort, filter, and print -> use bands.txt
	public static void Q8() {
		try {
			Files.lines(Paths.get("/home/shivaak/Documents/Java/Streams/bands.txt"))
			.sorted()
			.filter(x-> x.length()>10)
			.forEach(System.out::println);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	//Q9 : Stream rows from text file and save to List
	public static void Q9() {
		try {
			List<String> myList = Files.lines(Paths.get("/home/shivaak/Documents/Java/Streams/bands.txt"))
					.collect(Collectors.toList());
			System.out.println(myList.size());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//Q10 : Stream rows from CSV file and count -> Use data.txt
	public static void Q10() {
		try {
			long count = Files.lines(Paths.get("/home/shivaak/eclipse-workspace/Learning/src/data.txt"))
					.count();
			System.out.println(count);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Q11 : Stream rows from CSV file, parse data from rows
	public static void Q11() {
		try {
			Files.lines(Paths.get("/home/shivaak/eclipse-workspace/Learning/src/data.txt"))
				 .map(x->x.split(","))
				 .filter(x->x.length==3)
				 .forEach(x->System.out.println(x[0] + " -- " + x[1] + " -- " + x[2]));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Q12 : Stream rows from CSV file, store fields in HashMap
	public static void Q12() {
		try {
			Map<String, String> collect = Files.lines(Paths.get("/home/shivaak/eclipse-workspace/Learning/src/data.txt"))
				 .map(x->x.split(","))
				 .filter(x->x.length==3)
				 .collect(Collectors.toMap(x->x[0], x->x[2]));
			System.out.println(collect);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Q13 : Reduction - sum (7.3, 1.5, 4.8)
	public static void Q13() {
		Double reduce = Stream.of(7.3, 1.5, 4.8).reduce(0.0 , (Double a, Double b)-> a+b);
		System.out.println(reduce);
	}
	
	//Q14 : Reduction - summary statistics (7, 2, 19, 88, 73, 4, 10)
	public static void Q14() {
		DoubleSummaryStatistics summaryStatistics = DoubleStream.of(7, 2, 19, 88, 73, 4, 10).summaryStatistics();
		System.out.println(summaryStatistics);
	}

}
