package petrov.ivan.bsc.utils

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import petrov.ivan.bsc.R

@BindingAdapter("imageParty")
fun ImageView.setPartyImage(imgUrl: String) {
    Glide.with(context)
        .load(imgUrl)
        .placeholder(R.drawable.ic_party)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .into(this)
}

@BindingAdapter("imagePerson")
fun ImageView.loadPersonImage(imgUri: Uri?) {
    Glide.with(context)
        .load(imgUri)
        .apply(RequestOptions.circleCropTransform())
        .placeholder(R.drawable.ic_person)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .into(this)
}