package hr.muzej.servisi;

import hr.muzej.entiteti.Prostorija;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class TSPNativeServis {

    @Value("${tsp.executable.path:./tsp-engine}")
    private String tspPath;
    
    @Value("${tsp.timeout.seconds:30}")
    private int timeout;
    
    public List<Integer> pozovi(List<Prostorija> prostorije) {
        log.info("Pozivam TSP engine za {} prostorija", prostorije.size());
        
        double[][] matrica = kreirajMatricuUdaljenosti(prostorije);
        String ulaz = formatirajUlaz(matrica);
        log.debug("TSP ulaz:\n{}", ulaz);
        
        List<Integer> rezultat = pozovtProces(ulaz, prostorije.size());
        
        log.info("TSP rezultat: {}", rezultat);
        return rezultat;
    }
    
    private double[][] kreirajMatricuUdaljenosti(List<Prostorija> prostorije) {
        int n = prostorije.size();
        double[][] matrica = new double[n][n];
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    matrica[i][j] = 0;
                } else {
                    Prostorija p1 = prostorije.get(i);
                    Prostorija p2 = prostorije.get(j);
                    matrica[i][j] = p1.udaljenostDo(p2);
                }
            }
        }
        
        return matrica;
    }
    
    private String formatirajUlaz(double[][] matrica) {
        StringBuilder sb = new StringBuilder();
        int n = matrica.length;
        
        sb.append(n).append("\n");
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(String.format("%.2f", matrica[i][j]));
                if (j < n - 1) sb.append(" ");
            }
            sb.append("\n");
        }
        
        return sb.toString();
    }
    
    private List<Integer> pozovtProces(String ulaz, int n) {
        List<Integer> ruta = new ArrayList<>();
        
        try {
            ProcessBuilder pb = new ProcessBuilder(tspPath);
            pb.redirectErrorStream(true);
            
            Process process = pb.start();
            
            try (OutputStream os = process.getOutputStream()) {
                os.write(ulaz.getBytes(StandardCharsets.UTF_8));
                os.flush();
            }
            
            boolean zavrsen = process.waitFor(timeout, TimeUnit.SECONDS);
            
            if (!zavrsen) {
                process.destroyForcibly();
                log.error("TSP engine timeout!");
                return fallbackRuta(n);
            }
            
            try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8)
            )) {
                String linija;
                while ((linija = reader.readLine()) != null) {
                    linija = linija.trim();
                    if (!linija.isEmpty()) {
                        String[] dijelovi = linija.split("\\s+");
                        for (String dio : dijelovi) {
                            try {
                                ruta.add(Integer.parseInt(dio));
                            } catch (NumberFormatException e) {
                            }
                        }
                    }
                }
            }
            
            int exitCode = process.exitValue();
            if (exitCode != 0) {
                log.warn("TSP engine zavrsio s kodom {}", exitCode);
            }
            
        } catch (IOException e) {
            log.error("Greska pri pokretanju TSP enginea: {}", e.getMessage());
            return fallbackRuta(n);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("TSP prekinut");
            return fallbackRuta(n);
        }
        
        if (ruta.size() != n) {
            log.warn("TSP vratio {} elemenata, ocekivano {}", ruta.size(), n);
            return fallbackRuta(n);
        }
        
        return ruta;
    }
    
    private List<Integer> fallbackRuta(int n) {
        log.warn("Koristim fallback rutu (sekvencijalni redoslijed)");
        List<Integer> ruta = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            ruta.add(i);
        }
        return ruta;
    }
}
