package hr.muzej.javafx.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hr.muzej.javafx.model.OdgovorDTO;
import hr.muzej.javafx.model.ProstorijaDTO;
import hr.muzej.javafx.model.ZahtjevDTO;
import okhttp3.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ApiServis {
    private final OkHttpClient client;
    private final ObjectMapper mapper;
    private final String baseUrl;

    public ApiServis(String baseUrl) {
        this.baseUrl = baseUrl;
        this.mapper = new ObjectMapper();

        this.client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    public List<ProstorijaDTO> dohvatiProstorije() throws IOException {
        String url = baseUrl + "/api/prostorije/aktivne";

        Request req = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response resp = client.newCall(req).execute()) {
            if (!resp.isSuccessful()) {
                throw new IOException("HTTP error: " + resp.code());
            }
            String json = resp.body() != null ? resp.body().string() : "[]";
            return mapper.readValue(json, new TypeReference<List<ProstorijaDTO>>() {});
        }
    }

    public OdgovorDTO izracunajRutu(ZahtjevDTO zahtjev) throws IOException {
        String url = baseUrl + "/api/rute/izracunaj";

        String jsonBody = mapper.writeValueAsString(zahtjev);

        RequestBody body = RequestBody.create(
                jsonBody,
                MediaType.get("application/json; charset=utf-8")
        );

        Request req = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try (Response resp = client.newCall(req).execute()) {
            if (!resp.isSuccessful()) {
                String msg = (resp.body() != null) ? resp.body().string() : "Unknown error";
                throw new IOException("HTTP error: " + resp.code() + " - " + msg);
            }

            String json = resp.body() != null ? resp.body().string() : "{}";
            return mapper.readValue(json, OdgovorDTO.class);
        }
    }
}
