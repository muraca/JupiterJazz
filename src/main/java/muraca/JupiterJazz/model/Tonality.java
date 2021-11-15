package muraca.JupiterJazz.model;

import lombok.Data;

@Data
public class Tonality { //TODO
    private int rootNote;
    private int[] notes;
    public String[] noteNames = new String[]{"C", "D", "E", "F", "G", "A", "B"};
    public String[] noteAccidentals;

    public int getNote(int index) {
        index = index % notes.length;
        return notes[index];
    }

    public String getNoteName(int index) {
        index = index % notes.length;
        return noteNames[notes[index]];
    }

    public String getNoteAccidental(int index) {
        index = index % notes.length;
        return noteAccidentals[notes[index]];
    }

    public int getNumberOfNotesInKey() {
        return notes.length;
    }
}
