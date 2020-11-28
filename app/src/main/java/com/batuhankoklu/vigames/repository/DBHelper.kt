package com.batuhankoklu.vigames.repository

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.batuhankoklu.vigames.model.ScreenshotModel
import com.batuhankoklu.vigames.model.VideoGame
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONObject

class DBHelper(val context: Context) : SQLiteOpenHelper(context,DBHelper.DATABASE_NAME,null,DBHelper.DATABASE_VERSION) {
    private val TABLE_NAME="VideoGames"
    private val COL_ID = "id"
    private val COL_V_ID = "v_id"
    private val COL_V_NAME = "v_name"
    private val COL_V_IMAGE = "v_image"
    private val COL_V_SCREENSHOTS = "v_screenshots"
    companion object {
        private val DATABASE_NAME = "SQLITE_DATABASE"//database adı
        private val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_V_ID  VARCHAR(256), $COL_V_NAME  VARCHAR(256), $COL_V_IMAGE  VARCHAR(256), $COL_V_SCREENSHOTS  VARCHAR(256))"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun insertData(videoGame : VideoGame){
        val sqliteDB = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_V_ID , videoGame.id)
        contentValues.put(COL_V_NAME, videoGame.name)
        contentValues.put(COL_V_IMAGE, videoGame.background_image)

        //listeyi jsonstring şeklinde atmalıyız
        var gson = Gson()
        var listString = gson.toJson(videoGame.short_screenshots)
        contentValues.put(COL_V_SCREENSHOTS, listString)


        val result = sqliteDB.insert(TABLE_NAME,null,contentValues)

        //Toast.makeText(context,if(result != -1L) "Kayıt Başarılı" else "Kayıt yapılamadı.", Toast.LENGTH_SHORT).show()
    }

    fun readData():MutableList<VideoGame>{
        val videoGameList = mutableListOf<VideoGame>()
        val sqliteDB = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val result = sqliteDB.rawQuery(query,null)
        if(result.moveToFirst()){
            do {
                //screenshots
                var gson = Gson()
                val type = object : TypeToken<ArrayList<ScreenshotModel>>() {}.type
                var screenshots = gson.fromJson<ArrayList<ScreenshotModel>>(result.getString(result.getColumnIndex(COL_V_SCREENSHOTS)),type)

                val videoGame = VideoGame(
                    result.getString(result.getColumnIndex(COL_V_ID)).toInt(),
                    result.getString(result.getColumnIndex(COL_V_NAME)),
                    "",
                    result.getString(result.getColumnIndex(COL_V_IMAGE)),
                    0.0,
                    screenshots
                )
                videoGameList.add(videoGame)
            }while (result.moveToNext())
        }
        result.close()
        sqliteDB.close()
        return videoGameList
    }

    fun deleteData(videoGame: VideoGame){
        val sqliteDB = this.writableDatabase
        sqliteDB.delete(TABLE_NAME,COL_V_ID + "=" + videoGame.id,null)
        sqliteDB.close()
    }

    fun deleteAllData(){
        val sqliteDB = this.writableDatabase
        sqliteDB.delete(TABLE_NAME,null,null)
        sqliteDB.close()

    }

    fun deleteTable(){
        val db = this.writableDatabase
        db.execSQL("DROP TABLE " + TABLE_NAME)
        db.close()
    }


}
