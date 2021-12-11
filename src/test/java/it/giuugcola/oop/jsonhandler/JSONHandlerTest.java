package it.giuugcola.oop.jsonhandler;

import it.giuugcola.oop.metadata.FileMinAvgMax;
import it.giuugcola.oop.restcontroller.CallsHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("unchecked")
class JSONHandlerTest {


    @DisplayName("Test stats")
    @ParameterizedTest
    @MethodSource("provideJSONStats")
    void toJsonStats(String json) {
        Assertions.assertEquals(JSONHandler.toJson(json), JSONHandler.toJsonStats(provideArrayList()));
    }

    private ArrayList<Map<String, String>> provideArrayList() {
        Map<String, String> mapFiles = new HashMap<>();
        Map<String, String> mapPhotos = new HashMap<>();
        Map<String, String> mapVideos = new HashMap<>();

        mapFiles.put("sample_of_text.txt", "2,07MB");
        mapPhotos.put("Parrots.jpg", "0,03MB");
        mapVideos.put("SampleVideo_1280x720_1mb.mp4", "1,01MB");

        ArrayList<Map<String, String>> arrayList = new ArrayList<>();
        arrayList.add(mapFiles);
        arrayList.add(mapPhotos);
        arrayList.add(mapVideos);

        return arrayList;
    }

    private static Stream<Arguments> provideJSONStats() {
        return Stream.of(
                Arguments.of("""
                        {
                            "Tipo_file": {
                                "Foto": [
                                    {
                                        "Size": "0,03MB",
                                        "Name": "Parrots.jpg"
                                    }
                                ],
                                "Video": [
                                    {
                                        "Size": "1,01MB",
                                        "Name": "SampleVideo_1280x720_1mb.mp4"
                                    }
                                ],
                                "File": [
                                    {
                                        "Size": "2,07MB",
                                        "Name": "sample_of_text.txt"
                                    }
                                ]
                            }
                        }
                        """)
        );
    }

    @DisplayName("Test statsMAM")
    @ParameterizedTest
    @MethodSource("provideJSONStatsMAM")
    void toJsonMinAvgMax(String json) {
        Assertions.assertEquals(JSONHandler.toJsonMinAvgMax(provideFileMinAvgMax()), JSONHandler.toJson(json));
    }

    private static Stream<Arguments> provideJSONStatsMAM() {
        return Stream.of(
                Arguments.of("""
                        {
                            "Tipo_file": {
                                "Foto": {
                                    "Min": {
                                        "Size": "0,03MB",
                                        "Name": "Parrots.jpg"
                                    },
                                    "Avg": {
                                        "Size": "0,03MB"
                                    },
                                    "Max": {
                                        "Size": "0,03MB",
                                        "Name": "Parrots.jpg"
                                    }
                                },
                                "Video": {
                                    "Min": {
                                        "Size": "1,01MB",
                                        "Name": "SampleVideo_1280x720_1mb.mp4"
                                    },
                                    "Avg": {
                                        "Size": "1,01MB"
                                    },
                                    "Max": {
                                        "Size": "1,01MB",
                                        "Name": "SampleVideo_1280x720_1mb.mp4"
                                    }
                                },
                                "File": {
                                    "Min": {
                                        "Size": "2,07MB",
                                        "Name": "sample_of_text.txt"
                                    },
                                    "Avg": {
                                        "Size": "2,07MB"
                                    },
                                    "Max": {
                                        "Size": "2,07MB",
                                        "Name": "sample_of_text.txt"
                                    }
                                }
                            }
                        }
                        """)
        );
    }

    private FileMinAvgMax provideFileMinAvgMax() {
        ArrayList<Map<String, String>> mapArray = provideArrayList();
        return CallsHandler.getMinAvgMax(mapArray);
    }

}