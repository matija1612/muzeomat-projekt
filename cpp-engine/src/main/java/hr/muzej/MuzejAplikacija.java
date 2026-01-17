package hr.muzej;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class MuzejAplikacija {
public static void main(String[] args) {
SpringApplication.run(MuzejAplikacija.class, args);
System.out.println("==============================================");
System.out.println("Muzej Backend pokrenut na portu 8080");
System.out.println("Admin panel: http://localhost:8080/admin/login");
System.out.println("API dokumentacija: http://localhost:8080/api");
System.out.println("==============================================");
}
}
