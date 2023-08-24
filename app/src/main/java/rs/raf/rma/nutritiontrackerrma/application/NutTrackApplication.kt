package rs.raf.rma.nutritiontrackerrma.application

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin
import rs.raf.rma.nutritiontrackerrma.modules.*
import org.koin.core.logger.Level
import timber.log.Timber

class NutTrackApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        init()
        val sharedPreferencesManager = SharedPreferencesManager.getInstance()
        sharedPreferencesManager.initialize(this)

    }

    private fun init() {
        initTimber()
        initKoin()
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

    private fun initKoin() {
        val modules = listOf(

            coreModule,
            loginModule,
            filterModule,
            categoryModule,
            mealsModule,
            savedMealsModule,
            mealStaisticsModule,
            ingredientsModule
        )

        startKoin {
            androidLogger(Level.ERROR)
            // Use application context
            androidContext(this@NutTrackApplication)
            // Use properties from assets/koin.properties
            androidFileProperties()
            // Use koin fragment factory for fragment instantiation
            fragmentFactory()
            // modules
            modules(modules)
        }
    }
}