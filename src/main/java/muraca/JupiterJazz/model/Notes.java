package muraca.JupiterJazz.model;

import java.util.HashMap;

public class Notes {
    public static final String[][] NOTE_COMPACTNAMES = {
            {"C", "Dbb", "B#"},
            {"C#", "Db", "Bx"},
            {"D", "Ebb", "Cx"},
            {"D#", "Eb", "Fbb"},
            {"E", "Fb", "Dx"},
            {"F", "Gbb", "E#"},
            {"F#", "Gb", "Ex"},
            {"G", "Abb", "F#"},
            {"G#", "Ab"},
            {"A", "Bbb", "Gx"},
            {"A#", "Bb", "Cbb"},
            {"B", "Cb", "Ax"}
    };

    public static final HashMap<String, String> ACCIDENTALS = new HashMap<>() {{
        put("","natural");
        put("b","flat");
        put("bb","double_flat");
        put("#","sharp");
        put("x","double_sharp");
    }};

    public static String getNoteName(int noteId, int pos) {
        String compactName = NOTE_COMPACTNAMES[noteId][pos];
        return String.valueOf(compactName.charAt(0));
    }

    public static String getNoteAccidental(int noteId, int pos) {
        String compactName = NOTE_COMPACTNAMES[noteId][pos];
        return ACCIDENTALS.get(compactName.substring(1));
    }

    public static String midiToEnglish(int midiPitch) {
        StringBuilder res = new StringBuilder();
        res.append(NOTE_COMPACTNAMES[midiPitch % 12][0]);
        res.append(midiPitch / 12 - 1);
        return res.toString();
    }
}
