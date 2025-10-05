import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.ComposeUIViewController
import com.santimattius.kmp.skeleton.core.ui.components.LocalNativeViewFactory
import com.santimattius.kmp.skeleton.core.ui.components.NativeViewFactory


fun MainViewController(
    nativeViewFactory: NativeViewFactory
) = ComposeUIViewController {
    CompositionLocalProvider(LocalNativeViewFactory provides nativeViewFactory) {
        App()
    }
}
