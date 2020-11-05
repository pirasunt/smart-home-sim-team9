package Custom.CustomXStream;
import Enums.ProfileType;
import Models.UserProfileModel;
import com.thoughtworks.xstream.XStream;

public class CustomUserXStream extends XStream{
    public CustomUserXStream() {
        XStream.setupDefaultSecurity(this);
        super.allowTypes(
                new Class[] {
                        UserProfileModel.class, ProfileType.class
                });
        super.alias("UserProfileModel", UserProfileModel.class);
        super.alias("ProfileType", ProfileType.class);
    }
}
