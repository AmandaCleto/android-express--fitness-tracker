package co.tiagoaguiar.fitnesstracker.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CalcDao {
    @Insert
    fun insert(calc: Calc) // we don't need the body of the function because the DAO do it by the @Insert

    //:type is the reference of getRegisterByType's parameter
    //type is the ColumnInfo that we declared in Calc
    @Query("SELECT * FROM Calc WHERE type = :type")
    fun getRegisterByType(type: String) : List<Calc>
}
