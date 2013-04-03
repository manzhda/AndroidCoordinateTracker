package com.mda.coordinatetracker.demos;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mda.coordinatetracker.geocoder.dto.AddressRootResponse;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

/**
 * User: mda
 * Date: 4/1/13
 * Time: 10:53 PM
 */
public class SampleApp {
    public static void main(String[] args){
        Gson gson = new Gson();
        Collection<Integer> ints = new ArrayList<Integer>();
        for(int i = 0; i < 10; i++){
            ints.add(i);
        }

        String json = gson.toJson(ints);
        System.out.print(json);

        Type collectionType = new TypeToken<Collection<Integer>>(){}.getType();
        Collection<Integer> ints2 = gson.fromJson(json, collectionType);

        System.out.println();

        for(Integer integer: ints2){
            System.out.print(integer);
        }

        AddressRootResponse response = gson.fromJson(getJson(), AddressRootResponse.class);
        System.out.println(response.toString());

        AddressRootResponse responseAnother = gson.fromJson(getJson(), AddressRootResponse.class);
        System.out.println(response.toString());
    }


    private static String getJson(){
        return "{\n" +
                "   \"results\" : [\n" +
                "      {\n" +
                "         \"address_components\" : [\n" +
                "            {\n" +
                "               \"long_name\" : \"1600\",\n" +
                "               \"short_name\" : \"1600\",\n" +
                "               \"types\" : [ \"street_number\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"Amphitheatre Pkwy\",\n" +
                "               \"short_name\" : \"Amphitheatre Pkwy\",\n" +
                "               \"types\" : [ \"route\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"Mountain View\",\n" +
                "               \"short_name\" : \"Mountain View\",\n" +
                "               \"types\" : [ \"locality\", \"political\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"Santa Clara\",\n" +
                "               \"short_name\" : \"Santa Clara\",\n" +
                "               \"types\" : [ \"administrative_area_level_2\", \"political\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"California\",\n" +
                "               \"short_name\" : \"CA\",\n" +
                "               \"types\" : [ \"administrative_area_level_1\", \"political\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"United States\",\n" +
                "               \"short_name\" : \"US\",\n" +
                "               \"types\" : [ \"country\", \"political\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"94043\",\n" +
                "               \"short_name\" : \"94043\",\n" +
                "               \"types\" : [ \"postal_code\" ]\n" +
                "            }\n" +
                "         ],\n" +
                "         \"formatted_address\" : \"1600 Amphitheatre Pkwy, Mountain View, CA 94043, USA\",\n" +
                "         \"geometry\" : {\n" +
                "            \"location\" : {\n" +
                "               \"lat\" : 37.42291810,\n" +
                "               \"lng\" : -122.08542120\n" +
                "            },\n" +
                "            \"location_type\" : \"ROOFTOP\",\n" +
                "            \"viewport\" : {\n" +
                "               \"northeast\" : {\n" +
                "                  \"lat\" : 37.42426708029149,\n" +
                "                  \"lng\" : -122.0840722197085\n" +
                "               },\n" +
                "               \"southwest\" : {\n" +
                "                  \"lat\" : 37.42156911970850,\n" +
                "                  \"lng\" : -122.0867701802915\n" +
                "               }\n" +
                "            }\n" +
                "         },\n" +
                "         \"types\" : [ \"street_address\" ]\n" +
                "      }\n" +
                "   ],\n" +
                "   \"status\" : \"OK\"\n" +
                "}";
    }


    private static String getAnotherJson(){
        return "{\n" +
                "   \"results\" : [\n" +
                "      {\n" +
                "         \"address_components\" : [\n" +
                "            {\n" +
                "               \"long_name\" : \"Shadowglen\",\n" +
                "               \"short_name\" : \"Shadowglen\",\n" +
                "               \"types\" : [ \"neighborhood\", \"political\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"Manor\",\n" +
                "               \"short_name\" : \"Manor\",\n" +
                "               \"types\" : [ \"locality\", \"political\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"Travis\",\n" +
                "               \"short_name\" : \"Travis\",\n" +
                "               \"types\" : [ \"administrative_area_level_2\", \"political\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"Texas\",\n" +
                "               \"short_name\" : \"TX\",\n" +
                "               \"types\" : [ \"administrative_area_level_1\", \"political\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"United States\",\n" +
                "               \"short_name\" : \"US\",\n" +
                "               \"types\" : [ \"country\", \"political\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"78653\",\n" +
                "               \"short_name\" : \"78653\",\n" +
                "               \"types\" : [ \"postal_code\" ]\n" +
                "            }\n" +
                "         ],\n" +
                "         \"formatted_address\" : \"Shadowglen, Manor, TX 78653, USA\",\n" +
                "         \"geometry\" : {\n" +
                "            \"bounds\" : {\n" +
                "               \"northeast\" : {\n" +
                "                  \"lat\" : 30.3651460,\n" +
                "                  \"lng\" : -97.53273109999999\n" +
                "               },\n" +
                "               \"southwest\" : {\n" +
                "                  \"lat\" : 30.34942490,\n" +
                "                  \"lng\" : -97.5538840\n" +
                "               }\n" +
                "            },\n" +
                "            \"location\" : {\n" +
                "               \"lat\" : 30.35765830,\n" +
                "               \"lng\" : -97.54503430\n" +
                "            },\n" +
                "            \"location_type\" : \"APPROXIMATE\",\n" +
                "            \"viewport\" : {\n" +
                "               \"northeast\" : {\n" +
                "                  \"lat\" : 30.3651460,\n" +
                "                  \"lng\" : -97.53273109999999\n" +
                "               },\n" +
                "               \"southwest\" : {\n" +
                "                  \"lat\" : 30.34942490,\n" +
                "                  \"lng\" : -97.5538840\n" +
                "               }\n" +
                "            }\n" +
                "         },\n" +
                "         \"types\" : [ \"neighborhood\", \"political\" ]\n" +
                "      },\n" +
                "      {\n" +
                "         \"address_components\" : [\n" +
                "            {\n" +
                "               \"long_name\" : \"Shadowglen\",\n" +
                "               \"short_name\" : \"Shadowglen\",\n" +
                "               \"types\" : [ \"neighborhood\", \"political\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"Channelview\",\n" +
                "               \"short_name\" : \"Channelview\",\n" +
                "               \"types\" : [ \"locality\", \"political\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"Harris\",\n" +
                "               \"short_name\" : \"Harris\",\n" +
                "               \"types\" : [ \"administrative_area_level_2\", \"political\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"Texas\",\n" +
                "               \"short_name\" : \"TX\",\n" +
                "               \"types\" : [ \"administrative_area_level_1\", \"political\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"United States\",\n" +
                "               \"short_name\" : \"US\",\n" +
                "               \"types\" : [ \"country\", \"political\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"77530\",\n" +
                "               \"short_name\" : \"77530\",\n" +
                "               \"types\" : [ \"postal_code\" ]\n" +
                "            }\n" +
                "         ],\n" +
                "         \"formatted_address\" : \"Shadowglen, Channelview, TX 77530, USA\",\n" +
                "         \"geometry\" : {\n" +
                "            \"bounds\" : {\n" +
                "               \"northeast\" : {\n" +
                "                  \"lat\" : 29.78947790,\n" +
                "                  \"lng\" : -95.12471990\n" +
                "               },\n" +
                "               \"southwest\" : {\n" +
                "                  \"lat\" : 29.78595799999999,\n" +
                "                  \"lng\" : -95.14095089999999\n" +
                "               }\n" +
                "            },\n" +
                "            \"location\" : {\n" +
                "               \"lat\" : 29.78725060,\n" +
                "               \"lng\" : -95.13213139999999\n" +
                "            },\n" +
                "            \"location_type\" : \"APPROXIMATE\",\n" +
                "            \"viewport\" : {\n" +
                "               \"northeast\" : {\n" +
                "                  \"lat\" : 29.78947790,\n" +
                "                  \"lng\" : -95.12471990\n" +
                "               },\n" +
                "               \"southwest\" : {\n" +
                "                  \"lat\" : 29.78595799999999,\n" +
                "                  \"lng\" : -95.14095089999999\n" +
                "               }\n" +
                "            }\n" +
                "         },\n" +
                "         \"types\" : [ \"neighborhood\", \"political\" ]\n" +
                "      }\n" +
                "   ],\n" +
                "   \"status\" : \"OK\"\n" +
                "}";
    }
}
