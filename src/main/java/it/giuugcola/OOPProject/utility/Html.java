package it.giuugcola.OOPProject.utility;

import org.json.simple.JSONObject;

public class Html {

    public static String homePage() {
        return """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <style>
                        body {
                            background: linear-gradient(-45deg, #ee7752, #e73c7e, #23a6d5, #23d5ab);
                            background-size: 400% 400%;
                            animation: gradient 15s ease infinite;
                        }
                                
                        @keyframes gradient {
                            0% {
                                background-position: 0% 50%;
                            }
                            50% {
                                background-position: 100% 50%;
                            }
                            100% {
                                background-position: 0% 50%;
                            }
                        }
                                
                        h1 {
                            margin: 50px auto;
                            text-align: center;
                            color: white;
                            font-size: 8em;
                            font-family: Arial, Helvetica, sans-serif;
                            transition: 0.5s;
                        }
                                
                        p {
                            margin: 20px auto;
                            color: white;
                            text-align: center;
                            font-family: Arial, Helvetica, sans-serif;
                            font-size: 5em;
                            font-weight: normal;
                            transition: 0.5s;
                        }
                                
                        h2 {
                            margin: 35px auto;
                            text-align: center;
                            color: dimgrey;
                            font-size: 2em;
                            font-family: Arial, Helvetica, sans-serif;
                        }
                                
                        h3 {
                            margin: 35px auto;
                            text-align: center;
                            text-shadow: 2px 2px #353535;
                            color: dimgrey;
                            font-size: 7em;
                            font-family: Arial, Helvetica, sans-serif;
                        }
                                
                        h1:hover {
                            text-shadow: 0 1px 0 #ccc, 0 2px 0 #ccc,
                            0 3px 0 #ccc, 0 4px 0 #ccc,
                            0 5px 0 #ccc, 0 6px 0 #ccc,
                            0 7px 0 #ccc, 0 8px 0 #ccc,
                            0 9px 0 #ccc, 0 10px 0 #ccc,
                            0 11px 0 #ccc, 0 12px 0 #ccc,
                            0 20px 30px rgba(0, 0, 0, 0.5);
                        }
                                
                        p:hover {
                            text-shadow: 0 1px 0 #575757, 0 2px 0 #575757,
                            0 3px 0 #575757, 0 4px 0 #575757,
                            0 5px 0 #575757, 0 6px 0 #575757,
                            0 10px 20px rgba(0, 0, 0, 0.5);
                        }
                                
                    </style>
                    <meta charset="UTF-8">
                    <title>Progetto OOP</title>
                    <link rel="preconnect" href="https://fonts.googleapis.com">
                    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
                    <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">
                    <link rel="icon" href="https://toppng.com/uploads/preview/java-logo-11609365784e4gmvr3iyr.png">
                </head>
                <body>
                <h1>PROGETTO OOP</h1>
                <h2>Progetto del corso di Programmazione a oggetti sviluppato sul framework <a href="https://spring.io/" style="color: lime; text-decoration: none;">SpringBoot</a></h2>
                <h3>Autori:</h3>
                <p><a href="https://github.com/Davide-Colabella" style="color: white; text-decoration: none" >Davide Colabella</a></p>
                <p><a href="https://github.com/GiulianiM" style="color: white; text-decoration: none">Matteo Giuliani</a></p>
                </body>
                </html>
                """;
    }

}
