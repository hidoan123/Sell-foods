package com.example.orderfood.sharedPre

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.orderfood.model.CartModel


class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "food.db", null, 1) {


    companion object {
        private const val TABLE_CART = "cart"
        const val COLUMN_IMAGE = "image"
        const val COLUMN_PRICE = "price"
        const val COLUMN_NAME = "name"
        const val COLUMN_MOTA = "mota"
        const val COLUMN_SOLUONG = "soluong"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_ID_SANPHAM = "idsanpham"

    }

    private val createCart = """
            CREATE TABLE $TABLE_CART (
             id INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_IMAGE TEXT,
                $COLUMN_PRICE LONG,
                $COLUMN_NAME TEXT,
                $COLUMN_MOTA TEXT,
                $COLUMN_SOLUONG INTEGER,
                $COLUMN_EMAIL TEXT,
                $COLUMN_ID_SANPHAM INTEGER
            )
        """

//    private val createCart =
//        "CREATE TABLE $TABLE_CART (id INTEGER PRIMARY KEY AUTOINCREMENT," +
//                " image TEXT, price LONG , name TEXT, mota TEXT, soluong INTEGER, email TEXT)"


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createCart)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_CART")
        onCreate(db)
    }

    fun addCart(data: CartModel, email: String): Long {
        return if (isExists(data.image)) {
            -2
        } else {
            val db = this.writableDatabase
            val values = ContentValues()
            values.put("image", data.image)
            values.put("price", data.price)
            values.put("name", data.name)
            values.put("mota", data.description)
            values.put("soluong", data.amount)
            values.put("email", email)
            values.put("idsanpham", data.idsp)
            db.insert("cart", null, values)
        }
    }

    fun removeCart(id: Int): Int {
        val db = this.writableDatabase
        return db.delete("cart", "id=?", arrayOf(id.toString()))
    }

//    @SuppressLint("Range")
//    fun getAllRank2(chude: String): List<Rank> {
//        val list: MutableList<Rank> = ArrayList<Rank>()
//        val db = this.writableDatabase
//        val sql = "SELECT * FROM Rank WHERE chude =?"
//        val cursor = db.rawQuery(sql, arrayOf(chude))
//        if (cursor.moveToFirst()) {
//            while (!cursor.isAfterLast) {
//                val name = cursor.getString(cursor.getColumnIndex("name"))
//                val diem = cursor.getString(cursor.getColumnIndex("diem"))
//                list.add(Rank(name, diem))
//                cursor.moveToNext()
//            }
//        }
//        return list
//    }

    @SuppressLint("Range")
    fun getAllCart(emailQuery: String): ArrayList<CartModel> {
        val list: ArrayList<CartModel> = ArrayList()
        val db = this.writableDatabase
        val query = "SELECT * FROM $TABLE_CART WHERE email =?"
        val cursor = db.rawQuery(query, arrayOf(emailQuery))

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val idsp = cursor.getInt(cursor.getColumnIndex("idsanpham"))
                val image = cursor.getString(cursor.getColumnIndex("image"))
                val name = cursor.getString(cursor.getColumnIndex("name"))
                val description = cursor.getString(cursor.getColumnIndex("mota"))
                val price = cursor.getLong(cursor.getColumnIndex("price"))
                val amount = cursor.getInt(cursor.getColumnIndex("soluong"))
                list.add(CartModel(image, name, description, price, amount, id, idsp))
            } while (cursor.moveToNext())

        }
        cursor.close()
        db.close()
        return list
    }

    private fun isExists(image: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT 1 FROM cart WHERE image = ?", arrayOf(image))

        val exists = (cursor.count > 0)
        cursor.close()
        return exists
    }
}