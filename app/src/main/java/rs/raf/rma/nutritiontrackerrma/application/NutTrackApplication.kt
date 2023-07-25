package rs.raf.rma.nutritiontrackerrma.application

import android.app.Application
import rs.raf.rma.nutritiontrackerrma.modules.*
import timber.log.Timber

class NutTrackApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        init()
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
            coreModule
        )


/*        startKoin {
            androidLogger(Level.ERROR)
            // Use application context
            androidContext(this@NutTrackApplication)
            // Use properties from assets/koin.properties
            androidFileProperties()
            // Use koin fragment factory for fragment instantiation
            fragmentFactory()
            // modules
            modules(modules)
        }*/
    }
}