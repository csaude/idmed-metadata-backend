import mz.org.idmed.metadata.protection.CorsFilter
import mz.org.idmed.metadata.protection.CustomAppRestAuthTokenJsonRenderer
import mz.org.idmed.metadata.protection.CustomSecurityEventListener
import mz.org.idmed.metadata.protection.UserPasswordEncoderListener

// Place your Spring DSL code here
beans = {
    userPasswordEncoderListener(UserPasswordEncoderListener)
    corsFilterTest(CorsFilter)
    accessTokenJsonRenderer(CustomAppRestAuthTokenJsonRenderer)
    customerSecurityEventListener(CustomSecurityEventListener)
}
