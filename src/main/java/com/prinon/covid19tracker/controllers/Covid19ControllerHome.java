package com.prinon.covid19tracker.controllers;

import com.prinon.covid19tracker.models.LocationData;
import com.prinon.covid19tracker.services.Covid19DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class Covid19ControllerHome {

    @Autowired
    Covid19DataService covid19DataService;

    @GetMapping("/")
    public String home(Model model) {
        List<LocationData> allDatas = covid19DataService.getAllData();
        List<LocationData> deathDatas = covid19DataService.getDeathData();
        List<LocationData> recoveryDatas = covid19DataService.getrecoveryData();
        int totalReportedCases  = allDatas.stream().mapToInt(data -> data.getLatestTotalCases()).sum();
        int totalNewCases  = allDatas.stream().mapToInt(data -> data.getDiffFromPrevDay()).sum();
        int totalDeaths = deathDatas.stream().mapToInt(data -> data.getLatestTotalDeaths()).sum();
        int totalRecovery = recoveryDatas.stream().mapToInt(data -> data.getLatestTotalRecovery()).sum();
        model.addAttribute( "locationDatas", allDatas);
        model.addAttribute( "totalReportedCases", totalReportedCases);
        model.addAttribute( "totalNewCases", totalNewCases);
        model.addAttribute("totalDeaths",totalDeaths);
        model.addAttribute("totalRecovery",totalRecovery);
        return "home";
    }
}
