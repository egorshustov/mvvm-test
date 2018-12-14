package com.egorshustov.mvvmtest

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Database
import android.os.AsyncTask
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * Singleton class for RoomDatabase access.
 */

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java, "app_database")
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallback) // Attach callback to the database
                        .build()
                }
            }
            return INSTANCE
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
                PopulateDbAsyncTask(INSTANCE).execute()
            }
        }

        private class PopulateDbAsyncTask
        internal constructor(db: AppDatabase?) : AsyncTask<Void, Void, Void>() {
            private val noteDao: NoteDao = db!!.noteDao()

            override fun doInBackground(vararg voids: Void): Void? {
                noteDao.insert(Note("Title 1", "Description 1", 1))
                noteDao.insert(Note("Title 2", "Description 2", 2))
                noteDao.insert(Note("Title 3", "Description 3", 3))
                return null
            }
        }
    }
}