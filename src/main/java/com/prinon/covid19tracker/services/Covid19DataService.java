package com.prinon.covid19tracker.services;

import com.prinon.covid19tracker.models.LocationData;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class Covid19DataService {
    private static String VIRUS_Data_URL= "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    private static String Death_Data_URL="https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_deaths_global.csv";

    private static String Recovery_Data_URL= "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_recovered_global.csv";
    private List<LocationData> allData = new ArrayList<>();

    public List<LocationData> getAllData() {
        return allData;
    }

    private List<LocationData> deathData = new ArrayList<>();

    public List<LocationData> getDeathData() {
        return deathData;
    }

    private List<LocationData> recoveryData = new ArrayList<>();

    public List<LocationData> getrecoveryData() {
        return recoveryData;
    }

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void fetchVirusData() throws IOException, InterruptedException {
        List<LocationData> newData = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(VIRUS_Data_URL)).build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

        HttpClient deathclient = HttpClient.newHttpClient();
        HttpRequest deathrequest = HttpRequest.newBuilder().uri(URI.create(Death_Data_URL)).build();
        HttpResponse<String> deathhttpResponse = deathclient.send(deathrequest, HttpResponse.BodyHandlers.ofString());


        HttpClient recoveryclient = HttpClient.newHttpClient();
        HttpRequest recoveryrequest = HttpRequest.newBuilder().uri(URI.create(Recovery_Data_URL)).build();
        HttpResponse<String> recoveryhttpResponse = recoveryclient.send(recoveryrequest, HttpResponse.BodyHandlers.ofString());

        StringReader csvReader = new StringReader(httpResponse.body());
        StringReader csvReader_death = new StringReader(deathhttpResponse.body());
        StringReader csvReader_recovery = new StringReader(recoveryhttpResponse.body());

        Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(csvReader);
        Iterable<CSVRecord> deathrecords = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(csvReader_death);
        Iterable<CSVRecord> recoveryrecords = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(csvReader_recovery);


        for (CSVRecord record : records ) {
            LocationData locationDatas = new LocationData();
            String country = record.get("Country/Region");
            String state =record.get("Province/State");
            locationDatas.setCountry(record.get("Country/Region"));
            locationDatas.setState(record.get("Province/State"));


            int latestCases = Integer.parseInt(record.get(record.size() - 1));
            int previousDayCases = Integer.parseInt(record.get(record.size() - 2));
            int lastWeekCases = Integer.parseInt(record.get(record.size() - 8));
            locationDatas.setLatestTotalCases(latestCases);
            locationDatas.setDiffFromPrevDay(latestCases - previousDayCases);
            locationDatas.setDiffFromPrevWeek(latestCases - lastWeekCases);
            if (lastWeekCases != 0) {
                locationDatas.setPercentChange(Math.round(((latestCases - lastWeekCases) / (double) lastWeekCases) * 100));
            } else {
                locationDatas.setPercentChange(0);
            }

            System.out.println(locationDatas);
            newData.add(locationDatas);
        }
            this.allData = newData;




        for (CSVRecord deathrecord : deathrecords) {
                LocationData deathlocationDatas = new LocationData();
                int latestDeathCases = Integer.parseInt(deathrecord.get(deathrecord.size() - 1));
                deathlocationDatas.setLatestTotalDeaths(latestDeathCases);
                this.deathData.add(deathlocationDatas);


        }

        for (CSVRecord recoveryrecord1 : recoveryrecords) {
            LocationData recoverylocationDatas = new LocationData();
            int latestRecoveryCases = Integer.parseInt(recoveryrecord1.get(recoveryrecord1.size() - 1));
            recoverylocationDatas.setLatestTotalRecovery(latestRecoveryCases);
            this.recoveryData.add(recoverylocationDatas);
        }

    }
}
