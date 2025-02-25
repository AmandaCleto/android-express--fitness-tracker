package co.tiagoaguiar.fitnesstracker.model

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface CalcDao {
    @Insert
    fun insert(calc: Calc) // we don't need the body of the function because the DAO do it by the @Insert
}