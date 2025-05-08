import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GCStressTest {
    private static final int MB = 1024 * 1024;
    private static final Random RANDOM = new Random();
    private static final List<byte[]> memoryHog = new ArrayList<>();
    
    public static void main(String[] args) {
        System.out.println("Starting GC Stress Test Application");
        
        // Print JVM memory settings
        Runtime runtime = Runtime.getRuntime();
        System.out.println("Max Memory: " + (runtime.maxMemory() / MB) + " MB");
        System.out.println("Initial Memory: " + (runtime.totalMemory() / MB) + " MB");
        
        while (true) {
            try {
                // Generate random memory allocation
                int size = 1 + RANDOM.nextInt(50); // Allocate between 1-50MB
                byte[] wasteMemory = new byte[size * MB];
                memoryHog.add(wasteMemory);
                
                // Periodically force a full GC
                if (RANDOM.nextInt(100) < 10) { // 10% chance
                    System.gc();
                    System.out.println("Forced Full GC");
                }
                
                // Print memory usage
                if (RANDOM.nextInt(100) < 5) { // 5% chance
                    runtime.gc();
                    long usedMemory = (runtime.totalMemory() - runtime.freeMemory()) / MB;
                    long maxMemory = runtime.maxMemory() / MB;
                    System.out.println("Memory Usage: " + usedMemory + "/" + maxMemory + " MB");
                }
                
                Thread.sleep(1000); // Sleep for 1 second
            } catch (Exception e) {
                System.out.println("Exception occurred: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
