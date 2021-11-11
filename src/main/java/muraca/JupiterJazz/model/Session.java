package muraca.JupiterJazz.model;

import lombok.Data;

@Data
public class Session {

    private String author = "";
    private String title = "";

    private int bpm = 60;
    private TimeSignature timeSignature = new TimeSignature();
    private int durationInMeasures = 60;

    private int pauseProbability = 25; //0 to 99

    private int minPitch = 1;
    private int maxPitch = 88;

    private int minNoteDuration = 1;
    private int maxNoteDuration = 2;

    private int minPauseDuration = 11;
    private int maxPauseDuration = 12;

    //TODO key

    public Session() {}

    public float getDurationInSeconds() {
        float beatDurationInSeconds = 60.f / bpm * 4.f / timeSignature.getInverseBeatNoteLength();
        float durationInSeconds = timeSignature.getBeatsPerMeasure() * beatDurationInSeconds * durationInMeasures;
        return Math.round(durationInSeconds * 10) / 10.f; //only one decimal digit
    }
}
