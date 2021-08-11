package pe.bigprime.models;

public class OcrLinesList{
    private String line;
    private String confidence;

    public OcrLinesList() {
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }
}