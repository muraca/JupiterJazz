package muraca.JupiterJazz.model.xml;

import muraca.JupiterJazz.model.Constants;
import muraca.JupiterJazz.model.Fraction;
import muraca.JupiterJazz.model.session.Instrument;
import muraca.JupiterJazz.model.session.Session;
import muraca.JupiterJazz.model.session.Tonality;
import muraca.JupiterJazz.view.utils.FileHandler;
import muraca.JupiterJazz.view.utils.MessageHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;

public class IEEE1599XML {

    private static Random random = new Random();

    public static void saveToXML(Session s) {
        File file = FileHandler.chooseXMLFile(FileHandler.SAVE_FILE);
        if (file == null || file.getName().isBlank()) {
            MessageHandler.showNoFileSelectedErrorMessage();
            return;
        }

        try {
            Document doc = DocumentUtils.newDocument();

            Element root = doc.createElement("ieee1599");
            root.setAttribute("creator", "JupiterJazz");
            root.setAttribute("version", "1.0");
            doc.appendChild(root);

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
                if (!instrument.isEnabled())
                    continue;

                String name = String.valueOf(instrument.getId());

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
                clef.setAttribute("staff_step", String.valueOf(instrument.getClefStaffStep()));

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
                    measure.appendChild(voice);
                    voice.setAttribute("voice_item_ref", "Instrument_" + name + "_0_voice");

                    int currentMeasureRemainingDuration = measureDurationInVTU;
                    while (currentMeasureRemainingDuration > 0) {
                        String eventId = "Instrument_" + name + "_voice0_measure" + i  + "_ev" + voice.getChildNodes().getLength();

                        boolean isRest = random.nextInt(100) < s.getPauseProbability();
                        if (isRest && currentMeasureRemainingDuration > s.getMaxPauseDurationVTU() && //min pause is longer than remaining time
                                currentMeasureRemainingDuration <= s.getMinNoteDurationVTU()) { //min note is not longer than remaining time
                            isRest = false; //must be a note
                        }

                        int minDurationIndex = isRest ? s.getMinPauseDurationIndex() : s.getMinNoteDurationIndex(),
                            maxDurationIndex = Math.min(Constants.EVENT_DURATIONS_VTU.indexOf(currentMeasureRemainingDuration),
                                                        isRest ? s.getMaxPauseDurationIndex() : s.getMaxNoteDurationIndex()),

                            eventDurationIndex = minDurationIndex >= maxDurationIndex ? maxDurationIndex :
                                    random.nextInt(maxDurationIndex - minDurationIndex + 1) + minDurationIndex,

                            eventDurationVTU = Constants.EVENT_DURATIONS_VTU.get(eventDurationIndex);

                        Fraction eventDuration = Fraction.parseString(Constants.EVENT_DURATIONS_FRAC.get(eventDurationIndex));
                        currentMeasureRemainingDuration -= eventDurationVTU;

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

                            int minPitch = instrument.getMinPitch(),
                                maxPitch = instrument.getMaxPitch(),
                                minOctave = Math.floorDiv(minPitch, 12),
                                maxOctave = Math.floorDiv(maxPitch, 12),
                                randomOctaveBound = maxOctave - minOctave + 1,
                                enabledNotes = tonality.getNumberOfEnabledNotes(),
                                noteId = -1,
                                octave = 0,
                                midiPitch = 0;

                            while (midiPitch < minPitch || midiPitch > maxPitch) {
                                int noteIndex = random.nextInt(enabledNotes);
                                noteId = tonality.getEnabledNoteId(noteIndex);
                                octave = random.nextInt(randomOctaveBound) + minOctave;

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
            MessageHandler.showXMLExportCompletedMessage(file.getName());

        } catch (FileNotFoundException e) {
            FileHandler.fileNotFoundExceptionInfo(file.getName());
        } catch (ParserConfigurationException | TransformerException e) {
            MessageHandler.showXMLExportErrorMessage(file.getName());
        }
    }
}
