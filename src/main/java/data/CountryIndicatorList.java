package main.java.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CountryIndicatorList implements DataManager{

    private ObservableList<CountryIndicator> countryIndicators = FXCollections.observableArrayList();

    private static final String COUNTRY_LIST ="GB;US;TR;AE;CL;CN;AU;AT;BE;BR;DK;CZ;TH;SE;CH;ES;SG;RO;RU;PL;PT;CA;FI;GR;VN;BD;CO;ZA;PK;MY;IE;IL;IT;IR;IN;ID;PH;HK;JP;VE;EG;NO;NG;AR;DE;FR;KR;MX;NL;SA";
    private static final String GDP_CURRENT_$US = "http://api.worldbank.org/countries/GB;US;TR;AE;CL;CN;AU;AT;BE;BR;DK;CZ;TH;SE;CH;ES;SG;RO;RU;PL;PT;CA;FI;GR;VN;BD;CO;ZA;PK;MY;IE;IL;IT;IR;IN;ID;PH;HK;JP;VE;EG;NO;NG;AR;DE;FR;KR;MX;NL;SA/indicators/NY.GDP.MKTP.CD?format=json&per_page=15050";
    private static final String GDP_PER_CAPITA_CURRENT_$US = "http://api.worldbank.org/countries/GB;US;TR;AE;CL;CN;AU;AT;BE;BR;DK;CZ;TH;SE;CH;ES;SG;RO;RU;PL;PT;CA;FI;GR;VN;BD;CO;ZA;PK;MY;IE;IL;IT;IR;IN;ID;PH;HK;JP;VE;EG;NO;NG;AR;DE;FR;KR;MX;NL;SA/indicators/NY.GDP.PCAP.CD?format=json&per_page=15050";
    private static final String INFLATION_RATE = "http://api.worldbank.org/countries/GB;US;TR;AE;CL;CN;AU;AT;BE;BR;DK;CZ;TH;SE;CH;ES;SG;RO;RU;PL;PT;CA;FI;GR;VN;BD;CO;ZA;PK;MY;IE;IL;IT;IR;IN;ID;PH;HK;JP;VE;EG;NO;NG;AR;DE;FR;KR;MX;NL;SA/indicators/FP.CPI.TOTL.ZG?format=json&per_page=15050";
    private static final String UNEMPLOYMENT_RATE = "http://api.worldbank.org/countries/GB;US;TR;AE;CL;CN;AU;AT;BE;BR;DK;CZ;TH;SE;CH;ES;SG;RO;RU;PL;PT;CA;FI;GR;VN;BD;CO;ZA;PK;MY;IE;IL;IT;IR;IN;ID;PH;HK;JP;VE;EG;NO;NG;AR;DE;FR;KR;MX;NL;SA/indicators/SL.UEM.TOTL.ZS?format=json&per_page=15050";
    private static final String GDP_GROWTH = "http://api.worldbank.org/countries/GB;US;TR;AE;CL;CN;AU;AT;BE;BR;DK;CZ;TH;SE;CH;ES;SG;RO;RU;PL;PT;CA;FI;GR;VN;BD;CO;ZA;PK;MY;IE;IL;IT;IR;IN;ID;PH;HK;JP;VE;EG;NO;NG;AR;DE;FR;KR;MX;NL;SA/indicators/NY.GDP.MKTP.KD.ZG?format=json&per_page=15050";
    private static final String GDP_PER_CAPITA_GROWTH = "http://api.worldbank.org/countries/GB;US;TR;AE;CL;CN;AU;AT;BE;BR;DK;CZ;TH;SE;CH;ES;SG;RO;RU;PL;PT;CA;FI;GR;VN;BD;CO;ZA;PK;MY;IE;IL;IT;IR;IN;ID;PH;HK;JP;VE;EG;NO;NG;AR;DE;FR;KR;MX;NL;SA/indicators/NY.GDP.PCAP.KD.ZG?format=json&per_page=15050";
    private static final String COUNTRYINDICATOR_PATH = "main/resources/indicators/"; // change to "indicators/" when building with gradle

    public CountryIndicatorList() {
        storeJSONFromLocalToList();
    }
//    public static void main(String[] args){
//        new CountryIndicatorList().storeJSONToFile(GDP_PER_CAPITA_GROWTH, "src/main/resources/indicators/GDP_PER_CAPITA_GROWTH.json");
//    }

    public CountryIndicator initializeCountryIndicator(JSONObject jsonObject){
        CountryIndicator countryIndicator = null;
        try {
            String countryID = (String) jsonObject.getJSONObject("country").get("id");
            String countryValue = (String) jsonObject.getJSONObject("country").get("value");
            int date = Integer.parseInt((String) jsonObject.get("date"));
            countryIndicator = new CountryIndicator(countryID, countryValue, date);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return countryIndicator;
    }

    @Override
    public void storeJSONFromLocalToList() {
        try {
            JSONArray jsonArray = new JSONArray(getJSONFromFile(COUNTRYINDICATOR_PATH + "GDP_CURRENT_$US.json")).getJSONArray(1);
            JSONArray jsonArray1 = new JSONArray(getJSONFromFile(COUNTRYINDICATOR_PATH + "GDP_PER_CAPITA_CURRENT_$US.json")).getJSONArray(1);
            JSONArray jsonArray2 = new JSONArray(getJSONFromFile(COUNTRYINDICATOR_PATH + "INFLATION_RATE.json")).getJSONArray(1);
            JSONArray jsonArray3 = new JSONArray(getJSONFromFile(COUNTRYINDICATOR_PATH + "UNEMPLOYMENT_RATE.json")).getJSONArray(1);
            JSONArray jsonArray4 = new JSONArray(getJSONFromFile(COUNTRYINDICATOR_PATH + "GDP_GROWTH.json")).getJSONArray(1);
            JSONArray jsonArray5 = new JSONArray(getJSONFromFile(COUNTRYINDICATOR_PATH + "GDP_PER_CAPITA_GROWTH.json")).getJSONArray(1);
            for(int i = 0; i < jsonArray.length() && i < jsonArray1.length(); i++){
                CountryIndicator countryIndicator = initializeCountryIndicator(jsonArray.getJSONObject(i));
                if(!jsonArray.getJSONObject(i).get("value").equals(null)){
                    countryIndicator.setGDP_CURRENT_$US(Double.parseDouble((String) jsonArray.getJSONObject(i).get("value")));
                }
                if(!jsonArray1.getJSONObject(i).get("value").equals(null)){
                    countryIndicator.setGDP_PER_CAPITA_CURRENT_$US(Double.parseDouble((String) jsonArray1.getJSONObject(i).get("value")));
                }
                if(!jsonArray2.getJSONObject(i).get("value").equals(null)){
                    countryIndicator.setINFLATION_RATE(Double.parseDouble((String) jsonArray2.getJSONObject(i).get("value")));
                }
                if(!jsonArray3.getJSONObject(i).get("value").equals(null)){
                    countryIndicator.setUNEMPLOYMENT_RATE(Double.parseDouble((String) jsonArray3.getJSONObject(i).get("value")));
                }
                if(!jsonArray4.getJSONObject(i).get("value").equals(null)){
                    countryIndicator.setGDP_GROWTH(Double.parseDouble((String) jsonArray4.getJSONObject(i).get("value")));
                }
                if(!jsonArray5.getJSONObject(i).get("value").equals(null)){
                    countryIndicator.setGDP_PER_CAPITA_GROWTH(Double.parseDouble((String) jsonArray5.getJSONObject(i).get("value")));
                }
                countryIndicators.add(countryIndicator);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void storeJSONFromURLToList() {
        try {
            JSONArray jsonArray = new JSONArray(getJSONFromURL(GDP_CURRENT_$US)).getJSONArray(1);
            JSONArray jsonArray1 = new JSONArray(getJSONFromURL(GDP_PER_CAPITA_CURRENT_$US)).getJSONArray(1);
            JSONArray jsonArray2 = new JSONArray(getJSONFromURL(INFLATION_RATE)).getJSONArray(1);
            JSONArray jsonArray3 = new JSONArray(getJSONFromURL(UNEMPLOYMENT_RATE)).getJSONArray(1);
            JSONArray jsonArray4 = new JSONArray(getJSONFromFile(GDP_GROWTH)).getJSONArray(1);
            JSONArray jsonArray5 = new JSONArray(getJSONFromFile(GDP_PER_CAPITA_GROWTH)).getJSONArray(1);
            for(int i = 0; i < jsonArray.length() && i < jsonArray1.length(); i++){
                CountryIndicator countryIndicator = initializeCountryIndicator(jsonArray.getJSONObject(i));
                if(!jsonArray.getJSONObject(i).get("value").equals(null)){
                    countryIndicator.setGDP_CURRENT_$US(Double.parseDouble((String) jsonArray.getJSONObject(i).get("value")));
                }
                if(!jsonArray1.getJSONObject(i).get("value").equals(null)){
                    countryIndicator.setGDP_PER_CAPITA_CURRENT_$US(Double.parseDouble((String) jsonArray1.getJSONObject(i).get("value")));
                }
                if(!jsonArray2.getJSONObject(i).get("value").equals(null)){
                    countryIndicator.setINFLATION_RATE(Double.parseDouble((String) jsonArray2.getJSONObject(i).get("value")));
                }
                if(!jsonArray3.getJSONObject(i).get("value").equals(null)){
                    countryIndicator.setUNEMPLOYMENT_RATE(Double.parseDouble((String) jsonArray3.getJSONObject(i).get("value")));
                }
                if(!jsonArray4.getJSONObject(i).get("value").equals(null)){
                    countryIndicator.setGDP_GROWTH(Double.parseDouble((String) jsonArray4.getJSONObject(i).get("value")));
                }
                if(!jsonArray5.getJSONObject(i).get("value").equals(null)){
                    countryIndicator.setGDP_PER_CAPITA_GROWTH(Double.parseDouble((String) jsonArray5.getJSONObject(i).get("value")));
                }
                countryIndicators.add(countryIndicator);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<CountryIndicator> getCountryIndicators(){
        return countryIndicators;
    }

}