package until.soulsparc.com.soulsparc;

/**
 * Created by chandan on 17/08/19.
 */
import java.io.File;

public abstract class AlbumStorageDirFactory {
    public abstract File getAlbumStorageDir(String albumName);
}
