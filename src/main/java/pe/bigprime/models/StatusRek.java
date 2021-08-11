package pe.bigprime.models;

import java.util.List;

public class StatusRek{
    private String externalId;
    private String dni;
    private String uuid;

    public String documentId;
    public String confidence;
    public String status;
    public List<OcrLinesList> ocrLinesList;
    public String createdAt;
    public String finish_at;
    public String ip_client;
    public String imageSmile;
    public String imageNeutral;

    public StatusRek(){

    }

    public StatusRek(String externalId, String dni, String uuid) {
        this.externalId = externalId;
        this.dni = dni;
        this.uuid = uuid;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OcrLinesList> getOcrLinesList() {
        return ocrLinesList;
    }

    public void setOcrLinesList(List<OcrLinesList> ocrLinesList) {
        this.ocrLinesList = ocrLinesList;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getFinish_at() {
        return finish_at;
    }

    public void setFinish_at(String finish_at) {
        this.finish_at = finish_at;
    }

    public String getIp_client() {
        return ip_client;
    }

    public void setIp_client(String ip_client) {
        this.ip_client = ip_client;
    }

    public String getImageSmile() {
        return imageSmile;
    }

    public void setImageSmile(String imageSmile) {
        this.imageSmile = imageSmile;
    }

    public String getImageNeutral() {
        return imageNeutral;
    }

    public void setImageNeutral(String imageNeutral) {
        this.imageNeutral = imageNeutral;
    }

}