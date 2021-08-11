package pe.bigprime.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import pe.bigprime.models.OcrLinesList;
import pe.bigprime.models.StatusRek;

import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

public class ApiRekognition {
    public static void main(String[] args) throws Exception {
        System.out.println("Welcome to the Jungle!");

        String name = "elbananero4";
        String lastname = "sapee";
        String dni = "46191069";
        String UUID = "e937528869ca4cea92101e0ddbdf1c05";
        StringBuilder urlRegister = new StringBuilder();
        StringBuilder urlStatus = new StringBuilder();

        File path = new File("C:\\Users\\LENOVO\\Desktop\\dni.jpeg");

        urlRegister.append("https://rekognition.bigprime.pe/api/registerid/")
                .append(UUID).append("?")
                .append("dni=").append(dni).append("&")
                .append("name=").append(name).append("&")
                .append("lastname=").append(lastname);

        urlStatus.append("https://rekognition.bigprime.pe/api/getstatus");

        //Habilitar uno de los tres metodos para Registrar usuario, Crear link para la Validacion o
        //getstatus para verificar el estado de la validacion ya realizada!
        //agregar las librerias requeridas gson 2.8.5

        //RegisterNewUser(urlRegister.toString(), path);

        String response = getStatus(urlStatus.toString(), new StatusRek(name,dni,UUID));
        Imprimir(response); // va junto con metodo getStatus...

        //valid(new StatusRek(name,dni,UUID));

    }
    private static void Imprimir(String reko){
        StatusRek rek = new Gson().fromJson(reko, StatusRek.class);

        System.out.println("ExternalId = " + rek.getExternalId());
        System.out.println("documentId = " + rek.getDocumentId());
        System.out.println("Status = " + rek.getStatus());
        System.out.println("OcrLinesList = " + rek.getOcrLinesList().size());
        for (OcrLinesList item : rek.getOcrLinesList()) {
            System.out.println("    Line :: " + item.getLine() + " || Confidence :: " + item.getConfidence());
        }
        System.out.println("Confidence = " + rek.getConfidence());
        System.out.println("CreatedAt = " + rek.getCreatedAt());
        System.out.println("Finish_At = " + rek.getFinish_at());
        System.out.println("Ip_Client = " + rek.getIp_client());
        System.out.println("ImageSmile = " + rek.getImageSmile());
        System.out.println("ImageNeutral = " + rek.getImageNeutral());
        System.out.println("On More...  ");

    }

    private static void RegisterNewUser(String data, File file) throws Exception {
        URL url = new URL(data);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("content-type","application/octet-stream");

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

        //Se imprime la respuesta del servidor
        System.out.println(responseFromServer);
    }

    private static String getStatus(String data, StatusRek s) throws Exception {
        String respuesta = "";
        StatusRek rek = new StatusRek();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(s);
        URL url = new URL(data);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
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

    private static void valid(StatusRek s) throws IOException, URISyntaxException {
        System.out.println("Link de Validacion!");
        StringBuilder link = new StringBuilder();
        link.append("https://rekognition.bigprime.pe/").append("?")
                .append("externalId=").append(s.getExternalId()).append("&")
                .append("dni=").append(s.getDni()).append("&")
                .append("uuid=").append(s.getUuid());
        System.out.println("Link de Verificacion : " + System.lineSeparator() + link.toString());
        System.out.println("Desea Verificar Ahora? (Y/N) : ");
        Scanner scanner = new Scanner(System.in);
        String res = scanner.nextLine();
        if (res.equalsIgnoreCase("y")){
            System.out.println("Abriendo Link :");
            Desktop.getDesktop().browse(new URI(link.toString())); //navegador por defecto!
            //Runtime.getRuntime().exec(new String[]{"cmd", "/c","start chrome " + link.toString()}); // abrir chrome
        }else if(res.equalsIgnoreCase("N")){
            System.out.println("Vale, puedes copiar el link y hacerlo mas tarde!");
        }else{
            System.out.println("Solo eran esas dos Opciones : Y o N !");
        }
    }

}