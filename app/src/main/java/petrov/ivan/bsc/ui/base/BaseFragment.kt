package petrov.ivan.bsc.ui.base

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import petrov.ivan.bsc.R
import petrov.ivan.bsc.application.MyApplication
import petrov.ivan.bsc.components.ApplicationComponents

abstract class BaseFragment : Fragment() {

    protected lateinit var application: MyApplication

    protected val applicationComponents: ApplicationComponents by lazy(mode = LazyThreadSafetyMode.NONE) {
        application.getApplicationComponent()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        application = requireNotNull(this.activity).application as MyApplication
        updateAppTitle(getTitle())
    }

    private fun updateAppTitle(value: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = value
    }

    open fun getTitle(): String {
        return application.getString(R.string.app_name)
    }
}