package petrov.ivan.bsc.data

import android.net.Uri
import java.io.Serializable

// fixme лучше избавиться от мутабельности
data class Person(
    var name: String,
    val phone: String,
    var imgUri: Uri?
): Serializable