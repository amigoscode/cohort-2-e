package com.amigoscode;

import com.amigoscode.domain.note.Note;
import com.amigoscode.domain.schedule.Schedule;
import com.amigoscode.domain.schedule.Status;
import com.amigoscode.domain.version.Version;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TestScheduleFactory {

    private static int scheduleSequence = 30;
    private static int versionSequence = 5;
    private static int noteSequence = 10;
    private static int userSequence = 15;

    public static Schedule create() {
        scheduleSequence++;
        noteSequence++;
        versionSequence++;
        Version version = new Version(
                null,
                versionSequence,
                scheduleSequence,
                userSequence,
                ZonedDateTime.of(2023, 6, 24, 12, 40, 00, 0, ZoneId.of("UTC")),
                ZonedDateTime.of(2023, 6, 24, 12, 40, 00, 0, ZoneId.of("UTC")),
                ZonedDateTime.of(2023, 6, 24, 12, 40, 00, 0, ZoneId.of("UTC")),
                0,
                2
        );
        Note note = new Note(
                null,
                scheduleSequence,
                versionSequence,
                "this is a note_" + noteSequence,
                ZonedDateTime.of(2023, 6, 24, 12, 40, 00, 0, ZoneId.of("UTC")),
                userSequence);

        return new Schedule(
                null,
                scheduleSequence,
                Status.REVIEW,
                version,
                note
        );
    }
}