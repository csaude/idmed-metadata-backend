package mz.org.idmed.metadata.restUtils

import grails.util.Environment
import mz.org.idmed.metadata.server.Server
import org.grails.web.json.JSONArray
import org.grails.web.json.JSONObject

import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class RestClient {

    RestClient() {

    }


    static def requestGetDataOnMetadataServer(String urlPath) {

        def server = Server.findWhere(destination: 'C_SAUDE_METADATA')
        String restUrlBase = server.getUrlPath() + server.getPort()
        String restUrl = server.getUrlPath() + server.getPort() + urlPath
        String result = ""

        try {
            String token =  PostgrestAuthenticationUtils.getJWTPermission(restUrlBase, server.getUsername(), server.getPassword())

            HttpRequest  request = HttpRequest
                        .newBuilder()
                        .uri(new URI(restUrl))
                        .header("Accept", "application/json")
                        .header("Content-type", "application/json")
                        .header("Authorization", "Bearer " + token)
                        .header("Accept-Encoding", "gzip, deflate, br")
                        .header("X-Auth-Token", token)
                        .GET()
                        .build()

            HttpResponse<String> response = HttpClient
                    .newBuilder()
                    .followRedirects(HttpClient.Redirect.NORMAL)
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofString())

            if (response.statusCode() == HttpURLConnection.HTTP_OK) {
                String result1 = response.body()
                return new JSONArray(result1)
            }
            result = response.statusCode()

        }
        catch (Exception e) {
            return e;
        }
        println(result)
        return result
    }

    static def requestGetDataOnProvincialServerClient(String urlPath) {
        def currentEnvironment = Environment.current.name
        def type = null
        println("Current environment: $currentEnvironment")
        if (currentEnvironment == Environment.DEVELOPMENT.name) {
          type = 'TEST'
        } else if (currentEnvironment == Environment.PRODUCTION.name) {
            type = 'PROD'
        }
        def server = Server.findWhere(destination: 'CENTRAL_TOOL',type:type)
        String restUrl = server.getUrlPath()+server.getPort()+urlPath
        String result = ""
        int code = 200
        try {
          //  println(restUrl)
            URL siteURL = new URL(restUrl)
            HttpURLConnection connection = (HttpURLConnection) siteURL.openConnection()
         //   connection.setRequestProperty("Authorization", "Bearer " + PostgrestAuthenticationUtils.getJWTPermission(restUrlBase, provincialServer.getUsername(), provincialServer.getPassword(), provincialServer.destination))
            connection.setRequestMethod("GET")
            connection.setRequestProperty("Content-Type", "application/json; utf-8")
            connection.setDoInput(true)
            connection.setDoOutput(true)
//            connection.setConnectTimeout(3000)
            // Send post request
            connection.connect()
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) { // success
                BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()))
                String inputLine
              ///  List response = new ArrayList<>()
                StringBuffer response = new StringBuffer()
                while ((inputLine = input.readLine()) != null) {
                    response.append(inputLine)
                }
                input.close()

                // print result
              String responseStr = response.toString();

                JSONObject jsonResponse = new JSONObject(responseStr);
                JSONObject resultJson = jsonResponse.getJSONObject("result");
                JSONArray contentArray = resultJson.getJSONArray("content");
                println(new JSONArray(contentArray))
                return new JSONArray(contentArray)
            } else {
                println("GET request not worked")
                println(new JSONObject("{\"sessionId\":null,\"authenticated\":null}"))
                return new JSONObject("{\"sessionId\":null,\"authenticated\":null}")
            }

//            connection.connect()
            code = connection.getResponseCode()
            connection.disconnect()
            if (code == 201) {
                result = "-> Green <-\t" + "Code: " + code;
            } else {
                result = "-> Yellow <-\t" + "Code: " + code;
            }
        } catch (Exception e) {
            result = "-> Red <-\t" + "Wrong domain - Exception: " + e.getMessage();
        }
        return result
    }
}
