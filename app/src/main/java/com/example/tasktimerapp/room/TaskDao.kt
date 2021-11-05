package com.example.tasktimerapp.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {
    @Query("SELECT * FROM Tasks")
    fun getAllTasks(): LiveData<List<Data>>

    @Query("SELECT * FROM Tasks WHERE pk = :givenPk")
    fun getTask(givenPk:Int): Data

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addNewTask(task: Data)

    @Delete
    fun deleteTask(task: Data)

    @Update
    fun updateTask(task: Data)
}