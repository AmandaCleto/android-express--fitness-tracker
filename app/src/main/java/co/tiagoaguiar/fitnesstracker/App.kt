package co.tiagoaguiar.fitnesstracker

import android.app.Application
import co.tiagoaguiar.fitnesstracker.model.AppDatabase


// executes along with the initialization of the app
// must be add in android manifest
class App : Application() {

    lateinit var db: AppDatabase
    override fun onCreate() {
        super.onCreate()

        db = AppDatabase.getDatabase(this)
    }
}