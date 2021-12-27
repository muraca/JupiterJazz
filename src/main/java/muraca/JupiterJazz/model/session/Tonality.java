package muraca.JupiterJazz.model.session;

import muraca.JupiterJazz.model.Notes;

public class Tonality {
    private boolean[] notes = new boolean[12];
    public int[] noteNamePos = new int[12];
    public int[] noteAccidentalPos = new int[12];

    public void enableNote(int noteId) {
        noteId = noteId % 12;
        notes[noteId] = true;
    }

    public void disableNote(int noteId) {
        noteId = noteId % 12;
        notes[noteId] = false;
    }

    public boolean isNoteEnabled(int noteId) {
        noteId = noteId % 12;
        return notes[noteId];
    }

    public String getNoteName(int noteId) {
        noteId = noteId % 12;
        return Notes.getNoteName(noteId, noteNamePos[noteId]);
    }

    public int getNoteNamePos(int noteId) {
        return noteNamePos[noteId];
    }

    public void setNoteName(int noteId, int noteNamePosition) {
        noteId = noteId % 12;
        noteNamePos[noteId] = noteNamePosition;
    }

    public String getNoteAccidental(int noteId) {
        noteId = noteId % 12;
        return Notes.getNoteAccidental(noteId, noteAccidentalPos[noteId]);
    }

    public int getNoteAccidentalPos(int noteId) {
        return noteAccidentalPos[noteId];
    }

    public void setNoteAccidental(int noteId, int noteAccidentalPosition) {
        noteId = noteId % 12;
        noteAccidentalPos[noteId] = noteAccidentalPosition;
    }

    public int getNumberOfEnabledNotes() {
        int n = 0;
        for (boolean note: notes)
            n += note ? 1 : 0;
        return n;
    }

    public int getEnabledNoteId(int index) {
        index = index % 12;
        for (int i = 0; i < 12; ++i) {
            if (notes[i]) {
                if (index == 0) {
                    return i;
                }
                index --;
            }
        }
        return -1;
    }
}
