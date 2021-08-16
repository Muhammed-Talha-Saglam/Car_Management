package com.example.carmanagement.model.database

import androidx.room.*
import com.example.carmanagement.model.*
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

    @Query("SELECT * FROM cars WHERE carId not in (:ids)")
    suspend fun getAvailableCars(ids: Set<Int>): List<Car>


    @Query("SELECT * FROM cars WHERE carId = :carId ")
    fun getCar(carId: String): Flow<Car>


    //Handle Reservation Entity
    @Insert
    suspend fun insertReservation(reservation: Reservation)

    @Delete
    suspend fun deleteReservation(reservation: Reservation)

    @Query("SELECT * FROM reservations")
    fun getAllReservations(): Flow<List<Reservation>>


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


    //Handle UserHistory Entity
    @Insert
    suspend fun insertUserHistory(userHistory: UserHistory)

    @Query("SELECT * FROM userHistory WHERE userOwnerId = :userOwnerId")
    suspend fun getUserHistoryById(userOwnerId: Int) : List<UserHistory>

    @Query("Delete FROM userHistory WHERE userOwnerId = :userOwnerId")
    suspend fun clearUserHistoryByUserId(userOwnerId: Int)

    // One to many queries
    @Transaction
    @Query("SELECT * FROM cars")
    suspend fun getCarsWithReservations(): List<CarWithReservations>

    @Transaction
    @Query("SELECT * FROM users")
    suspend fun getUsersWithUserHistories(): List<UserWithUserHistories>


}