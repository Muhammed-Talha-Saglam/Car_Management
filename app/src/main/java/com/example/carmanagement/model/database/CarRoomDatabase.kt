package com.example.carmanagement.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.carmanagement.di.ApplicationScope
import com.example.carmanagement.model.Car
import com.example.carmanagement.model.User
import com.example.carmanagement.model.UserType
import com.example.carmanagement.model.database.converters.Converters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider


@Database(entities = [Car::class, User::class], version = 5, exportSchema = false)
@TypeConverters(Converters::class)
abstract class CarRoomDatabase : RoomDatabase() {

    abstract fun carDao(): CarDao


    class Callback @Inject constructor(
        private val database: Provider<CarRoomDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            val dao = database.get().carDao()

            applicationScope.launch {
                dao.insertCar(Car(brand = "Mercedes", plate = "34 aa 101"))
                dao.insertCar(Car(brand = "Ford", plate = "34 bb 102"))
                dao.insertCar(Car(brand = "Porsche", plate = "34 cc 103"))
                dao.insertCar(Car(brand = "Volkswagen", plate = "34 dd 104"))
                dao.insertCar(Car(brand = "Fiat", plate = "34 ee 105"))


                dao.insertUser(User(userType = UserType.ADMIN,email = "admin@gmail.com",password = "admin"))
                dao.insertUser(User(userType = UserType.STANDART,email = "user1@gmail.com",password = "user1"))
                dao.insertUser(User(userType = UserType.STANDART,email = "user2@gmail.com",password = "user2"))
                dao.insertUser(User(userType = UserType.STANDART,email = "user3@gmail.com",password = "user3"))
                dao.insertUser(User(userType = UserType.STANDART,email = "user4@gmail.com",password = "user4"))
                dao.insertUser(User(userType = UserType.STANDART,email = "user5@gmail.com",password = "user5"))


            }

        }

    }


//    companion object {
//
//        @Volatile
//        private var INSTANCE: CarRoomDatabase? = null
//
//        fun getDatabase(context: Context): CarRoomDatabase {
//
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    CarRoomDatabase::class.java,
//                    "car_database"
//                )
//                    .fallbackToDestructiveMigration()
//                    .build()
//
//                INSTANCE = instance
//
//                instance
//            }
//
//        }
//    }

}