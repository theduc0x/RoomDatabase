package com.fftools.roomdatabase.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.fftools.roomdatabase.model.Note
import com.fftools.roomdatabase.utils.Constants.DATABASE_NAME
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [Note::class], // Các bảng
    version = 1, // Khi thay đổi database(bảng, trường) thì tăng version để tự cập nhật
    exportSchema = true // Lưu lại 1 file để so sánh khi có sự thay đổi (thay đổi version, thêm, sửa dữ liệu model)
)
abstract class DatabaseHelper: RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var instance: DatabaseHelper? = null

        fun getInstance(context: Context): DatabaseHelper {
            return instance ?: synchronized(this) {
                val instanceCreate =
                    Room.databaseBuilder(context, DatabaseHelper::class.java, DATABASE_NAME)
                        .setJournalMode(JournalMode.TRUNCATE) // Kiểu lưu file
                        .fallbackToDestructiveMigration()
//                        .createFromAsset("ok")
//                        .allowMainThreadQueries() // Có cho gọi trong Main Thread hay không
                        .addCallback(object: RoomDatabase.Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                CoroutineScope(Dispatchers.IO).launch {
                                    val editTime = System.currentTimeMillis()
                                    val noteDao = getInstance(context).noteDao()
                                    noteDao.insertNote(Note(title = "Note 1", content = "Note 1", editTime = editTime - 100))
                                    noteDao.insertNote(Note(title = "Note 2", content = "Note 2", editTime = editTime - 90))
                                    noteDao.insertNote(Note(title = "Note 3", content = "Note 3", editTime = editTime - 80))
                                    noteDao.insertNote(Note(title = "Note 4", content = "Note 4", editTime = editTime - 70))
                                    noteDao.insertNote(Note(title = "Note 5", content = "Note 5", editTime = editTime - 60))
                                    noteDao.insertNote(Note(title = "Note 6", content = "Note 6", editTime = editTime - 50))
                                    noteDao.insertNote(Note(title = "Note 7", content = "Note 7", editTime = editTime - 40))
                                    noteDao.insertNote(Note(title = "Note 8", content = "Note 8", editTime = editTime - 30))
                                    noteDao.insertNote(Note(title = "Note 9", content = "Note 9", editTime = editTime - 20))
                                    noteDao.insertNote(Note(title = "Note 10", content = "Note 10", editTime = editTime - 10))
                                }
                            }
                        })
                        .build()
                instance = instanceCreate
                instance!!
            }
        }
    }
}