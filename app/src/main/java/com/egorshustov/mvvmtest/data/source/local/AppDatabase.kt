package com.egorshustov.mvvmtest.data.source.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Database
import android.os.AsyncTask
import androidx.sqlite.db.SupportSQLiteDatabase
import com.egorshustov.mvvmtest.data.Note

/**
 * Singleton class for RoomDatabase access.
 */

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NotesDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java, "app_database"
                        )
                            .fallbackToDestructiveMigration()
                            .addCallback(roomCallback) // Attach callback to the database
                            .build()
                    }
                }
            }
            return INSTANCE!!
        }

        fun destroyInstance() {
            INSTANCE = null
        }

        // Lets populate RoomDatabase after we created it
        // and add some notes in it, so we can later fill our RecyclerView
        // before we added any notes ourselves
        // (must be static, because later we will call it from getInstance method,
        // which is static too):

        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // After the database was created:
                PopulateDbAsyncTask(INSTANCE)
                    .execute()
            }
        }

        private class PopulateDbAsyncTask
        internal constructor(db: AppDatabase?) : AsyncTask<Void, Void, Void>() {
            private val notesDao: NotesDao = db!!.noteDao()

            override fun doInBackground(vararg voids: Void): Void? {
                notesDao.insert(Note("Title 1", "Description 1", 1))
                notesDao.insert(Note("Title 2", "Description 2", 2))
                notesDao.insert(Note("Title 3", "Description 3", 3))
                return null
            }
        }
    }
}