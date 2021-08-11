package com.example.carmanagement.model.database

import androidx.room.*
import com.example.carmanagement.model.Car
import com.example.carmanagement.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface CarDao {

    // Handle Car entity
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCar(car: Car)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCar(car: Car)

    @Delete
    suspend fun deleteCar(car: Car)

    @Query("SELECT * FROM cars")
    fun getAllCars(): Flow<List<Car>>

    @Query("SELECT * FROM cars WHERE isAvailable = 1")
    fun getAvailableCars(): Flow<List<Car>>


    @Query("SELECT * FROM cars WHERE carId = :carId ")
    fun getCar(carId: String): Flow<Car>



    // Handle User entity
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: User)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<User>>

    @Query("SELECT * FROM users WHERE email = :email AND password = :passWord")
    suspend fun getUser(email: String, passWord: String): User


    @Query("SELECT EXISTS(SELECT * FROM users WHERE email = :email AND password = :passWord)")
    suspend fun authUser(email: String, passWord: String) : Boolean

    @Transaction
    @Query("SELECT * FROM users")
    fun getUsersAndCars(): Flow<List<UserAndCar>>


}