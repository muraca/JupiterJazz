# JupiterJazz

This project was developed using Java and Java Swing for "Programmazione per la musica" course, Università degli Studi di Milano, AA 2021/2022.

<a href="https://ieee1599.lim.di.unimi.it">IEEE 1599</a> is a multi-layer XML-based format for music, developed by Laboratorio di Informatica Musicale, Dipartimento di Informatica "Giovanni Degli Antoni", Università degli Studi di Milano.

JupiterJazz generates a random composition represented by an IEEE 1599 file, based on a series of user-defined parameters, including:
- BPM, time signature, length in measures;
- Note/pause probability;
- Note duration (min and max);
- Pause duration (min and max);
- Custom tonality (enable/disable notes, choose note names);
- Instruments based on General MIDI standard instruments, with min and max pitch, and custom clef.

Users can save the current session containing all the parameters, and load an existing session XML file.