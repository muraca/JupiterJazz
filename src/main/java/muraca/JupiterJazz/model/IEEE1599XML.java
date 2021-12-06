package muraca.JupiterJazz.model;

import muraca.JupiterJazz.view.utils.ErrorHandler;
import muraca.JupiterJazz.view.utils.FileHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;

public class IEEE1599XML {

    private static Random random = new Random();

    public static void readFromXML(File f) {

    }

    public static void saveToXML(Session s, File file) {
        try {
            Document doc = DocumentUtils.newDocument();

            Element root = doc.createElement("ieee1599");
            root.setAttribute("creator", s.getAuthor());
            root.setAttribute("version", "1.0");

            Element general = doc.createElement("general");
            root.appendChild(general);
            Element description = doc.createElement("description");
            general.appendChild(description);
            Element main_title = doc.createElement("main_title");
            main_title.setTextContent(s.getTitle());
            description.appendChild(main_title);
            Element author = doc.createElement("author");
            author.setTextContent(s.getAuthor());
            description.appendChild(author);

            Element logic = doc.createElement("logic");
            root.appendChild(logic);
            Element spine = doc.createElement("spine");
            logic.appendChild(spine);
            Element los = doc.createElement("los");
            logic.appendChild(los);

            Element staff_list = doc.createElement("staff_list");
            los.appendChild(staff_list);

            final int measureDurationInVTU = Constants.TIME_SIGNATURES_VTU.get(Constants.TIME_SIGNATURES_FRAC.indexOf(s.getTimeSignature().toString()));
            for (Instrument instrument: s.getInstruments()) {
                String name = instrument.getName();

                Element staff = doc.createElement("staff");
                staff_list.appendChild(staff);
                staff_list.setAttribute("id", "Instrument_" + name + "_staff");
                staff_list.setAttribute("line_number", "5");

                Element time_signature = doc.createElement("time_signature");
                staff.appendChild(time_signature);
                time_signature.setAttribute("event_ref", "TimeSignature_Instrument_" + name + "_1");
                Element time_indication = doc.createElement("time_indication");
                time_signature.appendChild(time_indication);
                time_indication.setAttribute("num", String.valueOf(s.getTimeSignature().getNum()));
                time_indication.setAttribute("den", String.valueOf(s.getTimeSignature().getDen()));

                Element time_signatureEvent = doc.createElement("event");
                spine.appendChild(time_signatureEvent);
                time_signatureEvent.setAttribute("id", "TimeSignature_Instrument_" + name + "_1");
                time_signatureEvent.setAttribute("hpos", "0");
                time_signatureEvent.setAttribute("timing", "0");

                Element clef = doc.createElement("clef");
                staff.appendChild(clef);
                clef.setAttribute("event_ref", "Clef_Instrument_" + name + "_1");
                clef.setAttribute("octave_num", "0");
                clef.setAttribute("shape", instrument.getClefShape());
                clef.setAttribute("staff_step", instrument.getClefStaffStep());

                Element clefEvent = doc.createElement("event");
                spine.appendChild(clefEvent);
                clefEvent.setAttribute("id", "Clef_Instrument_" + name + "_1");
                clefEvent.setAttribute("hpos", "0");
                clefEvent.setAttribute("timing", "0");

                Element part = doc.createElement("part");
                los.appendChild(part);
                Element voice_list = doc.createElement("voice_list");
                part.appendChild(voice_list);
                Element voice_item = doc.createElement("voice_item");
                voice_list.appendChild(voice_item);
                voice_item.setAttribute("id", "Instrument_" + name + "_0_voice");
                voice_item.setAttribute("staff_ref", "Instrument_" + name + "_staff");

                for (int i = 1; i <= s.getDurationInMeasures(); ++i) {
                    Element measure = doc.createElement("measure");
                    part.appendChild(measure);
                    measure.setAttribute("number", String.valueOf(i));

                    Element voice = doc.createElement("voice");
                    measure.appendChild(measure);
                    voice.setAttribute("voice_item_ref", "Instrument_" + name + "_0_voice");

                    int currentMeasureRemainingDuration = measureDurationInVTU;
                    while (currentMeasureRemainingDuration != 0) {
                        String eventId = "Instrument_" + name + "_voice0_measure1_ev"+voice.getChildNodes().getLength();

                        boolean isRest = random.nextInt(100) < s.getPauseProbability();
                        if (isRest && currentMeasureRemainingDuration > s.getMaxPauseDurationVTU() && //min pause is longer than remaining time
                                currentMeasureRemainingDuration <= s.getMinNoteDurationVTU()) { //min note is not longer than remaining time
                            isRest = false; //must be a note
                        }

                        int minDurationIndex = isRest ? s.getMinPauseDurationIndex() : s.getMinNoteDurationIndex();
                        int maxDurationIndex = Math.min(Constants.EVENT_DURATIONS_VTU.indexOf(currentMeasureRemainingDuration),
                                                        isRest ? s.getMaxPauseDurationIndex() : s.getMaxNoteDurationIndex());;

                        int eventDurationIndex = random.nextInt(maxDurationIndex - minDurationIndex + 1) + minDurationIndex;
                        int eventDurationVTU = Constants.EVENT_DURATIONS_VTU.get(eventDurationIndex);
                        Fraction eventDuration = Fraction.parseString(Constants.EVENT_DURATIONS_FRAC.get(eventDurationIndex));

                        Element event = doc.createElement("event");
                        spine.appendChild(event);
                        event.setAttribute("id", eventId);
                        event.setAttribute("hpos", String.valueOf(eventDurationVTU));
                        event.setAttribute("timing", String.valueOf(eventDurationVTU));

                        Element partEvent = doc.createElement(isRest ? "rest" : "chord");
                        voice.appendChild(partEvent);
                        partEvent.setAttribute("event_ref", eventId);
                        Element partEventDuration = doc.createElement("duration");
                        partEvent.appendChild(partEventDuration);
                        partEventDuration.setAttribute("num", String.valueOf(eventDuration.getNum()));
                        partEventDuration.setAttribute("den", String.valueOf(eventDuration.getDen()));

                        if (!isRest) {
                            Element notehead = doc.createElement("notehead");
                            partEvent.appendChild(notehead);

                            Element pitch = doc.createElement("pitch");
                            notehead.appendChild(pitch);
                            Tonality tonality = s.getTonality();
                            int noteId = -1;
                            int octave = 0;
                            int midiPitch = 0;
                            while ((midiPitch < instrument.getMinPitch() || midiPitch > instrument.getMaxPitch()) && noteId >= 0){
                                int noteIndex = random.nextInt(tonality.getNumberOfEnabledNotes());
                                noteId = tonality.getEnabledNoteId(noteIndex);
                                octave = random.nextInt(9);
                                midiPitch = octave * 12 + tonality.getEnabledNoteId(noteIndex);
                            }
                            pitch.setAttribute("step", tonality.getNoteName(noteId));
                            pitch.setAttribute("actual_accidental", tonality.getNoteAccidental(noteId));
                            pitch.setAttribute("octave", String.valueOf(octave));

                            Element printed_accidentals = doc.createElement("printed_accidentals");
                            pitch.appendChild(printed_accidentals);
                            Element accidental = doc.createElement(s.getTonality().getNoteAccidental(noteId));
                            printed_accidentals.appendChild(accidental);
                        }
                    }
                }
            }

            DocumentUtils.transform(doc, file);

        } catch (FileNotFoundException e) {
            FileHandler.fileNotFoundExceptionInfo(file.getName());
        } catch (ParserConfigurationException | TransformerException e) {
            ErrorHandler.xmlExportException(file.getName());
        }
    }
}
