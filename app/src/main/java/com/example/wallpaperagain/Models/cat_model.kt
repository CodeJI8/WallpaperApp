import android.net.Uri
import java.util.Date


data class cat_model(
    var category: String? = null,

    var category_image: Uri? = null,
    var timestamp: Date? = null

    )
{
    constructor() : this(null, null, null)
}