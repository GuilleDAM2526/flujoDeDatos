import java.io.File;

public class DeleteOnExit {
    public static void main(String[] args) {

            File temporal = new File("Ficheros/temporal.txt");
            temporal.deleteOnExit();
            
    }
}
