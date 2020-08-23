package petrov.ivan.bsc.utils

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment


class RuntimePermissionHelper(val fragment: Fragment) {

    fun isPermissionGranted(permission: String): Boolean {
        if (ContextCompat.checkSelfPermission(fragment.context!!, permission) != PackageManager.PERMISSION_GRANTED)
                return false
        return true
    }

    fun requestPermissions(requestCode: Int, permissions: Array<String>) {
        fragment.requestPermissions(permissions, requestCode)
    }

    companion object {
        const val PERMISSIONS_REQUEST_CODE = 101
    }
}