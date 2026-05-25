package com.hnem06.takenote;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {

    public abstract NoteDAO NoteDao();

    private static volatile NoteDatabase INS;
    private static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static NoteDatabase getDatabase(final Context context) {
        if (INS == null) {
            synchronized (NoteDatabase.class) {
                if (INS == null) {
                    INS = Room.databaseBuilder(context.getApplicationContext(),
                                    NoteDatabase.class, "note_db")
                            .build();
                }
            }
        }
        return INS;
    }

}
