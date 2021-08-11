package pe.bigprime.controller;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class BigSignerClient {
    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to the Jungle!");
        BigSignerClient bs = new BigSignerClient();
        String Server = "http://digitalsignatureclientside-corretto.us-east-1.elasticbeanstalk.com";
        String servir = "http://digitalsignatureclientside-corretto.us-east-1.elasticbeanstalk.com/api/getstatus";
        String servor = "http://digitalsignatureclientside-corretto.us-east-1.elasticbeanstalk.com/api/getsigneddocument";
        File Archivo = new File("C:\\Users\\LENOVO\\Desktop\\demoS.pdf");
            //Enviar pdf a firmar
        //String response = bs.setFiletoSing(Archivo, Server);
        //System.out.println(response);
            //Verificar el estado de la firma
        //String getStatus = bs.getStatus2(servir);
        //System.out.println(getStatus);
            //Descargar el archivo firmado
        bs.getDocumentSing(servor);
    }

    private String getStatus(String Server) throws IOException {
        String json = "{\"code\": \"58a6dd9c417dd9bb00a5075d727ffb0ff769204248cab598880bb8ffd3dbcd83\"}";
        URL url = new URL (Server);
        String respuesta = "";
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        try {
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes());
            os.flush();

            if (conn.getResponseCode() == HttpURLConnection.HTTP_CREATED) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String output;

            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                respuesta = output;
            }

        }catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            conn.disconnect();
        }

        return respuesta;
    }

    private String getStatus2(String Server) throws IOException {
        //String json = "{\"code\": \"58a6dd9c417dd9bb00a5075d727ffb0ff769204248cab598880bb8ffd3dbcd83\"}";
        URL url = new URL (Server);
        String respuesta = "";
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);

        String jsonInputString = "{\"code\": \"58a6dd9c417dd9bb00a5075d727ffb0ff769204248cab598880bb8ffd3dbcd83\"}";

        try(OutputStream os = con.getOutputStream()){
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int code = con.getResponseCode();
        System.out.println(code);

        try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))){
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            respuesta = response.toString();
        }

        return respuesta;
    }

    public String setFiletoSing(File file, String server) throws IOException {
        String parameters = server + "/api/signfile?vis_sig_width=90&vis_sig_height=50&vis_sig_text_size=5&vis_sig_text=Firmado+digitalmente<SIGNER><br>Fecha<DATE>";
        URL url = new URL(parameters);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-type", "application/pdf");

        final long documentLength = file.length();
        conn.setFixedLengthStreamingMode(documentLength);

        OutputStream os = conn.getOutputStream();
        try (InputStream documentIS = new BufferedInputStream(new FileInputStream(file))) {
            byte[] b = new byte[4096];

            int readCount;
            while (-1 != (readCount = documentIS.read(b))) {
                os.write(b, 0, readCount);
            }
        }

        int statusCode = conn.getResponseCode();

        String responseFromServer = "";
        if (statusCode == 200) {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            while ((output = br.readLine()) != null) {
                responseFromServer = output;
            }
        }
        conn.disconnect();
        System.out.println(statusCode);
        return responseFromServer;
    }

    public void getDocumentSing(String Server) throws IOException {
        URL url = new URL (Server);
        String json = "{\"code\": \"58a6dd9c417dd9bb00a5075d727ffb0ff769204248cab598880bb8ffd3dbcd83\", \"documentId\": \"2725\"}";
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");

        OutputStream os = conn.getOutputStream();
        os.write(json.getBytes());
        os.flush();
        int code = conn.getResponseCode();
        System.out.println(code);
        InputStream inputStream = conn.getInputStream();
        String saveFilePath = "C:\\Users\\LENOVO\\Desktop\\demoFirmado.pdf";
        FileOutputStream outputStream = new FileOutputStream(saveFilePath);

        int bytesRead = -1;
        byte[] buffer = new byte[4096];
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        outputStream.close();
        inputStream.close();

        System.out.println("File downloaded");
    }
}
